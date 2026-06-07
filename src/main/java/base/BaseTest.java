package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

import java.lang.reflect.Method;

/**
 * Base class for all test classes.
 * Handles driver init/teardown and URL navigation.
 * All test classes extend this.
 */
public class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    protected WebDriver driver;

    @BeforeMethod
    public void setup(Method method) {
        log.info("── Starting test: [{}] ──", method.getName());
        log.info("Env: [{}] | Browser: [{}]",
                System.getProperty("env", "dev"),
                System.getProperty("browser", ConfigReader.get("browser", "chrome")));

        driver = DriverFactory.initDriver();
        String url = ConfigReader.get("baseUrl");
        driver.get(url);
        log.info("Navigated to: {}", url);
    }

    @AfterMethod
    public void tearDown(Method method) {
        log.info("── Finished test: [{}] ──", method.getName());
        DriverFactory.quitDriver();
    }
}
