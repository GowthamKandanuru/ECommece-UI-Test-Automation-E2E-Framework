package com.ui.opencart.pages;

import com.ui.opencart.utils.ElementUtil;
import com.ui.opencart.utils.TimeUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AccountPage {

    private WebDriver driver;
    private By logout = By.linkText("Logout");
    private By accountHeaders = By.cssSelector("div#content>h2");
    private By searchBox = By.cssSelector("input[name='search']");

    private By searchButton = By.cssSelector("button.btn-default");
    ElementUtil  elementUtil;
    public AccountPage(WebDriver driver)
    {
        this.driver = driver;
        elementUtil = new ElementUtil(driver);
    }

    public String getAccountPageTitle()
    {
        return elementUtil.waitForTitleIs("My Account",10);
    }

    public boolean isLogoutLinkExist()
    {
        elementUtil.waitForElementVisible(logout, 10);
        return elementUtil.isElementExist(logout);
    }

    public SearchResultsPage doSearch(String productName)
    {
        if(elementUtil.doElementGetAttribute(searchBox, "value") != null)
        {
            elementUtil.clearTextbox(searchBox);
        }
        elementUtil.doSendKeys(searchBox, productName);
        TimeUtil.defaultTime();
        elementUtil.doClick(searchButton);
       return new SearchResultsPage(driver);
       // elementUtil.waitForElementVisible(searchBox, 10).sendKeys("Macbook");
    }

    public List<String> getAccountsPageHeadersList() {

      // return elementUtil.getElementsTextList(accountHeaders);
        List<String> headersList = new ArrayList<>();
        List<WebElement> headerEleList = elementUtil.getElements(accountHeaders);
        for (WebElement e : headerEleList)
        {
            String headers = e.getText();
            if(headers != null)
            {
                headersList.add(headers);
            }
        }
        return headersList;
    }

    public String getAccPageURL() {
        return null;
    }
}
