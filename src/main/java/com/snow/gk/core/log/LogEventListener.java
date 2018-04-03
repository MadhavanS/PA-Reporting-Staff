package com.snow.gk.core.log;

import com.snow.gk.core.supers.Components;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.TestListenerAdapter;

/*
* Prints the log of the each event happens in the framework
*
* Below only defined few methods aa the other methods for logs
* */


public class LogEventListener extends TestListenerAdapter implements WebDriverEventListener {
    private By lastFindBy;

    public void beforeAlertAccept(WebDriver driver) {

    }

    public void afterAlertAccept(WebDriver driver) {

    }

    public void afterAlertDismiss(WebDriver driver) {

    }

    public void beforeAlertDismiss(WebDriver driver) {

    }

    public void beforeNavigateTo(String url, WebDriver driver) {
        Logger.info("WebDriver navigating to:'"+url+"'");
    }

    public void afterNavigateTo(String url, WebDriver driver) {

    }

    public void beforeNavigateBack(WebDriver driver) {

    }

    public void afterNavigateBack(WebDriver driver) {

    }

    public void beforeNavigateForward(WebDriver driver) {

    }

    public void afterNavigateForward(WebDriver driver) {

    }

    public void beforeNavigateRefresh(WebDriver driver) {

    }

    public void afterNavigateRefresh(WebDriver driver) {

    }

    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        lastFindBy = by;
    }

    public void afterFindBy(By by, WebElement element, WebDriver driver) {

    }

    public void beforeClickOn(WebElement element, WebDriver driver) {

    }

    public void afterClickOn(WebElement element, WebDriver driver) {
        String locator = element.toString().split("-> ")[1];
        String elementName = Components.getElementName(locator.substring(0, locator.length() - 1).split(": ")[1]);
        Logger.info("WebDriver clicking on: '"+ (elementName == null? locator: elementName) +"'");
    }

    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }

    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }

    public void beforeScript(String script, WebDriver driver) {

    }

    public void afterScript(String script, WebDriver driver) {

    }

    public void beforeSwitchToWindow(String windowName, WebDriver driver) {

    }

    public void afterSwitchToWindow(String windowName, WebDriver driver) {

    }

    public void onException(Throwable error, WebDriver driver) {
        if (error.getClass().equals(NoSuchElementException.class)){
            Logger.error("WebDriver error: Element not found "+lastFindBy);
        } else if(error.getClass().equals(StaleElementReferenceException.class)){
            Logger.error("Stale element exception:");
        } else if(error.getClass().equals(UnhandledAlertException.class)) {
            Logger.error("Alert exception: ");
        } else {
            Logger.error("WebDriver error:" + error);
        }
    }
}
