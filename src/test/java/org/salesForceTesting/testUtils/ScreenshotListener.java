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
    public void onTestStart(ITestResult result) {
        // Can add logging here if needed
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Can add logging here if needed
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Reporter.log("Test Failed: " + result.getName()); // Log failure
        // Get the WebDriver instance from the BaseTest
        Object testInstance = result.getInstance();
        WebDriver driver = null;
        if (testInstance instanceof BaseTestCore) {
            driver = ((BaseTestCore) testInstance).getDriver();
        }

        if (driver != null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String testMethodName = result.getMethod().getMethodName();
            String screenshotName = testMethodName + "_" + timestamp + ".png";
            // Define path relative to project root -> test-output/screenshots
            String screenshotDir = "test-output" + File.separator + "screenshots";
            String screenshotPath = screenshotDir + File.separator + screenshotName;

            try {
                // Ensure directory exists
                File directory = new File(screenshotDir);
                if (!directory.exists()) {
                    directory.mkdirs(); // Create parent directories if needed
                }

                // Take screenshot
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                // Copy screenshot to desired location
                FileUtils.copyFile(scrFile, new File(screenshotPath));

                // Log the path to the TestNG report (with a clickable link)
                // Relative path from the report (index.html is in test-output)
                String relativePath = "screenshots" + File.separator + screenshotName;
                Reporter.log("Screenshot captured: <a href='" + relativePath + "'> " + screenshotName + "</a><br>");

            } catch (IOException e) {
                Reporter.log("Failed to capture screenshot for " + testMethodName + ": " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            Reporter.log("Could not capture screenshot: WebDriver instance was null.");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Can add logging here if needed
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Usually ignored, but can implement if needed
    }

    @Override
    public void onStart(ITestContext context) {
        // Called before test suite starts
        Reporter.log("Test Suite Started: " + context.getName() + "<br>");
    }

    @Override
    public void onFinish(ITestContext context) {
        // Called after test suite finishes
        Reporter.log("Test Suite Finished: " + context.getName() + "<br>");
    }
}
