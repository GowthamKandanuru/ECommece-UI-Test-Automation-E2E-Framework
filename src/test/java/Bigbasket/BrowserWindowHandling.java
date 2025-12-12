package Bigbasket;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BrowserWindowHandling {

    WebDriver driver = new ChromeDriver();

    @Test
    public void handleWindows() throws InterruptedException
    {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2000));
        Thread.sleep(2000);
        String parentWindow = driver.getWindowHandle();
        driver.findElement(By.xpath("//a[contains(@href,'youtube')]")).click();
        driver.findElement(By.xpath("//a[contains(@href,'twitter')]")).click();
        driver.findElement(By.xpath("//a[contains(@href,'linkedin')]")).click();
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
