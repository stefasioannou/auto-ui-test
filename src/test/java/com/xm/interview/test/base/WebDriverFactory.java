package com.xm.interview.test.base;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.logging.Logger;

public class WebDriverFactory {

    private WebDriverFactory() {}

    /**
     * Retrieves an instance of WebDriver based on the specified browser type.
     *
     * @param browserType The type of browser to be used.
     * @return The WebDriver instance.
     */
    public static WebDriver getDriver(String browserType) {

        final Logger logger = Logger.getLogger(WebDriverFactory.class.getName());
        WebDriver driver = null;

        try {
            switch (browserType.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--default-search-engine=Google");
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--disable-search-engine-choice-screen");
                    System.out.println("Opening the browser: ChromeDriver");
                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    System.out.println("Opening the browser: FirefoxDriver");
                    driver = new FirefoxDriver(firefoxOptions);
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    System.out.println("Opening the browser: EdgeDriver");
                    driver = new EdgeDriver(edgeOptions);
                    break;

                default:
                    logger.severe("Unsupported browser type: " + browserType);
                    throw new IllegalArgumentException("Unsupported browser type: " + browserType);
            }
        } catch (Exception e) {
            logger.severe("Error occurred while initializing the WebDriver: " + e.getMessage());
        }

        return driver;
    }
}
