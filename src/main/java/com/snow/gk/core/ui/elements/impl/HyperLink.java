package com.snow.gk.core.ui.elements.impl;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.log.Logger;
import com.snow.gk.core.ui.elements.IHyperLink;
import com.snow.gk.core.utils.Constants;
import org.openqa.selenium.WebElement;

public class HyperLink extends Element implements IHyperLink {
    public HyperLink() { super(); }
    public HyperLink(WebElement element, String elementName) {
        super(element, elementName);
    }

    @Override
    public String getHref() {
        try {
            return element.getAttribute("href");
        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getHref() of Frame class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getHref() of Frame class." + fe);
        }
    }

    @Override
    public String getTarget() {
        try {
            return element.getAttribute("target");
        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getTarget() of Frame class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getTarget() of Frame class." + fe);
        }
    }

    @Override
    public void clickLink() {
        try {
            if (click()) {
                Logger.info("HyperLink element: " + elementOriginalName + " clicked");
            } else {
                Logger.error(Constants.FORMATTER + " HyperLink element: " + elementOriginalName + " click failed");
                throw new CustomException("HyperLink element: " + elementOriginalName + " not found");
            }
        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " clickLink() of Frame class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " clickLink() of Frame class." + fe);
        }
    }
}
