package com.rex.autotest.testCases.Regressions;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.rex.autotest.businessComponent.BusinessComponent;
import com.rex.autotest.pageObjects.DashboardPageObject;
import com.rex.autotest.pageObjects.HeaderPageObject;
import com.rex.autotest.pageObjects.HomePageObject;
import com.rex.autotest.pageObjects.SignUpPageObject;
import com.rex.autotest.pageObjects.SigninPageObject;
import com.rex.autotest.utilities.AppiumHelper;
import com.rex.autotest.utilities.ExcelUtils;
import com.rex.autotest.utilities.PropUtils;
import com.rex.autotest.utilities.Utils;


public class DP_TalentAcceptOffer extends BaseTestCase {
	
	Map<String, Map<String, String>> dataMap = ExcelUtils.readExcel("ID-17667");
	Map<String, String> resultMap = new HashMap<String, String>();
   	Properties eleMap = PropUtils.getProperties(MSGFILE); 
   	final String account = "TC-01";
   	final String subpath = "/Talent_ViewOfferAndAccept_17667";
   	 	
	 @Test
	  public void Talent_ViewOfferAndAccept(){	  
		  logger.info("***** start Talent_ViewOfferAndAccept_17667 page ******");
		  
		  //Talent sign in
		  BusinessComponent.Signin(driver, dataMap.get(account).get("username"), dataMap.get(account).get("passwd"));
		  Utils.sleep(3000);
		  
		  //verify jumping to dashboard
		  DashboardPageObject dash = new DashboardPageObject(driver);
		  Utils.sleep(2000);

		  AppiumHelper.screenShot(driver, "dashboard", screenshotPath + subpath);
		  AppiumHelper.verificaion_true(dash.verify_msg_dashboard(), "dashboard displays");
		  AppiumHelper.verificaion_true(dash.verify_view_offerbtn(), "View Offer Details button displays");
		  
		  dash.click_view_offer_btn();
		  Utils.sleep(2000);
		  AppiumHelper.screenShot(driver, "offer details", screenshotPath + subpath);
		  AppiumHelper.verificaion_true(dash.verify_view_details_page(), "dashboard displays");
		  AppiumHelper.verificaion_true(dash.verify_accept_btn(), "Accept button displays");
		  AppiumHelper.verificaion_true(dash.verify_decline_btn(), "Decline button displays");
		  
		  dash.clickElementByxPath("accept_btn");
		  
		  Utils.sleep(2000);
		  
		  resultMap.put(account, "pass");
		  logger.info("***** end Talent_ViewOfferAndAccept_17667 page ******");
	 }
	 
	 
	 @AfterClass(alwaysRun=true)
	  public void updateResult(){
		  logger.info("***** end update result to excel ******");
		  dataMap.keySet().removeAll(resultMap.keySet());
		  for(String tcID : dataMap.keySet()){
			  if(tcID.length()!=0){
				  resultMap.put(tcID, "fail");
			  }   
		  }
		  ExcelUtils.updateTestResults("ID-17667", resultMap);
	  } 
	 
}
