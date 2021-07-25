package CommonFunLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import Utilities.PropertyFileUtil;

public class FunctionLibrary {
	public static WebDriver driver;
	public static WebDriver startBrowser(WebDriver driver) throws Throwable, Throwable
	{
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "CommonDrivers/geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else
			if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Chrome"))
			{
				System.setProperty("webdriver.chrome.driver", "CommonDrivers/chromedriver.exe");
				driver = new ChromeDriver();
				driver.manage().window().maximize();

			}
			else
				if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("IE"))
				{
					System.setProperty("webdriver.ie.driver", "CommonDrivers/IEDriverServer.exe");
					driver = new InternetExplorerDriver();
					driver.manage().window().maximize();

				}
		return driver;

	}

	public static void openApplication(WebDriver driver) throws Throwable, Throwable
	{
		driver.get(PropertyFileUtil.getValueForKey("URL"));
	}
	//method for wait for element 
	public static void waitForElement(WebDriver driver,String locatorType,
			String locatorValue,String waitTime)
	{
		WebDriverWait myWait = new WebDriverWait(driver, Integer.parseInt(waitTime));
		if(locatorType.equalsIgnoreCase("id"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
		}
		else if(locatorType.equalsIgnoreCase("name"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
		}
		else if(locatorType.equalsIgnoreCase("xpath"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
		}
	}
	//method for type Action
	public static void typeAction(WebDriver driver,String locatorType,String locatorValue,
			String testData)
	{
		if(locatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorValue)).clear();
			driver.findElement(By.id(locatorValue)).sendKeys(testData);
		}
		else if(locatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(locatorValue)).clear();
			driver.findElement(By.name(locatorValue)).sendKeys(testData);
		}
		else if(locatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locatorValue)).clear();
			driver.findElement(By.xpath(locatorValue)).sendKeys(testData);
		}

	}
	//method for clickaction
	public static void clickAction(WebDriver driver,String locatorType,String locatorValue)
	{
		if(locatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorValue)).sendKeys(Keys.ENTER);
		}
		else if(locatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(locatorValue)).click();
		}
		else if(locatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locatorValue)).click();
		}
	}
	//method for titleValidation
	public static void titleValidation(WebDriver driver,String exp_Title)
	{
		String act_Title=driver.getTitle();
		Assert.assertEquals(act_Title, exp_Title);
	}
	//method for close application
	public static void CloseApplication(WebDriver driver)
	{
		driver.close();
	}
	public static String generateDate()
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY_MM_dd_ss");
				return sdf.format(date);
	}
	//method for capture data
	public static void captureData(WebDriver driver,String locatorType,String locatorValue)throws Throwable
	{
		String supplierNum="";
		if(locatorType.equalsIgnoreCase("name"))
		{
			supplierNum=driver.findElement(By.name(locatorValue)).getAttribute("value");
		}
		else if(locatorType.equalsIgnoreCase("id"))
		{
			supplierNum =driver.findElement(By.id(locatorValue)).getAttribute("value");
		}
		//write into capture data folder
		FileWriter fw = new FileWriter("./CaptureData/supplier.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(supplierNum);
		bw.flush();
		bw.close();
	}
	//method for supplier table
	public static void supplierTable(WebDriver driver,String colNum )throws Throwable
	{
		//read data from above notepad file
		FileReader fr = new FileReader("./CaptureData/supplier.txt");
		BufferedReader br = new BufferedReader(fr);
		String exp_data =br.readLine();
		//convert string into integer
		int column = Integer.parseInt(colNum);
		//if search text is not displayed click on search panel button
	WebElement searchTextbox =driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox")));
			if(!searchTextbox.isDisplayed())
				//click on search panel 
				Thread.sleep(5000);
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panael"))).click();
	Thread.sleep(5000);
	searchTextbox.clear();
	Thread.sleep(5000);
	searchTextbox.sendKeys(exp_data);
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
	Thread.sleep(5000);
WebElement table = driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("WebTable-path")));
List<WebElement> rows =table.findElements(By.tagName("tr"));
for(int i=1; i<rows.size();i++)
{
	//capture supplier number column
String act_data= driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column+"]/div/span/span")).getText();
Thread.sleep(5000);
Reporter.log(act_data+"       "+exp_data,true);
Assert.assertEquals(exp_data, act_data);
break;
}
}
//method for Mouse click
	public static void stockCategories(WebDriver driver)throws Throwable
	{
		Actions ac= new Actions(driver);
	ac.moveToElement(driver.findElement(By.linkText("Stock Items"))).perform();
	Thread.sleep(5000);
	ac.moveToElement(driver.findElement(By.xpath("//body/div[2]/div[2]/div[1]/div[1]/ul[1]/li[2]/ul[1]/li[1]/a[1]"))).click().perform();
	Thread.sleep(5000);
	}
	//stock table validation
	public static void sttableValidation(WebDriver driver,String testdata)throws Throwable
	{
		//if search text is not displayed click on search panel button
		WebElement searchTextbox =driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox")));
				if(!searchTextbox.isDisplayed())
					//click on search panel 
					Thread.sleep(5000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panael"))).click();
		Thread.sleep(5000);
		searchTextbox.clear();
		Thread.sleep(5000);
		searchTextbox.sendKeys(testdata);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		Thread.sleep(5000);
	WebElement table = driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("WebTable-path")));
	List<WebElement> rows= table.findElements(By.tagName("tr"));
	for(int i=1;i<rows.size();i++)
	{
	String exp_Data= driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr["+i+"]/td[4]/div/span/span")).getText();
	Reporter.log(testdata+"    "+exp_Data,true);
	Thread.sleep(5000);
	Assert.assertEquals(testdata, exp_Data);
	break;
	}
	}
}









