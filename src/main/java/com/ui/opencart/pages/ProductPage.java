package com.ui.opencart.pages;

import com.ui.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {

    WebDriver driver;
    ElementUtil eutil;
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        eutil = new ElementUtil(driver);
    }

    private By productHeader = By.xpath("//h1");

    private By addToCart = By.xpath("//button[text()='Add to Cart']");

    private By quantityBox = By.cssSelector("input#input-quantity");

    private By cartValue = By.xpath("//span[@id='cart-total']");

    private By itemValue = By.xpath("//ul/li/h2");

    private By cartPriceButton = By.cssSelector("button.btn-inverse");

    private By removeProducts = By.cssSelector("button.btn-danger");

    public String getProductHeader()
    {
        return eutil.getElementText(productHeader);
    }

    public void setAddToCart()
    {
        eutil.clickWhenReady(addToCart,10);
    }

    public void addQuantity(int quantity)
    {
        eutil.clearTextbox(quantityBox);
        eutil.doSendKeys(quantityBox, String.valueOf(quantity));
    }

    public int getProductValue()
    {
        String productValue = eutil.getElementText(itemValue);
        String amount = productValue.substring(productValue.indexOf("$")+1,productValue.indexOf("."));
        return Integer.parseInt(amount);
    }
    public Long getCartValue()
    {
        String cartString = eutil.getElementText(cartValue).trim().replace(",", "");
        String cartAmount = cartString.substring(cartString.indexOf("$")+1, cartString.indexOf("."));
        return Long.parseLong(cartAmount);
    }

    public int getCartCount()
    {   String cartString = eutil.getElementText(cartValue).trim();
      return Integer.parseInt(cartString.substring(0,1));
    }

    public void removeProductsFromCart()
    {
        eutil.doClick(cartPriceButton);
        eutil.waitForElementVisible(removeProducts, 10).click();
    }
}
