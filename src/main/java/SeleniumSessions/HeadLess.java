package SeleniumSessions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class HeadLess {

    public static void main(String[] args) {

        // Faster
        // no browser visibility
        // recommend : Not recommended
        // problems : its not an actual user simulatiom
        // not fit for complex apps , alerts / pop up

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        options.addArguments("--incognito");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.flipkart.com/");
        System.out.println(driver.getTitle());
        driver.quit();
    }

}
