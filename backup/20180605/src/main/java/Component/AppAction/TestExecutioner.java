package Component.AppAction;

import Component.Page.ANDPageContent;
import Component.Page.Element.operationItem;
import LocalException.FFPandaException;
import LocalException.ParameterException;
import dataModel.TestPlatformData;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import javassist.tools.rmi.ObjectNotFoundException;
import org.openqa.selenium.WebDriver;
import utility.StringB;
import Component.AppAction.ActionXML2OperationSequence.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TestExecutioner {
    // 目标是将基本的路由结果检查,然后在page对象内增加action对象
    StringBuilder autoOSType = TestPlatformData.getAutoOSType();
    private int operationType = 0; // default 0 for review, 1 for Edit, 2 for Remove, 3 for search, 4 for route
    // private AbsPage thePage = null;
    private StringBuilder currentPage = null;
    private StringBuilder targetPagename = new StringBuilder("");
    //   private StringBuilder wholePageName = null;
    public ANDPageContent pageContent = null;  // get content in xml file  XML 内容
    HashMap<String, operationItem> contentMap = null;      //  map 4 name 2 ele att map   页面元素信息集合, 不是元素集合  名称,所有信息

    protected StringBuilder path4Page = new StringBuilder("\\PageXML\\");   //  file path 4 xml, "/PageXML/AND/" for And, "/PageXML/IOS/"; for IOS 页面文件路径
    protected StringBuilder fileName4Page = new StringBuilder("");     // file name 4 xml  页面文件名称,带.XML

    protected StringBuilder path4Action = new StringBuilder("\\OperationXML\\Action\\");
    protected StringBuilder fileName4Action = new StringBuilder("");     // file name 4 xml  页面文件名称,带.XML

    //   protected IOSPageLogin basePageIOS = null;
//    protected IOSPageNaviBar theNaviBarIOS = null;
    //   protected IOSPageHome theHomeIOS = null;
    protected StringBuilder result4exe = null;

    protected AppiumDriver theDriver = null;
    protected WebDriver theWebDriver = null;
    protected ArrayList<StringBuilder> theActionSeq = new ArrayList<StringBuilder>();
    protected ArrayList<StringBuilder> paraList4TC = new ArrayList<StringBuilder>();
    protected StringBuilder path4Log = new StringBuilder("");
    protected StringBuilder content4input = new StringBuilder("");
    protected StringBuilder content4check = new StringBuilder("");
    protected StringBuilder content4pagecheck = new StringBuilder("");
    protected boolean flag4page = false;
    protected StringBuilder contentFromExcept = new StringBuilder("");
    protected String[][] classNameMap = null;
    public int paraIndex = 0;
    protected StringBuilder appInputFeedBackMode = new StringBuilder("exact");
    protected StringBuilder date4diffPage = new StringBuilder("");
    protected StringBuilder key4searchMatch = new StringBuilder("");
    protected StringBuilder errWindowFromElement = new StringBuilder("");
    protected StringBuilder confirmWindowFromElement = new StringBuilder("");
    protected StringBuilder completeWindowFromElement = new StringBuilder("");
    protected ArrayList<StringBuilder> eleWinListAfterClick = new ArrayList<StringBuilder>();
    protected ArrayList<StringBuilder> eleErrorWinListAfterClick = new ArrayList<StringBuilder>();
    private static StringBuilder logSpace4thisPage = new StringBuilder(" 000 BaseAction 000 ");

    protected StringBuilder deviceType = new StringBuilder("");

    //FF Panda
    public TestExecutioner(AppiumDriver driver, StringBuilder currentPageName, int operaTypeInd, ArrayList<StringBuilder> actionSequence, ArrayList<StringBuilder> extPara, StringBuilder path4LogWithTCName, StringBuilder expResultContent) throws Exception {
        long startTime = System.currentTimeMillis();

        if (!autoOSType.toString().toLowerCase().contains("windows")) {
            path4Page = new StringBuilder(path4Page.toString().replaceAll("\\\\", File.separator));
            path4Action = new StringBuilder(path4Action.toString().replaceAll("\\\\", File.separator));
        }
        this.operationType = operaTypeInd;
        this.currentPage = currentPageName;
        this.theDriver = driver;
        this.theActionSeq = actionSequence;
        this.paraList4TC = extPara;

        this.path4Log = mkdir4Log(path4LogWithTCName);
        this.classNameMap = PandaClassShortName2CompleteName.IOSList();
        this.contentFromExcept = expResultContent;
        System.out.println("^^^^^^    BaseAction init ：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
    }

    //从testNG 的 case接收环境，测试机的配置；和driver有关的应该在tc的抽象父类的准备步骤中都设置过了；剩下的不多
    protected void readConfigurationData(StringBuilder extDeviceType) {
        try {
            if (extDeviceType != null) {
                this.deviceType = new StringBuilder(extDeviceType.toString().toLowerCase());
            } else {
                throw new ObjectNotFoundException("The Device Type from TC is NULL!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //引入homepage，navi等必经之路的页面的类对象到页面对象集合内备用；  这些页面的元素信息应该在实例化这些类对象的时候自动装载了
    protected void prepareDefaultPageList() {
        String testENVpreName = null;
        String filepath4XML = null;
        Object devicePage4next = null;
        Object devicePage4last = null;
        HashMap<String, Object> pageGroup = new HashMap<String, Object>();

        if (this.deviceType.toString().contains("pad")) {
            testENVpreName = new String("IPADPage_");
            filepath4XML = new String("farfetch.panda.beta.ios.AppPage.IOSAppPage.PAD.");
            long gstartTime = System.currentTimeMillis();
            //   this.basePageIPAD = new IPADPageLogin(theDriver, this.path4Log);
            //   this.theNaviBarIPAD = new IPADPageNaviBar(theDriver, this.path4Log);
            //   this.theHomeIPAD = new IPADPageHome(theDriver, this.path4Log);
            System.out.println("^^^^^^    pre-createInstance：" + (System.currentTimeMillis() - gstartTime) + " ms    ^^^^^^");
            //   pageGroup.put("homePage", this.basePageIPAD);
            //   pageGroup.put("bashPage", this.theHomeIPAD);
            //   pageGroup.put("NaviBar", this.theNaviBarIPAD);
        } else {
            testENVpreName = new String("IOSPage_");
            filepath4XML = new String("farfetch.panda.beta.ios.AppPage.IOSAppPage.IOS.");
            //  this.basePageIOS = new IOSPageLogin(theDriver, this.path4Log);
            //  this.theNaviBarIOS = new IOSPageNaviBar(theDriver, this.path4Log);
            //  this.theHomeIOS = new IOSPageHome(theDriver, this.path4Log);
            //  pageGroup.put("homePage", this.basePageIOS);
            //  pageGroup.put("bashPage", this.theHomeIOS);
            //  pageGroup.put("NaviBar", this.theNaviBarIOS);
        }

        String elePageName4Last = null;
        String name4DevicePage = null;
        this.paraIndex = 0;
        String elePageName4next = null;
        Class classType = null; // Class.forName(testENVpreName + operationPage4this);
        this.result4exe = new StringBuilder("teststart");
        String tempName4Screen = null;
        String currentOpeItemStepName4Error = "";
        String lastOpeItemStepName4Error = "";
    }

    protected ArrayList<StringBuilder> checkStepParaInfo(ArrayList<StringBuilder> extPara) {
        ArrayList<StringBuilder> result = new ArrayList<StringBuilder>();
        try {
            result.clear();
            result.addAll(extPara);
            if (result.size() == 0) {  //todo really ZERO?
                //   throw new ParameterException("Failed at Component.AppAction.comOperationInIOS.checkStepParaInfo");
            }
        } catch (Exception e) {
        } finally {
            return result;
        }
    }
    public StringBuilder getExeResult() {
        return this.result4exe;
    }
    protected AppiumDriver targetSYSstartup(AppiumDriver driver) {
        AppiumDriver result = driver;
        try {
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    protected AppiumDriver targetSYSTeardown(AppiumDriver driver) {
        AppiumDriver result = driver;
        try {
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    private int firstStepHandler(ArrayList<StringBuilder> extActSeq, IOSDriver driver, int extStepInd) {
        int result = extStepInd;
        try {
            if (extActSeq.get(0).toString().equalsIgnoreCase("login")) {
                boolean loginResult = false;
                boolean dashResult = false;
                if (deviceType.toString().toLowerCase().contains("pad")) {
                    //    loginResult = basePageIPAD.confirmOperation(theDriver);
                    //   if (loginResult == false) {
                    //      dashResult = theHomeIPAD.confirmOperation(theDriver);
                    //     }
                } else {
                 //   loginResult = basePageIOS.confirmOperation(driver);
                    if (loginResult == false) {
                 //       dashResult = theHomeIOS.confirmOperation(driver);
                    }
                }
                if (loginResult == true && dashResult == false) {
                    System.out.println("Process start with Login");
                } else if (loginResult == false && dashResult == true) {
                    System.out.println("Process start with DashBoard");
                    result = 1;
                    this.paraIndex = 2;  // because there are 2 parameters for the "login"
                } else {
                    throw new FFPandaException("Initial page is not Login OR Dashboard");
                }
            } else {
                System.out.println("Process don't start with Login, be careful");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    private void testStepLooper(ArrayList<StringBuilder> actionSequence) throws Exception {
        HashMap<StringBuilder, operationItem> wholeSeqMap = new HashMap<StringBuilder, operationItem>();
        HashMap<StringBuilder, operationItem> singleSeqMap = new HashMap<StringBuilder, operationItem>();
        wholeSeqMap = ActionXML2OperationSequence.getOperationSeq(actionSequence, this.path4Action);
        for (int ind4ope = 0; ind4ope < actionSequence.size(); ) {
            if (ind4ope == 0) {
                ind4ope = firstStepHandler(actionSequence, (IOSDriver) theDriver, ind4ope);
            }

        }

    }

    public IOSDriver comOperationInIOS(IOSDriver theDriver, ArrayList<StringBuilder> actionSequence, ArrayList<StringBuilder> extPara, StringBuilder deviceType) throws Exception {
        IOSDriver result = null;
        try {

            //从testNG 的 case接收环境，测试机的配置；和driver有关的应该在tc的抽象父类的准备步骤中都设置过了；剩下的不多
            readConfigurationData(deviceType);
            //引入homepage，navi等必经之路的页面的类对象到页面对象集合内备用；  这些页面的元素信息应该在实例化这些类对象的时候自动装载了
            prepareDefaultPageList();
            //数据库或者后台接口信息；
            //buildUPDBconnection(){}

            //初始化循环步骤执行相关的变量
            String elePageName4Last = null;
            String name4DevicePage = null;
            this.paraIndex = 0;
            String elePageName4next = null;
            Class classType = null; // Class.forName(testENVpreName + operationPage4this);
            this.result4exe = new StringBuilder("teststart");
            String tempName4Screen = null;
            String currentOpeItemStepName4Error = "";
            String lastOpeItemStepName4Error = "";

            //从tc获取参数，主要是步骤和对应的参数
            checkStepParaInfo(extPara);
            // android,ios,web等可能的独特的准备动作
            theDriver = (IOSDriver) targetSYSstartup(theDriver);
            //然后进入常规动作循环
            testStepLooper(actionSequence);
            // android,ios,web等可能的独特的退出动作
            theDriver = (IOSDriver) targetSYSTeardown(theDriver);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }

    }


    public StringBuilder mkdir4Log(StringBuilder cPath) throws Exception {
        StringBuilder res = null;
        try {
            File pfile = new File(cPath.toString());
            pfile.mkdir();
            Thread.sleep(2000);
            if (pfile.exists()) {
                res = new StringBuilder(pfile.getPath());
            } else {
                res = new StringBuilder("create folder failed");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            return res;
        }
    }
}
