/**
 * 
 */
package com.rex.autotest.pageObjects;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.rex.autotest.utilities.AppiumHelper;
import com.rex.autotest.utilities.Utils;
import com.rex.autotest.utilities.XmlParse;
import com.rex.autotest.utilities.XmlParseHandler;


public class BasePageObject {

	Logger logger = Logger.getLogger(this.getClass());
	XmlParseHandler xmlUtil = XmlParse.getConfigDocInstance();
	WebDriver driver;
	
	//find element by xpath
	public WebElement findElement(String xpathStr){
		logger.info("find element : "+xpathStr);
		try{
		   return driver.findElement(By.xpath(xpathStr));
		}catch(Exception e){
			logger.info("find element exception: "+e.getMessage());
		   return null;
		}
	}
	
	public void releaseElement(WebElement ele){
		/*Actions ac= new Actions(driver);
		ac.release(ele);*/
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ENTER).perform();
	}
	
	//element is displayed or not
	public boolean isElementDisplay(WebElement ele) {
        boolean isdisplay=false;
        WebDriverWait wait=new WebDriverWait(driver, 30);
        try{
             wait.until(ExpectedConditions.visibilityOf(ele));           
             isdisplay=ele.isDisplayed(); 
             logger.info("Verify current element" + ele.getTagName() +" is displayed in the page :"+isdisplay);
           } 
        catch (Exception e) {
             logger.info("Sorry,this element is not displayed in the page,throw:"+e.getMessage());
           }
        return isdisplay;
    }
	
	//clear input box and type
	public void clearAndTypeString(WebElement ele, String text) {
        logger.info("Type a text into the element is:" + ele.getTagName()
                + ", the inputted text:" + text);
        ele.clear();
        ele.sendKeys(text);
    }
	 
	//click element
	public void clickElement(WebElement ele) {
        logger.info("Click elements in page-clicked this element:"
                 + ele.getTagName() + ",the text is:" + ele.getText());   
        ele.click();
    }
	 
	public void clickElementWithFocus(WebElement ele) {
	        logger.info("Click elements in page-clicked this element:"
	                 + ele.getTagName() + ",the text is:" + ele.getText());
	        AppiumHelper.getFocus(driver,ele);
	        ele.click();
	}
	 
	public void doubleClickElement(WebElement ele) {
	        logger.info("Click elements in page-clicked this element:"
	                 + ele.getTagName() + ",the text is:" + ele.getText()); 
	        Actions ac = new Actions(driver); 
		   	ac.doubleClick(ele).perform();
	}


	public void mouseOver(WebElement ele){
		logger.info("mouse over on element :" + ele.getTagName());
	   	Actions ac = new Actions(driver); 
	   	ac.moveToElement(ele).clickAndHold().perform();
	}
	 
	public void rightMenu(WebElement ele){
		logger.info("Right context menu on element :" + ele.getTagName());
	    Actions ac = new Actions(driver); 
		ac.contextClick(ele).perform();
	}
	 
	public String getCssValue(WebElement ele,String value){
		 logger.info("Css value on element :" + ele.getTagName());
		 return ele.getCssValue(value);
	}
	 
	//switch to frame/iframe
	public String switchToFrame(String fName){
	   logger.info("switch to frame : "+fName);
	   String strMainHandler = driver.getWindowHandle();
	   driver.switchTo().frame(fName);
	   return strMainHandler;
	}
	 
	//switch to window
	public String switchToWindow(String wName){
	   logger.info("switch to window : "+wName + "from main page");
	   String strMainHandler = driver.getWindowHandle();
	   driver.switchTo().window(wName);
	   return strMainHandler;
	}
	 
	 //get cookie
	 /*public Set getCookieValue(){
		logger.info("retrieve cookies");
	    return driver.manage().getCookies();
	 }*/
	 //rich text	 
	 
	 //execute js
	 public Object executeJS(String script) {
         logger.info("Run the javascript from page ,the java script is:" + script);
         JavascriptExecutor je = (JavascriptExecutor) driver;
         return je.executeScript(script);
     }
	 
     public void executeJS(String script,WebElement e) {
         logger.info("Run the javascript from page ,the java script is:" + script);
         JavascriptExecutor je = (JavascriptExecutor) driver;
         je.executeScript(script,e);
     }
     
//     public Object executeJSReturn(String script,WebElement e) {
//         logger.info("Run the javascript from page ,the java script is:" + script);
//         JavascriptExecutor je = (JavascriptExecutor) driver;
//         Object object=je.executeScript(script,e);
//         return object;
//     }
     
     public void refreshPage(){
         //driver.navigate().refresh();
         logger.info("Now refresh the page to keep the session valid");
         //or blow
         Actions actions = new Actions(driver);
         actions.sendKeys(Keys.F5).perform();
     }
     
     public void scrollToView(WebElement ele){
    	 logger.info("Now we scroll the view to the position we can see");
         //AppiumHelper.getFocus(driver, ele);
         executeJS("window.scrollTo(0,"+ele.getLocation().y+")");
         //executeJS("arguments[0].scrollIntoView(true);", e);
     }
     
     public void Checkboxed(WebElement e) {
         if (!(e.isSelected())) {
             logger.info("Check the checkbox,the webelment :" + e.getTagName()
                     + e.getText() + ",was unselected before");
             e.click();
             logger.info("Check the checkbox,the webelment :" + e.getTagName()
                     + e.getText() + ",is selected now");
         } else {
             logger.info("Check the checkbox,the webelment :" + e.getTagName()
                     + e.getText() + ",had been selected default");
         }
     }
     
     public void unCheckbox(WebElement e) {
         if (e.isSelected()) {
             logger.info("Check the checkbox,the webelment :" + e.getTagName()
                     + e.getText() + ",was selected before");
             e.click();
             logger.info("Check the checkbox,the webelment :" + e.getTagName()
                     + e.getText() + ",is unselected now");
         } else {
             logger.info("Check the checkbox,the webelment :" + e.getTagName()
                     + e.getText() + ",had been unselected default");
         }
     }

	 //select drop down
     public void selectDropDownByValue(WebElement e,String value) {
    	 logger.info("Try to select item :"+ value + "from dropdown :" + e.getTagName());
         Select sele = new Select(e);
         sele.selectByValue(value);
     }
     
     public void selectDropDownByIndex(WebElement e,int index) {
    	 logger.info("Try to select item :"+ index + "from dropdown :" + e.getTagName());
         Select sele = new Select(e);
         sele.selectByIndex(index);
     }
     
     public void selectDropDownByVisableText(WebElement e,String value) {
    	 logger.info("Try to select item :"+ value + "from dropdown :" + e.getTagName());
         Select sele = new Select(e);
         sele.selectByVisibleText(value);
     }
     
     public void selectDropDownByPartialValue(WebElement e,String name) {
    	 logger.info("Try to select item :"+ name + " from dropdown :" + e.getTagName());
         Select sele = new Select(e);
         List<WebElement> optList=sele.getOptions();
         for(WebElement opt: optList){
        	 if(opt.getText().contains(name)){
        		 opt.click();
        		 break;
        	 }
         }
     }
     
     public void selectDropDownByText(String strXpath, String name) {
    	 WebElement ulObj = driver.findElement(By.xpath(strXpath));
    	 List<WebElement> optList= ulObj.findElements(By.tagName("li")); 
         for(WebElement opt: optList){
        	 if(opt.getText().contains(name)){
        		 logger.info("Try to select item :"+ name + " from dropdown :" + opt.getTagName());
        		 opt.findElement(By.tagName("a")).click();
        		 break;
        	 }
         }
     }
     
     public void selectDropDownByIndex(String strXpath, String num) {
    	 WebElement ulObj = driver.findElement(By.xpath(strXpath));
    	 List<WebElement> optList= ulObj.findElements(By.tagName("li")); 
         for(WebElement opt: optList){
        	 if(opt.getAttribute("data-original-index").equals(num)){
        		 logger.info("Try to select item (index):"+ num + " from dropdown :" + opt.getTagName());
        		 opt.findElement(By.tagName("a")).click();
        		 break;
        	 }
         }
     }
     
     public String getSelectedValue(WebElement e) {
    	 logger.info("find selected item from dropdown :" + e.getText());
    	 String result = "";
         Select sele = new Select(e);
         List<WebElement> optList=sele.getOptions();
         for(WebElement opt: optList){
        	 if(opt.getAttribute("selected")!=null){
        		 result = opt.getText();
        		 break;
        	 }
         }
         return result;
     }
     
     //drag and drop by point
     public void dragAndDrop(WebElement e,int x,int y) {
    	 logger.info("Try to drag :" + e.getTagName()+"  X:"+x+"   Y:"+y);
    	 Actions actions = new Actions(driver);
    	 actions.dragAndDropBy(e, x, y).build().perform();
     }
     
     //drag and drop by from/to element
//     FirefoxProfile profile = new FirefoxProfile();    
//     profile.setEnableNativeEvents(true);    
//     WebDriver driver = new FirefoxDriver(profile);
     public void dragAndDropByMove(WebElement from,WebElement to) {
    	 logger.info("Try to drag :"+ from.getTagName() + "to : " + to.getTagName());
    	 Actions actions = new Actions(driver);
    	 actions.clickAndHold(from)
    	        .moveToElement(to).release(to).build();
    	 actions.perform();
     }
     
     
     public void holdAndMoveMouse(WebElement ele,int xOffset,int yOffset) {
    	 logger.info("Try to drag :"+ ele.getText());
//    	 int x = ele.getLocation().getX();
//    	 int y = ele.getLocation().getY();
    	 Actions actions = new Actions(driver);
    	 actions.moveToElement(ele, 100, 100)
    	        .clickAndHold()
    	        .moveByOffset(xOffset, yOffset).release().build();
    	 actions.perform();
     }
     
     public void moveMouse(WebElement ele) {
    	 logger.info("Try to drag :"+ ele.getText());
    	 Actions actions = new Actions(driver);
//    	 int x = ele.getLocation().getX();
//    	 int y = ele.getLocation().getY();
    	 actions.moveToElement(ele, 100, 100).release().build();
    	 actions.perform();   	 
     }
     
     public void PressEnterKey(){
 		Actions action = new Actions(driver); 
 		action.sendKeys(Keys.ENTER).perform();
     }
     
 	public Boolean isObjectExist(String xpathStr) {
		try {
			driver.findElement(By.xpath(xpathStr));
			return true;
		} 
		catch (NoSuchElementException e) {
			return false;
		}
	}
 	
} 
