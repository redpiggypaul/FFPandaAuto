package utility;

import Component.Page.Element.ElementMapping;
import Component.Page.Element.FFPandaElementEntity;
import Component.Page.Element.elementEntity;
import LocalException.XMLException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by appledev131 on 8/29/16.
 */
public class MyJsonReader {

    public static void main(String[] args) {
        try {
            long gstartTime = System.currentTimeMillis();
          //  TreeMap<String, ElementMapping> elementsMap = readJSON_4_WebElement();
            //System.out.println(System.currentTimeMillis() - gstartTime + " ms");
            //System.out.println(elementsMap.size());
            createJSON_WebElement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void readJSON() throws Exception {

        // 创建json解析器
        JsonParser parser = new JsonParser();
        // 使用解析器解析json数据，返回值是JsonElement，强制转化为其子类JsonObject类型
        JsonObject object = (JsonObject) parser.parse(new FileReader(System.getProperty("user.dir") + "/src/main/java/utility/test.json"));

        // 使用JsonObject的get(String memeberName)方法返回JsonElement，再使用JsonElement的getAsXXX方法得到真实类型
        //System.out.println("cat = " + object.get("cat").getAsString());

        // 遍历JSON数组
        JsonArray languages = object.getAsJsonArray("languages");
        for (JsonElement jsonElement : languages) {
            JsonObject language = jsonElement.getAsJsonObject();
            //System.out.println("id = " + language.get("id").getAsInt() + ",ide = " + language.get("ide").getAsString() + ",name = " + language.get("name").getAsString());
        }

        //System.out.println("pop = " + object.get("pop").getAsString());
    }

/*
    public static TreeMap<String, ElementMapping> readJSON_4_WebElement() throws Exception {
        TreeMap<String, ElementMapping> elementsMap = new TreeMap<String, ElementMapping>();
        elementsMap.clear();
        int lineN = 1;
        // 创建json解析器
        JsonParser parser = new JsonParser();
        // 使用解析器解析json数据，返回值是JsonElement，强制转化为其子类JsonObject类型
        StringBuilder theFileName = new StringBuilder("testpage.json");
        // System.getProperty("user.dir")
        // JsonObject object = (JsonObject) parser.parse(new FileReader(System.getProperty("user.dir") + theFileName));
        JsonObject object = (JsonObject) parser.parse(new FileReader(System.getProperty("user.dir") + "/src/main/java/utility/" + theFileName));

        try {
            if (object.get("pagename").getAsString().equalsIgnoreCase(theFileName.toString())) {
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
                        ElementMapping eLocator;
                        if (waitTime != null) {
                            eLocator = new ElementMapping(name, value, Integer.parseInt(waitTime), type, nextPage, showMode, dValue, text);
                        } else {
                            eLocator = new ElementMapping(name, value, type, nextPage, showMode, dValue, text);
                        }
                        elementsMap.put(name, eLocator);
                    } catch (Exception e) {
                        throw new XMLException("The content in Element Line " + lineN + "is incorrect : " + e.getCause());
                    }
                }
            } else {
                throw new XMLException("The NAME att is missing in the pageName 4 Android");
            }
        } catch (XMLException e) {
            throw new XMLException("utility.XmlUtils.readANDXMLDocument4Base : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return elementsMap;
        }
    }
*/

    public static HashMap<String, ElementMapping> readJSON_4_WebElement(String fileName) throws Exception {
        // long startTime = System.currentTimeMillis();
        HashMap<String, ElementMapping> elementsMap = new HashMap<String, ElementMapping>();
        elementsMap.clear();
        int lineN = 1;
        // 创建json解析器
        JsonParser parser = new JsonParser();
        // 使用解析器解析json数据，返回值是JsonElement，强制转化为其子类JsonObject类型
        //System.out.println("~~~~~~~~~~~~ the JSON file is : " + fileName );
        //System.out.println("~~~~~~~~~~~~ the JSON file abs path is : " + System.getProperty("user.dir") + fileName );
        StringBuilder theFileName = new StringBuilder(fileName);
        // System.getProperty("user.dir")
        // JsonObject object = (JsonObject) parser.parse(new FileReader(System.getProperty("user.dir") + theFileName));
        JsonObject object = (JsonObject) parser.parse(new FileReader(System.getProperty("user.dir") + theFileName));

        try {
            // //System.out.println("~~~~~~~~~~~~ the JSON file pagename att is  : " + object.get("pagename").getAsString() );
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
                        elementsMap.put(name, eLocator);
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
            //System.out.println("^^^^^^   readJSON_4_WebElement ：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
            return elementsMap;
        }
    }


    public static HashMap<String, elementEntity> readJSON_4_WebElement_NEW(String fileName) throws Exception {
        // long startTime = System.currentTimeMillis();
        HashMap<String, elementEntity> elementsMap = new HashMap<String, elementEntity>();
        elementsMap.clear();
        int lineN = 1;
        // 创建json解析器
        JsonParser parser = new JsonParser();
        // 使用解析器解析json数据，返回值是JsonElement，强制转化为其子类JsonObject类型
        //System.out.println("~~~~~~~~~~~~ the JSON file is : " + fileName );
        //System.out.println("~~~~~~~~~~~~ the JSON file abs path is : " + System.getProperty("user.dir") + fileName );
        StringBuilder theFileName = new StringBuilder(fileName);
        // System.getProperty("user.dir")
        // JsonObject object = (JsonObject) parser.parse(new FileReader(System.getProperty("user.dir") + theFileName));
        JsonObject object = (JsonObject) parser.parse(new FileReader(System.getProperty("user.dir") + theFileName));

        try {
            // //System.out.println("~~~~~~~~~~~~ the JSON file pagename att is  : " + object.get("pagename").getAsString() );
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
                        StringBuilder nextPage = new StringBuilder(elementLine.get("NextPage").getAsString());
                        StringBuilder showMode = new StringBuilder(elementLine.get("showMode").getAsString());
                        StringBuilder dValue = new StringBuilder(elementLine.get("defaultValue").getAsString());
                        StringBuilder text = new StringBuilder(elementLine.get("textContent").getAsString());
                        StringBuilder tWin = new StringBuilder(elementLine.get("triggerWindow").getAsString());
                        StringBuilder strHM = new StringBuilder(elementLine.get("strHandleMode").getAsString());
                        elementEntity eLocator;
                        eLocator = new elementEntity(name, value, type, showMode, dValue, text, tWin, strHM);
                        elementsMap.put(name.toString(), eLocator);
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
            //System.out.println("^^^^^^   readJSON_4_WebElement ：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
            return elementsMap;
        }
    }


    public static HashMap<String, String> readJSON_4_dValue(String fileName) throws Exception {
        // long startTime = System.currentTimeMillis();
        HashMap<String, String> elementsMap = new HashMap<String, String>();
        elementsMap.clear();
        int lineN = 1;
        // 创建json解析器
        JsonParser parser = new JsonParser();
        // 使用解析器解析json数据，返回值是JsonElement，强制转化为其子类JsonObject类型
        //System.out.println("~~~~~~~~~~~~ the JSON file is : " + fileName );
        //System.out.println("~~~~~~~~~~~~ the JSON file abs path is : " + System.getProperty("user.dir") + fileName );
        StringBuilder theFileName = new StringBuilder(fileName);
        // System.getProperty("user.dir")
        // JsonObject object = (JsonObject) parser.parse(new FileReader(System.getProperty("user.dir") + theFileName));
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
                        String dValue = elementLine.get("defaultValue").getAsString();
                        elementsMap.put(name, dValue);
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
            //System.out.println("^^^^^^   readJSON_4_dValue ：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
            return elementsMap;
        }
    }


    public static HashMap<String, StringBuilder> readJSON_4_dValue_NEW(String fileName) throws Exception {
        // long startTime = System.currentTimeMillis();
        HashMap<String, StringBuilder> elementsMap = new HashMap<String, StringBuilder>();
        elementsMap.clear();
        int lineN = 1;
        // 创建json解析器
        JsonParser parser = new JsonParser();
        // 使用解析器解析json数据，返回值是JsonElement，强制转化为其子类JsonObject类型
        //System.out.println("~~~~~~~~~~~~ the JSON file is : " + fileName );
        //System.out.println("~~~~~~~~~~~~ the JSON file abs path is : " + System.getProperty("user.dir") + fileName );
        StringBuilder theFileName = new StringBuilder(fileName);
        // System.getProperty("user.dir")
        // JsonObject object = (JsonObject) parser.parse(new FileReader(System.getProperty("user.dir") + theFileName));
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
                        StringBuilder dValue = new StringBuilder(elementLine.get("defaultValue").getAsString());
                        elementsMap.put(name.toString(), dValue);
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
            //System.out.println("^^^^^^   readJSON_4_dValue ：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
            return elementsMap;
        }
    }


    public static HashMap<String, String> readJSON_4_textContent(String fileName) throws Exception {
        // long startTime = System.currentTimeMillis();
        HashMap<String, String> elementsMap = new HashMap<String, String>();
        elementsMap.clear();
        int lineN = 1;
        // 创建json解析器
        JsonParser parser = new JsonParser();
        // 使用解析器解析json数据，返回值是JsonElement，强制转化为其子类JsonObject类型
        //System.out.println("~~~~~~~~~~~~ the JSON file is : " + fileName );
        //System.out.println("~~~~~~~~~~~~ the JSON file abs path is : " + System.getProperty("user.dir") + fileName );
        StringBuilder theFileName = new StringBuilder(fileName);
        // System.getProperty("user.dir")
        // JsonObject object = (JsonObject) parser.parse(new FileReader(System.getProperty("user.dir") + theFileName));
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
                        String dValue = elementLine.get("defaultValue").getAsString();
                        elementsMap.put(name, dValue);
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
            //System.out.println("^^^^^^   readJSON_4_textContent ：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
            return elementsMap;
        }
    }


    public static HashMap<String, String> readJSON_4_tWin(String fileName) throws Exception {
        // long startTime = System.currentTimeMillis();
        HashMap<String, String> elementsMap = new HashMap<String, String>();
        elementsMap.clear();
        int lineN = 1;
        // 创建json解析器
        JsonParser parser = new JsonParser();
        // 使用解析器解析json数据，返回值是JsonElement，强制转化为其子类JsonObject类型
        //System.out.println("~~~~~~~~~~~~ the JSON file is : " + fileName );
        //System.out.println("~~~~~~~~~~~~ the JSON file abs path is : " + System.getProperty("user.dir") + fileName );
        StringBuilder theFileName = new StringBuilder(fileName);
        // System.getProperty("user.dir")
        // JsonObject object = (JsonObject) parser.parse(new FileReader(System.getProperty("user.dir") + theFileName));
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
                        String dValue = elementLine.get("triggerWindow").getAsString();
                        if (!dValue.equals("")) {
                            elementsMap.put(name, dValue);
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
            //System.out.println("^^^^^^   readJSON_4_tWin ：" + (System.currentTimeMillis() - startTime) + " ms    ^^^^^^");
            return elementsMap;
        }
    }

/*
    public static HashMap<String, ElementMapping> readANDXMLDocument4Base
            (String fileNameXML) throws Exception {
        HashMap<String, ElementMapping> elementsMap = new HashMap<String, ElementMapping>();
        elementsMap.clear();
        int lineN = 1;
        File file = new File(System.getProperty("user.dir") + fileNameXML);
        if (!file.exists()) {
            throw new IOException("Can't find elements mapping file: " + fileNameXML);
        }
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element root = document.getRootElement();
        try {
            if (file.toString().toLowerCase().contains(root.attributeValue("name").toLowerCase())) {
                if (root.getName().equalsIgnoreCase("page")) {
                    for (Iterator<?> it = root.elementIterator(); it.hasNext(); ) {
                        lineN++;
                        try {
                            Element elementObj = (Element) it.next();
                            String name = elementObj.attributeValue("name");
                            String waitTime = elementObj.attributeValue("waitTime");
                            String type = elementObj.attributeValue("type");
                            String value = elementObj.attributeValue("value");
                            String nextPage = elementObj.attributeValue("NextPage");
                            String showMode = elementObj.attributeValue("showMode");
                            String dValue = elementObj.attributeValue("defaultValue"); // for textfiled default e.g value content
                            String text = elementObj.attributeValue("textContent");    // for text content of btn/line/title
                            String parEleName = elementObj.attributeValue("parentElementName");
                            ElementMapping eLocator;
                            if (waitTime != null) {
                                eLocator = new ElementMapping(name, value, Integer.parseInt(waitTime), type, nextPage, showMode, dValue, text);
                            } else {
                                eLocator = new ElementMapping(name, value, type, nextPage, showMode, dValue, text);
                            }
                            elementsMap.put(name, eLocator);
                        } catch (Exception e) {
                            throw new XMLException("The content in Element Line " + lineN + "is incorrect : " + e.getCause());
                        }
                    }
                } else {
                    throw new XMLException("The Root Line is not PAGE!!!");
                }
            } else {
                throw new XMLException("The NAME att is missing in the pageName 4 Android");
            }
        } catch (XMLException e) {
            throw new XMLException("utility.XmlUtils.readANDXMLDocument4Base : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }
*/

    public static void createJSON() throws IOException {

        JsonObject object = new JsonObject();  // 创建一个json对象
        object.addProperty("cat", "it");       // 为json对象添加属性

        JsonArray languages = new JsonArray(); // 创建json数组
        JsonObject language = new JsonObject();
        language.addProperty("id", 1);
        language.addProperty("ide", "Eclipse");
        language.addProperty("name", "java");
        languages.add(language);               // 将json对象添加到数组

        language = new JsonObject();
        language.addProperty("id", 2);
        language.addProperty("ide", "XCode");
        language.addProperty("name", "Swift");
        languages.add(language);

        language = new JsonObject();
        language.addProperty("id", 3);
        language.addProperty("ide", "Visual Studio");
        language.addProperty("name", "C#");
        languages.add(language);

        object.add("languages", languages);   // 将数组添加到json对象
        object.addProperty("pop", true);

        String jsonStr = object.toString();   // 将json对象转化成json字符串
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("data.json")));
        pw.print(jsonStr);
        pw.flush();
        pw.close();
    }

    public static void createJSON_WebElement() throws IOException {

        JsonObject object = new JsonObject();  // 创建一个json对象
        object.addProperty("pagename", "it");       // 为json对象添加属性

        JsonArray languages = new JsonArray(); // 创建json数组
        JsonObject language = new JsonObject();
        language.addProperty("name", "那么1");
        language.addProperty("waitTime", "2");
        language.addProperty("type", "ttt");
        language.addProperty("value", "ttt");
        language.addProperty("NextPage", "ttt");
        language.addProperty("showMode", "ttt");
        language.addProperty("defaultValue", "ttt");
        language.addProperty("textContent", "ttt");
        language.addProperty("parentElementName", "ttt");
        languages.add(language);

        object.add("languages", languages);   // 将数组添加到json对象
        object.addProperty("pop", true);

        String jsonStr = object.toString();   // 将json对象转化成json字符串
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/src/main/java/utility/" + "data.json")));
        pw.print(jsonStr);
        pw.flush();
        pw.close();
    }


    public static void createJSON_WebElement(TreeMap<String, ElementMapping> extEleMap, String fileName, String pathName) throws IOException {
        String line = System.getProperty("line.separator");
        if (fileName.endsWith(".json")) {
        } else if (fileName.contains(".")) {
            fileName = new String(fileName.substring(0, fileName.lastIndexOf(".")) + ".json");
        } else {
            fileName = new String(fileName + ".json");
        }
        JsonObject object = new JsonObject();  // 创建一个json对象
        object.addProperty("pagename", fileName);       // 为json对象添加属性

        JsonArray languages = new JsonArray(); // 创建json数组

        // for (Iterator it = extEleMap.entrySet().iterator(); it.hasNext(); ) {
        for (Iterator it = extEleMap.keySet().iterator(); it.hasNext(); ) {

            JsonObject language = new JsonObject();
            // Map.Entry eleEntry = (Map.Entry) it.next();
            //  ElementMapping theEle = (ElementMapping) eleEntry.getValue();
            ElementMapping theEle = extEleMap.get(it.next());
            language.addProperty("name", theEle.getElementName());
            language.addProperty("waitTime", theEle.getWaitTime());
            language.addProperty("type", theEle.getType());
            language.addProperty("value", theEle.getLocatorStr());
            language.addProperty("NextPage", theEle.getNextPage());
            language.addProperty("showMode", theEle.getMode());
            language.addProperty("defaultValue", theEle.getDefaultValue());
            language.addProperty("textContent", theEle.getTextContent());
            language.addProperty("parentElementName", theEle.getParent());
            language.addProperty("triggerWindow", theEle.getTriggerWin());
            languages.add(language);
            object.add("elements", languages);   // 将数组添加到json对象
        }

        String jsonStr = object.toString();   // 将json对象转化成json字符串
        // jsonStr.replaceAll("}","}"+line);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(pathName + "/" + fileName)));
        pw.print(jsonStr);
        pw.flush();
        pw.close();
    }

    public static void createJSON_PageElement(TreeMap<String, FFPandaElementEntity> extEleMap, StringBuilder fileName, StringBuilder pathName) throws IOException {
        if (fileName.toString().endsWith(".json")) {
        } else if (fileName.toString().contains(".")) {
            fileName = new StringBuilder(fileName.substring(0, fileName.lastIndexOf(".")) + ".json");
        } else {
            fileName = new StringBuilder(fileName + ".json");
        }
        JsonObject object = new JsonObject();  // 创建一个json对象
        object.addProperty("pagename", fileName.toString());       // 为json对象添加属性

        JsonArray languages = new JsonArray(); // 创建json数组

        // for (Iterator it = extEleMap.entrySet().iterator(); it.hasNext(); ) {
        for (Iterator it = extEleMap.keySet().iterator(); it.hasNext(); ) {

            JsonObject language = new JsonObject();
            // Map.Entry eleEntry = (Map.Entry) it.next();
            //  ElementMapping theEle = (ElementMapping) eleEntry.getValue();
            FFPandaElementEntity theEle = extEleMap.get(it.next());
            language.addProperty("pageName",theEle.getPageName().toString());
            language.addProperty("elementName", theEle.getElementName().toString());
            language.addProperty("locatorType", theEle.getLocatorType().toString());
            language.addProperty("locatorStr", theEle.getLocatorStr().toString());
            language.addProperty("defaultValue", theEle.getDefaultValue().toString());
            language.addProperty("textContent", theEle.getTextContent().toString());
            language.addProperty("triggerWindow", theEle.getTriggerWin().toString());
            languages.add(language);
            object.add("elements", languages);   // 将数组添加到json对象
        }

        String jsonStr = object.toString();   // 将json对象转化成json字符串

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(pathName + "/" + fileName)));
        pw.print(jsonStr);
        pw.flush();
        pw.close();
    }
}
