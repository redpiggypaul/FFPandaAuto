package ftech.panda.beta.ios;

import Component.AppAction.TestExecutioner;
import LocalException.PageTitleException;
import dataModel.TargetMobileData;
import dataModel.XLSReportData;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.testng.annotations.*;
import utility.readProperity.SingleTonReadProperity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static Component.Driver.ScreenShot.ScreenShot;

/**
 * Unit test for simple App.
 */
public class AppTestAccount extends FF_PandaAppTestTemplate {
    private AppiumDriver driver;
    private Date date = new Date();
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String time = format.format(date);
    public StringBuilder port = new StringBuilder("4723");
    private FileOutputStream theFileOutStream = null;
    private String nameOfMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
    private ArrayList<String> list4result = new ArrayList<String>();
    private TargetMobileData currentMobileInfo = new TargetMobileData();
    int globalRowId4TC = 0;

    String accontFromXls = SingleTonReadProperity.getProValue(this.getClass().getSimpleName() + "_account").toString();
    String accontPSWFromXls = SingleTonReadProperity.getProValue(this.getClass().getSimpleName() + "_accountPSW").toString();

    String[] TestStepList_Demo = new String[]{"Demo"};

    String[] TestStepList_Account_PSW = new String[]{"login", "switch2AccountSetting", "changePsw"};
    String[] TestStepList_Account_del = new String[]{"login", "switch2AccountSetting", "delAccount"};
    String[] iosTestStepList_Account_PSW = new String[]{"login", "switch2AccountSetting", "changePsw", "AccountSettingLogout"};
    String[] iosTestStepList_Account_del = new String[]{"login", "switch2AccountSetting", "delAccount", "AccountSettingLogout"};
    String[] TestStepList_change_FirstName = new String[]{"login", "switch2AccountSetting", "changeFirstName", "changeFirstName_Check"};
    String[] TestStepList_change_LastName = new String[]{"login", "switch2AccountSetting", "changeLastName", "changeLastName_Check"};
    String[] TestStepList_change_Email = new String[]{"login", "switch2AccountSetting", "changeEmail", "changeEmail_Check"};

    ArrayList<String> TestStepList_Account_PSW_List = new ArrayList<String>();
    ArrayList<String> TestStepList_Account_del_List = new ArrayList<String>();
    ArrayList<String> iosTestStepList_Account_PSW_List = new ArrayList<String>();
    ArrayList<String> iosTestStepList_Account_del_List = new ArrayList<String>();
    ArrayList<String> TestStepList_change_FirstName_List = new ArrayList<String>();
    ArrayList<String> TestStepList_change_LastName_List = new ArrayList<String>();
    ArrayList<String> TestStepList_change_Email_List = new ArrayList<String>();
    ArrayList<String> TestStepList_Demo_List = new ArrayList<String>();

    public AppTestAccount() throws FileNotFoundException {

    }


    @DataProvider
    public Object[][] accountPSW() {
        return new Object[][]{
                {this.accontFromXls, this.accontPSWFromXls, this.accontPSWFromXls + "::Pwcwelcome2::Pwcwelcome2", "regular", ""},
                {this.accontFromXls, "Pwcwelcome2", "Pwcwelcome2::" + this.accontPSWFromXls + "::" + this.accontPSWFromXls, "regular", ""},
        };
    }

    @DataProvider
    public Object[][] accountDEL() {
        return new Object[][]{
                {this.accontFromXls, this.accontPSWFromXls, "", "", "Your request to remove your account from Talent Exchange cannot occur because"},
        };
    }

    @DataProvider
    public Object[][] accountFirstName() {
        return new Object[][]{
                {this.accontFromXls, this.accontPSWFromXls, "try", "regular", ""},
        };
    }

    @DataProvider
    public Object[][] accountLastName() {
        return new Object[][]{
                {this.accontFromXls, this.accontPSWFromXls, "try", "regular", ""},
                {this.accontFromXls, this.accontPSWFromXls, "REXexceed01", "regular", ""},
        };
    }

    @DataProvider
    public Object[][] accountEmail() {
        return new Object[][]{
                {this.accontFromXls, this.accontPSWFromXls, "try@163.com", "regular", "emptyContent"},
        };
    }

    @Test(dataProvider = "accountEmail",groups = {"Demo Test"})
    @Parameters({"APPIUM_Driver_port"})
    public void iosTestStepList_Account_PSW(String theUserPara, String thePSW, String paraGroup, String targetResultType, StringBuilder expectResult) throws Exception {
        String nameOfMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        globalRowId4TC++;
        String result4XLSX = "Failed";

        date = new Date();
        time = format.format(date);

        String preRoleName = null;
        String[] extPara = paraGroup.split("::");
        if (extPara.length != 0) {
            if (extPara[extPara.length - 1].length() > 10) {//如果长度大于10则截取
                preRoleName = new String(extPara[extPara.length - 1].substring(0, 9));  // for screen
                preRoleName = filter4space(preRoleName);
            } else {
                preRoleName = extPara[extPara.length - 1];
            }
        }
        ArrayList<String> extParaList = super.stringList2SBArrlist(extPara);
        ArrayList<String> para4Action = preapareActionParameterList(new String[]{theUserPara, thePSW}, extPara);  // for action
        StringBuilder cMethodPath = new StringBuilder(XLSReportData.getcPath() + File.separator + nameOfMethod + "_" + preRoleName + "_" + time.replaceAll(":", "_")); // for screen
        list4result.add(nameOfMethod + "#" + time + "#" + preRoleName + "#" + targetResultType + "#" + "JustStart" + "#" + "waiting4result");
        try {
            TestExecutioner theTCRunner = new TestExecutioner(driver, null, 1, para4Action, extParaList, cMethodPath, expectResult);  // 2nd para is for os type, last para is the excepted result str
            //   if (TargetMobileData.getMobileOS().toString().equalsIgnoreCase("Android")) {
            //       theTCRunner.comOperationInAND((AndroidDriver) driver, this.TestStepList_change_Email, para4Action);   // 2nd para is the action sequence
            //    } else if (TargetMobileData.getMobileOS().toString().startsWith("ios")) {
            theTCRunner.comOperationInIOS((IOSDriver) driver, this.TestStepList_Demo_List, para4Action, TargetMobileData.getMobileOS());     // 2nd para is the action sequence
            //  }
            Thread.sleep(2000);
            System.out.println("Here is the result for this case : " + theTCRunner.getExeResult());

            date = new Date();
            time = format.format(date);
            boolean boolean4assert = assertTestResult(theTCRunner.getExeResult().toString(), targetResultType); // for judge
            if (boolean4assert == true) {
                result4XLSX = "PASS";
            }
            list4result.add(nameOfMethod + "#" + time + "#" + preRoleName + "#" + targetResultType + "#" + theTCRunner.getExeResult().toString() + "#" + result4XLSX);  // for xlsx
            if (boolean4assert == false)  // judge the result
            {
                assert false;
            }
        } catch (PageTitleException e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ Single result judgement for PageTitleException ~~~~~~~~~~~~~~~~~~~~~~~~~~");
            boolean boolean4assert = assertTestResult(e.getMessage(), targetResultType); // for judge
            if (boolean4assert == true) {
                result4XLSX = "PASS";
            }
            list4result.add(nameOfMethod + "#" + time + "#" + preRoleName + "#" + targetResultType + "#" + e.getMessage() + "#" + result4XLSX);  // for xlsx
            if (boolean4assert == false)  // judge the result
            {
                assert false;
            }
        } catch (Exception e) {
            date = new Date();
            time = format.format(date);
            String expName = e.getClass().getSimpleName();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ " + expName + " ~~~~~~~~~~~~~~~~~~~~~~~~~~");
            e.printStackTrace();
            ScreenShot(driver, new StringBuilder(nameOfMethod + time + expName + ".png"), cMethodPath);
            String newRow4Cell = super.formatResult4XLSX(nameOfMethod, preRoleName, targetResultType, expName, time);
            list4result.add(newRow4Cell);
            e.printStackTrace();
            assert false;
            recover();
        } finally {
            driver.closeApp();
            Thread.sleep(1000);
            driver.launchApp();
        }
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        super.setUp4APP(driver, this.port);
        currentMobileInfo.setCurrentScreen(driver);
        TestStepList_Account_PSW_List = super.stringList2SBArrlist(TestStepList_Account_PSW);
        TestStepList_Account_del_List = super.stringList2SBArrlist(TestStepList_Account_del);
        iosTestStepList_Account_PSW_List = super.stringList2SBArrlist(iosTestStepList_Account_PSW);
        iosTestStepList_Account_del_List = super.stringList2SBArrlist(iosTestStepList_Account_del);
        TestStepList_change_FirstName_List = super.stringList2SBArrlist(TestStepList_change_FirstName);
        TestStepList_change_LastName_List = super.stringList2SBArrlist(TestStepList_change_LastName);
        TestStepList_change_Email_List = super.stringList2SBArrlist(TestStepList_change_Email);
        TestStepList_Demo_List = super.stringList2SBArrlist(TestStepList_Demo);
        StringBuilder cPath = XLSReportData.getcPath();
        File pfile = new File(cPath.append(this.getClass().getSimpleName()).append("_").append(time.replaceAll(":", "_")).toString());
        pfile.mkdir();
    }

    public void recover() throws Exception {
        driver.closeApp();
        driver.quit();
        Thread.sleep(5000);
        super.recover4APP(driver);
    }

    @BeforeMethod
    public void beforeMethod() throws FileNotFoundException {
        System.out.println("*******beforeMethod in " + nameOfMethod + "********");
        theFileOutStream = super.BeforeMethod();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("*******aftermethod in " + nameOfMethod + "********");
        theFileOutStream = super.AfterMethod(theFileOutStream, list4result, globalRowId4TC);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        theFileOutStream.close();
        driver.closeApp();
        driver.quit();
    }

    @Test(groups = {"API Test", "Fucntion Test"})
    public void test01(){
        System.out.println("API Testing and Function testing");
    }

    @Test(groups = {"API Test"})
    public void test02(){
        System.out.println("API Testing");
    }

    @Test(groups = {"Function Test"})
    public void test03(){
        System.out.println("Function testing");
    }

    @Test
    public void test04(){
        System.out.println("not in API and Function testing");
    }

    @Test(enabled=false,priority = 1,groups = {"API Test"})
    public void test1(){
        System.out.println("Hello");
    }

    @Test(priority = 1,groups = {"API Test"})
    public void test2(){
        System.out.println("TestNG");
    }

    @Test(dependsOnMethods={"test2"},groups = {"API Test"})
    @Parameters({"Browser", "Server"})
    public void test3(String browser, String server){
        System.out.println("Hello");
        System.out.println("这次启动浏览器是： "+browser+" 测试服务器地址是： "+server);
    }

    @Test(invocationCount = 5, invocationTimeOut = 5100,groups = {"API Test"})
    public void loginTest() throws InterruptedException{

        Thread.sleep(1000);
        System.out.println("login test");
    }

}



