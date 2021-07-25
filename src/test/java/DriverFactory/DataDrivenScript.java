package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.LoginPage;
import CommonFunLibrary.SupplierPage;
import Utilities.ExcelFileUtil;

public class DataDrivenScript {
WebDriver driver;
String inputpath ="D:\\4oclockOJT\\StockAccounting\\TestInput\\SupplierData.xlsx";
String outputpath ="D:\\4oclockOJT\\StockAccounting\\TestOutPut\\DataDrivenResults.xlsx";
boolean str=false;
ExtentReports report;
ExtentTest test;
@BeforeTest
public void setUp()throws Throwable
{
	//generate html Report
	report= new ExtentReports("./ExtenReport/Reports/Supplier.html");
	System.setProperty("webdriver.chrome.driver", "D:\\4oclockOJT\\StockAccounting\\CommonDrivers\\chromedriver.exe");
	driver =new ChromeDriver();
	driver.manage().window().maximize();
	driver.get("http://projects.qedgetech.com/webapp/login.php");
	Thread.sleep(5000);
	LoginPage login =PageFactory.initElements(driver, LoginPage.class);
	login.verifyLogin("admin", "master");
}
@Test
public void addSupplier()throws Throwable
{
	//call supplierpage
	SupplierPage addsup= PageFactory.initElements(driver, SupplierPage.class);
	//create object for accessing xl methods
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//count no of cells in first row
	int cc=xl.cellCount("Supplier");
	//count no of rows in a sheet
	int rc =xl.rowCount("Supplier");
	Reporter.log("No of rows are::"+rc+"  "+"No ofcells in firtsrow::"+cc,true);
	for(int i=1;i<=rc;i++)
	{
		test= report.startTest("Supplier Creation");
		//read all cells data
		String supplierName=xl.getCellData("Supplier", i, 0);
		String Address=xl.getCellData("Supplier", i, 1);
		String city=xl.getCellData("Supplier", i, 2);
		String Country =xl.getCellData("Supplier", i, 3);
		String cperson=xl.getCellData("Supplier", i, 4);
		String phoneNumber=xl.getCellData("Supplier", i, 5);
		String Email=xl.getCellData("Supplier", i, 6);
		String mobileNumber =xl.getCellData("Supplier", i, 7);
		String Notes=xl.getCellData("Supplier", i, 8);
		test.log(LogStatus.INFO, supplierName+" "+Address+" "+city+" "+Country+" "+cperson+" "+phoneNumber+"  "+Email+"  "+mobileNumber+"  "+Notes);
		String status =addsup.verifySupplier(supplierName, Address, city, Country, cperson, phoneNumber, Email, mobileNumber, Notes);
		if(str)
		{
//write status into result cell
		xl.setCellData("Supplier", i, 9, status, outputpath);
		test.log(LogStatus.PASS, "Suppler Created");
		}
		else
		{
		xl.setCellData("Supplier", i, 9, status, outputpath);	
		test.log(LogStatus.FAIL, "Fail To Create");
		}
		report.endTest(test);
		report.flush();
	}
}
@AfterTest
public void tearDown()
{
	driver.close();
}
}











