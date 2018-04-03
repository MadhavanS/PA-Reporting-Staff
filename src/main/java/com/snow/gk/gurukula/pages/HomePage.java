package com.snow.gk.gurukula.pages;

import com.snow.gk.core.supers.PageClass;
import com.snow.gk.core.ui.elements.IHyperLink;
import com.snow.gk.core.ui.elements.ILabel;
import com.snow.gk.core.ui.elements.impl.HyperLink;
import com.snow.gk.core.ui.elements.impl.Label;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageClass {
    @FindBy(xpath = "//h1[contains(text(),'Welcome to')]")
    private WebElement headerWelcomeToGurukula;

    @FindBy(xpath = "//div[contains(normalize-space(text()), 'Click here to')]/a[text()='login']")
    private WebElement login;

    @FindBy(xpath = "//div[contains(normalize-space(text()), \"You don\'t have an account yet?\")]/a[text()='Register a new account']")
    private WebElement registerNewAccount;

    @FindBy(xpath = "//span[text()='Home']")
    private WebElement home;

    @FindBy(xpath = "//span[text()='Account']")
    private WebElement account;

    @FindBy(xpath = "//span[text()='Authenticate']")
    private WebElement authenticate;

    @FindBy(css = "span:contains('Register')")
    private WebElement register;

    // Getters
    public ILabel getWelcomeHeader() { return getComponent(headerWelcomeToGurukula, Label.class); }
    public IHyperLink getLogin() { return getComponent(login, HyperLink.class); }
    public IHyperLink getRegisterNewAccount() { return getComponent(registerNewAccount, HyperLink.class); }

    // Setter
    public void clickLogin() { getLogin().click(); }
    public void clickRegisterNewAccount() { getRegisterNewAccount().clickLink(); }
}
