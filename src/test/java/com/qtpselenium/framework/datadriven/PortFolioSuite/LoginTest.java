package com.qtpselenium.framework.datadriven.PortFolioSuite;

import java.net.MalformedURLException;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.framework.datadriven.utility.Constants;
import com.qtpselenium.framework.datadriven.utility.TestDataProvider;
import com.qtpselenium.framework.datadriven.utility.Utility;
import com.qtpselenium.framework.datadriven.utility.Xls_Reader;
import com.qtpselenium.framwork.datadriven.TestBase;

public class LoginTest extends TestBase {
	
	@BeforeTest
	public void before()
	{
		initLogs(this.getClass());
	}
	
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="PortfolioDataProvider")
	public void LoginTest(Hashtable<String, String> table) throws MalformedURLException
	{
		Application_Log.debug("Hi");
		Application_Log.debug("executing the Test1 of " + Constants.PortFolioSuite_SheetName);
		ValidateRunmodes("LoginTest", Constants.PortFolioSuite_SheetName, table.get(Constants.Runmode_Col));
	    
		doLogin(table.get("Browser"), table.get(Constants.Username_Col), table.get(Constants.Password_Col));
		
		/*boolean SignOutPresent = IsElementPresent(prop.getProperty("SignOut_xpath"));
		if(!(((table.get(Constants.ExpectedResult_Col).equalsIgnoreCase("SUCCESS")) && (SignOutPresent))))
		       Assert.fail("Not able to login with correct credentials");
		else if(table.get(Constants.ExpectedResult_Col).equalsIgnoreCase("FAILURE"))
		{
			if(SignOutPresent)
			{
				Assert.fail("Logged in with wrong credentials");
			}
		}*/
		
		closebrowser();
	}
	
	

}
