package com.rex.autotest.testCases.Regressions;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.rex.autotest.businessComponent.DataBaseConnection;
import com.rex.autotest.pageObjects.DashboardPageObject;
import com.rex.autotest.pageObjects.HomePageObject;
import com.rex.autotest.pageObjects.SetPrefsPageObject;
import com.rex.autotest.pageObjects.SignUpPageObject;
import com.rex.autotest.pageObjects.SigninPageObject;
import com.rex.autotest.utilities.AppiumHelper;
import com.rex.autotest.utilities.ExcelUtils;
import com.rex.autotest.utilities.PropUtils;
import com.rex.autotest.utilities.Utils;


public class DP_TalentRegister extends BaseTestCase {
	
	Map<String, Map<String, String>> dataMap = ExcelUtils.readExcel("ID-784");
	Map<String, String> resultMap = new HashMap<String, String>();
   	Properties eleMap = PropUtils.getProperties(MSGFILE); 
   	final String account = "TC-01";
   	final String subpath = "/Talent_SignupWithMail_784";
   	 	
	 @Test
	  public void Talent_SignupWithMail(){	  
		  logger.info("***** start Talent_SignupWithMail_784 page ******");
		  
		  //click sign up button
		  HomePageObject home  = new HomePageObject(driver);
		  home.click_signup_rex_front();
		  Utils.sleep(5000);
		  
		  //verify sign up page
		  SignUpPageObject signup = new SignUpPageObject(driver);
		  AppiumHelper.screenShot(driver, "signup", screenshotPath + subpath);
		  AppiumHelper.verificaion_true(signup.verify_msg_create_account(), "sign up page displays");
		  int x = (int)(Math.random()*1000);
		  String email = "rexauto_"+ x + "@mailinator.com";
		  //input data
		  signup.input_first_name(dataMap.get(account).get("FirstN"));
		  Utils.sleep(500);
		  signup.input_last_name(dataMap.get(account).get("LastN"));
		  Utils.sleep(500);
		  signup.input_email(email);		
		  Utils.sleep(500);
		  signup.input_pwd(dataMap.get(account).get("Pwd"));
		  Utils.sleep(500);
		  signup.input_confirm_pwd(dataMap.get(account).get("ConfirmP"));
		  Utils.sleep(500);
		  signup.clickElementByxPath("live_and_authorized_us");
		  Utils.sleep(500);
		  signup.click_former_no();
		  Utils.sleep(500);
		  signup.click_checkbox_policy();
		  Utils.sleep(500);
		  signup.click_sign_up();
		  Utils.sleep(3000);
		  
		  AppiumHelper.screenShot(driver, "dashboard", screenshotPath + subpath);
		  AppiumHelper.verificaion_true(signup.verifyElementValue("msg_validate_email", "Validate Your Email Address"), "Email validation message displays");
		  
		  resultMap.put(account, "pass");
		  logger.info("***** end Talent_SignupWithMail_784 page ******");
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
		  ExcelUtils.updateTestResults("ID-784", resultMap);
	  } 
}
