package com.rex.autotest.pageObjects;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.rex.autotest.utilities.PropUtils;
import com.rex.autotest.utilities.Utils;


public class EMDashboardPageObject extends BasePageObject {
	private  static final String PROP_FILE =  System.getProperty("user.dir")+"/src/com/rex/autotest/uimapping/EMDashboard.properties";
	Properties mapping = PropUtils.getProperties(PROP_FILE);
	
	public EMDashboardPageObject(WebDriver driver) {
		this.driver = driver;
		//if(!driver.getTitle().contains("Talent Exchange")){
		//	logger.info("REX-Dashboard page is not loaded");
		//	throw new IllegalStateException("REX-Dashboard page is not loaded");
		//}
	}
	
	public Boolean verify_stage_role(){	
		return driver.findElement(By.xpath(mapping.getProperty("stage_role"))).getText().toUpperCase().contains(mapping.getProperty("stage_role_txt").toUpperCase());
	}
	
	public Boolean verify_ok_btn(){	
		return driver.findElement(By.xpath(mapping.getProperty("ok_btn"))).isDisplayed();
	}
	
	public void click_ok_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("ok_btn"))));
	}
	
	public void click_mobile_roles_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_menu"))));
		Utils.sleep(1000);
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_roles_item"))));
	}
	
	
	public void click_mobile_role_name(String xpath){
		clickElement(driver.findElement(By.xpath(mapping.getProperty(xpath))));
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
	
	public String get_mobile_value(String xpath){
		 return driver.findElement(By.xpath(mapping.getProperty(xpath))).getText().toString();
	}
	
	public void check_mobile_checkbox_approved_xpath(String xpath){
		clickElement(driver.findElement(By.xpath(mapping.getProperty(xpath))));
	}
	
	public void check_mobile_checkbox_approved(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("m_approved_id"))));
	}
	
	public Boolean verify_delivery_score_txt(){	
		return driver.findElement(By.xpath(mapping.getProperty("delivery_score_txt"))).getText().toUpperCase().contains(mapping.getProperty("delivery_score_txt1").toUpperCase());
	}
	
	public Boolean verify_reviewed_item(){	
		return driver.findElement(By.xpath(mapping.getProperty("reviewed_item"))).getText().toUpperCase().contains("REVIEWED");
	}
	
	public Boolean verify_to_be_reviewed_item(){	
		return driver.findElement(By.xpath(mapping.getProperty("to_be_reviewed_item"))).getText().toUpperCase().contains("TO BE REVIEWED");
	}
	
	public Boolean verify_to_be_reviewed_number(){	
		String str = driver.findElement(By.xpath(mapping.getProperty("to_be_reviewed_number"))).getText().toString();
		int i = Integer.parseInt(str);
		return i ==1 || i > 1;
	}
	
	public Boolean verify_tobereviewed_talent_name(){	
		return driver.findElement(By.xpath(mapping.getProperty("tobereviewed_talent_name"))).getText().toUpperCase() != "";
	}
	
	public Boolean verify_tobereviewed_role_name(){	
		return driver.findElement(By.xpath(mapping.getProperty("tobereviewed_role_name"))).getText().toUpperCase() != "";
	}
	
	public Boolean verify_tobereviewed_talent_login(){	
		return driver.findElement(By.xpath(mapping.getProperty("tobereviewed_talent_login"))).getText().toUpperCase().contains("2016");
	}
	
	public Boolean verify_review_later_logout(){	
		return driver.findElement(By.xpath(mapping.getProperty("review_later_logout"))).getText().toUpperCase().contains("Review Later and Sign Out".toUpperCase());
	}
	
	public void click_review_later_logout(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("review_later_logout"))));
	}
	
	public void click_offer_stage(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("offer_stage"))));
	}
	
	public void click_recruiter_create_new_role(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("recruiter_create_new_role"))));
	}
	
	public Boolean verify_stage_location(){	
		return driver.findElement(By.xpath(mapping.getProperty("stage_location"))).getText().toUpperCase().contains(mapping.getProperty("stage_location_txt").toUpperCase());
	}
	
	public Boolean verify_stage_talent(){	
		return driver.findElement(By.xpath(mapping.getProperty("stage_talent"))).getText().toUpperCase().contains(mapping.getProperty("stage_talent_txt").toUpperCase());
	}
	
	public Boolean verify_stage_final_rate(){	
		return driver.findElement(By.xpath(mapping.getProperty("stage_final_rate"))).getText().toUpperCase().contains(mapping.getProperty("stage_final_rate_txt").toUpperCase());
	}
	
	public Boolean verify_stage_status(){	
		return driver.findElement(By.xpath(mapping.getProperty("stage_status"))).getText().toUpperCase().contains(mapping.getProperty("stage_status_txt").toUpperCase());
	}
	
	public void click_create_new_role(){
		clickElement(driver.findElement(By.xpath("//*[text()='Create New Role']")));
	}
	
	public void click_recruiter_create_new_rolebtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("recruiter_create_new_role"))));
	}
	
	public void click_reruiter_createnewrole(){
		clickElement(driver.findElement(By.xpath("//*[text()='Create New Role']")));
	}
	
	public Boolean verify_pricing_event_tab(){	
		return driver.findElement(By.xpath(mapping.getProperty("pricing_event_tab"))).getText().toUpperCase().contains(mapping.getProperty("pricing_event_tab_txt").toUpperCase());
	}
	
	public Boolean verify_offer_stage(){	
		return driver.findElement(By.xpath(mapping.getProperty("offer_stage"))).getText().toUpperCase().contains(mapping.getProperty("offer_stage_txt").toUpperCase());
	}
	
	public Boolean verify_pe_status(){	
		return driver.findElement(By.xpath(mapping.getProperty("pe_status"))).getText().toUpperCase().contains(mapping.getProperty("pe_status_txt"));
	}
	
	public Boolean verify_pe_nobids(){	
		return driver.findElement(By.xpath(mapping.getProperty("pe_nobids"))).getText().toUpperCase().contains(mapping.getProperty("pe_nobids_txt"));
	}
	
	public Boolean verify_pe_rate_range(){	
		return driver.findElement(By.xpath(mapping.getProperty("pe_rate_range"))).getText().toUpperCase().contains(mapping.getProperty("pe_rate_range_txt"));
	}
	
	public String get_bid_number(){
		return driver.findElement(By.xpath(mapping.getProperty("pe_bidno"))).getText().toString();
	}
	
	public String get_current_rate_range(){
		return driver.findElement(By.xpath(mapping.getProperty("pe_current_rate_range"))).getText().toString();
	}
	
	public Boolean verify_updated_bid_number(String str){
		return driver.findElement(By.xpath(mapping.getProperty("pe_bidno"))).getText().contains(str);
	}
	
	public Boolean verify_updated_current_rate_range(String str){
		return driver.findElement(By.xpath(mapping.getProperty("pe_current_rate_range"))).getText() != str;
	}
	
	public Boolean verify_pe_role(){	
		return driver.findElement(By.xpath(mapping.getProperty("pe_role"))).getText().toUpperCase().contains(mapping.getProperty("pe_role_txt"));
	}
	
	public Boolean verify_pe_location(){	
		return driver.findElement(By.xpath(mapping.getProperty("pe_location"))).getText().toUpperCase().contains(mapping.getProperty("pe_location_txt"));
	}
	
	public Boolean verify_signin_Error(){	
		return driver.findElement(By.xpath(mapping.getProperty("pricing_event_tab"))).getText().toUpperCase().contains(mapping.getProperty("pricing_event_tab_txt"));
	}
	
	public Boolean verify_delivery_score_dialog(){	
		return driver.findElement(By.xpath(mapping.getProperty("delivery_score_dialog"))).getText().toUpperCase().contains(mapping.getProperty("delivery_score_dialog_txt").toUpperCase());
	}
	
	public void click_ds_rolename(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("ds_role_name"))));
	}
	
	public void click_yes_radiobtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("yes_radiobtn"))));
	}
	
	public void click_no_radiobtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("no_radiobtn"))));
	}
	
	public void click_submit_delivery_scorebtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("submit_delivery_scorebtn"))));
	}
	
	public void click_rate_review_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("rate_review_btn"))));
	}
	
	public void click_view_all_activities(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("view_all_activities"))));
	}
	
	public void click_dash_gotoRoles(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("dash_gotoRoles"))));
	}
	
	public void click_dash_roles_list01(){
		if(driver.findElement(By.xpath(mapping.getProperty("dash_roles_list01"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("dash_roles_list01"))));
		}
		Utils.sleep(5000);
	}
	
	public void click_dash_roles_list02(){
		if(driver.findElement(By.xpath(mapping.getProperty("dash_roles_list01"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("dash_roles_list02"))));
		}
	}
	
	public void click_view_starredtalent_btn(){
		
		WebElement ele = driver.findElement(By.xpath(mapping.getProperty("em_starred_talent")));
		
		scrollToView(ele);
		
		Utils.sleep(1000);
		
		if(driver.findElement(By.xpath(mapping.getProperty("view_starredtalent_btn"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("view_starredtalent_btn"))));
		}
	}
	
	public void click_viewall_starredtalent_btn(){
		
		WebElement ele = driver.findElement(By.xpath(mapping.getProperty("em_starred_talent")));
		
		scrollToView(ele);
		
		Utils.sleep(1000);
		
		if(driver.findElement(By.xpath(mapping.getProperty("viewall_starredtalent_btn"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("viewall_starredtalent_btn"))));
		}
	}
	
	public Boolean verify_nostarredtalent_txt(){	
    	return driver.findElement(By.xpath(mapping.getProperty("no_starredtalent_msg"))).getText().toUpperCase().contains(mapping.getProperty("no_starredtalent_txt").toUpperCase());
    }
	
	public Boolean verify_msg_radiobtn(){	
    	return driver.findElement(By.xpath(mapping.getProperty("msg_radiobtn"))).getText().toUpperCase().contains(mapping.getProperty("msg_radiobtn_txt").toUpperCase());
    }
	
	public Boolean verify_msg_delivery_score(){	
    	return driver.findElement(By.xpath(mapping.getProperty("msg_delivery_score"))).getText().toUpperCase().contains(mapping.getProperty("msg_delivery_score_txt").toUpperCase());
    }
	
	public Boolean verify_msg_dashboard(){	
    	return driver.findElement(By.xpath(mapping.getProperty("msg_dashboard"))).getText().contains(mapping.getProperty("msg_dash"));
    }
	
	public Boolean verify_pricing_event_started(){	
    	return mapping.getProperty("activity_msgs").toUpperCase().contains(driver.findElement(By.xpath(mapping.getProperty("pricing_event_started"))).getText().toUpperCase());
    }
	
	public Boolean verify_role_approved(){	
    	return mapping.getProperty("activity_msgs").toUpperCase().contains(driver.findElement(By.xpath(mapping.getProperty("role_approved"))).getText().toUpperCase());
    }
	
	public Boolean verify_role_denied(){	
    	return mapping.getProperty("activity_msgs").toUpperCase().contains(driver.findElement(By.xpath(mapping.getProperty("role_denied"))).getText().toUpperCase());
    }
	
	public Boolean verify_pricing_event_complete(){	
    	return mapping.getProperty("activity_msgs").toUpperCase().contains(driver.findElement(By.xpath(mapping.getProperty("pricing_event_complete"))).getText().toUpperCase());
    }
	
	public Boolean verify_pricing_event_ending(){	
    	return mapping.getProperty("activity_msgs").toUpperCase().contains(driver.findElement(By.xpath(mapping.getProperty("pricing_event_ending"))).getText().toUpperCase());
    }
	
	public Boolean verify_role_tracking(){	
    	return driver.findElement(By.xpath(mapping.getProperty("em_role_tracking"))).getText().contains(mapping.getProperty("msg_role_tracking"));
    }
	
	public Boolean verify_roles(){	
    	return driver.findElement(By.xpath(mapping.getProperty("em_roles"))).getText().contains(mapping.getProperty("msg_roles"));
    }
	
	public Boolean verify_activity(){	
    	return driver.findElement(By.xpath(mapping.getProperty("em_activity"))).getText().toUpperCase().contains(mapping.getProperty("msg_activity"));
    }
	
	public Boolean verify_starredtalent(){	
    	return driver.findElement(By.xpath(mapping.getProperty("em_starred_talent"))).getText().contains(mapping.getProperty("msg_starredtalent"));
    }
	
	public Boolean verify_starredtalentnumberafter(){	
    	return driver.findElement(By.xpath(mapping.getProperty("em_starred_talent"))).getText().contains(mapping.getProperty("msg_starredtalentnumberafter"));
    }
	
	public Boolean verify_starredtalentnumberbefore(){	
    	return driver.findElement(By.xpath(mapping.getProperty("em_starred_talent"))).getText().contains(mapping.getProperty("msg_starredtalentnumberbefore"));
    }
	
	public void click_favourite(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("favourite_btn"))));
	}
	
	public Boolean verify_favourite(){	
    	return driver.findElement(By.xpath(mapping.getProperty("favourite_btn"))).isDisplayed();
    }
	
	public Boolean verify_favourite_checked(){	
    	return driver.findElement(By.xpath(mapping.getProperty("favourite_btn"))).getClass().getName().toUpperCase().contains("glyphicon pull-right glyphicon-star".toUpperCase());
    }
	
	public void click_view_allroles(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("view_all_role"))));
	}
	
	public void click_dashboard(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("dashboard_btn"))));
	}
	
	public void click_dash_talent(){
		if(driver.findElement(By.xpath(mapping.getProperty("dash_talent"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("dash_talent"))));
		}
		
		Utils.sleep(5000);
	}
	
	public void click_role_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_btn"))));
	}
	
	public void click_roles_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("dash_roles"))));
		Utils.sleep(3000);
	}
	
	public void click_engagement_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("dash_engagement"))));
	}
}
