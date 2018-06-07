package dataModel;

import utility.StringB;
import utility.readProperity.SingleTonReadProperity;

import java.util.Properties;

public class TestPlatformData {
    private static Properties props = System.getProperties();
    private static StringBuilder autoOSType = new StringBuilder(props.getProperty("os.name"));
    private static StringBuilder MACInstallFilePath = new StringBuilder(props.getProperty("MACAppInstallFilePath"));
    private static StringBuilder WinInstallFilePath = new StringBuilder(props.getProperty("WindowsAppInstallFilePath"));

    private static StringBuilder AndroidPackageFile = new StringBuilder(props.getProperty("AndroidInstallFile"));
    private static StringBuilder IOSPackageFile = new StringBuilder(props.getProperty("IOSInstallFile"));

    public TestPlatformData(){}

    public static StringBuilder getAndroidPackageFile() {
        return AndroidPackageFile;
    }

    public static StringBuilder getAutoOSType() {
        return autoOSType;
    }

    public static StringBuilder getIOSPackageFile() {
        return IOSPackageFile;
    }

    public static StringBuilder getMACInstallFilePath() {
        return MACInstallFilePath;
    }

    public static StringBuilder getWinInstallFilePath() {
        return WinInstallFilePath;
    }


    //   private StringBuilder autoOSType = SingleTonReadProperity.getProValue("autoRunningOSType");
}
