package Component.AppAction;

import Component.Page.ANDPageContent;
import Component.Page.Element.ElementMapping;
import Component.Page.Element.operationItem;
import LocalException.FFPandaException;
import LocalException.PageTitleException;
import LocalException.XMLException;
import dataModel.TestPlatformData;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import utility.StringB;


import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static Component.Driver.ScreenShot.ScreenShot;
import static utility.XmlUtils.readActionXMLDocument;
import static java.util.Collections.sort;

public class BaseAction {   // 目标是将基本的路由结果检查,然后在page对象内增加action对象
    String autoOSType = TestPlatformData.getAutoOSType().toString();
    private int operationType = 0; // default 0 for review, 1 for Edit, 2 for Remove, 3 for search, 4 for route

    protected String path4Page = ("\\PageXML\\");   //  file path 4 xml, "/PageXML/AND/" for And, "/PageXML/IOS/"; for IOS 页面文件路径

    protected String path4Action = ("\\OperationXML\\Action\\");
    //   protected IOSPageLogin basePageIOS = null;
//    protected IOSPageNaviBar theNaviBarIOS = null;
    //   protected IOSPageHome theHomeIOS = null;
    protected StringBuffer result4exe = null;

    protected AppiumDriver theDriver = null;
    protected WebDriver theWebDriver = null;
    protected String[] theActionSeq = null;

    protected String path4Log = null;
    protected String content4input = "";
    protected String content4check = "";
    protected String content4pagecheck = "";
    protected boolean flag4page = false;
    protected String contentFromExcept = "";
    protected String[][] classNameMap = null;
    public int paraIndex = 0;
    protected String appInputFeedBackMode = "exact";
    protected String date4diffPage = "";
    protected String key4searchMatch = "";
    protected String errWindowFromElement = "";
    protected String confirmWindowFromElement = "";
    protected String completeWindowFromElement = "";
    protected ArrayList<String> eleWinListAfterClick = new ArrayList<String>();
    protected ArrayList<String> eleErrorWinListAfterClick = new ArrayList<String>();
    private static String logSpace4thisPage = " 000 BaseAction 000 ";

    public BaseAction(AppiumDriver d, String cp, int opT, String[] inSeq) throws Exception {
        if (!autoOSType.toLowerCase().contains("windows")) {
            path4Page = new String(path4Page.replaceAll("\\\\", File.separator));
            path4Action = new String(path4Action.replaceAll("\\\\", File.separator));
        }
        this.operationType = opT;
     //   this.currentPage = cp;
        this.theDriver = d;
        this.theActionSeq = inSeq;
        this.classNameMap = classShortName2completeName.AndroidList();

    }



    public BaseAction(AppiumDriver d, String cp, int opT, String[] inSeq, String cPath, String expResult) throws Exception {
        long startTime = System.currentTimeMillis();

        if (!autoOSType.toLowerCase().contains("windows")) {
            path4Page = new String(path4Page.replaceAll("\\\\", File.separator));
            path4Action = new String(path4Action.replaceAll("\\\\", File.separator));
        }
        this.operationType = opT;
   //     this.currentPage = cp;
        this.theDriver = d;
        this.theActionSeq = inSeq;
        this.path4Log = mkdir4Log(cPath);
        this.classNameMap = classShortName2completeName.AndroidList();
        this.contentFromExcept = expResult;
        System.out.println("^^^^^^    BaseAction init ：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
    }

    public BaseAction(WebDriver d, String cp, int opT, String[] inSeq, String cPath, String expResult) throws Exception {
        long startTime = System.currentTimeMillis();

        if (!autoOSType.toLowerCase().contains("windows")) {
            path4Page = new String(path4Page.replaceAll("\\\\", File.separator));
            path4Action = new String(path4Action.replaceAll("\\\\", File.separator));
        }
        this.operationType = opT;
     //   this.currentPage = cp;
        this.theWebDriver = d;
        this.theActionSeq = inSeq;
        this.path4Log = mkdir4Log(cPath);
        this.classNameMap = classShortName2completeName.AndroidList();
        this.contentFromExcept = expResult;
        System.out.println("^^^^^^    BaseAction init ：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
    }


    public BaseAction() {
        if (!autoOSType.toLowerCase().contains("windows")) {
            path4Page = new String(path4Page.replaceAll("\\\\", File.separator));
            path4Action = new String(path4Action.replaceAll("\\\\", File.separator));
        }
        this.classNameMap = classShortName2completeName.AndroidList();
    }

    public String mkdir4Log(String cPath) throws Exception {
        String res = null;
        try {
            File pfile = new File(cPath);
            pfile.mkdir();
            Thread.sleep(2000);
            if (pfile.exists()) {
                res = pfile.getPath();
            } else {
                res = "create folder failed";
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            return res;
        }
    }

    public StringBuffer getExeResult() {
        return this.result4exe;
    }

    public String getContent4input() {
        return this.content4input;
    }

    public String getContent4check() {
        return this.content4check;
    }

    public String getContentFromExcept() {
        return this.contentFromExcept;
    }

    /*
    private List<String> getActionXMLAND(String[] actionName) throws XMLException {
        List<String> result = new ArrayList<String>();
        try {
            Map<String, String> testCaseScenario = action2PageOperationXML.action2PageOperationAND;
            for (int ind = 0; ind < actionName.length; ind++) {
                for (Iterator it = testCaseScenario.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry obj = (Map.Entry) it.next();
                    if (obj.getKey().toString().equalsIgnoreCase(actionName[ind])) {
                        System.out.println(" Find the name of XML file by abs action name ~ ");
                        result.add((String) obj.getValue());
                    }
                }
            }
            if ((result == null) || (result.size() == 0)) {
                throw new XMLException("Failed to match XML for Action " + actionName);
            }
        } catch (Exception e) {
            throw new XMLException("Failed to match XML for Action " + actionName);
        } finally {
            return result;
        }
    }
*/
    private List<String> getActionXMLIOS(String[] actionName) throws XMLException {
        List<String> result = new ArrayList<String>();
        try {
            Map<StringBuilder, StringBuilder> testCaseScenario = action2PageOperationXML.action2PageOperationIOS;
            for (int ind = 0; ind < actionName.length; ind++) {
                for (Iterator it = testCaseScenario.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry obj = (Map.Entry) it.next();
                    if (obj.getKey().toString().equalsIgnoreCase(actionName[ind])) {
                        System.out.println(" Find the name of XML file by abs action name ~ ");
                        result.add((String) obj.getValue());
                    }
                }
            }
            if ((result == null) || (result.size() == 0)) {
                throw new XMLException("Failed to match XML for Action " + actionName);
            }
        } catch (Exception e) {
            throw new XMLException("Failed to match XML for Action " + actionName);
        } finally {
            return result;
        }
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

    public HashMap<String, operationItem> getOperationSeq(String[] inActionSeq) throws Exception {
        HashMap<String, operationItem> temp = new HashMap<String, operationItem>();
        HashMap<String, operationItem> result = new HashMap<String, operationItem>();
        //按照给定的文件顺序读取文件,并且把 all 文件的内容全部读出去
        try {
            List<String> actionXMLname = getActionXMLIOS(inActionSeq);  // get the name list for all used action
            for (int ind = 0; ind < actionXMLname.size(); ind++) {   //  read all files as action sequence
                temp = readActionXMLDocument(new StringBuilder(path4Action + actionXMLname.get(ind)));
                List actionlist = new ArrayList(temp.keySet());
                Object[] ary = actionlist.toArray();
                Arrays.sort(ary);
                actionlist = Arrays.asList(ary);
                if (actionlist.size() != 0) {
                    int flag4length = 0;
                    flag4length = actionlist.size();
                    System.out.println("There are " + flag4length + " step(s) in the related XML file of sub action : " + actionXMLname.get(ind));
                } else {
                    throw new XMLException("action list from XML contains empty content`");
                }
                result.putAll(temp);
            }
            System.out.println("Account the result : " + result.size());

            for (Iterator it = result.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry obj = (Map.Entry) it.next();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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

    public Object createInstance(Class cType, AndroidDriver theD) throws Exception {
        //System.out.println(" ~~~~~~ In the createInstance()");
        System.out.println("Check the cType : " + cType.toString());
        Object result = null;
        Constructor<?>[] consts = cType.getConstructors();
        Constructor<?> constructor = null;
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
    }

    public Object createInstance(Class cType, WebDriver theD) throws Exception {
        //System.out.println(" ~~~~~~ In the createInstance()");
        System.out.println("Check the cType : " + cType.toString());
        Object result = null;
        Constructor<?>[] consts = cType.getConstructors();
        Constructor<?> constructor = null;
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


    public Object createInstance4constructor1(Class cType, AndroidDriver theD) throws Exception {
        //System.out.println(" ~~~~~~ In the createInstance()");
        System.out.println("Check the cType : " + cType.toString());
        Object result = null;
        Constructor<?>[] consts = cType.getConstructors();
        Constructor<?> constructor = null;
        for (int index = 0; index < consts.length; index++) {
            //System.out.println("The consts [] is not empty ! ");
            int paramsLength = consts[index].getParameterAnnotations().length;
            System.out.println("Check the paraType : " + consts[index].getParameterTypes());
            System.out.println("The AndDr class type is : " + ((Class) AndroidDriver.class).getSimpleName().toString());
            if (paramsLength == 1) {
                System.out.println("@@@ Find the constructor with 1 para : " + consts[index]);
                constructor = consts[index];
                break;
            }
        }
        if (constructor != null) {
            //System.out.println("Double check the constructor !!!");
            Class<?>[] type = constructor.getParameterTypes();
            System.out.println("What is this ??? : " + type);
            result = cType.getConstructor(type).newInstance(theDriver);
            System.out.println("cPage : " + result);
        }
        return result;
    }

    private static String statusCheck(Object dP4N, String oP4Last, String oP4this, String n4DP, String eP4N) {
        String result = null;


        return result;
    }

    private String autoSwitchWithNavi(Class cType, AppiumDriver theD, operationItem cOpeItem, Object dP4N, String oP4this, String n4DP) throws Exception {
        String result = null;
        //TODO
        //  if current page can switch to home/navi, and the route/elenpagename can be find in the navi route


        return result;
    }

    /*
    private String findEleName4tempMapinHome(AppiumDriver theD, String theOpe4This) throws Exception { //todo , for android
        // find the element name with speical route
        String result = "sorry";
        bANDPageHome xInstance = new bANDPageHome((AndroidDriver) theD, this.path4Log);
        HashMap<String, ElementMapping> tempMap = xInstance.getEleConMap();
        for (Iterator it = tempMap.entrySet().iterator(); it.hasNext(); ) {
            System.out.println("Start loop the Map of Home in autoSwitch");
            Map.Entry eleEntry = (Map.Entry) it.next();
            ElementMapping newTobj = (ElementMapping) eleEntry.getValue();
            String tempRoute = newTobj.getNextPage();
            System.out.println("tempRoute " + tempRoute);
            if ((tempRoute.equalsIgnoreCase(theOpe4This) || tempRoute.toLowerCase().contains(theOpe4This.toLowerCase()))) {
                System.out.println("put!!!");
                tempMap.clear();
                tempMap.put((String) eleEntry.getKey(), (ElementMapping) eleEntry.getValue());
                result = (((ElementMapping) eleEntry.getValue())).getElementName();
                break;
            }
        }
        return result;
    }

    private String findEleName4tempMapinNavi(AppiumDriver theD, String theOpe4This) throws Exception {   //todo , for android
        // find the element name with speical route
        String result = "sorry";
        bANDPageNaviBar xInstance = new bANDPageNaviBar((AndroidDriver) theD, this.path4Log);
        HashMap<String, ElementMapping> tempMap = xInstance.getEleConMap();
        for (Iterator it = tempMap.entrySet().iterator(); it.hasNext(); ) {
            System.out.println("Start loop the Map of Navi in autoSwitch");
            Map.Entry eleEntry = (Map.Entry) it.next();
            ElementMapping newTobj = (ElementMapping) eleEntry.getValue();
            String tempRoute = newTobj.getNextPage();
            System.out.println("tempRoute " + tempRoute);
            if ((tempRoute.equalsIgnoreCase(theOpe4This) || tempRoute.toLowerCase().contains(theOpe4This.toLowerCase()))) {
                System.out.println("put for Navi!!!");
                tempMap.clear();
                tempMap.put((String) eleEntry.getKey(), (ElementMapping) eleEntry.getValue());
                result = (((ElementMapping) eleEntry.getValue())).getElementName();
                break;
            }
        }
        return result;
    }

*/

    public HashMap<String, ElementMapping> getContentByRoute(HashMap<String, ElementMapping> eleContentMap, String tRoute) throws Exception {
        HashMap<String, ElementMapping> result = null;
        for (Iterator it = eleContentMap.entrySet().iterator(); it.hasNext(); ) {
            System.out.println("Start loop the Map in autoSwitch . getContentByRoute");
            Map.Entry eleEntry = (Map.Entry) it.next();
            ElementMapping newTobj = (ElementMapping) eleEntry.getValue();
            String tempRoute = newTobj.getNextPage();
            System.out.println("Route in map : " + tempRoute);
            if ((tempRoute.equalsIgnoreCase(tRoute) || tempRoute.toLowerCase().contains(tRoute.toLowerCase()))) {
                System.out.println("Find the match route ! ");
                result.clear();
                result.put((String) eleEntry.getKey(), (ElementMapping) eleEntry.getValue());
                break;
            }
        }
        if (!result.isEmpty()) {
            return result;
        } else {
            return null;
        }
    }

    public String getEleNameByRoute(HashMap<String, ElementMapping> eleContentMap, String tRoute) throws Exception {
        String result = null;
        HashMap<String, ElementMapping> map4Route = getContentByRoute(eleContentMap, tRoute);
        if (!map4Route.isEmpty()) {
            result = ((ElementMapping) map4Route.values()).getElementName();
        }
        return result;
    }


    public String getEleByRouteAsThis(HashMap<String, ElementMapping> xMap, final String oP4this) {
        String eleNameFromtempMap = null;
        for (Iterator it = xMap.entrySet().iterator(); it.hasNext(); ) {
            System.out.println("Start loop the Map in getEleByRouteAsThis");
            Map.Entry eleEntry = (Map.Entry) it.next();
            ElementMapping newTobj = (ElementMapping) eleEntry.getValue();
            String tempRoute = newTobj.getNextPage();
            System.out.println("tempRoute " + tempRoute);
            if ((tempRoute.equalsIgnoreCase(oP4this) || tempRoute.toLowerCase().contains(oP4this.toLowerCase()))) {
                System.out.println("put!!!");
                xMap.clear();
                xMap.put((String) eleEntry.getKey(), (ElementMapping) eleEntry.getValue());
                System.out.println("Check the tempMap in getEleByRouteAsThis: " + xMap);
                eleNameFromtempMap = (((ElementMapping) eleEntry.getValue())).getElementName();
                System.out.println("Check the eleNameFromtempMap in getEleByRouteAsThis: " + eleNameFromtempMap);
                break;
            }
        }
        return eleNameFromtempMap;
    }

    public String getEleNameAndTypeByRouteHAN(HashMap<String, ElementMapping> xMap) {
        String eleName = null;
        String eleType = null;
        for (Iterator it = xMap.entrySet().iterator(); it.hasNext(); ) {
            System.out.println("Start loop the Map in getEleNameAndTypeByRouteHAN 4 Home OR Navi");
            Map.Entry eleEntry = (Map.Entry) it.next();
            ElementMapping newTobj = (ElementMapping) eleEntry.getValue();
            String tempRoute = newTobj.getNextPage();
            System.out.println("tempRoute " + tempRoute);
            if ((tempRoute.equalsIgnoreCase("Home") || tempRoute.equalsIgnoreCase("NaviBar"))) {
                System.out.println("put!!!");
                xMap.clear();
                xMap.put((String) eleEntry.getKey(), (ElementMapping) eleEntry.getValue());
                eleName = (((ElementMapping) eleEntry.getValue())).getElementName();
                eleType = (((ElementMapping) eleEntry.getValue())).getType();
                System.out.println("Check the eleName in getEleNameAndTypeByRouteHAN: " + eleName);
                System.out.println("Check the eleType in getEleNameAndTypeByRouteHAN: " + eleType);
                break;
            }
        }
        return eleName + ":" + eleType;
    }

    public String getEleNameAndTypeByRouteH(HashMap<String, ElementMapping> xMap) {
        String eleName = null;
        String eleType = null;
        for (Iterator it = xMap.entrySet().iterator(); it.hasNext(); ) {
            System.out.println("Start loop the Map in getEleNameAndTypeByRouteHe 4 Home ");
            Map.Entry eleEntry = (Map.Entry) it.next();
            ElementMapping newTobj = (ElementMapping) eleEntry.getValue();
            String tempRoute = newTobj.getNextPage();
            System.out.println("tempRoute " + tempRoute);
            if (tempRoute.equalsIgnoreCase("Home")) {
                System.out.println("put!!!");
                xMap.clear();
                xMap.put((String) eleEntry.getKey(), (ElementMapping) eleEntry.getValue());
                eleName = (((ElementMapping) eleEntry.getValue())).getElementName();
                eleType = (((ElementMapping) eleEntry.getValue())).getType();
                System.out.println("Check the eleName in getEleNameAndTypeByRouteH: " + eleName);
                System.out.println("Check the eleType in getEleNameAndTypeByRouteH: " + eleType);
                break;
            }
        }
        return eleName + ":" + eleType;
    }


    private String checkScreenInput(String para, String screenInput) throws Exception {
        return "true"; //// TODO: 5/17/16  
    }

    private String commonMethodHandle(String operationName, operationItem cOperationItem, Object thePageInstance, Class cType, String[] extraPara, int orgParaIndex, Object dPageLast) throws Exception {
        int newIndex = orgParaIndex;
        //  int paraIndex = orgParaIndex;
        String tempType = operationName;
        Object devicePage4next = thePageInstance;
        Class classType = cType;
        String[] extPara = extraPara;
        String para4this = null;
        operationItem cOpeItem = cOperationItem;
        String resultPageName = new String("Test failed");
        try {

            if (!classType.getSimpleName().endsWith("Win")) {
                Method getTitleCheck = classType.getDeclaredMethod("getFlag4TitleCheck");
                boolean tCheckEnable = (Boolean) getTitleCheck.invoke(devicePage4next);
                if (tCheckEnable == true) {
                    Thread.sleep(2600);
                    Method titleCheck = classType.getDeclaredMethod("titleCheck", new Class[]{AndroidDriver.class});
                    boolean tCheckResult = (Boolean) titleCheck.invoke(devicePage4next, (AndroidDriver) theDriver);
                    if (tCheckResult == true) {
                        Method setTitleCheck = classType.getDeclaredMethod("setFlag4TitleCheckFalse");
                        setTitleCheck.invoke(devicePage4next);
                        System.out.println("Title Check passed !!!");
                    } else {
                        if (dPageLast != null) {
                            System.out.println("Test failed at Page : " + dPageLast.getClass().getSimpleName().replaceFirst("bANDPage", ""));
                            resultPageName = new String("Test failed at Page:" + dPageLast.getClass().getSimpleName().replaceFirst("bANDPage", ""));
                            //   throw new FFPandaException("Test failed at Page:" + dPageLast.getClass().getSimpleName());
                            //  throw new PageTitleException("Step : " + cOpeItem.getElementName() + " Error, " + cOpeItem.getPageName());
                        } else {
                            System.out.println("Test failed on the way to Page : " + classType.getSimpleName().replaceFirst("bANDPage", ""));
                            resultPageName = new String("Test failed on the way to Page : " + classType.getSimpleName().replaceFirst("bANDPageD", ""));
                            //   throw new FFPandaException("Test failed on the way to Page:" + classType.getSimpleName());
                            //  throw new PageTitleException("Step : " + cOpeItem.getElementName() + " Error, " + cOpeItem.getPageName());
                        }
                    }
                }
            }
            if (resultPageName.equals("Test failed")) {
                if (tempType.contains("calendar")) {
                    if (tempType.contains("_date")) {
                        //TODO
                        System.out.println("Try to find the calendar method with date input !!!");
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;

                        Method setMethod = classType.getDeclaredMethod("calendarOperationDate", new Class[]{AndroidDriver.class, String.class, String.class});
                        Object nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        if ((nextPageName != null) && (!nextPageName.toString().equals(""))) {
                            if (!nextPageName.toString().equalsIgnoreCase("noRoute")) {
                                resultPageName = new String(nextPageName.toString());
                            }
                        }
                    } else if (tempType.contains("_tomorrow")) {
                        System.out.println("Try to find the calendar tomorrow method with date input !!!");

                        Method setMethod = classType.getDeclaredMethod("calendarOperationTomorrow", new Class[]{AndroidDriver.class, String.class});
                        Object nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        if ((nextPageName != null) && (!nextPageName.toString().equals(""))) {
                            if (!nextPageName.toString().equalsIgnoreCase("noRoute")) {
                                resultPageName = new String(nextPageName.toString());
                            }
                        }
                    } else if (!tempType.contains("_date")) {
                        System.out.println("Try to find the calendar method !!!");
                        Method setMethod = classType.getDeclaredMethod("calendarOperation", new Class[]{AndroidDriver.class, String.class});
                        Object nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        if ((nextPageName != null) && (!nextPageName.toString().equals(""))) {
                            if (!nextPageName.toString().equalsIgnoreCase("noRoute")) {
                                resultPageName = new String(nextPageName.toString());
                            }
                        }
                    }

                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("btn")) {
                    System.out.println("Try to find the btn method !!!");

                    Object nextPageName = null;
                    if (!tempType.contains("_match")) {
                        Method setMethod = classType.getDeclaredMethod("btnOperationRoute", new Class[]{AndroidDriver.class, String.class});
                        nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                        String temp4date = (String) getMethod.invoke(devicePage4next);
                        if (temp4date != null && temp4date != "") {
                            date4diffPage = new String(temp4date);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(temp4date);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + temp4date);
                            }
                        }
                    } else {
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        Method setMethod = classType.getDeclaredMethod("btnOperationRoute", new Class[]{AndroidDriver.class, String.class, String.class});
                        nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                        String temp4date = (String) getMethod.invoke(devicePage4next);
                        if (temp4date != null && temp4date != "") {
                            date4diffPage = new String(temp4date);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(temp4date);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + temp4date);
                            }
                        } else {
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        }
                    }
                    if ((nextPageName != null) && (!nextPageName.toString().equals(""))) {
                        if (!nextPageName.toString().equalsIgnoreCase("noRoute")) {
                            resultPageName = new String(nextPageName.toString());
                        }
                    }
                    System.out.println("        &&&&&& Find the Route name for next page : " + nextPageName.toString());
                    resultPageName = new String("");
                } else if (tempType.contains("text")) {
                    System.out.println("Try to find the text method !!!");
                    System.out.println("Current testNG Suit is running in : " + this.autoOSType);
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    if (tempType.contains("_input")) {
                        Method setMethod = classType.getMethod("textOperationWithSaveInput", new Class[]{AndroidDriver.class, String.class, String.class});
                        String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                            content4input = new String(content4input + "::" + tempR);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(tempR);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + tempR);
                            }
                        } else {
                            content4input = new String(content4input + "::" + para4this);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        }
                        //  content4input = new String(content4input + "::" + (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this));
                    } else {
                        Method setMethod = classType.getDeclaredMethod("textOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("roll")) {
                    System.out.println("Try to find the roll method !!!");
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    if (tempType.contains("_input")) {
                        Method setMethod = classType.getMethod("rollTheRound", new Class[]{AndroidDriver.class, String.class, String.class, String.class});
                        String result4roll = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), this.date4diffPage, para4this);
                        if (result4roll == null || result4roll == "" || result4roll.equalsIgnoreCase("emptyContent")) {
                            content4input = new String(content4input + "::" + para4this);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        } else {
                            content4input = new String(content4input + "::" + para4this);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        }
                    } else {
                        throw new XMLException("Roll without ext para is not ready");
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("checkpoint")) {
                    System.out.println("Try to find the comPageCheck method !!!");
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    Method setMethod = classType.getDeclaredMethod("comPageCheck", new Class[]{AndroidDriver.class, String.class, String.class});
                    setMethod.invoke(devicePage4next, theDriver, "ready", "all");
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("move")) {
                    System.out.println("Try to find the move method !!!");
                    if (tempType.equals("moveupall")) {
                        Method setMethod = classType.getDeclaredMethod("moveUPall", new Class[]{AndroidDriver.class});
                        setMethod.invoke(devicePage4next, theDriver);
                    } else if (tempType.equals("movedown")) {
                        Method setMethod = classType.getDeclaredMethod("moveDOWN", new Class[]{AndroidDriver.class});  //TODO Declared?? or method
                        setMethod.invoke(devicePage4next, theDriver);
                    } else if (tempType.equals("moveup")) {
                        Method setMethod = classType.getDeclaredMethod("moveUP", new Class[]{AndroidDriver.class});
                        setMethod.invoke(devicePage4next, theDriver);
                    } else if (tempType.equals("moveupsmart")) {
                        Method setMethod = classType.getDeclaredMethod("moveUPSmart", new Class[]{AndroidDriver.class});
                        setMethod.invoke(devicePage4next, theDriver);
                    } else if (tempType.equals("movedownall")) {
                        Method setMethod = classType.getDeclaredMethod("moveDOWNall", new Class[]{AndroidDriver.class});
                        setMethod.invoke(devicePage4next, theDriver);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("sortcontent")) {
                    System.out.println("Try to find the checkcontent method !!!");
                    if (tempType.contains("_match")) {
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        Method setMethod = classType.getDeclaredMethod("sortElementContent", new Class[]{AndroidDriver.class, String.class, String.class});
                        String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        //    if (tempResult.contains("_:REX:_")) {
                        appInputFeedBackMode = new String("sortMatch");
                        //  }
                        content4check = new String(content4check + "::" + tempResult);
                    } else {
                        Method setMethod = classType.getMethod("sortElementContent", new Class[]{AndroidDriver.class, String.class});
                        String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        //if (tempResult.contains("_:REX:_")) {
                        appInputFeedBackMode = new String("sortMatch");
                        // }
                        content4check = new String(content4check + "::" + tempResult);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("checkcontent")) {
                    System.out.println("Try to find the checkcontent method !!!");
                    if (tempType.contains("_match")) {
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        Method setMethod = classType.getDeclaredMethod("getElementContent", new Class[]{AndroidDriver.class, String.class, String.class});
                        String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        if (tempResult.contains("_:REX:_")) {
                            appInputFeedBackMode = new String("duplicatedMatch");
                        } else if (tempResult.contains("_~REX~_")) {
                            appInputFeedBackMode = new String("screenMatch");
                        }
                        content4check = new String(content4check + "::" + tempResult);
                    } else if (tempType.contains("_postfix")) {
                        Method setMethod = classType.getMethod("getElementContentPostFix", new Class[]{AndroidDriver.class, String.class});
                        String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        if (tempResult.contains("_:REX:_")) {
                            appInputFeedBackMode = new String("duplicatedMatch");
                        } else if (tempResult.contains("_~REX~_")) {
                            appInputFeedBackMode = new String("screenMatch");
                        }
                        content4check = new String(content4check + "::" + tempResult);
                    } else {
                        Method setMethod = classType.getMethod("getElementContent", new Class[]{AndroidDriver.class, String.class});
                        String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        if (tempResult.contains("_:REX:_")) {
                            appInputFeedBackMode = new String("duplicatedMatch");
                        } else if (tempResult.contains("_~REX~_")) {
                            appInputFeedBackMode = new String("screenMatch");
                        }
                        content4check = new String(content4check + "::" + tempResult);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("pagecontent")) {
                    flag4page = true;
                    System.out.println("Try to find the pagecontent method !!!");
                    System.out.println("Current testNG Suit is running in : " + this.autoOSType);
                    Method setMethod = classType.getMethod("getPageContent", new Class[]{AndroidDriver.class, String.class});
                    String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());

                    if (!content4pagecheck.equals("")) {
                        //  content4pagecheck = strCompAppen(content4pagecheck, tempResult);
                        content4pagecheck = new String(tempResult);
                    }
                    appInputFeedBackMode = new String("pageMatch");
                    resultPageName = new String("");
                } else if (tempType.contains("dropdownlist")) {
                    System.out.println("Try to find the dropDownList method !!!");
                    if (tempType.contains("_input")) {
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        System.out.println("para4NextStep for the dropDownList method is : " + para4this);
                        Method setMethod = classType.getMethod("dropDownListOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        //content4input = new String(content4input + "::" + (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this));
                        String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                            content4input = new String(content4input + "::" + tempR);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(tempR);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + tempR);
                            }
                        } else {
                            content4input = new String(content4input + "::" + para4this);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        }
                    } else if (tempType.contains("_ignore")) {
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        System.out.println("para4NextStep for the dropDownList method is : " + para4this);
                        Method setMethod = classType.getMethod("dropDownListOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    } else {
                        Method setMethod = classType.getMethod("dropDownListOperation", new Class[]{AndroidDriver.class, String.class});
                        String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                            content4input = new String(content4input + "::" + tempR);
                        } else {
                            content4input = new String(content4input + "::" + para4this);
                        }
                        //new String(content4input + "::" + (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName()));
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("multilist")) {
                    System.out.println("Try to find the multiList method !!!");
                    if (tempType.contains("_input")) {
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        System.out.println("para4NextStep for the multiList method is : " + para4this);
                        Method setMethod = classType.getMethod("itemListOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        if (!tempType.contains("_ignore")) {
                            String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                            if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                                content4input = new String(content4input + "::" + tempR);
                                if (key4searchMatch == "") {
                                    key4searchMatch = new String(tempR);
                                } else {
                                    key4searchMatch = new String(key4searchMatch + "::" + tempR);
                                }
                            } else {
                                content4input = new String(content4input + "::" + para4this);
                                if (key4searchMatch == "") {
                                    key4searchMatch = new String(para4this);
                                } else {
                                    key4searchMatch = new String(key4searchMatch + "::" + para4this);
                                }
                            }
                        } else {
                            setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        }
                    } else {
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        Method setMethod = classType.getMethod("itemListOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("singlelist")) {
                    System.out.println("Try to find the singleList method !!!");
                    if (tempType.contains("_input")) {
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        System.out.println("para4NextStep for the singleList method is : " + para4this);
                        Method setMethod = classType.getMethod("itemListOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        if (!tempType.contains("_ignore")) {
                            String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                            if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                                content4input = new String(content4input + "::" + tempR);
                                if (key4searchMatch == "") {
                                    key4searchMatch = new String(tempR);
                                } else {
                                    key4searchMatch = new String(key4searchMatch + "::" + tempR);
                                }
                            } else {
                                content4input = new String(content4input + "::" + para4this);
                                if (key4searchMatch == "") {
                                    key4searchMatch = new String(para4this);
                                } else {
                                    key4searchMatch = new String(key4searchMatch + "::" + para4this);
                                }
                            }
                        } else {
                            setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        }
                    } else {
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        Method setMethod = classType.getMethod("itemListOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("autocomplete")) {
                    System.out.println("Try to find the autoComplete method !!!");
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;

                    Method setMethod = classType.getMethod("autoCompleteOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                    String searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    if (searchItemContent != "" && searchItemContent != null && !searchItemContent.equalsIgnoreCase("emptyContent")) {
                        content4input = new String(content4input + "::" + searchItemContent);
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(searchItemContent);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + searchItemContent);
                        }
                    } else {
                        content4input = new String(content4input + "::" + para4this);
                        appInputFeedBackMode = new String("try2match");
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(para4this);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + para4this);
                        }
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.toLowerCase().startsWith("boxsearch")) {
                    System.out.println("Try to find the boxsearch method !!!");
                    //  para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    //  newIndex++;
                    String searchItemContent = null;
                    if (tempType.toLowerCase().endsWith("_click")) {
                        Method setMethod = classType.getMethod("boxSearchClickOperation", new Class[]{AndroidDriver.class, String.class});
                        setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    } else if (tempType.toLowerCase().endsWith("_titlecontent")) {
                        Method setMethod = classType.getMethod("boxSearchTitleContentListOperation", new Class[]{AndroidDriver.class, String.class});
                        searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        content4check = new String(content4check + "::" + searchItemContent);
                        if (searchItemContent.contains("_:REX:_")) {
                            appInputFeedBackMode = new String("duplicatedMatch");
                        }
                    } else if (tempType.toLowerCase().endsWith("_sampletitlecontent")) {
                        String[] sampleSet = content4input.split("::");
                        String sample = sampleSet[sampleSet.length - 1];
                        Method setMethod = classType.getMethod("boxSearchTitleContentListOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), sample);
                        content4check = new String(content4check + "::" + searchItemContent);
                        if (searchItemContent.contains("_:REX:_")) {
                            appInputFeedBackMode = new String("duplicatedMatch");
                        }
                    } else if (tempType.toLowerCase().endsWith("_titleANDclick")) {
                        Method setMethod = classType.getMethod("boxSearchTitleANDClickOperation", new Class[]{AndroidDriver.class, String.class});
                        searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        if (searchItemContent != "" && searchItemContent != null && !searchItemContent.equalsIgnoreCase("emptyContent")) {
                            content4input = new String(content4input + "::" + searchItemContent);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(searchItemContent);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + searchItemContent);
                            }
                        } else {
                            content4input = new String(content4input + "::" + para4this);
                            appInputFeedBackMode = new String("try2match");
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        }
                    } else if (tempType.toLowerCase().endsWith("_getcontent")) {
                        Method setMethod = classType.getMethod("boxSearchContentOperation", new Class[]{AndroidDriver.class, String.class});
                        searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        if (searchItemContent != "" && searchItemContent != null && !searchItemContent.equalsIgnoreCase("emptyContent")) {
                            content4input = new String(content4input + "::" + searchItemContent);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(searchItemContent);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + searchItemContent);
                            }
                        } else {
                            content4input = new String(content4input + "::" + para4this);
                            appInputFeedBackMode = new String("try2match");
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        }
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.toLowerCase().startsWith("search")) {
                    System.out.println("Try to find the search method !!!");
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;

                    Method setMethod = classType.getMethod("searchOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                    String searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    if (searchItemContent != "" && searchItemContent != null && !searchItemContent.equalsIgnoreCase("emptyContent")) {
                        content4input = new String(content4input + "::" + searchItemContent);
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(searchItemContent);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + searchItemContent);
                        }
                    } else {
                        content4input = new String(content4input + "::" + para4this);
                        appInputFeedBackMode = new String("try2match");
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(para4this);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + para4this);
                        }
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
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
                            List<String> tempWinList = new ArrayList<String>();
                            tempWinList.clear();
                            if (tempWinName.contains(";")) {
                                String[] tString4name = tempWinName.split(";");
                                for (int ind = 0; ind < tString4name.length; ind++) {
                                    tempWinList.add(tString4name[ind]);
                                }
                            } else {
                                tempWinList.add(tempWinName);
                            }
                            for (String thex : tempWinList) {
                                if (thex.toLowerCase().contains("errorwin")) {
                                    eleWinListAfterClick.add(thex);
                                    if (thex.equalsIgnoreCase("errorwin")) {
                                        this.errWindowFromElement = new String(thex);
                                    } else {
                                        eleErrorWinListAfterClick.add(thex);
                                    }
                                } else if (thex.toLowerCase().contains("confirmwin")) {
                                    eleWinListAfterClick.add(thex);
                                    this.confirmWindowFromElement = new String(thex);
                                } else if (thex.toLowerCase().contains("completewin")) {
                                    eleWinListAfterClick.add(thex);
                                    this.completeWindowFromElement = new String(thex);
                                } else {
                                    throw new XMLException("wrong content in triggerWindow part of page XML!!!");
                                }
                            }
                        }
                    }

                    //todo
                    if (tempType.contains("_skiperror")) {

                    }

                } else {
                    System.out.println("no element trigger window in this page");
                    this.errWindowFromElement = new String("");
                    this.confirmWindowFromElement = new String("");
                    this.completeWindowFromElement = new String("");
                    eleErrorWinListAfterClick.clear();
                }
                this.paraIndex = newIndex;
                if (!classType.getSimpleName().endsWith("Win")) {
                    if (!classType.getSimpleName().equals(resultPageName) && !resultPageName.equals("Test failed")) {
                        System.out.println(" ~~~~~~~ Set the Title Check Flag before leave the page :" + classType.getSimpleName() + "~~~~~~~ ");
                        //TODO set the titlecheck flag before leave the page
                        Method setTitleCheckT = classType.getDeclaredMethod("setFlag4TitleCheckTrue");
                        setTitleCheckT.invoke(devicePage4next);
                    }
                }
            }
            return resultPageName;
        } catch (Exception e) {
            InvocationTargetException targetEx = (InvocationTargetException) e;
            Throwable t = targetEx.getTargetException();
            System.out.println("~~~~ t.getclass.getsimplename : " + t.getClass().getSimpleName());
            if (t.getClass().getSimpleName().contains("TimeoutException")) {
                throw new TimeoutException(t);
            } else {
                System.out.println("Exception appear in BaseAction :: commonMethodHandle");
                throw e;
            }
        }
    }


    private String commonMethodHandleIOS(String operationName, operationItem cOperationItem, Object thePageInstance, Class cType, String[] extraPara, int orgParaIndex) throws Exception {
        int newIndex = orgParaIndex;
        //  int paraIndex = orgParaIndex;
        String tempType = operationName.toLowerCase();
        Object devicePage4next = thePageInstance;
        Class classType = cType;
        String[] extPara = extraPara;
        String para4this = null;
        operationItem cOpeItem = cOperationItem;
        String resultPageName = null;
        try {
            if (tempType.contains("calendar")) {
                if (tempType.endsWith("_date")) {
                    System.out.println("Try to find the calendar method with date input !!!");
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;

                    Method setMethod = classType.getDeclaredMethod("calendarOperationDate", new Class[]{IOSDriver.class, String.class, String.class});
                    Object nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    if ((nextPageName != null) && (!nextPageName.toString().equals(""))) {
                        if (!nextPageName.toString().equalsIgnoreCase("noRoute")) {
                            resultPageName = nextPageName.toString();
                        }
                    }
                } else if (tempType.endsWith("_tomorrow")) {
                    System.out.println("Try to find the calendar tomorrow method with date input !!!");

                    Method setMethod = classType.getDeclaredMethod("calendarOperationTomorrow", new Class[]{IOSDriver.class, String.class});
                    Object nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    if ((nextPageName != null) && (!nextPageName.toString().equals(""))) {
                        if (!nextPageName.toString().equalsIgnoreCase("noRoute")) {
                            resultPageName = nextPageName.toString();
                        }
                    }
                } else if (!tempType.endsWith("_date")) {
                    System.out.println("Try to find the calendar method !!!");
                    Method setMethod = classType.getDeclaredMethod("calendarOperation", new Class[]{IOSDriver.class, String.class});
                    Object nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    if ((nextPageName != null) && (!nextPageName.toString().equals(""))) {
                        if (!nextPageName.toString().equalsIgnoreCase("noRoute")) {
                            resultPageName = nextPageName.toString();
                        }
                    }
                } else if (!tempType.endsWith("_YandM")) {
                    System.out.println("Try to find the calendar method with date input !!!");
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    System.out.println("Try to find the calendar method !!!");
                    Method setMethod = classType.getDeclaredMethod("calendarOperationYandM", new Class[]{IOSDriver.class, String.class, String.class});
                    Object nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    if ((nextPageName != null) && (!nextPageName.toString().equals(""))) {
                        if (!nextPageName.toString().equalsIgnoreCase("noRoute")) {
                            resultPageName = nextPageName.toString();
                        }
                    }
                }

                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new String(temp4date);
                }
            } else if (tempType.contains("btn")) {
                System.out.println("Try to find the btn method !!!");

                Object nextPageName = null;
                if (!tempType.contains("_match")) {
                    Method setMethod = classType.getDeclaredMethod("btnOperationRoute", new Class[]{IOSDriver.class, String.class});
                    nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(temp4date);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + temp4date);
                        }
                    }
                } else {
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    Method setMethod = classType.getDeclaredMethod("btnOperationRoute", new Class[]{IOSDriver.class, String.class, String.class});
                    nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(temp4date);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + temp4date);
                        }
                    } else {
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(para4this);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + para4this);
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
                System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                newIndex++;
                if (tempType.contains("_input")) {
                    Method setMethod = classType.getMethod("textOperationWithSaveInput", new Class[]{IOSDriver.class, String.class, String.class});
                    String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                        content4input = new String(content4input + "::" + tempR);
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(tempR);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + tempR);
                        }
                    } else {
                        content4input = new String(content4input + "::" + para4this);
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(para4this);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + para4this);
                        }
                    }
                } else {
                    Method setMethod = classType.getDeclaredMethod("textOperation", new Class[]{IOSDriver.class, String.class, String.class});
                    setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new String(temp4date);
                }
            } else if (tempType.contains("roll")) {
                System.out.println("Try to find the roll method !!!");
                System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                newIndex++;
                if (tempType.contains("_input")) {
                    Method setMethod = classType.getMethod("rollTheRound", new Class[]{IOSDriver.class, String.class, String.class, String.class});
                    String result4roll = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), this.date4diffPage, para4this);
                    if (result4roll == null || result4roll == "" || result4roll.equalsIgnoreCase("emptyContent")) {
                        content4input = new String(content4input + "::" + para4this);
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(para4this);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + para4this);
                        }
                    } else {
                        content4input = new String(content4input + "::" + para4this);
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(para4this);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + para4this);
                        }
                    }
                } else {
                    throw new XMLException("Roll without ext para is not ready");
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new String(temp4date);
                }
            } else if (tempType.contains("checkpoint")) {
                System.out.println("Try to find the comPageCheck method !!!");
                System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                Method setMethod = classType.getDeclaredMethod("comPageCheck", new Class[]{IOSDriver.class, String.class, String.class});
                setMethod.invoke(devicePage4next, theDriver, "ready", "all");
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new String(temp4date);
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
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    Method setMethod = classType.getDeclaredMethod("getElementContent", new Class[]{IOSDriver.class, String.class, String.class});
                    String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    if (tempResult.contains("_:REX:_")) {
                        appInputFeedBackMode = new String("duplicatedMatch");
                    } else if (tempResult.contains("_~REX~_")) {
                        appInputFeedBackMode = new String("screenMatch");
                    }
                    content4check = new String(content4check + "::" + tempResult);
                } else if (tempType.contains("_postfix")) {
                    Method setMethod = classType.getMethod("getElementContentPostFix", new Class[]{IOSDriver.class, String.class});
                    String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    if (tempResult.contains("_:REX:_")) {
                        appInputFeedBackMode = new String("duplicatedMatch");
                    } else if (tempResult.contains("_~REX~_")) {
                        appInputFeedBackMode = new String("screenMatch");
                    }
                    content4check = new String(content4check + "::" + tempResult);
                } else {
                    Method setMethod = classType.getMethod("getElementContent", new Class[]{IOSDriver.class, String.class});
                    //content4check = new String(content4check + "::" + (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName()));
                    String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    if (tempResult.contains("_:REX:_")) {
                        appInputFeedBackMode = new String("duplicatedMatch");
                    } else if (tempResult.contains("_~REX~_")) {
                        appInputFeedBackMode = new String("screenMatch");
                    }
                    content4check = new String(content4check + "::" + tempResult);
                }
            } else if (tempType.contains("pagecontent")) {
                flag4page = true;
                System.out.println("Try to find the pagecontent method !!!");
                System.out.println("Current testNG Suit is running in : " + this.autoOSType);
                Method setMethod = classType.getMethod("getPageContent", new Class[]{AndroidDriver.class, String.class});
                String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());

                if (!content4pagecheck.equals("")) {
                    //  content4pagecheck = strCompAppen(content4pagecheck, tempResult);
                    content4pagecheck = new String(tempResult);
                }
                appInputFeedBackMode = new String("pageMatch");
            } else if (tempType.contains("dropdownlist")) {
                System.out.println("Try to find the dropDownList method !!!");
                if (tempType.contains("_input")) {
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    System.out.println("para4NextStep for the dropDownList method is : " + para4this);
                    Method setMethod = classType.getMethod("dropDownListOperation", new Class[]{IOSDriver.class, String.class, String.class});
                    String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                        content4input = new String(content4input + "::" + tempR);
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(tempR);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + tempR);
                        }
                    } else {
                        content4input = new String(content4input + "::" + para4this);
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(para4this);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + para4this);
                        }
                    }
                } else if (tempType.contains("_ignore")) {
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    System.out.println("para4NextStep for the dropDownList method is : " + para4this);
                    Method setMethod = classType.getMethod("dropDownListOperation", new Class[]{IOSDriver.class, String.class, String.class});
                    setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                } else {
                    Method setMethod = classType.getMethod("dropDownListOperation", new Class[]{IOSDriver.class, String.class});
                    String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                        content4input = new String(content4input + "::" + tempR);
                    } else {
                        content4input = new String(content4input + "::" + para4this);
                    }
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new String(temp4date);
                }
            } else if (tempType.contains("multilist")) {
                System.out.println("Try to find the multiList method !!!");
                if (tempType.contains("_input")) {
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    System.out.println("para4NextStep for the multiList method is : " + para4this);
                    Method setMethod = classType.getMethod("itemListOperation", new Class[]{IOSDriver.class, String.class, String.class});
                    if (!tempType.contains("_ignore")) {
                        String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                            content4input = new String(content4input + "::" + tempR);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(tempR);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + tempR);
                            }
                        } else {
                            content4input = new String(content4input + "::" + para4this);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        }
                    } else {
                        setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    }
                } else {
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    Method setMethod = classType.getMethod("itemListOperation", new Class[]{IOSDriver.class, String.class, String.class});
                    String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new String(temp4date);
                }
            } else if (tempType.contains("singlelist")) {
                System.out.println("Try to find the singleList method !!!");
                if (tempType.contains("_input")) {
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    System.out.println("para4NextStep for the singleList method is : " + para4this);
                    Method setMethod = classType.getMethod("itemListOperation", new Class[]{IOSDriver.class, String.class, String.class});
                    if (!tempType.contains("_ignore")) {
                        String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                            content4input = new String(content4input + "::" + tempR);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(tempR);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + tempR);
                            }
                        } else {
                            content4input = new String(content4input + "::" + para4this);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        }
                    } else {
                        setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    }
                } else {
                    // Method setMethod = classType.getMethod("itemListOperation", new Class[]{IOSDriver.class, String.class});
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    Method setMethod = classType.getMethod("itemListOperation", new Class[]{IOSDriver.class, String.class, String.class});
                    String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new String(temp4date);
                }
            } else if (tempType.toLowerCase().startsWith("search")) {
                System.out.println("Try to find the search method !!!");
                para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                newIndex++;

                Method setMethod = classType.getMethod("searchOperation", new Class[]{IOSDriver.class, String.class, String.class});
                String searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                if (searchItemContent != "" && searchItemContent != null && !searchItemContent.equalsIgnoreCase("emptyContent")) {
                    content4input = new String(content4input + "::" + searchItemContent);
                    if (key4searchMatch == "") {
                        key4searchMatch = new String(searchItemContent);
                    } else {
                        key4searchMatch = new String(key4searchMatch + "::" + searchItemContent);
                    }
                } else {
                    content4input = new String(content4input + "::" + para4this);
                    appInputFeedBackMode = new String("try2match");
                    if (key4searchMatch == "") {
                        key4searchMatch = new String(para4this);
                    } else {
                        key4searchMatch = new String(key4searchMatch + "::" + para4this);
                    }
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new String(temp4date);
                }
            } else if (tempType.toLowerCase().startsWith("boxsearch")) {
                System.out.println("Try to find the boxsearch method !!!");
                //  para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                //  newIndex++;
                String searchItemContent = null;
                if (tempType.toLowerCase().endsWith("_click")) {
                    Method setMethod = classType.getMethod("boxSearchClickOperation", new Class[]{IOSDriver.class, String.class});
                    setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                } else if (tempType.toLowerCase().endsWith("_getcontent")) {
                    Method setMethod = classType.getMethod("boxSearchContentOperation", new Class[]{IOSDriver.class, String.class});
                    searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    if (searchItemContent != "" && searchItemContent != null && !searchItemContent.equalsIgnoreCase("emptyContent")) {
                        content4input = new String(content4input + "::" + searchItemContent);
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(searchItemContent);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + searchItemContent);
                        }
                    } else {
                        content4input = new String(content4input + "::" + para4this);
                        appInputFeedBackMode = new String("try2match");
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(para4this);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + para4this);
                        }
                    }
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new String(temp4date);
                }
            } else if (tempType.contains("autocomplete")) {
                System.out.println("Try to find the autoComplete method !!!");
                para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                newIndex++;

                Method setMethod = classType.getMethod("autoCompleteOperation", new Class[]{IOSDriver.class, String.class, String.class});
                String searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                if (searchItemContent != "" && searchItemContent != null && !searchItemContent.equalsIgnoreCase("emptyContent")) {
                    content4input = new String(content4input + "::" + searchItemContent);
                    if (key4searchMatch == "") {
                        key4searchMatch = new String(searchItemContent);
                    } else {
                        key4searchMatch = new String(key4searchMatch + "::" + searchItemContent);
                    }
                } else {
                    content4input = new String(content4input + "::" + para4this);
                    appInputFeedBackMode = new String("try2match");
                    if (key4searchMatch == "") {
                        key4searchMatch = new String(para4this);
                    } else {
                        key4searchMatch = new String(key4searchMatch + "::" + para4this);
                    }
                }
                Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                String temp4date = (String) getMethod.invoke(devicePage4next);
                if (temp4date != null && temp4date != "") {
                    date4diffPage = new String(temp4date);
                }
            } else if (tempType.contains("sortcontent")) {
                System.out.println("Try to find the checkcontent method !!!");
                if (tempType.contains("_match")) {
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    Method setMethod = classType.getDeclaredMethod("sortElementContent", new Class[]{IOSDriver.class, String.class, String.class});
                    String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    appInputFeedBackMode = new String("sortMatch");
                    content4check = new String(content4check + "::" + tempResult);
                } else {
                    Method setMethod = classType.getMethod("sortElementContent", new Class[]{IOSDriver.class, String.class});
                    String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    appInputFeedBackMode = new String("sortMatch");
                    content4check = new String(content4check + "::" + tempResult);
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
                        List<String> tempWinList = new ArrayList<String>();
                        tempWinList.clear();
                        if (tempWinName.contains(";")) {
                            String[] tString4name = tempWinName.split(";");
                            for (int ind = 0; ind < tString4name.length; ind++) {
                                tempWinList.add(tString4name[ind]);
                            }
                        } else {
                            tempWinList.add(tempWinName);
                        }
                        //     eleWinListAfterClick = new ArrayList<String>()
                        for (String thex : tempWinList) {
                            if (thex.toLowerCase().contains("errorwin")) {
                                eleWinListAfterClick.add(thex);
                                if (thex.equalsIgnoreCase("errorwin")) {
                                    this.errWindowFromElement = new String(thex);
                                } else {
                                    eleErrorWinListAfterClick.add(thex);
                                }
                            } else if (thex.toLowerCase().contains("confirmwin")) {
                                eleWinListAfterClick.add(thex);
                                this.confirmWindowFromElement = new String(thex);
                            } else if (thex.toLowerCase().contains("completewin")) {
                                eleWinListAfterClick.add(thex);
                                this.completeWindowFromElement = new String(thex);
                            } else {
                                throw new XMLException("wrong content in triggerWindow part of page XML!!!");
                            }
                        }
                    }
                }
            } else {
                System.out.println("no element trigger window in this page");
                this.errWindowFromElement = new String("");
                this.confirmWindowFromElement = new String("");
                this.completeWindowFromElement = new String("");
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

    public AndroidDriver comOperationInAND(AndroidDriver theDriver, String[] opeSeq, String[] extPara) throws Exception {
        HashMap<String, operationItem> wholeSeqMap = new HashMap<String, operationItem>();
        HashMap<String, operationItem> singleSeqMap = new HashMap<String, operationItem>();
        String testENVpreName = "bANDPage";
        String filepath4XML = new String("REXSH.REXAUTO.APPunderTest.AppPage.AND.");
        String elePageName4Last = null;
        long gstartTime = System.currentTimeMillis();
     //   this.basePage = new bANDPageLogin(theDriver, this.path4Log);
     //   this.theNaviBar = new bANDPageNaviBar(theDriver, this.path4Log);
     //   this.homePage = new bANDPageHome(theDriver, this.path4Log);
        System.out.println("^^^^^^    pre-createInstance：" + (System.currentTimeMillis() - gstartTime) + " ms    ^^^^^^");
        Object devicePage4next = null;
        Object devicePage4last = null;
        HashMap<String, Object> pageGroup = new HashMap<String, Object>();
     //   pageGroup.put("loginPage", this.basePage);
     //    pageGroup.put("NaviBar", this.theNaviBar);
     //    pageGroup.put("homePage", this.homePage);
        String name4DevicePage = null;
        this.paraIndex = 0;
        String elePageName4next = null;
        Class classType = null; // Class.forName(testENVpreName + operationPage4this);
        this.result4exe = new StringBuffer().append("teststart");
        String tempName4Screen = null;
        String eleName4ThisStep = "";
        String eleName4LastStep = "";
        String currentOpeItemStepName4Error = "";
        String lastOpeItemStepName4Error = "";
        int keywordLength = 0;
        int subkeywordLength = 0;
        try {
            wholeSeqMap = getOperationSeq(opeSeq);
            if (keywordLength == 0) {
                keywordLength = opeSeq.length;
            }
            for (int ind4ope = 0; ind4ope < opeSeq.length; ) {

                singleSeqMap = analyOpeSequence(wholeSeqMap, opeSeq[ind4ope]);    // get the use part
                if (subkeywordLength == 0) {
                    subkeywordLength = singleSeqMap.size();
                }
                keywordLength--;
                String operationPage4this = null;
                List list = new ArrayList(singleSeqMap.keySet());
                Object[] ary = list.toArray();
                Arrays.sort(ary);
                list = Arrays.asList(ary);
                operationItem tOpeItem = null;
                int ind_opeItem = 0;
                for (Object opeItem : list) {
                    subkeywordLength--;
                    lastOpeItemStepName4Error = new String(currentOpeItemStepName4Error);
                    ind_opeItem++;
                    System.out.println("Start to handle the " + ind_opeItem + "nd :: " + list.size() + " opeItem in : " + opeItem);
                    System.out.println("Check the Object in the sub operation item list : " + singleSeqMap.get(opeItem).getElementName());
                    eleName4LastStep = eleName4ThisStep;
                    operationItem currentOpeItem = singleSeqMap.get(opeItem);
                    eleName4ThisStep = new String(currentOpeItem.getElementName());
                    System.out.println("Check the sub operationItem " + currentOpeItem.getElementName());
                    String operationPage4Last = operationPage4this;
                    operationPage4this = currentOpeItem.getPageName();
                    tempName4Screen = operationPage4this;
                    String para4NextStep = null;

                    // if (((elePageName4next == null) || (elePageName4next.equals(""))) && ((name4DevicePage == null) || (name4DevicePage.equals("")))) {

                    if (((elePageName4next == null)) && ((name4DevicePage == null) || (name4DevicePage.equals("")))) {
                        System.out.println(" ~~~~~~~~~ in the 1 fork");
                        //         System.out.println("operationPage4this " + operationPage4this + " : basePage.getClass().getSimpleName())" + basePage.getClass().getSimpleName());
                        for (Iterator it = pageGroup.entrySet().iterator(); it.hasNext(); ) {
                            System.out.println(" ~~~~~~~~~ in new 1.1 loop");
                            Map.Entry eleEntry = (Map.Entry) it.next();
                            String tempPageName = eleEntry.getValue().getClass().getSimpleName();
                            if (tempPageName.contains(testENVpreName + operationPage4this)) {
                                System.out.println("Get the : " + tempPageName + " in the pageGroup ");
                                devicePage4next = eleEntry.getValue();
                                classType = eleEntry.getValue().getClass();
                                if (elePageName4next != null) {
                                    elePageName4Last = elePageName4next;
                                }
                                currentOpeItemStepName4Error = currentOpeItem.getElementName();
                                elePageName4next = commonMethodHandle(currentOpeItem.getElementType(), currentOpeItem, devicePage4next, classType, extPara, this.paraIndex, devicePage4last);
                                if (elePageName4next != null && elePageName4next.startsWith("Test failed")) {
                                    System.out.println("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                                    throw new PageTitleException("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                                }
                            }

                        }
                        System.out.println("Stop after fork 1");
                    } else if ((operationPage4this != null) && (!operationPage4this.equals(""))) {
                        boolean flag4pageGroup = false;
                        System.out.println(" ~~~~~~~~~ in the fork 2");
                        String tempTypeINfork2 = currentOpeItem.getElementType().toLowerCase();

                        for (Iterator it = pageGroup.entrySet().iterator(); it.hasNext(); ) {
                            System.out.println(" ~~~~~~~~~ in the fork 2 loop");
                            Map.Entry eleEntry = (Map.Entry) it.next();
                            String tempPageName = eleEntry.getValue().getClass().getSimpleName();
                            if (tempPageName.equalsIgnoreCase(testENVpreName + operationPage4this)) {
                                System.out.println("Get the : " + tempPageName + " in the pageGroup ");
                                devicePage4next = eleEntry.getValue();
                                classType = eleEntry.getValue().getClass();
                                flag4pageGroup = true;
                                break;
                            }
                        }
                        if (flag4pageGroup == false) {
                            classType = Class.forName("REXSH.REXAUTO.APPunderTest.AppPage.AND." + testENVpreName + operationPage4this);
                            // classType = Class.forName(classShortName2completeName.AndroidList.get(operationPage4this)+operationPage4this); //TODO
                            long startTime = System.currentTimeMillis();
                            devicePage4next = createInstance(classType, theDriver);
                            System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                            pageGroup.put(operationPage4this, devicePage4next);
                        }
                        if (elePageName4next != null) {
                            elePageName4Last = elePageName4next;
                        }
                        currentOpeItemStepName4Error = currentOpeItem.getElementName();
                        elePageName4next = commonMethodHandle(tempTypeINfork2, currentOpeItem, devicePage4next, classType, extPara, this.paraIndex, devicePage4last);
                        if (elePageName4next != null && elePageName4next.startsWith("Test failed")) {
                            System.out.println("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                            throw new PageTitleException("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                        }
                        System.out.println("Stop after fork 2");
                    } else if (((operationPage4this != null) && (!operationPage4this.equals(""))) && ((name4DevicePage != null)) && (!name4DevicePage.equals(""))) {
                        System.out.println(" ~~~~~~~~~ in the fork 3");
                        String tempTypeINfork3 = currentOpeItem.getElementType().toLowerCase();
                        System.out.println("devicePage4next " + name4DevicePage + " : operationPage4this " + operationPage4this + "");
                        if ((name4DevicePage.toLowerCase().contains(operationPage4this.toLowerCase()))) {
                            System.out.println(" ~~~~~~~~~ in the fork 3.1");
                            classType = Class.forName("REXSH.REXAUTO.APPunderTest.AppPage.AND." + testENVpreName + operationPage4this);
                            System.out.println("Check the device Page class before refresh it : " + devicePage4next.getClass());
                            if ((operationPage4Last != null) && (operationPage4this.equalsIgnoreCase(operationPage4Last))) {  // ope.this == ope.last
                                if (devicePage4next.getClass().getSimpleName().contains(operationPage4this)) {  // instance == ope.this
                                    System.out.println("Same Page as last time, don't reload the page content");
                                } else if (!devicePage4next.getClass().getSimpleName().contains(operationPage4this)) {  // instance ! ope.this
                                    long startTime = System.currentTimeMillis();
                                    devicePage4next = createInstance(classType, theDriver);
                                    System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                                    pageGroup.put(operationPage4this, devicePage4next);
                                }

                            } else if ((elePageName4next != null) && (!elePageName4next.equals("")) && (!elePageName4next.equalsIgnoreCase("noRoute"))) {
                                if (operationPage4this.equals(elePageName4next)) {  //  ope.this == route of last element
                                    System.out.println("operation 3.1.2.1 this is same as route of last element ");
                                    classType = Class.forName("REXSH.REXAUTO.APPunderTest.AppPage.AND." + testENVpreName + elePageName4next);
                                    long startTime = System.currentTimeMillis();
                                    devicePage4next = createInstance(classType, theDriver);
                                    System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                                    pageGroup.put(operationPage4this, devicePage4next);
                                } else {   //  ope.this != route of last element
                                    System.out.println("operation 3.1.2.2 this is diff with route of last element ");
                                    classType = Class.forName("REXSH.REXAUTO.APPunderTest.AppPage.AND." + testENVpreName + operationPage4this);
                                }
                            } else if ((name4DevicePage != null) && (!name4DevicePage.equals("")) && (!name4DevicePage.equals("noRoute"))) {
                                classType = Class.forName("REXSH.REXAUTO.APPunderTest.AppPage.AND." + testENVpreName + name4DevicePage);
                                long startTime = System.currentTimeMillis();
                                devicePage4next = createInstance(classType, theDriver);
                                System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                                pageGroup.put(operationPage4this, devicePage4next);
                            } else if ((devicePage4next == null) || (devicePage4next == "")) {
                                System.out.println("In 3.2 other issue 4");
                                classType = Class.forName("REXSH.REXAUTO.APPunderTest.AppPage.AND." + testENVpreName + operationPage4this);
                                long startTime = System.currentTimeMillis();
                                devicePage4next = createInstance(classType, theDriver);
                                System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                                pageGroup.put(operationPage4this, devicePage4next);
                            }

                            if (elePageName4next != null) {
                                elePageName4Last = elePageName4next;
                            }
                            currentOpeItemStepName4Error = currentOpeItem.getElementName();
                            elePageName4next = commonMethodHandle(tempTypeINfork3, currentOpeItem, devicePage4next, classType, extPara, this.paraIndex, devicePage4last);
                            if (elePageName4next != null && elePageName4next.startsWith("Test failed")) {
                                System.out.println("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                                throw new PageTitleException("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                            }
                        }

                    }
                    // do the operation
                    else if (!name4DevicePage.toLowerCase().contains(operationPage4this.toLowerCase())) {

                   //     name4DevicePage = autoSwitch(classType, theDriver, currentOpeItem, devicePage4next, operationPage4this, name4DevicePage);
                        System.out.println(" ~~~~~~~~~ After auto switch in 4.2 ");


                        classType = Class.forName("REXSH.REXAUTO.APPunderTest.AppPage.AND." + testENVpreName + operationPage4this);
                        System.out.println("Check the device Page class before refresh it : " + devicePage4next.getClass());
                        if (elePageName4next != null) {
                            elePageName4Last = elePageName4next;
                        }
                        currentOpeItemStepName4Error = currentOpeItem.getElementName();
                        elePageName4next = commonMethodHandle(currentOpeItem.getElementType(), currentOpeItem, devicePage4next, classType, extPara, this.paraIndex, devicePage4last);
                        if (elePageName4next != null && elePageName4next.startsWith("Test failed")) {
                            System.out.println("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                            throw new PageTitleException("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                        }
                    } else {
                        System.out.println("4.3");
                    }
                    // (Class cType, AppiumDriver theD, operationItem cOpeItem, Object dP4N, String oP4this, String n4DP) throws Exception {
                    System.out.println("Stop after all fork ");
                    //  start the page check after every element operation done
                    devicePage4last = devicePage4next;
                    classType = devicePage4last.getClass();
                    System.out.println(new StringBuffer("@@ classType : ").append(classType));


                    if (this.eleWinListAfterClick.size() == 0) {

                    } else {
                        if (!currentOpeItem.getElementType().toLowerCase().contains("_skiperror")) {
                            elePageName4next = handleANDEleTriggerWinList(eleWinListAfterClick, eleErrorWinListAfterClick, filepath4XML, testENVpreName, theDriver, pageGroup, operationPage4this, devicePage4next, tempName4Screen, currentOpeItem, elePageName4Last, result4exe);
                        } else {
                            elePageName4next = handleANDEleTriggerWinListSkipError(eleWinListAfterClick, eleErrorWinListAfterClick, filepath4XML, testENVpreName, theDriver, pageGroup, operationPage4this, devicePage4next, tempName4Screen, currentOpeItem, elePageName4Last, result4exe);
                        }
                        eleWinListAfterClick = new ArrayList<String>();
                        this.errWindowFromElement = new String("");
                        this.confirmWindowFromElement = new String("");
                        this.completeWindowFromElement = new String("");
                        eleErrorWinListAfterClick.clear();
                    }


//  end the page check after every element operation done
                    if ((elePageName4next != null) && (!elePageName4next.equals(""))) {
                        System.out.println("In Sub-operation list, the operation in last page done, now let's move to : " + elePageName4next);
                        name4DevicePage = elePageName4next; //TODO should check the page title before this
                        if (name4DevicePage.endsWith("Page")) {
                            name4DevicePage = name4DevicePage.substring(0, name4DevicePage.length() - 4);
                        }
                        elePageName4next = null;
                    }
                }
                System.out.println("Stop 3.5");
                if (result4exe.toString().equals("teststart")) {
                    System.out.println(new StringBuffer("Single action pass : ").append(ind4ope));
                } else {
                    //   throw new FFPandaException(new StringBuffer("Single action Error").append(ind4ope).append(" : ").append(result4exe));
                    System.out.println("result4exe has been changed : " + result4exe.toString());
                }
                ind4ope = ind4ope + 1;
                System.out.println(new StringBuffer("    ^^^^^^ Now ind4ope is :").append(ind4ope));
                System.out.println("    ^^^^^^ Check the issue in the end ");
            }
            if (flag4page = true) {
                content4check = new String(content4check + content4pagecheck);
            }
            if ((elePageName4next != null) && (!elePageName4next.equals(""))) {
                System.out.println("In the whole operation map, the operation in last page done, now let's move to : " + elePageName4next);
                name4DevicePage = elePageName4next;
                if (name4DevicePage.endsWith("Page")) {
                    name4DevicePage = name4DevicePage.substring(0, name4DevicePage.length() - 4);
                }
                elePageName4next = null;
            }
            System.out.println("Stop 4");
            result4exe = resultContentCheck(result4exe, theDriver);

            if (keywordLength == 0 && subkeywordLength == 0) {
                if (result4exe.toString().equals("teststart")) {
                    System.out.println(" All action pass ! ");
                } else {
                    System.out.println(" Last action cause some error ! ");
                }
            } else {
                throw new PageTitleException("Step : " + lastOpeItemStepName4Error + " Error, " + elePageName4Last);
            }

        } catch (Exception e) {
            if (e.getClass().getSimpleName().equalsIgnoreCase("TimeoutException") || e.getClass().getSimpleName().equalsIgnoreCase("NoSuchElementException")) {
                throw new PageTitleException("Step : " + lastOpeItemStepName4Error + " Error, " + elePageName4Last);
            } else if (e.getClass().getSimpleName().equalsIgnoreCase("PageTitleException")) {
                throw e;
            } else {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                String tSpLast = devicePage4next.getClass().getSimpleName();
                ScreenShot(theDriver, "runtime_" + tSpLast + "_From_" + tempName4Screen + time + ".png", this.path4Log);
                throw e;
            }
        }

        return theDriver;
    }


    // end of comOperationInAND

    public StringBuffer resultContentCheck(StringBuffer inRes, WebDriver driver) throws Exception {  //TODO involve the ex para for excepted content from TC para
        System.out.println("++++++++++++++++ " + this.appInputFeedBackMode + " ++++++++++++++++");
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        if (inRes.toString().equals("teststart") && !this.content4check.contains("_REX_")) {
            System.out.println("++++++++++++++++ " + inRes.toString() + " ++++++++++++++++");
            if (this.content4input.equals(this.content4check)) {
                return inRes;
            } else if (!this.contentFromExcept.equals("")) {
                return contentCheckExceptAndResult(inRes);
            } else {
                if (!this.content4check.contains("_:REX:_")) {
                    return textContentCheck(inRes);
                } else if (this.content4check.contains("_:REX:_") && !this.content4input.equals("") && this.contentFromExcept.equals("")) {
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
                //return new StringBuffer("cool");
                return contentCheckExceptAndResult(inRes);
            }
        }
    }


    public StringBuffer textContentCheck(StringBuffer inRes, Object dP4n, String tN4S) throws Exception {
        StringBuffer result = new StringBuffer();
        //     result.delete(0, result.length());
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        String tSpLast = dP4n.getClass().getSimpleName();

        // ScreenShot(theDriver, "textContent_" + tSpLast + "_From_" + tN4S + time + ".png", this.path4Log);
        System.out.println("*** in textContentCheck ***");
        System.out.println("*here is the input  : " + this.content4input.length() + "; length : " + this.content4input.length());
        System.out.println("*here is the result : " + this.content4check.length() + "; length : " + this.content4check.length());
        try {
            if (this.content4input.length() != this.content4check.length()) {
                result.append("content length mis-match , input : " + this.content4input.length() + " , result : " + this.content4check.length());
                ScreenShot(theDriver, "content length mis-match " + tSpLast + time + ".png", this.path4Log);
            } else {
                if ((this.content4input != null) && (this.content4input != "") && (this.content4check != null) && (this.content4check != "")) {
                    if (this.content4check.equals(this.content4input)) {
                        result.append(inRes);
                        System.out.println("result in SB : " + result);
                    } else {
                        result.append("Element content failed to match input");
                        ScreenShot(theDriver, "textContent_nomatch_" + tSpLast + "_From_" + tN4S + time + ".png", this.path4Log);
                    }
                } else if ((this.content4check != null) && (this.content4check != "")) {
                    result.append(content4check);
                } else if ((this.content4input != null) && (this.content4input != "")) {
                    result.append("checkcontent line missing in operation XML");
                    ScreenShot(theDriver, "textContent_missing_" + tSpLast + "_From_" + tN4S + time + ".png", this.path4Log);
                } else {
                    result.append(inRes);
                }
            }
            System.out.println("result in SB end : " + result);
            return result;
        } catch (Exception e) {
            result.append("Exception appear during element content compare");
            ScreenShot(theDriver, "runtime_Exception" + tSpLast + "_From_" + tN4S + time + ".png", this.path4Log);
            throw new FFPandaException("BaseAction : textContentCheck : " + e.getMessage());
        }
    }

    public StringBuffer textContentCheck(StringBuffer inRes) throws Exception {
        StringBuffer result = new StringBuffer();
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        boolean flag4match = false;
        if (this.content4input.startsWith("::")) {
            this.content4input = new String(this.content4input.replaceFirst("::", ""));
        }
        if (this.content4input.endsWith("::")) {
            this.content4input = new String(this.content4input.substring(0, this.content4input.length() - 2));
        }
        if (this.content4check.startsWith("::")) {
            this.content4check = new String(this.content4check.replaceFirst("::", ""));
        }
        if (this.content4check.endsWith("::")) {
            this.content4check = new String(this.content4check.substring(0, this.content4check.length() - 2));
        }
        System.out.println("*** in textContentCheck ***");
        System.out.println("*here is the input  : " + this.content4input + "; length : " + this.content4input.length());
        System.out.println("*here is the result : " + this.content4check + "; length : " + this.content4check.length());
        try {
            if (appInputFeedBackMode.equalsIgnoreCase("exact")) {  // for exact
                if (this.content4input.length() != this.content4check.length()) {
                    if (content4check.contains("_REX_") || content4input.contains("_REX_")) {
                        appInputFeedBackMode = "try2match"; //TODO
                    } else {
                        result.append("content length mis-match , input : " + this.content4input.length() + " , result : " + this.content4check.length());
                        ScreenShot(theDriver, "content length mis-match " + time + ".png", this.path4Log);
                    }
                } else {
                    if ((this.content4input != null) && (this.content4input != "") && (this.content4check != null) && (this.content4check != "")) {
                        if (this.content4check.equals(this.content4input)) {
                            result.append("teststart");
                            System.out.println("result in SB : " + result);
                        } else {
                            result.append("Element content failed to match input");
                            ScreenShot(theDriver, "textContent_nomatch_" + time + ".png", this.path4Log);
                        }
                    } else if ((this.content4check != null) && (this.content4check != "")) {
                        result.append(content4check);
                    } else if ((this.content4input != null) && (this.content4input != "")) {
                        result.append("checkcontent line missing in operation XML");
                        ScreenShot(theDriver, "textContent_missing_" + time + ".png", this.path4Log);
                    } else {
                        result.append(inRes);
                    }
                }
            }
            if (appInputFeedBackMode.equalsIgnoreCase("try2match")) {// for try2mtach
                if ((this.content4input != null) && (this.content4input != "") && (this.content4check != null) && (this.content4check != "")) {
                    if (this.content4check.equals(this.content4input)) {
                        result.append(inRes);
                        System.out.println("result in SB : " + result);
                    } else if (!content4check.contains("_REX_") && !content4input.contains("_REX_")) {
                        String[] tempInput = content4input.split("::");
                        String[] tempCheck = content4check.split("::");
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
                        if (content4input.startsWith("::")) {
                            content4input = new String(content4input.replaceFirst("::", ""));
                        }
                        if (content4check.startsWith("::")) {
                            content4check = new String(content4check.replaceFirst("::", ""));
                        }

                        String temp1 = content4input.replaceAll("_REX_", "::");
                        String temp2 = content4check.replaceAll("_REX_", "::");

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
            } else if (appInputFeedBackMode.equalsIgnoreCase("screenMatch")) {
                if (this.content4input.equals("")) {
                    flag4match = true;
                } else {
                    ArrayList<String> extraList = new ArrayList<String>();
                    ArrayList<String> duplicatedList = new ArrayList<String>();
                    ArrayList<String> checkList = new ArrayList<String>();

                    for (String temp : this.content4input.split("::")) {
                        checkList.add(temp);
                    }
                    for (String temp : this.content4check.split("::")) {
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
            } else if (appInputFeedBackMode.equalsIgnoreCase("sortMatch")) {
                ArrayList<String> extraList = new ArrayList<String>();
                ArrayList<String> duplicatedList = new ArrayList<String>();

                for (String temp : this.content4check.split("::")) {
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
            } else if (appInputFeedBackMode.equalsIgnoreCase("duplicatedMatch"))       // for IOS duplicatedMatch
            {
                if (this.content4input.startsWith("::")) {
                    this.content4input = new String(this.content4input.replaceFirst("::", ""));
                }
                ArrayList<String> extraList = new ArrayList<String>();
                ArrayList<String> duplicatedList = new ArrayList<String>();
                ArrayList<String> checkList = new ArrayList<String>();

                for (String temp : this.content4input.split("::")) {
                    if (temp.contains("_REX_")) {
                        for (String subStr : temp.split("_REX_")) {
                            checkList.add(subStr);
                        }
                    } else {
                        checkList.add(temp);
                    }
                }
                for (String temp : this.content4check.split("::")) {
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
                if (appInputFeedBackMode.equalsIgnoreCase("sortMatch") && flag4match == true) {
                    result.append("teststart");
                } else if ((this.content4check != null) && (this.content4check != "")) {
                    result.append(content4check);
                } else if ((this.content4input != null) && (this.content4input != "")) {
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

    public static ArrayList<String> removeDuplicate(ArrayList arlList) {
        LinkedHashSet<String> set = new LinkedHashSet<String>(arlList);
        arlList.clear();
        arlList = new ArrayList<String>(set);
        System.out.println("");
        return arlList;
    }

    public StringBuffer contentCheckExceptAndResult(StringBuffer inRes) throws Exception {
        String tResult = "";
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        boolean flag4match = false;
        if (this.content4check == "") {
            this.content4check = new String(inRes.toString());
            appInputFeedBackMode = new String("try2match");
        }
        if (this.contentFromExcept.startsWith("::")) {
            this.contentFromExcept = new String(this.contentFromExcept.replaceFirst("::", ""));
        }
        if (this.contentFromExcept.endsWith("::")) {
            this.contentFromExcept = new String(this.contentFromExcept.substring(0, this.contentFromExcept.length() - 2));
        }
        if (this.content4check.startsWith("::")) {
            this.content4check = new String(this.content4check.replaceFirst("::", ""));
        }
        if (this.content4check.endsWith("::")) {
            this.content4check = new String(this.content4check.substring(0, this.content4check.length() - 2));
        }
        if (this.content4input.startsWith("::::")) {
            this.content4input = new String(this.content4input.replaceFirst("::::", ""));
        }
        if (this.content4input.startsWith("::")) {
            this.content4input = new String(this.content4input.replaceFirst("::", ""));
        }
        if (this.content4input.endsWith("::")) {
            this.content4input = new String(this.content4check.substring(0, this.content4input.length() - 2));
        }
        System.out.println("*** in contentCheckExceptAndResult ***");
        System.out.println("*here is the except  : " + this.contentFromExcept + "; length : " + this.contentFromExcept.length());
        System.out.println("*here is the result : " + this.content4check + "; length : " + this.content4check.length());
        try {
            if (this.contentFromExcept.length() != this.content4check.length()) {
                if (appInputFeedBackMode.equalsIgnoreCase("try2match") || appInputFeedBackMode.equalsIgnoreCase("sortmatch")) {
                    if (this.content4check.toLowerCase().contains(this.contentFromExcept.toLowerCase())) {
                        tResult = new String("teststart");
                        //result = new StringBuffer("teststart");
                    }
                } else if (!this.content4input.equals("") && this.contentFromExcept.length() == 0) {
                    System.out.println("Temp solution, empty expected result AND some thing in input:" + this.content4input);
                } else {
                    if ((Math.abs(this.contentFromExcept.length() - this.content4check.length()) < 10) && (this.contentFromExcept.contains("::")) && (this.content4check.contains("::"))) {
                        String newCon4Except = "";
                        String newCon4Check = "";
                        for (String temp : this.contentFromExcept.split("::")) {
                            newCon4Except = new String(newCon4Except + filter4space(temp));
                        }
                        for (String temp : this.content4check.split("::")) {
                            newCon4Check = new String(newCon4Check + filter4space(temp));
                        }
                        if (newCon4Except.equals(newCon4Check)) {
                            this.appInputFeedBackMode = "OtherMode";
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
            if (appInputFeedBackMode.equalsIgnoreCase("exact")) {
                if ((this.contentFromExcept != null) && (this.contentFromExcept != "") && (this.content4check != null) && (this.content4check != "")) {
                    if (this.content4check.equals(this.contentFromExcept)) {
                        tResult = new String(inRes.toString());
                        System.out.println("result in SB : " + tResult);
                    } else {
                        tResult = new String("Element content failed to match excepted");
                        ScreenShot(theDriver, "nomatch_exceptResult" + time + ".png", this.path4Log);
                    }
                } else if ((this.content4check != null) && (this.content4check != "")) {
                    tResult = new String(content4check);
                } else if ((this.contentFromExcept != null) && (this.contentFromExcept != "")) {
                    tResult = new String("contentFromExcept missing in TC");
                    ScreenShot(theDriver, "missing_exceptResult" + time + ".png", this.path4Log);
                } else {
                    tResult = new String(inRes.toString());
                }
            } else if (appInputFeedBackMode.equalsIgnoreCase("screenMatch")) {

            } else if (appInputFeedBackMode.equalsIgnoreCase("pageMatch")) {
                boolean result = true;
                String[] excResult = this.contentFromExcept.split(";");
                for (int ind = 0; (ind < excResult.length) && result == true; ind++) {

                    String[] singlePartResult = excResult[ind].split(" ");
                    for (int dind = 0; (dind < singlePartResult.length) && result == true; dind++) {
                        System.out.println("~~~search : " + singlePartResult[dind]);
                        if (!this.content4check.toLowerCase().contains(singlePartResult[dind].toLowerCase())) {
                            result = false;
                            break;
                        } else {
                            System.out.println("start : " + this.content4check.indexOf(singlePartResult[dind]));
                            System.out.println("end : " + this.content4check.length());
                            this.content4check = this.content4check.substring(this.content4check.indexOf(singlePartResult[dind]), this.content4check.length());  //TODO cut too much, should be length()
                            System.out.println(this.content4check);
                        }
                    }
                    if (result == true) {
                        tResult = new String("teststart");
                    } else {
                        tResult = new String("page content failed to match excepted");
                    }
                }
            } else if (appInputFeedBackMode.equalsIgnoreCase("duplicatedMatch"))       // for IOS duplicatedMatch
            {
                if (this.content4input.startsWith("::")) {
                    this.content4input = new String(this.content4input.replaceFirst("::", ""));
                }
                ArrayList<String> extraList = new ArrayList<String>();
                ArrayList<String> duplicatedList = new ArrayList<String>();
                ArrayList<String> checkList = new ArrayList<String>();

                for (String temp : this.content4input.split("::")) {
                    if (temp.contains("_REX_")) {
                        for (String subStr : temp.split("_REX_")) {
                            checkList.add(subStr);
                        }
                    } else {
                        checkList.add(temp);
                    }
                }
                for (String temp : this.content4check.split("::")) {
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

            } else if (appInputFeedBackMode.equalsIgnoreCase("sortMatch")) {
                ArrayList<String> extraList = new ArrayList<String>();
                ArrayList<String> duplicatedList = new ArrayList<String>();

                for (String temp : this.content4check.split("::")) {
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
            StringBuffer result = new StringBuffer(tResult);
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

    public boolean checkNextPage(HashMap<String, String> theTPage, AppiumDriver dr) throws Exception {
        boolean result = false;

        checkPage("x", dr);

        return result;
    }

    public boolean checkPage(String xml, AppiumDriver dr) throws Exception {
        boolean result = false;

        return result;
    }


    public IOSDriver comOperationInIOS(IOSDriver theDriver, String[] opeSeq, String[] extPara, String deviceType) throws Exception {
        HashMap<String, operationItem> wholeSeqMap = new HashMap<String, operationItem>();
        HashMap<String, operationItem> singleSeqMap = new HashMap<String, operationItem>();
        String testENVpreName = null;
        String filepath4XML = null;
        Object devicePage4next = null;
        Object devicePage4last = null;
        HashMap<String, Object> pageGroup = new HashMap<String, Object>();

        if (deviceType.toLowerCase().contains("pad")) {
            testENVpreName = new String("IPADPage");
            filepath4XML = new String("REXSH.REXAUTO.APPunderTest.AppPage.iPAD.");
            long gstartTime = System.currentTimeMillis();
            //   this.basePageIPAD = new IPADPageLogin(theDriver, this.path4Log);
            //   this.theNaviBarIPAD = new IPADPageNaviBar(theDriver, this.path4Log);
            //   this.theHomeIPAD = new IPADPageHome(theDriver, this.path4Log);
            System.out.println("^^^^^^    pre-createInstance：" + (System.currentTimeMillis() - gstartTime) + " ms    ^^^^^^");
            //   pageGroup.put("homePage", this.basePageIPAD);
            //   pageGroup.put("bashPage", this.theHomeIPAD);
            //   pageGroup.put("NaviBar", this.theNaviBarIPAD);
        } else {
            testENVpreName = new String("IOSPage");
            filepath4XML = new String("REXSH.REXAUTO.APPunderTest.AppPage.IOS.");
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
        this.result4exe = new StringBuffer().append("teststart");
        String tempName4Screen = null;
        String currentOpeItemStepName4Error = "";
        String lastOpeItemStepName4Error = "";
        try {
            wholeSeqMap = getOperationSeq(opeSeq);
            for (int ind4ope = 0; ind4ope < opeSeq.length; ) {
                if (ind4ope == 0) {
                    if (opeSeq[0].equalsIgnoreCase("login")) {
                        boolean loginResult = false;
                        boolean dashResult = false;
                        if (deviceType.toLowerCase().contains("pad")) {
                            //    loginResult = basePageIPAD.confirmOperation(theDriver);
                            //   if (loginResult == false) {
                            //      dashResult = theHomeIPAD.confirmOperation(theDriver);
                            //     }
                        } else {
                         //   loginResult = basePageIOS.confirmOperation(theDriver);
                            if (loginResult == false) {
                        //        dashResult = theHomeIOS.confirmOperation(theDriver);
                            }
                        }
                        if (loginResult == true && dashResult == false) {
                            System.out.println("Process start with Login");
                        } else if (loginResult == false && dashResult == true) {
                            System.out.println("Process start with DashBoard");
                            ind4ope = 1;
                            this.paraIndex = 2;
                        } else {
                            throw new FFPandaException("Initial page is not Login OR Dashboard");
                        }
                    } else {
                        System.out.println("Process don't start with Login, be careful");
                    }
                }
                singleSeqMap = analyOpeSequence(wholeSeqMap, opeSeq[ind4ope]);    // get the use part
                String operationPage4this = null;
                List list = new ArrayList(singleSeqMap.keySet());
                Object[] ary = list.toArray();
                Arrays.sort(ary);
                list = Arrays.asList(ary);
                operationItem tOpeItem = null;
                int ind_opeItem = 0;
                for (Object opeItem : list) {
                    lastOpeItemStepName4Error = new String(currentOpeItemStepName4Error);
                    ind_opeItem++;
                    System.out.println("Start to handle the " + ind_opeItem + "nd :: " + list.size() + " opeItem in : " + opeItem);
                    System.out.println("Check the Object in the sub operation item list : " + singleSeqMap.get(opeItem).getElementName());
                    operationItem currentOpeItem = singleSeqMap.get(opeItem);
                    System.out.println("Check the sub operationItem " + currentOpeItem.getElementName());
                    String operationPage4Last = operationPage4this;
                    operationPage4this = currentOpeItem.getPageName();
                    tempName4Screen = operationPage4this;
                    String para4NextStep = null;

                    if (((elePageName4next == null) || (elePageName4next.equals(""))) && ((name4DevicePage == null) || (name4DevicePage.equals("")))) {
                        System.out.println(" ~~~~~~~~~ in the 1 fork");
                        for (Iterator it = pageGroup.entrySet().iterator(); it.hasNext(); ) {
                            System.out.println(" ~~~~~~~~~ in new 1.1 loop");
                            Map.Entry eleEntry = (Map.Entry) it.next();
                            String tempPageName = eleEntry.getValue().getClass().getSimpleName();
                            if (tempPageName.contains(testENVpreName + operationPage4this)) {
                                System.out.println("Get the : " + tempPageName + " in the pageGroup ");
                                devicePage4next = eleEntry.getValue();
                                classType = eleEntry.getValue().getClass();
                                if (elePageName4next != null) {
                                    elePageName4Last = elePageName4next;
                                }
                                currentOpeItemStepName4Error = new String(currentOpeItem.getElementName());
                                elePageName4next = commonMethodHandleIOS(currentOpeItem.getElementType(), currentOpeItem, devicePage4next, classType, extPara, this.paraIndex);
                            }
                        }
                        System.out.println("Stop after fork 1");
                    } else if ((operationPage4this != null) && (!operationPage4this.equals(""))) {
                        boolean flag4pageGroup = false;
                        System.out.println(" ~~~~~~~~~ in the fork 2");
                        String tempTypeINfork2 = currentOpeItem.getElementType().toLowerCase();

                        for (Iterator it = pageGroup.entrySet().iterator(); it.hasNext(); ) {
                            System.out.println(" ~~~~~~~~~ in the fork 2 loop");
                            Map.Entry eleEntry = (Map.Entry) it.next();
                            String tempPageName = eleEntry.getValue().getClass().getSimpleName();
                            if (tempPageName.equalsIgnoreCase(testENVpreName + operationPage4this)) {
                                System.out.println("Get the : " + tempPageName + " in the pageGroup ");
                                devicePage4next = eleEntry.getValue();
                                classType = eleEntry.getValue().getClass();
                                flag4pageGroup = true;
                                break;
                            }
                        }
                        if (flag4pageGroup == false) {
                            classType = Class.forName(filepath4XML + testENVpreName + operationPage4this);
                            long startTime = System.currentTimeMillis();
                            devicePage4next = createInstance(classType, theDriver);
                            System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                            pageGroup.put(operationPage4this, devicePage4next);
                        }
                        if (elePageName4next != null) {
                            elePageName4Last = elePageName4next;
                        }
                        currentOpeItemStepName4Error = new String(currentOpeItem.getElementName());
                        elePageName4next = commonMethodHandleIOS(tempTypeINfork2, currentOpeItem, devicePage4next, classType, extPara, this.paraIndex);
                        System.out.println("Stop after fork 2");
                    } else if (((operationPage4this != null) && (!operationPage4this.equals(""))) && ((name4DevicePage != null)) && (!name4DevicePage.equals(""))) {
                        System.out.println(" ~~~~~~~~~ in the fork 3");
                        String tempTypeINfork3 = currentOpeItem.getElementType().toLowerCase();
                        System.out.println("devicePage4next " + name4DevicePage + " : operationPage4this " + operationPage4this + "");
                        if ((name4DevicePage.toLowerCase().contains(operationPage4this.toLowerCase()))) {
                            System.out.println(" ~~~~~~~~~ in the fork 3.1");
                            classType = Class.forName(filepath4XML + testENVpreName + operationPage4this);
                            System.out.println("Check the device Page class before refresh it : " + devicePage4next.getClass());
                            if ((operationPage4Last != null) && (operationPage4this.equalsIgnoreCase(operationPage4Last))) {  // ope.this == ope.last
                                if (devicePage4next.getClass().getSimpleName().contains(operationPage4this)) {  // instance == ope.this
                                    System.out.println("Same Page as last time, don't reload the page content");
                                } else if (!devicePage4next.getClass().getSimpleName().contains(operationPage4this)) {  // instance ! ope.this
                                    long startTime = System.currentTimeMillis();
                                    devicePage4next = createInstance(classType, theDriver);
                                    System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                                    pageGroup.put(operationPage4this, devicePage4next);
                                }
                            } else if ((elePageName4next != null) && (!elePageName4next.equals("")) && (!elePageName4next.equalsIgnoreCase("noRoute"))) {
                                if (operationPage4this.equals(elePageName4next)) {  //  ope.this == route of last element
                                    System.out.println("operation 3.1.2.1 this is same as route of last element ");
                                    classType = Class.forName(filepath4XML + testENVpreName + elePageName4next);
                                    long startTime = System.currentTimeMillis();
                                    devicePage4next = createInstance(classType, theDriver);
                                    System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                                    pageGroup.put(operationPage4this, devicePage4next);
                                } else {   //  ope.this != route of last element
                                    System.out.println("operation 3.1.2.2 this is diff with route of last element ");
                                    classType = Class.forName(filepath4XML + testENVpreName + operationPage4this);
                                }
                            } else if ((name4DevicePage != null) && (!name4DevicePage.equals("")) && (!name4DevicePage.equals("noRoute"))) {
                                classType = Class.forName(filepath4XML + testENVpreName + name4DevicePage);
                                long startTime = System.currentTimeMillis();
                                devicePage4next = createInstance(classType, theDriver);
                                System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                                pageGroup.put(operationPage4this, devicePage4next);
                            } else if ((devicePage4next == null) || (devicePage4next == "")) {
                                System.out.println("In 3.2 other issue 4");
                                classType = Class.forName(filepath4XML + testENVpreName + operationPage4this);
                                long startTime = System.currentTimeMillis();
                                devicePage4next = createInstance(classType, theDriver);
                                System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                                pageGroup.put(operationPage4this, devicePage4next);
                            }
                            if (elePageName4next != null) {
                                elePageName4Last = elePageName4next;
                            }
                            currentOpeItemStepName4Error = new String(currentOpeItem.getElementName());
                            elePageName4next = commonMethodHandleIOS(tempTypeINfork3, currentOpeItem, devicePage4next, classType, extPara, this.paraIndex);
                        }
                    } else if (!name4DevicePage.toLowerCase().contains(operationPage4this.toLowerCase())) {
                        //   name4DevicePage = autoSwitch(classType, theDriver, currentOpeItem, devicePage4next, operationPage4this, name4DevicePage); //todo removed in 20180525
                        System.out.println(" ~~~~~~~~~ After auto switch in 4.2 ");

                        classType = Class.forName(filepath4XML + testENVpreName + operationPage4this);
                        System.out.println("Check the device Page class before refresh it : " + devicePage4next.getClass());
                        if (elePageName4next != null) {
                            elePageName4Last = elePageName4next;
                        }
                        currentOpeItemStepName4Error = new String(currentOpeItem.getElementName());
                        elePageName4next = commonMethodHandleIOS(currentOpeItem.getElementType(), currentOpeItem, devicePage4next, classType, extPara, this.paraIndex);

                    } else {
                        System.out.println("4.3");
                    }
                    System.out.println("Stop after all fork ");
                    devicePage4last = devicePage4next;
                    classType = devicePage4last.getClass();
                    System.out.println(new StringBuffer("@@ classType : ").append(classType));

                    if (this.eleWinListAfterClick.size() == 0) {

                    } else {
                        elePageName4next = handleIOSEleTriggerWinList(eleWinListAfterClick, eleErrorWinListAfterClick, filepath4XML, testENVpreName, theDriver, pageGroup, operationPage4this, devicePage4next, tempName4Screen, currentOpeItem, elePageName4Last, result4exe);
                        eleWinListAfterClick = new ArrayList<String>();
                        this.errWindowFromElement = new String("");
                        this.confirmWindowFromElement = new String("");
                        this.completeWindowFromElement = new String("");
                        eleErrorWinListAfterClick.clear();
                    }
//  end the page check after every element operation done
                    if ((elePageName4next != null) && (!elePageName4next.equals(""))) {
                        System.out.println("In Sub-operation list, the operation in last page done, now let's move to : " + elePageName4next);
                        name4DevicePage = elePageName4next; //TODO should check the page title before this
                        if (name4DevicePage.endsWith("Page")) {
                            name4DevicePage = name4DevicePage.substring(0, name4DevicePage.length() - 4);
                        }
                        elePageName4next = null;
                    }
                    if (elePageName4next != null) {
                        elePageName4Last = elePageName4next;
                    }
                }
                System.out.println("Stop 3.5");
                if (result4exe.toString().equals("teststart")) {
                    System.out.println(new StringBuffer("Single action pass : ").append(ind4ope));
                } else {
                    System.out.println("result4exe has been changed : " + result4exe.toString());
                }
                ind4ope = ind4ope + 1;
                System.out.println(new StringBuffer("    ^^^^^^ Now ind4ope is :").append(ind4ope));
                System.out.println("    ^^^^^^ Check the issue in the end ");
            }
            if (flag4page = true) {
                content4check = new String(content4check + content4pagecheck);
            }
            if ((elePageName4next != null) && (!elePageName4next.equals(""))) {
                System.out.println("In the whole operation map, the operation in last page done, now let's move to : " + elePageName4next);
                name4DevicePage = elePageName4next;
                if (name4DevicePage.endsWith("Page")) {
                    name4DevicePage = name4DevicePage.substring(0, name4DevicePage.length() - 4);
                }
                elePageName4next = null;
            }
            System.out.println("Stop 4");
            result4exe = resultContentCheck(result4exe, theDriver);
            if (result4exe.toString().equals("teststart")) {
                System.out.println(" All action pass ! ");
            } else {
                System.out.println(" Last action cause some error ! ");
            }
        } catch (Exception e) {
            System.out.println("~~~~ e.getclass : baseAction");
            System.out.println("~~~~ e.getclass : " + e.getClass());
            System.out.println("~~~~ e.getclass.tostring : " + e.getClass().toString());
            if (e.getClass().getSimpleName().equalsIgnoreCase("TimeoutException") || e.getClass().getSimpleName().equalsIgnoreCase("NoSuchElementException")) {
                throw new PageTitleException("Step : " + lastOpeItemStepName4Error + " Error, " + elePageName4Last);
            } else {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                String tSpLast = devicePage4next.getClass().getSimpleName();
                ScreenShot(theDriver, "runtime_" + tSpLast + "_From_" + tempName4Screen + time + ".png", this.path4Log);
                e.printStackTrace();
                throw e;
            }
        }
        return theDriver;
    }

    public String handleIOSEleTriggerWinList(ArrayList<String> winList, ArrayList<String> errwinList, String fp4XML, String tENVpreName, IOSDriver theD, HashMap<String, Object> pageGroup,
                                             String op4t, Object dp4n, String tn4screen, operationItem coi, String ep4l, StringBuffer res4exe) throws Exception {
        String result = null;
        String loopresult = null;
        for (int ind = 0; ind < winList.size(); ind++) {
            if (winList.get(ind).toLowerCase().contains("errorwin")) {
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
                for (String tempErrWinName : errwinList) {
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
                    throw new Exception("Complete win is not supported");
                }
                if (!this.errWindowFromElement.equals("")) {
                    boolean errResult = handleIOSEleTriggerErrorWin(this.errWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
                    if (errResult == true) {
                        throw new PageTitleException("Step : " + coi.getElementName() + " Error, " + ep4l);
                    } else {

                    }
                }
                if (ind != 0) {
                    for (String tempErrWinName : errwinList) {
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

    public String handleANDEleTriggerWinList(ArrayList<String> winList, ArrayList<String> errwinList, String fp4XML, String tENVpreName, AndroidDriver theD, HashMap<String, Object> pageGroup,
                                             String op4t, Object dp4n, String tn4screen, operationItem coi, String ep4l, StringBuffer res4exe) throws Exception {
        String result = null;
        String loopresult = null;
        for (int ind = 0; ind < winList.size(); ind++) {
            if (winList.get(ind).toLowerCase().contains("errorwin")) {
                winList.remove(ind);
            }
        }
        try {
            if (!this.errWindowFromElement.equals("")) {
                boolean errResult = handleANDEleTriggerErrorWin(this.errWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
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
                for (String tempErrWinName : errwinList) {
                    boolean errResult = handleANDEleTriggerErrorWin(tempErrWinName, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
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
                    loopresult = handleANDEleTriggerConfirmWin(this.confirmWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen);
                    if (loopresult != null) {
                        winList.remove(ind);
                        ind = 0;
                    } else {
                        ind++;
                    }
                } else if (winList.get(ind).equals(this.completeWindowFromElement)) {
                    throw new Exception("Complete win is not supported");
                }
                if (!this.errWindowFromElement.equals("")) {
                    boolean errResult = handleANDEleTriggerErrorWin(this.errWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
                    if (errResult == true) {
                        throw new PageTitleException("Step : " + coi.getElementName() + " Error, " + ep4l);
                    } else {

                    }
                }
                if (ind != 0) {
                    for (String tempErrWinName : errwinList) {
                        boolean errResult = handleANDEleTriggerErrorWin(tempErrWinName, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
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


    public String handleANDEleTriggerWinListSkipError(ArrayList<String> winList, ArrayList<String> errwinList, String fp4XML, String tENVpreName, AndroidDriver theD, HashMap<String, Object> pageGroup,
                                                      String op4t, Object dp4n, String tn4screen, operationItem coi, String ep4l, StringBuffer res4exe) throws Exception {
        String result = null;
        String loopresult = null;
        for (int ind = 0; ind < winList.size(); ind++) {
            if (winList.get(ind).toLowerCase().contains("errorwin")) {
                winList.remove(ind);
            }
        }
        try {
            if (!this.errWindowFromElement.equals("")) {
                boolean errResult = handleANDEleTriggerErrorWinSkipError(this.errWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
            }
        } catch (Exception e) {
            throw e;
        }
        if (errwinList.size() != 0) {
            for (String tempErrWinName : errwinList) {
                handleANDEleTriggerErrorWinSkipError(tempErrWinName, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
            }
        }
        for (int ind = 0; ind < winList.size() && winList.size() != 0; ) {
            if (winList.get(ind).equals(this.confirmWindowFromElement)) {
                loopresult = handleANDEleTriggerConfirmWin(this.confirmWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen);
                if (loopresult != null) {
                    winList.remove(ind);
                    ind = 0;
                } else {
                    ind++;
                }
            } else if (winList.get(ind).equals(this.completeWindowFromElement)) {
                throw new Exception("Complete win is not supported");
            }
            if (!this.errWindowFromElement.equals("")) {
                handleANDEleTriggerErrorWinSkipError(this.errWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
            }
            if (ind != 0) {
                for (String tempErrWinName : errwinList) {
                    handleANDEleTriggerErrorWinSkipError(tempErrWinName, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
                }
            }
        }
        result = loopresult;
        return result;
    }

    public boolean handleIOSEleTriggerErrorWin(String winname, String fp4XML, String tENVpreName, IOSDriver theD, HashMap<String, Object> pageGroup,
                                               String op4t, Object dp4n, String tn4screen, operationItem coi, String ep4l, StringBuffer res4exe) throws Exception {
        boolean result = false;
        System.out.println(winname);
        Class tempClassType = Class.forName(String.valueOf(new StringBuffer(fp4XML).append(tENVpreName).append(winname)));
        System.out.println("@@@ tempClassType : " + tempClassType);
        Object tempIns = createInstance(tempClassType, theD);
        pageGroup.put(op4t, dp4n);
        Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{IOSDriver.class, String.class, String.class});
        Object comPageCheckResult = setMethod.invoke(tempIns, theD, "ready", "errorTitle");
        if (((String) comPageCheckResult).equals("pass")) {
            setMethod = tempClassType.getDeclaredMethod("getElementContent", new Class[]{IOSDriver.class, String.class});
            Object messageInWin = setMethod.invoke(tempIns, theD, "errorMessage");
            if (messageInWin.toString().length() > 30) {
                appInputFeedBackMode = "try2match";
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


    public String handleIOSEleTriggerConfirmWin(String winname, String fp4XML, String tENVpreName, IOSDriver theD, HashMap<String, Object> pageGroup,
                                                String op4t, Object dp4n, String tn4screen) throws Exception {
        String result = null;
        System.out.println(winname);
        Class tempClassType = Class.forName(String.valueOf(new StringBuffer(fp4XML).append(tENVpreName).append(winname)));
        System.out.println("@@@ tempClassType : " + tempClassType);
        Object tempIns = createInstance(tempClassType, theD);
        pageGroup.put(op4t, dp4n);
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

    public String handleEleTriggerWinList(ArrayList<String> winList, ArrayList<String> errwinList, String fp4XML, String tENVpreName, WebDriver theD, HashMap<String, Object> pageGroup,
                                          String op4t, Object dp4n, String tn4screen, operationItem coi, String ep4l, StringBuffer res4exe) throws Exception {
        String result = null;
        String loopresult = null;
        for (int ind = 0; ind < winList.size(); ind++) {
            if (winList.get(ind).toLowerCase().contains("errorwin")) {
                winList.remove(ind);
            }
        }
        try {
            if (!this.errWindowFromElement.equals("")) {
                boolean errResult = handleEleTriggerErrorWin(this.errWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
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
                for (String tempErrWinName : errwinList) {
                    boolean errResult = handleEleTriggerErrorWin(tempErrWinName, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
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
                    loopresult = handleEleTriggerConfirmWin(this.confirmWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen);
                    if (loopresult != null) {
                        winList.remove(ind);
                        ind = 0;
                    } else {
                        ind++;
                    }
                } else if (winList.get(ind).equals(this.completeWindowFromElement)) {
                    throw new Exception("Complete win is not supported");
                }
                if (!this.errWindowFromElement.equals("")) {
                    boolean errResult = handleEleTriggerErrorWin(this.errWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
                    if (errResult == true) {
                        throw new PageTitleException("Step : " + coi.getElementName() + " Error, " + ep4l);
                    } else {

                    }
                }
                if (ind != 0) {
                    for (String tempErrWinName : errwinList) {
                        boolean errResult = handleEleTriggerErrorWin(tempErrWinName, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
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


    public String handleEleTriggerWinListSkipError(ArrayList<String> winList, ArrayList<String> errwinList, String fp4XML, String tENVpreName, WebDriver theD, HashMap<String, Object> pageGroup,
                                                   String op4t, Object dp4n, String tn4screen, operationItem coi, String ep4l, StringBuffer res4exe) throws Exception {
        String result = null;
        String loopresult = null;
        for (int ind = 0; ind < winList.size(); ind++) {
            if (winList.get(ind).toLowerCase().contains("errorwin")) {
                winList.remove(ind);
            }
        }
        try {
            if (!this.errWindowFromElement.equals("")) {
                boolean errResult = handleEleTriggerErrorWinSkipError(this.errWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
            }
        } catch (Exception e) {
            throw e;
        }
        if (errwinList.size() != 0) {
            for (String tempErrWinName : errwinList) {
                handleEleTriggerErrorWinSkipError(tempErrWinName, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
            }
        }
        for (int ind = 0; ind < winList.size() && winList.size() != 0; ) {
            if (winList.get(ind).equals(this.confirmWindowFromElement)) {
                loopresult = handleEleTriggerConfirmWin(this.confirmWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen);
                if (loopresult != null) {
                    winList.remove(ind);
                    ind = 0;
                } else {
                    ind++;
                }
            } else if (winList.get(ind).equals(this.completeWindowFromElement)) {
                throw new Exception("Complete win is not supported");
            }
            if (!this.errWindowFromElement.equals("")) {
                handleEleTriggerErrorWinSkipError(this.errWindowFromElement, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
            }
            if (ind != 0) {
                for (String tempErrWinName : errwinList) {
                    handleEleTriggerErrorWinSkipError(tempErrWinName, fp4XML, tENVpreName, theD, pageGroup, op4t, dp4n, tn4screen, coi, ep4l, res4exe);
                }
            }
        }
        result = loopresult;
        return result;
    }


    public String handleIOSEleTriggerCompleteWin(String winname, String fp4XML, String tENVpreName, IOSDriver theD, HashMap<String, Object> pageGroup,
                                                 String op4t, Object dp4n, String tn4screen) throws Exception {
        String result = null;
        return result;
    }


    public boolean handleANDEleTriggerErrorWin(String winname, String fp4XML, String tENVpreName, AndroidDriver theD, HashMap<String, Object> pageGroup,
                                               String op4t, Object dp4n, String tn4screen, operationItem coi, String ep4l, StringBuffer res4exe) throws Exception {
        boolean result = false;
        System.out.println(winname);
        Class tempClassType = Class.forName(String.valueOf(new StringBuffer(fp4XML).append(tENVpreName).append(winname)));
        System.out.println("@@@ tempClassType : " + tempClassType);
        Object tempIns = createInstance(tempClassType, theD);
        pageGroup.put(op4t, dp4n);
        Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{AndroidDriver.class, String.class, String.class});
        Object comPageCheckResult = setMethod.invoke(tempIns, theD, "ready", "errorTitle");
        if (((String) comPageCheckResult).equals("pass")) {
            setMethod = tempClassType.getDeclaredMethod("getElementContent", new Class[]{AndroidDriver.class, String.class});
            Object messageInWin = setMethod.invoke(tempIns, theD, "errorMessage");
            if (messageInWin.toString().length() > 30) {
                appInputFeedBackMode = "try2match";
            }
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(date);
            String tSpLast = dp4n.getClass().getSimpleName();
            ScreenShot(theD, "ErrorNoticeWindow_" + tSpLast + "_From_" + tn4screen + time + ".png", this.path4Log);
            setMethod = tempClassType.getDeclaredMethod("btnOperationRoute", new Class[]{AndroidDriver.class, String.class});
            Object btnRouteResult = setMethod.invoke(tempIns, theD, "OKBtn");//  define all btn  name for all notice window
            res4exe.delete(0, res4exe.length());
            res4exe.append(messageInWin.toString());  //  complete the execution result code for TC layer judge
            result = true;
            throw new PageTitleException("Step : " + coi.getElementName() + " Error, " + ep4l + " , " + messageInWin);
        } else {
        }
        return result;
    }

    public boolean handleEleTriggerErrorWin(String winname, String fp4XML, String tENVpreName, WebDriver theD, HashMap<String, Object> pageGroup,
                                            String op4t, Object dp4n, String tn4screen, operationItem coi, String ep4l, StringBuffer res4exe) throws Exception {
        boolean result = false;
        System.out.println(winname);
        Class tempClassType = Class.forName(String.valueOf(new StringBuffer(fp4XML).append(tENVpreName).append(winname)));
        System.out.println("@@@ tempClassType : " + tempClassType);
        Object tempIns = createInstance(tempClassType, theD);
        pageGroup.put(op4t, dp4n);
        Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{AndroidDriver.class, String.class, String.class});
        Object comPageCheckResult = setMethod.invoke(tempIns, theD, "ready", "errorTitle");
        if (((String) comPageCheckResult).equals("pass")) {
            setMethod = tempClassType.getDeclaredMethod("getElementContent", new Class[]{AndroidDriver.class, String.class});
            Object messageInWin = setMethod.invoke(tempIns, theD, "errorMessage");
            if (messageInWin.toString().length() > 30) {
                appInputFeedBackMode = "try2match";
            }
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(date);
            String tSpLast = dp4n.getClass().getSimpleName();
            // ScreenShot(theD, "ErrorNoticeWindow_" + tSpLast + "_From_" + tn4screen + time + ".png", this.path4Log);
            setMethod = tempClassType.getDeclaredMethod("btnOperationRoute", new Class[]{AndroidDriver.class, String.class});
            Object btnRouteResult = setMethod.invoke(tempIns, theD, "OKBtn");//  define all btn  name for all notice window
            res4exe.delete(0, res4exe.length());
            res4exe.append(messageInWin.toString());  //  complete the execution result code for TC layer judge
            result = true;
            throw new PageTitleException("Step : " + coi.getElementName() + " Error, " + ep4l + " , " + messageInWin);
        } else {
        }
        return result;
    }


    public boolean handleANDEleTriggerErrorWinSkipError(String winname, String fp4XML, String tENVpreName, AndroidDriver theD, HashMap<String, Object> pageGroup,
                                                        String op4t, Object dp4n, String tn4screen, operationItem coi, String ep4l, StringBuffer res4exe) throws Exception {
        boolean result = false;
        System.out.println(winname);
        Class tempClassType = Class.forName(String.valueOf(new StringBuffer(fp4XML).append(tENVpreName).append(winname)));
        System.out.println("@@@ tempClassType : " + tempClassType);
        Object tempIns = createInstance(tempClassType, theD);
        pageGroup.put(op4t, dp4n);
        Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{AndroidDriver.class, String.class, String.class});
        Object comPageCheckResult = setMethod.invoke(tempIns, theD, "ready", "errorTitle");
        if (((String) comPageCheckResult).equals("pass")) {
            setMethod = tempClassType.getDeclaredMethod("getElementContent", new Class[]{AndroidDriver.class, String.class});
            Object messageInWin = setMethod.invoke(tempIns, theD, "errorMessage");
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(date);
            String tSpLast = dp4n.getClass().getSimpleName();
            ScreenShot(theD, "ErrorNoticeWindow_" + tSpLast + "_From_" + tn4screen + time + ".png", this.path4Log);
            setMethod = tempClassType.getDeclaredMethod("btnOperationRoute", new Class[]{AndroidDriver.class, String.class});
            Object btnRouteResult = setMethod.invoke(tempIns, theD, "OKBtn");//  define all btn  name for all notice window
            result = true;
        } else {
        }
        return result;
    }

    public boolean handleEleTriggerErrorWinSkipError(String winname, String fp4XML, String tENVpreName, WebDriver theD, HashMap<String, Object> pageGroup,
                                                     String op4t, Object dp4n, String tn4screen, operationItem coi, String ep4l, StringBuffer res4exe) throws Exception {
        boolean result = false;
        System.out.println(winname);
        Class tempClassType = Class.forName(String.valueOf(new StringBuffer(fp4XML).append(tENVpreName).append(winname)));
        System.out.println("@@@ tempClassType : " + tempClassType);
        Object tempIns = createInstance(tempClassType, theD);
        pageGroup.put(op4t, dp4n);
        Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{AndroidDriver.class, String.class, String.class});
        Object comPageCheckResult = setMethod.invoke(tempIns, theD, "ready", "errorTitle");
        if (((String) comPageCheckResult).equals("pass")) {
            setMethod = tempClassType.getDeclaredMethod("getElementContent", new Class[]{AndroidDriver.class, String.class});
            Object messageInWin = setMethod.invoke(tempIns, theD, "errorMessage");
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(date);
            String tSpLast = dp4n.getClass().getSimpleName();
            // ScreenShot(theD, "ErrorNoticeWindow_" + tSpLast + "_From_" + tn4screen + time + ".png", this.path4Log);
            setMethod = tempClassType.getDeclaredMethod("btnOperationRoute", new Class[]{AndroidDriver.class, String.class});
            Object btnRouteResult = setMethod.invoke(tempIns, theD, "OKBtn");//  define all btn  name for all notice window
            result = true;
        } else {
        }
        return result;
    }

    public String handleEleTriggerConfirmWin(String winname, String fp4XML, String tENVpreName, WebDriver theD, HashMap<String, Object> pageGroup,
                                             String op4t, Object dp4n, String tn4screen) throws Exception {
        String result = null;
        System.out.println(winname);
        Class tempClassType = Class.forName(String.valueOf(new StringBuffer(fp4XML).append(tENVpreName).append(winname)));
        System.out.println("@@@ tempClassType : " + tempClassType);
        Object tempIns = createInstance(tempClassType, theD);
        pageGroup.put(op4t, dp4n);
        Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{AndroidDriver.class, String.class, String.class});
        Object comPageCheckResult = setMethod.invoke(tempIns, theD, "ready", "confirmMessage");
        //  noBtn
        if (((String) comPageCheckResult).equals("pass")) {
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(date);
            String tSpLast = dp4n.getClass().getSimpleName();
            // ScreenShot(theD, "ConfirmWindow_" + tSpLast + "_From_" + tn4screen + time + ".png", this.path4Log);
            setMethod = tempClassType.getDeclaredMethod("btnOperationRoute", new Class[]{AndroidDriver.class, String.class});
            Object btnRouteResult = setMethod.invoke(tempIns, theD, "yesBtn");//  define all btn  name for all notice window
            result = (String) btnRouteResult;  //

        } else {
        }
        return result;
    }


    public String handleANDEleTriggerConfirmWin(String winname, String fp4XML, String tENVpreName, AndroidDriver theD, HashMap<String, Object> pageGroup,
                                                String op4t, Object dp4n, String tn4screen) throws Exception {
        String result = null;
        System.out.println(winname);
        Class tempClassType = Class.forName(String.valueOf(new StringBuffer(fp4XML).append(tENVpreName).append(winname)));
        System.out.println("@@@ tempClassType : " + tempClassType);
        Object tempIns = createInstance(tempClassType, theD);
        pageGroup.put(op4t, dp4n);
        Method setMethod = tempClassType.getDeclaredMethod("comPageCheck", new Class[]{AndroidDriver.class, String.class, String.class});
        Object comPageCheckResult = setMethod.invoke(tempIns, theD, "ready", "confirmMessage");
        //  noBtn
        if (((String) comPageCheckResult).equals("pass")) {
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(date);
            String tSpLast = dp4n.getClass().getSimpleName();
            ScreenShot(theD, "ConfirmWindow_" + tSpLast + "_From_" + tn4screen + time + ".png", this.path4Log);
            setMethod = tempClassType.getDeclaredMethod("btnOperationRoute", new Class[]{AndroidDriver.class, String.class});
            Object btnRouteResult = setMethod.invoke(tempIns, theD, "yesBtn");//  define all btn  name for all notice window
            result = (String) btnRouteResult;  //

        } else {
        }
        return result;
    }


    public String handleANDEleTriggerCompleteWin(String winname, String fp4XML, String tENVpreName, AndroidDriver theD, HashMap<String, Object> pageGroup,
                                                 String op4t, Object dp4n, String tn4screen) throws Exception {
        String result = null;
        return result;
    }

    private static String strCompAppen(String lhm1, String lhm2) {
        String[] extG1 = lhm1.replaceFirst("<", "").split("><");
        String[] extG2 = lhm2.replaceFirst("<", "").split("><");
        return "<" + strCompAppen(extG1, extG2).toString();
    }

    private static StringBuilder strCompAppen(String[] lhm1, String[] lhm2) {
        StringBuilder result = new StringBuilder();
        boolean flag4quit = false;
        int endi = 0;
        int end4o = 0;
        for (int i = 0; i < lhm1.length; i++) {
            boolean flag4match = false;
            boolean flag4dismatch = false;

            for (int j = endi; j < lhm2.length; j++) {
                if (!lhm1[i].equals(lhm2[j])) {     //  不同
                    flag4dismatch = true;     //  标记不同
                    if (flag4match == false) {      //  若一直都不同,继续
                    } else {        //  若曾经相同
                        flag4quit = true;   //  标记准备删除
                        break;      //  退出
                    }
                } else {        //  相同
                    flag4match = true;      //  标记相同
                    lhm2[j] = new String("");       //  删除相同
                    endi = j;
                }
            }
            if (flag4quit == true) {
                end4o = i;
                break;
            }
        }
        if (flag4quit == true) {        //  开始删除
            for (int newi0 = 0; newi0 < end4o + 1; newi0++) {
                result.append(lhm1[newi0]);
            }
            for (int newi = endi; newi < lhm2.length; newi++) {
                if (!lhm2[newi].equals("")) {
                    result.append(lhm2[newi]);
                }
            }
        }
        return result;
    }

    public void main(String args[]) {
        System.out.println("The AndDr class type is : " + ((Class) AndroidDriver.class).getSimpleName().toString());
        try {
            AndroidDriver theD = null;
            BaseAction x = new BaseAction();
            HashMap ss = x.getOperationSeq(new String[]{"login", "test"});


            x.comOperationInAND(theD, new String[]{"login"}, new String[]{"640@qq.com", "Pwcwelcome1", "  8sas3f sf9 22234 dsf "});


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String commonMethodHandleFOX(String operationName, operationItem cOperationItem, Object thePageInstance, Class cType, String[] extraPara, int orgParaIndex, Object dPageLast) throws Exception {
        int newIndex = orgParaIndex;
        //  int paraIndex = orgParaIndex;
        String tempType = operationName;
        Object devicePage4next = thePageInstance;
        Class classType = cType;
        String[] extPara = extraPara;
        String para4this = null;
        operationItem cOpeItem = cOperationItem;
        String resultPageName = new String("Test failed");
        try {

            if (!classType.getSimpleName().endsWith("Win")) {
                /*
                Method getTitleCheck = classType.getDeclaredMethod("getFlag4TitleCheck");
                boolean tCheckEnable = (Boolean) getTitleCheck.invoke(devicePage4next);
                if (tCheckEnable == true) {
                    Thread.sleep(2600);
                    Method titleCheck = classType.getDeclaredMethod("titleCheck", new Class[]{AndroidDriver.class});
                    boolean tCheckResult = (Boolean) titleCheck.invoke(devicePage4next, (AndroidDriver) theDriver);
                    if (tCheckResult == true) {
                        Method setTitleCheck = classType.getDeclaredMethod("setFlag4TitleCheckFalse");
                        setTitleCheck.invoke(devicePage4next);
                        System.out.println("Title Check passed !!!");
                    } else {
                        if (dPageLast != null) {
                            System.out.println("Test failed at Page : " + dPageLast.getClass().getSimpleName().replaceFirst("FOXPage", ""));
                            resultPageName = new String("Test failed at Page:" + dPageLast.getClass().getSimpleName().replaceFirst("FOXPage", ""));
                            //   throw new FFPandaException("Test failed at Page:" + dPageLast.getClass().getSimpleName());
                            //  throw new PageTitleException("Step : " + cOpeItem.getElementName() + " Error, " + cOpeItem.getPageName());
                        } else {
                            System.out.println("Test failed on the way to Page : " + classType.getSimpleName().replaceFirst("FOXPage", ""));
                            resultPageName = new String("Test failed on the way to Page : " + classType.getSimpleName().replaceFirst("FOXPage", ""));
                            //   throw new FFPandaException("Test failed on the way to Page:" + classType.getSimpleName());
                            //  throw new PageTitleException("Step : " + cOpeItem.getElementName() + " Error, " + cOpeItem.getPageName());
                        }
                    }
                }
                */
            }
            if (resultPageName.equals("Test failed")) {
                if (tempType.contains("calendar")) {
                    if (tempType.contains("_date")) {
                        //TODO
                        System.out.println("Try to find the calendar method with date input !!!");
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;

                        Method setMethod = classType.getDeclaredMethod("calendarOperationDate", new Class[]{AndroidDriver.class, String.class, String.class});
                        Object nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        if ((nextPageName != null) && (!nextPageName.toString().equals(""))) {
                            if (!nextPageName.toString().equalsIgnoreCase("noRoute")) {
                                resultPageName = new String(nextPageName.toString());
                            }
                        }
                    } else if (tempType.contains("_tomorrow")) {
                        System.out.println("Try to find the calendar tomorrow method with date input !!!");

                        Method setMethod = classType.getDeclaredMethod("calendarOperationTomorrow", new Class[]{AndroidDriver.class, String.class});
                        Object nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        if ((nextPageName != null) && (!nextPageName.toString().equals(""))) {
                            if (!nextPageName.toString().equalsIgnoreCase("noRoute")) {
                                resultPageName = new String(nextPageName.toString());
                            }
                        }
                    } else if (!tempType.contains("_date")) {
                        System.out.println("Try to find the calendar method !!!");
                        Method setMethod = classType.getDeclaredMethod("calendarOperation", new Class[]{AndroidDriver.class, String.class});
                        Object nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        if ((nextPageName != null) && (!nextPageName.toString().equals(""))) {
                            if (!nextPageName.toString().equalsIgnoreCase("noRoute")) {
                                resultPageName = new String(nextPageName.toString());
                            }
                        }
                    }

                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("btn")) {
                    System.out.println("Try to find the btn method !!!");

                    Object nextPageName = null;
                    if (!tempType.contains("_match")) {
                        Method setMethod = classType.getDeclaredMethod("btnOperationRoute", new Class[]{AndroidDriver.class, String.class});
                        nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                        String temp4date = (String) getMethod.invoke(devicePage4next);
                        if (temp4date != null && temp4date != "") {
                            date4diffPage = new String(temp4date);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(temp4date);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + temp4date);
                            }
                        }
                    } else {
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        Method setMethod = classType.getDeclaredMethod("btnOperationRoute", new Class[]{AndroidDriver.class, String.class, String.class});
                        nextPageName = setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                        String temp4date = (String) getMethod.invoke(devicePage4next);
                        if (temp4date != null && temp4date != "") {
                            date4diffPage = new String(temp4date);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(temp4date);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + temp4date);
                            }
                        } else {
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        }
                    }
                    if ((nextPageName != null) && (!nextPageName.toString().equals(""))) {
                        if (!nextPageName.toString().equalsIgnoreCase("noRoute")) {
                            resultPageName = new String(nextPageName.toString());
                        }
                    }
                    System.out.println("        &&&&&& Find the Route name for next page : " + nextPageName.toString());
                    resultPageName = new String("");
                } else if (tempType.contains("text")) {
                    System.out.println("Try to find the text method !!!");
                    System.out.println("Current testNG Suit is running in : " + this.autoOSType);
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    if (tempType.contains("_input")) {
                        Method setMethod = classType.getMethod("textOperationWithSaveInput", new Class[]{AndroidDriver.class, String.class, String.class});
                        String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                            content4input = new String(content4input + "::" + tempR);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(tempR);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + tempR);
                            }
                        } else {
                            content4input = new String(content4input + "::" + para4this);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        }
                        //  content4input = new String(content4input + "::" + (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this));
                    } else {
                        Method setMethod = classType.getDeclaredMethod("textOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("roll")) {
                    System.out.println("Try to find the roll method !!!");
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;
                    if (tempType.contains("_input")) {
                        Method setMethod = classType.getMethod("rollTheRound", new Class[]{AndroidDriver.class, String.class, String.class, String.class});
                        String result4roll = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), this.date4diffPage, para4this);
                        if (result4roll == null || result4roll == "" || result4roll.equalsIgnoreCase("emptyContent")) {
                            content4input = new String(content4input + "::" + para4this);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        } else {
                            content4input = new String(content4input + "::" + para4this);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        }
                    } else {
                        throw new XMLException("Roll without ext para is not ready");
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("checkpoint")) {
                    System.out.println("Try to find the comPageCheck method !!!");
                    System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    Method setMethod = classType.getDeclaredMethod("comPageCheck", new Class[]{AndroidDriver.class, String.class, String.class});
                    setMethod.invoke(devicePage4next, theDriver, "ready", "all");
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("sortcontent")) {
                    System.out.println("Try to find the checkcontent method !!!");
                    if (tempType.contains("_match")) {
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        Method setMethod = classType.getDeclaredMethod("sortElementContent", new Class[]{AndroidDriver.class, String.class, String.class});
                        String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        //    if (tempResult.contains("_:REX:_")) {
                        appInputFeedBackMode = new String("sortMatch");
                        //  }
                        content4check = new String(content4check + "::" + tempResult);
                    } else {
                        Method setMethod = classType.getMethod("sortElementContent", new Class[]{AndroidDriver.class, String.class});
                        String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        //if (tempResult.contains("_:REX:_")) {
                        appInputFeedBackMode = new String("sortMatch");
                        // }
                        content4check = new String(content4check + "::" + tempResult);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("checkcontent")) {
                    System.out.println("Try to find the checkcontent method !!!");
                    if (tempType.contains("_match")) {
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        Method setMethod = classType.getDeclaredMethod("getElementContent", new Class[]{AndroidDriver.class, String.class, String.class});
                        String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        if (tempResult.contains("_:REX:_")) {
                            appInputFeedBackMode = new String("duplicatedMatch");
                        } else if (tempResult.contains("_~REX~_")) {
                            appInputFeedBackMode = new String("screenMatch");
                        }
                        content4check = new String(content4check + "::" + tempResult);
                    } else if (tempType.contains("_postfix")) {
                        Method setMethod = classType.getMethod("getElementContentPostFix", new Class[]{AndroidDriver.class, String.class});
                        String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        if (tempResult.contains("_:REX:_")) {
                            appInputFeedBackMode = new String("duplicatedMatch");
                        } else if (tempResult.contains("_~REX~_")) {
                            appInputFeedBackMode = new String("screenMatch");
                        }
                        content4check = new String(content4check + "::" + tempResult);
                    } else {
                        Method setMethod = classType.getMethod("getElementContent", new Class[]{AndroidDriver.class, String.class});
                        String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        if (tempResult.contains("_:REX:_")) {
                            appInputFeedBackMode = new String("duplicatedMatch");
                        } else if (tempResult.contains("_~REX~_")) {
                            appInputFeedBackMode = new String("screenMatch");
                        }
                        content4check = new String(content4check + "::" + tempResult);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("pagecontent")) {
                    flag4page = true;
                    System.out.println("Try to find the pagecontent method !!!");
                    System.out.println("Current testNG Suit is running in : " + this.autoOSType);
                    Method setMethod = classType.getMethod("getPageContent", new Class[]{AndroidDriver.class, String.class});
                    String tempResult = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());

                    if (!content4pagecheck.equals("")) {
                        //  content4pagecheck = strCompAppen(content4pagecheck, tempResult);
                        content4pagecheck = new String(tempResult);
                    }
                    appInputFeedBackMode = new String("pageMatch");
                    resultPageName = new String("");
                } else if (tempType.contains("dropdownlist")) {
                    System.out.println("Try to find the dropDownList method !!!");
                    if (tempType.contains("_input")) {
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        System.out.println("para4NextStep for the dropDownList method is : " + para4this);
                        Method setMethod = classType.getMethod("dropDownListOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        //content4input = new String(content4input + "::" + (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this));
                        String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                            content4input = new String(content4input + "::" + tempR);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(tempR);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + tempR);
                            }
                        } else {
                            content4input = new String(content4input + "::" + para4this);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        }
                    } else if (tempType.contains("_ignore")) {
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        System.out.println("para4NextStep for the dropDownList method is : " + para4this);
                        Method setMethod = classType.getMethod("dropDownListOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    } else {
                        Method setMethod = classType.getMethod("dropDownListOperation", new Class[]{AndroidDriver.class, String.class});
                        String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                            content4input = new String(content4input + "::" + tempR);
                        } else {
                            content4input = new String(content4input + "::" + para4this);
                        }
                        //new String(content4input + "::" + (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName()));
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("multilist")) {
                    System.out.println("Try to find the multiList method !!!");
                    if (tempType.contains("_input")) {
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        System.out.println("para4NextStep for the multiList method is : " + para4this);
                        Method setMethod = classType.getMethod("itemListOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        if (!tempType.contains("_ignore")) {
                            String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                            if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                                content4input = new String(content4input + "::" + tempR);
                                if (key4searchMatch == "") {
                                    key4searchMatch = new String(tempR);
                                } else {
                                    key4searchMatch = new String(key4searchMatch + "::" + tempR);
                                }
                            } else {
                                content4input = new String(content4input + "::" + para4this);
                                if (key4searchMatch == "") {
                                    key4searchMatch = new String(para4this);
                                } else {
                                    key4searchMatch = new String(key4searchMatch + "::" + para4this);
                                }
                            }
                        } else {
                            setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        }
                    } else {
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        Method setMethod = classType.getMethod("itemListOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("singlelist")) {
                    System.out.println("Try to find the singleList method !!!");
                    if (tempType.contains("_input")) {
                        System.out.println("Check the extPara [" + newIndex + "] is : " + extPara[newIndex]);
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        System.out.println("para4NextStep for the singleList method is : " + para4this);
                        Method setMethod = classType.getMethod("itemListOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        if (!tempType.contains("_ignore")) {
                            String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                            if (tempR != "" && tempR != null && !tempR.equalsIgnoreCase("emptyContent")) {
                                content4input = new String(content4input + "::" + tempR);
                                if (key4searchMatch == "") {
                                    key4searchMatch = new String(tempR);
                                } else {
                                    key4searchMatch = new String(key4searchMatch + "::" + tempR);
                                }
                            } else {
                                content4input = new String(content4input + "::" + para4this);
                                if (key4searchMatch == "") {
                                    key4searchMatch = new String(para4this);
                                } else {
                                    key4searchMatch = new String(key4searchMatch + "::" + para4this);
                                }
                            }
                        } else {
                            setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                        }
                    } else {
                        para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                        newIndex++;
                        Method setMethod = classType.getMethod("itemListOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        String tempR = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.contains("autocomplete")) {
                    System.out.println("Try to find the autoComplete method !!!");
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;

                    Method setMethod = classType.getMethod("autoCompleteOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                    String searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    if (searchItemContent != "" && searchItemContent != null && !searchItemContent.equalsIgnoreCase("emptyContent")) {
                        content4input = new String(content4input + "::" + searchItemContent);
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(searchItemContent);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + searchItemContent);
                        }
                    } else {
                        content4input = new String(content4input + "::" + para4this);
                        appInputFeedBackMode = new String("try2match");
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(para4this);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + para4this);
                        }
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.toLowerCase().startsWith("boxsearch")) {
                    System.out.println("Try to find the boxsearch method !!!");
                    //  para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    //  newIndex++;
                    String searchItemContent = null;
                    if (tempType.toLowerCase().endsWith("_click")) {
                        Method setMethod = classType.getMethod("boxSearchClickOperation", new Class[]{AndroidDriver.class, String.class});
                        setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                    } else if (tempType.toLowerCase().endsWith("_titlecontent")) {
                        Method setMethod = classType.getMethod("boxSearchTitleContentListOperation", new Class[]{AndroidDriver.class, String.class});
                        searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        content4check = new String(content4check + "::" + searchItemContent);
                        if (searchItemContent.contains("_:REX:_")) {
                            appInputFeedBackMode = new String("duplicatedMatch");
                        }
                    } else if (tempType.toLowerCase().endsWith("_sampletitlecontent")) {
                        String[] sampleSet = content4input.split("::");
                        String sample = sampleSet[sampleSet.length - 1];
                        Method setMethod = classType.getMethod("boxSearchTitleContentListOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                        searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), sample);
                        content4check = new String(content4check + "::" + searchItemContent);
                        if (searchItemContent.contains("_:REX:_")) {
                            appInputFeedBackMode = new String("duplicatedMatch");
                        }
                    } else if (tempType.toLowerCase().endsWith("_titleANDclick")) {
                        Method setMethod = classType.getMethod("boxSearchTitleANDClickOperation", new Class[]{AndroidDriver.class, String.class});
                        searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        if (searchItemContent != "" && searchItemContent != null && !searchItemContent.equalsIgnoreCase("emptyContent")) {
                            content4input = new String(content4input + "::" + searchItemContent);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(searchItemContent);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + searchItemContent);
                            }
                        } else {
                            content4input = new String(content4input + "::" + para4this);
                            appInputFeedBackMode = new String("try2match");
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        }
                    } else if (tempType.toLowerCase().endsWith("_getcontent")) {
                        Method setMethod = classType.getMethod("boxSearchContentOperation", new Class[]{AndroidDriver.class, String.class});
                        searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName());
                        if (searchItemContent != "" && searchItemContent != null && !searchItemContent.equalsIgnoreCase("emptyContent")) {
                            content4input = new String(content4input + "::" + searchItemContent);
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(searchItemContent);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + searchItemContent);
                            }
                        } else {
                            content4input = new String(content4input + "::" + para4this);
                            appInputFeedBackMode = new String("try2match");
                            if (key4searchMatch == "") {
                                key4searchMatch = new String(para4this);
                            } else {
                                key4searchMatch = new String(key4searchMatch + "::" + para4this);
                            }
                        }
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
                } else if (tempType.toLowerCase().startsWith("search")) {
                    System.out.println("Try to find the search method !!!");
                    para4this = choosePara(extPara[newIndex], cOpeItem.getElementPara(), newIndex);
                    newIndex++;

                    Method setMethod = classType.getMethod("searchOperation", new Class[]{AndroidDriver.class, String.class, String.class});
                    String searchItemContent = (String) setMethod.invoke(devicePage4next, theDriver, cOpeItem.getElementName(), para4this);
                    if (searchItemContent != "" && searchItemContent != null && !searchItemContent.equalsIgnoreCase("emptyContent")) {
                        content4input = new String(content4input + "::" + searchItemContent);
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(searchItemContent);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + searchItemContent);
                        }
                    } else {
                        content4input = new String(content4input + "::" + para4this);
                        appInputFeedBackMode = new String("try2match");
                        if (key4searchMatch == "") {
                            key4searchMatch = new String(para4this);
                        } else {
                            key4searchMatch = new String(key4searchMatch + "::" + para4this);
                        }
                    }
                    Method getMethod = classType.getDeclaredMethod("value4compare", new Class[]{});
                    String temp4date = (String) getMethod.invoke(devicePage4next);
                    if (temp4date != null && temp4date != "") {
                        date4diffPage = new String(temp4date);
                    }
                    resultPageName = new String("");
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
                            List<String> tempWinList = new ArrayList<String>();
                            tempWinList.clear();
                            if (tempWinName.contains(";")) {
                                String[] tString4name = tempWinName.split(";");
                                for (int ind = 0; ind < tString4name.length; ind++) {
                                    tempWinList.add(tString4name[ind]);
                                }
                            } else {
                                tempWinList.add(tempWinName);
                            }
                            for (String thex : tempWinList) {
                                if (thex.toLowerCase().contains("errorwin")) {
                                    eleWinListAfterClick.add(thex);
                                    if (thex.equalsIgnoreCase("errorwin")) {
                                        this.errWindowFromElement = new String(thex);
                                    } else {
                                        eleErrorWinListAfterClick.add(thex);
                                    }
                                } else if (thex.toLowerCase().contains("confirmwin")) {
                                    eleWinListAfterClick.add(thex);
                                    this.confirmWindowFromElement = new String(thex);
                                } else if (thex.toLowerCase().contains("completewin")) {
                                    eleWinListAfterClick.add(thex);
                                    this.completeWindowFromElement = new String(thex);
                                } else {
                                    throw new XMLException("wrong content in triggerWindow part of page XML!!!");
                                }
                            }
                        }
                    }

                    //todo
                    if (tempType.contains("_skiperror")) {

                    }

                } else {
                    System.out.println("no element trigger window in this page");
                    this.errWindowFromElement = new String("");
                    this.confirmWindowFromElement = new String("");
                    this.completeWindowFromElement = new String("");
                    eleErrorWinListAfterClick.clear();
                }
                this.paraIndex = newIndex;
                if (!classType.getSimpleName().endsWith("Win")) {
                    if (!classType.getSimpleName().equals(resultPageName) && !resultPageName.equals("Test failed")) {
                        System.out.println(" ~~~~~~~ Set the Title Check Flag before leave the page :" + classType.getSimpleName() + "~~~~~~~ ");
                        //TODO set the titlecheck flag before leave the page
                        Method setTitleCheckT = classType.getDeclaredMethod("setFlag4TitleCheckTrue");
                        setTitleCheckT.invoke(devicePage4next);
                    }
                }
            }
            return resultPageName;
        } catch (Exception e) {
            InvocationTargetException targetEx = (InvocationTargetException) e;
            Throwable t = targetEx.getTargetException();
            System.out.println("~~~~ t.getclass.getsimplename : " + t.getClass().getSimpleName());
            if (t.getClass().getSimpleName().contains("TimeoutException")) {
                throw new TimeoutException(t);
            } else {
                System.out.println("Exception appear in BaseAction :: commonMethodHandle");
                throw e;
            }
        }
    }


    /*

    public FirefoxDriver comOperationInWEBFOX(FirefoxDriver theDriver, String[] opeSeq, String[] extPara) throws Exception {
        HashMap<String, operationItem> wholeSeqMap = new HashMap<String, operationItem>();
        HashMap<String, operationItem> singleSeqMap = new HashMap<String, operationItem>();
        String testENVpreName = "FOXPage";
        String filepath4XML = new String("REXSH.REXAUTO.APPunderTest.AppPage.fox.");
        String elePageName4Last = null;
        long gstartTime = System.currentTimeMillis();
        this.foxPageLogin = new FOXPageLogin(theWebDriver, this.path4Log);
        //this.theNaviBar = new bANDPageNaviBar(theDriver, this.path4Log);
        this.foxPageHome = new FOXPageDashBoard(theWebDriver, this.path4Log);
        System.out.println("^^^^^^    pre-createInstance：" + (System.currentTimeMillis() - gstartTime) + " ms    ^^^^^^");
        Object devicePage4next = null;
        Object devicePage4last = null;
        HashMap<String, Object> pageGroup = new HashMap<String, Object>();
        pageGroup.put("loginPage", this.basePage);
        pageGroup.put("NaviBar", this.theNaviBar);
        pageGroup.put("homePage", this.homePage);
        String name4DevicePage = null;
        this.paraIndex = 0;
        String elePageName4next = null;
        Class classType = null; // Class.forName(testENVpreName + operationPage4this);
        this.result4exe = new StringBuffer().append("teststart");
        String tempName4Screen = null;
        String eleName4ThisStep = "";
        String eleName4LastStep = "";
        String currentOpeItemStepName4Error = "";
        String lastOpeItemStepName4Error = "";
        int keywordLength = 0;
        int subkeywordLength = 0;
        try {
            wholeSeqMap = getOperationSeq(opeSeq);
            if (keywordLength == 0) {
                keywordLength = opeSeq.length;
            }
            for (int ind4ope = 0; ind4ope < opeSeq.length; ) {

                singleSeqMap = analyOpeSequence(wholeSeqMap, opeSeq[ind4ope]);    // get the use part
                if (subkeywordLength == 0) {
                    subkeywordLength = singleSeqMap.size();
                }
                keywordLength--;
                String operationPage4this = null;
                List list = new ArrayList(singleSeqMap.keySet());
                Object[] ary = list.toArray();
                Arrays.sort(ary);
                list = Arrays.asList(ary);
                operationItem tOpeItem = null;
                int ind_opeItem = 0;
                for (Object opeItem : list) {
                    subkeywordLength--;
                    lastOpeItemStepName4Error = new String(currentOpeItemStepName4Error);
                    ind_opeItem++;
                    System.out.println("Start to handle the " + ind_opeItem + "nd :: " + list.size() + " opeItem in : " + opeItem);
                    System.out.println("Check the Object in the sub operation item list : " + singleSeqMap.get(opeItem).getElementName());
                    eleName4LastStep = eleName4ThisStep;
                    operationItem currentOpeItem = singleSeqMap.get(opeItem);
                    eleName4ThisStep = new String(currentOpeItem.getElementName());
                    System.out.println("Check the sub operationItem " + currentOpeItem.getElementName());
                    String operationPage4Last = operationPage4this;
                    operationPage4this = currentOpeItem.getPageName();
                    tempName4Screen = operationPage4this;
                    String para4NextStep = null;

                    // if (((elePageName4next == null) || (elePageName4next.equals(""))) && ((name4DevicePage == null) || (name4DevicePage.equals("")))) {

                    if (((elePageName4next == null)) && ((name4DevicePage == null) || (name4DevicePage.equals("")))) {
                        System.out.println(" ~~~~~~~~~ in the 1 fork");
                        //         System.out.println("operationPage4this " + operationPage4this + " : basePage.getClass().getSimpleName())" + basePage.getClass().getSimpleName());
                        for (Iterator it = pageGroup.entrySet().iterator(); it.hasNext(); ) {
                            System.out.println(" ~~~~~~~~~ in new 1.1 loop");
                            Map.Entry eleEntry = (Map.Entry) it.next();
                            String tempPageName = eleEntry.getValue().getClass().getSimpleName();
                            if (tempPageName.contains(testENVpreName + operationPage4this)) {
                                System.out.println("Get the : " + tempPageName + " in the pageGroup ");
                                devicePage4next = eleEntry.getValue();
                                classType = eleEntry.getValue().getClass();
                                if (elePageName4next != null) {
                                    elePageName4Last = elePageName4next;
                                }
                                currentOpeItemStepName4Error = currentOpeItem.getElementName();
                                elePageName4next = commonMethodHandleFOX(currentOpeItem.getElementType(), currentOpeItem, devicePage4next, classType, extPara, this.paraIndex, devicePage4last);
                                if (elePageName4next != null && elePageName4next.startsWith("Test failed")) {
                                    System.out.println("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                                    throw new PageTitleException("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                                }
                            }

                        }
                        System.out.println("Stop after fork 1");
                    } else if ((operationPage4this != null) && (!operationPage4this.equals(""))) {
                        boolean flag4pageGroup = false;
                        System.out.println(" ~~~~~~~~~ in the fork 2");
                        String tempTypeINfork2 = currentOpeItem.getElementType().toLowerCase();

                        for (Iterator it = pageGroup.entrySet().iterator(); it.hasNext(); ) {
                            System.out.println(" ~~~~~~~~~ in the fork 2 loop");
                            Map.Entry eleEntry = (Map.Entry) it.next();
                            String tempPageName = eleEntry.getValue().getClass().getSimpleName();
                            if (tempPageName.equalsIgnoreCase(testENVpreName + operationPage4this)) {
                                System.out.println("Get the : " + tempPageName + " in the pageGroup ");
                                devicePage4next = eleEntry.getValue();
                                classType = eleEntry.getValue().getClass();
                                flag4pageGroup = true;
                                break;
                            }
                        }
                        if (flag4pageGroup == false) {
                            classType = Class.forName("REXSH.REXAUTO.APPunderTest.AppPage.fox." + testENVpreName + operationPage4this);
                            // classType = Class.forName(classShortName2completeName.AndroidList.get(operationPage4this)+operationPage4this); //TODO
                            long startTime = System.currentTimeMillis();
                            devicePage4next = createInstance(classType, theWebDriver);
                            System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                            pageGroup.put(operationPage4this, devicePage4next);
                        }
                        if (elePageName4next != null) {
                            elePageName4Last = elePageName4next;
                        }
                        currentOpeItemStepName4Error = currentOpeItem.getElementName();
                        elePageName4next = commonMethodHandleFOX(tempTypeINfork2, currentOpeItem, devicePage4next, classType, extPara, this.paraIndex, devicePage4last);
                        if (elePageName4next != null && elePageName4next.startsWith("Test failed")) {
                            System.out.println("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                            throw new PageTitleException("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                        }
                        System.out.println("Stop after fork 2");
                    } else if (((operationPage4this != null) && (!operationPage4this.equals(""))) && ((name4DevicePage != null)) && (!name4DevicePage.equals(""))) {
                        System.out.println(" ~~~~~~~~~ in the fork 3");
                        String tempTypeINfork3 = currentOpeItem.getElementType().toLowerCase();
                        System.out.println("devicePage4next " + name4DevicePage + " : operationPage4this " + operationPage4this + "");
                        if ((name4DevicePage.toLowerCase().contains(operationPage4this.toLowerCase()))) {
                            System.out.println(" ~~~~~~~~~ in the fork 3.1");
                            classType = Class.forName("REXSH.REXAUTO.APPunderTest.AppPage.fox." + testENVpreName + operationPage4this);
                            System.out.println("Check the device Page class before refresh it : " + devicePage4next.getClass());
                            if ((operationPage4Last != null) && (operationPage4this.equalsIgnoreCase(operationPage4Last))) {  // ope.this == ope.last
                                if (devicePage4next.getClass().getSimpleName().contains(operationPage4this)) {  // instance == ope.this
                                    System.out.println("Same Page as last time, don't reload the page content");
                                } else if (!devicePage4next.getClass().getSimpleName().contains(operationPage4this)) {  // instance ! ope.this
                                    long startTime = System.currentTimeMillis();
                                    devicePage4next = createInstance(classType, theWebDriver);
                                    System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                                    pageGroup.put(operationPage4this, devicePage4next);
                                }

                            } else if ((elePageName4next != null) && (!elePageName4next.equals("")) && (!elePageName4next.equalsIgnoreCase("noRoute"))) {
                                if (operationPage4this.equals(elePageName4next)) {  //  ope.this == route of last element
                                    System.out.println("operation 3.1.2.1 this is same as route of last element ");
                                    classType = Class.forName("REXSH.REXAUTO.APPunderTest.AppPage.fox." + testENVpreName + elePageName4next);
                                    long startTime = System.currentTimeMillis();
                                    devicePage4next = createInstance(classType, theWebDriver);
                                    System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                                    pageGroup.put(operationPage4this, devicePage4next);
                                } else {   //  ope.this != route of last element
                                    System.out.println("operation 3.1.2.2 this is diff with route of last element ");
                                    classType = Class.forName("REXSH.REXAUTO.APPunderTest.AppPage.fox." + testENVpreName + operationPage4this);
                                }
                            } else if ((name4DevicePage != null) && (!name4DevicePage.equals("")) && (!name4DevicePage.equals("noRoute"))) {
                                classType = Class.forName("REXSH.REXAUTO.APPunderTest.AppPage.fox." + testENVpreName + name4DevicePage);
                                long startTime = System.currentTimeMillis();
                                devicePage4next = createInstance(classType, theWebDriver);
                                System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                                pageGroup.put(operationPage4this, devicePage4next);
                            } else if ((devicePage4next == null) || (devicePage4next == "")) {
                                System.out.println("In 3.2 other issue 4");
                                classType = Class.forName("REXSH.REXAUTO.APPunderTest.AppPage.fox." + testENVpreName + operationPage4this);
                                long startTime = System.currentTimeMillis();
                                devicePage4next = createInstance(classType, theWebDriver);
                                System.out.println("^^^^^^    createInstance：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
                                pageGroup.put(operationPage4this, devicePage4next);
                            }

                            if (elePageName4next != null) {
                                elePageName4Last = elePageName4next;
                            }
                            currentOpeItemStepName4Error = currentOpeItem.getElementName();
                            elePageName4next = commonMethodHandleFOX(tempTypeINfork3, currentOpeItem, devicePage4next, classType, extPara, this.paraIndex, devicePage4last);
                            if (elePageName4next != null && elePageName4next.startsWith("Test failed")) {
                                System.out.println("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                                throw new PageTitleException("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                            }
                        }

                    }
                    // do the operation
                    else if (!name4DevicePage.toLowerCase().contains(operationPage4this.toLowerCase())) {

                        //   name4DevicePage = autoSwitch(classType, theWebDriver, currentOpeItem, devicePage4next, operationPage4this, name4DevicePage);
                        System.out.println(" ~~~~~~~~~ After auto switch in 4.2 ");


                        classType = Class.forName("REXSH.REXAUTO.APPunderTest.AppPage.fox." + testENVpreName + operationPage4this);
                        System.out.println("Check the device Page class before refresh it : " + devicePage4next.getClass());
                        if (elePageName4next != null) {
                            elePageName4Last = elePageName4next;
                        }
                        currentOpeItemStepName4Error = currentOpeItem.getElementName();
                        elePageName4next = commonMethodHandleFOX(currentOpeItem.getElementType(), currentOpeItem, devicePage4next, classType, extPara, this.paraIndex, devicePage4last);
                        if (elePageName4next != null && elePageName4next.startsWith("Test failed")) {
                            System.out.println("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                            throw new PageTitleException("Step : " + eleName4LastStep + " Error, " + elePageName4next);
                        }
                    } else {
                        System.out.println("4.3");
                    }
                    // (Class cType, AppiumDriver theD, operationItem cOpeItem, Object dP4N, String oP4this, String n4DP) throws Exception {
                    System.out.println("Stop after all fork ");
                    //  start the page check after every element operation done
                    devicePage4last = devicePage4next;
                    classType = devicePage4last.getClass();
                    System.out.println(new StringBuffer("@@ classType : ").append(classType));


                    if (this.eleWinListAfterClick.size() == 0) {

                    } else {
                        if (!currentOpeItem.getElementType().toLowerCase().contains("_skiperror")) {
                            elePageName4next = handleEleTriggerWinList(eleWinListAfterClick, eleErrorWinListAfterClick, filepath4XML, testENVpreName, theDriver, pageGroup, operationPage4this, devicePage4next, tempName4Screen, currentOpeItem, elePageName4Last, result4exe);
                        } else {
                            elePageName4next = handleEleTriggerWinListSkipError(eleWinListAfterClick, eleErrorWinListAfterClick, filepath4XML, testENVpreName, theDriver, pageGroup, operationPage4this, devicePage4next, tempName4Screen, currentOpeItem, elePageName4Last, result4exe);
                        }
                        eleWinListAfterClick = new ArrayList<String>();
                        this.errWindowFromElement = new String("");
                        this.confirmWindowFromElement = new String("");
                        this.completeWindowFromElement = new String("");
                        eleErrorWinListAfterClick.clear();
                    }


//  end the page check after every element operation done
                    if ((elePageName4next != null) && (!elePageName4next.equals(""))) {
                        System.out.println("In Sub-operation list, the operation in last page done, now let's move to : " + elePageName4next);
                        name4DevicePage = elePageName4next; //TODO should check the page title before this
                        if (name4DevicePage.endsWith("Page")) {
                            name4DevicePage = name4DevicePage.substring(0, name4DevicePage.length() - 4);
                        }
                        elePageName4next = null;
                    }
                }
                System.out.println("Stop 3.5");
                if (result4exe.toString().equals("teststart")) {
                    System.out.println(new StringBuffer("Single action pass : ").append(ind4ope));
                } else {
                    //   throw new FFPandaException(new StringBuffer("Single action Error").append(ind4ope).append(" : ").append(result4exe));
                    System.out.println("result4exe has been changed : " + result4exe.toString());
                }
                ind4ope = ind4ope + 1;
                System.out.println(new StringBuffer("    ^^^^^^ Now ind4ope is :").append(ind4ope));
                System.out.println("    ^^^^^^ Check the issue in the end ");
            }
            if (flag4page = true) {
                content4check = new String(content4check + content4pagecheck);
            }
            if ((elePageName4next != null) && (!elePageName4next.equals(""))) {
                System.out.println("In the whole operation map, the operation in last page done, now let's move to : " + elePageName4next);
                name4DevicePage = elePageName4next;
                if (name4DevicePage.endsWith("Page")) {
                    name4DevicePage = name4DevicePage.substring(0, name4DevicePage.length() - 4);
                }
                elePageName4next = null;
            }
            System.out.println("Stop 4");
            result4exe = resultContentCheck(result4exe, theDriver);

            if (keywordLength == 0 && subkeywordLength == 0) {
                if (result4exe.toString().equals("teststart")) {
                    System.out.println(" All action pass ! ");
                } else {
                    System.out.println(" Last action cause some error ! ");
                }
            } else {
                throw new PageTitleException("Step : " + lastOpeItemStepName4Error + " Error, " + elePageName4Last);
            }

        } catch (Exception e) {
            if (e.getClass().getSimpleName().equalsIgnoreCase("TimeoutException") || e.getClass().getSimpleName().equalsIgnoreCase("NoSuchElementException")) {
                throw new PageTitleException("Step : " + lastOpeItemStepName4Error + " Error, " + elePageName4Last);
            } else if (e.getClass().getSimpleName().equalsIgnoreCase("PageTitleException")) {
                throw e;
            } else {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                String tSpLast = devicePage4next.getClass().getSimpleName();
                ScreenShot(theDriver, "runtime_" + tSpLast + "_From_" + tempName4Screen + time + ".png", this.path4Log);
                throw e;
            }
        }

        return theDriver;
    }



     */
}
