package com.ui.opencart.tests;

import com.ui.opencart.base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchResultsTest extends BaseTest {

    Logger logger = LogManager.getLogger(SearchResultsTest.class);

    @BeforeClass
    public void loginToWebsite()
    {
      accPage =  loginPage.doLogin(properties.getProperty("username"), properties.getProperty("password"));
    }

    @Test
    public void searchForProduct()
    {
        String product = "Macbook";
        searchResultsPage = accPage.doSearch(product);
        int productsCount = searchResultsPage.getSearchResultsCount();
        logger.info("product count of {} is {}: ",product,productsCount);
        Assert.assertEquals(productsCount, 3);
    }
}
