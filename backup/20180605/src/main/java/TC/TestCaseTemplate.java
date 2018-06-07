package TC;


import REXSH.REXAUTO.Component.Driver.DriverFactory;
import REXSH.REXAUTO.Utils.PropUtils;
import REXSH.REXAUTO.Utils.Utils;
import Utility.Log;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.apache.log4j.Logger;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
/**
 * @author lyou009 modify: pzhu023
 *
 */
public class TestCaseTemplate {
	//protected Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	//private static final String CONFIG_FILE = System.getProperty("user.dir") + "/src/com/uitest/resources/config.properties";
	
	protected Log logger = new Log(TestCaseTemplate.class.getName());
	private static final String CONFIG_FILE = System.getProperty("user.dir") + "/src/REXenvConfig/config.properties";
	// user.dir --> C:\Paul\APPT4AND\REXAUTO
		
	protected Properties configvalue = PropUtils.getProperties(CONFIG_FILE);
	protected String screenshotPath ="/src/com/uitest/testSuites/screenshots/"+configvalue.getProperty("operation.platform")+ "/" + Utils.getCurrentDate();

	protected WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	@BeforeTest
	public void setUp() throws Exception {
		driver = DriverFactory.getWebDriver(configvalue);
		
	//	System.out.println("~~~" + TestCaseTemplate.class.getName() + configvalue.getProperty("operation.platform") + "~~~");

		logger.error("~~~" + TestCaseTemplate.class.getName() + configvalue.getProperty("operation.platform") + "~~~");
		
		System.out.println(configvalue.getProperty("operation.platform"));
		
		if (configvalue.getProperty("operation.platform").equalsIgnoreCase("web")) {
			driver.get(configvalue.getProperty("web.url"));
			Utils.sleep(3000);
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(Long.parseLong(configvalue.getProperty("pageLoadTimeout")),
					TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(Long.parseLong(configvalue.getProperty("waitTimeout")),
					TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(Long.parseLong(configvalue.getProperty("scriptTimeout")),
					TimeUnit.SECONDS);
		}
	}

	@AfterTest(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.quit();
	}
}



