package farfetch.panda.beta.ios.AppPage.IOS;

import REXSH.REXAUTO.Component.Page.Element.ElementMapping;
import REXSH.REXAUTO.Component.Page.IOSPageContent;
import REXSH.REXAUTO.Component.PageLoadWait.IOSDriverWait;
import REXSH.REXAUTO.Component.PageLoadWait.IOSExpectedCondition;
import REXSH.REXAUTO.LocalException.REXException;
import REXSH.REXAUTO.LocalException.XMLException;
import Utility.Log;
import Utility.readProperity;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.util.*;


/**
 * Created by appledev131 on 4/11/16.
 */
public class IOSPageYourHistory extends IOSPageTemplate {
    protected String fileName = "YourHistory.json";
    String autoOSType = readProperity.getProValue("autoRunningOSType");
    private String contentFilePath = "\\PageXML\\IOS\\History\\";
    public String path4Log = System.getProperty("user.dir");
    protected String targetPagename = "";
    protected String comFileName = null;
    protected IOSPageContent content = null;
    protected HashMap<String, ElementMapping> eleContentMap = null;
    protected HashMap<String, IOSElement> eleMap = null;
    public HashMap<String, By> byMap = null;
    public HashMap<String, String> textContentMap = null;
    protected IOSDriver dr = null;
    protected int osType = 2;
    protected HashMap<String, String> nextPageList = null;
    private static String logSpace4thisPage = " --- IOSPageYourHistory --";
    protected long timeOutInSeconds = Long.parseLong(readProperity.getProValue("pageLoadTimeout"));

    private String errNoticeWind = "";
    private String confirmNoticeWind = "";
    private String completeNoticeWind = "";
    private String confirmNoticeWindWOT = "";
    private String storeValue4compare = "";

    public boolean getFlag4TitleCheck() {
        return this.content.getFlag4TitleCheck();
    }

    public void setFlag4TitleCheckTrue() {
        this.content.setFlag4TitleCheck(true);
    }

    public void setFlag4TitleCheckFalse() {
        this.content.setFlag4TitleCheck(false);
    }


    public IOSPageYourHistory(IOSDriver theD) throws Exception {
        super(theD);
        this.dr = theD;
        if (!autoOSType.toLowerCase().contains("windows")) {
            contentFilePath = new String(contentFilePath.replaceAll("\\\\", File.separator));
            path4Log = new String(path4Log.replaceAll("\\\\", File.separator));
        }
        String tempName = contentFilePath + fileName;

        content = new IOSPageContent(theD, tempName, osType);
        eleContentMap = content.getContentMap(content.getContentMap4Base(tempName, osType));
        Thread.sleep(3000);
        byMap = getByMap(theD);
    }


    public IOSPageYourHistory(IOSDriver theD, String p4L) throws Exception {
        super(theD);
        this.dr = theD;
        if (!autoOSType.toLowerCase().contains("windows")) {
            contentFilePath = new String(contentFilePath.replaceAll("\\\\", File.separator));
            path4Log = new String(path4Log.replaceAll("\\\\", File.separator));
        }
        String tempName = contentFilePath + fileName;

        content = new IOSPageContent(theD, tempName, osType);
        eleContentMap = content.getContentMap4Base(tempName, osType);
        byMap = content.getByMap(theD,this.eleContentMap);
        loadingJudgement(theD, this.eleContentMap);
        this.path4Log = p4L;
    }

    public IOSPageYourHistory(String theFile, IOSDriver theD, Log theLogger) throws Exception {
        super(theFile, theD, theLogger);
        if (!autoOSType.toLowerCase().contains("windows")) {
            contentFilePath = new String(contentFilePath.replaceAll("\\\\", File.separator));
            path4Log = new String(path4Log.replaceAll("\\\\", File.separator));
        }
        this.comFileName = contentFilePath + fileName;
        this.dr = theD;
    }


    public String getConfWindName() {
        return this.confirmNoticeWind;
    }

    public String getErrWindName() {
        return this.errNoticeWind;
    }

    public String getCompWindName() {
        return this.completeNoticeWind;
    }

    public String getConfWindWOTName() {
        return this.confirmNoticeWindWOT;
    }

    public String value4compare() {
        return this.storeValue4compare;
    }

    public HashMap<String, String> getEleWinMap() {
        return this.content.eleWindowMap;
    }

    public boolean checkFlag4Win() {
        return this.content.flag4EleWindow;
    }

    public HashMap<String, AndroidElement> getAndroidElemenet(IOSDriver driver) {
        HashMap<String, AndroidElement> theAndEle = null;
        HashMap<String, By> theByMap = getByMap(dr);


        return theAndEle;
    }

    public HashMap<String, ElementMapping> getEleConMap() {
        return this.eleContentMap;
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

    /*
            public static void main(String[] args) {
                String s = trim("  hello  ");
                System.out.println(s);
            }
    */
    public HashMap<String, By> getByMap(IOSDriver driver) {
        HashMap result = new HashMap<String, By>();
        result.clear();
        By tempElement = null;
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IOSPageYourHistory . getByMap(IOSDriver driver)   : " + driver.toString());

        for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext(); ) {
            try {
                Thread.sleep(50);

                Map.Entry tempContentEntry = (Map.Entry) it.next();
            //    System.out.println(logSpace4thisPage + "xxxxxx ------ Check the. contMap  getkey ~~~~ " + tempContentEntry.getKey());
            //    System.out.println(logSpace4thisPage + "xxxxxx ------ Check the. contMap  getValue~~~~ " + tempContentEntry.getValue());
            //    System.out.println(logSpace4thisPage + "^^^^^^^^^^^^^^^^^^^^^^ getElementMap Call combindAndElement ^^^^^^^^^^^^^^^^^");
            //    System.out.println(logSpace4thisPage + "Check the value in Entry in combind : " + (ElementMapping) tempContentEntry.getValue() + " !!! ");

                //     tempElement = combindAndElement(driver, (ElementMapping) tempContentEntry.getValue(), 6, this.osTypeInContent);
                if (((ElementMapping) tempContentEntry.getValue()).getType().equalsIgnoreCase("xpath")) {
                    tempElement = By.xpath(((ElementMapping) tempContentEntry.getValue()).getLocatorStr());
                } else if (((ElementMapping) tempContentEntry.getValue()).getType().equalsIgnoreCase("classname")) {
                    tempElement = By.className(((ElementMapping) tempContentEntry.getValue()).getLocatorStr());
                } else {
                    System.out.println(logSpace4thisPage + "currently we just support xpath & classname");
                }
            //    System.out.println(logSpace4thisPage + "Check the tempElement from combind : " + tempElement.toString() + " !!! ");
            //    System.out.println(logSpace4thisPage + "Check the getKey from combind : " + tempContentEntry.getKey().toString() + " !!! ");
            //    System.out.println(logSpace4thisPage + "Chcek the Class of tempElement getKey " + tempContentEntry.getKey().toString() instanceof String);
            //    System.out.println(logSpace4thisPage + "Chcek the Class of tempElement " + tempElement.getClass());

                result.put(tempContentEntry.getKey().toString(), tempElement);
            //    System.out.println("!!!!!! " + result.toString() + " !!!!!!");
                //return resultList;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Iterator it = result.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
        //    System.out.println("@@@@@@------ getElementMap check result value ------@@@@@@  " + eleEntry.getKey());
        //    System.out.println("@@@@@@------ getElementMap check result value ------@@@@@@  " + eleEntry.getValue());
        }
        return result;
    }

    public HashMap<String, By> getByMap(IOSDriver driver, HashMap<String, ElementMapping> eleMap) {
        HashMap result = new HashMap<String, By>();
        result.clear();
        By tempElement = null;
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IOSPageYourHistory . getByMap (IOSDriver driver, HashMap<String, ElementMapping> eleMap)  : " + driver.toString());

        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
            try {
                Thread.sleep(50);

                Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (((ElementMapping) tempContentEntry.getValue()).getType().equalsIgnoreCase("xpath")) {
                    tempElement = By.xpath(((ElementMapping) tempContentEntry.getValue()).getLocatorStr());
                } else if (((ElementMapping) tempContentEntry.getValue()).getType().equalsIgnoreCase("id")) {
                    tempElement = By.id(((ElementMapping) tempContentEntry.getValue()).getLocatorStr());
                } else {
                    System.out.println(logSpace4thisPage + "currently we just support xpath & id");
                }

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


    public WebElement rest() {
        {
            System.out.println(logSpace4thisPage + "!!! ^^^^^^^^^^^^^^^^ This time we call the androiduiautomator : ");
        }
        return null;
    }


    public String textOperationWithSaveInputOLD(IOSDriver driver, String eleName, String para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperationWithSaveInput in IOSPageYourHistory has been called ~~~ +++");
        String tempDefaultValue = null;
        WebElement theResult = null;
        String result = null;
        try {
            for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                if (eleName.equals(tempEle.getElementName())) {
                    tempDefaultValue = tempEle.getDefaultValue();
                    IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, this.path4Log);
                    final String tempByStr = tempEle.getLocatorStr();
                    final String tempByType = tempEle.getType();
                    if (eleName.equalsIgnoreCase("searchContent")) {
                        if (tempByType.equalsIgnoreCase("id")) {
                            theResult = xWait.until(
                                    new IOSExpectedCondition<WebElement>() {
                                        public WebElement apply(IOSDriver driver) {
                                            return driver.findElement(By.id(tempByStr));
                                        }
                                    });
                        } else if (tempByType.equalsIgnoreCase("xpath")) {
                            theResult = xWait.until(
                                    new IOSExpectedCondition<WebElement>() {
                                        public WebElement apply(IOSDriver driver) {
                                            return driver.findElement(By.xpath(tempByStr));
                                        }
                                    });
                        }
                        theResult.click();
                        //   for (int tempTextLength = theResult.getText().length();tempTextLength!= 0;tempTextLength = theResult.getText().length()) {
                        for (int ind = 3; ind > 0; ind--) {
                            int tempTextLength = theResult.getText().length();
                            if (tempTextLength != 0) {
                                driver.sendKeyEvent(123);
                                for (int i = 0; i < tempTextLength; i++) {
                                    driver.sendKeyEvent(67);
                                }
                            }
                        }
                        System.out.println("Try to sendKeyEvents");
                        String regex = "^[a-z0-9A-Z\\s*]+$";
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
                            result = theResult.getText();
                        } else {
                            System.out.println("    +++ ~~~ Wrong Search Key , including NON \"^[a-z0-9A-Z\\\\s*]+$\"! ~~~ +++");
                        }
                    } else if (tempDefaultValue != null) {
                        result = super.textOperationWithSaveInput(driver, eleName, para1, this.byMap, tempDefaultValue);
                    } else {
                        throw new XMLException("some element need to add with the default value");
                    }
                    break;
                }
            }
        } catch (InterruptedException e) {
            throw e;
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In IOSPageYourHistory . textOperationWithSaveInput , Other Error appear : " + e.getCause());
            e.printStackTrace();
        } finally {
            return result;
        }
    }


    public String textOperationWithSaveInput(IOSDriver driver, String eleName, String para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperationWithSaveInput in IOSPageYourHistory has been called ~~~ +++");
        String tempDefaultValue = null;
        WebElement theResult = null;
        String result = null;
        final String localPath = this.path4Log;
        try {
            if (eleName.equals("searchContent")) {
                for (Iterator it = this.byMap.entrySet().iterator(); it.hasNext() && theResult == null; ) {
                    System.out.println("iterator : " + it == null);
                    System.out.println("inMap size : " + this.byMap.size());
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
                        theResult.click();
                    }
                }
                for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                    ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                    if ("searchContentEdit".equals(tempEle.getElementName())) {
                        tempDefaultValue = tempEle.getDefaultValue();
                        if (tempDefaultValue != null) {
                            result = super.textOperationWithSaveInput(driver, "searchContentEdit", para1, this.byMap, tempDefaultValue);
                        } else {
                            throw new XMLException("some element need to add with the default value");
                        }
                        break;
                    }
                }
                //   result = super.textOperationWithSaveInput(driver, "searchContentEdit", para1, this.byMap, tempDefaultValue);
            } else {
                result = super.textOperationWithSaveInput(driver, eleName, para1, this.byMap, tempDefaultValue);
            }
        } catch (
                InterruptedException e
                ) {
            throw e;
        } catch (
                REXException e
                )

        {
            throw e;
        } catch (
                TimeoutException e
                ) {
            throw e;
        } catch (
                Exception e
                ) {
            System.out.println(logSpace4thisPage + "In IOSPageYourHistory . textOperationWithSaveInput , Other Error appear : " + e.getCause());
            e.printStackTrace();
        } finally

        {
            return result;
        }

    }

    public void textOperationOLD(IOSDriver driver, String eleName, String para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperation in IOSPageYourHistory has been called ~~~ +++");
        for (Iterator it = this.byMap.entrySet().iterator(); it.hasNext(); ) {
            final Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                System.out.println(eleName + " ::");
                IOSDriverWait xWait = new IOSDriverWait(driver, timeOutInSeconds, this.path4Log);

                if (eleName.startsWith("role")) {
                    //TODO
                    //    textOperationListWithSaveInput
                } else {
                    WebElement theResult = xWait.until(
                            new IOSExpectedCondition<WebElement>() {
                                public WebElement apply(IOSDriver driver) {
                                    return driver.findElement((By) tempContentEntry.getValue());
                                }
                            });
                    if (eleName.equalsIgnoreCase("searchContent")) {
                        theResult.click();
                        //   for (int tempTextLength = theResult.getText().length();tempTextLength!= 0;tempTextLength = theResult.getText().length()) {
                        for (int ind = 3; ind > 0; ind--) {
                            int tempTextLength = theResult.getText().length();
                            if (tempTextLength != 0) {
                                driver.sendKeyEvent(123);
                                for (int i = 0; i < tempTextLength; i++) {
                                    driver.sendKeyEvent(67);
                                }
                            }
                        }
                        System.out.println("Try to sendKeyEvents");
                        String regex = "^[a-z0-9A-Z\\s*]+$";
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
                    } else {
                        theResult.sendKeys();
                    }
                }
            }
        }
    }

    public void textOperation(IOSDriver driver, String eleName, String para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperation in IOSPageYourHistory has been called ~~~ +++");
        String tempDefaultValue = null;
        String result = null;
        try {
            for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                if (eleName.equals(tempEle.getElementName())) {
                    tempDefaultValue = tempEle.getDefaultValue();
                    if (tempDefaultValue != null) {
                        result = super.textOperationWithSaveInput(driver, eleName, para1, this.byMap, tempDefaultValue);
                    } else {
                        throw new XMLException("some element need to add with the default value");
                    }
                    break;
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void btnOperation(IOSDriver driver, String eleName) throws Exception {
        System.out.println("    +++ ~~~ The btnOperation in IOSPageYourHistory has been called ~~~ +++");
        if (eleName.startsWith("role")) {
            super.btnOperationListMatch(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
        } else {
            super.btnOperation(driver, eleName, this.byMap);
        }
    }

    public String btnOperationRoute(IOSDriver driver, String eleName) throws Exception {
        System.out.println("    +++ ~~~ The btnOperationRoute in IOSPageYourHistory has been called ~~~ +++");
        if (eleName.startsWith("role")) {
            return super.btnOperationListMatch(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
        } else {
            return super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
        }
    }

    public String getElementContent(IOSDriver driver, String eleName) throws Exception {
        System.out.println("    +++ ~~~ The getElementContent in IOSPageYourHistory has been called ~~~ +++");
        ElementMapping tempEleCon = this.eleContentMap.get(eleName);
        String tempEleStr = null;
        List<String> tempList = new ArrayList<String>();
        String result = "";
        if (tempEleCon != null) {
            tempEleStr = tempEleCon.getLocatorStr();
        } else {
            ElementMapping newtempEleCon = null;
            for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext() && newtempEleCon == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    newtempEleCon = (ElementMapping) tempContentEntry.getValue();
                }
            }
            tempEleStr = newtempEleCon.getLocatorStr();
        }
        if (eleName.startsWith("role")) {
            if (!tempEleStr.contains(":REX")) {
                tempList = super.getElementContentList(driver, eleName, this.byMap, this.eleContentMap);
            } else {
                tempList = super.getElementContentListMoveDyncIndex(driver, eleName, tempEleStr, this.path4Log);
            }
            if (tempList.size() != 0) {
                for (int ind = 0; ind < tempList.size(); ind++) {
                    if (ind == 0) {
                        result = new String(tempList.get(ind));
                    } else {
                        result = new String(result + "_:REX:_" + tempList.get(ind));
                    }
                }
            }
            return result;
        } else {
            return super.getElementContent(driver, eleName, this.byMap, this.eleContentMap);
        }
    }

    public String sortElementContent(IOSDriver driver, String eleName) throws Exception {
        System.out.println("    +++ ~~~ The sortElementContent in IOSPageYourHistory has been called ~~~ +++");
        List<String> tempList = new ArrayList<String>();
        if (eleName.startsWith("role")) {
            String result = "";
            ElementMapping tempEleCon = this.eleContentMap.get(eleName);
            String tempEleStr = null;
            if (tempEleCon != null) {
                tempEleStr = tempEleCon.getLocatorStr();
            } else {
                ElementMapping newtempEleCon = null;
                for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext() && newtempEleCon == null; ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        newtempEleCon = (ElementMapping) tempContentEntry.getValue();
                    }
                }
                tempEleStr = newtempEleCon.getLocatorStr();
            }
            if (!tempEleStr.contains(":REX")) {
                tempList = super.getElementContentListMove(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);

            } else {
                tempList = super.getElementContentListMoveDyncIndex(driver, eleName, tempEleStr, this.path4Log);
            }
            if (tempList.size() != 0) {
                for (int ind = 0; ind < tempList.size(); ind++) {
                    if (ind == 0) {
                        result = new String(tempList.get(ind));
                    } else {
                        result = new String(result + "_:REX:_" + tempList.get(ind));
                    }
                }
            }
            return result;
        } else {
            return super.getElementContent(driver, eleName, this.byMap, this.eleContentMap);
        }
    }

    public String checkAbnormalElement(IOSDriver driver, String eleName) throws REXException {
        System.out.println("    +++ ~~~ The checkAbnormalElement in IOSPageYourHistory has been called ~~~ +++");
        return super.checkAbnormalElement(driver, eleName, this.byMap, this.eleContentMap);
    }

    public String comPageCheck(IOSDriver theD, String type4check, String para4check) {  // type4check : ready, loading, error, temp , etc.  , para4check: target element name, all element, title element
        String result = null;
        boolean flag = false;
        try {
            if (type4check.equals("ready")) {       // ready mode
                if (para4check.equals("all")) {     // check all element for ready mode
                    String[] temp = this.content.getContentNameList4type(this.eleContentMap, "ready");
                    for (int ind = 0; ind < temp.length; ind++) {
                        flag = super.getSingleEle(theD, para4check, this.byMap);
                    }
                } else if (para4check.equals("title")) // check title for ready mode
                {
                    ArrayList<String> temp = this.content.getContentNameList4name(this.eleContentMap, "PageTitle");
                    if (temp.size() != 1) {
                        flag = false;
                        System.out.println("More than 1 title element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        //TODO  compare the ((ElementMapping) temp[0]).getTextContent() and compare with the element current content to confirm the result
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
                        //TODO  compare the ((ElementMapping) temp[0]).getTextContent() and compare with the element current content to confirm the result
                        if (checkAbnormalElement(theD, "alertTitle").equals("ready")) {
                            flag = false;
                        } else if (checkAbnormalElement(theD, "alertTitle").equals("someerror")) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                    }
                } else {
                }
            } else {
                //TODO
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


    public void moveUP(IOSDriver dr) throws Exception {
        System.out.println(" moveUP in bANDPageProfileEdit ");
        super.moveUPtest(dr);
    }


    public void moveDown(IOSDriver dr) throws Exception {
        System.out.println(" moveDown in bANDPageProfileEdit ");
        super.moveDOWNtest(dr);
    }

    public static void main(String args[]) {
        IOSDriver driver = null;

        try {
            File app = new File("/Users/appledev131/Downloads", "app-debug.apk");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("deviceName", "Android Emulator");
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
            capabilities.setCapability(CapabilityType.VERSION, "5.1");
            capabilities.setCapability("app", app.getAbsolutePath());
            capabilities.setCapability("app-package", "com.pwc.talentexchange");
            capabilities.setCapability("app-activity", ".activity.BaseActivity");
            driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            Thread.sleep(6000);

            IOSPageYourHistory x = new IOSPageYourHistory("/PageXML/AND/loginPage.xml", driver, null);
            System.out.println("before defaulf operation");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

}


