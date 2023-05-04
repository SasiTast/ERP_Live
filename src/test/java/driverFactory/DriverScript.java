package driverFactory;

import commonFunctions.functionLibrary;
import xlutilities.ExcelFileUtility;


public class DriverScript extends functionLibrary
{
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
    					
    				}else if(ObjectType.equalsIgnoreCase("launchUrl"))
    				{
    					functionLibrary.launchUrl(driver);
    				}else if(ObjectType.equalsIgnoreCase("waitforElement"))
    				{
    					functionLibrary.waitforElement(driver, locatortype, locatorvalue, testdata);
    				}else if(ObjectType.equalsIgnoreCase("typeAction"))
    				{
    					functionLibrary.typeAction(driver, locatortype, locatorvalue, testdata);
    				}else if(ObjectType.equalsIgnoreCase("clickAction"))
    				{
    					functionLibrary.clickAction(driver, locatortype, locatorvalue);
    				}else if(ObjectType.equalsIgnoreCase("validateTitle"))
    				{
    					functionLibrary.validateTitle(driver, testdata);
    				}else if(ObjectType.equalsIgnoreCase("closeBrowser"))
    				{
    					functionLibrary.closeBrowser(driver);
    				}
    				else if(ObjectType.equalsIgnoreCase("mouseClick"))
    				{
    					functionLibrary.mouseClick(driver);
    				}
    				else if(ObjectType.equalsIgnoreCase("categoryTable"))
    				{
    					functionLibrary.categoryTable(driver, testdata);
    				}
    				//write as pass into status cell TCModule 
    				xl.setcelldata(Tc_Module, j, 5, "pass", outputpath);
    				Modulestatus = "True";
    				
    				
    			}catch (Exception e) 
    			{
					System.out.println(e.getMessage());
					xl.setcelldata(Tc_Module, j, 5, "fail", outputpath);
					Modulestatus = "False";
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
    		
    	}
    }
	
}
