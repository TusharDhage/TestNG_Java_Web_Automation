package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * JavaScriptExecutor helpers.
 * Use when standard Selenium interactions fail due to overlays or animations.
 */
public class JavaScriptUtil {

    private static final Logger log = LogManager.getLogger(JavaScriptUtil.class);

    private JavaScriptUtil() {}

    private static JavascriptExecutor js(WebDriver driver) {
        return (JavascriptExecutor) driver;
    }

    public static void click(WebDriver driver, WebElement element) {
        log.debug("JS click: {}", element);
        js(driver).executeScript("arguments[0].click();", element);
    }

    public static void setValue(WebDriver driver, WebElement element, String value) {
        js(driver).executeScript("arguments[0].value=arguments[1];", element, value);
    }

    public static void scrollIntoView(WebDriver driver, WebElement element) {
        js(driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    public static void scrollToTop(WebDriver driver) {
        js(driver).executeScript("window.scrollTo(0, 0);");
    }

    public static void scrollToBottom(WebDriver driver) {
        js(driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public static void highlightElement(WebDriver driver, WebElement element) {
        String original = element.getAttribute("style");
        js(driver).executeScript("arguments[0].setAttribute('style','border:3px solid red');", element);
        WaitUtil.hardWait(400);
        js(driver).executeScript("arguments[0].setAttribute('style',arguments[1]);", element, original);
    }

    public static void waitForPageLoad(WebDriver driver) {
        WaitUtil.waitFor(driver,
                d -> js(d).executeScript("return document.readyState").equals("complete"));
        log.debug("Page load complete");
    }

    public static String getPageTitle(WebDriver driver) {
        return (String) js(driver).executeScript("return document.title;");
    }
}
