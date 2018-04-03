package com.snow.gk.gurukula.pages;

import com.snow.gk.core.supers.PageClass;
import com.snow.gk.core.ui.elements.IElement;
import com.snow.gk.core.ui.elements.IMenu;
import com.snow.gk.core.ui.elements.impl.Element;
import com.snow.gk.core.ui.elements.impl.Menu;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderPage extends PageClass {
    @FindBy(xpath = "//span[text()='Home']")
    private WebElement home;

    @FindBy(xpath = "//span[text()='Account']")
    private WebElement account;

    @FindBy(xpath = "//span[text()='Entities']")
    private WebElement entities;

    @FindBy(xpath = "//span[text()='Branch']")
    private WebElement branch;

    @FindBy(xpath = "//span[text()='Staff']")
    private WebElement staff;

    @FindBy(xpath = "//span[text()='Settings']")
    private WebElement settings;

    @FindBy(xpath = "//span[text()='Password']")
    private WebElement password;

    @FindBy(xpath = "//span[text()='Sessions']")
    private WebElement sessions;

    @FindBy(xpath = "//span[text()='Log out']")
    private WebElement logout;

    // Getters
    public IElement getEntities() { return getComponent(entities, Element.class); }
    public IElement getBranch() { return getComponent(branch, Element.class); }
    public IElement getStaff() { return getComponent(staff, Element.class); }
    public IElement getAccount() { return getComponent(account, Element.class); }
    public IElement getHome() { return getComponent(home, Element.class); }
    public IElement getSettings() { return getComponent(settings, Element.class); }
    public IElement getPassword() { return getComponent(password, Element.class); }
    public IElement getSessions() { return getComponent(sessions, Element.class); }
    public IElement getLogout() { return getComponent(logout, Element.class); }

    // Setter
    public void clickHome() { getHome().click(); }
    public void clickEntities() {
        getEntities().jsClick();
    }
    public void clickAccount() { getAccount().click(); }
    public void clickBranch(){
        clickEntities();
        getBranch().jsClick();
    }
    public void clickStaff() {
        clickEntities();
        getStaff().waitForPresent();
        getStaff().jsClick();
    }

    public void clickSettings() {
        clickAccount();
        getSettings().waitForPresent();
        getSettings().jsClick();
    }

    public void clickPassword() {
        clickAccount();
        getPassword().waitForPresent();
        getPassword().jsClick();
    }

    public void clickSessions() {
        clickAccount();
        getSessions().waitForPresent();
        getSessions().jsClick();
    }

    public void clickLogout() {
        clickAccount();
        getLogout().waitForPresent();
        getLogout().jsClick();
    }
}
