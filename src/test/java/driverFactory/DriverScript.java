package driverFactory;

import java.io.File;
import java.lang.System.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.functionLibrary;
import xlutilities.ExcelFileUtility;


public class DriverScript extends functionLibrary
{
	ExtentReports report;
	ExtentTest test;
    String inputpath = "./FileInput/DataEngine.xlsx";
    String outputpath ="./FileOutput/Hybridreults.xlsx";
    public void startTest() throws Throwable 
    {
    	String Modulestatus ="";
    	//call excelfileutils calss methods
    	ExcelFileUtility xl = new ExcelFileUtility(inputpath);
    	//iterate all rows in the master sheat
    	for(int i=1;i<=xl.rowCount("MasterTestCases");i++)
    	{
    		
    		
    		if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
    		{
    			
    		//store corresponding sheet into variable 
    		String Tc_Module = xl.getCellData("MasterTestCases", i,1);
    		report = new ExtentReports("./target/Reports/"+Tc_Module+"  "+functionLibrary.generateDate()+".html");
    		//testcase strats here
			test = report.startTest(Tc_Module);
    		//iterate all rows in tc_module
    		for(int j=1;j<=xl.rowCount(Tc_Module);j++)
    		{
    			
    			//call all cels
    			String Description = xl.getCellData(Tc_Module, j, 0);
    			String ObjectType = xl.getCellData(Tc_Module, j, 1);
    			String locatortype = xl.getCellData(Tc_Module, j, 2);
    			String locatorvalue = xl.getCellData(Tc_Module, j, 3);
    			String testdata = xl.getCellData(Tc_Module, j, 4);
    			try
    			{
    				if(ObjectType.equalsIgnoreCase("startBrowser"))
    				{
    					driver =functionLibrary.startBrowser();
    					test.log(LogStatus.INFO, Description);
    					
    				}else if(ObjectType.equalsIgnoreCase("launchUrl"))
    				{
    					functionLibrary.launchUrl(driver);
    					test.log(LogStatus.INFO, Description);
    				}else if(ObjectType.equalsIgnoreCase("waitforElement"))
    				{
    					functionLibrary.waitforElement(driver, locatortype, locatorvalue, testdata);
    					test.log(LogStatus.INFO, Description);
    				}else if(ObjectType.equalsIgnoreCase("typeAction"))
    				{
    					functionLibrary.typeAction(driver, locatortype, locatorvalue, testdata);
    					test.log(LogStatus.INFO, Description);
    				}else if(ObjectType.equalsIgnoreCase("clickAction"))
    				{
    					functionLibrary.clickAction(driver, locatortype, locatorvalue);
    					test.log(LogStatus.INFO, Description);
    				}else if(ObjectType.equalsIgnoreCase("validateTitle"))
    				{
    					functionLibrary.validateTitle(driver, testdata);
    					test.log(LogStatus.INFO, Description);
    				}else if(ObjectType.equalsIgnoreCase("closeBrowser"))
    				{
    					functionLibrary.closeBrowser(driver);
    					test.log(LogStatus.INFO, Description);
    				}
    				else if(ObjectType.equalsIgnoreCase("mouseClick"))
    				{
    					functionLibrary.mouseClick(driver);
    					test.log(LogStatus.INFO, Description);
    				}
    				else if(ObjectType.equalsIgnoreCase("categoryTable"))
    				{
    					functionLibrary.categoryTable(driver, testdata);
    					test.log(LogStatus.INFO, Description);
    				}
    				else if(ObjectType.equalsIgnoreCase("captureSnumber"))
    				{
    					functionLibrary.captureSnumber(driver, locatortype, locatorvalue);
    					test.log(LogStatus.INFO, Description);
    				}
    				else if(ObjectType.equalsIgnoreCase("supplierTable"))
    				{
    					functionLibrary.supplierTable(driver);
    					test.log(LogStatus.INFO, Description);
    				}
    				//write as pass into status cell TCModule 
    				xl.setcelldata(Tc_Module, j, 5, "pass", outputpath);
    				test.log(LogStatus.PASS, Description);
    				Modulestatus = "True";
    				
    				
    			}catch (Exception e) 
    			{
					System.out.println(e.getMessage());
					xl.setcelldata(Tc_Module, j, 5, "fail", outputpath);
					test.log(LogStatus.FAIL, Description);
					Modulestatus = "False";
					File srcfile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    				FileUtils.copyFile(srcfile, new File("./Screenshot/"+Description+functionLibrary.generateDate()+".jpg"));
    				//to get screenshot into extentsreports
    				String image = test.addScreenCapture("./Screenshot/"+Description+functionLibrary.generateDate()+".jpg");
    				test.log(LogStatus.FAIL,image);
    				
    				break;
				}
    			//to get screenshot of assertion error ,we added catch block assertion
    			catch (AssertionError a)
    			{
    				File srcfile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    				FileUtils.copyFile(srcfile, new File("./Screenshot/"+Description+functionLibrary.generateDate()+".jpg"));
    				//to get screenshot into extentsreports
    				String image = test.addScreenCapture("./Screenshot/"+Description+functionLibrary.generateDate()+".jpg");
    				test.log(LogStatus.FAIL,image);
    				
    				break;
    			}
    			if(Modulestatus.equalsIgnoreCase("True"))
    			{
    				xl.setcelldata("MasterTestCases", i, 3, "pass", outputpath);
    			}else
    			{
    				xl.setcelldata("MasterTestCases", i, 3, "fail", outputpath);
    			}
    			
    		}
    		
    		}
    		
    		else if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("N"))
    		{
    			xl.setcelldata("MasterTestCases", i, 3, "Blocked", outputpath);
    			
    		}
    		report.endTest(test);
			report.flush();
    	}
    }
	
}
