package utility;

/**
 * @author pzhu023
 *
 */
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

public class Log {

    //  DOMConfigurator.configure("log4j.xml");
    private static Logger logger;
    private static String filePath=("src\\REXenvConfig\\log4jnew.properties").replaceAll("\\\\", File.separator);
    private static boolean flag=false;
    private static String classname;
    //Initialize Log4j logs
    public Log(String c){
        // this.classname=c;
        this.classname = " " + c; // 解决时间戳和类名之间的过渡问题
        logger=Logger.getLogger(classname);
    }
    private static void getProperty(){
        PropertyConfigurator.configure(new File(filePath).getAbsolutePath());
        flag=true;
    }
    private static void getFlag(){
        if(flag==false){
            getProperty();
        }

    }
    // This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite
    public static void startTestCase(String sTestCaseName){
        getFlag();
        int stan_length = 60;
        String stan_cont = "------------------------------------------------------------";
        String key_content = sTestCaseName + " Start";
        int content_length = key_content.length();
        StringBuffer buffer = new StringBuffer(stan_cont);

        buffer.replace((stan_length-content_length)/2, (stan_length-content_length)/2+content_length, key_content);

        logger.info(stan_cont);
        logger.info(buffer.toString());
        logger.info(stan_cont);
    }

    //This is to print log for the ending of the test case
    public static void endTestCase(String sTestCaseName){
        getFlag();
        int stan_length = 60;
        String stan_cont = "------------------------------------------------------------";
        String key_content = sTestCaseName + " End";
        int content_length = key_content.length();
        StringBuffer buffer = new StringBuffer(stan_cont);

        buffer.replace((stan_length-content_length)/2, (stan_length-content_length)/2+content_length, key_content);

        logger.info(stan_cont);
        logger.info(buffer.toString());
        logger.info(stan_cont);
    }

    // Need to create these methods, so that they can be called
    public static void info(String message) {
        getFlag();
        logger.info(message);
    }

    public static void warn(String message) {

        getFlag();
        logger.warn(message);
    }

    public static void error(String message) {

        getFlag();
        logger.error(message);
    }

    public static void fatal(String message) {

        getFlag();
        logger.fatal(message);
    }

    public static void debug(String message) {

        getFlag();
        if (logger.isDebugEnabled()==true)
        {
        	System.out.println("@@@@@@ Logger Debug mode is enabled @@@@@@");
        	logger.debug(message);
        }
        else
        {
        	System.out.println("Logger Debug mode is disable!");
        }
    }

}

