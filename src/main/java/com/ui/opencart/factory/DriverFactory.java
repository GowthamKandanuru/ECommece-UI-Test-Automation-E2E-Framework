package com.ui.opencart.factory;

import com.ui.opencart.exceptions.BrowserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverFactory {

    Logger logger = LogManager.getLogger(DriverFactory.class);

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

    OptionsManager optionsManager;
    Properties properties;

    public WebDriver initDriver(Properties props) {
        String browserName = props.getProperty("browser").toLowerCase().trim();
        optionsManager = new OptionsManager(props);
        logger.info("browser name: " + browserName);
        switch (browserName) {
            case "chrome" -> {
                if (Boolean.parseBoolean(props.getProperty("remote"))) {
                    initRemoteDriver(browserName);
                } else {
                    tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
                }
            }
            case "firefox" -> {
                if (Boolean.parseBoolean(props.getProperty("remote"))) {
                    initRemoteDriver(browserName);
                } else {
                    tlDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
                }
            }
            case "edge" -> {
                if (Boolean.parseBoolean(props.getProperty("remote"))) {
                    initRemoteDriver(browserName);
                } else {
                    tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
                }
            }
            default -> {
                logger.info("Invalid browser name: " + browserName);
                throw new BrowserException("BROWSER NOT FOUND");
            }
        }
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().get(props.getProperty("url"));
        return getDriver();
    }

    /**
     * @param browserName
     */
    private void initRemoteDriver(String browserName) {
        logger.info("Running tests on the remote grid on browser: {}", browserName);
        try {
            switch (browserName) {
                case "chrome" ->
                        tlDriver.set(new RemoteWebDriver(new URL(properties.getProperty("huburl")), optionsManager.getChromeOptions()));
                case "firefox" ->
                        tlDriver.set(new RemoteWebDriver(new URL(properties.getProperty("huburl")), optionsManager.getFireFoxOptions()));
                case "edge" ->
                        tlDriver.set(new RemoteWebDriver(new URL(properties.getProperty("huburl")), optionsManager.getEdgeOptions()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    /**
     * @return
     */
    public Properties initprop() {
        FileInputStream fis = null;
        properties = new Properties();
        //mvn clean install -Denv="qa"
        String environment = System.getProperty("env");
        try {
            if (environment == null) {
                logger.info("No environment information provided");
                fis = new FileInputStream("./src/test/resources/config/config.prod.properties");
                properties.load(fis);
                logger.info("Running test on Env: PROD");
            } else {
                    /*if(environment.trim().equalsIgnoreCase("prod"))
                    {
                        environment = "";
                    }else {
                        environment += ".";
                    }*/
                fis = new FileInputStream("./src/test/resources/config/config." + environment + ".properties");
                properties.load(fis);
                logger.info("Running test on Env: {}", environment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}
