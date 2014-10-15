package com.qtpselenium.framwork.datadriven;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.SkipException;

import com.qtpselenium.framework.datadriven.utility.Constants;
import com.qtpselenium.framework.datadriven.utility.Utility;
import com.qtpselenium.framework.datadriven.utility.Xls_Reader;

public class TestBase {
	
	public static Properties prop;
	public Logger Application_Log = null; //Logger.getLogger("devpinoyLogger");
	public static WebDriver driver;
	//public static DesiredCapabilities capability;
	
	public static void init()
	{
		if(prop == null)
		{
		prop = new Properties();
		
		FileInputStream fl;
		try {
			fl = new FileInputStream(System.getProperty("user.dir")+ "\\src\\test\\resources\\Project.properties");
			prop.load(fl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		
		
	}
	
	public void initLogs(Class<?> class1)
	{
		FileAppender appender = new FileAppender();
		appender.setFile(System.getProperty("user.dir")+"//target//reports//" + CustomListener.resultFoldername+ "//" +class1.getName()+".log");
		appender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m %n"));
		appender.setAppend(false);
		appender.activateOptions();
		
		Application_Log = Logger.getLogger(class1);
		Application_Log.setLevel(Level.DEBUG);
		Application_Log.addAppender(appender);
		
	}
	
	public void ValidateRunmodes(String testName, String suiteName, String dataRunmode)
	{
		
		//Application_Log.debug("Validating the testcases " +testName + " inside the suite " +suiteName);
		System.out.println("****************************" +dataRunmode);
		boolean testCaseRunmode = Utility.IsTestCaseRunnable(new Xls_Reader(prop.getProperty("fileLocation") +suiteName+ ".xlsx"), testName);
		boolean testSuiteRunmode = Utility.IsTestSuiteRunnable(new Xls_Reader(prop.getProperty("fileLocation")+Constants.Suite_SheetName +".xlsx"), suiteName);
		
		boolean datasetRunmode=false;
		if(dataRunmode.equalsIgnoreCase(Constants.Runmode_Yes))
			datasetRunmode=true;
		
		if(!(testCaseRunmode && testSuiteRunmode && datasetRunmode))
		{
			
			throw new SkipException("Skipping the test " + testName+ " inside the test suite " +suiteName);
		}
			
	}
	
	//****************************General Methods*****************************8
	
	//code to open the browser
	public void openBrowser(String BrowserName) throws MalformedURLException
	{
		if(BrowserName.equalsIgnoreCase("Mozilla"))
		{
			driver = new FirefoxDriver();
			//capability = DesiredCapabilities.firefox();
			//driver= new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capability);
		}
		else if(BrowserName.equalsIgnoreCase("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver", prop.getProperty("Chromedriverexe"));
			driver= new ChromeDriver();
			//capability = DesiredCapabilities.chrome();
			//driver= new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capability);
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	//navigate to URL
	public void navigate(String URLKey)
	{
		driver.get(prop.getProperty(URLKey));
	}
	//code to click Webelements
	public void click(String identifier)
	{
		if(identifier.endsWith("_xpath"))
		{
			driver.findElement(By.xpath(prop.getProperty(identifier))).click();
		}
		else if(identifier.endsWith("_id"))
		{
			driver.findElement(By.xpath(prop.getProperty(identifier))).click();
		}
		else if(identifier.endsWith("_name"))
		{
			
		}
	}
	 
	//to enter value in Input field
	public void input(String identifier, String value)
	{
		if(identifier.endsWith("_xpath"))
		{
			driver.findElement(By.xpath(prop.getProperty(identifier))).sendKeys(value);
		}
		else if(identifier.endsWith("_id"))
		{
			driver.findElement(By.xpath(prop.getProperty(identifier))).sendKeys(value);
		}
		else if(identifier.endsWith("_name"))
		{
			
		}
	}
	
	//to get text from elements present
	String text ="";
	public String getText(String identifier)
	{
		if(identifier.endsWith("_xpath"))
		{
			text = driver.findElement(By.xpath(prop.getProperty(identifier))).getText();
		}
		else if(identifier.endsWith("_id"))
		{
			text = driver.findElement(By.xpath(prop.getProperty(identifier))).getText();
		}
		else if(identifier.endsWith("_name"))
		{
			
		}
		else
			text = driver.findElement(By.xpath(identifier)).getText();
		
		return text;
	}
	
	//to test titles
	public boolean title(String Expected)
	{
		
		if(prop.getProperty(Expected).equals(driver.getTitle()))
			return true;
		
		else
			return false;
		
	}
	
	int size=0;
	public boolean IsElementPresent(String identifier)
	{
		
		if(identifier.endsWith("_xpath"))
		    size = driver.findElements(By.xpath(prop.getProperty(identifier))).size();
		else if(identifier.endsWith("_id"))
		    size = driver.findElements(By.xpath(prop.getProperty(identifier))).size();
		else if(identifier.endsWith("_name"))
			size = driver.findElements(By.xpath(prop.getProperty(identifier))).size();
		else
			size=driver.findElements(By.xpath(identifier)).size();
		
		if(size>0)
			return true;
		else 
			return false;
			
		
	}

	//************************Application Specific methods*******************************
	public void doLogin(String Browser, String Username, String Password) throws MalformedURLException
	{
		openBrowser(Browser);
	    navigate("URLKey");
	    Assert.assertTrue(IsElementPresent("MoneyLink_xpath"),"Element not present - MoneyLink_xpath");
	    click("MoneyLink_xpath");
	   // Assert.assertTrue(title("PortfolioPage_title"), "portfolio title does not match with" +driver.getTitle());
	    click("MyPortfolio_xpath");
	    input("LoginUsername_id", Username);
	    click("LoginContinueButton_xpath");
	    input("LoginPassword_id", Password);
	    click("loginSubitbutton_id");
		
	}
	
	public void defaultLogin(String Browser) throws MalformedURLException
	{
		doLogin(Browser, prop.getProperty("defaultUserName"), prop.getProperty("defaultPassword"));
		
	}
	
	//close browser
	public void closebrowser()
	{
		
		if(driver!=null)
		{
			driver.quit();
			driver=null;
		}
	}

}
