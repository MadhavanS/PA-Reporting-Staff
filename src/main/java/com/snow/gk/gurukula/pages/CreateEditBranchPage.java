package com.snow.gk.gurukula.pages;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.log.Logger;
import com.snow.gk.core.ui.elements.IButton;
import com.snow.gk.core.ui.elements.ILabel;
import com.snow.gk.core.ui.elements.ITextField;
import com.snow.gk.core.ui.elements.impl.Button;
import com.snow.gk.core.ui.elements.impl.Label;
import com.snow.gk.core.ui.elements.impl.TextField;
import com.snow.gk.gurukula.beans.BranchBean;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.List;

public class CreateEditBranchPage extends BranchPage {
    @FindBy(css = "#myBranchLabel")
    private WebElement headerCreateEditBranch;

    @FindBy(css = "input[name='code']")
    private WebElement txtCode;

    @FindBy(xpath = "//input[@name='code']/following-sibling::div/p")
    private WebElement codeFieldRequiredMessage;

    /*@FindBy(css = "#saveStaffModal .close")
    private WebElement close;*/

    @FindBy(css = "input[name='id']")
    private WebElement txtID;

    @FindBy(css = "input[name='name']")
    private WebElement txtName;

    @FindBy(xpath = "//input[@name='name']/following-sibling::div/p")
    private WebElement nameFieldRequiredMessage;

    @FindBy(xpath = "//div[@id='saveBranchModal']//span[text()='Cancel']")
    private WebElement btnCancel;

    @FindBy(xpath = "//div[@id='saveBranchModal']//span[text()='Save']")
    private WebElement btnSave;

    @FindBys(@FindBy(xpath = "//label[text()='Code']//following-sibling::div/p[not(contains(@class,'ng-hide'))]"))
    private List<WebElement> errCodes;

    @FindBys(@FindBy(xpath = "//label[text()='Name']//following-sibling::div/p[not(contains(@class,'ng-hide'))]"))
    private List<WebElement> errBranchName;

    /*@FindBy(xpath = "//p[contains(text(),'A-Z0')]")
    private WebElement errCodePattern;

    @FindBy(xpath = "//p[contains(text(),'10')]")
    private WebElement errCodeTenChars;

    @FindBy(xpath = "//p[contains(text(),'5')]")
    private WebElement errCodeFiveChars;

    @FindBy(xpath = "//p[contains(text(), '//p[contains(text(),'a-zA-Z')]')]")
    private WebElement errBranchPattern;

    @FindBy(xpath = "//label[text()='Name']//following::p[contains(text(),'required.')]")
    private WebElement errBranchFieldRequired;

    @FindBy(xpath = "//label[text()='Code']//following::p[contains(text(),'required.')]")
    private WebElement errCodeFieldRequired;*/

    // Getters
    public ITextField getID() { return getComponent(txtID, TextField.class); }
    public ITextField getName() { return getComponent(txtName, TextField.class); }
    public IButton getSave() { return getComponent(btnSave, Button.class); }
    public IButton getCancel() { return getComponent(btnCancel, Button.class); }
    public ILabel getCreateEditBranchLbl() { return getComponent(headerCreateEditBranch, Label.class); }
    public ITextField getCode() { return getComponent(txtCode, TextField.class); }
    /*public ILabel getErrCodePattern() { return getComponent(errCodePattern, Label.class); }
    public ILabel getErrCodeTenChars() { return getComponent(errCodeTenChars, Label.class); }
    public ILabel getErrCodeFiveChars() { return getComponent(errCodeFiveChars, Label.class); }
    public ILabel getErrBranchPattern() { return getComponent(errBranchPattern, Label.class); }
    public ILabel getErrBranchFieldRequired() { return getComponent(errBranchFieldRequired, Label.class); }
    public ILabel getErrCodeFieldRequired() { return getComponent(errCodeFieldRequired, Label.class); }*/

    // Setters
    public void setCode(String strCode) { getCode().setText(strCode); }
    public void setName(String strName) { getName().setText(strName); }
    public void clickSave() { getSave().clickAndWait(By.cssSelector("#myBranchLabel")); }
    public void clickCancel() { getCancel().clickAndWait(By.cssSelector("#myBranchLabel")); }

    /*
     * Creates the New Branch
     *
     * @param BranchBean
     *              Branch Bean data for which user want to create
     * @return boolean
     *           true - staff is created successful
     *           false - creation of staff failed
     *
     * */
    public boolean createNewBranch(BranchBean branchBean) {
        boolean isCreated = false;
        try {
            List<String> before = getTable().getValuesOfGivenColumns("ID");
            int rows = getTable().getRowCount();
            clickCreateNewBranch();

            fillForm(branchBean);
            clickSave();

            getHeaderBranch().waitForPresent();
            isCreated = (getTable().getRowCount() == (rows + 1));
            if (isCreated) {
                Logger.info("New row created!!");
                List<String> after = getTable().getValuesOfGivenColumns("ID");
                after.removeAll(before);
                branchBean.setId(Integer.parseInt(after.get(0)));
            } else {
                Logger.error("New Branch not created");
                return false;
            }
        } catch (Exception e) {
            throw new CustomException("Failed in method createNewBranch " + e);
        }
        return isCreated;
    }

    /*
     * Fills the create edit form of branch
     *
     * @param BranchBean
     *              Branch Bean data for which user want to create
     * @return void
     *
     * */
    public void fillForm(BranchBean branchBean) {
        if (getCreateEditBranchLbl().waitForPresent() != null) {
            setName(branchBean.getName());
            setCode(branchBean.getCode());
        } else {
            Logger.error("Create Edit Branch not loaded.");
            throw new CustomException("Create Edit Branch not loaded.");
        }
    }

    /*
     * Edit the existing branch form
     *
     * @param BranchBean
     *              Branch Bean with modified data
     * @return void
     *
     * */
    public void editForm(BranchBean branchBean) {
        if(getCreateEditBranchLbl().isDisplayed()) {
            getName().clearText();
            getCode().clearText();
            fillForm(branchBean);
        }
    }


}
