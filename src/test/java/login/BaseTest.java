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

    // Isolated browser context and page for each test thread
    protected ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    protected ThreadLocal<Page> page = new ThreadLocal<>();

    @BeforeClass
    @Parameters({"browserName"})
    public void suiteSetup(@Optional("chromium") String browserName) {
        playwright = Playwright.create();

        BrowserType.LaunchOptions options =
                new BrowserType.LaunchOptions().setHeadless(true);

        switch (browserName.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(options);
                break;

            case "webkit":
                browser = playwright.webkit().launch(options);
                break;

            case "chromium":
            case "chrome":
                browser = playwright.chromium()
                        .launch(options.setChannel("chrome"));
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browserName
                );
        }
    }

    @BeforeMethod
    @Parameters({"url"})
    public void testSetup(
            @Optional("https://www.saucedemo.com/") String url) {

        BrowserContext testContext = browser.newContext();
        context.set(testContext);

        Page testPage = testContext.newPage();
        page.set(testPage);

        getPage().navigate(url);
    }

    public Page getPage() {
        return page.get();
    }

    @AfterMethod
    public void testTearDown() {
        try {
            BrowserContext testContext = context.get();

            if (testContext != null) {
                testContext.close();
            }
        } finally {
            page.remove();
            context.remove();
        }
    }

    @AfterClass
    public void suiteTearDown() {
        if (browser != null) {
            browser.close();
        }

        if (playwright != null) {
            playwright.close();
        }
    }
}