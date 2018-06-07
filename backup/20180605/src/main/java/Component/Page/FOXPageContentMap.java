package REXSH.REXAUTO.Component.Page;

import REXSH.REXAUTO.Component.Page.Element.elementEntity;
import REXSH.REXAUTO.LocalException.XMLException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.util.HashMap;

/**
 * Created by appledev131 on 4/12/16.
 */
public class FOXPageContentMap {
    private HashMap<StringBuilder, elementEntity> eleContMapInContent = new HashMap<StringBuilder, elementEntity>();  // for ready element
    private HashMap<StringBuilder, elementEntity> eleContMapInContentBase = new HashMap<StringBuilder, elementEntity>();  // for all element
    private int osTypeInContent = 0;  // 1 for android, 2 for ios
    private HashMap<StringBuilder, elementEntity> eleContMap4Route = new HashMap<StringBuilder, elementEntity>();  // for all element with route
    private HashMap<StringBuilder, StringBuilder> eleDefaultValueMap = new HashMap<StringBuilder, StringBuilder>();
    private HashMap<StringBuilder, StringBuilder> eleTextContentMap = new HashMap<StringBuilder, StringBuilder>();
    private HashMap<StringBuilder, StringBuilder> eleWindowMap = new HashMap<StringBuilder, StringBuilder>();
    private String logSpace4thisPage = " --- FOXPageContentMap ---";

    private FOXPageContentMap() throws Exception {
        System.out.println(logSpace4thisPage + " clear all !!!");
        eleContMapInContentBase.clear();
        eleContMapInContent.clear();
        eleContMap4Route.clear();
        eleDefaultValueMap.clear();
        eleTextContentMap.clear();
        eleWindowMap.clear();
    }

    public HashMap<StringBuilder, elementEntity> getEleMapBase() throws Exception {
        return eleContMapInContentBase;
    }

    public HashMap<StringBuilder, elementEntity> getEleMap() throws Exception {
        return eleContMapInContent;
    }

    public HashMap<StringBuilder, elementEntity> getEleRouteMap() throws Exception {
        return eleContMap4Route;
    }

    public HashMap<StringBuilder, StringBuilder> getEleDValueMap() throws Exception {
        return eleDefaultValueMap;
    }

    public HashMap<StringBuilder, StringBuilder> getEleTextMap() throws Exception {
        return eleTextContentMap;
    }

    public HashMap<StringBuilder, StringBuilder> getEleTWINMap() throws Exception {
        return eleWindowMap;
    }

    public FOXPageContentMap(String fileName, Integer osType) throws Exception {
        this.osTypeInContent = osType;
        System.out.println(logSpace4thisPage + " init !!!");
        try {
            this.getAllMap4JSON(fileName, osType);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private FOXPageContentMap getAllMap4JSON(String fileName, int osType) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap4JSON ~~~~"); // get all elements in xml file
        FOXPageContentMap temp = null;
        try {
            if (1 == osType) {
                temp = readJSON_4_ALLWebMap(fileName, this);
            } else if (2 == osType) {
                temp = readJSON_4_ALLWebMap(fileName, this);
            } else {
                System.out.println("Wrong osType for FOXPageContentMap . getAllMap4JSON");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return temp;
        }
    }

    private static FOXPageContentMap readJSON_4_ALLWebMap(String fileName, FOXPageContentMap emptyMapGroup) throws Exception {
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
                        StringBuilder name = new StringBuilder(elementLine.get("name").getAsString());
                        StringBuilder type = new StringBuilder(elementLine.get("type").getAsString());
                        StringBuilder value = new StringBuilder(elementLine.get("value").getAsString());
                        StringBuilder showMode = new StringBuilder(elementLine.get("showMode").getAsString());
                        StringBuilder dValue = new StringBuilder(elementLine.get("defaultValue").getAsString());
                        StringBuilder text = new StringBuilder(elementLine.get("textContent").getAsString());
                        StringBuilder tWin = new StringBuilder(elementLine.get("triggerWindow").getAsString());
                        StringBuilder strHM = new StringBuilder(elementLine.get("strHandleMode").getAsString());
                        elementEntity eLocator;
                        eLocator = new elementEntity(name, value, type, showMode, dValue, text, tWin, strHM);
                        //  public elementEntity(StringBuilder name, StringBuilder lStr, StringBuilder typeExt, StringBuilder modeExt, StringBuilder dValue, StringBuilder tContent, StringBuilder tWin, StringBuilder strHMode) {

                        emptyMapGroup.eleContMapInContentBase.put(name, eLocator);
                        emptyMapGroup.eleContMapInContent.put(name, eLocator);
                        emptyMapGroup.eleContMap4Route.put(name, eLocator);
                        emptyMapGroup.eleDefaultValueMap.put(name, value);
                        emptyMapGroup.eleTextContentMap.put(name, text);
                        if (!tWin.equals("")) {
                            emptyMapGroup.eleWindowMap.put(name, tWin);
                        }

                    } catch (Exception e) {
                        throw new XMLException("The content in Element Line " + lineN + "is incorrect : " + e.getCause());
                    }
                }
            } else {
                throw new XMLException("The NAME att is missing in the pageName 4 FOX");
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


}
