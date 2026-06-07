package listeners;

import org.testng.*;
import com.aventstack.extentreports.*;
import reports.ExtentManager;
import utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import base.DriverFactory;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        test.set(extent.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.get().fail(result.getThrowable());

        // ✅ Capture Screenshot
        WebDriver driver = DriverFactory.getDriver();
        String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());

        // ✅ Attach to report
        try {
            test.get().addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}