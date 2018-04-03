package com.snow.gk.core.ui.elements.impl;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.log.Logger;
import com.snow.gk.core.ui.elements.ICheckbox;
import com.snow.gk.core.utils.Constants;
import org.openqa.selenium.WebElement;

public class Checkbox extends Element implements ICheckbox {
    public Checkbox() { super(); }

    public Checkbox(WebElement element, String elementName) {
        super(element, elementName);
        this.element = getElement();
    }

    @Override
    public void check() {
        try {
            if (isLoaded()) {
                if (!isChecked()) {
                    element.click();
                    Logger.info("Checkbox element: " + elementOriginalName + " checked");
                } else {
                    Logger.info("Checkbox element: " + elementOriginalName + " already checked");
                }
            } else {
                Logger.error(Constants.FORMATTER + " Checkbox element: " + elementOriginalName + " click failed");
                throw new CustomException("Checkbox Element: " + elementOriginalName + " not loaded");
            }
        } catch(Exception ce) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " check() of Checkbox class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " check() of Checkbox class." + ce);
        }
    }

    @Override
    public boolean isChecked() {
        try {
            if (isLoaded()) {
                String checkedAttr = element.getAttribute("checked");
                return null != checkedAttr;
            } else {
                Logger.error(Constants.FORMATTER + " Checkbox element: " + elementOriginalName + " checking failed");
                return false;
            }
        } catch (Exception ce) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " isChecked() of Checkbox class"+ce);
            return false;
        }
    }
}
