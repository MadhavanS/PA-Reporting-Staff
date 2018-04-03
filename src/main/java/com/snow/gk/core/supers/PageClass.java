package com.snow.gk.core.supers;

import com.snow.gk.core.ui.elements.IButton;
import com.snow.gk.core.ui.elements.ITable;
import com.snow.gk.core.ui.elements.impl.Button;
import com.snow.gk.core.ui.elements.impl.Table;
import com.snow.gk.gurukula.beans.BranchBean;
import com.snow.gk.gurukula.beans.StaffBean;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class PageClass extends Components {
    @FindBy(xpath = "//table")
    private WebElement tbl;

    @FindBy(xpath = "//a[text()='<<']")
    private WebElement btnBwd;

    @FindBy(xpath = "//a[text()='>']")
    private WebElement btnNext;


    public ITable getTable() { return getComponent(tbl, Table.class); }
    public IButton getBwd() { return getComponent(btnBwd, Button.class); }
    public IButton getNext() { return getComponent(btnNext, Button.class); }
    public void clickBackward() { getBwd().clickAndWait(By.cssSelector("#myStaffLabel")); }
    public void clickNext() { getNext().clickAndWait(By.cssSelector("#myStaffLabel")); }

    public void branchOperations(BranchBean branchBean, String colName, Table.AXNS actions) {
        String valueToBeSelected = "";
        if(colName.equalsIgnoreCase("ID")) valueToBeSelected = Integer.toString(branchBean.getId());
        else if(colName.equalsIgnoreCase("Name")) valueToBeSelected = branchBean.getName();
        else if(colName.equalsIgnoreCase("Code")) valueToBeSelected = branchBean.getCode();

        getTable().tableOperations(colName, valueToBeSelected, actions);
    }

    public void staffOperations(StaffBean staffBean, String colName, Table.AXNS actions) {
        String valueToBeSelected = "";
        if(colName.equalsIgnoreCase("ID")) valueToBeSelected = Integer.toString(staffBean.getId());
        else if(colName.equalsIgnoreCase("Name")) valueToBeSelected = staffBean.getName();
        else if(colName.equalsIgnoreCase("Branch")) valueToBeSelected = staffBean.getBranchBean().getName();

        getTable().tableOperationsWithPagination(colName, valueToBeSelected, actions);
    }

    public List<String> getValuesFromAllPages(String colName) {
        List<String> lst = new ArrayList<>();
        clickBackward();
        do {
            lst.addAll(getTable().getValuesOfGivenColumns(colName));
            if(getNext().isDisplayed()) clickNext();
            else break;
        }while(true);
        return lst;
    }
}
