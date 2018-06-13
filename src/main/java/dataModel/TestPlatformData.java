package dataModel;

import utility.StringB;
import utility.readProperity.SingleTonReadProperity;

import java.util.Properties;

public class TestPlatformData {
    private static Properties props = System.getProperties();
    private static StringBuilder autoOSType = new StringBuilder(props.getProperty("os.name"));
    private static StringBuilder MACInstallFilePath = new StringBuilder(SingleTonReadProperity.getProValue("MACAppInstallFilePath"));
    private static StringBuilder WinInstallFilePath = new StringBuilder(SingleTonReadProperity.getProValue("WindowsAppInstallFilePath"));

    private static StringBuilder AndroidPackageFile = new StringBuilder(SingleTonReadProperity.getProValue("AndroidInstallFile"));
    private static StringBuilder IOSPackageFile = new StringBuilder(SingleTonReadProperity.getProValue("IOSInstallFile"));

    public TestPlatformData(){
        Properties props = System.getProperties();
        StringBuilder autoOSType = new StringBuilder(props.getProperty("os.name"));
        StringBuilder MACInstallFilePath = new StringBuilder(SingleTonReadProperity.getProValue("MACAppInstallFilePath"));
        StringBuilder WinInstallFilePath = new StringBuilder(SingleTonReadProperity.getProValue("WindowsAppInstallFilePath"));
        StringBuilder AndroidPackageFile = new StringBuilder(SingleTonReadProperity.getProValue("AndroidInstallFile"));
        StringBuilder IOSPackageFile = new StringBuilder(SingleTonReadProperity.getProValue("IOSInstallFile"));
    }

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

    public static void main(String args[]) {
        TestPlatformData td = new TestPlatformData();
        StringBuilder autoOSType = td.getAutoOSType();
        System.out.println();
    }

    //   private StringBuilder autoOSType = SingleTonReadProperity.getProValue("autoRunningOSType");
}
