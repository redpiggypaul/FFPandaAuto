package com.rex.autotest.pageObjects;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.rex.autotest.pageObjects.BasePageObject;
import com.rex.autotest.utilities.PropUtils;
import com.rex.autotest.utilities.Utils;


public class EM_SearchForRolesPageObject extends BasePageObject {
	private  static final String PROP_FILE =  System.getProperty("user.dir")+"/src/com/rex/autotest/uimapping/EM_SearchForRoles.properties";
	Properties mapping = PropUtils.getProperties(PROP_FILE);
	
	public EM_SearchForRolesPageObject(WebDriver driver) {
		this.driver = driver;
		//if(!driver.getTitle().contains("Talent Exchange")){
		//	logger.info("Search For Roles page is not loaded");
		//	throw new IllegalStateException("Search For Roles page is not loaded");
		//}
	}
	

	public void clickElementByxPath(String xPath)
	{
		clickElement(driver.findElement(By.xpath(mapping.getProperty(xPath))));
	}
	
	public void clickElementByPath(String str)
	{
		clickElement(driver.findElement(By.xpath("\"" + str + "\"")));
	}
	
	public void clickElementById(String Id)
	{
		clickElement(driver.findElement(By.xpath(mapping.getProperty(Id))));
	}
	
	public void InputValueById(String Id, String txt)
	{
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty(Id))), txt);
	}
	
	public void InputValueByxPath(String xPath, String txt)
	{
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty(xPath))), txt);
	}
	
	public Boolean verifyPage(String xPath, String str){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getText().toString().toUpperCase().contains(str.toUpperCase());
	}
	
	public Boolean verifyElementDisplayed(String xPath){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).isDisplayed();
	}
	
	public Boolean verifyElementEnable(String xPath){
		try{
		     return driver.findElement(By.xpath(mapping.getProperty(xPath))).isDisplayed();
		}catch(NoSuchElementException e)
		{ 
			return true;
		}
	}
	
	public Boolean verifyElementDisable(String xPath){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getAttribute("ng-show").matches("!userPermission.isEnableEditBtn");
	}
	
	public String getElementAttributeByxPath(String xPath, String str){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getAttribute(str);
	}
	
	public String getElementValueByxPath(String xPath){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getText().toString();
	}
	
	public Boolean verifyUpdatedValue(String str, String str1){
		return (str.contains(str1)==false);
	}
	
	
	public void click_link_your_roles(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("link_your_roles"))));
	}
	
	public void click_link_all_roles(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("link_all_roles"))));
	}
	
	//web
	public void click_btn_roles(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("btn_roles"))));
	}
	
	public void check_created_roles(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("checkbox_role_created"))));
	}
	
	public void check_pending_status()
	{
		clickElement(driver.findElement(By.xpath(mapping.getProperty("checkbox_awaiting"))));
	}
	
	//mobile
	public void android_click_btn_roles(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_btn_roles"))));
	}
	
	public void android_select_roles(String stropt){
		selectDropDownByText(mapping.getProperty("m_opt_role"), stropt);
	}
	
	public void android_click_opt_all_roles(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_opt_all_roles"))));
	}
	//mobile
	
	public void input_search_keyword(String msgStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("input_search_keyword"))), msgStr);
	}
	
	public void click_btn_search(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("btn_search"))));
	}
	
	public void checkbox_draft(){
		Checkboxed(driver.findElement(By.xpath(mapping.getProperty("checkbox_draft"))));
	}
	
	public void checkbox_arm(){
		Checkboxed(driver.findElement(By.xpath(mapping.getProperty("checkbox_arm"))));
	}
	
	public void checkbox_iamel(){
		Checkboxed(driver.findElement(By.xpath(mapping.getProperty("checkbox_iamel"))));
	}
	
	public void checkbox_awaiting(){
		Checkboxed(driver.findElement(By.xpath(mapping.getProperty("checkbox_awaiting"))));
	}
	
	public void checkbox_closed(){
		Checkboxed(driver.findElement(By.xpath(mapping.getProperty("checkbox_closed"))));
	}
	
	public void uncheck_iam_em(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("checkbox_EM"))));
	}
	
	public void checkbox_approved(){
		Checkboxed(driver.findElement(By.xpath(mapping.getProperty("checkbox_approved"))));
	}
	
	public void checkbox_20(){
		Checkboxed(driver.findElement(By.xpath(mapping.getProperty("checkbox_20"))));
	}
	
	public void checkbox_200_250(){
		Checkboxed(driver.findElement(By.xpath(mapping.getProperty("checkbox_200_250"))));
	}
	
	public void click_filter_role_created(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("filter_role_created"))));
	}
	
	public void click_role_name_01(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_name_01"))));
		Utils.sleep(2000);
		
	}
	
	public void click_role_name_02(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_name_02"))));
	}
	
	
	public Boolean verify_role_name_01(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("role_name_01"))).getText().contains(str.toUpperCase());
    }
	
	public Boolean verify_role_client_name_01(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("role_client_name_01"))).getText().contains(str);
    }
	
	public Boolean verify_display_frame_role_01(){	
		return driver.findElement(By.xpath(mapping.getProperty("frame_role_01"))).isDisplayed();
    }
	
	public Boolean verify_msg_searchroles_title(){	
		return driver.findElement(By.xpath(mapping.getProperty("msg_searchroles_title"))).getText().equals("Search for Roles");
    }
	
}
