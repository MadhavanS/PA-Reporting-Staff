package com.snow.gk.core.ui.elements.impl;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.log.Logger;
import com.snow.gk.core.ui.elements.ITable;
import com.snow.gk.core.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Table extends Element implements ITable {
    private String rowXPath = "//tbody/tr";
    private String headerXPath = "//table//th";
    private String COMPLETE_ROW = "table tbody>tr";


    public Table() {
        super();
    }

    public Table(WebElement element) {
        super(element);
    }

    public Table(WebElement element, String elementName) {
        super(element, elementName);
    }


    @Override
    public int getRowCount() {
        List<WebElement> lstElements;
        try {
            if (element == null)
                lstElements = driver.findElements(By.xpath(rowXPath));
            else
                lstElements = element.findElements(By.xpath(rowXPath));
            return lstElements.size();
        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getRowCount() of Table class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getRowCount() of Table class." + fe);
        }
    }

    @Override
    public int getColumnCount() {
        List<WebElement> lstElements;
        try {
            if (element == null)
                lstElements = driver.findElements(By.xpath(headerXPath));
            else
                lstElements = element.findElements(By.xpath(headerXPath));
            lstElements.removeIf((WebElement we) -> we.getText().length() == 0);
        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getRowCount() of Table class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getRowCount() of Table class." + fe);
        }
        return lstElements.size();
    }

    @Override
    public List<String> getColumnHeaderValues() {
        List<WebElement> cols;
        List<String> colHeader = new ArrayList<>();
        try {
            if(element == null)
                cols = driver.findElements(By.xpath(headerXPath));
            else
                cols = element.findElements(By.xpath(headerXPath));

            cols.forEach((we) -> {
                if(we.getText().length() != 0)
                    colHeader.add(we.getText());
            });
        } catch (Exception e) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getColumnHeaderValues() of Table class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getColumnHeaderValues() of Table class." + e);
        }
        return colHeader;
    }

    @Override
    public List<WebElement> getColumnHeaderElements() {
        List<WebElement> columnElements;
        try {
            if(element == null)
                columnElements = driver.findElements(By.xpath(headerXPath));
            else
                columnElements = element.findElements(By.xpath(headerXPath));

            columnElements.removeIf((WebElement we) -> we.getText().length() == 0);
        } catch (Exception e) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getColumnHeaderValues() of Table class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getColumnHeaderValues() of Table class." + e);
        }
        return columnElements;
    }

    @Override
    public List<String> getValuesOfGivenColumns(String colName) {
        List<String> colHeader = getColumnHeaderValues();
        int colNum = 0;
        List<String> lstVals;
        if(colHeader.contains(colName)) {
            colNum = colHeader.indexOf(colName);
            lstVals = getValues(colNum);
        } else {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getValuesOfGivenColumns() of Table class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getValuesOfGivenColumns() of Table class.");
        }
        return lstVals;
    }

    private List<String> getValues(int colNum) {
        List<String> lstValues = new ArrayList<>();
        try {
            int rowCount = getRowCount();
            int colCount = getColumnCount();
            if(colCount < colNum) {
                Logger.error("Given colNum: " + colNum + " not available. Please check.");
                throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getValues() of Table class.");
            } else {
                for (int i=1; i<=rowCount; i++) {
                    String colXpath = String.format("//tbody/tr[%d]//td[%d]", i, colNum+1);
                    String val = driver.findElement(By.xpath(colXpath)).getText();
                    lstValues.add(val);
                }
            }
        } catch (Exception e) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getValues() of Table class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getValues() of Table class. " + e);
        }
        return lstValues;
    }

    public boolean tableOperations(String columnName, String valueToBeSelected, AXNS actions) {
        boolean flag = false;
        try {
            int columnIndex = getColumnIndexFromTableHeader(columnName);
            if( columnIndex != -1) {
                    int colIterator = 1;
                    String pathIndex = String.format("td[%d]", columnIndex);
                    List<WebElement> completeRowElements = getListOfElementsInGivenBy(By.cssSelector(COMPLETE_ROW));
                    for (WebElement elt : completeRowElements) {
                        String valueText = elt.findElement(By.xpath(pathIndex)).getText().trim();
                        if (valueToBeSelected.trim().equalsIgnoreCase(valueText)) {
                            flag = doActionAccordingToTag(actions, valueText, colIterator);
                            break;
                        }
                        colIterator++;
                    }
                }
        } catch(Exception ex) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " tableOperations() of Table class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " tableOperations() of Table class." + ex);
        }
        return flag;
    }

    public boolean tableOperationsWithPagination(String columnName, String valueToBeSelected, AXNS actions) {
        /*driver.findElement(By.xpath("//a[text()='<<']")).click();
        WebElement nextElement = driver.findElement(By.xpath("//a[text()='>']"));
        do {
            flag = selectTableValue(valueToBeSelected, actions, columnIndex);
            if(flag) break;
            if(nextElement.isDisplayed() && nextElement.isEnabled()) nextElement.click();
            else break;
        } while(true);
        int size = driver.findElements(By.xpath("//ul[@class='pager']//li[@class='ng-hide']")).size();
        if(size<=2) {
            flag = selectTableValue(valueToBeSelected, actions, columnIndex);
        } else {
            driver.findElement(By.xpath("//a[text()='<<']")).click();
            WebElement nextElement = driver.findElement(By.xpath("//a[text()='>']"));
            do {
                flag = selectTableValue(valueToBeSelected, actions, columnIndex);
                if(flag) break;
                if(nextElement.isDisplayed() && nextElement.isEnabled()) nextElement.click();
                else break;
            } while(true);
        }*/

        boolean flag = false;
        WebElement nextElement = driver.findElement(By.xpath("//a[text()='>']"));
        driver.findElement(By.xpath("//a[text()='<<']")).click();
        do {
            flag = tableOperations(columnName, valueToBeSelected, actions);
            if(!flag || nextElement.isDisplayed()) nextElement.click();
            else break;
        }while(true);
        return flag;
    }

    private boolean selectTableValue(String valueToBeSelected, AXNS actions, int columnIndex) {
        boolean flag = false;
        try {
            int colIterator = 1;
            String pathIndex = String.format("td[%d]", columnIndex);
            List<WebElement> completeRowElements = getListOfElementsInGivenBy(By.cssSelector(COMPLETE_ROW));
            for (WebElement elt : completeRowElements) {
                String valueText = elt.findElement(By.xpath(pathIndex)).getText().trim();
                if (valueToBeSelected.trim().equalsIgnoreCase(valueText)) {
                    flag = doActionAccordingToTag(actions, valueText, colIterator);
                    break;
                }
                colIterator++;
            }
        } catch(Exception ex) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " tableOperations() of Table class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " tableOperations() of Table class." + ex);
        }
        return flag;
    }

    private int getColumnIndexFromTableHeader(String columnName) {
        try {
            List<WebElement> colHeaders = getColumnHeaderElements();
            WebElement getFirstElementOfColName = colHeaders.stream().filter(columns -> columnName.toLowerCase().trim().equalsIgnoreCase(columns.getText()))
                    .findFirst().orElse(null);
            int columnIndex = -1;
            if(getFirstElementOfColName != null) {
                columnIndex = colHeaders.indexOf(getFirstElementOfColName) + 1;
            }
            columnIndex = columnIndex;
            return columnIndex;
        } catch(Exception ex) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getColumnIndexFromTableHeader() of Table class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getColumnIndexFromTableHeader() of Table class." + ex);
        }
    }

    private List<WebElement> getListOfElementsInGivenBy(By by) {
        List<WebElement> lstElements;
        try {
            if (element == null)
                lstElements = driver.findElements(by);
            else
                lstElements = element.findElements(by);
            return lstElements;
        } catch (Exception ex) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getListOfElementsInGivenBy() of Table class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getListOfElementsInGivenBy() of Table class." + ex);
        }
    }

    private boolean doActionAccordingToTag(AXNS type, String textForLink, int rowNumber) {
        boolean flag = false;
        try {
            WebElement elt = getRowElement(rowNumber, type);
            switch (type) {
                case VIEW:
                case EDIT:
                case DELETE:
                    elt.click();
                    flag = true;
                    break;
                default:
                    break;
            }
        } catch(Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " doActionAccordingToTag() of Table class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " doActionAccordingToTag() of Table class." + fe);
        }
        return flag;
    }

    private WebElement getRowElement(int rowNumber, AXNS enumTags) throws CustomException {
        if(rowNumber > getRowCount()) {
            throw new CustomException("Row count given is not available. There are only " + getRowCount() + " available.");
        }
        try {
            String tag = "";
            switch (enumTags) {
                case VIEW:
                    tag = "span[text()='View']";
                    break;
                case EDIT:
                    tag = "span[text()='Edit']";
                    break;
                case DELETE:
                    tag = "span[text()='Delete']";
                    break;
                default:
                    break;
            }

            String pathItem = String.format(rowXPath + "[%d]//" + tag, rowNumber);
            return driver.findElement(By.xpath(pathItem));

        } catch (Exception fe) {
            Logger.error(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getRowElement() of Table class");
            throw new CustomException(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getRowElement() of Table class." + fe);
        }
    }

    public enum AXNS {
        VIEW, EDIT, DELETE
    }
}
