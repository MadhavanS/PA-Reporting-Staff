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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DeletePage extends PageClass {
    @FindBy(xpath = "//form[@name='deleteForm']")
    private WebElement deleteForm;

    @FindBy(xpath = "//form[@name='deleteForm']//h4[text()='Confirm delete operation']")
    private WebElement headerDeleteConfirmation;

    @FindBy(xpath = "//p[contains(text(),'Are you sure')]")
    private WebElement txtDeleteMessage;

    @FindBy(xpath = "//form[@name='deleteForm']//span[text()='Cancel']")
    private WebElement btnCancel;

    @FindBy(xpath = "//form[@name='deleteForm']//span[text()='Delete']")
    private WebElement btnDelete;

    // Getters
    public ILabel getDeleteConfirmationHeader() { return getComponent(headerDeleteConfirmation, Label.class); }
    public ITextField getDeleteMessage() { return getComponent(txtDeleteMessage, TextField.class); }
    public IButton getCancel() { return getComponent(btnCancel, Button.class); }
    public IButton getDelete() { return getComponent(btnDelete, Button.class); }

    // Setters
    public void clickDelete() { getDelete().clickAndWait(By.xpath("//form[@name='deleteForm']//span[text()='Delete']")); }
    public void clickCancel() { getCancel().jsClick(); }

    /*
     * Verifying the delete pop up is with proper delete message
     *
     * @param int
     *           branch id you want to delete
     * @return boolean
     *          true - delete pop up is displayed  as expected
     *          false- delete pop up is not displayed as expected
     *
     * */
    public boolean validateBranchDeleteMessage(int branchID) {
        if(getDeleteConfirmationHeader().waitForPresent() != null) {
            String deleteMsg = String.format("Are you sure you want to delete Branch %d?", branchID);
            if(deleteMsg.equals(getDeleteMessage().getContent())) return true;
        }
        return false;
    }

    /*
     * Delete the branch
     *
     * @param BranchBean
     *           branch which want to delete
     * @return boolean
     *          true - successfully the branch deleted
     *          false- failed to delete the branch
     *
     */
    public boolean deleteBranch(BranchBean branchBean) {
        int branchId = branchBean.getId();
        if(validateBranchDeleteMessage(branchId)) {
            clickDelete();

            List<String> lstOfId = getTable().getValuesOfGivenColumns("ID");
            if(!lstOfId.contains(Integer.toString(branchId))) {
                Logger.info("Branch with ID: " + branchId + " is deleted.");
                return true;
            }
        } else {
            Logger.error("Delete confirmation pop up is not displayed.");
        }
        return false;
    }

    /*
     * Delete the staff
     *
     * @param StaffBean
     *           staff bean which want to delete
     * @return boolean
     *          true - successfully the staff deleted
     *          false- failed to delete the staff
     *
     */
    public boolean deleteStaff(StaffBean staffBean) {
        int staffId = staffBean.getId();
        if(validateStaffDeleteMessage(staffId)) {
            clickDelete();
            List<String> lstOfId = getValuesFromAllPages("ID");
            if(!lstOfId.contains(Integer.toString(staffId))) {
                Logger.info("Staff with ID: " + staffId + " is deleted.");
                return true;
            }
        } else {
            Logger.error("Delete confirmation pop up is not displayed.");
        }
        return false;
    }

    /*
     * Staff which want to delete
     *
     * @param int
     *           staff id
     * @return boolean
     *          true - successfully the staff deleted
     *          false- failed to delete the staff
     *
     */
    private boolean validateStaffDeleteMessage(int staffId) {
        if(getDeleteConfirmationHeader().waitForPresent() != null) {
            String deleteMsg = String.format("Are you sure you want to delete Staff %d?", staffId);
            if(deleteMsg.equals(getDeleteMessage().getContent())) return true;
        }
        return false;
    }

}
