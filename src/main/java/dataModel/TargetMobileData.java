package dataModel;

import io.appium.java_client.AppiumDriver;
import utility.readProperity.SingleTonReadProperity;

public class TargetMobileData {

    private static StringBuilder mobileOS = SingleTonReadProperity.getProValue("targetMobileOS");
    private static StringBuilder deviceORsimulator = SingleTonReadProperity.getProValue("deviceORsimulator");
    private static StringBuilder andOSVersion = SingleTonReadProperity.getProValue("androidOSVersion");
    private static StringBuilder andPlatVersion = SingleTonReadProperity.getProValue("androidPlatformVersion");

    private static StringBuilder iosDeviceType = SingleTonReadProperity.getProValue("IOSdeviceType");
    private static StringBuilder iosDeviceName = SingleTonReadProperity.getProValue("IOSdeviceName");
    private static StringBuilder iosPlaformVersion = SingleTonReadProperity.getProValue("IOSplatformVersion");
    private static StringBuilder iosPlatformName = SingleTonReadProperity.getProValue("IOSplatformName");
    private static StringBuilder iosBund = SingleTonReadProperity.getProValue("IOSbundleID");
    private static StringBuilder iosUDID = SingleTonReadProperity.getProValue("IOSudid");
    private static StringBuilder iosShowMode = SingleTonReadProperity.getProValue("IOSorientationMode");

    private static int currentScreenWidth = 0;
    private static int currentScreenLength = 0;
    private static int up4CurrentScreen = 0;
    private static int down4CurrentScreen = 0;
    private static int screenMoveTimeDuration = 1000;
    private static int upRollLimit = Integer.valueOf(SingleTonReadProperity.getProValue("upRollLimit").toString());
    private static int singleElementSearchTimeDuration = Integer.parseInt(SingleTonReadProperity.getProValue("singleElementSearchTimeDuration").toString());

    public static StringBuilder port = new StringBuilder("4723");

    //  private static final TargetMobileData INSTANCE = new TargetMobileData();

    public TargetMobileData() {
    }

    public static void setCurrentScreen(AppiumDriver driver) {
        int Screen_X = driver.manage().window().getSize().getWidth();//获取手机屏幕宽度
        int Screen_Y = driver.manage().window().getSize().getHeight();//获取手机屏幕长度
        currentScreenWidth = Screen_X;
        currentScreenLength = Screen_Y;
        down4CurrentScreen = currentScreenLength / 100 * 100;
        up4CurrentScreen = currentScreenLength / 100 * 300 * 3 / 10;
    }

    public  static  void setCurrentScreen4DryRun(AppiumDriver driver)
    {
        currentScreenWidth = 300;
        currentScreenLength = 900;
        down4CurrentScreen = currentScreenLength / 100 * 100;
        up4CurrentScreen = currentScreenLength / 100 * 300 * 3 / 10;
    }

    public static int getSingleElementSearchTimeDuration() {
        return singleElementSearchTimeDuration;
    }

    public static int getUpRollLimit() {
        return upRollLimit;
    }

    public static int getCurrentScreenWidth() {
        return currentScreenWidth;
    }

    public static int getCurrentScreenLength() {
        return currentScreenLength;
    }

    public static int getUp4CurrentScreen() {
        return up4CurrentScreen;
    }

    public static int getDown4CurrentScreen() {
        return down4CurrentScreen;
    }

    public static int getScreenMoveTimeDuration() {
        return screenMoveTimeDuration;
    }

    public static StringBuilder getMobileOS() {
        return mobileOS;
    }

    public static StringBuilder getAndOSVersion() {
        return andOSVersion;
    }

    public static StringBuilder getAndPlatVersion() {
        return andPlatVersion;
    }

    public static StringBuilder getDeviceORsimulator() {
        return deviceORsimulator;
    }

    public static StringBuilder getIosBund() {
        return iosBund;
    }

    public static StringBuilder getIosDeviceName() {
        return iosDeviceName;
    }

    public static StringBuilder getIosDeviceType() {
        return iosDeviceType;
    }

    public static StringBuilder getIosPlaformVersion() {
        return iosPlaformVersion;
    }

    public static StringBuilder getIosPlatformName() {
        return iosPlatformName;
    }

    public static StringBuilder getIosShowMode() {
        return iosShowMode;
    }

    public static StringBuilder getIosUDID() {
        return iosUDID;
    }

    public static StringBuilder getPort() {
        return port;
    }
}
