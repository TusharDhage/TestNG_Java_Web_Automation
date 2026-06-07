package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class LoginPage {

    private WebDriver driver;
    private WaitUtils wait;

    private By username = By.name("username");
    private By password = By.name("password");
    private By loginBtn = By.xpath("//button[@type='submit']");
    private By errorMsg = By.xpath("//span[contains(@class,'oxd-text')]");
    private By invalidLoginErrorMsg = By.xpath("//p[contains(@class,'oxd-alert-content')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public void login(String user, String pass){
        wait.waitForElementVisible(username).sendKeys(user);
        wait.waitForElementVisible(password).sendKeys(pass);
        wait.waitForElementClickable(loginBtn).click();
    }

    public String getErrorMessage() {
        return wait.waitForElementVisible(errorMsg).getText();
    }

    public String getInvalidLoginErrorMessage() {
        return wait.waitForElementVisible(invalidLoginErrorMsg).getText();
    }
}