/**
 *
 */
package utility;

import Component.Page.Element.FFPandaElementEntity;
import Component.Page.Element.operationItem;
import LocalException.XMLException;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.*;

import static utility.OSMatcher.matchOS;

/**
 * @author lyou009  , Modified by pzhu023
 */
public class FFPandaXmlUtils {
    private static Logger theLogger;
    private static String logSpace4thisPage = "             ";

    public FFPandaXmlUtils(Logger l) {
        theLogger = l;
    }

    public static HashMap<StringBuilder, FFPandaElementEntity> readXMLDocumentAll
            (StringBuilder fileNameXML, Object osType) throws Exception {
        HashMap<StringBuilder, FFPandaElementEntity> elementsMap = new HashMap<StringBuilder, FFPandaElementEntity>();
        elementsMap.clear();
        try {
            //System.out.println("in XmlUtils readXMLDoucmentAll(StringBuilder fileNameXML, Object osType ) *** ");
            //System.out.println("Chcek the file " + fileNameXML);
            File file = new File(System.getProperty("user.dir") + fileNameXML);
            if (!file.exists()) {
                throw new IOException("Can't find elements mapping file: " + fileNameXML);
            }
            if (1 == matchOS(osType)) {
                elementsMap = readANDXMLDocumentN(fileNameXML);
            } else if (2 == matchOS(osType)) {
                elementsMap = readIOSXMLDocumentN(fileNameXML);
            } else {
                throw new XMLException("Wrong OS id for : " + osType + "NO match XML Decoder Method !!!");
            }
        } catch (XMLException e) {
            throw new XMLException("utility.XmlUtils.readXMLDocumentAll : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }

    public static HashMap<String, operationItem> readActionXMLDocument
            (StringBuilder fileNameXML) throws Exception {
        return readActionXMLDocumentAll(fileNameXML, 0);
    }

    public static HashMap<String, operationItem> readActionXMLDocumentAll
            (StringBuilder fileNameXML, Object osType) throws Exception {
        HashMap<String, operationItem> opeMap = new HashMap<String, operationItem>();
        opeMap.clear();
        int lineN = 1;
        try {
            File file = new File(System.getProperty("user.dir") + fileNameXML);
            if (!file.exists()) {
                throw new IOException("Can't find elements mapping file: " + fileNameXML);
            }
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            Element root = document.getRootElement();
            //System.out.println("Check the file name in XmlUtils.readxxxAll : " + file);
            //System.out.println(logSpace4thisPage + "in try");
            //System.out.println(logSpace4thisPage + "root " + root.attributeValue("name"));
            //System.out.println(logSpace4thisPage + "att name " + root.attributeValue("name"));
            if (file.toString().toLowerCase().contains(root.attributeValue("name").toLowerCase())) {
                if (root.getName().equalsIgnoreCase("page")) {
                    for (Iterator<?> it = root.elementIterator(); it.hasNext(); ) {
                        //System.out.println(logSpace4thisPage + "Line NUM : " + lineN);
                        lineN++;
                        Element elementObj = (Element) it.next();
                        String oName = elementObj.attributeValue("OperationName");
                        String pName = elementObj.attributeValue("PageName");
                        String oType = elementObj.attributeValue("OperationType");
                        String eleName = elementObj.attributeValue("elementName");
                        String eleType = elementObj.attributeValue("elementType");
                        String elePara = elementObj.attributeValue("elementParameter");
                        Integer s = Integer.parseInt(elementObj.attributeValue("Step"));

                        //System.out.println(oName);
                        //System.out.println(s.toString());
                        operationItem eLocator;
                        eLocator = new operationItem(oName, pName, oType, eleName, eleType, elePara, s);
                        opeMap.put(new String(oName + ":" + s), eLocator);
                    }
                } else {
                    throw new XMLException("The Root Line is not PAGE!!!");
                }
            }
        } catch (XMLException e) {
            throw new XMLException("utility.XmlUtils.readActionXMLDocumentAll : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(opeMap.size());
        return opeMap;
    }

    public static void readActionXMLDoc4Set
            (StringBuilder fileNameXML, Object osType) throws Exception {
        int lineN = 1;
        File file = new File(System.getProperty("user.dir") + fileNameXML);
        if (!file.exists()) {
            throw new IOException("Can't find elements mapping file: " + fileNameXML);
        }
        int rowNum = getRows(file);
        //System.out.println("LLLLLL Line  : " + rowNum);
        SortedSet<operationItem> parts = new TreeSet<operationItem>();
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element root = document.getRootElement();
        //System.out.println(logSpace4thisPage + "in try");
        //System.out.println(logSpace4thisPage + "file " + file.toString());
        //System.out.println(logSpace4thisPage + "att name " + root.attributeValue("name"));
        if (file.toString().toLowerCase().contains(root.attributeValue("name").toLowerCase())) {
            //System.out.println("Pass the name check");
            if (root.getName().equalsIgnoreCase("page")) {
                //System.out.println("Pass the 1st line check");
                for (Iterator<?> it = root.elementIterator(); it.hasNext(); ) {
                    //System.out.println(logSpace4thisPage + "Line NUM : " + lineN);
                    lineN++;
                    Element elementObj = (Element) it.next();
                    String oName = elementObj.attributeValue("OperationName");
                    String pName = elementObj.attributeValue("PageName");
                    String oType = elementObj.attributeValue("OperationType");
                    String eleName = elementObj.attributeValue("elementName");
                    String eleType = elementObj.attributeValue("elementType");
                    String elePara = elementObj.attributeValue("elementParameter");
                    Integer s = Integer.parseInt(elementObj.attributeValue("Step"));
                    //System.out.println(oName);
                    //System.out.println(s.toString());
                    operationItem eLocator = null;
                    // parts.add(new operationItem("oName3","pName3", "oType3", "eleName", "eleType","elePara3", 3));
                    //System.out.println("Check the eL : " + eLocator.toString() + "::" + eLocator.getElementPara());
                    parts.add(eLocator);
                }
                //System.out.println("size : " + parts.size());
                for (Iterator it = parts.iterator(); it.hasNext(); ) {
                    //System.out.println("check para" + ((operationItem) it.next()).getElementPara());
                }
            } else {
                throw new XMLException("The Root Line is not PAGE!!!");
            }
            //System.out.println("size " + parts.size());
            //System.out.println("first " + parts.first());
            //System.out.println("last " + parts.last());
        }
    }


    public static HashMap<StringBuilder, FFPandaElementEntity> readXMLDocumentNeg
            (StringBuilder fileNameXML, Object osType) throws Exception {
        HashMap<StringBuilder, FFPandaElementEntity> elementsMap = new HashMap<StringBuilder, FFPandaElementEntity>();
        HashMap<StringBuilder, FFPandaElementEntity> tMap = new HashMap<StringBuilder, FFPandaElementEntity>();
        elementsMap.clear();
        tMap.clear();
        try {
            File file = new File(System.getProperty("user.dir") + fileNameXML);
            if (!file.exists()) {
                throw new IOException("Can't find elements mapping file: " + fileNameXML);
            }
            tMap = readXMLDocumentAll(fileNameXML, osType);
            for (Iterator it = tMap.entrySet().iterator(); it.hasNext(); ) {
                //System.out.println(logSpace4thisPage + "Start loop the Map in readXMLDocumentNeg");
                Map.Entry eleEntry = (Map.Entry) it.next();
                FFPandaElementEntity newTobj = (FFPandaElementEntity) eleEntry.getValue();
                String modeValue = newTobj.getShowMode().toString();
                //System.out.println(modeValue);
                if (!(modeValue.equalsIgnoreCase("ready") || modeValue.toLowerCase().contains("ready"))) {
                    //System.out.println(logSpace4thisPage + "put!!!");
                    elementsMap.put((StringBuilder) eleEntry.getKey(), (FFPandaElementEntity) eleEntry.getValue());
                }
            }
        } catch (XMLException e) {
            throw new XMLException("utility.XmlUtils.readXMLDocumentNeg : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }


    public static int getRows(File file) {
        LineNumberReader lnr = null;
        int num = 0;
        try {
            lnr = new LineNumberReader(new FileReader(file));
            lnr.skip(Long.MAX_VALUE);
            num = lnr.getLineNumber();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                lnr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ++num;
    }


    public static HashMap<StringBuilder, FFPandaElementEntity> readXMLDocument
            (StringBuilder fileNameXML, Object osType) throws Exception {
        HashMap<StringBuilder, FFPandaElementEntity> elementsMap = new HashMap<StringBuilder, FFPandaElementEntity>();
        HashMap<StringBuilder, FFPandaElementEntity> tMap = new HashMap<StringBuilder, FFPandaElementEntity>();
        elementsMap.clear();
        tMap.clear();
        //System.out.println(logSpace4thisPage + "S XmlU java");
        try {
            File file = new File(System.getProperty("user.dir") + fileNameXML);
            if (!file.exists()) {
                throw new IOException("Can't find elements mapping file: " + fileNameXML);
            }
            tMap = readXMLDocumentAll(fileNameXML, osType);
            for (Iterator it = tMap.entrySet().iterator(); it.hasNext(); ) {
                //System.out.println(logSpace4thisPage + "Start loop the Map in readXMLDocument");
                Map.Entry eleEntry = (Map.Entry) it.next();
                FFPandaElementEntity newTobj = (FFPandaElementEntity) eleEntry.getValue();
                StringBuilder modeValue = newTobj.getShowMode();
                //System.out.println(logSpace4thisPage + modeValue);
                if ((modeValue.toString().equalsIgnoreCase("ready") || modeValue.toString().toLowerCase().contains("ready"))) {
                    //System.out.println("put!!!");
                    elementsMap.put((StringBuilder) eleEntry.getKey(), (FFPandaElementEntity) eleEntry.getValue());
                }
            }
        } catch (XMLException e) {
            throw new XMLException("utility.XmlUtils.readXMLDocument : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }


    public static HashMap<StringBuilder, operationItem> readXMLDocument4Ope
            (StringBuilder fileNameXML, Object osType) throws Exception {
        HashMap<StringBuilder, operationItem> elementsMap = new HashMap<StringBuilder, operationItem>();
        HashMap<String, operationItem> tMap = new HashMap<String, operationItem>();
        elementsMap.clear();
        tMap.clear();
        try {
            File file = new File(System.getProperty("user.dir") + fileNameXML);
            if (!file.exists()) {
                throw new IOException("Can't find elements mapping file: " + fileNameXML);
            }
            tMap = readActionXMLDocumentAll(fileNameXML, osType);
            for (Iterator it = tMap.entrySet().iterator(); it.hasNext(); ) {
                //System.out.println(logSpace4thisPage + "Start loop the Map in readXMLDocumentNeg");
                Map.Entry eleEntry = (Map.Entry) it.next();
                FFPandaElementEntity newTobj = (FFPandaElementEntity) eleEntry.getValue();
                String modeValue = newTobj.getShowMode().toString();
                //System.out.println(logSpace4thisPage + modeValue);
                if ((modeValue.equalsIgnoreCase("ready") || modeValue.toLowerCase().contains("ready"))) {
                    //System.out.println(logSpace4thisPage + "put!!!");
                    elementsMap.put((StringBuilder) eleEntry.getKey(), (operationItem) eleEntry.getValue());
                }
            }
        } catch (XMLException e) {
            throw new XMLException("utility.XmlUtils.readXMLDocument4Ope : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }


    public static HashMap<StringBuilder, StringBuilder> findRouteList(StringBuilder fileNameXML, Object osType) throws Exception {
        //    HashMap<StringBuilder, String> elementsMap = new HashMap<StringBuilder, String>();
        HashMap<StringBuilder, StringBuilder> tMap = new HashMap<StringBuilder, StringBuilder>();
        tMap.clear();
        //   tMap.clear();

        try {
            File file = new File(fileNameXML.toString());
            if (!file.exists()) {
                throw new IOException("Can't find elements mapping file: " + fileNameXML);
            }
            if (1 == matchOS(osType)) {
                tMap = findANDRouteList(fileNameXML);
            } else if (2 == matchOS(osType)) {
                tMap = findIOSRouteList(fileNameXML);
            } else {
                throw new XMLException("Wrong OS type for findRouteList : ");
            }
        } catch (XMLException e) {
            throw new XMLException("utility.XmlUtils.findRouteList : " + e.getMessage());
        }
        return tMap;
    }


    /*

    public static HashMap<StringBuilder, FFPandaElementEntity> readANDXMLDocument
            (String path, String pageName, String para1, String tPageName) throws Exception {
        HashMap<StringBuilder, FFPandaElementEntity> elementsMap = new HashMap<StringBuilder, FFPandaElementEntity>();
        elementsMap.clear();
        int lineN = 1;
        File file = new File(System.getProperty("user.dir") + path);
        //System.out.println(logSpace4thisPage + "The file" + file);
        //System.out.println(logSpace4thisPage + "sss 383 : " + file.exists());
        if (!file.exists()) {
            throw new IOException("Can't find elements mapping file: " + path);
        }
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element root = document.getRootElement();

        try {
            //System.out.println(logSpace4thisPage + "in try");
            //System.out.println(logSpace4thisPage + "1st : " + pageName);
            //System.out.println(logSpace4thisPage + "tPageName : " + tPageName);
            //System.out.println(logSpace4thisPage + "root " + root.attributeValue("name"));
            //System.out.println(logSpace4thisPage + "att name " + root.attributeValue("name"));
            if (file.toString().toLowerCase().contains(root.attributeValue("name").toLowerCase())) {
                if (root.getName().equalsIgnoreCase("page")) {
                    for (Iterator<?> it = root.elementIterator(); it.hasNext(); ) {
                        //System.out.println(logSpace4thisPage + "Line NUM : " + lineN);
                        lineN++;
                        try {
                            Element elementObj = (Element) it.next();
                            String name = elementObj.attributeValue("name");
                            String waitTime = elementObj.attributeValue("waitTime");
                            String type = elementObj.attributeValue("type");
                            String value = elementObj.attributeValue("value");
                            //    String attEditORDispaly = elementObj.attributeValue("EditORDisplay");
                            //    String attComparable = elementObj.attributeValue("Comaprable");
                            //    String attUnique = elementObj.attributeValue("Unique");
                            //    String attRoute = elementObj.attributeValue("Route");
                            //System.out.println(logSpace4thisPage + "### " + name);
                            //System.out.println(logSpace4thisPage + "### " + value);

                            FFPandaElementEntity eLocator;
                            if (waitTime != null) {
                                eLocator = new FFPandaElementEntity(name, value, Integer.parseInt(waitTime), type);
                            } else {
                                eLocator = new FFPandaElementEntity(name, value, type);
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
            throw new XMLException("utility.XmlUtils.readANDXMLDocument : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }
*/
    public static HashMap<StringBuilder, FFPandaElementEntity> readIOSXMLDocument4X
    (StringBuilder fileNameXML) throws Exception {
        //System.out.println(logSpace4thisPage + "readIOSXMLDocument4X NOT ready !!!");
        return null;
    }

    public static HashMap<StringBuilder, FFPandaElementEntity> readIOSXMLDocument4Base
            (StringBuilder fileNameXML) throws Exception {
        HashMap<StringBuilder, FFPandaElementEntity> elementsMap = new HashMap<StringBuilder, FFPandaElementEntity>();
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
                            StringBuilder pName = new StringBuilder(elementObj.attributeValue("pageName"));
                            StringBuilder eName = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder locatorType = new StringBuilder(elementObj.attributeValue("locatorType"));
                            StringBuilder locatorStr = new StringBuilder(elementObj.attributeValue("locatorStr"));
                            StringBuilder showMode = new StringBuilder(elementObj.attributeValue("showMode"));
                            StringBuilder dValue = new StringBuilder(elementObj.attributeValue("defaultValue")); // for textfiled default e.g value content
                            StringBuilder text = new StringBuilder(elementObj.attributeValue("textContent"));    // for text content of btn/line/title
                            StringBuilder nextPage = new StringBuilder(elementObj.attributeValue("nextPage"));
                            StringBuilder tWin = new StringBuilder(elementObj.attributeValue("triggerWindow"));
                            FFPandaElementEntity eLocator;
                            eLocator = new FFPandaElementEntity(pName, eName, locatorType, locatorStr, dValue, text, tWin, showMode, nextPage);
                            elementsMap.put(eName, eLocator);
                        } catch (Exception e) {
                            throw new XMLException("The content in Element Line " + lineN + "is incorrect : " + e.getCause());
                        }
                    }
                } else {
                    throw new XMLException("The Root Line is not PAGE!!!");
                }
            } else {
                throw new XMLException("The NAME att is missing in the pageName 4 IOS");
            }
        } catch (XMLException e) {
            throw new XMLException("utility.XmlUtils.readIOSXMLDocument4Base : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }


    public static HashMap<StringBuilder, FFPandaElementEntity> readANDXMLDocument4X
            (StringBuilder fileNameXML) throws Exception {
        HashMap<StringBuilder, FFPandaElementEntity> elementsMap = new HashMap<StringBuilder, FFPandaElementEntity>();
        elementsMap.clear();
        int lineN = 1;
        //System.out.println(logSpace4thisPage + "Check the userdir in readANDXMLDocN " + System.getProperty("user.dir"));
        File file = new File(System.getProperty("user.dir") + fileNameXML);
        //System.out.println(logSpace4thisPage + "The file" + file + " exists? : " + file.exists());
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
                            StringBuilder pName = new StringBuilder(elementObj.attributeValue("pageName"));
                            StringBuilder eName = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder locatorType = new StringBuilder(elementObj.attributeValue("locatorType"));
                            StringBuilder locatorStr = new StringBuilder(elementObj.attributeValue("locatorStr"));
                            StringBuilder showMode = new StringBuilder(elementObj.attributeValue("showMode"));
                            StringBuilder dValue = new StringBuilder(elementObj.attributeValue("defaultValue")); // for textfiled default e.g value content
                            StringBuilder text = new StringBuilder(elementObj.attributeValue("textContent"));    // for text content of btn/line/title
                            StringBuilder nextPage = new StringBuilder(elementObj.attributeValue("nextPage"));
                            StringBuilder tWin = new StringBuilder(elementObj.attributeValue("triggerWindow"));
                            FFPandaElementEntity eLocator;

                            if ((showMode.toString().equalsIgnoreCase("ready") || showMode.toString().toLowerCase().contains("ready"))) {
                                eLocator = new FFPandaElementEntity(pName, eName, locatorType, locatorStr, dValue, text, tWin, showMode, nextPage);
                                elementsMap.put(eName, eLocator);
                            }
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
            throw new XMLException("utility.XmlUtils.readANDXMLDocument4X : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(elementsMap.size());
        return elementsMap;
    }

    public static HashMap<StringBuilder, FFPandaElementEntity> readANDXMLDocument4Base
            (StringBuilder fileNameXML) throws Exception {
        HashMap<StringBuilder, FFPandaElementEntity> elementsMap = new HashMap<StringBuilder, FFPandaElementEntity>();
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
                            StringBuilder pName = new StringBuilder(elementObj.attributeValue("pageName"));
                            StringBuilder eName = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder locatorType = new StringBuilder(elementObj.attributeValue("locatorType"));
                            StringBuilder locatorStr = new StringBuilder(elementObj.attributeValue("locatorStr"));
                            StringBuilder showMode = new StringBuilder(elementObj.attributeValue("showMode"));
                            StringBuilder dValue = new StringBuilder(elementObj.attributeValue("defaultValue")); // for textfiled default e.g value content
                            StringBuilder text = new StringBuilder(elementObj.attributeValue("textContent"));    // for text content of btn/line/title
                            StringBuilder nextPage = new StringBuilder(elementObj.attributeValue("nextPage"));
                            StringBuilder tWin = new StringBuilder(elementObj.attributeValue("triggerWindow"));
                            FFPandaElementEntity eLocator;
                            eLocator = new FFPandaElementEntity(pName, eName, locatorType, locatorStr, dValue, text, tWin, showMode, nextPage);
                            elementsMap.put(eName, eLocator);
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

    public static HashMap<StringBuilder, FFPandaElementEntity> readANDXMLDoc4loading
            (StringBuilder fileNameXML) throws Exception {
        HashMap<StringBuilder, FFPandaElementEntity> elementsMap = new HashMap<StringBuilder, FFPandaElementEntity>();
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
                        //System.out.println(logSpace4thisPage + "Line NUM : " + lineN);
                        lineN++;
                        try {
                            Element elementObj = (Element) it.next();
                            StringBuilder pName = new StringBuilder(elementObj.attributeValue("pageName"));
                            StringBuilder eName = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder locatorType = new StringBuilder(elementObj.attributeValue("locatorType"));
                            StringBuilder locatorStr = new StringBuilder(elementObj.attributeValue("locatorStr"));
                            StringBuilder showMode = new StringBuilder(elementObj.attributeValue("showMode"));
                            StringBuilder dValue = new StringBuilder(elementObj.attributeValue("defaultValue")); // for textfiled default e.g value content
                            StringBuilder text = new StringBuilder(elementObj.attributeValue("textContent"));    // for text content of btn/line/title
                            StringBuilder nextPage = new StringBuilder(elementObj.attributeValue("nextPage"));
                            StringBuilder tWin = new StringBuilder(elementObj.attributeValue("triggerWindow"));
                            FFPandaElementEntity eLocator;

                            if ((showMode.toString().equalsIgnoreCase("load"))) {
                                eLocator = new FFPandaElementEntity(pName, eName, locatorType, locatorStr, dValue, text, tWin, showMode, nextPage);
                                elementsMap.put(eName, eLocator);
                            }

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
            throw new XMLException("utility.XmlUtils.readANDXMLDoc4loading : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }

    public static HashMap<StringBuilder, FFPandaElementEntity> readANDXMLDoc4Mode
            (StringBuilder fileNameXML, StringBuilder mode) throws Exception {
        HashMap<StringBuilder, FFPandaElementEntity> elementsMap = new HashMap<StringBuilder, FFPandaElementEntity>();
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
                            StringBuilder pName = new StringBuilder(elementObj.attributeValue("pageName"));
                            StringBuilder eName = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder locatorType = new StringBuilder(elementObj.attributeValue("locatorType"));
                            StringBuilder locatorStr = new StringBuilder(elementObj.attributeValue("locatorStr"));
                            StringBuilder showMode = new StringBuilder(elementObj.attributeValue("showMode"));
                            StringBuilder dValue = new StringBuilder(elementObj.attributeValue("defaultValue")); // for textfiled default e.g value content
                            StringBuilder text = new StringBuilder(elementObj.attributeValue("textContent"));    // for text content of btn/line/title
                            StringBuilder nextPage = new StringBuilder(elementObj.attributeValue("nextPage"));
                            StringBuilder tWin = new StringBuilder(elementObj.attributeValue("triggerWindow"));
                            FFPandaElementEntity eLocator;

                            if ((showMode.toString().equalsIgnoreCase(mode.toString()))) {
                                eLocator = new FFPandaElementEntity(pName, eName, locatorType, locatorStr, dValue, text, tWin, showMode, nextPage);
                                elementsMap.put(eName, eLocator);
                            }
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
            throw new XMLException("utility.XmlUtils.readANDXMLDocument4Mode : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }


    public static HashMap<StringBuilder, FFPandaElementEntity> readANDXMLDocumentN
            (StringBuilder fileNameXML) throws Exception {
        HashMap<StringBuilder, FFPandaElementEntity> elementsMap = new HashMap<StringBuilder, FFPandaElementEntity>();
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
                        //System.out.println(logSpace4thisPage + "Line NUM : " + lineN);
                        lineN++;
                        try {
                            Element elementObj = (Element) it.next();
                            StringBuilder pName = new StringBuilder(elementObj.attributeValue("pageName"));
                            StringBuilder eName = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder locatorType = new StringBuilder(elementObj.attributeValue("locatorType"));
                            StringBuilder locatorStr = new StringBuilder(elementObj.attributeValue("locatorStr"));
                            StringBuilder showMode = new StringBuilder(elementObj.attributeValue("showMode"));
                            StringBuilder dValue = new StringBuilder(elementObj.attributeValue("defaultValue")); // for textfiled default e.g value content
                            StringBuilder text = new StringBuilder(elementObj.attributeValue("textContent"));    // for text content of btn/line/title
                            StringBuilder nextPage = new StringBuilder(elementObj.attributeValue("nextPage"));
                            StringBuilder tWin = new StringBuilder(elementObj.attributeValue("triggerWindow"));
                            FFPandaElementEntity eLocator;
                            if ((showMode.toString().equalsIgnoreCase("ready") || showMode.toString().toLowerCase().contains("ready"))) {
                                eLocator = new FFPandaElementEntity(pName, eName, locatorType, locatorStr, dValue, text, tWin, showMode, nextPage);
                                elementsMap.put(eName, eLocator);
                            }

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
            throw new XMLException("utility.XmlUtils.readANDXMLDocumentN : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }

    public static HashMap<StringBuilder, FFPandaElementEntity> readIOSXMLDocumentN
            (StringBuilder fileNameXML) throws Exception {
        HashMap<StringBuilder, FFPandaElementEntity> elementsMap = new HashMap<StringBuilder, FFPandaElementEntity>();
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
                            StringBuilder pName = new StringBuilder(elementObj.attributeValue("pageName"));
                            StringBuilder eName = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder locatorType = new StringBuilder(elementObj.attributeValue("locatorType"));
                            StringBuilder locatorStr = new StringBuilder(elementObj.attributeValue("locatorStr"));
                            StringBuilder showMode = new StringBuilder(elementObj.attributeValue("showMode"));
                            StringBuilder dValue = new StringBuilder(elementObj.attributeValue("defaultValue")); // for textfiled default e.g value content
                            StringBuilder text = new StringBuilder(elementObj.attributeValue("textContent"));    // for text content of btn/line/title
                            StringBuilder nextPage = new StringBuilder(elementObj.attributeValue("nextPage"));
                            StringBuilder tWin = new StringBuilder(elementObj.attributeValue("triggerWindow"));
                            FFPandaElementEntity eLocator;
                            if ((showMode.toString().equalsIgnoreCase("ready") || showMode.toString().toLowerCase().contains("ready"))) {
                                eLocator = new FFPandaElementEntity(pName, eName, locatorType, locatorStr, dValue, text, tWin, showMode, nextPage);
                                elementsMap.put(eName, eLocator);
                            }
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
            throw new XMLException("utility.XmlUtils.readIOSXMLDocumentN : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }


    public static HashMap<StringBuilder, StringBuilder> findANDRouteList
            (StringBuilder fileName) throws Exception {
        HashMap<StringBuilder, StringBuilder> elementsMap = new HashMap<StringBuilder, StringBuilder>();
        elementsMap.clear();
        int lineN = 1;
        File file = new File(System.getProperty("user.dir") + fileName);
        if (!file.exists()) {
            throw new IOException("Can't find elements mapping file: " + fileName);
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
                            StringBuilder name = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder nextPage =  new StringBuilder(elementObj.attributeValue("nextPage"));
                            elementsMap.put(name, nextPage);
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
            throw new XMLException("utility.XmlUtils.findANDXMLRouteList : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }

    public static HashMap<StringBuilder, StringBuilder> findIOSRouteList
            (StringBuilder fileName) throws Exception {
        HashMap<StringBuilder, StringBuilder> elementsMap = new HashMap<StringBuilder, StringBuilder>();
        elementsMap.clear();
        int lineN = 1;
        File file = new File(System.getProperty("user.dir") + fileName);
        // File file = new File(path);
        //System.out.println(logSpace4thisPage + "The file" + file);
        //System.out.println(logSpace4thisPage + "sss 673: " + file.exists());
        if (!file.exists()) {
            throw new IOException("Can't find elements mapping file: " + fileName);
        }
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element root = document.getRootElement();
        try {
            //System.out.println(logSpace4thisPage + "in try");
            // //System.out.println(logSpace4thisPage + "1st : " + fileName);
            // //System.out.println(logSpace4thisPage + "tPageName : " + tPageName);
            //System.out.println(logSpace4thisPage + "root " + root.attributeValue("name"));
            //System.out.println(logSpace4thisPage + "att name " + root.attributeValue("name"));
            if (file.toString().toLowerCase().contains(root.attributeValue("name").toLowerCase())) {
                if (root.getName().equalsIgnoreCase("page")) {
                    for (Iterator<?> it = root.elementIterator(); it.hasNext(); ) {
                        //System.out.println(logSpace4thisPage + "Line NUM : " + lineN);
                        lineN++;
                        try {
                            Element elementObj = (Element) it.next();
                            StringBuilder name = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder nextPage = new StringBuilder(elementObj.attributeValue("nextPage"));
                            //System.out.println(logSpace4thisPage + "### " + name);
                            //System.out.println(logSpace4thisPage + "### " + nextPage);
                            elementsMap.put(name, nextPage);
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
            throw new XMLException("utility.XmlUtils.findIOSRouteList : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }


    public static HashMap<StringBuilder, StringBuilder> findIOSDefaultValueList
            (StringBuilder fileName) throws Exception {
        HashMap<StringBuilder, StringBuilder> elementsMap = new HashMap<StringBuilder, StringBuilder>();
        elementsMap.clear();
        int lineN = 1;
        File file = new File(System.getProperty("user.dir") + fileName);
        if (!file.exists()) {
            throw new IOException("Can't find elements mapping file: " + fileName);
        }
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element root = document.getRootElement();
        try {
            //System.out.println(logSpace4thisPage + "in try");
            //System.out.println(logSpace4thisPage + "root " + root.attributeValue("name"));
            //System.out.println(logSpace4thisPage + "att name " + root.attributeValue("name"));
            if (file.toString().toLowerCase().contains(root.attributeValue("name").toLowerCase())) {
                if (root.getName().equalsIgnoreCase("page")) {
                    for (Iterator<?> it = root.elementIterator(); it.hasNext(); ) {
                        //System.out.println(logSpace4thisPage + "Line NUM : " + lineN);
                        lineN++;
                        try {
                            Element elementObj = (Element) it.next();
                            StringBuilder name = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder defaultValue = new StringBuilder(elementObj.attributeValue("defaultValue"));
                            //System.out.println(logSpace4thisPage + "### " + name);
                            //System.out.println(logSpace4thisPage + "### " + defaultValue);
                            elementsMap.put(name, defaultValue);
                        } catch (Exception e) {
                            throw new XMLException("The content in Element Line " + lineN + "is incorrect : " + e.getCause());
                        }
                    }
                } else {
                    throw new XMLException("The Root Line is not PAGE!!!");
                }
            } else {
                throw new XMLException("The NAME att is missing in the pageName 4 IOS");
            }
        } catch (XMLException e) {
            throw new XMLException("utility.XmlUtils.findIOSDefaultValueList : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }


    public static HashMap<StringBuilder, StringBuilder> findIOSWindowList
            (StringBuilder fileName) throws Exception {
        HashMap<StringBuilder, StringBuilder> elementsMap = new HashMap<StringBuilder, StringBuilder>();
        elementsMap.clear();
        int lineN = 1;
        File file = new File(System.getProperty("user.dir") + fileName);
        if (!file.exists()) {
            throw new IOException("Can't find elements mapping file: " + fileName);
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
                            StringBuilder eName = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder tWin = new StringBuilder(elementObj.attributeValue("triggerWindow"));

                            if (!tWin.equals("")) {
                                elementsMap.put(eName, tWin);
                            }
                        } catch (Exception e) {
                            throw new XMLException("The content in Element Line " + lineN + "is incorrect : " + e.getCause());
                        }
                    }
                } else {
                    throw new XMLException("The Root Line is not PAGE!!!");
                }
            } else {
                throw new XMLException("The NAME att is missing in the pageName 4 IOS");
            }
        } catch (XMLException e) {
            throw new XMLException("utility.XmlUtils.findIOSWindowList : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }

    public static HashMap<StringBuilder, StringBuilder> findANDTextContentList
            (StringBuilder fileName) throws Exception {
        HashMap<StringBuilder, StringBuilder> elementsMap = new HashMap<StringBuilder, StringBuilder>();
        elementsMap.clear();
        int lineN = 1;
        File file = new File(System.getProperty("user.dir") + fileName);
        if (!file.exists()) {
            throw new IOException("Can't find elements mapping file: " + fileName);
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
                            StringBuilder eName = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder text = new StringBuilder(elementObj.attributeValue("textContent"));
                            elementsMap.put(eName, text);
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
            throw new XMLException("utility.XmlUtils.findANDTextContentList : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }


    public static HashMap<StringBuilder, StringBuilder> findIOSTextContentList
            (StringBuilder fileName) throws Exception {
        HashMap<StringBuilder, StringBuilder> elementsMap = new HashMap<StringBuilder, StringBuilder>();
        elementsMap.clear();
        int lineN = 1;
        File file = new File(System.getProperty("user.dir") + fileName);
        if (!file.exists()) {
            throw new IOException("Can't find elements mapping file: " + fileName);
        }
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element root = document.getRootElement();
        try {
            if (file.toString().toLowerCase().contains(root.attributeValue("name").toLowerCase())) {
                if (root.getName().equalsIgnoreCase("page")) {
                    for (Iterator<?> it = root.elementIterator(); it.hasNext(); ) {
                        //System.out.println(logSpace4thisPage + "Line NUM : " + lineN);
                        lineN++;
                        try {
                            Element elementObj = (Element) it.next();
                            StringBuilder eName = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder text = new StringBuilder(elementObj.attributeValue("textContent"));
                            elementsMap.put(eName, text);
                        } catch (Exception e) {
                            throw new XMLException("The content in Element Line " + lineN + "is incorrect : " + e.getCause());
                        }
                    }
                } else {
                    throw new XMLException("The Root Line is not PAGE!!!");
                }

            } else {
                throw new XMLException("The NAME att is missing in the pageName 4 IOS");
            }
        } catch (XMLException e) {
            throw new XMLException("utility.XmlUtils.findIOSTextContentList : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }


    public static HashMap<StringBuilder, StringBuilder> findANDDefaultValueList
            (StringBuilder fileName) throws Exception {
        HashMap<StringBuilder, StringBuilder> elementsMap = new HashMap<StringBuilder, StringBuilder>();
        elementsMap.clear();
        int lineN = 1;
        File file = new File(System.getProperty("user.dir") + fileName);
        if (!file.exists()) {
            throw new IOException("Can't find elements mapping file: " + fileName);
        }
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element root = document.getRootElement();
        try {
            if (file.toString().toLowerCase().contains(root.attributeValue("name").toLowerCase())) {
                if (root.getName().equalsIgnoreCase("page")) {
                    for (Iterator<?> it = root.elementIterator(); it.hasNext(); ) {
                        //System.out.println(logSpace4thisPage + "Line NUM : " + lineN);
                        lineN++;
                        try {
                            Element elementObj = (Element) it.next();
                            StringBuilder eName = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder text = new StringBuilder(elementObj.attributeValue("defaultValue"));
                            elementsMap.put(eName, text);
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
            throw new XMLException("utility.XmlUtils.findANDDefaultValueList : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }

    public static HashMap<StringBuilder, StringBuilder> findANDWindowList
            (StringBuilder fileName) throws Exception {
        HashMap<StringBuilder, StringBuilder> elementsMap = new HashMap<StringBuilder, StringBuilder>();
        elementsMap.clear();
        int lineN = 1;
        File file = new File(System.getProperty("user.dir") + fileName);
        if (!file.exists()) {
            throw new IOException("Can't find elements mapping file: " + fileName);
        }
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element root = document.getRootElement();
        try {
            if (file.toString().toLowerCase().contains(root.attributeValue("name").toLowerCase())) {
                if (root.getName().equalsIgnoreCase("page")) {
                    for (Iterator<?> it = root.elementIterator(); it.hasNext(); ) {
                        //System.out.println(logSpace4thisPage + "Line NUM : " + lineN);
                        lineN++;
                        try {
                            Element elementObj = (Element) it.next();
                            StringBuilder eName = new StringBuilder(elementObj.attributeValue("elementName"));
                            StringBuilder tWIN = new StringBuilder(elementObj.attributeValue("triggerWindow"));
                            if (!tWIN.toString().equals("")) {
                                elementsMap.put(eName, tWIN);
                            }
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
            throw new XMLException("utility.XmlUtils.findANDWindowList : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsMap;
    }


    public static void main(String args[]) {
        try {
            SortedSet<operationItem> parts = new TreeSet<operationItem>();
            operationItem aa = (new operationItem("xxx", "", "", "", "", "", 1));
            operationItem bb = (new operationItem("asv", "", "", "", "", "", 2));
            operationItem cc = (new operationItem("123", "", "", "", "", "", 3));
            //  operationItem[] ada  = new operationItem[]{aa, bb, cc};
            parts.add(aa);
            parts.add(bb);
            parts.add(cc);


            long gstartTime = System.currentTimeMillis();
            readANDXMLDocument4Base(new StringBuilder("/src/main/java/utility/" + "test.xml"));

            System.out.println(System.currentTimeMillis() - gstartTime + " ms");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
