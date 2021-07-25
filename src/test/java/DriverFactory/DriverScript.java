package DriverFactory;

import java.io.File;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.mongodb.MapReduceCommand.OutputType;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.FunctionLibrary;
import Utilities.ExcelFileUtil;

public class DriverScript {
static WebDriver driver;
ExtentReports report;
ExtentTest test;
String inputpath="D:\\4oclockOJT\\StockAccounting\\TestInput\\HybridTest.xlsx";
String outputpath="D:\\4oclockOJT\\StockAccounting\\TestOutPut\\HybridResults.xlsx";
public void startTest()throws Throwable
{
	//access excelutil methods
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//iterate all rows in masterttestcases sheet
	for(int i=1; i<=xl.rowCount("MasterTestCases");i++)
	{
		String ModuleStatus = "";
		if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
		{
			//define module name
			String TCModule=xl.getCellData("MasterTestCases", i, 1);
			//generate html reports
			report= new ExtentReports("./ExtentReports/"+TCModule+"  "+FunctionLibrary.generateDate()+" "+".html",true);
			//iterate TCModule sheet
			test=report.startTest(TCModule);
			for(int j=1; j<=xl.rowCount(TCModule);j++)
			{
			String Description =xl.getCellData(TCModule, j, 0);	
			String Function_Name= xl.getCellData(TCModule, j, 1);
			String Locator_Type= xl.getCellData(TCModule, j, 2);
			String Locator_Value= xl.getCellData(TCModule, j, 3);
			String Test_Data = xl.getCellData(TCModule, j, 4);
			try{
				if(Function_Name.equalsIgnoreCase("startBrowser"))
				{
					driver= FunctionLibrary.startBrowser(driver);
					test.log(LogStatus.INFO, Description);
				}
				else if(Function_Name.equalsIgnoreCase("openApplication"))
				{
					FunctionLibrary.openApplication(driver);
					test.log(LogStatus.INFO, Description);
				}
				else if(Function_Name.equalsIgnoreCase("waitForElement"))
				{
					FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
					test.log(LogStatus.INFO, Description);
				}
				else if(Function_Name.equalsIgnoreCase("typeAction"))
				{
					FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
					test.log(LogStatus.INFO, Description);
				}
				else if(Function_Name.equalsIgnoreCase("clickAction"))
				{
					FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
					test.log(LogStatus.INFO, Description);
				}
				else if(Function_Name.equalsIgnoreCase("titleValidation"))
				{
					FunctionLibrary.titleValidation(driver, Test_Data);
					test.log(LogStatus.INFO, Description);
				}
				else if(Function_Name.equalsIgnoreCase("CloseApplication"))
				{
					FunctionLibrary.CloseApplication(driver);
					test.log(LogStatus.INFO, Description);
				}
				else if(Function_Name.equalsIgnoreCase("captureData"))
				{
					FunctionLibrary.captureData(driver, Locator_Type, Locator_Value);
					test.log(LogStatus.INFO, Description);
				}
				else if(Function_Name.equalsIgnoreCase("supplierTable"))
				{
					FunctionLibrary.supplierTable(driver, Test_Data);
					test.log(LogStatus.INFO, Description);
				}
				else if(Function_Name.equalsIgnoreCase("stockCategories"))
				{
					FunctionLibrary.stockCategories(driver);
					test.log(LogStatus.INFO, Description);
				}
				else if(Function_Name.equalsIgnoreCase("sttableValidation"))
				{
					FunctionLibrary.sttableValidation(driver, Test_Data);
					test.log(LogStatus.INFO, Description);
				}
				//write as pass into TCModule
				xl.setCellData(TCModule, j, 5, "Pass", outputpath);
				test.log(LogStatus.PASS, Description);
				ModuleStatus="True";
			}catch(Exception e)
			{
				//write as fail into TCModule
				xl.setCellData(TCModule, j, 5, "Fail", outputpath);
				test.log(LogStatus.FAIL, Description);
				ModuleStatus="False";
			//	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				//FileUtils.copyFile(scrFile, new File("./Screenshots/"+Description+"_"+FunctionLibrary.generateDate()+".jpg"));
			}
			if(ModuleStatus.equalsIgnoreCase("True"))
			{
				xl.setCellData("MasterTestCases", i, 3, "Pass", outputpath);
			}
			else
			{
				if(ModuleStatus.equalsIgnoreCase("False"))
				{
					xl.setCellData("MasterTestCases", i, 3, "Fail", outputpath);
				}
			}
			report.endTest(test);	
			report.flush();
			}
		}
		else
		{
			//write as blocked into status cell
		xl.setCellData("MasterTestCases", i, 3, "Blocked", outputpath);	
		}
	
	}
}
}
