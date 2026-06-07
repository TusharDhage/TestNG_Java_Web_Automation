package pages;

import org.openqa.selenium.*;

public class DashboardPage {

    private WebDriver driver;
    private WaitUtils wait;

    private By profileMenu = By.cssSelector(".oxd-userdropdown-tab");
    private By logoutBtn = By.linkText("Logout");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public void logout() {
        wait.waitForElementClickable(profileMenu).click();
        wait.waitForElementClickable(logoutBtn).click();
    }
}