package com.rex.autotest.pageObjects;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.rex.autotest.utilities.PropUtils;
import com.rex.autotest.utilities.Utils;


public class TalentProfilePageObject extends BasePageObject {
	private  static final String PROP_FILE =  System.getProperty("user.dir")+"/src/com/rex/autotest/uimapping/TalentProfile.properties";
	Properties mapping = PropUtils.getProperties(PROP_FILE);
	//Map<String, Map<String, String>> dataMap = ExcelUtils.readExcel("ID-5210");
	//final String account = "TC-00";
	public TalentProfilePageObject(WebDriver driver) {
		this.driver = driver;
		//if(!driver.getTitle().contains("Talent Exchange")){
		//	logger.info("REX-Create new role page is not loaded");
		//	throw new IllegalStateException("REX-Create new role page is not loaded");
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
	
	public Boolean verifyElementDisplayed(String xPath){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).isDisplayed();
	}
	
	public Boolean verifyElement(String xPath, String str){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getText().toString().toUpperCase().contains(str.toUpperCase());
	}
	
	public String getElementValueByxPath(String xPath){
		return driver.findElement(By.xpath(mapping.getProperty(xPath))).getText().toString();
	}
	
	public String getElementValueById(String id){
		return driver.findElement(By.id(id)).getText().toString();
	}
	
	public Boolean verifyUpdatedValue(String str, String str1){
		return (str.contains(str1)==false);
	}
	
	public void click_radio_yesbtn(){
		//clickElement(driver.findElement(By.xpath(mapping.getProperty("yes_radiobtn"))));
		clickElement(driver.findElement(By.id("employee-status1-1120")));
	}
	
	public void input_screen_notes(String usrStr){
		Utils.sleep(3000);
		if(driver.findElement(By.id("ui-tinymce-1_ifr")).isDisplayed()==false)
		{
		driver.switchTo().frame("ui-tinymce-2_ifr");
		Utils.sleep(2000);
		WebElement editor = driver.findElement(By.tagName("body"));
		Utils.sleep(1000);
		editor.click();
		Utils.sleep(1000);
		editor.sendKeys("Screened.");
//		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
//		Utils.sleep(1000);
//		jsExecutor.executeScript("arguments[0].innerHTML = '<h2>Meet Requirement</h2>'", editor);
//		Utils.sleep(2000);
		driver.switchTo().defaultContent();
		Utils.sleep(1000);
		}
		else
		{
				driver.switchTo().frame("ui-tinymce-1_ifr");
				Utils.sleep(2000);
				WebElement editor = driver.findElement(By.tagName("body"));
				Utils.sleep(1000);
				editor.click();
				Utils.sleep(1000);
				editor.sendKeys("Screened.");
//				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
//				Utils.sleep(1000);
//				jsExecutor.executeScript("arguments[0].innerHTML = '<h2>Meet Requirement</h2>'", editor);
//				Utils.sleep(2000);
				driver.switchTo().defaultContent();
				Utils.sleep(1000);
			
		}
	}
	
	public void click_radiono_btn(){
//		WebElement menu = driver.findElement(By.id("employee-screening-status2-0"));
//		Utils.sleep(1000);
//		Actions build = new Actions(driver);
//	    build.moveToElement(menu).build().perform();
//		Utils.sleep(1000);
//	    WebElement m2m= driver.findElement(By.id("employee-screening-status2-0"));
//		Utils.sleep(1000);
//	    m2m.click();
		clickElement(driver.findElement(By.xpath(mapping.getProperty("no_radiobtn"))));
		//clickElement(driver.findElement(By.id("employee-screening-status2-0"))); //*[@id='employee-screening-status2-0']
	}
	
	public void click_screen_decision(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("screen_decision"))));
	}
	
	public void click_meet_requirement(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("meet_requirement"))));
	}
	
	public void click_talent_not_interested(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("talent_not_interested"))));
	}
	
	public void click_doesnot_meet_requirement(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("doesnot_meet_requirement"))));
	}
	
	public void click_partially_meet(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("partially_meet"))));
	}

	public Boolean verify_selected_decision(String str){
		return driver.findElement(By.xpath(mapping.getProperty("screen_decision"))).getText().toUpperCase().contains(str.toUpperCase());
	}
	
	public void click_summary_edit_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("summary_edit_btn"))));
	}
	
	public void click_summary_save_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("summary_save_btn"))));
	}
	
	public void click_talent_summary_editbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("talent_summary_editbtn"))));
	}
	
	public void click_talent_summary_savebtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("talent_summary_savebtn"))));
	}
	
	public void click_contact_edit_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("contact_edit_btn"))));
	}
	
	public void click_contact_save_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("contact_save_btn"))));
	}
	
	public void click_talent_contact_editbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("talent_contact_editbtn"))));
	}
	
	public void click_talent_contact_savebtn(){
		clickElement(driver.findElement(By.xpath("//*[text()='Save']")));
	}
	
	public void input_summary_textarea(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("summary_textarea"))),usrStr);
	}
	
	public void input_talent_summary_txtfield(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("talent_summary_txtfield"))),usrStr);
	}
	
	public void input_contact_title_filed(String usrStr){
		clearAndTypeString(driver.findElement(By.xpath(mapping.getProperty("contact_title_filed"))),usrStr);
	}
	
	public void input_talent_contact_titleid(String usrStr){
		clearAndTypeString(driver.findElement(By.id(mapping.getProperty("talent_contact_titleid"))),usrStr);
	}
	
	public Boolean verify_updated_summary(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("updated_summary"))).getText().toUpperCase().contains(str.toUpperCase());
	}
	
	public Boolean verify_updated_contact_title(String str){	
		return driver.findElement(By.xpath(mapping.getProperty("updated_contact_title"))).getText().toUpperCase().contains(str.toUpperCase());
	}
	
	public void click_recruiter_screen_submit(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("recruiter_screen_submit"))));
	}
	
	public void click_radio_nobtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("radio_no"))));
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
	
	public Boolean verify_choose_roles(){	
		return driver.findElement(By.xpath(mapping.getProperty("choose_roles"))).getText().toUpperCase().contains(mapping.getProperty("choose_roles_txt").toUpperCase());
	}
	
	public Boolean verify_other_pending_screening(){	
		return driver.findElement(By.xpath(mapping.getProperty("other_pending_screening"))).getText().toUpperCase().contains(mapping.getProperty("other_pending_screening_txt").toUpperCase());
	}
	
	public void click_screen_btn(){
		if(driver.findElement(By.xpath(mapping.getProperty("screen_btn"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("screen_btn"))));
		}
	}
	
	public void click_interaction_tab_btn(){
			clickElement(driver.findElement(By.xpath(mapping.getProperty("interaction_tab_btn"))));
	}
	

	
	public void input_txtfield(){
		WebElement f = driver.findElement(By.id("ui-tinymce-0_ifr"));
		driver.switchTo().frame(f);

		Utils.sleep(1000);
		WebElement editor = driver.findElement(By.tagName("body"));
		Utils.sleep(500);
		editor.click();
		Utils.sleep(500);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		Utils.sleep(500);
		jsExecutor.executeScript("arguments[0].innerHTML = '<h1>Screened. Check the note please</h1>'", editor);

		Utils.sleep(1000);
		driver.switchTo().defaultContent();
		Utils.sleep(1000);
		driver.switchTo().frame(f);
		Utils.sleep(1000);
		editor.click();
		Utils.sleep(1000);
		driver.switchTo().defaultContent();
		Utils.sleep(1000);
	}
	
	public void click_btn_dropdown(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("options"))));
	}
	
	public void click_delegate_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("delegate_btn"))));
	}
	
	public Boolean verify_already_added()
	{
		return driver.findElement(By.xpath(mapping.getProperty("already_added"))).isDisplayed();
	}
	
	public Boolean verify_recruiter_screen_submit_confirmation()
	{
		return driver.findElement(By.xpath(mapping.getProperty("recruiter_screen_submit_confirmation"))).getText().toUpperCase().contains(mapping.getProperty("recruiter_screen_submit_confirmation_txt").toUpperCase());
	}
	
	public void click_already_added(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("already_added"))));
	}
	
	public void select_goodfit_option(String stropt){
		selectDropDownByText(mapping.getProperty("good_fit"), stropt);
	}
	
	public void click_goodfit_option(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("good_fit_option"))));
	}
	
	public void click_person_notinterested_option(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("person_notinterested_option"))));
	}
	
	public void click_notgoodfit_option(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("notgoodfit_option"))));
	}
	
	public Boolean verify_selected_option(String str){
		return driver.findElement(By.xpath(mapping.getProperty("screen_decision"))).getText().toUpperCase().contains(str.toUpperCase());
	}
	
	public void select_person_notinterested_option(String stropt){
		selectDropDownByText(mapping.getProperty("person_notinterested"), stropt);
	}
	
	public void input_screener(String usrStr){
		if(driver.findElement(By.id(mapping.getProperty("screener_txtfiled"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clearAndTypeString(driver.findElement(By.id(mapping.getProperty("screener_txtfiled"))),usrStr);
		}
	}
	
	public void click_send_delegate_mail(){
		clickElement(driver.findElement(By.id(mapping.getProperty("send_delegatemailid"))));
	}
	
	public void click_cancel_send_mail(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("cancel_sendmail"))));
	}
	
	public void select_not_agoodfit_option(String stropt){
		selectDropDownByText(mapping.getProperty("not_agoodfit"), stropt);
	}
	
	public void click_send_mail(){
		if(driver.findElement(By.xpath(mapping.getProperty("send_mail"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("send_mail"))));
		}
	}
	
	public void click_send_mail_id(){
		clickElement(driver.findElement(By.id(mapping.getProperty("send_mail_id"))));
	}
	
	public void click_submit_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("submit_btn"))));
	}
	
	public void click_talent_list_shortlist(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("talent_list_shortlist"))));
	}
	
	public void click_shortlist_btn(){
		clickElement(driver.findElement(By.xpath("//*[text()='Shortlist to Role']")));
	}
	
	public void click_shortlist_bt1n(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("shortlist_btn"))));
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
	
	public void click_contact_talent(){
		if(driver.findElement(By.xpath(mapping.getProperty("contact_talentbtn"))).isEnabled()==false)
		{
			Utils.sleep(1000);
		}
		else
		{
			clickElement(driver.findElement(By.xpath(mapping.getProperty("contact_talentbtn"))));
		}
	}
	
	public void click_role_list01(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_list01"))));
	}
	
	public void click_role_01(){
		//clickElement(driver.findElement(By.id("role-id-0")));
		clickElement(driver.findElement(By.xpath(mapping.getProperty("role_01id"))));
	}
	
	public void click_role_01_id(){
		clickElement(driver.findElement(By.id(mapping.getProperty("role_01id"))));
	}
	
	public void click_cel_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("cel_btn"))));
	}
	
	public void click_em_add_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("em_add_btn"))));
	}
	
	public void click_add_btn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("add_btn"))));
	}
	
	public Boolean verify_submit_result(){
		return driver.findElement(By.xpath(mapping.getProperty("submit_confirm_msg"))).getText().toUpperCase().contains(mapping.getProperty("confirm_msg").toUpperCase());
	}
	
	public Boolean verify_contact_talent_dialog(){
		return driver.findElement(By.xpath(mapping.getProperty("contact_talent_dialog"))).getText().toUpperCase().contains(mapping.getProperty("contact_talent_txt"));
	}
	
	public void click_confirm_okbtn(){
		clickElement(driver.findElement(By.xpath(mapping.getProperty("confirm_ok"))));
	}
}
