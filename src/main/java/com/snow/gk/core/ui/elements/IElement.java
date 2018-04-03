package com.snow.gk.core.ui.elements;

import org.openqa.selenium.WebElement;

public interface IElement {
    WebElement getElement();

    // Waits
    boolean waitforPageLoadJS();
    WebElement waitForPresent();

    boolean click();
    boolean isElementLoaded();
    boolean isDisplayed();
    String getText();
    String getAttribute(String value);
    boolean isEditable();

    boolean jsClick();
}
