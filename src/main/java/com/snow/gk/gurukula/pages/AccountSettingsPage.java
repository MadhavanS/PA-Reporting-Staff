package com.snow.gk.gurukula.pages;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.log.Logger;
import com.snow.gk.core.supers.PageClass;
import com.snow.gk.core.ui.elements.IButton;
import com.snow.gk.core.ui.elements.IDropDown;
import com.snow.gk.core.ui.elements.ILabel;
import com.snow.gk.core.ui.elements.ITextField;
import com.snow.gk.core.ui.elements.impl.Button;
import com.snow.gk.core.ui.elements.impl.DropDown;
import com.snow.gk.core.ui.elements.impl.TextField;
import com.snow.gk.core.ui.elements.impl.Label;
import com.snow.gk.gurukula.beans.AccountBean;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountSettingsPage extends PageClass {
    @FindBy(xpath = "//h2[contains(text(),'User settings for')]//parent::h2")
    private WebElement headerAccountSettings;

    @FindBy(xpath = "//input[@name='firstName']")
    private WebElement txtFirstName;

    @FindBy(xpath = "//input[@name='lastName']")
    private WebElement txtLastName;

    @FindBy(xpath = "//input[@name='email']")
    private WebElement txtEMail;

    @FindBy(xpath = "//select[@name='langKey']")
    private WebElement ddLanguage;

    @FindBy(xpath = "//button[text()='Save']")
    private WebElement btnSave;

    @FindBy(xpath = "//strong[contains(text(),'An error has')]//parent::div")
    private WebElement errSettingsCantSaved;

    // Getters
    public ILabel getAccountSettingsHeader() { return getComponent(headerAccountSettings, Label.class); }
    public ILabel getSettingsCantSaveErr() { return getComponent(errSettingsCantSaved, Label.class); }
    public ITextField getFirstName() { return getComponent(txtFirstName, TextField.class); }
    public ITextField getLastName() { return getComponent(txtLastName, TextField.class); }
    public ITextField getEMail() { return getComponent(txtEMail, TextField.class); }
    public IButton getSave() { return getComponent(btnSave, Button.class); }
    public IDropDown getLanguage() { return getComponent(ddLanguage, DropDown.class); }

    // Setters
    public void clickSave() { getSave().jsClick(); }
    public void setFirstName(String fn) {
        getFirstName().clearText();
        getFirstName().setText(fn);
    }
    public void setLastName(String ln) {
        getLastName().clearText();
        getLastName().setText(ln);
    }
    public void setEMail(String email) {
        getEMail().clearText();
        getEMail().setText(email);
    }
    public void setLanguage(String lang) { getLanguage().selectByVisibleText(lang); }

    /*
     * Fill the Account > Settings form
     *
     * @param AccountBean
     *              Account Bean with the required data
     * @return boolean
     *          true - settings updated according to the given bean
     *          false - settings updation failed due to error or data issue
     *
     * */
    public boolean fillSettingsForm(AccountBean accountBean) {
        String headerText = "User settings for [admin]";
        if(headerText.equals(getAccountSettingsHeader().getLabel())) {
            setFirstName(accountBean.getFirstName());
            setLastName(accountBean.getLastName());
            setEMail(accountBean.geteMail());
            setLanguage(accountBean.getLanguage());
            clickSave();

            if(getAccountSettingsHeader().isDisplayed()) {
                Logger.error("An error has occurred! Settings could not be saved.");
                throw new CustomException("An error has occurred! Settings could not be saved.");
            }
            return true;
        }
        return false;
    }
}
