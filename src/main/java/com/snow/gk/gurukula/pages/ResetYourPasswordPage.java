package com.snow.gk.gurukula.pages;

import com.snow.gk.core.supers.PageClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResetYourPasswordPage extends PageClass {
    @FindBy(xpath = "//h1[text()='Reset your password']")
    private WebElement headerResetYourPassword;

    @FindBy(xpath = "//input[@name='email']")
    private WebElement tfEmail;

    @FindBy(xpath = "//button[text()='Reset password']")
    private WebElement btnResetPassword;

}
