package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {
    
    @FindBy(xpath = "//button[contains(@class, 'pd') and contains(., 'Proceed To Checkout')]")
    private WebElement proceedToCheckoutButton;
    
    public CartPage(WebDriver driver) {
        super(driver);
    }
    
    public ShippingAddressPage clickProceedToCheckout() {
        clickElement(proceedToCheckoutButton);
        return new ShippingAddressPage(driver);
    }
    
    public boolean isCartPageLoaded() {
        return getCurrentUrl().contains("cart.jsp") || getCurrentUrl().contains("checkout");
    }
    
    public boolean isProceedToCheckoutButtonDisplayed() {
        try {
            return proceedToCheckoutButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
} 