package Component.PageLoadWait;

/**
 * Created by appledev131 on 4/11/16.
 * <p>
 * Created by appledev131 on 4/11/16.
 * <p>
 * Created by appledev131 on 4/11/16.
 */

/**
 * Created by appledev131 on 4/11/16.
 */


import dataModel.TestPlatformData;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.SystemClock;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static Component.Driver.ScreenShot.ScreenShot;

/**
 * A specialization of {@link FluentWait} that uses WebDriver instances.
 */
public class IOSDriverWait extends FluentWait<IOSDriver> {
    public final static long DEFAULT_SLEEP_TIMEOUT = 200;
    private final WebDriver driver;
    String autoOSType = TestPlatformData.getAutoOSType().toString();
    private String time = null;
    private String path4Log = (System.getProperty("user.dir") + "/logs/ScreenShot").replaceAll("/", File.separator);
    private boolean flag4Screen = true;

    /**
     * Wait will ignore instances of NotFoundException that are encountered (thrown) by default in
     * the 'until' condition, and immediately propagate all others.  You can add more to the ignore
     * list by calling ignoring(exceptions to add).
     *
     * @param driver           The WebDriver instance to pass to the expected conditions
     * @param timeOutInSeconds The timeout in seconds when an expectation is called
     * @see IOSDriverWait#ignoring(java.lang.Class)
     */
    public IOSDriverWait(IOSDriver driver, long timeOutInSeconds) throws Exception {
        this(driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT);
    }

    public IOSDriverWait(IOSDriver driver, long timeOutInSeconds, String p4L) throws Exception {
        this(driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT, p4L.toLowerCase());
    }

    public IOSDriverWait(IOSDriver driver, long timeOutInSeconds, Object p4L) throws Exception {
        this(driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT, p4L.toString().toLowerCase());
    }

    public IOSDriverWait(IOSDriver driver, long timeOutInSeconds, String p4L, boolean theFlag) throws Exception {
        this(driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT, p4L.toLowerCase(), theFlag);
    }


    public IOSDriverWait(IOSDriver driver, long timeOutInSeconds, StringBuilder p4L, boolean theFlag) throws Exception {
        this(driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT, p4L.toString().toLowerCase(), theFlag);
    }

    /**
     * Wait will ignore instances of NotFoundException that are encountered (thrown) by default in
     * the 'until' condition, and immediately propagate all others.  You can add more to the ignore
     * list by calling ignoring(exceptions to add).
     *
     * @param driver           The WebDriver instance to pass to the expected conditions
     * @param timeOutInSeconds The timeout in seconds when an expectation is called
     * @param sleepInMillis    The duration in milliseconds to sleep between polls.
     * @see IOSDriverWait#ignoring(java.lang.Class)
     */
    public IOSDriverWait(IOSDriver driver, long timeOutInSeconds, long sleepInMillis) throws Exception {
        this(driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, sleepInMillis);
    }

    /**
     * @param driver           The WebDriver instance to pass to the expected conditions
     * @param clock            The clock to use when measuring the timeout
     * @param sleeper          Object used to make the current thread go to sleep.
     * @param timeOutInSeconds The timeout in seconds when an expectation is
     * @param sleepTimeOut     The timeout used whilst sleeping. Defaults to 500ms called.
     */
    public IOSDriverWait(IOSDriver driver, Clock clock, Sleeper sleeper, long timeOutInSeconds,
                         long sleepTimeOut) throws Exception {
        super(driver, clock, sleeper);
        withTimeout(timeOutInSeconds, TimeUnit.SECONDS);
        pollingEvery(sleepTimeOut, TimeUnit.MILLISECONDS);
        ignoring(NotFoundException.class);
        this.driver = driver;

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.time = format.format(date);
    }

    public IOSDriverWait(IOSDriver driver, Clock clock, Sleeper sleeper, long timeOutInSeconds,
                         long sleepTimeOut, String p4L) throws Exception {
        super(driver, clock, sleeper);
        withTimeout(timeOutInSeconds, TimeUnit.SECONDS);
        pollingEvery(sleepTimeOut, TimeUnit.MILLISECONDS);
        ignoring(NotFoundException.class);
        this.driver = driver;

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.time = format.format(date);
        this.path4Log = p4L;
        if (!autoOSType.toLowerCase().contains("windows")) {
            this.path4Log = new String(path4Log.replaceAll("\\\\", File.separator));
        }
    }

    public IOSDriverWait(IOSDriver driver, Clock clock, Sleeper sleeper, long timeOutInSeconds,
                         long sleepTimeOut, String p4L, boolean theFlag) throws Exception {
        super(driver, clock, sleeper);
        RemoteWebDriver remote = (RemoteWebDriver) driver;
        withTimeout(timeOutInSeconds, TimeUnit.SECONDS);
        pollingEvery(sleepTimeOut, TimeUnit.MILLISECONDS);
        ignoring(NotFoundException.class);
        this.driver = driver;
        this.flag4Screen = theFlag;

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.time = format.format(date);
        this.path4Log = p4L;
        if (this.flag4Screen == false) {
        } else {
            if (!autoOSType.toLowerCase().contains("windows")) {
                this.path4Log = new String(path4Log.replaceAll("\\\\", File.separator));
            }
            ScreenShot(remote, "TimeOut : " + time + ".png", this.path4Log);
        }
    }

    public IOSDriverWait(IOSDriver driver, Clock clock, Sleeper sleeper, long timeOutInSeconds,
                         long sleepTimeOut, StringBuilder p4L, boolean theFlag) throws Exception {
        super(driver, clock, sleeper);
        RemoteWebDriver remote = (RemoteWebDriver) driver;
        withTimeout(timeOutInSeconds, TimeUnit.SECONDS);
        pollingEvery(sleepTimeOut, TimeUnit.MILLISECONDS);
        ignoring(NotFoundException.class);
        this.driver = driver;
        this.flag4Screen = theFlag;

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.time = format.format(date);
        this.path4Log = p4L.toString();
        if (this.flag4Screen == false) {
        } else {
            if (!autoOSType.toLowerCase().contains("windows")) {
                this.path4Log = new String(path4Log.replaceAll("\\\\", File.separator));
            }
            ScreenShot(remote, "TimeOut : " + time + ".png", this.path4Log);
        }
    }

    @Override
    protected RuntimeException timeoutException(String message, Throwable lastException) {
        TimeoutException ex = new TimeoutException(message, lastException);
        ex.addInfo(WebDriverException.DRIVER_INFO, driver.getClass().getName());
        if (driver instanceof RemoteWebDriver) {
            RemoteWebDriver remote = (RemoteWebDriver) driver;
            if (remote.getSessionId() != null) {
                ex.addInfo(WebDriverException.SESSION_ID, remote.getSessionId().toString());
            }
            if (remote.getCapabilities() != null) {
                ex.addInfo("Capabilities", remote.getCapabilities().toString());
            }
            if (this.flag4Screen == false) {
            } else {
                ScreenShot(remote, "TimeOut : " + time + ".png", this.path4Log);
            }
        }
        throw ex;
    }

    protected RuntimeException timeoutException(String message, Throwable lastException, boolean flag4noscreen) {
        TimeoutException ex = new TimeoutException(message, lastException);
        ex.addInfo(WebDriverException.DRIVER_INFO, driver.getClass().getName());
        if (driver instanceof RemoteWebDriver) {
            RemoteWebDriver remote = (RemoteWebDriver) driver;
            if (remote.getSessionId() != null) {
                ex.addInfo(WebDriverException.SESSION_ID, remote.getSessionId().toString());
            }
            if (remote.getCapabilities() != null) {
                ex.addInfo("Capabilities", remote.getCapabilities().toString());
            }
        }
        throw ex;
    }

    protected RuntimeException timeoutExceptionNOScreen(String message, Throwable lastException) {
        TimeoutException ex = new TimeoutException(message, lastException);
        ex.addInfo(WebDriverException.DRIVER_INFO, driver.getClass().getName());
        if (driver instanceof RemoteWebDriver) {
            RemoteWebDriver remote = (RemoteWebDriver) driver;
            if (remote.getSessionId() != null) {
                ex.addInfo(WebDriverException.SESSION_ID, remote.getSessionId().toString());
            }
            if (remote.getCapabilities() != null) {
                ex.addInfo("Capabilities", remote.getCapabilities().toString());
            }
        }
        throw ex;
    }
}
