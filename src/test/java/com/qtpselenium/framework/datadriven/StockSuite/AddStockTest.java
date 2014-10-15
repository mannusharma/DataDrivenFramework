package com.qtpselenium.framework.datadriven.StockSuite;

import java.net.MalformedURLException;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.framework.datadriven.utility.Constants;
import com.qtpselenium.framework.datadriven.utility.ErrorUtil;
import com.qtpselenium.framework.datadriven.utility.TestDataProvider;
import com.qtpselenium.framework.datadriven.utility.Utility;
import com.qtpselenium.framework.datadriven.utility.Xls_Reader;
import com.qtpselenium.framwork.datadriven.TestBase;

public class AddStockTest extends TestBase {
	
	@BeforeTest
	public void before()
	{
		initLogs(this.getClass());
	}
	
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="StockSuiteDataProvider")
	public void AddStockTest(Hashtable<String, String> table) throws MalformedURLException
	{
		ValidateRunmodes("AddStockTest", Constants.StockSuite_SheetName, table.get(Constants.Runmode_Col));
		defaultLogin(table.get(Constants.Browser_Name));
		
	    try{
		Assert.assertEquals("title1", "title2");
	    }catch(Throwable t)
	    {
	    	ErrorUtil.addVerificationFailure(t);
	    }
		
		//doLogin(table.get("Browser"), table.get(Constants.Username_Col), table.get(Constants.Password_Col));
		
		closebrowser();
	}
	

}
