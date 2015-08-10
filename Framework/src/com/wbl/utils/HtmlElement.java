package com.wbl.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

/**
 * Created by svelupula on 8/8/2015.
 */
public class HtmlElement implements ElementsContainer {

    private final PageDriver _browser;
    private final WebElement _element;

    public HtmlElement(PageDriver browser, WebElement element) {
        _element = element;
        _browser = browser;
    }

    public boolean isDisplayed() {
        return _element.isDisplayed();
    }

    public String getValue() {
        return _element.getAttribute("value");
    }

    public String getCssClass() {
        return _element.getAttribute("class");
    }

    public String getText() {
        return _element.getText();
    }

    public void type(String text) {
        if (text == null) {
            return;
        }
        clear();
        _element.sendKeys(text);
        //Value.Should().BeEquivalentTo(text, "should be similar to entered text");
    }

    public void clear() {
        _element.clear();
    }

    public void pressEnter() {
        _element.sendKeys(Keys.RETURN);
    }

    public void click() {
        _element.click();
            /*if (useJavaScript && _element.TagName != TagNames.Link)
            {
                _browser.ExecuteJavaScript(string.Format("$(arguments[0]).{0}();", JavaScriptEvents.Click), _element);
            }
            else
            {
                // Possible: _element.Click(); or _element.SendKeys(Keys.Enter);
                new Actions(_browser.WebDriver).MoveToElement(_element).MoveByOffset(5, 5).Click().Build().Perform();
            }*/
    }

    public void focus() {
        if (_element.getTagName().equals("input")) {
            _element.sendKeys("");
        } else {
            new Actions(_browser.getDriver()).moveToElement(_element).perform();
        }
    }

    public String getDescription() {
        return String.format("<{0} class=\"{1}\" />", _element.getTagName(), getCssClass());
    }

}
