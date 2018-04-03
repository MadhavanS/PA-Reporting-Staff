package com.snow.gk.core.ui.elements;

import org.openqa.selenium.Keys;

public interface ITextField extends IElement {
    void setText(String text);
    String getContent();
    void clearText();
    void setTextWithSendKey(String text, Keys key);
    void sendKey(Keys key);
    void setTextWithAutoComplete(String text);
}
