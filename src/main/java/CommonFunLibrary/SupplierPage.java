package CommonFunLibrary;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class SupplierPage {
WebDriver driver;
WebDriverWait wait;
public SupplierPage(WebDriver driver)
{
	this.driver=driver;
}
@FindBy(xpath="(//a[text()='Suppliers'])[2]")
WebElement clickSuppierlink;
@FindBy(xpath="(//span[@data-caption='Add'])[1]")
WebElement clickAddIcon;
@FindBy(name="x_Supplier_Number")
WebElement SupplierNumber;
@FindBy(name="x_Supplier_Name")
WebElement SupplierName;
@FindBy(name="x_Address")
WebElement Address;
@FindBy(name="x_City")
WebElement City;
@FindBy(name="x_Country")
WebElement Country;
@FindBy(name="x_Contact_Person")
WebElement ContactPerson;
@FindBy(name="x_Phone_Number")
WebElement PhoneNumber;
@FindBy(name="x__Email")
WebElement Email;
@FindBy(name="x_Mobile_Number")
WebElement MobileNumber;
@FindBy(name="x_Notes")
WebElement Notes;
@FindBy(name="btnAction")
WebElement ClickAddBtn;
@FindBy(xpath="//button[contains(text(),'OK!')]")
WebElement clickConfirmOkBtn;
@FindBy(xpath="(//button[text()='OK'])[6]")
WebElement clickAlertOkBtn;
@FindBy(xpath="//span[@data-caption='Search']")
WebElement clickSearchPanelBtn;
@FindBy(name="psearch")
WebElement SearchTextbox;
@FindBy(name="btnsubmit")
WebElement SearchBtn;
@FindBy(xpath="//table[@id='tbl_a_supplierslist']")
WebElement supplierTable;
public String verifySupplier(String suppliername,String Address,String City,String Country,
	String ContactPerson,String PhoneNumber,String Email,String MobileNumber,String Note)
{
	String res="";
	wait= new WebDriverWait(driver, 20);
	wait.until(ExpectedConditions.elementToBeClickable(clickSuppierlink));
	this.clickSuppierlink.click();
	wait.until(ExpectedConditions.visibilityOf(clickAddIcon));
	this.clickAddIcon.click();
	wait.until(ExpectedConditions.visibilityOf(SupplierNumber));
	String suppNumber=SupplierNumber.getAttribute("value");
	this.SupplierName.sendKeys(suppliername);
	this.Address.sendKeys(Address);
	this.City.sendKeys(City);
	this.Country.sendKeys(Country);
	this.ContactPerson.sendKeys(ContactPerson);
	this.PhoneNumber.sendKeys(PhoneNumber);
	this.Email.sendKeys(Email);
	this.MobileNumber.sendKeys(MobileNumber);
	this.Notes.sendKeys(Note);
	this.ClickAddBtn.sendKeys(Keys.ENTER);
	wait.until(ExpectedConditions.elementToBeClickable(clickConfirmOkBtn));
	this.clickConfirmOkBtn.click();
	wait.until(ExpectedConditions.elementToBeClickable(clickAlertOkBtn));
	this.clickAlertOkBtn.click();
	if(!SearchTextbox.isDisplayed())
	//click on search panel
	this.clickSearchPanelBtn.click();
	this.SearchTextbox.clear();
	this.SearchTextbox.sendKeys(suppNumber);
	this.SearchBtn.click();
	wait.until(ExpectedConditions.visibilityOf(supplierTable));
	String expected=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();
	Reporter.log(suppNumber+"     "+expected,true);
if(suppNumber.equals(expected))
{
	res="pass";
}
else
{
	res="Fail";
}
return res;
}







}
