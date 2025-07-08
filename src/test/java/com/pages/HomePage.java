package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    
    @FindBy(linkText = "Customer Login")
    private WebElement customerLoginLink;
    
    @FindBy(linkText = "View All Product")
    private WebElement viewAllProductButton;
    
    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    public void navigateToHomePage() {
        driver.get("http://localhost:8080/EcommerceApp/");
    }
    
    public CustomerLoginPage clickCustomerLogin() {
        clickElement(customerLoginLink);
        return new CustomerLoginPage(driver);
    }
    
    public ProductListPage clickViewAllProduct() {
        clickElement(viewAllProductButton);
        return new ProductListPage(driver);
    }
    
    public boolean isCustomerLoginLinkDisplayed() {
        return customerLoginLink.isDisplayed();
    }
    
    public boolean isViewAllProductButtonDisplayed() {
        return viewAllProductButton.isDisplayed();
    }
} 