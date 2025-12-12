package com.ui.opencart.pages;

import com.ui.opencart.constants.AppConstants;
import com.ui.opencart.utils.ElementUtil;
import com.ui.opencart.utils.TimeUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.*;

public class LoginPage {

    // 1. private Locators
    // 2. public Page class Constructor
    // 3. public Page actions/methods
    private WebDriver driver;
    private ElementUtil elementUtil;
    private By emailId = By.id("input-email");
    private By passwordField = By.id("input-password");
    private By loginButton = By.xpath("//input[@value='Login']");
    private By forgotPWdLink = By.linkText("Forgotten Password");

    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
        elementUtil = new ElementUtil(driver);
    }
    @Step("Logging open cart with username: {0} and password: {1}")
    public AccountPage doLogin(String username, String password) /*throws InterruptedException*/ {
        // Login functionality code
        elementUtil.doSendKeys(emailId, username);
        elementUtil.doSendKeys(passwordField, password);
        elementUtil.doClick(loginButton);
        TimeUtil.applyWait(4);
        return new AccountPage(driver);
    }
    @Step("Getting login page title")
    public String getLoginPageTitle()
    {
        return elementUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE,30);
    }

    public void getLoginPageUrl()
    {
        System.out.println(driver.getCurrentUrl());
    }

    public boolean isForgotPasswordLinkExist()
    {
        return elementUtil.isElementExist(forgotPWdLink);

    }

    public boolean isForgotPasswordLinkExistUsingRelative()
    {
        return elementUtil.isElementExistUsingBelowLocator(loginButton,forgotPWdLink);
    }
}
