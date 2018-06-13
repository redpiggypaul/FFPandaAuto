package ftech.panda.beta.ios;

import Component.AppAction.TestExecutioner;
import LocalException.PageTitleException;
import dataModel.TargetMobileData;
import dataModel.XLSReportData;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.testng.annotations.*;
import utility.MailSender;
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
public class AppTest_FFPANDA_Demo extends FF_PandaAppTestTemplate {
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

    String[] TestStepList_Demo = new String[]{"Demo", "DemoEND","Demo3"};
    String[] TestStepList_Demo2 = new String[]{"Demo2"};

    ArrayList<String> TestStepList_Demo_List = new ArrayList<String>();
    ArrayList<String> TestStepList_Demo_List2 = new ArrayList<String>();

    protected long startTime = Long.parseLong("0");
    public AppTest_FFPANDA_Demo() throws FileNotFoundException {

    }


    @DataProvider
    public Object[][] data4Demo() {
        return new Object[][]{
                {this.accontFromXls, this.accontPSWFromXls, this.accontPSWFromXls + "::Pwcwelcome2::Pwcwelcome2", "regular", "dryrun_getElementContent::dryrun_getElementContent::dryrun_getElementContent"},
                {this.accontFromXls, "Pwcwelcome2", "Pwcwelcome2::" + this.accontPSWFromXls + "::" + this.accontPSWFromXls, "regular", ""},
        };
    }

    @DataProvider
    public Object[][] data4Demo2() {
        return new Object[][]{
                {this.accontFromXls, "dryrun_getElementContent", "", "regular", ""},
        };
    }

    @Test(dataProvider = "data4Demo", groups = {"Demo Test"})
    @Parameters({"APPIUM_Driver_port"})
    public void iosTestStepList_Demo(String theUserPara, String thePSW, String paraGroup, String targetResultType, String expectResult) throws Exception {
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
            TestExecutioner theTCRunner = new TestExecutioner(driver, null, 1, para4Action, extParaList, cMethodPath, new StringBuilder(expectResult), this.startTime);  // 2nd para is for os type, last para is the excepted result str
            theTCRunner.comOperationInIOS((IOSDriver) driver, this.TestStepList_Demo_List, para4Action, TargetMobileData.getMobileOS());     // 2nd para is the action sequence
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
            //   driver.closeApp();   //todo escape 4 dry run
            Thread.sleep(1000);
            //    driver.launchApp();   //todo escape 4 dry run
        }
    }

    @Test(dataProvider = "data4Demo2", groups = {"Demo Test"})
    @Parameters({"APPIUM_Driver_port"})
    public void iosTestStepList_Demo2(String theUserPara, String thePSW, String paraGroup, String targetResultType, String expectResult) throws Exception {
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
            TestExecutioner theTCRunner = new TestExecutioner(driver, null, 1, para4Action, extParaList, cMethodPath, new StringBuilder(expectResult),this.startTime);  // 2nd para is for os type, last para is the excepted result str
            theTCRunner.comOperationInIOS((IOSDriver) driver, this.TestStepList_Demo_List2, para4Action, TargetMobileData.getMobileOS());     // 2nd para is the action sequence
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
            //   driver.closeApp();   //todo escape 4 dry run
            Thread.sleep(1000);
            //    driver.launchApp();   //todo escape 4 dry run
        }
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        super.setUp4APP(driver, this.port);
        //  currentMobileInfo.setCurrentScreen(driver);  // TODO escape for dry run
     //   this.startTime=super.startTime4template; // TODO escape for dry run
        this.startTime=System.currentTimeMillis();
        currentMobileInfo.setCurrentScreen4DryRun(driver);
        TestStepList_Demo_List = super.stringList2SBArrlist(TestStepList_Demo);
        TestStepList_Demo_List2 = super.stringList2SBArrlist(TestStepList_Demo2);
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
        //   theFileOutStream.close();   //todo escape 4 dry run
        MailSender.sendMail();
        //    driver.closeApp();  //todo escape 4 dry run
        //   driver.quit();  //todo escape 4 dry run
    }

    @Test(groups = {"API Test", "Fucntion Test"})
    public void test01() {
        System.out.println("API Testing and Function testing");
    }

    @Test(groups = {"API Test"})
    public void test02() {
        System.out.println("API Testing");
    }

    @Test(groups = {"Function Test"})
    public void test03() {
        System.out.println("Function testing");
    }

    @Test
    public void test04() {
        System.out.println("not in API and Function testing");
    }

    @Test(enabled = false, priority = 1, groups = {"API Test"})
    public void test1() {
        System.out.println("Hello");
    }

    @Test(priority = 1, groups = {"API Test"})
    public void test2() {
        System.out.println("TestNG");
    }

    @Test(dependsOnMethods = {"test2"}, groups = {"API Test"})
    @Parameters({"Browser", "Server"})
    public void test3(String browser, String server) {
        System.out.println("Hello");
        System.out.println("这次启动浏览器是： " + browser + " 测试服务器地址是： " + server);
    }

    @Test(invocationCount = 5, invocationTimeOut = 5100, groups = {"API Test"})
    public void loginTest() throws InterruptedException {

        Thread.sleep(1000);
        System.out.println("login test");
    }

}



