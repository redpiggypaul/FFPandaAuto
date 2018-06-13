package com.rex.autotest.testCases.Regressions;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.rex.autotest.businessComponent.BusinessComponent;
import com.rex.autotest.pageObjects.CreateFromScratchPageObject;
import com.rex.autotest.pageObjects.EMDashboardPageObject;
import com.rex.autotest.utilities.AppiumHelper;
import com.rex.autotest.utilities.ExcelUtils;
import com.rex.autotest.utilities.PropUtils;
import com.rex.autotest.utilities.Utils;

public class DP_EMCreateRole extends BaseTestCase {
	
	//Map<String, Map<String, String>> dataMap = ExcelUtils.readExcel("ID-4719");
	//Map<String, String> resultMap = new HashMap<String, String>();
   	Properties eleMap = PropUtils.getProperties(MSGFILE); 
   	final String account = "TC-01";
   	final String subpath = "/CreateRoleFromScratch_SaveAndSubmit_4719";
   	
	 @Test
	  public void CreateRoleFromScratch_SaveAndSubmit(){	
		 
		  logger.info("***** start CreateRoleFromScratch_SaveAndSubmit_4719 page ******");
		  
		  //EM login
		  BusinessComponent.Signin(driver, "swan004", "Admin@001");
		  Utils.sleep(5000);
		  
		  //from dashboard to create new role
		  EMDashboardPageObject EMobj = new EMDashboardPageObject(driver);
		  Utils.sleep(2000);

		  CreateFromScratchPageObject cs = new CreateFromScratchPageObject(driver);
		  EMobj.click_create_new_role();
		  Utils.sleep(1000);
		  int x = (int)(Math.random()*1000);
		  //input data
		  String roleName = "DP Tester_" + x;
		  
		  cs.Create_Role_BasicInformation(cs, roleName, "10", "Sylar Wan", "00883995001");
		  
		  cs.Create_Role_Requirement(cs);

		  cs.Create_Role_ContactDetails(cs, "80", "150");
		  
		  cs.Create_Role_EngagementInformation(cs, "REX", "Mark Li");
		  
		  Utils.sleep(1000);
 		  cs.click_btn_submit_role();
 		  
 		  Utils.sleep(3000);
		  AppiumHelper.screenShot(driver, "SubmitforApproval", screenshotPath + subpath);
		  AppiumHelper.verificaion_true(cs.verify_display_submitmsg(),"You role has been submitted");
		  
		  cs.click_ok_btn();
 		  Utils.sleep(5000); 
		  
		  //resultMap.put(account, "pass");
		  logger.info("***** End CreateRoleFromScratch_SaveAndSubmit_4719 Test ******");
}	 
	 
	  @AfterClass(alwaysRun=true)
	  public void updateResult(){
		  logger.info("***** end update result to excel ******");
		  //dataMap.keySet().removeAll(resultMap.keySet());
		  //for(String tcID : dataMap.keySet()){
			  //if(tcID.length()!=0){
				  //resultMap.put(tcID, "fail");
			  //}   
		 // }
		 // ExcelUtils.updateTestResults("ID-4719", resultMap);
	  } 
}
