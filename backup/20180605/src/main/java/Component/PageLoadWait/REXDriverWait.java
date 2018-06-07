package Component.PageLoadWait;

import REXSH.REXAUTO.APPunderTest.AppPage.AbsLoginPage;
import Component.Page.Element.ElementMapping;
import REXSH.REXAUTO.Component.Page.BasePageContent;
import Component.Page.Element.elementEntity;
import REXSH.REXAUTO.LocalException.REXException;
import REXSH.REXAUTO.LocalException.XMLException;
import utility.Log;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static utility.OSMatcher.matchOS;

/**
 * Created by appledev131 on 4/11/16.
 */
public class REXDriverWait {
    public Log theLogger = new Log(AbsLoginPage.class.getName());
    private static String logSpace4thisPage = "        ";

    public static WebElement combindElement(AppiumDriver theD, final ElementMapping inLocator, int timeOutInSeconds, String sourceOS)
            throws Exception {
        WebElement resultWE = null;
        try {

            ElementMapping locater = inLocator;
            String sType = locater.getType().toLowerCase().trim();
            String key = locater.getLocatorStr().toLowerCase().trim();
            //OSMatcher(sourceOS);

            if (1 == matchOS(sourceOS)) {
                //  resultWE = readANDXMLDocumentN(fileNameXML);
                System.out.println(logSpace4thisPage + "In TRY, REXDriverWait.combindAndElement");
                System.out.println(logSpace4thisPage + "Check the theD " + theD.toString() + "44444444444444444444444444");
                ANDDriverWait xWait = new ANDDriverWait((AndroidDriver) theD, timeOutInSeconds);
                System.out.println(logSpace4thisPage + "Finder is working with " + sType + " 4444444444444444 BYE");
                resultWE = AndFinder(theD, xWait, locater, key, sType);
            } else if (2 == matchOS(sourceOS)) {
                // resultWE = readIOSXMLDocumentN(fileNameXML);
                System.out.println(logSpace4thisPage + "In TRY, REXDriverWait.combindIOSElement");
                System.out.println(logSpace4thisPage + "Check the theD " + theD.toString() + "44444444444444444444444444");
                IOSDriverWait xWait = new IOSDriverWait((IOSDriver) theD, timeOutInSeconds);
                System.out.println(logSpace4thisPage + "Finder is working with " + sType + " 4444444444444444 BYE");
                resultWE = IOSFinder(theD, xWait, locater, key, sType);
            } else {
                throw new XMLException("Wrong OS id for : " + sourceOS + "NO match DriverWait & Finder Method !!!");
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In REXDriverWait . combindAndElement, Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        return resultWE;
    }

    public static AndroidElement combindAndElement(AndroidDriver theD, final ElementMapping inLocator, int timeOutInSeconds, int sourceOS) throws REXException {
        AndroidElement resultWE = null;
        ElementMapping locater = inLocator;
        String sType = locater.getType().toLowerCase().trim();
        String key = locater.getLocatorStr().toLowerCase().trim();
        try {
            System.out.println(logSpace4thisPage + "In TRY, REXDriverWait.combindAndElement");
            System.out.println(logSpace4thisPage + "Check theD is empty ? : " + (theD == null));
            System.out.println(logSpace4thisPage + "Check the theD " + theD.toString() + "44444444444444444444444444");
            ANDDriverWait xWait = new ANDDriverWait(theD, timeOutInSeconds);
            System.out.println(logSpace4thisPage + "Finder is working with " + sType + " 4444444444444444 BYE");
            resultWE = (AndroidElement) AndFinder(theD, xWait, locater, key, sType);
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In REXDriverWait . combindAndElement, Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        System.out.println(logSpace4thisPage + "Check the result of WebElement : " + resultWE.toString() + " in the c" +
                "REXDriverWait . combindAndElement ! ");
        return resultWE;
    }


    public static WebElement combindWebElement(WebDriver theD, final elementEntity inLocator, int timeOutInSeconds, int sourceOS) throws REXException {
        WebElement resultWE = null;
        elementEntity locater = inLocator;
        String sType = locater.getType().toString().toLowerCase().trim();
        String key = locater.getLocatorStr().toString().toLowerCase().trim();
        try {
            System.out.println(logSpace4thisPage + "In TRY, REXDriverWait.combindAndElement");
            System.out.println(logSpace4thisPage + "Check theD is empty ? : " + (theD == null));
            System.out.println(logSpace4thisPage + "Check the theD " + theD.toString() + "44444444444444444444444444");
            FOXDriverWait xWait = new FOXDriverWait(theD, timeOutInSeconds);
            System.out.println(logSpace4thisPage + "Finder is working with " + sType + " 4444444444444444 BYE");
            resultWE = FOXFinder(theD, xWait, locater, key, sType);
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In REXDriverWait . combindAndElement, Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        System.out.println(logSpace4thisPage + "Check the result of WebElement : " + resultWE.toString() + " in the c" +
                "REXDriverWait . combindAndElement ! ");
        return resultWE;
    }


    public static List<AndroidElement> combindAndElementList(AndroidDriver theD, final ElementMapping inLocator, int timeOutInSeconds, int sourceOS) throws REXException {
        List<AndroidElement> resultWE = null;
        ElementMapping locater = inLocator;
        String sType = locater.getType().toLowerCase().trim();
        String key = locater.getLocatorStr().toLowerCase().trim();
        try {
            System.out.println(logSpace4thisPage + "In TRY, REXDriverWait.combindAndElementList");
            System.out.println(logSpace4thisPage + "Check theD is empty ? : " + (theD == null));
            System.out.println(logSpace4thisPage + "Check the theD " + theD.toString() + "44444444444444444444444444");
            ANDDriverWait xWait = new ANDDriverWait(theD, timeOutInSeconds);
            System.out.println(logSpace4thisPage + "Finder is working with " + sType + " 4444444444444444 BYE");
            //   resultWE = AndListFinder(theD, xWait, (By)key);
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In REXDriverWait . combindAndElementList, Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        System.out.println(logSpace4thisPage + "Check the result of AndElement : " + resultWE.toString() + " in the c" +
                "REXDriverWait . combindAndElementList ! ");
        return resultWE;
    }

    public static IOSElement combindIOSElement(IOSDriver theD, final ElementMapping inLocator, int timeOutInSeconds, int sourceOS) throws REXException {
        IOSElement resultWE = null;
        ElementMapping locater = inLocator;
        String sType = locater.getType().toLowerCase().trim();
        String key = locater.getLocatorStr().toLowerCase().trim();
        try {
            IOSDriverWait xWait = new IOSDriverWait(theD, timeOutInSeconds);
            resultWE = IOSFinder(theD, xWait, locater, key, sType);
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In REXDriverWait . combindIOSElement, Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        return resultWE;
    }

    /*
    public static AppiumDriver combindElementSetDriver(AppiumDriver theD, final HashMap theMap, int timeOutInSeconds, String sourceOS) {
        AppiumDriver resultWE = null;
        ElementMapping locater = null;
        System.out.println("In the REXDriverWait.combindElementSetDriver for Appium Driver ");
        for (Iterator it = theMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry x2 = (Map.Entry) it.next();
            System.out.println("~~~~ combindElement ~~~~ " + x2.getValue());
            //locater = (ElementMapping) x2;
            System.out.println("~~~~ x2.getkey ~~~~ " + x2.getKey());
            System.out.println("~~~~ x2.ele.xxx ~~~~ " + ((ElementMapping) x2.getValue()).getType());
        }
        String sType = locater.getType().toLowerCase().trim();

        String key = locater.getLocatorStr().toLowerCase().trim();
        try {
            if (sourceOS.equalsIgnoreCase("and") || sourceOS.toLowerCase().contains("and")) {
                ANDDriverWait xWait = new ANDDriverWait((AndroidDriver) theD, timeOutInSeconds);
                resultWE = (AppiumDriver) AndFinder(theD, xWait, locater, key, sType);
            } else if (sourceOS.equalsIgnoreCase("IOS") || sourceOS.toLowerCase().contains("IOS")) {
                IOSDriverWait xWait = new IOSDriverWait((IOSDriver) theD, timeOutInSeconds);
                resultWE = (AppiumDriver) IOSFinder(xWait, locater, key, sType);
            } else {
                System.out.println("Wrong OS type");
            }
        } catch (Exception e) {

        }
        return resultWE;
    }
    */

/*
    public static WebElement[] combindElementSet(AppiumDriver theD, final HashMap theMap, int timeOutInSeconds, String sourceOS) {
        WebElement[] resultWE = null;
        System.out.println("^^^^^^^^ this time we called the combindElementSet : !!!");

        int indexEle = 0;
        for (Iterator it = theMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry mapObj = (Map.Entry) it.next();
            ElementMapping locater = (ElementMapping) mapObj;
            String sType = locater.getType().toLowerCase().trim();
            String key = locater.getLocatorStr().toLowerCase().trim();

            try {
                if (sourceOS.equalsIgnoreCase("and") || sourceOS.toLowerCase().contains("and")) {
                    ANDDriverWait xWait = new ANDDriverWait((AndroidDriver) theD, timeOutInSeconds);
                    resultWE[indexEle] = AndFinder(theD, xWait, locater, key, sType);
                } else if (sourceOS.equalsIgnoreCase("IOS") || sourceOS.toLowerCase().contains("IOS")) {
                    IOSDriverWait xWait = new IOSDriverWait((IOSDriver) theD, timeOutInSeconds);
                    resultWE[indexEle] = IOSFinder(xWait, locater, key, sType);
                } else {
                    System.out.println("Wrong OS type");
                }
            } catch (Exception e) {

            }
        }
        indexEle++;
        return resultWE;
    }
*/


    private static WebElement AndFinder(final AppiumDriver theD, ANDDriverWait x, final ElementMapping locator, final String key, String type) throws REXException {
        WebElement resultWE = null;
        try {
            System.out.println(logSpace4thisPage + "Check the Driver is empty : " + (theD == null));
            System.out.println(logSpace4thisPage + "Check the Driver in AndFinder : " + theD.toString());
            System.out.println(logSpace4thisPage + "Check the key in AndFinder : " + key);
            if (type.equalsIgnoreCase("name")) {
                System.out.println(logSpace4thisPage + "!!! ^^^^^^^^^^^^^^^^ This time we call the name : ");
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver theD) {
                                return theD.findElement(By.name(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . name : " + key);
            } else if (type.equalsIgnoreCase("xpath")) {
                System.out.println(logSpace4thisPage + "!!! ^^^^^^^^^^^^^^^^ This time we call the xpath : ");
                //       resultWE = ((AndroidElement) theD.findElement(By.xpath(key)));
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver d) {
                                return theD.findElement(By.xpath(key));
                            }
                        });
                //resultWE = theD.findElement(By.xpath(key));

                System.out.println(logSpace4thisPage + "REXDriverWait . xpath : " + key);

            } else if (type.equalsIgnoreCase("id")) {
                System.out.println(logSpace4thisPage + "!!! ^^^^^^^^^^^^^^^^ This time we call the id : ");
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver theD) {
                                return theD.findElement(By.id(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . id : " + key);
            } else if (type.equalsIgnoreCase("cssSelector")) {
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver theD) {
                                return theD.findElement(By.cssSelector(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . cssSelector : " + key);
            } else if (type.equalsIgnoreCase("className")) {
                System.out.println(logSpace4thisPage + "!!! ^^^^^^^^^^^^^^^^ This time we call the className : ");
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver theD) {
                                return theD.findElement(By.className(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . className : " + key);
            } else if (type.equalsIgnoreCase("tagName")) {
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver theD) {
                                return theD.findElement(By.tagName(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . tagName : " + key);
            } else if (type.equalsIgnoreCase("linkText")) {
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver theD) {
                                return theD.findElement(By.linkText(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . linkText : " + key);
            } else if (type.equalsIgnoreCase("partialLinkText")) {
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver theD) {
                                return theD.findElement(By.partialLinkText(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . partialLinkText : " + key);
            } else if (type.equalsIgnoreCase("AccessibilityId")) {
                System.out.println(logSpace4thisPage + "!!! ^^^^^^^^^^^^^^^^ This time we call the AccessibilityId : ");
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver theD) {
                                return theD.findElementByAccessibilityId(locator.getLocatorStr());
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . AccessibilityId : " + key);
            } else if (type.equalsIgnoreCase("AndroidUIAutomator")) {
                System.out.println(logSpace4thisPage + "!!! ^^^^^^^^^^^^^^^^ This time we call the androiduiautomator : ");
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver theD) {
                                return theD.findElementByAndroidUIAutomator(key);
                            }
                        });

                //   resultWE = ((AndroidDriver)theD).findElementByAndroidUIAutomator(key);
            } else if (type.equalsIgnoreCase("IosUIAutomation")) {
                System.out.println(logSpace4thisPage + "Wrong calling source" + type);
                throw new REXException("Wrong calling source");
            } else {
                System.out.println(logSpace4thisPage + "Wrong calling source " + type);
                throw new REXException("Wrong calling source ");
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In REXDriverWait . AndFinder, Other Error appear : " + e.getCause());
            e.printStackTrace();
        }

        return resultWE;
    }


    private static WebElement FOXFinder(final WebDriver theD, FOXDriverWait x, final elementEntity locator, final String key, String type) throws REXException {
        WebElement resultWE = null;
        try {
            System.out.println(logSpace4thisPage + "Check the Driver is empty : " + (theD == null));
            System.out.println(logSpace4thisPage + "Check the Driver in AndFinder : " + theD.toString());
            System.out.println(logSpace4thisPage + "Check the key in AndFinder : " + key);
            if (type.equalsIgnoreCase("name")) {
                System.out.println(logSpace4thisPage + "!!! ^^^^^^^^^^^^^^^^ This time we call the name : ");
                resultWE = x.until(
                        new FOXExpectedCondition<WebElement>() {
                            public WebElement apply(WebDriver theD) {
                                return theD.findElement(By.name(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . name : " + key);
            } else if (type.equalsIgnoreCase("xpath")) {
                System.out.println(logSpace4thisPage + "!!! ^^^^^^^^^^^^^^^^ This time we call the xpath : ");
                //       resultWE = ((AndroidElement) theD.findElement(By.xpath(key)));
                resultWE = x.until(
                        new FOXExpectedCondition<WebElement>() {
                            public WebElement apply(WebDriver d) {
                                return theD.findElement(By.xpath(key));
                            }
                        });
                //resultWE = theD.findElement(By.xpath(key));

                System.out.println(logSpace4thisPage + "REXDriverWait . xpath : " + key);

            } else if (type.equalsIgnoreCase("id")) {
                System.out.println(logSpace4thisPage + "!!! ^^^^^^^^^^^^^^^^ This time we call the id : ");
                resultWE = x.until(
                        new FOXExpectedCondition<WebElement>() {
                            public WebElement apply(WebDriver theD) {
                                return theD.findElement(By.id(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . id : " + key);
            } else if (type.equalsIgnoreCase("cssSelector")) {
                resultWE = x.until(
                        new FOXExpectedCondition<WebElement>() {
                            public WebElement apply(WebDriver theD) {
                                return theD.findElement(By.cssSelector(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . cssSelector : " + key);
            } else if (type.equalsIgnoreCase("className")) {
                System.out.println(logSpace4thisPage + "!!! ^^^^^^^^^^^^^^^^ This time we call the className : ");
                resultWE = x.until(
                        new FOXExpectedCondition<WebElement>() {
                            public WebElement apply(WebDriver theD) {
                                return theD.findElement(By.className(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . className : " + key);
            } else if (type.equalsIgnoreCase("tagName")) {
                resultWE = x.until(
                        new FOXExpectedCondition<WebElement>() {
                            public WebElement apply(WebDriver theD) {
                                return theD.findElement(By.tagName(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . tagName : " + key);
            } else if (type.equalsIgnoreCase("linkText")) {
                resultWE = x.until(
                        new FOXExpectedCondition<WebElement>() {
                            public WebElement apply(WebDriver theD) {
                                return theD.findElement(By.linkText(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . linkText : " + key);
            } else if (type.equalsIgnoreCase("partialLinkText")) {
                resultWE = x.until(
                        new FOXExpectedCondition<WebElement>() {
                            public WebElement apply(WebDriver theD) {
                                return theD.findElement(By.partialLinkText(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . partialLinkText : " + key);
            } else if (type.equalsIgnoreCase("IosUIAutomation")) {
                System.out.println(logSpace4thisPage + "Wrong calling source" + type);
                throw new REXException("Wrong calling source");
            } else {
                System.out.println(logSpace4thisPage + "Wrong calling source " + type);
                throw new REXException("Wrong calling source ");
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In REXDriverWait . AndFinder, Other Error appear : " + e.getCause());
            e.printStackTrace();
        }

        return resultWE;
    }


    public static List<WebElement> FOXListFinder(final WebDriver theD, final FOXDriverWait x, final By key) throws Exception {
        List<WebElement> resultWE = new ArrayList<WebElement>();
        List<WebElement> tResult = new ArrayList<WebElement>();
        try {
            tResult = x.until(
                    new FOXExpectedCondition<List<WebElement>>() {
                        public List<WebElement> apply(WebDriver theD) {
                            return theD.findElements(key);
                        }
                    }
            );
            for (WebElement x_ind : tResult) {
                resultWE.add((AndroidElement) x_ind);
            }
            System.out.println("Check list content ~~~");
            System.out.println("list size : " + resultWE.size());
            for (int ind = 0; ind < resultWE.size(); ind++) {
                System.out.println("[" + ind + "] : " + resultWE.get(ind));
            }
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In REXDriverWait . FOXListFinder, Other Error appear : " + e.getCause());
            throw e;
        }
        return resultWE;
    }

    public static List<AndroidElement> AndListFinder(final AppiumDriver theD, final ANDDriverWait x, final By key) throws Exception {
        List<AndroidElement> resultWE = new ArrayList<AndroidElement>();
        List<WebElement> tResult = new ArrayList<WebElement>();
        try {
            tResult = x.until(
                    new ANDExpectedCondition<List<WebElement>>() {
                        public List<WebElement> apply(AndroidDriver theD) {
                            return theD.findElements(key);
                        }
                    }
            );
            for (WebElement x_ind : tResult) {
                resultWE.add((AndroidElement) x_ind);
            }
            System.out.println("Check list content ~~~");
            System.out.println("list size : " + resultWE.size());
            for (int ind = 0; ind < resultWE.size(); ind++) {
                System.out.println("[" + ind + "] : " + resultWE.get(ind));
            }
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In REXDriverWait . AndListFinder, Other Error appear : " + e.getCause());
            throw e;
        }
        return resultWE;
    }


    public static List<IOSElement> IOSListFinder(final AppiumDriver theD, final IOSDriverWait x, final By key) throws Exception {
        List<IOSElement> resultWE = new ArrayList<IOSElement>();
        List<WebElement> tResult = new ArrayList<WebElement>();
        try {
            tResult = x.until(
                    new IOSExpectedCondition<List<WebElement>>() {
                        public List<WebElement> apply(IOSDriver theD) {
                            return theD.findElements(key);
                        }
                    }
            );
            for (WebElement x_ind : tResult) {
                resultWE.add((IOSElement) x_ind);
            }
            System.out.println("Check list content ~~~");
            System.out.println("list size : " + resultWE.size());
            for (int ind = 0; ind < resultWE.size(); ind++) {
                System.out.println("[" + ind + "] : " + resultWE.get(ind));
            }
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In REXDriverWait . IOSListFinder, Other Error appear : " + e.getCause());
            throw e;
        }
        return resultWE;
    }

    private static WebElement AndFinderxxx(final AppiumDriver theD, ANDDriverWait x, final ElementMapping locator, final String key, String type) throws REXException {
        WebElement resultWE = null;
        try {

            if (type.equalsIgnoreCase("name")) {
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver d) {
                                return theD.findElement(By.name(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . name : " + key);
            } else if (type.equalsIgnoreCase("xpath") || type.toLowerCase().contains("xpath")) {
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver d) {
                                return theD.findElement(By.xpath(key));
                            }
                        });
                //resultWE = theD.findElement(By.xpath(key));

                System.out.println(logSpace4thisPage + "REXDriverWait . xpath : " + key);

            } else if (type.equalsIgnoreCase("id")) {
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver d) {
                                return theD.findElement(By.id(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . id : " + key);
            } else if (type.equalsIgnoreCase("cssSelector")) {
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver d) {
                                return theD.findElement(By.cssSelector(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . cssSelector : " + key);
            } else if (type.equalsIgnoreCase("className")) {
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver d) {
                                return theD.findElement(By.className(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . className : " + key);
            } else if (type.equalsIgnoreCase("tagName") || type.toLowerCase().contains("tagName")) {
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver d) {
                                return theD.findElement(By.tagName(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . tagName : " + key);
            } else if (type.equalsIgnoreCase("linkText")) {
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver d) {
                                return theD.findElement(By.linkText(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . linkText : " + key);
            } else if (type.equalsIgnoreCase("partialLinkText")) {
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver d) {
                                return theD.findElement(By.partialLinkText(key));
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . partialLinkText : " + key);
            } else if (type.equalsIgnoreCase("AccessibilityId")) {
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver d) {
                                return theD.findElementByAccessibilityId(locator.getLocatorStr());
                            }
                        }
                );
                System.out.println(logSpace4thisPage + "REXDriverWait . AccessibilityId : " + key);
            } else if (type.equalsIgnoreCase("AndroidUIAutomator")) {
                resultWE = x.until(
                        new ANDExpectedCondition<WebElement>() {
                            public WebElement apply(AndroidDriver d) {
                                return d.findElementByAndroidUIAutomator(locator.getLocatorStr());
                            }
                        }
                );
            } else if (type.equalsIgnoreCase("IosUIAutomation") || type.toLowerCase().contains("IosUIAutomation")) {
                System.out.println(logSpace4thisPage + "Wrong calling source" + type);
                throw new REXException("Wrong calling source");
            } else {
                System.out.println(logSpace4thisPage + "Wrong calling source" + type);
                throw new REXException("Wrong calling source");
            }
        } catch (REXException e) {
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In REXDriverWait . AndFinderxxx, Other Error appear : " + e.getCause());
            e.printStackTrace();
        }
        return resultWE;
    }


    private static IOSElement IOSFinder(AppiumDriver theD, IOSDriverWait x, final ElementMapping locator, final String key, String type) throws Exception {
        WebElement resultWE = null;
        String sType = locator.getType().toLowerCase().trim();
        System.out.println("Show the content of un-used para sType : " + sType);
        try {
            if (type.equalsIgnoreCase("name")) {
                resultWE = x.until(
                        new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver theD) {
                                return theD.findElement(By.name(key));
                            }
                        }
                );
                System.out.println("REXDriverWait . name : " + key);
            } else if (type.equalsIgnoreCase("xpath")) {
                resultWE = x.until(
                        new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver theD) {
                                return theD.findElement(By.xpath(key));
                            }
                        }
                );
                System.out.println("REXDriverWait . xpath : " + key);
            } else if (type.equalsIgnoreCase("id")) {
                resultWE = x.until(
                        new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver theD) {
                                return theD.findElement(By.id(key));
                            }
                        }
                );
                System.out.println("REXDriverWait . id : " + key);
            } else if (type.equalsIgnoreCase("cssSelector") || type.toLowerCase().contains("cssSelector")) {
                resultWE = x.until(
                        new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver theD) {
                                return theD.findElement(By.cssSelector(key));
                            }
                        }
                );
                System.out.println("REXDriverWait . cssSelector : " + key);
            } else if (type.equalsIgnoreCase("className")) {
                resultWE = x.until(
                        new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver theD) {
                                return theD.findElement(By.className(key));
                            }
                        }
                );
                System.out.println("REXDriverWait . className : " + key);
            } else if (type.equalsIgnoreCase("tagName")) {
                resultWE = x.until(
                        new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver theD) {
                                return theD.findElement(By.tagName(key));
                            }
                        }
                );
                System.out.println("REXDriverWait . tagName : " + key);
            } else if (type.equalsIgnoreCase("linkText")) {
                resultWE = x.until(
                        new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver theD) {
                                return theD.findElement(By.linkText(key));
                            }
                        }
                );
                System.out.println("REXDriverWait . linkText : " + key);
            } else if (type.equalsIgnoreCase("partialLinkText")) {
                resultWE = x.until(
                        new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver theD) {
                                return theD.findElement(By.partialLinkText(key));
                            }
                        }
                );
                System.out.println("REXDriverWait . partialLinkText : " + key);
            } else if (type.equalsIgnoreCase("AccessibilityId")) {
                resultWE = x.until(
                        new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver theD) {
                                return theD.findElementByAccessibilityId(locator.getLocatorStr());
                            }
                        }
                );
                System.out.println("REXDriverWait . AccessibilityId : " + key);
            } else if (type.equalsIgnoreCase("IosUIAutomator")) {
                resultWE = x.until(
                        new IOSExpectedCondition<WebElement>() {
                            public WebElement apply(IOSDriver theD) {
                                return theD.findElementByIosUIAutomation(locator.getLocatorStr());
                            }
                        }
                );
            } else if (type.equalsIgnoreCase("AndroidUIAutomator")) {
                System.out.println("Wrong calling source" + type);
                throw new REXException("Wrong calling source");
            } else {
                System.out.println("Wrong calling source" + type);
                throw new REXException("Wrong calling source");
            }
        } catch (REXException e) {
            //   e.getMessage();
            throw e;
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In REXDriverWait . IOSFinder, Other Error appear : " + e.getCause());
            e.printStackTrace();
            throw e;
        }
        return (IOSElement) resultWE;
    }


    public static WebElement REXDriverWait(AppiumDriver theD, int timeOutInSeconds, String sourceOS, BasePageContent key) throws Exception {
        WebElement resultWE = null;
        try {
            if (sourceOS.equalsIgnoreCase("And")) {
                resultWE = new ANDDriverWait((AndroidDriver) theD, timeOutInSeconds).until(new ANDExpectedCondition<WebElement>() {
                    public WebElement apply(AndroidDriver d) {
                        return d.findElement(By.name("key"));
                    }
                });
            } else if (sourceOS.equalsIgnoreCase("IOS")) {
                resultWE = new IOSDriverWait((IOSDriver) theD, timeOutInSeconds).until(new IOSExpectedCondition<WebElement>() {
                    public WebElement apply(IOSDriver d) {
                        return d.findElement(By.name("key"));
                    }
                });
            } else {
                System.out.println(logSpace4thisPage + "Wrong calling source" + sourceOS);
            }
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In REXDriverWait . REXDriverWait 4, Other Error appear : " + e.getCause());
            throw e;
        }
        return resultWE;
    }


    public static WebElement REXDriverWait(AppiumDriver theD, int timeOutInSeconds, String sourceOS) throws Exception {
        WebElement resultWE = null;
        try {

            if (sourceOS.equalsIgnoreCase("And")) {
                resultWE = new ANDDriverWait((AndroidDriver) theD, timeOutInSeconds).until(new ANDExpectedCondition<WebElement>() {
                    public WebElement apply(AndroidDriver d) {
                        return d.findElement(By.name("key"));
                    }
                });
            } else if (sourceOS.equalsIgnoreCase("IOS")) {
                resultWE = new IOSDriverWait((IOSDriver) theD, timeOutInSeconds).until(new IOSExpectedCondition<WebElement>() {
                    public WebElement apply(IOSDriver d) {
                        return d.findElement(By.name("key"));
                    }
                });
            } else {
                System.out.println(logSpace4thisPage + "Wrong calling source" + sourceOS);
            }

        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(logSpace4thisPage + "In REXDriverWait . REXDriverWait 3, Other Error appear : " + e.getCause());
            throw e;
        }
        return resultWE;
    }
}
