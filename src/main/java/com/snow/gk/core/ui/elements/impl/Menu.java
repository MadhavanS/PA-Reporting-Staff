package com.snow.gk.core.ui.elements.impl;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.log.Logger;
import com.snow.gk.core.ui.elements.IElement;
import com.snow.gk.core.ui.elements.IMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Menu extends Element implements IMenu {
    public Menu() { super(); }

    public Menu(WebElement element, String elementName) {
        super(element, elementName);
    }

    private IElement getMainMenu() {
        return new Element(driver.findElement(By.xpath("//div[contains(@id,'context_list_title')]")));
    }

    private IElement getSubMenu() {
        return new Element( driver.findElement(By.xpath("//div[contains(@id,'context_list_title')]/following-sibling::div[@class='context_menu']")));
    }

    private IElement getMainMenuItem(String strItemName){
        return new Element( driver.findElement(By.xpath("//div[contains(@id,'context_list_title')]/div[contains(text(),'"+strItemName+"')]")));
    }
    private IElement getSubMenuItem(String strSubItemName){
        return new Element( driver.findElement(By.xpath("//div[contains(@id,'context_list_title')]/following-sibling::div[@class='context_menu']/div[contains(text(), '"+strSubItemName+"')]")));
    }

    @Override
    public void selectAdditionalActions(String strActionName, String strSubActionName) {
        try{
            if(getMainMenu().isElementLoaded()){
                getMainMenuItem(strActionName).click();
            }
            else {
                Logger.error("Failed to load main menu");
                throw new CustomException("Failed to load main menu");
            }
            if(! strSubActionName.isEmpty() ) {
                if(getSubMenu().isElementLoaded()){
                    getSubMenuItem(strSubActionName).click();
                }
                else {
                    Logger.error("Failed to load sub-menu");
                    throw new CustomException("Failed to load sub-menu");
                }
            }
        } catch (Exception e){
            Logger.error("Failed in selectAdditionalActions() method "+e);
            throw new CustomException("Failed in selectAdditionalActions() method "+e);
        }
    }
}
