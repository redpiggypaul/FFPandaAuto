package farfetch.panda.beta.ios.AppPage.IOS;

import REXSH.REXAUTO.Component.Page.Element.ElementMapping;
import REXSH.REXAUTO.Component.Page.IOSPageContent;
import REXSH.REXAUTO.Component.Page.PageIndexGroup;
import REXSH.REXAUTO.Component.PageLoadWait.IOSDriverWait;
import REXSH.REXAUTO.Component.PageLoadWait.IOSExpectedCondition;
import REXSH.REXAUTO.LocalException.REXException;
import REXSH.REXAUTO.LocalException.XMLException;
import Utility.Log;
import Utility.readProperity;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by appledev131 on 4/11/16.
 */
public class IOSPageProfileEdit extends IOSPageTemplate {
    protected String fileName = "ProfileEdit.json";
    String autoOSType = readProperity.getProValue("autoRunningOSType");
    private String contentFilePath = "\\PageXML\\IOS\\Resume\\";
    public String path4Log = System.getProperty("user.dir");
    protected String targetPagename = "";
    protected String comFileName = null;
    protected IOSPageContent content = null;
    protected PageIndexGroup indexGroup = null;
    protected ArrayList<String> titleList = new ArrayList<String>();
    protected HashMap<String, ElementMapping> eleContentMap = null;
    protected HashMap<String, IOSElement> eleMap = null;
    public HashMap<String, By> byMap = null;
    protected IOSDriver dr = null;
    protected int index4XPATH = 0;
    protected int osType = 2;
    protected HashMap<String, String> nextPageList = null;
    private String errNoticeWind = "errorWin";
    private String confirmNoticeWind = "";
    private String completeNoticeWind = "";
    private String confirmNoticeWindWOT = "";
    private String storeValue4compare = "";
    private boolean pageMoveableCheckDisable = true;    // flag for check the page is moveable or not during test
    private boolean moveable = true;                   // flag for the page is moveable or not
    private static String logSpace4thisPage = " --- IPADPageProfileEdit--";
  /*
    private HashMap<String, Integer> titleLocation = new HashMap<String, Integer>() {   // iphone 6
        {
            put("titleText", 1);
            put("summaryText", 2);
            put("skills", 2);
            put("workExp",3);
            put("education", 3);
            put("certifiANDlicense", 3);
            put("languages", 3);
            put("publication", 4);
            put("award", 4);
            put("patents", 4);
            put("proAffiliation", 4);
            put("security", 4);
            put("military", 4);
            put("reference", 5);
            put("links", 5);
        }
    };
*/

    private HashMap<String, Integer> titleLocation = new HashMap<String, Integer>() {   // iphone 6 plus
        {
            put("titleText", 1);
            put("summaryText", 2);
            put("skills", 2);
            put("workExp", 2);
            put("education", 2);
            put("certifiANDlicense", 3);
            put("languages", 3);
            put("publication", 3);
            put("award", 3);
            put("patents", 3);
            put("proAffiliation", 4);
            put("security", 4);
            put("military", 4);
            put("reference", 4);
            put("links", 5);
            put("link_field", 5);
        }
    };

    private HashMap<String, Integer> cellIndex = new HashMap<String, Integer>() {
        {
            put("skills", 11);
            put("workExp", 13);
            put("education", 15);
            put("certifiANDlicense", 17);
            put("languages", 19);
            put("publication", 21);
            put("award", 23);
            put("patents", 25);
            put("proAffiliation", 27);
            put("security", 29);
            put("reference", 32);
            put("links", 34);
        }
    };


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

    public HashMap<String, String> getEleWinMap() {
        return this.content.eleWindowMap;
    }

    public IOSPageProfileEdit(IOSDriver theD) throws Exception {
        super(theD);
        this.dr = theD;
        if (!autoOSType.toLowerCase().contains("windows")) {
            contentFilePath = new String(contentFilePath.replaceAll("\\\\", File.separator));
            path4Log = new String(path4Log.replaceAll("\\\\", File.separator));
        }
        String tempName = contentFilePath + fileName;

        content = new IOSPageContent(theD, tempName, osType);
        indexGroup = new PageIndexGroup();
        eleContentMap = content.getContentMap(content.getContentMap4JSON(tempName, osType));
        Thread.sleep(3000);
        byMap = getByMap(theD);
    }


    public IOSPageProfileEdit(IOSDriver theD, String p4L) throws Exception {
        super(theD);
        this.dr = theD;
        if (!autoOSType.toLowerCase().contains("windows")) {
            contentFilePath = new String(contentFilePath.replaceAll("\\\\", File.separator));
            path4Log = new String(path4Log.replaceAll("\\\\", File.separator));
        }
        String tempName = contentFilePath + fileName;

        content = new IOSPageContent(theD, tempName, osType);
        indexGroup = new PageIndexGroup();
        eleContentMap = content.getContentMap4Base(tempName, osType);
        byMap = content.getByMap(theD,this.eleContentMap);
        loadingJudgement(theD, this.eleContentMap);
        this.path4Log = p4L;
        this.content.setFlag4MoveCheck(this.pageMoveableCheckDisable);
        this.content.setMoveable(this.moveable);
        loadingJudgement(theD, this.eleContentMap);
    }


    public IOSPageProfileEdit(String theFile, IOSDriver theD, Log theLogger) throws Exception {
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
        System.out.println("    +++ ~~~ The btnOperationRoute in IPADPageProfileEdit has been called ~~~ +++");

        // System.out.println(driver.getPageSource());

        String tempLocatorStr = null;
        String tempType = null;
        String result = null;
        String commonType = null;
        String commonLocatorStr = null;
        String recordType = null;
        String recordLocatorStr = null;
        String combind_btnType = null;
        String combind_btnLocatorStr = null;
        String eleType = null;
        String eleText = null;
        String eleContent = null;
        ElementMapping tempEleMap = this.eleContentMap.get(eleName);
        if (tempEleMap != null) {
            eleType = tempEleMap.getType();
            eleText = tempEleMap.getTextContent();
        } else {
            for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext() && eleType == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    eleType = ((ElementMapping) tempContentEntry.getValue()).getType();
                    eleText = ((ElementMapping) tempContentEntry.getValue()).getTextContent();
                }
            }
        }
        try {
            long startTime = System.currentTimeMillis();
            ElementMapping tEle = this.eleContentMap.get(eleName);
            System.out.println("^^^^^^    find element with get in hashmap ：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
            if (tEle != null) {
                tempLocatorStr = tEle.getLocatorStr();
                tempType = tEle.getType();

            } else {
                 startTime = System.currentTimeMillis();
                for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                    ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                    if (eleName.equals(tempEle.getElementName())) {
                        System.out.println("^^^^^^    find element with Iterator ：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                        eleName = new String(tempEle.getElementName());
                        tempLocatorStr = tempEle.getLocatorStr();
                        tempType = tempEle.getType();
                    }
                }
            }
            tEle = null;
            tEle = this.eleContentMap.get("commonby");
            if (tEle != null) {
                commonLocatorStr = tEle.getLocatorStr();
                commonType = tEle.getType();
            } else {
                for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                    ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                    if (tempEle.getElementName().toLowerCase().contains("commonby")) {
                        commonLocatorStr = tempEle.getLocatorStr();
                        commonType = tempEle.getType();
                    }
                }
            }
            tEle = null;
            tEle = this.eleContentMap.get("recordby");
            if (tEle != null) {
                recordLocatorStr = tEle.getLocatorStr();
                recordType = tEle.getType();
            } else {
                for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                    ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                    if (tempEle.getElementName().toLowerCase().contains("recordby")) {
                        recordLocatorStr = tempEle.getLocatorStr();
                        recordType = tempEle.getType();
                    }
                }
            }
            loadingJudgement(driver, this.eleContentMap);
            if (!eleName.equalsIgnoreCase("NaviBarBtn") && !eleName.equalsIgnoreCase("clearBtn") && !eleName.equalsIgnoreCase("saveBtn") && !eleName.equalsIgnoreCase("cancelBtn") && !eleName.equalsIgnoreCase("PageTitle")) {
                if (!eleName.toLowerCase().contains("_record")) { // for title
                    int index4title = -1;
                    try {
                        index4title = titleLocation.get(eleName);
                        if (index4title != -1) {
                            for (int ind = 0; ind < index4title; ind++) {
                                moveUPs(driver);
                                btnOperationRoute(driver, "PageTitle");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("This element don't need pre-swipe");
                    }
                    if (commonLocatorStr != null) {  //
                        if ((commonType.equals("xpath")) && (tempType.equals("xpath") && tempLocatorStr.contains(":REXindex:"))) {
                            result = super.btnOperationSearch(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                            if (result.split("::").length > 1) {
                                index4XPATH = Integer.parseInt(result.split("::")[1]);
                                result = new String(result.split("::")[0]);
                            } else if (result.endsWith("::")) {
                                result = new String(result.split("::")[0]);
                            } else {
                            }
                        } else if ((commonType.equals("classname")) && (tempType.equals("xpath") && tempLocatorStr.contains(":REXindex:"))) {
                            if (index4title != -1 && !eleName.equalsIgnoreCase("military")) {
                                int startIndex = 7;
                                if (cellIndex.get(eleName) != null) {
                                    startIndex = cellIndex.get(eleName);
                                }
                                result = super.btn4XPATHCombind(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, startIndex);
                            } else if (eleName.equalsIgnoreCase("military")) {
                                result = super.btn4ClassNameCombind(driver, "commonby", commonType, commonLocatorStr, this.byMap, this.eleContentMap, this.content.eleTextContentMap, eleText);
                            } else {
                                int tempID4ClassName = 0;
                                combind_btnType = new String(this.eleContentMap.get(eleName+"Btn").getType());
                                combind_btnLocatorStr = new String(this.eleContentMap.get(eleName+"Btn").getLocatorStr());
                                tempID4ClassName = super.btnId4ClassNameIsolate(driver, "commonby", commonType, commonLocatorStr, this.byMap, this.eleContentMap, this.content.eleTextContentMap, eleText);
                                result = super.isolatedBtnSearchWithIndex(driver, eleName, commonType, commonLocatorStr, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH, this.content.eleTextContentMap, eleText, tempID4ClassName, combind_btnType, combind_btnLocatorStr);
                                if (result.split("::").length > 1) {
                                    index4XPATH = Integer.parseInt(result.split("::")[1]);
                                    result = new String(result.split("::")[0]);
                                } else if (result.endsWith("::")) {
                                    result = new String(result.split("::")[0]);
                                } else {
                                }
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
                    String name4title = eleName.split("_")[0];
                    String titleTempLocatorStr = null;
                    String titleTempType = null;
                    String titleText = null;
                    for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                        Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                        ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                        if (name4title.equals(tempEle.getElementName())) {
                            titleTempLocatorStr = tempEle.getLocatorStr();
                            titleTempType = tempEle.getType();
                            titleText = ((ElementMapping) tempContentEntry.getValue()).getTextContent();
                            break;
                        }
                    }
                    if (commonLocatorStr != null && titleTempLocatorStr != null & titleTempType != null) {  //
                        if ((commonType.equals("xpath")) && (titleTempType.equals("xpath") && titleTempLocatorStr.contains(":REXindex:"))) {
                            index4XPATH = getIndex4TitleIndex(driver, name4title, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);

                        } else if ((commonType.equals("classname")) && (titleTempType.equals("xpath") && titleTempLocatorStr.contains(":REXindex:"))) {
                            index4XPATH = super.btnId4ClassNameIsolate(driver, "commonby", commonType, commonLocatorStr, this.byMap, this.eleContentMap, this.content.eleTextContentMap, titleText);
                        }
                    }
                    if (recordLocatorStr != null && recordType != null && tempLocatorStr != null && tempType != null) {
                        if ((recordType.equals("xpath")) && ((recordLocatorStr.contains(":REXindex:")) || (recordLocatorStr.contains(":REXrecord:")))) {
                            result = super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                        } else if ((recordType.equals("classname")) && ((tempType.contains(":REXindex:")) || (tempLocatorStr.contains(":REXrecord:")))) {
                            result = isolatedRecordSearchWithLocation(driver, eleName, recordType, recordLocatorStr, this.byMap, this.eleContentMap, this.path4Log, index4XPATH, this.content.eleTextContentMap, titleText, index4XPATH, commonType, commonLocatorStr);
                            if (result.split("::").length > 1) {
                                index4XPATH = Integer.parseInt(result.split("::")[1]);
                                result = new String(result.split("::")[0]);
                            } else if (result.endsWith("::")) {
                                result = new String(result.split("::")[0]);
                            } else {
                            }
                        } else {
                            throw new XMLException("element name _record need XML content support");
                        }
                    } else {
                        throw new XMLException("the locator str don't match");
                    }
                } else {
                    result = super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                }

            } else {
                return super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }


    public String getElementContent(IOSDriver driver, String eleName) throws Exception { // elename may contains common 4 title, record 4 record under title
        String eleType = null;
        String eleText = null;
        String eleStr = null;
        ElementMapping tempEleMap = this.eleContentMap.get(eleName);
        if (tempEleMap != null) {
            eleType = tempEleMap.getType();
            eleStr = tempEleMap.getLocatorStr();
        } else {

            for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext() && eleType == null; ) {
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    eleType = ((ElementMapping) tempContentEntry.getValue()).getType();
                    eleStr = ((ElementMapping) tempContentEntry.getValue()).getLocatorStr();
                }
            }
        }
        if (!eleName.toLowerCase().contains("language") && !eleName.toLowerCase().contains("skill") && !eleName.toLowerCase().contains("security")) {
            System.out.println("    +++ ~~~ The getElementContent in IOSPageProfileEdit has been called ~~~ +++");
            return super.getElementContent(driver, eleName, eleType, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
        } else {
            System.out.println("    +++ ~~~ The getElementContent in IPADPageProfileEdit has been called ~~~ +++");
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
                            result = super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
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
                        } else if ((commonType.equals("classname")) && (titleTempType.equals("xpath") && titleTempLocatorStr.contains(":REXindex:"))) {
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
                                // result = super.getStaticElementContent(driver, tLocatorStr, this.byMap, this.eleContentMap, this.path4Log, index4XPATH);
                                result = super.getStaticElementContent(driver, tLocatorStr, this.byMap, this.eleContentMap, this.path4Log, 5);
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
                        } else if ((commonType.equals("classname")) && (titleTempType.equals("classname") && titleTempLocatorStr.contains(":REXindex:"))) {
                            index4XPATH = getIndex4TitleIndex(driver, name4title, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                        }
                    }
                    // find the index of title directly
                    if (recordLocatorStr != null && recordType != null && tempLocatorStr != null && tempType != null) {
                        if ((recordType.equals("xpath")) && ((recordLocatorStr.contains(":REXindex:")) || (recordLocatorStr.contains(":REXrecord:")))) {
                            result = super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                        } else if ((recordType.equals("classname")) && ((tempType.contains(":REXindex:")) || (tempLocatorStr.contains(":REXrecord:")))) {
                            result = super.recordSearch(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, index4XPATH, tempType, tempLocatorStr, recordType, recordLocatorStr, this.content.eleWaitStepMap);
                        } else {
                            throw new XMLException("element name _record need XML content support");
                        }
                    } else {
                        throw new XMLException("the locator str don't match");
                    }
                } else {
                    result = super.getElementContent(driver, eleName, eleType, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
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
        long startTime = System.currentTimeMillis();
        HashMap result = new HashMap<String, By>();
        result.clear();
        By tempElement = null;
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IPADPageProfileEdit. getByMap (IOSDriver driver)  : " + driver.toString());

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
        System.out.println("^^^^^^   getByMap ：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
        return result;
    }

    public HashMap<String, By> getByMap(IOSDriver driver, HashMap<String, ElementMapping> eleMap) {
        HashMap result = new HashMap<String, By>();
        result.clear();
        By tempElement = null;
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IPADPageProfileEdit. getByMap (IOSDriver driver, HashMap<String, ElementMapping> eleMap)  : " + driver.toString());

        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
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
        System.out.println("    +++ ~~~ The btnOperation in IPADPageProfileEdit has been called ~~~ +++");
        super.btnOperation(driver, eleName, this.byMap);
    }

    public void textOperation(IOSDriver driver, String eleName, String para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperation in IPADPageProfileEdit has been called ~~~ +++");
        super.textOperation(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
    }


    public String textOperationWithSaveInput(IOSDriver driver, String eleName, String para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperationWithSaveInput in IPADPageProfileEdit has been called ~~~ +++");
        return super.textOperationWithSaveInput(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
    }

    public void moveUP(IOSDriver dr) throws Exception {
        System.out.println(" moveUP in IPADPageProfileEdit");
        super.moveUpFitScreen(dr); //TODO for every page which need roll page
    }


    public void moveDOWN(IOSDriver dr) throws Exception {
        System.out.println(" moveDOWN in IPADPageProfileEdit");
        super.moveDOWNtest(dr);
    }

    public void moveDOWNall(IOSDriver dr) throws Exception {
        System.out.println(" moveDOWNall in IPADPageProfileEdit");
        super.moveDOWNall(dr);
    }

    public void moveUPall(IOSDriver dr) throws Exception {
        System.out.println(" moveUPall in IPADPageProfileEdit");
        super.moveUPall(dr);
    }


    public String dropDownListOperation(IOSDriver driver, String eleName) throws Exception {  //TODO
        String defaultText = null;
        String dropItemValue = null;
        System.out.println("    +++ ~~~ The dropDownListOperation in bANDPageProfileEditEducation has been called ~~~ +++");
        defaultText = super.dropDownListShow(driver, eleName + "View", this.byMap, this.eleContentMap, this.path4Log, this.content.eleDefaultValueMap);
        dropItemValue = pressItemString(driver, "", eleName);
        super.btnOperationStatic(driver, "pickerDoneBtn", this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
        return dropItemValue;
    }

    public String dropDownListOperation(IOSDriver driver, String eleName, String extPara) throws Exception {  //TODO
        String defaultText = null;
        String dropItemValue = null;
        System.out.println("    +++ ~~~ The dropDownListOperation in bANDPageProfileEditEducation has been called ~~~ +++");
        System.out.println(" this time we want to choose the " + extPara + " item ");
        defaultText = super.dropDownListShow(driver, eleName + "View", this.byMap, this.eleContentMap, this.path4Log, this.content.eleDefaultValueMap);
        dropItemValue = pressItemString(driver, extPara, eleName);
        super.btnOperationStatic(driver, "pickerDoneBtn", this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
        return dropItemValue;
    }


    public String pressItemString(IOSDriver theD, String theDay, String eleName) throws Exception {  // type4check : ready, loading, error, temp , etc.  , para4check: target element name, all element, title element
        String targetDay = theDay;
        int roundSco = 5; // 5 in the windows
        boolean flag = false;
        WebElement theItem = null;
        boolean rollUP = false;
        String result = "";
        for (Iterator it = this.byMap.entrySet().iterator(); it.hasNext(); ) {
            final Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName + "_picker")) {
                System.out.println(eleName + "_picker" + " ::");
                IOSDriverWait xWait = new IOSDriverWait(theD, timeOutInSeconds, this.path4Log);
                theItem = theD.findElement((By) tempContentEntry.getValue());
                break;
            }
        }
        if (theItem == null) {
            throw new REXException("failed to find the " + eleName + "_picker");
        } else {
            String currentDayStr = theItem.getText();
            if (currentDayStr.equals(targetDay)) {
                flag = true;
            } else {
                rollUP = true;
                result = super.pressTheRound(theD, (IOSElement) theItem, targetDay, roundSco, rollUP);      // true for up
            }
            System.out.println("Current value in roller is : " + result);
        }

        if (!result.equals("error") && !result.equals("emptyContent")) { //TODO
            flag = true;
        }
        return result;
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


}


