package farfetch.panda.beta.ios.AppPage.fox;

import REXSH.REXAUTO.Component.Page.Element.elementEntity;
import REXSH.REXAUTO.LocalException.REXException;
import Utility.Log;
import Utility.readProperity;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.HashMap;


/**
 * Created by appledev131 on 4/11/16.
 */
public class FOXPageDashBoard extends foxPageTemplate {
    protected String fileName = "DashBoard_WEBfox.json";
    String autoOSType = readProperity.getProValue("autoRunningOSType");
    private String contentFilePath = "\\PageXML\\WEB\\FOX\\";
    public String path4Log = System.getProperty("user.dir");
    protected String comFileName = null;
    protected FOXPageContent content = null;
    protected HashMap<StringBuilder, elementEntity> eleContentMap = null;
    protected HashMap<StringBuilder, elementEntity> eleMap = null;
    public HashMap<String, By> byMap = null;
    protected WebDriver dr = null;
    protected int osType = 1;
    private String errNoticeWind = "";
    private String confirmNoticeWind = "";
    private String completeNoticeWind = "";
    private String confirmNoticeWindWOT = "";
    private String storeValue4compare = "";
    private static String logSpace4thisPage = " --- FOXPageDashBoard --";

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

    public HashMap<StringBuilder, StringBuilder> getEleWinMap() {
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

    public boolean titleCheck(WebDriver driver) throws Exception {
        System.out.println("    +++ ~~~ The titleCheck in " + logSpace4thisPage + " has been called ~~~ +++");
        return super.titleCheckTe(driver, "loginBtn", this.byMap, this.eleContentMap, this.content.eleTextContentMap, this.path4Log);
    }


    public FOXPageDashBoard(WebDriver theD, String p4L) throws Exception {
        super(theD);
        this.dr = theD;
        if (!autoOSType.toLowerCase().contains("windows")) {
            contentFilePath = new String(contentFilePath.replaceAll("\\\\", File.separator));
            path4Log = new String(path4Log.replaceAll("\\\\", File.separator));
        }
        String tempName = contentFilePath + fileName;

        content = new FOXPageContent(theD, tempName, osType);
        eleContentMap = content.getContentMap4Base(tempName, osType);
        byMap = content.getByMap(theD,this.eleContentMap);
        loadingJudgement(theD, this.eleContentMap);
        this.path4Log = p4L;
    }


    public FOXPageDashBoard(String theFile, WebDriver theD, Log theLogger) throws Exception {
        super(theFile, theD, theLogger);
        if (!autoOSType.toLowerCase().contains("windows")) {
            contentFilePath = new String(contentFilePath.replaceAll("\\\\", File.separator));
            path4Log = new String(path4Log.replaceAll("\\\\", File.separator));
        }
        this.comFileName = contentFilePath + fileName;
        this.dr = theD;
    }


    public String btnOperationRoute(WebDriver driver, String eleName) throws Exception {
        System.out.println("    +++ ~~~ The btnOperationRoute in FOXPageDashBoard has been called ~~~ +++");
        try {
            return super.btnOperation(driver, eleName, this.byMap, this.eleContentMap, this.path4Log);
        } catch (Exception e) {
            throw e;
        }
    }

    public HashMap<StringBuilder, elementEntity> getEleConMap() {
        return this.eleContentMap;
    }

    public void btnOperation(WebDriver driver, String eleName) throws Exception {
        System.out.println("    +++ ~~~ The btnOperation in FOXPageDashBoard has been called ~~~ +++");
        try {
            super.btnOperation(driver, eleName, this.byMap);
        } catch (Exception e) {
            throw e;
        }
    }

    public void testOperation(WebDriver driver, String eleName) throws Exception {
        System.out.println("    +++ ~~~ The btnOperation in FOXPageDashBoard has been called ~~~ +++");
        try {
            super.testOperation(driver, eleName);
        } catch (Exception e) {
            throw e;
        }
    }

    public void textOperation(WebDriver driver, String eleName, String para1) throws Exception {
        System.out.println("    +++ ~~~ The textOperationn in FOXPageDashBoard has been called ~~~ +++");
        try {
            super.textOperation(driver, eleName, para1, this.byMap, this.content.eleDefaultValueMap);
        } catch (Exception e) {
            throw e;
        }
    }

    public String comPageCheck(WebDriver theD, String type4check, String para4check) {  // type4check : ready, loading, error, temp , etc.  , para4check: target element name, all element, title element
        String result = null;
        boolean flag = false;
        try {
            if (type4check.equals("ready")) {       // ready mode
                if (para4check.equals("all")) {     // check all element for ready mode
                    StringBuilder[] temp = this.content.getContentNameList4type(this.eleContentMap, "ready");
                    for (int ind = 0; ind < temp.length; ind++) {
                        flag = super.getSingleEle(theD, para4check, this.byMap);
                    }
                } else if (para4check.equals("title")) // check title for ready mode
                {
                    StringBuilder[] temp = this.content.getContentNameList4name(this.eleContentMap, "PageTitle");
                    if (temp.length != 1) {
                        flag = false;
                        System.out.println("More than 1 title element found in : " + this.getClass().getSimpleName());
                        System.out.println("Please check the XML file for : " + this.getClass().getSimpleName());
                    } else {
                        //TODO  compare the ((ElementMapping) temp[0]).getTextContent() and compare with the element current content to confirm the result
                        flag = true;
                        System.out.println("TODO  compare the ((ElementMapping) temp[0]).getTextContent() and compare with the element current content to confirm the result");
                    }
                } else if (para4check.equals("alertTitle")) {
                    StringBuilder[] temp = this.content.getContentNameList4name(this.eleContentMap, "alertTitle");
                    if (temp.length != 1) {
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
        WebDriver driver = null;

        try {


            FOXPageDashBoard x = new FOXPageDashBoard("/PageXML/AND/loginPage.xml", driver, null);
            System.out.println("before defaulf operation");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

}


