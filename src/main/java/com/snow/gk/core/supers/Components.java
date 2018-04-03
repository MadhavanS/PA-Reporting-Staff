package com.snow.gk.core.supers;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.ui.elements.IElement;
import com.snow.gk.core.log.Logger;
import com.snow.gk.core.utils.Config;
import com.snow.gk.core.utils.Constants;
import com.snow.gk.core.utils.Waits;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Components {
    static BidiMap locatorsMap = new TreeBidiMap();

    /*
     * Create an object for a particular element which is passed as a parameter.
     *
     * @param Class<E>
     *       clazz - element ( e.g. Button, TextField etc) into which IElement need to be converted
     * @return <E extends IElement> E
     *         constructor.newInstance() - new instance object for the parameter(element class)
     *
     * */
    public <E extends IElement> E getComponent(Class<E> elementClass) {
        try {
            Waits.waitForPageLoadJS();
            Constructor<E> constructor = elementClass.getConstructor();
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            Logger.error(String.valueOf(e));
            Logger.error("Failed in getComponents() method of Components class");
        }
        return null;
    }

    /*
     * Create an object for a particular element which is passed as a parameter.
     *
     * @param WebElement
     *       element - webelement for which user want to convert
     * @param Class<E>
     *       clazz - element ( e.g. Button, TextField etc) into which IElement need to be converted
     * @return <E extends IElement> E
     *         constructor.newInstance() - new instance object for the parameter(element class)
     *
     * */
    public <E extends IElement> E getComponent(final WebElement element, Class<E> clazz) {
        Class callClazz;
        try {
            Waits.waitForPageLoadJS();
            WebElement waitElementStatus = Waits.componentWait(element, Integer.parseInt(Config.getConfig().getConfigProperty(Constants.ELEMENTWAITTIME)));
            if(waitElementStatus != null) {
                String xp = element.toString().split("-> ")[1].trim();
                String elementName = getVariableNameFromValue(this.getClass(), xp.substring(0, xp.length() - 1).split(": ")[1]);
                Constructor<E> constructor = clazz.getConstructor(WebElement.class, String.class);
                return constructor.newInstance(element, elementName);
            } else {
                System.out.println("ELEMENT IS NULL");
                Constructor<E> constructor = clazz.getConstructor();
                return constructor.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            Logger.error(String.valueOf(e));
        } catch(WebDriverException wex) {
            return null;
        } catch (CustomException ce) {
            callClazz = this.getClass();
            String s = getVariableCausedException(callClazz, element);
            Logger.error("Failed to creating interaction :: " + s + " -- " +ce );
            Logger.error("Failed in getComponents() method of Components class");
        }
        return null;
    }

    /*
     * To get the variable name.
     *
     * @param Class
     *       clazz - Send the class where webElement is located
     *             - To get the variable name for logging purpose
     * @param String
     *       value - variable Name as per selenium internals
     * @return String
     *          - variable Name as per selenium internals
     *
     * */
    public String getVariableNameFromValue(Class clazz, String value) {
        if(locatorsMap.containsValue(value)) {
            String key = (String) locatorsMap.getKey(value);
            String[] arrOfPackages = key.split(clazz.getName() + ".");
            return arrOfPackages[arrOfPackages.length-1];
        } else {
            Field[] fields = clazz.getDeclaredFields();
            for (Field fld : fields)
                try {
                    String variableName = fld.getDeclaredAnnotations()[0].toString();
                    if (variableName.contains(value)) {
                        locatorsMap.put(clazz.getName() + "." + fld.getName(), value);
                        return fld.getName();
                    }
                } catch (Exception e) {
                    Logger.error(String.valueOf(e));
                }

            return  value;
        }
    }

    /*
     * o get the exception caused by variables.
     *
     * @param Class
     *       callClass - Send the class where webElement is located
     *                 - To get the variable name for logging purpose
     * @param WebElement
     *       element - webelement
     * @return String
     *          - variable Name as per selenium internals
     *
     * */
    public String getVariableCausedException(Class callClass, WebElement element) {
        Field [] field = callClass.getDeclaredFields();
        String var = null;
        for (Field fld:field) {
            if(fld.isAnnotationPresent(FindBy.class))
            {
                fld.setAccessible(true);
                try {
                    if(fld.get(this).toString().equalsIgnoreCase(element.toString())) {
                        var = fld.getName();
                        break;
                    }
                } catch (IllegalAccessException e) {
                    Logger.error(String.valueOf(e));
                }
            }
        }
        return var;
    }

    public static String getElementName(String how) {
        return (String)locatorsMap.getKey(how);
    }
}
