package com.rex.autotest.testCases.Regressions;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.rex.autotest.businessComponent.BusinessComponent;
import com.rex.autotest.pageObjects.CreateFromScratchPageObject;
import com.rex.autotest.pageObjects.EMDashboardPageObject;
import com.rex.autotest.pageObjects.EM_SearchForRolesPageObject;
import com.rex.autotest.pageObjects.ManageRolesPageObject;
import com.rex.autotest.utilities.AppiumHelper;
import com.rex.autotest.utilities.ExcelUtils;
import com.rex.autotest.utilities.PropUtils;
import com.rex.autotest.utilities.Utils;

public class DP_ELApproveRole extends BaseTestCase {
	
	//Map<String, Map<String, String>> dataMap = ExcelUtils.readExcel("ID-13832");
	//Map<String, String> resultMap = new HashMap<String, String>();
   	Properties eleMap = PropUtils.getProperties(MSGFILE); 
   	final String account = "TC-01";
   	final String subpath = "/EL_EditAndCloseRole_13832";

	 @Test
	  public void EL_EditAndCloseRole(){	
		 
		  logger.info("***** start EL_EditAndCloseRole_13832 page ******");
		  
		  //EM login
		  BusinessComponent.Signin(driver, "mli197", "Lovevera0501");
		  Utils.sleep(5000);
		  
		  //from dashboard to create new role
		  EMDashboardPageObject dashObj = new EMDashboardPageObject(driver);
		  dashObj.click_roles_btn();
		  Utils.sleep(6000);
		  EM_SearchForRolesPageObject emsro = new EM_SearchForRolesPageObject(driver);
		  emsro.checkbox_awaiting();
		  Utils.sleep(3000);
		  emsro.input_search_keyword("DP");
		  Utils.sleep(1000);
		  emsro.click_btn_search();
		  Utils.sleep(3000);
//
//		  emsro.checkbox_iamel();
//		  Utils.sleep(2000);
//		  emsro.uncheck_iam_em();
//		  Utils.sleep(2000);

		  emsro.click_role_name_01();
		  Utils.sleep(6000);
		  ManageRolesPageObject mrpo = new ManageRolesPageObject(driver);
		  Utils.sleep(2000);
		  mrpo.clickElementByxPath("approve_btn");
		  Utils.sleep(2000);
		  
		  //resultMap.put(account, "Pass");
		  logger.info("***** End EL_EditAndCloseRole_13832 Test ******");
		  driver.quit();
}	 
	 
	  @AfterClass(alwaysRun=true)
	  public void updateResult(){
//		  logger.info("***** end update result to excel ******");
//		  dataMap.keySet().removeAll(resultMap.keySet());
//		  for(String tcID : dataMap.keySet()){
//			  if(tcID.length()!=0){
//				  resultMap.put(tcID, "Failed");
//			  }   
//		  }
//		  ExcelUtils.updateTestResults("ID-13832", resultMap);
	  } 
}
