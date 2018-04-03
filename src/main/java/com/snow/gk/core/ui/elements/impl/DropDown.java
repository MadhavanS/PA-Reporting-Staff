package com.snow.gk.core.ui.elements.impl;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.log.Logger;
import com.snow.gk.core.ui.elements.IDropDown;
import com.snow.gk.core.utils.Constants;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DropDown extends Element implements IDropDown {
    private String strExceptionText  = "DropDown Element: ";
    private Select select = null;

    public DropDown() { super(); }
    public DropDown(WebElement element, String elementName) {
        super(element, elementName);
        select = new Select(element);
    }

    @Override
    public void selectByValue(String valueToBeSelected) {
        try {
            if (valueToBeSelected == null)
                return;
            if (isLoaded()) {
                select = new Select(element);
                select.selectByValue(valueToBeSelected);
                Logger.info(strExceptionText + elementOriginalName + " => " + valueToBeSelected + Constants.SELECTLOGMESSAGE);
            } else {
                Logger.error(Constants.FORMATTER + strExceptionText + elementOriginalName + Constants.SELECTLOGMESSAGE_FAILURE);
                throw new CustomException(strExceptionText + elementOriginalName + " not loaded in method selectByValue()");
            }
        } catch (Exception ce) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " selectByValue() of DropDown class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " selectByValue() of DropDown class." + ce);
        }
    }

    @Override
    public void selectByVisibleText(String valueToBeSelected){
        try {
            if (valueToBeSelected == null)
                return;
            if (isLoaded()) {
                select.selectByVisibleText(valueToBeSelected);
                Logger.info(strExceptionText + elementOriginalName + " => " + valueToBeSelected + Constants.SELECTLOGMESSAGE);
            } else {
                Logger.error(Constants.FORMATTER + strExceptionText + elementOriginalName + Constants.SELECTLOGMESSAGE_FAILURE);
                throw new CustomException(strExceptionText + elementOriginalName + " not loaded in method selectByVisibleText()");
            }
        } catch (Exception ce) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " selectByVisibleText() of DropDown class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " selectByVisibleText() of DropDown class." + ce);
        }
    }

    @Override
    public void selectByIndex(int valueToBeSelected){
        try {
            if (isLoaded()) {
                select.selectByIndex(valueToBeSelected);
                Logger.info(strExceptionText + elementOriginalName + " => " + valueToBeSelected + Constants.SELECTLOGMESSAGE);
            } else {
                Logger.error(Constants.FORMATTER + strExceptionText + elementOriginalName + Constants.SELECTLOGMESSAGE_FAILURE);
                throw new CustomException(strExceptionText + elementOriginalName + " not loaded in method selectByIndex()");
            }
        } catch (Exception ce) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " selectByIndex() of DropDown class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " selectByIndex() of DropDown class." + ce);
        }
    }

    @Override
    public String getSelectedValue() {
        try {
            if (isLoaded()) {
                WebElement option = select.getFirstSelectedOption();
                String strText = option.getText();
                Logger.info(strExceptionText + elementOriginalName + " => " + "Value Captured successfully:" + strText);
                return strText;
            } else {
                Logger.error(Constants.FORMATTER + strExceptionText + elementOriginalName + Constants.SELECTLOGMESSAGE_FAILURE);
                throw new CustomException(strExceptionText + elementOriginalName + " not loaded in method getSelectedValue()");
            }
        } catch (Exception ce) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getSelectedValue() of DropDown class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getSelectedValue() of DropDown class." + ce);
        }
    }

    @Override
    public boolean verifyDropDownValues(Map<String, String> strExpValues) {
        boolean flag=true;
        List<String> strActValues = getDropdownValues();
        if(strActValues.size()==strExpValues.size()) {
            for(String valueToCheck : strExpValues.keySet()){
                if (strActValues.contains(strExpValues.get(valueToCheck))) {
                    Logger.info("Value - "+ strExpValues.get(valueToCheck) +" Exists in " + elementOriginalName);
                }else{
                    flag=false;
                    Logger.error("Value - "+ strExpValues.get(valueToCheck)+" does not exists in " + elementOriginalName);
                }
            }
        }
        else
            flag=false;

        if(flag) {
            Logger.info(elementOriginalName +" Dropdown Values verified successfully");
            return flag;
        }
        else{
            Logger.error(elementOriginalName + " Dropdown Values verification failed: Values not Matched");
            throw new CustomException("Failed in newCallMandatoryDataFields() -->" + elementOriginalName + " Dropdown Values verification failed Values not Matched");
        }
    }

    @Override
    public List<String> getDropdownValues() {
        ArrayList<String> lstValues = new ArrayList<>();
        try {
            if (isLoaded()) {
                List<WebElement> optionElements = select.getOptions();
                for (WebElement optionElement : optionElements) {
                    String strOption = optionElement.getText();
                    if (!"[please select]".equalsIgnoreCase(strOption))
                        lstValues.add(strOption);
                }
                Logger.info(strExceptionText + elementOriginalName + " => " + "Dropdown values captured successfully");
            } else {
                Logger.error(Constants.FORMATTER + strExceptionText + elementOriginalName + "Failed to get dropdown values");
                throw new CustomException(strExceptionText + elementOriginalName + " Failed to get dropdown values");
            }
        } catch (Exception ce) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getDropdownValues() of DropDown class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getDropdownValues() of DropDown class." + ce);
        }
        return lstValues;
    }

    @Override
    public void deselect() {
        try {
            select.deselectAll();
        } catch (Exception ce) {
            throw new CustomException("DROPDOWN: Exception occurred in method deselect()" + ce);
        }
    }
}
