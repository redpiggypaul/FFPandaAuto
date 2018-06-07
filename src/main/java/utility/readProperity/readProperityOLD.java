package utility.readProperity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by appledev131 on 5/10/16.
 */
public final class readProperityOLD {


    //   private static final String FILENAME = "REXenvConfig/config.properties";

    public static String getProValue(String key) {
        String result = null;
        String propsPath = (System.getProperty("user.dir") + "\\src\\FFenvConfig\\config.properties").replaceAll("\\\\", File.separator);
        Properties props = new Properties();

        try {
            InputStream in = Files.newInputStream(Paths.get(propsPath));
            props.load(in);
            result = props.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String setProValue(String key, String value) throws Exception {
        String result = null;
        try {
            String propsPath = (System.getProperty("user.dir") + "\\src\\FFenvConfig\\config.properties").replaceAll("\\\\", File.separator);
            FileInputStream fis = new FileInputStream((System.getProperty("user.dir") + "\\src\\REXenvConfig\\config.properties").replaceAll("\\\\", File.separator));
            Properties props = new Properties();
            props.load(fis);// 将属性文件流装载到Properties对象中
            InputStream in = Files.newInputStream(Paths.get(propsPath));
            props.load(in);
            fis.close();// 关闭流
            props.setProperty(key, value);
            System.out.println("~~~~~~ Check the property which just set : " + key + " : " + props.getProperty(key));
            FileOutputStream fos = new FileOutputStream((System.getProperty("user.dir") + "\\src\\FFenvConfig\\config.properties").replaceAll("\\\\", File.separator));
            // 将Properties集合保存到流中
            props.store(fos, "Copyright (c) Paul ZHU's Studio");
            fos.close();// 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        String x = (System.getProperty("user.dir") + "\\src\\FFenvConfig\\config.properties").replaceAll("\\\\", File.separator);
        System.out.println("x : " + x);
        Properties props = new Properties();
        x = ("\\Users\\appledev131\\Documents\\REXAUTO4\\src\\FFenvConfig\\config.properties").replaceAll("\\\\", File.separator);

        try {
            InputStream in = Files.newInputStream(Paths.get(x));
            props.load(in);
            System.out.println(props.getProperty("pageLoadTimeout"));
            System.out.println(props.getProperty("upOFscreen"));
            System.out.println(props.getProperty("downOFscreen"));
            System.out.println(props.getProperty("upallNumber"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(getProValue("operation.platform"));
    }
}

    /*
    private static String param1;
    private static String param2;

    {
        Properties prop = new Properties();
        String x = (System.getProperty("user.dir") + "/src/REXenvConfig/config.properties").replaceAll("/", File.separator);
        System.out.println("x : " + x);
        x = x.replaceAll("/", File.separator);
        System.out.println("x  rp : " + x);


        try {
            InputStream in = readProperity.class.getClassLoader().getResourceAsStream(x);
            prop.load(in);
            System.out.println(prop.getProperty("pageLoadTimeout"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 私有构造方法，不需要创建对象
     */
    /*
    private readProperity() {
    }

    public static String getParam1() {
        return param1;
    }

    public static String getParam2() {
        return param2;
    }

    public static void main(String args[]) {
        System.out.println(getParam1());
        System.out.println(getParam2());
    }
}
*/