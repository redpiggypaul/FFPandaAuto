package Component.Driver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by appledev131 on 4/15/16.
 */
public class ScreenShot {
    public static void ScreenShot(TakesScreenshot drivername, String filename) {
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        String currentPath = null;
        if (osName.contains("Windows")) {
            currentPath = (System.getProperty("user.dir") + "\\logs\\ScreenShot").replaceAll(":", "_").replaceFirst("_", ":"); // get current work
        } else {
            currentPath = (System.getProperty("user.dir") + "\\logs\\ScreenShot").replaceAll("\\\\", File.separator);
        }
        File scrFile = drivername.getScreenshotAs(OutputType.FILE);
        try {
            System.out.println("save snapshot path is:" + currentPath + "/"
                    + filename.replaceAll(":", "_"));
            FileUtils
                    .copyFile(scrFile, new File(currentPath + File.separator + filename.replaceAll(":", "_")));
        } catch (IOException e) {
            System.out.println("REXSH.REXAUTO.Component.Driver.ScreenShot.ScreenShot() Can't save screenshot");
            e.printStackTrace();
        } finally {
            System.out.println("screen shot finished, it's in " + currentPath
                    + " folder");
        }
    }


    public static void ScreenShot(TakesScreenshot drivername, Object fileNamme, Object targetPath) {
        StringBuilder localFileName = new StringBuilder(fileNamme.toString());
        StringBuilder localPath = new StringBuilder(targetPath.toString());
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        String currentPath = null;
        if (osName.contains("Windows")) {
            currentPath = (System.getProperty("user.dir") + "\\logs\\ScreenShot").replaceAll(":", "_").replaceFirst("_", ":"); // get current work
            localPath = new StringBuilder(localPath.toString().replaceAll(":", "_").replaceFirst("_", ":"));
        } else {
            currentPath = (System.getProperty("user.dir") + "\\logs\\ScreenShot").replaceAll("\\\\", File.separator);
            localPath = new StringBuilder(localPath.toString().replaceAll("/", File.separator));
        }
        File scrFile = drivername.getScreenshotAs(OutputType.FILE);
        try {
            System.out.println("save snapshot path is:" + localPath + "/"
                    + localFileName.toString().replaceAll(":", "_"));
            FileUtils
                    .copyFile(scrFile, new File(localPath + File.separator + localFileName.toString().replaceAll(":", "_")));
        } catch (IOException e) {
            System.out.println("REXSH.REXAUTO.Component.Driver.ScreenShot.ScreenShot() 3 parameters Can't save screenshot");
            e.printStackTrace();
        } finally {
            System.out.println("screen shot finished, it's in " + currentPath
                    + " folder");
        }
    }

/*
    public static void ScreenShot(TakesScreenshot drivername, String filename, String tPath) {
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        String currentPath = null;
        if (osName.contains("Windows")) {
            currentPath = (System.getProperty("user.dir") + "\\logs\\ScreenShot").replaceAll(":", "_").replaceFirst("_", ":"); // get current work
            tPath = tPath.replaceAll(":", "_").replaceFirst("_", ":");
        } else {
            currentPath = (System.getProperty("user.dir") + "\\logs\\ScreenShot").replaceAll("\\\\", File.separator);
            tPath = tPath.replaceAll("/", File.separator);
        }
        File scrFile = drivername.getScreenshotAs(OutputType.FILE);
        try {
            System.out.println("save snapshot path is:" + tPath + "/"
                    + filename.replaceAll(":", "_"));
            FileUtils
                    .copyFile(scrFile, new File(tPath + File.separator + filename.replaceAll(":", "_")));
        } catch (IOException e) {
            System.out.println("REXSH.REXAUTO.Component.Driver.ScreenShot.ScreenShot() 3 parameters Can't save screenshot");
            e.printStackTrace();
        } finally {
            System.out.println("screen shot finished, it's in " + currentPath
                    + " folder");
        }
    }

    public static void ScreenShot(TakesScreenshot drivername, StringBuilder filename, StringBuilder tPath) {
        ScreenShot(drivername, filename.toString(), tPath.toString());
    }
    */
}