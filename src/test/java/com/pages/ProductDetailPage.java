package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.FindBy;

public class ProductDetailPage extends BasePage {
    @FindBy(xpath = "//h2[contains(text(), '')]")
    private WebElement productNameHeader;
    
    @FindBy(linkText = "Add To Cart")
    private WebElement addToCartButton;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductDetailPageLoaded() {
        // Check if we're on a product detail page by looking for the product name header
        return isProductDisplayed();
    }

    public boolean isProductDisplayed() {
        try {
            return productNameHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public CartPage clickAddToCart() {
        try {
            // Scroll to the element first
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
            Thread.sleep(1000); // Wait for scroll to complete
            clickElement(addToCartButton);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new CartPage(driver);
    }
    
    public boolean isAddToCartButtonDisplayed() {
        try {
            return addToCartButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
} 