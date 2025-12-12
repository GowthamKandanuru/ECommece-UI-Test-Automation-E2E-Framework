package com.ui.opencart.tests;

import com.ui.opencart.base.BaseTest;
import com.ui.opencart.utils.ExcelUtil;
import com.ui.opencart.utils.TimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;

public class ProductPageTest extends BaseTest {

    Logger logger = LogManager.getLogger(ProductPageTest.class);

    @BeforeClass
    public void init()
    {
        accPage = loginPage.doLogin(properties.getProperty("username"), properties.getProperty("password"));
    }

    @DataProvider
    public Object[][] getProductDetails()
    {
       // return new Object[][] {{"macbook","MacBook",5}};
       return ExcelUtil.getTestData("ProductDetails");
    }
    @Test(dataProvider = "getProductDetails")
    public void validateCartValueFeature(String productSearch,String productName, String quantity)
    {
        searchResultsPage = accPage.doSearch(productSearch);
        productPage = searchResultsPage.selectProduct(productName);
        int productCount = Integer.parseInt(quantity.substring(0,quantity.indexOf(".")));
        productPage.addQuantity(productCount);
        productPage.setAddToCart();
        TimeUtil.mediumTime();
        int expectedCartValue = productPage.getProductValue()*productCount;
        long actualCartValue = productPage.getCartValue();
        logger.info(String.format("Expected cart value is: %d", expectedCartValue));
        logger.info(String.format("Actual cart value is: %d", actualCartValue));
        softAssert.assertEquals(actualCartValue, expectedCartValue);
        softAssert.assertEquals(productPage.getCartCount(), productCount);
        productPage.removeProductsFromCart();
        softAssert.assertAll();
    }
}
