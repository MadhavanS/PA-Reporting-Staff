package com.snow.gk.core.supers;

import com.snow.gk.core.ui.drivers.DriverSetup;
import org.openqa.selenium.support.PageFactory;

public class AllPages {
    private AllPages(){}

    public static <T> T getPage(Class<T> clazz) {
        return PageFactory.initElements(DriverSetup.getDriver(), clazz);
    }
}
