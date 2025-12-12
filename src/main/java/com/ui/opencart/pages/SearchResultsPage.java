package com.ui.opencart.pages;

import com.ui.opencart.utils.ElementUtil;
import com.ui.opencart.utils.TimeUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage {

    private WebDriver driver;
    private ElementUtil eutil;
    private By productsCount = By.cssSelector("div.product-layout");
    private By searchBox = By.cssSelector("input[name='search']");

    private By searchButton = By.cssSelector("button.btn-default");

    private String productLocator = "//h4/a[text()='%s']";
    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        eutil = new ElementUtil(driver);
    }

    public void doSearch(String productName)
    {
        if(eutil.doElementGetAttribute(searchBox, "value") != null)
        {
            eutil.clearTextbox(searchBox);
        }
        eutil.doSendKeys(searchBox, productName);
        TimeUtil.defaultTime();
        eutil.doClick(searchButton);
    }
    public int getSearchResultsCount()
    {
        return eutil.waitForElementsVisible(productsCount, 10).size();
    }
    public int getSearchResultsCount(String productName)
    {
        doSearch(productName);
        return eutil.waitForElementsVisible(productsCount, 10).size();
    }

    public ProductPage selectProduct(String productName)
    {
        eutil.waitForElementVisible(By.xpath(String.format(productLocator, productName)),10).click();
        //eutil.clickWhenReady(By.xpath(String.format(productLocator, productName)),10);
        return new ProductPage(driver);
    }
}
