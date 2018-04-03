package com.snow.gk.core.utils;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.ui.drivers.DriverSetup;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class JSExecutor {
    private JSExecutor(){}

    public static void jsExecutor(String value) throws CustomException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) DriverSetup.getDriver();
            js.executeScript(value);
        } catch (WebDriverException we) {
            throw new CustomException("Failed in jsExecutor method "+we);
        }
    }

    public static void jsClick(WebElement element){
        getJavaScriptExec().executeScript("arguments[0].click();", element);
        Waits.waitForPageLoadJS();
    }

    public static JavascriptExecutor getJavaScriptExec() {
        return (JavascriptExecutor) DriverSetup.getDriver();
    }

    // Get Value using Java Script Executor
    public static String jsGetValue(WebElement element){
        return getJavaScriptExec().executeScript("return arguments[0].value;",element).toString();
    }
}
