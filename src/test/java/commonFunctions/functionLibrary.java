package commonFunctions;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class functionLibrary 
{

	public static WebDriver driver;
	public static Properties conpro;
	public static String expected="";
	public static String actual="";
	
	public static WebDriver startBrowser() throws Throwable
	{
	//conpro.load("C:\\Liveproject\\ERP_hybrid_StockAccounting\\");	
		conpro = new Properties();
		conpro.load(new FileInputStream("./PropertyFile/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
			//driver.manage().window().maximize(); it automatically opens max window
			driver.manage().deleteAllCookies();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
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
		}else if(Locator_Type.equalsIgnoreCase("xpath"))
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
	public static void clickAction(WebDriver driver,String Locator_Type,String Locator_value)
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
	//System.out.println(Actual_title);
	try
	{
		assertEquals(Expected_Title, Actual_title,"Title is not matching");
	//Assert.assertEquals(Expected_Title,Actual_title,"Title is not matching");
	}catch(Throwable t)
	{
		System.out.println(t.getMessage());
	}
     }

    //method for closeBrowser
    public static void closeBrowser(WebDriver driver) 
    {
    	driver.quit();
    }
    //method for mouseClick
    public static void mouseClick(WebDriver driver) throws Throwable
    {
    	Actions ac = new Actions(driver);
        ac.moveToElement(driver.findElement(By.xpath("//a[starts-with(text(),'Stock Items ')]"))).perform();
        Thread.sleep(3000);
    	ac.moveToElement(driver.findElement(By.xpath("(//a[.='Stock Categories'])[2]"))).click().perform();
    }
    //method for category table
    public static void categoryTable(WebDriver driver,String ExpectedData) throws Throwable
    {
    if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed())
    {
    	driver.findElement(By.xpath(conpro.getProperty("Search-panel"))).click();
    }
    driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys(ExpectedData);
    Thread.sleep(3000);
    driver.findElement(By.xpath(conpro.getProperty("Search-button"))).click();
    Thread.sleep(3000);
    String Actual_Data = driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr/td[4]/div/span/span")).getText();
    System.out.println(Actual_Data+"      "+ExpectedData);
    try
    {
    Assert.assertEquals(Actual_Data, ExpectedData, "Category name not matching");
    }catch (Exception e) 
    {
		System.out.println(e.getMessage());
	}
    }
    //method for capturesnumber
    public static void captureSnumber(WebDriver driver,String Locator_Type,String Locator_value)
    {
    	expected = driver.findElement(By.xpath(Locator_value)).getAttribute("value");
    	
    }
    //method for supplierTable
    public static void supplierTable(WebDriver driver) throws Throwable
    {
    	if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed());
    	{
    		driver.findElement(By.xpath(conpro.getProperty("Search-panel"))).click();
    	}
    	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys(expected);
        Thread.sleep(3000);
        driver.findElement(By.xpath(conpro.getProperty("Search-button"))).click();
        Thread.sleep(3000);
        String Actual_Data = driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr/td[6]/div/span/span")).getText();
        System.out.println(Actual_Data+"      "+expected);
        try
        {
        Assert.assertEquals(Actual_Data, expected, "supplier number not matching");
        }catch (Exception e) 
        {
    		System.out.println(e.getMessage());
    	}
    	
    }
    //method for generating date
    public static String generateDate()
    {
    Date dt = new Date();
    DateFormat df = new SimpleDateFormat("YYYY_MM_DD hh_mm");
    return df.format(dt);
    }

}
