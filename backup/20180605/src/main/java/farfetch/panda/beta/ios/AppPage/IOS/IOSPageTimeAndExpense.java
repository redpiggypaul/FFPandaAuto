package farfetch.panda.beta.ios.AppPage.IOS;

import REXSH.REXAUTO.Component.Page.IOSPageContent;
import REXSH.REXAUTO.Component.Page.Element.ElementMapping;
import REXSH.REXAUTO.Component.PageLoadWait.IOSDriverWait;
import REXSH.REXAUTO.Component.PageLoadWait.IOSExpectedCondition;
import REXSH.REXAUTO.LocalException.PageException;
import REXSH.REXAUTO.LocalException.REXException;
import REXSH.REXAUTO.LocalException.XMLException;
import Utility.Log;
import Utility.readProperity;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by appledev131 on 4/11/16.
 */
public class IOSPageTimeAndExpense extends IOSPageTemplate {
    protected String fileName = "TimeAndExpense.json";
    String autoOSType = readProperity.getProValue("autoRunningOSType");
    private String contentFilePath = "\\PageXML\\IOS\\ProjectTimeAndExpense\\";
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
    protected int index4XPATH = 0;
    private String errNoticeWind = "";
    private String storeValue4compare = "";
    // private String confirmNoticeWind = "ProfileEditClearWin";
    private String confirmNoticeWind = "";
    private String completeNoticeWind = "";
    private String confirmNoticeWindWOT = "";

    private static String logSpace4thisPage = " --- IPADPageTimeAndExpense --";


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

    public boolean checkFlag4Win() {
        return this.content.flag4EleWindow;
    }

    public boolean getFlag4TitleCheck() {
        return this.content.getFlag4TitleCheck();
    }

    public void setFlag4TitleCheckTrue() {
        this.content.setFlag4TitleCheck(true);
    }

    public void setFlag4TitleCheckFalse() {
        this.content.setFlag4TitleCheck(false);
    }

/*    public boolean titleCheck(IOSDriver driver) throws Exception {
        System.out.println("    +++ ~~~ The titleCheck in " + logSpace4thisPage + " has been called ~~~ +++");
        return super.titleCheckTe(driver, "PageTitle", this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
    }
*/
    public HashMap<String, String> getEleWinMap() {
        return this.content.eleWindowMap;
    }

    public IOSPageTimeAndExpense(IOSDriver theD) throws Exception {
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


    public IOSPageTimeAndExpense(IOSDriver theD, String p4L) throws Exception {
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


    public IOSPageTimeAndExpense(String theFile, IOSDriver theD, Log theLogger) throws Exception {
        super(theFile, theD, theLogger);
        if (!autoOSType.toLowerCase().contains("windows")) {
            contentFilePath = new String(contentFilePath.replaceAll("\\\\", File.separator));
            path4Log = new String(path4Log.replaceAll("\\\\", File.separator));
        }
        this.comFileName = contentFilePath + fileName;
        this.dr = theD;
    }


    @Override
    public String getContentInBtn(IOSDriver driver, String eleName) throws Exception {
        String theResult = null;
        for (Iterator it = this.byMap.entrySet().iterator(); it.hasNext(); ) {
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
            }
        }
        return theResult;
    }

    public String btnOperationRoute(IOSDriver driver, String eleName) throws Exception { // elename may contains common 4 title, record 4 record under title
        System.out.println("    +++ ~~~ The btnOperationRoute in IPADPageTimeAndExpense has been called ~~~ +++");
        String tempLocatorStr = null;
        String tempType = null;
        String result = null;
        String commonType = null;
        String commonLocatorStr = null;
        String recordType = null;
        String recordLocatorStr = null;
        try {
            for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                if (eleName.equals(tempEle.getElementName())) {
                    tempLocatorStr = tempEle.getLocatorStr();
                    tempType = tempEle.getType();
                }
                if (tempEle.getElementName().toLowerCase().contains("commonby")) {
                    commonLocatorStr = tempEle.getLocatorStr();
                    commonType = tempEle.getType();
                }
                if (tempEle.getElementName().toLowerCase().contains("recordby")) {
                    recordLocatorStr = tempEle.getLocatorStr();
                    recordType = tempEle.getType();
                }
            }
            if (!eleName.toLowerCase().contains("_record")) { // for title
                if (commonLocatorStr != null) {  //
                    if ((commonType.equals("xpath")) && (tempType.equals("xpath") && tempLocatorStr.contains(":REXindex:"))) {
                        result = super.btnOperationSearch(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                        if (result.split("::").length > 1) {
                            index4XPATH = Integer.parseInt(result.split("::")[1]);
                            result = new String(result.split("::")[0]);
                        } else if (result.endsWith("::")) {
                            result = new String(result.split("::")[0]);
                        } else {
                            //
                        }
                    } else if ((commonType.equals("id")) && (tempType.equals("xpath") && tempLocatorStr.contains(":REXindex:"))) {
                        result = super.btnOperationSearch(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                        if (result.split("::").length > 1) {
                            index4XPATH = Integer.parseInt(result.split("::")[1]);
                            result = new String(result.split("::")[0]);
                        } else if (result.endsWith("::")) {
                            result = new String(result.split("::")[0]);
                        } else {
                            //
                        }
                    } else if ((tempType.equals("xpath") && tempLocatorStr.contains(":REXindex:") && !tempLocatorStr.contains(":REXrecord:"))) {
                        result = super.btnOperationAutoIndex(driver, eleName, this.byMap, this.eleContentMap, this.path4Log); // TODO
                    } else {
                        result = super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                    }
                } else {
                    throw new XMLException("the locator str don't match");
                }
            } else if (eleName.toLowerCase().contains("_record")) {  // for record
                System.out.println("stop 2.1  ~~");
                String name4title = eleName.split("_")[0];
                String titleTempLocatorStr = null;
                String titleTempType = null;
                // find the index of title directly
                for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                    ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                    if (name4title.equals(tempEle.getElementName())) {
                        titleTempLocatorStr = tempEle.getLocatorStr();
                        titleTempType = tempEle.getType();
                        break;
                    }
                }
                if (commonLocatorStr != null && titleTempLocatorStr != null & titleTempType != null) {  //
                    System.out.println("stop 2.2  ~~");
                    if ((commonType.equals("xpath")) && (titleTempType.equals("xpath") && titleTempLocatorStr.contains(":REXindex:"))) {
                        System.out.println("stop 2.3  ~~");
                        index4XPATH = getIndex4TitleIndex(driver, name4title, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                    } else if ((commonType.equals("id")) && (titleTempType.equals("xpath") && titleTempLocatorStr.contains(":REXindex:"))) {
                        System.out.println("stop 2.4  ~~");
                        index4XPATH = getIndex4TitleIndex(driver, name4title, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                    }
                }
                // find the index of title directly
                if (recordLocatorStr != null && recordType != null && tempLocatorStr != null && tempType != null) {
                    System.out.println("stop 3.1  ~~");
                    if ((recordType.equals("xpath")) && ((recordLocatorStr.contains(":REXindex:")) || (recordLocatorStr.contains(":REXrecord:")))) {
                        System.out.println("stop 3.3  ~~");
                        //// TODO: 5/18/16
                        result = super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);

                    } else if ((recordType.equals("id")) && ((tempType.contains(":REXindex:")) || (tempLocatorStr.contains(":REXrecord:")))) {
                        System.out.println("stop 3.4  ~~");
                        result = super.recordSearch(driver, eleName, this.eleContentMap, this.path4Log, index4XPATH, tempType, tempLocatorStr, recordType, recordLocatorStr);
                    } else {
                        System.out.println("stop 3.6  ~~");
                        throw new XMLException("element name _record need XML content support");
                    }
                } else {
                    System.out.println("stop 3.7  ~~");
                    throw new XMLException("the locator str don't match");
                }
            } else {
                result = super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }


    public String btnOperationRoute(IOSDriver driver, String eleName, String key) throws Exception { // elename may contains common 4 title, record 4 record under title
        System.out.println("    +++ ~~~ The btnOperationRoute in IPADPageTimeAndExpense has been called ~~~ +++");
        String tempLocatorStr = null;
        String tempType = null;
        String result = null;
        String commonType = null;
        String commonLocatorStr = null;
        String recordType = null;
        String recordLocatorStr = null;
        String relatedValue = null;
        String index4REXdig = "";
        int int4REXdig = 0;
        boolean keyMatchInTable = false;
        try {
            for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                if (eleName.equals(tempEle.getElementName())) {
                    tempLocatorStr = tempEle.getLocatorStr();
                    tempType = tempEle.getType();
                }
                if (tempEle.getElementName().toLowerCase().contains("commonby")) {
                    commonLocatorStr = tempEle.getLocatorStr();
                    commonType = tempEle.getType();
                }
                if (tempEle.getElementName().toLowerCase().contains("recordby")) {
                    recordLocatorStr = tempEle.getLocatorStr();
                    recordType = tempEle.getType();
                }
            }
            if (!tempLocatorStr.contains(":REX")) {
                if (!eleName.contains("_record")) {
                    result = super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                } else if (eleName.contains("_record")) {
                    result = super.recordSearch(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, index4XPATH, tempType, tempLocatorStr, recordType, recordLocatorStr, this.content.eleWaitStepMap, key);
                } else {
                }
            } else {
                if (eleName.toLowerCase().contains("_record") && (recordType != null && recordLocatorStr != null) && (commonType != null && commonLocatorStr != null)) {  // 4 record
                    String name4title = eleName.split("_")[0];
                    String titleTempLocatorStr = null;
                    String titleTempType = null;
                    // find the index of title directly
                    ElementMapping tempEleMapEntry = this.eleContentMap.get(name4title);
                    if(tempEleMapEntry!=null){
                        titleTempLocatorStr = tempEleMapEntry.getLocatorStr();
                        titleTempType = tempEleMapEntry.getType();
                    }else {

                        for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                            Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                            ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                            if (name4title.equalsIgnoreCase(tempEle.getElementName())) {
                                titleTempLocatorStr = tempEle.getLocatorStr();
                                titleTempType = tempEle.getType();
                                break;
                            }
                        }
                    }
                    // TODO get the complete table view
                    // TODO use the length match to get the correct screen, then use the string substring to get the REXindex dig
                    if (commonType.equalsIgnoreCase("xpath") && recordType.equalsIgnoreCase("xpath")) {
                        keyMatchInTable = super.tableMatch(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, index4XPATH, this.content.eleWaitStepMap, key, By.xpath(commonLocatorStr), By.xpath(recordLocatorStr));
                    } else if (commonType.equalsIgnoreCase("classname") && recordType.equalsIgnoreCase("classname")) {
                        keyMatchInTable = super.tableMatch(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, index4XPATH, this.content.eleWaitStepMap, key, By.className(commonLocatorStr), By.className(recordLocatorStr));
                    }
                    //index4REXdig = "";  //set the index dig in method, not in return result
                    //int4REXdig = 0;
                    if (keyMatchInTable == true) {
                        if (tempType.equalsIgnoreCase("xpath") && tempLocatorStr.contains(":REXindex:") && tempLocatorStr.contains(":REXrecord:")) {
                            String[] tempPart = eleName.split("_");
                            if (eleName.contains("hour")) {
                                String relateValueName = tempPart[0] + "_mapvalue";  // target value elename in the XML,can be changed easily
                                relatedValue = super.btnMatchReturnValue(driver, relateValueName, this.byMap, this.eleContentMap, this.path4Log, index4XPATH, tempType, tempLocatorStr, recordType, recordLocatorStr, this.content.eleWaitStepMap, key);
                                this.storeValue4compare = relatedValue.split("::")[0];
                                result = relatedValue.split("::")[1];
                            }
                        } else {

                        }
                    } else {
                        throw new PageException("Failed to find the table with target key parameter :" + key);
                    }
                    //TODO get the value for compare in return result
                    //TODO click the correct btn with match the value
                } else if (!eleName.toLowerCase().contains("_record") && commonLocatorStr != null && commonType != null) {  // 4 title
                    if ((commonType.equals("xpath")) && (tempType.equals("xpath") && tempLocatorStr.contains(":REXindex:"))) {
                        result = super.btnOperationSearch(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                        if (result.split("::").length > 1) {
                            index4XPATH = Integer.parseInt(result.split("::")[1]);
                            result = new String(result.split("::")[0]);
                        } else if (result.endsWith("::")) {
                            result = new String(result.split("::")[0]);
                        } else {
                            //
                        }
                    } else if ((commonType.equals("classname")) && (tempType.equals("xpath") && tempLocatorStr.contains(":REXindex:"))) {
                        result = super.btnOperationSearch(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                        if (result.split("::").length > 1) {
                            index4XPATH = Integer.parseInt(result.split("::")[1]);
                            result = new String(result.split("::")[0]);
                        } else if (result.endsWith("::")) {
                            result = new String(result.split("::")[0]);
                        } else {
                            //
                        }
                    } else if ((tempType.equals("xpath") && tempLocatorStr.contains(":REXindex:") && !tempLocatorStr.contains(":REXrecord:"))) {
                        result = super.btnOperationAutoIndex(driver, eleName, this.byMap, this.eleContentMap, this.path4Log); // TODO
                    } else {
                        result = super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                    }
                } else {
                    throw new XMLException("in " + this.getClass().getSimpleName() + "  element with :REX need commonby OR recordby in XML");
                }
            }
        } catch (
                Exception e
                ) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    public String getElementContent(IOSDriver driver, String eleName) throws Exception { // elename may contains common 4 title, record 4 record under title
        System.out.println("    +++ ~~~ The getElementContent in IPADPageTimeAndExpense has been called ~~~ +++");
        boolean movecheckdisable = this.content.getFlag4MoveCheck();
        boolean moveable = this.content.getMoveable();
        boolean localMoveable = false;
        String result = null;
        String eleType = null;
        String eleText = null;
        String eleStr = null;
        for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext() && eleType == null; ) {
            final Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                eleType = ((ElementMapping) tempContentEntry.getValue()).getType();
                eleStr = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();
            }
        }
        if (!eleName.equalsIgnoreCase("NaviBarBtn")) {
            if (movecheckdisable == false) {
                localMoveable = checkMoveable(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                this.content.setMoveable(localMoveable);
                this.content.setFlag4MoveCheck(true);
                if (localMoveable == true) {
                    if (eleType.equalsIgnoreCase("xpath")) {
                        result = super.getElementContent(driver, eleName, eleType, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
                    } else if (eleType.equalsIgnoreCase("classname")) {
                        result = super.getElementContentSmart(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
                    }
                } else {
                    if (eleType.equalsIgnoreCase("xpath")) {
                        result = super.getElementContentStatic(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
                    } else if (eleType.equalsIgnoreCase("classname")) {
                        result = super.getElementContentSmart(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
                    }
                }
            } else {
                if (moveable == false) {
                    if (eleType.equalsIgnoreCase("xpath")) {
                        result = super.getElementContentStatic(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
                    } else if (eleType.equalsIgnoreCase("classname")) {
                        result = super.getElementContentSmart(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
                    }
                } else {
                    if (eleType.equalsIgnoreCase("xpath")) {
                        result = super.getElementContentStatic(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
                    } else if (eleType.equalsIgnoreCase("classname")) {
                        result = super.getElementContentSmart(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
                    }
                }
            }
        } else {
            result = super.getElementContentStatic(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
        }
        return result;
    }

    public String getElementContent(IOSDriver driver, String eleName, String key) throws Exception { // elename may contains common 4 title, record 4 record under title
        if (!eleName.toLowerCase().contains("hour")) {
            System.out.println("    +++ ~~~ The getElementContent in IPADPageTimeAndExpense has been called ~~~ +++");
            return super.getElementContent(driver, eleName, this.byMap, this.eleContentMap);
        } else {
            System.out.println("    +++ ~~~ The getElementContent in IPADPageTimeAndExpense has been called ~~~ +++");
            String tempLocatorStr = null;
            String tempType = null;
            String result = null;
            String commonType = null;
            String commonLocatorStr = null;
            String recordType = null;
            String recordLocatorStr = null;
            try {
                for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                    ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                    if (eleName.equals(tempEle.getElementName())) {
                        tempLocatorStr = tempEle.getLocatorStr();
                        tempType = tempEle.getType();
                    }
                    if (tempEle.getElementName().toLowerCase().contains("commonby")) {
                        commonLocatorStr = tempEle.getLocatorStr();
                        commonType = tempEle.getType();
                    }
                    if (tempEle.getElementName().toLowerCase().contains("recordby")) {
                        recordLocatorStr = tempEle.getLocatorStr();
                        recordType = tempEle.getType();
                    }
                }
                if (!eleName.toLowerCase().contains("_record")) { // for title
                    if (commonLocatorStr != null) {  //
                        if ((commonType.equals("xpath")) && (tempType.equals("xpath") && tempLocatorStr.contains(":REXindex:"))) {
                            result = super.btnOperationSearch(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                            if (result.split("::").length > 1) {
                                index4XPATH = Integer.parseInt(result.split("::")[1]);
                                result = new String(result.split("::")[0]);
                            } else if (result.endsWith("::")) {
                                result = new String(result.split("::")[0]);
                            } else {
                                //
                            }
                        } else if ((commonType.equals("classname")) && (tempType.equals("xpath") && tempLocatorStr.contains(":REXindex:"))) {
                            result = super.btnOperationSearch(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                            if (result.split("::").length > 1) {
                                index4XPATH = Integer.parseInt(result.split("::")[1]);
                                result = new String(result.split("::")[0]);
                            } else if (result.endsWith("::")) {
                                result = new String(result.split("::")[0]);
                            } else {
                                //
                            }
                        } else {
                            result = super.getElementContent(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, 0);
                        }
                    } else {
                        throw new XMLException("the locator str don't match");
                    }
                } else if (eleName.toLowerCase().contains("_recordcontent")) {// for record content
                    String name4title = eleName.split("_")[0];
                    String titleTempLocatorStr = null;
                    String titleTempType = null;
                    for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                        Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                        ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                        if (name4title.equals(tempEle.getElementName())) {
                            titleTempLocatorStr = tempEle.getLocatorStr();
                            titleTempType = tempEle.getType();
                            break;
                        }
                    }
                    if (commonLocatorStr != null) {  //
                        if ((commonType.equals("xpath")) && (titleTempType.equals("xpath") && titleTempLocatorStr.contains(":REXindex:"))) {
                            index4XPATH = getIndex4TitleIndex(driver, name4title, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                        } else if ((commonType.equals("id")) && (titleTempType.equals("xpath") && titleTempLocatorStr.contains(":REXindex:"))) {
                            index4XPATH = getIndex4TitleIndex(driver, name4title, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                        }
                    }
                    String tLocatorStr = null;
                    String tType = null;
                    for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                        Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                        ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                        if (eleName.equals(tempEle.getElementName())) {
                            tLocatorStr = tempEle.getLocatorStr();
                            tType = tempEle.getType();
                            break;
                        }
                    }
                    if (recordLocatorStr != null) {
                        if ((tType.equals("xpath")) && ((tLocatorStr.contains(":REXindex:")) || (tLocatorStr.contains(":REXrecord:")))) {
                            if (index4XPATH == 0) {
                                throw new REXException("failed to find the target title index");
                            } else {
                                result = super.getStaticElementContent(driver, tLocatorStr, this.byMap, this.eleContentMap, this.path4Log, index4XPATH);
                            }
                        } else {
                            throw new XMLException("element name _recordContent need XML content support");
                        }
                    } else {
                        throw new XMLException("the locator str don't match");
                    }
                } else if (eleName.toLowerCase().contains("_record")) {  // for record
                    String name4title = eleName.split("_")[0];
                    String titleTempLocatorStr = null;
                    String titleTempType = null;
                    for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                        Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                        ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                        if (name4title.equals(tempEle.getElementName())) {
                            titleTempLocatorStr = tempEle.getLocatorStr();
                            titleTempType = tempEle.getType();
                            break;
                        }
                    }
                    if (commonLocatorStr != null && titleTempLocatorStr != null & titleTempType != null) {  //
                        if ((commonType.equals("xpath")) && (titleTempType.equals("xpath") && titleTempLocatorStr.contains(":REXindex:"))) {
                            index4XPATH = getIndex4TitleIndex(driver, name4title, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                        } else if ((commonType.equals("id")) && (titleTempType.equals("xpath") && titleTempLocatorStr.contains(":REXindex:"))) {
                            index4XPATH = getIndex4TitleIndex(driver, name4title, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                        }
                    }
                    // find the index of title directly
                    if (recordLocatorStr != null && recordType != null && tempLocatorStr != null && tempType != null) {
                        if ((recordType.equals("xpath")) && ((recordLocatorStr.contains(":REXindex:")) || (recordLocatorStr.contains(":REXrecord:")))) {
                            result = super.getElementContent(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, 0);
                        } else if ((recordType.equals("id")) && ((tempType.contains(":REXindex:")) || (tempLocatorStr.contains(":REXrecord:")))) {
                            String[] tempPart = eleName.split("_");
                            if (eleName.contains("hour")) {
                                String relateValueName = tempPart[0] + "_mapvalue";
                                result = super.contentMatchSearchByValue(driver, relateValueName, this.byMap, this.eleContentMap, this.path4Log, index4XPATH, tempType, tempLocatorStr, recordType, recordLocatorStr, this.content.eleWaitStepMap, key);
                                //this.storeValue4compare = relatedValue.split("::")[0];
                                //result = relatedValue.split("::")[1];
                            } else {
                                result = super.recordSearch(driver, eleName, this.eleContentMap, this.path4Log, index4XPATH, tempType, tempLocatorStr, recordType, recordLocatorStr);
                                //           result = super.recordSearch(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, index4XPATH, tempType, tempLocatorStr, recordType, recordLocatorStr, this.content.eleWaitStepMap);
                            }
                        } else {
                            throw new XMLException("element name _record need XML content support");
                        }
                    } else {
                        throw new XMLException("the locator str don't match");
                    }
                } else {
                    result = super.getElementContent(driver, eleName, this.byMap, this.eleContentMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            return result;
        }
    }


    public HashMap<String, ElementMapping> getEleConMap() {
        return this.eleContentMap;
    }


    public HashMap<String, By> getByMap(IOSDriver driver) {
        HashMap result = new HashMap<String, By>();
        result.clear();
        By tempElement = null;
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IPADPageTimeAndExpense . getByMap (IOSDriver driver)  : " + driver.toString());

        for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext(); ) {
            try {
                Thread.sleep(50);

                Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (((ElementMapping) tempContentEntry.getValue()).getType().equalsIgnoreCase("xpath")) {
                    tempElement = By.xpath(((ElementMapping) tempContentEntry.getValue()).getLocatorStr());
                } else if (((ElementMapping) tempContentEntry.getValue()).getType().equalsIgnoreCase("id")) {
                    tempElement = By.id(((ElementMapping) tempContentEntry.getValue()).getLocatorStr());
                } else if (((ElementMapping) tempContentEntry.getValue()).getType().equalsIgnoreCase("classname")) {
                    tempElement = By.className(((ElementMapping) tempContentEntry.getValue()).getLocatorStr());
                } else {
                    System.out.println(logSpace4thisPage + "currently we just support xpath & classname");
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
        }
        return result;
    }

    public HashMap<String, By> getByMap(IOSDriver driver, HashMap<String, ElementMapping> eleMap) {
        HashMap result = new HashMap<String, By>();
        result.clear();
        By tempElement = null;
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IPADPageTimeAndExpense . getByMap (IOSDriver driver, HashMap<String, ElementMapping> eleMap)  : " + driver.toString());

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
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Iterator it = result.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
        }
        return result;
    }

    public void btnOperation(IOSDriver driver, String eleName) throws Exception {
        System.out.println("    +++ ~~~ The btnOperation in IPADPageTimeAndExpense has been called ~~~ +++");
        super.btnOperation(driver, eleName, this.byMap);
    }

    public void textOperation(IOSDriver driver, String eleName, String para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperation in IPADPageTimeAndExpense has been called ~~~ +++");
        super.textOperation(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
    }


    public String textOperationWithSaveInput(IOSDriver driver, String eleName, String para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperationWithSaveInput in IPADPageTimeAndExpense has been called ~~~ +++");
        return super.textOperationWithSaveInput(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
    }

    public void moveUP(IOSDriver dr) throws Exception {
        System.out.println(" moveUP in IPADPageTimeAndExpense ");
        super.moveUpFitScreen(dr); //TODO for every page which need roll page
    }


    public void moveDOWN(IOSDriver dr) throws Exception {
        System.out.println(" moveDOWN in IPADPageTimeAndExpense ");
        super.moveDOWNtest(dr);
    }

    public void moveDOWNall(IOSDriver dr) throws Exception {
        System.out.println(" moveDOWNall in IPADPageTimeAndExpense ");
        super.moveDOWNall(dr);
    }

    public void moveUPall(IOSDriver dr) throws Exception {
        System.out.println(" moveUPall in IPADPageTimeAndExpense ");
        super.moveUPall(dr);
    }

    public String dropDownListOperation(IOSDriver driver, String eleName) throws Exception {  //TODO
        String defaultText = null;
        String dropItemValue = null;
        System.out.println("    +++ ~~~ The dropDownListOperation in IPADPageTimeAndExpense has been called ~~~ +++");
        defaultText = super.dropDownListShow(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, this.content.eleDefaultValueMap);
        dropItemValue = super.dropDownItemChoose(driver, defaultText, this.byMap, this.eleContentMap, this.path4Log);
        return dropItemValue;
    }

    public String dropDownListOperation(IOSDriver driver, String eleName, String extPara) throws Exception {  //TODO
        String defaultText = null;
        String dropItemValue = null;
        System.out.println("    +++ ~~~ The dropDownListOperation in IPADPageTimeAndExpense has been called ~~~ +++");
        System.out.println(" this time we want to choose the " + extPara + " item ");
        defaultText = super.dropDownListShow(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, this.content.eleDefaultValueMap);
        dropItemValue = super.dropDownItemChooseWithPara(driver, defaultText, this.byMap, this.eleContentMap, this.path4Log, extPara);
        return dropItemValue;
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
                        flag = true;

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

            IOSPageTimeAndExpense x = new IOSPageTimeAndExpense("/PageXML/AND/bPageProfileEdit.xml", driver, null);
            System.out.println("before defaulf operation");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

}


