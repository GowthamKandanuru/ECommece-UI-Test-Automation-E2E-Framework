package com.ui.opencart.tests;

import com.ui.opencart.base.BaseTest;
import com.ui.opencart.constants.AppConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class AccountPageTest extends BaseTest {



    Logger logger = LogManager.getLogger(AccountPageTest.class);

    @BeforeClass
    public void accSetup() {
        accPage = loginPage.doLogin(properties.getProperty("username"), properties.getProperty("password"));
    }

    @Test
    public void accPageTitleTest() {
        String actTitle = accPage.getAccountPageTitle();
        Assert.assertEquals(actTitle, AppConstants.ACCOUNT_PAGE_TITLE);
    }

   /* @Test
    public void accPageURLTest() {
        String actURL = accPage.getAccPageURL();
        Assert.assertTrue(actURL.contains(AppConstants.ACC_PAGE_URL_FRACTION));
    }*/

    @Test
    public void isLogoutLinkExistTest() {
        Assert.assertTrue(accPage.isLogoutLinkExist());
    }

    @Test
    public void accPageHeadersTest() {
        List<String> actHeadersList = accPage.getAccountsPageHeadersList();
        logger.info("List of headers displayed: {}",actHeadersList);
    }

    @Test
    public void searchTest() {
        accPage.doSearch("macbook");
    }
}
