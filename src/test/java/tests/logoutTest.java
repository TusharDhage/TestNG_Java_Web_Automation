package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

public class logoutTest extends BaseTest {
    @Test
    public void testLogout() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.logout();

        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }
}
