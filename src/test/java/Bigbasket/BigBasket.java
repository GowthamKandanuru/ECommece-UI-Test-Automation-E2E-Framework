package Bigbasket;


import SeleniumSessions.BrowserUtil;
import SeleniumSessions.ElementUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;


public class BigBasket {

    Logger LOGGER = LogManager.getLogger(BigBasket.class);

        @Test
        public void searchText() throws InterruptedException {
            BrowserUtil browserUtil = new BrowserUtil();
            RemoteWebDriver driver = browserUtil.launchBrowser(" CHROME ");
            browserUtil.launchUrl("https://www.jqueryscript.net/demo/Drop-Down-Combo-Tree/");
            browserUtil.refresh();
            try {
                Thread.sleep(2000);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            //By searchProducts = By.xpath("//header[@class='sm:hidden z-30 flex flex-col col-span-12']//input[@placeholder='Search for Products...']");
            ElementUtil elementUtil = new ElementUtil(driver);
            elementUtil.doClick(By.xpath("//h3[text()='Multi Selection']/parent::div//button"));
            Thread.sleep(5000);
            elementUtil.doClick(By.xpath("//h3[text()='Multi Selection']/parent::div//ul//span[text()='choice 6 2 3']/input"));
            LOGGER.info(elementUtil.isElementSelected(By.xpath("//h3[text()='Multi Selection With Cascade Option Select']/parent::div//ul//span[text()='choice 6 2 3']")));
            /*LOGGER.info(elementUtil.doElementGetAttribute(By.xpath("(//img)[3]"), "alt"));
            int count = elementUtil.getElementsCount(By.xpath("(//ul[@class='flex justify-start min-w-full p-0'])[1]/li//div[contains(@class,'break-wor')]/h3"));
            LOGGER.info(count);
            LOGGER.info(elementUtil.getElementsTextList(By.xpath("(//ul[@class='flex justify-start min-w-full p-0'])[1]/li//div[contains(@class,'break-wor')]/h3")));*/
            //elementUtil.doSendKeys("xpath", "//header[@class='sm:hidden z-30 flex flex-col col-span-12']//input[@placeholder='Search for Products...']", "Yoga");
            /*elementUtil.doClick("Xpath","//button[text()='Login/ Sign Up']");
            elementUtil.doSendKeys("xpath", "//input[@placeholder='Enter Phone number/ Email Id']", "7674988859");
            try {
                Thread.sleep(2000);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            elementUtil.doClick(By.xpath("//button[text()='Continue']"));
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            browserUtil.refresh();
            System.out.println(elementUtil.checkElementDisplayed(By.xpath("//button[contains(@class,'MemberDropdown')]")));*/
            //elementUtil.doSendKeys(searchProducts,"Yoga");
        }
}