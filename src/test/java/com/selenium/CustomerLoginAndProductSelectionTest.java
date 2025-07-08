package com.selenium;

import com.pages.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerLoginAndProductSelectionTest {
    
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static HomePage homePage;
    private static CustomerLoginPage customerLoginPage;
    private static ProductListPage productListPage;
    private static ProductDetailPage productDetailPage;
    private static CartPage cartPage;
    private static ShippingAddressPage shippingAddressPage;
    private static PaymentConfirmationPage paymentConfirmationPage;
    private static OrderConfirmationPage orderConfirmationPage;
    
    @BeforeAll
    static void setUp() {
        driver = TestConfig.createChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 second timeout
        homePage = new HomePage(driver);
        customerLoginPage = new CustomerLoginPage(driver);
        productListPage = new ProductListPage(driver);
        productDetailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
        shippingAddressPage = new ShippingAddressPage(driver);
        paymentConfirmationPage = new PaymentConfirmationPage(driver);
        orderConfirmationPage = new OrderConfirmationPage(driver);
    }
    
    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    @Test
    @Order(1)
    @DisplayName("Step 1: Navigate to home page")
    void testNavigateToHomePage() {
        homePage.navigateToHomePage();
        assertTrue(homePage.isCustomerLoginLinkDisplayed(), "Customer Login link should be displayed on home page");
        assertTrue(homePage.isViewAllProductButtonDisplayed(), "View all product button should be displayed on home page");
    }
    
    @Test
    @Order(2)
    @DisplayName("Step 2: Click Customer Login and verify login form")
    void testClickCustomerLogin() {
        customerLoginPage = homePage.clickCustomerLogin();
        assertTrue(customerLoginPage.isEmailInputDisplayed(), "Email input field should be displayed");
        assertTrue(customerLoginPage.isPasswordInputDisplayed(), "Password input field should be displayed");
        assertTrue(customerLoginPage.isLoginButtonDisplayed(), "Login button should be displayed");
    }
    
    @Test
    @Order(3)
    @DisplayName("Step 3: Fill login form with credentials")
    void testFillLoginForm() {
        customerLoginPage.enterEmail(TestConfig.getTestEmail());
        customerLoginPage.enterPassword(TestConfig.getTestPassword());
        assertEquals(TestConfig.getTestEmail(), customerLoginPage.getEmailInputValue(), "Email should be filled correctly");
        assertEquals(TestConfig.getTestPassword(), customerLoginPage.getPasswordInputValue(), "Password should be filled correctly");
    }
    
    @Test
    @Order(4)
    @DisplayName("Step 4: Submit login form and verify redirect to home page")
    void testSubmitLoginForm() {
        homePage = customerLoginPage.clickLogin();
        assertTrue(homePage.isViewAllProductButtonDisplayed(), "Should be redirected to home page after login");
    }
    
    @Test
    @Order(5)
    @DisplayName("Step 5: Click 'View all product' button")
    void testClickViewAllProduct() {
        productListPage = homePage.clickViewAllProduct();
        assertTrue(productListPage.isFirstProductDisplayed(), "First product should be displayed on product list page");
        assertTrue(productListPage.isFirstProductImageDisplayed(), "First product image should be displayed");
    }
    
    @Test
    @Order(6)
    @DisplayName("Step 6: Click on first product")
    void testClickFirstProduct() {
        productDetailPage = productListPage.clickFirstProduct();
        assertTrue(productDetailPage.isProductDetailPageLoaded(), "Should be redirected to product detail page");
        assertTrue(productDetailPage.isProductDisplayed(), "Should be viewing product details");
    }
    
    @Test
    @Order(7)
    @DisplayName("Step 7: Add product to cart")
    void testAddToCart() {
        cartPage = productDetailPage.clickAddToCart();
        assertTrue(cartPage.isCartPageLoaded(), "Should be redirected to cart page");
        assertTrue(cartPage.isProceedToCheckoutButtonDisplayed(), "Proceed to checkout button should be displayed");
    }
    
    @Test
    @Order(8)
    @DisplayName("Step 8: Proceed to checkout")
    void testProceedToCheckout() {
        shippingAddressPage = cartPage.clickProceedToCheckout();
        assertTrue(shippingAddressPage.isShippingAddressPageLoaded(), "Should be redirected to shipping address page");
        assertTrue(shippingAddressPage.isAddressFormDisplayed(), "Shipping address form should be displayed");
        assertTrue(shippingAddressPage.isPaymentButtonsDisplayed(), "Payment buttons should be displayed");
    }
    
    @Test
    @Order(9)
    @DisplayName("Step 9: Fill shipping address form")
    void testFillShippingAddress() {
        shippingAddressPage.fillShippingForm(
            TestConfig.getTestAddress(),
            TestConfig.getTestCity(),
            TestConfig.getTestState(),
            TestConfig.getTestCountry(),
            TestConfig.getTestPincode()
        );
    }
    
    @Test
    @Order(10)
    @DisplayName("Step 10: Click Cash on Delivery")
    void testClickCashOnDelivery() throws InterruptedException {
        paymentConfirmationPage = shippingAddressPage.clickCashOnDelivery();
        Thread.sleep(2000);
        assertTrue(paymentConfirmationPage.isPaymentConfirmationPageLoaded(), "Should be redirected to payment confirmation page");
        assertTrue(paymentConfirmationPage.isConfirmPaymentButtonDisplayed(), "Confirm payment button should be displayed");
    }
    
    @Test
    @Order(11)
    @DisplayName("Step 11: Confirm payment")
    void testConfirmPayment() throws InterruptedException {
        orderConfirmationPage = paymentConfirmationPage.clickConfirmPayment();
        assertTrue(orderConfirmationPage.isOrderConfirmationPageLoaded(), "Should be redirected to order confirmation page");
        assertTrue(orderConfirmationPage.isOrderConfirmed(), "Order should be confirmed");
    }
    
    @Test
    @DisplayName("Complete end-to-end test: Login, select product, and complete checkout")
    void testCompleteLoginAndCheckoutFlow() {
        // Step 1: Navigate to home page
        homePage.navigateToHomePage();
        
        // Step 2: Login
        customerLoginPage = homePage.clickCustomerLogin();
        customerLoginPage.login(TestConfig.getTestEmail(), TestConfig.getTestPassword());
        
        // Step 3: Navigate to products and select first product
        productListPage = homePage.clickViewAllProduct();
        productDetailPage = productListPage.clickFirstProduct();
        
        // Step 4: Add to cart
        cartPage = productDetailPage.clickAddToCart();
        
        // Step 5: Proceed to checkout
        shippingAddressPage = cartPage.clickProceedToCheckout();
        
        // Step 6: Fill shipping address
        shippingAddressPage.fillShippingForm(
            TestConfig.getTestAddress(),
            TestConfig.getTestCity(),
            TestConfig.getTestState(),
            TestConfig.getTestCountry(),
            TestConfig.getTestPincode()
        );
        
        paymentConfirmationPage = shippingAddressPage.clickCashOnDelivery();
        
        orderConfirmationPage = paymentConfirmationPage.clickConfirmPayment();
        
        assertTrue(orderConfirmationPage.isOrderConfirmationPageLoaded(), "Should successfully complete the entire checkout flow");
        assertTrue(orderConfirmationPage.isOrderConfirmed(), "Order should be confirmed");
    }
}