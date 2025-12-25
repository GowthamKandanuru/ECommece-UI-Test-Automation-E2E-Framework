package com.ui.opencart.factory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.*;
import java.util.Properties;

public class OptionsManager {

    private ChromeOptions co;
    private FirefoxOptions fo;
    private EdgeOptions eo;
    private Properties properties;

    public OptionsManager(Properties properties) {
        this.properties = properties;
    }

    public ChromeOptions getChromeOptions() {
        co = new ChromeOptions();

        if (Boolean.parseBoolean(properties.getProperty("remote").trim())) {
            co.setCapability("browserName", "chrome");
        }
        if (Boolean.parseBoolean(properties.getProperty("headless").trim())) {
            co.addArguments("--headless");
        }
        if (Boolean.parseBoolean(properties.getProperty("incognito").trim())) {
            co.addArguments("--incognito");
        }
        co.addArguments("--guest");
        return co;
    }

    public FirefoxOptions getFireFoxOptions() {
        fo = new FirefoxOptions();

        if (Boolean.parseBoolean(properties.getProperty("remote").trim())) {
            fo.setCapability("browserName", "firefox");
        }
        if (Boolean.parseBoolean(properties.getProperty("headless").trim())) {
            fo.addArguments("--headless");
        }
        if (Boolean.parseBoolean(properties.getProperty("incognito").trim())) {
            fo.addArguments("--incognito");
        }
        return fo;
    }

    public EdgeOptions getEdgeOptions() {
        eo = new EdgeOptions();
        if (Boolean.parseBoolean(properties.getProperty("remote"))) {
            eo.setCapability("browserName", "edge");
        }
        if (Boolean.parseBoolean(properties.getProperty("headless").trim())) {
            System.out.println("Running edge in headless mode");
            eo.addArguments("--headless");
        }
        if (Boolean.parseBoolean(properties.getProperty("incognito").trim())) {
            eo.addArguments("--inprivate");
        }

        return eo;
    }
}
