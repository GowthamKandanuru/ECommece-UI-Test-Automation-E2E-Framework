package Bigbasket;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Set;

public class CascadeWindowHandling {

    WebDriver driver = new ChromeDriver();

    @Test
    public void handleWindows() throws InterruptedException
    {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();
        Thread.sleep(2000);
        String parentWindow = driver.getWindowHandle();
        driver.findElement(By.xpath("//a[contains(@href,'twitter')]")).click();

        By xpath = By.xpath("//section/div[4]//span[text()='Terms of Service']");
        By xpath2 = By.xpath("//a[text()='Terms of Service Archive']");
        Set<String> windows = driver.getWindowHandles();
        Iterator<String> it = windows.iterator();
        while (it.hasNext())
        {
            String childWindows = it.next();
            if(!childWindows.equals(parentWindow))
            {
                driver.switchTo().window(childWindows);
                System.out.println(driver.getCurrentUrl());
                driver.close();
            }
        }
        driver.switchTo().window(parentWindow);
    }
}
