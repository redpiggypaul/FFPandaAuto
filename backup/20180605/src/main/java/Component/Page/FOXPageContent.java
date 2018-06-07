package REXSH.REXAUTO.Component.Page;

import REXSH.REXAUTO.Component.Page.Element.elementEntity;
import REXSH.REXAUTO.LocalException.PageException;
import utility.MyJsonReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static REXSH.REXAUTO.Component.PageLoadWait.REXDriverWait.combindWebElement;
import static utility.XmlUtils.findANDWaitStepList;
import static utility.XmlUtils.findIOSWaitStepList;

/**
 * Created by appledev131 on 4/12/16.
 */
public class FOXPageContent {
    protected HashMap<StringBuilder, elementEntity> eleContMapInContent = new HashMap<StringBuilder, elementEntity>();  // for ready element
    protected HashMap<StringBuilder, elementEntity> eleContMapInContentBase = new HashMap<StringBuilder, elementEntity>();  // for all element
    protected WebDriver theDriver = null;
    protected String fileNameInContent = null;
    protected int osTypeInContent = 0;  // 1 for android, 2 for ios
    protected HashMap<StringBuilder, elementEntity> eleContMap4Route = new HashMap<StringBuilder, elementEntity>();  // for all element with route
    public HashMap<StringBuilder, StringBuilder> eleDefaultValueMap = null;
    public HashMap<StringBuilder, StringBuilder> eleTextContentMap = null;
    public HashMap<StringBuilder, StringBuilder> eleWindowMap = null;
    public boolean flag4EleWindow = false;
    public boolean flag4TitleCheck = true;
    public FOXPageContentMap insideMapSet = null;
    private String logSpace4thisPage = " --- FOXBasePageContent ---";

    public FOXPageContent(WebDriver driver, String fileName, Integer osType) throws Exception {
        this.theDriver = driver;
        this.fileNameInContent = fileName;
        this.osTypeInContent = osType;
        System.out.println(logSpace4thisPage + " init!!!");
        try {
            insideMapSet = new FOXPageContentMap(fileName, osType);
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

    public HashMap<StringBuilder, elementEntity> getContentMap(HashMap<StringBuilder, elementEntity> xMap) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap ~~~~");
        return xMap;
    }

    public HashMap<StringBuilder, elementEntity> getContentMap() {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap ~~~~");
        return this.eleContMapInContent;
    }


    public HashMap<StringBuilder, StringBuilder> getDefaultValueMap() throws Exception {
        return this.eleDefaultValueMap;
    }

    public HashMap<StringBuilder, StringBuilder> getDefaultValueMap(String path, int osType, String tPageName) throws Exception {
        System.out.println("BasePageContent : the getDefaultValueMap with OS TYPE is called");
        try {
            if (osType == 2) {
                //return findIOSDefaultValueList(path);
                return MyJsonReader.readJSON_4_dValue_NEW(path);
            } else if (osType == 1) {
                return MyJsonReader.readJSON_4_dValue_NEW(path);
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

    public HashMap<String, elementEntity> getContentMap4Load(HashMap<String, elementEntity> xMap) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap ~~~~");
        HashMap<String, elementEntity> result = new HashMap<String, elementEntity>();
        HashMap<String, elementEntity> temp = xMap;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            if (((elementEntity) eleEntry.getValue()).getMode().equals("loading")) {
                result.put((String) eleEntry.getKey(), (elementEntity) eleEntry.getValue());
            }
        }
        return result;
    }

    public HashMap<String, elementEntity> getContentMap4Temp(HashMap<String, elementEntity> xMap) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap ~~~~");
        HashMap<String, elementEntity> result = new HashMap<String, elementEntity>();
        HashMap<String, elementEntity> temp = xMap;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            if (((elementEntity) eleEntry.getValue()).getMode().equals("temp")) {
                result.put((String) eleEntry.getKey(), (elementEntity) eleEntry.getValue());
            }
        }
        return result;
    }

    public HashMap<String, elementEntity> getContentMap4Type(HashMap<String, elementEntity> xMap, String type) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap ~~~~");
        HashMap<String, elementEntity> result = new HashMap<String, elementEntity>();
        HashMap<String, elementEntity> temp = xMap;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            if (((elementEntity) eleEntry.getValue()).getMode().equals(type)) {
                result.put((String) eleEntry.getKey(), (elementEntity) eleEntry.getValue());
            }
        }
        return result;
    }


    public HashMap<StringBuilder, elementEntity> getAllMap4JSON(String fileName, int osType) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap4JSON ~~~~"); // get all elements in xml file
        HashMap<StringBuilder, elementEntity> result = null;
        try {
            if (1 == osType) {
                result = MyJsonReader.readJSON_4_WebElement_NEW(fileName);
            } else if (2 == osType) {
                result = MyJsonReader.readJSON_4_WebElement_NEW(fileName);
            } else {
                System.out.println("Wrong osType for foxPageContent . getContentMap4JSON");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public HashMap<StringBuilder, elementEntity> getContentMap4JSON(String fileName, int osType) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap4JSON ~~~~"); // get all elements in xml file
        HashMap<StringBuilder, elementEntity> result = null;
        try {
            if (1 == osType) {
                result = MyJsonReader.readJSON_4_WebElement_NEW(fileName);
            } else if (2 == osType) {
                result = MyJsonReader.readJSON_4_WebElement_NEW(fileName);
            } else {
                System.out.println("Wrong osType for foxPageContent . getContentMap4JSON");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public StringBuilder[] getContentNameList4type(HashMap<StringBuilder, elementEntity> xMap, String type) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap4Type ~~~~");
        StringBuilder[] x = null;
        HashMap<StringBuilder, elementEntity> temp = xMap;
        int account = 0;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            if (((elementEntity) eleEntry.getValue()).getMode().equals(type)) {
                account++;
            }
        }
        if (account > 0) {
            StringBuilder[] tempS = new StringBuilder[account];
            for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry eleEntry = (Map.Entry) it.next();
                for (int ind = 0; ind < account; ind++) {
                    if (((elementEntity) eleEntry.getValue()).getMode().equals(type)) {
                        tempS[ind] = ((elementEntity) eleEntry.getValue()).getElementName();
                    }
                }
            }
            x = tempS;
        }
        return x;
    }

    public StringBuilder[] getContentNameList4name(HashMap<StringBuilder, elementEntity> xMap, String name) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap4Type ~~~~");
        StringBuilder[] result = null;
        HashMap<StringBuilder, elementEntity> temp = xMap;
        int account = 0;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            if (((elementEntity) eleEntry.getValue()).getElementName().equals(name)) {
                account++;
            }
        }
        if (account > 0) {
            StringBuilder[] tempS = new StringBuilder[account];
            for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry eleEntry = (Map.Entry) it.next();
                for (int ind = 0; ind < account; ind++) {
                    if (((elementEntity) eleEntry.getValue()).getElementName().equals(name)) {
                        tempS[ind] = ((elementEntity) eleEntry.getValue()).getElementName();
                    }
                }
            }
            result = tempS;
        }
        return result;
    }


    public HashMap<StringBuilder, elementEntity> getContentMap4Base() {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap4Base ~~~~"); // get all elements in xml file
        return this.eleContMapInContentBase;
    }

    public HashMap<StringBuilder, elementEntity> getContentMap4Base(String fileName, int osType) {
        return this.eleContMapInContentBase;
    }

    public HashMap<StringBuilder, elementEntity> getLocalContentMap(String fileName, int osType) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap ~~~~");
        HashMap<StringBuilder, elementEntity> result = null;
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
        WebElement tempElement = null;
        System.out.println(logSpace4thisPage + " -- Check the driver in FOXPageContent . getElementMap  : " + driver.toString());
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
                    System.out.println("Check the value in Entry in combind : " + (elementEntity) tempContentEntry.getValue() + " !!! ");
                    tempElement = combindWebElement(driver, (elementEntity) tempContentEntry.getValue(), 6, this.osTypeInContent);
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

    public HashMap<String, AndroidElement> getElementMap(AndroidDriver driver, HashMap<String, elementEntity> contMap, int osType) throws Exception {
        HashMap result = new HashMap<String, AndroidElement>();
        result.clear();
        WebElement tempElement = null;
        System.out.println(logSpace4thisPage + " x- Check the driver in FOXPageContent . getElementMap  : " + driver.toString());

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
                    tempElement = combindWebElement(driver, (elementEntity) tempContentEntry.getValue(), 6, osType);
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
            WebDriver driver = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public HashMap<String, By> getByMap(WebDriver driver, HashMap<StringBuilder, elementEntity> eleConMap) {
        HashMap result = new HashMap<String, By>();
        result.clear();
        By tempElement = null;
        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in  ANDPageContent. getByMap (AndroidDriver driver)  : " + driver.toString());

        for (Iterator it = eleConMap.entrySet().iterator(); it.hasNext(); ) {
            try {
                Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (!((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString().contains(":REX")) {
                    if (((elementEntity) tempContentEntry.getValue()).getType().toString().equalsIgnoreCase("xpath")) {
                        tempElement = By.xpath(((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString());
                    } else if (((elementEntity) tempContentEntry.getValue()).getType().toString().equalsIgnoreCase("id")) {
                        tempElement = By.id(((elementEntity) tempContentEntry.getValue()).getLocatorStr().toString());
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
