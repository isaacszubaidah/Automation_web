package login;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {

    RemoteWebDriver driver;
    @BeforeClass
    public void setUp(){
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1");
    }
    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
