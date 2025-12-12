package Bigbasket;

import SeleniumSessions.BrowserUtil;
import SeleniumSessions.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.util.LinkedList;

public class WebTableHandling {

    WebDriver webDriver;
    @Test
    public void handleWebTable() throws InterruptedException {
        BrowserUtil browserUtil = new BrowserUtil();
        webDriver = browserUtil.launchBrowser("chrome");
        browserUtil.launchUrl("https://selectorshub.com/xpath-practice-page/");
        String locator = "//td[text()='%s']/preceding-sibling::td[2]/following-sibling::td";
        ElementUtil elementUtil = new ElementUtil(webDriver);
        Thread.sleep(5000);
        LinkedList<String> list = elementUtil.getElementsTextList(By.xpath(String.format(locator, "Joe Root")));
        System.out.println(list);
    }

}
