package com.xm.interview.test.runner;

import com.xm.interview.test.project.Config;
import com.xm.interview.test.base.BaseTest;
import org.junit.Test;
import static com.xm.interview.test.pages.CommonPages.*;
import static com.xm.interview.test.pages.MobileResolutionPages.*;

import java.util.Map;



/**
 * The SmallResolutionTradingTest class is a test case for trading stocks on a web page with small resolution.
 * It extends the BaseTest class and performs various actions on the web page.
 */
public class SmallResolutionTest extends BaseTest {
    /**
     * This method is a test case for trading stocks on a web page with small resolution.
     * It performs a series of actions on the web page to test the trading functionality.
     * @throws InterruptedException if the thread is interrupted while sleeping.
     */
    @Test
    public void stockTesting() throws InterruptedException {

            driver.manage().window().setSize(new org.openqa.selenium.Dimension(800, 600));
            Config.browserSize = "800,600";
            System.out.println("Browser Resolution set to: 800,600");
            driver.get(Config.websiteURL);

            // Perform actions for lower-resolution

            //Handle cookies popup
            handleCookiesPopup(driver);

            //Click on Hamburger Menu
            clickHamburgerMenu(driver);

            //Click tab on mobile
            clickTabMobile(driver, "trading");

            //Click on Tab option
            clickMobileTabOption(driver, "stocks");

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