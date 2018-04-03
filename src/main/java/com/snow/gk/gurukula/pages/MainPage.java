package com.snow.gk.gurukula.pages;

import com.snow.gk.core.ui.elements.IElement;
import com.snow.gk.core.ui.elements.ILabel;
import com.snow.gk.core.ui.elements.impl.Element;
import com.snow.gk.core.ui.elements.impl.Label;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends HeaderPage {
    @FindBy(xpath = "//h1[text() = 'Welcome to Gurukula!']")
    private WebElement welcomeMessage;

    @FindBy(xpath = "//div[contains(text(),'You are logged')]")
    private WebElement txtYourLoggedInMessage;

    public IElement getWelcomeMessage() { return getComponent(welcomeMessage, Element.class); }
    public ILabel getYourLoggedIn() { return getComponent(txtYourLoggedInMessage, Label.class); }

    public boolean validatePage(String username) {
        String loggedInMsg = String.format("You are logged in as user \"%s\".", username);
        if(getWelcomeMessage().getElement().isDisplayed() &&
                loggedInMsg.equalsIgnoreCase(getYourLoggedIn().getLabel()))
            return true;
        else
            return false;
    }
}
