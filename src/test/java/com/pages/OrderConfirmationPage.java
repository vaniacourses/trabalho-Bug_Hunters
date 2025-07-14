package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderConfirmationPage extends BasePage {
    
    @FindBy(xpath = "//h1[contains(text(), 'Order')]")
    private WebElement orderConfirmationHeader;
    
    @FindBy(xpath = "//h2[contains(text(), 'Order')]")
    private WebElement orderConfirmationHeaderAlt;
    
    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }
    
    public boolean isOrderConfirmationPageLoaded() {
        return getCurrentUrl().contains("order") || getCurrentUrl().contains("success") || 
               getPageTitle().contains("Order") || getPageTitle().contains("Success") ||
               orderConfirmationHeader.isDisplayed() || orderConfirmationHeaderAlt.isDisplayed();
    }
    
    public boolean isOrderConfirmed() {
        try {
            return orderConfirmationHeader.isDisplayed() || orderConfirmationHeaderAlt.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getOrderConfirmationMessage() {
        try {
            if (orderConfirmationHeader.isDisplayed()) {
                return orderConfirmationHeader.getText();
            } else if (orderConfirmationHeaderAlt.isDisplayed()) {
                return orderConfirmationHeaderAlt.getText();
            }
        } catch (Exception e) {
            // Return page title as fallback
        }
        return getPageTitle();
    }
} 