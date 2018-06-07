package EnvConfig;

import org.apache.log4j.*;
import utility.StringB;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger4Framework {
    private static Logger logger = Logger.getLogger(Logger4Framework.class);

    public static Logger getLocalLog(Class<?> clazz, StringBuilder LogPath4TC) {
        Logger logger = Logger.getLogger(clazz);  // 生成新的Logger
        logger.removeAllAppenders(); // 清空Appender，特別是不想使用現存實例時一定要初期化
        logger.setLevel(Level.DEBUG); // 设定Logger級別。
        logger.setAdditivity(true); // 设定是否继承父Logger。默认为true，继承root输出；设定false后将不出书root。

        FileAppender appender = new RollingFileAppender(); // 生成新的Appender
        PatternLayout layout = new PatternLayout();
        layout.setConversionPattern("[%d{yyyy-MM-dd HH:mm:ss}] %p %l : %m%n"); // log的输出形式
        appender.setLayout(layout);

        appender.setFile(LogPath4TC + getTime("MM-dd HH-mm-ss") + ".log");  // log输出路径
        appender.setEncoding("UTF-8"); // log的字符编码
        appender.setAppend(true);  //日志合并方式： true:在已存在log文件后面追加 false:新log覆盖以前的log
        appender.activateOptions();  // 适用当前配置

        logger.addAppender(appender); // 将新的Appender加到Logger中
        return logger;
    }

    private static String getTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    public static void main(String[] args) {
        StringBuilder testcaseName4path = new StringBuilder(Thread.currentThread().getStackTrace()[1].getFileName().replace(".java", ""));
        Logger logger4test = Logger4Framework.getLocalLog(Logger4Framework.class,
                new StringBuilder("E:\\log4jDemo\\" + testcaseName4path + "\\"));
        logger4test.debug("try");
    }

}
