package com.snow.gk.gurukula.pages;

import com.snow.gk.core.log.Logger;
import com.snow.gk.core.supers.PageClass;
import com.snow.gk.core.ui.elements.IButton;
import com.snow.gk.core.ui.elements.ILabel;
import com.snow.gk.core.ui.elements.ITextField;
import com.snow.gk.core.ui.elements.impl.Button;
import com.snow.gk.core.ui.elements.impl.TextField;
import com.snow.gk.core.ui.elements.impl.Label;
import com.snow.gk.gurukula.beans.BranchBean;
import com.snow.gk.gurukula.beans.StaffBean;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ViewPage extends PageClass {
    @FindBy(xpath = "//span[text()='Branch']/parent::h2")
    private WebElement headerBranchViewPage;

    @FindBy(xpath = "//span[text()='Staff']/parent::h2")
    private WebElement headerStaffViewPage;

    @FindBy(xpath = "//span[text()='Name']/parent::td/following-sibling::td[1]/input")
    private WebElement txtNameValue;

    @FindBy(xpath = "//span[text()='Code']/parent::td/following-sibling::td[1]/input")
    private WebElement txtCodeValue;

    @FindBy(xpath = "//span[text()='Branch']/parent::td/following-sibling::td[1]/input")
    private WebElement txtBranchValue;

    @FindBy(xpath = "//span[text()='Back']")
    private WebElement btnBack;

    // Getters
    public ILabel getBranchViewPageHeader() { return getComponent(headerBranchViewPage, Label.class); }
    public ILabel getStaffViewPageHeader() { return getComponent(headerStaffViewPage, Label.class); }
    public ITextField getName() { return getComponent(txtNameValue, TextField.class); }
    public ITextField getCode() { return getComponent(txtCodeValue, TextField.class); }
    public ITextField getBranch() { return getComponent(txtBranchValue, TextField.class); }
    public IButton getBack() { return getComponent(btnBack, Button.class); }

    // Setters
    public void clickBack() {
        getBack().jsClick();
    }

    /*
     * Verifies the branch is displayed is having the data which we set
     * Compares the data send as the bean
     *
     * @param BranchBean
     *         bean - beans with the details required for verifying the branch
     * @return boolean
     *         true - branch displayed are having expected details
     *         false - branch displayed has conflicts
     * */
    public boolean verifyBranchBean(BranchBean bean) {
        boolean flag = true;
        String header = "Branch " + bean.getId();
        if(header.equalsIgnoreCase(getBranchViewPageHeader().getText())) {
            Logger.info("Header of the selected branch is " + header);
        } else {
            flag = false;
            Logger.info("Header of the selected branch failed");
        }

        if(bean.getName().equalsIgnoreCase(getName().getText())) {
            Logger.info("Name of the selected branch is " + bean.getName());
        } else {
            flag = false;
            Logger.info("Name of the selected branch failed");
        }

        if(bean.getCode().equalsIgnoreCase(getCode().getText())) {
            Logger.info("Code of the selected branch is " + bean.getCode());
        } else {
            flag = false;
            Logger.info("Code of the selected branch failed");
        }

        return flag;
    }

    /*
     * Verifies the staff is displayed is having the data which we set
     * Compares the data send as the bean
     *
     * @param StaffBean
     *         bean - beans with the details required for verifying the staff
     * @return boolean
     *         true - staff displayed are having expected details
     *         false - staff displayed has conflicts
     * */
    public boolean verifyStaffBean(StaffBean bean) {
        boolean flag = true;
        String header = "Staff " + bean.getId();
        getStaffViewPageHeader().waitForPresent();
        if(header.equalsIgnoreCase(getStaffViewPageHeader().getLabel())) {
            Logger.info("Header of the selected staff is " + header);
        } else {
            flag = false;
            Logger.info("Header of the selected staff failed");
        }

        if(bean.getName().equalsIgnoreCase(getName().getText())) {
            Logger.info("Name of the selected staff is " + bean.getName());
        } else {
            flag = false;
            Logger.info("Name of the selected staff failed");
        }

        if(bean.getBranchBean().getName().equalsIgnoreCase(getBranch().getText())) {
            Logger.info("Branch of the selected staff is " + bean.getBranchBean().getName());
        } else {
            flag = false;
            Logger.error("Branch of the selected staff failed");
        }

        return flag;
    }
}
