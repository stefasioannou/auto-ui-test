package com.xm.interview.test.base;

import com.xm.interview.test.project.Config;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.logging.Logger;

public class BaseTest {
    protected static WebDriver driver;
    private static final Logger logger = Logger.getLogger(BaseTest.class.getName());

    /**
     * Sets up the test environment by initializing the WebDriver instance.
     *
     * @throws RuntimeException if failed to initialize the WebDriver
     */
    @BeforeClass
    public static void setUp() {
        String browserDriver = System.getProperty("Browser", "chrome");
        driver = WebDriverFactory.getDriver(browserDriver);
        if (driver == null) {
            logger.severe("Failed to initialize the WebDriver!");
            throw new RuntimeException("Failed to initialize the WebDriver!");
        }
        Config.entriesSize = 0;
    }

    /**
     * Cleans up the test environment by quitting the WebDriver instance.
     */
    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
