package org.salesForceTesting.testUtils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.salesForceTesting.tests.BaseTestCore;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Reporter.log("Test Failed: " + result.getName());
        Object testInstance = result.getInstance();
        WebDriver driver = null;
        if (testInstance instanceof BaseTestCore) {
            driver = ((BaseTestCore) testInstance).getDriver();
        }

        if (driver != null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String testMethodName = result.getMethod().getMethodName();
            String screenshotName = testMethodName + "_" + timestamp + ".png";
            String screenshotDir = "test-output" + File.separator + "screenshots";
            String screenshotPath = screenshotDir + File.separator + screenshotName;

            try {

                /*
                 * Create the directory if it doesn't exist already
                 * The screenshots will be saved to /test-output/screenshots 
                 */
                File directory = new File(screenshotDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Try to take screenshot and save it to the screenshot directory
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File(screenshotPath));
                String relativePath = "screenshots" + File.separator + screenshotName;
                Reporter.log("Screenshot captured: <a href='" + relativePath + "'> " + screenshotName + "</a><br>");

            } catch (IOException e) {
                Reporter.log("Failed to capture a screenshot for " + testMethodName + ": " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            Reporter.log("Could not capture a screenshot as the WebDriver instance is equal to null.");
        }
    }


    // reports when test suite starts and finishes, this makes it easier to track the test suite execution
    @Override
    public void onStart(ITestContext context) {
        Reporter.log("Test Suite Started: " + context.getName() + "<br>");
    }

    @Override
    public void onFinish(ITestContext context) {
        Reporter.log("Test Suite Finished: " + context.getName() + "<br>");
    }

    // reports when a test starts, passes or fails
    @Override
    public void onTestStart(ITestResult result) {
        Reporter.log("Test Started: " + result.getName() + "<br>");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Reporter.log("Test Passed: " + result.getName() + "<br>");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Reporter.log("Test Skipped: " + result.getName() + "<br>");
    }
}
