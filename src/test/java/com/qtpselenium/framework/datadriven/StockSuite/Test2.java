package com.qtpselenium.framework.datadriven.StockSuite;

import java.util.Hashtable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.framework.datadriven.utility.Constants;
import com.qtpselenium.framework.datadriven.utility.TestDataProvider;
import com.qtpselenium.framework.datadriven.utility.Utility;
import com.qtpselenium.framework.datadriven.utility.Xls_Reader;
import com.qtpselenium.framwork.datadriven.TestBase;

public class Test2 extends TestBase {
	
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="SuiteBDataProvider")
	public void test2(Hashtable<String, String> table)
	{
		ValidateRunmodes("Test2", Constants.StockSuite_SheetName, table.get(Constants.Runmode_Col));
	}

}
