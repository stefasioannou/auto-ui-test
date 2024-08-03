package com.xm.interview.test.project;

import org.openqa.selenium.WebElement;

import java.util.Map;

/**
 * The Config class contains static fields that hold configuration values for the application.
 */
public class Config {

    public static final String chromeDriverPath = "drivers\\chromedriver.exe";
    public static final String websiteURL = "https://www.xm.com";
    public static final String symbolName = "Orkla ASA (ORK.OL)";
    public static final String country = "Norway";
    public static String browserSize;
    public static Map<String, String> entries;
    public static int entriesSize;
}