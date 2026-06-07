package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

/**
 * Extra element interaction utilities beyond what BasePage covers.
 *
 * Usage:
 *   ElementUtil.hover(driver, By.cssSelector(".menu-item"));
 *   ElementUtil.getTextFromAll(driver, By.cssSelector(".product-name"));
 */
public class ElementUtil {

    private static final Logger log = LogManager.getLogger(ElementUtil.class);

    private ElementUtil() {}

    /** Hovers over an element — triggers CSS :hover dropdowns. */
    public static void hover(WebDriver driver, By locator) {
        log.debug("Hover: {}", locator);
        WebElement el = WaitUtil.waitForVisible(driver, locator);
        new Actions(driver).moveToElement(el).perform();
    }

    /** Double-clicks an element. */
    public static void doubleClick(WebDriver driver, By locator) {
        log.debug("Double click: {}", locator);
        WebElement el = WaitUtil.waitForClickable(driver, locator);
        new Actions(driver).doubleClick(el).perform();
    }

    /** Right-clicks (context menu) an element. */
    public static void rightClick(WebDriver driver, By locator) {
        log.debug("Right click: {}", locator);
        WebElement el = WaitUtil.waitForClickable(driver, locator);
        new Actions(driver).contextClick(el).perform();
    }

    /** Drag and drop from source to target. */
    public static void dragAndDrop(WebDriver driver, By source, By target) {
        log.debug("Drag {} → {}", source, target);
        WebElement src = WaitUtil.waitForClickable(driver, source);
        WebElement tgt = WaitUtil.waitForClickable(driver, target);
        new Actions(driver).dragAndDrop(src, tgt).perform();
    }

    /** Sends a key press to a specific element (e.g. Keys.ENTER, Keys.TAB). */
    public static void pressKey(WebDriver driver, By locator, Keys key) {
        WaitUtil.waitForVisible(driver, locator).sendKeys(key);
    }

    /** Returns text from all matching elements as a List<String>. */
    public static List<String> getTextFromAll(WebDriver driver, By locator) {
        List<WebElement> elements = WaitUtil.waitForAllVisible(driver, locator);
        List<String> texts = new ArrayList<>();
        for (WebElement el : elements) {
            texts.add(el.getText().trim());
        }
        log.debug("Collected {} texts from: {}", texts.size(), locator);
        return texts;
    }

    /** Clicks the element at a specific index from a list of matching elements. */
    public static void clickByIndex(WebDriver driver, By locator, int index) {
        List<WebElement> elements = WaitUtil.waitForAllVisible(driver, locator);
        if (index >= elements.size()) {
            throw new RuntimeException("Index " + index + " out of bounds for locator: " + locator
                    + " (found " + elements.size() + " elements)");
        }
        elements.get(index).click();
        log.debug("Clicked index [{}] of: {}", index, locator);
    }

    /** Returns true if any element matching locator contains the given text. */
    public static boolean isTextPresentInList(WebDriver driver, By locator, String text) {
        List<String> texts = getTextFromAll(driver, locator);
        return texts.stream().anyMatch(t -> t.contains(text));
    }

    /** Checks if element exists in DOM at all (no wait). */
    public static boolean isElementPresent(WebDriver driver, By locator) {
        return !driver.findElements(locator).isEmpty();
    }
}
