package Component.Page;

import Component.Page.Element.ElementMapping;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static Component.PageLoadWait.REXDriverWait.combindAndElement;
import static utility.XmlUtils.readANDXMLDocument;

/**
 * Created by appledev131 on 4/12/16.
 */
public class SubscriberAttribute extends AppBasePageContent {
    private WebElement [] runningEleList = null;
    static private int osType = 1;

    private AndroidDriver theD = null;
    private String fPath = null;

    private static String logSpace4thisPage = " --- SubscriberAttribute ---";

    public SubscriberAttribute(AndroidDriver dr, String path) throws Exception {
        super(dr, path);
        System.out.println("Just called the super AppBasePageContent in ANDBasePageContent with 3 para");
        this.theD = dr;
        this.fPath = path;
        locatorMap = this.AndXMLdecoder(path);
        System.out.println(logSpace4thisPage + "locatorMapt is empty? " + locatorMap.isEmpty());
        for (Iterator it = locatorMap.entrySet().iterator(); it.hasNext(); ) {
            Thread.sleep(500);
            Map.Entry eleEntry = (Map.Entry) it.next();
        }
    }


    public SubscriberAttribute(String path) throws Exception {
        System.out.println("Just called the super AppBasePageContent in ANDBasePageConten with 3 para");
        this.fPath = path;
        locatorMap = this.AndXMLdecoder(path);

        for (Iterator it = locatorMap.entrySet().iterator(); it.hasNext(); ) {
            Thread.sleep(500);
            Map.Entry eleEntry = (Map.Entry) it.next();
            System.out.println("~~~~ Check the this.baseObject.locatorMap.entrySet() getValue~~~~ " + eleEntry.getValue());
            System.out.println("~~~~ Check the this.baseObject.locatorMap.entrySet() getkey ~~~~ " + eleEntry.getKey());
        }
    }

    public WebElement[] getPageEleList(String tPageName) throws Exception {
        WebElement[] resultList = null;
        super.getPageEleList(osType, tPageName);
        return resultList;
    }

    public static void main(String args[]) {
        try {
            AndroidDriver dr = null;
            String path = ("/Users/appledev131/Documents/AUTO/REXAUTO4AND/PageXML/And").replaceAll("/", File.separator);
            try {
                File classpathRoot = new File(System.getProperty("user.dir"));
                File appDir = new File(classpathRoot, "../");
                File app = new File(appDir, "app-debug.apk");
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("deviceName", "Android Emulator");
                capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
                capabilities.setCapability(CapabilityType.VERSION, "5.1");
                capabilities.setCapability("app", app.getAbsolutePath());
                capabilities.setCapability("app-package", "com.pwc.talentexchange");
                capabilities.setCapability("app-activity", ".activity.BaseActivity");
                dr = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                Thread.sleep(6000);
                WebElement theEleS = null;
                HashMap eleSet = new HashMap<String, AndroidElement>();
                eleSet.clear();

                SubscriberAttribute x = new SubscriberAttribute(dr, path);


                HashMap map = readANDXMLDocument(System.getProperty("user.dir")
                                + File.separator + "PageXML"
                                + File.separator + "AND"
                                + File.separator + "loginPage.xml",
                        "loginPage", "test", "loginPage");

                for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
                    Thread.sleep(500);
                    Map.Entry eleEntry = (Map.Entry) it.next();
                    System.out.println("~~~~ combindElement. eleEntry  getValue~~~~ " + eleEntry.getValue());
                    System.out.println("~~~~ eleEntry.getkey ~~~~ " + eleEntry.getKey());
                    theEleS = combindAndElement(dr, (ElementMapping) eleEntry.getValue(), 6, 1);
                    eleSet.put(eleEntry.getKey(), theEleS);
                }
                dr.hideKeyboard();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
