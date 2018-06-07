package farfetch.panda.beta.ios.AppPage.fox;

import REXSH.REXAUTO.Component.Page.ANDPageContent;
import REXSH.REXAUTO.Component.Page.Element.elementEntity;
import REXSH.REXAUTO.Component.PageLoadWait.FOXDriverWait;
import REXSH.REXAUTO.Component.PageLoadWait.FOXDriverWaitNOScreen;
import REXSH.REXAUTO.Component.PageLoadWait.FOXExpectedCondition;
import REXSH.REXAUTO.Component.PageLoadWait.REXDriverWait;
import REXSH.REXAUTO.LocalException.PageException;
import REXSH.REXAUTO.LocalException.PageTitleException;
import REXSH.REXAUTO.LocalException.REXException;
import REXSH.REXAUTO.LocalException.XMLException;
import Utility.Log;
import Utility.readProperity;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by appledev131 on 4/11/16.
 */
public abstract class foxPageTemplate {
    Properties props = System.getProperties();

    public String path4Log = System.getProperty("user.dir");

    String osName = props.getProperty("os.name");
    protected String targetPagename = "bhomePage";
    protected String comFileName = null;
    protected FOXPageContent content = null;
    protected HashMap<StringBuilder, elementEntity> eleContentMap = null;
    protected HashMap<String, WebElement> eleMap = null;
    public HashMap<String, By> byMap = null;
    public HashMap<StringBuilder, StringBuilder> dValueMap = null;
    protected WebDriver dr = null;
    protected int osType = 1;
    protected HashMap<String, String> nextPageList = null;
    protected long timeOutInSeconds = Long.parseLong(readProperity.getProValue("pageLoadTimeout"));
    private static String logSpace4thisPage = " --- bANDPageTemplate --";

    public foxPageTemplate(WebDriver theD) throws Exception {
    }

    public foxPageTemplate(String theFile, WebDriver theD, Log theLogger) throws Exception {
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

    public HashMap<StringBuilder, StringBuilder> getEleWinMap() {
        return this.content.eleWindowMap;
    }

    public HashMap<String, By> getByMap(WebDriver driver) {
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
                System.out.println(logSpace4thisPage + "Check the value in Entry in combind : " + (elementEntity) tempContentEntry.getValue() + " !!! ");

                //     tempElement = combindAndElement(driver, (elementEntity) tempContentEntry.getValue(), 6, this.osTypeInContent);
                if (((elementEntity) tempContentEntry.getValue()).getType().toString().equalsIgnoreCase("xpath")) {
                    tempElement = By.xpath(((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString());
                } else if (((elementEntity) tempContentEntry.getValue()).getType().toString().equalsIgnoreCase("id")) {
                    tempElement = By.id(((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString());
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


    public boolean compareTheContent(String fromEle, String fromXML) {
        String eleText = filter4space(fromEle);
        String xmlText = filter4space(fromXML);
        if (eleText.equalsIgnoreCase(xmlText)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean titleCheckTe(WebDriver driver, String eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, HashMap<StringBuilder, StringBuilder> textMap, String extPath) throws Exception {
        boolean result = false;
        WebElement theResult = null;
        final String localPath = extPath;
        try {
            System.out.println("    +++ ~~~ In the ANDPageTemplate :: titleCheckTe in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            loadingJudgement(driver, eleMap);
            final By tempBy = inMap.get(eleName);
            FOXDriverWait xWait = new FOXDriverWait(driver, 2, localPath);
            System.out.println(eleName + " ::");
            if (tempBy != null) {
                theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        WebElement temp = null;
                        try {
                            temp = (WebElement) findElementRepeat(driver, tempBy, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
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
                        theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                            public WebElement apply(WebDriver driver) {
                                WebElement temp = null;
                                try {
                                    temp = (WebElement) findElementRepeat(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
                    }
                }
            }
            System.out.println("AND , titleCheckTe : " + theResult.getText());
            if (!textMap.get(eleName).toString().equals("") && textMap.get(eleName).toString().equals(theResult.getText())) {
                result = true;
                System.out.println("AND , titleCheckTe : PASS");
            } else if (textMap.get(eleName).toString().equals("")) {
                result = true;
                System.out.println("AND , titleCheckTe : PASS");
            } else {
                result = false;
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }


    public void textOperationMAC(WebDriver driver, String eleName, String para1, HashMap<String, By> inMap, HashMap<StringBuilder, StringBuilder> dValueMap) throws Exception {
        String dValue = null;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        System.out.println("Some on called me with : " + para1);
        System.out.println("inMap size : " + inMap.size());

        for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) { // &&theResult==null;
            System.out.println("iterator : " + it == null);
            System.out.println("inMap size : " + inMap.size());
            final Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                System.out.println(eleName + " ::");
                FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);

                theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        WebElement temp = null;
                        try {
                            temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                        } catch (Exception e) {
                            throw new TimeoutException(e.getMessage());
                        }
                        return temp;
                    }
                });
                for (Iterator iter = dValueMap.entrySet().iterator(); iter.hasNext(); ) { // &&dValue==null;
                    final Map.Entry tempValueEntry = (Map.Entry) iter.next();
                    if (tempValueEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        dValue = (String) tempValueEntry.getValue();
                        break;
                    }
                }
            }
        }
      //  theResult.click();
      //  Thread.sleep(200);
        System.out.println("Ready to set Value for text field !!! ");
        if(theResult.isEnabled()){
            if(theResult.isSelected())
            {

            }
            else {theResult.click();}
            theResult.clear();
            theResult.sendKeys(para1);
        }
        else {
            throw new PageTitleException("element is not enabled");
        }
       // theResult.setValue(para1);
    }


    public void textOperationMACexact(WebDriver driver, String eleName, String para1, HashMap<String, By> inMap, HashMap<StringBuilder, StringBuilder> dValueMap) throws Exception {
        String dValue = null;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        System.out.println("Some on called me with : " + para1);
        System.out.println("inMap size : " + inMap.size());
        try {
            final By tempBy = inMap.get(eleName);
            System.out.println(eleName + " ::");
            FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
            theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver driver) {
                    WebElement temp = null;
                    try {
                        temp = (WebElement) findElementWithSearch(driver, tempBy, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                    } catch (Exception e) {
                        throw new TimeoutException(e.getMessage());
                    }
                    return temp;
                }
            });
            //theResult.click();
          //  Thread.sleep(200);
            System.out.println("Ready to set Value for text field !!! ");
            if(theResult.isEnabled()){
                if(theResult.isSelected())
                {

                }
                else {theResult.click();}
                theResult.clear();
                theResult.sendKeys(para1);
            }
            else {            throw new PageTitleException("element is not enabled");
            }
           // theResult.setValue(para1);

        } catch (Exception e) {
            throw new REXException("Failed with textOperationMACexact IN bANDPageTemplate");
        }
    }


    public void textOperationMACexact(WebDriver driver, By extTempBy, String para1, HashMap<String, By> inMap, HashMap<StringBuilder, StringBuilder> dValueMap) throws Exception {
        String dValue = null;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        System.out.println("Some on called me with : " + para1);
        System.out.println("inMap size : " + inMap.size());

        try {
            final By tempBy = extTempBy;
            FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);

            theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver driver) {
                    WebElement temp = null;
                    try {
                        temp = (WebElement) findElementWithSearch(driver, tempBy, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                    } catch (Exception e) {
                        throw new TimeoutException(e.getMessage());
                    }
                    return temp;
                }
            });
           // theResult.click();
            //Thread.sleep(200);
            System.out.println("Ready to set Value for text field !!! ");
            if(theResult.isEnabled()){
                if(theResult.isSelected())
                {

                }
                else {theResult.click();}
                theResult.clear();
                theResult.sendKeys(para1);
            }
            else {            throw new PageTitleException("element is not enabled");
            }
            //theResult.setValue(para1);
        } catch (Exception e) {
            throw new REXException("Failed with textOperationMACexact IN bANDPageTemplate");
        }
    }


    public void textOperationWINDOWSexact(WebDriver driver, String eleName, String para1, HashMap<String, By> inMap, HashMap<StringBuilder, StringBuilder> dValueMap) throws Exception {
        String dValue = null;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        System.out.println("Some on called me with : " + para1);
        System.out.println("inMap size : " + inMap.size());
        final By tempBy = inMap.get(eleName);
        System.out.println(eleName + " ::");
        FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
        theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
                WebElement temp = null;
                try {
                    temp = (WebElement) findElementWithSearch(driver, tempBy, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                } catch (Exception e) {
                    throw new TimeoutException(e.getMessage());
                }
                return temp;
            }
        });

        theResult.click();
        Thread.sleep(200);
        String tempTextFieldContent = theResult.getText(); // page row content
        boolean loop4text = false;
        System.out.println("Ready to set Value for text field !!! ");
        //   theResult.setValue(para1);

        if ((dValue != "") && (dValue != null)) {  // xml d value is not empty
            if (!tempTextFieldContent.equals(dValue)) {  // page row content != xml
                for (String x = theResult.getText(); !x.equals(dValue) && loop4text == false; ) {
                    System.out.println("### Default value of current textfield : " + x);
                    System.out.println("~~~ Default value from page element : " + dValue);
                    int tempTextLength = theResult.getText().length();
                    if (tempTextLength == 0) {
                        loop4text = true;
                    }
                    theResult.sendKeys("123");
                    for (int i = 0; i < tempTextLength; i++) {
                        theResult.sendKeys("67");
                        x = theResult.getText();   // check page current content
                        if (x.equals(tempTextFieldContent))  // page current content == row content
                        {
                            loop4text = true;  //
                        }
                    }
                }
            } else {  // xml d value == page row content
                System.out.println("### Current textfield match " + dValue + " :: " + theResult.getText());
            }
        } else {  // xml is not defined
            for (String x = theResult.getText(); (x.length() != 0) && loop4text == false; ) {  // page content is not empty
                int tempTextLength = x.length();
                System.out.println("### Default value of current textfield : " + x);
                System.out.println("### Default value of current textfield : " + tempTextLength);
                System.out.println("~~~ Default value from page element : " + dValue);
                theResult.sendKeys("123");
                for (int i = 0; i < tempTextLength; i++) {
                    theResult.sendKeys("67");
                    x = theResult.getText(); // check page current content
                    if (x.equals(tempTextFieldContent))  // page current content == row content
                    {
                        loop4text = true;
                    }
                }
            }
        }
        System.out.println("Ready to send the key !!! ");
        theResult.sendKeys(para1);

        Thread.sleep(500);
        try {
        //     driver.hideKeyboard();
        } catch (Exception e) {
            System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
            throw e;
        }

    }


    public void textOperationWINDOWSexact(WebDriver driver, By extTempBy, String para1, HashMap<String, By> inMap, HashMap<StringBuilder, StringBuilder> dValueMap) throws Exception {
        String dValue = null;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        System.out.println("Some on called me with : " + para1);
        System.out.println("inMap size : " + inMap.size());
        final By tempBy = extTempBy;

        FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);

        theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
                WebElement temp = null;
                try {
                    temp = (WebElement) findElementWithSearch(driver, tempBy, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                } catch (Exception e) {
                    throw new TimeoutException(e.getMessage());
                }
                return temp;
            }
        });

        theResult.click();
        Thread.sleep(200);
        String tempTextFieldContent = theResult.getText(); // page row content
        boolean loop4text = false;
        System.out.println("Ready to set Value for text field !!! ");
        //   theResult.setValue(para1);

        if ((dValue != "") && (dValue != null)) {  // xml d value is not empty
            if (!tempTextFieldContent.equals(dValue)) {  // page row content != xml
                theResult.clear();
                tempTextFieldContent = theResult.getText();
                if (!tempTextFieldContent.equals(dValue)) {
                    System.out.println("### Current textfield don't match " + dValue + "::" + theResult.getText());
                }

            } else {  // xml d value == page row content
                System.out.println("### Current textfield match " + dValue + " :: " + theResult.getText());
            }
        } else {  // xml is not defined
            theResult.clear();
        }
        System.out.println("Ready to send the key !!! ");
        theResult.sendKeys(para1);

        Thread.sleep(500);
        try {
        //     driver.hideKeyboard();
        } catch (Exception e) {
            System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
            throw e;
        }

    }


    public void textOperationWINDOWS(WebDriver driver, String eleName, String para1, HashMap<String, By> inMap, HashMap<StringBuilder, StringBuilder> dValueMap) throws Exception {
        String dValue = null;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        System.out.println("Some on called me with : " + para1);
        System.out.println("inMap size : " + inMap.size());
        for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) { // &&theResult==null;
            System.out.println("iterator : " + it == null);
            System.out.println("inMap size : " + inMap.size());
            final Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                System.out.println(eleName + " ::");
                FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);

                theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        WebElement temp = null;
                        try {
                            temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                        } catch (Exception e) {
                            throw new TimeoutException(e.getMessage());
                        }
                        return temp;
                    }
                });
                for (Iterator iter = dValueMap.entrySet().iterator(); iter.hasNext(); ) { // &&dValue==null;
                    final Map.Entry tempValueEntry = (Map.Entry) iter.next();
                    if (tempValueEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        dValue = (String) tempValueEntry.getValue();
                        break;
                    }
                }
                theResult.click();
                Thread.sleep(200);
                String tempTextFieldContent = theResult.getText(); // page row content
                boolean loop4text = false;
                System.out.println("Ready to set Value for text field !!! ");
                //   theResult.setValue(para1);
                if ((dValue != "") && (dValue != null)) {  // xml d value is not empty
                    if (!tempTextFieldContent.equals(dValue)) {  // page row content != xml
                        theResult.clear();
                        tempTextFieldContent = theResult.getText();
                        if (!tempTextFieldContent.equals(dValue)) {
                            System.out.println("### Current textfield don't match " + dValue + "::" + theResult.getText());
                        }
                    } else {  // xml d value == page row content
                        System.out.println("### Current textfield match " + dValue + " :: " + theResult.getText());
                    }
                } else if (dValue.equals("")) {  // xml is not defined
                    theResult.clear();
                }
                System.out.println("Ready to send the key !!! ");
                theResult.sendKeys(para1);
                Thread.sleep(500);
                try {
                //     driver.hideKeyboard();
                } catch (Exception e) {
                    System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
                    throw e;
                }
            }
        }
    }


    public void textOperation(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, HashMap<StringBuilder, StringBuilder> dValueMap) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        System.out.println(" ~~~~~~ IN bANDPageTemplate :textOperation with OS choose ~~~~~~");
        try {
            if (autoOSType.equalsIgnoreCase("MAC")) {
                By temp = null;
                temp = inMap.get(eleName);
                if (temp != null) {
                    textOperationMACexact(driver, temp, para1, inMap, dValueMap);
                } else {
                    textOperationMAC(driver, eleName, para1, inMap, dValueMap);
                }
            } else {
                By temp = null;
                temp = inMap.get(eleName);
                if (temp != null) {
                    textOperationWINDOWSexact(driver, temp, para1, inMap, dValueMap);
                } else {
                    textOperationWINDOWS(driver, eleName, para1, inMap, dValueMap);
                }
            }
        } catch (Exception e) {
            System.out.println(" ~~~~~~ Some thing wrong IN bANDPageTemplate :textOperation with OS choose ~~~~~~");
            throw e;
        }
    }


    public void textListOperation(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        String dValue = null;
        WebElement theResult = null;
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
                                        theResult = (WebElement) resultlist.get(ind);
                                        break;
                                    }
                                }
                            }
                        }
                        if (flag4match == false) {
                            theResult = (WebElement) resultlist.get(1);
                        }
                     //   theResult.click();
                     //   Thread.sleep(200);
                        System.out.println("Ready to send the key !!! ");
                        if(theResult.isEnabled()){
                            if(theResult.isSelected())
                            {

                            }
                            else {theResult.click();}
                            theResult.clear();
                            theResult.sendKeys(para1);
                        }
                        else {            throw new PageTitleException("element is not enabled");
                        }
                   //     theResult.setValue(para1);
                        Thread.sleep(500);
                        try {
                        //     driver.hideKeyboard();
                        } catch (Exception e) {
                            System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
                            throw e;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }


    public void textListOperationexact(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        String dValue = null;
        WebElement theResult = null;
        List<WebElement> resultlist = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("Some on called me with : " + para1);
            System.out.println("inMap size : " + inMap.size());
            boolean flag4match = false;

            final By tempBy = inMap.get(eleName);
            System.out.println(eleName + " ::");
            resultlist = findEleListWithSearch(driver, tempBy, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);

            if (resultlist.size() != 0) {

                for (Iterator iter = dValueMap.entrySet().iterator(); iter.hasNext() && flag4match == false; ) { // &&dValue==null;
                    for (int ind = 0; ind < resultlist.size() && flag4match == false; ind++) {
                        final Map.Entry tempValueEntry = (Map.Entry) iter.next();
                        if (tempValueEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                            dValue = (String) tempValueEntry.getValue();
                            if (dValue.equalsIgnoreCase(resultlist.get(ind).getText())) {
                                flag4match = true;
                                theResult = (WebElement) resultlist.get(ind);
                                break;
                            }
                        }
                    }
                }
            }

            if (flag4match == false) {
                theResult = (WebElement) resultlist.get(1);
            }
         //   theResult.click();
        //    Thread.sleep(200);
            System.out.println("Ready to send the key !!! ");
          //  theResult.setValue(para1);
            if(theResult.isEnabled()){
                if(theResult.isSelected())
                {

                }
                else {theResult.click();}
                theResult.clear();
                theResult.sendKeys(para1);
            }
            else {            throw new PageTitleException("element is not enabled");
            }
            Thread.sleep(500);
            try {
            //     driver.hideKeyboard();
            } catch (Exception e) {
                System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
                throw e;
            }
        } catch (Exception e) {
            throw e;
        }
    }


    public void textListOperationexact(WebDriver driver, By extTempBy, String
            para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap, String eleName) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        String dValue = null;
        WebElement theResult = null;
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
                                theResult = (WebElement) resultlist.get(ind);
                                break;
                            }
                        }
                    }
                }
            }

            if (flag4match == false) {
                theResult = (WebElement) resultlist.get(1);
            }
          //  theResult.click();
           // Thread.sleep(200);
            System.out.println("Ready to send the key !!! ");
         //   theResult.setValue(para1);
            if(theResult.isEnabled()){
                if(theResult.isSelected())
                {

                }
                else {theResult.click();}
                theResult.clear();
                theResult.sendKeys(para1);
            }
            else {            throw new PageTitleException("element is not enabled");
            }
            Thread.sleep(500);
            try {
            //     driver.hideKeyboard();
            } catch (Exception e) {
                System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
                throw e;
            }
        } catch (Exception e) {
            throw e;
        }
    }


    public String textOperationWithSaveInputMAC(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
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
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, this.path4Log);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                 //   theResult.click();
                 //   Thread.sleep(200);
                    System.out.println("Ready to set value !!! ");
                //    theResult.setValue(para1);
                    if(theResult.isEnabled()){
                        if(theResult.isSelected())
                        {

                        }
                        else {theResult.click();}
                        theResult.clear();
                        theResult.sendKeys(para1);
                    }
                    else {            throw new PageTitleException("element is not enabled");
                    }
                    //   theResult.sendKeys(para1);
                    Thread.sleep(200);
                    String tempText = theResult.getText();
                    if ((tempText != "") && (tempText != null)) {
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
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . textOperationWithSaveInputMAC, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }


    public String textOperationWithSaveInputWindows(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
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
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, this.path4Log);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                 //   theResult.click();
                  //  Thread.sleep(200);
                    System.out.println("Ready to send the key !!! ");
                //    theResult.setValue(para1);
                    if(theResult.isEnabled()){
                        if(theResult.isSelected())
                        {

                        }
                        else {theResult.click();}
                        theResult.clear();
                        theResult.sendKeys(para1);
                    }
                    else {            throw new PageTitleException("element is not enabled");
                    }
                    //   theResult.sendKeys(para1);
                    Thread.sleep(200);
                    String tempText = theResult.getText();
                    if ((tempText != "") && (tempText != null)) {
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
            //     driver.hideKeyboard();
            } catch (Exception e) {
                System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
                throw e;
            }

        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . textOperationWithSaveInputWindows, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }

    public String textOperationWithSaveInput(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
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
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, this.path4Log);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                 //   theResult.click();
                 //   Thread.sleep(200);
                    System.out.println("Ready to send the key !!! ");
                //    theResult.setValue(para1);
                    if(theResult.isEnabled()){
                        if(theResult.isSelected())
                        {

                        }
                        else {theResult.click();}
                        theResult.clear();
                        theResult.sendKeys(para1);
                    }
                    else {            throw new PageTitleException("element is not enabled");
                    }
                    //   theResult.sendKeys(para1);
                    Thread.sleep(200);
                    String tempText = theResult.getText();
                    if ((tempText != "") && (tempText != null)) {
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
            //     driver.hideKeyboard();
            } catch (Exception e) {
                System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
                throw e;
            }

        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . textOperationWithSaveInput, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }

    public String textOperationWithSaveInputMAC(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap) throws Exception {
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
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);

                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    for (Iterator iter = dValueMap.entrySet().iterator(); iter.hasNext(); ) {
                        final Map.Entry tempValueEntry = (Map.Entry) iter.next();
                        if (tempValueEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                            dValue = (String) tempValueEntry.getValue();
                            break;
                        }
                    }
                //    theResult.click();
                //    Thread.sleep(200);
                    System.out.println("Ready to set the value !!! ");
                    if(theResult.isEnabled()){
                        if(theResult.isSelected())
                        {

                        }
                        else {theResult.click();}
                        theResult.clear();
                        theResult.sendKeys(para1);
                    }
                    else {            throw new PageTitleException("element is not enabled");
                    }
             //       theResult.setValue(para1);
                    Thread.sleep(200);
                    String tempText = theResult.getText();

                    if ((tempText != "") && (tempText != null)) {
                        if (eleName.toLowerCase().contains("phone")) {
                            tempText = new String(tempText.replaceAll("-", ""));
                        }
                        if (tempText.equals(para1)) {
                            result = tempText;
                        } else {
                            throw new REXException("textOperationWithSaveInputMAC : the real input para is different from excepted ");
                        }
                    } else {
                        result = "failedToGetText";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . textOperationWithSaveInputMAC, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }

    }

    public String textOperationWithSaveInputWindows(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap) throws Exception {
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
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);

                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    for (Iterator iter = dValueMap.entrySet().iterator(); iter.hasNext(); ) {
                        final Map.Entry tempValueEntry = (Map.Entry) iter.next();
                        if (tempValueEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                            dValue = (String) tempValueEntry.getValue();
                            break;
                        }
                    }
                    theResult.click();
                    Thread.sleep(200);
                    String tempTextFieldContent = theResult.getText(); // page row content
                    boolean loop4text = false;
                    if ((dValue != "") && (dValue != null)) {  // xml d value is not empty
                        if (!tempTextFieldContent.equals(dValue)) {  // page row content != xml
                            for (String x = theResult.getText(); !x.equals(dValue) && loop4text == false; ) {
                                System.out.println("### Default value of current textfield : " + x);
                                System.out.println("~~~ Default value from page element : " + dValue);
                                int tempTextLength = theResult.getText().length();
                                if (tempTextLength == 0) {
                                    loop4text = true;
                                }
                                theResult.sendKeys("123");
                                for (int i = 0; i < tempTextLength; i++) {
                                    theResult.sendKeys("67");
                                    x = theResult.getText();   // check page current content
                                    if (x.equals(tempTextFieldContent))  // page current content == row content
                                    {
                                        loop4text = true;  //
                                    }
                                }
                            }
                        } else {  // xml d value == page row content
                            System.out.println("### Current textfield match " + dValue + " :: " + theResult.getText());
                        }
                    } else if (dValue.equals("")) {  // xml is not defined
                        for (String x = theResult.getText(); (x.length() != 0) && loop4text == false; ) {  // page content is not empty
                            int tempTextLength = x.length();
                            System.out.println("### Default value of current textfield : " + x);
                            System.out.println("### Default value of current textfield : " + tempTextLength);
                            System.out.println("~~~ Default value from page element : " + dValue);
                            theResult.sendKeys("123");
                            for (int i = 0; i < tempTextLength; i++) {
                                theResult.sendKeys("67");
                                x = theResult.getText(); // check page current content
                                if (x.equals(tempTextFieldContent))  // page current content == row content
                                {
                                    loop4text = true;
                                }
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
                            throw new REXException("textOperationWithSaveInputWindows : the real input para is different from excepted ");
                        }
                    } else {
                        result = "failedToGetText";
                    }
                }
            }
            Thread.sleep(500);
            try {
            //     driver.hideKeyboard();
            } catch (Exception e) {
                System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate .textOperationWithSaveInputWindows, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }

    public String textOperationWithSaveInput(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        String result = null;
        try {
            if (autoOSType.equalsIgnoreCase("MAC")) {
                result = textOperationWithSaveInputMAC(driver, eleName, para1, inMap, dValueMap);
            } else {
                result = textOperationWithSaveInputWindows(driver, eleName, para1, inMap, dValueMap);
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . textOperationWithSaveInput with OS choose, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }


    public String textListOperationWithSaveInputMAC(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap) throws Exception {
        String result = null;
        String dValue = null;
        List<WebElement> resultlist = null;
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
                                        theResult = (WebElement) resultlist.get(ind);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (flag4match == false) {
                        theResult = (WebElement) resultlist.get(1);
                    }
                //    theResult.click();
                //    Thread.sleep(200);
                    System.out.println("Ready to set value !!! ");
               //     theResult.setValue(para1);
                    if(theResult.isEnabled()){
                        if(theResult.isSelected())
                        {

                        }
                        else {theResult.click();}
                        theResult.clear();
                        theResult.sendKeys(para1);
                    }
                    else {            throw new PageTitleException("element is not enabled");
                    }
                    Thread.sleep(200);
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


    public String textListOperationWithSaveInputWindows(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap) throws Exception {
        String result = null;
        String dValue = null;
        List<WebElement> resultlist = null;
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
                                        theResult = (WebElement) resultlist.get(ind);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (flag4match == false) {
                        theResult = (WebElement) resultlist.get(1);
                    }
                    theResult.click();
                    Thread.sleep(200);
                    theResult.clear();
                    Thread.sleep(200);
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
                            throw new REXException("textListOperationWithSaveInputWindows : the real input para is different from excepted ");
                        }
                    } else {
                        result = "failedToGetText";
                    }
                }
            }
            Thread.sleep(500);
            try {
            //     driver.hideKeyboard();
            } catch (Exception e) {
                System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . textListOperationWithSaveInputWindows, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }


    public String textListOperationWithSaveInput(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, HashMap<String, String> dValueMap) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        if (autoOSType.equalsIgnoreCase("MAC")) {
            return textListOperationWithSaveInputMAC(driver, eleName, para1, inMap, dValueMap);
        } else {
            return textListOperationWithSaveInputWindows(driver, eleName, para1, inMap, dValueMap);
        }
    }


    public String textOperationWithSaveInputMAC(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, String dValuePara) throws Exception {
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
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                  //  theResult.click();
                 //   Thread.sleep(200);
                //    theResult.clear();
                //    Thread.sleep(200);
                    System.out.println("Ready to set value !!! ");
                //    theResult.setValue(para1);
                    if(theResult.isEnabled()){
                        if(theResult.isSelected())
                        {

                        }
                        else {theResult.click();}
                        theResult.clear();
                        theResult.sendKeys(para1);
                    }
                    else {            throw new PageTitleException("element is not enabled");
                    }
                    Thread.sleep(200);
                    String tempText = theResult.getText();
                    if (!tempText.equals("") && (tempText != null)) {
                        if (eleName.toLowerCase().contains("phone")) {
                            tempText = new String(tempText.replaceAll("-", ""));
                        }
                        if (tempText.equals(para1) || eleName.contains("PSW")) {
                            result = tempText;
                        } else {
                            throw new REXException("textOperationWithSaveInputMAC : the real input para is different from excepted ");
                        }
                    } else {
                        result = "failedToGetText";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . textOperationWithSaveInputMAC, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }


    public String textOperationWithSaveInputWindows(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, String dValuePara) throws Exception {
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
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    theResult.click();
                    Thread.sleep(200);
                    theResult.clear();
                    System.out.println("Ready to send the key !!! ");
                    theResult.sendKeys(para1);
                    Thread.sleep(200);
                    String tempText = theResult.getText();
                    if ((tempText != "") && (tempText != null)) {
                        if (eleName.toLowerCase().contains("phone")) {
                            tempText = new String(tempText.replaceAll("-", ""));
                        }
                        if (tempText.equals(para1) || eleName.contains("PSW")) {
                            result = tempText;
                        } else {
                            throw new REXException("textOperationWithSaveInputWindows : the real input para is different from excepted ");
                        }
                    } else {
                        result = "failedToGetText";
                    }
                }
            }
            Thread.sleep(500);
            try {
            //     driver.hideKeyboard();
            } catch (Exception e) {
                System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . textOperationWithSaveInputWindows, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }


    public String textOperationWithSaveInputSmartMAC(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, String dValuePara) throws Exception {
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
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
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
                                theResult.sendKeys("123");
                                for (int i = 0; i < tempTextLength; i++) {
                                    theResult.sendKeys("67");
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
                            theResult.sendKeys("123");
                            String temp4loopcheck = null;
                            for (int i = 0; i < tempTextLength; i++) {
                                theResult.sendKeys("67");
                            }
                            temp4loopcheck = new String(x);
                            x = theResult.getText();
                            if (temp4loopcheck.equals(x)) {
                                loop4text = true;
                            }
                        }
                    }
                    System.out.println("Ready to set value !!! ");
                  //  theResult.setValue(para1);
                    if(theResult.isEnabled()){
                        if(theResult.isSelected())
                        {

                        }
                        else {theResult.click();}
                        theResult.clear();
                        theResult.sendKeys(para1);
                    }
                    else {            throw new PageTitleException("element is not enabled");
                    }
                    Thread.sleep(200);
                    String tempText = theResult.getText();
                    if ((tempText != "") && (tempText != null)) {
                        if (eleName.toLowerCase().contains("phone")) {
                            tempText = new String(tempText.replaceAll("-", ""));
                        }
                        if (tempText.equals(para1) || eleName.contains("PSW")) {
                            result = tempText;
                        } else {
                            throw new REXException("textOperationWithSaveInputWindows : the real input para is different from excepted ");
                        }
                    } else {
                        result = "failedToGetText";
                    }
                }
            }
            Thread.sleep(500);
            try {
            //     driver.hideKeyboard();
            } catch (Exception e) {
                System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . textOperationWithSaveInputWindows, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }


    public String textOperationWithSaveInput(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, String dValuePara) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        if (autoOSType.equalsIgnoreCase("MAC")) {
            return textOperationWithSaveInputMAC(driver, eleName, para1, inMap, dValuePara);
        } else {
            return textOperationWithSaveInputWindows(driver, eleName, para1, inMap, dValuePara);
        }
    }


    public String textOperationListWithSaveInputMAC(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, String dValuePara) throws Exception {
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
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                   // theResult.click();
                   // Thread.sleep(200);
                    System.out.println("Ready to set value !!! ");
                   // theResult.setValue(para1);
                    if(theResult.isEnabled()){
                        if(theResult.isSelected())
                        {

                        }
                        else {theResult.click();}
                        theResult.clear();
                        theResult.sendKeys(para1);
                    }
                    else {            throw new PageTitleException("element is not enabled");
                    }
                    Thread.sleep(200);
                    String tempText = theResult.getText();
                    if ((tempText != "") && (tempText != null)) {
                        if (eleName.toLowerCase().contains("phone")) {
                            tempText = new String(tempText.replaceAll("-", ""));
                        }
                        if (tempText.equals(para1)) {
                            result = tempText;
                        } else {
                            throw new REXException("textOperationListWithSaveInputMAC : the real input para is different from excepted ");
                        }
                    } else {
                        result = "failedToGetText";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . textOperationListWithSaveInputMAC, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }


    public String textOperationListWithSaveInputWindows(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, String dValuePara) throws Exception {
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
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
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
                                theResult.sendKeys("123");
                                for (int i = 0; i < tempTextLength; i++) {
                                    theResult.sendKeys("67");
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
                            theResult.sendKeys("123");
                            String temp4loopcheck = null;
                            for (int i = 0; i < tempTextLength; i++) {
                                theResult.sendKeys("67");
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
                            throw new REXException("textOperationListWithSaveInputWindows : the real input para is different from excepted ");
                        }
                    } else {
                        result = "failedToGetText";
                    }
                }
            }
            Thread.sleep(500);
            try {
            //     driver.hideKeyboard();
            } catch (Exception e) {
                System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . textOperationListWithSaveInputWindows, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }


    public String textOperationListWithSaveInput(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap, String dValuePara) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        if (autoOSType.equalsIgnoreCase("MAC")) {
            return textOperationListWithSaveInputMAC(driver, eleName, para1, inMap, dValuePara);
        } else {
            return textOperationListWithSaveInputWindows(driver, eleName, para1, inMap, dValuePara);
        }
    }


    public void getElementContentMAC(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap) throws Exception {
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
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                  //  theResult.click();
                    System.out.println("Ready to set value !!! ");
                 //   theResult.setValue(para1);
                    if(theResult.isEnabled()){
                        if(theResult.isSelected())
                        {

                        }
                        else {theResult.click();}
                        theResult.clear();
                        theResult.sendKeys(para1);
                    }
                    else {            throw new PageTitleException("element is not enabled");
                    }
                }
            }
            Thread.sleep(500);
            try {
            //     driver.hideKeyboard();
            } catch (Exception e) {
                System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . getElementContentMAC, Other Error appear : " + e.getCause());
            throw e;
        }
    }

    public void getElementContentWindows(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap) throws Exception {
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
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    theResult.click();
                    System.out.println("Ready to send the key !!! ");
                    theResult.sendKeys(para1);
                }
            }
            Thread.sleep(500);
            try {
            //     driver.hideKeyboard();
            } catch (Exception e) {
                System.out.println(" ~~~~~~ Some thing wrong during hide keyboard ~~~~~~");
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . getElementContentWindows, Other Error appear : " + e.getCause());
            throw e;
        }
    }

    public void getElementContent(WebDriver driver, String eleName, String
            para1, HashMap<String, By> inMap) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        if (autoOSType.equalsIgnoreCase("MAC")) {
            getElementContentMAC(driver, eleName, para1, inMap);
        } else {
            getElementContentWindows(driver, eleName, para1, inMap);
        }
    }


    public WebElement findElementWithSearch(WebDriver driver, final By theKey, int timeValue, String path) throws
            Exception {
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
        for (; result == null; ) {
            try {
                FOXDriverWait wait4find = new FOXDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);
                result = wait4find.until(new FOXExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        return driver.findElement(theKey);
                    }
                });
                if (result != null) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("In the findElementWithSearch, searching ");
                //moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                Thread.sleep(localTime / ((step + 1) * 4));
                flag4timeout++;
            }
            if (flag4timeout > 12) {
                throw new REXException(" The element can't be find after 12 times screen move! ");
            }
        }
        return result;
    }


    public WebElement findElementRepeat(WebDriver driver, final By theKey, int timeValue, String path) throws
            Exception {
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
        for (; result == null; ) {
            try {
                FOXDriverWait wait4find = new FOXDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);
                result = wait4find.until(new FOXExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        return driver.findElement(theKey);
                    }
                });
                if (result != null) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("In the findElementRepeat, waiting ");
                Thread.sleep(localTime / ((step + 1) * 4));
                flag4timeout++;
            }
            if (flag4timeout > 6) {
                throw new REXException(" The element can't be find after 3 times screen move! ");
            }
        }
        return result;
    }


    public WebElement findElementWithOutSearch(WebDriver driver, final By theKey, int timeValue, String
            path) throws Exception {
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
            FOXDriverWait wait4find = new FOXDriverWait(driver, localTime / 1000, path, false);
            result = wait4find.until(new FOXExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(theKey);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }


    public List<WebElement> findEleListWithSearch(WebDriver driver, final By theKey, int timeValue, String
            path) throws Exception {
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
                FOXDriverWait wait4find = new FOXDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);

                result = wait4find.until(new FOXExpectedCondition<List>() {
                    public List<WebElement> apply(WebDriver driver) {
                        return driver.findElements(theKey);
                    }
                });
                if (result != null) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("In the findEleListWithSearch, saerching ");
                //moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                Thread.sleep(localTime / ((step + 1) * 4));
                flag4timeout++;
            }
            if (flag4timeout > 6) {
                throw new REXException(" The element can't be find after 3 times screen move! ");
            }
        }
        return result;
    }

    public static ArrayList<WebElement> removeEleDuplicate(ArrayList arlList) {
        LinkedHashSet<WebElement> set = new LinkedHashSet<WebElement>(arlList);
        //Constructing listWithoutDuplicateElements using set
        arlList.clear();
        arlList = new ArrayList<WebElement>(set);
        System.out.println("+++++++" + arlList.size());
        return arlList;
    }

    //TODO
    public List<String> plusEleContentListWithSearch(WebDriver driver, final By theKey, int timeValue, String
            path) throws Exception {
        ArrayList<String> conList = new ArrayList<String>();
        ArrayList<WebElement> result = new ArrayList<WebElement>();
        ArrayList<String> lastresult = new ArrayList<String>();
        ArrayList<String> currentresult = new ArrayList<String>();
        conList.clear();
        int localTime = timeValue;
        int step = 2;
        int flag4timeout = 0;
        int theDuration = Integer.parseInt(readProperity.getProValue("singleElementSearchTimeDuration"));
        if (timeValue > 10000) {
            localTime = theDuration;
        } else {
            localTime = 5400;
        }
        boolean flag4end = false;
        for (; flag4end == false && flag4timeout <= 9; ) {
            try {
                FOXDriverWait wait4find = new FOXDriverWait(driver, localTime / ((step + 1) * 4) / 1000, path, false);

                result = wait4find.until(new FOXExpectedCondition<ArrayList>() {
                    public ArrayList<WebElement> apply(WebDriver driver) {
                        return (ArrayList) driver.findElements(theKey);
                    }
                });
                if (result.size() != 0) {
                    for (WebElement tele : result) {
                        currentresult.add(tele.getText());
                    }
                    currentresult = removeDuplicate(currentresult);
                    if (currentresult.size() == lastresult.size()) {
                        flag4end = true;
                    }
                    System.out.println("In the plusEleListWithSearch, searching ");
                    //moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                    Thread.sleep(localTime / ((step + 1) * 4));
                } else {
                    System.out.println("In the plusEleListWithSearch, searching ");
                    //moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                    Thread.sleep(localTime / ((step + 1) * 4));
                    flag4timeout++;
                }
                lastresult = new ArrayList<String>(currentresult);
            } catch (Exception e) {
                System.out.println("In the plusEleListWithSearch, searching ");
                //moveUPwithSyncTime(driver, localTime / ((step + 1) * 4) * step);
                Thread.sleep(localTime / ((step + 1) * 4));
                flag4timeout++;
            }
        }
        return currentresult;
    }

    public List<WebElement> findEleListByIndexWithSearch(WebDriver driver, String theKey, int timeValue, String
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

    public void btnOperation(WebDriver driver, String eleName) throws Exception {
        final String localPath = this.path4Log;
        WebElement theResult = null;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The btnOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = this.byMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . btnOperation 2, Other Error appear : " + e.getCause());
            throw e;
        }
    }

    public void btnOperation(WebDriver driver, String eleName, HashMap<String, By> inMap) throws Exception {
        final String localPath = this.path4Log;
        WebElement theResult = null;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The btnOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);

                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . btnOperation 2, Other Error appear : " + e.getCause());
            throw e;
        }
    }


    public void btnOperationexact(WebDriver driver, String eleName, HashMap<String, By> inMap) throws Exception {
        final String localPath = this.path4Log;
        WebElement theResult = null;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The btnOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            final By tempBy = inMap.get(eleName);

            System.out.println(eleName + " ::");
            FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
            theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver driver) {
                    WebElement temp = null;
                    try {
                        temp = findElementWithSearch(driver, tempBy, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                    } catch (Exception e) {
                        throw new TimeoutException(e.getMessage());
                    }
                    return temp;
                }
            });
            theResult.click();

        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . btnOperation 2, Other Error appear : " + e.getCause());
            throw e;
        }
    }


    public void testOperation(WebDriver driver, String theIN) throws Exception {
        WebElement theResult = null;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The testOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            theResult = driver.findElement(By.id(theIN));
            theResult.click();
        } catch (Exception e)

        {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . testOperation, Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
    }


    public Object createInstance(Class cType, WebDriver theD) throws Exception {
        //System.out.println(" ~~~~~~ In the createInstance()");
        System.out.println("Check the cType : " + cType.toString());
        Object result = null;
        Constructor<?>[] consts = cType.getConstructors();
        Constructor<?> constructor = null;
        for (int index = 0; index < consts.length; index++) {
            //System.out.println("The consts [] is not empty ! ");
            int paramsLength = consts[index].getParameterAnnotations().length;
            //System.out.println("Check the paraType : " + consts[index].getParameterTypes());
            //System.out.println("The AndDr class type is : " + ((Class) WebDriver.class).getSimpleName().toString());
            if (paramsLength == 2) {
                System.out.println("@@@ Find the constructor with 2 para : " + consts[index]);
                constructor = consts[index];
                break;
            }
        }
        if (constructor != null) {
            //System.out.println("Double check the constructor !!!");
            Class<?>[] type = constructor.getParameterTypes();
            System.out.println("What is this ??? : " + type);
            result = cType.getConstructor(type).newInstance(theD, this.path4Log);
            System.out.println("cPage : " + result);
        }
        return result;
    }

 /*   public String checkTheNextPage(String btnName, HashMap<StringBuilder, elementEntity> eleMap) {
        String result = null;
        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry tempContentEntry = (Map.Entry) it.next();
            System.out.println("    +++ ~~~ tempContentEntry : " + tempContentEntry);
            System.out.println("    +++ ~~~ tempContentEntry.getvalue : " + tempContentEntry.getValue());
            String tempRoute = ((elementEntity) tempContentEntry.getValue()).getNextPage();
            System.out.println("    +++ ~~~ Find the temp Route : " + tempRoute);
            if (((elementEntity) tempContentEntry.getValue()).getElementName().toLowerCase().contains((btnName).toLowerCase())) {
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
*/
    public String checkLocaterStr(String btnName, HashMap<StringBuilder, elementEntity > eleMap) {
        String result = null;
        elementEntity temp = eleMap.get(btnName);
        if (temp == null) {
            for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry tempContentEntry = (Map.Entry) it.next();
                String tempRoute = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();
                if (((elementEntity) tempContentEntry.getValue()).getElementName().toString().toLowerCase().contains((btnName).toLowerCase())) {
                    if ((tempRoute != null) && (tempRoute != "")) {
                        result = tempRoute;
                    } else {
                        result = "";
                    }
                    break;
                }
            }
        } else {
            String tempRoute = temp.getLocatorStr().toString();
            if ((tempRoute != null) && (tempRoute != "")) {
                result = tempRoute;
            } else {
                result = "";
            }
        }
        return result;
    }

    public String checkTheLocaterType(String btnName, HashMap<StringBuilder, elementEntity > eleMap) {
        String result = null;
        elementEntity temp = eleMap.get(btnName);
        if (temp == null) {
            for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry tempContentEntry = (Map.Entry) it.next();
                String tempType = ((elementEntity) tempContentEntry.getValue()).getType().toString();
                if (((elementEntity) tempContentEntry.getValue()).getElementName().toString().toLowerCase().contains((btnName).toLowerCase())) {
                    if ((tempType != null) && (tempType != "")) {
                        result = tempType;
                    } else {
                        result = "";
                    }
                    break;
                }
            }
        } else {
            String tempType = temp.getType().toString();
            if ((tempType != null) && (tempType != "")) {
                result = tempType;
            } else {
                result = "";
            }
        }
        return result;
    }


    public String checkThe(String btnName, HashMap<StringBuilder, elementEntity> eleMap) {
        String result = null;
        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry tempContentEntry = (Map.Entry) it.next();
            String tempType = ((elementEntity) tempContentEntry.getValue()).getType().toString();
            if (((elementEntity) tempContentEntry.getValue()).getElementName().toString().toLowerCase().contains((btnName).toLowerCase())) {
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


    private String checkTheTextContent(String btnName, HashMap<StringBuilder , elementEntity > eleMap) throws
            InterruptedException {
        String textresult = null;
        elementEntity temp = eleMap.get(btnName);
        if (temp == null) {
            for (Iterator it = eleMap.entrySet().iterator(); (it.hasNext() && (textresult == null)); ) {
                Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                elementEntity tempEle = (elementEntity) tempContentEntry.getValue();  // get the ele instance
                String tempTextContent = tempEle.getTextContent().toString();      // get the text content value
                System.out.println(tempTextContent + " : " + tempTextContent.length());
                System.out.println("    +++ ~~~ Find the temp textContent : " + tempTextContent);
                if (tempEle.getElementName().toString().equalsIgnoreCase(btnName)) {     // if the element name which has text content value match with the incoming element name
                    if ((tempTextContent != null) && (tempTextContent != "")) {     // and the text content name is not null
                        textresult = new String(filter4space(tempTextContent));
                    } else {
                        textresult = "empty";
                    }
                    break;
                }
            }
        } else {
            String tempTextContent = temp.getTextContent().toString();
            if ((tempTextContent != null) && (tempTextContent != "")) {     // and the text content name is not null
                textresult = new String(filter4space(tempTextContent));
            } else {
                textresult = "empty";
            }
        }
        return textresult;
    }

    public String btnOperation(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap) throws Exception {
        String result = null;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: btnOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
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
                  //  result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                    theResult.click();
                  //  if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        //ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                   // }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate .btnOperation, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String btnOperationexact(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap) throws Exception {
        String result = null;
        WebElement theResult = null;
        final String localPath = this.path4Log;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: btnOperationexact in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            final By tempby = inMap.get(eleName);
            System.out.println(eleName + " :?:");
            FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
            theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver driver) {
                    WebElement temp = null;
                    try {
                        temp = findElementWithSearch(driver, tempby, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                    } catch (Exception e) {
                        throw new TimeoutException(e.getMessage());
                    }
                    return temp;
                }
            });
            System.out.println("Check the eleContMpa : " + eleMap.size());
        //    result = checkTheNextPage(eleName, eleMap);
        //    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
            theResult.click();
        //    if (!result.equals("noRoute") && (result != "") && (result != null)) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                //ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
         //   }

        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . btnOperationexact, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }

    public String calendarOperation(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L) throws Exception {
        String result = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        WebElement theResult = null;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: calendarOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            //      elementEntity tempEle = eleMap.get(eleName);
            //     if(tempEle==null){}
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
               //     result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                    theResult.click();
               //     if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        //ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
               //     }
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . calendarOperation, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }

    public String calendarOperationSuper(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L, String tDate, String dateWinName) throws Exception {
        String result = null;
        String calendarValue = null;
        String localTDate = tDate;
        String dateWin = dateWinName;
        System.out.println("    +++ ~~~ The calendarOperationSuper in bANDPageTemplate has been called ~~~ +++");
        try {
            result = absCalendarOpenWin(driver, eleName, inMap, eleMap, p4L);

            Class tempClassType = Class.forName(String.valueOf(new StringBuffer("REXSH.REXAUTO.APPunderTest.AppPage.AND.").append("bANDPage").append(dateWin)));
            System.out.println("@@@ tempClassType : " + tempClassType);
            Object tempIns = createInstance(tempClassType, driver);
            Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{WebDriver.class, String.class, String.class});
            Object comPageCheckResult = setMethod.invoke(tempIns, driver, "ready", "overview");
            if (((String) comPageCheckResult).equals("pass")) {
                Method chooseMethod = tempClassType.getDeclaredMethod("chooseDateItem", new Class[]{WebDriver.class, String.class});
                TimeZone tz = TimeZone.getTimeZone("America/Chicago");
                Calendar cl = Calendar.getInstance(tz, Locale.US);
                String defaultDay = String.valueOf(cl.get(Calendar.DAY_OF_MONTH));
                if (Integer.parseInt(defaultDay) - 1 >= Integer.parseInt(localTDate)) {
                    int yesterdayInt = Integer.parseInt(defaultDay) - 1;  //TODO, consider the 1st day og the month
                    String yesterdayString = String.valueOf(yesterdayInt);
                    if (Integer.parseInt(localTDate) > 1 && Integer.parseInt(localTDate) <= yesterdayInt) {
                        Object selectDateResult = chooseMethod.invoke(tempIns, driver, localTDate); // chooseDateItem
                        if (selectDateResult.toString().equalsIgnoreCase("true")) {
                            Method saveMethod = tempClassType.getDeclaredMethod("saveDate", new Class[]{WebDriver.class, String.class, String.class});
                            Object saveDateResult = saveMethod.invoke(tempIns, driver, localTDate, p4L);
                            if (saveDateResult.toString() != "" && saveDateResult != null) {
                                result = saveDateResult.toString();
                                calendarValue = calendarGetTextOperation(driver, eleName, inMap, eleMap, p4L);
                                System.out.println(" ~~~~~~ Here is the date in calendar after save : " + calendarValue.toString());
                            }
                        }
                    }
                } else {
                    Method backMethod = tempClassType.getDeclaredMethod("btnOperationRoute", new Class[]{WebDriver.class, String.class}); //  btnOperationRoute(WebDriver driver, String eleName)
                    backMethod.invoke(tempIns, driver, "backwardBtn");
                    Object selectDateResult = chooseMethod.invoke(tempIns, driver, localTDate); // chooseDateItem
                    if (selectDateResult.toString().equalsIgnoreCase("true")) {
                        Method saveMethod = tempClassType.getDeclaredMethod("saveDate", new Class[]{WebDriver.class, String.class, String.class});
                        Object saveDateResult = saveMethod.invoke(tempIns, driver, localTDate, p4L);
                        if (saveDateResult.toString() != "" && saveDateResult != null) {
                            result = saveDateResult.toString();
                            calendarValue = calendarGetTextOperation(driver, eleName, inMap, eleMap, p4L);
                            System.out.println(" ~~~~~~ Here is the date in calendar after save : " + calendarValue.toString());
                        }
                    }
                }
            } else {
                throw new REXException("Failed to find the yesterday item in the calendar");
            }
            return result;
        } catch (Exception e) {
            throw e;
        }
    }


    public String autoCompleteOperationSuper(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L, String tDate, String autoCompleteWin) throws Exception {
        String result = null;
        String calendarValue = null;
        String localTDate = tDate;
        String autoWin = autoCompleteWin;
        System.out.println("    +++ ~~~ The autoCompleteOperationSuper in bANDPageTemplate has been called ~~~ +++");
        try {
            result = absCalendarOpenWin(driver, eleName, inMap, eleMap, p4L);

            Class tempClassType = Class.forName(String.valueOf(new StringBuffer("REXSH.REXAUTO.APPunderTest.AppPage.AND.").append("bANDPage").append(autoWin)));
            System.out.println("@@@ tempClassType : " + tempClassType);
            Object tempIns = createInstance(tempClassType, driver);
            Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{WebDriver.class, String.class, String.class});
            Object comPageCheckResult = setMethod.invoke(tempIns, driver, "ready", "title");
            if (((String) comPageCheckResult).equals("pass")) {
                Method chooseMethod = tempClassType.getDeclaredMethod("autoCompleteOperation", new Class[]{WebDriver.class, String.class, String.class});
                Object selectDateResult = chooseMethod.invoke(tempIns, driver, "searchField", localTDate); // chooseDateItem
            } else {
                throw new REXException("Failed to find the yesterday item in the calendar");
            }
            return result;
        } catch (Exception e) {
            throw e;
        }
    }


    public String calendarOperationSuper(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L, String dateWinName) throws Exception {
        String result = null;
        String calendarValue = null;
        //    String localTDate = tDate;
        String dateWin = dateWinName;
        System.out.println("    +++ ~~~ The calendarOperationSuper in bANDPageTemplate has been called ~~~ +++");
        try {
            result = absCalendarOpenWin(driver, eleName, inMap, eleMap, p4L);

            Class tempClassType = Class.forName(String.valueOf(new StringBuffer("REXSH.REXAUTO.APPunderTest.AppPage.AND.").append("bANDPage").append(dateWin)));
            System.out.println("@@@ tempClassType : " + tempClassType);
            Object tempIns = createInstance(tempClassType, driver);
            Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{WebDriver.class, String.class, String.class});
            Object comPageCheckResult = setMethod.invoke(tempIns, driver, "ready", "overview");
            if (((String) comPageCheckResult).equals("pass")) {
                Method chooseMethod = tempClassType.getDeclaredMethod("chooseDateItem", new Class[]{WebDriver.class, String.class});
                TimeZone tz = TimeZone.getTimeZone("America/Chicago");
                Calendar cl = Calendar.getInstance(tz, Locale.US);
                String defaultDay = String.valueOf(cl.get(Calendar.DAY_OF_MONTH));
                if (Integer.parseInt(defaultDay) > 1) {
                    int yesterdayInt = Integer.parseInt(defaultDay) - 1;  //TODO, consider the 1st day og the month
                    String yesterdayString = String.valueOf(yesterdayInt);
                    Object selectDateResult = chooseMethod.invoke(tempIns, driver, yesterdayString); // chooseDateItem
                    if (selectDateResult.toString().equalsIgnoreCase("true")) {
                        Method saveMethod = tempClassType.getDeclaredMethod("saveDate", new Class[]{WebDriver.class, String.class, String.class});
                        Object saveDateResult = saveMethod.invoke(tempIns, driver, yesterdayString, p4L);
                        if (saveDateResult.toString() != "" && saveDateResult != null) {
                            result = saveDateResult.toString();
                            calendarValue = calendarGetTextOperation(driver, eleName, inMap, eleMap, p4L);
                            System.out.println(" ~~~~~~ Here is the date in calendar after save : " + calendarValue.toString());
                        }
                    }
                } else {
                    Method backMethod = tempClassType.getDeclaredMethod("btnOperationRoute", new Class[]{WebDriver.class, String.class}); //  btnOperationRoute(WebDriver driver, String eleName)
                    backMethod.invoke(tempIns, driver, "backwardBtn");
                    int yesterdayInt = 2;
                    if (defaultDay.equals("1")) {
                        yesterdayInt = 28;
                    }
                    String yesterdayString = String.valueOf(yesterdayInt);
                    Object selectDateResult = chooseMethod.invoke(tempIns, driver, yesterdayString); // chooseDateItem
                    if (selectDateResult.toString().equalsIgnoreCase("true")) {
                        Method saveMethod = tempClassType.getDeclaredMethod("saveDate", new Class[]{WebDriver.class, String.class, String.class});
                        Object saveDateResult = saveMethod.invoke(tempIns, driver, yesterdayString, p4L);
                        if (saveDateResult.toString() != "" && saveDateResult != null) {
                            result = saveDateResult.toString();
                            calendarValue = calendarGetTextOperation(driver, eleName, inMap, eleMap, p4L);
                            System.out.println(" ~~~~~~ Here is the date in calendar after save : " + calendarValue.toString());
                        }
                    }
                }
            } else {
                throw new REXException("Failed to find the yesterday item in the calendar");
            }
            return result;
        } catch (Exception e) {
            throw e;
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


    public static boolean checkStartwithNumInd(String extPara) {
        boolean result = false;
        String temp = null;
        System.out.println(extPara);
        Pattern test = Pattern.compile("(^[A-Za-z0-9\\s]+)(\\()(\\d+)(\\))$");
        //  Matcher matcher = pattern.matcher("10.11");
        Matcher matcher2dig = test.matcher(extPara);
        //  System.out.println(matcher.matches());
        if (matcher2dig.matches()) {
            System.out.println("Match the Start with Num ind");
            result = true;
        }
        return result; //TODO
    }

    public static String getNumFromStr(String str) {
        String dest = "";
        if (str != null) {
            dest = str.replaceAll("[^0-9]", "");
        }
        return dest;
    }

    public static String getFirstNumFromStr(String str) {
        String dest = "";
        if (str != null && checkStartwithNumInd(str)) {
            dest = str.replaceAll("[^0-9]", "");
        }
        return dest;
    }

    public static Integer getFirstNumIntFromStr(String str) throws Exception {
        int result = 0;
        String dest = "";
        try {
            if (str != null && checkStartwithNumInd(str)) {
                dest = str.replaceAll("[^0-9]", "");
                result = Integer.parseInt(dest);
            }
            return result;
        } catch (Exception e) {
            return 0;
        }
    }

    public static ArrayList<String> removeNeighbourDuplicate(ArrayList arlList) {
        ArrayList<String> result = new ArrayList<String>();
        for (Object singleStr : arlList) {
            if (result.size() == 0) {
                result.add(singleStr.toString());
            } else {
                if (result.get(result.size() - 1).equals(singleStr)) {
                } else {
                    result.add(singleStr.toString());
                }
            }
        }
        return result;
    }

    public String absCalendarOpenWin(WebDriver driver, String
            eleName, final HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L) throws Exception {
        String result = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        WebElement theResult = null;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: absCalendarOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            elementEntity tempEle = eleMap.get(eleName);
            FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
            final By tempBy = inMap.get(eleName);
            if (tempEle != null) {
                theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver driver) {
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
                        theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                            public WebElement apply(WebDriver driver) {
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
          //  result = tempEle.getNextPage();
         //   System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
            theResult.click();
        //    if (!result.equals("noRoute") && (result != "") && (result != null)) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                //ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
       //     }

        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . absCalendarOperation, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String calendarOperationexact(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L) throws Exception {
        String result = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        WebElement theResult = null;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: calendarOperationexact in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            final By tempBy = inMap.get(eleName);
            System.out.println(eleName + " :?:");
            FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
            theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver driver) {
                    WebElement temp = null;
                    try {
                        temp = findElementWithSearch(driver, tempBy, Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                    } catch (Exception e) {
                        throw new TimeoutException(e.getMessage());
                    }
                    return temp;
                }
            });
          //  result = checkTheNextPage(eleName, eleMap);
        //    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
            theResult.click();
       //     if (!result.equals("noRoute") && (result != "") && (result != null)) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                //ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
      //      }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . calendarOperationexact, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String calendarGetTextOperation(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L) throws Exception {
        String result = null;
        WebElement theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: calendarOperation in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . calendarGetTextOperation, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "";
        } else {
            return result;
        }
    }
// TODO make following loop quit with suitable reason

    public String dropDownListShow(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity > eleMap, final String p4L, HashMap<
            String, String> dValueMap) throws Exception {
        String result = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        HashMap<String, String> local = dValueMap;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: dropDownListShow in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    loadingJudgement(driver, eleMap);
                    WebElement theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
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

                    theResult.click();
                    if (result == "" || result == null) {
                        for (Iterator iter = local.entrySet().iterator(); iter.hasNext(); ) {
                            final Map.Entry tempValueEntry = (Map.Entry) iter.next();
                            if (tempValueEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                                result = (String) tempValueEntry.getValue();
                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . dropDownListShow, Other Error appear : " + e.getCause());
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    public String dropDownItemChoose(WebDriver driver, String
            preiousText, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity > eleMap, final String p4L) throws
            Exception {  //TODO
        String result = null;
        List<WebElement> theResult = new ArrayList<WebElement>();
        WebElement choosedItem = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: dropDownItemChoose in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().toLowerCase().contains("dropdown_item")) {
                    System.out.println("dropdown_item" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, p4L);
                    loadingJudgement(driver, eleMap);
                    if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new FOXExpectedCondition<List>() {
                            public List<WebElement> apply(WebDriver driver) {
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
                        theResult = xWait.until(new FOXExpectedCondition<List>() {
                            public List<WebElement> apply(WebDriver driver) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . dropDownItemChoose, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }

    public String dropDownItemChooseWithPara(WebDriver driver, String
            preiousText, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity > eleMap, final String p4L, String
                                                     extPara) throws Exception {  //TODO
        String result = null;
        List<WebElement> theResult = new ArrayList<WebElement>();
        WebElement choosedItem = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        System.out.println(" this time we want to choose the " + extPara + " item ");
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: dropDownItemChoose in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = eleMap.entrySet().iterator(); it.hasNext() && choosedItem == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase("dropdown_item")) {
                    System.out.println("dropdown_item" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, p4L);
                    loadingJudgement(driver, eleMap);
                    if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new FOXExpectedCondition<List>() {
                            public List<WebElement> apply(WebDriver driver) {
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
                        theResult = xWait.until(new FOXExpectedCondition<List>() {
                            public List<WebElement> apply(WebDriver driver) {
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
                                if (theResult.get(ind).getText().equalsIgnoreCase(extPara) || theResult.get(ind).getText().toLowerCase().contains(extPara.toLowerCase())) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . dropDownItemChoose, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            if (result != null) {
                return result;
            } else {
                throw new XMLException("No dropdown_item found in XML");
            }
        }
    }

    public String singleItemChoose(WebDriver driver, String
            preiousText, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity > eleMap, final String p4L) throws
            Exception {  //TODO
        String result = null;
        List<WebElement> theResult = new ArrayList<WebElement>();
        WebElement choosedItem = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: singleItemChoose in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().toLowerCase().contains("single_item")) {
                    System.out.println("single_item" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new FOXExpectedCondition<List>() {
                            public List<WebElement> apply(WebDriver driver) {
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
                        theResult = xWait.until(new FOXExpectedCondition<List>() {
                            public List<WebElement> apply(WebDriver driver) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . dropDownItemChoose, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }

    public String singleItemChooseWithPara(WebDriver driver, String
            preiousText, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity > eleMap, final String p4L, String
                                                   extPara) throws Exception {  //TODO
        String result = null;
        List<WebElement> theResult = new ArrayList<WebElement>();
        WebElement choosedItem = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        System.out.println(" this time we want to choose the " + extPara + " item ");
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: singleItemChooseWithPara in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && choosedItem == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().toLowerCase().contains("single_item")) {
                    System.out.println("single_item" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new FOXExpectedCondition<List>() {
                            public List<WebElement> apply(WebDriver driver) {
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
                        theResult = xWait.until(new FOXExpectedCondition<List>() {
                            public List<WebElement> apply(WebDriver driver) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . dropDownItemChoose, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }

    public String multiItemChoose(WebDriver driver, String
            preiousText, HashMap<String, By> inMap, HashMap<StringBuilder , elementEntity > eleMap, final String p4L) throws
            Exception {  //TODO
        String result = null;
        List<WebElement> theResult = new ArrayList<WebElement>();
        WebElement choosedItem = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: dropDownItemChoose in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().toLowerCase().contains("multi_item")) {
                    System.out.println("multi_item" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new FOXExpectedCondition<List>() {
                            public List<WebElement> apply(WebDriver driver) {
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
                        theResult = xWait.until(new FOXExpectedCondition<List>() {
                            public List<WebElement> apply(WebDriver driver) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . dropDownItemChoose, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }


    public String multiItemChooseWithPara(WebDriver driver, String
            preiousText, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity > eleMap, final String p4L, String
                                                  extPara) throws Exception {  //TODO
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
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: dropDownItemChoose in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && choosedItem == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().toLowerCase().contains("multi_item")) {
                    System.out.println("multi_item" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                        theResult = xWait.until(new FOXExpectedCondition<List>() {
                            public List<WebElement> apply(WebDriver driver) {
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
                        theResult = xWait.until(new FOXExpectedCondition<List>() {
                            public List<WebElement> apply(WebDriver driver) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . dropDownItemChoose, Other Error appear : " + e.getCause());
            throw e;
        }

    }

    public String searchStart(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, final String p4L, String
                                      dValue, String extPara, boolean flag4auto) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        System.out.println(" ~~~~~~ IN bANDPageTemplate :textOperation with OS choose ~~~~~~");
        String result = null;
        try {
            if (flag4auto == false) {
                if (autoOSType.equalsIgnoreCase("MAC")) {
                    result = searchStartMAC(driver, eleName, inMap, eleMap, p4L, dValue, extPara);
                } else {
                    result = searchStartWINDOWS(driver, eleName, inMap, eleMap, p4L, dValue, extPara);
                }
            } else {
                if (autoOSType.equalsIgnoreCase("MAC")) {
                    result = autoCompleteStartMAC(driver, eleName, inMap, eleMap, p4L, dValue, extPara);
                } else {
                    result = autoCompleteStartWINDOWS(driver, eleName, inMap, eleMap, p4L, dValue, extPara);
                }
            }
        } catch (Exception e) {
            System.out.println(" ~~~~~~ Some thing wrong IN bANDPageTemplate :textOperation with OS choose ~~~~~~");
            throw e;
        }
        return result;
    }

    public String searchStartMAC(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, final String p4L, String
                                         dValue, String extPara) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        System.out.println(" ~~~~~~ IN bANDPageTemplate :searchStartMAC ~~~~~~");

        String result = null;
        WebElement theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: searchStartMAC in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                   // theResult.click();
                  //  Thread.sleep(200);

                    System.out.println("Ready to send the key !!! ");
                 //   theResult.setValue(extPara);
                    if(theResult.isEnabled()){
                        if(theResult.isSelected())
                        {

                        }
                        else {theResult.click();}
                        theResult.clear();
                        theResult.sendKeys(extPara);
                    }
                    else {            throw new PageTitleException("element is not enabled");
                    }
                    Thread.sleep(200);
                    theResult.sendKeys("66");
                    //     theResult.sendKeys("66");
                    String tempText = theResult.getText();
                    if ((tempText != "") && (tempText != null)) {
                        System.out.println("tempText : " + tempText);
                        if (tempText.equalsIgnoreCase(extPara) || tempText.toLowerCase().contains(extPara.toLowerCase())) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . searchStart, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }

    public String autoCompleteStartMAC(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, final String p4L, String
                                               dValue, String extPara) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        System.out.println(" ~~~~~~ IN bANDPageTemplate :autoCompleteStartMAC ~~~~~~");

        String result = null;
        WebElement theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: autoCompleteStartMAC in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = (WebElement) findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                  //  theResult.click();
                 //   Thread.sleep(200);
                 //   theResult.clear();
                    System.out.println("Ready to send the key !!! ");
                 //   theResult.setValue(extPara);
                    if(theResult.isEnabled()){
                        if(theResult.isSelected())
                        {

                        }
                        else {theResult.click();}
                        theResult.clear();
                        theResult.sendKeys(extPara);
                    }
                    else {            throw new PageTitleException("element is not enabled");
                    }
                    Thread.sleep(200);
                    String tempText = theResult.getText();
                    if ((tempText != "") && (tempText != null)) {
                        if (tempText.equals(extPara)) {
                            result = tempText;
                        } else {
                            throw new REXException("autoCompleteStartMAC : the real input para is different from excepted ");
                        }
                    } else {
                        result = "failedToGetText";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . autoCompleteStartMAC, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }

    public String searchStartWINDOWS(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, final String p4L, String
                                             dValue, String extPara) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        System.out.println(" ~~~~~~ IN bANDPageTemplate :searchStartWINDOWS ~~~~~~");

        String result = null;
        WebElement theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: searchStartWINDOWS in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    if (dValue.equals("")) {
                        dValue = new String(theResult.getText());
                    }
                    theResult.click();
                    Thread.sleep(200);
                    theResult.clear();
                    System.out.println("Ready to send the key !!! ");
                    theResult.sendKeys(extPara);
                    Thread.sleep(200);
                    theResult.sendKeys("66");
                    String tempText = theResult.getText();
                    if ((tempText != "") && (tempText != null)) {
                        System.out.println("tempText : " + tempText);
                        if (tempText.equalsIgnoreCase(extPara) || tempText.toLowerCase().contains(extPara.toLowerCase())) {
                            result = new String(tempText);
                        } else {
                            throw new REXException("searchStartMAC : the real input para is different from excepted ");
                        }
                    } else if (tempText.equalsIgnoreCase(dValue) || tempText.equals("")) {
                        result = new String(tempText);
                    } else {
                        result = new String("failedToGetText");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . searchStart, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }


    public String autoCompleteStartWINDOWS(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, final String p4L, String
                                                   dValue, String extPara) throws Exception {
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        System.out.println(" ~~~~~~ IN bANDPageTemplate :autoCompleteStartWINDOWS ~~~~~~");

        String result = null;
        WebElement theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: autoCompleteStartWINDOWS in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
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
                    Thread.sleep(200);
                    theResult.clear();
                    System.out.println("Ready to send the key !!! ");
                    theResult.sendKeys(extPara);
                    Thread.sleep(200);
                    //    theResult.sendKeys("66");
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . autoCompleteStartWINDOWS, Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }


    public String selectItem(WebDriver driver, String
            defaultText, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity > eleMap, final String p4L, String
                                     extPara) throws Exception {  //TODO
        String result = null;
        List<WebElement> theResultList = new ArrayList<WebElement>();
        WebElement choosedItem = null;
        Thread.sleep(2500);
        final String localPath = p4L;
        String contentInItem = null;
        WebElement item4Index = null;
        int index4Line = 0;
        boolean flag4retry = false;
        String str4error = "";
        System.out.println(" this time we want to choose the " + extPara + " item ");
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: selectItem in " + this.getClass().getSimpleName() + " has been called ~~~ +++"); // find all items
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().contains("itemByID")) {
                    System.out.println("itemByID" + " :?:" + tempContentEntry.getKey().toString());
                    String locaterType = checkTheLocaterType(tempContentEntry.getKey().toString(), eleMap);
                    final String locaterStr = checkLocaterStr(tempContentEntry.getKey().toString(), eleMap);
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, p4L);
                    if (locaterType.toLowerCase().equals("id") && !locaterStr.contains(":REX")) {
                        theResultList = xWait.until(new FOXExpectedCondition<List>() {
                            public List<WebElement> apply(WebDriver driver) {
                                List<WebElement> temp = null;
                                try {
                                    temp = findEleListWithSearch(driver, By.id(locaterStr), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                                } catch (Exception e) {
                                    throw new TimeoutException(e.getMessage());
                                }
                                return temp;
                            }
                        });
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
            try {
                result = choosedItem.getText();     //  // non - isolated content and clickable btn
                choosedItem.click();
            } catch (Exception e)         // isolated content and clickable btn
            {
                try {
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
                            FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, p4L);
                            if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                                item4Index = xWait.until(new FOXExpectedCondition<WebElement>() {
                                    public WebElement apply(WebDriver driver) {
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
                                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, p4L);
                                    if (locaterType.toLowerCase().equals("xpath") && locaterStr.contains(":REXitem:")) {
                                        theLine = xWait.until(new FOXExpectedCondition<WebElement>() {
                                            public WebElement apply(WebDriver driver) {
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
                    }
                } catch (Exception ein) {
                    flag4retry = true;
                    str4error = new String(ein.getMessage() + " : " + ein.getCause());
                }
            }
            if (flag4retry == true) {
                throw new REXException(str4error);
            } else {
                if (result == null || result == "") {
                    result = contentInItem;
                }
                return result;
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . selectItem, Other Error appear : " + e.getCause());
            e.printStackTrace();
            throw e;
        }
    }


    public String checkCalendarDate(WebDriver driver, String eleName, HashMap<String, By> inMap, String
            p4L) throws Exception {
        String result = null;
        WebElement theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: checkCalendarDate in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . checkCalendarDate , Other Error appear : " + e.getCause());
            throw e;
        }
        return result;
    }

    public String btnOperation(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L) throws Exception {
        String result = null;
        WebElement theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: btnOperation 4 Route in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
               //     result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                //    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                    Thread.sleep(2000);
                    theResult.click();
             //       if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        //ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
              //      }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . btnOperation 4 Route 2, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public boolean matchScreen(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L) throws Exception {
        boolean result = false;
        WebElement theResult = null;

        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: matchScreen in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            WebElement temp = null;
                            try {
                                temp = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                    int currentY = theResult.getLocation().getY();
                    if (currentY <= 600) {
                    } else {
                        //moveUPwithSyncTime(driver, 1000);
                    }
                    result = true;

                }
            }
        } catch (Exception e) {
            result = false;
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . matchScreen, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return result;
        }
    }


    public String btnOperationListMatch(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity > eleMap, String p4L) throws Exception {
        String result = null;
        List<WebElement> theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: btnOperationListMatch in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<List>() {
                        public List<WebElement> apply(WebDriver driver) {
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
                        throw new REXException("Failed to find the element in bANDPageTemplate btnOperationListMatch, Line 2067 ");
                    } else if (theResult.size() == 1) {
                  //      result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                        System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                        Thread.sleep(2000);
                        theResult.get(0).click();
                   //     if (!result.equals("noRoute") && (result != "") && (result != null)) {
                            Date date = new Date();
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time = format.format(date);
                            //ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                  //      }
                    } else {
                  //      result = checkTheNextPage(tempContentEntry.getKey().toString(), eleMap);
                    //    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                  //      Thread.sleep(2000);
                        theResult.get(0).click();
                //        if (!result.equals("noRoute") && (result != "") && (result != null)) {
                            Date date = new Date();
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time = format.format(date);
                            //ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
                  //      }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . btnOperationListMatch, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String btnOperationAutoIndex(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L) throws Exception {   //TODO
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
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: btnOperationAutoIndex in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (int ind = 1; (ind <= steprange) && (singleEle == null); ind++) {
                System.out.println("step ~~~~~~~~~~~~~~~~~~" + ind);
                stepINString = new String(String.valueOf(ind));
                locatorStr = new String(locatorStr.replaceFirst(":REXindex:", stepINString));
                final By afterReplace = By.xpath(localStr);
                FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                try {
                    singleEle = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
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
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . btnOperationAutoIndex, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }

    public String getElementContent(WebDriver driver, String
            locatorStr, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L,
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
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: getElementContent in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (int ind = 0; (ind <= steprange) && (theResult == null); ind++) {
                System.out.println("step ~~~~~~~~~~~~~~~~~~" + ind);
                stepINString = new String(String.valueOf(TITLEindex + ind));
                locatorStr = new String(locatorStr.replaceFirst(":REXindex:", stepINString));
                localStr = new String(locatorStr.replaceFirst(":REXrecord:", String.valueOf(index4item)));
                final By afterReplace = By.xpath(localStr);
                FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                try {
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . getElementContent, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "";
        } else {
            return result;
        }
    }


    public String getStaticElementContent(WebDriver driver, String
            locatorStr, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L,
                                          int TITLEindex) throws Exception {
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
                    theResult = (WebElement) driver.findElement(singleRecord);
                } catch (Exception e) {
                }
            }
            Thread.sleep(1000);
            result = theResult.getText();
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . getStaticElementContent, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "";
        } else {
            return result;
        }
    }


    public String getSingleElementContentWithBYid(WebDriver driver, final String byIDvalue, int moveStep, int recordNum, String targetSample, String p4L) throws Exception {
        String result = null;
        final String localPath = p4L;
        int local_moveStep = moveStep;
        int local_recordNum = recordNum;
        ArrayList<WebElement> theResult = new ArrayList<WebElement>();
        ArrayList<String> strList = new ArrayList<String>();
        strList.clear();
        try {
            System.out.println("    +++ ~~~ In the getSingleElementContentWithBYid :: getElementContent in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (; local_moveStep != 0 && strList.size() < local_recordNum; local_moveStep--) {
                FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                try {
                    theResult.clear();
                    theResult = xWait.until(new FOXExpectedCondition<ArrayList<WebElement>>() {
                        public ArrayList<WebElement> apply(WebDriver driver) {
                            ArrayList<WebElement> temp = null;
                            try {
                                temp = new ArrayList<WebElement>(driver.findElements(By.id(byIDvalue)));
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                } catch (Exception e) {
                    System.out.println(logSpace4thisPage + "In bANDPageTemplate . getSingleElementContentWithBYid, Failed to find the elements based on ID : " + e.getCause());
                    throw new XMLException("Element att in XML for ID");
                }
                if (theResult.size() != 0) {
                    for (WebElement x4list : theResult) {
                        strList.add(x4list.getText());
                    }
                } else {

                }
                if (targetSample != null || !targetSample.equals("")) {
                    for (String current : strList) {
                        if (current.equalsIgnoreCase(targetSample)) {
                            result = new String(current);
                            break;
                        }
                    }
                } else {
                    result = new String(strList.get(0));
                }
                //moveUPwithSyncTime(driver, 1000);
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . getSingleElementContentWithBYid, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "";
        } else {
            return result;
        }
    }


    public String getSingleElementContentWithBYxpath(WebDriver driver, final String byXPATHvalue, int moveStep, int recordNum, String targetSample, String p4L) throws Exception {
        String result = null;
        final String localPath = p4L;
        int local_moveStep = moveStep;
        int local_recordNum = recordNum;
        WebElement tempEle = null;
        ArrayList<WebElement> theResult = new ArrayList<WebElement>();
        ArrayList<String> strList = new ArrayList<String>();
        strList.clear();
        try {
            System.out.println("    +++ ~~~ In the getSingleElementContentWithBYxpath :: getElementContent in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (; local_moveStep != 0 && strList.size() < local_recordNum; local_moveStep--) {
                FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                try {
                    theResult.clear();
                    for (int index = 1; index < 6; index++) {
                        tempEle = null;
                        String newStr = byXPATHvalue.replaceFirst(":REX:", String.valueOf(index));
                        final By newByValue = By.xpath(newStr);
                        try {
                            tempEle = xWait.until(new FOXExpectedCondition<WebElement>() {
                                public WebElement apply(WebDriver driver) {
                                    WebElement temp = null;
                                    try {
                                        temp = driver.findElement(newByValue);
                                    } catch (Exception e) {
                                        throw new TimeoutException(e.getMessage());
                                    }
                                    return temp;
                                }
                            });
                        } catch (Exception e) {
                            break;
                        }
                        if (tempEle != null) {
                            theResult.add(tempEle);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(logSpace4thisPage + "In bANDPageTemplate . getSingleElementContentWithBYxpath, Failed to find the elements based on ID : " + e.getCause());
                    throw new XMLException("Element att in XML for ID");
                }
                if (theResult.size() != 0) {
                    for (WebElement x4list : theResult) {
                        strList.add(x4list.getText());
                    }
                } else {

                }
                if (targetSample != null || !targetSample.equals("")) {
                    for (String current : strList) {
                        if (current.equalsIgnoreCase(targetSample)) {
                            result = new String(current);
                            break;
                        }
                    }
                } else {
                    result = new String(strList.get(0));
                }
                //moveUPwithSyncTime(driver, 1000);
            }
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . getSingleElementContentWithBYxpath, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "";
        } else {
            return result;
        }
    }

    public ArrayList<String> getListElementContentWithBYid(WebDriver driver, final String byIDvalue, int moveStep, int recordNum, String targetSample, String p4L) throws Exception {
        final String localPath = p4L;
        int local_moveStep = moveStep;
        int local_recordNum = recordNum;
        ArrayList<WebElement> theResult = new ArrayList<WebElement>();
        ArrayList<String> strList = new ArrayList<String>();
        strList.clear();
        try {
            System.out.println("    +++ ~~~ In the getListElementContentWithBYid :: getElementContent in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (; local_moveStep != 0 && strList.size() < local_recordNum; local_moveStep--) {
                FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                try {
                    theResult.clear();
                    theResult = xWait.until(new FOXExpectedCondition<ArrayList<WebElement>>() {
                        public ArrayList<WebElement> apply(WebDriver driver) {
                            ArrayList<WebElement> temp = null;
                            try {
                                temp = new ArrayList<WebElement>(driver.findElements(By.id(byIDvalue)));
                            } catch (Exception e) {
                                throw new TimeoutException(e.getMessage());
                            }
                            return temp;
                        }
                    });
                } catch (Exception e) {
                    System.out.println(logSpace4thisPage + "In bANDPageTemplate . getListElementContentWithBYid, Failed to find the elements based on ID : " + e.getCause());
                    throw new XMLException("Element att in XML for ID");
                }
                if (theResult.size() != 0) {
                    for (WebElement x4list : theResult) {
                        strList.add(x4list.getText());
                    }
                } else {

                }
                if (targetSample != null || !targetSample.equals("")) {
                    for (String current : strList) {
                        if (current.equalsIgnoreCase(targetSample)) {
                            strList.clear();
                            strList.add(current);
                            break;
                        }
                    }
                } else {
                }
                //moveUPwithSyncTime(driver, 1000);
            }
            strList =  removeNeighbourDuplicate(strList);
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . getListElementContentWithBYid, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return strList;
        }
    }

    public ArrayList<String> getListElementContentWithBYxpath(WebDriver driver, final String byXPATHvalue, int moveStep, int recordNum, String targetSample, String p4L) throws Exception {
        String result = null;
        final String localPath = p4L;
        int local_moveStep = moveStep;
        int local_recordNum = recordNum;
        WebElement tempEle = null;
        ArrayList<WebElement> theResult = new ArrayList<WebElement>();
        ArrayList<String> strList = new ArrayList<String>();
        strList.clear();
        try {
            System.out.println("    +++ ~~~ In the getListElementContentWithBYxpath :: getElementContent in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (; local_moveStep != 0 && strList.size() < local_recordNum; local_moveStep--) {
                FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                try {
                    theResult.clear();
                    for (int index = 1; index < 6; index++) {
                        tempEle = null;
                        String newStr = byXPATHvalue.replaceFirst(":REX:", String.valueOf(index));
                        final By newByValue = By.xpath(newStr);
                        try {
                            tempEle = xWait.until(new FOXExpectedCondition<WebElement>() {
                                public WebElement apply(WebDriver driver) {
                                    WebElement temp = null;
                                    try {
                                        temp = driver.findElement(newByValue);
                                    } catch (Exception e) {
                                        throw new TimeoutException(e.getMessage());
                                    }
                                    return temp;
                                }
                            });
                        } catch (Exception e) {
                            break;
                        }
                        if (tempEle != null) {
                            theResult.add(tempEle);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(logSpace4thisPage + "In bANDPageTemplate . getListElementContentWithBYxpath, Failed to find the elements based on ID : " + e.getCause());
                    throw new XMLException("Element att in XML for ID");
                }
                if (theResult.size() != 0) {
                    for (WebElement x4list : theResult) {
                        strList.add(x4list.getText());
                    }
                } else {

                }
                if (targetSample != null || !targetSample.equals("")) {
                    for (String current : strList) {
                        if (current.equalsIgnoreCase(targetSample)) {
                            strList.clear();
                            strList.add(current);
                            break;
                        }
                    }
                } else {
                }
                //moveUPwithSyncTime(driver, 1000);
            }
            strList =  removeNeighbourDuplicate(strList);
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . getListElementContentWithBYxpath, Other Error appear : " + e.getCause());
            throw e;
        } finally {
            return strList;
        }
    }

    public String autoSearch(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L) throws Exception {
        String result = null;
        WebElement theResult = null;
        Thread.sleep(1000);
        final String localPath = p4L;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: autoSearch 4 Route in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . autoSearch , Other Error appear : " + e.getCause());
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


    private WebElement filterBtnElement(WebDriver driver, FOXDriverWait xWait, By theByKey, String
            eleName, HashMap<StringBuilder, elementEntity> eleMap) throws Exception {
        boolean review = false;
        int moveAccount = 10;
        WebElement result = null;
        List<WebElement> temp = new ArrayList<WebElement>();
        List<WebElement> temp4his = new ArrayList<WebElement>();
        List<String> tempString = new ArrayList<String>();
        List<String> temp4hisString = new ArrayList<String>();
        String targetXPATH_start = "";
        String targetXPATH_end = "";
        String rowBtnXPath = "";
        int num4xpath = 0;
        String tEleTextContent = checkTheTextContent(eleName, eleMap);
        try {
            for (; (review != true) && (moveAccount > 0); ) {       // 只要没有到页尾 或者 计数器满就继续找
                temp = REXDriverWait.FOXListFinder(driver, xWait, theByKey); // get all element in current screen with a common id;only ID are supported, so don't send the type para
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
                                    String tempXpath = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();  // xpath内容找到
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
                                        String tempXpath = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();  // xpath内容找到
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
                                        String tempXpath = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();  // xpath内容找到
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
                                        String tempXpath = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();  // xpath内容找到
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
                    throw new REXException("in bANDPageTemplate :: filterBtnElement, fork else");
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
                    //moveUPtest(driver);
                }
                moveAccount--;
                Thread.sleep(2000);
            }
            if (targetXPATH_start != "") {
                for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {  // find the xpath of the element in map
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName + "Btn")) {   // 注意格式
                        String tempXpath = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();
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
                            result = (WebElement) tEle;
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
                throw new XMLException("in bANDPageTemplate :: filterBtnElement, fork else, the XML file is not correct");
            }
        } catch (Exception e) {
            result = null;
            throw e;
        } finally {
            return result;
        }

    }

    private int getIndex4BtnElement(WebDriver driver, FOXDriverWait xWait, By theByKey, String
            eleName, HashMap<StringBuilder, elementEntity > eleMap) throws Exception {
        boolean review = false;
        int moveAccount = 10;
        WebElement result = null;
        List<WebElement> temp = new ArrayList<WebElement>();
        List<WebElement> temp4his = new ArrayList<WebElement>();
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
                temp = REXDriverWait.FOXListFinder(driver, xWait, theByKey); // get all element in current screen with a common id;only ID are supported, so don't send the type para
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
                           // moveUP300(driver);
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
                                   // moveUP300(driver);
                                    review = true;
                                }
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
                                   // moveUP300(driver);
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
                                   // moveUP300(driver);
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
                    throw new REXException("in bANDPageTemplate :: filterBtnElement, fork else");
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
                    }
                    moveAccount--;
                    Thread.sleep(2000);
                } else {
                    review = true;
                }
            }

            temp.clear();
            temp = REXDriverWait.FOXListFinder(driver, xWait, theByKey); // get all element in current screen with a common id;only ID are supported, so don't send the type para
            for (int i = 0; i < temp.size(); i++) {
                tempString.add(temp.get(i).getText());
            }
            if (temp.size() == 0) {
                throw new REXException("element lost after match the screen");
            } else if (temp.size() == 1) {
                sampleText4first = new String(temp.get(0).getText());
                if (sampleText4first.equals(tEleTextContent)) {
                    for (Iterator it = eleMap.entrySet().iterator(); it.hasNext() && (num4xpath == 0); ) {  // find the xpath of the element in map  去找这个元素在xml内的
                        final Map.Entry tempContentEntry = (Map.Entry) it.next();
                        if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                            String tempXpath = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();  // xpath内容找到
                            if (tempXpath.contains(":REXindex:")) {
                                for (int in = 1; in <= 15; in++) {
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
                                String tempXpath = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();  // xpath内容找到
                                if (tempXpath.contains(":REXindex:")) {
                                    for (int in = 1; in <= 15; in++) { //TODO
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


    private WebElement filterRecordElement(WebDriver driver, FOXDriverWait xWait, By theByKey, String
            eleName, HashMap<StringBuilder, elementEntity > eleMap) throws Exception {
        boolean review = false;
        int moveAccount = 10;
        WebElement result = null;
        List<WebElement> temp = new ArrayList<WebElement>();
        List<WebElement> temp4his = new ArrayList<WebElement>();
        List<String> tempString = new ArrayList<String>();
        List<String> temp4hisString = new ArrayList<String>();
        String targetXPATH_start = "";
        String targetXPATH_end = "";
        String rowBtnXPath = "";
        int num4xpath = 0;
        String tEleTextContent = checkTheTextContent(eleName, eleMap);
        try {
            for (; (review != true) && (moveAccount > 0); ) {       // 只要没有到页尾 或者 计数器满就继续找
                temp = REXDriverWait.FOXListFinder(driver, xWait, theByKey); // get all element in current screen with a common id;only ID are supported, so don't send the type para
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
                                    String tempXpath = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();  // xpath内容找到
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
                                        String tempXpath = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();  // xpath内容找到
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
                                        String tempXpath = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();  // xpath内容找到
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
                                        String tempXpath = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();  // xpath内容找到
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
                    throw new REXException("in bANDPageTemplate :: filterBtnElement, fork else");
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
                    //moveUPtest(driver);
                }
                moveAccount--;
                Thread.sleep(2000);
            }
            if (targetXPATH_start != "") {
                for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {  // find the xpath of the element in map
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName + "Btn")) {   // 注意格式
                        String tempXpath = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();
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
                            result = (WebElement) tEle;
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
                throw new XMLException("in bANDPageTemplate :: filterBtnElement, fork else, the XML file is not correct");
            }
        } catch (Exception e) {
            result = null;
            throw e;
        } finally {
            return result;
        }

    }


    public String btnOperationSearch(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder,elementEntity > eleMap, String p4L,
                                     int index4xpath) throws Exception {
        String result = null;
        WebElement theResult = null;
        Thread.sleep(500);
        final String localPath = p4L;
        try {
            By theComByKey = null;
            final FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
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
                theResult = filterBtnElement(driver, xWait, theComByKey, eleName, eleMap);
                if (index4xpath > 0) {
               //     result = checkTheNextPage(eleName, eleMap);
                    theResult.click();
              //      if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        //ScreenShot(driver, "During_Switch_" + time + ".png", localPath);
            //        }
                } else {
                    throw new REXException("Failed to find the index 4 modify xpath");
                }
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . btnOperationSearch, Other Error appear : " + e.getCause());
            throw e;
        }
        if (result == null || result.equals("")) {
            return "noRoute" + "::" + index4xpath;
        } else {
            return (result + "::" + index4xpath);
        }
    }


    public int getIndex4TitleIndex(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity > eleMap, String p4L,
                                   int index4xpath) throws REXException, InterruptedException {
        Thread.sleep(500);
        final String localPath = p4L;
        try {
            By theComByKey = null;
            final FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . getIndex4TitleIndex, Other Error appear : " + e.getCause());
            e.printStackTrace();
        } finally {
            return index4xpath;
        }

    }


    public String recordSearch(WebDriver driver, String
            eleName, HashMap<StringBuilder, elementEntity> eleMap, String p4L, int index4path, String type, String
                                       rowLocatorString, String recordType, String recordLocatorStr) throws Exception {
        String result = null;
        WebElement theResult = null;
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
        /*
        if (recordType.equals("xpath")) {
            theComByKey = By.xpath(recordLocatorStr);  // for list
        } else if (recordType.equals("id")) {
            theComByKey = By.id(recordLocatorStr);   // for list
        }
        */
        final FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
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
                    theResult = (WebElement) driver.findElement(singleRecord);
                } catch (Exception e) {
                    //TODO ?
                    flag4end++;
                }
            }
            if (theResult == null) {
                //moveUpFitScreen(driver);
                move++;
            }
        }
        if (theResult != null) {
      //      result = checkTheNextPage(eleName, eleMap);
        //    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
       //     Thread.sleep(2000);
            theResult.click();
       //     if (!result.equals("noRoute") && (result != "") && (result != null)) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                //ScreenShot(driver, "Record_During_Switch_" + time + ".png", p4L);
        //    }
        } else {
            throw new REXException("failed to find the element with index step ");
        }
        if (result == null || result.equals("")) {
            return "noRoute";
        } else {
            return result;
        }
    }


    public String recordSearch(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L,
                               int index4path, String type, String rowLocatorString, String recordType, String
                                       recordLocatorStr, HashMap<String, Integer> waitStepMap, String key) throws Exception {
        String result = null;
        WebElement theResult = null;
        List<WebElement> theResultList = new ArrayList<WebElement>();
        final String localPath = p4L;
        By theComByKey = null;
        int TITLEindex = 0;
        String RECORDindex = "1";
        String rowContent = rowLocatorString;
        String stepINString = null;
        int steprange = 3;
        int flag4end = 0;
        int flag4groupend = 0;
        /*
        if (recordType.equals("xpath")) {
            theComByKey = By.xpath(recordLocatorStr);  // for list
        } else if (recordType.equals("id")) {
            theComByKey = By.id(recordLocatorStr);   // for list
        }
        */
        final FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
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
                        theResult = (WebElement) driver.findElement(singleRecord);
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
               //     result = checkTheNextPage(eleName, eleMap);
                //    System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                    theEle.click();
                 //   if (!result.equals("noRoute") && (result != "") && (result != null)) {
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        //ScreenShot(driver, "Record_During_Switch_" + time + ".png", p4L);
               //     }
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

    public String btnMatchReturnValue(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L,
                                      int index4path, String type, String rowLocatorString, String recordType, String
                                              recordLocatorStr, HashMap<String, Integer> waitStepMap, String key) throws Exception {
        String result = null;
        WebElement theResult = null;
        WebElement theRecord = null;
        List<WebElement> theMapValueList = new ArrayList<WebElement>();
        List<WebElement> theRecordList = new ArrayList<WebElement>();
        WebElement titleElement = null;
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
     //   String resultpart2 = "";
        int indexDig = 0;
        int recordDig = 0;
        // current element name is xxx_mapvalue
        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().toLowerCase().contains((eleName).toLowerCase())) { // for map value element
                locatorString = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();
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
                        theResult = (WebElement) driver.findElement(singleRecord);
                        if (theResult.getText().toLowerCase().contains(key.toLowerCase())) {
                       //     resultpart2 = checkTheNextPage(eleName.replace("_mapvalue", "_record"), eleMap);
                       //     System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + resultpart2);
                   //         if (!resultpart2.equals("noRoute") && (resultpart2 != "") && (resultpart2 != null)) {
                                Date date = new Date();
                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String time = format.format(date);
                                //ScreenShot(driver, "Record_During_Switch_" + time + ".png", localPath);
                    //        }
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
                theRecord = (WebElement) driver.findElement(singleRecord);
                resultpart1 = theRecord.getText();
                System.out.println(resultpart1);
                result = new String(resultpart1); //  + "::" + resultpart2);
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

    public String btnMatchSearchByValue(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L,
                                        int index4path, String type, String rowLocatorString, String recordType, String
                                                recordLocatorStr, HashMap<String, Integer> waitStepMap, String key) throws Exception {
        String result = null;
        WebElement theResult = null;
        WebElement theRecord = null;
        List<WebElement> theMapValueList = new ArrayList<WebElement>();
        List<WebElement> theRecordList = new ArrayList<WebElement>();
        List<WebElement> ComtheRecordList = new ArrayList<WebElement>();
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
     //   String resultpart2 = "";

        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().toLowerCase().contains((eleName).toLowerCase())) { // for map value element
                locatorString = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();
                break;
            }
        }
        for (; (theMapValueList.size() != theRecordList.size()) || (theMapValueList.size() == 0 && theRecordList.size() == 0); ) {
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
                            theResult = (WebElement) driver.findElement(singleRecord);
                            System.out.println("theResult in valuemap : " + theResult.getText());
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
            if (recordType.equalsIgnoreCase("id")) {
                ComtheRecordList = findEleListWithSearch(driver, By.id(recordLocatorStr), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                for (WebElement tx : ComtheRecordList) {
                    System.out.println("com list for id : " + tx.getText());
                }
            } else {
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
                                theRecord = (WebElement) driver.findElement(singleRecord);
                                System.out.println("theRecord in recordlist " + theResult.getText());
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
            }
            if (theMapValueList.size() != theRecordList.size() && theMapValueList.size() != ComtheRecordList.size()) {
                theMapValueList.clear();
                theRecordList.clear();
                ComtheRecordList.clear();
                flag4end = 0;
                flag4groupend = 0;

            } else {
                break;
            }

        }
        // just 4 get the complete table view and get the table length
        if ((ComtheRecordList.size() != 0 || theRecordList.size() != 0) && theMapValueList.size() != 0) {
            int range = theRecordList.size();
            String recordName = eleName.split("_")[0] + "_record";  // get the record Locator String from xml
            String recordTypeCheck = null;
            String recordRowStr = null;
            String backrecordRowStr = null;
            for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().toLowerCase().contains((recordName).toLowerCase())) { // for record element
                    recordTypeCheck = ((elementEntity) tempContentEntry.getValue()).getType().toString();
                    recordRowStr = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();
                    backrecordRowStr = new String(recordRowStr);
                    break;
                }
            }
            if (recordTypeCheck.equalsIgnoreCase("xpath")) {
                if (recordRowStr.contains(":REXindex:") && recordRowStr.contains(":REXrecord:")) {  // record str is REX format
                    String finalStr = null;
                    WebElement singleRecordEle = null;
                    for (int indee = 0; indee < range; indee++) {
                        TITLEindex = index4path;  //getIndex4BtnElement(driver, xWait, theComByKey, tempEleName, eleMap);
                        if (recordRowStr.contains(":REXindex:") && recordRowStr.contains(":REXrecord:")) {
                            for (int ind = 0; (ind <= steprange) && (theRecord == null) && (flag4end < 3); ind++) {  // try yo d
                                System.out.println("step ~~~~~~~~~~~~~~~~~~" + ind);
                                stepINString = new String(String.valueOf(TITLEindex + ind));
                                String newContent1 = new String(recordRowStr.replaceFirst(":REXindex:", stepINString));  // for single
                                flag4groupend = 0;
                                for (int index = 1; (index <= 31) && (flag4groupend < 3); index++) {
                                    String newContent2 = new String(newContent1.replaceFirst(":REXrecord:", String.valueOf(index)));  // for record
                                    final By singleRecord = By.xpath(newContent2);
                                    System.out.println(newContent2);
                                    try {
                                        theRecord = (WebElement) driver.findElement(singleRecord);  // loop the record again, get the element with don't have the 0.0 hour
                                        System.out.println("theRecord in 2nd loop : " + theRecord.getText());
                                        //   if(theRecord.getText().equalsIgnoreCase(key)) {
                                        if (theRecord != null) {
                                            System.out.println(recordRowStr.indexOf(":REXindex:"));
                                            int startRex1 = recordRowStr.indexOf(":REXindex:");
                                            System.out.println(newContent2.indexOf("]", startRex1 - 1));
                                            int endRex1 = newContent2.indexOf("]", startRex1 - 1);
                                            String dig4REXindex = null;
                                            if (startRex1 != -1 && endRex1 != -1) {
                                                if (startRex1 <= endRex1) {
                                                    dig4REXindex = newContent2.substring(startRex1, endRex1);
                                                    System.out.println(dig4REXindex);
                                                }
                                                recordRowStr = new String(recordRowStr.replaceFirst(":REXindex:", dig4REXindex));
                                                System.out.println(recordRowStr.indexOf(":REXrecord:"));
                                                int startRex2 = recordRowStr.indexOf(":REXrecord:");
                                                System.out.println(newContent2.indexOf("]", startRex2 - 1));
                                                int endRex2 = newContent2.indexOf("]", startRex2 - 1);
                                                String dig4REXrecord = null;
                                                if (startRex2 != -1 && endRex2 != -1 && startRex2 <= endRex2) {
                                                    dig4REXrecord = newContent2.substring(startRex2, endRex2);
                                                    System.out.println(dig4REXrecord);
                                                }
                                                finalStr = new String(recordRowStr.replaceFirst(":REXrecord:", dig4REXrecord));  // replace the index and record with same dig from mapvalue 0.0h element
                                                System.out.println(finalStr);
                                            }

                                        }
                                        singleRecordEle = (WebElement) driver.findElement(singleRecord);  //try to get the element for record and get the value compare with the key from caller
                                        System.out.println("singleRecordEle : " + singleRecordEle.getText());
                                        if (singleRecordEle.getText().equalsIgnoreCase(key)) {

                                            resultpart1 = theMapValueList.get(indee).getText();
                                          //  resultpart2 = checkTheNextPage(recordName, eleMap);
                                            System.out.println("    +++ ~~~ Check the route result : ~~~ +++" + result);
                                            singleRecordEle.click();
                                     //       if (!resultpart2.equals("noRoute") && (resultpart2 != "") && (resultpart2 != null)) {
                                                Date date = new Date();
                                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                String time = format.format(date);
                                                //ScreenShot(driver, "Record_During_Switch_" + time + ".png", p4L);
                                       //     }
                                            result = new String(resultpart1);// + "::" + resultpart2);
                                            //   flag4quit = true;
                                        }

                                        theRecord = null;
                                        singleRecordEle = null;

                                    } catch (Exception e) {
                                        flag4groupend++;
                                    }
                                }
                                if (theRecordList.size() == 0) {
                                    flag4end++;
                                }
                                recordRowStr = new String(backrecordRowStr);
                            }
                            recordRowStr = new String(backrecordRowStr);
                        } else {
                            throw new XMLException("map value element locator string in XML file are incorrect! ");
                        }
                    }
                } else {
                    throw new XMLException("record element locator string in XML file are incorrect! ");
                }
            }
        } else {
            throw new REXException("failed to find the element with inde step , at least the mapvalue &// record list is empty ! ");
        }
        if (result == null || result.equals("::")) {
            return "::noRoute";
        } else {
            return result;
        }
    }

    public boolean tableMatch(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L,
                              int index4path, HashMap<String, Integer> waitStepMap, String key, By titleStr, By recordStr) throws Exception {
        WebElement theResult = null;
        WebElement theRecord = null;
        List<WebElement> tempList = new ArrayList<WebElement>();
        List<WebElement> theTitleList = new ArrayList<WebElement>();
        List<WebElement> theRecordList = new ArrayList<WebElement>();
        final String localPath = p4L;
        int flag4loop = 0;
        int moveGap = 0;
        boolean findMatch = false;
        for (; flag4loop < 3 && findMatch == false; ) {
            System.out.println("In the tableMatch of AND Page ! ");
            moveGap = 0;
            tempList = driver.findElements(titleStr);
            for (WebElement tEle : tempList) {
                theTitleList.add((WebElement) tEle);
                System.out.println("title list :" + tEle.getText());
                if (tEle.getText().toLowerCase().contains(key.toLowerCase())) {
                    findMatch = true;
                }
            }
            tempList.clear();
            tempList = driver.findElements(recordStr);
            for (WebElement tEle : tempList) {
                theRecordList.add((WebElement) tEle);
                System.out.println("record list :" + tEle.getText());
                if (tEle.getText().toLowerCase().contains(key.toLowerCase())) {
                    findMatch = true;
                }
            }

            if (theTitleList.size() != theRecordList.size()) {
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
                theRecordList.clear();
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

    public String contentMatchSearchByValue(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L,
                                            int index4path, String type, String rowLocatorString, String recordType, String
                                                    recordLocatorStr, HashMap<String, Integer> waitStepMap, String key) throws Exception {
        String result = null;
        WebElement theResult = null;
        WebElement theRecord = null;
        List<WebElement> theMapValueList = new ArrayList<WebElement>();
        List<WebElement> theRecordList = new ArrayList<WebElement>();
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
                locatorString = ((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString();
                break;
            }
        }
        for (; (theMapValueList.size() != theRecordList.size()) || (theMapValueList.size() == 0 && theRecordList.size() == 0); ) {
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
                            theResult = (WebElement) driver.findElement(singleRecord);
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
                            theRecord = (WebElement) driver.findElement(singleRecord);
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


    public String rollTheRound(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, int stepNum, int roundRange,
                               boolean direction) throws Exception {
        boolean dir = direction;
        WebElement theResult = null;
        String result = null;
        int timeDuration = Integer.parseInt(readProperity.getProValue("screenMoveTimeDuration"));
        final String localPath = this.path4Log;
        Point theLocation = null;
        Dimension theSize = null;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: rollTheRound ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult =
                            xWait.until(
                                    new FOXExpectedCondition<WebElement>() {
                                        public WebElement apply(WebDriver driver) {
                                            return driver.findElement((By) tempContentEntry.getValue());
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
            if (dir == true) {
                if (roundRange % 2 == 1) {
                    startY = theLocation.getY() + (theSize.getHeight() * (roundRange + 1 / 2) / (roundRange + 1));
                    endY = theLocation.getY() + (theSize.getHeight() * ((roundRange + 1 / 2) - 1) / (roundRange + 1));
                } else {
                    startY = theLocation.getY() + (theSize.getHeight() * (roundRange / 2) / (roundRange + 1));
                    endY = theLocation.getY() + (theSize.getHeight() * ((roundRange / 2) - 1) / (roundRange + 1));
                }
            } else {
                if (roundRange % 2 == 1) {
                    endY = theLocation.getY() + (theSize.getHeight() * (roundRange + 1 / 2) / (roundRange + 1));
                    startY = theLocation.getY() + (theSize.getHeight() * ((roundRange + 1 / 2) - 1) / (roundRange + 1));
                } else {
                    endY = theLocation.getY() + (theSize.getHeight() * (roundRange / 2) / (roundRange + 1));
                    startY = theLocation.getY() + (theSize.getHeight() * ((roundRange / 2) - 1) / (roundRange + 1));
                }
            }
            for (int ind = 0; ind < stepNum; ind++) {
                //moveUPwithPara(driver, startX, startY, endX, endY);
            }
            result = "";
        } catch (REXException e) {
            result = "error";
            throw e;
        } catch (TimeoutException e) {
            result = "error";
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  bANDPageTemplate :: rollTheRound , Other Error appear : " + e.getCause());
            result = "error";
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }

    public String getElementContent(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap) throws
            Exception {
        WebElement theResult = null;
        String result = null;
        final String localPath = this.path4Log;
        Thread.sleep(1000);
        String eleType = null;
        String eleStr = null;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: getElementContent ~~~ +++");
            elementEntity tempEle = eleMap.get(eleName);
            if (tempEle != null) {
                eleType = tempEle.getType().toString();
                eleStr = tempEle.getLocatorStr().toString();
            } else {
                for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        eleName = new String(tempContentEntry.getKey().toString());
                        eleType = tempEle.getType().toString();
                        eleStr = tempEle.getLocatorStr().toString();
                        break;
                    }
                }
            }
            loadingJudgement(driver, eleMap);
            FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
            if (eleType.equalsIgnoreCase("xpath")) {
                final By eleLocator = By.xpath(eleStr);
                theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        WebElement searResult = null;
                        try {
                            searResult = findElementWithSearch(driver, eleLocator, 5400, localPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            return searResult;
                        }
                    }
                });
            } else if (eleType.equalsIgnoreCase("id")) {
                final By eleLocator = By.id(eleStr);
                theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        WebElement searResult = null;
                        try {
                            searResult = findElementWithSearch(driver, eleLocator, 5400, localPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            return searResult;
                        }
                    }
                });
            }

            result = theResult.getText();
            if (eleName.toLowerCase().contains("phone")) {
                result = new String(result.replaceAll("-", ""));
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  bANDPageTemplate :: getElementContent , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }


    public String getElementContentByIndex(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, int index) throws
            Exception {
        WebElement theResult = null;
        String result = null;
        final String localPath = this.path4Log;
        String eleType = null;
        String eleStr = null;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: getElementContentByIndex ~~~ +++");
            elementEntity tempEle = eleMap.get(eleName);
            if (tempEle != null) {
                eleType = tempEle.getType().toString();
                eleStr = tempEle.getLocatorStr().toString();
            } else {
                for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        eleName = new String(tempContentEntry.getKey().toString());
                        eleType = tempEle.getType().toString();
                        eleStr = tempEle.getLocatorStr().toString();
                        break;
                    }
                }
            }
            loadingJudgement(driver, eleMap);
            FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
            if (eleType.equalsIgnoreCase("xpath")) {
                final By eleLocator = By.xpath(eleStr);
                theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        WebElement searResult = null;
                        try {
                            searResult = findElementWithSearch(driver, eleLocator, 5400, localPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            return searResult;
                        }
                    }
                });
            } else if (eleType.equalsIgnoreCase("id")) {
                final By eleLocator = By.id(eleStr);
                theResult = xWait.until(new FOXExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        WebElement searResult = null;
                        try {
                            searResult = findElementWithSearch(driver, eleLocator, 5400, localPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            return searResult;
                        }
                    }
                });
            }

            result = theResult.getText();
            if (eleName.toLowerCase().contains("phone")) {
                result = new String(result.replaceAll("-", ""));
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  bANDPageTemplate :: getElementContent , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }

    public String getPageContent(WebDriver driver) throws
            Exception {
        String result = "";
        Thread.sleep(1000);
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: getPageContent ~~~ +++");
            result = driver.getPageSource();
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  bANDPageTemplate :: getPageContent , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        return result;
    }


    private static StringBuilder strCompAppen(String[] lhm1, String[] lhm2) {
        StringBuilder result = new StringBuilder();
        boolean flag4quit = false;
        int endi = 0;
        int end4o = 0;
        for (int i = 0; i < lhm1.length; i++) {
            boolean flag4match = false;
            boolean flag4dismatch = false;

            for (int j = endi; j < lhm2.length; j++) {
                if (!lhm1[i].equals(lhm2[j])) {     //  不同
                    flag4dismatch = true;     //  标记不同
                    if (flag4match == false) {      //  若一直都不同,继续
                    } else {        //  若曾经相同
                        flag4quit = true;   //  标记准备删除
                        break;      //  退出
                    }
                } else {        //  相同
                    flag4match = true;      //  标记相同
                    lhm2[j] = new String("");       //  删除相同
                    endi = j;
                }
            }
            if (flag4quit == true) {
                end4o = i;
                break;
            }
        }
        if (flag4quit == true) {        //  开始删除
            for (int newi0 = 0; newi0 < end4o + 1; newi0++) {
                result.append(lhm1[newi0]);
            }
            for (int newi = endi; newi < lhm2.length; newi++) {
                if (!lhm2[newi].equals("")) {
                    result.append(lhm2[newi]);
                }
            }
        }
        return result;
    }

    public List<String> getElementContentList(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap) throws
            Exception {
        List<WebElement> theResult = null;
        List<String> result = new ArrayList<String>();
        final String localPath = this.path4Log;
        Thread.sleep(1000);
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: getElementContentList ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                    theResult =
                            xWait.until(
                                    new FOXExpectedCondition<List>() {
                                        public List<WebElement> apply(WebDriver driver) {
                                            return driver.findElements((By) tempContentEntry.getValue());
                                        }
                                    });
                    if (theResult.size() >= 1) {
                        for (int ind = 0; ind < theResult.size(); ind++) {
                            result.add(theResult.get(ind).getText());
                        }
                    } else {
                        throw new REXException("No item found in getElementContentList FOR contentcheck");
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
            System.out.println(logSpace4thisPage + "In  bANDPageTemplate :: getElementContentList , Other Error appear : " + e.getCause());
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

    public List<String> getElementContentListMove(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, String p4L) throws
            Exception {
        List<WebElement> theResult = null;
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> tempList = new ArrayList<String>();
        final String localPath = p4L;
        Thread.sleep(1000);
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate :: getElementContentListMove ~~~ +++");
            boolean flag4end = false;
            int account4empty = 0;
            int account4dup = 0;
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    for (; flag4end == false && result.size() < 4; ) {
                        FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, localPath);
                        theResult =
                                xWait.until(
                                        new FOXExpectedCondition<List>() {
                                            public List<WebElement> apply(WebDriver driver) {
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
                        //moveUPwithSyncTime(driver, 1000);
                        Date date = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(date);
                        //ScreenShot(driver, "SortContent_" + time + ".png", localPath);
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
            System.out.println(logSpace4thisPage + "In  bANDPageTemplate :: getElementContentListMove , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        if (result == null || result.size() == 0) {
            return new ArrayList<String>();
        } else {
            return result;
        }
    }


    public String getElementContentWithSearch(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap) throws
            Exception {
        WebElement theResult = null;
        String result = null;
        final String localPath = this.path4Log;
        Thread.sleep(1000);
        try {

            System.out.println("    +++ ~~~ In the bANDPageTemplate :: getElementContent ~~~ +++");
            if (eleMap.get(eleName).getLocatorStr().toString().toLowerCase().contains("text")) {
                List<String> tempList = new ArrayList<String>();
                tempList.clear();
                for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && tempList.size() == 0; ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        tempList = plusEleContentListWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);
                        break;
                    }
                }
                for (int ind = 0; ind < tempList.size(); ind++) {
                    if (ind == 0) {
                        result = new String(tempList.get(ind));
                    } else {
                        result = new String(result + "_~REX~_" + tempList.get(ind));
                    }
                }
                //  "_:REX:_"
            } else {

                for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        theResult = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);  // WebDriver driver, final By theKey, int timeValue, String path
                        result = theResult.getText();
                        if (eleName.toLowerCase().contains("phone")) {
                            result = new String(result.replaceAll("-", ""));
                        }
                        break;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  bANDPageTemplate :: getElementContent , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }

    public String getElementContent(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap, boolean flag4search) throws
            Exception {   // with search choose by ex para
        String result = null;
        final String localPath = this.path4Log;
        Thread.sleep(1000);
        try {
            if (flag4search == true) {
                System.out.println("    +++ ~~~ In the bANDPageTemplate :: getElementContent, with search ~~~ +++");
                for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        WebElement theResult = findElementWithSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);  // WebDriver driver, final By theKey, int timeValue, String path
                        result = theResult.getText();
                        if (eleName.toLowerCase().contains("phone")) {
                            result = new String(result.replaceAll("-", ""));
                        }
                        break;
                    }
                }
            } else {
                System.out.println("    +++ ~~~ In the bANDPageTemplate :: getElementContent, without search ~~~ +++");
                for (Iterator it = inMap.entrySet().iterator(); it.hasNext(); ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        WebElement theResult = findElementWithOutSearch(driver, (By) tempContentEntry.getValue(), Integer.parseInt(String.valueOf(timeOutInSeconds * 1000)), localPath);  // WebDriver driver, final By theKey, int timeValue, String path
                        result = theResult.getText();
                        if (eleName.toLowerCase().contains("phone")) {
                            result = new String(result.replaceAll("-", ""));
                        }
                        break;
                    }
                }
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In  bANDPageTemplate :: getElementContent , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            return "emptyContent";
        } else {
            return result;
        }
    }


    public String checkAbnormalElement(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap) throws REXException {
        WebElement theResult = null;
        String result = null;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The checkElement in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    FOXDriverWaitNOScreen xWait = new FOXDriverWaitNOScreen(driver, 2);
                    theResult = xWait.until(
                            new FOXExpectedCondition<WebElement>() {
                                public WebElement apply(WebDriver driver) {
                                    return driver.findElement((By) tempContentEntry.getValue());
                                }
                            });
                    break;
                }
            }
            if (theResult != null) {
                String tempDvalue = eleMap.get(eleName).getDefaultValue().toString();
                String tempTextcontnent = eleMap.get(eleName).getTextContent().toString();
                String currentValue = theResult.getText();
                if (compareTheContent(currentValue, tempDvalue) || compareTheContent(currentValue, tempTextcontnent)) {
                    result = "someerror";
                } else if (tempDvalue.equals("") && !currentValue.equals("")) {
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


    public String checkNormalElement(WebDriver driver, String
            eleName, HashMap<String, By> inMap, HashMap<StringBuilder, elementEntity> eleMap) throws REXException {
        WebElement theResult = null;
        String result = null;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The comPageCheck in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " :?:" + tempContentEntry.getKey().toString());
                    FOXDriverWaitNOScreen xWait = new FOXDriverWaitNOScreen(driver, 2);
                    theResult = xWait.until(
                            new FOXExpectedCondition<WebElement>() {
                                public WebElement apply(WebDriver driver) {
                                    return driver.findElement((By) tempContentEntry.getValue());
                                }
                            });
                    break;
                }
            }
            if (theResult != null) {
                String tempDvalue = eleMap.get(eleName).getDefaultValue().toString();
                String tempTextcontnent = eleMap.get(eleName).getTextContent().toString();
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

    public String getContentInBtn(WebDriver driver, String eleName) throws Exception {
        String theResult = null;
        try {
            for (Iterator it = this.byMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, this.path4Log);

                    theResult = xWait.until(
                            new FOXExpectedCondition<String>() {
                                public String apply(WebDriver driver) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . getContentInBtn , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        return theResult;
    }

    public boolean getSingleEle(WebDriver driver, String eleName, HashMap<String, By> inMap) throws
            REXException {
        WebElement theResult = null;
        boolean result = false;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The getSingleEle in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, this.path4Log);
                    theResult = xWait.until(
                            new FOXExpectedCondition<WebElement>() {
                                public WebElement apply(WebDriver driver) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . getSingleEle , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        return result;
    }

    public String getSingleEleText(WebDriver driver, String eleName, HashMap<String, By> inMap) throws
            REXException {
        WebElement theResult = null;
        String result = null;
        try {
            System.out.println("    +++ ~~~ In the bANDPageTemplate ~~~ +++");
            System.out.println("    +++ ~~~ The getSingleEle in " + this.getClass().getSimpleName() + " has been called ~~~ +++");
            for (Iterator it = inMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    System.out.println(eleName + " ::");
                    FOXDriverWait xWait = new FOXDriverWait(driver, timeOutInSeconds, this.path4Log);
                    theResult = xWait.until(
                            new FOXExpectedCondition<WebElement>() {
                                public WebElement apply(WebDriver driver) {
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
            System.out.println(logSpace4thisPage + "In bANDPageTemplate . getSingleEle , Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        return result;
    }


    public String comPageCheck(WebDriver theD, String type4check, String para4check) throws Exception {  // type4check : ready, loading, error, temp , etc.  , para4check: target element name, all element, title element
        String result = null;
        boolean flag = false;
        String eleText = null;
        try {
            if (type4check.equals("ready")) {       // ready mode
                if (para4check.equals("all")) {     // check all element for ready mode
                    StringBuilder[] temp = this.content.getContentNameList4type(this.eleContentMap, "ready");
                    for (int ind = 0; ind < temp.length; ind++) {
                        flag = getSingleEle(theD, para4check, this.byMap);
                    }
                } else if (para4check.equals("title")) // check title for ready mode
                {
                    StringBuilder[] temp = this.content.getContentNameList4name(this.eleContentMap, "PageTitle");
                    if (temp.length != 1) {
                        flag = false;
                        System.out.println("More than 1 title element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        flag = true;
                        System.out.println("TODO  compare the ((elementEntity) temp[0]).getTextContent() and compare with the element current content to confirm the result");
                    }
                } else if (para4check.equals("alertTitle")) {
                    StringBuilder[] temp = this.content.getContentNameList4name(this.eleContentMap, "alertTitle");
                    if (temp.length != 1) {
                        flag = false;
                        System.out.println("More than 1 alertTitle element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        flag = true;
                    }
                } else if (para4check.equals("overview")) {
                    StringBuilder[] temp = this.content.getContentNameList4name(this.eleContentMap, "overview");
                    for (int ind = 0; ind < temp.length; ind++) {
                        eleText = getSingleEleText(theD, para4check, this.byMap);
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


    public String comPageCheckWithInstanceInfo(WebDriver theD, String type4check, String
            para4check, FOXPageContent
                                                       inContent, HashMap<StringBuilder, elementEntity> inEleContentMap, HashMap<String, By> inByMap) throws Exception {  // type4check : ready, loading, error, temp , etc.  , para4check: target element name, all element, title element
        String result = null;
        boolean flag = false;
        String eleText = null;
        try {
            if (type4check.equals("ready")) {       // ready mode
                if (para4check.equals("all")) {     // check all element for ready mode
                    StringBuilder[] temp = inContent.getContentNameList4type(inEleContentMap, "ready");
                    for (int ind = 0; ind < temp.length; ind++) {
                        flag = getSingleEle(theD, para4check, inByMap);
                    }
                } else if (para4check.equals("title")) // check title for ready mode
                {
                    StringBuilder[] temp = inContent.getContentNameList4name(inEleContentMap, "PageTitle");
                    if (temp.length != 1) {
                        flag = false;
                        System.out.println("More than 1 title element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        flag = true;
                        System.out.println("TODO  compare the ((elementEntity) temp[0]).getTextContent() and compare with the element current content to confirm the result");
                    }
                } else if (para4check.equals("alertTitle")) {
                    StringBuilder[] temp = inContent.getContentNameList4name(inEleContentMap, "alertTitle");
                    if (temp.length != 1) {
                        flag = false;
                        System.out.println("More than 1 alertTitle element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        flag = true;
                    }
                } else if (para4check.equals("overview")) {
                    StringBuilder[] temp = inContent.getContentNameList4name(inEleContentMap, "overview");
                    for (int ind = 0; ind < temp.length; ind++) {
                        eleText = getSingleEleText(theD, para4check, inByMap);
                        System.out.println("here is the text of calendar title : " + eleText);
                        TimeZone tz = TimeZone.getTimeZone("America/New_York");
                        DateFormat theMonth = new SimpleDateFormat("MMM", Locale.ENGLISH);
                        theMonth.setTimeZone(tz);
                        DateFormat theYear = new SimpleDateFormat("yyyy", Locale.ENGLISH);
                        theYear.setTimeZone(tz);
                        String monthPart = theMonth.format(new Date());
                        String yearPart = theYear.format(new Date());
                        if ((eleText.toLowerCase().contains(monthPart.toLowerCase()))
                                && (eleText.contains(yearPart))) {
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


    public String comPageCheck(WebDriver theD, String type4check, String para4check, FOXPageContent
            tContent, HashMap<StringBuilder, elementEntity> eMap, HashMap<String, By> bMap) throws Exception {  // type4check : ready, loading, error, temp , etc.  , para4check: target element name, all element, title element
        String result = null;
        boolean flag = false;
        try {
            if (type4check.equals("ready")) {       // ready mode
                if (para4check.equals("all")) {     // check all element for ready mode
                    StringBuilder[] temp = tContent.getContentNameList4type(eMap, "ready");
                    for (int ind = 0; ind < temp.length; ind++) {
                        flag = getSingleEle(theD, para4check, bMap);
                    }
                } else if (para4check.equals("title")) // check title for ready mode
                {
                    StringBuilder[] temp = tContent.getContentNameList4name(eMap, "PageTitle");
                    if (temp.length != 1) {
                        flag = false;
                        System.out.println("More than 1 title element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        flag = true;
                        System.out.println("TODO  compare the ((elementEntity) temp[0]).getTextContent() and compare with the element current content to confirm the result");
                    }
                } else if (para4check.equals("alertTitle")) {
                    StringBuilder[] temp = tContent.getContentNameList4name(eMap, "alertTitle");
                    if (temp.length != 1) {
                        flag = false;
                        System.out.println("More than 1 alertTitle element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        flag = true;
                    }
                } else if (para4check.equals("bothBtn")) {
                    StringBuilder[] temp = tContent.getContentNameList4name(eMap, "yesBtn");
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

    public boolean loadingJudgement(WebDriver driver, HashMap<StringBuilder, elementEntity> eleMap) throws Exception {
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

    public boolean loadingCheckNew(WebDriver driver, HashMap<StringBuilder, elementEntity> eleMap) throws Exception {
        boolean result = false;
        String localFlag = null;
        try {
            long gstartTime = System.currentTimeMillis();
            for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase("loadOuter")) {
                    localFlag = new String(tempContentEntry.getKey().toString());
                    gstartTime = System.currentTimeMillis();
                    result = checkElementPresent(driver, localFlag, eleMap);
                }
            }
            if (localFlag == null) {
                gstartTime = System.currentTimeMillis();
                localFlag = new String(readProperity.getProValue("android_loading_name"));
                String eType = new String(readProperity.getProValue("android_loading_type"));
                String eStr = new String(readProperity.getProValue("android_loading_str"));
                String eContent = new String(readProperity.getProValue("android_loading_content"));
                String eDvalue = new String(readProperity.getProValue("android_loading_dvalue"));
                gstartTime = System.currentTimeMillis();
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

    public boolean checkElementPresent(WebDriver driver, String eleName, HashMap<StringBuilder, elementEntity> eleMap) throws Exception {
        boolean result = false;
        WebElement tempEle = null;
        try {
            tempEle = getElementFast(driver, eleName, eleMap);
            if (tempEle == null) {
                return false;
            } else if (tempEle.getText().equalsIgnoreCase(getElementXMLContent(eleName, eleMap)) || tempEle.getText().equalsIgnoreCase(getElementXMLDvalue(eleName, eleMap))) {
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

    public boolean checkElementPresent(WebDriver driver, String eleName, String eType, String eStr, String eContent, String eDvalue) throws Exception {
        boolean result = false;
        WebElement tempEle = null;
        try {
            tempEle = getElementFast(driver, eleName, eType, eStr);
            if (tempEle == null) {
                return false;
            } else if (tempEle.getText().equalsIgnoreCase(eContent) || tempEle.getText().equalsIgnoreCase(eDvalue)) {
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

    public WebElement getElementFast(WebDriver driver, String eleName, HashMap<StringBuilder, elementEntity> eleMap) throws Exception {
        WebElement theResult = null;
        try {
            final By tempBy = generateByObjWithType(eleName, eleMap);
            FOXDriverWait xWait = new FOXDriverWait(driver, 1, this.path4Log, false);
            theResult = xWait.until(
                    new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
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


    public WebElement getElementFast(WebDriver driver, String eleName, String eType, String eStr) throws Exception {
        WebElement theResult = null;
        try {
            final By tempBy = generateByObjWithType(eleName, eType, eStr);
            FOXDriverWait xWait = new FOXDriverWait(driver, 1, this.path4Log, false);
            theResult = xWait.until(
                    new FOXExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
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


    public By generateByObjWithType(String eleName, HashMap<StringBuilder, elementEntity> eleMap) throws Exception {
        By result = null;
        try {
            for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    String new_eleName = new String(tempContentEntry.getKey().toString());
                    String t_type = eleMap.get(new_eleName).getType().toString();
                    String t_str = eleMap.get(new_eleName).getLocatorStr().toString();
                    if (!t_str.contains(":REX")) {
                        if (t_type.equalsIgnoreCase("id")) {
                            result = By.id(t_str);
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
                if (eType.equalsIgnoreCase("id")) {
                    result = By.id(eStr);
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


    public String getElementXMLContent(String eleName, HashMap<StringBuilder, elementEntity> eleMap) throws Exception {
        String result = null;
        try {
            for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    String new_eleName = new String(tempContentEntry.getKey().toString());
                    result = eleMap.get(new_eleName).getTextContent().toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }


    public String getElementXMLDvalue(String eleName, HashMap<StringBuilder, elementEntity> eleMap) throws Exception {
        String result = null;
        try {
            for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    String new_eleName = new String(tempContentEntry.getKey().toString());
                    result = eleMap.get(new_eleName).getDefaultValue().toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static void main(String args[]) {
        WebDriver driver = null;
        List<String> a = Arrays.asList("v", "f", "3", "z");
        List<String> b = Arrays.asList("3", "z", "v", "f");
        System.out.println(compare(a, b));
        List<WebElement> ax = new ArrayList<WebElement>();
        System.out.println(ax.size());

        String ccc = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.support.v4.widget.DrawerLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[2]/android.widget.LinearLayout[1]/android.view.View[1]/android.widget.ScrollView[1]/android.widget.RelativeLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.RelativeLayout[:REXindex:]/android.widget.RelativeLayout[1]";
        String[] xxx = ccc.split(":REXindex:");
        System.out.println("xxx length " + xxx.length);
        System.out.println("end_ " + xxx[1]);

        Point a1 = new Point(1, 1);
        Point a2 = new Point(2, 2);
        Point b1 = new Point(1, 1);
        Point b2 = new Point(3, 2);

        //  List<Point> al = new List<Point>(a1,a2);
        // al.add(a1);
        // al.add(a2);

        // List<Point> bl = null;
        // bl.add(b1);
        // bl.add(b2);
        String para1 = "";
        String regex = "^[a-z0-9A-Z\\s*]+$";
        //     para1 = filter4space(para1);
        if (para1.matches(regex)) {
            System.out.println("");
            for (int ind = 0; ind < para1.length(); ind++) {
                int chr = para1.toLowerCase().charAt(ind);
                if (chr >= 48 && chr <= 57) {
                    chr = chr - 41;
                    //theResult.sendKeys(chr);
                } else if (chr >= 97 && chr <= 122) {
                    chr = chr - 68;
               //     theResult.sendKeys(chr);
                } else if (chr >= 65 && chr <= 90) {
                    System.out.println("    +++ ~~~ Wrong Search Key , HIGH CASE! ~~~ +++");
                } else if (chr == 32) {
                 //   theResult.sendKeys(62);
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


         //   driver = new WebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

}


