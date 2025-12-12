package SeleniumSessions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;

public class LaunchDrivers {

    public static void main(String[] args)
    {
        WebDriver driver = new ChromeDriver();
        driver = new ChromeDriver();

        driver.close();
    }

}
