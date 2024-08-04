package com.xm.interview.test.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xm.interview.test.project.Config;
import org.testng.Assert;

/**
 * Class representing common utility methods for working with web pages.
 */
public class CommonPages {
    private static final int TIMEOUT_IN_SECONDS = 10;

    private CommonPages() {
    }

    /**
     * Waits until the element located by the given locator is clickable within the specified timeout.
     *
     * @param driver             The WebDriver instance.
     * @param locator            The locator used to find the element.
     * @param timeoutInSeconds   The maximum time to wait for the element to become clickable, in seconds.
     * @return The WebElement that became clickable.
     */
    public static WebElement waitUntilElementIsClickable(WebDriver driver, By locator, int timeoutInSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits until the element located by the given locator is visible within the specified timeout.
     *
     * @param driver             The WebDriver instance.
     * @param locator            The locator used to find the element.
     * @param timeoutInSeconds   The maximum time to wait for the element to become visible, in seconds.
     * @return The WebElement that became visible.
     */
    public static WebElement waitUntilElementIsVisible(WebDriver driver, By locator, int timeoutInSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits until the elements located by the given locator are clickable within the specified timeout.
     *
     * @param driver            The WebDriver instance.
     * @param locator           The locator used to find the elements.
     * @return A list of WebElements that became clickable.
     */
    private static List<WebElement> waitUntilElementsAreClickable(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_IN_SECONDS));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Scrolls down the page by a given amount.
     *
     * @param driver The WebDriver instance.
     */
    public static void scrollDown(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1000)");
        System.out.println("Scrolled down the page");
        System.out.println("---------------------------------------------------");
    }

    /**
     * Scrolls the given element into the middle of the screen and clicks on it.
     *
     * @param driver  The WebDriver instance.
     * @param element The WebElement to click on.
     * @throws Exception If an error occurs while clicking the element.
     */
    public static void clickElement(WebDriver driver, WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);" +
                            "var elementTop = arguments[0].getBoundingClientRect().top;" +
                            "window.scrollBy(0, elementTop - (viewPortHeight / 2));",
                    element
            );

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_IN_SECONDS));
            wait.until(ExpectedConditions.elementToBeClickable(element));

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            System.out.println("Error clicking the element: " + e.getMessage());
            throw e;
        }
    }


    /**
     * Handles the cookies popup on a web page.
     *
     * @param driver The WebDriver instance used to interact with the web page.
     */
    public static void handleCookiesPopup(WebDriver driver) {
        try {
            WebElement cookiesPopup = waitUntilElementIsVisible(driver, By.xpath(".//*[contains(@class,'acceptDefaultCookieFirstVisit')]"), TIMEOUT_IN_SECONDS);
            clickElement(driver, cookiesPopup);
            System.out.println("Accepted cookies popup");
        } catch (TimeoutException e) {
            System.out.println("Cookies popup did not appear");
        }
        System.out.println("---------------------------------------------------");
    }

    /**
     * Clicks on the trading link.
     *
     * @param driver The WebDriver instance used to interact with the web page.
     */
    public static void clickTradingLink(WebDriver driver) {
        WebElement tradingLink = waitUntilElementIsClickable(driver, By.xpath(".//*[@class='main_nav_trading']"), TIMEOUT_IN_SECONDS);
        System.out.println("Trading link found and clickable");
        clickElement(driver, tradingLink);
        waitUntilPageLoadComplete(driver);
        System.out.println("---------------------------------------------------");
    }

    /**
     * Checks if the trading tab is successfully opened and selected.
     *
     * @param driver The WebDriver instance.
     */
    public static void checkTradingTabOpened(WebDriver driver) {
        WebElement tradingTabOpened = waitUntilElementIsVisible(driver, By.xpath(".//*[@class='main_nav_trading selected']"), TIMEOUT_IN_SECONDS);
        if (tradingTabOpened != null) {
            System.out.println("Trading tab is successfully opened and selected");
        } else {
            throw new AssertionError("Trading tab did not open correctly");
        }
        System.out.println("---------------------------------------------------");
    }

    /**
     * Clicks on a tab identified by its name and verifies the page loaded successfully.
     *
     * @param driver The WebDriver instance.
     * @param tabName The name of the tab to click on.
     */
    public static void clickTabAndVerify(WebDriver driver, String tabName) {
        WebElement tabLink = waitUntilElementIsClickable(driver, By.xpath(".//li[contains(@class,'" + tabName + "')]/a"), TIMEOUT_IN_SECONDS);
        System.out.println(tabName + " link found and clickable");
        waitUntilPageLoadComplete(driver);
        String expectedUrl = tabLink.getAttribute("href");
        String expectedTitle = tabName.substring(0, 1).toUpperCase() + tabName.substring(1).replace("-", " ");
        clickElement(driver, tabLink);
        verifyPageLoaded(driver, expectedUrl, expectedTitle);
        System.out.println("---------------------------------------------------");
    }

    /**
     * Applies a filter on a web page based on the specified country.
     *
     * @param driver The WebDriver instance.
     * @param country The country to filter for.
     */
    public static void applyFilter(WebDriver driver, String country) {
        WebElement norwayFilter = waitUntilElementIsVisible(driver, By.xpath(".//*[@type='button'][@data-value='" + country + "']"), TIMEOUT_IN_SECONDS);
        System.out.println("Norway filter found and clickable");
        clickElement(driver, norwayFilter);
        waitUntilPageLoadComplete(driver);
        By activeNorwayFilterLocator = By.xpath(".//*[@type='button'][contains(@class,'active')][@data-value='" + country + "']");
        WebElement activeNorwayFilter = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_IN_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(activeNorwayFilterLocator));
        if (activeNorwayFilter.isDisplayed()) {
            System.out.println("Norway filteris now active");
        } else
            System.out.println("Norway filterdid not activate successfully");

        System.out.println("--------------------------------------------------");
    }

    /**
     * Searches for stock data by entering the stock name in a search field and pressing Enter.
     *
     * @param driver The WebDriver instance used to interact with the web page.
     * @param stockName The name of the stock to search for.
     */
    public static void searchForStockData(WebDriver driver, String stockName) {
        WebElement searchField = waitUntilElementIsVisible(driver, By.xpath(".//*[@id='DataTables_Table_0_filter']//*[@type='search']"), TIMEOUT_IN_SECONDS);
        System.out.println("Search field found and clickable");
        searchField.sendKeys(stockName);
        searchField.sendKeys(Keys.ENTER);
        waitUntilPageLoadComplete(driver);
        System.out.println("---------------------------------------------------");
    }

    /**
     * Expands all tabs on a mobile device.
     *
     * @param driver The WebDriver instance representing the browser session.
     */
    public static void expandAllTabsOnMobile(WebDriver driver) {
        WebElement rowCell = driver.findElement(By.xpath("//*[@id='DataTables_Table_0']/tbody/tr/td"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String beforeContent = (String) js.executeScript(
                "var element = arguments[0];" +
                        "var style = window.getComputedStyle(element, '::before');" +
                        "return style.getPropertyValue('content');",
                rowCell
        );
        if (beforeContent != null && !beforeContent.isEmpty() && !beforeContent.equals("none")) {
            List<WebElement> expandTabs = waitUntilElementsAreClickable(driver, By.xpath(".//*[@data-xm-qa-name='symbolWithDescription']"));
            for (WebElement expandTab : expandTabs) {
                clickElement(driver, expandTab);
                waitUntilPageLoadComplete(driver);
            }
        }
    }

    /**
     * Extracts the table headers from a web page table.
     *
     * @param driver The WebDriver instance.
     * @return A list of strings representing the table headers.
     */
    public static List<String> extractTableHeaders(WebDriver driver) {
        List<String> headers = new ArrayList<>();
        WebElement headerRow = driver.findElement(By.xpath(".//*[@id='DataTables_Table_0']/thead/tr"));
        List<WebElement> headerCells = headerRow.findElements(By.tagName("th"));
        for (WebElement cell : headerCells) {
            headers.add(cell.getText());
        }

        return headers;
    }

    /**
     * Retrieves all the entries from a web page table, including data from multiple pages.
     *
     * @param driver The WebDriver instance.
     * @return A list of maps representing the table data, where each map contains the row data.
     */
    public static List<Map<String, String>> getAllEntries(WebDriver driver) {
        List<Map<String, String>> allEntries = new ArrayList<>();
        List<String> headers = extractTableHeaders(driver);

        do {
            allEntries.addAll(extractTableData(driver, headers));
        } while (navigateToNextPage(driver));

        Config.entriesSize = allEntries.size();
        System.out.println("allEntries: " + allEntries.size());
        System.out.println("---------------------------------------------------");
        return allEntries;
    }

    /**
     * Navigates to the next page.
     *
     * @param driver The WebDriver instance.
     * @return True if successfully navigated to the next page, false otherwise.
     */
    public static boolean navigateToNextPage(WebDriver driver) {
        try {
            WebElement nextButton = driver.findElement(By.xpath(".//*[@id='DataTables_Table_0_wrapper']//*[@class='paginate_button next']"));
            if (nextButton.getAttribute("class").contains("disabled")) {
                return false;
            }
            clickElement(driver, nextButton);
            Thread.sleep(2000);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the data of a table on a web page and returns it as a list of maps.
     *
     * @param driver  The WebDriver instance.
     * @param headers A list of strings representing the table headers.
     * @return A list of maps representing the table data, where each map contains the row data.
     */
    public static List<Map<String, String>> extractTableData(WebDriver driver, List<String> headers) {
        // Expand all tabs if table rows are wrapped due to limited width space
        expandAllTabsOnMobile(driver);
        List<Map<String, String>> tableData = new ArrayList<>();
        List<WebElement> rows = driver.findElements(By.xpath(".//*[@id='DataTables_Table_0']/tbody/tr"));

        for (WebElement row : rows) {
            if (row.getAttribute("class").contains("parent") || row.getAttribute("class").contains("odd") || row.getAttribute("class").contains("even")) {
                Map<String, String> rowData = extractRowData(row, headers);
                tableData.add(rowData);
            }
        }
        return tableData;
    }

    /**
     * Extracts the data of a row in a web page table and returns it as a map.
     *
     * @param row     The WebElement representing the row to extract the data from.
     * @param headers A list of strings representing the table headers.
     * @return A map representing the row data, where each header is a key and the corresponding cell value is the value.
     */
    public static Map<String, String> extractRowData(WebElement row, List<String> headers) {
        Map<String, String> rowData = new LinkedHashMap<>();
        List<WebElement> cells = row.findElements(By.tagName("td"));

        for (int i = 0; i < cells.size(); i++) {
            String cellText = cells.get(i).getText().trim();
            String header = headers.get(i).trim();
            rowData.put(header, cellText);

            List<WebElement> links = cells.get(i).findElements(By.tagName("a"));
            if (!links.isEmpty()) {
                WebElement link = links.get(0);
                String href = link.getAttribute("href").trim();
                rowData.put("ReadmoreHref", href);
            }
        }

        if (row.getAttribute("class").contains("parent")) {
            List<WebElement> hiddenRows = row.findElements(By.xpath("following-sibling::tr[@class='child']"));
            for (WebElement hiddenRow : hiddenRows) {
                List<WebElement> hiddenLis = hiddenRow.findElements(By.tagName("li"));
                for (WebElement hiddenLi : hiddenLis) {
                    String hiddenHeader = hiddenLi.findElement(By.className("dtr-title")).getText().trim();
                    String hiddenValue = hiddenLi.findElement(By.className("dtr-data")).getText().trim();

                    List<WebElement> links = hiddenLi.findElements(By.tagName("a"));
                    if (!links.isEmpty()) {
                        WebElement link = links.get(0);
                        String href = link.getAttribute("href").trim();
                        hiddenValue = href;
                        rowData.put("ReadmoreHref", href);
                    }

                    if (!hiddenHeader.isEmpty()) {
                        rowData.put(hiddenHeader, hiddenValue);
                    } else {
                        rowData.put("ReadmoreHref", hiddenValue);
                    }
                }
            }
        }

        return rowData;
    }

    /**
     * Finds the stock data entry for the given stock symbol in a web page table and returns it as a map.
     *
     * @param driver       The WebDriver instance.
     * @param stockSymbol  The stock symbol to search for in the table.
     * @return A map representing the stock data entry, with keys representing the table headers and values representing the cell values.
     */
    public static Map<String, String> findStockData(WebDriver driver, String stockSymbol) {
        List<Map<String, String>> allEntries = getAllEntries(driver);
        for (Map<String, String> entry : allEntries) {
            if (entry.values().contains(stockSymbol)) {
                System.out.println("entry: " + entry);
                System.out.println("---------------------------------------------------");
                return entry;
            }
        }
        return null;
    }

    /**
     * Navigates to the "Read More" link associated with the given stock symbol.
     *
     * @param driver       The WebDriver instance.
     * @param stockSymbol  The stock symbol for which to navigate to the "Read More" link.
     */
    public static void navigateToReadMore(WebDriver driver, String stockSymbol) {

        Map<String, String> stockData = Config.entries;
        // Find the stock data entry for the given stock symbol
        if (stockData != null) {
            System.out.println("Stock data found for symbol: " + stockSymbol);
            String readMoreHref = stockData.get("ReadmoreHref");
            // Navigate to the "Read More" link associated with the stock symbol
            if (readMoreHref != null && !readMoreHref.isEmpty()) {
                System.out.println("Navigating to ReadmoreHref: " + readMoreHref);
                driver.navigate().to(readMoreHref);
                waitUntilPageLoadComplete(driver);
                System.out.println("Navigation to ReadmoreHref completed.");
            } else {
                System.out.println("ReadmoreHref is empty for stock symbol: " + stockSymbol);
            }
        } else {
            System.out.println("Stock data not found for symbol: " + stockSymbol);
        }
    }

    /**
     * Retrieves the count of entries from a web page table and compares it with {@link Config#entriesSize}.
     *
     * @param driver The WebDriver instance to use for interacting with the web page.
     */
    public static void entriesCount(WebDriver driver) {
        WebElement entriesCountElement = driver.findElement(By.xpath(".//*[@id='DataTables_Table_0_info']"));
        String entriesCountText = entriesCountElement.getText();

        // Define the regex pattern to match the total number of entries
        // e.g. Showing 1 to 10 of 23 entries (filtered from 1,311 total entries)
        String regex = "Showing \\d+ to \\d+ of (\\d+) entries";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Match the pattern against the input text
        Matcher matcher = pattern.matcher(entriesCountText);

        // Check if a match is found and extract the number
        Assert.assertTrue(matcher.find(), "No match found.");
        int totalEntries = Integer.parseInt(matcher.group(1));

        // Compare the total number of entries with the expected number
        Assert.assertEquals(totalEntries, Config.entriesSize, "Total entries found in table do not match the size of all entries count");
        System.out.println("---------------------------------------------------");
    }

    /**
     * Extracts the trading conditions data from a web page and returns it as a Map.
     *
     * @param driver The WebDriver instance to use for interacting with the web page.
     * @return A Map<String, String> representing the trading conditions data, where the keys are the header names and the values are the corresponding values.
     */
    public static Map<String, String> extractTradingConditions(WebDriver driver) {
        List<WebElement> tradingRows = driver.findElements(By.xpath("//div[@class='container']//tr"));
        Map<String, String> data = new HashMap<>();

        // Extract the trading conditions data from the web page
        for (WebElement row : tradingRows) {
            String header = "";
            String value = "";

            try {
                WebElement headerElement = row.findElement(By.xpath(".//td[1]"));
                header = headerElement.findElement(By.tagName("strong")).getText();
            } catch (NoSuchElementException e) {
                try {
                    header = row.findElement(By.xpath(".//td[1]")).getText();
                } catch (NoSuchElementException ignored) {
                }
            }

            try {
                WebElement valueElement = row.findElement(By.xpath(".//td[2]"));
                value = valueElement.findElement(By.tagName("strong")).getText().trim();
            } catch (NoSuchElementException e) {
                try {
                    value = row.findElement(By.xpath(".//td[2]")).getText().trim();
                } catch (NoSuchElementException ignored) {
                }
            }

            if (!header.isEmpty() && !value.isEmpty()) {
                data.put(header, value);
            }
        }
        System.out.println("---------------------------------------------------");
        return data;
    }

    /**
     * Compares the data from the tradingConditions map with the entries map and prints a message for matching values.
     *
     * @param tradingConditions A Map representing the trading conditions data, where the keys are the header names and the values are the corresponding values.
     */
    public static void compareData(Map<String, String> tradingConditions) {

        for (Map.Entry<String, String> orklaEntry : Config.entries.entrySet()) {
            for (Map.Entry<String, String> tradingEntry : tradingConditions.entrySet()) {
                if (orklaEntry.getValue().equals(tradingEntry.getValue())) {
                    System.out.println("The Column '" + orklaEntry.getKey() + "' for '" + Config.symbolName + "' Data matches the Value of '" + tradingEntry.getKey() + "' in the Trading Conditions table. The common value is '" + orklaEntry.getValue() + "'.");
                    System.out.println("----------------------------------------------------------------------------------------");
                }
            }
        }
    }

    /**
     * Waits until the page is completely loaded.
     *
     * @param driver The WebDriver instance.
     */
    public static void waitUntilPageLoadComplete(WebDriver driver) {
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }


    /**
     * Verifies that the page is loaded by checking the URL and title.
     *
     * @param driver        The WebDriver instance.
     * @param expectedUrl   The expected URL of the page.
     * @param expectedTitle The expected title of the page.
     */
    private static void verifyPageLoaded(WebDriver driver, String expectedUrl, String expectedTitle) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);

        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        wait.until(ExpectedConditions.titleContains(expectedTitle));

        String actualUrl = driver.getCurrentUrl();
        String actualTitle = driver.getTitle();

        // Verify that the actual URL and title match the expected values
        assert actualUrl.equals(expectedUrl) : "Expected URL: " + expectedUrl + " but got: " + actualUrl;
        assert actualTitle.contains(expectedTitle) : "Expected title to contain: " + expectedTitle + " but got: " + actualTitle;
    }
}