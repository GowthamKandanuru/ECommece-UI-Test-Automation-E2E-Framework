package com.ui.opencart.tests;

import com.ui.opencart.base.BaseTest;
import com.ui.opencart.constants.AppConstants;
import com.ui.opencart.errors.AppError;
import com.ui.opencart.factory.DriverFactory;
import com.ui.opencart.pages.AccountPage;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Epic 100: Design Open cart login")
@Feature("Login feature")
@Story("Login")
public class LoginPageTest extends BaseTest {

    private AccountPage accountPage;

    Logger logger = LogManager.getLogger(LoginPageTest.class);

    @Description("Checking Login Title")
    @Severity(SeverityLevel.MINOR)
    @Test(priority = 1)
    public void loginPageTileTest()
    {
        String actual =  loginPage.getLoginPageTitle();
        softAssert.assertEquals(actual, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
    }

    @Description("Checking forgot password link")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 2)
    public void forgotPwdLinkExistTest()
    {
        Assert.assertTrue(loginPage.isForgotPasswordLinkExistUsingRelative());
    }

    @Description("Checking Login functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 3)
    public void loginTest() /*throws InterruptedException*/{
       accountPage  = loginPage.doLogin(properties.getProperty("username"), properties.getProperty("password"));
        Assert.assertEquals(accountPage.getAccountPageTitle(), "My Account");
    }
}
