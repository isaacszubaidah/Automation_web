package login;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
@Epic("SWAGLABS")
@Feature("Login")
@Story("Log in a user")
@Test
public class LoginTest extends BaseTest {

    @Description("As a user,I should be able to log in with valid credentials.")
    public void loginNewUser() {
        WebPage webPage = new WebPage(driver);
        ProductsPage productsPage = webPage.loginUser(email, password);
        assertTrue(productsPage.isProductPageDisplayed());
    }

    @Test(dataProvider = "invalid-login",dataProviderClass = WebPage.class)
    @Description("As an invalid user, I should not be able to login.")
    public void invalidLoginUser(String email, String password){
        WebPage webPage = new WebPage(driver);
        webPage.loginUser(email,password);
        assertTrue(webPage.isLoginPageDisplayed());
    }
}