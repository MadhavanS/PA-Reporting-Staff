package com.snow.gk.core.ui.elements.impl;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.log.Logger;
import com.snow.gk.core.ui.elements.ITextField;
import com.snow.gk.core.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TextField extends Element implements ITextField {
    public TextField() { super(); }

    public TextField(WebElement element, String elementName) {
        super(element, elementName);
    }

    @Override
    public void setText(String text) {
        if(text == null)
            return;
        try {
            if (isLoaded()) {
                element.sendKeys(text);
                Logger.info(Constants.TEXTFIELDLOGMESSAGE + elementOriginalName + " => " + text + Constants.SETTEXTLOGMESSAGE);
            } else {
                Logger.error(Constants.FORMATTER + Constants.TEXTFIELDLOGMESSAGE + elementOriginalName + " failed to enter text");
                throw new CustomException(Constants.TEXTFIELDLOGMESSAGE + elementOriginalName + " not loaded in method setText()");
            }
        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " setText() of TextField class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " setText() of TextField class." + fe);
        }
    }

    @Override
    public void clearText() {
        try {
            if (isLoaded()) {
                element.clear();
                Logger.info(Constants.TEXTFIELDCLEARTEXTMESSAGE);
            } else {
                Logger.error(Constants.FORMATTER + " " + Constants.TEXTFIELDLOGMESSAGE + elementOriginalName + " failed to clear text");
                throw new CustomException(Constants.TEXTFIELDLOGMESSAGE + elementOriginalName + " not loaded in method clearText()");
            }
        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " clearText() of TextField class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " clearText() of TextField class." + fe);
        }
    }

    @Override
    public String getContent() {
        try {
            if (isLoaded()) {
                String text = element.getText();
                Logger.info(Constants.TEXTFIELDLOGMESSAGE + elementOriginalName + " => " + text + Constants.SETTEXTLOGMESSAGE);
                return text;
            } else {
                Logger.error(Constants.FORMATTER + Constants.TEXTFIELDLOGMESSAGE + elementOriginalName + " failed to get text");
                throw new CustomException(Constants.TEXTFIELDLOGMESSAGE + elementOriginalName + " not loaded in method getContent()");
            }
        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getContent() of TextField class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getContent() of TextField class." + fe);
        }
    }

    @Override
    public void sendKey(Keys key) {
        try {
            if (isLoaded()) {
                element.sendKeys(key);
            } else {
                Logger.error(Constants.FORMATTER + Constants.TEXTFIELDLOGMESSAGE + elementOriginalName + " failed to send keys");
                throw new CustomException(Constants.TEXTFIELDLOGMESSAGE + elementOriginalName + " not loaded in method setTextWithSendKey()");
            }
        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " sendKey() of TextField class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " sendKey() of TextField class." + fe);
        }
    }

    @Override
    public void setTextWithAutoComplete(String text) {
        try {
            if (isLoaded())
            {
                element.sendKeys(text);
                Thread.sleep(1000);
                element.sendKeys(Keys.ARROW_DOWN);
                element.sendKeys(Keys.ENTER);
            }
        }
        catch (Exception fe)
        {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " setTextWithAutoComplete() of TextField class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " setTextWithAutoComplete() of TextField class." + fe);
        }
    }

    @Override
    public void setTextWithSendKey(String text, Keys key) {
        if(text == null)
            return;
        try {
            if (isLoaded()) {
                element.sendKeys(text);
                Thread.sleep(2000);
                Logger.info(Constants.TEXTFIELDLOGMESSAGE + elementOriginalName + " => " + text + Constants.SETTEXTLOGMESSAGE);
                element.sendKeys(key);
            } else {
                Logger.error(Constants.FORMATTER + Constants.TEXTFIELDLOGMESSAGE + elementOriginalName + " failed to enter text");
                throw new CustomException(Constants.TEXTFIELDLOGMESSAGE + elementOriginalName + " not loaded in method setTextWithSendKey()");
            }
        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " setTextWithSendKey() of TextField class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " setTextWithSendKey() of TextField class." + fe);
        }
    }
}
