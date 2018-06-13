package com.rex.autotest.testCases.Regressions;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.rex.autotest.businessComponent.BusinessComponent;
import com.rex.autotest.pageObjects.EMDashboardPageObject;
import com.rex.autotest.pageObjects.EM_SearchForRolesPageObject;
import com.rex.autotest.pageObjects.EM_SearchForTalentPageObject;
import com.rex.autotest.pageObjects.ManageRolesPageObject;
import com.rex.autotest.pageObjects.TalentProfilePageObject;
import com.rex.autotest.utilities.AppiumHelper;
import com.rex.autotest.utilities.ExcelUtils;
import com.rex.autotest.utilities.PropUtils;
import com.rex.autotest.utilities.Utils;

public class DP_EMPrepareOffer extends BaseTestCase {
	
	//Map<String, Map<String, String>> dataMap = ExcelUtils.readExcel("ID-7222");
	//Map<String, String> resultMap = new HashMap<String, String>();
   	Properties eleMap = PropUtils.getProperties(MSGFILE); 
   	final String account = "TC-01";
   	final String subpath = "/EM_OnboardTalentPreEvent_7222";
   	
	 @Test
	  public void EM_OnboardTalentPreEvent(){	  
		  logger.info("***** start EM_OnboardTalentPreEvent_7222 ******");
		  
		  //valid freelancer/contractor signin
		  BusinessComponent.Signin(driver, "swan004", "Admin@001");
		  Utils.sleep(5000);
		  
		  EMDashboardPageObject dashObj = new EMDashboardPageObject(driver);
		  Utils.sleep(2000);
		  
		  dashObj.click_roles_btn();
		  Utils.sleep(5000);
		  EM_SearchForRolesPageObject emsrpo = new EM_SearchForRolesPageObject(driver);
		  emsrpo.input_search_keyword("DP");
		  Utils.sleep(2000);
		  emsrpo.click_btn_search();
		  Utils.sleep(5000);

		  emsrpo.click_role_name_01();
		  Utils.sleep(6000);
		  ManageRolesPageObject mrpo = new ManageRolesPageObject(driver);
		  
		  mrpo.click_tab_talent();
		  Utils.sleep(2000);
		  
		  String str = mrpo.getElementValueByxPath("shortlist_number");
		  String str1 = mrpo.getElementValueByxPath("offer_sent_no");
		  if(str.contains("0"))
		  {
		  mrpo.click_shortlisted_tab();
		  Utils.sleep(2000);
		  mrpo.click_find_talent_btn();
		  Utils.sleep(20000);
		  EM_SearchForTalentPageObject EMstpo = new EM_SearchForTalentPageObject(driver);
		  Utils.sleep(1000);
		  EMstpo.input_search_keyword("RexAuto");
		  Utils.sleep(2000);

		  EMstpo.click_btn_search_keyword();
		  Utils.sleep(6000);  
		  
		  EMstpo.click_el_talent_name_01();
		  Utils.sleep(6000);
		  TalentProfilePageObject tppo = new TalentProfilePageObject(driver);
          
          tppo.click_shortlist_btn();

		  Utils.sleep(8000);

		  mrpo.click_shortlisted_tab();
		  Utils.sleep(2000);
		  mrpo.click_shortlist_action_btn();
		  Utils.sleep(2000);
		  mrpo.click_el_prepare_offer_btn();
		  Utils.sleep(5000);
		  }
		  else if(str.contains("1"))
		  {
		  mrpo.click_shortlisted_tab();
		  Utils.sleep(2000);
		  mrpo.click_shortlist_action_btn();
		  Utils.sleep(1000);
		  logger.info(mrpo.isObjectExist("el_prepareoffer_btn"));
		  mrpo.click_el_prepare_offer_btn();
		  Utils.sleep(5000);
		  }
		 
		  AppiumHelper.screenShot(driver, "Send offer page displays", screenshotPath + subpath);
		  AppiumHelper.verificaion_true(mrpo.verify_send_offer_page(), "Send offer page");
		  Utils.sleep(1000);
		  mrpo.input_offer_rate("120");
		  Utils.sleep(1000);
		  mrpo.click_offer_rate_savebtn();
		  Utils.sleep(3000);
		  mrpo.click_offer_start_date();
		  Utils.sleep(1000);
		  mrpo.select_offer_start_date();
		  Utils.sleep(1000);
		  mrpo.click_offer_start_time();
		  Utils.sleep(1000);
		  mrpo.select_offer_start_time();
		  Utils.sleep(1000);
		  mrpo.input_check_in_location("Ross Ave 2001");

		  Utils.sleep(1000);
		  //tpp.click_submit_btn();
		  mrpo.click_send_offerbtn();
		  Utils.sleep(5000);

		  //String str4 = Integer.toString(k + 1);
		  
		  //resultMap.put(account, "pass");
		  logger.info("***** end EM_OnboardTalentPreEvent_7222 page ******");
	 }
	  
	 @AfterClass(alwaysRun=true)
	  public void updateResult(){
		  logger.info("***** end update result to excel ******");
//		  dataMap.keySet().removeAll(resultMap.keySet());
//		  for(String tcID : dataMap.keySet()){
//			  if(tcID.length()!=0){
//				  resultMap.put(tcID, "fail");
//			  }   
//		  }
//		  ExcelUtils.updateTestResults("ID-7222", resultMap);
//		  driver.close();
	  } 
	 
}
