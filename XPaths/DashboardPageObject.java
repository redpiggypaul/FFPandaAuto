package com.rex.autotest.pageObjects;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.rex.autotest.utilities.PropUtils;
import com.rex.autotest.utilities.Utils;


public class DashboardPageObject extends BasePageObject {
	private  static final String PROP_FILE =  System.getProperty("user.dir")+"/src/com/rex/autotest/uimapping/Dashboard.properties";
	Properties mapping = PropUtils.getProperties(PROP_FILE);
	
	public DashboardPageObject(WebDriver driver) {
		this.driver = driver;
		//if(!driver.getTitle().contains("Talent Exchange")){
		//	logger.info("REX-Dashboard page is not loaded");
		//	throw new IllegalStateException("REX-Dashboard page is not loaded");
		//}
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
	
	public void InputValueByxPath(String xPath, String txt)
	{
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty(xPath))), txt);
	}
	
	public Boolean verifyPage(String xPath, String str){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getText().toString().toUpperCase().contains(str.toUpperCase());
	}
	
	public Boolean verifyElement(String xPath, String str){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getText().toString().toUpperCase().contains(str.toUpperCase());
	}
	
	public Boolean verifyElementDisplayed(String xPath){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).isDisplayed();
	}
	
	public String getElementValueByxPath(String xPath){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getText().toString();
	}
	
	public Boolean verifyUpdatedValue(String str, String str1){
		return (str.contains(str1)==false);
	}
	
	public boolean IsElementPresent(String xPath) {
        try {
            driver.findElement(By.xpath(mapping.getProperty(xPath)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
	public void click_expressed_interest(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("tab_expressed_interest"))));
	}
	
	public void click_expressed_interest_role(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("expressed_interest_role"))));
	}
	
	public void click_complete_profile(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("complete_profile"))));
	}
	
	public void click_cancel_edit(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("cancel_edit"))));
	}
	
	public void click_goto_market(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("goto_market"))));
	}
	
	public void click_dashboard_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("dashboard_btn"))));
	}
	
	public void click_search_roles_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("search_roles"))));

	}
	
	public void m_click_search_roles(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_nav"))));
		Utils.sleep(2000);
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_search_roles"))));
	}
	
	public void click_mobile_dropdown(String xpath){
		clickElement(driver.findElement(By.xpath(mapping.getProperty(xpath))));
	}
	
	public void click_mobile_dropdown_item(String xpath){
		clickElement(driver.findElement(By.xpath(mapping.getProperty(xpath))));
	}
	
	public void click_mobile_button(String xpath){
		clickElement(driver.findElement(By.xpath(mapping.getProperty(xpath))));
	}
	
	public void scrollToView(String xpath){
   	 logger.info("Now we scroll the view to the position we can see");
        //AppiumHelper.getFocus(driver, e);
   	    WebElement e = driver.findElement(By.xpath(xpath));
   	 
        executeJS("window.scrollTo(0,"+e.getLocation().y+")");
        //executeJS("arguments[0].scrollIntoView(true);", e);
    }
	
	public void input_mobile_textfield(String xpath, String str){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty(xpath))), str);
	}
	
	public String get_mobile_value(String xpath){
		 return driver.findElement(By.xpath(mapping.getProperty(xpath))).getText().toString();
	}
	
	public void click_market_arrow(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("market_arrow"))));
	}
	
	public void click_edit_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("edit_btn"))));
	}
	
	public void click_select_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_date"))));
	}
	
	public String get_select_date_value(){
		return driver.findElement(By.xpath(mapping.getProperty("select_date"))).getText().toString();
	}
	
	public Boolean verifyElementEnable(String xPath){
		try{
		     return driver.findElement(By.xpath(mapping.getProperty(xPath))).isDisplayed();
		}catch(NoSuchElementException e)
		{ 
			return true;
		}
	}
	
	public void click_click_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("click_date"))));
	}
	
	public void uncheck_contact_availablity(){
		clickElement(driver.findElement(By.id("availability")));
		//Checkboxed(driver.findElement(By.xpath(mapping.getProperty("checkbox_arm"))));
	}
	
	public void select_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_date"))));
	}
	
	public String get_select_date(){
		return driver.findElement(By.xpath(mapping.getProperty("select_date"))).getText().toString();
	}
	
	public void click_update_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("update_btn"))));
	}
	
	public void click_expense_tab(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("expense_tab"))));
	}
	
	public void click_create_expense(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("create_expense"))));
	}
	
	public void click_expense_click_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("expense_click_date"))));
	}
	
	public void click_expense_select_date(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("expense_select_date"))));
	}
	
	public void click_expense_type(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("expense_type"))));
	}
	
	public void click_expense_type_option(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("expense_type_option"))));
	}
	
	public void click_expense_vendor(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("expense_vendor"))));
	}
	
	public void click_expense_done(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("expense_done"))));
	}
	
	public void click_expense_save_changes(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("expense_save_changes"))));
	}
	
	public void click_ts_new_line(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("ts_new_line"))));
	}
	
	public void click_location_drop_down(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("location_drop_down"))));
	}
	
	public void click_wbs_code(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("click_wbs_code"))));
	}
	
	public void select_wbs_code(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("select_wbs_code"))));
	}
	
	public void input_wbs_code(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("click_wbs_code"))),usrStr);
	}
	
	public void input_expense_wbs_code(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("expense_wbs_code"))),usrStr);
	}
	
	public void input_expense_purpose(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("expense_purpose"))),usrStr);
	}
	
	public void input_expense_description(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("expense_description"))),usrStr);
	}
	
	public void input_expense_amount(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("expense_amount"))),usrStr);
	}
	
	public void input_expense_vendor(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("expense_vendor"))),usrStr);
	}
	
	public Boolean verify_expense_amount_exceedmsg(){	
    	return driver.findElement(By.xpath(mapping.getProperty("expense_amount_exceedmsg"))).getText().toUpperCase().contains("EXCEEDS LIMIT");
    }
	
	public Boolean verify_expense_save_status(){	
    	return driver.findElement(By.xpath(mapping.getProperty("expense_save_msg"))).getText().toUpperCase().contains("The changes you have made to this expense has been saved.".toUpperCase());
    }
	
	public String get_ts_location_value(){
		return driver.findElement(By.xpath(mapping.getProperty("location_drop_down"))).getText();
	}
	
	public void click_location_options(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("location_options"))));
	}
	
	public void click_cancel_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("cancel_btn"))));
	}
	public void click_set_prefs(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("set_prefs"))));
	}
	
	public Boolean verify_role_tracking(){	
    	return driver.findElement(By.xpath(mapping.getProperty("role_tracking"))).getText().contains("ROLE TRACKING");
    }
	
	public Boolean verify_interested_rolestatus(String str){	
    	return driver.findElement(By.xpath(mapping.getProperty("interested_rolestatus"))).getText().toUpperCase() != "";
    }
	
	public Boolean verify_interested_rolename(String str){	
    	return driver.findElement(By.xpath(mapping.getProperty("interested_rolename"))).getText().toString() != "";
    }
	
	public Boolean verify_interested_rolelocation(String str){	
    	return driver.findElement(By.xpath(mapping.getProperty("interested_rolelocation"))).getText().toUpperCase() != "";
    }
	
	public Boolean verify_interested_rolestart(){	
		String str = "2016";
    	return driver.findElement(By.xpath(mapping.getProperty("interested_rolestart"))).getText().toUpperCase().contains(str.toUpperCase());
    }
	
	public Boolean verify_interested_roleduration(String str){	
    	return driver.findElement(By.xpath(mapping.getProperty("interested_roleduration"))).getText().toString() !="";
    }
	
	public Boolean verify_updated_date(String str){	
    	return driver.findElement(By.xpath(mapping.getProperty("updated_date"))).getText().toUpperCase().contains(str.toUpperCase());
    }
	
	public String get_updated_date(){	
    	return driver.findElement(By.xpath(mapping.getProperty("updated_date"))).getText().toUpperCase().toString();
    }
	
	public Boolean verify_availablity_section(){	
    	return driver.findElement(By.xpath(mapping.getProperty("availablity_section"))).getText().toUpperCase().contains("Availability".toUpperCase());
    }
	
	public Boolean verify_availablity_now(){	
    	return driver.findElement(By.xpath(mapping.getProperty("availablity_now"))).getText().toUpperCase().contains("AVAILABLE NOW");
    }
	
	public Boolean verify_on_project(){	
    	return driver.findElement(By.xpath(mapping.getProperty("on_project"))).getText().toUpperCase().contains("ON PROJECT");
    }
	
	public Boolean verify_onproject_rolename(String str){	
    	return driver.findElement(By.xpath(mapping.getProperty("onproject_rolename"))).getText().toUpperCase().contains(str.toUpperCase());
    }
	
	public Boolean verify_time_expense_btn(){	
    	return driver.findElement(By.xpath(mapping.getProperty("time_expense_btn"))).isDisplayed();
    }
	
	public Boolean verify_dashboard_btn(){	
    	return driver.findElement(By.xpath(mapping.getProperty("dashboard_btn"))).isDisplayed();
    }
	
	public Boolean verify_search_roles_btn(){	
    	return driver.findElement(By.xpath(mapping.getProperty("search_roles"))).isDisplayed();
    }
	
	public Boolean verify_time_expense_dialog(){	
    	return driver.findElement(By.xpath(mapping.getProperty("time_expense_dialog"))).isDisplayed();
    }
	
	public void click_time_expense_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("time_expense_btn"))));
	}
	
	public Boolean verify_msg_dashboard(){	
    	return driver.findElement(By.xpath(mapping.getProperty("msg_dashboard"))).getText().toString().toUpperCase().contains("DASHBOARD");
    }

	public Boolean verify_view_offerbtn(){	
    	return driver.findElement(By.xpath(mapping.getProperty("view_offer_btn"))).getText().toUpperCase().contains("VIEW DETAILS");
    }
	
	public Boolean verify_view_details_page(){	
    	return driver.findElement(By.xpath(mapping.getProperty("offer_details_page"))).getText().toUpperCase().contains("OFFER DETAILS");
    }
	
	public Boolean verify_accept_btn(){	
    	return driver.findElement(By.xpath(mapping.getProperty("accept_btn"))).getText().toUpperCase().contains("ACCEPT");
    }
	
	public Boolean verify_decline_btn(){	
    	return driver.findElement(By.xpath(mapping.getProperty("decline_btn"))).getText().toUpperCase().contains("DECLINE");
    }
	
	public void click_decline_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("decline_btn"))));
	}
	
	public Boolean verify_decline_page(){	
    	return driver.findElement(By.xpath(mapping.getProperty("decline_page"))).getText().toUpperCase().contains("DECLINE OFFER");
    }
	
	public Boolean verify_delcine_button(){	
    	return driver.findElement(By.xpath(mapping.getProperty("delcine_button"))).isEnabled();
    }
	
	public Boolean verify_onboarding_section(){	
    	return driver.findElement(By.xpath(mapping.getProperty("onboarding_section"))).getText().toUpperCase().contains("ON-BOARDING");
    }
	
	public Boolean verify_view_onboarding_offer(){	
    	return driver.findElement(By.xpath(mapping.getProperty("view_onboarding_offer"))).getText().toUpperCase().contains("VIEW OFFER");
    }
	
	public Boolean verify_work_arrangement(){	
    	return driver.findElement(By.xpath(mapping.getProperty("work_arrangement"))).getText().toUpperCase().contains("WORK ARRANGEMENT");
    }
	
	public Boolean verify_background_check(){	
    	return driver.findElement(By.xpath(mapping.getProperty("background_check"))).getText().toUpperCase().contains("BACKGROUND CHECK");
    }
	
	public Boolean verify_compliance(){	
    	return driver.findElement(By.xpath(mapping.getProperty("compliance"))).getText().toUpperCase().contains("COMPLIANCE");
    }
	
	
	public void click_view_offer_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("view_offer_btn"))));
	}
	
	public void click_notification_alert(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("notification_alert"))));
	}
	
	public void click_view_all_notification(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("view_all_notification"))));
	}
	
	public void click_all_dropdown(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("all_dropdown"))));
	}
	
	public void click_offer_related(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("offer_related"))));
	}
	
	public void click_view_offer(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("view_offer"))));
	}
	
	public Boolean verify_view_offer(){	
    	return driver.findElement(By.xpath(mapping.getProperty("view_offer"))).isEnabled();
    }
	
	
	public Boolean verify_time_left(){	
    	return (driver.findElement(By.xpath(mapping.getProperty("time_left"))).getText().contains(mapping.getProperty("time_left_txt"))||
    			driver.findElement(By.xpath(mapping.getProperty("time_left"))).getText().contains("h"));
    }
	
	public Boolean verify_current_rate(){	
    	return driver.findElement(By.xpath(mapping.getProperty("current_rate"))).getText().toUpperCase().contains(mapping.getProperty("current_rate_txt").toUpperCase());
    }
	
	public Boolean verify_submit_btn(){	
    	return driver.findElement(By.xpath(mapping.getProperty("rate_submit_btn"))).isDisplayed();
    }
	
	public Boolean verify_time_left_clmn(){	
    	return driver.findElement(By.xpath(mapping.getProperty("time_left_clmn"))).getText().toUpperCase().contains(mapping.getProperty("time_left_clmn_txt").toUpperCase());
    }
	
	public Boolean verify_role_clmn(){	
    	return driver.findElement(By.xpath(mapping.getProperty("role_clmn"))).getText().toUpperCase().contains(mapping.getProperty("role_clmn_txt").toUpperCase());
    }
	
	public Boolean verify_location_clmn(){	
    	return driver.findElement(By.xpath(mapping.getProperty("location_clmn"))).getText().toUpperCase() != "";
    }
	
	public Boolean verify_current_rate_clmn(){	
    	return driver.findElement(By.xpath(mapping.getProperty("current_rate_clmn"))).getText().toUpperCase().contains(mapping.getProperty("currenet_rate_clmn_txt").toUpperCase());
    }
	
	public Boolean verify_fit_score_clmn(){	
    	return driver.findElement(By.xpath(mapping.getProperty("fit_score_clmn"))).getText().toUpperCase().contains(mapping.getProperty("fit_score_clmn_txt").toUpperCase());
    }
	
	public Boolean verify_pricing_event_page(){	
    	return driver.findElement(By.xpath(mapping.getProperty("pricing_event_page"))).getText().toUpperCase().contains(mapping.getProperty("pricing_event_page_txt").toUpperCase());
    }
	
	public Boolean verify_rate(String str){	
    	return driver.findElement(By.xpath(mapping.getProperty("verify_rate"))).getText().toUpperCase().contains(str.toUpperCase());
    }
	
	public Boolean verify_submitted_rate(String str){	
    	return driver.findElement(By.xpath(mapping.getProperty("submitted_rate"))).getText().toUpperCase().contains(str.toUpperCase());
    }
	
	public void input_enter_rate(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("enter_rate"))),usrStr);
	}
	
	public void click_rate_updatebtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("rate_update_btn"))));
	}
	
	public void click_roles_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("roles_btn"))));
	}
	
	public void click_role_details(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_details"))));
	}
	
	public void click_rate_submit_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("rate_submit_btn"))));
	}
	
	public void click_update_ratebtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("update_ratebtn"))));
	}
	
	public void click_pricing_event_rolename(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("pricing_event_rolename"))));
	}
	
	public void click_pricing_eventtab(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("pricing_event_tab"))));
	}
	
	public void click_talent_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("talent_btn"))));
	}
}
