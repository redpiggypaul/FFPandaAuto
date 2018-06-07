package farfetch.panda.beta.ios.AppPage.IOS;

import REXSH.REXAUTO.Component.Page.IOSPageContent;
import REXSH.REXAUTO.Component.Page.Element.ElementMapping;
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
public class IOSPageRoleFilter extends IOSPageTemplate {
    protected String fileName = "RoleFilter.json";
    String autoOSType = readProperity.getProValue("autoRunningOSType");
    private String contentFilePath = "\\PageXML\\IOS\\";
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
    private String autoCompWin = "AutoComplete";
    // private String confirmNoticeWind = "ProfileEditClearWin";
    private String confirmNoticeWind = "";
    private String completeNoticeWind = "";
    private String confirmNoticeWindWOT = "";
    private String storeValue4compare = "";
    private static String logSpace4thisPage = " --- IPADPageRoleFilter --";


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

    public boolean getFlag4TitleCheck() {
        return this.content.getFlag4TitleCheck();
    }

    public void setFlag4TitleCheckTrue() {
        this.content.setFlag4TitleCheck(true);
    }

    public void setFlag4TitleCheckFalse() {
        this.content.setFlag4TitleCheck(false);
    }


    public IOSPageRoleFilter(IOSDriver theD) throws Exception {
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


    public IOSPageRoleFilter(IOSDriver theD, String p4L) throws Exception {
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


    public IOSPageRoleFilter(String theFile, IOSDriver theD, Log theLogger) throws Exception {
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
        System.out.println("    +++ ~~~ The btnOperationRoute in IPADPageRoleFilter has been called ~~~ +++");
        String result = null;
        try {
            String eleText = null;
            ElementMapping tempEleMap = this.eleContentMap.get(eleName);
            if (tempEleMap != null) {
                eleText = tempEleMap.getTextContent();
            } else {
                for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext(); ) {
                    final Map.Entry tempContentEntry = (Map.Entry) it.next();
                    if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                        eleText = ((ElementMapping) tempContentEntry.getValue()).getTextContent();
                        break;
                    }
                }
            }
            if (eleName.equalsIgnoreCase("PageTitle") || eleName.equalsIgnoreCase("ApplyBtn") || eleName.equalsIgnoreCase("CancelBtn") || eleName.equalsIgnoreCase("backBtn") || eleName.equalsIgnoreCase("clearBtn")
                    ||eleName.equalsIgnoreCase("StarRoleBtn")||eleName.equalsIgnoreCase("SharedRoleBtn")||eleName.equalsIgnoreCase("InterestRoleBtn")) {
                result = super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
            } else {
                result = super.btnOperationIsolateSmart(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, eleText, this.path4Log);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

    public String getElementContent(IOSDriver driver, String eleName) throws Exception { // elename may contains common 4 title, record 4 record under title
        if (!eleName.toLowerCase().contains("language") && !eleName.toLowerCase().contains("skill")) {
            System.out.println("    +++ ~~~ The getElementContent in IPADPageRoleFilter has been called ~~~ +++");
            return super.getElementContent(driver, eleName, this.byMap, this.eleContentMap);
        } else {
            System.out.println("    +++ ~~~ The getElementContent in IPADPageRoleFilter has been called ~~~ +++");
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
                    if (commonLocatorStr != null) {  //
                        if ((commonType.equals("xpath")) && (titleTempType.equals("xpath") && titleTempLocatorStr.contains(":REXindex:"))) {
                            index4XPATH = getIndex4TitleIndex(driver, name4title, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                        } else if ((commonType.equals("id")) && (titleTempType.equals("xpath") && titleTempLocatorStr.contains(":REXindex:"))) {
                            index4XPATH = getIndex4TitleIndex(driver, name4title, this.byMap, this.eleContentMap, this.path4Log, this.index4XPATH);
                        }
                    }
                    // find the index of title directly
                    if (recordLocatorStr != null) {
                        if ((recordType.equals("xpath")) && ((recordLocatorStr.contains(":REXindex:")) || (recordLocatorStr.contains(":REXrecord:")))) {
                            if (index4XPATH == 0) {
                                throw new REXException("failed to find the target title index");
                            } else {
                                //// TODO: 5/18/16
                                result = super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                            }
                        } else if ((recordType.equals("id")) && ((tempType.contains(":REXindex:")) || (tempLocatorStr.contains(":REXrecord:")))) {
                            if (index4XPATH == 0) {
                                throw new REXException("failed to find the target title index");
                            } else {
                                //// TODO: 5/18/16
                                //     result = super.recordSearch(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, index4XPATH, tempType, tempLocatorStr, recordType, recordLocatorStr, this.content.eleWaitStepMap);
                                result = super.recordSearch(driver, eleName, this.eleContentMap, this.path4Log, index4XPATH, tempType, tempLocatorStr, recordType, recordLocatorStr);
                            }
                        } else {
                            throw new XMLException("element name _record need XML content support");
                        }
                    } else {
                        throw new XMLException("the locator str don't match");
                    }
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
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IPADPageRoleFilter . getByMap (IOSDriver driver)  : " + driver.toString());

        for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext(); ) {
            try {
                Thread.sleep(50);

                Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (((ElementMapping) tempContentEntry.getValue()).getType().equalsIgnoreCase("xpath")) {
                    tempElement = By.xpath(((ElementMapping) tempContentEntry.getValue()).getLocatorStr());
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
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IPADPageRoleFilter . getByMap (IOSDriver driver, HashMap<String, ElementMapping> eleMap)  : " + driver.toString());

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
        System.out.println("    +++ ~~~ The btnOperation in IPADPageRoleFilter has been called ~~~ +++");
        super.btnOperation(driver, eleName, this.byMap);
    }

    public void textOperation(IOSDriver driver, String eleName, String para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperation in IPADPageRoleFilter has been called ~~~ +++");
        if (!eleName.equals("zipAndState")) {
            super.textOperation(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
        } else {
            By temp = null;
            temp = this.byMap.get(eleName);
            if (temp != null) {
                super.textListOperationexact(driver, temp, para1, this.byMap, this.content.eleDefaultValueMap, eleName);
            } else {
                super.textListOperation(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
            }
        }

    }

    public String autoCompleteOperation(IOSDriver driver, String eleName, String extPara) throws Exception {  //TODO
        String result = null;
        System.out.println("    +++ ~~~ The autoCompleteOperation in IPADPageRoleFilter has been called ~~~ +++");
        System.out.println(" this time we want to choose the " + extPara + " item ");
        try {
            result = super.autoCompleteOperationSuper(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, extPara, autoCompWin);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    public String textOperationWithSaveInput(IOSDriver driver, String eleName, String para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperationWithSaveInput in IPADPageRoleFilter has been called ~~~ +++");
        if (!eleName.equalsIgnoreCase("zipAndState")) {
            return super.textOperationWithSaveInput(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
        } else {
           // String temp = super.textListOperationWithSaveInput(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
            String temp = super.textListOperationWithSaveInput(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
            if (para1.contains(" ")) {
                String[] newP = para1.split(" ");
                String shortCut = "";
                for (int ind = 0; ind < newP.length; ind++) {
                    shortCut = new String(shortCut + newP[ind].substring(0, 1));
                }
                if(!shortCut.equalsIgnoreCase("")) {
                    temp = new String(temp + ", " + shortCut+"_REX_");
                }
            }
            return temp;
        }
    }

    public void moveUP(IOSDriver dr) throws Exception {
        System.out.println(" moveUP in IPADPageRoleFilter ");
        super.moveUpFitScreen(dr); //TODO for every page which need roll page
    }


    public void moveDOWN(IOSDriver dr) throws Exception {
        System.out.println(" moveDOWN in IPADPageRoleFilter ");
        super.moveDOWNtest(dr);
    }

    public void moveDOWNall(IOSDriver dr) throws Exception {
        System.out.println(" moveDOWNall in IPADPageRoleFilter ");
        super.moveDOWNall(dr);
    }

    public void moveUPall(IOSDriver dr) throws Exception {
        System.out.println(" moveUPall in IPADPageRoleFilter ");
        super.moveUPall(dr);
    }

    public String dropDownListOperation(IOSDriver driver, String eleName) throws Exception {  //TODO
        String defaultText = null;
        String dropItemValue = null;
        System.out.println("    +++ ~~~ The dropDownListOperation in IPADPageRoleFilter has been called ~~~ +++");
        defaultText = super.dropDownListShow(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, this.content.eleDefaultValueMap);
        dropItemValue = super.dropDownItemChoose(driver, defaultText, this.byMap, this.eleContentMap, this.path4Log);
        return dropItemValue;
    }

    public String dropDownListOperation(IOSDriver driver, String eleName, String extPara) throws Exception {  //TODO
        String defaultText = null;
        String dropItemValue = null;
        System.out.println("    +++ ~~~ The dropDownListOperation in IPADPageRoleFilter has been called ~~~ +++");
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

            IOSPageRoleFilter x = new IOSPageRoleFilter("/PageXML/AND/bPageProfileEdit.xml", driver, null);
            System.out.println("before defaulf operation");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

}


