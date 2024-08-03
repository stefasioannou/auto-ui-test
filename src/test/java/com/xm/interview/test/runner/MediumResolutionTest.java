package com.xm.interview.test.runner;

import com.xm.interview.test.project.Config;
import com.xm.interview.test.base.BaseTest;
import org.junit.Test;
import static com.xm.interview.test.pages.CommonPages.*;

import java.util.Map;


/**
 * The MediumResolutionTradingTest class is a JUnit test class that tests the trading functionality on a web page.
 * It extends the BaseTest class and utilizes the WebDriver instance to interact with the web page.
 */
public class MediumResolutionTest extends BaseTest {
    /**
     * This method is a JUnit test method that tests the trading functionality on a web page.
     * It performs a series of actions to validate the trading functionality and extract trading conditions for a given stock.
     *
     * @throws InterruptedException If the thread is interrupted while waiting for an element to load or to be clickable.
     */
    @Test
    public void stockTesting() throws InterruptedException {

            driver.manage().window().setSize(new org.openqa.selenium.Dimension(1024, 768));
            Config.browserSize = "1024,768";
            System.out.println("Browser Resolution set to: 1024,768");

            driver.get(Config.websiteURL);

        //Handle cookies popup
        handleCookiesPopup(driver);

        //Click on Trading link
        clickTradingLink(driver);

        //Check if Trading tab is opened
        checkTradingTabOpened(driver);

        //Click on Stocks tab
        clickTabAndVerify(driver, "stocks");

        //Apply country filter
        applyFilter(driver, Config.country);

        //Search for stock data
        Config.entries = findStockData(driver, Config.symbolName);

        //Count the number of entries
        entriesCount(driver);

        //Click on Read More link
        navigateToReadMore(driver, Config.symbolName);

        //Scroll down
        scrollDown(driver);

        //Extract trading conditions
        Map<String, String> tradingData = extractTradingConditions(driver);

        //Compare data
        compareData(tradingData);
        }

    }