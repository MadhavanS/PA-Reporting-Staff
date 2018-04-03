package com.snow.gk.core.ui.drivers;

import com.snow.gk.core.utils.Config;
import com.snow.gk.core.log.LogEventListener;
import com.snow.gk.core.log.Logger;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.concurrent.TimeUnit;

public class DriverSetup {

    private static final ThreadLocal<WebDriver> webDriverThreadLocal;
    private static WebDriver driver = null;
    public static boolean browserInitialized = false;


    static {
        webDriverThreadLocal = new ThreadLocal<>();
    }

    /**
     * To initialize the Driver
     * @param   browser
     *          - Browser (e.g. Chrome, Firefox IE etc)
     * @return
     */
    public static void initializeDriver(String browser) {
        if (("chromedriver").equalsIgnoreCase(browser)) {
            driver = initiateChromeDriver();
            Logger.info("Started Chrome Browser successfully.");
        } else if (("firefox").equalsIgnoreCase(browser)) {
            driver = initiateFireFoxDriver();
            Logger.info("Started Firefox Browser successfully.");
        }
        driver = new EventFiringWebDriver(driver).register(new LogEventListener());
        setDriver(driver);
        browserInitialized = true;
    }

    /**
     * To initialize the Chrome Driver
     * @param
     * @return  driver
     *          - chrome driver
     */
    private static WebDriver initiateChromeDriver() {
        WebDriver driver = null;
        try {
            String chromePath = Config.getConfig().getConfigProperty("webdriver.chrome.driver");
            System.setProperty("webdriver.chrome.driver", chromePath);

            ChromeDriverService chromeService = ChromeDriverService.createDefaultService();
            String commandSwitches = "WebDriverCommandSwitch";
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
            if (!commandSwitches.isEmpty() || commandSwitches.contains("--")) {
                Logger.info("User had specified [" + commandSwitches + "] command switch for Chrome Browser");
                ChromeOptions options = new ChromeOptions();
                String[] commandList = commandSwitches.split(",");
                for (String command : commandList) {
                    options.addArguments(command);
                    options.addArguments("--test-type");
                    options.addArguments("chrome.switches",	"--disable-extensions");
                }
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                driver = new ChromeDriver(chromeService, capabilities);
                Logger.info("Started Google Chrome Driver with command switches successfully");
            } else {
                Logger.debug("Starting the Chrome Driver");
                driver = new ChromeDriver(chromeService);
                Logger.info("Started Google Chrome Browser successfully");
            }
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            Logger.error("Failed to initiate Chrome Driver"+e);
        }
        return driver;
    }


    private static WebDriver initiateFireFoxDriver() {
        return null;
    }

    private static void setDriver(WebDriver driver){ webDriverThreadLocal.set(driver); }

    public static WebDriver getDriver() {
        driver = webDriverThreadLocal.get();
        if (driver == null)
            throw new IllegalStateException("Driver not set...");
        return driver;
    }

    public static void gotoUrl(String url) {
        driver.get(url);
        //Waits.waitForPageLoadJS();
        Logger.info("Navigated to " + url + " link");
    }
}
