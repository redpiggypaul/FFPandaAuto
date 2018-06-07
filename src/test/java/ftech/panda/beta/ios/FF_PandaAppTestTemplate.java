package ftech.panda.beta.ios;

import LocalException.FFPandaException;
import LocalException.FFPandaException;
import dataModel.*;
import utility.readProperity.SingleTonReadProperity;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

//import static com.sun.tools.doclint.Entity.para;


/**
 * Unit test for simple App.
 */
public class FF_PandaAppTestTemplate {
    private AppiumDriver driver;
    private StringBuilder port = new StringBuilder("4723");
    private Date date = new Date();
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String time = format.format(date);

    private DesiredCapabilities capabilities = new DesiredCapabilities();

    // TargetMobileData localTargetMobileData = new TargetMobileData();

    public FF_PandaAppTestTemplate() throws FileNotFoundException {

    }


    public ArrayList<StringBuilder> stringList2SBArrlist(String[] input) {
        ArrayList<StringBuilder> result = new ArrayList<StringBuilder>();
        try {
            result.clear();
            for (int i = 0; i < input.length; i++) {
                result.add(new StringBuilder(input[i]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public void setScreenInfoAndroid(int theWide, int theLength) throws Exception {
        SingleTonReadProperity.setProValue("screenWide", String.valueOf(theWide));
        SingleTonReadProperity.setProValue("screenLength", String.valueOf(theLength));
        int down = theLength / 100 * 100 - 100; // the button 100 is out of the main operation area
        int up = down - theLength / 100 * 100 * 3 / 10;
        System.out.println("~~~~~~ Android wide is :" + theWide);
        System.out.println("~~~~~~ Android length is :" + theLength);
        System.out.println("~~~~~~ Android up is :" + up);
        System.out.println("~~~~~~ Android down is :" + down);
        SingleTonReadProperity.setProValue("upOFscreen", String.valueOf(up));
        SingleTonReadProperity.setProValue("downOFscreen", String.valueOf(down));
//        prop.setProperty("upOFscreen", theENVConfigInfo.getupOFscreen());
//        prop.setProperty("downOFscreen", theENVConfigInfo.getdownOFscreen());
        // 将Properties集合保存到流中
    }

    public void setScreenInfoIOS(int theWide, int theLength) throws Exception {
        SingleTonReadProperity.setProValue("IOSscreenWide", String.valueOf(theWide));
        SingleTonReadProperity.setProValue("IOSscreenLength", String.valueOf(theLength));
        int down = theLength / 100 * 100;
        int up = down - theLength / 100 * 100 * 3 / 10;
        System.out.println("~~~~~~ IOS wide is :" + theWide);
        System.out.println("~~~~~~ IOS length is :" + theLength);
        System.out.println("~~~~~~ IOS up is :" + up);
        System.out.println("~~~~~~ IOS down is :" + down);
        SingleTonReadProperity.setProValue("IOSupOFscreen", String.valueOf(up));
        SingleTonReadProperity.setProValue("IOSdownOFscreen", String.valueOf(down));

//        prop.setProperty("IOSupOFscreen", theENVConfigInfo.getupOFscreen());
//        prop.setProperty("IOSdownOFscreen", theENVConfigInfo.getdownOFscreen());
        // 将Properties集合保存到流中
    }

    public AndroidDriver setupAndroid(File extapp, String extport) throws Exception {
        AndroidDriver driver = null;
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(CapabilityType.VERSION, TargetMobileData.getAndOSVersion());
        capabilities.setCapability("app", extapp.getAbsolutePath());
        capabilities.setCapability("app-package", APPConfigData.getIOSAppPackage());
        capabilities.setCapability("app-activity", APPConfigData.getIOSAppActivity());
        capabilities.setCapability("unicodeKeyboard", APPConfigData.getUnicodeKeyBoard());
        capabilities.setCapability("resetKeyboard", APPConfigData.getResetKeyBoard());
        capabilities.setCapability("platform", TargetMobileData.getAndPlatVersion());
        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:" + extport + "/wd/hub"), capabilities);
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            int Screen_X = driver.manage().window().getSize().getWidth();//获取手机屏幕宽度
            int Screen_Y = driver.manage().window().getSize().getHeight();//获取手机屏幕长度
            setScreenInfoAndroid(Screen_X, Screen_Y);
        } catch (UnreachableBrowserException e) {
            driver = new AndroidDriver(new URL("http://127.0.0.1:" + extport + "/wd/hub"), capabilities);
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            int Screen_X = driver.manage().window().getSize().getWidth();//获取手机屏幕宽度
            int Screen_Y = driver.manage().window().getSize().getHeight();//获取手机屏幕长度
            setScreenInfoAndroid(Screen_X, Screen_Y);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return driver;
        }
    }

    public AndroidDriver recoverAndroid(File extapp, String extport) throws Exception {
        AndroidDriver driver = null;
        System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(CapabilityType.VERSION, TargetMobileData.getAndOSVersion());
        capabilities.setCapability("app", extapp.getAbsolutePath());
        capabilities.setCapability("app-package", APPConfigData.getIOSAppPackage());
        capabilities.setCapability("app-activity", APPConfigData.getIOSAppActivity());
        capabilities.setCapability("unicodeKeyboard", APPConfigData.getUnicodeKeyBoard());
        capabilities.setCapability("resetKeyboard", APPConfigData.getResetKeyBoard());
        capabilities.setCapability("platform", TargetMobileData.getAndPlatVersion());
        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:" + extport + "/wd/hub"), capabilities);
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            int Screen_X = driver.manage().window().getSize().getWidth();//获取手机屏幕宽度
            int Screen_Y = driver.manage().window().getSize().getHeight();//获取手机屏幕长度
            setScreenInfoAndroid(Screen_X, Screen_Y);
        } catch (UnreachableBrowserException e) {
            driver = new AndroidDriver(new URL("http://127.0.0.1:" + extport + "/wd/hub"), capabilities);
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            int Screen_X = driver.manage().window().getSize().getWidth();//获取手机屏幕宽度
            int Screen_Y = driver.manage().window().getSize().getHeight();//获取手机屏幕长度
            setScreenInfoAndroid(Screen_X, Screen_Y);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return driver;
        }
    }

    public IOSDriver setupIOSsimulator(File extapp, String extport) throws Exception {
        IOSDriver driver;
        System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));
        capabilities.setCapability("BundleID", TargetMobileData.getIosBund());
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("app", extapp.getAbsolutePath());
        capabilities.setCapability("unicodeKeyboard", APPConfigData.getUnicodeKeyBoard());
        capabilities.setCapability("resetKeyboard", APPConfigData.getResetKeyBoard());
        //   capabilities.setCapability("deviceName", ("iPad 2"));
        capabilities.setCapability("deviceName", TargetMobileData.getIosDeviceName());
        capabilities.setCapability("platformVersion", TargetMobileData.getIosPlaformVersion());
        capabilities.setCapability("platformName", TargetMobileData.getIosPlatformName());
        //   capabilities.setCapability("orientation","LANDSCAPE");

        driver = new IOSDriver(new URL("http://127.0.0.1:" + extport + "/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        int Screen_X = driver.manage().window().getSize().getWidth();//获取手机屏幕宽度
        int Screen_Y = driver.manage().window().getSize().getHeight();//获取手机屏幕长度
        setScreenInfoIOS(Screen_X, Screen_Y);
        return driver;
    }


    public IOSDriver recoverIOSsimulator(File extapp, String extport) throws Exception {
        IOSDriver driver;
        System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));
        capabilities.setCapability("app", extapp.getAbsolutePath());
        capabilities.setCapability("unicodeKeyboard", APPConfigData.getUnicodeKeyBoard());
        capabilities.setCapability("resetKeyboard", APPConfigData.getResetKeyBoard());
        capabilities.setCapability("BundleID", TargetMobileData.getIosBund());
        //   capabilities.setCapability("deviceName", ("iPad 2"));
        capabilities.setCapability("deviceName", (TargetMobileData.getIosDeviceName()));
        capabilities.setCapability("platformVersion", TargetMobileData.getIosPlaformVersion());
        capabilities.setCapability("platformName", TargetMobileData.getIosPlatformName());
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        //    capabilities.setCapability("orientation","LANDSCAPE");
        driver = new IOSDriver(new URL("http://127.0.0.1:" + extport + "/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        return driver;
    }

    public IOSDriver setupIPADsimulator(File extapp, String extport) throws Exception {
        IOSDriver driver;
        System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));
        capabilities.setCapability("BundleID", TargetMobileData.getIosBund());
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("app", extapp.getAbsolutePath());
        capabilities.setCapability("unicodeKeyboard", APPConfigData.getUnicodeKeyBoard());
        capabilities.setCapability("resetKeyboard", APPConfigData.getResetKeyBoard());
        capabilities.setCapability("deviceName", (TargetMobileData.getIosDeviceName()));
        // capabilities.setCapability("deviceName", ("iPhone 6"));
        capabilities.setCapability("platformVersion", TargetMobileData.getIosPlaformVersion());
        capabilities.setCapability("platformName", TargetMobileData.getIosPlatformName());
        capabilities.setCapability("orientation", TargetMobileData.getIosShowMode());

        driver = new IOSDriver(new URL("http://127.0.0.1:" + extport + "/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        int Screen_X = driver.manage().window().getSize().getWidth();//获取手机屏幕宽度
        int Screen_Y = driver.manage().window().getSize().getHeight();//获取手机屏幕长度
        setScreenInfoIOS(Screen_X, Screen_Y);
        return driver;
    }


    public IOSDriver recoverIPADsimulator(File extapp, String extport) throws Exception {
        IOSDriver driver;
        System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));
        capabilities.setCapability("app", extapp.getAbsolutePath());

        capabilities.setCapability("unicodeKeyboard", APPConfigData.getUnicodeKeyBoard());
        capabilities.setCapability("resetKeyboard", APPConfigData.getResetKeyBoard());
        capabilities.setCapability("BundleID", TargetMobileData.getIosBund());
        capabilities.setCapability("deviceName", (TargetMobileData.getIosDeviceName()));
        //  capabilities.setCapability("deviceName", ("iPhone 6"));
        capabilities.setCapability("platformVersion", TargetMobileData.getIosPlaformVersion());
        capabilities.setCapability("platformName", TargetMobileData.getIosPlatformName());
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("orientation", TargetMobileData.getIosShowMode());

        driver = new IOSDriver(new URL("http://127.0.0.1:" + extport + "/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        return driver;
    }


    public IOSDriver setupIOSdevice(File extapp, String extport) throws Exception {
        IOSDriver driver;
        System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));

        capabilities.setCapability("deviceName", TargetMobileData.getIosDeviceName());
        capabilities.setCapability("udid", TargetMobileData.getIosUDID());
        capabilities.setCapability("platformVersion", TargetMobileData.getIosPlaformVersion());

        capabilities.setCapability("BundleID", TargetMobileData.getIosBund());
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("app", extapp.getAbsolutePath());
        capabilities.setCapability("unicodeKeyboard", APPConfigData.getUnicodeKeyBoard());
        capabilities.setCapability("resetKeyboard", APPConfigData.getResetKeyBoard());
        capabilities.setCapability("platformName", TargetMobileData.getIosPlatformName());

        driver = new IOSDriver(new URL("http://127.0.0.1:" + extport + "/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        int Screen_X = driver.manage().window().getSize().getWidth();//获取手机屏幕宽度
        int Screen_Y = driver.manage().window().getSize().getHeight();//获取手机屏幕长度
        setScreenInfoIOS(Screen_X, Screen_Y);
        return driver;
    }

    public IOSDriver recoverIOSdevice(File extapp, String extport) throws Exception {
        IOSDriver driver;
        driver = new IOSDriver(new URL("http://127.0.0.1:" + extport + "/wd/hub"), capabilities);
        capabilities.setCapability("app", extapp.getAbsolutePath());

        capabilities.setCapability("unicodeKeyboard", APPConfigData.getUnicodeKeyBoard());
        capabilities.setCapability("resetKeyboard", APPConfigData.getResetKeyBoard());

        capabilities.setCapability("udid", TargetMobileData.getIosUDID());
        capabilities.setCapability("deviceName", (TargetMobileData.getIosDeviceName()));
        capabilities.setCapability("platformVersion", TargetMobileData.getIosPlaformVersion());

        capabilities.setCapability("BundleID", TargetMobileData.getIosBund());
        capabilities.setCapability("platformName", TargetMobileData.getIosPlatformName());
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        driver = new IOSDriver(new URL("http://127.0.0.1:" + extport + "/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        return driver;
    }

    public IOSDriver setupIPADdevice(File extapp, String extport) throws Exception {
        IOSDriver driver;
        System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));
        capabilities.setCapability("deviceName", TargetMobileData.getIosDeviceName());
        capabilities.setCapability("udid", TargetMobileData.getIosUDID());
        capabilities.setCapability("platformVersion", TargetMobileData.getIosPlaformVersion());
        capabilities.setCapability("BundleID", TargetMobileData.getIosBund());
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("app", extapp.getAbsolutePath());

        capabilities.setCapability("unicodeKeyboard", APPConfigData.getUnicodeKeyBoard());
        capabilities.setCapability("resetKeyboard", APPConfigData.getResetKeyBoard());
        capabilities.setCapability("orientation", TargetMobileData.getIosShowMode());
        capabilities.setCapability("platformName", TargetMobileData.getIosPlatformName());
        driver = new IOSDriver(new URL("http://127.0.0.1:" + extport + "/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        int Screen_X = driver.manage().window().getSize().getWidth();//获取手机屏幕宽度
        int Screen_Y = driver.manage().window().getSize().getHeight();//获取手机屏幕长度
        setScreenInfoIOS(Screen_X, Screen_Y);
        return driver;
    }

    public IOSDriver recoverIPADdevice(File extapp, String extport) throws Exception {
        IOSDriver driver;
        capabilities.setCapability("app", extapp.getAbsolutePath());
        capabilities.setCapability("unicodeKeyboard", APPConfigData.getUnicodeKeyBoard());
        capabilities.setCapability("resetKeyboard", APPConfigData.getResetKeyBoard());
        capabilities.setCapability("deviceName", TargetMobileData.getIosDeviceName());
        capabilities.setCapability("udid", TargetMobileData.getIosUDID());
        capabilities.setCapability("platformVersion", TargetMobileData.getIosPlaformVersion());
        capabilities.setCapability("BundleID", TargetMobileData.getIosBund());
        capabilities.setCapability("platformName", TargetMobileData.getIosPlatformName());
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("orientation", TargetMobileData.getIosShowMode());
        driver = new IOSDriver(new URL("http://127.0.0.1:" + extport + "/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        return driver;
    }

    public WebDriver setupWEBFOX(File extFile, String extport) throws Exception {
        WebDriver driver;
        System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));

        File pathAbs = extFile;
        FirefoxBinary fBin = new FirefoxBinary(pathAbs);
        FirefoxProfile fProfile = new FirefoxProfile();

        capabilities.setBrowserName("firefox");
        capabilities.setCapability("version", "36");

        return new FirefoxDriver(fBin, fProfile, capabilities);
    }

    public WebDriver setupWEBChrome(File extFile, String extport) throws Exception {
        WebDriver driver;
        System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));

        File pathAbs = extFile;
        FirefoxBinary fBin = new FirefoxBinary(pathAbs);
        FirefoxProfile fProfile = new FirefoxProfile();

        capabilities.setBrowserName("chrome");  // internet explorer
        capabilities.setCapability("version", "36");

        return new FirefoxDriver(fBin, fProfile, capabilities);
    }

    public WebDriver setupWEBIE(File extFile, String extport) throws Exception {
        WebDriver driver;
        System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));

        File pathAbs = extFile;
        FirefoxBinary fBin = new FirefoxBinary(pathAbs);
        FirefoxProfile fProfile = new FirefoxProfile();

        capabilities.setBrowserName("internet explorer");
        capabilities.setCapability("version", "36");

        return new FirefoxDriver(fBin, fProfile, capabilities);
    }


    public WebDriver setupWEB() throws Exception {  // TODO firefox done, complete the other brower
        try {
            WebDriver driver = null;
            System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));
            StringBuilder bFileName = new StringBuilder(SingleTonReadProperity.getProValue("browerFile"));
            StringBuilder bName = new StringBuilder(SingleTonReadProperity.getProValue("browerName"));
            StringBuilder bVersion = new StringBuilder(SingleTonReadProperity.getProValue("browerVersion"));
            StringBuilder osType = new StringBuilder(SingleTonReadProperity.getProValue("targetMobileOS"));
            if (checkBrowerFile(bFileName, osType)) {

                File browerExeFile = new File(bFileName.toString());
                //   File pathAbs = browerExeFile;
                if (bFileName.toString().toLowerCase().contains("fire")) {
                    FirefoxBinary fBin = new FirefoxBinary(browerExeFile);
                    FirefoxProfile fProfile = new FirefoxProfile();
                    capabilities.setBrowserName(bName.toString());
                    capabilities.setCapability("version", bVersion);
                    driver = new FirefoxDriver(fBin, fProfile, capabilities);
                } else if (bFileName.toString().toLowerCase().contains("chrome")) {
                    capabilities.setBrowserName(bName.toString());
                    capabilities.setCapability("version", bVersion);

                    driver = new ChromeDriver(capabilities);
                } else if (bFileName.toString().toLowerCase().contains("safari")) {
                } else if (bFileName.toString().toLowerCase().contains("internet")) {
                } else {
                }
            } else {
                throw new FFPandaException("The System Property file content is wrong ! Please check the browerFile AND OS type");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            return driver;
        }
    }

    public WebDriver setupWEB(StringBuilder browerName, StringBuilder osType) throws Exception {  // TODO firefox done, complete the other brower
        // ie, firefox, chrome              windows, mac
        try {
            WebDriver driver = null;
            System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));
            StringBuilder bFileName = new StringBuilder(SingleTonReadProperity.getProValue("browerFile"));
            StringBuilder bName = new StringBuilder(SingleTonReadProperity.getProValue("browerName"));
            StringBuilder bVersion = new StringBuilder(SingleTonReadProperity.getProValue("browerVersion"));
            if (checkBrowerFile(bFileName, osType)) {

                File browerExeFile = new File(bFileName.toString());
                //   File pathAbs = browerExeFile;
                if (bFileName.toString().toLowerCase().contains("fire")) {
                    FirefoxBinary fBin = new FirefoxBinary(browerExeFile);
                    FirefoxProfile fProfile = new FirefoxProfile();
                    capabilities.setBrowserName(bName.toString());
                    capabilities.setCapability("version", bVersion);
                    driver = new FirefoxDriver(fBin, fProfile, capabilities);
                } else if (bFileName.toString().toLowerCase().contains("chrome")) {
                    capabilities.setBrowserName(bName.toString());
                    capabilities.setCapability("version", bVersion);

                    driver = new ChromeDriver(capabilities);
                } else if (bFileName.toString().toLowerCase().contains("safari")) {
                } else if (bFileName.toString().toLowerCase().contains("internet")) {
                } else {
                }
            } else {
                throw new FFPandaException("The System Property file content is wrong ! Please check the browerFile AND OS type");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            return driver;
        }
    }


    public WebDriver setupWEB(StringBuilder osType) throws Exception {  // TODO firefox done, complete the other brower
        // ie, firefox, chrome              windows, mac
        try {
            WebDriver driver = null;
            System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));
            StringBuilder bFileName = new StringBuilder(SingleTonReadProperity.getProValue("browerFile"));
            StringBuilder bName = new StringBuilder(SingleTonReadProperity.getProValue("browerName"));
            StringBuilder bVersion = new StringBuilder(SingleTonReadProperity.getProValue("browerVersion"));
            if (checkBrowerFile(bFileName, osType)) {

                File browerExeFile = new File(bFileName.toString());
                //   File pathAbs = browerExeFile;
                if (bFileName.toString().toLowerCase().contains("fire")) {
                    FirefoxBinary fBin = new FirefoxBinary(browerExeFile);
                    FirefoxProfile fProfile = new FirefoxProfile();
                    capabilities.setBrowserName(bName.toString());
                    capabilities.setCapability("version", bVersion);
                    driver = new FirefoxDriver(fBin, fProfile, capabilities);
                } else if (bFileName.toString().toLowerCase().contains("chrome")) {
                    capabilities.setBrowserName(bName.toString());
                    capabilities.setCapability("version", bVersion);

                    driver = new ChromeDriver(capabilities);
                } else if (bFileName.toString().toLowerCase().contains("safari")) {
                } else if (bFileName.toString().toLowerCase().contains("internet")) {
                } else {
                }
            } else {
                throw new FFPandaException("The System Property file content is wrong ! Please check the browerFile AND OS type");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            return driver;
        }
    }


    public boolean checkBrowerFile(StringBuilder fileName, StringBuilder osType) {
        boolean result = false;
        try {
            if (osType.toString().equalsIgnoreCase("windows")) {
                if ((fileName.toString().startsWith("C:") || fileName.toString().startsWith("D:") || fileName.toString().startsWith("E:")) && fileName.toString().endsWith(".exe")) {
                    result = true;
                } else {
                    result = false;
                }
            } else if (osType.toString().equalsIgnoreCase("mac")) {
                if (fileName.toString().endsWith("exe")) {
                    result = false;
                } else {
                    result = true;
                }
            } else {
                result = false;
            }
        } catch (Exception e) {
        } finally {
            return result;
        }
    }

    public static String[] preapareActionParameter(String[] a, String[] b) {
        String[] target = new String[a.length + b.length];
        System.arraycopy(a, 0, target, 0, a.length);
        System.arraycopy(b, 0, target, a.length, b.length);
        for (int ind = 0; ind < target.length; ind++) {
            System.out.println("%%%%%%%%%%%%%%%%%%%" + target[ind]);
        }
        return target;
    }


    public static ArrayList<StringBuilder> preapareActionParameterList(String[] a, String[] b) {
        ArrayList<StringBuilder> result = new ArrayList<StringBuilder>();
        try {
            result.clear();
            String[] target = new String[a.length + b.length];
            System.arraycopy(a, 0, target, 0, a.length);
            System.arraycopy(b, 0, target, a.length, b.length);
            for (int ind = 0; ind < target.length; ind++) {
                System.out.println("%%%%%%%%%%%%%%%%%%%" + target[ind]);
                result.add(new StringBuilder(target[ind]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static String[] preapareActionParameter(String[] a) {
        String[] target = new String[a.length];
        System.arraycopy(a, 0, target, 0, a.length);
        for (int ind = 0; ind < target.length; ind++) {
            System.out.println("%%%%%%%%%%%%%%%%%%%" + target[ind]);
        }
        return target;
    }

    public AppiumDriver setUp4APP(AppiumDriver driver, StringBuilder extPort) throws Exception {
        try {
            File app = null;
            if (extPort.toString().equalsIgnoreCase("") || extPort == null) {
                System.out.println("TESTNG Suit don't define the correct port, use the Default value : " + this.port);
            } else {
                this.port = new StringBuilder(extPort);
            }
            if (TargetMobileData.getMobileOS().toString().equalsIgnoreCase("Android")) {
                if (!TestPlatformData.getAutoOSType().toString().toLowerCase().contains("windows")) {
                    app = new File(TestPlatformData.getMACInstallFilePath().toString().replaceAll("\\\\", File.separator), TestPlatformData.getAndroidPackageFile().toString());
                } else if (TestPlatformData.getAutoOSType().toString().toLowerCase().contains("windows")) {
                    app = new File(TestPlatformData.getWinInstallFilePath().toString(), TestPlatformData.getAndroidPackageFile().toString());
                } else {
                    throw new Exception("Abnormal Test Running Platform Info");
                }
            } else if (TargetMobileData.getMobileOS().toString().toLowerCase().startsWith("ios")) {
                app = new File(TestPlatformData.getMACInstallFilePath().toString().replaceAll("\\\\", File.separator), TestPlatformData.getMACInstallFilePath().toString());
                System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));
            }
            if (TargetMobileData.getMobileOS().toString().equalsIgnoreCase("Android")) {
                driver = setupAndroid(app, this.port.toString());
            } else if (TargetMobileData.getMobileOS().toString().toLowerCase().equals("ios")) {
                if (EnvData.getDeviceORsimulator().toString().equalsIgnoreCase("device")) {
                    driver = setupIOSdevice(app, this.port.toString());
                } else if (EnvData.getDeviceORsimulator().toString().equalsIgnoreCase("simulator")) {
                    driver = setupIOSsimulator(app, this.port.toString());
                }
            } else if (TargetMobileData.getMobileOS().toString().toLowerCase().contains("ipad")) {
                if (EnvData.getDeviceORsimulator().toString().equalsIgnoreCase("device")) {
                    driver = setupIPADdevice(app, this.port.toString());
                } else if (EnvData.getDeviceORsimulator().toString().equalsIgnoreCase("simulator")) {
                    driver = setupIPADsimulator(app, this.port.toString());
                }
            }
            //     pfile.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return driver;
        }
    }

    public AppiumDriver recover4APP(AppiumDriver driver) throws Exception {
        try {
            driver.closeApp();
            driver.quit();
            File app = null;
            Thread.sleep(5000);
            if (TargetMobileData.getMobileOS().toString().equalsIgnoreCase("Android")) {
                if (!TestPlatformData.getAutoOSType().toString().toLowerCase().contains("windows")) {
                    app = new File(TestPlatformData.getMACInstallFilePath().toString().replaceAll("\\\\", File.separator), TestPlatformData.getAndroidPackageFile().toString());
                } else if (TestPlatformData.getAutoOSType().toString().toLowerCase().contains("windows")) {
                    app = new File(TestPlatformData.getWinInstallFilePath().toString(), TestPlatformData.getAndroidPackageFile().toString());
                } else {
                    throw new Exception("Abnormal Test Running Platform Info");
                }
            } else if (TargetMobileData.getMobileOS().toString().toLowerCase().startsWith("ios")) {
                app = new File(TestPlatformData.getMACInstallFilePath().toString().replaceAll("\\\\", File.separator), TestPlatformData.getMACInstallFilePath().toString());
                System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));
            }
            if (TargetMobileData.getMobileOS().toString().equalsIgnoreCase("Android")) {
                driver = setupAndroid(app, this.port.toString());
            } else if (TargetMobileData.getMobileOS().toString().toLowerCase().equals("ios")) {
                if (EnvData.getDeviceORsimulator().toString().equalsIgnoreCase("device")) {
                    driver = setupIOSdevice(app, this.port.toString());
                } else if (EnvData.getDeviceORsimulator().toString().equalsIgnoreCase("simulator")) {
                    driver = setupIOSsimulator(app, this.port.toString());
                }
            } else if (TargetMobileData.getMobileOS().toString().toLowerCase().contains("ipad")) {
                if (EnvData.getDeviceORsimulator().toString().equalsIgnoreCase("device")) {
                    driver = setupIPADdevice(app, this.port.toString());
                } else if (EnvData.getDeviceORsimulator().toString().equalsIgnoreCase("simulator")) {
                    driver = setupIPADsimulator(app, this.port.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return driver;
        }
    }

    public FileOutputStream BeforeMethod() {
        FileOutputStream result = null;
        try {
            result = XLSReportData.getTheFileOutStream();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public FileOutputStream AfterMethod(FileOutputStream extFileOutStream, ArrayList<String> list4result, int ROWID4TC) {
        FileOutputStream result = null;
        try {
            result = XLSReportData.FlushTCResult2File(extFileOutStream, list4result, ROWID4TC);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static boolean assertTestResult(String testResult, String exceptedResultMode) throws FFPandaException {
        System.out.println("~~~~~~ Start to judge the test result! : ");
        boolean result = true;
        testResult = new String(testResult.toLowerCase());
        String sampleResult = new String(testResult);
        exceptedResultMode = filter4space(exceptedResultMode).toLowerCase();
        if (!exceptedResultMode.equalsIgnoreCase("ignore")) {
            if (!exceptedResultMode.equalsIgnoreCase("regular")) {  // for error check
                if (!sampleResult.startsWith("teststart")) {  //error cause present
                    String[] excResult = exceptedResultMode.split(";");
                    for (int ind = 0; (ind < excResult.length) && result == true; ind++) {
                        sampleResult = new String(testResult);
                        String[] singlePartResult = excResult[ind].split(" ");
                        for (int dind = 0; (dind < singlePartResult.length) && result == true; dind++) {
                            if (!sampleResult.contains(singlePartResult[dind])) {
                                result = false;
                                break;
                            } else {
                                System.out.println("start : " + sampleResult.indexOf(singlePartResult[dind]));
                                System.out.println("end : " + sampleResult.length());
                                sampleResult = sampleResult.substring(sampleResult.indexOf(singlePartResult[dind]), sampleResult.length());  //TODO cut too much, should be length()
                                System.out.println(sampleResult);
                            }
                        }
                    }
                } else  // error check but the error cause is empty OR the case which need error end with no error
                {
                    result = false;
                }
            } else if (exceptedResultMode.equalsIgnoreCase("regular")) {
                if (sampleResult.startsWith("teststart")) {
                    result = true;
                } else if (testResult.toLowerCase().contains("err") || testResult.toLowerCase().contains("fail")) {
                    result = false;
                } else {
                    System.out.println("");
                    result = false;
                }
            }
        } else {
            result = true;
        }
        System.out.println("~~~~~~ End of judge the test result! : " + result);
        return result;
    }


    public static boolean assertTestResultPageTitle(String testResult, String exceptedResult) throws FFPandaException {
        System.out.println("~~~~~~ Start to judge the test result! : assertTestResultPageTitle");
        boolean result = true;
        testResult = testResult.toLowerCase();
        String sampleResult = testResult;
        exceptedResult = filter4space(exceptedResult).toLowerCase();
        if (!exceptedResult.equalsIgnoreCase("ignore")) {
            if (!exceptedResult.equalsIgnoreCase("regular")) {
                if (!sampleResult.startsWith("teststart")) {
                    String[] excResult = exceptedResult.split(";");
                    for (int ind = 0; (ind < excResult.length) && result == true; ind++) {
                        sampleResult = testResult;
                        String[] singlePartResult = excResult[ind].split(" ");
                        for (int dind = 0; (dind < singlePartResult.length) && result == true; dind++) {
                            if (!sampleResult.contains(singlePartResult[dind])) {
                                result = false;
                                break;
                            } else {
                                System.out.println("start : " + sampleResult.indexOf(singlePartResult[dind]));
                                System.out.println("end : " + sampleResult.length());
                                sampleResult = sampleResult.substring(sampleResult.indexOf(singlePartResult[dind]), sampleResult.length());
                                System.out.println(sampleResult);
                            }
                        }
                    }
                }
            } else if (exceptedResult.equalsIgnoreCase("regular")) {
                if (sampleResult.startsWith("teststart")) {
                    result = true;
                } else if (testResult.toLowerCase().contains("err") || testResult.toLowerCase().contains("fail")) {
                    result = false;
                } else {
                    System.out.println("");
                    result = false;
                }
            }
        } else {
            result = true;
        }
        System.out.println("~~~~~~ End of judge the test result! : " + result);
        return result;
    }

    public String formatResult4XLSX(String nameOfMethod, String preRoleName, String targetResult, String nameExp, String intime) throws Exception {
        String result = null;
        String shortM = null;
        String time4xlsx = intime;
        result = (nameOfMethod + "#" + time4xlsx + "#" + preRoleName + "#" + targetResult + "#" + "End with " + "#" + nameExp);
        return result;
    }


    public static String filter4space(String s) {
        int i = s.length();//
        int j = 0;//
        int k = 0;//
        char[] arrayOfChar = s.toCharArray();//
        while ((j < i) && (arrayOfChar[(k + j)] <= ' '))
            ++j;//
        while ((j < i) && (arrayOfChar[(k + i - 1)] <= ' '))
            --i;//
        return (((j > 0) || (i < s.length())) ? s.substring(j, i) : s);//
    }

    public static StringBuilder filter4space(StringBuilder extStr) {
        String s = extStr.toString();
        StringBuilder result = new StringBuilder();
        int i = s.length();//
        int j = 0;//
        int k = 0;//
        char[] arrayOfChar = s.toCharArray();//
        while ((j < i) && (arrayOfChar[(k + j)] <= ' '))
            ++j;//
        while ((j < i) && (arrayOfChar[(k + i - 1)] <= ' '))
            --i;//
        result = new StringBuilder(((j > 0) || (i < s.length())) ? s.substring(j, i) : s);
        return result;
    }

}



