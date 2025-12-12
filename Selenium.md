
SearchContext Interface --> findElement and findElements both are abstract methods

WebDriver Interface extends SearchContext --> get(url) , getTitle() , close() , quit() all are abstract methods

Even WebDriver has @Overriden methods such as findElement and findElements

RemoteWebDriver is the Class which implements WebDriver (direct child of webdriver interface)

ChromiumDriver , firefoxDriver , safariDriver are the children to the RemoteDriver

ChromeDriver and EdgeDriver are the children to the ChromiumDriver