package com.rex.autotest.testCases.Regressions;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.rex.autotest.businessComponent.BusinessComponent;
import com.rex.autotest.pageObjects.EMDashboardPageObject;
import com.rex.autotest.pageObjects.EM_SearchForRolesPageObject;
import com.rex.autotest.pageObjects.ManageRolesPageObject;
import com.rex.autotest.utilities.AppiumHelper;
import com.rex.autotest.utilities.ExcelUtils;
import com.rex.autotest.utilities.PropUtils;
import com.rex.autotest.utilities.Utils;


public class DP_EMSetUpPricingEvent extends BaseTestCase {
	
	Map<String, Map<String, String>> dataMap = ExcelUtils.readExcel("ID-7195");
	Map<String, String> resultMap = new HashMap<String, String>();
   	Properties eleMap = PropUtils.getProperties(MSGFILE); 
   	final String account = "TC-01";
   	final String subpath = "/EM_SetupPricingEvent_7195";
   	
	 @Test
	  public void EM_SetupPricingEvent(){	  
		  logger.info("***** start EM_SetupPricingEvent_7195 ******");
		  
		  //valid freelancer/contractor signin
		  BusinessComponent.Signin(driver,dataMap.get(account).get("username"),dataMap.get(account).get("passwd"));
		  Utils.sleep(2000);
  
		  EMDashboardPageObject dashObj = new EMDashboardPageObject(driver);
		  Utils.sleep(2000);
		  dashObj.click_roles_btn();
		  Utils.sleep(5000);
		  
		  EM_SearchForRolesPageObject EMsfrpo = new EM_SearchForRolesPageObject(driver);
		  Utils.sleep(1000);
		  EMsfrpo.checkbox_approved();
		  Utils.sleep(3000);
		  EMsfrpo.input_search_keyword("EMPE");
		  Utils.sleep(1000);
		  EMsfrpo.click_btn_search();
		  Utils.sleep(2000);

		  EMsfrpo.click_role_name_01();
		  Utils.sleep(2000);
		  ManageRolesPageObject mrpo = new ManageRolesPageObject(driver);
		  Utils.sleep(2000);
		  mrpo.click_pe_tab();
		  Utils.sleep(2000);
		  //mrpo.clickElementByxPath("setup_newpe_btn");
		  if(mrpo.verifyElementEnable("setup_pe_btn"))
		  {
			  mrpo.clickElementByxPath("setup_pe_btn");
		  }
		  else
		  {
			  mrpo.clickElementByxPath("setup_newpe_btn");
		  }
 		  Utils.sleep(2000);
		  AppiumHelper.screenShot(driver, "Pricing Event Setup", screenshotPath + subpath);
		  //AppiumHelper.verificaion_true(mrpo.verify_popup_dialog_title(), "Edited Minbill");
		  
		  mrpo.click_pestart_date();
		  Utils.sleep(1000);
		  Calendar date = Calendar.getInstance();
		  int day = date.get(Calendar.DAY_OF_MONTH);
		  String path = "//*[text()='" + (day+1) +"']";
		  mrpo.clickElementByPath(path);
		  //mrpo.select_pestart_date();
		  Utils.sleep(1000);
		  mrpo.click_pestart_time();
		  Utils.sleep(1000);
		  mrpo.select_pestart_time();
		  Utils.sleep(1000);
		  mrpo.click_peend_date();
		  Utils.sleep(1000);
		  String path1 = "//*[text()='" + (day+1) +"']";
		  mrpo.clickElementByPath(path1);
		  //mrpo.select_peend_date();
		  Utils.sleep(1000);
		  mrpo.click_peend_time();
		  Utils.sleep(1000);
		  mrpo.select_peend_time();
		  Utils.sleep(1000);
		  mrpo.clickElementByxPath("penext_btn");
		  
		  mrpo.check_petalent01();
		  Utils.sleep(1000);
		  mrpo.check_petalent02();
		  Utils.sleep(1000);
		  mrpo.clickElementByxPath("set_up_event");
		  //mrpo.click_set_up_event();
		  Utils.sleep(6000);
		  AppiumHelper.screenShot(driver, "Pricing Event Setup", screenshotPath + subpath);
		  Utils.sleep(1000);
		  AppiumHelper.verificaion_true(mrpo.verifyElementDisplayed("saved_pe_starttime"), "PE start time displays");
		  AppiumHelper.verificaion_true(mrpo.verifyElementDisplayed("saved_pe_endtime"), "PE end time displays");
		  Utils.sleep(1000);
		  AppiumHelper.verificaion_true(mrpo.verifyElementDisplayed("saved_pe_talent01"), "PE talent 01 displays");
		  AppiumHelper.verificaion_true(mrpo.verifyElementDisplayed("saved_pe_talent02"), "PE talent 02 displays");
		  
		  resultMap.put(account, "pass");
		  logger.info("***** end EM_SetupPricingEvent_7195 page ******");
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
		  ExcelUtils.updateTestResults("ID-7195", resultMap);
		  driver.close();
	  } 
	 
}
