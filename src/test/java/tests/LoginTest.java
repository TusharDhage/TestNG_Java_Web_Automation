package tests;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.LoginPage;
import dataproviders.TestData;
import utils.LoggerUtil;


public class LoginTest extends BaseTest {
    private static final Logger log = LoggerUtil.getLogger(LoginTest.class);

    @Test(dataProvider = "loginData", dataProviderClass = TestData.class, retryAnalyzer = listeners.RetryAnalyzer.class)
    public void testLogin(String username, String password){
        log.info("Starting login test");
        log.info("Executing login test with user: " + username);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
    }

    @Test
    public void invalidLoginTest(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wrongUser", "wrongPass");

        String error = loginPage.getInvalidLoginErrorMessage();
        Assert.assertEquals(error, "Invalid credentials");
    }

    @Test
    public void emptyLoginTest(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Required"));
    }

    @Test
    public void verifyDashboardVisible(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
    }

}