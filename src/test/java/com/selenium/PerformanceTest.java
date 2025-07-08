package com.selenium;

import com.pages.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PerformanceTest {
    
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static HomePage homePage;
    private static CustomerLoginPage customerLoginPage;
    private static ProductListPage productListPage;
    private static ProductDetailPage productDetailPage;
    private static CartPage cartPage;
    
    // Performance metrics storage
    private static List<Long> pageLoadTimes = new ArrayList<>();
    private static List<Long> navigationTimes = new ArrayList<>();
    private static List<Long> loginTimes = new ArrayList<>();
    private static List<Long> productLoadTimes = new ArrayList<>();
    
    @BeforeAll
    static void setUp() {
        driver = TestConfig.createChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increased timeout for performance testing
        homePage = new HomePage(driver);
        customerLoginPage = new CustomerLoginPage(driver);
        productListPage = new ProductListPage(driver);
        productDetailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
    }
    
    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        // Print performance summary
        printPerformanceSummary();
    }
    
    @Test
    @Order(1)
    @DisplayName("Performance Test: Home Page Load Time")
    void testHomePageLoadTime() {
        Instant start = Instant.now();
        
        homePage.navigateToHomePage();
        
        Instant end = Instant.now();
        long loadTime = Duration.between(start, end).toMillis();
        pageLoadTimes.add(loadTime);
        
        System.out.println("Home Page Load Time: " + loadTime + "ms");
        
        // Performance requirement: Home page should load within 3 seconds
        assertTrue(loadTime < 3000, "Home page should load within 3 seconds. Actual: " + loadTime + "ms");
        
        assertTrue(homePage.isCustomerLoginLinkDisplayed(), "Customer Login link should be displayed");
        assertTrue(homePage.isViewAllProductButtonDisplayed(), "View all product button should be displayed");
    }
    
    @Test
    @Order(2)
    @DisplayName("Performance Test: Customer Login Page Load Time")
    void testCustomerLoginPageLoadTime() {
        Instant start = Instant.now();
        
        customerLoginPage = homePage.clickCustomerLogin();
        
        Instant end = Instant.now();
        long loadTime = Duration.between(start, end).toMillis();
        pageLoadTimes.add(loadTime);
        
        System.out.println("Customer Login Page Load Time: " + loadTime + "ms");
        
        // Performance requirement: Login page should load within 2 seconds
        assertTrue(loadTime < 2000, "Login page should load within 2 seconds. Actual: " + loadTime + "ms");
        
        assertTrue(customerLoginPage.isEmailInputDisplayed(), "Email input field should be displayed");
        assertTrue(customerLoginPage.isPasswordInputDisplayed(), "Password input field should be displayed");
    }
    
    @Test
    @Order(3)
    @DisplayName("Performance Test: Login Process Time")
    void testLoginProcessTime() {
        Instant start = Instant.now();
        
        customerLoginPage.enterEmail(TestConfig.getTestEmail());
        customerLoginPage.enterPassword(TestConfig.getTestPassword());
        homePage = customerLoginPage.clickLogin();
        
        Instant end = Instant.now();
        long loginTime = Duration.between(start, end).toMillis();
        loginTimes.add(loginTime);
        
        System.out.println("Login Process Time: " + loginTime + "ms");
        
        // Performance requirement: Login process should complete within 4 seconds
        assertTrue(loginTime < 4000, "Login process should complete within 4 seconds. Actual: " + loginTime + "ms");
        
        assertTrue(homePage.isViewAllProductButtonDisplayed(), "Should be redirected to home page after login");
    }
    
    @Test
    @Order(4)
    @DisplayName("Performance Test: Product List Page Load Time")
    void testProductListPageLoadTime() {
        Instant start = Instant.now();
        
        productListPage = homePage.clickViewAllProduct();
        
        Instant end = Instant.now();
        long loadTime = Duration.between(start, end).toMillis();
        productLoadTimes.add(loadTime);
        
        System.out.println("Product List Page Load Time: " + loadTime + "ms");
        
        // Performance requirement: Product list should load within 3 seconds
        assertTrue(loadTime < 3000, "Product list should load within 3 seconds. Actual: " + loadTime + "ms");
        
        assertTrue(productListPage.isFirstProductDisplayed(), "First product should be displayed");
        assertTrue(productListPage.isFirstProductImageDisplayed(), "First product image should be displayed");
    }
    
    @Test
    @Order(5)
    @DisplayName("Performance Test: Product Detail Page Load Time")
    void testProductDetailPageLoadTime() {
        Instant start = Instant.now();
        
        productDetailPage = productListPage.clickFirstProduct();
        
        Instant end = Instant.now();
        long loadTime = Duration.between(start, end).toMillis();
        productLoadTimes.add(loadTime);
        
        System.out.println("Product Detail Page Load Time: " + loadTime + "ms");
        
        // Performance requirement: Product detail page should load within 2.5 seconds
        assertTrue(loadTime < 2500, "Product detail page should load within 2.5 seconds. Actual: " + loadTime + "ms");
        
        assertTrue(productDetailPage.isProductDetailPageLoaded(), "Should be redirected to product detail page");
        assertTrue(productDetailPage.isProductDisplayed(), "Should be viewing product details");
    }
    
    @Test
    @Order(6)
    @DisplayName("Performance Test: Add to Cart Response Time")
    void testAddToCartResponseTime() {
        Instant start = Instant.now();
        
        cartPage = productDetailPage.clickAddToCart();
        
        Instant end = Instant.now();
        long responseTime = Duration.between(start, end).toMillis();
        
        System.out.println("Add to Cart Response Time: " + responseTime + "ms");
        
        // Performance requirement: Add to cart should respond within 2 seconds
        assertTrue(responseTime < 2000, "Add to cart should respond within 2 seconds. Actual: " + responseTime + "ms");
        
        assertTrue(cartPage.isCartPageLoaded(), "Should be redirected to cart page");
        assertTrue(cartPage.isProceedToCheckoutButtonDisplayed(), "Proceed to checkout button should be displayed");
    }
    
    @Test
    @Order(7)
    @DisplayName("Performance Test: Multiple Page Navigation Stress Test")
    void testMultiplePageNavigationStressTest() {
        List<Long> navigationTimes = new ArrayList<>();
        
        // Navigate between pages multiple times to test system stability
        for (int i = 0; i < 5; i++) {
            Instant start = Instant.now();
            
            // Navigate to home
            homePage.navigateToHomePage();
            
            // Navigate to products
            productListPage = homePage.clickViewAllProduct();
            
            // Navigate back to home
            driver.navigate().back();
            
            Instant end = Instant.now();
            long navigationTime = Duration.between(start, end).toMillis();
            navigationTimes.add(navigationTime);
            
            System.out.println("Navigation Cycle " + (i + 1) + " Time: " + navigationTime + "ms");
            
            // Performance requirement: Each navigation cycle should complete within 5 seconds
            assertTrue(navigationTime < 5000, "Navigation cycle should complete within 5 seconds. Actual: " + navigationTime + "ms");
        }
        
        // Calculate average navigation time
        double avgNavigationTime = navigationTimes.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);
        
        System.out.println("Average Navigation Time: " + avgNavigationTime + "ms");
        
        // Performance requirement: Average navigation time should be under 3 seconds
        assertTrue(avgNavigationTime < 3000, "Average navigation time should be under 3 seconds. Actual: " + avgNavigationTime + "ms");
    }
    
    private static void printPerformanceSummary() {
        System.out.println("\n=== PERFORMANCE TEST SUMMARY ===");
        
        if (!pageLoadTimes.isEmpty()) {
            double avgPageLoadTime = pageLoadTimes.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
            System.out.println("Average Page Load Time: " + avgPageLoadTime + "ms");
        }
        
        if (!loginTimes.isEmpty()) {
            double avgLoginTime = loginTimes.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
            System.out.println("Average Login Time: " + avgLoginTime + "ms");
        }
        
        if (!productLoadTimes.isEmpty()) {
            double avgProductLoadTime = productLoadTimes.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
            System.out.println("Average Product Load Time: " + avgProductLoadTime + "ms");
        }
        
        System.out.println("=== END PERFORMANCE SUMMARY ===\n");
    }
} 