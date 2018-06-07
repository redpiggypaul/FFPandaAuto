package utility;


import static REXSH.REXAUTO.Component.Driver.ScreenShot.ScreenShot;

//import static com.sun.tools.doclint.Entity.para;

/**
 * Created by appledev131 on 9/4/16.
 */
public class Wd {

  /*
    //配置 webdriver 并启动 webview 应用。
    try{
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("device", "iPhone Simulator");
        desiredCapabilities.setCapability("app", "http://appium.s3.amazonaws.com/WebViewApp6.0.app.zip");
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(url, desiredCapabilities);

        // 切换到最新的web视图
        for (String winHandle : remoteWebDriver.getWindowHandles()) {
            remoteWebDriver.switchTo().window(winHandle);
        }

        //在 guinea-pig 页面用 id 和 元素交互。
        WebElement div = remoteWebDriver.findElement(By.id("i_am_an_id"));
        Assert.assertEquals("I am a div", div.getText()); //验证得到的文本是否正确。
        remoteWebDriver.findElement(By.id("comments")).sendKeys("My comment"); //填写评论。

        //离开 webview，回到原生应用。
        remoteWebDriver.executeScript("mobile: leaveWebView");
        //关闭应用。
        remoteWebDriver.quit();
    }catch(Exception e)
    {

    }
    */
}
