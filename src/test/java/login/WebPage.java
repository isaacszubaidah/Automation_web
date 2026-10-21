package login;

import com.microsoft.playwright.Page;
import org.testng.annotations.DataProvider;

public class WebPage {

    private final Page page;

    public WebPage(Page page) {
        this.page = page;
    }

    @DataProvider(name = "invalid-login")
    public static Object[][] invalidLogin() {
        ConfigClass.AppConfig appConfig =
                ConfigClass.AppConfig.getInstance("sherlock.properties");

        return new Object[][]{
                {appConfig.getProperty("email"), ""},
                {"", ""},
                {"password", appConfig.getProperty("password")},
                {"null", ""},
                {"", "password"}
        };
    }

    public ProductsPage loginUser(String username, String password) {
        page.locator("[placeholder='Username']").fill(username);
        page.locator("[placeholder='Password']").fill(password);
        page.locator("#login-button").click();

        return new ProductsPage(page);
    }

    public boolean isProductPageDisplayed() {
        return page.locator(".title").isVisible();
    }
}