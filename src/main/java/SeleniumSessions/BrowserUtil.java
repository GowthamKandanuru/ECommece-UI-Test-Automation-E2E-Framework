package SeleniumSessions;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

//static cant use on the launch browser as in real time parallel execution is not possible
public class BrowserUtil {
    private RemoteWebDriver driver;
    /**
     * This method helps to launch browser
     *
     */
    public RemoteWebDriver launchBrowser(String browserName)
    {
        System.out.println("browser name: "+ browserName);

        switch (browserName.trim().toLowerCase())
        {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Invalid browser name: "+browserName);
                throw new RuntimeException("BROWSER NOT FOUND");
        }
        driver.manage().window().maximize();
        return driver;
    }

    public void launchUrl(String url)
    {
        if(url == null)
        {
            throw new RuntimeException("URL IS NULL");
        }
        if(url.indexOf("http") == 0)
        {
         //  driver.get(url);
            driver.navigate().to(url);
        }else {
            throw new RuntimeException("HTTP IS MISSING IN URL");
        }
    }

    public void closeBrowser()
    {
        driver.close();
    }

    public String getTitle()
    {
        return driver.getTitle();
    }

    public void navigateToForward()
    {
        driver.navigate().forward();
    }
    public void refresh()
    {
        driver.navigate().refresh();
    }
}
