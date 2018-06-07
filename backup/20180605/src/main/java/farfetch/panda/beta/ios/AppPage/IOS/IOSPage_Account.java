package farfetch.panda.beta.ios.AppPage.IOS;

import Component.Page.Element.ElementMapping;
import LocalException.FFPandaException;
import dataModel.TestPlatformData;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import utility.Log;
import utility.readProperity.SingleTonReadProperity;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by appledev131 on 4/11/16.
 */
public class IOSPage_Account extends IOSPageTemplate {
   // protected String fileName = "Account.json";
    protected StringBuilder fileName = new StringBuilder(this.getClass().getSimpleName());
    private StringBuilder autoOSType = TestPlatformData.getAutoOSType();
    private StringBuilder className= new StringBuilder(this.getClass().getSimpleName());
    private StringBuilder contentFilePath = new StringBuilder("\\PageXML\\IOS\\"+className+"\\");

    public String path4Log = System.getProperty("user.dir");
    protected String comFileName = null;
    protected IOSPageContent content = null;
    protected HashMap<String, ElementMapping> eleContentMap = null;
    protected HashMap<String, IOSElement> eleMap = null;
    public HashMap<String, By> byMap = null;
    protected IOSDriver dr = null;
    protected int osType = 2;
    private String errNoticeWind = "";
    private String confirmNoticeWind = "";
    private String completeNoticeWind = "";
    private String confirmNoticeWindWOT = "";
    private String storeValue4compare = "";
    private boolean pageMoveableCheckDisable = true;    // flag for check the page is moveable or not during test
    private boolean moveable = false;                   // flag for the page is moveable or not
    private static String logSpace4thisPage = " --- IOSPageAccount --";

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

    public IOSPage_Account(IOSDriver theD) throws Exception {
        super(theD);
        this.dr = theD;
        if (!autoOSType.toString().toLowerCase().contains("windows")) {
            contentFilePath = new String(contentFilePath.replaceAll("\\\\", File.separator));
            path4Log = new String(path4Log.replaceAll("\\\\", File.separator));
        }
        String tempName = contentFilePath + fileName;

        content = new IOSPageContent(theD, tempName, osType);
        eleContentMap = content.getContentMap(content.getContentMap4Base(tempName, osType));
        Thread.sleep(3000);
        byMap = getByMap(theD);
    }

    public IOSPage_Account(IOSDriver theD, String p4L) throws Exception {
        super(theD);
        this.dr = theD;
        if (!autoOSType.toString().toLowerCase().contains("windows")) {
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


    public IOSPage_Account(String theFile, IOSDriver theD, Log theLogger) throws Exception {
        super(theFile, theD, theLogger);
        if (!autoOSType.toString().toLowerCase().contains("windows")) {
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

    public HashMap<String, By> getByMap(IOSDriver driver) {
        HashMap result = new HashMap<String, By>();
        result.clear();
        By tempElement = null;
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IOSPageAccount . getByMap(IOSDriver driver)   : " + driver.toString());

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
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IOSPageAccount . getByMap (IOSDriver driver, HashMap<String, ElementMapping> eleMap)  : " + driver.toString());

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
        System.out.println("    +++ ~~~ The btnOperationRoute in IOSPageAccount has been called ~~~ +++");
        return super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, this.content.eleTextContentMap);
    }

    public HashMap<String, ElementMapping> getEleConMap() {
        return this.eleContentMap;
    }

    public void btnOperation(IOSDriver driver, String eleName) throws Exception {
        System.out.println("    +++ ~~~ The btnOperation in IOSPageAccount has been called ~~~ +++");
        super.btnOperation(driver, eleName, this.byMap, this.eleContentMap,this.content.eleTextContentMap);
    }

    public void testOperation(IOSDriver driver, String eleName) throws Exception {
        System.out.println("    +++ ~~~ The btnOperation in IOSPageAccount has been called ~~~ +++");
        super.testOperation(driver, eleName);
    }

    public void textOperation(IOSDriver driver, String eleName, String para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperationn in IOSPageAccount has been called ~~~ +++");
        super.textOperation(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
    }

    public String textOperationWithSaveInput(IOSDriver driver, String eleName, String para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperationWithSaveInput in IOSPageAccount has been called ~~~ +++");
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

    public String getElementContent(IOSDriver driver, String eleName) throws Exception {
        System.out.println("    +++ ~~~ The getElementContent in IOSPageAccount has been called ~~~ +++");
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
                    ArrayList<String>  temp = this.content.getContentNameList4name(this.eleContentMap, "alertTitle");
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

            IOSPageAccount x = new IOSPageAccount("/PageXML/IOS/loginPage.xml", driver, null);
            System.out.println("before defaulf operation");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

}


