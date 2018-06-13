package dataModel;

import utility.readProperity.SingleTonReadProperity;

public class EnvData {
    private static StringBuilder mobileOS = SingleTonReadProperity.getProValue("targetMobileOS");
    private static StringBuilder deviceORsimulator = SingleTonReadProperity.getProValue("deviceORsimulator");

    public static StringBuilder getDeviceORsimulator() {
        return deviceORsimulator;
    }

    public static StringBuilder getMobileOS() {
        return mobileOS;
    }
}
