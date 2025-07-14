package com.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class TestConfig {
    
    private static final String BASE_URL = "http://localhost:8080/EcommerceApp/";
    private static final String TEST_EMAIL = "daniel@gmail.com";
    private static final String TEST_PASSWORD = "12345";
    
    // Shipping address test data
    private static final String TEST_ADDRESS = "123 Test Street";
    private static final String TEST_CITY = "Test City";
    private static final String TEST_STATE = "Test State";
    private static final String TEST_COUNTRY = "Test Country";
    private static final String TEST_PINCODE = "12345";
    
    public static String getBaseUrl() {
        return BASE_URL;
    }
    
    public static String getTestEmail() {
        return TEST_EMAIL;
    }
    
    public static String getTestPassword() {
        return TEST_PASSWORD;
    }
    
    public static String getTestAddress() {
        return TEST_ADDRESS;
    }
    
    public static String getTestCity() {
        return TEST_CITY;
    }
    
    public static String getTestState() {
        return TEST_STATE;
    }
    
    public static String getTestCountry() {
        return TEST_COUNTRY;
    }
    
    public static String getTestPincode() {
        return TEST_PINCODE;
    }
    
    public static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        // Add options for better stability
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        // Disable password strength warnings and other popups
        options.addArguments("--disable-password-generation");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-translate");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--disable-infobars");
        options.addArguments("--password-store=basic");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--incognito");

        // Set Chrome preferences to disable password manager
        java.util.Map<String, Object> prefs = new java.util.HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        
        // Uncomment the line below to run in headless mode
        // options.addArguments("--headless");
        
        return new ChromeDriver(options);
    }
    
    public static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        
        // Add options for better stability
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        
        return new FirefoxDriver(options);
    }
    
    public static void waitForPageLoad(WebDriver driver) {
        try {
            Thread.sleep(2000); // Simple wait for page load
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
} 