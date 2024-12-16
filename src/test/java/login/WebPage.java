package login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;

import java.time.Duration;

public class WebPage {

    RemoteWebDriver driver;
    WebDriverWait wait;
    static ConfigClass.AppConfig appConfig;

    @FindBy(css = "[placeholder='Username']")
    public WebElement usernameInput;

    @FindBy(css = "[placeholder='Password']")
    public WebElement passwordInput;
    @FindBy(id = "login-button")
    public WebElement loginButton;
    @FindBy(css = ".bot_column")
    public WebElement loginPageImage;

    public WebPage(RemoteWebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @DataProvider(name = "invalid-login")
    public static Object[][] invalidLogin() {
        appConfig = ConfigClass.AppConfig.getInstance("sherlock.properties");
        return new Object[][]{
                {appConfig.getProperty("email"), ""},
                {"", ""},
                {"password",appConfig.getProperty("password")},
                {"null", ""},
                {"","password"}
        };
    }

    public ProductsPage loginUser(String username, String password) {
        wait.until(ExpectedConditions.visibilityOf(usernameInput)).click();
        usernameInput.sendKeys(username);
        wait.until(ExpectedConditions.visibilityOf(passwordInput)).click();
        passwordInput.sendKeys(password);
        loginButton.click();
        return new ProductsPage(driver);
    }

    public boolean isLoginPageDisplayed(){
        return wait.until(ExpectedConditions.visibilityOf(loginPageImage)).isDisplayed();
    }
}