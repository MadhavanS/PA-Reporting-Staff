package com.snow.gk.core.ui.elements;

import java.util.List;
import java.util.Map;

public interface IDropDown extends IElement {
    void selectByValue(String text);
    void selectByVisibleText(String text);
    void selectByIndex(int index);
    String getSelectedValue();
    boolean verifyDropDownValues(Map<String,String> strExpValues);
    List<String> getDropdownValues();
    void deselect();
}
