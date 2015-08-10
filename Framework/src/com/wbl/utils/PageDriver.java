package com.wbl.utils;

import org.apache.commons.logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Created by svelupula on 8/8/2015.
 */
public class PageDriver {

    private final Configuration _configuration;
    private final Browsers _browser;
    private WebDriver _webDriver;
    private String _mainWindowHandler;

    public PageDriver(Configuration configuration) {
        _configuration = configuration;
        _browser = _configuration.Browser;
    }

    public WebDriver getDriver() {
        if (_webDriver == null) {
            Start();
        }
        return _webDriver;
    }

    public Browsers getBrowser() {
        return _browser;
    }

    public String getMainWindowHandler() {
        return _mainWindowHandler;
    }

    public HtmlElement findElement(String locator) throws Exception {
        try {
            return new HtmlElement(this, getDriver().findElement(WBy.get(locator)));
        } catch (Exception ex) {
            throw ex;
        }
    }

    // Do not throws exceptions, only return null
    public Collection<HtmlElement> findElements(String locator) throws Exception {
        Collection<HtmlElement> elements = null;
        try {
            Collection<WebElement> webElements = getDriver().findElements(WBy.get(locator));
            if (webElements.size() > 0) {
                elements = new ArrayList<HtmlElement>();
            }
            for (WebElement element : webElements) {
                HtmlElement el = new HtmlElement(this, element);
                if (elements != null) elements.add(el);
            }
            return elements;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void quit() {
        if (_webDriver == null) {
            return;
        }

        _webDriver.quit();
        _webDriver = null;

        // TODO: Kill web driver process: chromedriver.exe, IEDriverServer.exe (test regarding should it be done on start)
    }

    public void open(String url) {
        _webDriver.navigate().to(url);
        implicitWait();
    }

    public void implicitWait() throws Exception {
        if (_browser != Browsers.HtmlUnit) {
            _webDriver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
            return;
        }
        Thread.sleep(2000);
    }

    public String GetCurrentUrl() {
        return WebDriver.Url;
    }

    public void SaveScreenshot(string path) {
        ((ITakesScreenshot) WebDriver).GetScreenshot().SaveAsFile(path, ImageFormat.Jpeg);
    }

    public object ExecuteJavaScript(string javaScript, params object[]args) {
        var javaScriptExecutor = (IJavaScriptExecutor) WebDriver;

        return javaScriptExecutor.ExecuteScript(javaScript, args);
    }

    public string GetDescription() {
        return "Browser";
    }

    private void Start() {
        switch (BrowserType) {
            case Browsers.InternetExplorer:
                _webDriver = StartInternetExplorer();
                break;
            case Browsers.Firefox:
                _webDriver = StartFirefox();
                break;
            case Browsers.Chrome:
                _webDriver = StartChrome();
                break;
            case Browsers.SimpleBrowser:
                _webDriver = StartSimpleBrowser();
                break;
            default:
                throw new Exception(string.Format("Unknown browser selected: {0}.", _configuration.Browser));
        }
        if (_browser != Browsers.SimpleBrowser) {
            _webDriver.Manage().Window.Maximize();
            _webDriver.Manage().Cookies.DeleteAllCookies();
        }
        _mainWindowHandler = _webDriver.CurrentWindowHandle;
    }

    private InternetExplorerDriver StartInternetExplorer() {
        var internetExplorerOptions = new InternetExplorerOptions
        {
            IntroduceInstabilityByIgnoringProtectedModeSettings = true,
                    InitialBrowserUrl = "about:blank",
                    EnableNativeEvents = true,
                    IgnoreZoomLevel = true
        } ;

        return new InternetExplorerDriver(Directory.GetCurrentDirectory(), internetExplorerOptions);
    }

    private FirefoxDriver StartFirefox() {
        var firefoxProfile = new FirefoxProfile
        {
            AcceptUntrustedCertificates = true,
                    EnableNativeEvents = true
        } ;

        return new FirefoxDriver(firefoxProfile);
    }

    private ChromeDriver StartChrome() {
        var chromeOptions = new ChromeOptions();
        //var defaultDataFolder = Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData) + @"\..\Local\Google\Chrome\User Data\Default";
        return new ChromeDriver(Directory.GetCurrentDirectory(), chromeOptions);
    }

    private SimpleBrowserDriver StartSimpleBrowser() {
        var b = new Browser();
        return new SimpleBrowserDriver(new BrowserWrapper(b));
    }

}
