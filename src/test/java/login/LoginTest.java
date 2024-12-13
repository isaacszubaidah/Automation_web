package login;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@Test
public class LoginTest extends BaseTest {

    public void loginNewUser(){
        WebPage webPage = new WebPage(driver);
      ProductsPage productsPage= webPage.loginUser("standard_user","secret_sauce");
        assertTrue(productsPage.isProductPageDisplayed());
    }
}