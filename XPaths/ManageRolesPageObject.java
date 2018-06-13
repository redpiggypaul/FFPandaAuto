package com.rex.autotest.pageObjects;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.rex.autotest.utilities.PropUtils;
import com.rex.autotest.utilities.Utils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;

public class ManageRolesPageObject extends BasePageObject {
	private  static final String PROP_FILE =  System.getProperty("user.dir")+"/src/com/rex/autotest/uimapping/ManageRoles.properties";
	Properties mapping = PropUtils.getProperties(PROP_FILE);

	public ManageRolesPageObject(WebDriver driver) {
		this.driver = driver;
		//if(!driver.getTitle().contains("Talent Exchange")){
		//	logger.info("REX-Manage Roles page is not loaded");
		//	throw new IllegalStateException("REX-Manage Roles page is not loaded");
		//}
	}
	
	public void clickElementByxPath(String xPath)
	{
		clickElement(driver.findElement(By.xpath(mapping.getProperty(xPath))));
	}
	
	public void clickElementByPath(String path)
	{
		clickElement(driver.findElement(By.xpath(path)));
	}
	
	public String getElementAttribute(String xPath, String attribute)
	{
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getAttribute(attribute).toString();
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
	
	public Boolean verifyElementValue(String xPath, String str){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getText().toString().toUpperCase().contains(str.toUpperCase());
	}
	
	public String getElementValueByxPath(String xPath){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getText().toString();
	}
	
	public Boolean verifyUpdatedValue(String str, String str1){
		return (str.contains(str1)==false);
	}
	
	public Boolean verifyElementEnable(String xPath){
//		try{
//		     return driver.findElement(By.xpath(mapping.getProperty(xPath))).isDisplayed();
//		     return isObjectExist("");
//		}
//		catch(NoSuchElementException e)
//		{ 
//			return true;
//		}
		return isObjectExist(mapping.getProperty(xPath));
	}
	
	public Boolean verifyElementEnableByPath(String xPath){
		return isObjectExist(xPath);
	}
	
	public void input_max_price(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("max_price"))),usrStr);
	}
	
	public void click_btn_clone(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("btn_clone_role"))));
	}
	
	public void click_publish_clone(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("published_clone_role"))));
	}
	
	public void click_clone_role_em(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("clone_role_id"))));
	}
	
	public void click_btn_close_role(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("btn_close_role"))));
	}
	
	public void click_arm_delete_this_draft(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("arm_delete_this_role"))));
	}
	
	public void click_delete_this_draft(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("delete_this_draft"))));
	}
	
	public void click_delete_this_draft_yes(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("delete_this_draft_yes"))));
	}
	
	public void click_arm_delete_this_role_yes(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("arm_delete_this_role_yes"))));
	}
	
	public Boolean verify_delete_draft_role(){
		return driver.findElement(By.xpath(mapping.getProperty("delete_draft_role_popup"))).isDisplayed();
	}
	
	public void check_close_reason2(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("close_reason2"))));
	}
	
	//
	public void click_setup_pebtn(){
		if(driver.findElement(By.xpath(mapping.getProperty("setup_pebtn"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("setup_pebtn"))));
		}
	}
	
	public Boolean check_setup_pebtn(){
		return driver.findElement(By.xpath(mapping.getProperty("setup_pebtn"))).isEnabled();
	}
	
	public Boolean check_arm_setup_pebtn(){
		return driver.findElement(By.xpath(mapping.getProperty("arm_setup_pebtn"))).isEnabled();
	}
	
	public void click_pe_tab(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("pe_tab"))));
	}
	
	public void click_setup_new_pebtn(){
			clickElement(driver.findElement(By.xpath("//*[text()='Set Up Pricing Event']")));
	}
	
	public Boolean verify_popup_dialog_title(){	
		return driver.findElement(By.xpath(mapping.getProperty("pes_dialog"))).getText().toUpperCase().contains("Pricing Event Setup".toUpperCase());
	}
	
	public void input_beeline_id(String usrStr){
		clearAndTypeString(driver.findElement(By.id(mapping.getProperty("beelineid_id"))),usrStr);
	}
	
	public Boolean verify_beeline_id_before(String str){	
		if(driver.findElement(By.xpath(mapping.getProperty("beeline_id"))).isDisplayed()==false)
		{
			Utils.sleep(1000);
		}
		return driver.findElement(By.xpath(mapping.getProperty("beeline_id"))).getText().toUpperCase().contains(str.toUpperCase());
	}
	
	public String get_beeline_id_value(){	
		return driver.findElement(By.xpath(mapping.getProperty("beeline_id"))).getText().toString();
	}
	
	public Boolean verify_beeline_id_after(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("beeline_id"))).getText().toUpperCase().contains(str.toUpperCase());
	}
	
	public Boolean verify_updated_beeline_id(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("beeline_id"))).getText().toUpperCase().contains(str.toUpperCase());
	}
	
	public void click_pestart_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("pestart_date"))));
	}
	
	public void select_pestart_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("peselect_start_date"))));
	}
	
	public void click_pestart_time(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("pestart_time"))));
	}
	
	public void select_pestart_time(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("peselect_start_time"))));
	}
	
	public void click_peend_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("peend_date"))));
	}
	
	public void select_peend_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("peselect_end_date"))));
	}
	
	public void click_peend_time(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("peend_time"))));
	}
	
	public void select_peend_time(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_peend_time"))));
	}
	
	public void click_pesave_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("pesave_btn"))));
	}
	
	public void check_petalent01(){
		clickElement(driver.findElement(By.id(mapping.getProperty("pe_choose_talent1id"))));
	}
	
	public void check_petalent02(){
		clickElement(driver.findElement(By.id(mapping.getProperty("pe_choose_talent2id"))));
	}
	
	public void click_set_up_event(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("set_up_event"))));
	}
	
	public void click_save_beeline_btn(){
		//clickElement(driver.findElement(By.xpath(mapping.getProperty("save_beeiline_btn"))));
		clickElement(driver.findElement(By.xpath("//*[contains(text(), 'Save')]")));
	}
	
	public void click_pecancel_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("pescancel_btn"))));
	}
	
	public void click_popup_ok(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("popup_ok"))));
	}
	
	public void click_popup_cancel(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("popup_cancel"))));
	}
	
	public Boolean verify_popup_title(){	
		return driver.findElement(By.xpath(mapping.getProperty("popup_title"))).getText().equals("Close Role");
	}
	
	public Boolean verify_rate_range(){	
		return driver.findElement(By.xpath(mapping.getProperty("rate_range"))).getText().toUpperCase().contains(mapping.getProperty("rate_range_txt").toUpperCase());
	}
	
	public Boolean verify_send_offer_page(){	
		return driver.findElement(By.xpath(mapping.getProperty("send_offer_page"))).getText().toUpperCase().contains(mapping.getProperty("send_offer_txt"));
	}
	
	public String get_offer_sent_no(){
		return driver.findElement(By.xpath(mapping.getProperty("offer_sent_no"))).getText().toString();
	}
	
	public String get_shortlist_number(){
		return driver.findElement(By.xpath(mapping.getProperty("shortlist_number"))).getText().toString();
	}
	
	public Boolean verify_offer_sent_no(String str)
	{
		return driver.findElement(By.xpath(mapping.getProperty("offer_sent_no"))).getText().toString().contains(str);
	}
	
	public void input_offer_rate(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("offer_rate"))),usrStr);
	}
	
	public void input_check_in_location(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("check_in_location"))),usrStr);
	}
	
	public void click_offer_rate_savebtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("offer_rate_savebtn"))));
	}
	
	public void click_offer_accepted_tab(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("offer_accepted_tab"))));
	}
	
	public void click_offer_accepted_dropdown(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("offer_accepted_dropdown"))));
	}
	
	public void click_offer_start_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("offer_start_date"))));
	}
	
	public void select_offer_start_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_start_date"))));
	}
	
	public void click_offer_start_time(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("offer_start_time"))));
	}
	
	public void click_prepareoffer_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("prepare_offer_btn"))));
		//clickElement(driver.findElement(By.xpath("//*[text()='Prepare Offer']")));
	}
	
	public void click_em_prepare_offer_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("em_prepare_offer_btn"))));
	}
	
	public void click_el_prepare_offer_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("el_prepareoffer_btn"))));
	}
	
	public void click_arm_prepare_offer_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("arm_prepare_offer_btn"))));
	}
	
	public void click_canceloffer_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("cancel_offerbtn"))));
	}
	
	public void click_send_offerbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("send_offerbtn"))));
	}
	
	public void select_offer_start_time(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_start_time"))));
	}
	
	public Boolean verify_billing_classification(){	
		return driver.findElement(By.xpath(mapping.getProperty("billing_classification"))).getText().toUpperCase() != "";
	}
	
	public Boolean verify_location(){	
		return driver.findElement(By.xpath(mapping.getProperty("role_location"))).getText().toUpperCase() !="";
	}
	
	public String get_travel_requirement(){
		return driver.findElement(By.xpath(mapping.getProperty("travel_requirements"))).getText().toString();
	}
	
	public String get_role_people_number(){
		return driver.findElement(By.xpath(mapping.getProperty("role_people_number"))).getText().toString();
	}
	
	public String get_people_needed_txt(){
		return driver.findElement(By.xpath(mapping.getProperty("people_needed_txt"))).getText().toString();
	}
	
	public String get_people_number(){
		return driver.findElement(By.xpath(mapping.getProperty("people_number"))).getText().toString();
	}
	
	public String get_people_on_project_number(){
		return driver.findElement(By.xpath(mapping.getProperty("people_on_project_number"))).getText().toString();
	}
	
	public String get_people_on_project_txt(){
		return driver.findElement(By.xpath(mapping.getProperty("people_on_project_txt"))).getText().toString();
	}
	
	public Boolean verify_people_on_project_txt(){
		return driver.findElement(By.xpath(mapping.getProperty("people_on_project_txt"))).getText().toUpperCase().contains("people on-project".toUpperCase());
	}
	
	public String get_match_for_role_name(){
		return driver.findElement(By.xpath(mapping.getProperty("match_for_role_name"))).getText().toString();
	}
	
	public String get_role_detail_name(){
		return driver.findElement(By.xpath(mapping.getProperty("role_detail_name"))).getText().toString();
	}
	
	public void click_find_talent_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("find_talent_btn"))));
		Utils.sleep(3000);
	}
	
	public void click_admin_find_talent_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("admin_find_talentbtn"))));
	}
	
	public void click_offer_sent_dropdown(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("offer_sent_dropdown"))));
	}
	
	public Boolean verify_offer_sent_accepted(){
		return driver.findElement(By.xpath(mapping.getProperty("offer_sent_accepted"))).getText().toUpperCase().contains("ACCEPTED");
	}
	
	public Boolean verify_offer_sent_pending(){
		return driver.findElement(By.xpath(mapping.getProperty("offer_sent_pending"))).getText().toUpperCase().contains("PENDING");
	}
	
	public Boolean verify_offer_sent_declined(){
		return driver.findElement(By.xpath(mapping.getProperty("offer_sent_declined"))).getText().toUpperCase().contains("DECLINED");
	}
	
	public Boolean verify_offer_sent_expired(){
		return driver.findElement(By.xpath(mapping.getProperty("offer_sent_expired"))).getText().toUpperCase().contains("EXPIRED");
	}
	
	public Boolean verify_match_for_role_name(String str){
		return driver.findElement(By.xpath(mapping.getProperty("match_for_role_name"))).getText().toUpperCase().contains(str);
	}
	
	public Boolean verify_offer_accepted_onboard(){
		return driver.findElement(By.xpath(mapping.getProperty("offer_accepted_onboard"))).getText().toUpperCase().contains("ON-BOARD");
	}
	
	public Boolean verify_offer_accepted_onproject(){
		return driver.findElement(By.xpath(mapping.getProperty("offer_accepted_onproject"))).getText().toUpperCase().contains("ON-PROJECT");
	}
	
	public Boolean verify_offer_accepted_offboarded(){
		return driver.findElement(By.xpath(mapping.getProperty("offer_accepted_offboarded"))).getText().toUpperCase().contains("OFF-BOARDED");
	}
	
	public Boolean verify_find_talent_btn(){
		return driver.findElement(By.xpath(mapping.getProperty("find_talent_btn"))).isDisplayed();
	}
	
	public Boolean verify_required_skill(){	
		return driver.findElement(By.xpath(mapping.getProperty("required_skill"))).getText().toUpperCase()!="";
	}
	
	public Boolean verify_offer_sent_tab(){
		if(isObjectExist("error_okbtn"))
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("error_okbtn"))));
			Utils.sleep(2000);
			 return driver.findElement(By.xpath(mapping.getProperty("offer_sent_tab"))).isDisplayed();
		}
		else
		{
		 return driver.findElement(By.xpath(mapping.getProperty("offer_sent_tab"))).isDisplayed();
		}
	}
	
	public Boolean verify_offer_sent_tab_txt(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("offer_sent_tab"))).getText().toUpperCase().contains("ON PROJECT");
	}
	
	public Boolean verify_offer_accepted_tab(){	
		return driver.findElement(By.xpath(mapping.getProperty("offer_accepted_tab"))).isDisplayed();
	}
	
	public Boolean verify_offer_accepted_tab_txt(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("offer_accepted_tab"))).getText().toUpperCase().contains("OFFERS & ON-BOARDING");
	}
	
	public Boolean verify_shortlisted_tab(){	
		return driver.findElement(By.xpath(mapping.getProperty("shortlisted_tab"))).isDisplayed();
	}
	
	public Boolean verify_shortlisted_tab_txt(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("shortlisted_tab"))).getText().toUpperCase().contains("SHORTLISTED");
	}
	
	public Boolean verify_expressed_interest_tab(){	
		return driver.findElement(By.xpath(mapping.getProperty("expressed_interest_tab"))).isDisplayed();
	}
	
	public Boolean verify_expressed_interest_tab_txt(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("expressed_interest_tab"))).getText().toUpperCase().contains("SHOWN INTEREST");
	}
	
	public Boolean verify_so_talent_name(){	
		return driver.findElement(By.xpath(mapping.getProperty("talent_name"))).getText().toUpperCase()!="";
	}
	
	public Boolean verify_so_talent_title(){	
		return driver.findElement(By.xpath(mapping.getProperty("talent_title"))).getText().toUpperCase()!="";
	}
	
	public Boolean verify_so_talent_login(){	
		return driver.findElement(By.xpath(mapping.getProperty("talent_login"))).getText().contains("2016");
	}
	
	public Boolean verify_so_preferred_rate_txt(){	
		return driver.findElement(By.xpath(mapping.getProperty("preferred_rate_txt"))).getText().contains("PREFERRED RATE");
	}
	
	public Boolean verify_so_submitted_rate_txt(){	
		return driver.findElement(By.xpath(mapping.getProperty("submitted_rate_txt"))).getText().contains("OFFERED RATE");
	}
	
	public Boolean verify_so_offer_status_txt(){	
		return driver.findElement(By.xpath(mapping.getProperty("offer_status_txt"))).getText().contains("OFFER STATUS");
	}
	
	public Boolean verify_so_preferred_rate_value(){	
		return driver.findElement(By.xpath(mapping.getProperty("preferred_rate_value"))).getText() !="";
	}
	
	public Boolean verify_so_talent_location(){	
		return driver.findElement(By.xpath(mapping.getProperty("talent_location"))).getText()!="";
	}
	
	public Boolean verify_so_submitted_rate_value(){	
		return driver.findElement(By.xpath(mapping.getProperty("submitted_rate_value"))).getText() != "";
	}
	
	public Boolean verify_so_offer_status_value(){	
		return driver.findElement(By.xpath(mapping.getProperty("offer_status_value"))).getText().toUpperCase().contains("OFFER");
	}
	
	public Boolean verify_years_of_experience(){	
		return driver.findElement(By.xpath(mapping.getProperty("years_of_experience"))).getText().toUpperCase() != "";
	}
	//
	
	
	public Boolean verify_oa_talent_name(){	
		return driver.findElement(By.xpath(mapping.getProperty("oa_talent_name"))).getText().toUpperCase()!="";
	}
	
	public Boolean verify_oa_talent_title(){	
		return driver.findElement(By.xpath(mapping.getProperty("oa_talent_title"))).isDisplayed();
	}
	
	public Boolean verify_oa_talent_login(){	
		return driver.findElement(By.xpath(mapping.getProperty("oa_talent_login"))).getText().contains("2016");
	}
	
	public Boolean verify_oa_preferred_rate(){	
		return driver.findElement(By.xpath(mapping.getProperty("oa_preferred_rate"))).getText().contains("PREFERRED RATE");
	}
	
	public Boolean verify_oa_offered_rate(){	
		return driver.findElement(By.xpath(mapping.getProperty("oa_offered_rate"))).getText().contains("OFFERED RATE");
	}
	
	public Boolean verify_oa_offer_status(){	
		return driver.findElement(By.xpath(mapping.getProperty("oa_offer_status"))).getText().contains("STATUS");
	}
	
	public Boolean verify_oa_preferred_rate_value(){	
		return driver.findElement(By.xpath(mapping.getProperty("oa_preferred_rate_value"))).getText() != "";
	}
	
	public Boolean verify_oa_talent_location(){	
		return driver.findElement(By.xpath(mapping.getProperty("oa_talent_location"))).getText()!="";
	}
	
	public Boolean verify_oa_offered_rate_value(){	
		return driver.findElement(By.xpath(mapping.getProperty("oa_offered_rate_value"))).getText().contains("$") || driver.findElement(By.xpath(mapping.getProperty("oa_offered_rate_value"))).getText() !="";
	}
	
	public Boolean verify_oa_offer_status_value(){	
		return driver.findElement(By.xpath(mapping.getProperty("oa_offer_status_value"))).getText().toUpperCase().contains("ON-BOARDING") || driver.findElement(By.xpath(mapping.getProperty("oa_offer_status_value"))).getText().toUpperCase().contains("OFFER PENDING");
	}
	
	//shortlisted tab
	
	public Boolean verify_shortlisted_talent_name(){	
		return driver.findElement(By.xpath(mapping.getProperty("shortlisted_talent_name"))).getText().toUpperCase()!="";
	}
	
	public Boolean verify_shortlisted_talent_title(){	
		return driver.findElement(By.xpath(mapping.getProperty("shortlisted_talent_title"))).getText().toUpperCase()!="";
	}
	
	public Boolean verify_shortlisted_talent_login(){	
		return driver.findElement(By.xpath(mapping.getProperty("shortlisted_talent_login"))).getText().contains("2016");
	}
	
	public Boolean verify_shortlisted_talent_preferred_rate(){	
		return driver.findElement(By.xpath(mapping.getProperty("shortlisted_talent_preferred_rate"))).getText().contains("PREFERRED RATE");
	}
	
	public Boolean verify_shortlisted_talent_preferred_rate_value(){	
		return driver.findElement(By.xpath(mapping.getProperty("shortlisted_talent_preferred_rate_value"))).getText() != "";
	}
	
	public Boolean verify_shortlisted_talent_availability(){	
		return driver.findElement(By.xpath(mapping.getProperty("shortlisted_talent_availability"))).getText().toUpperCase().contains("AVAILABILITY");
	}
	
	public Boolean verify_shortlisted_talent_availability_value(){	
		return driver.findElement(By.xpath(mapping.getProperty("shortlisted_talent_availability_value"))).getText() != "";
	}
	
	public Boolean verify_shortlisted_talent_location(){	
		return driver.findElement(By.xpath(mapping.getProperty("shortlisted_talent_location"))).getText()!="";
	}
	
	public void click_shortlisted_action_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("shortlist_action_btn"))));
	}
	
	public Boolean verify_shortlisted_prepare_offer_btn(){	
		return driver.findElement(By.xpath(mapping.getProperty("shortlisted_prepare_offer_btn"))).getText().toUpperCase().contains("PREPARE OFFER");
	}
	
	//Expressed Interest tab
	
		public Boolean verify_ei_talent_name(){	
			return driver.findElement(By.xpath(mapping.getProperty("ei_talent_name"))).getText().toUpperCase()!="";
		}
		
		public Boolean verify_ei_talent_title(){	
			return driver.findElement(By.xpath(mapping.getProperty("ei_talent_title"))).getText().toUpperCase()!="";
		}
		
		public Boolean verify_ei_talent_login(){	
			return driver.findElement(By.xpath(mapping.getProperty("ei_talent_login"))).getText().contains("2016");
		}
		
		public Boolean verify_ei_preferred_rate(){	
			return driver.findElement(By.xpath(mapping.getProperty("ei_preferred_rate"))).getText().contains("PREFERRED RATE");
		}
		
		public Boolean verify_ei_talent_preferred_rate_value(){	
			return driver.findElement(By.xpath(mapping.getProperty("ei_talent_preferred_rate_value"))).getText().toString() !="";
		}
		
		public Boolean verify_ei_talent_availability(){	
			return driver.findElement(By.xpath(mapping.getProperty("ei_talent_availability"))).getText().toUpperCase().contains("AVAILABILITY");
		}
		
		public Boolean verify_ei_talent_availability_value(){	
			return driver.findElement(By.xpath(mapping.getProperty("ei_talent_availability_value"))).getText().toUpperCase() != "";
		}
		
		public Boolean verify_ei_talent_location(){	
			return driver.findElement(By.xpath(mapping.getProperty("ei_talent_location"))).getText()!="";
		}
		
		public void click_ei_action_btn(){
			clickElement(driver.findElement(By.xpath(mapping.getProperty("ei_action_btn"))));
		}
		
		public Boolean verify_shortlist_to_role_btn(){	
			return driver.findElement(By.xpath(mapping.getProperty("shortlist_to_role_btn"))).getText().toUpperCase().contains("SHORTLIST TO ROLE");
		}
	
		public void click_expressed_interest_tab(){
			clickElement(driver.findElement(By.xpath(mapping.getProperty("expressed_interest_tab"))));
		}
		
		public String get_expressed_interestno(){
			return driver.findElement(By.xpath(mapping.getProperty("expressed_interest_no"))).getText().toString();
		}
		
	public Boolean verify_shortlisted_screen_btn(){	
		return driver.findElement(By.xpath(mapping.getProperty("shortlisted_screen_btn"))).getText().toUpperCase().contains("SCREEN");
	}
	
	public Boolean verify_shortlisted_remove_from_role_btn(){	
		return driver.findElement(By.xpath(mapping.getProperty("shortlisted_remove_from_role_btn"))).getText().toUpperCase().contains("REMOVE FROM ROLE");
	}
	
	public void click_edit_btn(){
		if(driver.findElement(By.xpath(mapping.getProperty("edit_btn"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("edit_btn"))));
		}
	}
	
	public void click_beeline_edit_btn(){
		if(driver.findElement(By.xpath(mapping.getProperty("beeline_edit_btn"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("beeline_edit_btn"))));
		}
	}
	
	public void click_close_role_btn(){
		if(driver.findElement(By.xpath(mapping.getProperty("close_role"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("close_role"))));
		}
	}
	
	public void click_close_btn(){
		if(driver.findElement(By.xpath(mapping.getProperty("close_btn"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("close_btn"))));
		}
	}
	
	public void click_remove_btn(){
		if(driver.findElement(By.xpath(mapping.getProperty("remove_talent"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("remove_talent"))));
		}
	}
	
	
	public void click_cancel_btn(){
		if(driver.findElement(By.xpath(mapping.getProperty("cancel_btn"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("cancel_btn"))));
		}
	}
	
	public Boolean verify_role_status(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("role_status"))).getText().toUpperCase().contains(str);
	}
	
	public Boolean verify_close_dialog(){	
		return driver.findElement(By.xpath(mapping.getProperty("close_role_dialog"))).getText().contains(mapping.getProperty("close_role_txt"));
	}
	
	public Boolean verify_header(){	
		return driver.findElement(By.xpath(mapping.getProperty("header"))).getText().toUpperCase().contains(mapping.getProperty("header_txt").toUpperCase());
	}
	
	public Boolean verify_role_name(){	
		return driver.findElement(By.xpath(mapping.getProperty("role_name"))).getText().toUpperCase() !="";
	}
	
	public Boolean verify_role_location(){	
		return driver.findElement(By.xpath(mapping.getProperty("roledetail_location"))).getText().toUpperCase().contains(mapping.getProperty("location_txt").toUpperCase());
	}
	
	public Boolean verify_represent_location(){	
		return driver.findElement(By.xpath(mapping.getProperty("represent_location"))).getText().toUpperCase().contains("NORTH CALIFORNIA");
	}
	
	public Boolean verify_wbscode(){	
		return driver.findElement(By.xpath(mapping.getProperty("wbscode"))).getText() != "";
	}	
	
	public Boolean verify_role_activity(){	
		return driver.findElement(By.xpath(mapping.getProperty("role_activity"))).getText().toUpperCase().contains(mapping.getProperty("role_activity_txt").toUpperCase());
	}
	
	public Boolean verify_edited_minbill(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("edited_minbill"))).getText().contains(str);
	}
	
	public Boolean verify_draft_status(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("draft_status"))).getText().contains(str);
	}

	public Boolean verify_editedrole_status(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("edited_status"))).getText().contains(str);
	}
	
	public Boolean verify_submit_role_status(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("submit_role_status"))).getText().toUpperCase().contains(str.toUpperCase());
	}
	
	public Boolean verify_draft_role_status(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("draft_role_status"))).getText().toUpperCase().contains(str.toUpperCase());
	}
	
	public Boolean verify_close_role_status(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("close_role_status"))).getText().toUpperCase().contains(str.toUpperCase());
	}
	
	public Boolean verify_tab_match_event(){	
		return driver.findElement(By.xpath(mapping.getProperty("tab_match_event"))).getText().equals("Matching Event Setup");
	}
	
	public Boolean verify_tab_role_details(){	
		return driver.findElement(By.xpath(mapping.getProperty("tab_role_details"))).isDisplayed();
	}
	
	public Boolean verify_tab_talent(){	
		return driver.findElement(By.xpath(mapping.getProperty("tab_talent"))).isDisplayed();
	}
	
	public void click_tab_role_details(){	
		clickElement(driver.findElement(By.xpath(mapping.getProperty("tab_role_details"))));
	}
	
	public void click_tab_talent(){	
		if(isObjectExist("error_okbtn"))
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("error_okbtn"))));
			Utils.sleep(2000);
			clickElement(driver.findElement(By.xpath(mapping.getProperty("tab_talent"))));
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("tab_talent"))));
		}
	}
	
	public void click_shortlisted_tab(){	
		if(driver.findElement(By.xpath(mapping.getProperty("shortlisted_tab"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("shortlisted_tab"))));
		}
	}
	
	public void click_action_btn(){
		if(driver.findElement(By.xpath(mapping.getProperty("action_btn"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("action_btn"))));
		}
	}
	
	public void click_shortlist_action_btn(){
		if(driver.findElement(By.xpath(mapping.getProperty("shortlist_action_btn"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("shortlist_action_btn"))));
		}
	}
	
	public void click_recruiter_shortlist_action_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("shortlist_remove_btn"))));
	}
	
	public void click_shortlist_remove_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("shortlist_remove_btn"))));
	}
	
	public void click_em_shortlist_remove_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("em_remove_from_role"))));
	}
	
	public void click_remove_shortlisted_talent(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("remove_shortlisted_talent"))));
	}
	
	public void click_el_remove_from_role(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("el_remove_from_role"))));
	}
	
	public void click_arm_remove_from_role(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("arm_remove_from_role"))));
	}
	
	public String get_arm_shortlist_talent_number(){
		return driver.findElement(By.xpath(mapping.getProperty("arm_shortlist_talent_number"))).getText().toString();
	}
	
	public void click_admin_remove_shortlisted_talent(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("admin_remove_shortlisted_talent"))));
	}
	
	public void click_recruiter_screen(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("recruiter_screen"))));
	}
	
	public void click_recruiter_remove_from_role(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("recruiter_remove_from_role"))));
	}
	
	public void click_recuriter_remove_oneinfive_talent(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("recuriter_remove_oneinfive_talent"))));
	}
	
	public void click_screener(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("click_screener"))));
	}
	
	//New Screen page
	public void select_screener(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_screener"))));
	}
	
	public void click_screen_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("screen_date"))));
	}
	
	public void select_screen_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_screen_date"))));
		Utils.sleep(2000);
		PressEnterKey();
	}
	
	public void click_screen_start_time(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("screen_start_time"))));
	}
	
	public void select_screen_start_time(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_screen_start_time"))));
	}
	
	public void click_screen_end_time(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("screen_end_time"))));
	}
	
	public void select_screen_end_time(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_screen_end_time"))));
	}
	
	public void click_send_invite(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("send_invite"))));
	}
	
	public void click_screen_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("screen_btn"))));
	}
	
	public void click_contact_bymail_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("contact_by_mail"))));
	}
	
	public void input_mail_subject(String str){
		clearAndTypeString(driver.findElement(By.id(mapping.getProperty("mail_subject_id"))), str);
	}
	
	public void input_mail_message(String str){
		clearAndTypeString(driver.findElement(By.id(mapping.getProperty("mail_message_id"))), str);
	}
	
	public void click_send_mail(){
		clickElement(driver.findElement(By.id(mapping.getProperty("send_mail_id"))));
	}
	
	public void click_screen_nowbtn(){
		clickElement(driver.findElement(By.xpath("//*[text()='Screen Talent Now']")));
	}
			
	//mobile
	public void android_click_tab_dropdown(){	
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_tab_dropdown"))));
	}
	
	public void android_click_tab_role_details(){	
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_tab_role_details"))));
	}
	
	public void android_click_tab_talent(){	
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_tab_talent"))));
	}
	//mobile
	
	public Boolean verify_text_role_summary(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("role_summary"))).getText().contains(str);
	}
	
	public Boolean verify_action_button(){	
		return driver.findElement(By.xpath(mapping.getProperty("action_button"))).isDisplayed();
	}
	
	public Boolean verify_text_role_description(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("role_description"))).getText().contains(str);
	}
	
	public Boolean verify_text_role_differentiators(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("role_differentiators"))).getText().contains(str);
	}
	
	public Boolean verify_text_role_skill01(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("role_skill01"))).getText().contains(str);
	}
	
	public Boolean verify_text_role_experience(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("role_experience"))).getText().contains(str);
	}
	
	public Boolean verify_title_manage_role(){	
		return driver.findElement(By.xpath(mapping.getProperty("title_manage_role"))).getText().equals("Manage Role");
	}
	
	public void click_find_talentbtn(){
		if(driver.findElement(By.xpath(mapping.getProperty("find_talent"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("find_talent"))));
		}	
	}

	public Boolean verify_savetorole_number(){
		return driver.findElement(By.xpath(mapping.getProperty("savetorole_number"))).getText().contains("1")||driver.findElement(By.xpath(mapping.getProperty("savetorole_number"))).getText().contains("2");
	}
	

	public Boolean verify_cancelsavetorole_number(){
		return driver.findElement(By.xpath(mapping.getProperty("cancelsavetorole_number"))).getText().contains("0");
	}
	
	
	public Boolean verify_shortlist_talent(String str){
		return driver.findElement(By.xpath(mapping.getProperty("talent_name"))).getText().toUpperCase().contains(str);
	}
	
	public Boolean verify_shortlist_talent01(String str){
		return driver.findElement(By.xpath(mapping.getProperty("shortlisted_talent_name01"))).getText().toUpperCase().contains(str);
	}
	
}
