package com.rex.autotest.testCases.Regressions;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.rex.autotest.businessComponent.BusinessComponent;
import com.rex.autotest.pageObjects.EMDashboardPageObject;
import com.rex.autotest.pageObjects.EM_SearchForRolesPageObject;
import com.rex.autotest.pageObjects.ManageRolesPageObject;
import com.rex.autotest.pageObjects.TalentProfilePageObject;
import com.rex.autotest.utilities.AppiumHelper;
import com.rex.autotest.utilities.ExcelUtils;
import com.rex.autotest.utilities.PropUtils;
import com.rex.autotest.utilities.Utils;


public class DP_EMScreening extends BaseTestCase {
	
	Map<String, Map<String, String>> dataMap = ExcelUtils.readExcel("ID-4403");
	Map<String, String> resultMap = new HashMap<String, String>();
   	Properties eleMap = PropUtils.getProperties(MSGFILE); 
   	final String account = "TC-01";
   	final String subpath = "/EM_Screening_GoodFit_4403";
   	
	 @Test
	  public void EM_Screening_GoodFit(){	  
		  logger.info("***** start EM_Screening_GoodFit ******");
		  
		  //valid freelancer/contractor signin
		  BusinessComponent.Signin(driver,dataMap.get(account).get("username"),dataMap.get(account).get("passwd"));
		  Utils.sleep(5000);
  
		  EMDashboardPageObject dashObj = new EMDashboardPageObject(driver);

		  Utils.sleep(1000);	  
		  dashObj.click_roles_btn();
		  Utils.sleep(8000);
		  EM_SearchForRolesPageObject emsrpo = new EM_SearchForRolesPageObject(driver);
		  emsrpo.checkbox_approved();
		  Utils.sleep(4000);
		  emsrpo.input_search_keyword("REX");
		  Utils.sleep(2000);
		  emsrpo.click_btn_search();
		  Utils.sleep(6000);


		  emsrpo.click_role_name_01();
		  Utils.sleep(8000);
		  
		  ManageRolesPageObject mrpo = new ManageRolesPageObject(driver);
		  mrpo.click_tab_talent();
		  Utils.sleep(2000);
		  mrpo.click_shortlisted_tab();
		  Utils.sleep(2000);
		  mrpo.click_shortlist_action_btn();
		  Utils.sleep(2000);
		  mrpo.clickElementByxPath("em_screen_btn");
		  Utils.sleep(3000);
		  mrpo.clickElementByxPath("screen_talent_now");
		  Utils.sleep(6000);
		  TalentProfilePageObject tpp= new TalentProfilePageObject(driver);
		  Utils.sleep(2000);
		  tpp.clickElementByxPath("screening_date");
		  Utils.sleep(2000);
		  tpp.clickElementByxPath("select_screen_date");
		  Utils.sleep(2000);
		  tpp.InputValueByxPath("screen_notes", "Screened. Good fit.");
		  Utils.sleep(2000);
		  tpp.clickElementByxPath("screen_decision");
		  Utils.sleep(2000);
		  tpp.clickElementByxPath("meet_requirement");

		  Utils.sleep(1000);
		  tpp.click_radiono_btn();
		  Utils.sleep(1000);
		  tpp.click_submit_btn();
		  Utils.sleep(6000);
		  AppiumHelper.screenShot(driver, "Screen selected options", screenshotPath + subpath);
		  AppiumHelper.verificaion_true(tpp.verifyElementDisplayed("screening_submited"), "Screen Submitted displays");
		  Utils.sleep(1000);
		  
		  resultMap.put(account, "pass");
		  logger.info("***** end EM_Screening_GoodFit page ******");
		  driver.quit();
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
		  ExcelUtils.updateTestResults("ID-4403", resultMap);
	  } 
	 
}
