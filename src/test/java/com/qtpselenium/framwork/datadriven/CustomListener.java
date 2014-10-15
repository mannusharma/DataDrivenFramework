package com.qtpselenium.framwork.datadriven;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.internal.Utils;

import com.qtpselenium.framework.datadriven.utility.Constants;
import com.qtpselenium.framework.datadriven.utility.ErrorUtil;
import com.qtpselenium.framework.datadriven.utility.Xls_Reader;

public class CustomListener extends TestListenerAdapter implements IInvokedMethodListener,ISuiteListener {
	
	public static Hashtable<String, String> resultTable;
	public static ArrayList<String> key;
	public static String resultFoldername;
	public static String resultFilePath;
	
	public void onTestFailure(ITestResult tr)
	{
		//report(tr.getName(), tr.getThrowable().getMessage());
		List<Throwable> verificationFailures = ErrorUtil.getVerificationFailures();
		
		String errMsg="";
		for(int i=0; i<verificationFailures.size(); i++)
		{
			errMsg=errMsg+"[" +verificationFailures.get(i).getMessage()+"] -";
		}
		
		report(tr.getName(), errMsg);
		
		
	}
	
	public void onTestSkipped(ITestResult tr)
	{
		report(tr.getName(), tr.getThrowable().getMessage());
		
	}
	
	public void onTestSuccess(ITestResult tr)
	{
		report(tr.getName(), "Pass");
	}

	
	public void afterInvocation(IInvokedMethod method, ITestResult result) {
		Reporter.setCurrentTestResult(result);

		if (method.isTestMethod()) {
			List<Throwable> verificationFailures = ErrorUtil.getVerificationFailures();
			//if there are verification failures...
			if (verificationFailures.size() != 0) {
				//set the test to failed
				result.setStatus(ITestResult.FAILURE);
				
				//if there is an assertion failure add it to verificationFailures
				if (result.getThrowable() != null) {
					verificationFailures.add(result.getThrowable());
				}
 
				int size = verificationFailures.size();
				//if there's only one failure just set that
				if (size == 1) {
					result.setThrowable(verificationFailures.get(0));
				} else {
					//create a failure message with all failures and stack traces (except last failure)
					StringBuffer failureMessage = new StringBuffer("Multiple failures (").append(size).append("):nn");
					for (int i = 0; i < size-1; i++) {
						failureMessage.append("Failure ").append(i+1).append(" of ").append(size).append(":n");
						Throwable t = verificationFailures.get(i);
						String fullStackTrace = Utils.stackTrace(t, false)[1];
						failureMessage.append(fullStackTrace).append("nn");
					}
 
					//final failure
					Throwable last = verificationFailures.get(size-1);
					failureMessage.append("Failure ").append(size).append(" of ").append(size).append(":n");
					failureMessage.append(last.toString());
 
					//set merged throwable
					Throwable merged = new Throwable(failureMessage.toString());
					merged.setStackTrace(last.getStackTrace());
 
					result.setThrowable(merged);
					
				}
			}
		
		}
		
		
	}

	
	public void beforeInvocation(IInvokedMethod method, ITestResult result) {
		
	}

	@Override
	public void onStart(ISuite suite) {
		
		if(resultTable==null)
		{
			key = new ArrayList<String>();
			resultTable= new Hashtable<String, String>();
		}
		
		//System.out.println("Start suite " +suite.getName());
		
		if(resultFoldername==null)
		{
		Date d = new Date();
		resultFoldername = d.toString().replace(":", "_");
		File f = new File(System.getProperty("user.dir")+"//target//reports//" + resultFoldername);
		f.mkdir();
		
		resultFilePath = System.getProperty("user.dir")+"//target//reports//" + resultFoldername+ "//Report.xlsx";
		File src = new File(System.getProperty("user.dir")+"//target//reports//ReportTemplate.xlsx");
		File dest = new File(resultFilePath);
		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		}
		
	}

	@Override
	public void onFinish(ISuite suite) {
		
       if(resultTable!=null)
       {
    	   System.out.println(resultTable);
    	   System.out.println(key);
    	   
    	   if(!suite.getName().equals(Constants.Root_Suite))
    	   {
    	   Xls_Reader xls = new Xls_Reader(resultFilePath);
    	   xls.addSheet(suite.getName());
    	   
    	   xls.setCellData(suite.getName(), 0, 1, "Test Case");
    	   xls.setCellData(suite.getName(), 1, 1, "Result");
    	   
    	   for(int i=0; i<key.size();i++)
    	   {
    		   String allkey = key.get(i);
    		   String result = resultTable.get(allkey);
    		   
    		   xls.setCellData(suite.getName(), 0, i+2, allkey);
        	   xls.setCellData(suite.getName(), 1, i+2, result);
    		   
    	   }
    	   }
    	   
    	   resultTable=null;
    	   key = null;
       }
       
       //System.out.println("End suite " +suite.getName());
		
	}
	
	public void report(String name, String result)
	{
		int iterator_number=1;
		while(resultTable.containsKey(name+ " Iterator " +iterator_number))
		{
			iterator_number++;
		}
			
		key.add(name + " Iterator " +iterator_number);
		resultTable.put(name + " Iterator " +iterator_number, result);
		
		
	}
	
	
	
	

}
