package farfetch.panda.beta.ios.AppPage.IOS;

import REXSH.REXAUTO.Component.Page.Element.ElementMapping;
import REXSH.REXAUTO.Component.Page.IOSPageContent;
import REXSH.REXAUTO.Component.PageLoadWait.IOSDriverWait;
import REXSH.REXAUTO.LocalException.REXException;
import Utility.Log;
import Utility.dateHandle;
import Utility.readProperity;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static REXSH.REXAUTO.Component.Driver.ScreenShot.ScreenShot;


/**
 * Created by appledev131 on 4/11/16.
 */
public class IOSPageWithDrawInterest extends IOSPageTemplate {
    protected String fileName = "WithDrawInterest.json";
    String autoOSType = readProperity.getProValue("autoRunningOSType");
    private String contentFilePath = "\\PageXML\\IOS\\RoleFilter\\";
    public String path4Log = System.getProperty("user.dir");
    protected String comFileName = null;
    protected IOSPageContent content = null;
    protected HashMap<String, ElementMapping> eleContentMap = null;
    protected HashMap<String, IOSElement> eleMap = null;
    public HashMap<String, By> byMap = null;
    protected IOSDriver dr = null;
    protected int osType = 2;
    protected HashMap<String, String> nextPageList = null;
    private String dateWin = "DateWin";
    private String errNoticeWind = "";
    private String confirmNoticeWind = "";
    private String completeNoticeWind = "";
    private String confirmNoticeWindWOT = "";
    private String storeValue4compare = "";
    private boolean pageMoveableCheckDisable = true;        // flag for check the page is moveable or not during test
    private boolean moveable = true;                        // flag for the page is moveable or not
    private static String logSpace4thisPage = " --- IOSPageWithDrawInterest --";
    String tomorrow = readProperity.getProValue("tomorrow");

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

    public IOSPageWithDrawInterest(IOSDriver theD) throws Exception {
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

    public IOSPageWithDrawInterest(IOSDriver theD, String p4L) throws Exception {
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
        this.content.setFlag4MoveCheck(this.pageMoveableCheckDisable);
        this.content.setMoveable(this.moveable);
    }


    public IOSPageWithDrawInterest(String theFile, IOSDriver theD, Log theLogger) throws Exception {
        super(theFile, theD, theLogger);
        if (!autoOSType.toLowerCase().contains("windows")) {
            contentFilePath = new String(contentFilePath.replaceAll("\\\\", File.separator));
            path4Log = new String(path4Log.replaceAll("\\\\", File.separator));
        }
        this.comFileName = contentFilePath + fileName;
        this.dr = theD;
    }


    public HashMap<String, By> getByMap(IOSDriver driver) {
        HashMap result = new HashMap<String, By>();
        result.clear();
        By tempElement = null;
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IOSPageWithDrawInterest . getByMap(IOSDriver driver)   : " + driver.toString());

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

    public HashMap<String, By> getByMap(IOSDriver driver, HashMap<String, ElementMapping> eleMap) {
        HashMap result = new HashMap<String, By>();
        result.clear();
        By tempElement = null;
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IOSPageWithDrawInterest . getByMap (IOSDriver driver, HashMap<String, ElementMapping> eleMap)  : " + driver.toString());

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

    public String btnOperationRoute(IOSDriver driver, String eleName) throws Exception {
        System.out.println("    +++ ~~~ The btnOperationRoute in IOSPageWithDrawInterest has been called ~~~ +++");
        boolean movecheckdisable = this.content.getFlag4MoveCheck();
        boolean moveable = this.content.getMoveable();
        boolean localMoveable = false;
        if (!eleName.equalsIgnoreCase("NaviBarBtn")) {
            if (movecheckdisable == false) {
                localMoveable = checkMoveable(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                this.content.setMoveable(localMoveable);
                this.content.setFlag4MoveCheck(true);
                if (localMoveable == true) {
                    return super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
                } else {
                    return super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
                }
            } else {
                if (moveable == false) {
                    return super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
                } else {
                    return super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
                }
            }

        } else {
            return super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
        }
    }

    public HashMap<String, ElementMapping> getEleConMap() {
        return this.eleContentMap;
    }

    public String dropDownListOperation(IOSDriver driver, String eleName) throws Exception {  //TODO
        String defaultText = null;
        String dropItemValue = null;
        System.out.println("    +++ ~~~ The dropDownListOperation in IOSPageWithDrawInterest has been called ~~~ +++");
        defaultText = super.dropDownListShow(driver, eleName + "View", this.byMap, this.eleContentMap, this.path4Log, this.content.eleDefaultValueMap);
        dropItemValue = pressItemString(driver, "", eleName);
        super.btnOperationStatic(driver, "pickerDoneBtn", this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
        return dropItemValue;
    }

    public String dropDownListOperation(IOSDriver driver, String eleName, String extPara) throws Exception {  //TODO
        String defaultText = null;
        String dropItemValue = null;
        System.out.println("    +++ ~~~ The dropDownListOperation in IOSPageWithDrawInterest has been called ~~~ +++");
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

    public void btnOperation(IOSDriver driver, String eleName) throws Exception {
        System.out.println("    +++ ~~~ The btnOperation in IOSPageWithDrawInterest has been called ~~~ +++");
        boolean movecheckdisable = this.content.getFlag4MoveCheck();
        boolean moveable = this.content.getMoveable();
        boolean localMoveable = false;
        String eleType = null;
        String eleText = null;
        for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext() && eleType == null; ) {
            final Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                eleType = ((ElementMapping) tempContentEntry.getValue()).getType();
                eleText = ((ElementMapping) tempContentEntry.getValue()).getTextContent();
            }
        }
        if (!eleName.equalsIgnoreCase("NaviBarBtn")) {
            if (movecheckdisable == false) {
                localMoveable = checkMoveable(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                this.content.setMoveable(localMoveable);
                this.content.setFlag4MoveCheck(true);
                if (localMoveable == true) {
                    //  super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
                    if (eleType.equalsIgnoreCase("xpath")) {
                        super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
                    } else if (eleType.equalsIgnoreCase("classname")) {
                        super.btnOperationSmart(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, eleText, this.path4Log);
                    }
                } else {
                    //    super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
                    if (eleType.equalsIgnoreCase("xpath")) {
                        super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
                    } else if (eleType.equalsIgnoreCase("classname")) {
                        super.btnOperationSmart(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, eleText, this.path4Log);
                    }
                }
            } else {
                if (moveable == false) {
                    // super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
                    if (eleType.equalsIgnoreCase("xpath")) {
                        super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
                    } else if (eleType.equalsIgnoreCase("classname")) {
                        super.btnOperationSmart(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, eleText, this.path4Log);  // TODO , this fork should not appear
                    }
                } else {
                    //    super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
                    if (eleType.equalsIgnoreCase("xpath")) {
                        super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
                    } else if (eleType.equalsIgnoreCase("classname")) {
                        super.btnOperationSmart(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, eleText, this.path4Log);
                    }
                }
            }

        } else {
            super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
        }
    }

    public void testOperation(IOSDriver driver, String eleName) throws Exception {
        System.out.println("    +++ ~~~ The btnOperation in IOSPageWithDrawInterest has been called ~~~ +++");
        super.testOperation(driver, eleName);
    }

    public String getElementContent(IOSDriver driver, String eleName) throws Exception {
        System.out.println("    +++ ~~~ The getElementContent in IOSPageWithDrawInterest has been called ~~~ +++");
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

    public String textOperationWithSaveInput(IOSDriver driver, String eleName, String para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperationWithSaveInputn in IOSPageWithDrawInterest has been called ~~~ +++");
        //super.textOperation(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
        boolean movecheckdisable = this.content.getFlag4MoveCheck();
        boolean moveable = this.content.getMoveable();
        boolean localMoveable = false;
        String resultText = null;
        String eleType = null;
        for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext() && eleType == null; ) { //
            final Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                eleType = ((ElementMapping) tempContentEntry.getValue()).getType();
            }
        }
        if (!eleName.equalsIgnoreCase("PageTitle")) {
            if (movecheckdisable == false) {
                localMoveable = checkMoveable(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                this.content.setMoveable(localMoveable);
                this.content.setFlag4MoveCheck(true);
                if (localMoveable == true) {
                    resultText = super.textOperationWithSaveInput(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
                } else {
                    resultText = super.textOperationStaticWithValue(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
                }
            } else {
                if (moveable == false) {
                    if (eleType.equalsIgnoreCase("xpath")) {
                        resultText = super.textOperationStaticWithValue(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
                    } else if (eleType.equalsIgnoreCase("classname")) {
                        resultText = super.textOperationSmartSaveInput(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
                    }
                } else {
                    if (eleType.equalsIgnoreCase("xpath")) {
                        resultText = super.textOperationWithSaveInput(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
                    } else if (eleType.equalsIgnoreCase("classname")) {
                        resultText = super.textOperationSmartSaveInput(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
                    }
                }
            }

        } else {
            resultText = super.textOperationStaticWithValue(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
        }
        return resultText;
    }

    public void textOperation(IOSDriver driver, String eleName, String para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperationn in IPADPageLogin has been called ~~~ +++");
        //super.textOperation(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
        boolean movecheckdisable = this.content.getFlag4MoveCheck();
        boolean moveable = this.content.getMoveable();
        boolean localMoveable = false;
        String eleType = null;
        String eleText = null;
        for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext() && eleType == null; ) {
            final Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                eleType = ((ElementMapping) tempContentEntry.getValue()).getType();
                eleText = ((ElementMapping) tempContentEntry.getValue()).getTextContent();
            }
        }

        if (!eleName.equalsIgnoreCase("PageTitle")) {
            if (movecheckdisable == false) {
                localMoveable = checkMoveable(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                this.content.setMoveable(localMoveable);
                this.content.setFlag4MoveCheck(true);
                if (localMoveable == true) {
                    if (eleType.equalsIgnoreCase("xpath")) {
                        super.textOperation(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
                    } else if (eleType.equalsIgnoreCase("classname")) {
                        super.textOperationSmart(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, eleText);
                    }
                } else {
                    if (eleType.equalsIgnoreCase("xpath")) {
                        super.textOperationStatic(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
                    } else if (eleType.equalsIgnoreCase("classname")) {
                        super.textOperationSmart(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, eleText);
                    }
                }
            } else {
                if (moveable == false) {
                    if (eleType.equalsIgnoreCase("xpath")) {
                        super.textOperationStatic(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
                    } else if (eleType.equalsIgnoreCase("classname")) {
                        super.textOperationSmart(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, eleText);  // TODO , this fork should not appear
                    }
                } else {
                    if (eleType.equalsIgnoreCase("xpath")) {
                        super.textOperation(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
                    } else if (eleType.equalsIgnoreCase("classname")) {
                        super.textOperationSmart(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, eleText);
                    }
                }
            }
        } else {
            super.textOperationStatic(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
        }
    }

    public String calendarOperationTomorrow(IOSDriver driver, String eleName) throws Exception {
        String result = null;
        String calendarValue = null;
        System.out.println("    +++ ~~~ The calendarOperationTomorrow in IOSPageProfileEditEducation has been called ~~~ +++");
        result = super.calendarOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
        int lastDayInt = dateHandle.getLastDayOfMonth(new Date(), "America/Chicago");
        Class tempClassType = Class.forName(String.valueOf(new StringBuffer("REXSH.REXAUTO.APPunderTest.AppPage.IOS.").append("IOSPage").append(dateWin)));
        System.out.println("@@@ tempClassType : " + tempClassType);
        Object tempIns = super.createInstance(tempClassType, driver);
        Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{IOSDriver.class, String.class, String.class});
        Object comPageCheckResult = setMethod.invoke(tempIns, driver, "ready", "overview");
        if (((String) comPageCheckResult).equals("pass")) {
            Method chooseMethod = tempClassType.getDeclaredMethod("pressDateItem", new Class[]{IOSDriver.class, String.class});
            TimeZone tz = TimeZone.getTimeZone("America/Chicago");
            Calendar cl = Calendar.getInstance(tz, Locale.US);
            String defaultDay = String.valueOf(cl.get(Calendar.DAY_OF_MONTH));
            if (Integer.parseInt(defaultDay) != lastDayInt) {
                String tDayStr = this.tomorrow;
                Object selectDateResult = chooseMethod.invoke(tempIns, driver, tDayStr); // chooseDateItem
                if (selectDateResult.toString().equalsIgnoreCase("true")) {
                    Method saveMethod = tempClassType.getDeclaredMethod("saveDate", new Class[]{IOSDriver.class, String.class, String.class});
                    Object saveDateResult = saveMethod.invoke(tempIns, driver, tDayStr, this.path4Log);
                    if (saveDateResult.toString() != "" && saveDateResult != null) {
                        result = saveDateResult.toString();
                        calendarValue = super.calendarGetTextOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                        System.out.println(" ~~~~~~ Here is the date in calendar after save : " + calendarValue.toString());
                    }
                } else {
                    throw new REXException("The Picker value is wrong : " + selectDateResult);
                }
            } else {
                cl.add(Calendar.MONTH, +1);
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.ENGLISH);
                String lastMonthName = sdf.format(cl.getTime());
                Method monthMethod = tempClassType.getDeclaredMethod("pressMonthItem", new Class[]{IOSDriver.class, String.class}); //  btnOperationRoute(AndroidDriver driver, String eleName)
                Object selectMonthResult = monthMethod.invoke(tempIns, driver, lastMonthName);
                //  throw new Exception("code for handling 1st day of the month are not ready");

                if (selectMonthResult.toString().equalsIgnoreCase("true")) {
                    String tDayStr = this.tomorrow;
                    Object selectDateResult = chooseMethod.invoke(tempIns, driver, tDayStr); // chooseDateItem
                    Method saveMethod = tempClassType.getDeclaredMethod("saveDate", new Class[]{IOSDriver.class, String.class, String.class});
                    Object saveDateResult = saveMethod.invoke(tempIns, driver, tDayStr, this.path4Log);
                    if (saveDateResult.toString() != "" && saveDateResult != null) {
                        result = saveDateResult.toString();
                        calendarValue = super.calendarGetTextOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                        System.out.println(" ~~~~~~ Here is the date in calendar after save : " + calendarValue.toString());
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


    public String calendarOperationTomorrow(IOSDriver driver, String eleName, String tDate) throws Exception {
        String result = null;
        String calendarValue = null;
        String targetDate = tDate;
        int daylastmonth = dateHandle.getLastDayOfLastMonth(new Date(), "America/Chicago");
        System.out.println("    +++ ~~~ The btnOperationRoute in IOSPageProfileEditEducation has been called ~~~ +++");
        result = super.calendarOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);

        Class tempClassType = Class.forName(String.valueOf(new StringBuffer("REXSH.REXAUTO.APPunderTest.AppPage.IOS.").append("IOSPage").append(dateWin)));
        System.out.println("@@@ tempClassType : " + tempClassType);
        Object tempIns = super.createInstance(tempClassType, driver);
        Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{IOSDriver.class, String.class, String.class});
        Object comPageCheckResult = setMethod.invoke(tempIns, driver, "ready", "overview");
        if (((String) comPageCheckResult).equals("pass")) {
            Method chooseMethod = tempClassType.getDeclaredMethod("pressDateItem", new Class[]{IOSDriver.class, String.class});
            TimeZone tz = TimeZone.getTimeZone("America/Chicago");
            Calendar cl = Calendar.getInstance(tz, Locale.US);
            String defaultDay = String.valueOf(cl.get(Calendar.DAY_OF_MONTH));

            if (Integer.parseInt(defaultDay) - 1 >= Integer.parseInt(targetDate)) {
                int yesterdayInt = Integer.parseInt(defaultDay) + 1;  //TODO, consider the 1st day og the month
                if (Integer.parseInt(targetDate) <= yesterdayInt) {
                    Object selectDateResult = chooseMethod.invoke(tempIns, driver, targetDate); // chooseDateItem
                    if (selectDateResult.toString().equalsIgnoreCase("true")) {
                        Method saveMethod = tempClassType.getDeclaredMethod("saveDate", new Class[]{IOSDriver.class, String.class, String.class});
                        Object saveDateResult = saveMethod.invoke(tempIns, driver, targetDate, this.path4Log);
                        if (saveDateResult.toString() != "" && saveDateResult != null) {
                            result = saveDateResult.toString();
                            calendarValue = super.calendarGetTextOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
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
                        Object saveDateResult = saveMethod.invoke(tempIns, driver, targetDate, this.path4Log);
                        if (saveDateResult.toString() != "" && saveDateResult != null) {
                            result = saveDateResult.toString();
                            calendarValue = super.calendarGetTextOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
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

            IOSPageWithDrawInterest x = new IOSPageWithDrawInterest("/PageXML/AND/loginPage.xml", driver, null);
            System.out.println("before defaulf operation");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * Created by appledev131 on 4/11/16.
     */
    public static class IOSPageAddExpense extends IOSPageTemplate {
        protected String fileName = "AddExpense.xml";
        String autoOSType = readProperity.getProValue("autoRunningOSType");
        private String contentFilePath = "\\PageXML\\IOS\\ProjectTimeAndExpense\\";
        public String path4Log = System.getProperty("user.dir");
        protected String comFileName = null;
        protected IOSPageContent content = null;
        protected HashMap<String, ElementMapping> eleContentMap = null;
        protected HashMap<String, IOSElement> eleMap = null;
        public HashMap<String, By> byMap = null;
        protected IOSDriver dr = null;
        protected int osType = 2;
        protected HashMap<String, String> nextPageList = null;
        private String errNoticeWind = "";
        private String confirmNoticeWind = "";
        private String completeNoticeWind = "";
        private String confirmNoticeWindWOT = "";
        private String storeValue4compare = "";
        private boolean pageMoveableCheckDisable = true;    // flag for check the page is moveable or not during test
        private boolean moveable = false;                   // flag for the page is moveable or not
        private static String logSpace4thisPage = " --- IOSPageAddExpense --";

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

        public IOSPageAddExpense(IOSDriver theD) throws Exception {
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

        public IOSPageAddExpense(IOSDriver theD, String p4L) throws Exception {
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
            this.path4Log = p4L;
            this.content.setFlag4MoveCheck(this.pageMoveableCheckDisable);
            this.content.setMoveable(this.moveable);
        }


        public IOSPageAddExpense(String theFile, IOSDriver theD, Log theLogger) throws Exception {
            super(theFile, theD, theLogger);
            if (!autoOSType.toLowerCase().contains("windows")) {
                contentFilePath = new String(contentFilePath.replaceAll("\\\\", File.separator));
                path4Log = new String(path4Log.replaceAll("\\\\", File.separator));
            }
            this.comFileName = contentFilePath + fileName;
            this.dr = theD;
        }

        public void test4test() {
            System.out.println("just4test");

        }

        public void moveUP(IOSDriver dr) throws Exception {
            System.out.println(" moveUP in bANDPageProfileEdit ");
            super.moveUPtest(dr);
        }


        public void moveDown(IOSDriver dr) throws Exception {
            System.out.println(" moveDown in bANDPageProfileEdit ");
            super.moveDOWNtest(dr);
        }

        public String getElementContent(IOSDriver driver, String eleName) throws Exception {
            String result = null;
            String temp = null;
            System.out.println("    +++ ~~~ The getElementContent in IOSPageAddExpense has been called ~~~ +++");
            if (eleName.contains("totalAmount")) {
                String tempResult = super.getElementContent(driver, eleName, this.byMap, this.eleContentMap);
                tempResult = new String(tempResult.replaceFirst("Total", ""));
                tempResult = new String(tempResult.replaceAll(" ", ""));
                result = finicalCheckDeleteZeroAndSymbol(tempResult);
            } else if (eleName.contains("entryAmount")) {
                String tempResult = super.getElementContent(driver, eleName, this.byMap, this.eleContentMap);
                result = finicalCheckDeleteZeroAndSymbol(tempResult);
            } else {
                temp = super.getElementContent(driver, eleName, this.byMap, this.eleContentMap);
                result = temp;
            }
            return result;
        }

        public HashMap<String, By> getByMap(IOSDriver driver) {
            HashMap result = new HashMap<String, By>();
            result.clear();
            By tempElement = null;
            System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IOSPageAddExpense . getByMap(IOSDriver driver)   : " + driver.toString());

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

        public HashMap<String, By> getByMap(IOSDriver driver, HashMap<String, ElementMapping> eleMap) {
            HashMap result = new HashMap<String, By>();
            result.clear();
            By tempElement = null;
            System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IOSPageAddExpense . getByMap (IOSDriver driver, HashMap<String, ElementMapping> eleMap)  : " + driver.toString());

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

        public String btnOperationRoute(IOSDriver driver, String eleName) throws Exception {
            System.out.println("    +++ ~~~ The btnOperationRoute in IOSPageAddExpense has been called ~~~ +++");
            boolean movecheckdisable = this.content.getFlag4MoveCheck();
            boolean moveable = this.content.getMoveable();
            boolean localMoveable = false;
            if (!eleName.equalsIgnoreCase("PageTitle")) {
                if (movecheckdisable == false) {
                    localMoveable = checkMoveable(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                    this.content.setMoveable(localMoveable);
                    this.content.setFlag4MoveCheck(true);
                    if (localMoveable == true) {
                        return super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
                    } else {
                        return super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
                    }
                } else {
                    if (moveable == false) {
                        return super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
                    } else {
                        return super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
                    }
                }

            } else {
                return super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
            }

            //   return super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, this.content.eleTextContentMap);
            // return super.btnOperationDync(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, this.content.eleTextContentMap, this.content.getFlag4MoveCheck(), this.content.getMoveable());
        }

        public HashMap<String, ElementMapping> getEleConMap() {
            return this.eleContentMap;
        }

        private static String finicalCheckDeleteZeroAndSymbol(String extPara) {
            String result = null;
            String temp = null;
            if (extPara.startsWith("$")) {
                temp = new String(extPara.substring(1, extPara.length()));
            } else {
                temp = extPara;
            }
            System.out.println(temp);
            Pattern pattern2dig = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){2})?$");
            Pattern pattern1dig = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1})?$");
            Pattern patternEntry = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))?$");
            Pattern patternDot = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.)?$");
            Matcher matcher2dig = pattern2dig.matcher(temp);
            Matcher matcher1dig = pattern1dig.matcher(temp);
            Matcher matcherEntry = patternEntry.matcher(temp);
            Matcher matcherEntryDot = patternDot.matcher(temp);
            if (matcherEntry.matches()) {
                result = temp;
            } else if (matcherEntryDot.matches()) {
                result = new String(temp.substring(0, temp.length() - 1));
            } else if (matcher2dig.matches()) {
                if (temp.endsWith(".00")) {
                    //  result = new String (temp.replace(".00",""));
                    result = new String(temp.substring(0, temp.length() - 3));
                } else if (temp.endsWith("0")) {
                    result = new String(temp.substring(0, temp.length() - 1));
                } else {
                    result = temp;
                }
            } else if (matcher1dig.matches()) {
                if (temp.endsWith("0")) {
                    result = new String(temp.substring(0, temp.length() - 2));
                } else {
                    result = temp;
                }
            }
            return result;
        }

        public void btnOperation(IOSDriver driver, String eleName) throws Exception {
            System.out.println("    +++ ~~~ The btnOperation in IOSPageAddExpense has been called ~~~ +++");
            boolean movecheckdisable = this.content.getFlag4MoveCheck();
            boolean moveable = this.content.getMoveable();
            boolean localMoveable = false;
            if (!eleName.equalsIgnoreCase("PageTitle")) {
                if (movecheckdisable == false) {
                    localMoveable = checkMoveable(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                    this.content.setMoveable(localMoveable);
                    this.content.setFlag4MoveCheck(true);
                    if (localMoveable == true) {
                        super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
                    } else {
                        super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
                    }
                } else {
                    if (moveable == false) {
                        super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
                    } else {
                        super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
                    }
                }

            } else {
                super.btnOperationStatic(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
            }
            //  super.btnOperationDync(driver, eleName, this.byMap, this.eleContentMap,this.content.eleTextContentMap, this.content.getFlag4MoveCheck(), this.content.getMoveable());
        }

        public String textOperationWithSaveInput(IOSDriver driver, String eleName, String para1) throws Exception {
            System.out.println("    +++ ~~~ The textOperationWithSaveInputn in IOSPageAddExpense has been called ~~~ +++");
            //super.textOperation(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
            boolean movecheckdisable = this.content.getFlag4MoveCheck();
            boolean moveable = this.content.getMoveable();
            boolean localMoveable = false;
            String resultText = null;
            String eleType = null;
            for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext() && eleType == null; ) { //
                final Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName)) {
                    eleType = ((ElementMapping) tempContentEntry.getValue()).getType();
                }
            }
            if (!eleName.equalsIgnoreCase("PageTitle") && !eleName.equalsIgnoreCase("code")) {
                if (movecheckdisable == false) {
                    localMoveable = checkMoveable(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                    this.content.setMoveable(localMoveable);
                    this.content.setFlag4MoveCheck(true);
                    if (localMoveable == true) {
                        resultText = super.textOperationWithSaveInput(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
                    } else {
                        resultText = super.textOperationStaticWithValue(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
                    }
                } else {
                    if (moveable == false) {
                        if (eleType.equalsIgnoreCase("xpath")) {
                            resultText = super.textOperationStaticWithValue(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
                        } else if (eleType.equalsIgnoreCase("classname")) {
                            resultText = super.textOperationSmartSaveInput(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
                        }
                    } else {
                        if (eleType.equalsIgnoreCase("xpath")) {
                            resultText = super.textOperationWithSaveInput(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
                        } else if (eleType.equalsIgnoreCase("classname")) {
                            resultText = super.textOperationSmartSaveInput(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
                        }
                    }
                }

            } else if (eleName.equalsIgnoreCase("code")) {
                resultText = super.textOperationSmartClickSaveInput(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
            } else {
                resultText = super.textOperationStaticWithValue(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
            }
            return resultText;
        }


        public String searchOperation(IOSDriver driver, String eleName, String extPara) throws Exception {  //TODO
            String defaultText = null;
            String selectItemValue = null;
            System.out.println("    +++ ~~~ The searchOperation in IOSPageAddExpense has been called ~~~ +++");
            System.out.println(" this time we want to choose the " + extPara + " item ");
            String tempDefaultValue = null;
            for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                if (eleName.equals(tempEle.getElementName())) {
                    tempDefaultValue = tempEle.getDefaultValue();
                    break;
                }
            }
            defaultText = super.searchStartMAC(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, tempDefaultValue, extPara);
            selectItemValue = super.selectItemMAC(driver, defaultText, this.byMap, this.eleContentMap, this.path4Log, extPara, new Boolean(false));
            Thread.sleep(1000);
            if (selectItemValue != null && selectItemValue != "") {
                super.btnOperation(driver, "doneBtn", this.byMap);
                return selectItemValue;
            } else {
                System.out.println("Error appear with searchOperation IN IOSPageAddExpense");
                throw new REXException("Error appear with searchOperation IN IOSPageAddExpense");
            }
        }


        public String autoCompleteOperation(IOSDriver driver, String eleName, String extPara) throws Exception {  //TODO
            String defaultText = null;
            String selectItemValue = null;
            System.out.println("    +++ ~~~ The autoCompleteOperation in IOSPageAddExpense has been called ~~~ +++");
            System.out.println(" this time we want to choose the " + extPara + " item ");
            String tempDefaultValue = null;
            for (Iterator it = this.content.getContentMap().entrySet().iterator(); it.hasNext(); ) {
                Map.Entry tempContentEntry = (Map.Entry) it.next();     //  get the next string , ele map instance
                ElementMapping tempEle = (ElementMapping) tempContentEntry.getValue();  // get the ele instance
                if (eleName.equals(tempEle.getElementName())) {
                    tempDefaultValue = tempEle.getDefaultValue();
                    break;
                }
            }
            defaultText = super.autoCompleteStartMAC(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, tempDefaultValue, extPara);
            selectItemValue = super.selectCompareItemMAC(driver, defaultText, this.byMap, this.eleContentMap, this.path4Log, extPara, new Boolean(false));
            Thread.sleep(1000);
            if (selectItemValue != null && selectItemValue != "") {
                super.btnOperation(driver, "doneBtn", this.byMap);
                return selectItemValue;
            } else {
                System.out.println("Error appear with autoCompleteOperation IN autoCompleteOperation");
                throw new REXException("Error appear with autoCompleteOperation IN IOSPageAddExpense");
            }
        }

        public boolean confirmOperation(IOSDriver driver) throws Exception {
            System.out.println("    +++ ~~~ The confirmOperation in IOSPageAddExpense has been called ~~~ +++");
            return super.confirmOperationStatic(driver, "loginBtn", this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
        }


        public void textOperation(IOSDriver driver, String eleName, String para1) throws Exception {
            System.out.println("    +++ ~~~ The textOperationn in IOSPageAddExpense has been called ~~~ +++");
            //super.textOperation(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
            boolean movecheckdisable = this.content.getFlag4MoveCheck();
            boolean moveable = this.content.getMoveable();
            boolean localMoveable = false;
            if (!eleName.equalsIgnoreCase("PageTitle")) {
                if (movecheckdisable == false) {
                    localMoveable = checkMoveable(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                    this.content.setMoveable(localMoveable);
                    this.content.setFlag4MoveCheck(true);
                    if (localMoveable == true) {
                        //super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
                        super.textOperation(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
                    } else {
                        super.textOperationStatic(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
                    }
                } else {
                    if (moveable == false) {
                        super.textOperationStatic(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
                    } else {
                        //super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.content.eleTextContentMap);
                        super.textOperation(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
                    }
                }

            } else {
                super.textOperationStatic(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap, this.path4Log);
            }
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

                IOSPageAddExpense x = new IOSPageAddExpense("/PageXML/AND/loginPage.xml", driver, null);
                System.out.println("before defaulf operation");


            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }

    }
}


