package com.snow.gk.core.ui.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface IButton extends IElement{
    void clickAndWait(By shouldNotDisplay);
}
