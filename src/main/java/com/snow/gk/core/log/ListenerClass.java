package com.snow.gk.core.log;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/*
*
* */

public class ListenerClass extends TestListenerAdapter {
    public byte[] captureScreenshot(WebDriver d) {
        return ((TakesScreenshot) d).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    // Capture the Screen shot on Failure
    public void onTestFailure(ITestResult tr) {
        Object webDriverAttribute = tr.getTestContext().getAttribute("WebDriver");
        if (webDriverAttribute instanceof WebDriver) {
            Logger.info("Screenshot captured for test case:" + tr.getMethod().getMethodName());
            captureScreenshot((WebDriver) webDriverAttribute);
        }
    }
}
