package login;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    
    // ThreadLocal manages isolated incognito contexts and tabs per test
    protected ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    protected ThreadLocal<Page> page = new ThreadLocal<>();

    @BeforeClass
    @Parameters({"browserName"})
    public void suiteSetup(@Optional("chromium") String browserName) {
        playwright = Playwright.create();
        
        // Launch a single browser server process for the class
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(false);
        if (browserName.equalsIgnoreCase("firefox")) {
            browser = playwright.firefox().launch(options);
        } else if (browserName.equalsIgnoreCase("webkit")) {
            browser = playwright.webkit().launch(options);
        } else {
            browser = playwright.chromium().launch(options.setChannel("chrome"));
        }
    }

    @BeforeMethod
    @Parameters({"url"})
    public void testSetup(@Optional("https://www.saucedemo.com/") String url) {
        // Create an isolated incognito session for this specific test method
        BrowserContext testContext = browser.newContext();
        Page testPage = testContext.newPage();
        
        context.set(testContext);
        page.set(testPage);
        
        getPage().navigate(url);
    }

    public Page getPage() {
        return page.get();
    }

    @AfterMethod
    public void testTearDown() {
        // Close the specific tab and isolated session cleanly
        if (getPage() != null) {
            getPage().close();
            context.get().close();
            page.remove();
            context.remove();
        }
    }

    @AfterClass
    public void suiteTearDown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}