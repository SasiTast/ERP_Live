package commonFunctions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FuntionLibrary 
{

	public static WebDriver driver;
	public static Properties conpro;
	public static String expected="";
	public static String actual="";
	
	public static WebDriver startBrowser() throws Throwable
	{
	//conpro.load("C:\\Liveproject\\ERP_hybrid_StockAccounting\\");	
		conpro.load(new FileInputStream("./Liveproject/ERP_hybrid_StockAccounting"));
		if(conpro.getProperty("browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
			//driver.manage().window().maximize(); it automatically opens max window
			driver.manage().deleteAllCookies();
		}
		else if(conpro.getProperty("browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
		}else 
		{
			System.out.println("browser value is not matching");
		}
		return driver;
	}
	//method for launchUrl
	public static void launchUrl(WebDriver driver)
	{
		driver.get(conpro.getProperty("Url"));
	}
	//method for waitforElement

	public static void waitforElement(WebDriver driver,String Locator_Type,String Locator_value,String waitTime)
	{
		WebDriverWait mywait = new WebDriverWait(driver,Integer.parseInt(waitTime));
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_value)));
		}else if(Locator_Type.equalsIgnoreCase("xapth"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_value)));
		}else if(Locator_Type.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_value)));
		}
	}
	//method for textboxes

	public static void typeAction(WebDriver driver,String Locator_Type,String Locator_value,String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_value)).clear();
			driver.findElement(By.id(Locator_value)).sendKeys(Test_Data);
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_value)).clear();
			driver.findElement(By.name(Locator_value)).sendKeys(Test_Data);
		}else if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_value)).clear();
			driver.findElement(By.xpath(Locator_value)).sendKeys(Test_Data);
		}
	}
	//method for buttons,images,links,radio buttons,checkboxes
	public static void clickAction(WebDriver driver,String Locator_Type,String Locator_value,String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_value)).sendKeys(Keys.ENTER);;
		}else if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_value)).click();
		}else if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_value)).click();
		}
	}
	//method for validateTitle
    public static void validateTitle(WebDriver driver,String Expected_Title)
    {
	String Actual_title = driver.getTitle();
	try
	{
	Assert.assertEquals(Expected_Title, Actual_title,"Title is not matching");
	}catch(Throwable t)
	{
		System.out.println(t.getMessage());
	}
     }

    //method for closeBrowser

    public static void closeBrowser(WebDriver driver) 
    {
    	driver.close();
    }
}
