package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentConfirmationPage extends BasePage {
    
    @FindBy(xpath = "//input[@value='Confirm Payment']")
    private WebElement confirmPaymentButton;
    
    @FindBy(xpath = "//button[contains(text(), 'Confirm Payment')]")
    private WebElement confirmPaymentButtonAlt;
    
    @FindBy(xpath = "//a[contains(text(), 'Confirm Payment')]")
    private WebElement confirmPaymentLink;
    
    public PaymentConfirmationPage(WebDriver driver) {
        super(driver);
    }
    
    public OrderConfirmationPage clickConfirmPayment() {
        // Try different locators for the confirm payment button
        try {
            clickElement(confirmPaymentButton);
        } catch (Exception e) {
            try {
                clickElement(confirmPaymentButtonAlt);
            } catch (Exception e2) {
                clickElement(confirmPaymentLink);
            }
        }
        return new OrderConfirmationPage(driver);
    }
    
    public boolean isPaymentConfirmationPageLoaded() {
        return getCurrentUrl().contains("payment") || getCurrentUrl().contains("confirm") || 
               getPageTitle().contains("Payment") || getPageTitle().contains("Confirm");
    }
    
    public boolean isConfirmPaymentButtonDisplayed() {
        try {
            return confirmPaymentButton.isDisplayed() || confirmPaymentButtonAlt.isDisplayed() || 
                   confirmPaymentLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
} 