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

public class LeastPerAssetsTest extends TestBase {
	
	@BeforeTest
	public void before()
	{
		initLogs(this.getClass());
	}
	
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="PortfolioDataProvider")
	public void LeastPerAssetsTest(Hashtable<String, String> table) throws MalformedURLException {
		
		Application_Log.debug("second test");
		
		ValidateRunmodes("LeastPerAssetsTest", Constants.PortFolioSuite_SheetName, table.get(Constants.Runmode_Col));
		
		defaultLogin(table.get(Constants.Browser_Name));
		
		//doLogin(table.get("Browser"), table.get(Constants.Username_Col), table.get(Constants.Password_Col));
		
		//Assert.assertTrue(IsElementPresent("MyPortfolioLeastPerfAsset_xpath"),"Element not present - MyPortfolioLeastPerfAsset_xpath");
		String Header_Text = getText("MyPortfolioLeastPerfAsset_xpath");
		String[] txt = Header_Text.split(" ");
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  " +txt[0]);
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  " +txt[2]);
		
		String per = txt[2];
		
		String PercentData = per.split("\\(")[1].split("\\%")[0];
		
		//table[@id='stock']/tr[1]/td[2]
		//table[@id='stock']/tr[2]/td[2]
		
		int i=1;
		int j=2;
		while(IsElementPresent(prop.getProperty("Stock1")+i+prop.getProperty("Stock2")+j+prop.getProperty("Stock3")))
		{
			String data= getText(prop.getProperty("Stock1")+i+prop.getProperty("Stock2")+j+prop.getProperty("Stock3"));
			
			if(data.equals(txt[0]))
			{
				
				//System.out.println("#######################  ->" +data);
				int k=2;
			    while(IsElementPresent(prop.getProperty("Stock1")+i+prop.getProperty("Stock2")+k+prop.getProperty("Stock3")))
			    {
			    	String percent = getText(prop.getProperty("Stock1")+i+prop.getProperty("Stock2")+k+prop.getProperty("Stock3"));
			    	if(percent.equals(PercentData))
			    	{
			    		Assert.assertEquals(percent, PercentData);
			    		//System.out.println("&&&&&&&&&&&&&&&&&&&&  " +percent);
			    		break;
			    	}
			      k++;
			    }
				break;
			}
			
			i++;
			
		}
		
		
		
		closebrowser();
		
	   
	}
	

}
