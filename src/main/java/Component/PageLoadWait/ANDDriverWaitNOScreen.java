package Component.PageLoadWait;

/**
 * Created by appledev131 on 4/11/16.
 */


import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.SystemClock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static Component.Driver.ScreenShot.ScreenShot;

/**
 * A specialization of {@link FluentWait} that uses WebDriver instances.
 */
public class ANDDriverWaitNOScreen extends FluentWait<AndroidDriver> {
    public final static long DEFAULT_SLEEP_TIMEOUT = 500;
    private final WebDriver driver;
    //  private ScreenShot(driver, "Loging_done.png");
    private String time = null;

    //long t_end = 0; //System.currentTimeMillis();

    /**
     * Wait will ignore instances of NotFoundException that are encountered (thrown) by default in
     * the 'until' condition, and immediately propagate all others.  You can add more to the ignore
     * list by calling ignoring(exceptions to add).
     *
     * @param driver           The WebDriver instance to pass to the expected conditions
     * @param timeOutInSeconds The timeout in seconds when an expectation is called
     * @see ANDDriverWaitNOScreen#ignoring(Class)
     */
    public ANDDriverWaitNOScreen(AndroidDriver driver, long timeOutInSeconds) throws Exception{
        this(driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT);
    }

    /**
     * Wait will ignore instances of NotFoundException that are encountered (thrown) by default in
     * the 'until' condition, and immediately propagate all others.  You can add more to the ignore
     * list by calling ignoring(exceptions to add).
     *
     * @param driver           The WebDriver instance to pass to the expected conditions
     * @param timeOutInSeconds The timeout in seconds when an expectation is called
     * @param sleepInMillis    The duration in milliseconds to sleep between polls.
     * @see ANDDriverWaitNOScreen#ignoring(Class)
     */
    public ANDDriverWaitNOScreen(AndroidDriver driver, long timeOutInSeconds, long sleepInMillis) throws Exception{
        this(driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, sleepInMillis);
    }

    /**
     * @param driver           The WebDriver instance to pass to the expected conditions
     * @param clock            The clock to use when measuring the timeout
     * @param sleeper          Object used to make the current thread go to sleep.
     * @param timeOutInSeconds The timeout in seconds when an expectation is
     * @param sleepTimeOut     The timeout used whilst sleeping. Defaults to 500ms called.
     */
    public ANDDriverWaitNOScreen(AndroidDriver driver, Clock clock, Sleeper sleeper, long timeOutInSeconds,
                                 long sleepTimeOut) throws Exception{
        super(driver, clock, sleeper);
        withTimeout(timeOutInSeconds, TimeUnit.SECONDS);
        pollingEvery(sleepTimeOut, TimeUnit.MILLISECONDS);
        ignoring(NotFoundException.class);
        this.driver = driver;

        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.time=format.format(date);
    }

    @Override
    protected RuntimeException timeoutException(String message, Throwable lastException) {
        // long t_end = System.currentTimeMillis();
        // Calendar c=Calendar.getInstance();
        // c.setTimeInMillis(t_end-t_start);
        // TRunnerLog.info("耗时: " + c.get(Calendar.MINUTE) + "分 "+ c.get(Calendar.SECOND) + "秒 " + c.get(Calendar.MILLISECOND) + " 微秒");
        // TRunnerLog.endTestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
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
