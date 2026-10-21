
package login;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Epic("SWAGLABS")
@Feature("Login")
@Story("Log in a user")
public class LoginTest extends BaseTest {

    @Test
    @Description("As a user, I should be able to log in with valid credentials.")
    public void loginNewUser() {
        ConfigClass.AppConfig appConfig =
                ConfigClass.AppConfig.getInstance("sherlock.properties");

        WebPage webPage = new WebPage(getPage());

        ProductsPage productsPage = webPage.loginUser(
                appConfig.getProperty("email"),
                appConfig.getProperty("password")
        );

        getPage().waitForURL("**/inventory.html");

        assertTrue(
                productsPage.isProductPageDisplayed(),
                "The products page should be displayed after a successful login."
        );
    }

    @Test(
        dataProvider = "invalid-login",
        dataProviderClass = WebPage.class
    )
    @Description("As a user with invalid credentials, I should not be able to log in.")
    public void invalidLoginUser(String email, String password) {
        WebPage webPage = new WebPage(getPage());

        webPage.loginUser(email, password);

        assertFalse(
                webPage.isProductPageDisplayed(),
                "The products page should not be displayed after an unsuccessful login."
        );
    }
}