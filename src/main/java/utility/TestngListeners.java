/**
 *
 */
package utility;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * @author lyou009
 */
public class TestngListeners extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        takeScreenShot(tr);
       // log(tr.getName()+ "--Test method failed\n");
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        takeScreenShot(tr);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
    }

    private void takeScreenShot(ITestResult tr) {
    }
/*
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String mDateTime = formatter.format(new Date());
        String fileName = tr.getName() + "-" + mDateTime + ".png";
        WebDriver driver = ((TestCaseTemplate) tr.getInstance()).getDriver();
        String filePath = System.getProperty("user.dir") + "/src/com/uitest/testSuites/screenshots/onFailure/" + REXSH.REXAUTO.Utils.Utils.getCurrentDate();
        File target_file = new File(filePath + File.separator + fileName);
        try {
            File source_file = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source_file, target_file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Reporter.setCurrentTestResult(tr);
        String resultStr = "<a href=\"" + "javascript:toggleElement(\'img-" + mDateTime + "\', \'block\')\"><b>screenshot link</b></a>";
        resultStr += "<img class=\"stackTrace\" id=\'img-" + mDateTime + "\' src=\'" + target_file.getAbsolutePath() + "'/>";
        Reporter.log(resultStr);
    }
*/
}
