package com.qtpselenium.framework.datadriven.utility;

import java.util.Hashtable;

public class Utility {
	
	//checking if Suite is runnable
	public static boolean IsTestSuiteRunnable(Xls_Reader xls, String suiteName)
	{
		
		int rows = xls.getRowCount(Constants.Suite_SheetName);
		for(int rowNum=2; rowNum<=rows; rowNum++)
		{
			
			String Suite = xls.getCellData(Constants.Suite_SheetName, Constants.SuiteName_Col, rowNum);
			if(Suite.equalsIgnoreCase(suiteName))
			{
				
				String runMode = xls.getCellData(Constants.Suite_SheetName, Constants.Runmode_Col, rowNum);
				if(runMode.equalsIgnoreCase(Constants.Runmode_Yes))
				{
					return true;
					
				}
				else
				{
					
					return false;
				}
			}
		}
		
		return false;
		
	}
	
	//Checking if TestCase is runnable
	public static boolean IsTestCaseRunnable(Xls_Reader xls, String testcaseName)
	{
		
		int rows = xls.getRowCount(Constants.TestCases_SheetName);
		for(int rowNum=2; rowNum<=rows; rowNum++)
		{
			
			String TestCase = xls.getCellData(Constants.TestCases_SheetName, Constants.TestCases_Col, rowNum);
			if(TestCase.equalsIgnoreCase(testcaseName))
			{
				
				String runMode = xls.getCellData(Constants.TestCases_SheetName, Constants.Runmode_Col, rowNum);
				if(runMode.equalsIgnoreCase(Constants.Runmode_Yes))
				{
					return true;
					
				}
				else
				{
					
					return false;
				}
			}
		}
		
		return false;
		
	}
	
	public static Object[][] getData(Xls_Reader xls,String testName)
	{
		
		//Xls_Reader xls = new Xls_Reader("C:\\Users\\deepak sharma\\Workspace\\DataDrivenFramework_TestNG\\SuiteA.xlsx");
		//String testName="test2";
		int rows = xls.getRowCount(Constants.Data_Sheet);
		System.out.println("Total rows - "+ rows);
		
		// row number for testCase
		int testCaseRowNum=1;
		for(testCaseRowNum=1;testCaseRowNum<=rows;testCaseRowNum++){
			String testNameXls = xls.getCellData(Constants.Data_Sheet, 0, testCaseRowNum);
			if(testNameXls.equalsIgnoreCase(testName))
				break;
		}
		System.out.println("Test Starts from row Number - "+ testCaseRowNum);
		int dataStartRowNum=testCaseRowNum+2;
		int colStartRowNum=testCaseRowNum+1;
		
		// rows of data
		int testRows=1;
		while(!xls.getCellData(Constants.Data_Sheet, 0, dataStartRowNum+testRows).equals("")){
			testRows++;
		}
		System.out.println("Total rows of data are - "+testRows);
		
		//cols of data
		int testCols=0;
		while(!xls.getCellData(Constants.Data_Sheet, testCols, colStartRowNum).equals(""))
		{
			testCols++;
		}
		
		System.out.println("totla colms of data are - " +testCols);
		
		Object[][] data = new Object[testRows][1];
		
		//extract data
		int r=0;
		for(int rNum=dataStartRowNum; rNum<(dataStartRowNum+testRows); rNum++)
		{
			Hashtable<String, String> table = new Hashtable<String, String>();
			for(int cNum=0;cNum<testCols;cNum++)
			{
				
				//data[r][cNum] = xls.getCellData(Constants.Data_Sheet, cNum, rNum);
				table.put(xls.getCellData(Constants.Data_Sheet, cNum, colStartRowNum), xls.getCellData(Constants.Data_Sheet, cNum, rNum));
			}
			data[r][0]=table;
			r++;
		}
		
		return data;
		
	}

}
