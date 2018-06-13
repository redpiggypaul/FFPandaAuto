package Component.Page;


import Component.Page.Element.FFPandaElementEntity;
import LocalException.XMLException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by appledev131 on 4/12/16.
 */
public class FFPandaIOSPageContent {
    private HashMap<StringBuilder, FFPandaElementEntity> eleContMapInContent = new HashMap<StringBuilder, FFPandaElementEntity>();  // for ready element
    private HashMap<StringBuilder, FFPandaElementEntity> eleContMapInContentBase = new HashMap<StringBuilder, FFPandaElementEntity>();  // for all element
    private int osTypeInContent = 0;  // 1 for android, 2 for ios
    private HashMap<StringBuilder, FFPandaElementEntity> eleContMap4Route = new HashMap<StringBuilder, FFPandaElementEntity>();  // for all element with route
    private HashMap<StringBuilder, ArrayList<StringBuilder>> eleDefaultValueMap = new HashMap<StringBuilder, ArrayList<StringBuilder>>();
    protected HashMap<StringBuilder, ArrayList<StringBuilder>> eleTextContentMap = new HashMap<StringBuilder, ArrayList<StringBuilder>>();
    public HashMap<StringBuilder, StringBuilder> eleWindowMap = new HashMap<StringBuilder, StringBuilder>();
    private String logSpace4thisPage = " --- IOSPageContentMap ---";
    public boolean flag4EleWindow = false;
    protected IOSDriver theDriver = null;
    protected StringBuilder fileNameInContent = null;
    public FFPandaIOSPageContentMap insideMapSet = null;
    private boolean flag4MoveCheckdisable = true;
    private boolean moveable = false;

    private FFPandaIOSPageContent() throws Exception {
        System.out.println(logSpace4thisPage + " clear all !!!");
        eleContMapInContentBase.clear();
        eleContMapInContent.clear();
        eleContMap4Route.clear();
        eleDefaultValueMap.clear();
        eleTextContentMap.clear();
        eleWindowMap.clear();
    }

    public FFPandaIOSPageContent(StringBuilder tempName, int osType) {
        this.fileNameInContent = tempName;
        this.osTypeInContent = osType;
        System.out.println(logSpace4thisPage + " init!!!");
        try {
            insideMapSet = new FFPandaIOSPageContentMap(tempName, osType);
            this.eleContMapInContentBase = insideMapSet.getEleMapBase();
            this.eleContMapInContent = insideMapSet.getEleMap();
            this.eleContMap4Route = insideMapSet.getEleRouteMap();
            this.eleTextContentMap = insideMapSet.getEleTextMap();
            this.eleDefaultValueMap = insideMapSet.getEleDValueMap();
            this.eleWindowMap = insideMapSet.getEleTWINMap();
        } catch (Exception e) {
            e.printStackTrace();

        }
        if (eleWindowMap == null || eleWindowMap.size() == 0) {
            flag4EleWindow = false;
        } else {
            flag4EleWindow = true;
        }
    }


    public HashMap<StringBuilder, FFPandaElementEntity> getEleMapBase() throws Exception {
        return eleContMapInContentBase;
    }

    public HashMap<StringBuilder, FFPandaElementEntity> getEleMap() throws Exception {
        return eleContMapInContent;
    }

    public HashMap<StringBuilder, FFPandaElementEntity> getEleRouteMap() throws Exception {
        return eleContMap4Route;
    }

    public HashMap<StringBuilder, ArrayList<StringBuilder>> getEleDValueMap() throws Exception {
        return eleDefaultValueMap;
    }

    public HashMap<StringBuilder, ArrayList<StringBuilder>> getEleTextMap() throws Exception {
        return eleTextContentMap;
    }

    public HashMap<StringBuilder, StringBuilder> getEleTWINMap() throws Exception {
        return eleWindowMap;
    }

    public FFPandaIOSPageContent(StringBuilder fileName, Integer osType) throws Exception {
        this.osTypeInContent = osType;
        System.out.println(logSpace4thisPage + " init !!!");
        try {
            this.getAllMap4JSON(fileName, osType);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private FFPandaIOSPageContent getAllMap4JSON(StringBuilder fileName, int osType) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap4JSON ~~~~"); // get all elements in xml file
        FFPandaIOSPageContent temp = null;
        try {
            if (1 == osType) {
                temp = readJSON_4_ALLWebMap(fileName.toString(), this);
            } else if (2 == osType) {
                temp = readJSON_4_ALLWebMap(fileName.toString(), this);
            } else {
                System.out.println("Wrong osType for ANDPageContentMap . getAllMap4JSON");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return temp;
        }
    }

    public FFPandaIOSPageContent(IOSDriver driver, StringBuilder fileName, Integer osType) throws Exception {
        this.theDriver = driver;
        this.fileNameInContent = fileName;
        this.osTypeInContent = osType;
        System.out.println(logSpace4thisPage + " init!!!");
        try {
            insideMapSet = new FFPandaIOSPageContentMap(fileName, osType);
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

    private static FFPandaIOSPageContent readJSON_4_ALLWebMap(String fileName, FFPandaIOSPageContent emptyMapGroup) throws Exception {
        long gstartTime = System.currentTimeMillis();
        int lineN = 1;
        JsonParser parser = new JsonParser();
        StringBuilder theFileName = new StringBuilder(fileName);
        JsonObject object = (JsonObject) parser.parse(new FileReader(System.getProperty("user.dir") + theFileName));

        try {
            if ((theFileName.toString().toLowerCase().endsWith(object.get("pagename").getAsString().toLowerCase()))) {
                JsonArray languages2 = object.getAsJsonArray("elements");
                for (JsonElement jsonElement : languages2) {
                    lineN++;
                    try {
                        JsonObject elementLine = jsonElement.getAsJsonObject();
                        //     Element elementObj = (Element) it.next();
                        StringBuilder pageName = new StringBuilder(elementLine.get("pageName").getAsString());
                        StringBuilder eleName = new StringBuilder(elementLine.get("elementName").getAsString());
                        StringBuilder type = new StringBuilder(elementLine.get("locatorType").getAsString());
                        StringBuilder value = new StringBuilder(elementLine.get("locatorStr").getAsString());
                        StringBuilder dValue = new StringBuilder(elementLine.get("defaultValue").getAsString());
                        StringBuilder text = new StringBuilder(elementLine.get("textContent").getAsString());
                        StringBuilder tWin = new StringBuilder(elementLine.get("triggerWindow").getAsString());
                        StringBuilder sMode = new StringBuilder(elementLine.get("showMode").getAsString());
                        StringBuilder nPage = new StringBuilder(elementLine.get("nextPage").getAsString());
                        FFPandaElementEntity eLocator;
                        // (StringBuilder pName, StringBuilder eName, StringBuilder locType, StringBuilder locStr, StringBuilder dValue, StringBuilder tContent, StringBuilder tWin) t
                        eLocator = new FFPandaElementEntity(pageName, eleName, type, value, dValue, text, tWin, sMode, nPage);
                        emptyMapGroup.eleContMapInContentBase.put(eleName, eLocator);
                        emptyMapGroup.eleContMapInContent.put(eleName, eLocator);
                        emptyMapGroup.eleContMap4Route.put(eleName, eLocator);
                        emptyMapGroup.eleDefaultValueMap.put(eleName, eLocator.getDefaultValueList());
                        emptyMapGroup.eleTextContentMap.put(eleName, eLocator.getTextContentList());
                        if (!tWin.equals("")) {
                            emptyMapGroup.eleWindowMap.put(eleName, tWin);
                        }

                    } catch (Exception e) {
                        throw new XMLException("The content in Element Line " + lineN + "is incorrect : " + e.getCause());
                    }
                }
            } else {
                throw new XMLException("The NAME att is missing in the pageName 4 IOS");
            }
        } catch (XMLException e) {
            throw new XMLException("utility.XmlUtils.MyJsonReader.readJSON_4_WebElement : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("^^^^^^   readJSON_4_ALLWebMap ：" + fileName + " : " + (System.currentTimeMillis() - gstartTime) + " ms    ^^^^^^");
            return emptyMapGroup;
        }
    }

    public ArrayList<StringBuilder> getContentNameList4locType(HashMap<String, FFPandaElementEntity> xMap, StringBuilder type) {
        System.out.println(logSpace4thisPage + " ~~~~ In " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ~~~~");
        ArrayList<StringBuilder> result = null;
        HashMap<String, FFPandaElementEntity> temp = xMap;
        int account = 0;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            if (((FFPandaElementEntity) eleEntry.getValue()).getLocatorType().equals(type)) {
                account++;
            }
        }
        if (account > 0) {
            ArrayList<StringBuilder> tempS = new ArrayList<StringBuilder>(account);
            for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry eleEntry = (Map.Entry) it.next();
                for (int ind = 0; ind < account; ind++) {
                    if (((FFPandaElementEntity) eleEntry.getValue()).getLocatorType().equals(type)) {
                        tempS.set(ind, ((FFPandaElementEntity) eleEntry.getValue()).getElementName());
                    }
                }
            }
            result = tempS;
        }
        return result;
    }

    public ArrayList<StringBuilder> getContentNameList4showMode(HashMap<StringBuilder, FFPandaElementEntity> xMap, StringBuilder type) {
        System.out.println(logSpace4thisPage + " ~~~~ In " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ~~~~");
        ArrayList<StringBuilder> result = null;
        HashMap<StringBuilder, FFPandaElementEntity> temp = xMap;
        int account = 0;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            if (((FFPandaElementEntity) eleEntry.getValue()).getShowMode().equals(type)) {
                account++;
            }
        }
        if (account > 0) {
            ArrayList<StringBuilder> tempS = new ArrayList<StringBuilder>(account);
            for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry eleEntry = (Map.Entry) it.next();
                for (int ind = 0; ind < account; ind++) {
                    if (((FFPandaElementEntity) eleEntry.getValue()).getShowMode().equals(type)) {
                        tempS.set(ind, ((FFPandaElementEntity) eleEntry.getValue()).getElementName());
                    }
                }
            }
            result = tempS;
        }
        return result;
    }

    public ArrayList<StringBuilder> getContentNameList4name(HashMap<StringBuilder, FFPandaElementEntity> xMap, StringBuilder name) {
        System.out.println(logSpace4thisPage + " ~~~~ In " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ~~~~");
        ArrayList<StringBuilder> rList = new ArrayList<StringBuilder>();
        HashMap<StringBuilder, FFPandaElementEntity> temp = xMap;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            StringBuilder tName = ((FFPandaElementEntity) eleEntry.getValue()).getElementName();
            if (tName.toString().contains(name)) {
                rList.add(tName);
            }
        }
        return rList;
    }

    public ArrayList<StringBuilder> getContentNameList4type(HashMap<StringBuilder, FFPandaElementEntity> xMap, StringBuilder type) {
        System.out.println(logSpace4thisPage + " ~~~~ In " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ~~~~");
        ArrayList<StringBuilder> result = new ArrayList<StringBuilder>();
        HashMap<StringBuilder, FFPandaElementEntity> temp = xMap;
        int account = 0;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            if (((FFPandaElementEntity) eleEntry.getValue()).getShowMode().equals(type)) {
                account++;
            }
        }
        if (account > 0) {
            ArrayList<StringBuilder> tempS = new ArrayList<StringBuilder>(account);
            for (Iterator it = temp.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry eleEntry = (Map.Entry) it.next();
                for (int ind = 0; ind < account; ind++) {
                    if (((FFPandaElementEntity) eleEntry.getValue()).getShowMode().equals(type)) {
                        tempS.set(ind, ((FFPandaElementEntity) eleEntry.getValue()).getElementName());
                    }
                }
            }
            result.clear();
            result.addAll(tempS);
        }
        return result;
    }

    public HashMap<StringBuilder, FFPandaElementEntity> getContentMap4Base(StringBuilder fileName, int osType) {
        return this.eleContMapInContentBase;
    }

    public HashMap<StringBuilder, FFPandaElementEntity> getContentMap(HashMap<StringBuilder, FFPandaElementEntity> extMap) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap ~~~~");
        return extMap;
    }

    public void setFlag4MoveCheck(boolean ext) {
        System.out.println(this.getClass().getSimpleName() + " : setFlag4MoveCheckDisable ");
        flag4MoveCheckdisable = ext;
        System.out.println("Current flag4MoveCheckDisable is : " + flag4MoveCheckdisable);
    }


    public void setMoveable(boolean ext) {
        System.out.println(this.getClass().getSimpleName() + " : setMoveable ");
        moveable = ext;
        System.out.println("Current moveable is : " + moveable);
    }

    public HashMap<StringBuilder, By> getByMap(IOSDriver driver, HashMap<StringBuilder, FFPandaElementEntity> eleConMap) {
        HashMap result = new HashMap<String, By>();
        result.clear();
        By tempElement = null;
//        System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in  FFPandaIOSPageContent. getByMap (IOSDriver driver)  : " + driver.toString());

        for (Iterator it = eleConMap.entrySet().iterator(); it.hasNext(); ) {
            try {
                Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (!((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorStr().toString().contains(":REX")) {
                    if (((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorType().toString().equalsIgnoreCase("xpath")) {
                        tempElement = By.xpath(((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorStr().toString());
                    } else if (((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorType().toString().equalsIgnoreCase("classname")) {
                        tempElement = By.className(((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorStr().toString());
                    } else {
                        System.out.println(logSpace4thisPage + "currently we just support xpath & classname");
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

    // for dry run
    public HashMap<StringBuilder, By> getByMap(HashMap<StringBuilder, FFPandaElementEntity> eleConMap) {
        HashMap result = new HashMap<String, By>();
        result.clear();
        By tempElement = null;
        //   System.out.println(logSpace4thisPage + "xxxxxx ------ Check the driver in  FFPandaIOSPageContent. getByMap (IOSDriver driver)  : " + driver.toString());

        for (Iterator it = eleConMap.entrySet().iterator(); it.hasNext(); ) {
            try {
                Map.Entry tempContentEntry = (Map.Entry) it.next();
                if (!((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorStr().toString().contains(":REX")) {
                    if (((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorType().toString().equalsIgnoreCase("xpath")) {
                        tempElement = By.xpath(((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorStr().toString());
                    } else if (((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorType().toString().equalsIgnoreCase("classname")) {
                        tempElement = By.className(((FFPandaElementEntity) tempContentEntry.getValue()).getLocatorStr().toString());
                    } else {
                        System.out.println(logSpace4thisPage + "currently we just support xpath & classname");
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


    public boolean getFlag4MoveCheck() {
        System.out.println("get flag4MoveCheckDisable : " + flag4MoveCheckdisable);
        return flag4MoveCheckdisable;
    }

    public boolean getMoveable() {
        System.out.println("get moveable : " + moveable);
        return moveable;
    }

}