package login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductsPage {

    RemoteWebDriver driver;
    WebDriverWait wait;
    @FindBy(css = ".product_label")
    public WebElement productPageHeader;

    public ProductsPage(RemoteWebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isProductPageDisplayed(){
        return wait.until(ExpectedConditions.visibilityOf(productPageHeader)).isDisplayed();
    }
}