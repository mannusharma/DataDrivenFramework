package com.qtpselenium.framework.datadriven.utility;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.qtpselenium.framwork.datadriven.TestBase;

public class TestDataProvider {

	@DataProvider(name="PortfolioDataProvider")
	public static Object[][] getDataSuiteA(Method M)
	{
		TestBase.init();
		//System.out.println("file location is ------------ " +TestBase.prop.getProperty("fileLocation"));
		
		Xls_Reader xls = new Xls_Reader(TestBase.prop.getProperty("fileLocation") +Constants.PortFolioSuite_SheetName +".xlsx");
		return Utility.getData(xls, M.getName());
	}
	
	@DataProvider(name="StockSuiteDataProvider")
	public static Object[][] getDataStockSuite(Method M)
	{
		TestBase.init();
		//System.out.println("file location is ------------ " +TestBase.prop.getProperty("fileLocation"));
		
		Xls_Reader xls = new Xls_Reader(TestBase.prop.getProperty("fileLocation") +Constants.StockSuite_SheetName +".xlsx");
		return Utility.getData(xls, M.getName());
	}
	
}
