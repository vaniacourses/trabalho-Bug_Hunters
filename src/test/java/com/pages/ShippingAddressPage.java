package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.FindBy;

public class ShippingAddressPage extends BasePage {
    
    @FindBy(name = "Address")
    private WebElement addressInput;
    
    @FindBy(name = "City")
    private WebElement cityInput;
    
    @FindBy(name = "State")
    private WebElement stateInput;
    
    @FindBy(name = "Country")
    private WebElement countryInput;
    
    @FindBy(name = "Pincode")
    private WebElement pincodeInput;
    
    @FindBy(name = "cash")
    private WebElement cashOnDeliveryButton;
    
    @FindBy(name = "online")
    private WebElement onlinePaymentButton;
    
    public ShippingAddressPage(WebDriver driver) {
        super(driver);
    }
    
    public void fillAddress(String address) {
        sendKeysToElement(addressInput, address);
    }
    
    public void fillCity(String city) {
        sendKeysToElement(cityInput, city);
    }
    
    public void fillState(String state) {
        sendKeysToElement(stateInput, state);
    }
    
    public void fillCountry(String country) {
        sendKeysToElement(countryInput, country);
    }
    
    public void fillPincode(String pincode) {
        sendKeysToElement(pincodeInput, pincode);
    }
    
    public PaymentConfirmationPage clickCashOnDelivery() {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cashOnDeliveryButton);
            Thread.sleep(1000);
            clickElement(cashOnDeliveryButton);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new PaymentConfirmationPage(driver);
    }
    
    public PaymentConfirmationPage clickOnlinePayment() {
        clickElement(onlinePaymentButton);
        return new PaymentConfirmationPage(driver);
    }
    
    public void fillShippingForm(String address, String city, String state, String country, String pincode) {
        fillAddress(address);
        fillCity(city);
        fillState(state);
        fillCountry(country);
        fillPincode(pincode);
    }
    
    public boolean isShippingAddressPageLoaded() {
        return getCurrentUrl().contains("ShippingAddress") || getPageTitle().contains("Shipping");
    }
    
    public boolean isAddressFormDisplayed() {
        try {
            return addressInput.isDisplayed() && cityInput.isDisplayed() && 
                   stateInput.isDisplayed() && countryInput.isDisplayed() && 
                   pincodeInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isPaymentButtonsDisplayed() {
        try {
            return cashOnDeliveryButton.isDisplayed() && onlinePaymentButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
} 