package com.ui.opencart.base;

import com.ui.opencart.factory.DriverFactory;
import com.ui.opencart.pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.Properties;

public class BaseTest {

    DriverFactory driverFactory;
    private WebDriver driver;
    protected LoginPage loginPage;
    protected AccountPage accPage;
    protected SearchResultsPage searchResultsPage;
    protected ProductPage productPage;
    protected Properties properties;
    protected SoftAssert softAssert;

   /* @Parameters({"browser"})*/
    @BeforeTest
    public void setup(/*String browserName*/)
    {
        driverFactory = new DriverFactory();
        properties = driverFactory.initprop();
        /*if(browserName!=null)
        {
            *//*properties.setProperty("browser", browserName);*//*
            System.out.println(browserName);
        }*/
        driver = driverFactory.initDriver(properties);
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
    }

    @AfterTest
    public void teardown()
    {
        driver.quit();
    }
}
