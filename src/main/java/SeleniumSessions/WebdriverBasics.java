package SeleniumSessions;


import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;


import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WebdriverBasics {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.bigbasket.com/");
        driver.navigate().refresh();
        if (Objects.equals(driver.getTitle(), "Online Grocery Shopping and Online Supermarket in India - bigbasket")) {
            System.out.println("Test Case passed");
        }
        if (Objects.requireNonNull(driver.getCurrentUrl()).equals("https://www.bigbasket.com/")) {
            System.out.println("Test Case passed");
        }
        driver.quit();
    }
}
