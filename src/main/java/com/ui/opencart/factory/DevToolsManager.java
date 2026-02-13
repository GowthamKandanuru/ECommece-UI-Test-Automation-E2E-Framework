package com.ui.opencart.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v139.network.Network;
import org.openqa.selenium.devtools.v139.network.model.RequestPattern;

import java.util.Optional;

public class DevToolsManager {

  static DevTools devTools;
    public static void devToolsSetup(WebDriver driver)
    {
       HasDevTools hasDevTools = (HasDevTools)driver;
       devTools = hasDevTools.getDevTools();
       devTools.createSession();

       devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

        RequestPattern requestPattern = new RequestPattern(Optional.of("*ads*"),Optional.empty(),Optional.empty());

        devTools.send(Network.setBlockedURLs(requestPattern.getUrlPattern().stream().toList()));

    }
}
