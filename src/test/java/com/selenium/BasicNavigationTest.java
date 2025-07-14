package com.selenium;

import com.pages.HomePage;
import com.pages.CustomerLoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;

public class BasicNavigationTest {
    
    private static WebDriver driver;
    private static HomePage homePage;
    
    @BeforeAll
    static void setUp() {
        driver = TestConfig.createChromeDriver();
        homePage = new HomePage(driver);
    }
    
    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    @Test
    @DisplayName("Test basic navigation to home page")
    void testBasicNavigation() {
        // Navigate to home page
        homePage.navigateToHomePage();
        
        // Check if we're on the correct page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("EcommerceApp"), 
                "Should be on the EcommerceApp page");
        
        // Check if customer login link is present
        assertTrue(homePage.isCustomerLoginLinkDisplayed(), 
                "Customer Login link should be displayed");
        
        // Check if view all product button is present
        assertTrue(homePage.isViewAllProductButtonDisplayed(), 
                "View All Product button should be displayed");
        
        System.out.println("Current URL: " + currentUrl);
        System.out.println("Page Title: " + driver.getTitle());
    }
    
    @Test
    @DisplayName("Test customer login navigation")
    void testCustomerLoginNavigation() {
        // Navigate to home page
        homePage.navigateToHomePage();
        
        // Click on customer login
        CustomerLoginPage customerLoginPage = homePage.clickCustomerLogin();
        
        // Check if we're on the customer login page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("customerlogin.jsp"), 
                "Should be on the customer login page");
        
        System.out.println("Customer Login URL: " + currentUrl);
        System.out.println("Customer Login Page Title: " + driver.getTitle());
    }
} 