package com.snow.gk.gurukula.pages;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.supers.PageClass;
import com.snow.gk.core.ui.elements.IButton;
import com.snow.gk.core.ui.elements.IElement;
import com.snow.gk.core.ui.elements.ILabel;
import com.snow.gk.core.ui.elements.ITextField;
import com.snow.gk.core.ui.elements.impl.Element;
import com.snow.gk.core.ui.elements.impl.Label;
import com.snow.gk.core.ui.elements.impl.Button;
import com.snow.gk.core.ui.elements.impl.TextField;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageClass {
    @FindBy(xpath = "//h1[contains(text(),'Authentication')]")
    private WebElement headerAuthentication;

    @FindBy(css = "#username")
    private WebElement username;

    @FindBy(css = "#password")
    private WebElement password;

    @FindBy(xpath = "//span[text()='Automatic Login']")
    private WebElement automaticLogin;

    @FindBy(xpath = "//button[text()='Authenticate']")
    private WebElement btnAuthenticate;

    @FindBy(xpath = "//a[contains(text(), 'Did you forget')]")
    private WebElement forgetPassword;

    @FindBy(xpath = "//a[text() = 'Register a new account']")
    private WebElement registerNewAccount;

    @FindBy(xpath = "//strong[text()='Authentication failed!']/parent::div")
    private WebElement authenticationFailure;

    // Getters
    public ILabel getHeaderAuthentication() { return getComponent(headerAuthentication, Label.class); }
    public IElement getUsername() { return getComponent(username, Element.class); }
    public IElement getPassword() { return getComponent(password, Element.class); }
    public IButton getAuthenticate() { return getComponent(btnAuthenticate, Button.class); }
    public ITextField getAuthenticationFailure() { return getComponent(authenticationFailure, TextField.class); }

    // Setters
    public void setUsername(String user) {
        getUsername().getElement().sendKeys(user);
    }
    public void setPassword(String pwd) {
        getPassword().getElement().sendKeys(pwd);
    }
    public void clickAuthenticate() {
        getAuthenticate().click();
    }

    public boolean loginPageVerification() {
        return getHeaderAuthentication().isElementLoaded();
    }

    /*
     * Login app with given user credentials
     *
     * @param String
     *            user - user name for logging in
     *        password - password for logging in
     * @return void
     *
     * */
    public void login(String user, String pwd) throws CustomException {
        setUsername(user);
        setPassword(pwd);
        clickAuthenticate();
    }
}
