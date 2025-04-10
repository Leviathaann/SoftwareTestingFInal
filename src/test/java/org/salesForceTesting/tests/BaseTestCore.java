package org.salesForceTesting.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions; // Optional for headless etc.
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;


/* this is the base class for all tests, it contains the setup and teardown methods
   and the WebDriver instance. It is used to initialize the web driver before each test and quit it after each test.
   It also contains a method to get the WebDriver instance for use in the tests.
  * other tests will extend this class
 */
public class BaseTestCore {

        protected WebDriver driver;

        @BeforeMethod
        public void setUp() {
            WebDriverManager.chromedriver().setup();
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }

        @AfterMethod
        public void tearDown() {
            if (driver != null) {
                driver.quit();
            }
        }

        // this method is for the TESTNG listener to get the driver instance
        public WebDriver getDriver() {
            return driver;
        }
    }

