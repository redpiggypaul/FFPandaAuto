package Component.Operation;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

/**
 * @author lyou009
 *
 */
public class AndroidBaseOperation extends AppBaseOperation {

    //public AndroidBaseOperation(AppiumDriver<?> driver, String path)

    public AndroidBaseOperation(AppiumDriver driver) throws Exception {
        super(driver);
    }

    public AndroidBaseOperation(AppiumDriver driver, String path) throws Exception {
		super(driver, path);
	}


    // public void sendKeyEvent(int keyEvent) {((AndroidDriver<?>)driver).sendKeyEvent(keyEvent);}
    public void sendKeyEvent(int keyEvent) {
        ((AndroidDriver)appdriver).sendKeyEvent(keyEvent);
    }
    
  
}
