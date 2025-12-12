package Bigbasket;

import SeleniumSessions.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class MoveToeElement {

    public static void main(String[] args) throws InterruptedException{

        WebDriver driver = new ChromeDriver();
        ElementUtil elementUtil = new ElementUtil(driver);
        driver.get("https://www.bigbasket.com/");
        driver.navigate().refresh();
        driver.manage().window().maximize();
        //WebElement element = driver.findElement(By.xpath("(//span[text()='Shop by'])[2]"));
        Actions actions = new Actions(driver);
        elementUtil.doActionClick(By.xpath("(//span[text()='Shop by'])[2]"));
        Thread.sleep(2000);
        //WebElement element1 = driver.findElement(By.linkText("Food Court"));
        elementUtil.moveToElement(By.linkText("Food Court"));
        Thread.sleep(2000);
        WebElement element2 = driver.findElement(By.linkText("Burgers & Sandwiches"));
        actions.moveToElement(element2).perform();
        Thread.sleep(2000);
        //WebElement element3 = driver.findElement(By.linkText("Sandwiches & Subs"));
        elementUtil.doActionClick(By.linkText("Sandwiches & Subs"));
        Thread.sleep(3000);
        actions.scrollToElement(driver.findElement(By.xpath("//footer//li/a[text()='Ferrero']"))).perform();
        elementUtil.doClick(By.xpath("//footer//li/a[text()='Fortune']"));
        Thread.sleep(3000);
        actions.scrollToElement(driver.findElement(By.xpath("//footer//li/a[text()='Ferrero']"))).perform();
        elementUtil.doClick(By.xpath("//footer//li/a[text()='Health Drinks']"));
        //actions.click(element3).perform();
    }
}
