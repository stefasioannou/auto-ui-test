package com.xm.interview.test.base;

import com.xm.interview.test.project.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.logging.Logger;

public class WebDriverFactory {

    private WebDriverFactory(){}

    /**
     * Retrieves an instance of WebDriver.
     *
     * @return The WebDriver instance.
     */
    public static WebDriver getDriver() {
        final Logger logger = Logger.getLogger(WebDriverFactory.class.getName());
        WebDriver driver = null;
        ChromeOptions options = new ChromeOptions();

        try {
            WebDriverManager.chromedriver().setup();

            options.addArguments("--default-search-engine=Google");
            options.addArguments("--incognito");
            options.addArguments("--disable-search-engine-choice-screen");

            System.out.println("Opening the browser : ChromeDriver");
            System.out.println("Browser path: " + Config.chromeDriverPath);
            driver = new ChromeDriver(options);
        } catch (Exception e) {
            logger.severe("Error occurred while initializing the WebDriver: " + e.getMessage());
        }

        return driver;
    }
}
