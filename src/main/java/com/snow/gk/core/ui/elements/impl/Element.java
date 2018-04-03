package com.snow.gk.core.ui.elements.impl;

import com.google.common.base.Function;
import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.log.Assert;
import com.snow.gk.core.log.Logger;
import com.snow.gk.core.ui.drivers.DriverSetup;
import com.snow.gk.core.ui.elements.IElement;
import com.snow.gk.core.utils.Config;
import com.snow.gk.core.utils.Constants;
import com.snow.gk.core.utils.JSExecutor;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.Assertion;

import java.time.Duration;

import static com.snow.gk.core.utils.JSExecutor.getJavaScriptExec;

public class Element implements IElement {
    protected WebElement element;
    protected WebDriver driver;
    public String elementOriginalName = "";
    protected long duration = Long.parseLong(Config.getConfig().getConfigProperty(Constants.ELEMENTWAITTIME));

    public Element(){
        getInitializedDriver();
    }

    public Element(WebElement element) {
        this.element = element;
        getInitializedDriver();
    }

    public Element(WebElement element, String elementName) {
        this.element = element;
        elementOriginalName = elementName;
        getInitializedDriver();
    }

    private void getInitializedDriver() {
        driver = DriverSetup.getDriver();
    }

    @Override
    public WebElement getElement() {
        return element;
    }

    @Override
    public boolean waitforPageLoadJS() {
        return new WebDriverWait(driver, 10000)
                .until(new java.util.function.Function<WebDriver, Boolean>() {
                    public Boolean apply(WebDriver webDriver) {
                        return ("complete").equals(getJavaScriptExec().executeScript("return document.readyState"));
                    }
                });
    }

    @Override
    public WebElement waitForPresent() {
        try {
            return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(duration))
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
                    .pollingEvery(Duration.ofSeconds(1))
                    .until(elementEnabled());
        } catch (Exception e) {
            throw new CustomException("Element: "+element+" not found " + e);
        }
    }

    public Function<WebDriver, WebElement> elementEnabled() {
        return new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver input) {
                boolean isPresent = element.isDisplayed() && element.isEnabled();
                if (isPresent) {
                    return element;
                } else {
                    return null;
                }
            }
        };
    }

    @Override
    public boolean click() {
        try {
            if(waitForPresent() != null) {
                element.click();
                return true;
            } else {
                Logger.error(Constants.FORMATTER + Constants.ELEMENTLOGMESSAGE + elementOriginalName + Constants.ISLOADEDLOGMESSAGE_FAILURE);
                return false;
            }
        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getText() of Element class");
            return false;
        }
    }

    @Override
    public boolean isElementLoaded() {
        try {
            boolean flag = false;
            if (waitForPresent() != null) {
                flag = true;
                Logger.info(Constants.ELEMENTLOGMESSAGE + elementOriginalName + " loaded");
            } else {
                Assert.assertFalse(true, Constants.ELEMENTLOGMESSAGE + elementOriginalName + Constants.ISLOADEDLOGMESSAGE_FAILURE);
            }
            return flag;
        } catch (CustomException ex) {
            Logger.error(Constants.FORMATTER + Constants.ELEMENTLOGMESSAGE + elementOriginalName + Constants.ISLOADEDLOGMESSAGE_FAILURE);
            throw new CustomException("Element: " + elementOriginalName + " not loaded"+ex);
        }
    }

    @Override
    public boolean jsClick() {
        try {
            if (isLoaded()) {
                JSExecutor.jsClick(element);
                return true;
            } else {
                Logger.error(Constants.FORMATTER + Constants.ELEMENTLOGMESSAGE + elementOriginalName + Constants.ISLOADEDLOGMESSAGE_FAILURE);
                return false;
            }
        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getText() of Element class");
            return false;
        }
    }

    public boolean isLoaded() {
        try {
            boolean flag = false;
            if (waitForPresent() != null) {
                flag = true;
                Logger.info(Constants.ELEMENTLOGMESSAGE + elementOriginalName + " loaded");
            } else {
                Assert.assertFalse(true, Constants.ELEMENTLOGMESSAGE + elementOriginalName + Constants.ISLOADEDLOGMESSAGE_FAILURE);
            }
            return flag;
        } catch (CustomException ex) {
            Logger.error(Constants.FORMATTER + Constants.ELEMENTLOGMESSAGE + elementOriginalName + Constants.ISLOADEDLOGMESSAGE_FAILURE);
            throw new CustomException("Element: " + elementOriginalName + " not loaded " + ex);
        }
    }

    @Override
    public boolean isDisplayed() {
        boolean flag = false;
        try {
            if (element.isDisplayed()) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " isDisplayed() of Element class");
            return false;
        }
        return flag;
    }

    @Override
    public String getText() {
        try {
            if (isLoaded()) {
                String text = null;
                if (getAttribute("value") != null)
                    text = getAttribute("value");
                Logger.info(Constants.ELEMENTLOGMESSAGE + elementOriginalName + " get text successfully");
                return text;
            } else {
                Logger.error(Constants.FORMATTER + Constants.ELEMENTLOGMESSAGE + elementOriginalName + " failed to get text");
                throw new CustomException("Element Element: " + elementOriginalName + " not loaded in method getText()");
            }
        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getText() of Element class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getText() of Element class." + fe);
        }
    }

    @Override
    public String getAttribute(String value) {
        try {
            return java.util.Optional.ofNullable(getElement().getAttribute("value")).orElse("");
        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getAttribute() of Element class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getAttribute() of Element class." + fe);
        }
    }

    @Override
    public boolean isEditable() throws CustomException {
        return element.isEnabled();
    }

}
