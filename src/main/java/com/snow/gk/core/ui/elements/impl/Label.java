package com.snow.gk.core.ui.elements.impl;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.log.Logger;
import com.snow.gk.core.ui.elements.ILabel;
import com.snow.gk.core.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Label extends Element implements ILabel {
    public Label() { super(); }

    public Label(WebElement element, String elementName) {
        super(element, elementName);
    }

    @Override
    public String getLabel() {
        try {
            if (isLoaded()) {
                String text = element.getText();
                Logger.info("Label Element: " + elementOriginalName + " returned label");
                return text;
            } else {
                Logger.error(Constants.FORMATTER + " Label Element: " + elementOriginalName + " label not found");
                throw new CustomException("Label Element " + elementOriginalName + " not loaded");
            }
        }
        catch (Exception e) {
            Logger.error(Constants.FORMATTER + " Label Element: " + elementOriginalName + " label not found");
            throw new CustomException("Label Element: " + elementOriginalName + " not loaded"+e);
        }
    }

    @Override
    public boolean hasMandatoryMarker() {
        try{
            return (element.findElement(By.cssSelector("[mandatory]")).getAttribute("mandatory")).contains("true");
        }
        catch (Exception e)
        {
            Logger.error("Failed in hasMandatoryMarker method"+e);
            return false;
        }
    }
}
