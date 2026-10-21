package login;

import com.microsoft.playwright.Page;

public class ProductsPage {

    private final Page page;

    public ProductsPage(Page page) {
        this.page = page;
    }

    public boolean isProductPageDisplayed() {
        return page.locator(".title").isVisible();
    }
}