package SeleniumSessions;



import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;


import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WebdriverBasics  {

    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.bigbasket.com/");
        driver.navigate().refresh();
        driver.manage().window().maximize();
        String parentWindow = driver.getWindowHandle();
        System.out.println(parentWindow);
        JavascriptExecutor js = (JavascriptExecutor)driver;
       /* js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
        Thread.sleep(2000);
        js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
        Thread.sleep(2000);*/
        js.executeScript("arguments[0].scrollIntoView(true)",driver.findElement(By.xpath("//button[text()='Show more +']")));
        Thread.sleep(3000);
       // js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofMinutes(3))
                        .pollingEvery(Duration.ofSeconds(10))
                                .ignoring(ElementClickInterceptedException.class);
     wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Show more +']")));

         driver.findElement(By.xpath("//button[text()='Show more +']")).click();
     String url = driver.findElement(By.xpath("//ul[@class='flex flex-wrap w-full']/li[20]/a")).getAttribute("href");
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);
        TakesScreenshot screenshot = (TakesScreenshot)driver;
        File testResult = screenshot.getScreenshotAs(OutputType.FILE);
        String destPath = System.getProperty("user.dir") + "/screenshots/" + "image"+System.currentTimeMillis()+".png";
        File destFile = new File(destPath);
        FileUtils.copyFile(testResult, destFile);
    }
}
