package com.rex.autotest.pageObjects;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.rex.autotest.utilities.AppiumHelper;
import com.rex.autotest.utilities.PropUtils;
import com.rex.autotest.utilities.Utils;

public class CreateFromScratchPageObject extends BasePageObject {
	private  static final String PROP_FILE =  System.getProperty("user.dir")+"/src/com/rex/autotest/uimapping/CreateFromScratch.properties";
	Properties mapping = PropUtils.getProperties(PROP_FILE);
	
	public CreateFromScratchPageObject(WebDriver driver) {
		this.driver = driver;
	}
	
	public void clickElementByPath(String path)
	{
		clickElement(driver.findElement(By.xpath(path)));
	}
	
	public void Create_Role_BasicInformation(CreateFromScratchPageObject cs, String rolename, String rolenumber, String romeemname, String wbscode)
	{
		  Utils.sleep(1000);
		  cs.InputValueByxPath("role_creation_name", rolename);
		  Utils.sleep(1000);
		  cs.InputValueByxPath("role_creation_number", rolenumber);
		  Utils.sleep(1000);
		  cs.InputValueByxPath("role_creation_em_name", romeemname);
		  
		  Utils.sleep(2000);
		  cs.PressEnterKey();
		  Utils.sleep(1000);
		  cs.clickElementByxPath("role_creation_next");
		  Utils.sleep(5000);
		  //cs.clickElementByxPath("role_creation_wbs_code");
		  //Utils.sleep(1000);
		  cs.InputValueByxPath("creScr_info_WBScode", wbscode);
		  Utils.sleep(1000);
		  cs.clickElementByxPath("restricted_entity");
		  Utils.sleep(1000);
		  cs.clickElementByxPath("select_restricted_entity");

		  Utils.sleep(2000);
//		  cs.InputValueByxPath("role_creation_state", "TX");
//		  Utils.sleep(1000);
//		  cs.PressEnterKey();
//		  Utils.sleep(1000);
//		  cs.InputValueByxPath("role_creation_city", "Dallas");
//		  Utils.sleep(1000);
//		  cs.PressEnterKey();
//		  Utils.sleep(1000);
		  cs.InputValueByxPath("role_creation_location", "Dallas TX");
		  Utils.sleep(2000);
		  cs.PressEnterKey();
		  Utils.sleep(1000);
		  
		  Utils.sleep(1000);
		  cs.clickElementByxPath("role_savebtn");
		 
		  Utils.sleep(5000);
	}
	
	public void Create_Role_Requirement(CreateFromScratchPageObject cs)
	{
		Utils.sleep(2000);
		  cs.input_info_roleSummary();
		  Utils.sleep(1000);
		  cs.InputValueByxPath("role_required_skills_field", "PROJECT MANAGEMENT");
		  Utils.sleep(2000);
		  cs.PressEnterKey();
		  Utils.sleep(1000);
		  cs.InputValueByxPath("role_preferred_skills_field", "EXCEL");
		  Utils.sleep(2000);
		  cs.PressEnterKey();
		  Utils.sleep(1000);
		  cs.InputValueByxPath("role_lauguage_field", "ENGLISH");
		  Utils.sleep(2000);
		  cs.PressEnterKey();
		  Utils.sleep(1000);
		  cs.InputValueByxPath("role_required_certification", "CPA");
		  Utils.sleep(2000);
		  cs.PressEnterKey();
		  Utils.sleep(1000);
		  cs.input_info_roleDescription();
		  Utils.sleep(1000);
		  cs.clickElementByxPath("role_years_of_experience");
		  Utils.sleep(1000);
		  cs.clickElementByxPath("role_select_years_of_experience");
		  Utils.sleep(1000);
		  cs.click_role_requirement_savebtn();
		  
		  Utils.sleep(5000);
	}
	
	public void Create_Role_ContactDetails(CreateFromScratchPageObject cs, String minbill, String maxbill){
		  Utils.sleep(2000);
		  cs.click_contact_start_date();
		  Utils.sleep(2000);
		  cs.click_contact_select_start_date();
		  Utils.sleep(2000);
		  cs.click_contact_end_date();
		  Utils.sleep(2000);
		  cs.click_contact_next_month();
		  Utils.sleep(2000);
		  cs.click_contact_select_end_date();
		  Utils.sleep(2000);
		  cs.input_contact_details_minrate(minbill);
		  Utils.sleep(1000);
		  cs.input_contact_details_maxrate(maxbill);
		  Utils.sleep(1000);
		  cs.click_pwc_deliverable_yes();
		  Utils.sleep(1000);
		  cs.click_signoff_no();
		  Utils.sleep(1000);
		  cs.click_similiar_function_yes();
		  Utils.sleep(1000);
		  cs.click_contact_details_travel_requirement();
		  Utils.sleep(1000);
		  cs.click_contact_details_travel_requirement_option();
		  Utils.sleep(1000);
		  cs.click_contact_details_savebtn();
		  Utils.sleep(4000);
	}
	
	public void Create_Role_EngagementInformation(CreateFromScratchPageObject cs, String projectName, String elName){
		  Utils.sleep(1000);
		  cs.input_engagement_project_name(projectName);
		  Utils.sleep(2000);
		  cs.PressEnterKey();
		  Utils.sleep(1000);
		  cs.click_engagement_vertical();
		  Utils.sleep(1000);
		  cs.select_engagement_vertical();
		  Utils.sleep(1000);
		  cs.click_role_pwc_technology_no();
		  Utils.sleep(1000);
		  cs.input_engagement_leader(elName);
		  Utils.sleep(1000);
		  cs.click_engagement_savebtn();
		  Utils.sleep(4000);

	}
	
	public void clickElementByxPath(String xPath)
	{
		clickElement(driver.findElement(By.xpath(mapping.getProperty(xPath))));
	}
	
	public void clickElementById(String Id)
	{
		clickElement(driver.findElement(By.xpath(mapping.getProperty(Id))));
	}
	
	public void InputValueById(String Id, String txt)
	{
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty(Id))), txt);
	}
	
	public String getElementAttribute(String xPath, String attribute)
	{
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getAttribute(attribute).toString();
	}
	
	public Boolean verifyElementValue(String xPath, String str){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getText().toString().contains(str);
	}
	
	public void InputValueByxPath(String xPath, String txt)
	{
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty(xPath))), txt);
	}
	
	public Boolean verifyElement(String xPath, String str){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getText().toString().toUpperCase().contains(str.toUpperCase());
	}
//	
//	public Boolean verifyElement(String xPath, String str){
//		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getText().toString().toUpperCase().contains(str.toUpperCase());
//	}
	
	public Boolean verifyElementDisplayed(String xPath){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).isDisplayed();
	}
	
	public String getElementValueByxPath(String xPath){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getText().toString();
	}
	
	public Boolean verifyUpdatedValue(String str, String str1){
		return (str.contains(str1)==false);
	}
	
	
	/*ROLE OVERVIEW Header*/
	public Boolean verify_creScr_header(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_header"))).isDisplayed();
	}
	
	/*Role Name inputbox*/
	public Boolean verify_input_role_name(){	
		return driver.findElement(By.xpath(mapping.getProperty("input_role_name"))).isDisplayed();
	}
	
	/*Project Name inputbox*/
	public Boolean verify_input_project_name(){	
		return driver.findElement(By.xpath(mapping.getProperty("input_project_name"))).isDisplayed();
	}
	
	/*Num of Role inputbox*/
	public Boolean verify_input_num_role(){	
		return driver.findElement(By.xpath(mapping.getProperty("input_num_role"))).isDisplayed();
	}
	
	public Boolean verify_clone_role_num(){	
		return driver.findElement(By.xpath(mapping.getProperty("role_num"))).getText().contains("0");
	}
	
	public Boolean verify_clone_wbscode(){	
		return driver.findElement(By.xpath(mapping.getProperty("role_num"))).isSelected();
	}
	
	/*long char error*/
	public Boolean verify_err_rolename_longchar(String msgStr){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_roleNameErr"))).getText().contains(msgStr);
	}
	
	public Boolean verify_err_projectname_longchar(String msgStr){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_projectNameErr"))).getText().contains(msgStr);
	}
	
	public Boolean verify_err_rolename_required(String msgStr){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_roleNameErr01"))).getText().contains(msgStr);
	}
	
	public Boolean verify_err_projectname_required(String msgStr){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_projectNameErr01"))).getText().contains(msgStr);
	}
	
	public Boolean verify_err_rolenum(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_numberOfRolesErr"))).isDisplayed();
	}
	
	public Boolean verify_non_numerical(){	
		return driver.findElement(By.xpath(mapping.getProperty("input_num_role"))).getText().equals("");
	}
	
	/*three inputs*/
	public void input_role_name(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("role_creation_name"))),usrStr);
	}
	
	public void input_project_name(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("client_name"))),usrStr);
	}
	
	public void input_num_role(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("number_of_people"))),usrStr);
	}
	/*three inputs*/
	
	public void input_em_name(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("engament_manager"))),usrStr);
	}
	
	/*five tabs*/
	public void click_tab_information(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("tab_information"))));
	}
	
	public void click_tab_requirements(){
		if(driver.findElement(By.xpath(mapping.getProperty("tab_requirements"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("tab_requirements"))));
		}
	}
	
	public void click_role_requirement_editbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_requirement_editbtn"))));
	}
	
	public void click_tab_technology(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("tab_technology"))));
	}
	
	public void click_tab_classification(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("tab_classification"))));
	}
	
	public void click_tab_routing_reporting(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("tab_routing_reporting"))));
	}
	
//	public void click_tab_technology(){
//		clickElement(driver.findElement(By.xpath(mapping.getProperty("tab_matching_event"))));
//	}
	/*five tabs*/
	
	/*mobile - five tabs*/
	public void android_click_tab_information(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_tab_information"))));
	}
	
	public void android_click_tab_requirements(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_tab_requirements"))));
	}
	
	public String android_scroll_tab_information(){
		return mapping.getProperty("m_tab_requirements").toString();
	}
	
	public String android_scroll_tab_requirements(){
		return mapping.getProperty("m_tab_requirements").toString();
	}
	
	
	
	public void android_click_tab_classification(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_tab_classification"))));
	}
	
	public void android_click_tab_routing_reporting(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_tab_routing_reporting"))));
	}
	
	public void android_click_tab_matching_event(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_tab_matching_event"))));
	}
	/*mobile - five tabs*/
	
	
	public void click_creScr_leavePage(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_leavePage"))));
	}
	
	public void click_creScr_roleName(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("input_role_name"))));
	}
	
	public void click_creScr_projectName(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("input_project_name"))));

	}
	
	public void click_creScr_numberOfRoles(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("input_num_role"))));
	}
	
	//submit role for approval
	public void click_btn_submit_role(){
			clickElement(driver.findElement(By.xpath("//*[text()='Submit for Approval']")));
	}
	
	//submit role for approval display
	public Boolean verify_display_btn_submit_role(){
		return driver.findElement(By.xpath(mapping.getProperty("btn_submit_role"))).isDisplayed();
	}
	
	public Boolean verify_submitted_role_status(String str){
		return driver.findElement(By.xpath(mapping.getProperty("submitted_role_status"))).getText().toString().toUpperCase().contains(str.toUpperCase());
	}
	
	//requirement
	public void click_role_requirements_editbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_requirement_editbtn"))));
	}
	
	public void input_role_required_skills_field(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("role_required_skills_field"))),usrStr);
	}
	
	public void click_role_required_skills_addbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_required_skills_addbtn"))));
	}
	
	public void input_role_preferred_skills_field(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("role_preferred_skills_field"))),usrStr);
	}
	
	public void click_role_preferred_skills_addbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_preferred_skills_addbtn"))));
	}
	
	public void click_role_add_another_requirement(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_add_another_requirement"))));
	}
	
	public void click_role_another_requirement(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_another_requirement"))));
	}
	
	public void click_role_select_another_requirement(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_select_another_requirement"))));
	}
	
	public void input_role_another_requirement_field(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("role_another_requirement_field"))),usrStr);
	}
	
	public void click_role_another_requirement_addbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_another_requirement_addbtn"))));
	}
	
	public void click_role_years_of_experience(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_years_of_experience"))));
	}
	
	public void click_role_select_years_of_experience(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_select_years_of_experience"))));
	}
	
	public void click_role_pwc_technology_no(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_pwc_technology_no"))));
	}
	
	public void click_role_requirement_savebtn(){
		clickElement(driver.findElement(By.xpath("//*[text()='Save']")));
	}
	
	//information
	
	//Engagement information
	public void click_engagement_info_editbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("engagement_info_editbtn"))));
	}
	
	public void click_engagement_vertical(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("engagement_vertical"))));
	}
	
	public void select_engagement_vertical(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_engagement_vertical"))));
	}
	
	public void click_engagement_savebtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("engagement_savebtn"))));
	}
	
	public void input_engagement_project_name(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("engagement_project_name"))),usrStr);
	}
	
	public void input_engagement_leader(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("engagement_leader"))),usrStr);
		Utils.sleep(2000);
		PressEnterKey();
	}
	
	//Contact Details
	public void click_contact_details_editbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("contact_details_editbtn"))));
	}
	
	public void click_contact_details_travel_requirement(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("contact_details_travel_requirement"))));
	}
	
	public void click_contact_start_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("click_start_date"))));
	}
	
	public void click_contact_select_start_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_start_date"))));
	}
	
	public void select_start_date_in5days(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_start_date_in5days"))));
	}
	
	public void click_contact_end_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("click_end_date"))));
	}
	
	public void click_contact_next_month(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("next_month"))));
	}
	
	public void click_contact_select_end_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_end_date"))));
	}
	
	public void click_contact_details_travel_requirement_option(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("contact_details_travel_requirement_option"))));
	}
	
	public void click_pwc_deliverable_yes(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("pwc_deliverable_yes"))));
	}
	
	public void click_signoff_no(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("signoff_no"))));
	}
	
	public void click_similiar_function_yes(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("similiar_function_yes"))));
	}
	
	public void click_contact_details_savebtn(){
		clickElement(driver.findElement(By.xpath("//*[text()='Save']")));
	}
	
	public void input_contact_details_minrate(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("contact_details_minrate"))),usrStr);
	}
	
	public void input_contact_details_maxrate(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("contact_details_maxrate"))),usrStr);
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public void input_info_WBScode(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("wbs_code"))),usrStr);
	}
	
	public void input_additional_role_manager(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("additional_role_manager_txtfield"))),usrStr);
	}
	
	public void click_additional_role_manager_tab(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("additional_role_manager_tab"))));
	}
	
	public void click_role_details_editbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_details_editbtn"))));
	}
	
	public void click_clone_role_editbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("clone_role_editbtn"))));
	}
	
	public void click_clone_role_savebtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("clone_role_savebtn"))));
	}
	
	public void click_clone_contact_editbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("clone_contact_editbtn"))));
	}
	
	public void click_clone_contact_savebtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("clone_contact_savebtn"))));
	}
	
	public void click_clone_engagement_editbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("clone_engagement_editbtn"))));
	}
	
	public void click_clone_engagement_savebtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("clone_engagement_savebtn"))));
	}
	
	public void click_role_details_savebtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_details_savebtn"))));
	}
	
	public void click_info_WBS_checkbox(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_info_WBS_checkbox"))));
	}
	
	public void click_info_clientName(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_info_clientName"))));
	}
	
	public void input_info_clientName(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("creScr_info_clientName"))),usrStr);
	}
	
	public void click_info_entity(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_info_entity"))));
	}
	
	public void select_info_entity(String stropt){
		selectDropDownByText(mapping.getProperty("creScr_info_entity_opt"), stropt);
	}
	
	//mobile
	public void android_click_info_entity(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_creScr_info_entity"))));
	}
	
	public void android_select_info_entity(String stropt){
		selectDropDownByVisableText(driver.findElement(By.xpath(mapping.getProperty("m_creScr_info_entity"))), stropt);
	}
	//mobile
	
	public void input_info_roleSummary(){
		//WebElement f = driver.findElement(By.id("ui-tinymce-0_ifr"));\
		int x = (int)(Math.random()*1000);
		Utils.sleep(1000);
		driver.switchTo().frame("ui-tinymce-0_ifr");
		Utils.sleep(1000);
		WebElement editor = driver.findElement(By.tagName("body"));
		Utils.sleep(1000);
		editor.click();
		Utils.sleep(1000);
		editor.sendKeys("Testing Role Summary." + x); 
//		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
//		Utils.sleep(1000);
//		jsExecutor.executeScript("arguments[0].innerHTML = '<h2>Testing Role Summary</h2>'", editor);
		Utils.sleep(2000);
		driver.switchTo().defaultContent();
		Utils.sleep(1000);
	}
	
	public void input_info_roleSummary(String str){
		
	}
	
	public void input_info_roleDescription(){
		int x = (int)(Math.random()*1000);
		driver.switchTo().frame("ui-tinymce-1_ifr");
		Utils.sleep(1000);
		WebElement editor = driver.findElement(By.tagName("body"));
		Utils.sleep(1000);
		editor.click();
		Utils.sleep(1000);
		editor.sendKeys("Testing Role Description" + x);
//		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
//		Utils.sleep(1000);
//		jsExecutor.executeScript("arguments[0].innerHTML = '<h2>Testing Role Description</h2>'", editor);
//		Utils.sleep(1000);
		//editor.click();
		Utils.sleep(1000);
		driver.switchTo().defaultContent();
//		Utils.sleep(1000);
//		driver.switchTo().frame("ui-tinymce-0_ifr");
//		Utils.sleep(1000);
//		WebElement editor1 = driver.findElement(By.tagName("body"));
//		Utils.sleep(500);
//		editor1.click();
//		Utils.sleep(1000);
//		driver.switchTo().defaultContent();
	}
	
	public void input_info_roleDescription(String str){

	}
	
	public void input_role_required_certifications(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("role_required_certifications"))),usrStr);
	}
	
	public void input_role_required_languages(String msgStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("role_required_languages"))),msgStr);
	}
	
	public void click_role_cerfitications_addbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_cerfitications_addbtn"))));
	}
	
	public void click_role_language_addbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_language_addbtn"))));
	}
	
	public void input_info_roleDifferentiators(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("creScr_info_roleDifferentiators"))),usrStr);
	}
	
	public void input_info_skill(String msgStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("creScr_info_skill"))),msgStr);
	}
	
	public void input_expected_duration(String msgStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("expected_duration"))),msgStr);
	}
	
	public String get_saved_start_date(){
		return driver.findElement(By.xpath(mapping.getProperty("saved_start_date"))).getText().toString();
	}
	
	public String get_saved_end_date(){
		return driver.findElement(By.xpath(mapping.getProperty("saved_end_date"))).getText().toString();
	}
	
	public String get_saved_duration(){
		return driver.findElement(By.xpath(mapping.getProperty("saved_duration"))).getText().toString();
	}
	
	public void click_info_skill_add(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_info_skill_add"))));
	}
	
	public void click_info_years(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_info_years"))));
	}
	
	public void select_info_years(String stropt){
		selectDropDownByText(mapping.getProperty("creScr_info_years_opt"), stropt);
	}
	
	//mobile
	public void android_click_info_years(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_creScr_info_years"))));
	}
	
	public void android_select_info_years(String stropt){
		selectDropDownByVisableText(driver.findElement(By.xpath(mapping.getProperty("m_creScr_info_years"))), stropt);
	}
	//mobile
	
	public void click_info_saveButton(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_info_saveButton"))));
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public Boolean verify_creScr_info_clientNameErr(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_info_clientNameErr"))).isDisplayed();
	}
	
	public Boolean verify_warning_msg(){	
		return driver.findElement(By.xpath(mapping.getProperty("warning_msg"))).getText().toString().toUpperCase().contains(mapping.getProperty("warning_msg_txt").toUpperCase());
	}
	
	public Boolean verify_5_business_days(){	
		return driver.findElement(By.xpath(mapping.getProperty("5_business_days"))).getText().toString().toUpperCase().contains(mapping.getProperty("5_business_days_txt").toUpperCase());
	}
	
	public void click_warning_msg_ok(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("warning_msg_ok"))));
	}
	
	public void click_remove_arm(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("remove_arm"))));
	}
	
	public Boolean verify_err_roleSummary(String usrStr){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_info_roleSummaryErr"))).getText().contains(usrStr);
	}
	
	public Boolean verify_err_roleDifferentiators(String usrStr){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_info_roleDifferentiatorsErr"))).getText().contains(usrStr);
	}
	
	public Boolean verify_err_info_min_skill(String msgStr){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_info_skillErr"))).getText().contains(msgStr);
	}
	
	public Boolean verify_err_info_max_skill(){	
		String att = driver.findElement(By.xpath(mapping.getProperty("creScr_info_skill"))).getAttribute("disabled");
		System.out.println(att);
		if (att.contains("true"))
		{
			return true;		
		}
		else
			return false;
	}
	
	//Requirements Tab
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public Boolean verify_display_req_startDate(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_startDate"))).isDisplayed();
	}
	
	public Boolean verify_display_req_endDate(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_endDate"))).isDisplayed();
	}
	
	public Boolean verify_display_req_city(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_city"))).isDisplayed();
	}
	
	public Boolean verify_display_req_state(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_state"))).isDisplayed();
	}
	
	//mobile
	public Boolean android_verify_display_req_state(){	
		return driver.findElement(By.xpath(mapping.getProperty("m_creScr_req_state"))).isDisplayed();
	}
	//mobile
	
	public Boolean verify_display_req_zipcode(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_zipCode"))).isDisplayed();
	}
	
	public Boolean verify_display_req_travel(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_travel"))).isDisplayed();
	}
	
	//mobile
	public Boolean android_verify_display_req_travel(){	
		return driver.findElement(By.xpath(mapping.getProperty("m_creScr_req_travel"))).isDisplayed();
	}
	//mobile
	
	public Boolean verify_display_req_international(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_internaltional"))).isDisplayed();
	}
	
	//mobile
	public Boolean android_verify_display_req_international(){	
		return driver.findElement(By.xpath(mapping.getProperty("m_creScr_req_internaltional"))).isDisplayed();
	}
	//mobile
	
	public Boolean verify_display_req_minbill(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_minBillRate"))).isDisplayed();
	}
	
	public Boolean verify_display_req_maxbill(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_maxBillRate"))).isDisplayed();
	}
	
	public Boolean verify_display_req_payment(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_payment"))).isDisplayed();
	}
	
	//mobile
	public Boolean android_verify_display_req_payment(){	
		return driver.findElement(By.xpath(mapping.getProperty("m_creScr_req_payment"))).isDisplayed();
	}
	//mobile
	
	public Boolean verify_display_req_parttime(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_parttime"))).isDisplayed();
	}
	
	//mobile
	public Boolean android_verify_display_req_parttime(){	
		return driver.findElement(By.xpath(mapping.getProperty("m_creScr_req_parttime"))).isDisplayed();
	}
	//mobile
	
	public Boolean verify_display_req_reimbursed(){	
		return isObjectExist(mapping.getProperty("creScr_req_reimbursed"));
	}
	
	public Boolean verify_display_req_notReimbursed(){	
		return isObjectExist(mapping.getProperty("creScr_req_notReimbursed"));
	}
	
	public Boolean verify_display_req_notExpected(){	
		return isObjectExist(mapping.getProperty("creScr_req_notExpected"));
	}
	
	public Boolean verify_display_req_expected(){	
		return isObjectExist(mapping.getProperty("creScr_req_expected"));
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public void input_req_startDate(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("creScr_req_startDate"))),usrStr);
	}
	
	public void click_start_date(){
		if(driver.findElement(By.xpath(mapping.getProperty("creScr_req_startDate"))).isDisplayed()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_req_startDate"))));
		}
	}
	
	public void select_start_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_startDate"))));
	}
	
	public void select_clone_start_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("clone_start_date"))));
	}
	
	public void click_date_next(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("clone_date_next"))));
	}
	
	public void select_clone_end_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("clone_end_date"))));
	}
	
	public void click_end_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_req_endDate"))));
	}
	
	public void select_end_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_endDate"))));
	}

	public void input_req_endDate(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("creScr_req_endDate"))),usrStr);
	}
	
	public void input_req_city(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("role_city"))),usrStr);
	}
	
	public void click_dropdown_state(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_state"))));
	}
	
	public void select_opt_state(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_role_state"))));
	}
	
	public void click_role_technology()
	{
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_req_technology"))));
	}
	
	//mobile
	public void android_click_req_state(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_creScr_req_state"))));
	}
	
	public void android_select_req_state(String stropt){
		selectDropDownByVisableText(driver.findElement(By.xpath(mapping.getProperty("m_creScr_req_state"))), stropt);
	}
	//mobile
	
	public void input_req_zipCode(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("role_zipcode"))),usrStr);
	}
	
	public void click_dropdown_travel(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_req_travel"))));
	}
	
	public void check_represent_region(){
		clickElement(driver.findElement(By.id("represent")));
	}
	
	public void input_represent_region(){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("represent_region_txt"))), "North Colifornia");
	}
	
	public void select_opt_travel(String opt){
		selectDropDownByText(mapping.getProperty("creScr_req_travel_opt"),opt);
	}
	
	public void click_dropdown_restricted_entity(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("restricted_entity"))));
	}
	
	public void click_role_savebtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_savebtn"))));
	}
	
	public void click_role_save_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_save_btn"))));
	}
	
	public void click_exit_discard_changes(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("exit_discard_changes"))));
	}
	
	public void select_restricted_entity(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_restricted_entity"))));
	}
	
	//mobile
	public void android_click_req_travel(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_creScr_req_travel"))));
	}
	
	public void android_select_req_travel(String stropt){
		selectDropDownByVisableText(driver.findElement(By.xpath(mapping.getProperty("m_creScr_req_travel"))), stropt);
	}
	//mobile
	
	public void input_req_country(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("creScr_req_country"))),usrStr);
	}
	
	public void click_dropdown_international(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_req_internaltional"))));
	}
	
	public void select_opt_international(String opt){
		selectDropDownByText(mapping.getProperty("creScr_req_internaltional_opt"),opt);
	}
	
	//mobile
	public void android_click_req_internaltional(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_creScr_req_internaltional"))));
	}
	
	public void android_select_req_internaltional(String stropt){
		selectDropDownByVisableText(driver.findElement(By.xpath(mapping.getProperty("m_creScr_req_internaltional"))), stropt);
	}
	//mobile
	
	public void input_req_minBillRate(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("creScr_req_minBillRate"))),usrStr);
	}
	
	public void input_req_maxBillRate(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("creScr_req_maxBillRate"))),usrStr);
	}
	
	public void click_req_paymentType(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_req_payment"))));
	}
	
	public void select_paymentType(String stropt){
		selectDropDownByText(mapping.getProperty("creScr_req_payment_opt"), stropt);
	}
	
	public void click_dropdown_parttime(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_req_parttime"))));
	}
	
	public void select_opt_parttime(String stropt){
		selectDropDownByText(mapping.getProperty("creScr_req_parttime_opt"), stropt);
	}
	
	//mobile
	public void android_click_req_parttime(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_creScr_req_parttime"))));
	}
	
	public void android_select_req_parttime(String stropt){
		selectDropDownByVisableText(driver.findElement(By.xpath(mapping.getProperty("m_creScr_req_parttime"))), stropt);
	}
	//mobile
	
	public Boolean verify_creScr_req_startDateErr(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_startDateErr"))).isDisplayed();
	}
	
	public Boolean verify_creScr_req_dateErr(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_dateErr"))).getText().contains(str);
    }
	
	public void click_creScr_req_countryAdd(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_req_countryAdd"))));
	}
	
	public void input_creScr_req_maxBillRateErr(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("creScr_req_maxBillRate"))),usrStr);
	}
	
	public Boolean verify_err_BillRate(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_billRateErr"))).getText().contains(str);
	}
	
	public void click_creScr_req_reimbursed(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_req_reimbursed"))));
	}
	
	public Boolean creScr_req_reimbursed(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_reimbursed"))).isDisplayed();
	}
	
	public Boolean creScr_req_notReimbursed(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_notReimbursed"))).isDisplayed();
	}
	
	public void click_creScr_req_excepted(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_req_excepted"))));
	}
	
	public Boolean creScr_req_excepted(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_excepted"))).isDisplayed();
	}
	
	public Boolean creScr_req_notExcepted(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_req_notExcepted"))).isDisplayed();
	}
	
	public void click_req_saveButton(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_req_saveButton"))));
	}
	
	public void click_arm_savebtn(){
		clickElement(driver.findElement(By.xpath("//*[text()='Save']")));
	}
	
	public void click_req_save_update_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("save_update_btn"))));
	}
	
	public void click_req_saveButton_byclass(){
		clickElement(driver.findElement(By.className("btn-primary")));
	}
	
	public void click_role_minrate_savebtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_minrate_savebtn"))));
	}
	
	
	public void click_leave_this_page(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("leave_this_page"))));
	}
	
	public Boolean verify_leave_this_page(){
		return driver.findElement(By.xpath(mapping.getProperty("leave_this_page"))).isDisplayed();
	}
	
	public void click_save_technology()
	{
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_req_savetechnology"))));
	}
	
	public void click_submit_for_approval_byclass(){
		clickElement(driver.findElement(By.className("btn btn-primary btn-stretch submit-role col-sm-12 col-xs-12 ng-scope")));
	}
	
	public Boolean verify_display_submitmsg(){
		return driver.findElement(By.xpath(mapping.getProperty("msg_submit_role"))).getText().contains(mapping.getProperty("text_submit_role"));
	}
	
	public Boolean verify_submit_for_approval(){
		return driver.findElement(By.xpath("//*[text()='Submit for Approval']")).getAttribute("ng-disabled") == "!isSubmitEnabled()";
	}
	
	public Boolean verify_submit_for_approvalbtn(){
		return driver.findElement(By.xpath("//*[text()='Submit for Approval']")).isEnabled();
	}
	
	public void click_ok_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("ok_btn"))));
	}
	
	//classification

	public Boolean verify_display_cla_question01(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_cla_question01"))).isDisplayed();
	}
	
	public Boolean verify_display_cla_question02(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_cla_question02"))).isDisplayed();
	}
	
	public Boolean verify_display_cla_question03(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_cla_question03"))).isDisplayed();
	}
	
	public Boolean verify_display_cla_question04(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_cla_question04"))).isDisplayed();
	}
	
	public Boolean verify_display_cla_question05(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_cla_question05"))).isDisplayed();
	}
	
	public Boolean verify_exist_cla_question04(){	
		return isObjectExist(mapping.getProperty("creScr_cla_question04"));
    }
	
	public Boolean verify_exist_cla_question05(){	
		return isObjectExist(mapping.getProperty("creScr_cla_question05"));
    }
	
	public void click_creScr_cla_question01_yes(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_cla_question01_yes"))));
	}
	
	public void click_creScr_cla_question01_no(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_cla_question01_no"))));
	}
	
	public void click_creScr_cla_question02_yes(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_cla_question02_yes"))));
	}
	
	public void click_creScr_cla_question02_no(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_cla_question02_no"))));
	}
	
	public void click_creScr_cla_question03_yes(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_cla_question03_yes"))));
	}
	
	public void click_creScr_cla_question03_no(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_cla_question03_no"))));
	}
	
	public void click_creScr_cla_question04_yes(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_cla_question04_yes"))));
	}
	
	public void click_creScr_cla_question04_no(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_cla_question04_no"))));
	}
	
	public void click_creScr_cla_question05_yes(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_cla_question05_yes"))));
	}
	
	public void click_creScr_cla_question05_no(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_cla_question05_no"))));
	}
	
	public Boolean verify_role_classification_type(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("role_claasification_type"))).getText().contains(str);
    }
	
	public Boolean verify_tick_color_cla(){	
		if (driver.findElement(By.xpath(mapping.getProperty("creScr_cla_tick"))).getCssValue("color").equals("rgba(122, 198, 67, 1)"))
		{
			return true;
		}
		else
    		return false;
    }
	
	public void click_cla_saveButton(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_cla_saveButton"))));
	}
	
	//rt & reporting
	
	public Boolean verify_tab_routing_reporting(){	
		return driver.findElement(By.xpath(mapping.getProperty("tab_routing_reporting"))).isDisplayed();
    }
	
	public Boolean verify_display_rt_HRLeader(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_rt_HRLeader"))).isDisplayed();
	}
	
	//mobile
	public Boolean android_verify_display_rt_HRLeader(){	
		return driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_HRLeader"))).isDisplayed();
	}
	//mobile
	
	public Boolean verify_display_rt_EngagementLeader(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_rt_EngagementLeader"))).isDisplayed();
	}
	
	public Boolean verify_display_text_competency(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_rt_competency_text"))).isDisplayed();
	}
	
	public Boolean verify_display_data_competency(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_rt_competency_data"))).getText().equals(str);
	}
	
	//mobile
	public Boolean android_verify_display_rt_competency(){	
		return driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_competency"))).isDisplayed();
	}
	//mobile
	
	public Boolean verify_display_text_vertical(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_rt_vertical_text"))).isDisplayed();
	}
	
	public Boolean verify_display_data_vertical(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_rt_vertical_data"))).getText().equals(str);
	}
	
	//mobile
	public Boolean android_verify_display_rt_vertical(){	
		return driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_vertical"))).isDisplayed();
	}
	//mobile
	
	public Boolean verify_display_text_sector(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_rt_sector_text"))).isDisplayed();
	}
	
	public Boolean verify_display_data_sector(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_rt_sector_data"))).getText().equals(str);
	}
	
	//mobile
	public Boolean android_verify_display_rt_sector(){	
		return driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_sector"))).isDisplayed();
	}
	//mobile
	
	public Boolean verify_display_text_lineOfservice(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_rt_lineOfservice_text"))).isDisplayed();
	}
	
	public Boolean verify_display_data_lineOfservice(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_rt_lineOfservice_data"))).getText().equals(str);
	}
	
	//mobile
	public Boolean android_verify_display_rt_lineOfservice(){	
		return driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_lineOfservice"))).isDisplayed();
	}	
	//mobile
	
	public Boolean verify_display_text_territory(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_rt_territory_text"))).isDisplayed();
	}
	
	public Boolean verify_display_data_territory(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_rt_territory_data"))).getText().equals(str);
	}
	
	//mobile
	public Boolean android_verify_display_rt_territory(){	
		return driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_territory"))).isDisplayed();
	}
	//mobile
	
	public void click_rt_HRLeader(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_rt_HRLeader"))));
	}
	
	public void select_rt_HRLeader(String str){
		selectDropDownByText(mapping.getProperty("creScr_rt_HRLeader_opt"),str);
	}
	
	
	public void click_rr_vertical(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_rr_vertical"))));
	}
	
	public void click_clone_vertical_item(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("clone_vertical_item"))));
	}
	
	public void click_clone_vertical(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("clone_vertical_id"))));
	}
	
	public void input_clone_arm(String str){
		clearAndTypeString(driver.findElement(By.id(mapping.getProperty("clone_arm_id"))), str);
	}
	
	public void input_clone_engagement_leader(String str){
		clearAndTypeString(driver.findElement(By.id(mapping.getProperty("engagement_leader_id"))), str);
	}
	
	public void select_rr_verticalopt(String str){
		selectDropDownByText(mapping.getProperty("creScr_rr_verticalopt"),str);
	}
	
	//mobile
	public void android_click_rt_HRLeader(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_HRLeader"))));
	}
	
	public void android_select_rt_HRLeader(String stropt){
		selectDropDownByVisableText(driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_HRLeader"))), stropt);
	}
	//mobile
	
	public void input_rt_EngagementLeader(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("creScr_rt_EngagementLeader"))),usrStr);
	}
	
	public Boolean verify_error_EngagementLeader_required(){
		return driver.findElement(By.xpath(mapping.getProperty("creScr_EngagementLeader_required"))).isDisplayed();
	}
	
	public void click_rt_competency(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_rt_competency"))));
	}
	
	public void select_rt_competency(String str){
		selectDropDownByText(mapping.getProperty("creScr_rt_competency_opt"),str);
	}
	
	//mobile
	public void android_click_rt_competency(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_competency"))));
	}
	
	public void android_select_rt_competency(String stropt){
		selectDropDownByVisableText(driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_competency"))), stropt);
	}
	//mobile
	
	public Boolean verify_err_rt_competency(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_rt_competencyErr"))).getText().contains("Nothing has been selected");
	}
	
	public void click_rt_vertical(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_rt_vertical"))));
	}
	
	public void select_rt_vertical(String str){
		selectDropDownByText(mapping.getProperty("creScr_rt_vertical_opt"),str);
	}
	
	//mobile
	public void android_click_rt_vertical(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_vertical"))));
	}
	
	public void android_select_rt_vertical(String stropt){
		selectDropDownByVisableText(driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_vertical"))), stropt);
	}
	//mobile
	
	public void click_rt_sector(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_rt_sector"))));
	}

	public void select_rt_sector(String stropt){
		selectDropDownByText(mapping.getProperty("creScr_rt_sector_opt"), stropt);
	}
	
	//mobile
	public void android_click_rt_sector(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_sector"))));
	}
	
	public void android_select_rt_sector(String stropt){
		selectDropDownByVisableText(driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_sector"))), stropt);
	}
	//mobile
	
	public void click_rt_lineOfservice(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_rt_lineOfservice"))));
	}
	
	public void select_rt_lineOfservice(String stropt){
		selectDropDownByText(mapping.getProperty("creScr_rt_lineOfservice_opt"), stropt);
	}
	
	//mobile
	public void android_click_rt_lineOfservice(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_lineOfservice"))));
	}
	
	public void android_select_rt_lineOfservice(String stropt){
		selectDropDownByVisableText(driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_lineOfservice"))), stropt);
	}
	//mobile
	
	public void click_rt_territory(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_rt_territory"))));
	}
	
	public void select_rt_territory(String stropt){
		selectDropDownByText(mapping.getProperty("creScr_rt_territory_opt"), stropt);
	}
	
	//mobile
	public void android_click_rt_territory(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_territory"))));
	}
	
	public void android_select_rt_territory(String stropt){
		selectDropDownByVisableText(driver.findElement(By.xpath(mapping.getProperty("m_creScr_rt_territory"))), stropt);
	}
	//mobile
	
	public void click_rt_saveButton(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_rt_saveButton"))));
	}
	
	public Boolean verify_creScr_pricing_qu(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_pricing_qu"))).isDisplayed();
	}
	
	//matching event setup
	public void click_pricing_yes(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_pricing_yes"))));
	}
	
	public void click_pricing_no(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_pricing_no"))));
	}
	
	public Boolean verify_tab_matching_event(){	
		return driver.findElement(By.xpath(mapping.getProperty("tab_matching_event"))).isDisplayed();
	}
	
	public void click_pricing_saveButton(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("creScr_pricing_saveButton"))));
	}
	
	public Boolean verify_error_pricing_required(){	
		return driver.findElement(By.xpath(mapping.getProperty("creScr_pricing_required"))).isDisplayed();
	}

	public Boolean verify_msg_dashboard(){	
		return driver.findElement(By.xpath(mapping.getProperty("msg_dashboard"))).getText().contains(mapping.getProperty("msg_dash"));
    }
	
	public Boolean verify_title_match_talent(){	
		return driver.findElement(By.xpath(mapping.getProperty("match_num"))).isDisplayed();
    }
	
	public Boolean verify_match_num(){
		String str[] = driver.findElement(By.xpath(mapping.getProperty("match_num"))).getText().split(" ");
		if (Integer.parseInt(str[0]) >= 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Boolean verify_match_num_zero(){
		String str[] = driver.findElement(By.xpath(mapping.getProperty("match_num"))).getText().split(" ");
		if (Integer.parseInt(str[0]) == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Boolean verify_match_talent_num(){	
		String bool = driver.findElement(By.xpath(mapping.getProperty("title_match_talent"))).getText().substring(17, 18);
		if (bool != "0" | bool != "1" | bool != "2" | bool != "3" |bool != "4" | bool != "5" | bool != "6" | bool != "7" | bool != "8" | bool != "9")
		{
			int num= Integer.parseInt(driver.findElement(By.xpath(mapping.getProperty("title_match_talent"))).getText().substring(16, 17));
			if (num >=0 && num<=9)
			{
				return true;
			}
			else
				return false;
		}
		else
		{
			int num= Integer.parseInt(driver.findElement(By.xpath(mapping.getProperty("title_match_talent"))).getText().substring(16, 18));
			if (num >=0 && num<=99)
			{
				return true;
			}
			else
				return false;
		}
    }
	
	public Boolean verify_match_talent_num_zero(){
		int num= Integer.parseInt(driver.findElement(By.xpath(mapping.getProperty("title_match_talent"))).getText().substring(16, 17));
		if (num == 0)
		{
			return true;
		}
		else
			return false;
	}
	
	public void click_match_view_all(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("match_view_all"))));
	}
	
	//mobile
	public void android_click_match_view_all(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_match_view_all"))));
	}
	//mobile
	
	public Boolean verify_display_talent_frame_01(){	
		return driver.findElement(By.xpath(mapping.getProperty("talent_frame_01"))).isDisplayed();
    }
	
	public Boolean verify_display_talent_add_01(){	
		return driver.findElement(By.xpath(mapping.getProperty("talent_add_01"))).isDisplayed();
    }
	
	public Boolean verify_display_talent_image_01(){	
		return driver.findElement(By.xpath(mapping.getProperty("talent_image_01"))).isDisplayed();
    }
	
	public Boolean verify_display_talent_name_01(){	
		return driver.findElement(By.xpath(mapping.getProperty("talent_name_01"))).isDisplayed();
    }
	
	public Boolean verify_display_talent_position_01(){	
		return driver.findElement(By.xpath(mapping.getProperty("talent_position_01"))).isDisplayed();
    }
	
	public Boolean verify_display_talent_location_01(){	
		return driver.findElement(By.xpath(mapping.getProperty("talent_location_01"))).isDisplayed();
    }
	
	public Boolean verify_display_talent_fit_score_01(){	
		return driver.findElement(By.xpath(mapping.getProperty("talent_fit_score_01"))).isDisplayed();
    }
	
	public Boolean verify_display_talent_rate_01(){	
		return driver.findElement(By.xpath(mapping.getProperty("talent_rate_01"))).isDisplayed();
    }
	
	public Boolean verify_display_talent_star_01(){	
		return driver.findElement(By.xpath(mapping.getProperty("talent_star_01"))).isDisplayed();
    }
	
	public Boolean verify_display_talent_preferred_rate_01(){	
		return driver.findElement(By.xpath(mapping.getProperty("talent_preferred_rate_01"))).isDisplayed();
    }
	
	public Boolean verify_display_talent_availability_01(){	
		return driver.findElement(By.xpath(mapping.getProperty("talent_availability_01"))).isDisplayed();
    }
	
	public Boolean verify_location_not_provided(){	
		return driver.findElement(By.xpath(mapping.getProperty("location_not_provided"))).getText().toString().contains("Not Provided");
    }
	
	public Boolean verify_wbscode_not_provided(){	
		return driver.findElement(By.xpath(mapping.getProperty("wbscode_not_provided"))).getText().toString().contains("Not Provided");
    }
	
	public Boolean verify_start_date_not_provided(){	
		return driver.findElement(By.xpath(mapping.getProperty("start_date_not_provided"))).getText().toString().contains("Not Provided");
    }
	
	public Boolean verify_travel_requirement_not_provided(){	
		return driver.findElement(By.xpath(mapping.getProperty("travel_requirement_not_provided"))).getText().toString().contains("Not Provided");
    }
	
	public Boolean verify_role_summary_not_provided(){	
		return driver.findElement(By.xpath(mapping.getProperty("role_summary_not_provided"))).getText().toString().contains("Not Provided");
    }
	
	public Boolean verify_role_description_not_provided(){	
		return driver.findElement(By.xpath(mapping.getProperty("role_description_not_provided"))).getText().toString().contains("Not Provided");
    }
	
	public Boolean verify_project_name_not_provided(){	
		return driver.findElement(By.xpath(mapping.getProperty("project_name_not_provided"))).getText().toString().contains("Not Provided");
    }
	
	public Boolean verify_engagement_vertical_not_provided(){	
		return driver.findElement(By.xpath(mapping.getProperty("engagement_vertical_not_provided"))).getText().toString().contains("Not Provided");
    }
	
	public Boolean verify_engagement_leader_not_provided(){	
		return driver.findElement(By.xpath(mapping.getProperty("engagement_leader_not_provided"))).getText().toString().contains("Not Provided");
    }
	
}
