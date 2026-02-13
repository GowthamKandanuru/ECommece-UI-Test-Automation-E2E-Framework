package SeleniumSessions;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.KeyInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class Practice {

    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://selectorshub.com/xpath-practice-page/");
        driver.manage().window().maximize();
        String parent = driver.getWindowHandle();
      /*Set<String> s = driver.getWindowHandles();
        Iterator<String> it = s.iterator();
        while(it.hasNext())
        {
            String w = it.next();
        }*/
        Thread.sleep(2000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
       /* WebElement e = driver.findElement(By.xpath("//button[text()='Checkout here']"));
        js.executeScript("arguments[0].scrollIntoView(true)",e);
        Actions a = new Actions(driver);
      Action action =  a.moveToElement(e).click(driver.findElement(By.xpath("//a[text()='Try TestCase Studio']"))).build();
      action.perform();
      Thread.sleep(2000);*/
        WebElement windowAlert = driver.findElement(By.xpath("//button[text()='Click To Open Window Prompt Alert']"));
        js.executeScript("arguments[0].scrollIntoView(true)",windowAlert);
        Point loc = windowAlert.getLocation();
        int xco = loc.getX();
        int yco = loc.getY();
        Actions actions = new Actions(driver);
        actions.moveToElement(windowAlert).perform();
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(60)).pollingEvery(Duration.ofSeconds(2))
                .ignoring(ElementClickInterceptedException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Checkout here']")));
        windowAlert.click();
        Alert e = driver.switchTo().alert();
        e.accept();
        WebElement brokenlink = driver.findElement(By.xpath("//a[text()='This is a broken link']"));
        try {
            URL url = new URL(brokenlink.getAttribute("href"));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            int status_code = httpURLConnection.getResponseCode();
            if(status_code != 200)
            {
                System.out.println(status_code+" Link is broken");
            }
        } catch (IOException ex) {
           System.out.println(ex);
        }
        WebElement webElement = driver.findElement(By.xpath("practice iframe and nested iframe scenarios."));
        actions.moveToElement(webElement).click().build().perform();

      /*js.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//h6[text()='Shadow DOM']")));
      String script = "document.querySelector(\"#userName\").shadowRoot.querySelector(\"#kils\")";
      WebElement web = (WebElement) js.executeScript(script);
      web.sendKeys("gowtham");
      Thread.sleep(2000);*/
      Set<String> s = driver.getWindowHandles();
        Iterator<String> it = s.iterator();
        while(it.hasNext())
        {
            String w = it.next();
            if(!w.equals(parent))
            {
                driver.switchTo().window(w);
            }
        }
        driver.switchTo().frame("pact1");
        driver.findElement(By.xpath("//input[@title='Enter your first crush name']")).sendKeys("This is Iframe");
       // js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
        /*Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(12)).pollingEvery(Duration.ofSeconds(34))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Checkout here']")));*/

    }
}
