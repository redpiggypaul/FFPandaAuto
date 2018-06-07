package Component.Page;

import Component.Page.Element.ElementMapping;
import Component.Page.Element.operationItem;
import REXSH.REXAUTO.LocalException.PageException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static Component.PageLoadWait.REXDriverWait.combindAndElement;
import static Component.PageLoadWait.REXDriverWait.combindIOSElement;
import static utility.XmlUtils.*;

/**
 * Created by appledev131 on 4/12/16.
 */
public class AppBasePageContent {
    private int osType = 0;
    private AppiumDriver theD = null;
    private String fPath = null;
    public HashMap<String, ElementMapping> locatorMap = null;

    public AppBasePageContent(AppiumDriver driver, String path, int osT) throws Exception {
        this.theD = driver;
        this.fPath = path;
        this.osType = osT;
    }

    public AppBasePageContent(AppiumDriver driver, String path) throws Exception {
        this.theD = driver;
        this.fPath = path;
    }

    public AppBasePageContent() throws Exception {
    }

    public AppBasePageContent(String path) throws Exception {
    }


    public HashMap<String, WebElement> getPageEleList(final int osType, String tPageName) throws Exception {
        WebElement[] resultList = null;
        WebElement theEleS = null;
        HashMap eleSet = new HashMap<String, WebElement>();
        eleSet.clear();
        locatorMap = XMLdecoder(fPath, osType, tPageName);
        System.out.println("%%% chcek theD in AppBasePageContent : " + theD.toString());

        for (Iterator it = locatorMap.entrySet().iterator(); it.hasNext(); ) {
            try {
                Thread.sleep(500);

                Map.Entry eleEntry = (Map.Entry) it.next();
                System.out.println("~~~~ combindElement. eleEntry  getValue~~~~ " + eleEntry.getValue());
                //locater = (ElementMapping) x2;
                System.out.println("~~~~ eleEntry.getkey ~~~~ " + eleEntry.getKey());
                if (2 == osType) {
                    theEleS = combindIOSElement((IOSDriver) theD, (ElementMapping) eleEntry.getValue(), 6, osType);
                    eleSet.put(eleEntry.getKey(), theEleS);
                    //return resultList;
                } else if (1 == osType) {
                    System.out.println("^^^^^^^^^^^^^^^^^^^^^^ Call combindAndElement ^^^^^^^^^^^^^^^^^");
                    theEleS = combindAndElement((AndroidDriver) theD, (ElementMapping) eleEntry.getValue(), 6, osType);
                    eleSet.put(eleEntry.getKey(), theEleS);
                    System.out.println("!!!!!! " + theEleS.toString() + " !!!!!!");
                    //return resultList;
                } else {
                    throw new PageException("Failed to find the combind Method 4 os TYPE in :" + this.getClass().getSimpleName().toString());
                }
            } catch (PageException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Iterator it = eleSet.entrySet().iterator(); it.hasNext(); ) {
            Thread.sleep(500);
            Map.Entry eleEntry = (Map.Entry) it.next();
            System.out.println("@@@@@@@@@@@@@@@ combindElement. eleEntry  getValue~@@@@@@@@@@@@@@@  " + eleEntry.getValue());
            //locater = (ElementMapping) x2;
            System.out.println("@@@@@@@@@@@@@@@ eleEntry.getkey @@@@@@@@@@@@@@@  " + eleEntry.getKey());
            //System.out.println("~~~~ x2.ele.xxx ~~~~ " + ((ElementMapping)x2.getValue()).getType());
        }
        return eleSet;
    }


    public HashMap<String, WebElement> getPageEleList(AppiumDriver driver, HashMap<String, ElementMapping> locator, Integer osType) throws Exception {
        WebElement[] resultList = null;
        WebElement theEleS = null;
        HashMap eleSet = new HashMap<String, WebElement>();
        eleSet.clear();
        for (Iterator it = locator.entrySet().iterator(); it.hasNext(); ) {
            try {
                Thread.sleep(500);
                Map.Entry eleEntry = (Map.Entry) it.next();
                System.out.println("~~~~ combindElement. eleEntry  getValue~~~~ " + eleEntry.getValue());
                System.out.println("~~~~ eleEntry.getkey ~~~~ " + eleEntry.getKey());
                if (2 == osType) {
                    theEleS = combindIOSElement((IOSDriver) driver, (ElementMapping) eleEntry.getValue(), 6, osType);
                    eleSet.put(eleEntry.getKey(), theEleS);
                    //return resultList;
                } else if (1 == osType) {
                    System.out.println("^^^^^^^^^^^^^^^^^^^^^^ Call combindAndElement ^^^^^^^^^^^^^^^^^");
                    theEleS = combindAndElement((AndroidDriver) driver, (ElementMapping) eleEntry.getValue(), 6, osType);
                    eleSet.put(eleEntry.getKey(), theEleS);
                    System.out.println("!!!!!! " + theEleS.toString() + " !!!!!!");
                    //return resultList;
                } else {
                    throw new PageException("Failed to find the combind Method 4 os TYPE in :" + this.getClass().getSimpleName().toString());
                }
            } catch (PageException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Iterator it = eleSet.entrySet().iterator(); it.hasNext(); ) {
            Thread.sleep(500);
            Map.Entry eleEntry = (Map.Entry) it.next();
            System.out.println("@@@@@@@@@@@@@@@ combindElement. eleEntry  getValue~@@@@@@@@@@@@@@@  " + eleEntry.getValue());
            System.out.println("@@@@@@@@@@@@@@@ eleEntry.getkey @@@@@@@@@@@@@@@  " + eleEntry.getKey());
        }
        return eleSet;
    }


    public HashMap<String, ElementMapping> XMLdecoder(String path) throws Exception {
        System.out.println("AppBasePageContent . XMLdecoder(String path) has been called ! ");
        return readXMLDocument(path, this.getClass().getSimpleName());
    }


    public HashMap<String, ElementMapping> AndXMLdecoder(String path) throws Exception {
        System.out.println("AppBasePageContent . AndXMLdecoder(String path) has been called ! ");
        return readANDXMLDocumentN(path);
    }

    public HashMap<String, ElementMapping> IOSXMLdecoder(String path) throws Exception {
        System.out.println("AppBasePageContent . IOSXMLdecoder(String path) has been called ! ");
        return readIOSXMLDocumentN(path);
    }

    public HashMap<String, operationItem> opeXMLdecoder(String path) throws Exception {
        return readXMLDocument4Ope(path, this.getClass().getSimpleName());
    }

    public HashMap<String, ElementMapping> getLocaterMap() throws Exception {
        return this.locatorMap;
    }

    protected HashMap<String, ElementMapping> XMLdecoder(String path, int osType, String tPageName) throws Exception {
        System.out.println("BasePageContent : the XMLdecoder with OS TYPE is called");
        HashMap<String, ElementMapping> res = null;
        try {
            if (1 == osType) {
                res =  readANDXMLDocumentN(path);
            } else if (2 == osType) {
                res = readIOSXMLDocumentN(path);
            } else {
            }
        } catch (Exception e) {
            throw new PageException("Failed to XMLdecoder !!! +++ " + this.getClass().getSimpleName() + " +++ " + e.getCause());
        }
        return res ;
    }

    public HashMap<String, String> getNextPageList(String path, int osType, String tPageName) throws Exception {
        System.out.println("BasePageContent : the getNextPageList with OS TYPE is called");
        try {
            if (osType == 2) {
                return findIOSRouteList(path);
            } else if (osType == 1) {
                return findANDRouteList(path);
            } else {
                throw new PageException("Wrong OS Type !!! +++ " + this.getClass().getSimpleName() + " +++ ");
            }
        } catch (Exception e) {
            throw new PageException("Failed to getNextPageList !!! +++ " + this.getClass().getSimpleName() + " +++ " + e.getCause());
        }

    }

    public HashMap<String, String> getDefaultValueList(String path, int osType, String tPageName) throws Exception {
        System.out.println("BasePageContent : the getDefaultValueList with OS TYPE is called");
        try {
            if (osType == 2) {
                return findIOSDefaultValueList(path);
            } else if (osType == 1) {
                return findANDDefaultValueList(path);
            } else {
                throw new PageException("Wrong OS Type !!! +++ " + this.getClass().getSimpleName() + " +++ ");
            }
        } catch (Exception e) {
            throw new PageException("Failed to getDefaultValueList !!! +++ " + this.getClass().getSimpleName() + " +++ " + e.getCause());
        }

    }
}
