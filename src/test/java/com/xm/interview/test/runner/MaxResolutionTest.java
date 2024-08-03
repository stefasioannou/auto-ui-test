package com.xm.interview.test.runner;

import com.xm.interview.test.project.Config;
import com.xm.interview.test.base.BaseTest;
import org.junit.Test;
import static com.xm.interview.test.pages.CommonPages.*;

import java.util.Map;


/**
 * The MaxResolutionTradingTest class is used to test the trading of stocks functionality on a web page.
 * It extends the BaseTest class which sets up and tears down the test environment.
 */
public class MaxResolutionTest extends BaseTest {
    /**
     * This method is used to test the trading of stocks functionality.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    public void stockTesting() throws Exception {

        Config.browserSize="max";
        driver.manage().window().maximize();
        System.out.println("Browser Resolution set to: max");

        driver.get(Config.websiteURL);


        // Perform actions for max-resolution

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
