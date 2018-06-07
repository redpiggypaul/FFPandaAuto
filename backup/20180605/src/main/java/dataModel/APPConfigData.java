package dataModel;

import utility.readProperity.SingleTonReadProperity;

public class APPConfigData {

    private static StringBuilder IOSAppPackage = SingleTonReadProperity.getProValue("IOSapp-package");
    private static StringBuilder IOSAppActivity = SingleTonReadProperity.getProValue("IOSapp-activity");
    private static StringBuilder UnicodeKeyBoard = SingleTonReadProperity.getProValue("unicodeKeyboard");
    private static StringBuilder ResetKeyBoard = SingleTonReadProperity.getProValue("resetKeyboard");

    public static StringBuilder getIOSAppActivity() {
        return IOSAppActivity;
    }

    public static StringBuilder getIOSAppPackage() {
        return IOSAppPackage;
    }

    public static StringBuilder getResetKeyBoard() {
        return ResetKeyBoard;
    }

    public static StringBuilder getUnicodeKeyBoard() {
        return UnicodeKeyBoard;
    }
}

