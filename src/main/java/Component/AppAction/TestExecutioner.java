package Component.AppAction;

import Component.Page.Element.operationItem;
import Component.Page.FFPandaIOSPageContent;
import LocalException.FFPandaException;
import LocalException.PageTitleException;
import LocalException.XMLException;
import dataModel.TestPlatformData;
import farfetch.panda.beta.ios.AppPage.IOS.IOSPage_Home;
import farfetch.panda.beta.ios.AppPage.IOS.IOSPage_Login;
import farfetch.panda.beta.ios.AppPage.IOS.IOSPage_NaviBar;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import javassist.tools.rmi.ObjectNotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static Component.Driver.ScreenShot.ScreenShot;
import static java.util.Collections.sort;

public class TestExecutioner {
    // 目标是将基本的路由结果检查,然后在page对象内增加action对象
    StringBuilder autoOSType = TestPlatformData.getAutoOSType();
    private int operationType = 0; // default 0 for review, 1 for Edit, 2 for Remove, 3 for search, 4 for route
    // private AbsPage thePage = null;
    private StringBuilder currentPage = null;
    private StringBuilder targetPagename = new StringBuilder("");
    //   private StringBuilder wholePageName = null;
    public FFPandaIOSPageContent pageContent = null;  // get content in xml file  XML 内容
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
    protected ArrayList<String> theActionSeq = new ArrayList<String>();
    protected ArrayList<String> paraList4TC = new ArrayList<String>();
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
    protected HashMap<String, Object> pageGroup = new HashMap<String, Object>();
    private StringBuilder testENVpreName = new StringBuilder();
    private StringBuilder filepath4PageClassFile = new StringBuilder();
    protected IOSPage_Login basePageIOS = null;
    protected IOSPage_NaviBar theNaviBarIOS = null;
    protected IOSPage_Home theHomeIOS = null;
    //  protected IPADPageLogin basePageIPAD = null;
    // protected IPADPageNaviBar theNaviBarIPAD = null;
    // protected IPADPageHome theHomeIPAD = null;

    protected StringBuilder deviceType = new StringBuilder("");

    //FF Panda
    public TestExecutioner(AppiumDriver driver, StringBuilder currentPageName, int operaTypeInd, ArrayList<String> actionSequence, ArrayList<String> extPara, StringBuilder path4LogWithTCName, StringBuilder expResultContent) throws Exception {
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
        //  HashMap<String, Object> pageGroup = new HashMap<String, Object>();

        if (this.deviceType.toString().contains("pad")) {
            this.testENVpreName = new StringBuilder("IPADPage_");
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
            this.testENVpreName = new StringBuilder("IOSPage_");
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

    protected ArrayList<String> checkStepParaInfo(ArrayList<String> extPara) {
        ArrayList<String> result = new ArrayList<String>();
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

    protected AppiumDriver targetSYSstartup(AppiumDriver driver, StringBuilder deviceType, HashMap<String, Object> pageGroup) {
        AppiumDriver result = driver;
        try {

            StringBuilder filepath4XML = new StringBuilder();
            StringBuilder filepath4PageClassFile = new StringBuilder();
            if (deviceType.toString().contains("pad")) {
            /*
                testENVpreName = new StringBuilder("IPADPage_");
                filepath4PageClassFile = new StringBuilder("farfetch.panda.beta.ios.AppPage.iPAD.");
                this.basePageIPAD = new IPADPageLogin(theDriver, this.path4Log);
                this.theNaviBarIPAD = new IPADPageNaviBar(theDriver, this.path4Log);
                this.theHomeIPAD = new IPADPageHome(theDriver, this.path4Log);
                System.out.println("^^^^^^    pre-createInstance：" + (System.currentTimeMillis() - gstartTime) + " ms    ^^^^^^");
                pageGroup.put("homePage", this.basePageIPAD);
                pageGroup.put("bashPage", this.theHomeIPAD);
                pageGroup.put("NaviBar", this.theNaviBarIPAD);
           */
            } else {
                testENVpreName = new StringBuilder("IOSPage_");
                filepath4PageClassFile = new StringBuilder("farfetch.panda.beta.ios.AppPage.IOS.");
                this.basePageIOS = new IOSPage_Login((IOSDriver) result, this.path4Log);
                this.theNaviBarIOS = new IOSPage_NaviBar((IOSDriver) result, this.path4Log);
                this.theHomeIOS = new IOSPage_Home((IOSDriver) result, this.path4Log);
                pageGroup.put(new String("homePage"), this.basePageIOS);
                pageGroup.put(new String("bashPage"), this.theHomeIOS);
                pageGroup.put(new String("NaviBar"), this.theNaviBarIOS);
            }
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

    private int firstStepHandler(ArrayList<String> extActSeq, IOSDriver driver, int extStepInd) {
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
                    loginResult = basePageIOS.confirmOperation(driver);
                    if (loginResult == false) {
                        dashResult = theHomeIOS.confirmOperation(driver);
                    }
                }
                if (loginResult == true && dashResult == false) {
                    System.out.println("Process start with Login");
                } else if (loginResult == false && dashResult == true) {
                    System.out.println("Process start with HomePage");
                    result = 1;
                    this.paraIndex = 2;  // because there are 2 parameters for the "login"
                } else {
                    throw new FFPandaException("Initial page is not Login OR HomePage");
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

    public HashMap<String, operationItem> analyOpeSequence(HashMap<String, operationItem> theOpeMap, String opeName) throws Exception {
        //将hash map中符合操作名称的读取出,丢去不符合的
        HashMap<String, operationItem> result = new HashMap<String, operationItem>();
        try {
            for (Iterator it = theOpeMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry obj = (Map.Entry) it.next();
                String[] tSet = obj.getKey().toString().split(":");
                if (tSet.length == 2) {
                    if (tSet[0].equalsIgnoreCase(opeName)) {
                        operationItem eLocator;
                        eLocator = (operationItem) obj.getValue();
                        result.put(obj.getKey().toString(), eLocator);
                    }
                } else {
                    System.out.println("Wrong format from getOperationSeq, readXML");
                    throw new XMLException("Wrong format from getOperationSeq, readXML");
                }
            }
            System.out.println("Get the size : " + result.size());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private List<Object> getSortOperationList(HashMap<String, operationItem> singleSeqMap, String thisOperationName) throws Exception {
        List<Object> list = new ArrayList<>();
        list.add((operationItem) singleSeqMap.keySet());
        Object[] ary = list.toArray();
        Arrays.sort(ary);
        list = Arrays.asList(ary);
        return list;
    }

    public Object createInstance(Class cType, IOSDriver theD) throws Exception {
        //System.out.println(" ~~~~~~ In the createInstance()");
        System.out.println("Check the cType : " + cType.toString());
        Object result = null;
        Constructor<?>[] consts = cType.getConstructors();
        Constructor<?> constructor = null;
        try {
            for (int index = 0; index < consts.length; index++) {
                //System.out.println("The consts [] is not empty ! ");
                int paramsLength = consts[index].getParameterAnnotations().length;
                //System.out.println("Check the paraType : " + consts[index].getParameterTypes());
                //System.out.println("The AndDr class type is : " + ((Class) AndroidDriver.class).getSimpleName().toString());
                if (paramsLength == 2) {
                    System.out.println("@@@ Find the constructor with 2 para : " + consts[index]);
                    constructor = consts[index];
                    break;
                }
            }
            if (constructor != null) {
                //System.out.println("Double check the constructor !!!");
                Class<?>[] type = constructor.getParameterTypes();
                System.out.println("What is this ??? : " + type);
                result = cType.getConstructor(type).newInstance(theDriver, this.path4Log);
                System.out.println("cPage : " + result);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // every single Action contains several operations
    private void testStepLooper(IOSDriver driver, ArrayList<String> actionSequence, ArrayList<String> extPara) throws Exception {
        HashMap<String, operationItem> wholeSeqMap = new HashMap<String, operationItem>();
        HashMap<String, operationItem> singleSeqMap = new HashMap<String, operationItem>();
        StringBuilder tempName4Screen = new StringBuilder("");
        StringBuilder currentOpeItemElementName4Error = new StringBuilder("");
        StringBuilder lastOpeItemElementName4Error = new StringBuilder("");
        Class classType = null;
        Object appPage4Current = null;
        Object appPage4last = null;
        String appPageName4next = new String("");
        String appPageName4nextFromWinHandle = new String("");
        wholeSeqMap = ActionXML2OperationSequence.getOperationSeq(actionSequence, this.path4Action);    //从action xml内读取所有的关键字对应

        for (int ind4ope = 0; ind4ope < actionSequence.size(); ) {    // every Key Word - single Action contains several operations 每一个关键字都涵盖若干个步骤，就是operation （对应tc的step）
            if (ind4ope == 0) {
                ind4ope = firstStepHandler(actionSequence, (IOSDriver) theDriver, ind4ope);
            }
            singleSeqMap = analyOpeSequence(wholeSeqMap, actionSequence.get(ind4ope).toString());    // get the part 4 current test case 获得当前action 关键字的所有operation步骤
            List list4currentOperation = getSortOperationList(singleSeqMap, actionSequence.get(ind4ope).toString()); // get the sorted operation list 获得排序后的operation步骤
            StringBuilder operationPage4this = null;
            operationItem tOpeItem = null;


            int ind_opeItem = 0;
            for (Object opeItem : list4currentOperation) {      // every operation Item Loop 把排序后的operation步骤一一执行
                lastOpeItemElementName4Error = new StringBuilder(currentOpeItemElementName4Error);
                ind_opeItem++; // only for index step/operation, not for location item
                System.out.println("Start to handle the " + ind_opeItem + "nd :: " + list4currentOperation.size() + " opeItem in : " + opeItem);
                System.out.println("Check the Object in the sub operation item list : " + singleSeqMap.get(opeItem).getElementName());
                operationItem currentOpeItem = singleSeqMap.get(opeItem);   // get the single operation info from operation List  获得单个operation步骤
                System.out.println("Check the sub operationItem " + currentOpeItem.getElementName());

                operationPage4this = new StringBuilder(currentOpeItem.getPageName());  // get the page info fot this operation 获得步骤所在的页面名字
                StringBuilder operationPage4Last = operationPage4this; // for possible error in next operation
                tempName4Screen = operationPage4this;   // record the operation name for possible screen shot file
                StringBuilder para4NextStep = new StringBuilder("");
                boolean flag4pageGroup = false;

                System.out.println(" ~~~~~~~~~ Start the Loop");
                for (Iterator it = pageGroup.entrySet().iterator(); it.hasNext(); ) {
                    System.out.println(" ~~~~~~~~~ loop the page group");

                    Map.Entry eleEntry = (Map.Entry) it.next();
                    String tempPageName = eleEntry.getValue().getClass().getSimpleName();
                    if (tempPageName.contains(new String(testENVpreName.toString() + operationPage4this.toString()))) {
                        System.out.println("Get the : " + tempPageName + " in the pageGroup ");
                        appPage4Current = eleEntry.getValue();      //  object form single page class
                        classType = eleEntry.getValue().getClass(); //获得类名用于反射操作
                        lastOpeItemElementName4Error = new StringBuilder(appPage4Current.toString());
                        currentOpeItemElementName4Error = new StringBuilder(currentOpeItem.getElementName());
                        flag4pageGroup = true;
                        break;
                        //    elePageName4next = commonMethodHandleIOS(currentOpeItem.getElementType(), currentOpeItem, devicePage4next, classType, extPara, this.paraIndex);
                    }
                }
                if (flag4pageGroup == true) {//如果在已经加载的页面集之内
                    //判断异常处理后的下一步所在页面是否和用例中action内的operation list的上一个元素的路由页面一致
                    if ((!appPageName4nextFromWinHandle.equals("") && (appPageName4nextFromWinHandle.equalsIgnoreCase(appPageName4next))) || (appPageName4nextFromWinHandle.equals(""))) {
                        appPageName4nextFromWinHandle = new String("");
                        appPageName4next = commonMethodHandleIOS(new StringBuilder(currentOpeItem.getElementType().toLowerCase()), currentOpeItem, appPage4Current, classType, extPara, this.paraIndex);
                    } else {
                        throw new FFPandaException("after handle trigger window, the next operation page is :" + appPageName4nextFromWinHandle + ", which is mis-match with TC : " + appPageName4next);
                    }
                } else {  //如果不在已经加载的页面集之内，则创建对应页面的对象
                    //判断异常处理后的下一步所在页面是否和用例中action内的operation list的本次operation的元素的当前页面一致
                    classType = Class.forName(new StringBuilder().append(filepath4PageClassFile).append(testENVpreName).append(operationPage4this).toString());
                    long startTime = System.currentTimeMillis();
                    appPage4Current = createInstance(classType, driver);
                    System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                    pageGroup.put(operationPage4this.toString(), appPage4Current);  //放入已加载页面集合

                    if ((!appPageName4nextFromWinHandle.equals("") && (appPageName4nextFromWinHandle.equalsIgnoreCase(appPage4Current.toString()))) || (appPageName4nextFromWinHandle.equals(""))) {
                        appPageName4nextFromWinHandle = new String("");
                        appPageName4next = commonMethodHandleIOS(new StringBuilder(currentOpeItem.getElementType().toLowerCase()), currentOpeItem, appPage4Current, classType, extPara, this.paraIndex);
                    } else {
                        throw new FFPandaException("after handle trigger window, the next operation page is :" + appPageName4nextFromWinHandle + ", which is mis-match with TC : " + appPageName4next);
                    }
                }
                System.out.println("Stop after all fork ");
                appPage4last = appPage4Current;
                classType = appPage4last.getClass();
                System.out.println(new StringBuilder("@@ classType : ").append(classType));

                if (this.eleWinListAfterClick.size() == 0) {

                } else {
                    appPageName4nextFromWinHandle = handleIOSEleTriggerWinList(eleWinListAfterClick, eleErrorWinListAfterClick, filepath4PageClassFile, testENVpreName, (IOSDriver) theDriver, pageGroup, operationPage4this, appPage4Current, tempName4Screen, currentOpeItem, new StringBuilder(appPage4last.toString()), result4exe);
                    eleWinListAfterClick = new ArrayList<StringBuilder>();
                    this.errWindowFromElement = new StringBuilder("");
                    this.confirmWindowFromElement = new StringBuilder("");
                    this.completeWindowFromElement = new StringBuilder("");
                    eleErrorWinListAfterClick.clear();
                }

                //  end the page check after every element operation done
                if ((appPageName4next != null) && (!appPageName4next.equals(""))) {
                    System.out.println("In Sub-operation list, the operation in last page done, now let's move to : " + appPage4Current);
                    appPage4Current = appPageName4next; //TODO should check the page title before this
                    if (appPage4Current.toString().endsWith("Page")) {
                        appPage4Current = appPage4Current.toString().substring(0, appPage4Current.toString().length() - 4);
                    }
                    appPage4last = appPage4Current;
                    //  appPageName4next = null;
                }

            }   // every operation Item Loop 把排序后的operation步骤一一执行 end

            if (result4exe.toString().equals("teststart")) {
                System.out.println(new StringBuffer("Single action pass : ").append(ind4ope));
            } else {
                System.out.println("result4exe has been changed : " + result4exe.toString());
            }
            ind4ope = ind4ope + 1;
            System.out.println(new StringBuffer("    ^^^^^^ Now ind4ope is :").append(ind4ope));
            System.out.println("    ^^^^^^ Check the issue in the end ");
        }  // every Key Word - single Action  END

        result4exe = resultContentCheck(result4exe, theDriver);
        if (result4exe.toString().equals("teststart")) {
            System.out.println(" All action pass ! ");
        } else {
            System.out.println(" Last action in " + appPage4last + " cause some error ! ");
        }
    }

    public StringBuilder resultContentCheck(StringBuilder inRes, WebDriver driver) throws Exception {  //TODO involve the ex para for excepted content from TC para
        System.out.println("++++++++++++++++ " + this.appInputFeedBackMode + " ++++++++++++++++");
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        if (inRes.toString().equals("teststart") && !this.content4check.toString().contains("_REX_")) {
            System.out.println("++++++++++++++++ " + inRes.toString() + " ++++++++++++++++");
            if (this.content4input.equals(this.content4check)) {
                return inRes;
            } else if (!this.contentFromExcept.equals("")) {
                return contentCheckExceptAndResult(inRes);
            } else {
                if (!this.content4check.toString().contains("_:REX:_")) {
                    return textContentCheck(inRes);
                } else if (this.content4check.toString().contains("_:REX:_") && !this.content4input.equals("") && this.contentFromExcept.equals("")) {
                    return textContentCheck(inRes);
                } else {
                    return contentCheckExceptAndResult(inRes);
                }
            }
        } else {
            if (this.contentFromExcept.equals("")) {
                // ADD the new page failure info check and compare with the ext excepted error info
                return textContentCheck(inRes);
            } else {
                //return new StringBuilder("cool");
                return contentCheckExceptAndResult(inRes);
            }
        }
    }


    public IOSDriver comOperationInIOS(IOSDriver theDriver, ArrayList<String> actionSequence, ArrayList<String> extPara, StringBuilder deviceType) throws Exception {
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
            HashMap<String, Object> pageGroup = new HashMap<String, Object>();

            //从tc获取参数，主要是步骤和对应的参数
            checkStepParaInfo(extPara);
            // android,ios,web等可能的独特的准备动作
            theDriver = (IOSDriver) targetSYSstartup(theDriver, this.deviceType, pageGroup);
            //然后进入常规动作循环
            testStepLooper(theDriver, actionSequence, extPara);
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

    private String choosePara(String a, String b, int ind) throws Exception {
        String result = null;
        if ((a != null) && (a != "")) {
            result = a;
        } else if ((b != null) && (b != "")) {
            result = b;
        } else {
            throw new FFPandaException("    +++ Parameter 4 text operation is empty at step : " + ind + 1);
        }
        return result;
    }

    private String commonMethodHandleIOS(StringBuilder operationName, operationItem cOperationItem, Object thePageInstance, Class cType, ArrayList<String> extraPara, int orgParaIndex) throws Exception {
        int newIndex = orgParaIndex;
        //  int paraIndex = orgParaIndex;
        String tempType = operationName.toString().toLowerCase();
        Object devicePage4next = thePageInstance;
        Class classType = cType;
        ArrayList<String> extPara = extraPara;
        String para4this = null;
        operationItem cOpeItem = cOperationItem;
        String resultPageName = null;
        try {
            if (tempType.contains("btn")) {
                System.out.println("Try to find the btn method !!!");

                Object nextPageName = null;
                if (!tempType.contains("_match")) {
                    Method setMethod = classType.getDeclaredMethod("btnOperationRoute", new Class[]{IOSDriver.class, String.class});
                    nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new StringBuilder(temp4date);
                        if (key4searchMatch.toString().equals("")) {
                            key4searchMatch = new StringBuilder(temp4date);
                        } else {
                            key4searchMatch = new StringBuilder(key4searchMatch + "::" + temp4date);
                        }
                    }
                } else {
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara.get(newIndex));
                    para4this = choosePara(extPara.get(newIndex), cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    Method setMethod = classType.getDeclaredMethod("btnOperationRoute", new Class[]{IOSDriver.class, String.class, String.class});
                    nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new StringBuilder(temp4date);
                        if (key4searchMatch.toString().equals("")) {
                            key4searchMatch = new StringBuilder(temp4date);
                        } else {
                            key4searchMatch = new StringBuilder(key4searchMatch + "::" + temp4date);
                        }
                    } else {
                        if (key4searchMatch.toString().equals("")) {
                            key4searchMatch = new StringBuilder(para4this);
                        } else {
                            key4searchMatch = new StringBuilder(key4searchMatch + "::" + para4this);
                        }
                    }
                }
                if ((nextPageName != null) && (!nextPageName.toString().equals(""))) {
                    if (!nextPageName.toString().equalsIgnoreCase("noRoute")) {
                        resultPageName = nextPageName.toString();
                    }
                }
                System.out.println("        &&&&&& Find the Route name for next page : " + nextPageName.toString());
            } else if (tempType.contains("text")) {
                System.out.println("Try to find the text method !!!");
                System.out.println("Check the extPara [" + newIndex + "] is : " + extPara.get(newIndex));
                para4this = choosePara(extPara.get(newIndex), cOpeItem.getElementPara(), newIndex);
                newIndex++;
                if (tempType.contains("_input")) {
                    Method setMethod = classType.getMethod("textOperationWithSaveInput", new Class[]{IOSDriver.class, String.class, String.class});
                    String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                        content4input = new StringBuilder(content4input + "::" + tempR);
                        if (key4searchMatch.toString().equals("")) {
                            key4searchMatch = new StringBuilder(tempR);
                        } else {
                            key4searchMatch = new StringBuilder(key4searchMatch + "::" + tempR);
                        }
                    } else {
                        content4input = new StringBuilder(content4input + "::" + para4this);
                        if (key4searchMatch.toString().equals("")) {
                            key4searchMatch = new StringBuilder(para4this);
                        } else {
                            key4searchMatch = new StringBuilder(key4searchMatch + "::" + para4this);
                        }
                    }
                } else {
                    Method setMethod = classType.getDeclaredMethod("textOperation", new Class[]{IOSDriver.class, String.class, String.class});
                    setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new StringBuilder(temp4date);
                }
            } else if (tempType.contains("roll")) {
                System.out.println("Try to find the roll method !!!");
                System.out.println("Check the extPara [" + newIndex + "] is : " + extPara.get(newIndex));
                para4this = choosePara(extPara.get(newIndex), cOpeItem.getElementPara(), newIndex);
                newIndex++;
                if (tempType.contains("_input")) {
                    Method setMethod = classType.getMethod("rollTheRound", new Class[]{IOSDriver.class, String.class, String.class, String.class});
                    String result4roll = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), this.date4diffPage, para4this);
                    if (result4roll == null || result4roll == "" || result4roll.equalsIgnoreCase("emptyContent")) {
                        content4input = new StringBuilder(content4input + "::" + para4this);
                        if (key4searchMatch.toString().equals("")) {
                            key4searchMatch = new StringBuilder(para4this);
                        } else {
                            key4searchMatch = new StringBuilder(key4searchMatch + "::" + para4this);
                        }
                    } else {
                        content4input = new StringBuilder(content4input + "::" + para4this);
                        if (key4searchMatch.toString().equals("")) {
                            key4searchMatch = new StringBuilder(para4this);
                        } else {
                            key4searchMatch = new StringBuilder(key4searchMatch + "::" + para4this);
                        }
                    }
                } else {
                    throw new XMLException("Roll without ext para is not ready");
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new StringBuilder(temp4date);
                }
            } else if (tempType.contains("checkpoint")) {
                System.out.println("Try to find the comPageCheck method !!!");
                System.out.println("Check the extPara [" + newIndex + "] is : " + extPara.get(newIndex));
                para4this = choosePara(extPara.get(newIndex), cOpeItem.getElementPara(), newIndex);
                Method setMethod = classType.getDeclaredMethod("comPageCheck", new Class[]{IOSDriver.class, String.class, String.class});
                setMethod.invoke(devicePage4next, theDriver, "ready", "all");
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new StringBuilder(temp4date);
                }
            } else if (tempType.contains("move")) {
                System.out.println("Try to find the move method !!!");
                if (tempType.equals("moveupsmart")) {
                    Method setMethod = classType.getDeclaredMethod("moveUPSmart", new Class[]{IOSDriver.class});
                    setMethod.invoke(devicePage4next, theDriver);
                }
            } else if (tempType.contains("checkcontent")) {
                System.out.println("Try to find the checkcontent method !!!");
                if (tempType.contains("_match")) {
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara.get(newIndex));
                    para4this = choosePara(extPara.get(newIndex), cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    Method setMethod = classType.getDeclaredMethod("getElementContent", new Class[]{IOSDriver.class, String.class, String.class});
                    String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    if (tempResult.contains("_:REX:_")) {
                        appInputFeedBackMode = new StringBuilder("duplicatedMatch");
                    } else if (tempResult.contains("_~REX~_")) {
                        appInputFeedBackMode = new StringBuilder("screenMatch");
                    }
                    content4check = new StringBuilder(content4check + "::" + tempResult);
                } else if (tempType.contains("_postfix")) {
                    Method setMethod = classType.getMethod("getElementContentPostFix", new Class[]{IOSDriver.class, String.class});
                    String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    if (tempResult.contains("_:REX:_")) {
                        appInputFeedBackMode = new StringBuilder("duplicatedMatch");
                    } else if (tempResult.contains("_~REX~_")) {
                        appInputFeedBackMode = new StringBuilder("screenMatch");
                    }
                    content4check = new StringBuilder(content4check + "::" + tempResult);
                } else {
                    Method setMethod = classType.getMethod("getElementContent", new Class[]{IOSDriver.class, String.class});
                    //content4check = new String(content4check + "::" + (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName()));
                    String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    if (tempResult.contains("_:REX:_")) {
                        appInputFeedBackMode = new StringBuilder("duplicatedMatch");
                    } else if (tempResult.contains("_~REX~_")) {
                        appInputFeedBackMode = new StringBuilder("screenMatch");
                    }
                    content4check = new StringBuilder(content4check + "::" + tempResult);
                }
            } else if (tempType.contains("pagecontent")) {
                flag4page = true;
                System.out.println("Try to find the pagecontent method !!!");
                System.out.println("Current testNG Suit is running in : " + this.autoOSType);
                Method setMethod = classType.getMethod("getPageContent", new Class[]{AndroidDriver.class, String.class});
                String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());

                if (!content4pagecheck.equals("")) {
                    //  content4pagecheck = strCompAppen(content4pagecheck, tempResult);
                    content4pagecheck = new StringBuilder(tempResult);
                }
                appInputFeedBackMode = new StringBuilder("pageMatch");
            } else if (tempType.contains("dropdownlist")) {
                System.out.println("Try to find the dropDownList method !!!");
                if (tempType.contains("_input")) {
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara.get(newIndex));
                    para4this = choosePara(extPara.get(newIndex), cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    System.out.println("para4NextStep for the dropDownList method is : " + para4this);
                    Method setMethod = classType.getMethod("dropDownListOperation", new Class[]{IOSDriver.class, String.class, String.class});
                    String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                        content4input = new StringBuilder(content4input + "::" + tempR);
                        if (key4searchMatch.toString().equals("")) {
                            key4searchMatch = new StringBuilder(tempR);
                        } else {
                            key4searchMatch = new StringBuilder(key4searchMatch + "::" + tempR);
                        }
                    } else {
                        content4input = new StringBuilder(content4input + "::" + para4this);
                        if (key4searchMatch.toString().equals("")) {
                            key4searchMatch = new StringBuilder(para4this);
                        } else {
                            key4searchMatch = new StringBuilder(key4searchMatch + "::" + para4this);
                        }
                    }
                } else if (tempType.contains("_ignore")) {
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara.get(newIndex));
                    para4this = choosePara(extPara.get(newIndex), cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    System.out.println("para4NextStep for the dropDownList method is : " + para4this);
                    Method setMethod = classType.getMethod("dropDownListOperation", new Class[]{IOSDriver.class, String.class, String.class});
                    setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                } else {
                    Method setMethod = classType.getMethod("dropDownListOperation", new Class[]{IOSDriver.class, String.class});
                    String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                        content4input = new StringBuilder(content4input + "::" + tempR);
                    } else {
                        content4input = new StringBuilder(content4input + "::" + para4this);
                    }
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new StringBuilder(temp4date);
                }
            } else if (tempType.contains("multilist")) {
                System.out.println("Try to find the multiList method !!!");
                if (tempType.contains("_input")) {
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara.get(newIndex));
                    para4this = choosePara(extPara.get(newIndex), cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    System.out.println("para4NextStep for the multiList method is : " + para4this);
                    Method setMethod = classType.getMethod("itemListOperation", new Class[]{IOSDriver.class, String.class, String.class});
                    if (!tempType.contains("_ignore")) {
                        String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                            content4input = new StringBuilder(content4input + "::" + tempR);
                            if (key4searchMatch.toString().equals("")) {
                                key4searchMatch = new StringBuilder(tempR);
                            } else {
                                key4searchMatch = new StringBuilder(key4searchMatch + "::" + tempR);
                            }
                        } else {
                            content4input = new StringBuilder(content4input + "::" + para4this);
                            if (key4searchMatch.toString().equals("")) {
                                key4searchMatch = new StringBuilder(para4this);
                            } else {
                                key4searchMatch = new StringBuilder(key4searchMatch + "::" + para4this);
                            }
                        }
                    } else {
                        setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    }
                } else {
                    para4this = choosePara(extPara.get(newIndex), cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    Method setMethod = classType.getMethod("itemListOperation", new Class[]{IOSDriver.class, String.class, String.class});
                    String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new StringBuilder(temp4date);
                }
            } else if (tempType.contains("singlelist")) {
                System.out.println("Try to find the singleList method !!!");
                if (tempType.contains("_input")) {
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara.get(newIndex));
                    para4this = choosePara(extPara.get(newIndex), cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    System.out.println("para4NextStep for the singleList method is : " + para4this);
                    Method setMethod = classType.getMethod("itemListOperation", new Class[]{IOSDriver.class, String.class, String.class});
                    if (!tempType.contains("_ignore")) {
                        String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                            content4input = new StringBuilder(content4input + "::" + tempR);
                            if (key4searchMatch.toString().equals("")) {
                                key4searchMatch = new StringBuilder(tempR);
                            } else {
                                key4searchMatch = new StringBuilder(key4searchMatch + "::" + tempR);
                            }
                        } else {
                            content4input = new StringBuilder(content4input + "::" + para4this);
                            if (key4searchMatch.toString().equals("")) {
                                key4searchMatch = new StringBuilder(para4this);
                            } else {
                                key4searchMatch = new StringBuilder(key4searchMatch + "::" + para4this);
                            }
                        }
                    } else {
                        setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    }
                } else {
                    // Method setMethod = classType.getMethod("itemListOperation", new Class[]{IOSDriver.class, String.class});
                    para4this = choosePara(extPara.get(newIndex), cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    Method setMethod = classType.getMethod("itemListOperation", new Class[]{IOSDriver.class, String.class, String.class});
                    String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new StringBuilder(temp4date);
                }
            } else if (tempType.toLowerCase().startsWith("search")) {
                System.out.println("Try to find the search method !!!");
                para4this = choosePara(extPara.get(newIndex), cOpeItem.getElementPara(), newIndex);
                newIndex++;

                Method setMethod = classType.getMethod("searchOperation", new Class[]{IOSDriver.class, String.class, String.class});
                String searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                if (searchItemContent != "" && searchItemContent != null && !searchItemContent.equalsIgnoreCase("emptyContent")) {
                    content4input = new StringBuilder(content4input + "::" + searchItemContent);
                    if (key4searchMatch.toString().equals("")) {
                        key4searchMatch = new StringBuilder(searchItemContent);
                    } else {
                        key4searchMatch = new StringBuilder(key4searchMatch + "::" + searchItemContent);
                    }
                } else {
                    content4input = new StringBuilder(content4input + "::" + para4this);
                    appInputFeedBackMode = new StringBuilder("try2match");
                    if (key4searchMatch.toString().equals("")) {
                        key4searchMatch = new StringBuilder(para4this);
                    } else {
                        key4searchMatch = new StringBuilder(key4searchMatch + "::" + para4this);
                    }
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new StringBuilder(temp4date);
                }
            } else if (tempType.toLowerCase().startsWith("boxsearch")) {
                System.out.println("Try to find the boxsearch method !!!");
                //  para4this = choosePara(extPara.get(newIndex), cOpeItem.getElementPara(), newIndex);
                //  newIndex++;
                String searchItemContent = null;
                if (tempType.toLowerCase().endsWith("_click")) {
                    Method setMethod = classType.getMethod("boxSearchClickOperation", new Class[]{IOSDriver.class, String.class});
                    setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                } else if (tempType.toLowerCase().endsWith("_getcontent")) {
                    Method setMethod = classType.getMethod("boxSearchContentOperation", new Class[]{IOSDriver.class, String.class});
                    searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    if (searchItemContent != "" && searchItemContent != null && !searchItemContent.equalsIgnoreCase("emptyContent")) {
                        content4input = new StringBuilder(content4input + "::" + searchItemContent);
                        if (key4searchMatch.toString().equals("")) {
                            key4searchMatch = new StringBuilder(searchItemContent);
                        } else {
                            key4searchMatch = new StringBuilder(key4searchMatch + "::" + searchItemContent);
                        }
                    } else {
                        content4input = new StringBuilder(content4input + "::" + para4this);
                        appInputFeedBackMode = new StringBuilder("try2match");
                        if (key4searchMatch.toString().equals("")) {
                            key4searchMatch = new StringBuilder(para4this);
                        } else {
                            key4searchMatch = new StringBuilder(key4searchMatch + "::" + para4this);
                        }
                    }
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new StringBuilder(temp4date);
                }
            } else if (tempType.contains("autocomplete")) {
                System.out.println("Try to find the autoComplete method !!!");
                para4this = choosePara(extPara.get(newIndex), cOpeItem.getElementPara(), newIndex);
                newIndex++;

                Method setMethod = classType.getMethod("autoCompleteOperation", new Class[]{IOSDriver.class, String.class, String.class});
                String searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                if (searchItemContent != "" && searchItemContent != null && !searchItemContent.equalsIgnoreCase("emptyContent")) {
                    content4input = new StringBuilder(content4input + "::" + searchItemContent);
                    if (key4searchMatch.toString().equals("")) {
                        key4searchMatch = new StringBuilder(searchItemContent);
                    } else {
                        key4searchMatch = new StringBuilder(key4searchMatch + "::" + searchItemContent);
                    }
                } else {
                    content4input = new StringBuilder(content4input + "::" + para4this);
                    appInputFeedBackMode = new StringBuilder("try2match");
                    if (key4searchMatch.toString().equals("")) {
                        key4searchMatch = new StringBuilder(para4this);
                    } else {
                        key4searchMatch = new StringBuilder(key4searchMatch + "::" + para4this);
                    }
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new StringBuilder(temp4date);
                }
            } else if (tempType.contains("sortcontent")) {
                System.out.println("Try to find the checkcontent method !!!");
                if (tempType.contains("_match")) {
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara.get(newIndex));
                    para4this = choosePara(extPara.get(newIndex), cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    Method setMethod = classType.getDeclaredMethod("sortElementContent", new Class[]{IOSDriver.class, String.class, String.class});
                    String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    appInputFeedBackMode = new StringBuilder("sortMatch");
                    content4check = new StringBuilder(content4check + "::" + tempResult);
                } else {
                    Method setMethod = classType.getMethod("sortElementContent", new Class[]{IOSDriver.class, String.class});
                    String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    appInputFeedBackMode = new StringBuilder("sortMatch");
                    content4check = new StringBuilder(content4check + "::" + tempResult);
                }
            } else {
                System.out.println("Other kind of method calling has not been completed :: " + tempType);
                throw new XMLException("Other kind of method calling has not been completed :: " + tempType);
            }
            Method checkFlagMethod = classType.getDeclaredMethod("checkFlag4Win", new Class[]{});
            boolean tempFlag = (Boolean) checkFlagMethod.invoke(devicePage4next);
            if (tempFlag == true) {
                System.out.println("ready to handle the element trigger window");
                Method getWinMapMethod = classType.getDeclaredMethod("getEleWinMap", new Class[]{});
                HashMap<String, String> tempWinMap = (HashMap<String, String>) getWinMapMethod.invoke(devicePage4next);
                for (Iterator it = tempWinMap.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry eleEntry = (Map.Entry) it.next();
                    String tempElemetName = eleEntry.getKey().toString();
                    if (tempElemetName.equals(cOpeItem.getElementName())) {
                        String tempWinName = eleEntry.getValue().toString();
                        List<StringBuilder> tempWinList = new ArrayList<StringBuilder>();
                        tempWinList.clear();
                        if (tempWinName.contains(";")) {
                            String[] tString4name = tempWinName.split(";");
                            for (int ind = 0; ind < tString4name.length; ind++) {
                                tempWinList.add(new StringBuilder(tString4name[ind]));
                            }
                        } else {
                            tempWinList.add(new StringBuilder(tempWinName));
                        }
                        //     eleWinListAfterClick = new ArrayList<String>()
                        for (StringBuilder thex : tempWinList) {
                            if (thex.toString().toLowerCase().contains("errorwin")) {
                                eleWinListAfterClick.add(thex);
                                if (thex.toString().equalsIgnoreCase("errorwin")) {
                                    this.errWindowFromElement = new StringBuilder(thex);
                                } else {
                                    eleErrorWinListAfterClick.add(thex);
                                }
                            } else if (thex.toString().toLowerCase().contains("confirmwin")) {
                                eleWinListAfterClick.add(thex);
                                this.confirmWindowFromElement = new StringBuilder(thex);
                            } else if (thex.toString().toLowerCase().contains("completewin")) {
                                eleWinListAfterClick.add(thex);
                                this.completeWindowFromElement = new StringBuilder(thex);
                            } else {
                                throw new XMLException("wrong content in triggerWindow part of page XML!!!");
                            }
                        }
                    }
                }
            } else {
                System.out.println("no element trigger window in this page");
                this.errWindowFromElement = new StringBuilder("");
                this.confirmWindowFromElement = new StringBuilder("");
                this.completeWindowFromElement = new StringBuilder("");
                eleErrorWinListAfterClick.clear();
            }
            this.paraIndex = newIndex;
            return resultPageName;
        } catch (Exception e) {
            InvocationTargetException targetEx = (InvocationTargetException) e;
            Throwable t = targetEx.getTargetException();
            System.out.println("~~~~ t.getclass.getsimplename : " + t.getClass().getSimpleName());
            if (t.getClass().getSimpleName().contains("TimeoutException")) {
                throw new TimeoutException(t);
            } else {
                System.out.println("Exception appear in BaseAction :: commonMethodHandleIOS");
                throw e;
            }
        }
    }
    // end of commonMethodHandleIOS

    public String handleIOSEleTriggerWinList(ArrayList<StringBuilder> winList, ArrayList<StringBuilder> errwinList, StringBuilder fp4XML, StringBuilder tENVpreName,
                                             IOSDriver theD, HashMap<String, Object> pageGroup,
                                             StringBuilder op4t, Object dp4n, StringBuilder tn4screen, operationItem coi, StringBuilder ep4l, StringBuilder res4exe) throws Exception {
        String result = null;
        String loopresult = null;
        for (int ind = 0; ind < winList.size(); ind++) {
            if (winList.get(ind).toString().toLowerCase().contains("errorwin")) {
                winList.remove(ind);
            }
        }
        try {
            if (!this.errWindowFromElement.equals("")) {
                boolean errResult = handleIOSEleTriggerErrorWin(this.errWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
                if (errResult == true) {
                    throw new PageTitleException("Step : " + coi.getElementName() + " Error, " + ep4l);
                } else {

                }
            }
        } catch (Exception e) {
            throw e;
        }
        if (errwinList.size() != 0) {
            try {
                for (StringBuilder tempErrWinName : errwinList) {
                    boolean errResult = handleIOSEleTriggerErrorWin(tempErrWinName, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
                    if (errResult == true) {
                        throw new PageTitleException("Step : " + coi.getElementName() + " Error, " + ep4l);
                    } else {

                    }
                }
            } catch (Exception e) {
                throw e;
            }
        }
        for (int ind = 0; ind < winList.size() && winList.size() != 0; ) {
            try {
                if (winList.get(ind).equals(this.confirmWindowFromElement)) {
                    loopresult = handleIOSEleTriggerConfirmWin(this.confirmWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen);
                    if (loopresult != null) {
                        winList.remove(ind);
                        ind = 0;
                    } else {
                        ind++;
                    }
                } else if (winList.get(ind).equals(this.completeWindowFromElement)) {
                    throw new FFPandaException("Complete win is not supported");
                }
                if (!this.errWindowFromElement.equals("")) {
                    boolean errResult = handleIOSEleTriggerErrorWin(this.errWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
                    if (errResult == true) {
                        throw new PageTitleException("Step : " + coi.getElementName() + " Error, " + ep4l);
                    } else {

                    }
                }
                if (ind != 0) {
                    for (StringBuilder tempErrWinName : errwinList) {
                        boolean errResult = handleIOSEleTriggerErrorWin(tempErrWinName, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
                        if (errResult == true) {
                            throw new PageTitleException("Step : " + coi.getElementName() + " Error, " + ep4l);
                        } else {

                        }
                    }
                }
            } catch (Exception e) {
                throw e;
            }
        }
        result = loopresult;
        return result;
    }


    public boolean handleIOSEleTriggerErrorWin(StringBuilder winname, StringBuilder fp4XML, StringBuilder tENVpreName, IOSDriver theD, HashMap<String, Object> pageGroup,
                                               StringBuilder op4t, Object dp4n, StringBuilder tn4screen, operationItem coi, StringBuilder ep4l, StringBuilder res4exe) throws Exception {
        boolean result = false;
        System.out.println(winname);
        Class tempClassType = Class.forName(String.valueOf(new StringBuilder(fp4XML).append(tENVpreName).append(winname)));
        System.out.println("@@@ tempClassType : " + tempClassType);
        Object tempIns = createInstance(tempClassType, theD);
        pageGroup.put(op4t.toString(), dp4n);
        Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{IOSDriver.class, String.class, String.class});
        Object comPageCheckResult = setMethod.invoke(tempIns, theD, "ready", "errorTitle");
        if (((String) comPageCheckResult).equals("pass")) {
            setMethod = tempClassType.getDeclaredMethod("getElementContent", new Class[]{IOSDriver.class, String.class});
            Object messageInWin = setMethod.invoke(tempIns, theD, "errorMessage");
            if (messageInWin.toString().length() > 30) {
                appInputFeedBackMode = new StringBuilder("try2match");
            }
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(date);
            String tSpLast = dp4n.getClass().getSimpleName();
            ScreenShot(theD, "ErrorNoticeWindow_" + tSpLast + "_From_" + tn4screen + time + ".png", this.path4Log);
            setMethod = tempClassType.getDeclaredMethod("btnOperationRoute", new Class[]{IOSDriver.class, String.class});
            Object btnRouteResult = setMethod.invoke(tempIns, theD, "OKBtn");//  define all btn  name for all notice window
            res4exe.delete(0, res4exe.length());
            res4exe.append(messageInWin.toString());  //  complete the execution result code for TC layer judge
            result = true;
            throw new PageTitleException("Step : " + coi.getElementName() + " Error, " + ep4l + " , " + messageInWin);
        } else {
        }
        return result;
    }


    public String handleIOSEleTriggerConfirmWin(StringBuilder winname, StringBuilder fp4XML, StringBuilder tENVpreName, IOSDriver theD, HashMap<String, Object> pageGroup,
                                                StringBuilder op4t, Object dp4n, StringBuilder tn4screen) throws Exception {
        String result = null;
        System.out.println(winname);
        Class tempClassType = Class.forName(String.valueOf(new StringBuilder(fp4XML).append(tENVpreName).append(winname)));
        System.out.println("@@@ tempClassType : " + tempClassType);
        Object tempIns = createInstance(tempClassType, theD);
        pageGroup.put(op4t.toString(), dp4n);
        Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{IOSDriver.class, String.class, String.class});
        Object comPageCheckResult = setMethod.invoke(tempIns, theD, "ready", "noBtn");
        //  noBtn
        if (((String) comPageCheckResult).equals("pass")) {
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(date);
            String tSpLast = dp4n.getClass().getSimpleName();
            ScreenShot(theD, "ConfirmWindow_" + tSpLast + "_From_" + tn4screen + time + ".png", this.path4Log);
            setMethod = tempClassType.getDeclaredMethod("btnOperationRoute", new Class[]{IOSDriver.class, String.class});
            Object btnRouteResult = setMethod.invoke(tempIns, theD, "yesBtn");//  define all btn  name for all notice window
            result = (String) btnRouteResult;  //

        } else {
        }
        return result;
    }


    public StringBuilder contentCheckExceptAndResult(StringBuilder inRes) throws Exception {
        String tResult = "";
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        boolean flag4match = false;
        if (this.content4check.toString().equals("")) {
            this.content4check = new StringBuilder(inRes.toString());
            appInputFeedBackMode = new StringBuilder("try2match");
        }
        if (this.contentFromExcept.toString().startsWith("::")) {
            this.contentFromExcept = new StringBuilder(this.contentFromExcept.toString().replaceFirst("::", ""));
        }
        if (this.contentFromExcept.toString().endsWith("::")) {
            this.contentFromExcept = new StringBuilder(this.contentFromExcept.substring(0, this.contentFromExcept.length() - 2));
        }
        if (this.content4check.toString().startsWith("::")) {
            this.content4check = new StringBuilder(this.content4check.toString().replaceFirst("::", ""));
        }
        if (this.content4check.toString().endsWith("::")) {
            this.content4check = new StringBuilder(this.content4check.substring(0, this.content4check.length() - 2));
        }
        if (this.content4input.toString().startsWith("::::")) {
            this.content4input = new StringBuilder(this.content4input.toString().replaceFirst("::::", ""));
        }
        if (this.content4input.toString().startsWith("::")) {
            this.content4input = new StringBuilder(this.content4input.toString().replaceFirst("::", ""));
        }
        if (this.content4input.toString().endsWith("::")) {
            this.content4input = new StringBuilder(this.content4check.substring(0, this.content4input.length() - 2));
        }
        System.out.println("*** in contentCheckExceptAndResult ***");
        System.out.println("*here is the except  : " + this.contentFromExcept + "; length : " + this.contentFromExcept.length());
        System.out.println("*here is the result : " + this.content4check + "; length : " + this.content4check.length());
        try {
            if (this.contentFromExcept.length() != this.content4check.length()) {
                if (appInputFeedBackMode.toString().equalsIgnoreCase("try2match") || appInputFeedBackMode.toString().equalsIgnoreCase("sortmatch")) {
                    if (this.content4check.toString().toLowerCase().contains(this.contentFromExcept.toString().toLowerCase())) {
                        tResult = new String("teststart");
                        //result = new StringBuilder("teststart");
                    }
                } else if (!this.content4input.equals("") && this.contentFromExcept.length() == 0) {
                    System.out.println("Temp solution, empty expected result AND some thing in input:" + this.content4input);
                } else {
                    if ((Math.abs(this.contentFromExcept.length() - this.content4check.length()) < 10) && (this.contentFromExcept.toString().contains("::")) && (this.content4check.toString().contains("::"))) {
                        StringBuilder newCon4Except = new StringBuilder("");
                        StringBuilder newCon4Check = new StringBuilder("");
                        for (String temp : this.contentFromExcept.toString().split("::")) {
                            newCon4Except = new StringBuilder(newCon4Except + filter4space(temp));
                        }
                        for (String temp : this.content4check.toString().split("::")) {
                            newCon4Check = new StringBuilder(newCon4Check + filter4space(temp));
                        }
                        if (newCon4Except.equals(newCon4Check)) {
                            this.appInputFeedBackMode = new StringBuilder("OtherMode");
                            tResult = new String("teststart");
                        } else {
                            tResult = "content length mis-match , input : " + newCon4Except.length() + " , result : " + newCon4Check.length();
                            ScreenShot(theDriver, "content length mis-match " + time + ".png", this.path4Log);
                        }
                    } else {
                        tResult = "content length mis-match , input : " + this.contentFromExcept.length() + " , result : " + this.content4check.length();
                        ScreenShot(theDriver, "content length mis-match " + time + ".png", this.path4Log);
                    }
                }
            }
            if (appInputFeedBackMode.toString().equalsIgnoreCase("exact")) {
                if ((this.contentFromExcept != null) && (!this.contentFromExcept.toString().equals("")) && (this.content4check != null) && (!this.content4check.toString().equals(""))) {
                    if (this.content4check.equals(this.contentFromExcept)) {
                        tResult = new String(inRes.toString());
                        System.out.println("result in SB : " + tResult);
                    } else {
                        tResult = new String("Element content failed to match excepted");
                        ScreenShot(theDriver, "nomatch_exceptResult" + time + ".png", this.path4Log);
                    }
                } else if ((this.content4check != null) && (!this.content4check.toString().equals(""))) {
                    tResult = new String(content4check);
                } else if ((this.contentFromExcept != null) && (!this.contentFromExcept.toString().equals(""))) {
                    tResult = new String("contentFromExcept missing in TC");
                    ScreenShot(theDriver, "missing_exceptResult" + time + ".png", this.path4Log);
                } else {
                    tResult = new String(inRes.toString());
                }
            } else if (appInputFeedBackMode.toString().equalsIgnoreCase("screenMatch")) {

            } else if (appInputFeedBackMode.toString().equalsIgnoreCase("pageMatch")) {
                boolean result = true;
                String[] excResult = this.contentFromExcept.toString().split(";");
                for (int ind = 0; (ind < excResult.length) && result == true; ind++) {

                    String[] singlePartResult = excResult[ind].split(" ");
                    for (int dind = 0; (dind < singlePartResult.length) && result == true; dind++) {
                        System.out.println("~~~search : " + singlePartResult[dind]);
                        if (!this.content4check.toString().toLowerCase().contains(singlePartResult[dind].toLowerCase())) {
                            result = false;
                            break;
                        } else {
                            System.out.println("start : " + this.content4check.indexOf(singlePartResult[dind]));
                            System.out.println("end : " + this.content4check.length());
                            this.content4check = new StringBuilder(this.content4check.toString().substring(this.content4check.toString().indexOf(singlePartResult[dind]), this.content4check.toString().length()));  //TODO cut too much, should be length()
                            System.out.println(this.content4check);
                        }
                    }
                    if (result == true) {
                        tResult = new String("teststart");
                    } else {
                        tResult = new String("page content failed to match excepted");
                    }
                }
            } else if (appInputFeedBackMode.toString().equalsIgnoreCase("duplicatedMatch"))       // for IOS duplicatedMatch
            {
                if (this.content4input.toString().startsWith("::")) {
                    this.content4input = new StringBuilder(this.content4input.toString().replaceFirst("::", ""));
                }
                ArrayList<String> extraList = new ArrayList<String>();
                ArrayList<String> duplicatedList = new ArrayList<String>();
                ArrayList<String> checkList = new ArrayList<String>();

                for (String temp : this.content4input.toString().split("::")) {
                    if (temp.contains("_REX_")) {
                        for (String subStr : temp.split("_REX_")) {
                            checkList.add(subStr);
                        }
                    } else {
                        checkList.add(temp);
                    }
                }
                for (String temp : this.content4check.toString().split("::")) {
                    if (temp.contains("_:REX:_")) {
                        duplicatedList.add(temp);
                    } else {
                        extraList.add(temp);
                    }
                }


                //TODO
                // extra match first, delete the matched item from list, should follow the seque in the input list
                boolean flag4del = false;
                boolean flag4restart = false;
                for (int ind = 0; ind < checkList.size(); ind++) {
                    if (flag4restart == true) {
                        ind = 0;
                    }
                    for (int exrind = 0; exrind < extraList.size(); exrind++) {
                        if (extraList.get(exrind).equals(checkList.get(ind))) {
                            extraList.remove(exrind);
                            flag4del = true;
                            break;
                        }
                    }
                    if (flag4del == true) {
                        checkList.remove(ind);
                        flag4restart = true;
                        flag4del = false;
                    }
                    if (checkList.size() == 0 || extraList.size() == 0) {
                        break;
                    }
                }


                if (extraList.size() == 0) {
                    flag4match = true;
                } else {
                    tResult = new String("Extra compare 4 duplicatedMatch failed : " + extraList.get(0));
                    ScreenShot(theDriver, "textContent_nomatch_" + time + ".png", this.path4Log);
                }

                duplicatedList = removeDuplicate(duplicatedList);
                ArrayList<String> fuzzyList = new ArrayList<String>();

                for (String temp : duplicatedList) {
                    String[] tList = temp.split("_:REX:_");
                    for (String t : tList) {
                        fuzzyList.add(t);
                    }
                }
                // after extra list match, try the rest part from the remain input list, don't delete the dup list , only try next item in the remain input list
                fuzzyList = removeDuplicate(fuzzyList);
                flag4restart = false;
                for (int ind = 0; ind < checkList.size() || checkList.size() == 1; ind++) {
                    if (flag4restart == true) {
                        ind = 0;
                    }
                    for (int find = 0; find < fuzzyList.size(); find++) {
                        if (fuzzyList.get(find).equals(checkList.get(ind))) {
                            fuzzyList.remove(find);
                            flag4del = true;
                            break;
                        }
                    }
                    if (flag4del == true) {
                        checkList.remove(ind);
                        flag4restart = true;
                        flag4del = false;
                    }
                    if (checkList.size() == 0 || fuzzyList.size() == 0) {
                        break;
                    }
                }
                if (checkList.size() == 1 && fuzzyList.size() == 1) {
                    if (checkList.get(0).equals(fuzzyList.get(0))) {
                        flag4match = true;
                        tResult = new String(inRes.toString());
                    } else {
                        tResult = new String("Element content failed to match input in duplicatedMatch");
                        flag4match = false;
                    }
                } else if (checkList.size() == 0) {
                    flag4match = true;
                    tResult = new String(inRes.toString());
                } else {
                    tResult = new String("checkList is not empty, compare 4 duplicatedMatch failed : " + checkList.get(0));
                    ScreenShot(theDriver, "textContent_nomatch_" + time + ".png", this.path4Log);
                }

            } else if (appInputFeedBackMode.toString().equalsIgnoreCase("sortMatch")) {
                ArrayList<String> extraList = new ArrayList<String>();
                ArrayList<String> duplicatedList = new ArrayList<String>();

                for (String temp : this.content4check.toString().split("::")) {
                    if (temp.contains("_:REX:_")) {
                        duplicatedList.add(temp);
                    } else {
                        extraList.add(temp);
                    }
                }

                if (duplicatedList.size() != 0) {
                    duplicatedList = removeDuplicate(duplicatedList);
                    ArrayList<String> fuzzyList = new ArrayList<String>();

                    for (String temp : duplicatedList) {
                        String[] tList = temp.split("_:REX:_");
                        for (String t : tList) {
                            if (t != "") {
                                fuzzyList.add(t);
                            } else {
                            }
                        }
                    }
                    if (fuzzyList.size() == 1) {
                        flag4match = true;
                    } else {
                        ArrayList<String> rowStr = new ArrayList<String>();
                        ArrayList<Integer> rowDig = new ArrayList<Integer>();
                        ArrayList<Integer> pSort = new ArrayList<Integer>();
                        ArrayList<Integer> nSort = new ArrayList<Integer>();
                        for (String subRow : fuzzyList) {
                            String[] newsubRow = subRow.replaceAll("/", "").replaceAll("[^0-9]", ",").split(",");
                            for (String digRow : newsubRow) {
                                if (digRow != "" && digRow.length() > 0) {
                                    if (digRow != "0") {
                                        rowStr.add(digRow.replaceFirst("0", ""));
                                    }
                                    rowDig.add(Integer.parseInt(digRow));
                                    pSort.add(Integer.parseInt(digRow));
                                    nSort.add(Integer.parseInt(digRow) * -1);
                                }
                            }
                        }
                        sort(pSort);
                        sort(nSort);
                        ArrayList<Integer> finalNSort = new ArrayList<Integer>();

                        for (int i = 0; i < nSort.size(); i++) {
                            finalNSort.add(nSort.get(i) * -1);
                        }
                        if (compareSortList(rowDig, pSort)) {
                            flag4match = true;
                        } else if (compareSortList(rowDig, finalNSort)) {
                            flag4match = true;
                        } else {
                            flag4match = false;
                        }
                    }

                } else {
                    flag4match = true;
                }
            } else {
                //TODO
            }
            System.out.println("result in SB end : " + tResult);
            StringBuilder result = new StringBuilder(tResult);
            return result;
        } catch (Exception e) {
            // result.append("Exception appear during element content compare");
            ScreenShot(theDriver, "runtime_Exception" + time + ".png", this.path4Log);
            throw new FFPandaException("BaseAction : contentCheckExceptAndResult : " + e.getMessage());
        }
    }


    public static boolean compareSortList(ArrayList row, ArrayList target) {
        boolean result = false;
        if (row.size() != target.size()) {
            result = false;
        } else {
            for (int i = 0; i < row.size(); i++) {
                if (row.get(i).equals(target.get(i))) {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public static ArrayList<String> removeDuplicate(ArrayList arlList) {
        LinkedHashSet<String> set = new LinkedHashSet<String>(arlList);
        arlList.clear();
        arlList = new ArrayList<String>(set);
        System.out.println("");
        return arlList;
    }

    public static String filter4space(String s) {
        s = new String(s.replaceAll("\n", " "));
        int i = s.length();// 字符串最后一个字符的位置
        int j = 0;// 字符串第一个字符
        int k = 0;// 中间变量
        char[] arrayOfChar = s.toCharArray();// 将字符串转换成字符数组
        while ((j < i) && (arrayOfChar[(k + j)] <= ' '))
            ++j;// 确定字符串前面的空格数
        while ((j < i) && (arrayOfChar[(k + i - 1)] <= ' '))
            --i;// 确定字符串后面的空格数

        return (((j > 0) || (i < s.length())) ? s.substring(j, i) : s);// 返回去除空格后的字符串

    }

    public StringBuilder textContentCheck(StringBuilder inRes) throws Exception {
        StringBuilder result = new StringBuilder();
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        boolean flag4match = false;
        if (this.content4input.toString().startsWith("::")) {
            this.content4input = new StringBuilder(this.content4input.toString().replaceFirst("::", ""));
        }
        if (this.content4input.toString().endsWith("::")) {
            this.content4input = new StringBuilder(this.content4input.substring(0, this.content4input.length() - 2));
        }
        if (this.content4check.toString().startsWith("::")) {
            this.content4check = new StringBuilder(this.content4check.toString().replaceFirst("::", ""));
        }
        if (this.content4check.toString().endsWith("::")) {
            this.content4check = new StringBuilder(this.content4check.substring(0, this.content4check.length() - 2));
        }
        System.out.println("*** in textContentCheck ***");
        System.out.println("*here is the input  : " + this.content4input + "; length : " + this.content4input.length());
        System.out.println("*here is the result : " + this.content4check + "; length : " + this.content4check.length());
        try {
            if (appInputFeedBackMode.toString().equalsIgnoreCase("exact")) {  // for exact
                if (this.content4input.length() != this.content4check.length()) {
                    if (content4check.toString().contains("_REX_") || content4input.toString().contains("_REX_")) {
                        appInputFeedBackMode = new StringBuilder("try2match"); //TODO
                    } else {
                        result.append("content length mis-match , input : " + this.content4input.length() + " , result : " + this.content4check.length());
                        ScreenShot(theDriver, "content length mis-match " + time + ".png", this.path4Log);
                    }
                } else {
                    if ((this.content4input != null) && (!this.content4input.toString().equals("")) && (this.content4check != null) && (!this.content4check.toString().equals(""))) {
                        if (this.content4check.equals(this.content4input)) {
                            result.append("teststart");
                            System.out.println("result in SB : " + result);
                        } else {
                            result.append("Element content failed to match input");
                            ScreenShot(theDriver, "textContent_nomatch_" + time + ".png", this.path4Log);
                        }
                    } else if ((this.content4check != null) && (!this.content4check.toString().equals(""))) {
                        result.append(content4check);
                    } else if ((this.content4input != null) && (!this.content4check.toString().equals(""))) {
                        result.append("checkcontent line missing in operation XML");
                        ScreenShot(theDriver, "textContent_missing_" + time + ".png", this.path4Log);
                    } else {
                        result.append(inRes);
                    }
                }
            }
            if (appInputFeedBackMode.toString().equalsIgnoreCase("try2match")) {// for try2mtach
                if ((this.content4input != null) && (!this.content4input.toString().equals("")) && (this.content4check != null) && (!this.content4check.toString().equals(""))) {
                    if (this.content4check.equals(this.content4input)) {
                        result.append(inRes);
                        System.out.println("result in SB : " + result);
                    } else if (!content4check.toString().contains("_REX_") && !content4input.toString().contains("_REX_")) {
                        String[] tempInput = content4input.toString().split("::");
                        String[] tempCheck = content4check.toString().split("::");
                        //boolean flag4match = false;
                        for (int ind = 0; ind < tempInput.length && ind < tempCheck.length; ind++) {
                            if (tempCheck[ind].toLowerCase().contains(tempInput[ind].toLowerCase()) || tempInput[ind].toLowerCase().contains(tempCheck[ind].toLowerCase())) {
                                flag4match = true;
                            } else {
                                flag4match = false;
                            }
                        }
                        if (flag4match == true) {
                            result.append(inRes);
                        } else {
                            result.append("Element content failed to match input");
                            ScreenShot(theDriver, "textContent_nomatch_" + time + ".png", this.path4Log);
                        }
                    } else {
                        if (content4input.toString().startsWith("::")) {
                            content4input = new StringBuilder(content4input.toString().replaceFirst("::", ""));
                        }
                        if (content4check.toString().startsWith("::")) {
                            content4check = new StringBuilder(content4check.toString().replaceFirst("::", ""));
                        }

                        String temp1 = content4input.toString().replaceAll("_REX_", "::");
                        String temp2 = content4check.toString().replaceAll("_REX_", "::");

                        String[] tempInput = temp1.split("::");
                        String[] tempCheck = temp2.split("::");
                        for (int index = 0; index < tempInput.length; index++) {
                            for (int ind = 0; ind < tempCheck.length; ind++) {
                                if (tempCheck[ind].toLowerCase().contains(tempInput[index].toLowerCase())) {
                                    flag4match = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (flag4match == true) {
                    result.append(inRes);
                } else {
                    result.append("Element content failed to match input");
                    ScreenShot(theDriver, "textContent_nomatch_" + time + ".png", this.path4Log);
                }
            } else if (appInputFeedBackMode.toString().equalsIgnoreCase("screenMatch")) {
                if (this.content4input.equals("")) {
                    flag4match = true;
                } else {
                    ArrayList<String> extraList = new ArrayList<String>();
                    ArrayList<String> duplicatedList = new ArrayList<String>();
                    ArrayList<String> checkList = new ArrayList<String>();

                    for (String temp : this.content4input.toString().split("::")) {
                        checkList.add(temp);
                    }
                    for (String temp : this.content4check.toString().split("::")) {
                        if (temp.contains("_~REX~_")) {
                            duplicatedList.add(temp);
                        } else {
                            extraList.add(temp);
                        }
                    }
                    // extra match first, delete the matched item from list, should follow the seque in the input list
                    boolean flag4del = false;
                    boolean flag4restart = false;
                    for (int ind = 0; ind < checkList.size(); ind++) {
                        if (flag4restart == true) {
                            ind = 0;
                            flag4restart = false;
                        }
                        for (int exrind = 0; exrind < extraList.size(); exrind++) {
                            if (extraList.get(exrind).equals(checkList.get(ind))) {
                                extraList.remove(exrind);
                                flag4del = true;
                                break;
                            }
                        }
                        if (flag4del == true) {
                            checkList.remove(ind);
                            flag4del = false;
                            flag4restart = true;
                        }
                        if (checkList.size() == 0 || extraList.size() == 0) {
                            break;
                        }
                    }
                    duplicatedList = removeDuplicate(duplicatedList);
                    ArrayList<String> fuzzyList = new ArrayList<String>();

                    for (String temp : duplicatedList) {
                        String[] tList = temp.split("_~REX~_");
                        for (String t : tList) {
                            fuzzyList.add(t);
                        }
                    }
                    // after extra list match, try the rest part from the remain input list, don't delete the dup list , only try next item in the remain input list
                    fuzzyList = removeDuplicate(fuzzyList);
                    for (int ind = 0; ind < checkList.size(); ind++) {
                        for (int find = 0; find < fuzzyList.size(); find++) {
                            if (fuzzyList.get(find).equals(checkList.get(ind))) {
                                fuzzyList.remove(find);
                                flag4del = true;
                                break;
                            }
                        }
                        if (flag4del == true) {
                            checkList.remove(ind);
                            ind = 0;
                            flag4del = false;
                        }
                        if (checkList.size() == 0 || fuzzyList.size() == 0) {
                            break;
                        }
                    }
                    if (checkList.size() == 1 && fuzzyList.size() == 1) {
                        if (checkList.get(0).equals(fuzzyList.get(0))) {
                            flag4match = true;
                        } else {
                            flag4match = false;
                        }
                    } else if (checkList.size() == 0) {
                        flag4match = true;
                    } else {
                        result.append("Extra compare 4 screenMatch failed : " + checkList.get(0));
                        ScreenShot(theDriver, "textContent_nomatch_" + time + ".png", this.path4Log);
                    }
                }
                if (flag4match == true) {
                    result.append(inRes);
                } else {
                    result.append("Element content failed to match input");
                    ScreenShot(theDriver, "textContent_nomatch_" + time + ".png", this.path4Log);
                }
            } else if (appInputFeedBackMode.toString().equalsIgnoreCase("sortMatch")) {
                ArrayList<String> extraList = new ArrayList<String>();
                ArrayList<String> duplicatedList = new ArrayList<String>();

                for (String temp : this.content4check.toString().split("::")) {
                    if (temp.contains("_:REX:_")) {
                        duplicatedList.add(temp);
                    } else {
                        extraList.add(temp);
                    }
                }

                if (duplicatedList.size() != 0) {
                    duplicatedList = removeDuplicate(duplicatedList);
                    ArrayList<String> fuzzyList = new ArrayList<String>();

                    for (String temp : duplicatedList) {
                        String[] tList = temp.split("_:REX:_");
                        for (String t : tList) {
                            if (t != "") {
                                fuzzyList.add(t);
                            } else {
                            }
                        }
                    }
                    //  fuzzyList = removeDuplicate(fuzzyList);
                    if (fuzzyList.size() == 1) {
                        flag4match = true;
                    } else {
                        ArrayList<String> rowStr = new ArrayList<String>();
                        ArrayList<Integer> rowDig = new ArrayList<Integer>();
                        ArrayList<Integer> pSort = new ArrayList<Integer>();
                        ArrayList<Integer> nSort = new ArrayList<Integer>();
                        for (String subRow : fuzzyList) {
                            String[] newsubRow = subRow.replaceAll("/", "").replaceAll("[^0-9]", ",").split(",");
                            for (String digRow : newsubRow) {
                                if (digRow != "" && digRow.length() > 0) {
                                    if (digRow != "0") {
                                        rowStr.add(digRow.replaceFirst("0", ""));
                                    }
                                    rowDig.add(Integer.parseInt(digRow));
                                    pSort.add(Integer.parseInt(digRow));
                                    nSort.add(Integer.parseInt(digRow) * -1);
                                }
                            }
                        }

                        sort(pSort);
                        sort(nSort);
                        ArrayList<Integer> finalNSort = new ArrayList<Integer>();

                        for (int i = 0; i < nSort.size(); i++) {
                            finalNSort.add(nSort.get(i) * -1);
                        }
                        if (compareSortList(rowDig, pSort)) {
                            flag4match = true;
                        } else if (compareSortList(rowDig, finalNSort)) {
                            flag4match = true;
                        } else {
                            flag4match = false;
                        }
                    }

                } else {
                    flag4match = true;
                }
            } else if (appInputFeedBackMode.toString().equalsIgnoreCase("duplicatedMatch"))       // for IOS duplicatedMatch
            {
                if (this.content4input.toString().startsWith("::")) {
                    this.content4input = new StringBuilder(this.content4input.toString().replaceFirst("::", ""));
                }
                ArrayList<String> extraList = new ArrayList<String>();
                ArrayList<String> duplicatedList = new ArrayList<String>();
                ArrayList<String> checkList = new ArrayList<String>();

                for (String temp : this.content4input.toString().split("::")) {
                    if (temp.contains("_REX_")) {
                        for (String subStr : temp.split("_REX_")) {
                            checkList.add(subStr);
                        }
                    } else {
                        checkList.add(temp);
                    }
                }
                for (String temp : this.content4check.toString().split("::")) {
                    if (temp.contains("_:REX:_")) {
                        duplicatedList.add(temp);
                    } else {
                        extraList.add(temp);
                    }
                }
                //TODO
                // extra match first, delete the matched item from list, should follow the seque in the input list
                boolean flag4del = false;
                boolean flag4restart = false;
                for (int ind = 0; ind < checkList.size(); ind++) {
                    if (flag4restart == true) {
                        ind = 0;
                    }
                    for (int exrind = 0; exrind < extraList.size(); exrind++) {
                        if (extraList.get(exrind).equals(checkList.get(ind))) {
                            extraList.remove(exrind);
                            flag4del = true;
                            break;
                        } else {
                            if (checkList.size() > 1) {
                                for (int index4deep = 0; index4deep < checkList.size(); index4deep++) {
                                    if (checkList.get(index4deep).equals(extraList.get(exrind))) {
                                        extraList.remove(exrind);
                                        checkList.remove(index4deep);
                                        flag4restart = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (flag4del == true) {
                        checkList.remove(ind);
                        flag4restart = true;
                        flag4del = false;
                    }
                    if (checkList.size() == 0 || extraList.size() == 0) {
                        break;
                    }
                }

                if (extraList.size() == 0) {
                    flag4match = true;
                } else {
                    result.append("Extra compare 4 duplicatedMatch failed : " + extraList.get(0));
                    ScreenShot(theDriver, "textContent_nomatch_" + time + ".png", this.path4Log);
                }

                duplicatedList = removeDuplicate(duplicatedList);
                ArrayList<String> fuzzyList = new ArrayList<String>();

                for (String temp : duplicatedList) {
                    String[] tList = temp.split("_:REX:_");
                    for (String t : tList) {
                        fuzzyList.add(t);
                    }
                }

                // after extra list match, try the rest part from the remain input list, don't delete the dup list , only try next item in the remain input list
                fuzzyList = removeDuplicate(fuzzyList);
                flag4restart = false;
                for (int ind = 0; ind < checkList.size() || checkList.size() == 1; ind++) {
                    if (flag4restart == true) {
                        ind = 0;
                    }
                    for (int find = 0; find < fuzzyList.size(); find++) {
                        if (fuzzyList.get(find).equalsIgnoreCase(checkList.get(ind))) {
                            fuzzyList.remove(find);
                            flag4del = true;
                            break;
                        }
                    }
                    if (flag4del == true) {
                        checkList.remove(ind);
                        flag4restart = true;
                        flag4del = false;
                    }
                    if (checkList.size() == 0 || fuzzyList.size() == 0) {
                        break;
                    }
                }
                for (int ind = 0; ind < checkList.size() || checkList.size() == 1; ind++) {
                    if (flag4restart == true) {
                        ind = 0;
                    }
                    for (int find = 0; find < fuzzyList.size(); find++) {
                        if (fuzzyList.get(find).toLowerCase().contains(checkList.get(ind).toLowerCase())) {
                            fuzzyList.remove(find);
                            flag4del = true;
                            break;
                        }
                    }
                    if (flag4del == true) {
                        checkList.remove(ind);
                        flag4restart = true;
                        flag4del = false;
                    }
                    if (checkList.size() == 0 || fuzzyList.size() == 0) {
                        break;
                    }
                }
                if (checkList.size() == 1 && fuzzyList.size() == 1) {
                    if (checkList.get(0).equals(fuzzyList.get(0)) || fuzzyList.get(0).contains(checkList.get(0).toLowerCase())) {
                        flag4match = true;
                        result.append(inRes.toString());
                    } else {
                        result.append("Element content failed to match input in duplicatedMatch");
                        flag4match = false;
                    }
                } else if (checkList.size() == 0) {
                    flag4match = true;
                    result.append(inRes.toString());
                } else {
                    result.append("checkList is not empty, compare 4 duplicatedMatch failed : " + checkList.get(0));
                    ScreenShot(theDriver, "textContent_nomatch_" + time + ".png", this.path4Log);
                }


            }
            if (flag4match != true || !result.toString().equals("teststart")) {
                if (appInputFeedBackMode.toString().equalsIgnoreCase("sortMatch") && flag4match == true) {
                    result.append("teststart");
                } else if ((this.content4check != null) && (!this.content4check.toString().equals(""))) {
                    result.append(content4check);
                } else if ((this.content4input != null) && (!this.content4input.toString().equals(""))) {
                    result.append("check content line missing in operation XML");
                    ScreenShot(theDriver, "textContent_missing_" + time + ".png", this.path4Log);
                } else {
                    result.append(inRes);
                }
            }
            System.out.println("result in Sub end : " + result);
            return result;
        } catch (Exception e) {
            result.append("Exception appear during element content compare");
            ScreenShot(theDriver, "runtime_Exception" + time + ".png", this.path4Log);
            throw new FFPandaException("BaseAction : textContentCheck : " + e.getMessage());
        }
    }

}
