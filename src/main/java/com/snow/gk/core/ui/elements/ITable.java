package com.snow.gk.core.ui.elements;

import com.snow.gk.core.ui.elements.impl.Table;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface ITable {
    int getRowCount();
    int getColumnCount();
    List<String> getColumnHeaderValues();
    List<WebElement> getColumnHeaderElements();
    List<String> getValuesOfGivenColumns(String colName);
    boolean tableOperations(String columnName, String valueToBeSelected, Table.AXNS actions);
    boolean tableOperationsWithPagination(String columnName, String valueToBeSelected, Table.AXNS actions);
}
