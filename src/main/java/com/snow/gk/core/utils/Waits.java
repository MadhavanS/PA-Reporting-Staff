package com.snow.gk.core.utils;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.ui.drivers.DriverSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Waits {
    private static WebDriver driver = DriverSetup.getDriver();
    private Waits(){}

    // Fluent Wait
    public static WebElement componentWait(final WebElement element, long duration) {
        try {
            return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(duration))
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
                    .pollingEvery(Duration.ofSeconds(1))
                    .until(new Function<WebDriver, WebElement>() {
                        @Override
                        public WebElement apply(WebDriver input) {
                            boolean isPresent = element.isDisplayed() && (element.isEnabled() ||
                                    (element.getAttribute("readonly")!= null && (element.getAttribute("readonly").equals("true") ||
                                            element.getAttribute("readonly").equals("readonly")))
                                    || (element.getAttribute("disabled")!=null && element.getAttribute("disabled").equals("true")));
                            if (isPresent) {
                                return element;
                            } else {
                                return null;
                            }

                        }
                    });
        } catch (TimeoutException toe) {
            return null;
        } catch(WebDriverException wex) {
            return null;
        } catch (Exception e) {
            throw new CustomException("Element: "+element+" not found"+e);
        }
    }

    // Wait for the Page to load
    public static boolean waitForPageLoadJS() {
        new WebDriverWait(driver, 10000)
                .until(new java.util.function.Function<WebDriver, Boolean>() {
                    public Boolean apply(WebDriver webDriver) {
                        return ("complete").equals(getJSExec().executeScript("return document.readyState"));
                    }
                });

        return false;
    }

    public static JavascriptExecutor getJSExec() {
        return (JavascriptExecutor) driver;
    }
}
