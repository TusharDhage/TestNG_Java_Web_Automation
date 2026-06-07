package utils;

import constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Centralised explicit wait factory.
 * All waits in the framework go through here — no Thread.sleep anywhere else.
 */
public class WaitUtil {

    private static final Logger log = LogManager.getLogger(WaitUtil.class);

    private WaitUtil() {}

    public static WebDriverWait getWait(WebDriver driver) {
        int timeout = ConfigReader.getInt("explicitWait", Constants.DEFAULT_EXPLICIT_WAIT);
        return new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    public static WebDriverWait getWait(WebDriver driver, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    public static FluentWait<WebDriver> getFluentWait(WebDriver driver) {
        int timeout = ConfigReader.getInt("explicitWait", Constants.DEFAULT_EXPLICIT_WAIT);
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofMillis(Constants.POLLING_INTERVAL_MS))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    // ─── Visibility ───────────────────────────────────────────────────────────

    public static WebElement waitForVisible(WebDriver driver, By locator) {
        log.debug("Waiting visible: {}", locator);
        return getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForVisible(WebDriver driver, WebElement element) {
        return getWait(driver).until(ExpectedConditions.visibilityOf(element));
    }

    public static List<WebElement> waitForAllVisible(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // ─── Clickability ─────────────────────────────────────────────────────────

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        log.debug("Waiting clickable: {}", locator);
        return getWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, WebElement element) {
        return getWait(driver).until(ExpectedConditions.elementToBeClickable(element));
    }

    // ─── Presence ─────────────────────────────────────────────────────────────

    public static WebElement waitForPresent(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // ─── Invisibility ─────────────────────────────────────────────────────────

    public static boolean waitForInvisible(WebDriver driver, By locator) {
        log.debug("Waiting invisible: {}", locator);
        return getWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // ─── URL / Title ──────────────────────────────────────────────────────────

    public static boolean waitForUrlContains(WebDriver driver, String fragment) {
        return getWait(driver).until(ExpectedConditions.urlContains(fragment));
    }

    public static boolean waitForTitleContains(WebDriver driver, String title) {
        return getWait(driver).until(ExpectedConditions.titleContains(title));
    }

    // ─── Alert ────────────────────────────────────────────────────────────────

    public static Alert waitForAlert(WebDriver driver) {
        return getWait(driver).until(ExpectedConditions.alertIsPresent());
    }

    // ─── Custom condition ─────────────────────────────────────────────────────

    public static <T> T waitFor(WebDriver driver, ExpectedCondition<T> condition) {
        return getWait(driver).until(condition);
    }

    // ─── Hard wait (use sparingly) ────────────────────────────────────────────

    public static void hardWait(long ms) {
        log.warn("Hard wait {}ms — replace with explicit wait if possible", ms);
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
