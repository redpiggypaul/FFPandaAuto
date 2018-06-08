/**
 *
 */
package Component.Operation;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author lyou009 , Modified pzhu023
 */

//public class AppBaseOperation extends AppBasePageContent {
public class AppBaseOperation {
    //AppiumDriver<?> appdriver = (AppiumDriver<?>)this.driver;
    // protected WebDriver driver;
    protected AppiumDriver appdriver = null;   //  (AppiumDriver)this.driver;

    //public AppBaseOperation(AppiumDriver<?> driver, String path)
    public AppBaseOperation(AppiumDriver driver, String path) throws Exception {
        this.appdriver = driver;
        //super(driver, path);
        System.out.println("~~~~~~++++++****** AppBaseOperation(AppiumDriver driver, String path) ******++++++~~~~~~");
    }

    public AppBaseOperation(AppiumDriver driver) throws Exception {
        this.appdriver = driver;
        //super(driver, path);
        System.out.println("~~~~~~++++++****** AppBaseOperation(AppiumDriver driver) ******++++++~~~~~~");
    }

    public AppBaseOperation(String path) throws Exception {

        //super(path);
        System.out.println("~~~~~~++++++****** AppBaseOperation(String path) ******++++++~~~~~~");
    }


    public static void clearAndTypeString(WebElement ele, String text) throws InterruptedException {
        Thread.sleep(200);
        ele.clear();
        Thread.sleep(500);
        ele.sendKeys(text);
    }

    public void tab(WebElement eleObj) {
        appdriver.zoom(eleObj);
    }

    public void launchApp() {
        appdriver.launchApp();
    }

    public void closeApp() {
        appdriver.closeApp();
    }

    public void hideKeyboard() {
        appdriver.hideKeyboard();
    }

    //public void rotateMobile() {appdriver.rotate(((AppiumDriver<?>)driver).getOrientation());}
    public void rotateMobile(WebDriver driver) {
        appdriver.rotate(((AppiumDriver) driver).getOrientation());
    }

    public void pinch(WebElement eleObj) {
        appdriver.pinch(eleObj);
    }

    public void zoom(WebElement eleObj) {
        appdriver.zoom(eleObj);
    }

    public void scrollTo(String text) {
        appdriver.scrollTo(text);
    }

    // single click by one finger
    public void click(WebElement eleOj) {

        //   System.out.println("~~~~~~++++++****** click(WebElement eleOj) ******++++++~~~~~~");
        System.out.println("appdriver " + appdriver.toString());
        appdriver.tap(1, eleOj, 100);
    }

    public static void click(WebElement eleOj, AppiumDriver dr) throws Exception {
        try {
            //     System.out.println("~~~~~~++++++****** click(WebElement eleOj , AppiumDriver dr) ******++++++~~~~~~");
            System.out.println("dr " + dr.toString());
            System.out.println("check eleoj : " + eleOj);
            dr.tap(1, (WebElement) eleOj, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clickPoint(AppiumDriver dr, int x, int y) throws Exception {
        try {
            //     System.out.println("~~~~~~++++++****** click(WebElement eleOj , AppiumDriver dr) ******++++++~~~~~~");
            System.out.println("dr " + dr.toString());
            dr.tap(1, x, y, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void t(int startx, int starty, int endx, int endy, int duration) {
        appdriver.swipe(startx, starty, endx, endy, duration);
    }

    public void drag(WebElement from, WebElement to) {
        new TouchAction(appdriver).longPress(from).moveTo(to).release().perform();
    }

    public void gesture(int startx, int starty, int x1, int y1, int x2, int y2) {
        new TouchAction(appdriver).longPress(startx, starty).moveTo(x1, y1)
                .moveTo(x2, y2).release().perform();
    }

    public void iosSwipe(int startx, int starty, int endx, int endy, int duration) throws InterruptedException {
        try {
            new TouchAction(appdriver).press(startx, starty).waitAction(duration/5).moveTo(endx, endy).waitAction(duration/5)
                    .release().perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void iosSwipeHome(int startx, int starty, int endx, int endy, int duration) throws InterruptedException {
        try {
            new TouchAction(appdriver).press(startx, starty).waitAction(duration/5).moveTo(endx, endy)
                    .release().perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void iosSwipeNoWait(int startx, int starty, int endx, int endy, int duration) throws InterruptedException {
        try {
            new TouchAction(appdriver).press(startx, starty).waitAction(duration/5).moveTo(endx, endy/3).waitAction(duration/5).moveTo(endx, endy/3).waitAction(duration/5).moveTo(endx, endy/3).waitAction(duration/5)
                    .release().perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void iosSwipetest(int startx, int starty, int endx, int endy, int duration) throws Exception {
        try {
            new TouchAction(appdriver).press(220, 550).waitAction(500).moveTo(0, -150).waitAction(500)
                    .release().perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
