package dataproviders;

import org.testng.annotations.DataProvider;

public class TestData {

    @DataProvider(name = "loginData")
    public static Object[][] loginData() {
        return new Object[][] {
                {"Admin", "admin123"},
                {"wrongUser", "wrongPass"},
                {"", ""}
        };
    }
}