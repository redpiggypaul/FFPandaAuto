package farfetch.panda.beta.ios.AppPage.IOS;

import Component.Page.Element.FFPandaElementEntity;
import Component.Page.FFPandaIOSPageContent;
import dataModel.TestPlatformData;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import utility.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by appledev131 on 4/11/16.
 */
public class IOSPage_DateWin extends IOSPageTemplate {

    protected StringBuilder fileName = new StringBuilder(this.getClass().getSimpleName().split("_")[1]+".json");
    private StringBuilder autoOSType = TestPlatformData.getAutoOSType();
    private StringBuilder className = new StringBuilder(this.getClass().getSimpleName());
    private StringBuilder contentFilePath = new StringBuilder("\\PageXML\\IOS\\");

    public StringBuilder path4Log = new StringBuilder(System.getProperty("user.dir"));
    protected StringBuilder comFileName = null;
    protected FFPandaIOSPageContent content = null;
    protected HashMap<StringBuilder, FFPandaElementEntity> eleContentMap = null;
    protected HashMap<String, IOSElement> eleMap = null;
    public HashMap<StringBuilder, By> byMap = null;
    protected IOSDriver dr = null;
    protected int osType = 2;
    private String errNoticeWind = "";
    private String confirmNoticeWind = "";
    private String completeNoticeWind = "";
    private String confirmNoticeWindWOT = "";
    private String storeValue4compare = "";
    private boolean pageMoveableCheckDisable = true;    // flag for check the page is moveable or not during test
    private boolean moveable = false;                   // flag for the page is moveable or not
    private static String logSpace4thisPage = " --- IOSPage_AccountConfirmWinConfirmWin --";

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



    public IOSPage_DateWin(IOSDriver theD) throws Exception {
        super(theD);
        this.dr = theD;
        if (!autoOSType.toString().toLowerCase().contains("windows")) {
            contentFilePath = new StringBuilder(contentFilePath.toString().replaceAll("\\\\", File.separator));
            path4Log = new StringBuilder(path4Log.toString().replaceAll("\\\\", File.separator));
        }
        StringBuilder tempName = new StringBuilder().append(contentFilePath).append(fileName);

        content = new FFPandaIOSPageContent(theD, tempName, osType);
        eleContentMap = content.getContentMap(content.getContentMap4Base(tempName, osType));  //??
        Thread.sleep(3000);
        byMap = getByMap(theD);
    }

    public IOSPage_DateWin(IOSDriver theD, StringBuilder p4L) throws Exception {
        super(theD);
        this.dr = theD;
        if (!autoOSType.toString().toLowerCase().contains("windows")) {
            contentFilePath = new StringBuilder(contentFilePath.toString().replaceAll("\\\\", File.separator));
            path4Log = new StringBuilder(path4Log.toString().replaceAll("\\\\", File.separator));
        }
        StringBuilder tempName = new StringBuilder().append(contentFilePath).append(fileName);

        content = new FFPandaIOSPageContent(theD, tempName, osType);
        eleContentMap = content.getContentMap4Base(tempName, osType);
        byMap = content.getByMap(theD, this.eleContentMap);
        loadingJudgement(theD, this.eleContentMap);
        this.path4Log = p4L;
        this.content.setFlag4MoveCheck(this.pageMoveableCheckDisable);
        this.content.setMoveable(this.moveable);
    }

    // for dry run
    public IOSPage_DateWin(StringBuilder filename, StringBuilder p4L) throws Exception {
        super(filename, p4L);
        //  this.dr = theD;
        if (!autoOSType.toString().toLowerCase().contains("windows")) {
            contentFilePath = new StringBuilder(contentFilePath.toString().replaceAll("\\\\", File.separator));
            path4Log = new StringBuilder(path4Log.toString().replaceAll("\\\\", File.separator));
        }
        StringBuilder tempName = new StringBuilder().append(contentFilePath).append(fileName);

        content = new FFPandaIOSPageContent(tempName, osType);
        eleContentMap = content.getContentMap4Base(tempName, osType);
        byMap = content.getByMap(this.eleContentMap);
        loadingJudgement(this.eleContentMap);
        this.path4Log = p4L;
        this.content.setFlag4MoveCheck(this.pageMoveableCheckDisable);
        this.content.setMoveable(this.moveable);
    }


    public IOSPage_DateWin(String theFile, IOSDriver theD, Log theLogger) throws Exception {
        super(theFile, theD, theLogger);
        if (!autoOSType.toString().toLowerCase().contains("windows")) {
            contentFilePath = new StringBuilder(contentFilePath.toString().replaceAll("\\\\", File.separator));
            path4Log = new StringBuilder(path4Log.toString().replaceAll("\\\\", File.separator));
        }
        this.comFileName = new StringBuilder().append(contentFilePath).append(fileName);
        this.dr = theD;

        //    StringBuilder tempName = new StringBuilder().append(contentFilePath).append(fileName);
        content = new FFPandaIOSPageContent(theD, this.comFileName, osType);
        eleContentMap = content.getContentMap4Base(this.comFileName, osType);
        byMap = content.getByMap(theD, this.eleContentMap);
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

    public HashMap<StringBuilder, By> getByMap(IOSDriver driver) {
        HashMap result = new HashMap<StringBuilder, By>();
        result.clear();
        By tempElement = null;
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IOSPage_AccountConfirmWin . getByMap(IOSDriver driver)   : " + driver.toString());

        for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext(); ) {
            try {
                Thread.sleep(50);

                Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorType().toString().equalsIgnoreCase("xpath")) {
                    tempElement = By.xpath(((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorStr().toString());
                } else if (((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorType().toString().equalsIgnoreCase("classname")) {
                    tempElement = By.className(((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorStr().toString());
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

    public HashMap<String, By> getByMap(IOSDriver driver, HashMap<StringBuilder, FFPandaElementEntity> eleMap) {
        HashMap result = new HashMap<String, By>();
        result.clear();
        By tempElement = null;
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in IOSPage_AccountConfirmWin . getByMap (IOSDriver driver, HashMap<String, FFPandaElementEntity> eleMap)  : " + driver.toString());

        for (Iterator it = eleMap.entrySet().iterator(); it.hasNext(); ) {
            try {
                Thread.sleep(50);

                Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorType().toString().equalsIgnoreCase("xpath")) {
                    tempElement = By.xpath(((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorStr().toString());
                } else if (((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorType().toString().equalsIgnoreCase("id")) {
                    tempElement = By.id(((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorStr().toString());
                } else if (((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorType().toString().equalsIgnoreCase("classname")) {
                    tempElement = By.className(((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorStr().toString());
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

    public StringBuilder btnOperationRoute(IOSDriver driver, StringBuilder eleName) throws Exception {
        System.out.println("    +++ ~~~ The btnOperationRoute in IOSPage_AccountConfirmWin has been called ~~~ +++");
        return super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log, this.content.getEleTextMap());
    }

    public HashMap<StringBuilder, FFPandaElementEntity> getEleConMap() {
        return this.eleContentMap;
    }

    public void btnOperation(IOSDriver driver, StringBuilder eleName) throws Exception {
        System.out.println("    +++ ~~~ The btnOperation in IOSPage_AccountConfirmWin has been called ~~~ +++");
        super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.content.getEleTextMap());
    }

    public void testOperation(IOSDriver driver, StringBuilder eleName) throws Exception {
        System.out.println("    +++ ~~~ The btnOperation in IOSPage_AccountConfirmWin has been called ~~~ +++");
        super.testOperation(driver, eleName);
    }

    public void textOperation(IOSDriver driver, StringBuilder eleName, StringBuilder para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperationn in IOSPage_AccountConfirmWin has been called ~~~ +++");
        super.textOperation(driver, eleName, para1, this.byMap, this.content.getEleDValueMap());
    }

    public StringBuilder textOperationWithSaveInput(IOSDriver driver, StringBuilder eleName, StringBuilder para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperationWithSaveInput in IOSPage_AccountConfirmWin has been called ~~~ +++");
        //super.textOperation(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
        boolean movecheckdisable = this.content.getFlag4MoveCheck();
        boolean moveable = this.content.getMoveable();
        boolean localMoveable = false;
        StringBuilder resultText = new StringBuilder();
        StringBuilder eleType = new StringBuilder();
        for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext() && eleType == null; ) { //
            final Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName.toString())) {
                eleType = ((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorType();
            }
        }
        if (!eleName.toString().equalsIgnoreCase("PageTitle")) {
            if (movecheckdisable == false) {
                localMoveable = checkMoveable(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                this.content.setMoveable(localMoveable);
                this.content.setFlag4MoveCheck(true);
                if (localMoveable == true) {
                    resultText = super.textOperationWithSaveInput(driver, eleName, para1, this.byMap, this.content.getEleDValueMap());
                } else {
                    resultText = super.textOperationStaticWithValue(driver, eleName, para1, this.byMap, this.content.getEleDValueMap(), this.path4Log);
                }
            } else {
                if (moveable == false) {
                    if (eleType.toString().equalsIgnoreCase("xpath")) {
                        resultText = super.textOperationStaticWithValue(driver, eleName, para1, this.byMap, this.content.getEleDValueMap(), this.path4Log);
                    } else if (eleType.toString().equalsIgnoreCase("classname")) {
                        resultText = super.textOperationSmartSaveInput(driver, eleName, para1, this.byMap, this.content.getEleDValueMap(), this.path4Log);
                    }
                } else {
                    if (eleType.toString().equalsIgnoreCase("xpath")) {
                        resultText = super.textOperationWithSaveInput(driver, eleName, para1, this.byMap, this.content.getEleDValueMap());
                    } else if (eleType.toString().equalsIgnoreCase("classname")) {
                        resultText = super.textOperationSmartSaveInput(driver, eleName, para1, this.byMap, this.content.getEleDValueMap(), this.path4Log);
                    }
                }
            }

        } else {
            resultText = super.textOperationStaticWithValue(driver, eleName, para1, this.byMap, this.content.getEleDValueMap(), this.path4Log);
        }
        return resultText;
    }

    public StringBuilder getElementContent(IOSDriver driver, StringBuilder eleName) throws Exception {
        System.out.println("    +++ ~~~ The getElementContent in IOSPage_AccountConfirmWin has been called ~~~ +++");
        boolean movecheckdisable = this.content.getFlag4MoveCheck();
        boolean moveable = this.content.getMoveable();
        boolean localMoveable = false;
        StringBuilder result = null;
        StringBuilder eleType = null;
        StringBuilder eleText = null;
        StringBuilder eleStr = null;
        for (Iterator it = this.eleContentMap.entrySet().iterator(); it.hasNext() && eleType == null; ) {
            final Map.Entry tempContentEntry = (Map.Entry) it.next();
            if (tempContentEntry.getKey().toString().equalsIgnoreCase(eleName.toString())) {
                eleType = ((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorType();
                eleStr = ((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorStr();
            }
        }
        if (!eleName.toString().equalsIgnoreCase("NaviBarBtn")) {
            if (movecheckdisable == false) {
                localMoveable = checkMoveable(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
                this.content.setMoveable(localMoveable);
                this.content.setFlag4MoveCheck(true);
                if (localMoveable == true) {
                    if (eleType.toString().equalsIgnoreCase("xpath")) {
                        result = super.getElementContent(driver, eleName, eleType, this.byMap, this.eleContentMap, this.content.getEleTextMap());
                    } else if (eleType.toString().equalsIgnoreCase("classname")) {
                        result = super.getElementContentSmart(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
                    }
                } else {
                    if (eleType.toString().equalsIgnoreCase("xpath")) {
                        result = super.getElementContentStatic(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
                    } else if (eleType.toString().equalsIgnoreCase("classname")) {
                        result = super.getElementContentSmart(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
                    }
                }
            } else {
                if (moveable == false) {
                    if (eleType.toString().equalsIgnoreCase("xpath")) {
                        result = super.getElementContentStatic(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
                    } else if (eleType.toString().equalsIgnoreCase("classname")) {
                        result = super.getElementContentSmart(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
                    }
                } else {
                    if (eleType.toString().equalsIgnoreCase("xpath")) {
                        result = super.getElementContentStatic(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
                    } else if (eleType.toString().equalsIgnoreCase("classname")) {
                        result = super.getElementContentSmart(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
                    }
                }
            }
        } else {
            result = super.getElementContentStatic(driver, eleStr, eleType, this.byMap, this.eleContentMap, this.path4Log, 1);
        }
        return result;
    }

    public StringBuilder comPageCheck(IOSDriver theD, StringBuilder type4check, StringBuilder para4check) {  // type4check : ready, loading, error, temp , etc.  , para4check: target element name, all element, title element
        StringBuilder result = null;
        StringBuilder passResult = new StringBuilder("pass");
        StringBuilder failResult = new StringBuilder("fail");
        boolean flag = false;
        try {
            if (type4check.equals("ready")) {       // ready mode
                if (para4check.equals("all")) {     // check all element for ready mode
                    ArrayList<StringBuilder> temp = this.content.getContentNameList4type(this.eleContentMap, new StringBuilder("ready"));
                    for (int ind = 0; ind < temp.size(); ind++) {
                        flag = super.getSingleEle(theD, para4check, this.byMap);
                    }
                } else if (para4check.equals("title")) // check title for ready mode
                {
                    ArrayList<StringBuilder> temp = this.content.getContentNameList4name(this.eleContentMap, new StringBuilder("PageTitle"));
                    if (temp.size() != 1) {
                        flag = false;
                        System.out.println("More than 1 title element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        //TODO  compare the ((FFPandaElementEntity) temp[0]).getTextContent() and compare with the element current content to confirm the result
                        flag = true;
                        System.out.println("TODO  compare the ((FFPandaElementEntity) temp[0]).getTextContent() and compare with the element current content to confirm the result");
                    }
                } else if (para4check.equals("alertTitle")) {
                    ArrayList<StringBuilder> temp = this.content.getContentNameList4name(this.eleContentMap, new StringBuilder("alertTitle"));
                    if (temp.size() != 1) {
                        flag = false;
                        System.out.println("More than 1 alertTitle element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        //TODO  compare the ((FFPandaElementEntity) temp[0]).getTextContent() and compare with the element current content to confirm the result
                        flag = true;

                    }
                } else {
                }
            } else {
                //TODO
                System.out.println("Other type of check are not ready");
            }
            if (flag == true) {
                result = passResult;
            } else {
                result = failResult;
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            flag = false;
            result = failResult;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
            result = failResult;
        } finally {
            return result;
        }
    }
    public boolean confirmOperation(IOSDriver driver) throws Exception {
        System.out.println("    +++ ~~~ The confirmOperation in IPADPageLogin has been called ~~~ +++");
        return super.confirmOperationStatic(driver, new StringBuilder("loginBtn"), this.byMap, this.eleContentMap, this.content.getEleTextMap(), this.path4Log);
    }
}


