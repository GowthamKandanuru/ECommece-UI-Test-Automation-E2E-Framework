package com.ui.opencart.utils;

import SeleniumSessions.ElementException;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ElementUtil {

    private static final String DEFAULT_ELEMENT_TIME_OUT_MESSAGE = "Time out... Element is not found...";
    private final String DEFAULT_ALERT_TIME_OUT_MESSAGE = "Time out... Alert is not found...";
    private final WebDriver driver;
    private Actions actions;

    Logger LOGGER = LogManager.getLogger(ElementUtil.class);

    public ElementUtil(WebDriver driver)
    {
        this.driver = driver;
        actions = new Actions(driver);
    }

    private void nullBlankCheck(String value)
    {
        if(value == null || value.length() == 0)
        {
            throw new ElementException(value+" --- Text cannot be null or blank");
        }
    }
    private By getByLocator(String locatorType,String locator)
    {
        By by = null;
        switch (locatorType.trim().toLowerCase()) {
            case "xpath" -> by = By.xpath(locator);
            case "id" -> by = By.id(locator);
            case "css" -> by = By.cssSelector(locator);
            default -> {
            }
        }
        return  by;
    }
    public WebElement getElement(String locatorType, String locator)
    {
        WebElement element = null;
        try {
            element = driver.findElement(getByLocator(locatorType,locator));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border = ‘3px solid red'", element);
        }catch (NoSuchElementException e)
        {
            LOGGER.info("Element is not present on the page");
            e.printStackTrace();
            return null;
        }
        return element;
    }
    @Step("Getting web element using locator {0}")
    public WebElement getElement(By locator)
    {
        WebElement element = null;
        try {
            element = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border = '3px solid red'", element);
            // element = waitForElementVisible(locator, 60);
        }catch (NoSuchElementException e)
        {
            LOGGER.info("Element is not present on the page");
            e.printStackTrace();
            return null;
        }
        return element;
    }
    public List<WebElement> getElements(By locator)
    {
        return driver.findElements(locator);
       // return waitForElementsVisible(locator, 60);
    }
    public List<WebElement> getElements(String locatorType,String locator)
    {
        return driver.findElements(getByLocator(locatorType, locator));
    }
    public void doSendKeys(String locatorType, String locator, String searchText)
    {
        getElement(locatorType,locator).sendKeys(searchText);
    }
    public String getPageTitle()
    {
        return driver.getTitle();
    }
    public void clearTextbox(By locator)
    {
        getElement(locator).clear();
    }
    public  void doSendKeys(By locator, String searchText)
    {
        getElement(locator).sendKeys(searchText);
    }
    public void doClick(By locator)
    {
        getElement(locator).click();
    }
    public void doClick(String locatorType,String locator)
    {
        getElement(locatorType, locator).click();
    }
    public String getElementText(String locatorType,String locator)
    {
        return getElement(locatorType, locator).getText();
    }
    public String  getElementText(By locator)
    {
        return getElement(locator).getText();
    }
    public boolean checkElementDisplayed(By locator)
    {
        return getElement(locator).isDisplayed();
    }
    public boolean checkElementDisplayed(String locatorType,String locator)
    {
        return getElement(locatorType, locator).isDisplayed();
    }
    public boolean isElementExist(By locator)
    {
        boolean flag =false;
        if( getElements(locator).size() == 1)
        {
            return true;
        }else {
            return flag;
        }
    }
    public boolean isElementExist(String locatorType,String locator)
    {
        boolean flag =false;
        if( getElements(locatorType,locator).size() == 1)
        {
            return true;
        }else {
            return flag;
        }
    }
    public WebElement getElementUsingBelowLocator(By helpLocator , By locator)
    {
        return driver.findElement(RelativeLocator.with(locator).above(helpLocator));
    }
    public boolean isElementExistUsingBelowLocator(By helpLocator , By locator) {

        if(getElementUsingBelowLocator(helpLocator, locator).isDisplayed())
        {
            return true;
        }else {
            return false;
        }
    }
    public boolean multipleElementExist(String locatorType,String locator)
    {
        boolean flag =false;
        if( getElements(locatorType,locator).size() > 0)
        {
            return true;
        }else {
            return flag;
        }
    }
    public boolean multipleElementExist(By locator)
    {
        boolean flag =false;
        if( getElements(locator).size() > 0)
        {
            return true;
        }else {
            return flag;
        }
    }
    public boolean isElementEnabled(By locator)
    {
        return getElement(locator).isEnabled();
    }
    public boolean isElementSelected(By locator)
    {
        return getElement(locator).isSelected();
    }
    public boolean isElementEnabled(String locatorType,String locator)
    {
        return getElement(locatorType,locator).isEnabled();
    }
    public boolean isElementSelected(String locatorType,String locator)
    {
        return getElement(locatorType,locator).isSelected();
    }
    public String doElementGetAttribute(String locatorType,String locator,String attrName)
    {
        return getElement(locatorType, locator).getDomProperty(attrName);
    }
    public String doElementGetAttributeInOld(String locatorType,String locator,String attrName)
    {
        return getElement(locatorType, locator).getAttribute(attrName);
    }

    public String doElementGetDOMAttributeInOld(String locatorType,String locator,String attrName)
    {
        return getElement(locatorType, locator).getDomAttribute(attrName);
    }

    public String doElementGetAttribute(By locator,String attrName)
    {
        return getElement(locator).getDomProperty(attrName);
    }

    public String doElementGetAttributeInOld(By locator,String attrName)
    {
        return getElement(locator).getAttribute(attrName);
    }

    public String doElementGetDOMAttributeInOld(By locator,String attrName)
    {
        return getElement(locator).getDomAttribute(attrName);
    }
    public int getElementsCount(By locator)
    {
        return getElements(locator).size();
    }
    public int getElementsCount(String locatorType,String locator)
    {
        return getElements(locatorType,locator).size();
    }
    public LinkedList<String> getElementsTextList(By locator)
    {
        LinkedList<String> elementText = new LinkedList<>();
        List<WebElement> elementList = getElements(locator);

        for(WebElement element : elementList)
        {
            String text = element.getText();
            if(text.length()!=0)
            {
                elementText.add(text);
            }
        }
        return elementText;
    }
    public void doClickElementInDropdown(By locator,String text)
    {
        LinkedList<String> elementText = new LinkedList<>();
        List<WebElement> elementList = getElements(locator);

        for(WebElement element : elementList)
        {
            if(element.getText().equals(text))
            {
                element.click();
                break;
            }
        }
    }
    public void doClickElementFromList(String  locatorType,String locator,String text)
    {
        LinkedList<String> elementText = new LinkedList<>();
        List<WebElement> elementList = getElements(locatorType, locator);

        for(WebElement element : elementList)
        {
            if(element.getText().equals(text))
            {
                element.click();
                break;
            }
        }
    }
    public LinkedList<String> getElementsTextList(String locatorType,String locator)
    {
        LinkedList<String> elementText = new LinkedList<>();
        List<WebElement> elementList = getElements(locatorType,locator);

        for(WebElement element : elementList)
        {
            String text = element.getText();
            if(text.length()!=0)
            {
                elementText.add(text);
            }
        }
        return elementText;
    }
    public LinkedList<String> getElementsAttributeList(String locatorType,String locator,String attrName)
    {
        LinkedList<String> elementAttrText = new LinkedList<>();
        List<WebElement> elementList = getElements(locatorType,locator);

        for(WebElement element : elementList)
        {
            String text = element.getDomProperty(attrName);
            if(text.length()!=0)
            {
                elementAttrText.add(text);
            }
        }
        return elementAttrText;
    }
    public LinkedList<String> getElementsAttributeList(By locator,String attrName)
    {
        LinkedList<String> elementAttrText = new LinkedList<>();
        List<WebElement> elementList = getElements(locator);

        for(WebElement element : elementList)
        {
            String text = element.getDomProperty(attrName);
            if(text.length()!=0)
            {
                elementAttrText.add(text);
            }
        }
        return elementAttrText;
    }
    public void doSelectByVisibleText(By locator,String visibleText)
    {
        nullBlankCheck(visibleText);
        Select select = new Select(getElement(locator));
        select.selectByVisibleText(visibleText);
    }
    public void doSelectByVisibleText(String locatorType,String locator,String visibleText)
    {
        nullBlankCheck(visibleText);
        Select select = new Select(getElement(locatorType,locator));
        select.selectByVisibleText(visibleText);
    }
    public void doSelectByIndex(By locator,int elementPosition)
    {
        Select select = new Select(getElement(locator));
        select.selectByIndex(elementPosition);
    }
    public void doSelectByIndex(String locatorType,String locator,int elementPosition)
    {
        Select select = new Select(getElement(locatorType,locator));
        select.selectByIndex(elementPosition);
    }
    public void doSelectByValue(By locator,String value)
    {
        nullBlankCheck(value);
        Select select = new Select(getElement(locator));
        select.selectByValue(value);
    }
    public void doSelectByValue(String locatorType,String locator,String value)
    {
        nullBlankCheck(value);
        Select select = new Select(getElement(locatorType,locator));
        select.selectByValue(value);
    }
    public ArrayList<String> doGetDropdownValues(By locator)
    {
        ArrayList<String> dropdownText = new ArrayList<>();
        Select select = new Select(getElement(locator));
        List<WebElement> elementList = select.getOptions();
        for(WebElement element: elementList)
        {
            dropdownText.add(element.getText());
        }
        return dropdownText;
    }
    public int doGetDropdownCount(By locator)
    {
        ArrayList<String> dropdownText = new ArrayList<>();
        Select select = new Select(getElement(locator));
        List<WebElement> elementList = select.getOptions();
        return elementList.size();
    }
    public ArrayList<String> doGetDropdownValues(String locatorType,String locator)
    {
        ArrayList<String> dropdownText = new ArrayList<>();
        Select select = new Select(getElement(locatorType,locator));
        List<WebElement> elementList = select.getOptions();
        for(WebElement element: elementList)
        {
            dropdownText.add(element.getText());
        }
        return dropdownText;
    }
    public int doGetDropdownCount(String locatorType,String locator)
    {
        ArrayList<String> dropdownText = new ArrayList<>();
        Select select = new Select(getElement(locatorType,locator));
        List<WebElement> elementList = select.getOptions();
        return elementList.size();
    }
    public void doSelectTextFromDropdown(By locator,String value)
    {
        ArrayList<String> dropdownText = new ArrayList<>();
        Select select = new Select(getElement(locator));
        List<WebElement> elementList = select.getOptions();
        for(WebElement element: elementList)
        {
            if(element.getText().equals(value))
            {
                element.click();
                break;
            }
        }
    }
    public void moveToElement(By locator)
    {
        actions.moveToElement(getElement(locator)).perform();
    }
    public void doSelectTextFromDropdown(String locatorType,String locator,String value)
    {
        ArrayList<String> dropdownText = new ArrayList<>();
        Select select = new Select(getElement(locatorType, locator));
        List<WebElement> elementList = select.getOptions();
        for(WebElement element: elementList)
        {
            if(element.getText().equals(value))
            {
                element.click();
                break;
            }
        }
    }
    public void doActionSendKeys(By locator,String value)
    {
        nullBlankCheck(value);
        actions.sendKeys(getElement(locator), value).perform();
    }
    public void doActionClick(By locator)
    {
        actions.click(getElement(locator)).perform();
    }
    public void scrollToElementUsingAction(By locator)
    {
        actions.scrollToElement(getElement(locator)).perform();
    }

    public void handleMenuSubMenuLevel2(By parentMenuLocator, By SubMenuLocator) throws InterruptedException {
        Actions act = new Actions(driver);
        act.moveToElement(getElement(parentMenuLocator)).perform();
        Thread.sleep(1500);
        doClick(SubMenuLocator);

    }

    public void handleMenuSubMenuLevel4(By level1Menu, By level2Menu, By level3Menu, By level4Menu)
            throws InterruptedException {

        doClick(level1Menu);
        Thread.sleep(1500);
        Actions act = new Actions(driver);
        act.moveToElement(getElement(level2Menu)).perform();
        Thread.sleep(1500);
        act.moveToElement(getElement(level3Menu)).perform();
        Thread.sleep(1500);
        doClick(level4Menu);

    }

    public void handleMenuSubMenuLevel4MouseHover(By level1Menu, By level2Menu, By level3Menu, By level4Menu)
            throws InterruptedException {

        Actions act = new Actions(driver);

        act.moveToElement(getElement(level1Menu)).perform();
        Thread.sleep(1500);
        act.moveToElement(getElement(level2Menu)).perform();
        Thread.sleep(1500);
        act.moveToElement(getElement(level3Menu)).perform();
        Thread.sleep(1500);
        doClick(level4Menu);
    }

    // ******************Wait utils*********//

    /**
     * An expectation for checking an element is visible and enabled such that you
     * can click it.
     *
     * @param locator
     * @param timeOut
     */
    public void clickWhenReady(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        //checkHighlight(element);
        element.click();
    }

    /**
     * An expectation for checking that an element is present on the DOM of a page.
     * This does not necessarily mean that the element is visible.
     *
     * @param locator
     * @param timeOut
     * @return
     */
    public WebElement waitForElementPresence(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        WebElement element =  wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        //checkHighlight(element);
        return element;
    }

    /**
     * An expectation for checking that an element is present on the DOM of a page
     * and visible. Visibility means that the element is not only displayed but also
     * has a height and width that is greater than 0.
     *
     * @param locator
     * @param timeOut default interval time = 500 ms
     * @return
     */
   // @Step("waiting for element using locator : {0} within timeout {1}")
    public WebElement waitForElementVisible(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
       // checkHighlight(element);
        return element;
    }

    public WebElement waitForElementVisible(By locator, int timeOut, int intervalTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        //checkHighlight(element);
        return element;
    }

    /**
     * An expectation for checking that all elements present on a
     * web page.
     *
     * @param locator
     * @param timeOut
     * @return
     */
    public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public List<WebElement> waitForElementsPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
                .pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
                .withMessage(DEFAULT_ELEMENT_TIME_OUT_MESSAGE);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    /**
     * An expectation for checking that all elements present on the web page that
     * match the locator are visible. Visibility means that the elements are not
     * only displayed but also have a height and width that is greater than 0.
     */
    public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public String waitForTitleContains(String titleFraction, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

        try {
            if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
                return driver.getTitle();
            }
        } catch (Exception e) {
            System.out.println("title is not found within : " + timeOut);
        }
        return null;

    }

  //  @Step("waiting for the expected title...")
    public String waitForTitleIs(String title, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        LOGGER.info(driver.getTitle());
        try {
            if (wait.until(ExpectedConditions.titleIs(title))) {
                return driver.getTitle();
            }
        } catch (Exception e) {
           // System.out.println("title is not found within : " + timeOut);
            throw new ElementException("title is not found within : " + timeOut);
        }
        return driver.getTitle();

    }

  //  @Step("waiting for the expected url...")
    public String waitForURLContains(String urlFraction, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

        try {
            if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
                return driver.getCurrentUrl();
            }
        } catch (Exception e) {
            System.out.println("url fraction is not found within : " + timeOut);
        }
        return driver.getCurrentUrl();

    }

    public String waitForURLIs(String url, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

        try {
            if (wait.until(ExpectedConditions.urlToBe(url))) {
                return driver.getCurrentUrl();
            }
        } catch (Exception e) {
            System.out.println("url is not found within : " + timeOut);
        }
        return driver.getCurrentUrl();

    }

    public Alert waitForJSAlertWithFluentWait(int timeOut, int pollingTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
                .pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoAlertPresentException.class)
                .withMessage(DEFAULT_ALERT_TIME_OUT_MESSAGE);
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public Alert waitForJSAlert(int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public String getAlertText(int timeOut) {
        return waitForJSAlert(timeOut).getText();
    }

    public void acceptAlert(int timeOut) {
        waitForJSAlert(timeOut).accept();
    }

    public void dismissAlert(int timeOut) {
        waitForJSAlert(timeOut).dismiss();
    }

    public void alertSendKeys(int timeOut, String value) {
        waitForJSAlert(timeOut).sendKeys(value);
    }

    public boolean waitForWindow(int totalNumberOfWindowsToBe, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.numberOfWindowsToBe(totalNumberOfWindowsToBe));
    }

    public void waitForFrameAndSwitchToIt(By frameLocator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    public void waitForFrameAndSwitchToIt(int frameIndex, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    }

    public void waitForFrameAndSwitchToIt(WebElement frameElement, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
    }

    public WebElement retryingElement(By locator, int timeOut) {

        WebElement element = null;
        int attempts = 0;

        while (attempts < timeOut) {

            try {
                element = getElement(locator);
                System.out.println("element is found...." + locator + " in attempt " + attempts);
                break;
            } catch (NoSuchElementException e) {
                System.out.println("element is not found.... " + " in attempts " + attempts);
                TimeUtil.defaultTime();
            }
            attempts++;
        }

        if (element == null) {
            System.out.println("element is not found.....tried for " + timeOut + " times " + " with the interval of "
                    + 500 + " milliseconds...");
            throw new ElementException("No Such Element");
        }

        //checkHighlight(element);
        return element;

    }

    public WebElement retryingElement(By locator, int timeOut, int intervalTime) {

        WebElement element = null;
        int attempts = 0;

        while (attempts < timeOut) {

            try {
                element = getElement(locator);
                System.out.println("element is found...." + locator + " in attempt " + attempts);
                break;
            } catch (NoSuchElementException e) {
                System.out.println("element is not found.... " + " in attempts " + attempts);
                TimeUtil.applyWait(intervalTime);
            }
            attempts++;

        }

        if (element == null) {
            System.out.println("element is not found.....tried for " + timeOut + " times " + " with the interval of "
                    + intervalTime + " seconds...");
            throw new ElementException("No Such Element");
        }
      //  checkHighlight(element);
        return element;
    }
}
