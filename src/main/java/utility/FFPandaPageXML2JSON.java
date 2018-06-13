package utility;

import Component.Page.Element.ElementMapping;
import Component.Page.Element.FFPandaElementEntity;
import LocalException.XMLException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by appledev131 on 8/29/16.
 */
public class FFPandaPageXML2JSON {
    static int countFiles = 0;// 声明统计文件个数的变量
    static int countFolders = 0;// 声明统计文件夹的变量

    public static void main(String[] args) throws Exception {

        //  File folder = new File(new String(System.getProperty("user.dir") + "/PageXML/and/"));// 默认目录
        File folder = new File(new String(System.getProperty("user.dir") + "/PageXML/"));// 默认目录
        String keyword = ".xml";
        if (!folder.exists()) {// 如果文件夹不存在
            System.out.println("目录不存在：" + folder.getAbsolutePath());
            return;
        }
        long gstartTime = System.currentTimeMillis();
        File[] result = TextSearchFile.searchFile(folder, keyword);// 调用方法获得文件数组
        System.out.println("^^^^^^    get all files：" + (System.currentTimeMillis() - gstartTime) + " ms    ^^^^^^");
        System.out.println("在 " + folder + " 以及所有子文件时查找对象" + keyword);
        System.out.println("查找了" + countFiles + " 个文件，" + countFolders + " 个文件夹，共找到 " + result.length + " 个符合条件的文件：");
        LinkedHashMap<String, Path> file2path = new LinkedHashMap<String, Path>();
        for (int i = 0; i < result.length; i++) {// 循环显示文件
            File file = result[i];
            file2path.put(file.getName(), Paths.get(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("/") + 1)));
        }

        for (Iterator it = file2path.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry eleEntry = (Map.Entry) it.next();
            StringBuilder fileName = new StringBuilder(eleEntry.getKey().toString());
            StringBuilder contentFilePath = new StringBuilder(eleEntry.getValue().toString());
            StringBuilder fileNameXML = new StringBuilder(contentFilePath + "/" + fileName);
            System.out.println("~~~~~~ " + fileNameXML + " ~~~~~~~");
            TreeMap<String, FFPandaElementEntity> elementsMap = new TreeMap<String, FFPandaElementEntity>();
            elementsMap.clear();
            elementsMap = getElementMapFFPanda(new StringBuilder(System.getProperty("user.dir") + "/PageXML/"+fileNameXML));
            FFPandaJsonReader.createJSON_PageElement(elementsMap, fileName, contentFilePath);
        }
    }


    public static TreeMap<String, FFPandaElementEntity> getElementMapFFPanda(StringBuilder fileNameXML) throws Exception {
        TreeMap<String, FFPandaElementEntity> result = new TreeMap<String, FFPandaElementEntity>();
        HashMap<String, FFPandaElementEntity> tempMap = new HashMap<String, FFPandaElementEntity>();
        result.clear();
        int lineN = 1;
        try {
            File file = new File(fileNameXML.toString());
            if (!file.exists()) {
                throw new IOException("Can't find elements mapping file: " + fileNameXML.toString());
            }
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            Element root = document.getRootElement();

            if (file.toString().toLowerCase().contains(root.attributeValue("name").toLowerCase())) {
                if (root.getName().equalsIgnoreCase("page")) {
                    for (Iterator<?> iter = root.elementIterator(); iter.hasNext(); ) {
                        lineN++;
                        try {
                            Element elementObj = (Element) iter.next();
                            StringBuilder elementName = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder locatorType = new StringBuilder(elementObj.attributeValue("locatorType"));
                            StringBuilder locatorValue = new StringBuilder(elementObj.attributeValue("locatorStr"));
                            StringBuilder dValue = new StringBuilder(elementObj.attributeValue("defaultValue")); // for textfiled default e.g value content
                            StringBuilder text = new StringBuilder(elementObj.attributeValue("textContent"));    // for text content of btn/line/title
                            StringBuilder tWindowName = new StringBuilder(elementObj.attributeValue("triggerWindow"));
                            StringBuilder sMode = new StringBuilder(elementObj.attributeValue("showMode"));
                            StringBuilder nPage = new StringBuilder(elementObj.attributeValue("nextPage"));
                            FFPandaElementEntity eLocator;
                            eLocator = new FFPandaElementEntity(new StringBuilder(root.attributeValue("name").toLowerCase()), elementName, locatorType, locatorValue, dValue, text, tWindowName,sMode, nPage);
                            tempMap.put(elementName.toString(), eLocator);
                        } catch (Exception e) {
                            throw new XMLException("The content in Element Line " + lineN + "is incorrect : " + e.getCause());
                        }
                    }
                } else {
                    throw new XMLException("The Root Line is not PAGE!!!");
                }
            } else {
                throw new XMLException("The NAME att is missing in the pageName for FF Panda");
            }
        } catch (XMLException e) {
            throw new XMLException(FFPandaPageXML2JSON.class.getName() + Thread.currentThread().getStackTrace()[1].getMethodName() + " : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.putAll(tempMap);
        return result;
    }


}
