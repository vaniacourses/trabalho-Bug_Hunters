package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CustomerLoginPage extends BasePage {
    
    @FindBy(id = "x1")
    private WebElement emailInput;
    
    @FindBy(id = "x2")
    private WebElement passwordInput;
    
    @FindBy(name = "b1")
    private WebElement loginButton;
    
    public CustomerLoginPage(WebDriver driver) {
        super(driver);
    }
    
    public void enterEmail(String email) {
        sendKeysToElement(emailInput, email);
    }
    
    public void enterPassword(String password) {
        sendKeysToElement(passwordInput, password);
    }
    
    public HomePage clickLogin() {
        clickElement(loginButton);
        return new HomePage(driver);
    }
    
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }
    
    public boolean isEmailInputDisplayed() {
        return emailInput.isDisplayed();
    }
    
    public boolean isPasswordInputDisplayed() {
        return passwordInput.isDisplayed();
    }
    
    public boolean isLoginButtonDisplayed() {
        return loginButton.isDisplayed();
    }
    
    public String getEmailInputValue() {
        return emailInput.getAttribute("value");
    }
    
    public String getPasswordInputValue() {
        return passwordInput.getAttribute("value");
    }
} 