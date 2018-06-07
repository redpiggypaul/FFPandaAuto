package farfetch.panda.beta.ios.AppPage.IOS;

import Component.Operation.AppBaseOperation;
import Component.Page.ANDPageContent;
import Component.Page.Element.ElementMapping;
import Component.Page.PageIndexGroup;
import Component.PageLoadWait.*;
import LocalException.PageException;
import LocalException.XMLException;
import dataModel.TestPlatformData;
import utility.Log;
import utility.dateHandle;
import utility.readProperity;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.ios.IOSKeyCode;
import io.appium.java_client.remote.HideKeyboardStrategy;
import org.apache.poi.ss.formula.functions.Match;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Component.Driver.ScreenShot.ScreenShot;
import static org.openqa.selenium.Keys.ENTER;

/**
 * Created by appledev131 on 4/11/16.
 */
public abstract class IOSPageTemplate {
    protected String fileName = "loginPage.json";
    Properties props = System.getProperties();

    StringBuilder autoOSType = TestPlatformData.getAutoOSType();
    private String contentFilePath = "\\PageXML\\IOS\\";
    public String path4Log = System.getProperty("user.dir");

    String osName = props.getProperty("os.name");
    protected String targetPagename = "bhomePage";
    protected String comFileName = null;
    protected IOSPageContent content = null;
    protected HashMap<String, ElementMapping> eleContentMap = null;
    protected HashMap<String, IOSElement> eleMap = null;
    public HashMap<String, By> byMap = null;
    public HashMap<String, String> dValueMap = null;
    protected IOSDriver dr = null;
    protected int osType = 1;
    protected HashMap<String, String> nextPageList = null;
    protected long timeOutInSeconds = Long.parseLong(readProperity.getProValue("pageLoadTimeout"));
    private static String logSpace4thisPage = " --- FFPandaIOSPageTemplate --";

    public IOSPageTemplate(IOSDriver theD) throws Exception {
    }

    public IOSPageTemplate(String theFile, IOSDriver theD, Log theLogger) throws Exception {
    }

    public String getConfWindName() {
        return null;
    }

    public String getErrWindName() {
        return null;
    }

    public String getCompWindName() {
        return null;
    }

    public String getConfWindWOTName() {
        return null;
    }

    public boolean checkFlag4Win() {
        return this.content.flag4EleWindow;
    }

    public HashMap<String, String> getEleWinMap() {
        return this.content.eleWindowMap;
    }

    public HashMap<String, By> getByMap(IOSDriver driver) {
        HashMap result = new HashMap<String, By>();
        result.clear();
        By tempElement = null;
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in ANDPageContent . getElementMap  : " + driver.toString());

        for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext(); ) {
            try {
                Thread.sleep(50);

                Map.Entry tempContentEntry = (Map.Entry) it.next();
                System.out.println(logSpace4thisPage + "xxxxxx ------ Check the. contMap  getkey ~~~~ " + tempContentEntry.getKey());
                System.out.println(logSpace4thisPage + "xxxxxx ------ Check the. contMap  getValue~~~~ " + tempContentEntry.getValue());
                System.out.println(logSpace4thisPage + "^^^^^^^^^^^^^^^^^^^^^^ getElementMap Call combindAndElement ^^^^^^^^^^^^^^^^^");
                System.out.println(logSpace4thisPage + "Check the value in Entry in combind : " + (ElementMapping) tempContentEntry.getValue() + " !!! ");

                //     tempElement = combindAndElement(driver, (ElementMapping) tempContentEntry.getValue(), 6, this.osTypeInContent);
                if (((ElementMapping) tempContentEntry.getValue()).getType().equalsIgnoreCase("xpath")) {
                    tempElement = By.xpath(((ElementMapping) tempContentEntry.getValue()).getLocatorStr());
                } else if (((ElementMapping) tempContentEntry.getValue()).getType().equalsIgnoreCase("id")) {
                    tempElement = By.id(((ElementMapping) tempContentEntry.getValue()).getLocatorStr());
                } else if (((ElementMapping) tempContentEntry.getValue()).getType().equalsIgnoreCase("classname")) {
                    tempElement = By.className(((ElementMapping) tempContentEntry.getValue()).getLocatorStr());
                } else {
                    System.out.println(logSpace4thisPage + "currently we just support xpath & id");
                }
                System.out.println(logSpace4thisPage + "Check the tempElement from combind : " + tempElement.toString() + " !!! ");
                System.out.println(logSpace4thisPage + "Check the getKey from combind : " + tempContentEntry.getKey().toString() + " !!! ");
                System.out.println(logSpace4thisPage + "Chcek the Class of tempElement getKey " + tempContentEntry.getKey().toString() instanceof String);
                System.out.println(logSpace4thisPage + "Chcek the Class of tempElement " + tempElement.getClass());

                result.put(tempContentEntry.getKey().toString(), tempElement);
                System.out.println("!!!!!! " + result.toString() + " !!!!!!");
                //return resultList;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Iterator it = result.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            System.out.println("@@@@@@------ getElementMap check result value ------@@@@@@  " + eleEntry.getKey());
            System.out.println("@@@@@@------ getElementMap check result value ------@@@@@@  " + eleEntry.getValue());
        }
        return result;
    }

    public boolean loadingJudgement(IOSDriver driver, HashMap<String, ElementMapping> eleMap) throws Exception {
        boolean result = true;
        try {
            long gstartTime = 0;
            for (; result == true; ) {
                System.out.println("Start to check whether page is loading, hold on");
                gstartTime = System.currentTimeMillis();
                result = loadingCheckNew(driver, eleMap);
                if (result == false) {
                    break;
                } else {
                    System.out.println("Page is still loading, hold on");
                    if (System.currentTimeMillis() - gstartTime < 1000) {
                        Thread.sleep(1000 - (System.currentTimeMillis() - gstartTime));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public boolean loadingCheckNew(IOSDriver driver, HashMap<String, ElementMapping> extByMap) throws Exception {
        boolean result = false;
        String localFlag = null;
        try {
            for (Iterator it = extByMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase("loadOuter")) {
                    localFlag = new String(tempContentEntry.getKey().toString());
                    result = checkElementPresent(driver, localFlag, extByMap);
                }
            }
            if (localFlag == null) {
                localFlag = new String(readProperity.getProValue("ios_loading_name"));
                String eType = new String(readProperity.getProValue("ios_loading_type"));
                String eStr = new String(readProperity.getProValue("ios_loading_str"));
                String eContent = new String(readProperity.getProValue("ios_loading_content"));
                String eDvalue = new String(readProperity.getProValue("ios_loading_dvalue"));
                result = checkElementPresent(driver, localFlag, eType, eStr, eContent, eDvalue);
            }
            if (result == true) {
                return true;
            } else {
                return false;
            }
        } catch (TimeoutException e) {
            return false;
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean checkElementPresent(IOSDriver driver, String eleName, HashMap<String, ElementMapping> eleMap) throws Exception {
        boolean result = false;
        WebElement tempEle = null;
        try {
            tempEle = getElementFast(driver, eleName, eleMap);
            if (tempEle == null) {
                return false;
            } else if (tempEle.getText().equalsIgnoreCase(getElementXMLContent(eleName, eleMap))) { // || tempEle.getText().equalsIgnoreCase(getElementXMLDvalue(eleName, eleMap))) {
                return true;
            } else {
                return false;
            }
        } catch (TimeoutException e) {
            return false;
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean checkElementPresent(IOSDriver driver, String eleName, String eType, String eStr, String eContent, String eDvalue) throws Exception {
        boolean result = false;
        WebElement tempEle = null;
        try {
            tempEle = getElementFast(driver, eleName, eType, eStr);
            if (tempEle == null) {
                return false;
            } else if (tempEle.getText().equalsIgnoreCase(eContent)) { //|| tempEle.getText().equalsIgnoreCase(eDvalue)) {
                return true;
            } else {
                return false;
            }
        } catch (TimeoutException e) {
            return false;
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public String getContentInBtnFast(IOSDriver driver, String eleName, HashMap<String, By> extByMap) throws Exception {
        WebElement theResult = null;
        try {
            for (Iterator it = extByMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:");

                    IOSDriverWait xWait = new IOSDriverWait(driver, 1, this.path4Log, false);

                    theResult = xWait.until(
                            new IOSExpectedCondition<WebElement>() {
                                public WebElement apply(IOSDriver driver) {
                                    WebElement theX = driver.findElement((By) tempContentEntry.getValue());
                                    //         return theX.getText();
                                    return theX;
                                }
                            });
                }
            }
            if (theResult != null) {
                return theResult.getText();
            } else {
                return "ready";
            }
        } catch (Exception e) {
            return "ready";
        }
    }

    public WebElement getElementFast(IOSDriver driver, String eleName, HashMap<String, ElementMapping> eleMap) throws Exception {
        WebElement theResult = null;
        try {
            final By tempBy = generateByObjWithType(eleName, eleMap);
            IOSDriverWait xWait = new IOSDriverWait(driver, 1, this.path4Log, false);
            theResult = xWait.until(
                    new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement theX = driver.findElement(tempBy);
                            return theX;
                        }
                    });
            if (theResult != null) {
                return theResult;
            } else {
                return null;
            }
        } catch (TimeoutException e) {
            return null;
        } catch (NoSuchElementException e) {
            return null;
        } catch (Exception e) {
            throw e;
        }
    }


    public WebElement getElementFast(IOSDriver driver, String eleName, String eType, String eStr) throws Exception {
        WebElement theResult = null;
        try {
            final By tempBy = generateByObjWithType(eleName, eType, eStr);
            IOSDriverWait xWait = new IOSDriverWait(driver, 1, this.path4Log, false);
            theResult = xWait.until(
                    new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement theX = driver.findElement(tempBy);
                            return theX;
                        }
                    });
            if (theResult != null) {
                return theResult;
            } else {
                return null;
            }
        } catch (TimeoutException e) {
            return null;
        } catch (NoSuchElementException e) {
            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    public void moveUPtest(IOSDriver dr) throws Exception {
        int start = Integer.parseInt(readProperity.getProValue("IOSupOFscreen"));
        int end = Integer.parseInt(readProperity.getProValue("IOSdownOFscreen"));
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        try {
            System.out.println("IIIIIIIIII IN THE moveUPtest");
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(1000);
            temp.t(360, end, 360, start, timeDuration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        }
    }

    public void moveUPwithSyncTime(IOSDriver dr, int inTime) throws Exception {
        int start = Integer.parseInt(readProperity.getProValue("IOSupOFscreen"));
        int end = Integer.parseInt(readProperity.getProValue("IOSdownOFscreen"));

        int timeDuration = 0;
        if (inTime > 1500 || inTime < 750) {
            timeDuration = 1000;
        } else {
            timeDuration = inTime;
        }
        try {
            System.out.println("IIIIIIIIII IN THE moveUPwithSyncTime");
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(timeDuration);
            //temp.t(360, end, 360, start, timeDuration);
            temp.iosSwipe(220, end, 0, (start - end) / 2, timeDuration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        }
    }

    public String btn4XPATHCombind(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap,
                                   HashMap<String, String> textMap, int startIndex) throws Exception {
        String result = null;
        int resIndex = 0;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("    +++ ~~~ In the IPADPageTemplate :: btn4XPATHCombind in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            String titleStr = eleMap.get(eleName).getLocatorStr();
            String titleContent = eleMap.get(eleName).getTextContent();
            String btnStr = eleMap.get(eleName + "Btn").getLocatorStr();
            theResult = findsameID4XPATH(driver, titleStr, btnStr, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, titleContent, startIndex);
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In IPADPageTemplate . btn4XPATHCombind, Other Error appear : " + e.getCause());
            throw e;
        }
        if (theResult != null) {
            result = checkTheNextPage(eleName + "Btn", eleMap);
            theResult.click();
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public By generateByObjWithType(String eleName, HashMap<String, ElementMapping> eleMap) throws Exception {
        By result = null;
        try {
            for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    String new_eleName = new String(tempContentEntry.getKey().toString());
                    String t_type = eleMap.get(new_eleName).getType();
                    String t_str = eleMap.get(new_eleName).getLocatorStr();
                    if (!t_str.contains(":REX")) {
                        if (t_type.equalsIgnoreCase("classname")) {
                            result = By.className(t_str);
                        } else if (t_type.equalsIgnoreCase("xpath")) {
                            result = By.xpath(t_str);
                        } else {
                            throw new XMLException("Wrong XML content for element type : " + new_eleName + " in Page " + this.getClass());
                        }
                    } else {
                        throw new XMLException("Wrong XML content for element locator string : " + new_eleName + " in Page " + this.getClass());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }


    public By generateByObjWithType(String eleName, String eType, String eStr) throws Exception {
        By result = null;
        try {
            if (!eStr.contains(":REX")) {
                if (eType.equalsIgnoreCase("classname")) {
                    result = By.className(eStr);
                } else if (eType.equalsIgnoreCase("xpath")) {
                    result = By.xpath(eStr);
                } else {
                    throw new XMLException("Wrong XML content for element type : " + eleName + " in Page " + this.getClass());
                }
            } else {
                throw new XMLException("Wrong XML content for element locator string : " + eleName + " in Page " + this.getClass());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public String getElementXMLContent(String eleName, HashMap<String, ElementMapping> eleMap) throws Exception {
        String result = null;
        try {
            for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    String new_eleName = new String(tempContentEntry.getKey().toString());
                    result = eleMap.get(new_eleName).getTextContent();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }


    public String getElementXMLDvalue(String eleName, HashMap<String, ElementMapping> eleMap) throws Exception {
        String result = null;
        try {
            for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    String new_eleName = new String(tempContentEntry.getKey().toString());
                    result = eleMap.get(new_eleName).getDefaultValue();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public WebElement findsameID4XPATH(IOSDriver driver, String titleStr, String btnStr, int timeValue, String path, String tText, int startIndex) throws Exception {
        String result = null;
        WebElement tempElement = null;
        WebElement tempBtnElement = null;
        WebElement theEle = null;
        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        int flag4out = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        int theBtY = Integer.parseInt(readProperity.getProValue("IOSdownOFscreen"));
        int theEndY = Integer.parseInt(readProperity.getProValue("IOSscreenLength"));
        boolean flag4end = false;
        int index4lastEle = 0;
        int lastEleNum = 0;
        int currentEleNum = 0;
        String lastEleText4lastSearch = "";
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }
        System.out.println("findsameID4XPATH");
        IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);
        StringBuilder tEleCon = new StringBuilder("");
        Point theP4temp = new Point(0, 0);
        try {
            try {
                tempElement = driver.findElement(By.xpath(titleStr.replaceFirst(":REXindex:", String.valueOf(startIndex))));
                tEleCon.append(tempElement.getText());
                theP4temp = tempElement.getLocation();
            } catch (Exception e) {
                tEleCon.delete(0, tEleCon.length());
            }
            if (tEleCon.toString().equalsIgnoreCase(tText) && theP4temp.getY() != 0 && theP4temp.getY() < theEndY) {
                System.out.println("    +++ ~~~ find the element directly ~~~ +++" + tEleCon);
                tempBtnElement = driver.findElement(By.xpath(btnStr.replaceFirst(":REXindex:", String.valueOf(startIndex))));
            } else {
                startIndex = startIndex - 2;
                for (int ind = startIndex; ind < 33 && index4lastEle < 3; ind++) {
                    try {
                        tempElement = driver.findElement(By.xpath(titleStr.replaceFirst(":REXindex:", String.valueOf(ind))));
                        if (tempElement != null) {
                            index4lastEle = 0;
                            tEleCon.append(tempElement.getText());
                            int tEleY = tempElement.getLocation().getY();
                            if (tEleCon.toString().equalsIgnoreCase(tText) && tEleY > 50 && tEleY < theBtY) {
                                tempBtnElement = driver.findElement(By.xpath(btnStr.replaceFirst(":REXindex:", String.valueOf(ind))));
                                break;
                            } else if (tEleCon.toString().equalsIgnoreCase(tText) && tEleY >= theBtY) {
                                flag4out = 4;
                            } else if (!tEleCon.toString().equalsIgnoreCase(tText) && tEleY >= theBtY) {
                                flag4out++;
                            }
                            if (flag4out > 3) {
                                moveUPs(driver);
                                ind = startIndex;
                            }
                        } else {
                            index4lastEle++;
                        }
                    } catch (Exception e) {
                        index4lastEle++;
                    }
                    System.out.println("try next index");
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return tempBtnElement;
    }


    public void moveUP300(IOSDriver dr) throws Exception {
        int wide = Integer.parseInt(readProperity.getProValue("IOSscreenWide"));
        int length = Integer.parseInt(readProperity.getProValue("IOSscreenLength"));
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        try {
            int start_x = wide / 2;
            int end_x = wide / 2;
            int start_y = (length / 5) * 4;
            int end_y = start_y - 300;
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(500);
            temp.iosSwipe(start_x, start_y, end_x - start_x, 300, timeDuration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        } finally {
            Thread.sleep(500);
        }
    }

    public void moveUPhand(IOSDriver dr) throws Exception {   // fit the page which don't split
        int wide = Integer.parseInt(readProperity.getProValue("IOSscreenWide"));
        int length = Integer.parseInt(readProperity.getProValue("IOSscreenLength"));
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        try {
            int start_x = wide / 2;
            int end_x = wide / 2;
            int start_y = length / 2;
            int end_y = start_y - 100;
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(500);
            temp.iosSwipe(start_x, start_y, end_x - start_x, -150, timeDuration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        } finally {
            Thread.sleep(500);
        }
    }

    public void moveUPs(IOSDriver dr) throws Exception {   // fit the page which don't split
        int wide = Integer.parseInt(readProperity.getProValue("IOSscreenWide"));
        int length = Integer.parseInt(readProperity.getProValue("IOSscreenLength"));
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        if (timeDuration > 1500 || timeDuration < 750) {
            timeDuration = 1000;
        } else {
        }
        try {
            int start_x = wide / 2;
            int end_x = wide / 2;
            int start_y = length / 2;
            int end_y = length / 8;
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(500);
            //  temp.t(start_x, start_y, end_x, end_y, timeDuration);
            temp.iosSwipe(start_x, start_y, end_x - start_x, -100, timeDuration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        } finally {
            Thread.sleep(500);
        }
    }

    public void moveUPsHome(IOSDriver dr) throws Exception {   // fit the page which don't split
        System.out.println(" ~~~ In the moveUPsHome ~~~ ");
        int wide = Integer.parseInt(readProperity.getProValue("IOSscreenWide"));
        int length = Integer.parseInt(readProperity.getProValue("IOSscreenLength"));
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        try {
            int start_x = wide / 2;
            int end_x = wide / 2;
            int start_y = length * 3 / 4;
            int end_y = length / 8;
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(500);
            //  temp.t(start_x, start_y, end_x, end_y, timeDuration);
            temp.iosSwipeHome(start_x, start_y, end_x - start_x, -300, timeDuration);

        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        } finally {
            Thread.sleep(500);
        }
    }

    public void moveDOWNhand(IOSDriver dr) throws Exception {  // fit the page which don't split
        int wide = Integer.parseInt(readProperity.getProValue("IOSscreenWide"));
        int length = Integer.parseInt(readProperity.getProValue("IOSscreenLength"));
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        try {
            int start_x = wide / 2;
            int end_x = wide / 2;
            int start_y = length / 2;
            int end_y = start_y + 100;
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(500);
            temp.iosSwipe(start_x, start_y, end_x - start_x, 150, timeDuration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        } finally {
            Thread.sleep(500);
        }
    }

    public void moveDOWNs(IOSDriver dr) throws Exception {  // fit the page which don't split
        int wide = Integer.parseInt(readProperity.getProValue("IOSscreenWide"));
        int length = Integer.parseInt(readProperity.getProValue("IOSscreenLength"));
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        try {
            int start_x = wide / 2;
            int end_x = wide / 2;
            int start_y = length / 8;
            int end_y = length / 2;
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(500);
            //  temp.t(start_x, start_y, end_x, end_y, timeDuration);
            temp.iosSwipe(start_x, start_y, end_x - start_x, 100, timeDuration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        } finally {
            Thread.sleep(500);
        }
    }

    public void moveDOWN300(IOSDriver dr) throws Exception {
        int wide = Integer.parseInt(readProperity.getProValue("IOSscreenWide"));
        int length = Integer.parseInt(readProperity.getProValue("IOSscreenLength"));
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        try {
            int start_x = wide / 2;
            int end_x = wide / 2;
            int start_y = (length / 5) * 4;
            int end_y = start_y + 300;
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(500);
            temp.iosSwipe(start_x, start_y, end_x - start_x, 300, timeDuration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        } finally {
            Thread.sleep(500);
        }
    }

    public void moveBack(IOSDriver dr, int delta) throws Exception {
        int wide = Integer.parseInt(readProperity.getProValue("IOSscreenWide"));
        int length = Integer.parseInt(readProperity.getProValue("IOSscreenLength"));
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        try {
            int start_x = wide / 2;
            int end_x = wide / 2;
            int start_y = (length / 5) * 2;
            int end_y = start_y + delta;
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(500);
            temp.iosSwipe(start_x, start_y, end_x - start_x, delta, timeDuration);
            // temp.t(start_x, start_y, end_x, end_y, timeDuration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        } finally {
            Thread.sleep(500);
        }
    }

    public void moveUPwithPara(IOSDriver dr, int sX, int sY, int eX, int eY, int Dur) throws Exception {
        int timeDuration = Dur;
        try {
            int start_x = sX;
            int end_x = eX;
            int start_y = sY;
            int end_y = eY;
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(500);
            System.out.println("sY : " + start_y);
            //    System.out.println("eY : " + end_y);
            //   System.out.println("end_y-start_y : " + (end_y - start_y));
            temp.iosSwipe(start_x, start_y, end_x - start_x, -100, timeDuration);
            // temp.iosSwipetest(0,0,0,0,0, null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        } finally {
            Thread.sleep(500);
        }
    }


    public void moveDOWNwithPara(IOSDriver dr, int sX, int sY, int eX, int eY, int Dur) throws Exception {
        int timeDuration = Dur;
        try {
            int start_x = sX;
            int end_x = eX;
            int start_y = sY;
            int end_y = eY;
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(500);
            System.out.println("sY : " + start_y);
            //    System.out.println("eY : " + end_y);
            temp.iosSwipe(start_x, start_y, end_x - start_x, 100, timeDuration);
            //  temp.iosSwipetest(0,0,0,0,0, null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        } finally {
            Thread.sleep(500);
        }
    }


    public void moveUPwithPara(IOSDriver dr, int sX, int sY, int eX, int eY, int Dur, WebElement theE) throws Exception {
        int timeDuration = Dur;
        try {
            int start_x = sX;
            int end_x = eX;
            int start_y = sY;
            int end_y = eY;
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(500);
            System.out.println("sY : " + start_y);
            System.out.println("eY : " + end_y);
            // temp.iosSwipe(start_x, start_y, end_x - start_x, -50, timeDuration);
            temp.iosSwipetest(0, 0, 0, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        } finally {
            Thread.sleep(500);
        }
    }


    public void moveDOWNwithPara(IOSDriver dr, int sX, int sY, int eX, int eY, int Dur, WebElement theE) throws Exception {
        int timeDuration = Dur;
        try {
            int start_x = sX;
            int end_x = eX;
            int start_y = sY;
            int end_y = eY;
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(500);
            System.out.println("sY : " + start_y);
            //    System.out.println("eY : " + end_y);
            //    System.out.println("end_y-start_y : " + (end_y - start_y));
            //    temp.iosSwipe(start_x, start_y, end_x - start_x, 50, timeDuration);
            temp.iosSwipetest(0, 0, 0, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        } finally {
            Thread.sleep(500);
        }
    }


    public void moveUpFitScreen(IOSDriver dr) throws Exception {
        int wide = Integer.parseInt(readProperity.getProValue("IOSscreenWide"));
        int length = Integer.parseInt(readProperity.getProValue("IOSscreenLength"));
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));

        try {
            int start_x = wide / 2;
            int end_x = wide / 2;
            int start_y = (length / 5) * 4;
            int end_y = start_y - (length / 5) * 2;
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(1000);
            temp.t(start_x, start_y, end_x, end_y, timeDuration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        }
    }

    public void moveUPSmart(IOSDriver dr) throws Exception {
        int start = Integer.parseInt(readProperity.getProValue("IOSupOFscreen"));
        int end = Integer.parseInt(readProperity.getProValue("IOSdownOFscreen"));
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        try {
            System.out.println("IIIIIIIIII IN THE moveUPSmart");
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(1000);
            temp.t(360, end, 360, end - 800, timeDuration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        }
    }

    public void moveUPall(IOSDriver dr) throws Exception {
        int start = Integer.parseInt(readProperity.getProValue("IOSupOFscreen"));
        int end = Integer.parseInt(readProperity.getProValue("IOSdownOFscreen"));
        int account = Integer.parseInt(readProperity.getProValue("upallNumber"));
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        try {
            System.out.println("IIIIIIIIII IN THE moveUPall");
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(1000);
            for (; account > 0; account--) {
                temp.t(360, end, 360, start, timeDuration);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        }
    }

    public void moveDOWNall(IOSDriver dr) throws Exception {
        int start = Integer.parseInt(readProperity.getProValue("IOSdownOFscreen"));
        int end = Integer.parseInt(readProperity.getProValue("IOSupOFscreen"));
        int account = Integer.parseInt(readProperity.getProValue("upallNumber"));
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        try {
            System.out.println("IIIIIIIIII IN THE moveDOWNall");
            System.out.println("IIIIIIIIII , start" + end);
            System.out.println("IIIIIIIIII , end" + start);
            System.out.println("IIIIIIIIII , account" + account);
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(1000);
            for (; account > 0; account--) {
                temp.t(360, end, 360, start, timeDuration);
                Thread.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        }
    }


    public void moveDOWNtest(IOSDriver dr) throws Exception {
        int start = Integer.parseInt(readProperity.getProValue("IOSdownOFscreen"));
        int end = Integer.parseInt(readProperity.getProValue("IOSupOFscreen"));
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        try {
            System.out.println("IIIIIIIIII IN THE moveDOWNtest");
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(1000);
            temp.t(360, start, 360, end, timeDuration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        }
    }


    public void refreshPage(IOSDriver dr) throws Exception {
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        try {
            System.out.println("IIIIIIIIII IN THE refreshPage");
            AppBaseOperation temp = new AppBaseOperation(dr);
            //  if (elex.isEnabled())
            // elex.click();
            temp.t(360, 200, 360, 1000, 500);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        }
    }

    public boolean pressDateItem(IOSDriver theD, String theDay, HashMap<String, By> thebyMap) throws Exception {  // type4check : ready, loading, error, temp , etc.  , para4check: target element name, all element, title element
        String targetDay = theDay;
        int roundSco = 5; // 5 in the windows
        boolean flag = false;
        WebElement theItem = null;
        boolean rollUP = false;
        String result = "";
        By tempBy = thebyMap.get("dayRoll");
        if (tempBy != null) {
            System.out.println("dayRoll" + " :!:");
            IOSDriverWait xWait = new IOSDriverWait(theD, timeOutInSeconds, this.path4Log);
            theItem = theD.findElement(tempBy);
        } else {
            for (Iterator it = thebyMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase("dayRoll")) {
                    System.out.println("dayRoll" + " :?:");
                    IOSDriverWait xWait = new IOSDriverWait(theD, timeOutInSeconds, this.path4Log);
                    theItem = theD.findElement((By) tempContentEntry.getValue());
                    break;
                }
            }
        }
        if (theItem == null) {
            throw new REXException("failed to find the dayRoll");
        } else {
            String currentDayStr = theItem.getText();
            int currentDayInt = Integer.parseInt(currentDayStr);
            if (currentDayStr.equals(targetDay)) {
                flag = true;
            } else {
                if (currentDayInt > Integer.parseInt(theDay)) {
                    if (Math.abs(currentDayInt - Integer.parseInt(theDay)) < 15) {
                        rollUP = false;
                    } else {
                        rollUP = true;
                    }
                } else {
                    if (Math.abs(currentDayInt - Integer.parseInt(theDay)) < 15) {
                        rollUP = true;
                    } else {
                        rollUP = false;
                    }
                }
                result = pressTheRound(theD, (IOSElement) theItem, Integer.parseInt(targetDay), rollUP);      // true for up
            }
            System.out.println("Current value in roller is : " + result);
        }

        if (!result.equals("error") && !result.equals("emptyContent")) { //TODO
            flag = true;
        }
        return flag;
    }

/*
    public void textOperation(IOSDriver driver, String eleName, String para1) throws Exception {
        try {
            for (Iterator it = this.byMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds);
                    WebElement theResult = xWait.until(
                            new IOSExpectedCondition<WebElement>() {
                                public WebElement apply(IOSDriver driver) {
                                    return driver.findElement((By) tempContentEntry.getValue());
                                }
                            });
                    theResult.sendKeys(para1);
                }
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw e;
        } catch (FFPandaException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . textOperation 3, Other Error appear : " + e.getCause());
            e.printStackTrace();
        }

    }
    */

    public void textOperationLogin(IOSDriver driver, String eleName, String para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap) throws Exception {
        String dValue = null;
        IOSElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) { // &&theResult==null;
                System.out.println("iterator : " + it == null);
                System.out.println("inMap size : " + inMap.size());
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                        public IOSElement apply(IOSDriver driver) {
                            IOSElement temp = null;
                            try {
                                temp = (IOSElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    theResult.click();
                    Thread.sleep(200);
                    theResult.clear();
                    Thread.sleep(200);
                    theResult.setValue(para1);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void textOperationSendkey(IOSDriver driver, String eleName, String para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap) throws Exception {
        String dValue = null;
        IOSElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) { // &&theResult==null;
                System.out.println("iterator : " + it == null);
                System.out.println("inMap size : " + inMap.size());
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                        public IOSElement apply(IOSDriver driver) {
                            IOSElement temp = null;
                            try {
                                temp = (IOSElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    theResult.click();
                    Thread.sleep(200);
                    theResult.clear();
                    Thread.sleep(200);
                    theResult.sendKeys(para1);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void textOperation(IOSDriver driver, String eleName, String para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap) throws Exception {
        String dValue = null;
        IOSElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) { // &&theResult==null;
                System.out.println("iterator : " + it == null);
                System.out.println("inMap size : " + inMap.size());
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                        public IOSElement apply(IOSDriver driver) {
                            IOSElement temp = null;
                            try {
                                temp = (IOSElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    theResult.click();
                    Thread.sleep(200);
                    theResult.clear();
                    Thread.sleep(200);
                    theResult.setValue(para1);
                    try {
                        driver.hideKeyboard("Done");
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }


    public void textOperationSmart(IOSDriver driver, String eleName, String para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap, final String tText) throws Exception {
        String dValue = null;
        IOSElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) { // &&theResult==null;
                System.out.println("iterator : " + it == null);
                System.out.println("inMap size : " + inMap.size());
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                        public IOSElement apply(IOSDriver driver) {
                            IOSElement temp = null;
                            try {
                                temp = (IOSElement) findFilteEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    theResult.click();
                    Thread.sleep(200);
                    theResult.clear();
                    Thread.sleep(200);
                    theResult.setValue(para1);
                    try {
                        driver.hideKeyboard("Done");
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }


    public String textOperationSmartSaveInput(IOSDriver driver, String eleName, String para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap, final String path) throws Exception {
        String result = null;
        IOSElement theResult = null;
        final String localPath = path;
        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) { // &&theResult==null;
                System.out.println("iterator : " + it == null);
                System.out.println("inMap size : " + inMap.size());
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    for (Iterator iter = dValueMap.entrySet().iterator(); iter.hasNext(); ) {
                        final Map.Entry tempValueEntry = (Map.Entry) iter.next();
                        if (tempValueEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                            final String dValue = (String) tempValueEntry.getValue();
                            IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                            theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                                public IOSElement apply(IOSDriver driver) {
                                    IOSElement temp = null;
                                    try {
                                        temp = (IOSElement) findFilteEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, dValue);
                                    } catch (Exception e) {
                                        throw new TimeoutException(e.getMessage());
                                    }
                                    return temp;
                                }
                            });
                            break;
                        }
                    }

                    theResult.click();
                    Thread.sleep(200);
                    if (!eleName.contains("phone")) {
                        theResult.clear();
                        Thread.sleep(200);
                        theResult.setValue(para1);
                    } else {
                        para1 = new String(para1.substring(0, 3) + "-" + para1.substring(3, 6) + "-" + para1.substring(6, para1.length()));
                        theResult.clear();
                        Thread.sleep(200);
                        theResult.setValue(para1);
                    }
                    try {
                        driver.hideKeyboard("Done");
                    } catch (Exception e) {
                    }
                    Thread.sleep(500);

                    result = theResult.getText();
                    if (result.equals("") || result == null) {
                        result = para1;
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    public String textOperationWithSaveInput(IOSDriver driver, String eleName, String para1, HashMap<String, By> inMap) throws Exception {
        String result = null;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) { // &&theResult==null;
                System.out.println("iterator : " + it == null);
                System.out.println("inMap size : " + inMap.size());
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, this.path4Log);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    theResult.click();
                    Thread.sleep(200);
                    System.out.println("Ready to send the key !!! ");
                    theResult.clear();
                    Thread.sleep(200);
                    theResult.sendKeys(para1);
                    Thread.sleep(200);
                    try {
                        driver.hideKeyboard("Done");
                    } catch (Exception e) {
                    }
                    String tempText = theResult.getText();
                    if (!tempText.equals("") && (tempText != null)) {
                        if (eleName.toLowerCase().contains("phone")) {
                            tempText = new String(tempText.replaceAll("-", ""));
                        }
                        if (tempText.equals(para1)) {
                            result = tempText;
                        } else {
                            throw new REXException("textOperationWithSaveInput : the real input para is different from excepted ");
                        }
                    } else {
                        result = "failedToGetText";
                    }
                }
            }
            Thread.sleep(500);
            try {
                driver.hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done");
            } catch (Exception e) {
                System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
                throw e;
            }

        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . textOperationWithSaveInput, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }

    // (IOSDriver driver, String eleName, String para1, HashMap<String, By> inMap, String dValuePara)
    public String textOperationWithSaveInput(IOSDriver driver, String eleName, String para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap) throws Exception {
        String result = null;
        String dValue = null;
        WebElement theResult = null;
        final String localPath = this.path4Log;

        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());

            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) { //
                System.out.println("iterator : " + it == null);
                System.out.println("inMap size : " + inMap.size());
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    theResult.click();
                    Thread.sleep(200);
                    if (!eleName.contains("phone")) {
                        theResult.clear();
                        Thread.sleep(200);
                        ((IOSElement) theResult).setValue(para1);
                    } else {
                        para1 = new String(para1.substring(0, 3) + "-" + para1.substring(3, 6) + "-" + para1.substring(6, para1.length()));
                        theResult.clear();
                        Thread.sleep(200);
                        ((IOSElement) theResult).setValue(para1);
                    }
                    try {
                        driver.hideKeyboard("Done");
                    } catch (Exception e) {
                    }
                    Thread.sleep(500);
                    result = theResult.getText();
                    if (result.equals("") || result == null) {
                        result = para1;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . textOperationWithSaveInput, Other Error appear : " + e.getCause());
            e.printStackTrace();
            throw e;
        } finally {
            return result;
        }
    }


    public String textOperationWithSaveInput(IOSDriver driver, String eleName, String para1, HashMap<String, By> inMap, String dValuePara) throws Exception {
        String result = null;
        String dValue = dValuePara;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                System.out.println("iterator : " + it == null);
                System.out.println("inMap size : " + inMap.size());
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    theResult.click();
                    Thread.sleep(200);

                    boolean loop4text = false;
                    if ((dValue != "") && (dValue != null)) {
                        if (!theResult.getText().equals(dValue)) {
                            String temp4loopcheck = null;
                            for (String x = theResult.getText(); !x.equals(dValue) && loop4text == false; ) {
                                System.out.println("### Default value of current textfield : " + x);
                                System.out.println("~~~ Default value from page element : " + dValue);
                                int tempTextLength = theResult.getText().length();
                                driver.sendKeyEvent(123);
                                for (int i = 0; i < tempTextLength; i++) {
                                    driver.sendKeyEvent(67);
                                }
                                temp4loopcheck = new String(x);
                                x = theResult.getText();
                                if (temp4loopcheck.equals(x)) {
                                    loop4text = true;
                                }
                            }
                        } else {
                            System.out.println("### Current textfield match " + dValue + " :: " + theResult.getText());
                        }
                    } else if (dValue.equals("")) {
                        for (String x = theResult.getText(); (x.length() != 0) && loop4text == false; ) {
                            int tempTextLength = x.length();
                            System.out.println("### Default value of current textfield : " + x);
                            System.out.println("### Default value of current textfield : " + tempTextLength);
                            System.out.println("~~~ Default value from page element : " + dValue);
                            driver.sendKeyEvent(123);
                            String temp4loopcheck = null;
                            for (int i = 0; i < tempTextLength; i++) {
                                driver.sendKeyEvent(67);
                            }
                            temp4loopcheck = new String(x);
                            x = theResult.getText();
                            if (temp4loopcheck.equals(x)) {
                                loop4text = true;
                            }
                        }
                    }

                    System.out.println("Ready to send the key !!! ");
                    theResult.sendKeys(para1);
                    Thread.sleep(200);
                    try {
                        driver.hideKeyboard("Done");
                    } catch (Exception e) {
                    }
                    String tempText = theResult.getText();
                    if ((tempText != "") && (tempText != null)) {
                        if (eleName.toLowerCase().contains("phone")) {
                            tempText = new String(tempText.replaceAll("-", ""));
                        }
                        if (tempText.equals(para1) || eleName.contains("PSW")) {
                            result = tempText;
                        } else {
                            throw new REXException("textOperationWithSaveInput : the real input para is different from excepted ");
                        }
                    } else {
                        result = "failedToGetText";
                    }
                }
            }
            Thread.sleep(500);
            try {
                driver.hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done");
            } catch (Exception e) {
                System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . textOperationWithSaveInput, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }


    public String checkNormalElement(IOSDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap) throws REXException {
        WebElement theResult = null;
        String result = null;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The comPageCheck in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWaitNOScreen xWait = new IOSDriverWaitNOScreen(driver, 2);
                    theResult = xWait.until(
                            new IOSExpectedCondition<WebElement>() {
                                public WebElement apply(IOSDriver driver) {
                                    return driver.findElement((By) tempContentEntry.getValue());
                                }
                            });
                    break;
                }
            }
            if (theResult != null) {
                String tempDvalue = eleMap.get(eleName).getDefaultValue();
                String tempTextcontnent = eleMap.get(eleName).getTextContent();
                String currentValue = theResult.getText();
                if (compareTheContent(currentValue, tempDvalue) || compareTheContent(currentValue, tempTextcontnent)) {
                    result = "ready";
                } else {
                    result = "error";
                }
            } else {
                result = "error";
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            result = "error";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("The result in template . normal ele is : " + result);
            return result;
        }
    }

    public String autoCompleteOperationSuper(IOSDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L, String tDate, String autoCompleteWin) throws Exception {
        String result = null;
        String calendarValue = null;
        String localTDate = tDate;
        String autoWin = autoCompleteWin;
        System.out.println("    +++ ~~~ The autoCompleteOperationSuper in FFPandaIOSPageTemplate has been called ~~~ +++");
        try {
            result = absCalendarOpenWin(driver, eleName, inMap, eleMap, p4L);

            Class tempClassType = Class.forName(String.valueOf(new StringBuffer("REXSH.REXAUTO.APPunderTest.AppPage.IOS.").append("IOSPage").append(autoWin)));
            System.out.println("@@@ tempClassType : " + tempClassType);
            Object tempIns = createInstance(tempClassType, driver);
            Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{IOSDriver.class, String.class, String.class});
            Object comPageCheckResult = setMethod.invoke(tempIns, driver, "ready", "title");
            if (((String) comPageCheckResult).equals("pass")) {
                Method chooseMethod = tempClassType.getDeclaredMethod("autoCompleteOperation", new Class[]{IOSDriver.class, String.class, String.class});
                Object selectDateResult = chooseMethod.invoke(tempIns, driver, "searchField", localTDate); // chooseDateItem
            } else {
                throw new REXException("Failed to find the yesterday item in the calendar");
            }
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    public String autoCompleteStartMAC(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, final String p4L, String dValue, String extPara) throws Exception {
        String result = null;
        IOSElement theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: searchStartMAC in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                        public IOSElement apply(IOSDriver driver) {
                            IOSElement temp = null;
                            try {
                                temp = (IOSElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    if (dValue.equals("")) {
                        dValue = new String(theResult.getText());
                    }
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    theResult.setValue(extPara);
                    Thread.sleep(200);
                    try {
                        driver.hideKeyboard("Search");
                    } catch (Exception e) {
                    }
                    Thread.sleep(6000);
                    String tempText = theResult.getText();
                    if ((tempText != "") && (tempText != null)) {
                        System.out.println("tempText : " + tempText);
                        if (tempText.equalsIgnoreCase(extPara) || tempText.toLowerCase().contains(extPara.toLowerCase())) {
                            result = new String(tempText);
                        } else if (tempText.equalsIgnoreCase(dValue) || tempText.equals("")) {
                            result = new String(tempText);
                        } else {
                            throw new REXException("searchStartMAC : the real input para is different from excepted ");
                        }
                    } else {
                        result = new String("failedToGetText");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . searchStartMAC, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }


    public String selectCompareItemMAC(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, final String p4L, String extPara, Boolean contentIsolatedClickable) throws Exception {  //TODO
        String result = null;
        List<WebElement> theResultList = new ArrayList<WebElement>();
        WebElement choosedItem = null;
        Thread.sleep(2500);
        final String localPath = p4L;
        String contentInItem = null;
        WebElement item4Index = null;
        int index4Line = 0;
        System.out.println(" this time we want to choose the " + extPara + " item ");
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: selectItemMAC in " + this.getClass().getSimpleName() + " has been called ~~~ +++"); // find all items
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().contains("itemByID")) {
                    System.out.println("itemByID" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                        for (int ind = 1; ind <= 10; ind++) {
                            try {
                                WebElement temp4list = driver.findElement(By.xpath(locaterStr.replaceFirst(":REXitem:", String.valueOf(ind))));
                                if (temp4list != null) {
                                    String temp = temp4list.getText();
                                    if (temp.equalsIgnoreCase(extPara)) {
                                        contentInItem = temp;
                                        choosedItem = temp4list;
                                        if (contentIsolatedClickable == false) {  // non-isolated content and clickable btn
                                            result = choosedItem.getText();
                                            choosedItem.click();
                                        }
                                        break;
                                    } else if (temp.toLowerCase().contains(extPara.toLowerCase())) {
                                        contentInItem = temp;
                                        choosedItem = temp4list;
                                        if (contentIsolatedClickable == false) {  // non-isolated content and clickable btn
                                            result = choosedItem.getText();
                                            choosedItem.click();
                                        }
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            } catch (Exception e) {
                                throw new REXException("Empty search list");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . selectItemMAC, Other Error appear : " + e.getCause());
            e.printStackTrace();
            throw e;
        }
        if (result == null || result == "") {
            result = contentInItem;
        }
        return result;
    }

    public String textOperationListWithSaveInput(IOSDriver driver, String eleName, String para1, HashMap<String, By> inMap, String dValuePara) throws Exception {
        String result = null;
        String dValue = dValuePara;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                System.out.println("iterator : " + it == null);
                System.out.println("inMap size : " + inMap.size());
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    theResult.click();
                    Thread.sleep(200);

                    boolean loop4text = false;
                    if ((dValue != "") && (dValue != null)) {
                        if (!theResult.getText().equals(dValue)) {
                            String temp4loopcheck = null;
                            for (String x = theResult.getText(); !x.equals(dValue) && loop4text == false; ) {
                                System.out.println("### Default value of current textfield : " + x);
                                System.out.println("~~~ Default value from page element : " + dValue);
                                int tempTextLength = theResult.getText().length();
                                driver.sendKeyEvent(123);
                                for (int i = 0; i < tempTextLength; i++) {
                                    driver.sendKeyEvent(67);
                                }
                                temp4loopcheck = new String(x);
                                x = theResult.getText();
                                if (temp4loopcheck.equals(x)) {
                                    loop4text = true;
                                }
                            }
                        } else {
                            System.out.println("### Current textfield match " + dValue + " :: " + theResult.getText());
                        }
                    } else if (dValue.equals("")) {
                        for (String x = theResult.getText(); (x.length() != 0) && loop4text == false; ) {
                            int tempTextLength = x.length();
                            System.out.println("### Default value of current textfield : " + x);
                            System.out.println("### Default value of current textfield : " + tempTextLength);
                            System.out.println("~~~ Default value from page element : " + dValue);
                            driver.sendKeyEvent(123);
                            String temp4loopcheck = null;
                            for (int i = 0; i < tempTextLength; i++) {
                                driver.sendKeyEvent(67);
                            }
                            temp4loopcheck = new String(x);
                            x = theResult.getText();
                            if (temp4loopcheck.equals(x)) {
                                loop4text = true;
                            }
                        }
                    }

                    System.out.println("Ready to send the key !!! ");
                    theResult.sendKeys(para1);
                    Thread.sleep(200);
                    String tempText = theResult.getText();
                    if ((tempText != "") && (tempText != null)) {
                        if (eleName.toLowerCase().contains("phone")) {
                            tempText = new String(tempText.replaceAll("-", ""));
                        }
                        if (tempText.equals(para1)) {
                            result = tempText;
                        } else {
                            throw new REXException("textOperationListWithSaveInput : the real input para is different from excepted ");
                        }
                    } else {
                        result = "failedToGetText";
                    }
                }
            }
            Thread.sleep(500);
            try {
                driver.hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done");
            } catch (Exception e) {
                System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . textOperationListWithSaveInput, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }


    public void getElementContent(IOSDriver driver, String eleName, String para1, HashMap<String, By> inMap) throws Exception {
        final String localPath = this.path4Log;
        WebElement theResult = null;
        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                System.out.println("iterator : " + it == null);
                System.out.println("inMap size : " + inMap.size());
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    theResult.click();
                    System.out.println("Ready to send the key !!! ");
                    theResult.sendKeys(para1);
                    try {
                        driver.hideKeyboard("Done");
                    } catch (Exception e) {
                    }
                }
            }
            Thread.sleep(500);
            try {
                driver.hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done");
            } catch (Exception e) {
                System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . getElementContent, Other Error appear : " + e.getCause());
            throw e;
        }
    }


    public String getElementContent(IOSDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap) throws
            Exception {
        WebElement theResult = null;
        String result = null;
        final String localPath = this.path4Log;
        Thread.sleep(1000);
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: getElementContent ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult =
                            xWait.until(
                                    new IOSExpectedCondition<WebElement>() {
                                        public WebElement apply(IOSDriver driver) {
                                            WebElement searResult = null;
                                            //return driver.findElement((By) tempContentEntry.getValue());
                                            try {
                                                searResult = findElementWithSearch(driver, (By) tempContentEntry.getValue(), 5400, localPath);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            } finally {
                                                return searResult;
                                            }
                                            //   theResult = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                        }
                                    });
                    result = theResult.getText();
                    if (eleName.toLowerCase().contains("phone")) {
                        result = new String(result.replaceAll("-", ""));
                    }
                    break;
                }
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate :: getElementContent , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }


    public WebElement findElementWithSearch(IOSDriver driver, final By theKey, int timeValue, String path) throws Exception {
        WebElement result = null;
        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }
        try {
            for (; result == null; ) {
                try {
                    IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);
                    result = wait4find.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            return driver.findElement(theKey);
                        }
                    });
                    if (result != null) {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("In the findElementWithSearch, saerching ");
                    moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                    Thread.sleep(localTime / ((step + 1) * 4));
                    flag4timeout++;
                }
                if (flag4timeout > 6) {
                    throw new REXException(" The element can't be find after 3 times screen move! ");
                }
            }
            if (result != null) {
                int end = Integer.parseInt(readProperity.getProValue("IOSdownOFscreen"));
                int theY = result.getLocation().getY();
                if (theY > end) {
                    result = null;
                    moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                    result = driver.findElement(theKey);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    public WebElement findElementWithSearchMove(IOSDriver driver, final By theKey, int timeValue, String path, Boolean disable, Boolean able) throws Exception {
        WebElement result = null;
        boolean movecheckdisable = disable;
        boolean moveable = able;
        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        Point lastLocation = null;
        Point currentLocation = null;
        boolean localCheck = false;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }
        if (movecheckdisable == false) {
            for (; localCheck == false; ) {

                lastLocation = currentLocation;
                currentLocation = null;
                result = null;
                try {
                    IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);
                    result = wait4find.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            return driver.findElement(theKey);
                        }
                    });
                    if (result != null) {
                        currentLocation = result.getLocation();
                        if (lastLocation == null) {
                            moveUP300(driver);
                        } else if (currentLocation != null && lastLocation != null) {
                            if (lastLocation.getY() - currentLocation.getY() < 100 || currentLocation.getY() - lastLocation.getY() < 100) {
                                localCheck = true;

                                result = wait4find.until(new IOSExpectedCondition<WebElement>() {
                                    public WebElement apply(IOSDriver driver) {
                                        return driver.findElement(theKey);
                                    }
                                });
                                if (result != null) {
                                    this.content.setFlag4MoveCheck(true);
                                    this.content.setMoveable(false);
                                    break;
                                } else {
                                    throw new PageException("After last move up , the element lost ! ");
                                }
                            } else {
                                moveBack(driver, lastLocation.getY() - currentLocation.getY());
                                result = wait4find.until(new IOSExpectedCondition<WebElement>() {
                                    public WebElement apply(IOSDriver driver) {
                                        return driver.findElement(theKey);
                                    }
                                });
                                this.content.setFlag4MoveCheck(true);
                                this.content.setMoveable(true);
                                break;
                            }
                        } else {
                            moveDOWN300(driver);
                        }
                    } else {
                        System.out.println("In the findElementWithSearch, saerching ");
                        moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                        Thread.sleep(localTime / ((step + 1) * 4));
                        flag4timeout++;
                    }
                } catch (Exception e) {
                    System.out.println("In the findElementWithSearch, saerching ");
                    moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                    Thread.sleep(localTime / ((step + 1) * 4));
                    flag4timeout++;
                }
                if (flag4timeout > 6) {
                    throw new REXException(" The element can't be find after 3 times screen move! ");
                }
            }
        } else if (movecheckdisable == true && moveable == false) {
            try {
                IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);
                result = wait4find.until(new IOSExpectedCondition<WebElement>() {
                    public WebElement apply(IOSDriver driver) {
                        return driver.findElement(theKey);
                    }
                });
            } catch (Exception e) {
                System.out.println("In the findElementWithSearch, static mode ");
                throw new REXException(" The element can't be find in static mode ");
            }
        } else if (movecheckdisable == false && moveable == true) {
            for (; result == null; ) {
                try {
                    IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);
                    result = wait4find.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            return driver.findElement(theKey);
                        }
                    });
                    if (result != null) {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("In the findElementWithSearch, saerching ");
                    moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                    Thread.sleep(localTime / ((step + 1) * 4));
                    flag4timeout++;
                }

            }
        }
        return result;
    }


    public boolean checkMoveable(IOSDriver driver, String eleName, HashMap<String, By> byMap, HashMap<String, ElementMapping> eleContentMap, String path4log) throws Exception {
        WebElement resultEle = null;
        boolean moveable = false;
        String path = path4log;
        By Key = null;
        int step = 2;
        Point lastLocation = null;
        Point currentLocation = null;
        int flag4timeout = 0;
        int localTime = 3000;
        for (Iterator it = byMap.entrySet().iterator(); it.hasNext() && resultEle == null; ) {
            System.out.println("iterator : " + it == null);
            System.out.println("inMap size : " + byMap.size());
            final Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                Key = (By) tempContentEntry.getValue();
            }
        }
        final By theKey = Key;
        for (; resultEle == null && flag4timeout < 3; ) {
            lastLocation = currentLocation;
            currentLocation = null;
            resultEle = null;
            try {
                IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);
                resultEle = wait4find.until(new IOSExpectedCondition<WebElement>() {
                    public WebElement apply(IOSDriver driver) {
                        return driver.findElement(theKey);
                    }
                });
                if (resultEle != null) {
                    currentLocation = resultEle.getLocation();
                    if (lastLocation == null) {
                        moveUPhand(driver);
                        resultEle = null;
                    } else if (currentLocation != null && lastLocation != null) {
                        if (lastLocation.getY() - currentLocation.getY() < 50 || currentLocation.getY() - lastLocation.getY() < 50) {
                            moveable = false;
                            break;
                        } else {
                            moveable = true;
                            moveBack(driver, lastLocation.getY() - currentLocation.getY());
                            break;
                        }
                    } else {
                        moveDOWNhand(driver);
                        resultEle = wait4find.until(new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver driver) {
                                return driver.findElement(theKey);
                            }
                        });
                    }
                } else {
                    System.out.println("In the findElementWithSearch, saerching ");
                    moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                    Thread.sleep(localTime / ((step + 1) * 4));
                    flag4timeout++;
                }
            } catch (Exception e) {
                System.out.println("In the findElementWithSearch, saerching ");
                moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                Thread.sleep(localTime / ((step + 1) * 4));
                flag4timeout++;
            }
        }
        return moveable;
    }


    public WebElement findElementWithOutSearch(IOSDriver driver, final By theKey, int timeValue, String path) throws Exception {
        WebElement result = null;
        int localTime = timeValue;
        int flag4timeout = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }
        try {
            IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / 1000, path, false);
            result = wait4find.until(new IOSExpectedCondition<WebElement>() {
                public WebElement apply(IOSDriver driver) {
                    return driver.findElement(theKey);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }


    public List<WebElement> findEleListWithSearch(IOSDriver driver, final By theKey, int timeValue, String path) throws Exception {
        List<WebElement> result = null;
        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }
        for (; result == null; ) {
            try {
                IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);

                result = wait4find.until(new IOSExpectedCondition<List>() {
                    public List<WebElement> apply(IOSDriver driver) {
                        return driver.findElements(theKey);
                    }
                });
                if (result != null) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("In the findElementWithSearch, saerching ");
                moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                Thread.sleep(localTime / ((step + 1) * 4));
                flag4timeout++;
            }
            if (flag4timeout > 6) {
                throw new REXException(" The element can't be find after 3 times screen move! ");
            }
        }
        return result;
    }


    public String getElementContent(IOSDriver driver, String
            locatorStr, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L,
                                    int TITLEindex) throws Exception {
        String result = null;
        String localStr = null;
        Thread.sleep(1000);
        int steprange = 3;
        final String localPath = p4L;
        WebElement theResult = null;
        String stepINString = null;
        int index4item = 1;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: getElementContent in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (int ind = 0; (ind <= steprange) && (theResult == null); ind++) {
                System.out.println("step ~~~~~~~~~~~~~~~~~~" + ind);
                stepINString = new String(String.valueOf(TITLEindex + ind));
                locatorStr = new String(locatorStr.replaceFirst(":REXindex:", stepINString));
                localStr = new String(locatorStr.replaceFirst(":REXrecord:", String.valueOf(index4item)));
                final By afterReplace = By.xpath(localStr);
                IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                try {
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, afterReplace, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                } catch (Exception e) {
                }
            }
            Thread.sleep(1000);
            result = theResult.getText();
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . getElementContent, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "";
        } else {
            return result;
        }
    }


    public WebElement findFilteEleListWithSearch(IOSDriver driver, final By theKey, int timeValue, String path, String tText) throws Exception {
        List<WebElement> resultlist = null;
        WebElement theEle = null;
        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        int theBtY = Integer.parseInt(readProperity.getProValue("IOSdownOFscreen"));
        boolean flag4end = false;
        int index4lastEle = 0;
        int lastEleNum = 0;
        int currentEleNum = 0;
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }
        int lastY = 0;
        for (; flag4end == false; ) {
            resultlist = null;
            lastEleNum = currentEleNum;
            currentEleNum = 0;
            for (; resultlist == null; ) {
                try {
                    IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);
                    resultlist = wait4find.until(new IOSExpectedCondition<List>() {
                        public List<WebElement> apply(IOSDriver driver) {
                            return driver.findElements(theKey);
                        }
                    });
                    if (resultlist != null) {
                        currentEleNum = resultlist.size();
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("In the findElementWithSearch, saerching ");
                    moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                    Thread.sleep(localTime / ((step + 1) * 4));
                    flag4timeout++;
                }
                if (flag4timeout > 6) {
                    throw new REXException(" The element can't be find after 3 times screen move! ");
                }
            }
            if (currentEleNum == 1) {   // if lastNum ==1 too, then check the text, if same / match target, maybe compare the P.y, then
                WebElement theSingleEle = resultlist.get(0);
                Point tempPero = theSingleEle.getLocation();
                int tempX = tempPero.getX();
                int tempY = tempPero.getY();
                if ((tempY >= theBtY) || tempPero == null || (tempX == 0 && tempY == 0)) {
                    if (lastY != 0) {
                        if (Math.abs(lastY - tempY) < 50) { //should be the end
                            if (theSingleEle.getText().equals(tText)) {  // if exact match
                                theEle = theSingleEle;
                                flag4end = true;
                                break;
                            } else if (theSingleEle.getText().equalsIgnoreCase(tText)) {  // try match
                                System.out.println("The element text in this step is located by fuzzy match! ");
                                theEle = theSingleEle;
                                flag4end = true;
                                break;
                            } else {  // the only ele don;t match
                                continue;
                            }
                        } else {  // continue until end
                            lastY = tempY;
                            moveUPs(driver);
                        }
                    } else {
                        if (tempY != 0) { //should be the end
                            if (theSingleEle.getText().equals(tText)) {  // if exact match
                                theEle = theSingleEle;
                                flag4end = true;
                                break;
                            } else if (theSingleEle.getText().equalsIgnoreCase(tText)) {  // try match
                                System.out.println("The element text in this step is located by fuzzy match! ");
                                theEle = theSingleEle;
                                flag4end = true;
                                break;
                            } else {  // the only ele don;t match
                                continue;
                            }
                        } else {  // continue until end
                            lastY = tempY;
                            moveUPs(driver);
                        }
                    }
                } else {  // the only ele is in the suitable position
                    if (theSingleEle.getText().equals(tText)) {  //exact match
                        theEle = theSingleEle;
                        flag4end = true;
                        break;
                    } else if (theSingleEle.getText().equalsIgnoreCase(tText)) {
                        System.out.println("The element text in this step is located by fuzzy match! ");
                        theEle = theSingleEle;
                        flag4end = true;
                        break;
                    } else {
                        continue;
                    }
                }
            } else {
                if ((currentEleNum != 0 && lastEleNum == 0) || (lastEleNum != 0 && currentEleNum != 0)) {
                    int flag4zero = 0;
                    lastY = 0;
                    for (int ind = index4lastEle; ind < resultlist.size(); ind++) {
                        Point tempP = resultlist.get(ind).getLocation();
                        System.out.println("ind " + ind + " : " + resultlist.get(ind).getText());
                        if ((tempP.getY() >= theBtY) || tempP == null || (tempP.getY() == 0 && tempP.getX() == 0)) {
                            if (tempP.getY() == 0 && tempP.getX() == 0) {
                                if (flag4zero == 0) {
                                    if (ind > 0) {
                                        index4lastEle = ind - 1;
                                    } else {
                                        index4lastEle = 0;
                                    }
                                }
                                flag4zero++;
                            } else {
                                if (ind > 0) {
                                    index4lastEle = ind - 1;
                                } else {
                                    index4lastEle = 0;
                                }
                            }
                            if (lastY != 0 && Math.abs(tempP.getY() - lastY) < 50) {
                                flag4end = true;
                                break;
                            } else if ((flag4zero >= 2) || (tempP.getY() >= theBtY)) {
                                lastY = tempP.getY();
                                moveUPs(driver);
                                if (index4lastEle != 0) {
                                    ind = index4lastEle;
                                }
                            }

                        } else {
                            if (resultlist.get(ind).getText().equals(tText)) {
                                theEle = resultlist.get(ind);
                                flag4end = true;
                                break;
                            } else if (resultlist.get(ind).getText().equalsIgnoreCase(tText)) {
                                System.out.println("The element text in this step is located by fuzzy match! ");
                                theEle = resultlist.get(ind);
                                flag4end = true;
                                break;
                            } else {
                                continue;
                            }
                        }
                    }
                } else {
                    throw new REXException("The current page element list is different from original page elemetn list");
                }
            }
        }

        return theEle;
    }

    public WebElement findFilteEleListWithSearchHome(IOSDriver driver, final By theKey, int timeValue, String path, String tText) throws Exception {
        List<WebElement> resultlist = null;
        WebElement theEle = null;
        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        int theBtY = Integer.parseInt(readProperity.getProValue("IOSdownOFscreen"));
        boolean flag4end = false;
        int index4lastEle = 0;
        int lastEleNum = 0;
        int currentEleNum = 0;
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }
        int lastY = 0;
        for (; flag4end == false; ) {
            resultlist = null;
            lastEleNum = currentEleNum;
            currentEleNum = 0;
            for (; resultlist == null; ) {
                try {
                    IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);
                    resultlist = wait4find.until(new IOSExpectedCondition<List>() {
                        public List<WebElement> apply(IOSDriver driver) {
                            return driver.findElements(theKey);
                        }
                    });
                    if (resultlist != null) {
                        currentEleNum = resultlist.size();
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("In the findElementWithSearch, saerching ");
                    moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                    Thread.sleep(localTime / ((step + 1) * 4));
                    flag4timeout++;
                }
                if (flag4timeout > 6) {
                    throw new REXException(" The element can't be find after 3 times screen move! ");
                }
            }
            if (resultlist.size() == 1) {
                WebElement theSingleEle = resultlist.get(0);
                Point tempPero = theSingleEle.getLocation();
                if ((tempPero.getY() >= theBtY) || tempPero == null || (tempPero.getY() == 0 && tempPero.getX() == 0)) {

                    if (Math.abs(lastY - tempPero.getY()) < 10) {
                        if (theSingleEle.getText().equals(tText)) {
                            theEle = theSingleEle;
                            flag4end = true;
                            break;
                        } else if (theSingleEle.getText().equalsIgnoreCase(tText)) {
                            System.out.println("The element text in this step is located by fuzzy match! ");
                            theEle = theSingleEle;
                            flag4end = true;
                            break;
                        } else {
                            continue;
                        }
                    } else {
                        lastY = tempPero.getY();
                        moveUPsHome(driver);
                    }
                } else {
                    if (theSingleEle.getText().equals(tText)) {
                        theEle = theSingleEle;
                        flag4end = true;
                        break;
                    } else if (theSingleEle.getText().equalsIgnoreCase(tText)) {
                        System.out.println("The element text in this step is located by fuzzy match! ");
                        theEle = theSingleEle;
                        flag4end = true;
                        break;
                    } else {
                        continue;
                    }
                }
            } else {

                if ((currentEleNum != 0 && lastEleNum == 0) || (lastEleNum != 0 && currentEleNum != 0)) {
                    int flag4zero = 0;
                    lastY = 0;
                    for (int ind = index4lastEle; ind < resultlist.size(); ind++) {
                        Point tempP = resultlist.get(ind).getLocation();
                        System.out.println("ind " + ind + " : " + resultlist.get(ind).getText());
                        if ((tempP.getY() >= theBtY) || tempP == null || (tempP.getY() == 0 && tempP.getX() == 0)) {
                            if (tempP.getY() == 0 && tempP.getX() == 0) {
                                if (flag4zero == 0) {
                                    if (ind > 0) {
                                        index4lastEle = ind - 1;
                                    } else {
                                        index4lastEle = 0;
                                    }
                                }
                                flag4zero++;
                            } else {
                                if (ind > 0) {
                                    index4lastEle = ind - 1;
                                } else {
                                    index4lastEle = 0;
                                }
                            }
                            if (lastY != 0 && Math.abs(tempP.getY() - lastY) < 10) {
                                flag4end = true;
                                break;
                            } else if ((flag4zero >= 2) || (tempP.getY() >= theBtY)) {
                                lastY = tempP.getY();
                                moveUPsHome(driver);
                                if (index4lastEle != 0) {
                                    ind = index4lastEle;
                                }
                            }

                        } else {
                            if (resultlist.get(ind).getText().equals(tText)) {
                                theEle = resultlist.get(ind);
                                flag4end = true;
                                break;
                            } else if (resultlist.get(ind).getText().equalsIgnoreCase(tText)) {
                                System.out.println("The element text in this step is located by fuzzy match! ");
                                theEle = resultlist.get(ind);
                                flag4end = true;
                                break;
                            } else {
                                continue;
                            }
                        }
                    }
                } else {
                    throw new REXException("The current page element list is different from original page elemetn list");
                }
            }
        }

        return theEle;
    }


    public static ArrayList<String> getNumList(String str) {
        ArrayList<String> result = new ArrayList<String>();
        String dest = "";
        if (str != null) {
            dest = str.replaceAll("[^0-9]", " ");
            String[] tx = dest.split(" ");
            for (String temp : tx) {
                if (temp.length() != 0) {
                    result.add(temp);
                }
            }
        }
        return result;
    }

    public List<String> getElementContentListMoveDyncIndex(IOSDriver driver, String eleName, String extByStr, String p4L) throws Exception {
        WebElement theResult = null;
        WebElement theendResult = null;
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> tempList = new ArrayList<String>();
        final String localPath = p4L;
        boolean falg4dec = false;
        boolean falg4complete = false;
        By theStrInThisRound = null;
        Thread.sleep(1000);
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: getElementContentListMoveDyncIndex ~~~ +++");
            boolean flag4end = false;
            int account4empty = 0;
            int account4dup = 0;

            for (int ind = 1; flag4end == false && result.size() < 4; ind++) {
                IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                theStrInThisRound = By.xpath(extByStr.replaceFirst(":REXindex:", String.valueOf(ind)));
                //  IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                try {
                    final By byStrInThisRound = theStrInThisRound;
                    theResult =
                            xWait.until(
                                    new IOSExpectedCondition<WebElement>() {
                                        public WebElement apply(IOSDriver driver) {
                                            return driver.findElement(byStrInThisRound);
                                        }
                                    });
                    if (theResult != null) {
                        tempList = new ArrayList<String>(result);

                        result.add(theResult.getText());

                        result = removeDuplicate(result);
                        if (result.size() == tempList.size()) {
                            account4dup++;
                            if (account4dup >= 3) {
                                flag4end = true;
                            }
                        }
                    } else {
                        account4empty++;
                        if (account4empty >= 2) {
                            flag4end = true;
                        }
                        //throw new FFPandaException("No item found in getElementContent FOR contentcheck");
                    }
                    moveUPwithSyncTime(driver, 1000);
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = format.format(date);
                    ScreenShot(driver, "SortContent_" + time + ".png", localPath);
                } catch (Exception e) {
                    account4dup++;
                    moveUPwithSyncTime(driver, 1000);
                }
            }
            if (eleName.toLowerCase().contains("phone")) {
                List<String> tempResult = new ArrayList<String>();
                for (int ind = 0; ind < result.size(); ind++) {
                    tempResult.add(result.get(ind).replaceAll("-", ""));
                }
                result.clear();
                result.addAll(tempResult);
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate :: getElementContentListMoveDyncIndex , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        if (result == null || result.size() == 0) {
            return new ArrayList<String>();
        } else {
            return result;
        }
    }

    public List<String> getElementContentListMove(IOSDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L) throws
            Exception {
        List<WebElement> theResult = null;
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> tempList = new ArrayList<String>();
        final String localPath = p4L;
        Thread.sleep(1000);
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: getElementContentListMove ~~~ +++");
            boolean flag4end = false;
            int account4empty = 0;
            int account4dup = 0;
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    for (; flag4end == false && result.size() < 4; ) {
                        IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                        theResult =
                                xWait.until(
                                        new IOSExpectedCondition<List>() {
                                            public List<WebElement> apply(IOSDriver driver) {
                                                return driver.findElements((By) tempContentEntry.getValue());
                                            }
                                        });
                        if (theResult.size() >= 1) {
                            tempList = new ArrayList<String>(result);
                            for (int ind = 0; ind < theResult.size(); ind++) {
                                result.add(theResult.get(ind).getText());
                            }
                            result = removeDuplicate(result);
                            if (result.size() == tempList.size()) {
                                account4dup++;
                                if (account4dup >= 3) {
                                    flag4end = true;
                                }
                            }
                        } else {
                            account4empty++;
                            if (account4empty >= 2) {
                                flag4end = true;
                            }
                            //throw new FFPandaException("No item found in getElementContent FOR contentcheck");
                        }
                        moveUPwithSyncTime(driver, 1000);
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        ScreenShot(driver, "SortContent_" + time + ".png", localPath);
                    }

                }
            }
            if (eleName.toLowerCase().contains("phone")) {
                List<String> tempResult = new ArrayList<String>();
                for (int ind = 0; ind < result.size(); ind++) {
                    tempResult.add(result.get(ind).replaceAll("-", ""));
                }
                result.clear();
                result.addAll(tempResult);
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate :: getElementContentListMove , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        if (result == null || result.size() == 0) {
            return new ArrayList<String>();
        } else {
            return result;
        }
    }

    public static ArrayList<String> removeDuplicate(ArrayList arlList) {
        LinkedHashSet<String> set = new LinkedHashSet<String>(arlList);
        //Constructing listWithoutDuplicateElements using set
        arlList.clear();
        arlList = new ArrayList<String>(set);
        System.out.println("");
        return arlList;
    }

    public void textListOperationexact(IOSDriver driver, By extTempBy, String
            para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap, String eleName) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        String dValue = null;
        IOSElement theResult = null;
        List<WebElement> resultlist = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());
            boolean flag4match = false;

            final By tempBy = extTempBy;

            resultlist = findEleListWithSearch(driver, tempBy, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);

            if (resultlist.size() != 0) {

                for (Iterator iter = dValueMap.entrySet().iterator(); iter.hasNext() && flag4match == false; ) { // &&dValue==null;
                    for (int ind = 0; ind < resultlist.size() && flag4match == false; ind++) {
                        final Map.Entry tempValueEntry = (Map.Entry) iter.next();
                        if (tempValueEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                            dValue = (String) tempValueEntry.getValue();
                            if (dValue.equalsIgnoreCase(resultlist.get(ind).getText())) {
                                flag4match = true;
                                theResult = (IOSElement) resultlist.get(ind);
                                break;
                            }
                        }
                    }
                }
            }

            if (flag4match == false) {
                theResult = (IOSElement) resultlist.get(1);
            }
            theResult.click();
            Thread.sleep(200);
            System.out.println("Ready to send the key !!! ");
            theResult.clear();
            Thread.sleep(200);
            theResult.setValue(para1);

            Thread.sleep(500);
            try {
                driver.hideKeyboard("Done");
            } catch (Exception e) {
            }
        } catch (Exception e) {
            throw e;
        }
    }


    public void textListOperation(IOSDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        String dValue = null;
        IOSElement theResult = null;
        List<WebElement> resultlist = null;
        final String localPath = this.path4Log;
        boolean flag4match = false;
        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) { // &&theResult==null;
                System.out.println("iterator : " + it == null);
                System.out.println("inMap size : " + inMap.size());
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    resultlist = findEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                    if (resultlist.size() != 0) {
                        for (Iterator iter = dValueMap.entrySet().iterator(); iter.hasNext() && flag4match == false; ) { // &&dValue==null;
                            for (int ind = 0; ind < resultlist.size() && flag4match == false; ind++) {
                                final Map.Entry tempValueEntry = (Map.Entry) iter.next();
                                if (tempValueEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                                    dValue = (String) tempValueEntry.getValue();
                                    if (dValue.equalsIgnoreCase(resultlist.get(ind).getText())) {
                                        flag4match = true;
                                        theResult = (IOSElement) resultlist.get(ind);
                                        break;
                                    }
                                }
                            }
                        }
                        if (flag4match == false) {
                            theResult = (IOSElement) resultlist.get(1);
                        }
                        theResult.click();
                        Thread.sleep(200);
                        System.out.println("Ready to send the key !!! ");
                        theResult.clear();
                        Thread.sleep(200);
                        theResult.setValue(para1);
                        Thread.sleep(500);
                        try {
                            driver.hideKeyboard("Done");
                        } catch (Exception e) {
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }


    public String textListOperationWithSaveInput(IOSDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap) throws Exception {
        return textListOperationWithSaveInputMAC(driver, eleName, para1, inMap, dValueMap);
    }

    public String textListOperationWithSaveInput(IOSDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap, String path4Log) throws Exception {
        return textListOperationWithSaveInputMAC(driver, eleName, para1, inMap, dValueMap, path4Log);
    }

    public String textListOperationWithSaveInputMAC(IOSDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap) throws Exception {
        String result = null;
        String dValue = null;
        List<WebElement> resultlist = null;
        IOSElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) { //
                System.out.println("iterator : " + it == null);
                System.out.println("inMap size : " + inMap.size());
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    resultlist = findEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                    boolean flag4match = false;
                    if (resultlist.size() != 0) {
                        for (Iterator iter = dValueMap.entrySet().iterator(); iter.hasNext() && flag4match == false; ) { // &&dValue==null;
                            for (int ind = 0; ind < resultlist.size() && flag4match == false; ind++) {
                                final Map.Entry tempValueEntry = (Map.Entry) iter.next();
                                if (tempValueEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                                    dValue = (String) tempValueEntry.getValue();
                                    if (dValue.equalsIgnoreCase(resultlist.get(ind).getText())) {
                                        flag4match = true;
                                        theResult = (IOSElement) resultlist.get(ind);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (flag4match == false) {
                        theResult = (IOSElement) resultlist.get(1);
                    }
                    theResult.click();
                    Thread.sleep(200);
                    System.out.println("Ready to set value !!! ");
                    theResult.clear();
                    Thread.sleep(200);
                    theResult.setValue(para1);
                    Thread.sleep(200);
                    try {
                        driver.hideKeyboard("Done");
                    } catch (Exception e) {
                    }
                    String tempText = theResult.getText();

                    if ((tempText != "") && (tempText != null)) {
                        if (eleName.toLowerCase().contains("phone")) {
                            tempText = new String(tempText.replaceAll("-", ""));
                        }
                        if (tempText.equals(para1)) {
                            result = tempText;
                        } else {
                            throw new REXException("textListOperationWithSaveInputMAC : the real input para is different from excepted ");
                        }
                    } else {
                        result = "failedToGetText";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . textListOperationWithSaveInputMAC, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }

    public String textListOperationWithSaveInputMAC(IOSDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap, String p4l) throws Exception {
        String result = null;
        String dValue = null;
        List<WebElement> resultlist = null;
        IOSElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) { //
                System.out.println("iterator : " + it == null);
                System.out.println("inMap size : " + inMap.size());
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    resultlist = findEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                    boolean flag4match = false;
                    if (resultlist.size() != 0) {
                        for (Iterator iter = dValueMap.entrySet().iterator(); iter.hasNext() && flag4match == false; ) { // &&dValue==null;
                            for (int ind = 0; ind < resultlist.size() && flag4match == false; ind++) {
                                final Map.Entry tempValueEntry = (Map.Entry) iter.next();
                                if (tempValueEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                                    dValue = (String) tempValueEntry.getValue();
                                    if (dValue.equalsIgnoreCase(resultlist.get(ind).getText())) {
                                        flag4match = true;
                                        theResult = (IOSElement) resultlist.get(ind);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (flag4match == false) {
                        theResult = (IOSElement) resultlist.get(1);
                    }
                    theResult.click();
                    Thread.sleep(200);
                    System.out.println("Ready to set value !!! ");
                    theResult.clear();
                    Thread.sleep(200);
                    theResult.setValue(para1);
                    Thread.sleep(200);
                    try {
                        driver.hideKeyboard("Done");
                    } catch (Exception e) {
                    }
                    String tempText = theResult.getText();

                    if ((tempText != "") && (tempText != null)) {
                        if (eleName.toLowerCase().contains("phone")) {
                            tempText = new String(tempText.replaceAll("-", ""));
                        }
                        if (tempText.equalsIgnoreCase(para1) || tempText.toLowerCase().contains(para1.toLowerCase())) {
                            result = tempText;
                        } else if (tempText.length() == 0) {
                            result = para1;
                        } else {
                            System.out.println(tempText + " : " + tempText.length());
                            System.out.println(para1 + " : " + para1.length());
                            throw new REXException("textListOperationWithSaveInputMAC : the real input para is different from excepted ");
                        }
                    } else {
                        result = "failedToGetText";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . textListOperationWithSaveInputMAC, Other Error appear : " + e.getCause());
            e.printStackTrace();
            throw e;
        } finally {
            return result;
        }
    }

    public WebElement findFilteEleListWithSearch(IOSDriver driver, final By theKey, int timeValue, String path, String tText, String dValue) throws Exception {
        List<WebElement> resultlist = null;
        WebElement theEle = null;
        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        int theBtY = Integer.parseInt(readProperity.getProValue("IOSdownOFscreen"));
        boolean flag4end = false;
        int index4lastEle = 0;
        int lastEleNum = 0;
        int currentEleNum = 0;
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }

        for (; flag4end == false; ) {
            resultlist = null;
            lastEleNum = currentEleNum;
            currentEleNum = 0;

            for (; resultlist == null; ) {
                try {
                    IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);

                    resultlist = wait4find.until(new IOSExpectedCondition<List>() {
                        public List<WebElement> apply(IOSDriver driver) {
                            return driver.findElements(theKey);
                        }
                    });
                    if (resultlist != null) {
                        currentEleNum = resultlist.size();
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("In the findElementWithSearch, saerching ");
                    moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                    Thread.sleep(localTime / ((step + 1) * 4));
                    flag4timeout++;
                }
                if (flag4timeout > 6) {
                    throw new REXException(" The element can't be find after 3 times screen move! ");
                }
            }

            if ((currentEleNum != 0 && lastEleNum == 0) || (lastEleNum != 0 && currentEleNum != 0)) {
                int flag4zero = 0;
                for (int ind = index4lastEle; ind < resultlist.size(); ind++) {

                    Point tempP = resultlist.get(ind).getLocation();
                    System.out.println("ind " + ind + " : " + resultlist.get(ind).getText());
                    //      System.out.println("ind " + ind + " , X: " + resultlist.get(ind).getLocation().getX());
                    //     System.out.println("ind " + ind + " , Y: " + resultlist.get(ind).getLocation().getY());
                    if ((tempP.getY() >= theBtY) || tempP == null || (tempP.getY() == 0 && tempP.getX() == 0)) {
                        //       WebElement title = findElementWithOutSearch(driver,By.xpath("//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAStaticText[2]"), 1000,path);
                        //     title.click();
                        if (tempP.getY() == 0 && tempP.getX() == 0) {
                            if (flag4zero == 0) {
                                if (ind > 0) {
                                    index4lastEle = ind - 1;
                                } else {
                                    index4lastEle = 0;
                                }
                            }
                            flag4zero++;
                        } else {
                            if (ind > 0) {
                                index4lastEle = ind - 1;
                            } else {
                                index4lastEle = 0;
                            }
                        }
                        if ((flag4zero >= 2) || (tempP.getY() >= theBtY)) {
                            moveUPs(driver);
                            if (index4lastEle != 0) {
                                ind = index4lastEle;
                            }
                        }
                    } else {
                        if (resultlist.get(ind).getText().equals(tText)) {
                            theEle = resultlist.get(ind);
                            flag4end = true;
                            break;
                        } else if (resultlist.get(ind).getText().equalsIgnoreCase(tText)) {
                            System.out.println("The element text in this step is located by fuzzy match! ");
                            theEle = resultlist.get(ind);
                            flag4end = true;
                            break;
                        } else {
                            continue;
                        }
                    }
                }
            } else {
                throw new REXException("The current page element list is different from original page elemetn list");
            }
        }

        return theEle;
    }

/*
    public WebElement findFilteEleListWithSearch(IOSDriver driver, String eleType, String stWithWildCard, int timeValue, String path, String tText) throws Exception {
        List<WebElement> resultlist = null;
        WebElement theEle = null;
        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        int theBtY = Integer.parseInt(readProperity.getProValue("IOSdownOFscreen"));
        boolean flag4end = false;
        int index4lastEle = 0;
        int lastEleNum = 0;
        int currentEleNum = 0;
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }

        for (; flag4end == false; ) {
            resultlist = null;
            lastEleNum = currentEleNum;
            currentEleNum = 0;

            for (; resultlist == null; ) {
                try {
                    IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);

                    resultlist = wait4find.until(new IOSExpectedCondition<List>() {
                        public List<WebElement> apply(IOSDriver driver) {
                            return driver.findElements(theKey);
                        }
                    });
                    if (resultlist != null) {
                        currentEleNum = resultlist.size();
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("In the findElementWithSearch, saerching ");
                    moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                    Thread.sleep(localTime / ((step + 1) * 4));
                    flag4timeout++;
                }
                if (flag4timeout > 6) {
                    throw new FFPandaException(" The element can't be find after 3 times screen move! ");
                }
            }

            if ((currentEleNum != 0 && lastEleNum == 0) || (lastEleNum != 0 && currentEleNum != 0)) {
                int flag4zero = 0;
                for (int ind = index4lastEle; ind < resultlist.size(); ind++) {

                    Point tempP = resultlist.get(ind).getLocation();
                    System.out.println("ind " + ind + " : " + resultlist.get(ind).getText());
                    //      System.out.println("ind " + ind + " , X: " + resultlist.get(ind).getLocation().getX());
                    //     System.out.println("ind " + ind + " , Y: " + resultlist.get(ind).getLocation().getY());
                    if ((tempP.getY() >= theBtY) || tempP == null || (tempP.getY() == 0 && tempP.getX() == 0)) {
                        //       WebElement title = findElementWithOutSearch(driver,By.xpath("//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAStaticText[2]"), 1000,path);
                        //     title.click();
                        if (tempP.getY() == 0 && tempP.getX() == 0) {
                            if (flag4zero == 0) {
                                if (ind > 0) {
                                    index4lastEle = ind - 1;
                                } else {
                                    index4lastEle = 0;
                                }
                            }
                            flag4zero++;
                        } else {
                            if (ind > 0) {
                                index4lastEle = ind - 1;
                            } else {
                                index4lastEle = 0;
                            }
                        }
                        if ((flag4zero >= 2) || (tempP.getY() >= theBtY)) {
                            moveUPs(driver);
                            if (index4lastEle != 0) {
                                ind = index4lastEle;
                            }
                        }
                    } else {
                        if (resultlist.get(ind).getText().equals(tText)) {
                            theEle = resultlist.get(ind);
                            flag4end = true;
                            break;
                        } else if (resultlist.get(ind).getText().equalsIgnoreCase(tText)) {
                            System.out.println("The element text in this step is located by fuzzy match! ");
                            theEle = resultlist.get(ind);
                            flag4end = true;
                            break;
                        } else {
                            continue;
                        }
                    }
                }
            } else {
                throw new FFPandaException("The current page element list is different from original page elemetn list");
            }
        }

        return theEle;
    }
*/

    public WebElement findFilteEleListWithSearchIndex(IOSDriver driver, final By theKey, int timeValue, String path, String tText, int extIndex) throws Exception {
        List<WebElement> resultlist = null;
        WebElement theEle = null;
        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        boolean flag4end = false;
        int currentEleNum = 0;
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }

        for (; resultlist == null; ) {
            try {
                IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);

                resultlist = wait4find.until(new IOSExpectedCondition<List>() {
                    public List<WebElement> apply(IOSDriver driver) {
                        return driver.findElements(theKey);
                    }
                });
                if (resultlist != null) {
                    currentEleNum = resultlist.size();
                    break;
                }
            } catch (Exception e) {
                System.out.println("In the findElementWithSearch, saerching ");
                moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                Thread.sleep(localTime / ((step + 1) * 4));
                flag4timeout++;
            }
            if (flag4timeout > 6) {
                throw new REXException(" The element can't be find after 3 times screen move! ");
            }
        }

        if (currentEleNum != 0) {
            for (int ind = extIndex - 1; ind < resultlist.size() && flag4end == false; ind++) {
                System.out.println("ind " + ind + " : " + resultlist.get(ind).getText());
                if (resultlist.get(ind).getText().equals(tText)) {
                    theEle = resultlist.get(ind);
                    flag4end = true;
                    break;
                } else if (resultlist.get(ind).getText().equalsIgnoreCase(tText)) {
                    System.out.println("The element text in this step is located by fuzzy match! ");
                    theEle = resultlist.get(ind);
                    flag4end = true;
                    break;
                } else {
                    continue;
                }
            }
        } else {
            throw new REXException("The current page element list is different from original page elemetn list");
        }
        return theEle;
    }


    public WebElement findFilteNextEleListWithSearchIndex(IOSDriver driver, final By theKey, int timeValue, String path, String tText, int extIndex) throws Exception {
        List<WebElement> resultlist = null;
        WebElement theEle = null;
        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        boolean flag4end = false;
        int currentEleNum = 0;
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }

        for (; resultlist == null; ) {
            try {
                IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);

                resultlist = wait4find.until(new IOSExpectedCondition<List>() {
                    public List<WebElement> apply(IOSDriver driver) {
                        return driver.findElements(theKey);
                    }
                });
                if (resultlist != null) {
                    currentEleNum = resultlist.size();
                    break;
                }
            } catch (Exception e) {
                System.out.println("In the findElementWithSearch, saerching ");
                moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                Thread.sleep(localTime / ((step + 1) * 4));
                flag4timeout++;
            }
            if (flag4timeout > 6) {
                throw new REXException(" The element can't be find after 3 times screen move! ");
            }
        }

        if (currentEleNum != 0) {
            for (int ind = extIndex - 1; ind + 1 < resultlist.size() && flag4end == false; ind++) {
                System.out.println("ind " + ind + " : " + resultlist.get(ind).getText());
                if (resultlist.get(ind).getText().equals(tText)) {
                    theEle = resultlist.get(ind + 1);
                    flag4end = true;
                    break;
                } else if (resultlist.get(ind).getText().equalsIgnoreCase(tText)) {
                    System.out.println("The element text in this step is located by fuzzy match! ");
                    theEle = resultlist.get(ind + 1);
                    flag4end = true;
                    break;
                } else {
                    continue;
                }
            }
        } else {
            throw new REXException("The current page element list is different from original page elemetn list");
        }
        return theEle;
    }


    public WebElement findFilteEleWithSearch(IOSDriver driver, String eleName, int timeValue, String path, String tText, HashMap<String, ElementMapping> eleMap, String btnStr) throws Exception {
        List<WebElement> resultlist = null;
        WebElement theEle = null;
        WebElement theBtnEle = null;

        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        boolean flag4end = false;
        int currentEleIndex = 0;
        String tempEleStr = null;
        String localPath = path;
        boolean falg4loop = false;
        int titleY = 0;
        int btnY = 0;
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }
        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext() && tempEleStr == null; ) {
            final Map.Entry tempEleEntry = (Map.Entry) it.next();
            if (tempEleEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                System.out.println(eleName + " ::");
                tempEleStr = ((ElementMapping) tempEleEntry.getValue()).getLocatorStr(); //
            }
        }

        for (int index = 1; index < 50 && falg4loop == false; index++) {
            try {
                IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);
                final By temp = By.xpath(tempEleStr.replaceFirst(":REXindex:", String.valueOf(index)));
                theEle = wait4find.until(new IOSExpectedCondition<WebElement>() {
                    public WebElement apply(IOSDriver driver) {
                        return driver.findElement(temp);
                    }
                });
                if (theEle != null) {
                    System.out.println(theEle.getText());
                    if (theEle.getText().equals(tText)) {
                        titleY = theEle.getLocation().getY();
                        falg4loop = true;
                        currentEleIndex = index;
                        break;
                    } else {
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("In the findElementWithSearch, saerching ");
            }
        }


        if (currentEleIndex != 0) {
            try {
                IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);
                final By temp = By.xpath(btnStr.replaceFirst(":REXindex:", String.valueOf(currentEleIndex)));
                theBtnEle = wait4find.until(new IOSExpectedCondition<WebElement>() {
                    public WebElement apply(IOSDriver driver) {
                        return driver.findElement(temp);
                    }
                });
                if (theBtnEle != null) {
                    btnY = theBtnEle.getLocation().getY();
                    if (Math.abs(titleY - btnY) >= 30) {
                        System.out.println("This time we might match the wrong BTN, the Delta in Y is >=30 ");
                    }
                }
            } catch (Exception e) {
                throw new REXException("the index from title failed to match the btn xpath ");
            }
        } else {
            throw new REXException("the index form xpath is ZERO! ");
        }


        return theBtnEle;
    }


    public WebElement findFilteEleWithLocationCompare(IOSDriver driver, String eleName, int timeValue, String path, String tText, HashMap<String, ElementMapping> eleMap, int extTitleY, int extNextTitleY, int ind4ClassName) throws Exception {
        List<WebElement> resultlist = null;
        WebElement theEle = null;
        WebElement theBtnEle = null;

        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        boolean flag4end = false;
        int currentEleIndex = 0;
        String tempEleStr = null;
        String localPath = path;
        boolean falg4loop = false;
        int titleY = extTitleY;
        int nextTitleY = extNextTitleY;
        int btnY = 0;
        //  By temp = null;
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }
        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext() && tempEleStr == null; ) {
            final Map.Entry tempEleEntry = (Map.Entry) it.next();
            if (tempEleEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                System.out.println(eleName + " ::");
                tempEleStr = ((ElementMapping) tempEleEntry.getValue()).getLocatorStr(); //
            }
        }
/*
        for (int index = 1; index < 50 && falg4loop == false; index++) {
            try {
                IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);
                final By temp = By.xpath(tempEleStr.replaceFirst(":REXindex:", String.valueOf(index)));
                theEle = wait4find.until(new IOSExpectedCondition<WebElement>() {
                    public WebElement apply(IOSDriver driver) {
                        return driver.findElement(temp);
                    }
                });
                if (theEle != null) {
                    System.out.println(theEle.getText());
                    if (theEle.getText().equals(tText)) {
                        titleY = theEle.getLocation().getY();
                        falg4loop = true;
                        currentEleIndex = index;
                        break;
                    } else {
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("In the findElementWithSearch, saerching ");
            }
        }

*/
        //    if (currentEleIndex != 0) {
        if (tempEleStr.contains(":REXrecord:") && !tempEleStr.contains(":REXindex:")) {
            for (int index = 1; index < 50 && falg4loop == false; index++) {
                try {
                    IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);
                    final By temp = By.xpath(tempEleStr.replaceFirst(":REXrecord:", String.valueOf(index)));
                    theBtnEle = wait4find.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            return driver.findElement(temp);
                        }
                    });

                    if (theBtnEle != null) {
                        btnY = theBtnEle.getLocation().getY();
                        if (btnY > titleY && btnY < nextTitleY) {
                            if (btnY - titleY <= 50) {
                                falg4loop = true;
                                System.out.println("This time we match the BTN with the Delta in Y is <=50 ");
                            }
                        }
                    }
                    System.out.println("Try next ");
                } catch (Exception e) {
                    System.out.println("Try next index");
                    //  throw new FFPandaException("the index from title failed to match the btn xpath ");
                }
            }
        } else {
            throw new RuntimeException("Code for handle index+record not ready , IN findFilteEleWithLocationCompare ");
        }
        //     } else {
        //         throw new FFPandaException("the index form xpath is ZERO! ");
        //    }
        return theBtnEle;
    }


    public int findID4ClassNameWithSearch(IOSDriver driver, final By theKey, int timeValue, String path, String tText) throws Exception {
        int id4classname = 0;
        List<WebElement> resultlist = null;
        WebElement theEle = null;
        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        int theBtY = Integer.parseInt(readProperity.getProValue("IOSdownOFscreen"));
        boolean flag4end = false;
        int index4lastEle = 0;
        int lastEleNum = 0;
        int currentEleNum = 0;
        String lastEleText4lastSearch = "";
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }
        for (; flag4end == false; ) {
            resultlist = null;
            lastEleNum = currentEleNum;
            currentEleNum = 0;
            boolean flag4unlucky = false;
            for (; resultlist == null; ) {
                try {
                    IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);
                    resultlist = wait4find.until(new IOSExpectedCondition<List>() {
                        public List<WebElement> apply(IOSDriver driver) {
                            return driver.findElements(theKey);
                        }
                    });
                    if (resultlist != null) {
                        currentEleNum = resultlist.size();
                        break;
                    }
                } catch (Exception e) {
                    moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                    Thread.sleep(localTime / ((step + 1) * 4));
                    flag4timeout++;
                }
                if (flag4timeout > 6) {
                    throw new REXException(" The element can't be find after 3 times screen move! ");
                }
            }
            if (currentEleNum != 0) {
                int flag4zero = 0;
                boolean flag4rightScreen = false;
                if (index4lastEle != 0) {
                    if (lastEleText4lastSearch.equals(resultlist.get(index4lastEle).getText())) {
                        System.out.println("Lucky enough to start at last index : " + index4lastEle);
                    } else {
                        index4lastEle = index4lastEle - 2;
                    }
                    if (flag4unlucky == true) {
                        index4lastEle = index4lastEle - 2;
                        flag4unlucky = false;
                    } else {
                        index4lastEle = index4lastEle - 1;
                    }
                } else {
                    index4lastEle = 0;
                }
                flag4zero = 0;
                int lastEleY = 1;
                for (int ind = index4lastEle; ind < resultlist.size(); ind++) {
                    if (flag4rightScreen == false) {
                        flag4zero = 0;
                    }
                    WebElement tEleInLoop = resultlist.get(ind);
                    Point tempP = tEleInLoop.getLocation();
                    String tempText = tEleInLoop.getText();
                    int tempY = tempP.getY();
                    System.out.println("index " + ind + " : " + tempText + " : " + tempP.getX() + " : " + tempY);
                    if (resultlist.get(ind).getText().equalsIgnoreCase(tText)) {
                        if (tempY != 0 && tempY <= theBtY && tempP.getX() != 0) {
                            theEle = resultlist.get(ind);
                            flag4end = true;
                            id4classname = ind;
                            //   theEle.click();
                            break;
                        } else {
                            flag4unlucky = true;
                            lastEleY = tempY;
                            if (flag4rightScreen == true) {
                                flag4zero++;
                            } else {
                                flag4zero = 0;
                            }
                        }
                    } else if (resultlist.get(ind).getText().equals("")) {
                        if (tempY != lastEleY) {
                            if ((tempY > theBtY) || tempY == 0 || tempP.getX() == 0) {
                                if (flag4rightScreen == true) {
                                    flag4zero++;
                                } else {
                                    flag4zero = 0;
                                }
                            }
                        } else {
                            lastEleText4lastSearch = new String(tempText);
                            moveUPs(driver);
                            break;
                        }
                        if ((flag4zero > 2) || (tempY >= theBtY)) {
                            if (ind > 0) {
                                index4lastEle = ind;
                            } else {
                                index4lastEle = 0;
                            }
                            lastEleText4lastSearch = new String(tempText);
                            moveUPs(driver);
                            break;
                        }
                        lastEleY = tempY;
                    } else if (tempText.toLowerCase().contains("add icon")) {
                        continue;
                    } else {
                        if (tempP.getX() != 0) {
                            flag4rightScreen = true;
                            if ((tempY > theBtY) || (tempY == 0 && tempP.getX() == 0)) {
                                if (flag4rightScreen == true) {
                                    flag4zero++;
                                } else {
                                    flag4zero = 0;
                                }
                            } else {
                                flag4rightScreen = true;
                            }
                            if ((flag4zero > 2) || (tempY >= theBtY && tempP.getX() != 0)) {
                                if (ind > 0) {
                                    index4lastEle = ind;
                                } else {
                                    index4lastEle = 0;
                                }
                                lastEleText4lastSearch = new String(tempText);
                                moveUPs(driver);
                                break;
                            }
                            lastEleY = tempY;
                        } else {
                            flag4rightScreen = false;
                        }

                    }
                }
            } else {
                throw new REXException("The current page element list is different from original page elemetn list");
            }
        }
        return id4classname;
    }


    public int findANDClick4ClassNameWithSearch(IOSDriver driver, final By theKey, int timeValue, String path, String tText) throws Exception {
        int id4classname = 0;
        List<WebElement> resultlist = null;
        WebElement theEle = null;
        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        int theBtY = Integer.parseInt(readProperity.getProValue("IOSdownOFscreen"));
        boolean flag4end = false;
        int index4lastEle = 0;
        int lastEleNum = 0;
        int currentEleNum = 0;
        String lastEleText4lastSearch = "";
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }
        for (; flag4end == false; ) {
            resultlist = null;
            lastEleNum = currentEleNum;
            currentEleNum = 0;
            boolean flag4unlucky = false;
            for (; resultlist == null; ) {
                try {
                    IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);

                    resultlist = wait4find.until(new IOSExpectedCondition<List>() {
                        public List<WebElement> apply(IOSDriver driver) {
                            return driver.findElements(theKey);
                        }
                    });
                    if (resultlist != null) {
                        currentEleNum = resultlist.size();
                        break;
                    }
                } catch (Exception e) {
                    //      System.out.println("In the findElementWithSearch, saerching ");
                    moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                    Thread.sleep(localTime / ((step + 1) * 4));
                    flag4timeout++;
                }
                if (flag4timeout > 6) {
                    throw new REXException(" The element can't be find after 3 times screen move! ");
                }
            }
            if (currentEleNum != 0) {
                int flag4zero = 0;
                boolean flag4rightScreen = false;
                if (index4lastEle != 0) {
                    if (lastEleText4lastSearch.equals(resultlist.get(index4lastEle).getText())) {
                        System.out.println("Lucky enough to start at last index : " + index4lastEle);
                        if (flag4unlucky == true) {
                            index4lastEle = index4lastEle - 2;
                            flag4unlucky = false;
                        } else {
                            index4lastEle = index4lastEle - 1;
                        }
                    } else {
                        if (flag4unlucky == true) {
                            index4lastEle = index4lastEle - 3;
                            flag4unlucky = false;
                        } else {
                            index4lastEle = index4lastEle - 2;
                        }
                    }
                } else {
                    index4lastEle = 0;
                }
                flag4zero = 0;
                int lastEleY = 1;
                for (int ind = index4lastEle; ind < resultlist.size(); ind++) {
                    if (flag4rightScreen == false) {
                        flag4zero = 0;
                    }
                    WebElement tEleInLoop = resultlist.get(ind);
                    Point tempP = tEleInLoop.getLocation();
                    String tempText = tEleInLoop.getText();
                    int tempY = tempP.getY();
                    System.out.println("index " + ind + " : " + tempText + " : " + tempP.getX() + " : " + tempY);
                    if (resultlist.get(ind).getText().equalsIgnoreCase(tText)) {
                        if (tempY != 0 && tempY <= theBtY && tempP.getX() != 0) {
                            theEle = resultlist.get(ind);
                            flag4end = true;
                            id4classname = ind;
                            theEle.click();
                            break;
                        } else {
                            flag4unlucky = true;
                            lastEleY = tempY;
                            if (flag4rightScreen == true) {
                                flag4zero++;
                            } else {
                                flag4zero = 0;
                            }
                        }
                    } else if (resultlist.get(ind).getText().equals("")) {
                        if (tempY != lastEleY) {
                            if ((tempY > theBtY) || tempY == 0 || tempP.getX() == 0) {
                                if (flag4rightScreen == true) {
                                    flag4zero++;
                                } else {
                                    flag4zero = 0;
                                }
                            }
                        } else {
                            lastEleText4lastSearch = new String(tempText);
                            moveUPs(driver);
                            break;
                        }
                        if ((flag4zero > 2) || (tempY >= theBtY)) {
                            if (ind > 0) {
                                index4lastEle = ind;
                            } else {
                                index4lastEle = 0;
                            }
                            lastEleText4lastSearch = new String(tempText);
                            moveUPs(driver);
                            break;
                        }
                        lastEleY = tempY;
                    } else if (tempText.toLowerCase().contains("add icon")) {
                        continue;
                    } else {
                        if (tempP.getX() != 0) {
                            flag4rightScreen = true;
                            if ((tempY > theBtY) || (tempY == 0 && tempP.getX() == 0)) {
                                if (flag4rightScreen == true) {
                                    flag4zero++;
                                } else {
                                    flag4zero = 0;
                                }
                            } else {
                                flag4rightScreen = true;
                            }
                            if ((flag4zero > 2) || (tempY >= theBtY && tempP.getX() != 0)) {
                                if (ind > 0) {
                                    index4lastEle = ind;
                                } else {
                                    index4lastEle = 0;
                                }
                                lastEleText4lastSearch = new String(tempText);
                                moveUPs(driver);
                                break;
                            }
                            lastEleY = tempY;
                        } else {
                            flag4rightScreen = false;
                        }

                    }
                }


/*
            if (currentEleNum != 0) {

                int flag4zero = 0;
                boolean flag4rightScreen = false;
                if (index4lastEle != 0) {
                    if (lastEleText4lastSearch.equals(resultlist.get(index4lastEle).getText())) {
                        System.out.println("Lucky enough to start at last index : " + index4lastEle);
                    } else {
                        index4lastEle = index4lastEle - 2;
                    }
                } else {
                    index4lastEle = 0;
                }
                for (int ind = index4lastEle; ind < resultlist.size(); ind++) {
                    if (flag4rightScreen == false) {
                        flag4zero = 0;
                    }
                    WebElement tEleInLoop = resultlist.get(ind);
                    Point tempP = tEleInLoop.getLocation();
                    String tempText = tEleInLoop.getText();
                    int tempY = tempP.getY();
                    System.out.println("index " + ind + " : " + tempText);

                    if ((tempY >= theBtY) || tempY == 0 && tempP.getX() == 0) {
                        if (!tempText.toLowerCase().contains("add icon")) {
                            if (tempY == 0 && tempP.getX() == 0) {
                                if (flag4rightScreen == true) {
                                    flag4zero++;
                                } else {
                                    flag4zero = 0;
                                }
                            }
                            if ((flag4zero > 2) || (tempY >= theBtY)) {
                                if (ind > 0) {
                                    index4lastEle = ind;
                                } else {
                                    index4lastEle = 0;
                                }
                                lastEleText4lastSearch = new String(tempText);
                                moveUPs(driver);
                                break;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        if (tempY > 0 && tempY < theBtY) {
                            if (tempText.toLowerCase().contains("cancel") || tempText.toLowerCase().contains("clear") ||
                                    tempText.toLowerCase().contains("save") || tempText.toLowerCase().contains("edit") ||
                                    tempText.toLowerCase().contains("profile") || tempText.toLowerCase().contains("done")
                                    || tEleInLoop.getLocation().getY() < 50) {
                                flag4rightScreen = false;
                            } else {
                                flag4rightScreen = true;
                            }
                        } else {
                            flag4rightScreen = false;
                        }
                        if (resultlist.get(ind).getText().equals(tText)) {
                            theEle = resultlist.get(ind);
                            flag4end = true;
                            id4classname = ind;
                            theEle.click();
                            break;
                        } else if (resultlist.get(ind).getText().equalsIgnoreCase(tText)) {
                            System.out.println("The element text in this step is located by fuzzy match! ");
                            theEle = resultlist.get(ind);
                            flag4end = true;
                            id4classname = ind;
                            theEle.click();
                            break;
                        } else {

                            continue;
                        }
                    }
                }

                */

            } else {
                throw new REXException("The current page element list is different from original page elemetn list");
            }
        }
        return id4classname;
    }


    public int findID4ClassNameWithSearch(IOSDriver driver, final By theKey, int timeValue, String path, String tText, PageIndexGroup indexGroupFromPage) throws Exception {
        int id4classname = 0;
        List<WebElement> resultlist = null;
        WebElement theEle = null;
        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        int theBtY = Integer.parseInt(readProperity.getProValue("IOSdownOFscreen"));
        boolean flag4end = false;
        int index4lastEle = 0;
        int lastEleNum = 0;
        int currentEleNum = 0;
        String lastEleText4lastSearch = "";
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }

        for (; flag4end == false; ) {
            resultlist = null;
            lastEleNum = currentEleNum;
            currentEleNum = 0;
            for (; resultlist == null; ) {
                try {
                    IOSDriverWait wait4find = new IOSDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);

                    resultlist = wait4find.until(new IOSExpectedCondition<List>() {
                        public List<WebElement> apply(IOSDriver driver) {
                            return driver.findElements(theKey);
                        }
                    });
                    if (resultlist != null) {
                        currentEleNum = resultlist.size();
                        break;
                    }
                } catch (Exception e) {
                    moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                    Thread.sleep(localTime / ((step + 1) * 4));
                    flag4timeout++;
                }
                if (flag4timeout > 6) {
                    throw new REXException(" The element can't be find after 6 times screen move! ");
                }
            }
            if (currentEleNum != 0) {
                int flag4zero = 0;
                boolean flag4rightScreen = false;
                if (index4lastEle != 0) {
                    if (lastEleText4lastSearch.equals(resultlist.get(index4lastEle).getText())) {
                        System.out.println("Lucky enough to start at last index : " + index4lastEle);
                    } else {
                        index4lastEle = index4lastEle - 2;
                    }
                } else {
                    index4lastEle = 0;
                }
                for (int ind = index4lastEle; ind < resultlist.size(); ind++) {
                    if (flag4rightScreen == false) {
                        flag4zero = 0;
                    }
                    WebElement tEleInLoop = resultlist.get(ind);
                    Point tempP = tEleInLoop.getLocation();
                    String tempText = tEleInLoop.getText();
                    int tempY = tempP.getY();
                    System.out.println("index " + ind + " : " + tempText);

                    if ((tempY >= theBtY) || tempY == 0 && tempP.getX() == 0) {
                        if (!tempText.toLowerCase().contains("add icon")) {
                            if (tempY == 0 && tempP.getX() == 0) {
                                if (flag4rightScreen == true) {
                                    flag4zero++;
                                } else {
                                    flag4zero = 0;
                                }
                            }
                            if ((flag4zero > 2) || (tempY >= theBtY)) {
                                if (ind > 0) {
                                    index4lastEle = ind;
                                } else {
                                    index4lastEle = 0;
                                }
                                lastEleText4lastSearch = new String(tempText);
                                moveUPs(driver);
                                break;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        if (tempY > 0 && tempY < theBtY) {
                            if (tempText.toLowerCase().contains("cancel") || tempText.toLowerCase().contains("clear") ||
                                    tempText.toLowerCase().contains("save") || tempText.toLowerCase().contains("edit") ||
                                    tempText.toLowerCase().contains("profile") || tempText.toLowerCase().contains("done")
                                    || tEleInLoop.getLocation().getY() < 50) {
                                flag4rightScreen = false;
                            } else {
                                flag4rightScreen = true;
                            }
                        } else {
                            flag4rightScreen = false;
                        }
                        if (resultlist.get(ind).getText().equals(tText)) {
                            theEle = resultlist.get(ind);
                            flag4end = true;
                            id4classname = ind;
                            break;
                        } else if (resultlist.get(ind).getText().equalsIgnoreCase(tText)) {
                            System.out.println("The element text in this step is located by fuzzy match! ");
                            theEle = resultlist.get(ind);
                            flag4end = true;
                            id4classname = ind;
                            break;
                        } else {

                            continue;
                        }
                    }
                }

            } else {
                throw new REXException("The current page element list is different from original page element list");
            }
        }
        return id4classname;
    }

    public List<WebElement> findEleListByIndexWithSearch(IOSDriver driver, String theKey, int timeValue, String
            path) throws Exception {
        List<WebElement> result = new ArrayList<WebElement>();
        result.clear();
        WebElement singleTemp = null;
        String localStr = theKey;
        int range = 1;
        boolean endOFList = false;
        for (; endOFList == false; range++) {
            try {
                singleTemp = driver.findElement(By.xpath(localStr.replaceFirst(":REXitem:", String.valueOf(range))));
                result.add(singleTemp);
            } catch (Exception e) {
                endOFList = true;
            }
        }
        return result;
    }

    public void btnOperation(IOSDriver driver, String eleName) throws Exception {
        final String localPath = this.path4Log;
        WebElement theResult = null;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The btnOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = this.byMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    theResult.click();
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnOperation, Other Error appear : " + e.getCause());
            throw e;
        }
    }


    /*
    public void btnOperation(IOSDriver driver, String eleName, HashMap<String, By> inMap) throws Exception {
        final String localPath = this.path4Log;
        WebElement theResult = null;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The btnOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    theResult.click();
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnOperation 2, Other Error appear : " + e.getCause());
            throw e;
        }
    }
*/

    public void btnOperation(IOSDriver driver, String eleName, HashMap<String, By> inMap) throws Exception {
        final String localPath = this.path4Log;
        WebElement theResult = null;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The btnOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    theResult.click();
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnOperation, Other Error appear : " + e.getCause());
            throw e;
        }
    }

    public void testOperation(IOSDriver driver, String theIN) throws Exception {
        WebElement theResult = null;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The testOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            theResult = driver.findElement(By.id(theIN));
            System.out.println("For IOS , get the text before click : " + theResult.getText());
            theResult.click();
        } catch (Exception e)

        {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . testOperation, Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
    }


    public Object createInstance(Class cType, IOSDriver theD) throws Exception {
        System.out.println("Check the cType : " + cType.toString());
        Object result = null;
        Constructor<?>[] consts = cType.getConstructors();
        Constructor<?> constructor = null;
        for (int index = 0; index < consts.length; index++) {
            int paramsLength = consts[index].getParameterAnnotations().length;
            if (paramsLength == 2) {
                System.out.println("@@@ Find the constructor with 2 para : " + consts[index]);
                constructor = consts[index];
                break;
            }
        }
        if (constructor != null) {
            Class<?>[] type = constructor.getParameterTypes();
            System.out.println("What is this ??? : " + type);
            result = cType.getConstructor(type).newInstance(theD, this.path4Log);
            System.out.println("cPage : " + result);
        }
        return result;
    }

    public String checkTheNextPage(String btnName, HashMap<String, ElementMapping> eleMap) {
        String result = null;
        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry tempContentEntry = (Map.Entry) it.next();
            System.out.println("    +++ ~~~ tempContentEntry : " + tempContentEntry);
            System.out.println("    +++ ~~~ tempContentEntry.getvalue : " + tempContentEntry.getValue());
            String tempRoute = ((ElementMapping) tempContentEntry.getValue()).getNextPage();
            System.out.println("    +++ ~~~ Find the temp Route : " + tempRoute);
            if (((ElementMapping) tempContentEntry.getValue()).getElementName().toLowerCase().contains((btnName).toLowerCase())) {
                if ((tempRoute != null) && (tempRoute != "")) {
                    result = tempRoute;
                } else {
                    result = "noRoute";
                }
                break;
            }
        }
        return result;
    }

    public String checkLocaterStr(String btnName, HashMap<String, ElementMapping> eleMap) {
        String result = null;
        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry tempContentEntry = (Map.Entry) it.next();
            String tempRoute = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();
            if (((ElementMapping) tempContentEntry.getValue()).getElementName().toLowerCase().contains((btnName).toLowerCase())) {
                if ((tempRoute != null) && (tempRoute != "")) {
                    result = tempRoute;
                } else {
                    result = "";
                }
                break;
            }
        }
        return result;
    }

    public String checkTheLocaterType(String btnName, HashMap<String, ElementMapping> eleMap) {
        String result = null;
        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry tempContentEntry = (Map.Entry) it.next();
            String tempType = ((ElementMapping) tempContentEntry.getValue()).getType();
            if (((ElementMapping) tempContentEntry.getValue()).getElementName().toLowerCase().contains((btnName).toLowerCase())) {
                if ((tempType != null) && (tempType != "")) {
                    result = tempType;
                } else {
                    result = "";
                }
                break;
            }
        }
        return result;
    }


    public String checkThe(String btnName, HashMap<String, ElementMapping> eleMap) {
        String result = null;
        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry tempContentEntry = (Map.Entry) it.next();
            String tempType = ((ElementMapping) tempContentEntry.getValue()).getType();
            if (((ElementMapping) tempContentEntry.getValue()).getElementName().toLowerCase().contains((btnName).toLowerCase())) {
                if ((tempType != null) && (tempType != "")) {
                    result = tempType;
                } else {
                    result = "";
                }
                break;
            }
        }
        return result;
    }

    public static String filter4space(String s) {
        s = new String(s.replaceAll("\n", " "));
        int i = s.length();// 字符串最后一个字符的位置
        int j = 0;// 字符串第一个字符
        int k = 0;// 中间变量
        char[] arrayOfChar = s.toCharArray();// 将字符串转换成字符数组
        while ((j < i) && (arrayOfChar[(k + j)] <= ' '))
            ++j;// 确定字符串前面的空格数
        while ((j < i) && (arrayOfChar[(k + i - 1)] <= ' '))
            --i;// 确定字符串后面的空格数

        return (((j > 0) || (i < s.length())) ? s.substring(j, i) : s);// 返回去除空格后的字符串

    }


    private String checkTheTextContent(String btnName, HashMap<String, ElementMapping> eleMap) throws InterruptedException {
        String textresult = null;
        for (Iterator it = eleMap.entrySet().iterator(); (it.hasNext() && (textresult == null)); ) {
            Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
            ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
            String tempTextContent = tempEle.getTextContent();      // get the text content value
            System.out.println(tempTextContent + " : " + tempTextContent.length());
            System.out.println("    +++ ~~~ Find the temp textContent : " + tempTextContent);
            if (tempEle.getElementName().equalsIgnoreCase(btnName)) {     // if the element name which has text content value match with the incoming element name
                if ((tempTextContent != null) && (tempTextContent != "")) {     // and the text content name is not null
                    textresult = new String(filter4space(tempTextContent));
                } else {
                    textresult = "empty";
                }
                break;
            }
        }
        return textresult;
    }

/*
    public String btnOperation(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap) throws FFPandaException {
        String result = null;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnOperation 4 Route in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds);
                    WebElement theResult = xWait.until(
                            new IOSExpectedCondition<WebElement>() {
                                public WebElement apply(IOSDriver driver) {
                                    return driver.findElement((By) tempContentEntry.getValue());
                                }
                            });
                    System.out.println("Check the eleContMpa : " + eleMap.size());
                    result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                    theResult.click();
                    if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        ScreenShot(driver, "During_Switch_" + time + ".png");
                    }
                }
            }
        } catch (FFPandaException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnOperation 4 Route 2, Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }
*/

    public String btnOperation(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap) throws Exception {
        String result = null;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    System.out.println("Check the eleContMpa : " + eleMap.size());
                    result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    theResult.click();
                    if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnOperation, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String btnOperation(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, HashMap<String, String> textMap) throws Exception {
        String result = null;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    eleName = new String(tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    System.out.println("Check the eleContMpa : " + eleMap.size());

                    String currentText = theResult.getText();
                    System.out.println("For IOS , get the text before click : " + currentText);
                    String tempContent = eleMap.get(eleName).getTextContent();
                    String tempDvalue = eleMap.get(eleName).getDefaultValue();
                    if (!tempContent.equals("") || !tempDvalue.equals("")) {
                        if (theResult.getText().equals("") || tempContent.equalsIgnoreCase(currentText) || tempDvalue.equalsIgnoreCase(currentText)) {
                            theResult.click();
                        } else if (currentText.startsWith(tempContent) || currentText.endsWith(tempContent)) {
                            System.out.println("Watch out, some element is located by fuzzy match");
                            theResult.click();
                        } else {
                            throw new REXException("Failed to match the target element " + eleName + " in page : " + this.getClass());
                        }
                    } else {
                        theResult.click();
                    }
                    result = checkTheNextPage(eleName, eleMap);
                    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                    if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate .btnOperation Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String btnOperationSmart(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, HashMap<String, String> textMap, final String tText, String extPath) throws Exception {
        String result = null;
        WebElement theResult = null;
        final String localPath = extPath;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnOperationSmart in " + this.getClass().getSimpleName() + " has been called ~~~ +++");

            final By tempEleBy = inMap.get(eleName);
            if (tempEleBy != null) {
                String eleTextContent = textMap.get(eleName);
                // String eleRoute = eleMap.get(eleName).getNextPage();
                System.out.println(eleName + " :!:");
                IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                    public WebElement apply(IOSDriver driver) {
                        WebElement temp = null;
                        try {
                            temp = (IOSElement) findFilteEleListWithSearch(driver, tempEleBy, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText);
                            // temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                        } catch (Exception e) {
                            throw new TimeoutException(e.getMessage());
                        }
                        return temp;
                    }
                });
                System.out.println("Check the eleContMpa : " + eleMap.size());
                result = checkTheNextPage(eleName, eleMap);
            } else {

                for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        eleName = new String(tempContentEntry.getKey().toString());
                        System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                        IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                        theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver driver) {
                                WebElement temp = null;
                                try {
                                    temp = (IOSElement) findFilteEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText);
                                    // temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                        System.out.println("Check the eleContMpa : " + eleMap.size());
                        result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                    }
                }
            }
            String currentText = theResult.getText();
            System.out.println("For IOS , get the text before click : " + currentText);
            String tempContent = eleMap.get(eleName).getTextContent();
            String tempDvalue = eleMap.get(eleName).getDefaultValue();
            if (!tempContent.equals("") || !tempDvalue.equals("")) {
                if (theResult.getText().equals("") || tempContent.equalsIgnoreCase(currentText) || tempDvalue.equalsIgnoreCase(currentText)) {
                    theResult.click();
                } else if (currentText.startsWith(tempContent) || currentText.endsWith(tempContent)) {
                    System.out.println("Watch out, some element is located by fuzzy match");
                    theResult.click();
                } else {
                    throw new REXException("Failed to match the target element " + eleName + " in page : " + this.getClass());
                }
            } else {
                theResult.click();
            }
            result = checkTheNextPage(eleName, eleMap);
            System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
            if (!result.equals("noRoute") && (result != "") && (result != null)) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
            }


        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnOperationSmart , Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String btnOperationSmartHome(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, HashMap<String, String> textMap, final String tText, String extPath) throws Exception {
        String result = null;
        WebElement theResult = null;
        final String localPath = extPath;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnOperationSmartHome in " + this.getClass().getSimpleName() + " has been called ~~~ +++");

            final By tempEleBy = inMap.get(eleName);
            if (tempEleBy != null) {
                String eleTextContent = textMap.get(eleName);
                // String eleRoute = eleMap.get(eleName).getNextPage();
                System.out.println(eleName + " :!:");
                IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                    public WebElement apply(IOSDriver driver) {
                        WebElement temp = null;
                        try {
                            temp = (IOSElement) findFilteEleListWithSearchHome(driver, tempEleBy, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText);
                            // temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                        } catch (Exception e) {
                            throw new TimeoutException(e.getMessage());
                        }
                        return temp;
                    }
                });
                System.out.println("Check the eleContMpa : " + eleMap.size());
                result = checkTheNextPage(eleName, eleMap);
            } else {

                for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        eleName = new String(tempContentEntry.getKey().toString());
                        System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                        IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                        theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver driver) {
                                WebElement temp = null;
                                try {
                                    temp = (IOSElement) findFilteEleListWithSearchHome(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText);
                                    // temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                        System.out.println("Check the eleContMpa : " + eleMap.size());
                        result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                    }
                }
                System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                System.out.println("For IOS , get the text before click : " + theResult.getText());
                if (theResult.getText().equals("") || textMap.get(eleName).toString().equals(theResult.getText())) {
                    theResult.click();
                } else if (theResult.getText().startsWith(textMap.get(eleName).toString()) || theResult.getText().endsWith(textMap.get(eleName).toString())) {
                    System.out.println("Watch out, some element is located by fuzzy match");
                    theResult.click();
                }
                if (!result.equals("noRoute") && (result != "") && (result != null)) {
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = format.format(date);
                    ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                }


            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnOperationSmartHome , Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String btnOperationIsolateSmart(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, HashMap<String, String> textMap, final String tText, String extPath) throws Exception {
        String result = null;
        IOSElement theResult = null;
        IOSElement theBtnResult = null;
        final String localPath = extPath;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnOperationIsolateSmart in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            final By tempEleBy = inMap.get(eleName);
            if (tempEleBy != null) {
                IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                    public IOSElement apply(IOSDriver driver) {
                        IOSElement temp = null;
                        try {
                            temp = (IOSElement) findFilteEleListWithSearch(driver, tempEleBy, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText);
                        } catch (Exception e) {
                            throw new TimeoutException(e.getMessage());
                        }
                        return temp;
                    }
                });
                System.out.println("Check the eleContMpa : " + eleMap.size());
                //  result = checkTheNextPage(eleName, eleMap);
            } else {
                for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                        eleName = new String(tempContentEntry.getKey().toString());
                        IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                        theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                            public IOSElement apply(IOSDriver driver) {
                                IOSElement temp = null;
                                try {
                                    temp = (IOSElement) findFilteEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText);
                                    // temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                        System.out.println("Check the eleContMpa : " + eleMap.size());

                    }
                }
            }
            System.out.println("For IOS Isolated , get the title text before click : " + theResult.getText());
            String eleBtnName = new String(eleName + "Btn");

            final By tempBtnEleBy = inMap.get(eleBtnName);
            if (tempEleBy != null) {
                IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                theBtnResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                    public IOSElement apply(IOSDriver driver) {
                        IOSElement temp = null;
                        try {
                            temp = (IOSElement) findFilteEleListWithSearch(driver, tempEleBy, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText);
                        } catch (Exception e) {
                            throw new TimeoutException(e.getMessage());
                        }
                        return temp;
                    }
                });
                System.out.println("Check the eleContMpa : " + eleMap.size());
                //  result = checkTheNextPage(eleName, eleMap);
            } else {
                for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleBtnName)) {
                        System.out.println(eleBtnName + " :?:" + tempContentEntry.getKey().toString());
                        eleBtnName = tempContentEntry.getKey().toString();
                        IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                        theBtnResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                            public IOSElement apply(IOSDriver driver) {
                                IOSElement temp = null;
                                try {
                                    temp = (IOSElement) findFilteEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText);
                                    // temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                        System.out.println("Check the eleContMpa : " + eleMap.size());

                    }
                }
            }


            result = checkTheNextPage(eleBtnName, eleMap);
            System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);

            if (theResult.getText().equals("") || textMap.get(eleBtnName).toString().equals(theResult.getText())) {
                theBtnResult.click();
            } else if (theResult.getText().startsWith(textMap.get(eleBtnName).toString()) || theResult.getText().endsWith(textMap.get(eleName).toString())) {
                System.out.println("Watch out, some element is located by fuzzy match");
                theBtnResult.click();
            }
            if (!result.equals("noRoute") && (result != "") && (result != null)) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
            }


        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnOperationIsolateSmart , Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public int btnId4ClassNameIsolate(IOSDriver driver, String eleName, String eleType, String eleStr, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap,
                                      HashMap<String, String> textMap, final String tText) throws Exception {
        int resIndex = 0;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnId4ClassNameIsolate in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            //     for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
            //     final Map.Entry tempContentEntry = (Map.Entry) it.next();
            //     if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
            if (eleType.equals("classname")) {
                System.out.println(eleName + " :~~~:" + eleType + " :~~~:" + eleStr);
                By temp = By.className(eleStr);
                resIndex = findID4ClassNameWithSearch(driver, temp, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText);
            } else {
                throw new XMLException("Only support classname IN btnId4ClassNameIsolate! ");
            }
            //         break;
            //     }
            //   }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnId4ClassNameIsolate, Other Error appear : " + e.getCause());
            throw e;
        }
        return resIndex;
    }

    public String btn4ClassNameCombind(IOSDriver driver, String eleName, String eleType, String eleStr, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap,
                                       HashMap<String, String> textMap, final String tText) throws Exception {
        String result = null;
        int resIndex = 0;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btn4ClassNameCombind in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            if (eleType.equals("classname")) {
                System.out.println(eleName + " :~~~:" + eleType + " :~~~:" + eleStr);
                By temp = By.className(eleStr);
                resIndex = findANDClick4ClassNameWithSearch(driver, temp, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText);
            } else {
                throw new XMLException("Only support classname IN btn4ClassNameCombind! ");
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btn4ClassNameCombind, Other Error appear : " + e.getCause());
            throw e;
        }
        if (resIndex != 0) {
            result = checkTheNextPage(eleName, eleMap);
        }

        return result;
    }

    public int btnId4ClassNameIsolate(IOSDriver driver, String eleName, String eleType, String eleStr, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap,
                                      HashMap<String, String> textMap, final String tText, PageIndexGroup indexGroupFromPage) throws Exception {
        int resIndex = 0;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnId4ClassNameIsolate in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            //     for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
            //     final Map.Entry tempContentEntry = (Map.Entry) it.next();
            //     if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
            if (eleType.equals("classname")) {
                System.out.println(eleName + " :~~~:" + eleType + " :~~~:" + eleStr);
                By temp = By.className(eleStr);
                resIndex = findID4ClassNameWithSearch(driver, temp, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText, indexGroupFromPage);
            } else {
                throw new XMLException("Only support classname IN btnId4ClassNameIsolate! ");
            }
            //         break;
            //     }
            //   }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnId4ClassNameIsolate, Other Error appear : " + e.getCause());
            throw e;
        }
        return resIndex;
    }

    public String btnOperationStatic(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, HashMap<String, String> textMap, String extPath) throws Exception {
        String result = null;
        WebElement theResult = null;
        final String localPath = extPath;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnOperationStatic in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            final By tempEleBy = inMap.get(eleName);
            if (tempEleBy != null) {
                System.out.println(eleName + " :!:");
                IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                    public WebElement apply(IOSDriver driver) {
                        return driver.findElement(tempEleBy);
                    }
                });
                System.out.println("Check the eleContMpa : " + eleMap.size());
                result = checkTheNextPage(eleName, eleMap);
            } else {
                for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        eleName = new String(tempContentEntry.getKey().toString());
                        System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                        IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                        theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver driver) {
                                return driver.findElement((By) tempContentEntry.getValue());
                            }
                        });
                        System.out.println("Check the eleContMpa : " + eleMap.size());
                        result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                    }
                }
            }
            String currentText = theResult.getText();
            System.out.println("For IOS , get the text before click : " + currentText);
            String tempContent = eleMap.get(eleName).getTextContent();
            String tempDvalue = eleMap.get(eleName).getDefaultValue();
            if (!tempContent.equals("") || !tempDvalue.equals("")) {
                if (theResult.getText().equals("") || tempContent.equalsIgnoreCase(currentText) || tempDvalue.equalsIgnoreCase(currentText)) {
                    theResult.click();
                } else if (currentText.startsWith(tempContent) || currentText.endsWith(tempContent)) {
                    System.out.println("Watch out, some element is located by fuzzy match");
                    theResult.click();
                } else {
                    throw new REXException("Failed to match the target element " + eleName + " in page : " + this.getClass());
                }
            } else {
                theResult.click();
            }
            result = checkTheNextPage(eleName, eleMap);
            System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);

            if (!result.equals("noRoute") && (result != "") && (result != null)) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
            }
        } catch (Exception e) {
            System.out.println("~~~~ e.getclass : btnOperationStatic");
            System.out.println("~~~~ e.getclass : " + e.getClass());
            System.out.println("~~~~ e.getclass.tostring : " + e.getClass().toString());
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnOperationStatic, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public boolean confirmOperationStatic(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, HashMap<String, String> textMap, String extPath) throws Exception {
        boolean result = false;
        WebElement theResult = null;
        final String localPath = extPath;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: confirmOperationStatic in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, 2, localPath, false);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            return driver.findElement((By) tempContentEntry.getValue());
                        }
                    });
                    System.out.println("For IOS , confirmOperationStatic : " + theResult.getText());
                    if (!textMap.get(eleName).toString().equals("") && textMap.get(eleName).toString().equals(theResult.getText())) {
                        result = true;
                    } else if (textMap.get(eleName).toString().equals("")) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }


    public boolean titleCheck(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, HashMap<String, String> textMap, String extPath) throws Exception {
        boolean result = false;
        WebElement theResult = null;
        final String localPath = extPath;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: titleCheck in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, 2, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            return driver.findElement((By) tempContentEntry.getValue());
                        }
                    });
                    System.out.println("For IOS , titleCheck : " + theResult.getText());
                    if (!textMap.get(eleName).toString().equals("") && textMap.get(eleName).toString().equals(theResult.getText())) {
                        result = true;
                    } else if (textMap.get(eleName).toString().equals("")) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }


    public String getElementContentStatic(IOSDriver driver, String locatorStr, String locatorType, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L, int TITLEindex) throws Exception {
        String result = null;
        WebElement theResult = null;
        List<WebElement> eleList = new ArrayList<WebElement>();
        final String localPath = p4L;
        final String localStr = locatorStr;
        try {

            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: getElementContentStatic in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
            if (locatorType.equalsIgnoreCase("classname")) {
                eleList = xWait.until(new IOSExpectedCondition<List>() {
                    public List<WebElement> apply(IOSDriver driver) {
                        return driver.findElements((By.className(localStr)));
                    }
                });
                for (int ind = 0; ind < eleList.size(); ind++) {
                    result = new String(result + "_:REX:_" + eleList.get(ind).getText());
                }
                //result = theResult.getText();
                Thread.sleep(1000);
                for (int ind = 0; ind < eleList.size(); ind++) {
                    if (result != null) {
                        result = new String(result + "_:REX:_" + eleList.get(ind).getText());
                    } else {
                        result = new String(eleList.get(ind).getText());
                    }
                }
            } else if (locatorType.equalsIgnoreCase("xpath")) {         // for xpath
                if (locatorStr.contains(":REX")) {//  with :REX
                    for (int ind = TITLEindex; ; ind++) {
                        String newStr = new String(locatorStr.replaceFirst(":REXindex:", String.valueOf(ind)));
                        final By afterReplace = By.xpath(newStr);
                        theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver driver) {
                                WebElement tempele = null;
                                try {
                                    tempele = findElementWithSearch(driver, afterReplace, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return tempele;
                            }
                        });
                        if (result != null) {
                            result = new String(result + "_:REX:_" + theResult.getText());
                        } else {
                            result = new String(theResult.getText());
                        }
                    }
                } else {//  without :REX
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            return driver.findElement((By.xpath(localStr)));
                        }
                    });
                    result = theResult.getText();
                }
            }

        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . getElementContentStatic, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }


    public String textOperationStatic(IOSDriver driver, String eleName, String para, HashMap<String, By> inMap, HashMap<String, String> textMap, String extPath) throws Exception {
        String result = null;
        IOSElement theResult = null;
        final String localPath = extPath;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: textOperationStatic in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                        public IOSElement apply(IOSDriver driver) {
                            return (IOSElement) driver.findElement((By) tempContentEntry.getValue());
                        }
                    });
                    theResult.click();
                    Thread.sleep(200);
                    theResult.clear();
                    theResult.setValue(para);

                    try {
                        driver.hideKeyboard("Done");
                    } catch (Exception e) {
                    }
                    //   driver.executeScript("env.sendKeyStrategy = 'onebyone'");
                    //   theResult.sendKeys(Keys.ENTER);
                    //   driver.executeScript("env.sendKeyStrategy = 'setValue'");


//                    theResult.sendKeys(para);

                    //  driver.sendKeyEvent(13);
                    //  driver.sendKeyEvent(IOSKeyCode.ENTER);
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . textOperationStatic, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }


    public String textOperationStaticLogin(IOSDriver driver, String eleName, String para, HashMap<String, By> inMap, HashMap<String, String> textMap, String extPath) throws Exception {
        String result = null;
        IOSElement theResult = null;
        final String localPath = extPath;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: textOperationStaticLogin in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                        public IOSElement apply(IOSDriver driver) {
                            return (IOSElement) driver.findElement((By) tempContentEntry.getValue());
                        }
                    });
                    theResult.click();
                    Thread.sleep(200);
                    theResult.clear();
                    theResult.setValue(para);
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . textOperationStaticLogin, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }

    public String textOperationStaticSendkey(IOSDriver driver, String eleName, String para, HashMap<String, By> inMap, HashMap<String, String> textMap, String extPath) throws Exception {
        String result = null;
        IOSElement theResult = null;
        final String localPath = extPath;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: textOperationStaticSendkey in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                        public IOSElement apply(IOSDriver driver) {
                            return (IOSElement) driver.findElement((By) tempContentEntry.getValue());
                        }
                    });
                    theResult.click();
                    Thread.sleep(200);
                    theResult.clear();
                    theResult.sendKeys(para);
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . textOperationStaticSendkey, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }

    public String textOperationStaticWithValue(IOSDriver driver, String eleName, String para, HashMap<String, By> inMap, HashMap<String, String> textMap, String extPath) throws Exception {
        String result = null;
        IOSElement theResult = null;
        final String localPath = extPath;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: textOperationStaticWithValue in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                        public IOSElement apply(IOSDriver driver) {
                            return (IOSElement) driver.findElement((By) tempContentEntry.getValue());
                        }
                    });
                    theResult.click();
                    Thread.sleep(200);
                    theResult.clear();
                    Thread.sleep(200);
                    theResult.setValue(para);
                    try {
                        driver.hideKeyboard("Done");
                    } catch (Exception e) {
                    }
                    Thread.sleep(500);
                    result = theResult.getText();
                    if (result.equals("") || result == null) {
                        result = para;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . textOperationStaticWithValue, Other Error appear : " + e.getCause());
            throw e;
        }

        return result;
    }


    public String textOperationStaticWithValueSendKey(IOSDriver driver, String eleName, String para, HashMap<String, By> inMap, HashMap<String, String> textMap, String extPath) throws Exception {
        String result = null;
        IOSElement theResult = null;
        final String localPath = extPath;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: textOperationStaticWithValueSendKey in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                        public IOSElement apply(IOSDriver driver) {
                            return (IOSElement) driver.findElement((By) tempContentEntry.getValue());
                        }
                    });
                    theResult.click();
                    Thread.sleep(200);
                    theResult.clear();
                    Thread.sleep(200);
                    //   theResult.setValue(para);
                    theResult.sendKeys(para);
                    try {
                        driver.hideKeyboard("Done");
                    } catch (Exception e) {
                    }
                    Thread.sleep(500);
                    result = theResult.getText();
                    if (result.equals("") || result == null) {
                        result = para;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . textOperationStaticWithValue, Other Error appear : " + e.getCause());
            throw e;
        }

        return result;
    }


    public String btnOperationDync(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, HashMap<String, String> textMap, Boolean movecheckdisable, Boolean moveable) throws Exception {
        String result = null;
        WebElement theResult = null;
        final boolean lmovecheckdisable = movecheckdisable;
        final boolean lmoveable = moveable;
        final String localPath = this.path4Log;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnOperationDync in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearchMove(driver, (By) tempContentEntry.getValue(),
                                        Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath,
                                        lmovecheckdisable, lmoveable);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    System.out.println("Check the eleContMpa : " + eleMap.size());
                    result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    if (theResult.getText().equals("") || textMap.get(eleName).toString().equals(theResult.getText())) {
                        theResult.click();
                    }
                    if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnOperationDync, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String calendarOperation(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L) throws Exception {  // click only
        String result = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        WebElement theResult = null;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: calendarOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    theResult.click();
                    if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate .calendarOperation, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }

    public static boolean checkEndwithNumInd(String extPara) {
        boolean result = false;
        String temp = null;

        System.out.println(extPara);

        Pattern test = Pattern.compile("(^[A-Za-z0-9\\s]+)(\\()(\\d+)(\\))$");
        //  Matcher matcher = pattern.matcher("10.11");
        Matcher matcher2dig = test.matcher(extPara);
        //  System.out.println(matcher.matches());
        if (matcher2dig.matches()) {
            System.out.println("Match the Tail with Num ind");
            result = true;
        }
        return result; //TODO
    }

    public String absCalendarOpenWin(IOSDriver driver, String
            eleName, final HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L) throws Exception {
        String result = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        WebElement theResult = null;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: absCalendarOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            ElementMapping tempEle = eleMap.get(eleName);
            IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
            final By tempBy = inMap.get(eleName);
            if (tempEle != null) {
                theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                    public WebElement apply(IOSDriver driver) {
                        WebElement temp = null;
                        try {
                            temp = findElementWithSearch(driver, tempBy, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                        } catch (Exception e) {
                            throw new TimeoutException(e.getMessage());
                        }
                        return temp;
                    }
                });
            } else {
                for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                        eleName = new String(tempContentEntry.getKey().toString());
                        tempEle = eleMap.get(eleName);
                        theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver driver) {
                                WebElement temp = null;
                                try {
                                    temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                        break;
                    }
                }
            }
            result = tempEle.getNextPage();
            System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
            theResult.click();
            if (!result.equals("noRoute") && (result != "") && (result != null)) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
            }

        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . absCalendarOperation, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String calendarOperationSuper(IOSDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L, String tDate, String dateWinName) throws Exception {  // click only
        String result = null;
        String calendarValue = null;
        String targetDate = tDate;
        String dateWin = dateWinName;
        int daylastmonth = dateHandle.getLastDayOfLastMonth(new Date(), "America/Chicago");
        System.out.println("    +++ ~~~ The calendarOperationSuper in FFPandaIOSPageTemplate has been called ~~~ +++");
        result = absCalendarOpenWin(driver, eleName, inMap, eleMap, p4L);

        Class tempClassType = Class.forName(String.valueOf(new StringBuffer("REXSH.REXAUTO.APPunderTest.AppPage.IOS.").append("IOSPage").append(dateWin)));
        System.out.println("@@@ tempClassType : " + tempClassType);
        Object tempIns = createInstance(tempClassType, driver);
        Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{IOSDriver.class, String.class, String.class});
        Object comPageCheckResult = setMethod.invoke(tempIns, driver, "ready", "overview");
        if (((String) comPageCheckResult).equals("pass")) {
            Method chooseMethod = tempClassType.getDeclaredMethod("pressDateItem", new Class[]{IOSDriver.class, String.class});
            TimeZone tz = TimeZone.getTimeZone("America/Chicago");
            Calendar cl = Calendar.getInstance(tz, Locale.US);
            String defaultDay = String.valueOf(cl.get(Calendar.DAY_OF_MONTH));

            if (Integer.parseInt(defaultDay) - 1 >= Integer.parseInt(targetDate)) {
                int yesterdayInt = Integer.parseInt(defaultDay) - 1;  //TODO, consider the 1st day og the month
                if (Integer.parseInt(targetDate) <= yesterdayInt) {
                    Object selectDateResult = chooseMethod.invoke(tempIns, driver, targetDate); // chooseDateItem
                    if (selectDateResult.toString().equalsIgnoreCase("true")) {
                        Method saveMethod = tempClassType.getDeclaredMethod("saveDate", new Class[]{IOSDriver.class, String.class, String.class});
                        Object saveDateResult = saveMethod.invoke(tempIns, driver, targetDate, p4L);
                        if (saveDateResult.toString() != "" && saveDateResult != null) {
                            result = saveDateResult.toString();
                            calendarValue = calendarGetTextOperation(driver, eleName, inMap, eleMap, p4L);
                            System.out.println(" ~~~~~~ Here is the date in calendar after save : " + calendarValue.toString());
                        }
                    } else {
                        throw new REXException("The Picker value is wrong : " + selectDateResult);
                    }
                }
            } else {
                if (targetDate.equals(String.valueOf(daylastmonth - 2))) {
                    cl.add(Calendar.MONTH, -2);
                } else {
                    cl.add(Calendar.MONTH, -1);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.ENGLISH);
                String lastMonthName = sdf.format(cl.getTime());
                Method monthMethod = tempClassType.getDeclaredMethod("pressMonthItem", new Class[]{IOSDriver.class, String.class}); //  btnOperationRoute(AndroidDriver driver, String eleName)
                Object selectMonthResult = monthMethod.invoke(tempIns, driver, lastMonthName);
                if (selectMonthResult.toString().equalsIgnoreCase("true")) {
                    Object selectDateResult = chooseMethod.invoke(tempIns, driver, targetDate); // chooseDateItem
                    if (selectDateResult.toString().equalsIgnoreCase("true")) {
                        Method saveMethod = tempClassType.getDeclaredMethod("saveDate", new Class[]{IOSDriver.class, String.class, String.class});
                        Object saveDateResult = saveMethod.invoke(tempIns, driver, targetDate, p4L);
                        if (saveDateResult.toString() != "" && saveDateResult != null) {
                            result = saveDateResult.toString();
                            calendarValue = calendarGetTextOperation(driver, eleName, inMap, eleMap, p4L);
                            System.out.println(" ~~~~~~ Here is the date in calendar after save : " + calendarValue.toString());
                        }
                    }
                } else {
                    throw new REXException("The Picker value is wrong : " + selectMonthResult);
                }
            }
        } else {
            throw new REXException("Failed to find the yesterday item in the calendar");
        }
        return result;
    }


    public String calendarOperationYandM(IOSDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L, String tMonth, String dateWinName) throws Exception {  // click only
        String result = null;
        String calendarValue = null;
        String targetMonth = tMonth;
        String dateWin = dateWinName;
        int daylastmonth = dateHandle.getLastDayOfLastMonth(new Date(), "America/Chicago");
        System.out.println("    +++ ~~~ The calendarOperationYandM in FFPandaIOSPageTemplate has been called ~~~ +++");
        result = absCalendarOpenWin(driver, eleName, inMap, eleMap, p4L);

        Class tempClassType = Class.forName(String.valueOf(new StringBuffer("REXSH.REXAUTO.APPunderTest.AppPage.IOS.").append("IOSPage").append(dateWin)));
        System.out.println("@@@ tempClassType : " + tempClassType);
        Object tempIns = createInstance(tempClassType, driver);
        Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{IOSDriver.class, String.class, String.class});
        Object comPageCheckResult = setMethod.invoke(tempIns, driver, "ready", "overview");
        Object selectMonthResult = null;
        if (((String) comPageCheckResult).equals("pass")) {
            TimeZone tz = TimeZone.getTimeZone("America/Chicago");
            Calendar cl = Calendar.getInstance(tz, Locale.US);
            String defaultDay = String.valueOf(cl.get(Calendar.DAY_OF_MONTH));

         //   if (targetMonth.equals(String.valueOf(daylastmonth - 2))) {
         //       cl.add(Calendar.MONTH, -2);
         //   } else {
         //       cl.add(Calendar.MONTH, -1);
         //   }
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.ENGLISH);
            String lastMonthName = sdf.format(cl.getTime());
            if(lastMonthName.equalsIgnoreCase(targetMonth)){
                System.out.println("@@@ Target month match the current month in picker.");
                selectMonthResult = new String("true");
            }
            else {
                Method monthMethod = tempClassType.getDeclaredMethod("pressMonthItem", new Class[]{IOSDriver.class, String.class}); //  btnOperationRoute(AndroidDriver driver, String eleName)
                selectMonthResult = monthMethod.invoke(tempIns, driver, lastMonthName);
            }
            if (selectMonthResult.toString().equalsIgnoreCase("true")) {
                Method saveMethod = tempClassType.getDeclaredMethod("saveDate", new Class[]{IOSDriver.class, String.class, String.class});
                Object saveDateResult = saveMethod.invoke(tempIns, driver, targetMonth, p4L);
                if (saveDateResult.toString() != "" && saveDateResult != null) {
                    result = saveDateResult.toString();
                    calendarValue = calendarGetTextOperation(driver, eleName, inMap, eleMap, p4L);
                    System.out.println(" ~~~~~~ Here is the date in calendar after save : " + calendarValue.toString());
                }
            } else {
                throw new REXException("The Picker value is wrong : " + selectMonthResult);
            }
        } else {
            throw new REXException("Failed to find the Year or Month item in the calendar");
        }
        return result;
    }


    public String calendarOperationSuper(IOSDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L, String dateWinName) throws Exception {  // click only
        String result = null;
        String calendarValue = null;
        String dateWin = dateWinName;
        int daylastmonth = dateHandle.getLastDayOfLastMonth(new Date(), "America/Chicago");
        System.out.println("    +++ ~~~ The calendarOperationSuper in FFPandaIOSPageTemplate has been called ~~~ +++");
        result = absCalendarOpenWin(driver, eleName, inMap, eleMap, p4L);

        Class tempClassType = Class.forName(String.valueOf(new StringBuffer("REXSH.REXAUTO.APPunderTest.AppPage.IOS.").append("IOSPage").append(dateWin)));
        System.out.println("@@@ tempClassType : " + tempClassType);
        Object tempIns = createInstance(tempClassType, driver);
        Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{IOSDriver.class, String.class, String.class});
        Object comPageCheckResult = setMethod.invoke(tempIns, driver, "ready", "overview");
        if (((String) comPageCheckResult).equals("pass")) {
            Method chooseMethod = tempClassType.getDeclaredMethod("pressDateItem", new Class[]{IOSDriver.class, String.class});
            TimeZone tz = TimeZone.getTimeZone("America/Chicago");
            Calendar cl = Calendar.getInstance(tz, Locale.US);
            String defaultDay = String.valueOf(cl.get(Calendar.DAY_OF_MONTH));
            if (Integer.parseInt(defaultDay) > 1) {
                int yesterdayInt = Integer.parseInt(defaultDay) - 1;  //TODO, consider the 1st day og the month
                String yesterdayString = String.valueOf(yesterdayInt);
                Object selectDateResult = chooseMethod.invoke(tempIns, driver, yesterdayString); // chooseDateItem
                if (selectDateResult.toString().equalsIgnoreCase("true")) {
                    Method saveMethod = tempClassType.getDeclaredMethod("saveDate", new Class[]{IOSDriver.class, String.class, String.class});
                    Object saveDateResult = saveMethod.invoke(tempIns, driver, yesterdayString, p4L);
                    if (saveDateResult.toString() != "" && saveDateResult != null) {
                        result = saveDateResult.toString();
                        calendarValue = calendarGetTextOperation(driver, eleName, inMap, eleMap, p4L);
                        System.out.println(" ~~~~~~ Here is the date in calendar after save : " + calendarValue.toString());
                    }
                } else {
                    throw new REXException("The Picker value is wrong : " + selectDateResult);
                }
            } else {
                throw new Exception("code for handling 1st day of the month are not ready");
            }
        } else {
            throw new REXException("Failed to find the yesterday item in the calendar");
        }
        return result;
    }

    public String getSingleEleTextGroup(IOSDriver driver, String eleName, HashMap<String, By> inMap) throws
            REXException {
        WebElement theResult1 = null;
        WebElement theResult2 = null;
        String result = null;
        // String itemMonth = eleName+"_Month";
        //  String itemYear = eleName+"_Year";
        String itemName = eleName;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The getSingleEle in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult1 == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(itemName)) {
                    System.out.println(itemName + " :?:");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, this.path4Log);
                    theResult1 = xWait.until(
                            new IOSExpectedCondition<WebElement>() {
                                public WebElement apply(IOSDriver driver) {
                                    return driver.findElement((By) tempContentEntry.getValue());
                                }
                            });
                    if (theResult1 != null) {
                        result = theResult1.getText();
                    }
                    break;
                }
            }
       /*     for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult2 == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(itemYear)) {
                    System.out.println(itemYear + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, this.path4Log);
                    theResult2 = xWait.until(
                            new IOSExpectedCondition<WebElement>() {
                                public WebElement apply(IOSDriver driver) {
                                    return driver.findElement((By) tempContentEntry.getValue());
                                }
                            });
                    if (theResult2 != null) {
                        result = new String(result + " " + theResult2.getText());
                    }
                    break;
                }
            }
            */
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . getSingleEleTextGroup , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        return result;
    }

    public String calendarGetTextOperation(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L) throws Exception {
        String result = null;
        WebElement theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: calendarGetTextOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    result = theResult.getText();
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . calendarGetTextOperation, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "";
        } else {
            return result;
        }
    }
// TODO make following loop quit with suitable reason

    public String dropDownListShow(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, final String p4L, HashMap<String, String> dValueMap) throws Exception {
        String result = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        WebElement theResult = null;
        HashMap<String, String> local = dValueMap;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: dropDownListShow in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    ElementMapping tempEleIn = eleMap.get(tempContentEntry.getKey().toString());
                    String tValue = tempEleIn.getDefaultValue();
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());

                    if (!tempEleIn.getLocatorStr().contains(":REXindex:")) {
                        IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                        theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver driver) {
                                WebElement temp = null;
                                try {
                                    temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } else {
                        boolean flag4match = false;
                        for (int index = 5; index < 15 && flag4match == false; index++) {
                            System.out.println("In dropDownListShow, try index : " + index);
                            String tempStr = tempEleIn.getLocatorStr();
                            String index4replace = new String(String.valueOf(index));
                            System.out.println("In dropDownListShow, try index : " + index4replace);
                            IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                            try {
                                theResult = driver.findElement(By.xpath(tempStr.replaceFirst(":REXindex:", index4replace)));
                            } catch (Exception e) {
                                System.out.println("In dropDownListShow, try next index : " + index4replace);
                            }
                            if (theResult != null) {
                                if (theResult.getText().equalsIgnoreCase(tValue)) {
                                    flag4match = true;
                                    break;
                                }
                            }
                        }
                    }
                    result = theResult.getText();
                    System.out.println("For IOS , get the text before click : " + result);
                    theResult.click();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . dropDownListShow, Other Error appear : " + e.getCause());
            e.printStackTrace();
            throw e;
        }

        return result;
    }

    public String dropDownItemChoose(IOSDriver driver, String preiousText, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, final String p4L) throws Exception {  //TODO
        String result = null;
        List<WebElement> theResult = new ArrayList<WebElement>();
        WebElement choosedItem = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: dropDownItemChoose in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().toLowerCase().contains("dropdown_item")) {
                    System.out.println("dropdown_item" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new IOSExpectedCondition<List>() {
                            public List<WebElement> apply(IOSDriver driver) {
                                List<WebElement> temp = null;
                                try {
                                    temp = findEleListByIndexWithSearch(driver, locaterStr, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } else if (locaterType.toLowerCase().equals("id") && !locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new IOSExpectedCondition<List>() {
                            public List<WebElement> apply(IOSDriver driver) {
                                List<WebElement> temp = null;
                                try {
                                    temp = findEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } else {
                        throw new XMLException("the element locater type and content mismatch ! ");
                    }
                    if (theResult.size() == 0) {
                        throw new REXException("The drop down list items are 0 !");
                    } else {
                        if (theResult.size() == 1 && ((theResult.get(0).getText().equalsIgnoreCase(preiousText)) || (theResult.get(0).getText().toLowerCase().contains("please select")))) { // TODO change to default value
                            throw new REXException("The ONLY drop down list items is same as default !");
                        } else {
                            for (int ind = 0; ind < theResult.size(); ind++) {
                                System.out.println("ind " + ind + " the get text result is : " + theResult.get(ind).getText());
                                if (theResult.get(ind).getText().equalsIgnoreCase(preiousText) || (theResult.get(ind).getText().toLowerCase().contains("please select"))) {
                                    continue;
                                } else {
                                    choosedItem = theResult.get(ind);
                                    break;
                                }
                            }
                        }
                    }
                    result = choosedItem.getText();
                    //        choosedItem.getText();
                    //       choosedItem.getAttribute("disabled");
                    //      choosedItem.getTagName();
                    choosedItem.click();
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . dropDownItemChoose, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }


    public String dropDownItemChooseRoll(IOSDriver driver, String preiousText, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, final String p4L, String eleName) throws Exception {  //TODO
        String result = null;
        WebElement theResult = null;
        Thread.sleep(1000);

        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: dropDownItemChooseRoll in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().contains(eleName + "_picker")) {
                    System.out.println(eleName + "_picker" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final By locaterStr = (By) tempContentEntry.getValue();
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("xpath") || locaterType.toLowerCase().equals("classname")) {
                        theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver driver) {
                                WebElement temp = null;
                                try {
                                    temp = findElementWithSearch(driver, locaterStr, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return temp;
                            }
                        });
                    } else {
                        throw new XMLException("the element locater type and content mismatch ! ");
                    }
                    if (theResult == null) {
                        throw new REXException("The drop down list items are 0 !");
                    } else {
                        if (rollItem(driver, new String(""), theResult) == true) {
                            theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                                public WebElement apply(IOSDriver driver) {
                                    WebElement temp = null;
                                    try {
                                        temp = findElementWithSearch(driver, locaterStr, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    return temp;
                                }
                            });
                            result = theResult.getText();
                        } else {
                            throw new REXException("dropDownItemChooseRoll failed");
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . dropDownItemChooseRoll, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }


    public boolean rollItem(IOSDriver theD, String tValue, WebElement theEle) throws Exception {  // type4check : ready, loading, error, temp , etc.  , para4check: target element name, all element, title element
        String targetValue = new String(tValue);
        int roundSco = 7; // 7 ios in the windows
        boolean flag = false;
        WebElement theItem = theEle;
        boolean rollUP = false;
        //  String result = "";

        if (theItem == null) {
            throw new REXException("failed to find the Picker for value : " + targetValue);
        } else {
            String currentDayStr = theItem.getText();
            if (currentDayStr.equals(targetValue)) {
                flag = true;
            } else {
                for (int ind = 0; ind < 3; ind++) {
                    if (rollTheRoundSingle(theD, theEle, roundSco, true) == true) {
                        flag = true;
                        break;
                    } else {
                        throw new REXException("Roll the picker failed");
                    }
                }
            }
            //   System.out.println("Current value in roller is : " + result);
        }
        //  if (!result.equals("error") && !result.equals("emptyContent")) {
        //      flag = true;
        // }
        return flag;
    }


    public boolean rollItem(IOSDriver theD, String preValue, String tValue, WebElement theEle) throws Exception {  // type4check : ready, loading, error, temp , etc.  , para4check: target element name, all element, title element
        String targetValue = new String(tValue);
        int roundSco = 7; // 7 ios in the windows
        boolean flag = false;
        WebElement theItem = theEle;
        boolean rollUP = false;
        String result = "";

        if (theItem == null) {
            throw new REXException("failed to find the Picker for value : " + targetValue);
        } else {
            String currentDayStr = theItem.getText();
            if (currentDayStr.equals(targetValue)) {
                flag = true;
            } else {
                for (int ind = 0; ind < 3; ind++) {
                    if (rollTheRoundSingle(theD, theEle, roundSco, true) == true) {
                        flag = true;
                        break;
                    } else {
                        throw new REXException("Roll the picker failed");
                    }
                }
            }
            System.out.println("Current value in roller is : " + result);
        }
        return flag;
    }


    public String dropDownItemChooseWithPara(IOSDriver driver, String preiousText, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, final String p4L, String extPara) throws Exception {  //TODO
        String result = null;
        List<WebElement> theResult = new ArrayList<WebElement>();
        WebElement choosedItem = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        System.out.println(" this time we want to choose the " + extPara + " item ");
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: dropDownItemChooseWithPara in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && choosedItem == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().toLowerCase().contains("dropdown_item")) {
                    System.out.println("dropdown_item" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new IOSExpectedCondition<List>() {
                            public List<WebElement> apply(IOSDriver driver) {
                                List<WebElement> temp = null;
                                try {
                                    temp = findEleListByIndexWithSearch(driver, locaterStr, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } else if (locaterType.toLowerCase().equals("id") && !locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new IOSExpectedCondition<List>() {
                            public List<WebElement> apply(IOSDriver driver) {
                                List<WebElement> temp = null;
                                try {
                                    temp = findEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } else {
                        throw new XMLException("the element locater type and content mismatch ! ");
                    }
                    if (theResult.size() == 0) {
                        throw new REXException("The drop down list items are 0 !");
                    } else {
                        if (theResult.size() == 1 && ((!theResult.get(0).getText().equalsIgnoreCase(extPara)))) { // TODO change to default value
                            throw new REXException("The ONLY drop down list items is same as default !");
                        } else {
                            for (int ind = 0; ind < theResult.size(); ind++) {
                                System.out.println("ind " + ind + " the get text result is : " + theResult.get(ind).getText());
                                if (theResult.get(ind).getText().equalsIgnoreCase(extPara)) {
                                    choosedItem = theResult.get(ind);
                                    break;
                                } else {
                                    continue;
                                }
                            }
                        }
                    }
                    result = choosedItem.getText();
                    choosedItem.click();
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . dropDownItemChooseWithPara, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }


    public String dropDownItemChooseRollWithPara(IOSDriver driver, String preiousText, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, final String p4L, String extPara, String eleName) throws Exception {  //TODO
        String result = null;
        WebElement theResult = null;
        Thread.sleep(1000);

        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: dropDownItemChooseRollWithPara in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().contains(eleName + "_picker")) {
                    System.out.println(eleName + "_picker" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final By locaterStr = (By) tempContentEntry.getValue();
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("xpath") || locaterType.toLowerCase().equals("classname")) {
                        theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver driver) {
                                WebElement temp = null;
                                try {
                                    temp = findElementWithSearch(driver, locaterStr, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return temp;
                            }
                        });
                    } else {
                        throw new XMLException("the element locater type and content mismatch ! ");
                    }
                    if (theResult == null) {
                        throw new REXException("The drop down list items are 0 !");
                    } else {
                        if (rollItem(driver, new String(""), theResult) == true) {
                            theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                                public WebElement apply(IOSDriver driver) {
                                    WebElement temp = null;
                                    try {
                                        temp = findElementWithSearch(driver, locaterStr, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    return temp;
                                }
                            });
                            result = theResult.getText();
                        } else {
                            throw new REXException("dropDownItemChooseRollWithPara failed");
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . dropDownItemChooseRollWithPara, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }


    public String singleItemChoose(IOSDriver driver, String preiousText, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, final String p4L) throws Exception {  //TODO
        String result = null;
        List<WebElement> theResult = new ArrayList<WebElement>();
        WebElement choosedItem = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: singleItemChoose in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().toLowerCase().contains("dropdown_item")) {
                    System.out.println("dropdown_item" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new IOSExpectedCondition<List>() {
                            public List<WebElement> apply(IOSDriver driver) {
                                List<WebElement> temp = null;
                                try {
                                    temp = findEleListByIndexWithSearch(driver, locaterStr, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } else if (locaterType.toLowerCase().equals("id") && !locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new IOSExpectedCondition<List>() {
                            public List<WebElement> apply(IOSDriver driver) {
                                List<WebElement> temp = null;
                                try {
                                    temp = findEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } else {
                        throw new XMLException("the element locater type and content mismatch ! ");
                    }
                    if (theResult.size() == 0) {
                        throw new REXException("The drop down list items are 0 !");
                    } else {
                        if (theResult.size() == 1 && ((theResult.get(0).getText().equalsIgnoreCase(preiousText)) || (theResult.get(0).getText().toLowerCase().contains("please select")))) { // TODO change to default value
                            throw new REXException("The ONLY drop down list items is same as default !");
                        } else {
                            for (int ind = 0; ind < theResult.size(); ind++) {
                                System.out.println("ind " + ind + " the get text result is : " + theResult.get(ind).getText());
                                if (theResult.get(ind).getText().equalsIgnoreCase(preiousText) || (theResult.get(ind).getText().toLowerCase().contains("please select"))) {
                                    continue;
                                } else {
                                    choosedItem = theResult.get(ind);
                                    break;
                                }
                            }
                        }
                    }
                    result = choosedItem.getText();
                    choosedItem.click();
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . singleItemChoose, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }

    public String singleItemChooseWithPara(IOSDriver driver, String preiousText, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, final String p4L, String extPara) throws Exception {  //TODO
        String result = null;
        List<WebElement> theResult = new ArrayList<WebElement>();
        WebElement choosedItem = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        System.out.println(" this time we want to choose the " + extPara + " item ");
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: singleItemChooseWithPara in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && choosedItem == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().toLowerCase().contains("single_item")) {
                    System.out.println("dropdown_item" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new IOSExpectedCondition<List>() {
                            public List<WebElement> apply(IOSDriver driver) {
                                List<WebElement> temp = null;
                                try {
                                    temp = findEleListByIndexWithSearch(driver, locaterStr, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } else if (locaterType.toLowerCase().equals("classname") && !locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new IOSExpectedCondition<List>() {
                            public List<WebElement> apply(IOSDriver driver) {
                                List<WebElement> temp = null;
                                try {
                                    temp = findEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } else {
                        throw new XMLException("the element locater type and content mismatch ! ");
                    }
                    if (theResult.size() == 0) {
                        throw new REXException("The drop down list items are 0 !");
                    } else {
                        if (theResult.size() == 1 && ((!theResult.get(0).getText().equalsIgnoreCase(extPara)))) { // TODO change to default value
                            throw new REXException("The ONLY drop down list items is same as default !");
                        } else {
                            for (int ind = 0; ind < theResult.size(); ind++) {
                                System.out.println("ind " + ind + " the get text result is : " + theResult.get(ind).getText());
                                if (theResult.get(ind).getText().equalsIgnoreCase(extPara)) {
                                    choosedItem = theResult.get(ind);
                                    break;
                                } else {
                                    continue;
                                }
                            }
                            if (choosedItem == null) {
                                for (int ind = 0; ind < theResult.size(); ind++) {
                                    if (theResult.get(ind).getText().toLowerCase().contains(extPara.toLowerCase())) {
                                        choosedItem = theResult.get(ind);
                                        break;
                                    } else {
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                    result = choosedItem.getText();
                    choosedItem.click();
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . singleItemChooseWithPara, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }

    public String multiItemChoose(IOSDriver driver, String preiousText, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, final String p4L) throws Exception {  //TODO
        String result = null;
        List<WebElement> theResult = new ArrayList<WebElement>();
        WebElement choosedItem = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: multiItemChoose in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().toLowerCase().contains("multi_item")) {
                    System.out.println("multi_item" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new IOSExpectedCondition<List>() {
                            public List<WebElement> apply(IOSDriver driver) {
                                List<WebElement> temp = null;
                                try {
                                    temp = findEleListByIndexWithSearch(driver, locaterStr, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } else if (locaterType.toLowerCase().equals("id") && !locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new IOSExpectedCondition<List>() {
                            public List<WebElement> apply(IOSDriver driver) {
                                List<WebElement> temp = null;
                                try {
                                    temp = findEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } else {
                        throw new XMLException("the element locater type and content mismatch ! ");
                    }
                    if (theResult.size() == 0) {
                        throw new REXException("The drop down list items are 0 !");
                    } else {
                        if (theResult.size() == 1 && ((theResult.get(0).getText().equalsIgnoreCase(preiousText)) || (theResult.get(0).getText().toLowerCase().contains("please select")))) { // TODO change to default value
                            throw new REXException("The ONLY drop down list items is same as default !");
                        } else {
                            for (int ind = 0; ind < theResult.size(); ind++) {
                                System.out.println("ind " + ind + " the get text result is : " + theResult.get(ind).getText());
                                if (theResult.get(ind).getText().equalsIgnoreCase(preiousText) || (theResult.get(ind).getText().toLowerCase().contains("please select"))) {
                                    continue;
                                } else {
                                    choosedItem = theResult.get(ind);
                                    break;
                                }
                            }
                        }
                    }
                    result = choosedItem.getText();
                    choosedItem.click();
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . multiItemChoose, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }


    public String multiItemChooseWithPara(IOSDriver driver, String preiousText, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, final String p4L, String extPara) throws Exception {  //TODO
        String result = null;
        List<WebElement> theResult = new ArrayList<WebElement>();
        WebElement choosedItem = null;
        List<String> paraList = new ArrayList<String>();
        Thread.sleep(1000);
        final String localPath = p4L;
        System.out.println(" this time we want to choose the " + extPara + " item ");
        try {
            if (extPara.contains("_")) {
                String[] tempPara = extPara.split("_");
                for (int ind = 0; ind < tempPara.length; ind++) {
                    paraList.add(tempPara[ind]);
                }

            } else {
                paraList.add(extPara);
            }
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: multiItemChooseWithPara in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && choosedItem == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().toLowerCase().contains("multi_item")) {
                    System.out.println("multi_item" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new IOSExpectedCondition<List>() {
                            public List<WebElement> apply(IOSDriver driver) {
                                List<WebElement> temp = null;
                                try {
                                    temp = findEleListByIndexWithSearch(driver, locaterStr, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } else if (locaterType.toLowerCase().equals("classname") && !locaterStr.contains(":REX")) {
                        theResult = xWait.until(new IOSExpectedCondition<List>() {
                            public List<WebElement> apply(IOSDriver driver) {
                                List<WebElement> temp = null;
                                try {
                                    temp = findEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } else {
                        throw new XMLException("the element locater type and content mismatch ! ");
                    }
                    if (theResult.size() == 0) {
                        throw new REXException("The drop down list items are 0 !");
                    } else {
                        for (int ind = 0; ind < theResult.size(); ind++) {
                            System.out.println("ind " + ind + " the get text result is : " + theResult.get(ind).getText());
                            for (int index = 0; index < paraList.size(); index++) {
                                //                   if (theResult.get(ind).getText().equalsIgnoreCase(paraList.get(index))) {
                                if (theResult.get(ind).getText().equalsIgnoreCase(paraList.get(index)) || theResult.get(ind).getText().toLowerCase().contains(paraList.get(index).toLowerCase())) {
                                    choosedItem = theResult.get(ind);
                                    if (index == 0 && result == null) {
                                        result = new String(choosedItem.getText());
                                    } else {
                                        result = new String(result + "_REX_" + choosedItem.getText());
                                    }
                                    choosedItem.click();
                                } else {
                                    continue;
                                }
                            }
                        }
                    }

                }
            }
            return result;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . multiItemChooseWithPara, Other Error appear : " + e.getCause());
            throw e;
        }

    }


    public String searchStart(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, final String p4L, String dValue, String extPara) throws Exception {
        String result = null;
        WebElement theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: searchStart in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    theResult.click();
                    Thread.sleep(200);
                    boolean loop4text = false;
                    if ((dValue != "") && (dValue != null)) {
                        if (!theResult.getText().equals(dValue)) {
                            String temp4loopcheck = null;
                            for (String x = theResult.getText(); !x.equals(dValue) && loop4text == false; ) {
                                System.out.println("### Default value of current textfield : " + x);
                                System.out.println("~~~ Default value from page element : " + dValue);
                                int tempTextLength = theResult.getText().length();
                                driver.sendKeyEvent(123);
                                for (int i = 0; i < tempTextLength; i++) {
                                    driver.sendKeyEvent(67);
                                }
                                temp4loopcheck = new String(x);
                                x = theResult.getText();
                                if (temp4loopcheck.equals(x)) {
                                    loop4text = true;
                                }
                            }
                        } else {
                            System.out.println("### Current textfield match " + dValue + " :: " + theResult.getText());
                        }
                    } else if (dValue.equals("")) {
                        for (String x = theResult.getText(); (x.length() != 0) && loop4text == false; ) {
                            int tempTextLength = x.length();
                            System.out.println("### Default value of current textfield : " + x);
                            System.out.println("### Default value of current textfield : " + tempTextLength);
                            System.out.println("~~~ Default value from page element : " + dValue);
                            driver.sendKeyEvent(123);
                            String temp4loopcheck = null;
                            for (int i = 0; i < tempTextLength; i++) {
                                driver.sendKeyEvent(67);
                            }
                            temp4loopcheck = new String(x);
                            x = theResult.getText();
                            if (temp4loopcheck.equals(x)) {
                                loop4text = true;
                            }
                        }
                    }
                    System.out.println("Ready to send the key !!! ");
                    theResult.sendKeys(extPara);
                    Thread.sleep(200);
                    driver.sendKeyEvent(66);
                    String tempText = theResult.getText();
                    if ((tempText != "") && (tempText != null)) {
                        if (tempText.equals(extPara)) {
                            result = tempText;
                        } else {
                            throw new REXException("searchStart : the real input para is different from excepted ");
                        }
                    } else {
                        result = "failedToGetText";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . searchStart, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }


    public String searchStartMAC(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, final String p4L, String dValue, String extPara) throws Exception {
        String result = null;
        IOSElement theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: searchStartMAC in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                        public IOSElement apply(IOSDriver driver) {
                            IOSElement temp = null;
                            try {
                                temp = (IOSElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    theResult.click();
                    Thread.sleep(200);
                    System.out.println("Ready to set value !!! ");
                    theResult.clear();
                    Thread.sleep(200);
                    theResult.setValue(extPara);
                    Thread.sleep(200);
                    try {
                        driver.hideKeyboard("Done");
                    } catch (Exception e) {
                    }
                    String tempText = theResult.getText();
                    if ((tempText != "") && (tempText != null)) {
                        if (tempText.equalsIgnoreCase(extPara)) {
                            result = tempText;
                        } else if (eleMap.get(eleName).getDefaultValue().equalsIgnoreCase(tempText)) {
                            result = extPara;
                        } else {
                            throw new REXException("searchStartMAC : the real input para is different from excepted ");
                        }
                    } else if (tempText == null || tempText.equals("")) {
                        result = extPara;
                    } else {
                        result = "failedToGetText";
                    }
                    driver.hideKeyboard("Search");
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . searchStartMAC, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }


    public String selectItem(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, final String p4L, String extPara, Boolean contentIsolatedClickable) throws Exception {  //TODO
        String result = null;
        List<WebElement> theResultList = new ArrayList<WebElement>();
        WebElement choosedItem = null;
        Thread.sleep(2500);
        final String localPath = p4L;
        String contentInItem = null;
        WebElement item4Index = null;
        int index4Line = 0;
        System.out.println(" this time we want to choose the " + extPara + " item ");
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: selectItem in " + this.getClass().getSimpleName() + " has been called ~~~ +++"); // find all items
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().contains("itemByID")) {
                    System.out.println("itemByID" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("id") && !locaterStr.contains(":REX")) {
                        theResultList = xWait.until(new IOSExpectedCondition<List>() {
                            public List<WebElement> apply(IOSDriver driver) {
                                List<WebElement> temp = null;
                                try {
                                    temp = findEleListWithSearch(driver, By.id(locaterStr), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } else if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                        for (int ind = 1; ind <= 10; ind++) {
                            try {
                                WebElement temp4list = driver.findElement(By.xpath(locaterStr.replaceFirst(":REXitem:", String.valueOf(ind))));
                                if (temp4list != null) {
                                    theResultList.add(temp4list);
                                } else {
                                    break;
                                }
                            } catch (Exception e) {
                                break;
                            }
                        }
                    }
                }
            }
            for (WebElement sam : theResultList) {  // try to find the excat match item
                String temp = sam.getText();
                if (temp.equalsIgnoreCase(extPara)) {
                    contentInItem = temp;
                    choosedItem = sam;
                    break;
                }
            }
            if (contentInItem == null || contentInItem == "") { // try to find the almost match item
                for (WebElement sam : theResultList) {
                    String temp = sam.getText();
                    if (temp.toLowerCase().contains(extPara.toLowerCase())) {
                        contentInItem = temp;
                        choosedItem = sam;
                        break;
                    }
                }
            }
            if (contentIsolatedClickable == false) {  // non-isolated content and clickable btn
                result = choosedItem.getText();
                choosedItem.click();
            } else {// isolated content and clickable btn
                if (contentInItem != null && contentInItem != "") {  // use the item content to get the index of that line
                    String locaterType = null;
                    String locaterStr = null;
                    for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                        final Map.Entry tempContentEntry = (Map.Entry) it.next();
                        if (tempContentEntry.getKey().toString().contains("searchContent_item")) { // get the common info
                            System.out.println("searchContent_item" + " :?:" + tempContentEntry.getKey().toString());
                            locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                            locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                            break;
                        }
                    }
                    for (int ind = 1; ind < theResultList.size() + 1; ind++) { // use common info to get the exact index by loop from 1 to max
                        final int theInd = ind;
                        final String theStr = locaterStr;
                        IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, p4L);
                        if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                            item4Index = xWait.until(new IOSExpectedCondition<WebElement>() {
                                public WebElement apply(IOSDriver driver) {
                                    WebElement temp = null;
                                    try {
                                        temp = findElementWithSearch(driver, By.xpath(theStr.replaceFirst(":REXitem:", String.valueOf(theInd))), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                    } catch (Exception e) {
                                        throw new TimeoutException(e.getMessage());
                                    }
                                    return temp;
                                }
                            });
                        }
                        if (item4Index != null && item4Index.getText().equalsIgnoreCase(contentInItem)) {  // checek the content is match
                            index4Line = ind;
                            result = item4Index.getText();
                            break;
                        }
                    }
                    if (index4Line != 0) {          //  use the index to find the line instance and click it
                        for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                            final Map.Entry tempContentEntry = (Map.Entry) it.next();
                            String lineType = null;
                            WebElement theLine = null;
                            final int index = index4Line;
                            if (tempContentEntry.getKey().toString().contains("search_item")) {
                                System.out.println("search_item" + " :?:" + tempContentEntry.getKey().toString());
                                lineType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                                final String lineStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                                IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, p4L);
                                if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                                    theLine = xWait.until(new IOSExpectedCondition<WebElement>() {
                                        public WebElement apply(IOSDriver driver) {
                                            WebElement temp = null;
                                            try {
                                                temp = findElementWithSearch(driver, By.xpath(lineStr.replaceFirst(":REXitem:", String.valueOf(index))), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                            } catch (Exception e) {
                                                throw new TimeoutException(e.getMessage());
                                            }
                                            return temp;
                                        }
                                    });
                                }

                            }
                            if (theLine != null) {
                                theLine.click();
                                break;
                            }
                        }
                    }
                    //TODO get all of the selected mark
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . selectItem, Other Error appear : " + e.getCause());
            e.printStackTrace();
            throw e;
        }
        if (result == null || result == "") {
            result = contentInItem;
        }
        return result;
    }

    public String btnOperationHome(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, HashMap<String, String> textMap, String extPath) throws Exception {
        String result = null;
        WebElement theResult = null;
        final String localPath = extPath;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnOperationHome in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            final By tempEleBy = inMap.get(eleName);
            if (tempEleBy != null) {

                System.out.println(eleName + " :!:");
                IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                    public WebElement apply(IOSDriver driver) {
                        return driver.findElement(tempEleBy);
                    }
                });
                System.out.println("Check the eleContMpa : " + eleMap.size());
                result = checkTheNextPage(eleName, eleMap);

            } else {
                for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        eleName = new String(tempContentEntry.getKey().toString());
                        System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                        IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                        theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver driver) {
                                return driver.findElement((By) tempContentEntry.getValue());
                            }
                        });
                        System.out.println("Check the eleContMpa : " + eleMap.size());
                        result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);

                    }
                }
            }
            String currentText = theResult.getText();
            System.out.println("For IOS , get the text before click : " + currentText);
            String tempContent = eleMap.get(eleName).getTextContent();
            String tempDvalue = eleMap.get(eleName).getDefaultValue();
            if (!tempContent.equals("") || !tempDvalue.equals("")) {
                if (theResult.getText().equals("") || tempContent.equalsIgnoreCase(currentText) || tempDvalue.equalsIgnoreCase(currentText)) {
                    theResult.click();
                } else if (currentText.startsWith(tempContent) || currentText.endsWith(tempContent)) {
                    System.out.println("Watch out, some element is located by fuzzy match");
                    theResult.click();
                } else {
                    throw new REXException("Failed to match the target element " + eleName + " in page : " + this.getClass());
                }
            } else {
                theResult.click();
            }
            result = checkTheNextPage(eleName, eleMap);
            System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
            if (eleName.equalsIgnoreCase("loginBtn")) {
                IOSDriverWait xWait = new IOSDriverWait(driver, 2, localPath);
                try {
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            return driver.findElement(tempEleBy);
                        }
                    });
                    if (theResult != null) {
                        currentText = theResult.getText();
                        System.out.println("For IOS , get the text before click : " + currentText);
                        tempContent = eleMap.get(eleName).getTextContent();
                        tempDvalue = eleMap.get(eleName).getDefaultValue();
                        if (!tempContent.equals("") || !tempDvalue.equals("")) {
                            if (theResult.getText().equals("") || tempContent.equalsIgnoreCase(currentText) || tempDvalue.equalsIgnoreCase(currentText)) {
                                theResult.click();
                            } else if (currentText.startsWith(tempContent) || currentText.endsWith(tempContent)) {
                                System.out.println("Watch out, some element is located by fuzzy match");
                                theResult.click();
                            } else {
                                throw new REXException("Failed to match the target element " + eleName + " in page : " + this.getClass());
                            }
                        } else {
                            theResult.click();
                        }
                        result = checkTheNextPage(eleName, eleMap);
                        System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                    }
                } catch (Exception e) {

                }
            }

            if (!result.equals("noRoute") && (result != "") && (result != null)) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnOperationHome, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String selectItemMAC(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, final String p4L, String extPara, Boolean contentIsolatedClickable) throws Exception {  //TODO
        String result = null;
        List<WebElement> theResultList = new ArrayList<WebElement>();
        WebElement choosedItem = null;
        Thread.sleep(2500);
        final String localPath = p4L;
        String contentInItem = null;
        WebElement item4Index = null;
        int index4Line = 0;
        System.out.println(" this time we want to choose the " + extPara + " item ");
        try {
            System.out.println("    +++ ~~~ In the IPADPageTemplate :: selectItemMAC in " + this.getClass().getSimpleName() + " has been called ~~~ +++"); // find all items
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().contains("itemByID")) {
                    System.out.println("itemByID" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("classname") && !locaterStr.contains(":REX")) {
                        theResultList = xWait.until(new IOSExpectedCondition<List>() {
                            public List<WebElement> apply(IOSDriver driver) {
                                List<WebElement> temp = null;
                                try {
                                    temp = findEleListWithSearch(driver, By.id(locaterStr), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } else if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                        for (int ind = 1; ind <= 10; ind++) {
                            try {
                                WebElement temp4list = driver.findElement(By.xpath(locaterStr.replaceFirst(":REXitem:", String.valueOf(ind))));
                                if (temp4list != null) {
                                    String temp = temp4list.getText();
                                    if (temp.equalsIgnoreCase(extPara) || temp.toLowerCase().startsWith(extPara.toLowerCase())) {
                                        contentInItem = temp;
                                        choosedItem = temp4list;

                                        if (contentIsolatedClickable == false) {  // non-isolated content and clickable btn
                                            result = choosedItem.getText();
                                            choosedItem.click();
                                            break;
                                        } else {// isolated content and clickable btn
                                            if (contentInItem != null && contentInItem != "") {  // use the item content to get the index of that line
                                                String locaterType2 = null;
                                                String locaterStr2 = null;
                                                for (Iterator it2 = inMap.entrySet().iterator(); it2.hasNext(); ) {
                                                    final Map.Entry tempContentEntry2 = (Map.Entry) it2.next();
                                                    if (tempContentEntry2.getKey().toString().contains("searchContent_item")) { // get the common info
                                                        System.out.println("searchContent_item" + " :?:" + tempContentEntry.getKey().toString());
                                                        locaterType2 = checkTheLocaterType(tempContentEntry2.getKey().toString(), eleMap);
                                                        locaterStr2 = checkLocaterStr(tempContentEntry2.getKey().toString(), eleMap);
                                                        break;
                                                    }
                                                }
                                                if (locaterType2.toLowerCase().equals("xpath") && locaterStr2.contains(":REXitem:")) {
                                                    WebElement temp4line = driver.findElement(By.xpath(locaterStr2.replaceFirst(":REXitem:", String.valueOf(ind))));
                                                    temp4line.click();
                                                    break;
                                                } else {
                                                    throw new XMLException("searchContent_item locator string should including REX");
                                                }
                                            }

                                        }
                                    } else {
                                        continue;
                                    }
                                }
                            } catch (Exception e) {
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In IPADPageTemplate . selectItemMAC, Other Error appear : " + e.getCause());
            e.printStackTrace();
            throw e;
        }
        if (result == null || result == "") {
            result = contentInItem;
        }
        return result;
    }


    public String checkCalendarDate(IOSDriver driver, String eleName, HashMap<String, By> inMap, String p4L) throws Exception {
        String result = null;
        WebElement theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: checkCalendarDate in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    result = theResult.getText();
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . checkCalendarDate , Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }

    public String btnOperation(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L) throws Exception {
        String result = null;
        IOSElement theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnOperation 4 Route in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                        public IOSElement apply(IOSDriver driver) {
                            IOSElement temp = null;
                            try {
                                temp = (IOSElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                    Thread.sleep(2000);
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    theResult.click();
                    if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . checkCalendarDate, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String btnOperation(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L, HashMap<String, String> textMap) throws Exception {
        String result = null;
        IOSElement theResult = null;
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnOperation 4 Route in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                        public IOSElement apply(IOSDriver driver) {
                            IOSElement temp = null;
                            try {
                                temp = (IOSElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    String currentText = theResult.getText();
                    System.out.println("For IOS , get the text before click : " + currentText);
                    String tempContent = eleMap.get(eleName).getTextContent();
                    String tempDvalue = eleMap.get(eleName).getDefaultValue();
                    if (!tempContent.equals("") || !tempDvalue.equals("")) {
                        if (theResult.getText().equals("") || tempContent.equalsIgnoreCase(currentText) || tempDvalue.equalsIgnoreCase(currentText)) {
                            theResult.click();
                        } else if (currentText.startsWith(tempContent) || currentText.endsWith(tempContent)) {
                            System.out.println("Watch out, some element is located by fuzzy match");
                            theResult.click();
                        } else {
                            throw new REXException("Failed to match the target element " + eleName + " in page : " + this.getClass());
                        }
                    } else {
                        theResult.click();
                    }
                    result = checkTheNextPage(eleName, eleMap);
                    if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnOperation 4 Route 2, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String btnOperationDync(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L, HashMap<String, String> textMap, Boolean movedheckdisable, Boolean moveable) throws Exception {
        String result = null;
        IOSElement theResult = null;
        final boolean lmovecheckdisable = movedheckdisable;
        final boolean lmoveable = moveable;
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnOperationDync in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                        public IOSElement apply(IOSDriver driver) {
                            IOSElement temp = null;
                            try {
                                temp = (IOSElement) findElementWithSearchMove(driver, (By) tempContentEntry.getValue(),
                                        Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, lmovecheckdisable, lmoveable);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    if (theResult.getText().equals("") || textMap.get(eleName).toString().equals(theResult.getText())) {
                        theResult.click();
                    }
                    if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate .btnOperationDync, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }

    public String btnOperationListMatch(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L) throws Exception {
        String result = null;
        List<WebElement> theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnOperationListMatch in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<List>() {
                        public List<WebElement> apply(IOSDriver driver) {
                            List<WebElement> temp = null;
                            try {
                                temp = findEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    if (theResult == null) {
                        throw new REXException("Failed to find the element in FFPandaIOSPageTemplate btnOperationListMatch, Line 2067 ");
                    } else if (theResult.size() == 1) {
                        result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                        System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                        Thread.sleep(2000);
                        theResult.get(0).click();
                        if (!result.equals("noRoute") && (result != "") && (result != null)) {
                            Date date = new Date();
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time = format.format(date);
                            ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                        }
                    } else {
                        result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                        System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                        Thread.sleep(2000);
                        theResult.get(0).click();
                        if (!result.equals("noRoute") && (result != "") && (result != null)) {
                            Date date = new Date();
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time = format.format(date);
                            ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnOperationListMatch, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String btnOperationAutoIndex(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L) throws Exception {   //TODO
        String result = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        int index4item = 1;
        int steprange = 20;
        String stepINString = null;
        String locatorStr = null;
        List<WebElement> currentScreen = new ArrayList<WebElement>();
        String localStr = null;
        try {
            WebElement singleEle = null;
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: btnOperationAutoIndex in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (int ind = 1; (ind <= steprange) && (singleEle == null); ind++) {
                System.out.println("step ~~~~~~~~~~~~~~~~~~" + ind);
                stepINString = new String(String.valueOf(ind));
                locatorStr = new String(locatorStr.replaceFirst(":REXindex:", stepINString));
                final By afterReplace = By.xpath(localStr);
                IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                try {
                    singleEle = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, afterReplace, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                } catch (Exception e) {
                }
            }
            /*
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    WebElement theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                    Thread.sleep(2000);
                    theResult.click();
                    if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                    }
                }
            }*/
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnOperationAutoIndex, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }

    public String getElementContentSmart(IOSDriver driver, String locatorStr, String locatorType, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L, int TITLEindex) throws Exception {
        String result = null;
        List<WebElement> eleList = new ArrayList<WebElement>();
        String localStr = locatorStr;
        Thread.sleep(1000);
        int steprange = 3;
        final String localPath = p4L;
        WebElement theResult = null;
        String stepINString = null;
        int index4item = 1;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: getElementContentSmart in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            if (locatorType.equalsIgnoreCase("classname")) {        // for classname
                final By theByKey = By.className(localStr);
                IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                try {
                    eleList = xWait.until(new IOSExpectedCondition<List>() {
                        public List<WebElement> apply(IOSDriver driver) {
                            List<WebElement> temp = new ArrayList<WebElement>();
                            try {
                                temp = findEleListWithSearch(driver, theByKey, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                //  temp = findElementWithSearch(driver, afterReplace, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });

                } catch (Exception e) {
                    throw new PageException("Failed to get the text element list with : " + theByKey);
                }
                Thread.sleep(1000);
                for (int ind = 0; ind < eleList.size(); ind++) {
                    if (result != null) {
                        result = new String(result + "_:REX:_" + eleList.get(ind).getText());
                    } else {
                        result = new String(eleList.get(ind).getText());
                    }
                }
            } else if (locatorType.equalsIgnoreCase("xpath")) {         // for xpath
                if (localStr.contains(":REX")) {//  with :REX
                    for (int ind = 0; (ind <= steprange) && (theResult == null); ind++) {
                        System.out.println("step ~~~~~~~~~~~~~~~~~~" + ind);
                        stepINString = new String(String.valueOf(TITLEindex + ind));
                        locatorStr = new String(locatorStr.replaceFirst(":REXindex:", stepINString));
                        localStr = new String(locatorStr.replaceFirst(":REXrecord:", String.valueOf(index4item)));
                        final By afterReplace = By.xpath(localStr);
                        IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                        try {
                            theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                                public WebElement apply(IOSDriver driver) {
                                    WebElement temp = null;
                                    try {
                                        temp = findElementWithSearch(driver, afterReplace, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                    } catch (Exception e) {
                                        throw new TimeoutException(e.getMessage());
                                    }
                                    return temp;
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw e;
                        }
                        if (result != null) {
                            result = new String(result + "_:REX:_" + theResult.getText());
                        } else {
                            result = new String(theResult.getText());
                        }
                    }
                    Thread.sleep(1000);
                    result = theResult.getText();
                } else {  // without :REX
                    final By afterReplace = By.xpath(localStr);
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    try {
                        theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver driver) {
                                WebElement temp = null;
                                try {
                                    temp = findElementWithSearch(driver, afterReplace, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                    result = theResult.getText();
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . getElementContentSmart, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "";
        } else {
            return result;
        }
    }


    public String getStaticElementContent(IOSDriver driver, String locatorStr, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L, int TITLEindex) throws Exception {
        String result = null;
        String localStr = null;
        Thread.sleep(1000);
        int steprange = 3;
        final String localPath = p4L;
        WebElement theResult = null;
        String stepINString = null;
        int index4item = 1;
        String rowContent = locatorStr;
        List<By> temp = new ArrayList<By>();
        try {
            for (int ind = 0; (ind <= steprange) && (theResult == null); ind++) {  //  use this part for getstaticelementcontent()
                System.out.println("step ~~~~~~~~~~~~~~~~~~" + ind);
                stepINString = new String(String.valueOf(TITLEindex + ind));
                String newContent1 = new String(rowContent.replaceFirst(":REXindex:", stepINString));  // for single
                String newContent2 = new String(newContent1.replaceFirst(":REXrecord:", String.valueOf(index4item)));
                final By singleRecord = By.xpath(newContent2);
                System.out.println(newContent2);
                try {
                    theResult = (IOSElement) driver.findElement(singleRecord);
                } catch (Exception e) {
                }
            }
            Thread.sleep(1000);
            result = theResult.getText();
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . getStaticElementContent, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "";
        } else {
            return result;
        }
    }


    public String autoSearch(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L) throws Exception {
        String result = null;
        WebElement theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: autoSearch 4 Route in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                        public WebElement apply(IOSDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . autoSearch , Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    //public static <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
    public static <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
        if (a.size() != b.size())
            return false;
        // Collections.reverse(a);
        // Collections.reverse(b);
        Collections.sort(a);
        Collections.sort(b);
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }


    public static boolean comparePoint(List<Point> a, List<Point> b) {
        if (a.size() != b.size())
            return false;
        // Collections.reverse(a);
        // Collections.reverse(b);
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }

    public String textOperationSmartClickSaveInput(IOSDriver driver, String eleName, String para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap, final String path) throws Exception {
        String result = null;
        IOSElement theResult = null;
        final String localPath = path;
        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) { // &&theResult==null;
                System.out.println("iterator : " + it == null);
                System.out.println("inMap size : " + inMap.size());
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    for (Iterator iter = dValueMap.entrySet().iterator(); iter.hasNext(); ) {
                        final Map.Entry tempValueEntry = (Map.Entry) iter.next();
                        if (tempValueEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                            final String dValue = (String) tempValueEntry.getValue();
                            IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                            theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                                public IOSElement apply(IOSDriver driver) {
                                    IOSElement temp = null;
                                    try {
                                        temp = (IOSElement) findFilteEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, dValue);
                                    } catch (Exception e) {
                                        throw new TimeoutException(e.getMessage());
                                    }
                                    return temp;
                                }
                            });
                            break;
                        }
                    }
                    theResult.clear();
                    Thread.sleep(200);
                    if (!eleName.contains("phone")) {
                        theResult.clear();
                        Thread.sleep(200);
                        theResult.setValue(para1);
                    } else {
                        para1 = new String(para1.substring(0, 3) + "-" + para1.substring(3, 6) + "-" + para1.substring(6, para1.length()));
                        theResult.clear();
                        Thread.sleep(200);
                        theResult.setValue(para1);
                    }
                    Thread.sleep(200);

                    theResult.click();
                    Thread.sleep(500);
                    try {
                        driver.hideKeyboard("Done");
                    } catch (Exception e) {
                    }
                    result = theResult.getText();
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }


    private IOSElement filterBtnElement(IOSDriver driver, IOSDriverWait xWait, By theByKey, String eleName, HashMap<String, ElementMapping> eleMap, int extindex4path) throws Exception {
        boolean review = false;
        int moveAccount = 10;
        IOSElement result = null;
        List<IOSElement> temp = new ArrayList<IOSElement>();
        List<IOSElement> temp4his = new ArrayList<IOSElement>();
        List<String> tempString = new ArrayList<String>();
        List<String> temp4hisString = new ArrayList<String>();
        String targetXPATH_start = "";
        String targetXPATH_end = "";
        String rowBtnXPath = "";
        int num4xpath = extindex4path;
        String tEleTextContent = checkTheTextContent(eleName, eleMap);
        try {
            for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {  // find the xpath of the element in map  去找这个元素在xml内的
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    String tempXpath = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();  // xpath内容找到
                    if (tempXpath.contains(":REXindex:")) {
                        String[] tempXpath4split = tempXpath.split(":REXindex:");   // 切开
                        if (2 == tempXpath4split.length) {
                            targetXPATH_start = tempXpath4split[0];  // 保留头部
                            break;
                        }
                    }
                }
            }
            if (targetXPATH_start != "") {
                for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {  // find the xpath of the element in map
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName + "Btn")) {   // 注意格式
                        String tempXpath = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();
                        String[] tempXpath4split = tempXpath.split(":REXindex:");
                        if (2 == tempXpath4split.length) {
                            targetXPATH_end = tempXpath4split[1];       // 保存尾部
                            rowBtnXPath = tempXpath;
                            break;
                        }
                    }
                }
            }
            if ((targetXPATH_start != "") && (targetXPATH_end != "")) {
                By tempBYKey = By.xpath("");
                try {
                    if (rowBtnXPath.equals(targetXPATH_start + ":REXindex:" + targetXPATH_end)) {
                        tempBYKey = By.xpath(targetXPATH_start + num4xpath + targetXPATH_end);    //
                        WebElement tEle = driver.findElement(tempBYKey);
                        if (tEle != null) {
                            result = (IOSElement) tEle;
                        } else {
                            throw new REXException("XPATH is correct, but failed to locate the element");
                        }
                    } else {
                        throw new XMLException("XML file for page element error : the Btn XPATH and Text XPATH mismatch before :REXindex: ");
                    }
                } catch (Exception e) {
                    throw e;
                }
            } else {
                // 希望不会出这个问题
                throw new XMLException("in FFPandaIOSPageTemplate :: filterBtnElement, fork else, the XML file is not correct");
            }

        } catch (Exception e) {
            result = null;
            throw e;
        } finally {
            return result;
        }
    }


    private IOSElement filterBtnElementOLD(IOSDriver driver, IOSDriverWait xWait, By theByKey, String eleName, HashMap<String, ElementMapping> eleMap) throws Exception {
        boolean review = false;
        int moveAccount = 10;
        IOSElement result = null;
        List<IOSElement> temp = new ArrayList<IOSElement>();
        List<IOSElement> temp4his = new ArrayList<IOSElement>();
        List<String> tempString = new ArrayList<String>();
        List<String> temp4hisString = new ArrayList<String>();
        String targetXPATH_start = "";
        String targetXPATH_end = "";
        String rowBtnXPath = "";
        int num4xpath = 0;
        String tEleTextContent = checkTheTextContent(eleName, eleMap);
        try {
            for (; (review != true) && (moveAccount > 0); ) {       // 只要没有到页尾 或者 计数器满就继续找
                temp = REXDriverWait.IOSListFinder(driver, xWait, theByKey); // get all element in current screen with a common id;only ID are supported, so don't send the type para
                for (int i = 0; i < temp.size(); i++) {
                    tempString.add(temp.get(i).getText());
                }
                if ((temp.size() == 0) && (temp4his.size() != 0)) {
                    System.out.println("Waiting!!!");
                } else if (temp.size() != temp4his.size()) {  // 长度不一样,自然就是新内容了
                    if (temp.size() == 1) {  // 如果只有一个内容
                        if (temp.get(0).getText().equals(tEleTextContent)) {  // some element in page text is same as the text we want, 而且内容正确匹配
                            for (Iterator it = eleMap.entrySet().iterator(); it.hasNext() && (num4xpath == 0); ) {  // find the xpath of the element in map  去找这个元素在xml内的
                                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                                    String tempXpath = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();  // xpath内容找到
                                    if (tempXpath.contains(":REXindex:")) {
                                        for (int in = 1; in <= 10; in++) {
                                            try {
                                                By tempBYKey = By.xpath(tempXpath.replace(":REXindex:", String.valueOf(in)));    //
                                                WebElement tEle = driver.findElement(tempBYKey);
                                                if (tEle.getText().equals(temp.get(0).getText())) {
                                                    num4xpath = in;
                                                    //   theXpathInd = num4xpath;
                                                    break;
                                                }
                                            } catch (Exception e) {
                                                System.out.println("try next index for REXindex");
                                            }
                                        }
                                    } else {
                                        throw new XMLException("the element xpath is incorrect");
                                    }
                                    String[] tempXpath4split = tempXpath.split(":REXindex:");   // 切开
                                    if (2 == tempXpath4split.length) {
                                        targetXPATH_start = tempXpath4split[0];  // 保留头部
                                        review = true;
                                        continue;
                                    }
                                }
                            }
                        } else {// 没有找到匹配的内容
                            // 等待翻页
                        }
                    } else {  // 长度不为1, 遍历寻找
                        for (int ind_ele = 0; ind_ele < temp.size(); ind_ele++) {
                            System.out.println("num : " + temp.get(ind_ele).getText());
                            if (temp.get(ind_ele).getText().equals(tEleTextContent)) {  // some element in page text is same as the text we want, 而且内容正确匹配
                                for (Iterator it = eleMap.entrySet().iterator(); it.hasNext() && (num4xpath == 0); ) {  // find the xpath of the element in map  去找这个元素在xml内的
                                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                                        String tempXpath = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();  // xpath内容找到
                                        if (tempXpath.contains(":REXindex:")) {
                                            for (int in = 1; in <= 10; in++) {
                                                try {
                                                    By tempBYKey = By.xpath(tempXpath.replace(":REXindex:", String.valueOf(in)));    //
                                                    WebElement tEle = driver.findElement(tempBYKey);
                                                    if (tEle.getText().equals(temp.get(ind_ele).getText())) {
                                                        num4xpath = in;
                                                        //     theXpathInd = num4xpath;
                                                        break;
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("try next index for REXindex");
                                                }
                                            }
                                        } else {
                                            throw new XMLException("the element xpath is incorrect");
                                        }
                                        String[] tempXpath4split = tempXpath.split(":REXindex:");   // 切开
                                        if (2 == tempXpath4split.length) {
                                            targetXPATH_start = tempXpath4split[0];  // 保留头部
                                            review = true;
                                            break;
                                        }
                                    }
                                }
                            } else {// 没有找到匹配的内容
                                // 等待next element in current page
                            }
                        }
                    }
                } else if ((temp.size() == temp4his.size()) && (temp.size() != 0) && (temp4his.size() != 0)) {  // 如果长度一致
                    if (true == compare(tempString, temp4hisString)) {  // 而且内容一致,到达页尾
                        review = true;
                        for (int ind_ele = 0; ind_ele < temp.size(); ind_ele++) {
                            if (temp.get(ind_ele).getText().equals(tEleTextContent)) {  // some element in page text is same as the text we want, 而且内容正确匹配
                                for (Iterator it = eleMap.entrySet().iterator(); it.hasNext() && (num4xpath == 0); ) {  // find the xpath of the element in map  去找这个元素在xml内的
                                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                                        String tempXpath = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();  // xpath内容找到
                                        if (tempXpath.contains(":REXindex:")) {
                                            for (int in = 1; in <= 10; in++) {
                                                try {
                                                    By tempBYKey = By.xpath(tempXpath.replace(":REXindex:", String.valueOf(in)));    //
                                                    WebElement tEle = driver.findElement(tempBYKey);
                                                    if (tEle.getText().equals(temp.get(ind_ele).getText())) {
                                                        num4xpath = in;
                                                        //      theXpathInd = num4xpath;
                                                        break;
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("try next index for REXindex");
                                                }
                                            }
                                        } else {
                                            throw new XMLException("the element xpath is incorrect");
                                        }
                                        String[] tempXpath4split = tempXpath.split(":REXindex:");   // 切开
                                        if (2 == tempXpath4split.length) {
                                            targetXPATH_start = tempXpath4split[0];  // 保留头部
                                            review = true;
                                            break;
                                        }
                                    }
                                }
                            } else {// 没有找到匹配的内容
                                // 等待next element in current page
                            }
                        }
                    } else {  // 内容不一致
                        // 使用新内容遍历
                        for (int ind_ele = 0; ind_ele < temp.size(); ind_ele++) {
                            if (temp.get(ind_ele).getText().equals(tEleTextContent)) {  // some element in page text is same as the text we want, 而且内容正确匹配
                                for (Iterator it = eleMap.entrySet().iterator(); it.hasNext() && (num4xpath == 0); ) {  // find the xpath of the element in map  去找这个元素在xml内的
                                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                                        String tempXpath = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();  // xpath内容找到
                                        if (tempXpath.contains(":REXindex:")) {
                                            for (int in = 1; in <= 10; in++) {
                                                try {
                                                    By tempBYKey = By.xpath(tempXpath.replace(":REXindex:", String.valueOf(in)));    //
                                                    WebElement tEle = driver.findElement(tempBYKey);
                                                    if (tEle.getText().equals(temp.get(ind_ele).getText())) {
                                                        num4xpath = in;
                                                        //     theXpathInd = num4xpath;
                                                        break;
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("try next index for REXindex");
                                                }
                                            }
                                        } else {
                                            throw new XMLException("the element xpath is incorrect");
                                        }
                                        String[] tempXpath4split = tempXpath.split(":REXindex:");   // 切开
                                        if (2 == tempXpath4split.length) {
                                            targetXPATH_start = tempXpath4split[0];  // 保留头部
                                            review = true;
                                            continue;
                                        }
                                    }
                                }
                            } else {// 没有找到匹配的内容
                                // 等待next element in current page
                            }
                        }
                    }
                } else if ((temp.size() == 0) && (temp4his.size() == 0)) {
                    // 没有找到匹配的内容
                    // 等待翻页
                    System.out.println("Waiting!!!");
                } else {
                    // 希望不会出这个问题
                    throw new REXException("in FFPandaIOSPageTemplate :: filterBtnElementOLD, fork else");
                }
                temp4his.clear();
                temp4his.addAll(temp);
                temp.clear();
                //
                temp4hisString.clear();
                temp4hisString.addAll(tempString);
                tempString.clear();
                //
                if (review != true) {
                    moveUPtest(driver);
                }
                moveAccount--;
                Thread.sleep(2000);
            }
            if (targetXPATH_start != "") {
                for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {  // find the xpath of the element in map
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName + "Btn")) {   // 注意格式
                        String tempXpath = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();
                        String[] tempXpath4split = tempXpath.split(":REXindex:");
                        if (2 == tempXpath4split.length) {
                            targetXPATH_end = tempXpath4split[1];       // 保存尾部
                            rowBtnXPath = tempXpath;
                            break;
                        }
                    }
                }
            }
            if ((targetXPATH_start != "") && (targetXPATH_end != "")) {
                By tempBYKey = By.xpath("");
                try {
                    if (rowBtnXPath.equals(targetXPATH_start + ":REXindex:" + targetXPATH_end)) {
                        tempBYKey = By.xpath(targetXPATH_start + num4xpath + targetXPATH_end);    //
                        WebElement tEle = driver.findElement(tempBYKey);
                        if (tEle != null) {
                            result = (IOSElement) tEle;
                        } else {
                            throw new REXException("XPATH is correct, but failed to locate the element");
                        }
                    } else {
                        throw new XMLException("XML file for page element error : the Btn XPATH and Text XPATH mismatch before :REXindex: ");
                    }
                } catch (Exception e) {
                    throw e;
                }
            } else {
                // 希望不会出这个问题
                throw new XMLException("in FFPandaIOSPageTemplate :: filterBtnElementOLD, fork else, the XML file is not correct");
            }
        } catch (Exception e) {
            result = null;
            throw e;
        } finally {
            return result;
        }

    }

    private int getIndex4BtnElement(IOSDriver driver, IOSDriverWait xWait, By theByKey, String eleName, HashMap<String, ElementMapping> eleMap) throws Exception {
        boolean review = false;
        int moveAccount = 10;
        IOSElement result = null;
        List<IOSElement> temp = new ArrayList<IOSElement>();
        List<IOSElement> temp4his = new ArrayList<IOSElement>();
        List<String> tempString = new ArrayList<String>();
        List<String> temp4hisString = new ArrayList<String>();
        String targetXPATH_start = "";
        String targetXPATH_end = "";
        String rowBtnXPath = "";
        int num4xpath = 0;
        String sampleText4first = null;
        String tEleTextContent = checkTheTextContent(eleName, eleMap);
        try {
            for (; (review != true) && (moveAccount > 0); ) {       // 只要没有到页尾 或者 计数器满就继续找
                temp = REXDriverWait.IOSListFinder(driver, xWait, theByKey); // get all element in current screen with a common id;only ID are supported, so don't send the type para
                for (int i = 0; i < temp.size(); i++) {
                    tempString.add(temp.get(i).getText());
                }
                if ((temp.size() == 0) && (temp4his.size() != 0)) {
                    System.out.println("Waiting!!!");
                }
                if (temp.size() != temp4his.size()) {  // 长度不一样,自然就是新内容了
                    if (temp.size() == 1) {  // 如果只有一个内容
                        sampleText4first = new String(temp.get(0).getText());
                        if (sampleText4first.equals(tEleTextContent)) {  // some element in page text is same as the text we want, 而且内容正确匹配
                            moveUP300(driver);
                            review = true;
                        } else {// 没有找到匹配的内容
                            // 等待翻页
                        }
                    } else {  // 长度不为1, 遍历寻找
                        for (int ind_ele = 0; (ind_ele < temp.size()) && (review == false); ind_ele++) {
                            sampleText4first = new String(temp.get(ind_ele).getText());
                            System.out.println("num : " + sampleText4first);
                            if (sampleText4first.equals(tEleTextContent)) {  // some element in page text is same as the text we want, 而且内容正确匹配
                                if (ind_ele == (temp.size() - 1)) {
                                    moveUP300(driver);
                                    review = true;
                                }
                                num4xpath = ind_ele;
                                review = true;
                            } else {// 没有找到匹配的内容
                                // 等待next element in current page
                            }
                        }
                    }
                } else if ((temp.size() == temp4his.size()) && (temp.size() != 0) && (temp4his.size() != 0)) {  // 如果长度一致
                    if (true == compare(tempString, temp4hisString)) {  // 而且内容一致,到达页尾
                        review = true;
                        for (int ind_ele = 0; (ind_ele < temp.size()) && (review == false); ind_ele++) {
                            sampleText4first = new String(temp.get(ind_ele).getText());
                            if (sampleText4first.equals(tEleTextContent)) {  // some element in page text is same as the text we want, 而且内容正确匹配
                                if (ind_ele == (temp.size() - 1)) {
                                    moveUP300(driver);
                                    review = true;
                                } else {
                                    review = true;
                                }
                            } else {// 没有找到匹配的内容
                                // 等待next element in current page
                            }
                        }
                    } else {  // 内容不一致
                        // 使用新内容遍历
                        for (int ind_ele = 1; (ind_ele < temp.size() && (review == false)); ind_ele++) {
                            sampleText4first = new String(temp.get(ind_ele).getText());
                            if (sampleText4first.equals(tEleTextContent)) {  // some element in page text is same as the text we want, 而且内容正确匹配
                                if (ind_ele == (temp.size() - 1)) {
                                    moveUP300(driver);
                                    review = true;
                                } else {
                                    review = true;
                                }
                            } else {// 没有找到匹配的内容
                                // 等待next element in current page
                            }
                        }
                    }
                } else if ((temp.size() == 0) && (temp4his.size() == 0)) {
                    // 没有找到匹配的内容
                    // 等待翻页
                    System.out.println("Waiting!!!");
                } else {
                    // 希望不会出这个问题
                    throw new REXException("in FFPandaIOSPageTemplate :: getIndex4BtnElement, fork else");
                }
                if (num4xpath == 0) {
                    temp4his.clear();
                    temp4his.addAll(temp);
                    temp.clear();
                    temp4hisString.clear();
                    temp4hisString.addAll(tempString);
                    tempString.clear();
                    if (review != true) {
                        //moveUPtest(driver);
                        moveUPwithSyncTime(driver, 2000);
                    }
                    moveAccount--;
                    Thread.sleep(2000);
                } else {
                    review = true;
                }
            }

            Point targetLocation = temp.get(num4xpath).getLocation();
            int targetX = targetLocation.getX();
            int targetY = targetLocation.getY();
            //   System.out.println("targetLocation.getX :" + targetLocation.getX());
            //   System.out.println("targetLocation.getY :" + targetLocation.getY());
            for (; targetX == 0 && targetY == 0; ) {
                moveUPwithSyncTime(driver, 2000);
                targetLocation = temp.get(num4xpath).getLocation();
                targetX = targetLocation.getX();
                targetY = targetLocation.getY();
            }
            //    temp.clear();
            //    temp = REXDriverWait.IOSListFinder(driver, xWait, theByKey); // get all element in current screen with a common id;only ID are supported, so don't send the type para
            //   for (int i = 0; i < temp.size(); i++) {
            //      tempString.add(temp.get(i).getText());
            //  }
            if (temp.size() == 0) {
                throw new REXException("element lost after match the screen");
            } else if (temp.size() == 1) {
                sampleText4first = new String(temp.get(0).getText());
                if (sampleText4first.equals(tEleTextContent)) {
                    for (Iterator it = eleMap.entrySet().iterator(); it.hasNext() && (num4xpath == 0); ) {  // find the xpath of the element in map  去找这个元素在xml内的
                        final Map.Entry tempContentEntry = (Map.Entry) it.next();
                        if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                            String tempXpath = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();  // xpath内容找到
                            if (tempXpath.contains(":REXindex:")) {
                                for (int in = 1; in <= (num4xpath + 2); in++) {
                                    By tempBYKey = By.xpath(tempXpath.replace(":REXindex:", String.valueOf(in)));    //
                                    WebElement tEle = driver.findElement(tempBYKey);
                                    if (tEle.getText().equals(sampleText4first)) {
                                        num4xpath = in;
                                        //   theXpathInd = num4xpath;
                                        break;
                                    } else {
                                        System.out.println("stop");
                                    }
                                }
                            }
                        } else {
                            System.out.println("next");
                        }
                    }
                }
            } else if (temp.size() > 1) {
                for (int ind_ele = 0; (ind_ele < temp.size()) && num4xpath == 0; ind_ele++) {
                    sampleText4first = new String(temp.get(ind_ele).getText());
                    if (sampleText4first.equals(tEleTextContent)) {
                        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext() && (num4xpath == 0); ) {  // find the xpath of the element in map  去找这个元素在xml内的
                            final Map.Entry tempContentEntry = (Map.Entry) it.next();
                            if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                                String tempXpath = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();  // xpath内容找到
                                if (tempXpath.contains(":REXindex:")) {
                                    for (int in = 1; in <= num4xpath + 2; in++) { //TODO
                                        try {
                                            By tempBYKey = By.xpath(tempXpath.replace(":REXindex:", String.valueOf(in)));    //
                                            WebElement tEle = driver.findElement(tempBYKey);
                                            if (tEle.getText().equals(sampleText4first)) {
                                                num4xpath = in;
                                                //   theXpathInd = num4xpath;
                                                break;
                                            } else {
                                                System.out.println("stop");
                                            }
                                        } catch (Exception e) {
                                            System.out.println("failed to find the element with index : " + in + ", try next");
                                        }
                                    }
                                }
                            } else {
                                System.out.println("failed to find the xpath content with current element, try next");
                            }
                        }
                    } else {
                        System.out.println("try next element in temp list by common id");
                    }
                }
            }
        } catch (
                Exception e
                )

        {
            throw e;
        }

        return num4xpath;
    }


    private IOSElement filterRecordElement(IOSDriver driver, IOSDriverWait xWait, By theByKey, String eleName, HashMap<String, ElementMapping> eleMap) throws Exception {
        boolean review = false;
        int moveAccount = 10;
        IOSElement result = null;
        List<IOSElement> temp = new ArrayList<IOSElement>();
        List<IOSElement> temp4his = new ArrayList<IOSElement>();
        List<String> tempString = new ArrayList<String>();
        List<String> temp4hisString = new ArrayList<String>();
        String targetXPATH_start = "";
        String targetXPATH_end = "";
        String rowBtnXPath = "";
        int num4xpath = 0;
        String tEleTextContent = checkTheTextContent(eleName, eleMap);
        try {
            for (; (review != true) && (moveAccount > 0); ) {       // 只要没有到页尾 或者 计数器满就继续找
                temp = REXDriverWait.IOSListFinder(driver, xWait, theByKey); // get all element in current screen with a common id;only ID are supported, so don't send the type para
                for (int i = 0; i < temp.size(); i++) {
                    tempString.add(temp.get(i).getText());
                }
                //    for (int i = 0; i < temp4his.size(); i++) {
                //         temp4hisString.add(temp4his.get(i).getText());
                //    }
                if ((temp.size() == 0) && (temp4his.size() != 0)) {
                    System.out.println("Waiting!!!");
                }
                if (temp.size() != temp4his.size()) {  // 长度不一样,自然就是新内容了
                    if (temp.size() == 1) {  // 如果只有一个内容
                        if (temp.get(0).getText().equals(tEleTextContent)) {  // some element in page text is same as the text we want, 而且内容正确匹配
                            for (Iterator it = eleMap.entrySet().iterator(); it.hasNext() && (num4xpath == 0); ) {  // find the xpath of the element in map  去找这个元素在xml内的
                                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                                    String tempXpath = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();  // xpath内容找到
                                    if (tempXpath.contains(":REXindex:")) {
                                        for (int in = 1; in <= 10; in++) {
                                            By tempBYKey = By.xpath(tempXpath.replace(":REXindex:", String.valueOf(in)));    //
                                            WebElement tEle = driver.findElement(tempBYKey);
                                            if (tEle.getText().equals(temp.get(0).getText())) {
                                                num4xpath = in;
                                                break;
                                            }
                                        }
                                    } else {
                                        throw new XMLException("the element xpath is incorrect");
                                    }
                                    String[] tempXpath4split = tempXpath.split(":REXindex:");   // 切开
                                    if (2 == tempXpath4split.length) {
                                        targetXPATH_start = tempXpath4split[0];  // 保留头部
                                        review = true;
                                        continue;
                                    }
                                }
                            }
                        } else {// 没有找到匹配的内容
                            // 等待翻页
                        }
                    } else {  // 长度不为1, 遍历寻找
                        for (int ind_ele = 0; ind_ele < temp.size(); ind_ele++) {
                            System.out.println("num : " + temp.get(ind_ele).getText());
                            if (temp.get(ind_ele).getText().equals(tEleTextContent)) {  // some element in page text is same as the text we want, 而且内容正确匹配
                                for (Iterator it = eleMap.entrySet().iterator(); it.hasNext() && (num4xpath == 0); ) {  // find the xpath of the element in map  去找这个元素在xml内的
                                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                                        String tempXpath = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();  // xpath内容找到
                                        if (tempXpath.contains(":REXindex:")) {
                                            for (int in = 1; in <= 10; in++) {
                                                By tempBYKey = By.xpath(tempXpath.replace(":REXindex:", String.valueOf(in)));    //
                                                WebElement tEle = driver.findElement(tempBYKey);
                                                if (tEle.getText().equals(temp.get(ind_ele).getText())) {
                                                    num4xpath = in;
                                                    break;
                                                }
                                            }
                                        } else {
                                            throw new XMLException("the element xpath is incorrect");
                                        }
                                        String[] tempXpath4split = tempXpath.split(":REXindex:");   // 切开
                                        if (2 == tempXpath4split.length) {
                                            targetXPATH_start = tempXpath4split[0];  // 保留头部
                                            review = true;
                                            break;
                                        }
                                    }
                                }
                            } else {// 没有找到匹配的内容
                                // 等待next element in current page
                            }
                        }
                    }
                } else if ((temp.size() == temp4his.size()) && (temp.size() != 0) && (temp4his.size() != 0)) {  // 如果长度一致
                    if (true == compare(tempString, temp4hisString)) {  // 而且内容一致,到达页尾
                        review = true;
                        for (int ind_ele = 0; ind_ele < temp.size(); ind_ele++) {
                            if (temp.get(ind_ele).getText().equals(tEleTextContent)) {  // some element in page text is same as the text we want, 而且内容正确匹配
                                for (Iterator it = eleMap.entrySet().iterator(); it.hasNext() && (num4xpath == 0); ) {  // find the xpath of the element in map  去找这个元素在xml内的
                                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                                        String tempXpath = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();  // xpath内容找到
                                        if (tempXpath.contains(":REXindex:")) {
                                            for (int in = 1; in <= 10; in++) {
                                                By tempBYKey = By.xpath(tempXpath.replace(":REXindex:", String.valueOf(in)));    //
                                                WebElement tEle = driver.findElement(tempBYKey);
                                                if (tEle.getText().equals(temp.get(ind_ele).getText())) {
                                                    num4xpath = in;
                                                    break;
                                                }
                                            }
                                        } else {
                                            throw new XMLException("the element xpath is incorrect");
                                        }
                                        String[] tempXpath4split = tempXpath.split(":REXindex:");   // 切开
                                        if (2 == tempXpath4split.length) {
                                            targetXPATH_start = tempXpath4split[0];  // 保留头部
                                            review = true;
                                            break;
                                        }
                                    }
                                }
                            } else {// 没有找到匹配的内容
                                // 等待next element in current page
                            }
                        }
                    } else {  // 内容不一致
                        // 使用新内容遍历
                        for (int ind_ele = 0; ind_ele < temp.size(); ind_ele++) {
                            if (temp.get(ind_ele).getText().equals(tEleTextContent)) {  // some element in page text is same as the text we want, 而且内容正确匹配
                                for (Iterator it = eleMap.entrySet().iterator(); it.hasNext() && (num4xpath == 0); ) {  // find the xpath of the element in map  去找这个元素在xml内的
                                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                                        String tempXpath = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();  // xpath内容找到
                                        if (tempXpath.contains(":REXindex:")) {
                                            for (int in = 1; in <= 10; in++) {
                                                By tempBYKey = By.xpath(tempXpath.replace(":REXindex:", String.valueOf(in)));    //
                                                WebElement tEle = driver.findElement(tempBYKey);
                                                if (tEle.getText().equals(temp.get(ind_ele).getText())) {
                                                    num4xpath = in;
                                                    break;
                                                }
                                            }
                                        } else {
                                            throw new XMLException("the element xpath is incorrect");
                                        }
                                        String[] tempXpath4split = tempXpath.split(":REXindex:");   // 切开
                                        if (2 == tempXpath4split.length) {
                                            targetXPATH_start = tempXpath4split[0];  // 保留头部
                                            review = true;
                                            continue;
                                        }
                                    }
                                }
                            } else {// 没有找到匹配的内容
                                // 等待next element in current page
                            }
                        }
                    }
                } else if ((temp.size() == 0) && (temp4his.size() == 0)) {
                    // 没有找到匹配的内容
                    // 等待翻页
                    System.out.println("Waiting!!!");
                } else {
                    // 希望不会出这个问题
                    throw new REXException("in FFPandaIOSPageTemplate :: filterRecordElement, fork else");
                }
                temp4his.clear();
                temp4his.addAll(temp);
                temp.clear();
                //
                temp4hisString.clear();
                temp4hisString.addAll(tempString);
                tempString.clear();
                //
                if (review != true) {
                    moveUPtest(driver);
                }
                moveAccount--;
                Thread.sleep(2000);
            }
            if (targetXPATH_start != "") {
                for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {  // find the xpath of the element in map
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName + "Btn")) {   // 注意格式
                        String tempXpath = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();
                        String[] tempXpath4split = tempXpath.split(":REXindex:");
                        if (2 == tempXpath4split.length) {
                            targetXPATH_end = tempXpath4split[1];       // 保存尾部
                            rowBtnXPath = tempXpath;
                            break;
                        }
                    }
                }
            }
            if ((targetXPATH_start != "") && (targetXPATH_end != "")) {
                By tempBYKey = By.xpath("");
                try {
                    if (rowBtnXPath.equals(targetXPATH_start + ":REXindex:" + targetXPATH_end)) {
                        tempBYKey = By.xpath(targetXPATH_start + num4xpath + targetXPATH_end);    //
                        WebElement tEle = driver.findElement(tempBYKey);
                        if (tEle != null) {
                            result = (IOSElement) tEle;
                        } else {
                            throw new REXException("XPATH is correct, but failed to locate the element");
                        }
                    } else {
                        throw new XMLException("XML file for page element error : the Btn XPATH and Text XPATH mismatch before :REXindex: ");
                    }
                } catch (Exception e) {
                    throw e;
                }
            } else {
                // 希望不会出这个问题
                throw new XMLException("in FFPandaIOSPageTemplate :: filterRecordElement, fork else, the XML file is not correct");
            }
        } catch (Exception e) {
            result = null;
            throw e;
        } finally {
            return result;
        }

    }


    public String btnOperationSearch(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L, int index4xpath) throws Exception {
        String result = null;
        IOSElement theResult = null;
        Thread.sleep(500);
        final String localPath = p4L;
        try {
            By theComByKey = null;
            final IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase("commonByID")) {
                    theComByKey = (By) tempContentEntry.getValue();
                    break;
                }
            }
            if (theComByKey != null) {
                index4xpath = getIndex4BtnElement(driver, xWait, theComByKey, eleName, eleMap);
                System.out.println("%%%%%%%%%%%%% find the index for xpath " + index4xpath);
                theResult = filterBtnElement(driver, xWait, theComByKey, eleName, eleMap, index4xpath);
                if (index4xpath > 0) {
                    result = checkTheNextPage(eleName, eleMap);
                    System.out.println("For IOS , get the text before click : " + theResult.getText());
                    theResult.click();
                    if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                    }
                } else {
                    throw new REXException("Failed to find the index 4 modify xpath");
                }
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . btnOperationSearch, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute" + "::" + index4xpath;
        } else {
            return (result + "::" + index4xpath);
        }
    }


    public String isolatedBtnSearchWithIndex(IOSDriver driver, final String eleName, final String comType, final String comStr, final HashMap<String, By> inMap, final HashMap<String, ElementMapping> eleMap, String p4L, int ind4xpath, HashMap<String, String> textMap, final String tText, final int index4ClassName, final String btnType, final String btnStr) throws Exception {
        String result = null;
        IOSElement theResult = null;
        IOSElement theResult4xpath = null;
        Thread.sleep(500);
        final String localPath = p4L;
        String localComType = comType;
        String localComStr = comStr;
        String localBtnType = btnType;
        String localBtnStr = btnStr;
        int titleY = 0;
        int title4xpathY = 0;
        int index4xpath = index4ClassName;
        try {
            if (!btnType.equals("xpath") || !btnStr.contains(":REX")) {
                throw new XMLException("XML for page content is incorrect !!! ");
            } else if (localComType.equals("classname")) {
                final By tempCom = By.className(localComStr);
                IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds * 3, localPath);
                theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                    public IOSElement apply(IOSDriver driver) {
                        IOSElement temp = null;
                        try {
                            temp = (IOSElement) findFilteEleListWithSearchIndex(driver, tempCom, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText, index4ClassName);
                        } catch (Exception e) {
                            throw new TimeoutException(e.getMessage());
                        }
                        return temp;
                    }
                });
                // now we should get the text ele : theResult
                titleY = theResult.getLocation().getY();
                theResult4xpath = xWait.until(new IOSExpectedCondition<IOSElement>() {
                    public IOSElement apply(IOSDriver driver) {
                        IOSElement temp = null;
                        try {
                            temp = (IOSElement) findFilteEleWithSearch(driver, eleName, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText, eleMap, btnStr);
                        } catch (Exception e) {
                            throw new TimeoutException(e.getMessage());
                        }
                        return temp;
                    }
                });
                // title4xpathY = theResult4xpath.getLocation().getY();
                result = checkTheNextPage(eleName + "Btn", eleMap);
                System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                theResult4xpath.click();
                if (!result.equals("noRoute") && (result != "") && (result != null)) {
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = format.format(date);
                    ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . isolatedBtnSearchWithIndex, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute" + "::" + index4xpath;
        } else {
            return (result + "::" + index4xpath);
        }
    }


    public String isolatedBtnSearchWithIndex(IOSDriver driver, final String eleName, final String comType, final String comStr, final HashMap<String, By> inMap,
                                             final HashMap<String, ElementMapping> eleMap, String p4L, int ind4xpath, HashMap<String, String> textMap,
                                             final String tText, final int index4ClassName, final String btnType, final String btnStr, PageIndexGroup indexMapFromPage) throws Exception {
        String result = null;
        IOSElement theResult = null;
        IOSElement theResult4xpath = null;
        Thread.sleep(500);
        final String localPath = p4L;
        String localComType = comType;
        String localComStr = comStr;
        String localBtnType = btnType;
        String localBtnStr = btnStr;
        int titleY = 0;
        int title4xpathY = 0;
        int index4xpath = index4ClassName;
        try {
            if (!btnType.equals("xpath") || !btnStr.contains(":REX")) {
                throw new XMLException("XML for page content is incorrect !!! ");
            } else if (localComType.equals("classname")) {
                final By tempCom = By.className(localComStr);
                IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds * 3, localPath);
                theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                    public IOSElement apply(IOSDriver driver) {
                        IOSElement temp = null;
                        try {
                            temp = (IOSElement) findFilteEleListWithSearchIndex(driver, tempCom, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText, index4ClassName);
                        } catch (Exception e) {
                            throw new TimeoutException(e.getMessage());
                        }
                        return temp;
                    }
                });
                // now we should get the text ele : theResult
                titleY = theResult.getLocation().getY();
                theResult4xpath = xWait.until(new IOSExpectedCondition<IOSElement>() {
                    public IOSElement apply(IOSDriver driver) {
                        IOSElement temp = null;
                        try {
                            temp = (IOSElement) findFilteEleWithSearch(driver, eleName, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText, eleMap, btnStr);
                        } catch (Exception e) {
                            throw new TimeoutException(e.getMessage());
                        }
                        return temp;
                    }
                });
                // title4xpathY = theResult4xpath.getLocation().getY();
                result = checkTheNextPage(eleName + "Btn", eleMap);
                System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                theResult4xpath.click();
                if (!result.equals("noRoute") && (result != "") && (result != null)) {
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = format.format(date);
                    ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . isolatedBtnSearchWithIndex, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute" + "::" + index4xpath;
        } else {
            return (result + "::" + index4xpath);
        }
    }


    public String isolatedRecordSearchWithLocation(IOSDriver driver, final String eleName, final String recordType, final String recordStr, final HashMap<String, By> inMap, final HashMap<String, ElementMapping> eleMap, String p4L, int ind4xpath, HashMap<String, String> textMap, final String tText, final int index4ClassName, final String comType, final String comStr) throws Exception {
        String result = null;
        IOSElement theResult = null;
        IOSElement theNextTitle = null;
        IOSElement theResult4xpath = null;
        Thread.sleep(500);
        final String localPath = p4L;
        //String localEleType = eleType;
        //String localEleStr = eleStr;
        String localTitleType = comType;
        String localTitleStr = comStr;
        String localComType = recordType;
        String localComStr = recordStr;
        // int titleY = 0;
        int title4xpathY = 0;
        int index4xpath = index4ClassName;
        try {
            IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds * 3, localPath);
            if (localTitleType.equalsIgnoreCase("classname")) {
                final By tempCom = By.className(localTitleStr);
                theResult = xWait.until(new IOSExpectedCondition<IOSElement>() {
                    public IOSElement apply(IOSDriver driver) {
                        IOSElement temp = null;
                        try {
                            temp = (IOSElement) findFilteEleListWithSearchIndex(driver, tempCom, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText, index4ClassName);
                        } catch (Exception e) {
                            throw new TimeoutException(e.getMessage());
                        }
                        return temp;
                    }
                });
                theNextTitle = xWait.until(new IOSExpectedCondition<IOSElement>() {
                    public IOSElement apply(IOSDriver driver) {
                        IOSElement temp = null;
                        try {
                            temp = (IOSElement) findFilteNextEleListWithSearchIndex(driver, tempCom, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText, index4ClassName);
                        } catch (Exception e) {
                            throw new TimeoutException(e.getMessage());
                        }
                        return temp;
                    }
                });
            }
            // now we should get the text ele : theResult
            final int titleY = theResult.getLocation().getY();
            final int nextTitleY = theNextTitle.getLocation().getY();
            //IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
            theResult4xpath = xWait.until(new IOSExpectedCondition<IOSElement>() {
                public IOSElement apply(IOSDriver driver) {
                    IOSElement temp = null;
                    try {
                        temp = (IOSElement) findFilteEleWithLocationCompare(driver, eleName, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText, eleMap, titleY, nextTitleY, index4ClassName);
                        //      temp = (IOSElement) findFilteEleWithSearch(driver, eleName, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath, tText, eleMap, btnStr);
                    } catch (Exception e) {
                        throw new TimeoutException(e.getMessage());
                    }
                    return temp;
                }
            });
            // title4xpathY = theResult4xpath.getLocation().getY();
            result = checkTheNextPage(eleName, eleMap);
            System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
            theResult4xpath.click();
            if (!result.equals("noRoute") && (result != "") && (result != null)) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . isolatedRecordSearchWithLocation, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute" + "::" + index4xpath;
        } else {
            return (result + "::" + index4xpath);
        }
    }


    public int getIndex4TitleIndex(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L, int index4xpath) throws REXException, InterruptedException {
        String result = null;
        Thread.sleep(500);
        final String localPath = p4L;
        try {
            By theComByKey = null;
            final IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase("commonByID")) {
                    theComByKey = (By) tempContentEntry.getValue();
                    break;
                }
            }
            if (theComByKey != null) {
                index4xpath = getIndex4BtnElement(driver, xWait, theComByKey, eleName, eleMap);
            } else {
                throw new REXException("not index for title !");
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . getIndex4TitleIndex, Other Error appear : " + e.getCause());
            e.printStackTrace();
        } finally {
            return index4xpath;
        }

    }


    public String recordSearch(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L, int index4path, String type, String rowLocatorString, String recordType, String recordLocatorStr, HashMap<String, Integer> waitStepMap) throws Exception {
        String result = null;
        IOSElement theResult = null;
        Thread.sleep(500);
        final String localPath = p4L;
        By theComByKey = null;
        int TITLEindex = 0;
        String RECORDindex = "1";
        String rowContent = rowLocatorString;
        String stepINString = null;
        int steprange = 3;
        int flag4end = 0;
        int moveAccount = 3;
        if (recordType.equals("xpath")) {
            theComByKey = By.xpath(recordLocatorStr);  // for list
        } else if (recordType.equals("classname")) {
            theComByKey = By.className(recordLocatorStr);   // for list
        }
        final IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
        if (eleName.contains("_record")) {
            TITLEindex = index4path;  //getIndex4BtnElement(driver, xWait, theComByKey, tempEleName, eleMap);
        }
        for (int move = 0; move < moveAccount && theResult == null; move++) {
            for (int ind = 0; (ind <= steprange) && (theResult == null) && (flag4end < 3); ind++) {
                System.out.println("step ~~~~~~~~~~~~~~~~~~" + ind);
                stepINString = new String(String.valueOf(TITLEindex + ind));
                String newContent1 = new String(rowContent.replaceFirst(":REXindex:", stepINString));  // for single
                String newContent2 = new String(newContent1.replaceFirst(":REXrecord:", RECORDindex));
                final By singleRecord = By.xpath(newContent2);
                System.out.println(newContent2);
                try {
                    theResult = (IOSElement) driver.findElement(singleRecord);
                } catch (Exception e) {
                    flag4end++;
                }
            }
            if (theResult == null) {
                moveUpFitScreen(driver);
                move++;
            }
        }
        if (theResult != null) {
            result = checkTheNextPage(eleName, eleMap);
            System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
            Thread.sleep(2000);
            System.out.println("For IOS , get the text before click : " + theResult.getText());
            theResult.click();
            if (!result.equals("noRoute") && (result != "") && (result != null)) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                ScreenShot(driver, "Record_During_Switch_" + time + ".png", p4L);
            }
        } else {
            throw new REXException("failed to find the element with inde step ");
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String btnMatchSearchByValue(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L, int index4path, String type, String rowLocatorString, String recordType, String recordLocatorStr, HashMap<String, Integer> waitStepMap, String key) throws Exception {
        String result = null;
        IOSElement theResult = null;
        IOSElement theRecord = null;
        List<IOSElement> theMapValueList = new ArrayList<IOSElement>();
        List<IOSElement> theRecordList = new ArrayList<IOSElement>();
        final String localPath = p4L;
        By theComByKey = null;
        int TITLEindex = 0;
        String RECORDindex = "1";
        String rowContent = rowLocatorString;
        String stepINString = null;
        int steprange = 3;
        int flag4end = 0;
        int flag4groupend = 0;
        String locatorString = null;
        String resultpart1 = "";
        String resultpart2 = "";

        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().toLowerCase().contains((eleName).toLowerCase())) { // for map value element
                locatorString = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();
                break;
            }
        }
        for (; (theMapValueList.size() != theRecordList.size()) || (theMapValueList.size() == 0 && theRecordList.size() == 0); moveUP300(driver)) {
            if (locatorString == null || locatorString == "") {
                resultpart1 = "noRelatedMapValue";
            } else if (locatorString.contains(":REXindex:") && locatorString.contains(":REXrecord:")) {
                for (int ind = 0; (ind <= steprange) && (theResult == null) && (flag4end < 3); ind++) {  // TODO try yo use this part for getstaticelementcontent()
                    System.out.println("step ~~~~~~~~~~~~~~~~~~" + ind);
                    stepINString = new String(String.valueOf(TITLEindex + ind));
                    String newContent1 = new String(locatorString.replaceFirst(":REXindex:", stepINString));  // for single
                    flag4groupend = 0;
                    for (int index = 1; (index <= 31) && (flag4groupend < 3); index++) {
                        String newContent2 = new String(newContent1.replaceFirst(":REXrecord:", String.valueOf(index)));
                        final By singleRecord = By.xpath(newContent2);
                        System.out.println(newContent2);
                        try {
                            theResult = (IOSElement) driver.findElement(singleRecord);
                            theMapValueList.add(theResult);
                            theResult = null;
                        } catch (Exception e) {
                            flag4groupend++;
                        }
                    }
                    if (theMapValueList.size() == 0) {
                        flag4end++;
                    }
                }
            } else {
                //TODO more layer in record
            }
            flag4end = 0;
            TITLEindex = index4path;  //getIndex4BtnElement(driver, xWait, theComByKey, tempEleName, eleMap);
            if (rowContent.contains(":REXindex:") && rowContent.contains(":REXrecord:")) {
                for (int ind = 0; (ind <= steprange) && (theRecord == null) && (flag4end < 3); ind++) {  // TODO try yo use this part for getstaticelementcontent()
                    System.out.println("step ~~~~~~~~~~~~~~~~~~" + ind);
                    stepINString = new String(String.valueOf(TITLEindex + ind));
                    String newContent1 = new String(rowContent.replaceFirst(":REXindex:", stepINString));  // for single
                    flag4groupend = 0;
                    for (int index = 1; (index <= 31) && (flag4groupend < 3); index++) {
                        String newContent2 = new String(newContent1.replaceFirst(":REXrecord:", String.valueOf(index)));
                        final By singleRecord = By.xpath(newContent2);
                        System.out.println(newContent2);
                        try {
                            theRecord = (IOSElement) driver.findElement(singleRecord);
                            theRecordList.add(theRecord);
                            theRecord = null;
                        } catch (Exception e) {
                            flag4groupend++;
                        }
                    }
                    if (theRecordList.size() == 0) {
                        flag4end++;
                    }
                }
            } else {
                //TODO more layer in record
            }
            if (theMapValueList.size() != theRecordList.size()) {
                theMapValueList.clear();
                theRecordList.clear();
                flag4end = 0;
                flag4groupend = 0;
            } else {
                break;
            }
        }

        if (theRecordList.size() != 0 && theMapValueList.size() != 0) {

            int range = 0;
            if (theRecordList.size() < theMapValueList.size()) {
                range = theRecordList.size();
            } else {
                range = theMapValueList.size();
            }
            boolean flag4quit = false;
            for (int thei = 0; thei < range && flag4quit == false; thei++) {
                WebElement theEle = theRecordList.get(thei);
                String recordName = eleName.split("_")[0] + "_record";
                if (theEle.getText().toLowerCase().contains(key.toLowerCase())) {
                    resultpart1 = theMapValueList.get(thei).getText();
                    resultpart2 = checkTheNextPage(recordName, eleMap);
                    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                    theEle.click();
                    if (!resultpart2.equals("noRoute") && (resultpart2 != "") && (resultpart2 != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        ScreenShot(driver, "Record_During_Switch_" + time + ".png", p4L);
                    }
                    result = new String(resultpart1 + "::" + resultpart2);
                    flag4quit = true;
                }
            }
            if (flag4quit == false) {
                throw new REXException("failed to find the element with match the key word");
            }
        } else {
            throw new REXException("failed to find the element with inde step ");
        }
        if (result == null || result.equals("::")) {
            return "::noRoute";
        } else {
            return result;
        }
    }


    public String contentMatchSearchByValue(IOSDriver driver, String eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L, int index4path, String type, String rowLocatorString, String recordType, String recordLocatorStr, HashMap<String, Integer> waitStepMap, String key) throws Exception {
        String result = null;
        IOSElement theResult = null;
        IOSElement theRecord = null;
        List<IOSElement> theMapValueList = new ArrayList<IOSElement>();
        List<IOSElement> theRecordList = new ArrayList<IOSElement>();
        final String localPath = p4L;
        By theComByKey = null;
        int TITLEindex = 0;
        String RECORDindex = "1";
        String rowContent = rowLocatorString;
        String stepINString = null;
        int steprange = 3;
        int flag4end = 0;
        int flag4groupend = 0;
        String locatorString = null;

        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().toLowerCase().contains((eleName).toLowerCase())) { // for map value element
                locatorString = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();
                break;
            }
        }
        for (; (theMapValueList.size() != theRecordList.size()) || (theMapValueList.size() == 0 && theRecordList.size() == 0); moveUP300(driver)) {
            if (locatorString == null || locatorString == "") {
                result = "noRelatedMapValue";
            } else if (locatorString.contains(":REXindex:") && locatorString.contains(":REXrecord:")) {
                for (int ind = 0; (ind <= steprange) && (theResult == null) && (flag4end < 3); ind++) {  // TODO try yo use this part for getstaticelementcontent()
                    System.out.println("step ~~~~~~~~~~~~~~~~~~" + ind);
                    stepINString = new String(String.valueOf(TITLEindex + ind));
                    String newContent1 = new String(locatorString.replaceFirst(":REXindex:", stepINString));  // for single
                    flag4groupend = 0;
                    for (int index = 1; (index <= 31) && (flag4groupend < 3); index++) {
                        String newContent2 = new String(newContent1.replaceFirst(":REXrecord:", String.valueOf(index)));
                        final By singleRecord = By.xpath(newContent2);
                        System.out.println(newContent2);
                        try {
                            theResult = (IOSElement) driver.findElement(singleRecord);
                            theMapValueList.add(theResult);
                            theResult = null;
                        } catch (Exception e) {
                            flag4groupend++;
                        }
                    }
                    if (theMapValueList.size() == 0) {
                        flag4end++;
                    }
                }
            } else {
                //TODO more layer in record
            }
            flag4end = 0;
            TITLEindex = index4path;  //getIndex4BtnElement(driver, xWait, theComByKey, tempEleName, eleMap);
            if (rowContent.contains(":REXindex:") && rowContent.contains(":REXrecord:")) {
                for (int ind = 0; (ind <= steprange) && (theRecord == null) && (flag4end < 3); ind++) {  // TODO try yo use this part for getstaticelementcontent()
                    System.out.println("step ~~~~~~~~~~~~~~~~~~" + ind);
                    stepINString = new String(String.valueOf(TITLEindex + ind));
                    String newContent1 = new String(rowContent.replaceFirst(":REXindex:", stepINString));  // for single
                    flag4groupend = 0;
                    for (int index = 1; (index <= 31) && (flag4groupend < 3); index++) {
                        String newContent2 = new String(newContent1.replaceFirst(":REXrecord:", String.valueOf(index)));
                        final By singleRecord = By.xpath(newContent2);
                        System.out.println(newContent2);
                        try {
                            theRecord = (IOSElement) driver.findElement(singleRecord);
                            theRecordList.add(theRecord);
                            theRecord = null;
                        } catch (Exception e) {
                            flag4groupend++;
                        }
                    }
                    if (theRecordList.size() == 0) {
                        flag4end++;
                    }
                }
            } else {
                //TODO more layer in record
            }
            if (theMapValueList.size() != theRecordList.size()) {
                theMapValueList.clear();
                theRecordList.clear();
                flag4end = 0;
                flag4groupend = 0;
            } else {
                break;
            }
        }

        if (theRecordList.size() != 0 && theMapValueList.size() != 0) {
            int range = 0;
            if (theRecordList.size() < theMapValueList.size()) {
                range = theRecordList.size();
            } else {
                range = theMapValueList.size();
            }
            boolean flag4quit = false;
            for (int thei = 0; thei < range && flag4quit == false; thei++) {
                WebElement theEle = theRecordList.get(thei);
                String recordName = eleName.split("_")[0] + "_record";
                if (theEle.getText().toLowerCase().contains(key.toLowerCase())) {
                    result = theMapValueList.get(thei).getText();
                    flag4quit = true;
                }
            }
            if (flag4quit == false) {
                throw new REXException("failed to find the element with match the key word");
            }
        } else {
            throw new REXException("failed to find the element with inde step ");
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }


    public String rollTheRound(IOSDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, int currentDayInt, int roundRange, boolean direction) throws Exception {
        boolean dir = direction;
        IOSElement theResult = null;
        String result = "";
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        final String localPath = this.path4Log;
        Point theLocation = null;
        Dimension theSize = null;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: rollTheRound ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult =
                            xWait.until(
                                    new IOSExpectedCondition<IOSElement>() {
                                        public IOSElement apply(IOSDriver driver) {
                                            IOSElement temp = null;
                                            temp = (IOSElement) driver.findElement((By) tempContentEntry.getValue());
                                            return temp;
                                        }
                                    });
                    theLocation = theResult.getLocation();
                    theSize = theResult.getSize();
                    break;
                }
            }
            int startX = theLocation.getX() + (theSize.getWidth() / 2);
            int endX = startX;
            int startY = 0;
            int endY = 0;
            int SCREENwidth = driver.manage().window().getSize().width;
            int SCREENheight = driver.manage().window().getSize().height;
            int widgetHigh = 0;
            if ((theLocation.getY() + theSize.getHeight()) > SCREENheight) {
                widgetHigh = SCREENheight - theLocation.getY();
            } else {
                widgetHigh = theSize.getHeight();
            }
            if (dir == true) {
                if (roundRange % 2 == 1) {
                    startY = theLocation.getY() + (widgetHigh * (roundRange + 1 / 4) / (roundRange + 5));
                    endY = startY - 30;
                } else {
                    startY = theLocation.getY() + (widgetHigh * (roundRange / 4) / (roundRange + 4));
                    endY = startY - 30;
                }
                for (int ind = 0; Integer.parseInt(theResult.getText()) >= currentDayInt - 1; ind++) {
                    moveUPwithPara(driver, startX, startY, endX, endY, timeDuration * 5);
                }
            } else {
                if (roundRange % 2 == 1) {
                    startY = theLocation.getY() + (widgetHigh * (roundRange + 1 / 2) / (roundRange + 11));
                    endY = startY + 30;
                } else {
                    startY = theLocation.getY() + (widgetHigh * (roundRange / 2) / (roundRange + 12));
                    endY = startY + 30;
                }
                // for (int ind = 0; ind < stepNum; ind++) {
                for (int ind = 0; Integer.parseInt(theResult.getText()) >= currentDayInt - 1; ind++) {
                    moveDOWNwithPara(driver, startX, startY, endX, endY, timeDuration * 5);
                }
            }

            Thread.sleep(1000);
            result = new String(theResult.getText());

        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  FFPandaIOSPageTemplate :: rollTheRound , Other Error appear : " + e.getCause());
            result = "error";
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }


    public String rollTheRound(IOSDriver driver, IOSElement theRollEntry, int currentDayInt, int roundRange, boolean direction) throws Exception {
        boolean dir = direction;
        IOSElement theResult = theRollEntry;
        String result = "";
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        final String localPath = this.path4Log;
        Point theLocation = null;
        Dimension theSize = null;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: rollTheRound ~~~ +++");

            theLocation = theRollEntry.getLocation();
            theSize = theRollEntry.getSize();

            int startX = theLocation.getX() + (theSize.getWidth() / 2);
            int endX = startX;
            int startY = 0;
            int endY = 0;
            int SCREENwidth = driver.manage().window().getSize().width;
            int SCREENheight = driver.manage().window().getSize().height;
            int widgetHigh = 0;
            if ((theLocation.getY() + theSize.getHeight()) > SCREENheight) {
                widgetHigh = SCREENheight - theLocation.getY();
            } else {
                widgetHigh = theSize.getHeight();
            }
            if (dir == true) {
                if (roundRange % 2 == 1) {
                    startY = theLocation.getY() + (widgetHigh * (roundRange + 1 / 4) / (roundRange + 5));
                    endY = startY - 30;
                } else {
                    startY = theLocation.getY() + (widgetHigh * (roundRange / 4) / (roundRange + 4));
                    endY = startY - 30;
                }
                for (int ind = 0; Integer.parseInt(theResult.getText()) >= currentDayInt - 1; ind++) {
                    moveUPwithPara(driver, startX, startY, endX, endY, timeDuration * 5);
                }
            } else {
                if (roundRange % 2 == 1) {
                    startY = theLocation.getY() + (widgetHigh * (roundRange + 1 / 2) / (roundRange + 11));
                    endY = startY + 30;
                } else {
                    startY = theLocation.getY() + (widgetHigh * (roundRange / 2) / (roundRange + 12));
                    endY = startY + 30;
                }
                // for (int ind = 0; ind < stepNum; ind++) {
                for (int ind = 0; Integer.parseInt(theResult.getText()) >= currentDayInt - 1; ind++) {
                    moveDOWNwithPara(driver, startX, startY, endX, endY, timeDuration * 5);
                }
            }

            Thread.sleep(1000);
            result = new String(theResult.getText());

        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  FFPandaIOSPageTemplate :: rollTheRound , Other Error appear : " + e.getCause());
            result = "error";
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }


    public String pressTheRound(IOSDriver driver, IOSElement theEntry, int tDayInt, int roundRange, boolean direction) throws Exception {
        String localDay = String.valueOf(tDayInt);
        boolean dir = direction;
        IOSElement theResult = theEntry;
        String result = "";
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        final String localPath = this.path4Log;
        Point theLocation = null;
        Dimension theSize = null;
        try {
            theLocation = theEntry.getLocation();
            theSize = theEntry.getSize();
            int theX = theLocation.getX() + (theSize.getWidth() / 2);
            int theY = 0;
            int SCREENheight = driver.manage().window().getSize().height;
            for (; !result.equals(localDay); ) {
                if (direction == false) {  // false for down
                    if (theSize.getHeight() == 291) {
                        theY = theLocation.getY() + 125;
                    } else {
                        long rate = (long) theSize.getHeight() / (long) 291;
                        theY = theLocation.getY() + new BigDecimal(rate * 125).intValueExact();
                    }
                } else {  // true for up
                    if (theSize.getHeight() == 291) {
                        theY = theLocation.getY() + 165;
                    } else {
                        long rate = (long) theSize.getHeight() / (long) 291;
                        theY = theLocation.getY() + new BigDecimal(rate * 165).intValueExact();
                    }
                }
                if (theY >= SCREENheight || theY == 0) {
                    throw new PageException("the point Y is out of screen");
                }
                AppBaseOperation temp = new AppBaseOperation(driver);
                temp.clickPoint(driver, theX, theY);
                Thread.sleep(1000);
                result = new String(theResult.getText());
            }
        } catch (
                Exception e
                )

        {
            System.out.println(logSpace4thisPage + "In  FFPandaIOSPageTemplate :: pressTheRound , Other Error appear : " + e.getCause());
            result = "error";
            e.printStackTrace();
        }

        if (result == null || result.equals(""))

        {
            return "emptyContent";
        } else

        {
            return result;
        }

    }

    public String pressTheRound(IOSDriver driver, IOSElement theEntry, int tDayInt, boolean direction) throws Exception {
        String localDay = String.valueOf(tDayInt);
        boolean dir = direction;
        IOSElement theResult = theEntry;
        String result = "";
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        final String localPath = this.path4Log;
        Point theLocation = null;
        Dimension theSize = null;
        try {
            theLocation = theEntry.getLocation();
            theSize = theEntry.getSize();
            int theX = theLocation.getX() + (theSize.getWidth() / 2);
            int theY = 0;
            int SCREENheight = driver.manage().window().getSize().height;
            for (; !result.equals(localDay); ) {
                if (direction == false) {  // false for down
                    if (theSize.getHeight() == 291) {
                        theY = theLocation.getY() + 125;
                    } else {
                        long rate = (long) theSize.getHeight() / (long) 291;
                        theY = theLocation.getY() + new BigDecimal(rate * 125).intValueExact();
                    }
                } else {  // true for up
                    if (theSize.getHeight() == 291) {
                        theY = theLocation.getY() + 165;
                    } else {
                        long rate = (long) theSize.getHeight() / (long) 291;
                        theY = theLocation.getY() + new BigDecimal(rate * 165).intValueExact();
                    }
                }
                if (theY >= SCREENheight || theY == 0) {
                    throw new PageException("the point Y is out of screen");
                }
                AppBaseOperation temp = new AppBaseOperation(driver);
                temp.clickPoint(driver, theX, theY);
                Thread.sleep(1000);
                result = new String(theResult.getText());
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  FFPandaIOSPageTemplate :: pressTheRound , Other Error appear : " + e.getCause());
            result = "error";
            e.printStackTrace();
        }

        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }

    public String pressTheRound(IOSDriver driver, IOSElement theEntry, String tValue, boolean direction) throws Exception {
        String localValue = tValue;
        boolean dir = direction;
        IOSElement theResult = theEntry;
        String result = "";
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        final String localPath = this.path4Log;
        Point theLocation = null;
        Dimension theSize = null;
        try {
            theLocation = theEntry.getLocation();
            theSize = theEntry.getSize();
            int theX = theLocation.getX() + (theSize.getWidth() / 2);
            int theY = 0;
            int SCREENheight = driver.manage().window().getSize().height;
            for (; !result.equalsIgnoreCase(localValue); ) {
                if (direction == false) {  // false for down
                    if (theSize.getHeight() == 291) {
                        theY = theLocation.getY() + 125;
                    } else {
                        long rate = (long) theSize.getHeight() / (long) 291;
                        theY = theLocation.getY() + new BigDecimal(rate * 125).intValueExact();
                    }
                } else {  // true for up
                    if (theSize.getHeight() == 291) {
                        theY = theLocation.getY() + 165;
                    } else {
                        long rate = (long) theSize.getHeight() / (long) 291;
                        theY = theLocation.getY() + new BigDecimal(rate * 165).intValueExact();
                    }
                }
                if (theY >= SCREENheight || theY == 0) {
                    throw new PageException("the point Y is out of screen");
                }
                AppBaseOperation temp = new AppBaseOperation(driver);
                temp.clickPoint(driver, theX, theY);
                Thread.sleep(1000);
                result = new String(theResult.getText());
            }
        } catch (
                Exception e
                ) {
            System.out.println(logSpace4thisPage + "In  FFPandaIOSPageTemplate :: pressTheRound , Other Error appear : " + e.getCause());
            result = "error";
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }

    public String pressTheRound(IOSDriver driver, IOSElement theEntry, String targetValue, int roundRange, boolean direction) throws Exception {
        String localDay = targetValue;
        boolean dir = direction;
        IOSElement theResult = theEntry;
        String result = "";
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        final String localPath = this.path4Log;
        Point theLocation = null;
        Dimension theSize = null;
        try {
            theLocation = theEntry.getLocation();
            theSize = theEntry.getSize();
            int theX = theLocation.getX() + (theSize.getWidth() / 2);
            int theY = 0;
            int SCREENheight = driver.manage().window().getSize().height;
            if (localDay.equals("")) {
                if (theSize.getHeight() == 291) {
                    theY = theLocation.getY() + 165;
                } else {
                    long rate = (long) theSize.getHeight() / (long) 291;
                    theY = theLocation.getY() + new BigDecimal(rate * 165).intValueExact();
                }
                if (theY >= SCREENheight || theY == 0) {
                    throw new PageException("the point Y is out of screen");
                }
                AppBaseOperation temp = new AppBaseOperation(driver);
                temp.clickPoint(driver, theX, theY);
                Thread.sleep(1000);
                result = new String(theResult.getText());
            } else {
                for (; !result.equalsIgnoreCase(localDay); ) {
                    if (direction == false) {  // false for down
                        if (theSize.getHeight() == 291) {
                            theY = theLocation.getY() + 125;
                        } else {
                            long rate = (long) theSize.getHeight() / (long) 291;
                            theY = theLocation.getY() + new BigDecimal(rate * 125).intValueExact();
                        }
                    } else {  // true for up
                        if (theSize.getHeight() == 291) {
                            theY = theLocation.getY() + 165;
                        } else {
                            long rate = (long) theSize.getHeight() / (long) 291;
                            theY = theLocation.getY() + new BigDecimal(rate * 165).intValueExact();
                        }
                    }
                    if (theY >= SCREENheight || theY == 0) {
                        throw new PageException("the point Y is out of screen");
                    }
                    AppBaseOperation temp = new AppBaseOperation(driver);
                    temp.clickPoint(driver, theX, theY);
                    Thread.sleep(1000);
                    result = new String(theResult.getText());
                }

            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  FFPandaIOSPageTemplate :: pressTheRound , Other Error appear : " + e.getCause());
            result = "error";
            e.printStackTrace();
        }

        if (result == null || result.equals(""))

        {
            return "emptyContent";
        } else

        {
            return result;
        }

    }

    public String pressStrTheRound(IOSDriver driver, IOSElement theEntry, String tValue, int roundRange, boolean direction) throws Exception {
        boolean dir = direction;
        IOSElement theResult = theEntry;
        String result = "";
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        final String localPath = this.path4Log;
        Point theLocation = null;
        Dimension theSize = null;
        try {
            theLocation = theEntry.getLocation();
            theSize = theEntry.getSize();
            int theX = theLocation.getX() + (theSize.getWidth() / 2);
            int theY = 0;
            int SCREENheight = driver.manage().window().getSize().height;
            for (; !result.equals(tValue); ) {
                if (direction == false) {  // false for down
                    if (theSize.getHeight() == 291) {
                        theY = theLocation.getY() + 125;
                    } else {
                        long rate = (long) theSize.getHeight() / (long) 291;
                        theY = theLocation.getY() + new BigDecimal(rate * 125).intValueExact();
                    }
                } else {  // true for up
                    if (theSize.getHeight() == 291) {
                        theY = theLocation.getY() + 165;
                    } else {
                        long rate = (long) theSize.getHeight() / (long) 291;
                        theY = theLocation.getY() + new BigDecimal(rate * 165).intValueExact();
                    }
                }
                if (theY >= SCREENheight || theY == 0) {
                    throw new PageException("the point Y is out of screen");
                }
                AppBaseOperation temp = new AppBaseOperation(driver);
                temp.clickPoint(driver, theX, theY);
                Thread.sleep(1000);
                result = new String(theResult.getText());
            }
        } catch (
                Exception e
                )

        {
            System.out.println(logSpace4thisPage + "In  FFPandaIOSPageTemplate :: pressStrTheRound , Other Error appear : " + e.getCause());
            result = "error";
            e.printStackTrace();
        }

        if (result == null || result.equals(""))

        {
            return "emptyContent";
        } else

        {
            return result;
        }

    }

    public String rollTheRound(IOSDriver driver, WebElement thePicker, int step, int roundRange, boolean direction) throws Exception {
        boolean dir = direction;
        IOSElement theResult = null;
        String result = "";
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        final String localPath = this.path4Log;
        Point theLocation = null;
        Dimension theSize = null;
        int stepNum = 1;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: rollTheRound ~~~ +++");

            theLocation = thePicker.getLocation();
            theSize = thePicker.getSize();
            int startX = theLocation.getX() + (theSize.getWidth() / 2);
            int endX = startX;
            int startY = 0;
            int endY = 0;
            int SCREENwidth = driver.manage().window().getSize().width;
            int SCREENheight = driver.manage().window().getSize().height;
            int widgetHigh = 0;
            if ((theLocation.getY() + theSize.getHeight()) > SCREENheight) {
                widgetHigh = SCREENheight - theLocation.getY();
            } else {
                widgetHigh = theSize.getHeight();
            }
            if (dir == true) {
                if (roundRange % 2 == 1) {
                    startY = theLocation.getY() + (widgetHigh * (roundRange + 1 / 4) / (roundRange + 5));
                    endY = startY - 30;
                } else {
                    startY = theLocation.getY() + (widgetHigh * (roundRange / 4) / (roundRange + 4));
                    endY = startY - 30;
                }
                for (int ind = 0; ind < stepNum; ind++) {
                    moveUPwithPara(driver, startX, startY, endX, endY, timeDuration * 5);
                }
            } else {
                if (roundRange % 2 == 1) {
                    startY = theLocation.getY() + (widgetHigh * (roundRange + 1 / 2) / (roundRange + 11));
                    endY = startY + 30;
                } else {
                    startY = theLocation.getY() + (widgetHigh * (roundRange / 2) / (roundRange + 12));
                    endY = startY + 30;
                }
                for (int ind = 0; ind < stepNum; ind++) {
                    moveDOWNwithPara(driver, startX, startY, endX, endY, timeDuration * 5);
                }
            }

            Thread.sleep(1000);
            result = new String(theResult.getText());
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  FFPandaIOSPageTemplate :: rollTheRound , Other Error appear : " + e.getCause());
            result = "error";
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }


    public boolean rollTheRoundSingle(IOSDriver driver, WebElement thePicker, int roundRange, boolean direction) throws Exception {
        boolean dir = direction;
        IOSElement theResult = null;
        boolean result = false;
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        final String localPath = this.path4Log;
        Point theLocation = null;
        Dimension theSize = null;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: rollTheRoundSingle ~~~ +++");

            theLocation = thePicker.getLocation();
            theSize = thePicker.getSize();
            int startX = theLocation.getX() + (theSize.getWidth() / 2);
            int endX = startX;
            int startY = 0;
            int endY = 0;
            int SCREENwidth = driver.manage().window().getSize().width;
            int SCREENheight = driver.manage().window().getSize().height;
            int widgetHigh = 0;
            if ((theLocation.getY() + theSize.getHeight()) > SCREENheight) {
                widgetHigh = SCREENheight - theLocation.getY();
            } else {
                widgetHigh = theSize.getHeight();
            }
            if (dir == true) {
                if (roundRange % 2 == 1) {
                    startY = theLocation.getY() + (widgetHigh * (roundRange + 1 / 4) / (roundRange + 5));
                    endY = startY - 30;
                } else {
                    startY = theLocation.getY() + (widgetHigh * (roundRange / 4) / (roundRange + 4));
                    endY = startY - 30;
                }
                moveUPwithPara(driver, startX, startY, endX, endY, timeDuration * 5);
            } else {
                if (roundRange % 2 == 1) {
                    startY = theLocation.getY() + (widgetHigh * (roundRange + 1 / 2) / (roundRange + 11));
                    endY = startY + 30;
                } else {
                    startY = theLocation.getY() + (widgetHigh * (roundRange / 2) / (roundRange + 12));
                    endY = startY + 30;
                }
                moveDOWNwithPara(driver, startX, startY, endX, endY, timeDuration * 5);
            }
            //      Thread.sleep(1000);
            //     result = new String(theResult.getText());

        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  FFPandaIOSPageTemplate :: rollTheRoundSingle , Other Error appear : " + e.getCause());
            result = false;
            e.printStackTrace();
        }
        //    if (result == null || result.equals("")) {
        //        return "emptyContent";
        //    } else {
        //        return result;
        //   }
        return true;
    }

    //TODO
    public String getElementContent(IOSDriver driver, String
            eleName, String eleType, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, HashMap<String, String> textMap) throws
            Exception {
        WebElement theResult = null;
        List<WebElement> eleList = new ArrayList<WebElement>();
        String result = null;
        final String localPath = this.path4Log;
        Thread.sleep(1000);
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: getElementContent ~~~ +++");
            if (eleType.equalsIgnoreCase("xpath")) {
                for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                        IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                        theResult = xWait.until(new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver driver) {
                                WebElement temp = null;
                                try {
                                    temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                        result = theResult.getText();
                    }
                }
            } else if (eleType.equalsIgnoreCase("classname")) {
                for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                        IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                        eleList = xWait.until(new IOSExpectedCondition<List>() {
                            public List<WebElement> apply(IOSDriver driver) {
                                List<WebElement> temp = new ArrayList<WebElement>();
                                try {
                                    temp = findEleListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                        result = theResult.getText();
                    }
                }
                for (int ind = 0; ind < eleList.size(); ind++) {
                    result = new String(result + "_:REX:_" + eleList.get(ind).getText());
                }
            } else {
                throw new XMLException("Wrong type in XML for : " + eleName);
            }

        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  FFPandaIOSPageTemplate :: getElementContent , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }


    }


    public List<String> getElementContentList(IOSDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap) throws
            Exception {
        List<WebElement> theResult = null;
        List<String> result = new ArrayList<String>();
        final String localPath = this.path4Log;
        Thread.sleep(1000);
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: getElementContentList ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
                    theResult =
                            xWait.until(
                                    new IOSExpectedCondition<List>() {
                                        public List<WebElement> apply(IOSDriver driver) {
                                            return driver.findElements((By) tempContentEntry.getValue());
                                        }
                                    });
                    if (theResult.size() >= 1) {
                        for (int ind = 0; ind < theResult.size(); ind++) {
                            result.add(theResult.get(ind).getText());
                        }
                    } else {
                        throw new REXException("No item found in getElementContent FOR contentcheck");
                    }
                    if (eleName.toLowerCase().contains("phone")) {
                        List<String> tempResult = new ArrayList<String>();
                        for (int ind = 0; ind < result.size(); ind++) {
                            tempResult.add(result.get(ind).replaceAll("-", ""));
                        }
                        result.clear();
                        result.addAll(tempResult);
                    }

                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  FFPandaIOSPageTemplate :: getElementContentList , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        if (result == null || result.size() == 0) {
            return new ArrayList<String>();
        } else {
            return result;
        }
    }

    public String getElementContentWithSearch(IOSDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap) throws
            Exception {
        WebElement theResult = null;
        String result = null;
        final String localPath = this.path4Log;
        Thread.sleep(1000);
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: getElementContentWithSearch ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    theResult = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);  // IOSDriver driver, final By theKey, int timeValue, String path
                    result = theResult.getText();
                    if (eleName.toLowerCase().contains("phone")) {
                        result = new String(result.replaceAll("-", ""));
                    }
                    break;
                }
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  FFPandaIOSPageTemplate :: getElementContentWithSearch , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }

    public String getElementContent(IOSDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, boolean flag4search) throws
            Exception {   // with search choose by ex para
        String result = null;
        final String localPath = this.path4Log;
        Thread.sleep(1000);
        try {
            if (flag4search == true) {
                System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: getElementContent, with search ~~~ +++");
                for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        WebElement theResult = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);  // IOSDriver driver, final By theKey, int timeValue, String path
                        result = theResult.getText();
                        if (eleName.toLowerCase().contains("phone")) {
                            result = new String(result.replaceAll("-", ""));
                        }
                        break;
                    }
                }
            } else {
                System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate :: getElementContent, without search ~~~ +++");
                for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        WebElement theResult = findElementWithOutSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);  // IOSDriver driver, final By theKey, int timeValue, String path
                        result = theResult.getText();
                        if (eleName.toLowerCase().contains("phone")) {
                            result = new String(result.replaceAll("-", ""));
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  FFPandaIOSPageTemplate :: getElementContent , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }

    public boolean compareTheContent(String fromEle, String fromXML) {
        String eleText = filter4space(fromEle);
        String xmlText = filter4space(fromXML);
        if (eleText.equalsIgnoreCase(xmlText)) {
            return true;
        } else {
            return false;
        }
    }

    public String checkAbnormalElement(IOSDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap) throws REXException {
        WebElement theResult = null;
        String result = null;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The checkAbnormalElement in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    eleName = new String(tempContentEntry.getKey().toString());
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    IOSDriverWaitNOScreen xWait = new IOSDriverWaitNOScreen(driver, 2);
                    theResult = xWait.until(
                            new IOSExpectedCondition<WebElement>() {
                                public WebElement apply(IOSDriver driver) {
                                    return driver.findElement((By) tempContentEntry.getValue());
                                }
                            });
                    break;
                }
            }
            if (theResult != null) {
                String tempDvalue = eleMap.get(eleName).getDefaultValue();
                String tempTextcontnent = eleMap.get(eleName).getTextContent();
                String currentValue = theResult.getText();
                if (compareTheContent(currentValue, tempDvalue) || compareTheContent(currentValue, tempTextcontnent)) {
                    result = "someerror";
                } else if (tempDvalue.equals("") && tempTextcontnent.equals("") && !currentValue.equals("")) {
                    result = "someerror";
                } else {
                    result = "ready";
                }
            } else {
                result = "ready";
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            result = "ready";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("The result in template . abnormal ele is : " + result);
            return result;
        }
    }


    public String getContentInBtn(IOSDriver driver, String eleName) throws Exception {
        String theResult = null;
        try {
            for (Iterator it = this.byMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, this.path4Log);

                    theResult = xWait.until(
                            new IOSExpectedCondition<String>() {
                                public String apply(IOSDriver driver) {
                                    WebElement theX = driver.findElement((By) tempContentEntry.getValue());
                                    return theX.getText();
                                }
                            });
                    break;
                }
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . getContentInBtn , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        return theResult;
    }

    public boolean getSingleEle(IOSDriver driver, String eleName, HashMap<String, By> inMap) throws
            REXException {
        WebElement theResult = null;
        boolean result = false;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The getSingleEle in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, this.path4Log);
                    theResult = xWait.until(
                            new IOSExpectedCondition<WebElement>() {
                                public WebElement apply(IOSDriver driver) {
                                    return driver.findElement((By) tempContentEntry.getValue());
                                }
                            });
                    if (theResult != null) {
                        result = true;
                    }
                    break;
                }
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . getSingleEle , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        return result;
    }

    public String getSingleEleText(IOSDriver driver, String eleName, HashMap<String, By> inMap) throws
            REXException {
        WebElement theResult = null;
        String result = null;
        try {
            System.out.println("    +++ ~~~ In the FFPandaIOSPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The getSingleEleText in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, this.path4Log);
                    theResult = xWait.until(
                            new IOSExpectedCondition<WebElement>() {
                                public WebElement apply(IOSDriver driver) {
                                    return driver.findElement((By) tempContentEntry.getValue());
                                }
                            });
                    if (theResult != null) {
                        result = theResult.getText();
                    }
                    break;
                }
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In FFPandaIOSPageTemplate . getSingleEleText , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        return result;
    }

    public String comPageCheck(IOSDriver theD, String type4check, String para4check) throws Exception {  // type4check : ready, loading, error, temp , etc.  , para4check: target element name, all element, title element
        String result = null;
        boolean flag = false;
        String eleText = null;
        try {
            if (type4check.equals("ready")) {       // ready mode
                if (para4check.equals("all")) {     // check all element for ready mode
                    String[] temp = this.content.getContentNameList4type(this.eleContentMap, "ready");
                    for (int ind = 0; ind < temp.length; ind++) {
                        flag = getSingleEle(theD, para4check, this.byMap);
                    }
                } else if (para4check.equals("title")) // check title for ready mode
                {
                    ArrayList<String> temp = this.content.getContentNameList4name(this.eleContentMap, "PageTitle");
                    if (temp.size() != 1) {
                        flag = false;
                        System.out.println("More than 1 title element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        flag = true;
                        System.out.println("TODO  compare the ((ElementMapping) temp[0]).getTextContent() and compare with the element current content to confirm the result");
                    }
                } else if (para4check.equals("alertTitle")) {
                    ArrayList<String> temp = this.content.getContentNameList4name(this.eleContentMap, "alertTitle");
                    if (temp.size() != 1) {
                        flag = false;
                        System.out.println("More than 1 alertTitle element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        flag = true;
                    }
                } else if (para4check.equals("overview")) {
                    ArrayList<String> temp = this.content.getContentNameList4name(this.eleContentMap, "overview");
                    for (int ind = 0; ind < temp.size(); ind++) {
                        eleText = getSingleEleTextGroup(theD, temp.get(ind), this.byMap);
                        TimeZone tz = TimeZone.getTimeZone("America/New_York");
                        DateFormat theMonth = new SimpleDateFormat("MMM", Locale.ENGLISH);
                        theMonth.setTimeZone(tz);
                        DateFormat theYear = new SimpleDateFormat("yyyy", Locale.ENGLISH);
                        theYear.setTimeZone(tz);
                        if ((eleText.toLowerCase().contains(theMonth.format(new Date())))
                                && (eleText.contains(theYear.format(new Date())))) {
                            flag = true;
                            continue;
                        } else {
                            throw new REXException("calendar title don't match the (full name) month,(digital)year");
                        }
                    }
                } else {

                }
            } else {
                System.out.println("Other type of check are not ready");
            }
            if (flag == true) {
                result = "pass";
            } else {
                result = "fail";
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            flag = false;
            result = "fail";
        } catch (REXException e) {
            e.printStackTrace();
            flag = false;
            result = "fail";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }


    public String comPageCheckWithInstanceInfo(IOSDriver theD, String type4check, String para4check, IOSPageContent inContent, HashMap<String, ElementMapping> inEleContentMap, HashMap<String, By> inByMap) {  // type4check : ready, loading, error, temp , etc.  , para4check: target element name, all element, title element
        String result = null;
        boolean flag = false;
        String eleText = null;
        try {
            if (type4check.equals("ready")) {       // ready mode
                if (para4check.equals("all")) {     // check all element for ready mode
                    String[] temp = inContent.getContentNameList4type(inEleContentMap, "ready");
                    for (int ind = 0; ind < temp.length; ind++) {
                        flag = getSingleEle(theD, para4check, inByMap);
                    }
                } else if (para4check.equals("title")) // check title for ready mode
                {
                    ArrayList<String> temp = inContent.getContentNameList4name(inEleContentMap, "PageTitle");
                    if (temp.size() != 1) {
                        flag = false;
                        System.out.println("More than 1 title element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        flag = true;
                        System.out.println("TODO  compare the ((ElementMapping) temp[0]).getTextContent() and compare with the element current content to confirm the result");
                    }
                } else if (para4check.equals("alertTitle")) {
                    ArrayList<String> temp = inContent.getContentNameList4name(inEleContentMap, "alertTitle");
                    if (temp.size() != 1) {
                        flag = false;
                        System.out.println("More than 1 alertTitle element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        flag = true;
                    }
                } else if (para4check.equals("overview")) {
                    ArrayList<String> temp = inContent.getContentNameList4name(inEleContentMap, "overview");
                    for (int ind = 0; ind < temp.size(); ind++) {
                        eleText = getSingleEleTextGroup(theD, temp.get(ind), inByMap);
                        System.out.println("here is the text of calendar title : " + eleText);
                        TimeZone tz = TimeZone.getTimeZone("America/New_York");
                        DateFormat theMonth = new SimpleDateFormat("MMM", Locale.ENGLISH);
                        theMonth.setTimeZone(tz);
                        DateFormat theYear = new SimpleDateFormat("yyyy", Locale.ENGLISH);
                        theYear.setTimeZone(tz);
                        String monthPart = theMonth.format(new Date());
                        String yearPart = theYear.format(new Date());
                        if ((eleText.toLowerCase().contains(monthPart.toLowerCase()))
                                || (eleText.contains(yearPart))) {
                            flag = true;
                            continue;
                        } else {
                            throw new REXException("calendar title don't match the (full name) month,(digital)year");
                        }
                    }
                } else {

                }
            } else {
                System.out.println("Other type of check are not ready");
            }
            if (flag == true) {
                result = "pass";
            } else {
                result = "fail";
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            flag = false;
            result = "fail";
        } catch (REXException e) {
            e.printStackTrace();
            flag = false;
            result = "fail";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }


    public String comPageCheck(IOSDriver theD, String type4check, String para4check, ANDPageContent
            tContent, HashMap<String, ElementMapping> eMap, HashMap<String, By> bMap) {  // type4check : ready, loading, error, temp , etc.  , para4check: target element name, all element, title element
        String result = null;
        boolean flag = false;
        try {
            if (type4check.equals("ready")) {       // ready mode
                if (para4check.equals("all")) {     // check all element for ready mode
                    String[] temp = tContent.getContentNameList4type(eMap, "ready");
                    for (int ind = 0; ind < temp.length; ind++) {
                        flag = getSingleEle(theD, para4check, bMap);
                    }
                } else if (para4check.equals("title")) // check title for ready mode
                {
                    String[] temp = tContent.getContentNameList4name(eMap, "PageTitle");
                    if (temp.length != 1) {
                        flag = false;
                        System.out.println("More than 1 title element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        flag = true;
                        System.out.println("TODO  compare the ((ElementMapping) temp[0]).getTextContent() and compare with the element current content to confirm the result");
                    }
                } else if (para4check.equals("alertTitle")) {
                    String[] temp = tContent.getContentNameList4name(eMap, "alertTitle");
                    if (temp.length != 1) {
                        flag = false;
                        System.out.println("More than 1 alertTitle element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        flag = true;
                    }
                } else if (para4check.equals("bothBtn")) {
                    String[] temp = tContent.getContentNameList4name(eMap, "yesBtn");
                    if (temp.length != 1) {
                        flag = false;
                        System.out.println("More than 1 yesBtn element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        flag = true;
                    }
                    temp = tContent.getContentNameList4name(eMap, "noBtn");
                    if (temp.length != 1) {
                        flag = false;
                        System.out.println("More than 1 noBtn element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        flag = true;

                    }
                } else {
                }
            } else {
                System.out.println("Other type of check are not ready");
            }
            if (flag == true) {
                result = "pass";
            } else {
                result = "fail";
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            flag = false;
            result = "fail";
        } catch (REXException e) {
            e.printStackTrace();
            flag = false;
            result = "fail";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public boolean tableMatch(IOSDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L,
                              int index4path, HashMap<String, Integer> waitStepMap, String key, By titleStr, By recordStr) throws Exception {
        IOSElement theResult = null;
        IOSElement theRecord = null;
        List<WebElement> tempList = new ArrayList<WebElement>();
        List<IOSElement> theTitleList = new ArrayList<IOSElement>();
        List<IOSElement> theRecordList = new ArrayList<IOSElement>();
        final String localPath = p4L;
        int flag4loop = 0;
        int moveGap = 0;
        boolean findMatch = false;
        for (; flag4loop < 3 && findMatch == false; moveUPwithY(driver, moveGap)) {
            System.out.println("In the tableMatch of AND Page ! ");
            moveGap = 0;
            tempList = driver.findElements(titleStr);
            for (WebElement tEle : tempList) {
                theTitleList.add((IOSElement) tEle);
                System.out.println("title list :" + tEle.getText());
                if (tEle.getText().toLowerCase().contains(key.toLowerCase())) {
                    findMatch = true;
                }
            }

            if (theTitleList.size() != 7) {
                findMatch = false;
                flag4loop++;
                Point firstP = null;
                Point lastP = null;
                if (theTitleList.size() > theRecordList.size()) {
                    firstP = theTitleList.get(0).getLocation();
                    lastP = theTitleList.get(theTitleList.size() - 1).getLocation();
                    int firstY = firstP.getY();
                    int lastY = lastP.getY();
                    moveGap = lastY - firstY;
                } else {
                    throw new PageException("the title of table is missing , more record than title ! ");
                }
                theTitleList.clear();
                //  theRecordList.clear();
            } else {
                if (findMatch == true) {
                    break;
                } else {
                    flag4loop++;
                }
            }
        }

        return findMatch;
    }


    public String btnMatchReturnValue(IOSDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L,
                                      int index4path, String type, String rowLocatorString, String recordType, String
                                              recordLocatorStr, HashMap<String, Integer> waitStepMap, String key) throws Exception {
        String result = null;
        IOSElement theResult = null;
        IOSElement theRecord = null;
        List<IOSElement> theMapValueList = new ArrayList<IOSElement>();
        List<IOSElement> theRecordList = new ArrayList<IOSElement>();
        IOSElement titleElement = null;
        final String localPath = p4L;
        By theComByKey = null;
        int TITLEindex = 1;
        String RECORDindex = "1";
        String rowContent = rowLocatorString;
        String stepINString = null;
        int steprange = 3;
        int flag4end = 0;
        int flag4groupend = 0;
        String locatorString = null;
        String resultpart1 = "";
        String resultpart2 = "";
        int indexDig = 0;
        int recordDig = 0;
        // current element name is xxx_mapvalue
        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().toLowerCase().contains((eleName).toLowerCase())) { // for map value element
                locatorString = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();
                break;
            }
        }

        flag4end = 0;
        if (rowContent.contains(":REXindex:") && rowContent.contains(":REXrecord:")) {
            for (int ind = 0; (ind <= steprange) && (theRecord == null) && (flag4end < 3); ind++) {  // TODO try yo use this part for getstaticelementcontent()
                System.out.println("step ~~~~~~~~~~~~~~~~~~" + ind);
                stepINString = new String(String.valueOf(TITLEindex + ind));
                String newContent1 = new String(rowContent.replaceFirst(":REXindex:", stepINString));  // for single
                flag4groupend = 0;
                for (int index = 1; (index <= 31) && (flag4groupend < 3); index++) {
                    String newContent2 = new String(newContent1.replaceFirst(":REXrecord:", String.valueOf(index)));
                    final By singleRecord = By.xpath(newContent2);
                    System.out.println(newContent2);
                    try {
                        theResult = (IOSElement) driver.findElement(singleRecord);
                        if (theResult.getText().toLowerCase().contains(key.toLowerCase())) {
                            resultpart2 = checkTheNextPage(eleName.replace("_mapvalue", "_record"), eleMap);
                            System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + resultpart2);
                            if (!resultpart2.equals("noRoute") && (resultpart2 != "") && (resultpart2 != null)) {
                                Date date = new Date();
                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String time = format.format(date);
                                ScreenShot(driver, "Record_During_Switch_" + time + ".png", localPath);
                            }
                            indexDig = Integer.parseInt(stepINString);
                            recordDig = index;
                            break;
                        } else {
                            theResult = null;
                        }
                    } catch (Exception e) {
                        flag4groupend++;
                    }
                }
                if (theRecordList.size() == 0) {
                    flag4end++;
                }
            }
        } else {
            System.out.println("Current the method " + this.getClass().getSimpleName() + " only support the str contains :REXindex AND :REXrecord at the same time");
            //TODO more layer in record
        }

        if (locatorString == null || locatorString == "") {
            resultpart1 = "noRelatedMapValue";
        } else if (locatorString.contains(":REXindex:") && locatorString.contains(":REXrecord:")) {
            String newContent1 = new String(locatorString.replaceFirst(":REXindex:", String.valueOf(indexDig)));
            String newContent2 = new String(newContent1.replaceFirst(":REXrecord:", String.valueOf(recordDig)));
            final By singleRecord = By.xpath(newContent2);
            try {
                theRecord = (IOSElement) driver.findElement(singleRecord);
                resultpart1 = theRecord.getText();
                System.out.println(resultpart1);
                result = new String(resultpart1 + "::" + resultpart2);
                theResult.click();
            } catch (Exception e) {
                throw new REXException("failed to locate the element for record with indexDig AND recordDig");
            }
        } else {
            System.out.println("Current the method " + this.getClass().getSimpleName() + " only support the str contains :REXindex AND :REXrecord at the same time");
            //TODO more layer in record
        }
        if (result == null || result.equals("::")) {
            return "::noRoute";
        } else {
            return result;
        }
    }


    public void moveUPwithY(IOSDriver dr, int extY) throws Exception {
        int wide = Integer.parseInt(readProperity.getProValue("screenWide"));
        int length = Integer.parseInt(readProperity.getProValue("screenLength"));
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        try {
            int start_x = wide / 2;
            int end_x = wide / 2;
            int start_y = (length / 5) * 4;
            int end_y = start_y - extY;
            AppBaseOperation temp = new AppBaseOperation(dr);
            Thread.sleep(500);
            temp.t(start_x, start_y, end_x, end_y, timeDuration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getCause().toString());
        } finally {
            Thread.sleep(500);
        }
    }

    public String recordSearch(IOSDriver driver, String
            eleName, HashMap<String, ElementMapping> eleMap, String p4L, int index4path, String type, String
                                       rowLocatorString, String recordType, String recordLocatorStr) throws Exception {
        String result = null;
        IOSElement theResult = null;
        Thread.sleep(500);
        final String localPath = p4L;
        By theComByKey = null;
        int TITLEindex = 0;
        String RECORDindex = "1";
        String rowContent = rowLocatorString;
        String stepINString = null;
        int steprange = 4;
        int flag4end = 0;
        int moveAccount = 4;
        final IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
        if (eleName.contains("_record")) {
            TITLEindex = index4path;  //getIndex4BtnElement(driver, xWait, theComByKey, tempEleName, eleMap);
        }
        for (int move = 0; move < moveAccount && theResult == null; move++) {
            for (int ind = 0; (ind <= steprange) && (theResult == null) && (flag4end < 5); ind++) {  // TODO try yo use this part for getstaticelementcontent()
                System.out.println("step ~~~~~~~~~~~~~~~~~~" + ind);
                stepINString = new String(String.valueOf(TITLEindex + ind));
                String newContent1 = new String(rowContent.replaceFirst(":REXindex:", stepINString));  // for single
                String newContent2 = new String(newContent1.replaceFirst(":REXrecord:", RECORDindex));
                final By singleRecord = By.xpath(newContent2);
                System.out.println(newContent2);
                try {
                    theResult = (IOSElement) driver.findElement(singleRecord);
                } catch (Exception e) {
                    //TODO ?
                    flag4end++;
                }
            }
            if (theResult == null) {
                moveUpFitScreen(driver);
                move++;
            }
        }
        if (theResult != null) {
            result = checkTheNextPage(eleName, eleMap);
            System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
            Thread.sleep(2000);
            theResult.click();
            if (!result.equals("noRoute") && (result != "") && (result != null)) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                ScreenShot(driver, "Record_During_Switch_" + time + ".png", p4L);
            }
        } else {
            throw new REXException("failed to find the element with inde step ");
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public boolean pressMonthItem(IOSDriver theD, String theMonth, HashMap<String, By> thebyMap) throws Exception {  // type4check : ready, loading, error, temp , etc.  , para4check: target element name, all element, title element
        String targetMonth = theMonth;
        boolean flag = false;
        WebElement theItem = null;
        boolean rollUP = false;
        String result = "";
        By tempBy = thebyMap.get("monthRoll");
        if (tempBy != null) {
            System.out.println("monthRoll" + " :!:");
            IOSDriverWait xWait = new IOSDriverWait(theD, timeOutInSeconds, this.path4Log);
            theItem = theD.findElement(tempBy);
        } else {
            for (Iterator it = thebyMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase("monthRoll")) {
                    System.out.println("monthRoll" + " ::");
                    IOSDriverWait xWait = new IOSDriverWait(theD, timeOutInSeconds, this.path4Log);
                    theItem = theD.findElement((By) tempContentEntry.getValue());
                    break;
                }
            }
        }
        if (theItem == null) {
            throw new REXException("failed to find the monthRoll");
        } else {
            String currentDayStr = theItem.getText();
            if (currentDayStr.equalsIgnoreCase(targetMonth)) {
                flag = true;
            } else {
                rollUP = false;
                result = pressTheRound(theD, (IOSElement) theItem, targetMonth, rollUP);
            }
            System.out.println("Current value in roller is : " + result);
        }

        if (!result.equals("error") && !result.equals("emptyContent")) { //TODO
            flag = true;
        }
        return flag;
    }

    public String recordSearch(IOSDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<String, ElementMapping> eleMap, String p4L,
                               int index4path, String type, String rowLocatorString, String recordType, String
                                       recordLocatorStr, HashMap<String, Integer> waitStepMap, String key) throws Exception {
        String result = null;
        IOSElement theResult = null;
        List<IOSElement> theResultList = new ArrayList<IOSElement>();
        final String localPath = p4L;
        By theComByKey = null;
        int TITLEindex = 0;
        String RECORDindex = "1";
        String rowContent = rowLocatorString;
        String stepINString = null;
        int steprange = 3;
        int flag4end = 0;
        int flag4groupend = 0;

        final IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, localPath);
        if (eleName.contains("_record")) {
            TITLEindex = index4path;  //getIndex4BtnElement(driver, xWait, theComByKey, tempEleName, eleMap);
        }
        if (!rowContent.contains(":REXitem:")) {
            for (int ind = 0; (ind <= steprange) && (theResult == null) && (flag4end < 3); ind++) {  // TODO try yo use this part for getstaticelementcontent()
                System.out.println("step ~~~~~~~~~~~~~~~~~~" + ind);
                stepINString = new String(String.valueOf(TITLEindex + ind));
                String newContent1 = new String(rowContent.replaceFirst(":REXindex:", stepINString));  // for single
                flag4groupend = 0;
                for (int index = 1; (index <= 31) && (flag4groupend < 3); index++) {
                    String newContent2 = new String(newContent1.replaceFirst(":REXrecord:", String.valueOf(index)));
                    final By singleRecord = By.xpath(newContent2);
                    System.out.println(newContent2);
                    try {
                        theResult = (IOSElement) driver.findElement(singleRecord);
                        theResultList.add(theResult);
                        theResult = null;
                    } catch (Exception e) {
                        flag4groupend++;
                    }
                }
                if (theResultList.size() == 0) {
                    flag4end++;
                }
            }
        } else {
            //TODO more layer in record
        }
        if (theResultList.size() != 0) {
            boolean flag4quit = false;
            for (int thei = 0; thei < theResultList.size() && flag4quit == false; thei++) {
                WebElement theEle = theResultList.get(thei);
                if (theEle.getText().toLowerCase().contains(key.toLowerCase())) {
                    result = checkTheNextPage(eleName, eleMap);
                    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                    theEle.click();
                    if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        ScreenShot(driver, "Record_During_Switch_" + time + ".png", p4L);
                    }
                    flag4quit = true;
                }
            }
            if (flag4quit == false) {
                throw new REXException("failed to find the element with match the key word");
            }
        } else {
            throw new REXException("failed to find the element with inde step ");
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }

    public static void main(String args[]) {
        IOSDriver driver = null;
        List<String> a = Arrays.asList("v", "f", "3", "z");
        List<String> b = Arrays.asList("3", "z", "v", "f");
        System.out.println(compare(a, b));
        List<IOSElement> ax = new ArrayList<IOSElement>();
        System.out.println(ax.size());

        String ccc = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.support.v4.widget.DrawerLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[2]/android.widget.LinearLayout[1]/android.view.View[1]/android.widget.ScrollView[1]/android.widget.RelativeLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.RelativeLayout[:REXindex:]/android.widget.RelativeLayout[1]";
        String[] xxx = ccc.split(":REXindex:");
        System.out.println("xxx length " + xxx.length);
        System.out.println("end_ " + xxx[1]);

        Point a1 = new Point(1, 1);
        Point a2 = new Point(2, 2);
        Point b1 = new Point(1, 1);
        Point b2 = new Point(3, 2);

        String para1 = "";
        String regex = "^[a-z0-9A-Z\\s*]+$";
        //     para1 = filter4space(para1);
        if (para1.matches(regex)) {
            System.out.println("");
            for (int ind = 0; ind < para1.length(); ind++) {
                int chr = para1.toLowerCase().charAt(ind);
                if (chr >= 48 && chr <= 57) {
                    chr = chr - 41;
                    driver.sendKeyEvent(chr);
                } else if (chr >= 97 && chr <= 122) {
                    chr = chr - 68;
                    driver.sendKeyEvent(chr);
                } else if (chr >= 65 && chr <= 90) {
                    System.out.println("    +++ ~~~ Wrong Search Key , HIGH CASE! ~~~ +++");
                } else if (chr == 32) {
                    driver.sendKeyEvent(62);
                } else {
                    System.out.println("    +++ ~~~ Wrong Search Key , OTHER CASE! ~~~ +++");
                }
            }
        } else {
            System.out.println("    +++ ~~~ Wrong Search Key , including NON \"^[a-z0-9A-Z\\\\s*]+$\"! ~~~ +++");
        }


        System.out.println(" compare !!!! " + (a1.equals(b1)));


        try {
            File app = new File("/Users/appledev131/Downloads", "app-debug.apk");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));

            capabilities.setCapability("deviceName", "Android Emulator");

            capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
            capabilities.setCapability(CapabilityType.VERSION, "5.1");
            capabilities.setCapability("app", app.getAbsolutePath());
            capabilities.setCapability("app-package", "com.pwc.talentexchange");
            capabilities.setCapability("app-activity", ".activity.BaseActivity");
            capabilities.setCapability("unicodeKeyboard", "True");
            capabilities.setCapability("resetKeyboard", "True");


            driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }


}


