package com.snow.gk.gurukula.pages;

import com.snow.gk.core.log.Logger;
import com.snow.gk.core.supers.PageClass;
import com.snow.gk.core.ui.elements.IButton;
import com.snow.gk.core.ui.elements.ILabel;
import com.snow.gk.core.ui.elements.ITextField;
import com.snow.gk.core.ui.elements.impl.Label;
import com.snow.gk.core.ui.elements.impl.Button;
import com.snow.gk.core.ui.elements.impl.TextField;
import com.snow.gk.gurukula.beans.AccountBean;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountsPasswordPage extends PageClass {
    @FindBy(xpath = "//h2[contains(text(), 'Password for')]//parent::div/h2")
    private WebElement headerPasswordSettingsPage;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement txtNewPassword;

    @FindBy(xpath = "//input[@name='confirmPassword']")
    private WebElement txtConfirmPassword;

    @FindBy(xpath = "//button[text()='Save']")
    private WebElement btnSave;

    @FindBy(xpath = "//strong[contains(text(),'An error has')]//parent::div")
    private WebElement errSettingsCantSaved;

    // Getters
    public ILabel getPasswordSettingsHeader() { return getComponent(headerPasswordSettingsPage, Label.class); }
    public ILabel getPasswordUpdateErr() { return getComponent(errSettingsCantSaved, Label.class); }
    public ITextField getNewPassword() { return getComponent(txtNewPassword, TextField.class); }
    public ITextField getConfirmPassword() { return getComponent(txtConfirmPassword, TextField.class); }
    public IButton getSave() { return getComponent(btnSave, Button.class); }

    // Setters
    public void setNewPassword(String newPassword) {
        getNewPassword().clearText();
        getNewPassword().setText(newPassword);
    }
    public void setConfirmPassword(String confirmPassword) {
        getConfirmPassword().clearText();
        getConfirmPassword().setText(confirmPassword);
    }
    public void clickSave() { getSave().jsClick(); }

    /*
     * Fill the Account > Password form
     *
     * @param AccountBean
     *              Account Bean with the required data
     * @return boolean
     *          true - password update happened successfully
     *          false - failure in password update
     *
     * */
    public boolean fillUpdatePassword(AccountBean accountBean) {
        String header = "Password for [admin]";
        if(header.equals(getPasswordSettingsHeader().getLabel())) {
            setNewPassword(accountBean.getPassword());
            setConfirmPassword(accountBean.getPassword());
            clickSave();

            if(getPasswordSettingsHeader().isDisplayed()) {
                Logger.error("Error updating the password - " + getPasswordSettingsHeader().getLabel());
                return false;
            }

            return true;
        }
        return false;
    }

}
