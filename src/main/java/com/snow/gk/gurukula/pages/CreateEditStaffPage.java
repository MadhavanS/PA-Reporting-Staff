package com.snow.gk.gurukula.pages;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.log.Assert;
import com.snow.gk.core.log.Logger;
import com.snow.gk.core.ui.elements.*;
import com.snow.gk.core.ui.elements.impl.*;
import com.snow.gk.gurukula.beans.BranchBean;
import com.snow.gk.gurukula.beans.StaffBean;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.ArrayList;
import java.util.List;


public class CreateEditStaffPage extends StaffPage {
    @FindBy(css = "#myStaffLabel")
    private WebElement labelCreateEditStaff;

    @FindBy(css = "select[name='related_branch']")
    private WebElement selBranch;

    @FindBy(css = "#saveStaffModal .close")
    private WebElement close;

    @FindBy(css = "input[name='id']")
    private WebElement txtID;

    @FindBy(css = "input[name='name']")
    private WebElement txtName;

    @FindBy(xpath = "//input[@name='name']/following-sibling::div/p")
    private WebElement nameFieldRequiredMessage;

    @FindBy(xpath = "//div[@id='saveStaffModal']//span[text()='Cancel']")
    private WebElement btnCancel;

    @FindBy(xpath = "//div[@id='saveStaffModal']//span[text()='Save']")
    private WebElement btnSave;

    @FindBy(xpath = "//table")
    private WebElement tbl;

    @FindBys(@FindBy(xpath = "//label[text()='Name']//following-sibling::div/p[not(contains(@class,'ng-hide'))]"))
    private List<WebElement> errStaffName;

    @FindBy(xpath = "//a[text()='>>']")
    private WebElement btnFwd;

    @FindBy(xpath = "//a[text()='<']")
    private WebElement btnBack;

    /*@FindBy(xpath = "//a[text()='>']//parent::li")
    private WebElement btnNextParent;*/

    // Getters
    public ILabel getCreateEditBranchLbl() { return getComponent(labelCreateEditStaff, Label.class); }
    public IDropDown getBranch() { return getComponent(selBranch, DropDown.class); }
    public ITextField getID() { return getComponent(txtID, TextField.class); }
    public ITextField getName() { return getComponent(txtName, TextField.class); }
    public IButton getSave() { return getComponent(btnSave, Button.class); }
    public IButton getCancel() { return getComponent(btnCancel, Button.class); }
    public ITable getTable() { return getComponent(tbl, Table.class); }
    public IButton getFwd() { return getComponent(btnFwd, Button.class); }
    //public IButton getNextParent() { return getComponent(btnNextParent, Button.class); }
    public IButton getBack() { return getComponent(btnBack, Button.class); }

    // Setters
    public void selectBranch(String strBranch) { getBranch().selectByVisibleText(strBranch); }
    public void setID(String strID) { getID().setText(strID); }
    public void setName(String strName) { getName().setText(strName); }
    public void clickSave() { getSave().clickAndWait(By.cssSelector("#myStaffLabel")); }
    public void clickCancel() { getCancel().clickAndWait(By.cssSelector("#myStaffLabel")); }
    public void clickForward() { getFwd().clickAndWait(By.cssSelector("#myStaffLabel")); }
    public void clickBack() { getBack().clickAndWait(By.cssSelector("#myStaffLabel")); }


    /*
     * Creates the New Staff
     *
     * @param StaffBean
     *              Staff Bean with data which user want to create
     * @return boolean
     *           true - staff is created successful
     *           false - creation of staff failed
     *
     * */
    public boolean createStaff(StaffBean staffBean) {
        if(!headerStaffs()) throw new CustomException("Staffs page didnt open.");
        List<String> before = getValuesFromAllPages("ID");
        boolean isCreated = false;
        int rows = before.size();
        clickCreateNewStaff();

        fillStaffForm(staffBean);
        clickSave();

        getStaffsHeader().isDisplayed();

        isCreated = (getValuesFromAllPages("ID").size() == (rows+1));
        if(isCreated) {
            Logger.info("New row created in Staff!!" );
            List<String> after = getValuesFromAllPages("ID");
            after.removeAll(before);
            staffBean.setId(Integer.parseInt(after.get(0)));
        } else {
            Logger.error("New Staff not created");
            return false;
        }

        return isCreated;
    }

    /*public List<String> getValuesFromAllPages(String colName) {
        List<String> lst = new ArrayList<>();
        clickBackward();
        do {
            lst.addAll(getTable().getValuesOfGivenColumns(colName));
            if(getNext().isDisplayed()) clickNext();
            else break;
        }while(true);
        return lst;
    }*/

    /*
     * Fills the create edit form of staff
     *
     * @param StaffBean
     *              Staff Bean with modified data which user wants to update
     * @return void
     *
     * */
    public void fillStaffForm(StaffBean staffBean) {
        if (getCreateEditBranchLbl().waitForPresent() != null) {
            setName(staffBean.getName());
            selectBranch(staffBean.getBranchBean().getName());
        } else {
            Logger.error("Create Edit Staff not loaded.");
            throw new CustomException("Create Edit Staff not loaded.");
        }
    }

    /*
     * Edit the existing staff form
     *
     * @param StaffBean
     *              Staff Bean with modified data
     * @return void
     *
     * */
    public void editStaffForm(StaffBean staffBean) {
        if(getCreateEditBranchLbl().isDisplayed()) {
            getName().clearText();
            fillStaffForm(staffBean);
            clickSave();
        }
    }

}
