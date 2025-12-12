package Bigbasket;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;

public class RelativeLocators {

    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();
        Thread.sleep(2000);
        driver.findElement(RelativeLocator.with(By.xpath("//input")).below(By.xpath("//label[text()='Password']"))).sendKeys("gowtham");
         // to left of , to right of , above , below , near

    }
}
