package com.snow.gk.core.supers;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.log.Logger;
import com.snow.gk.core.ui.drivers.DriverSetup;
import com.snow.gk.core.utils.Config;
import com.snow.gk.core.utils.Waits;
import com.snow.gk.gurukula.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    protected WebDriver driver;
    protected String strUsername;
    protected String strPassword;
    protected String strVerifyUsername;
    protected String baseURL;

    @BeforeClass(alwaysRun = true)
    public void init() {
        try {
            Logger.info("================================ TEST STARTS NOW  =========================================");
        } catch (Exception e) {
            System.out.println("Exceptions:  " + e);
        }
    }

    @BeforeClass(alwaysRun = true)
    public void startingMethodPreReq() {
        try {
            String browserEnvironmentVariable = System.getenv("BROWSER");
            String browser;
            if (browserEnvironmentVariable == null || browserEnvironmentVariable.equalsIgnoreCase("null")) {
                browser = Config.getConfig().getConfigProperty("driver.name");
            } else {
                browser = browserEnvironmentVariable;
            }
            DriverSetup.initializeDriver(browser);
            driver = DriverSetup.getDriver();
            driver.manage().window().maximize();
            baseURL = Config.getConfig().getConfigProperty("baseurl") + ":" +Config.getConfig().getConfigProperty("port");
            System.out.println("Base URL: " + baseURL);
            Logger.info(baseURL);
            Logger.info("================================ TEST METHOD STARTS  =========================================");
        } catch (final Exception e) {
            Logger.error("Failed to start the scenario"+ e);
        }
    }

    @AfterClass(alwaysRun = true)
    public void logoutAndCloseOpenedBrowsers() throws CustomException {
        try {
            if (DriverSetup.browserInitialized && driver != null) {
                driver.quit();
            }
            Logger.info("================================ TEST METHOD ENDS  =========================================");
        }
        catch (Exception ex)
        {
            driver.quit();
            throw new CustomException("Failed to Close the Opened browsers" + ex);
        }
    }

    protected void logout() throws CustomException {}


    // Login to TP Application
    protected void gotoUrl(String url) {
        driver.get(url);
        Waits.waitForPageLoadJS();
        Logger.info("Navigated to " + url + " link");
    }

    // Prepare test data for Login
    protected void prepareLoginTestData(String userName, String password, String verifyUserName) {
        strUsername = userName;
        strPassword = password;
        strVerifyUsername = verifyUserName;
    }

    protected String getUrl() {
        return driver.getCurrentUrl();
    }

    protected String getTitle() {
        return driver.getTitle();
    }
}
