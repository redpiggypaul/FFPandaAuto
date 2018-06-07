package Component.Page;

import Component.Page.Element.ElementMapping;
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
public class ANDPageContentMap {
    private HashMap<String, ElementMapping> eleContMapInContent = new HashMap<String, ElementMapping>();  // for ready element
    private HashMap<String, ElementMapping> eleContMapInContentBase = new HashMap<String, ElementMapping>();  // for all element
    private int osTypeInContent = 0;  // 1 for android, 2 for ios
    private HashMap<String, ElementMapping> eleContMap4Route = new HashMap<String, ElementMapping>();  // for all element with route
    private HashMap<String, String> eleDefaultValueMap = new HashMap<String, String>();
    private HashMap<String, String> eleTextContentMap = new HashMap<String, String>();
    private HashMap<String, String> eleWindowMap = new HashMap<String, String>();
    private String logSpace4thisPage = " --- ANDPageContentMap ---";

    private ANDPageContentMap() throws Exception {
        System.out.println(logSpace4thisPage + " clear all !!!");
        eleContMapInContentBase.clear();
        eleContMapInContent.clear();
        eleContMap4Route.clear();
        eleDefaultValueMap.clear();
        eleTextContentMap.clear();
        eleWindowMap.clear();
    }

    public HashMap<String, ElementMapping> getEleMapBase() throws Exception {
        return eleContMapInContentBase;
    }

    public HashMap<String, ElementMapping> getEleMap() throws Exception {
        return eleContMapInContent;
    }

    public HashMap<String, ElementMapping> getEleRouteMap() throws Exception {
        return eleContMap4Route;
    }

    public HashMap<String, String> getEleDValueMap() throws Exception {
        return eleDefaultValueMap;
    }

    public HashMap<String, String> getEleTextMap() throws Exception {
        return eleTextContentMap;
    }

    public HashMap<String, String> getEleTWINMap() throws Exception {
        return eleWindowMap;
    }

    public ANDPageContentMap(String fileName, Integer osType) throws Exception {
        this.osTypeInContent = osType;
        System.out.println(logSpace4thisPage + " init !!!");
        try {
            this.getAllMap4JSON(fileName, osType);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    private ANDPageContentMap getAllMap4JSON(String fileName, int osType) {
        System.out.println(logSpace4thisPage + " ~~~~ In getContentMap4JSON ~~~~"); // get all elements in xml file
        ANDPageContentMap  temp = null;
        try {
            if (1 == osType) {
                temp =   readJSON_4_ALLWebMap(fileName, this);
            } else if (2 == osType) {
                temp =       readJSON_4_ALLWebMap(fileName, this);
            } else {
                System.out.println("Wrong osType for ANDPageContentMap . getAllMap4JSON");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return temp;
        }
    }

    private static ANDPageContentMap readJSON_4_ALLWebMap(String fileName, ANDPageContentMap emptyMapGroup) throws Exception {
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
                        String name = elementLine.get("name").getAsString();
                        String waitTime = elementLine.get("waitTime").getAsString();
                        String type = elementLine.get("type").getAsString();
                        String value = elementLine.get("value").getAsString();
                        String nextPage = elementLine.get("NextPage").getAsString();
                        String showMode = elementLine.get("showMode").getAsString();
                        String dValue = elementLine.get("defaultValue").getAsString();
                        String text = elementLine.get("textContent").getAsString();
                        String tWin = elementLine.get("triggerWindow").getAsString();
                        ElementMapping eLocator;
                        if (waitTime != null) {
                            eLocator = new ElementMapping(name, value, Integer.parseInt(waitTime), type, nextPage, showMode, dValue, text, tWin);
                        } else {
                            eLocator = new ElementMapping(name, value, type, nextPage, showMode, dValue, text, tWin);
                        }
                        emptyMapGroup.eleContMapInContentBase.put(name, eLocator);
                        emptyMapGroup.eleContMapInContent.put(name, eLocator);
                        emptyMapGroup.eleContMap4Route.put(name, eLocator);
                        emptyMapGroup.eleDefaultValueMap.put(name, value);
                        emptyMapGroup.eleTextContentMap.put(name, text);
                        if(!tWin.equals("")) {
                            emptyMapGroup.eleWindowMap.put(name, tWin);
                        }

                    } catch (Exception e) {
                        throw new XMLException("The content in Element Line " + lineN + "is incorrect : " + e.getCause());
                    }
                }
            } else {
                throw new XMLException("The NAME att is missing in the pageName 4 Android");
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
