package com.snow.gk.gurukula.pages;

import com.snow.gk.core.log.Logger;
import com.snow.gk.core.ui.elements.IButton;
import com.snow.gk.core.ui.elements.ILabel;
import com.snow.gk.core.ui.elements.ITextField;
import com.snow.gk.core.ui.elements.impl.Button;
import com.snow.gk.core.ui.elements.impl.Label;
import com.snow.gk.core.ui.elements.impl.TextField;
import com.snow.gk.gurukula.beans.BranchBean;
import com.snow.gk.gurukula.beans.StaffBean;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class StaffPage extends HeaderPage {
    @FindBy(xpath = "//h2[text()='Staffs']")
    private WebElement lblStaffs;

    @FindBy(xpath = "//span[text()='Create a new Staff']")
    private WebElement createNewStaff;

    @FindBy(xpath = "//span[text()='Search a Staff']")
    private WebElement searchStaff;

    @FindBy(css = "#searchQuery")
    private WebElement searchQuery;

    public IButton getCreateNewStaff() { return getComponent(createNewStaff, Button.class); }
    public IButton getSearchStaff() { return getComponent(searchStaff, Button.class); }
    public ITextField getQuery() { return getComponent(searchQuery, TextField.class); }
    public ILabel getStaffsHeader() { return getComponent(lblStaffs, Label.class); }

    public void clickCreateNewStaff() { getCreateNewStaff().click(); }
    public void clickSearchStaff() { getSearchStaff().click(); }
    public void setQuery(String query) {
        getQuery().clearText();
        getQuery().setText(query);
    }

    public boolean headerStaffs() { return getStaffsHeader().isElementLoaded(); }

    /*
     * Searches for the given staff in the Staff Page
     *
     * @param StaffBean
     *              Branch Bean which user want to search
     * @param Columns
     *               enum of the column name in the branch page
     *               possible enum values are ID, NAME, BRANCH
     *  @return boolean
     *           true - when search is successful
     *           false - when search failed
     *
     * */
    public boolean searchStaff(StaffBean staffBean, Columns colName) {
        setQuery(colName.toString(staffBean));
        clickSearchStaff();

        int id = staffBean.getId();
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
        ID("id"), NAME("name"), BRANCH("branch");
        private final String value;

        private Columns(String value) {
            this.value = value;
        }

        public String toString(StaffBean bean) {
            String output = "id";
            if(this.value.equalsIgnoreCase("name")) {
                output = bean.getName();
            } else if(this.value.equalsIgnoreCase("branch")) {
                output = bean.getBranchBean().getName();
            } else if(this.value.equalsIgnoreCase("id")) {
                output = Integer.toString(bean.getId());
            }
            return output;
        }
    }
}
