package com.xm.interview.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * The MobileResolutionPages class contains methods to interact with specific elements in the mobile view of a web page.
 */
public class MobileResolutionPages {
    private static final int TIMEOUT_IN_SECONDS = 10;


    private MobileResolutionPages() {

    }

    /**
     * Clicks on a specific tab in the mobile view of a web page.
     *
     * @param driver The WebDriver instance used to navigate the web page.
     * @param tabName The name of the tab to click on.
     */
    public static void clickTabMobile(WebDriver driver, String tabName) {
        WebElement tradingTab = CommonPages.waitUntilElementIsClickable(driver, By.xpath(".//*[@class='navbar-nav__toggleArrow'][contains(@aria-controls,'"+tabName+"')]"), TIMEOUT_IN_SECONDS);
        System.out.println("Trading tab found and clickable");
        CommonPages.clickElement(driver, tradingTab);
        System.out.println("---------------------------------------------------");
    }

    /**
     * Clicks on the hamburger menu.
     *
     * @param driver the WebDriver instance used to perform the action
     */
    public static void clickHamburgerMenu(WebDriver driver) {
        WebElement hamburgerMenu = CommonPages.waitUntilElementIsClickable(driver, By.xpath(".//*[@class='toggleLeftNav']"), TIMEOUT_IN_SECONDS);
        System.out.println("Hamburger menu found and clickable");
        System.out.println("---------------------------------------------------");
        CommonPages.clickElement(driver, hamburgerMenu);
    }

    /**
     * Clicks on the mobile tab option specified by the given tabName.
     *
     * @param driver   the WebDriver instance
     * @param tabName  the name of the tab to be clicked
     */
    public static void clickMobileTabOption(WebDriver driver, String tabName) {
        WebElement stocksTab = CommonPages.waitUntilElementIsClickable(driver, By.xpath(".//*[@id='tradingMenu']//a[contains(@href,'"+tabName+"')]"), TIMEOUT_IN_SECONDS);
        System.out.println("Stocks tab found and clickable");
        System.out.println("---------------------------------------------------");
        CommonPages.clickElement(driver, stocksTab);
    }

}