package com.snow.gk.gurukula.pages;

import com.snow.gk.core.log.Logger;
import com.snow.gk.core.ui.elements.IButton;
import com.snow.gk.core.ui.elements.ILabel;
import com.snow.gk.core.ui.elements.ITextField;
import com.snow.gk.core.ui.elements.impl.Button;
import com.snow.gk.core.ui.elements.impl.TextField;
import com.snow.gk.core.ui.elements.impl.Label;
import com.snow.gk.gurukula.beans.BranchBean;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BranchPage extends HeaderPage {
    @FindBy(xpath = "//h2[text()='Branches']")
    private WebElement lblBranches;

    @FindBy(xpath = "//span[text()='Create a new Branch']")
    private WebElement createNewBranch;

    @FindBy(xpath = "//span[text()='Search a Branch']")
    private WebElement searchBranch;

    @FindBy(css = "#searchQuery")
    private WebElement searchQuery;

    @FindBy(xpath = "//span[text()='View']")
    private WebElement btnView;

    @FindBy(xpath = "//span[text()='View']")
    private WebElement btnEdit;

    @FindBy(xpath = "//span[text()='View']")
    private WebElement btnDelete;

    // Getters
    public ILabel getHeaderBranch() { return getComponent(lblBranches, Label.class); }
    public IButton getCreateNewBranch() { return getComponent(createNewBranch, Button.class); }
    public IButton getSearchBranch() { return getComponent(searchBranch, Button.class); }
    public ITextField getQuery() { return getComponent(searchQuery, TextField.class); }
    public IButton getViewButton() { return getComponent(btnView, Button.class); }
    public IButton getEditButton() { return getComponent(btnEdit, Button.class); }
    public IButton getDeleteButton() { return getComponent(btnDelete, Button.class); }

    // Setters
    public void clickCreateNewBranch() { getCreateNewBranch().click(); }
    public void clickSearchBranch() { getSearchBranch().click(); }
    public void setQuery(String query) {
        getQuery().clearText();
        getQuery().setText(query);
    }

    /*
    * Searches for the given branch in the Branch Page
    *
    * @param BranchBean
    *              Branch Bean which user want to search
    * @param Columns
    *               enum of the column name in the branch page
    *               possible enum values are ID, NAME, CODE
    *  @return boolean
    *           true - when search is successful
    *           false - when search failed
    *
    * */
    public boolean searchBranch(BranchBean branchBean, Columns colName) {
        setQuery(colName.toString(branchBean));
        clickSearchBranch();

        int id = branchBean.getId();
        List<String> lstOfIds = getTable().getValuesOfGivenColumns("ID");
        if(lstOfIds.contains(Integer.toString(id))) {
            Logger.info("Search query for " + colName + " is found");
            if("ID".equalsIgnoreCase(colName.toString())) {
                return lstOfIds.size() == 1? true: false;
            }
            return true;
        } else {
            Logger.info("Failed to search query for " + colName + ".");
            return false;
        }
    }

    public enum Columns {
        ID("id"), NAME("name"), CODE("code");
        private final String value;

        private Columns(String value) {
            this.value = value;
        }

        public String toString(BranchBean bean) {
            String output = "id";
            if(this.value.equalsIgnoreCase("name")) {
                output = bean.getName();
            } else if(this.value.equalsIgnoreCase("code")) {
                output = bean.getCode();
            } else if(this.value.equalsIgnoreCase("id")) {
                output = Integer.toString(bean.getId());
            }
            return output;
        }
    }

}
