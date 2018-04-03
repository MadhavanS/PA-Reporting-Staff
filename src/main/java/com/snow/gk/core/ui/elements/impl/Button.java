package com.snow.gk.core.ui.elements.impl;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.log.Logger;
import com.snow.gk.core.ui.elements.IButton;
import com.snow.gk.core.ui.elements.IElement;
import com.snow.gk.core.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Button extends Element implements IButton {
    public Button() { super(); }
    public Button(WebElement element, String elementName) {
        super(element, elementName);
    }

    @Override
    public void clickAndWait(By shouldNotDisplay) {
        try {
            jsClick();
            FluentWait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(duration)).pollingEvery(Duration.ofSeconds(1));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(shouldNotDisplay));
        } catch(Exception ce) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " clickAndWait() of Button class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " clickAndWait() of Button class." + ce);
        }
    }
}
