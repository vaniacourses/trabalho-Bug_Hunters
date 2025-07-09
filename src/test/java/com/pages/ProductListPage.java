package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;

public class ProductListPage extends BasePage {
    
    @FindBy(xpath = "//a[contains(@href, 'selecteditemc.jsp')][1]")
    private WebElement firstProductLink;
    
    @FindBy(xpath = "//img[contains(@src, 'images/')][1]")
    private WebElement firstProductImage;
    
    @FindBy(xpath = "//a[contains(@href, 'selecteditemc.jsp')][1]")
    private WebElement firstProductNameLink;
    
    public ProductListPage(WebDriver driver) {
        super(driver);
    }
    
    public ProductDetailPage clickFirstProduct() {
        clickElement(firstProductLink);
        return new ProductDetailPage(driver);
    }
    
    public ProductDetailPage clickFirstProductImage() {
        clickElement(firstProductImage);
        return new ProductDetailPage(driver);
    }
    
    public ProductDetailPage clickFirstProductName() {
        clickElement(firstProductNameLink);
        return new ProductDetailPage(driver);
    }
    
    public boolean isFirstProductDisplayed() {
        return firstProductLink.isDisplayed();
    }
    
    public boolean isFirstProductImageDisplayed() {
        return firstProductImage.isDisplayed();
    }
    
    public String getFirstProductName() {
        return firstProductNameLink.getText();
    }
    
    public String getFirstProductImageSrc() {
        return firstProductImage.getAttribute("src");
    }
    
    public String getFirstProductLinkHref() {
        return firstProductLink.getAttribute("href");
    }
} 