package utility.readProperity;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class SingleTonReadMailConfigProperity implements Serializable {
    private static StringBuilder filePathAndName = new StringBuilder(System.getProperty("user.dir") + "\\src\\FFenvConfig\\report_config.properties");

    //initailzed during class loading
    private static final SingleTonReadMailConfigProperity INSTANCE = new SingleTonReadMailConfigProperity();

    //to prevent creating another instance of Singleton
    private SingleTonReadMailConfigProperity() {
    }


    public static SingleTonReadMailConfigProperity getSingleton() {
        return INSTANCE;
    }

    // private static class SingletonHolder {
    //   private static final SingleTonReadProperity INSTANCE = new SingleTonReadProperity() {
    //  };
    //  }


    public static String getProValueString(String key) {
        String result = null;
        Properties props = new Properties();

        try {
            //        InputStream inStream = new FileInputStream(System.getProperty("user.dir")+"\\sre\\envConfig\\config.properties");
            InputStream inStream = new FileInputStream(filePathAndName.toString());
            props.load(inStream);
            result = props.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static StringBuilder getProValue(String key) {
        StringBuilder result = null;
        Properties props = new Properties();

        try {
            InputStream inStream = new FileInputStream(filePathAndName.toString());
            props.load(inStream);
            result = new StringBuilder(props.getProperty(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String setProValue(String key, String value) throws Exception {
        String result = null;
        try {
            FileInputStream fis = new FileInputStream(filePathAndName.toString());
            Properties props = new Properties();
            props.load(fis);// 将属性文件流装载到Properties对象中
            InputStream in = Files.newInputStream(Paths.get(filePathAndName.toString()));
            props.load(in);
            fis.close();// 关闭流
            props.setProperty(key, value);
            System.out.println("~~~~~~ Check the property which just set : " + key + " : " + props.getProperty(key));
            FileOutputStream fos = new FileOutputStream(filePathAndName.toString());
            // 将Properties集合保存到流中
            props.store(fos, "Copyright (c) Paul ZHU's Studio");
            fos.close();// 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static SingleTonReadMailConfigProperity getInstance() {
        return INSTANCE;
    }
}
