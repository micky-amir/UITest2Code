package websites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

import static org.junit.Assert.*;

public class Amazon {

    ChromeDriver driver;
    WebElement element;

    @Before
    public void initDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        // Quit the driver
        driver.quit();
    }

    /**
     * SK_1
     * Tamar
     */
    @Test
    public void SK_1_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Español - ES']"))).click();
        assertEquals("Carrito", driver.findElement(By.id("nav-cart-text-container")).getText());
    }

    /**
     * SK_2
     * Tamar
     * HTML refer to SK_1, SK_2
     */
    @Test
    public void SK_2_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        driver.findElement(By.xpath("//a[contains(text(),'Deals')]")).click();
        assertEquals("Deals and Promotions", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_3
     * Tamar
     * HTML refer to SK_1, SK_3
     */
    @Test
    public void SK_3_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        driver.findElement(By.xpath("//h2[text()='AmazonBasics']/../following-sibling::*[@class='a-cardui-footer']/a")).click();
        String searchWord = driver.findElement(By.cssSelector(".a-color-state.a-text-bold")).getText();
        assertEquals("amazonbasics", searchWord.replaceAll("\"", ""));

        List<WebElement> searchResults = driver.findElements(By.xpath
                ("//*[contains(@data-cel-widget, 'MAIN-SEARCH_RESULTS')]/descendant::a/span[contains(@class, 'a-text-normal')]"));
        for (int i = 0; i < 6; i++) {
            assertTrue(searchResults.get(i).getText().toLowerCase(Locale.ROOT).contains("amazon") && searchResults.get(i).getText().toLowerCase(Locale.ROOT).contains("basic"));
        }
    }

    /**
     * SK_4
     * Tamar
     * HTML refer to SK_1, SK_4
     */
    @Test
    public void SK_4_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        element = driver.findElement(By.id("twotabsearchtextbox"));
        element.sendKeys("watch");
        element.submit();
        String searchWord = driver.findElement(By.cssSelector(".a-color-state.a-text-bold")).getText();
        assertEquals("watch", searchWord.replaceAll("\"", ""));

        List<WebElement> searchResults = driver.findElements(By.xpath
                ("//*[contains(@data-cel-widget, 'MAIN-SEARCH_RESULTS')]/descendant::a/span[contains(@class, 'a-text-normal')]"));
        for (WebElement result : searchResults) {
            assertTrue(result.getText().toLowerCase(Locale.ROOT).contains("watch"));
        }
    }

    /**
     * SK_5
     * Tamar
     * HTML refer to SK_1
     */
    @Test
    public void SK_5_Tamar() {
        driver.get("https://www.amazon.com/");
        element = driver.findElement(By.id("navBackToTop"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click(element).perform();
        JavascriptExecutor executor = driver;
        assertEquals(0, (long) executor.executeScript("return window.pageYOffset;"));
    }

    /**
     * SK_6
     * Tamar
     * HTML refer to SK_1, SK_6
     */
    @Test
    public void SK_6_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        driver.findElement(By.id("nav-cart")).click();
        assertEquals("Your Amazon Cart is empty", driver.findElement(By.tagName("h2")).getText());
        assertTrue(driver.findElement(By.cssSelector(".sc-sign-in .a-button-text")).getText().contains("Sign in"));
        assertTrue(driver.findElement(By.cssSelector(".sc-sign-in .a-button:nth-child(2) a")).getText().contains("Sign up"));
    }

    /**
     * SK_7
     * Tamar
     * HTML refer to SK_1, SK_7
     */
    @Test
    public void SK_7_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        element = driver.findElement(By.id("main-content"));
        actions.moveToElement(element).perform();
        element.findElement(By.cssSelector(".as-title-block-right > a")).click();
        List<WebElement> results = driver.findElements(By.cssSelector(".apb-browse-searchresults-product"));
        for (WebElement element : results) {
            assertTrue(element.findElement(By.tagName("img")).isDisplayed());
            assertTrue(element.findElement(By.tagName("h2")).isDisplayed());
            assertTrue(element.findElement(By.cssSelector(".a-icon-star-small")).isDisplayed());
            assertTrue(element.findElement(By.className("a-price")).isDisplayed());
            assertEquals("$", element.findElement(By.className("a-price-symbol")).getText());
        }
    }

    /**
     * SK_8
     * Tamar
     * HTML refer to SK_1, SK_8
     */
    @Test
    public void SK_8_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        driver.findElement(By.id("searchDropdownBox")).click();
        driver.findElement(By.xpath("//option[text()='Arts & Crafts']")).click();
        element = driver.findElement(By.id("twotabsearchtextbox"));
        element.sendKeys("color");
        element.submit();
        assertTrue(driver.findElement(By.cssSelector("#departments ul .a-text-bold")).getText().contains("Arts & Crafts"));

        String searchWord = driver.findElement(By.cssSelector(".a-color-state.a-text-bold")).getText();
        assertEquals("color", searchWord.replaceAll("\"", ""));

        List<WebElement> searchResults = driver.findElements(By.xpath
                ("//*[contains(@data-cel-widget, 'MAIN-SEARCH_RESULTS')]/descendant::a/span[contains(@class, 'a-text-normal')]"));
        for (WebElement result : searchResults) {
            assertTrue(result.getText().toLowerCase(Locale.ROOT).contains("color"));
        }
    }

    /**
     * SK_9
     * Tamar
     * HTML refer to SK_1
     */
    @Test
    public void SK_9_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        driver.findElement(By.id("nav-global-location-slot")).click();
        assertNotEquals("none", driver.findElement(By.id("a-popover-3")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("a-dropdown-container"))).click();
        driver.findElement(By.xpath("//a[text()='United Kingdom']")).click();
        wait.until(ExpectedConditions.textToBe(By.id("GLUXCountryValue"), "United Kingdom"));
        driver.findElement(By.xpath("//button[@name='glowDoneButton']")).click();
        wait.until(ExpectedConditions.textToBe(By.id("nav-global-location-slot"), "Deliver to\nUnited Kingdom"));
    }

    /**
     * SK_10
     * Tamar
     * HTML refer to SK_1
     */
    @Test
    public void SK_10_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        driver.findElement(By.id("nav-global-location-slot")).click();
        assertNotEquals("none", driver.findElement(By.id("a-popover-3")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("GLUXZipUpdateInput"))).sendKeys("32958");

        driver.findElement(By.cssSelector("#GLUXZipUpdate .a-button-input")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.id("GLUXHiddenSuccessSelectedAddressPlaceholder"), "")));
        assertEquals("32958", driver.findElement(By.id("GLUXHiddenSuccessSelectedAddressPlaceholder")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".a-popover-footer > .a-button"))).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.id("nav-global-location-slot"), "Deliver to\nIsrael")));
        assertTrue(driver.findElement(By.id("nav-global-location-slot")).getText().contains("Sebastian 32958"));
    }

    /**
     * SK_11
     * Tamar
     * HTML refer to SK_1, SK_11
     */
    @Test
    public void SK_11_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();
        assertEquals("Cart", driver.findElement(By.id("nav-cart-text-container")).getText());

        driver.findElement(By.id("nav-hamburger-menu")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hmenu-content")));
        element = driver.findElement(By.xpath("//div[text()='Amazon Music']/.."));
        actions.moveToElement(element).click(element).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Amazon Music HD']"))).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#katana-unrec-5 h1")));
        assertTrue(element.getText().contains("AMAZON MUSIC HD"));
    }

    /**
     * SK_12
     * Tamar
     * HTML refer to SK_1, SK_12
     */
    @Test
    public void SK_12_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        driver.findElement(By.id("nav-orders")).click();
        assertEquals("Sign-In", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_13
     * Tamar
     * HTML refer to SK_1, SK_13
     */
    @Test
    public void SK_13_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        element = driver.findElement(By.id("nav-link-accountList"));
        actions.moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-accountList")).getCssValue("display"));
        assertTrue(driver.findElement(By.id("nav-flyout-ya-signin")).isDisplayed());
        List<WebElement> titlesElements = driver.findElements(By.className("nav-title"));
        assertEquals("Your Lists", titlesElements.get(0).getText());
        assertEquals("Your Account", titlesElements.get(1).getText());
        for (WebElement element : titlesElements) {
            assertTrue(element.findElements(By.xpath(".//../a")).size() > 1);
        }

        driver.findElement(By.xpath("//*[text()='Create a List']/..")).click();
        assertEquals("Lists", driver.findElement(By.className("al-intro-banner-header")).getText());
        assertEquals("Sign In", driver.findElement(By.className("a-button-text")).getText());
    }

    /**
     * SK_14
     * Tamar
     * HTML refer to SK_1, SK_12, SK_14
     */
    @Test
    public void SK_14_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        element = driver.findElement(By.id("nav-link-accountList"));
        actions.moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-accountList")).getCssValue("display"));
        assertTrue(driver.findElement(By.id("nav-flyout-ya-signin")).isDisplayed());
        List<WebElement> titlesElements = driver.findElements(By.className("nav-title"));
        assertEquals("Your Lists", titlesElements.get(0).getText());
        assertEquals("Your Account", titlesElements.get(1).getText());
        for (WebElement element : titlesElements) {
            assertTrue(element.findElements(By.xpath(".//../a")).size() > 1);
        }

        driver.findElement(By.xpath("//*[text()='Account']/..")).click();
        assertEquals("Your Account", driver.findElement(By.tagName("h1")).getText());
        List<WebElement> cardElements = driver.findElements(By.xpath("//h2[contains(@class, 'a-text-normal')]"));
        List<String> expectedTitles = new LinkedList<>(Arrays.asList("Your Orders", "Login & security", "Prime",
                "Gift cards", "Your Payments", "Your Profiles", "Your devices and content"));
        List<String> actualTitles = new LinkedList<>();
        for (WebElement element : cardElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        cardElements.clear();
        cardElements = driver.findElements(By.cssSelector(".a-box.ya-card h2"));
        expectedTitles.clear();
        expectedTitles.addAll(Arrays.asList("Ordering and shopping preferences", "Digital content and devices",
                "Memberships and subscriptions", "Communication and content", "Shopping programs and rentals",
                "Other programs"));
        actualTitles.clear();
        for (WebElement element : cardElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);

        driver.findElement(By.xpath("//h2[contains(text(),'Your Profiles')]")).click();
        assertEquals("Sign-In", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_15
     * Tamar
     * HTML refer to SK_1, SK_4, SK_12, SK_15
     */
    @Test
    public void SK_15_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        element = driver.findElement(By.id("twotabsearchtextbox"));
        element.sendKeys("watch");
        element.submit();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-cel-widget='search_result_4'] a.a-text-normal")));
        String itemName = element.getText();
        element.click();
        assertEquals(itemName, driver.findElement(By.id("productTitle")).getText());
        driver.findElement(By.id("buy-now-button")).click();
        assertEquals("Sign-In", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_16
     * Tamar
     * HTML refer to SK_1, SK_4, SK_15, SK_16
     */
    @Test
    public void SK_16_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        element = driver.findElement(By.id("twotabsearchtextbox"));
        element.sendKeys("watch");
        element.submit();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-cel-widget='search_result_4'] a.a-text-normal")));
        String itemName = element.getText();
        element.click();
        assertEquals(itemName, driver.findElement(By.id("productTitle")).getText());
        driver.findElement(By.id("add-to-cart-button")).click();
        driver.findElement(By.id("nav-cart")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".sc-product-link .a-truncate-cut")));
        assertTrue(itemName.contains(element.getText()) || element.getText().contains(itemName));
    }

    /**
     * SK_17
     * Tamar
     * HTML refer to SK_1, SK_4, SK_12, SK_15
     */
    @Test
    public void SK_17_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        element = driver.findElement(By.id("twotabsearchtextbox"));
        element.sendKeys("watch");
        element.submit();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-cel-widget='search_result_4'] a.a-text-normal")));
        String itemName = element.getText();
        element.click();
        assertEquals(itemName, driver.findElement(By.id("productTitle")).getText());
        driver.findElement(By.cssSelector("a[title='Add to List']")).click();
        assertEquals("Sign-In", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_18
     * Tamar
     * HTML refer to SK_1, SK_18
     */
    @Test
    public void SK_18_Tamar() throws InterruptedException {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).build().perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).build().perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        Thread.sleep(1000);
        driver.findElement(By.className("icp-flyout-change")).click();

        assertEquals("Language Settings", driver.findElement(By.id("lop-heading")).getText());
        assertEquals("Currency Settings", driver.findElement(By.id("icp-sc-heading")).getText());
        driver.findElement(By.cssSelector(".a-button-dropdown")).click();
        assertNotEquals("none", driver.findElement(By.id("a-popover-3")).getCssValue("display"));
        driver.findElement(By.xpath("//a[contains(text(), 'ILS')]")).click();
        driver.findElement(By.className("a-button-input")).click();

        element = driver.findElement(By.id("twotabsearchtextbox"));
        element.sendKeys("watch");
        element.submit();
        List<WebElement> prices = driver.findElements(By.className("a-price-symbol"));
        for (WebElement element : prices) {
            if(!driver.findElement(By.xpath(".//ancestor::li")).isDisplayed())
                assertEquals("ILS", element.getText());
        }
    }
}
