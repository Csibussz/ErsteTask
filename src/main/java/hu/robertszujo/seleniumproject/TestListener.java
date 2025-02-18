package hu.robertszujo.seleniumproject;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import hu.robertszujo.seleniumproject.constants.TestContextConstants;
import hu.robertszujo.seleniumproject.utils.WebDriverUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestContext testContext = result.getTestContext();
        WebDriver driver = (WebDriver) testContext.getAttribute(TestContextConstants.DRIVER);
        ExtentTest reporter = (ExtentTest) testContext.getAttribute(TestContextConstants.REPORTER);

        try {
            reporter.info("Screenshot of the last visible screen",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(WebDriverUtils.captureScreenshotAsBase64(driver)).build());
        } catch (Exception ignored) {
            reporter.info("Could not attach screenshot of the last visible screen!");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestContext testContext = result.getTestContext();
        WebDriver driver = (WebDriver) testContext.getAttribute(TestContextConstants.DRIVER);
        ExtentTest reporter = (ExtentTest) testContext.getAttribute(TestContextConstants.REPORTER);

        reporter.fail(result.getThrowable());
        try {
            reporter.info("Screenshot of the last visible screen",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(WebDriverUtils.captureScreenshotAsBase64(driver)).build());
        } catch (Exception ignored) {
            reporter.info("Could not attach screenshot of the last visible screen!");
        }
    }
}
