package com.snow.gk.gurukula.pages;

import com.snow.gk.core.supers.PageClass;
import com.snow.gk.core.ui.elements.IButton;
import com.snow.gk.core.ui.elements.ILabel;
import com.snow.gk.core.ui.elements.ITextField;
import com.snow.gk.core.ui.elements.impl.Button;
import com.snow.gk.core.ui.elements.impl.Label;
import com.snow.gk.core.ui.elements.impl.TextField;
import com.snow.gk.gurukula.beans.RegistrationBean;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends PageClass {
    @FindBy(xpath = "//h1[text()='Registration']")
    private WebElement headerRegistration;

    @FindBy(xpath = "//strong[contains(text(),'Registration failed')]/parent::div")
    private WebElement lblRegistrationFailed;

    @FindBy(xpath = "//input[@name='login']")
    private WebElement txtLogin;

    @FindBy(xpath = "//input[@name='email']")
    private WebElement txtEmail;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement txtPassword;

    @FindBy(xpath = "//input[@name='confirmPassword']")
    private WebElement txtConfirmPassword;

    @FindBy(xpath = "//button[text()='Register']")
    private WebElement btnRegister;

    // Getters
    public ILabel getHeaderRegistration() { return getComponent(headerRegistration, Label.class); }
    public ILabel getRegistrationFailed() { return getComponent(lblRegistrationFailed, Label.class); }
    public ITextField getLogin() { return getComponent(txtLogin, TextField.class); }
    public ITextField getEmail() { return getComponent(txtEmail, TextField.class); }
    public ITextField getPassword() { return getComponent(txtPassword, TextField.class); }
    public ITextField getConfirmPassword() { return getComponent(txtConfirmPassword, TextField.class); }
    public IButton getRegister() { return getComponent(btnRegister, Button.class); }

    // Setters
    public void setLogin(String strLogin) { getLogin().setText(strLogin); }
    public void setEmail(String strEmail) { getEmail().setText(strEmail); }
    public void setPassword(String strPassword) { getPassword().setText(strPassword); }
    public void setConfirmPassword(String strConfirmPassword) { getConfirmPassword().setText(strConfirmPassword); }
    public void clickRegister() { getRegister().click(); }

    /*
     * Fills the registration of the new user
     *
     * @param RegistrationBean
     *       registrationBean - beans with the details required for registering new user
     * @return void
     *
     * */
    public void fillRegistration(RegistrationBean registrationBean) {
        if("Registration".equals(getHeaderRegistration().getLabel())) {
            setLogin(registrationBean.getLogin());
            setEmail(registrationBean.geteMail());
            setPassword(registrationBean.getPassword());
            setConfirmPassword(registrationBean.getConfirmPassword());

            clickRegister();
        }
    }

}
