package Component.Page;

import Component.Page.Element.ElementMapping;
import LocalException.PageException;
import utility.MyJsonReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static utility.XmlUtils.*;

/**
 * Created by appledev131 on 4/12/16.
 */
public class ANDPageContent {
    protected HashMap<String, ElementMapping> eleContMapInContent = new HashMap<String, ElementMapping>();  // for ready element
    protected HashMap<String, ElementMapping> eleContMapInContentBase = new HashMap<String, ElementMapping>();  // for all element
    protected AndroidDriver theDriver = null;
    protected String fileNameInContent = null;
    protected int osTypeInContent = 0;  // 1 for android, 2 for ios
    protected HashMap<String, ElementMapping> eleContMap4Route = new HashMap<String, ElementMapping>();  // for all element with route
    public HashMap<String, String> eleDefaultValueMap = null;
    public HashMap<String, String> eleTextContentMap = null;
    public HashMap<String, Integer> eleWaitStepMap = null;
    public HashMap<String, String> eleWindowMap = null;
    public boolean flag4EleWindow = false;
    public boolean flag4TitleCheck = true;
    public ANDPageContentMap insideMapSet = null;
    private String logSpace4thisPage = " --- ANDBasePageContent ---";

    public ANDPageContent(AndroidDriver driver, String fileName, Integer osType) throws Exception {
        this.theDriver = driver;
        this.fileNameInContent = fileName;
        this.osTypeInContent = osType;
        System.out.println(logSpace4thisPage + " init!!!");
        try {
            insideMapSet = new ANDPageContentMap(fileName, osType);
            this.eleContMapInContentBase = insideMapSet.getEleMapBase();
            this.eleContMapInContent = insideMapSet.getEleMap();
            this.eleContMap4Route = insideMapSet.getEleRouteMap();
            this.eleTextContentMap = insideMapSet.getEleTextMap();
            this.eleDefaultValueMap = insideMapSet.getEleDValueMap();
            this.eleWindowMap = insideMapSet.getEleTWINMap();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        if (eleWindowMap == null || eleWindowMap.size() == 0) {
            flag4EleWindow = false;
        } else {
            flag4EleWindow = true;
        }
    }

    public void setFlag4TitleCheck(boolean para) {
        System.out.println(logSpace4thisPage + " ~~~~ Set flag4TitleCheck as para ~~~~");
        this.flag4TitleCheck = para;
    }

    public boolean getFlag4TitleCheck() {
        System.out.println(logSpace4thisPage + " ~~~~ Get flag4TitleCheck ~~~~");
        return this.flag4TitleCheck;
    }

    public HashMap<String, ElementMapping> getContentMap(HashMap<String, ElementMapping> xMap) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap ~~~~");
        return xMap;
    }

    public HashMap<String, ElementMapping> getContentMap() {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap ~~~~");
        return this.eleContMapInContent;
    }


    public HashMap<String, String> getDefaultValueMap() throws Exception {
        return this.eleDefaultValueMap;
    }

    public HashMap<String, String> getDefaultValueMap(String path, int osType, String tPageName) throws Exception {
        System.out.println("BasePageContent : the getDefaultValueMap with OS TYPE is called");
        try {
            if (osType == 2) {
                //return findIOSDefaultValueList(path);
                return MyJsonReader.readJSON_4_dValue(path);
            } else if (osType == 1) {
                return MyJsonReader.readJSON_4_dValue(path);
            } else {
                throw new PageException("Wrong OS Type !!! +++ " + this.getClass().getSimpleName() + " +++ ");
            }
        } catch (Exception e) {
            throw new PageException("Failed to getDefaultValueMap !!! +++ " + this.getClass().getSimpleName() + " +++ " + e.getCause());
        }
    }


    public HashMap<String, Integer> getWaitStepMap(String path, int osType, String tPageName) throws Exception {
        System.out.println("BasePageContent : the getWaitStepMap with OS TYPE is called");
        try {
            if (osType == 2) {
                return findIOSWaitStepList(path);
            } else if (osType == 1) {
                return findANDWaitStepList(path);
            } else {
                throw new PageException("Wrong OS Type !!! +++ " + this.getClass().getSimpleName() + " +++ ");
            }
        } catch (Exception e) {
            throw new PageException("Failed to getWaitStepMap !!! +++ " + this.getClass().getSimpleName() + " +++ " + e.getCause());
        }
    }


    public HashMap<String, String> getWindowMap(String path, int osType, String tPageName) throws Exception {
        System.out.println("BasePageContent : the getWindowMap with OS TYPE is called");
        try {
            if (osType == 2) {
                //return findIOSWindowList(path);
                return MyJsonReader.readJSON_4_tWin(path);
            } else if (osType == 1) {
                return MyJsonReader.readJSON_4_tWin(path);
            } else {
                throw new PageException("Wrong OS Type !!! +++ " + this.getClass().getSimpleName() + " +++ ");
            }
        } catch (Exception e) {
            throw new PageException("Failed to getWindowMap !!! +++ " + this.getClass().getSimpleName() + " +++ " + e.getCause());
        }
    }

    public HashMap<String, String> getTextContentMap(String path, int osType, String tPageName) throws Exception {
        System.out.println("BasePageContent : the getTextContentMap with OS TYPE is called");
        try {
            if (osType == 2) {
                //    return findIOSTextContentList(path);
                return MyJsonReader.readJSON_4_textContent(path);
            } else if (osType == 1) {
                return MyJsonReader.readJSON_4_textContent(path);
            } else {
                throw new PageException("Wrong OS Type !!! +++ " + this.getClass().getSimpleName() + " +++ ");
            }
        } catch (Exception e) {
            throw new PageException("Failed to getTextContentMap !!! +++ " + this.getClass().getSimpleName() + " +++ " + e.getCause());
        }
    }

    public HashMap<String, ElementMapping> getContentMap4Load(HashMap<String, ElementMapping> xMap) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap ~~~~");
        HashMap<String, ElementMapping> result = new HashMap<String, ElementMapping>();
        HashMap<String, ElementMapping> temp = xMap;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            if (((ElementMapping) eleEntry.getValue()).getMode().equals("loading")) {
                result.put((String) eleEntry.getKey(), (ElementMapping) eleEntry.getValue());
            }
        }
        return result;
    }

    public HashMap<String, ElementMapping> getContentMap4Temp(HashMap<String, ElementMapping> xMap) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap ~~~~");
        HashMap<String, ElementMapping> result = new HashMap<String, ElementMapping>();
        HashMap<String, ElementMapping> temp = xMap;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            if (((ElementMapping) eleEntry.getValue()).getMode().equals("temp")) {
                result.put((String) eleEntry.getKey(), (ElementMapping) eleEntry.getValue());
            }
        }
        return result;
    }

    public HashMap<String, ElementMapping> getContentMap4Type(HashMap<String, ElementMapping> xMap, String type) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap ~~~~");
        HashMap<String, ElementMapping> result = new HashMap<String, ElementMapping>();
        HashMap<String, ElementMapping> temp = xMap;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            if (((ElementMapping) eleEntry.getValue()).getMode().equals(type)) {
                result.put((String) eleEntry.getKey(), (ElementMapping) eleEntry.getValue());
            }
        }
        return result;
    }

    public HashMap<String, ElementMapping> getContentMap4Route(HashMap<String, ElementMapping> xMap, String type) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap ~~~~");
        HashMap<String, ElementMapping> result = new HashMap<String, ElementMapping>();
        HashMap<String, ElementMapping> temp = xMap;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            if ((!((ElementMapping) eleEntry.getValue()).getNextPage().equals("")) && ((!((ElementMapping) eleEntry.getValue()).getNextPage().equalsIgnoreCase("noRoute")))) {
                result.put((String) eleEntry.getKey(), (ElementMapping) eleEntry.getValue());
            }
        }
        return result;
    }

    public HashMap<String, ElementMapping> getAllMap4JSON(String fileName, int osType) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap4JSON ~~~~"); // get all elements in xml file
        HashMap<String, ElementMapping> result = null;
        try {
            if (1 == osType) {
                result = MyJsonReader.readJSON_4_WebElement(fileName);
            } else if (2 == osType) {
                result = MyJsonReader.readJSON_4_WebElement(fileName);
            } else {
                System.out.println("Wrong osType for andPageContent . getContentMap4JSON");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public HashMap<String, ElementMapping> getContentMap4JSON(String fileName, int osType) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap4JSON ~~~~"); // get all elements in xml file
        HashMap<String, ElementMapping> result = null;
        try {
            if (1 == osType) {
                result = MyJsonReader.readJSON_4_WebElement(fileName);
            } else if (2 == osType) {
                result = MyJsonReader.readJSON_4_WebElement(fileName);
            } else {
                System.out.println("Wrong osType for andPageContent . getContentMap4JSON");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String[] getContentNameList4type(HashMap<String, ElementMapping> xMap, String type) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap4Type ~~~~");
        String[] x = null;
        HashMap<String, ElementMapping> temp = xMap;
        int account = 0;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            if (((ElementMapping) eleEntry.getValue()).getMode().equals(type)) {
                account++;
            }
        }
        if (account > 0) {
            String[] tempS = new String[account];
            for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry eleEntry = (Map.Entry) it.next();
                for (int ind = 0; ind < account; ind++) {
                    if (((ElementMapping) eleEntry.getValue()).getMode().equals(type)) {
                        tempS[ind] = ((ElementMapping) eleEntry.getValue()).getElementName();
                    }
                }
            }
            x = tempS;
        }
        return x;
    }

    public String[] getContentNameList4name(HashMap<String, ElementMapping> xMap, String name) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap4Type ~~~~");
        String[] result = null;
        HashMap<String, ElementMapping> temp = xMap;
        int account = 0;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            if (((ElementMapping) eleEntry.getValue()).getElementName().equals(name)) {
                account++;
            }
        }
        if (account > 0) {
            String[] tempS = new String[account];
            for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry eleEntry = (Map.Entry) it.next();
                for (int ind = 0; ind < account; ind++) {
                    if (((ElementMapping) eleEntry.getValue()).getElementName().equals(name)) {
                        tempS[ind] = ((ElementMapping) eleEntry.getValue()).getElementName();
                    }
                }
            }
            result = tempS;
        }
        return result;
    }


    public HashMap<String, ElementMapping> getContentMap4Base() {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap4Base ~~~~"); // get all elements in xml file
        return this.eleContMapInContentBase;
    }

    public HashMap<String, ElementMapping> getContentMap4Base(String fileName, int osType) {
        return this.eleContMapInContentBase;
    }

    public HashMap<String, ElementMapping> getLocalContentMap(String fileName, int osType) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap ~~~~");
        HashMap<String, ElementMapping> result = null;
        try {
            if (this.eleContMapInContent == null) {
                result = getContentMap(this.eleContMapInContentBase);
            } else {
                result = this.eleContMapInContent;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public HashMap<String, AndroidElement> getElementMap(AndroidDriver driver) throws Exception {
        HashMap result = new HashMap<String, AndroidElement>();
        result.clear();
        AndroidElement tempElement = null;
        System.out.println(logSpace4thisPage + " -- Check the driver in ANDPageContent . getElementMap  : " + driver.toString());
        for (Iterator it = this.eleContMapInContent.entrySet().iterator(); it.hasNext(); ) {
            try {
                Thread.sleep(50);
                Map.Entry tempContentEntry = (Map.Entry) it.next();
                System.out.println(logSpace4thisPage + " -- Check the. contMap  getkey ~~~~ " + tempContentEntry.getKey());
                System.out.println(logSpace4thisPage + " -- Check the. contMap  getValue~~~~ " + tempContentEntry.getValue());
                if (2 == this.osTypeInContent) {
                    //return resultList;
                } else if (1 == this.osTypeInContent) {
                    System.out.println(logSpace4thisPage + " ^^^^ getElementMap Call combindAndElement ^^^^^^^^^^^^^^^^^");
                    System.out.println("Check the value in Entry in combind : " + (ElementMapping) tempContentEntry.getValue() + " !!! ");
                    tempElement = combindAndElement(driver, (ElementMapping) tempContentEntry.getValue(), 6, this.osTypeInContent);
                    System.out.println("Check the tempElement from combind : " + tempElement.toString() + " !!! ");
                    System.out.println("Check the getKey from combind : " + tempContentEntry.getKey().toString() + " !!! ");
                    System.out.println("Chcek the Class of tempElement getKey " + tempContentEntry.getKey().toString() instanceof String);
                    System.out.println("Chcek the Class of tempElement " + tempElement.getClass());

                    result.put(tempContentEntry.getKey().toString(), tempElement);
                    System.out.println("!!!!!! " + result.toString() + " !!!!!!");
                } else {
                    throw new PageException("Failed to find the combind Method 4 os TYPE in :" + this.getClass().getSimpleName().toString());
                }
            } catch (PageException e) {
                throw new PageException("ANDPageContent.getElementMap() : " + e.getMessage());
            } catch (InterruptedException e) {
                throw e;
            } catch (Exception e) {
                throw e;
            }
        }
        for (Iterator it = result.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            System.out.println(logSpace4thisPage + " @ getElementMap check result value @  " + eleEntry.getKey());
            System.out.println(logSpace4thisPage + " @ getElementMap check result value @  " + eleEntry.getValue());
        }
        return result;
    }

    private AndroidElement combindAndElement(AndroidDriver driver, ElementMapping value, int i, int osTypeInContent) {
    }

    public HashMap<String, AndroidElement> getElementMap(AndroidDriver driver, HashMap<String, ElementMapping> contMap, int osType) throws Exception {
        HashMap result = new HashMap<String, AndroidElement>();
        result.clear();
        AndroidElement tempElement = null;
        System.out.println(logSpace4thisPage + " x- Check the driver in ANDPageContent . getElementMap  : " + driver.toString());

        for (Iterator it = contMap.entrySet().iterator(); it.hasNext(); ) {
            try {
                Thread.sleep(50);
                Map.Entry tempContentEntry = (Map.Entry) it.next();
                System.out.println(logSpace4thisPage + " x- Check the. contMap  getkey ~ " + tempContentEntry.getKey());
                System.out.println(logSpace4thisPage + " x- Check the. contMap  getValue ~ " + tempContentEntry.getValue());
                if (2 == osType) {
                    //return resultList;
                } else if (1 == osType) {
                    System.out.println(logSpace4thisPage + " ^^^^ getElementMap Call combindAndElement ^^^^^^^^^^^^^^^^^");
                    tempElement = combindAndElement(driver, (ElementMapping) tempContentEntry.getValue(), 6, osType);
                    result.put(tempContentEntry.getKey().toString(), tempElement);
                    System.out.println("!!!!!! " + result.toString() + " !!!!!!");
                } else {
                    throw new PageException("Failed to find the combind Method 4 os TYPE in :" + this.getClass().getSimpleName().toString());
                }
            } catch (PageException e) {
                throw new PageException("ANDPageContent.getElementMap() : " + e.getMessage());
            } catch (InterruptedException e) {
                throw e;
            } catch (Exception e) {
                throw e;
            }
        }
        for (Iterator it = result.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            System.out.println(logSpace4thisPage + " @ getElementMap check result value @  " + eleEntry.getKey());
            System.out.println(logSpace4thisPage + " @ getElementMap check result value @  " + eleEntry.getValue());
        }
        return result;
    }

    public static void main(String args[]) {
        try {
            AndroidDriver driver = null;

            File app = new File("/Users/appledev131/Downloads", "app-debug.apk");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("deviceName", "Android Emulator");
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
            capabilities.setCapability(CapabilityType.VERSION, "5.1");
            capabilities.setCapability("app", app.getAbsolutePath());
            capabilities.setCapability("app-package", "com.pwc.talentexchange");
            capabilities.setCapability("app-activity", ".activity.BaseActivity");
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            Thread.sleep(1000);
            System.out.println("Waiting 4 Page loading ~~~~~~~~~~~~~~~~~ for 6000");
            ANDPageContent xCont = new ANDPageContent(driver, "/PageXML/And/loginPage.xml", 1);
            HashMap<String, ElementMapping> eleContMapInContenttest = xCont.getContentMap4Base(("/PageXML/And/loginPage.xml").replaceAll("/", File.separator), 1);

            for (Iterator it = eleContMapInContenttest.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry eleEntry = (Map.Entry) it.next();
                System.out.println("@@@@@@------ eleContMapInContenttest check result value ------@@@@@@  " + eleEntry.getKey());
                System.out.println("@@@@@@------ eleContMapInContenttest check result value ------@@@@@@  " + eleEntry.getValue());
            }

            HashMap<String, AndroidElement> eleMapS = xCont.getElementMap(driver);
            for (Iterator it = eleContMapInContenttest.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry eleEntry = (Map.Entry) it.next();
                System.out.println("@@@@@@------ eleMapSt check result value ------@@@@@@  " + eleEntry.getKey());
                System.out.println("@@@@@@------ eleMapSt check result value ------@@@@@@  " + eleEntry.getValue());
            }

            HashMap<String, AndroidElement> eleMapD = xCont.getElementMap(driver, eleContMapInContenttest, 1);
            for (Iterator it = eleContMapInContenttest.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry eleEntry = (Map.Entry) it.next();
                System.out.println("@@@@@@------ eleMapD check result value ------@@@@@@  " + eleEntry.getKey());
                System.out.println("@@@@@@------ eleMapD check result value ------@@@@@@  " + eleEntry.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public HashMap<String, By> getByMap(AndroidDriver driver, HashMap<String, ElementMapping> eleConMap) {
        HashMap result = new HashMap<String, By>();
        result.clear();
        By tempElement = null;
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in  ANDPageContent. getByMap (AndroidDriver driver)  : " + driver.toString());

        for (Iterator it = eleConMap.entrySet().iterator(); it.hasNext(); ) {
            try {
                Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (!((ElementMapping) tempContentEntry.getValue()).getLocatorStr().contains(":REX")) {
                    if (((ElementMapping) tempContentEntry.getValue()).getType().equalsIgnoreCase("xpath")) {
                        tempElement = By.xpath(((ElementMapping) tempContentEntry.getValue()).getLocatorStr());
                    } else if (((ElementMapping) tempContentEntry.getValue()).getType().equalsIgnoreCase("id")) {
                        tempElement = By.id(((ElementMapping) tempContentEntry.getValue()).getLocatorStr());
                    } else {
                        System.out.println(logSpace4thisPage + "currently we just support xpath & id");
                    }
                    result.put(tempContentEntry.getKey().toString(), tempElement);
                } else {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}
