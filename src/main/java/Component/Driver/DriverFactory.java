package Component.Driver;

import LocalException.FFPandaException;
import LocalException.FFPandaException;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {

	/*public static WebDriver getWebDriver(Properties configValue)
			throws Exception {*/
	public static WebDriver getWebDriver(Properties configValue) throws Exception 
	{
		String osStr = configValue.getProperty("operation.platform");
		WebDriver driver = null;

		try
		{
			if (osStr.equalsIgnoreCase("web"))
			{
				String browser = configValue.getProperty("browser");
				if (browser.equalsIgnoreCase("ie")) {
					driver = getIEDriver(configValue);
				} else if (browser.equalsIgnoreCase("chrome")) {
					driver = getChromeDriver(configValue);
				} else if (browser.equalsIgnoreCase("safari")) {
					driver = getSafariDriver();
				} else {
				driver = getFirefoxDriver();
				}
			}

			else if (osStr.equalsIgnoreCase("IOS"))
			{
				driver = getIOSDriver(configValue);
			}
			else if (osStr.equalsIgnoreCase("android"))
			{
				driver = getAndroidDriver(configValue);
			}
				
//			return driver;	
		}
		catch (FFPandaException myExp){
			   System.out.println("FFPandaException catched!");
			  }
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return driver;

	}
	
	
	

	private static WebDriver getFirefoxDriver() {
//		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		FirefoxProfile profile = new FirefoxProfile();    
	    profile.setEnableNativeEvents(true); 
		return new FirefoxDriver(profile);
	}

	private static WebDriver getChromeDriver(Properties configValue) {
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")
						+ configValue.getProperty("chrome"));
		ChromeDriverService service = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File(System.getProperty("user.dir")
										+ configValue.getProperty("chrome")))
				.usingAnyFreePort().build();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--test-type");
		try {
			service.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ChromeDriver(options);
	}

	private static WebDriver getIEDriver(Properties configValue) {
		System.setProperty("webdriver.ie.driver",
				System.getProperty("user.dir") + configValue.get("IE"));
		DesiredCapabilities capabilities = DesiredCapabilities
				.internetExplorer();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
		capabilities
				.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);

		return new InternetExplorerDriver(capabilities);
	}

	private static WebDriver getSafariDriver() {
		DesiredCapabilities capabilities = DesiredCapabilities.safari();
        //TODO
		/* add proxy ...*/
		return new SafariDriver(capabilities);
	}

	@SuppressWarnings("rawtypes")
	private static IOSDriver getIOSDriver(Properties configValue)
			throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformVersion",
				configValue.get("ios.platformVersion"));
		capabilities.setCapability("platformName",
				configValue.get("test.platformName"));
		capabilities.setCapability("deviceName",
				configValue.get("ios.deviceName"));
		capabilities.setCapability("udid", configValue.get("ios.udid"));
		capabilities.setCapability("BundleID", configValue.get("ios.BundleID"));

		if (String.valueOf(configValue.get("ios.apptype")).equals("web")) {
			capabilities.setCapability("browserName",
					configValue.get("ios.browser"));
		} else {
			capabilities.setCapability("app",
					new File(String.valueOf(configValue.get("ios.app.path"))));
		}

		return new IOSDriver(new URL(String.valueOf(configValue
				.get("ios.server.url"))), capabilities);
	}

	@SuppressWarnings("rawtypes")
	private static AndroidDriver getAndroidDriver(Properties configValue)
			throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformVersion",
				configValue.get("android.platformVersion"));
		capabilities.setCapability("platformName",
				configValue.get("test.platformName"));
		capabilities.setCapability("deviceName",
				configValue.get("android.deviceName"));

		if (String.valueOf(configValue.get("android.apptype")).equals("web")) {
			capabilities.setCapability("appPackage",
					configValue.get("android.appPackage"));
			capabilities.setCapability("appActivity",
					configValue.get("android.appActivity"));
			capabilities.setCapability("app",
					new File(String.valueOf(configValue.get("android.app"))));
		} else {
			capabilities.setCapability("browserName",
					configValue.get("android.browser"));
		}

		return new AndroidDriver(new URL(String.valueOf(configValue
				.get("android.server.url"))), capabilities);
	}

}
