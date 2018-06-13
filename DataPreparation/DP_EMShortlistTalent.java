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


public class DP_EMShortlistTalent extends BaseTestCase {
	
	Map<String, Map<String, String>> dataMap = ExcelUtils.readExcel("ID-4402");
	Map<String, String> resultMap = new HashMap<String, String>();
   	Properties eleMap = PropUtils.getProperties(MSGFILE); 
   	final String account = "TC-01";
   	final String subpath = "/EM_AddTalentToRole_4402";
   	
	 @Test
	  public void Talent_AddTalentToRole(){	  
		  logger.info("***** start EM_AddTalentToRole_4402 ******");
		  
		  //valid freelancer/contractor signin
		  BusinessComponent.Signin(driver,dataMap.get(account).get("username"),dataMap.get(account).get("passwd"));
		  Utils.sleep(3000);
  
		  EMDashboardPageObject dashObj = new EMDashboardPageObject(driver);
		  Utils.sleep(2000);
		  
		  dashObj.click_roles_btn();
		  Utils.sleep(6000);
		  EM_SearchForRolesPageObject emsrpo = new EM_SearchForRolesPageObject(driver);
		  emsrpo.checkbox_approved();
		  Utils.sleep(3000);
		  emsrpo.input_search_keyword("UIOS");
		  Utils.sleep(1000);
		  emsrpo.click_btn_search();
		  Utils.sleep(4000);

		  emsrpo.click_role_name_01();
		  Utils.sleep(6000);
		  ManageRolesPageObject mrpo = new ManageRolesPageObject(driver);
		  
		  mrpo.click_tab_talent();
		  Utils.sleep(2000);
		  
		  String str = mrpo.get_shortlist_number();
		  int i = Integer.parseInt(str);
		  
		  mrpo.click_shortlisted_tab();
		  
		  Utils.sleep(1000);
		  mrpo.click_find_talent_btn();
		  Utils.sleep(5000);
		  EM_SearchForTalentPageObject emstpo = new EM_SearchForTalentPageObject(driver);

		  emstpo.input_search_keyword(dataMap.get(account).get("searchstring"));
		  Utils.sleep(3000);
		  emstpo.click_btn_search_keyword();
		  Utils.sleep(3000);

		  emstpo.click_em_talent_list_01();
		  Utils.sleep(5000);
		  TalentProfilePageObject tppo = new TalentProfilePageObject(driver);
		  Utils.sleep(2000); 
		  tppo.click_shortlist_btn();
		  Utils.sleep(3000);
		  
		  String str1 = mrpo.get_shortlist_number();
		  int j=Integer.parseInt(str1);
		  
		  if(j == i+1)
		  {
			  logger.info("The number of shortlist is changed from " + i + " to " +j);
		  }
		  Utils.sleep(3000);
//		  mrpo.click_shortlisted_tab();
//		  Utils.sleep(1000);
//		  mrpo.click_shortlist_action_btn();
//		  
//		  Utils.sleep(1000);
//		  mrpo.click_admin_remove_shortlisted_talent();
//		  Utils.sleep(2000);
//		  String str2 = mrpo.get_shortlist_number();
//		  int k = Integer.parseInt(str2);
//		  
//		  if(i == k)
//		  {
//			  logger.info("The number of shortlist is changed to " + k);
//		  }
//		  Utils.sleep(1000);
		  
		  AppiumHelper.screenShot(driver, "Shortlist talent details", screenshotPath + subpath);
		  //AppiumHelper.verificaion_true(mrpo.verify_savetorole_number(), "Save to role number");
		  //AppiumHelper.verificaion_true(mrpo.verify_shortlist_talent01(dataMap.get(account).get("talentname")), "Shortlist talent name");
		  
		  resultMap.put(account, "pass");
		  logger.info("***** end EM_AddTalentToRole_4402 page ******");
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
		  ExcelUtils.updateTestResults("ID-4402", resultMap);
	  } 
	 
}
