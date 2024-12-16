package login;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {

    RemoteWebDriver driver;
    ConfigClass.AppConfig appConfig;
    String email;
    String password;

    @BeforeClass
    public void setUp(){
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1");
        appConfig = ConfigClass.AppConfig.getInstance("sherlock.properties");
        email = appConfig.getProperty("email");
        appConfig.validateRequiredProperties("email");
        password = appConfig.getProperty("password");
        appConfig.validateRequiredProperties("password");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
