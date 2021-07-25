package CommonFunLibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
WebDriver driver;
WebDriverWait wait;
public LoginPage(WebDriver driver)
{
	this.driver=driver;
}
//repository for Login
@FindBy(name="btnreset")
WebElement ResetBtn;
@FindBy(id="username")
WebElement EnterUsername;
@FindBy(xpath="//input[@id='password']")
WebElement EnterPassword;
@FindBy(name="btnsubmit")
WebElement LoginBtn;
public void verifyLogin(String username,String password)
{
	wait= new WebDriverWait(driver, 20);
	wait.until(ExpectedConditions.elementToBeClickable(ResetBtn));
	this.ResetBtn.click();
	this.EnterUsername.sendKeys(username);
	this.EnterPassword.sendKeys(password);
	this.LoginBtn.click();
}
}
