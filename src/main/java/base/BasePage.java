package base;

import constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.JavaScriptUtil;
import utils.WaitUtil;

import java.time.Duration;
import java.util.List;

/**
 * Parent class for all Page Objects.
 *
 * Every page object extends BasePage and gets:
 *   - driver reference
 *   - all common Selenium actions (click, type, select, get text…)
 *   - built-in explicit waits before every action
 *   - JS fallbacks for tricky elements
 *
 * Usage:
 *   public class LoginPage extends BasePage {
 *       public LoginPage(WebDriver driver) { super(driver); }
 *   }
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger log = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        int timeout = ConfigReader.getInt("explicitWait", Constants.DEFAULT_EXPLICIT_WAIT);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        PageFactory.initElements(driver, this);
    }

    // ─── Navigation ───────────────────────────────────────────────────────────

    public void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void refreshPage() {
        driver.navigate().refresh();
        log.debug("Page refreshed");
    }

    public void goBack() {
        driver.navigate().back();
        log.debug("Navigated back");
    }

    // ─── Click ────────────────────────────────────────────────────────────────

    public void click(By locator) {
        log.debug("Click: {}", locator);
        WaitUtil.waitForClickable(driver, locator).click();
    }

    public void click(WebElement element) {
        log.debug("Click element: {}", element);
        WaitUtil.waitForClickable(driver, element).click();
    }

    /** JS click — use when standard click is blocked by overlay/animation */
    public void jsClick(By locator) {
        log.debug("JS click: {}", locator);
        WebElement el = WaitUtil.waitForPresent(driver, locator);
        JavaScriptUtil.click(driver, el);
    }

    // ─── Type / Input ─────────────────────────────────────────────────────────

    public void type(By locator, String text) {
        log.debug("Type [{}] into: {}", text, locator);
        WebElement el = WaitUtil.waitForVisible(driver, locator);
        el.clear();
        el.sendKeys(text);
    }

    public void type(WebElement element, String text) {
        log.debug("Type [{}] into element", text);
        WaitUtil.waitForVisible(driver, element);
        element.clear();
        element.sendKeys(text);
    }

    public void clearAndType(By locator, String text) {
        WebElement el = WaitUtil.waitForVisible(driver, locator);
        el.sendKeys(Keys.CONTROL + "a");
        el.sendKeys(Keys.DELETE);
        el.sendKeys(text);
    }

    /** Sets value via JS — useful for date pickers or read-only inputs */
    public void jsType(By locator, String value) {
        log.debug("JS type [{}] into: {}", value, locator);
        WebElement el = WaitUtil.waitForPresent(driver, locator);
        JavaScriptUtil.setValue(driver, el, value);
    }

    // ─── Get text / attribute ─────────────────────────────────────────────────

    public String getText(By locator) {
        String text = WaitUtil.waitForVisible(driver, locator).getText();
        log.debug("getText [{}] from: {}", text, locator);
        return text;
    }

    public String getText(WebElement element) {
        return WaitUtil.waitForVisible(driver, element).getText();
    }

    public String getAttribute(By locator, String attribute) {
        return WaitUtil.waitForPresent(driver, locator).getAttribute(attribute);
    }

    public String getInputValue(By locator) {
        return getAttribute(locator, "value");
    }

    // ─── Visibility checks ────────────────────────────────────────────────────

    public boolean isDisplayed(By locator) {
        try {
            return WaitUtil.waitForVisible(driver, locator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean isEnabled(By locator) {
        try {
            return WaitUtil.waitForPresent(driver, locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSelected(By locator) {
        try {
            return WaitUtil.waitForPresent(driver, locator).isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    // ─── Dropdown (standard HTML select) ─────────────────────────────────────

    public void selectByVisibleText(By locator, String text) {
        log.debug("Select [{}] from: {}", text, locator);
        new Select(WaitUtil.waitForVisible(driver, locator)).selectByVisibleText(text);
    }

    public void selectByValue(By locator, String value) {
        log.debug("Select by value [{}] from: {}", value, locator);
        new Select(WaitUtil.waitForVisible(driver, locator)).selectByValue(value);
    }

    public void selectByIndex(By locator, int index) {
        new Select(WaitUtil.waitForVisible(driver, locator)).selectByIndex(index);
    }

    public String getSelectedOption(By locator) {
        return new Select(WaitUtil.waitForVisible(driver, locator))
                .getFirstSelectedOption().getText();
    }

    // ─── Checkbox / Radio ─────────────────────────────────────────────────────

    public void check(By locator) {
        WebElement el = WaitUtil.waitForClickable(driver, locator);
        if (!el.isSelected()) {
            el.click();
            log.debug("Checked: {}", locator);
        }
    }

    public void uncheck(By locator) {
        WebElement el = WaitUtil.waitForClickable(driver, locator);
        if (el.isSelected()) {
            el.click();
            log.debug("Unchecked: {}", locator);
        }
    }

    // ─── Wait helpers exposed to page objects ─────────────────────────────────

    public void waitForVisible(By locator) {
        WaitUtil.waitForVisible(driver, locator);
    }

    public void waitForInvisible(By locator) {
        WaitUtil.waitForInvisible(driver, locator);
    }

    public void waitForUrlContains(String fragment) {
        WaitUtil.waitForUrlContains(driver, fragment);
    }

    public void waitForTitleContains(String title) {
        WaitUtil.waitForTitleContains(driver, title);
    }

    // ─── Scroll ───────────────────────────────────────────────────────────────

    public void scrollToElement(By locator) {
        WebElement el = WaitUtil.waitForPresent(driver, locator);
        JavaScriptUtil.scrollIntoView(driver, el);
    }

    public void scrollToTop() {
        JavaScriptUtil.scrollToTop(driver);
    }

    public void scrollToBottom() {
        JavaScriptUtil.scrollToBottom(driver);
    }

    // ─── Multi-element helpers ────────────────────────────────────────────────

    public List<WebElement> getElements(By locator) {
        return WaitUtil.waitForAllVisible(driver, locator);
    }

    public int getElementCount(By locator) {
        return driver.findElements(locator).size();
    }

    // ─── Alert ────────────────────────────────────────────────────────────────

    public void acceptAlert() {
        WaitUtil.waitForAlert(driver).accept();
        log.debug("Alert accepted");
    }

    public void dismissAlert() {
        WaitUtil.waitForAlert(driver).dismiss();
        log.debug("Alert dismissed");
    }

    public String getAlertText() {
        return WaitUtil.waitForAlert(driver).getText();
    }

    // ─── Window / Tab ─────────────────────────────────────────────────────────

    public void switchToNewTab() {
        String current = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(current)) {
                driver.switchTo().window(handle);
                log.debug("Switched to new tab");
                return;
            }
        }
    }

    public void closeCurrentTab() {
        driver.close();
    }

    // ─── Frame ────────────────────────────────────────────────────────────────

    public void switchToFrame(By locator) {
        driver.switchTo().frame(WaitUtil.waitForPresent(driver, locator));
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }
}
