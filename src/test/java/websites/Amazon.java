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
            if (!driver.findElement(By.xpath(".//ancestor::li")).isDisplayed())
                assertEquals("ILS", element.getText());
        }
    }

    /**
     * SK_19
     * Tamar
     * HTML refer to SK_1, SK_4, SK_15, SK_16
     */
    @Test
    public void SK_19_Tamar() {
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
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-cel-widget='search_result_3'] a.a-text-normal")));
        element.click();
        driver.findElement(By.id("add-to-cart-button")).click();
        driver.findElement(By.id("nav-cart")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".sc-product-link .a-truncate-cut")));
        String itemName = element.getText();
        driver.findElement(By.cssSelector("[value='Delete']")).click();
        assertFalse(driver.findElement(By.cssSelector(".sc-product-link .a-truncate-cut")).isDisplayed());
        assertFalse(driver.findElement(By.cssSelector(".sc-product-link .a-truncate-cut")).isDisplayed() && driver.findElement(By.cssSelector(".sc-product-link .a-truncate-cut")).getText().equals(itemName));
    }

    /**
     * SK_20
     * Tamar
     * HTML refer to SK_1, SK_4, SK_15, SK_16
     */
    @Test
    public void SK_20_Tamar() throws InterruptedException {
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
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-cel-widget='search_result_2'] a.a-text-normal")));
        element.click();
        driver.findElement(By.id("add-to-cart-button")).click();
        driver.findElement(By.id("nav-cart")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".sc-product-link .a-truncate-cut")));
        String itemName = element.getText().replaceAll(" - ", "-");
        driver.findElement(By.cssSelector("[value='Compare with similar items']")).click();
        element = driver.findElement(By.cssSelector(".a-popover"));
        assertNotEquals("none", element.getCssValue("display"));
        Thread.sleep(1000);
        assertEquals("Compare with similar items", element.findElement(By.tagName("h1")).getText());
        String presentedName = element.findElement(By.cssSelector(".comparable_item_scroller0 .a-size-base.a-link-normal")).getText()
                .replace("...", "").replaceAll(" - ", "-");
        assertTrue(itemName.contains(presentedName) || presentedName.contains(itemName));
        assertEquals(driver.findElement(By.cssSelector(".sc-product-price")).getText(), element.findElement(By.cssSelector(".a-color-price")).getText());
        assertTrue(element.findElements(By.tagName("img")).size() > 1);
    }

    /**
     * SK_20
     * Tamar
     * HTML refer to SK_1, SK_4, SK_15, SK_16
     */
    @Test
    public void SK_21_Tamar() throws InterruptedException {
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
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-cel-widget='search_result_2'] a.a-text-normal")));
        element.click();
        driver.findElement(By.id("add-to-cart-button")).click();
        driver.findElement(By.id("nav-cart")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".sc-product-link .a-truncate-cut")));
        String itemName = element.getText();
        driver.findElement(By.cssSelector("[value='Save for later']")).click();
        Thread.sleep(1000);
        assertFalse(driver.findElements(By.cssSelector("#activeCartViewForm .a-truncate-cut")).size() > 0
                && driver.findElement(By.cssSelector("#activeCartViewForm .sc-product-link .a-truncate-cut")).getText().equals(itemName));
        assertEquals(itemName, driver.findElement(By.cssSelector("#sc-saved-cart-items .a-truncate-full")).getText());
    }

    /**
     * SK_22
     * Tamar
     * HTML refer to SK_1, SK_22
     */
    @Test
    public void SK_22_Tamar() {
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
        element = driver.findElement(By.xpath("//div[text()='Electronics']/.."));
        actions.moveToElement(element).click(element).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Accessories & Supplies']"))).click();
        assertTrue(driver.findElement(By.cssSelector("[data-component-type='s-result-info-bar']")).isDisplayed());
        assertEquals("Accessories & Supplies", driver.findElement(By.cssSelector("#searchDropdownBox [selected]")).getText());
    }

    /**
     * SK_23
     * Tamar
     * HTML refer to SK_1, SK_23
     */
    @Test
    public void SK_23_Tamar() {
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
        element = driver.findElement(By.xpath("//div[text()='Gift Cards']/.."));
        actions.moveToElement(element).click(element).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='All gift cards']"))).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        assertEquals("Gift Cards", element.getText());
        driver.findElement(By.xpath("//img[@alt='Birthday Gift Cards']/..")).click();
        assertEquals("Birthday Gift Cards Header",
                driver.findElement(By.cssSelector(".a-container img")).getAttribute("alt"));
    }

    /**
     * SK_24
     * Tamar
     * HTML refer to SK_1, SK_24
     */
    @Test
    public void SK_24_Tamar() {
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
        element = driver.findElement(By.xpath("//div[text()='Amazon Live']/.."));
        actions.moveToElement(element).click(element).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Live']"))).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-id='pageHeaderLogo'] > img")));
        assertEquals("Amazon Live logo", element.getAttribute("alt"));
        assertTrue(driver.findElement(By.cssSelector("[aria-label='Video Player']")).isDisplayed());
    }

    /**
     * SK_25
     * Tamar
     * HTML refer to SK_1, SK_25
     */
    @Test
    public void SK_25_Tamar() {
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
        element = driver.findElement(By.xpath("//li/a[text()='Customer Service']"));
        actions.moveToElement(element).click(element).perform();
        List<WebElement> cardElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".a-box-inner h3")));
        List<String> expectedTitles = new LinkedList<>(Arrays.asList("Your Orders", "Returns & Refunds",
                "Get Product Help", "Digital Services and Device Support", "Manage Prime", "Payments & Gift Cards",
                "Your Account", "Amazon and COVID-19", "Safe Online Shopping"));
        List<String> actualTitles = new LinkedList<>();
        for (WebElement element : cardElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        assertEquals("Search the help library", driver.findElement(By.cssSelector("#help-search-label .a-text-bold")).getText());
        assertTrue(driver.findElement(By.id("helpsearch")).isDisplayed());
        assertEquals("Browse Help Topics", driver.findElement(By.cssSelector(".help-content h1")).getText());
    }

    /**
     * SK_26
     * Tamar
     * HTML refer to SK_1, SK_25, SK_26
     */
    @Test
    public void SK_26_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();
        assertEquals("Cart", driver.findElement(By.id("nav-cart-text-container")).getText());

        driver.findElement(By.xpath("//a[(text()='Customer Service')]")).click();
        element = driver.findElement(By.id("helpsearch"));
        element.sendKeys("menu");
        List<WebElement> suggestions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#help_srch_sggst > *")));
        assertEquals("menu", suggestions.get(0).getText());
        assertEquals("menu button", suggestions.get(1).getText());
        element.submit();
        assertTrue(driver.findElement(By.cssSelector("#search-help p")).getText().contains("search results for menu"));
    }

    /**
     * SK_27
     * Tamar
     * HTML refer to SK_1, SK_4, SK_15
     */
    @Test
    public void SK_27_Tamar() {
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

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-cel-widget='search_result_3'] a.a-text-normal")));
        String itemName = element.getText();
        element.click();
        assertEquals(itemName, driver.findElement(By.id("productTitle")).getText());
        element = driver.findElement(By.id("acrCustomerReviewLink"));
        element.click();
        assertTrue((Boolean) ((JavascriptExecutor) driver).executeScript(
                "var elem = arguments[0],                 " +
                        "  box = elem.getBoundingClientRect(),    " +
                        "  cx = box.left + box.width / 2,         " +
                        "  cy = box.top + box.height / 2,         " +
                        "  e = document.elementFromPoint(cx, cy); " +
                        "for (; e; e = e.parentElement) {         " +
                        "  if (e === elem)                        " +
                        "    return true;                         " +
                        "}                                        " +
                        "return false;                            "
                , driver.findElement(By.cssSelector("#reviewsMedley h2"))));
        assertEquals(driver.findElement(By.id("acrPopover")).getAttribute("title").replace(" stars", ""), driver.findElement(By.xpath("//*[@data-hook='rating-out-of-text']")).getText());
        assertEquals(element.getText(), driver.findElement(By.xpath("//*[@data-hook='total-review-count']")).getText().replace("global ", ""));
        assertTrue(driver.findElement(By.cssSelector("[data-hook='review']")).isDisplayed());
    }

    /**
     * SK_28
     * Tamar
     * HTML refer to SK_1, SK_4, SK_15
     */
    @Test
    public void SK_28_Tamar() {
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

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-cel-widget='search_result_3'] a.a-text-normal")));
        String itemName = element.getText();
        element.click();
        assertEquals(itemName, driver.findElement(By.id("productTitle")).getText());
        element = driver.findElement(By.id("askATFLink"));
        element.click();
        assertTrue((Boolean) ((JavascriptExecutor) driver).executeScript(
                "var elem = arguments[0],                 " +
                        "  box = elem.getBoundingClientRect(),    " +
                        "  cx = box.left + box.width / 2,         " +
                        "  cy = box.top + box.height / 2,         " +
                        "  e = document.elementFromPoint(cx, cy); " +
                        "for (; e; e = e.parentElement) {         " +
                        "  if (e === elem)                        " +
                        "    return true;                         " +
                        "}                                        " +
                        "return false;                            "
                , wait.until(ExpectedConditions.presenceOfElementLocated(By.className("askInlineWidget")))));
        String moreAnswers = driver.findElement(By.cssSelector(".askTopQandALoadMoreQuestions")).getText();
        int numberOfMoreAnswers = Integer.parseInt(moreAnswers.substring(moreAnswers.indexOf("(") + 1, moreAnswers.lastIndexOf(")")));
        int numberOfPresentedAnswers = driver.findElements(By.cssSelector(".askTeaserQuestions > *")).size();
        assertEquals(Integer.parseInt(element.getText().split(" ")[0]), numberOfMoreAnswers + numberOfPresentedAnswers);
        assertTrue(numberOfMoreAnswers > 0);
    }

    /**
     * SK_29
     * Tamar
     * HTML refer to SK_1, SK_4, SK_15
     */
    @Test
    public void SK_29_Tamar() {
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

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-cel-widget='search_result_3'] a.a-text-normal")));
        String itemName = element.getText();
        element.click();
        assertEquals(itemName, driver.findElement(By.id("productTitle")).getText());
        element = driver.findElement(By.cssSelector(".twisterSwatchWrapper"));
        element.click();
        wait.until(ExpectedConditions.textToBe(By.cssSelector("#variation_color_name .selection"), element.findElement(By.tagName("img")).getAttribute("alt")));
    }

    /**
     * SK_30
     * Tamar
     * HTML refer to SK_1, SK_4, SK_15
     */
    @Test
    public void SK_30_Tamar() {
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

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-cel-widget='search_result_3'] a.a-text-normal")));
        String itemName = element.getText();
        element.click();
        assertEquals(itemName, driver.findElement(By.id("productTitle")).getText());
        element = driver.findElement(By.xpath("//h2[contains(text(), 'Have a question?')]"));
        actions.moveToElement(element).perform();
        element = driver.findElement(By.cssSelector("[aria-labelledby='askDPSearchPromptLabel']"));
        element.sendKeys("size");

        List<WebElement> answersElement = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector(".askSearchResultsActive  [data-ask-selector].a-section")));
        for (WebElement element : answersElement) {
            assertTrue(element.getText().contains("size"));
        }
    }

    /**
     * SK_31
     * Tamar
     * HTML refer to SK_1, SK_4, SK_15, SK_16
     */
    @Test
    public void SK_31_Tamar() {
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
        driver.findElement(By.className("a-dropdown-container")).click();
        assertEquals("true", driver.findElement(By.cssSelector(("[name='quantity']"))).getAttribute("aria-pressed"));
        List<WebElement> options = driver.findElements(By.cssSelector("[name='quantity'] option"));
        for (int i = 1; i <= options.size(); i++) {
            assertEquals(i, Integer.parseInt(options.get(i - 1).getText()));
        }
        driver.findElement(By.xpath("//option[@value='2']")).click();
        assertEquals("Qty:2", driver.findElement(By.cssSelector("[data-action='a-dropdown-button']")).getText());
        driver.findElement(By.id("add-to-cart-button")).click();
        String sideDataEncodedOffering = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-encoded-offering]"))).getAttribute("data-encoded-offering");
        driver.findElement(By.id("nav-cart")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".sc-product-link .a-truncate-cut")));
        assertEquals(sideDataEncodedOffering, driver.findElement(By.cssSelector("[data-encoded-offering]")).getAttribute("data-encoded-offering"));
        assertEquals("2", element.findElement(By.xpath(".//ancestor::ul[contains(@class, 'sc-info-block')]/following-sibling::*/descendant::*[@class='a-dropdown-prompt']")).getText());
    }

    /**
     * SK_32
     * Tamar
     * HTML refer to SK_1, SK_4, SK_15
     */
    @Test
    public void SK_32_Tamar() {
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

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-cel-widget='search_result_3'] a.a-text-normal")));
        String itemName = element.getText();
        element.click();
        assertEquals(itemName, driver.findElement(By.id("productTitle")).getText());

        driver.findElement(By.id("nav-global-location-slot")).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("a-popover-4")));
        assertNotEquals("none", element.getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='GLUXCountryList']/.."))).click();
        driver.findElement(By.xpath("//a[text()='United Kingdom']")).click();
        wait.until(ExpectedConditions.textToBe(By.id("GLUXCountryValue"), "United Kingdom"));
        driver.findElement(By.xpath("//button[@name='glowDoneButton']")).click();
        wait.until(ExpectedConditions.textToBe(By.id("nav-global-location-slot"), "Deliver to\nUnited Kingdom"));
        assertTrue(driver.findElement(By.id("ourprice_shippingmessage")).getText().contains("United Kingdom"));
    }

    /**
     * SK_33
     * Tamar
     * HTML refer to SK_1, SK_33
     */
    @Test
    public void SK_33_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        element = driver.findElement(By.id("navFooter"));
        actions.moveToElement(element).perform();
        driver.findElement(By.xpath("//a[text()='Careers']")).click();
        assertEquals("Find jobs", driver.findElement(By.tagName("h1")).getText());
        assertTrue(driver.findElement(By.cssSelector("[data-react-class='SearchInput']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[aria-label='Apply now']")).isEnabled());

        assertTrue(driver.findElement(By.className("selected-employee-profile")).isDisplayed());
        assertTrue(driver.findElement(By.className("name")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector(".selected-employee-profile img")).isDisplayed());
    }

    /**
     * SK_34
     * Tamar
     * HTML refer to SK_1, SK_34
     */
    @Test
    public void SK_34_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        driver.findElement(By.xpath("//a[(text()='Sell')]")).click();

        assertEquals("Sell on Amazon", driver.findElement(By.cssSelector(".sticks-top h2")).getText());
        assertEquals("Introduction to ecommerce selling", driver.findElement(By.xpath("(//h2)[2]")).getText());
        element = driver.findElement(By.xpath("(//h2)[2]/../../descendant::h3"));
        actions.moveToElement(element).perform();
        element = driver.findElement(By.xpath("((//h2)[2]/../../descendant::h3)[8]"));
        actions.moveToElement(element).perform();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("((//h2)[2]/../../descendant::h3)[8]"), "")));
        List<WebElement> titlesElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("(//h2)[2]/../../descendant::h3")));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("What is ecommerce?", "Build a business",
                "Ecommerce fulfillment", "Inventory management", "What is dropshipping?", "Create an Amazon storefront",
                "Sell books on Amazon", "Seller University"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titlesElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        assertTrue(driver.findElement(By.xpath("//h3[text()='Start selling today']")).isDisplayed());
    }

    /**
     * SK_35
     * Tamar
     * HTML refer to SK_1, SK_35
     */
    @Test
    public void SK_35_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[(text()='Registry')]"))).click();

        assertEquals("Registry & Gifting", driver.findElement(By.cssSelector(".gr-header")).getText());
        assertEquals("Find a registry or gift list", driver.findElement(By.cssSelector(".gr-find-stripe__header")).getText());
        assertTrue(driver.findElement(By.cssSelector("[placeholder='Search by Registrant name']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[data-action='a-dropdown-select']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//button[@aria-label='Search']")).isDisplayed());

        List<WebElement> cardsElements = driver.findElements(By.className("gr-registry-types__card"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList(
                "Wedding Registry", "Baby Registry", "Birthday Gift List", "Custom Gift List"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : cardsElements) {
            assertTrue(element.findElement(By.xpath(".//img")).isDisplayed());
            actualTitles.add(element.findElement(By.className("gr-registry-types__header")).getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * SK_36
     * Tamar
     * HTML refer to SK_1, SK_25, SK_36
     */
    @Test
    public void SK_36_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[(text()='Customer Service')]"))).click();
        element = driver.findElement(By.id("csg-support-topics"));
        actions.moveToElement(element).perform();
        element = driver.findElement(By.xpath("//*[contains(text(), 'My Stuff')]"));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        element = driver.findElement(By.id("help-gateway-category-1"));
        assertNotEquals("none", element.getCssValue("display"));
        assertEquals("Where's My Stuff?", element.findElement(By.tagName("h3")).getText());
        List<WebElement> linkElements = driver.findElements(By.cssSelector("#help-gateway-category-1 a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Find a Missing Package that Shows as Delivered",
                "Contact Shipping Carrier", "Track Your Package", "More in Where's My Stuff"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : linkElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        linkElements.get(3).click();
        assertEquals("Where's My Stuff?", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_37
     * Tamar
     * HTML refer to SK_1, SK_4
     */
    @Test
    public void SK_37_Tamar() throws InterruptedException {
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

        driver.findElement(By.className("a-dropdown-container")).click();
        driver.findElement(By.xpath("//a[text()='Price: Low to High']")).click();
        Thread.sleep(2000);
        List<WebElement> priceElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("a-price-whole")));
        for (int i = 1; i < priceElements.size() - 4; i++) { // last 4 ones are sponsored and aren't sorted
            String previousPrice = priceElements.get(i - 1).getText() + "." +
                    priceElements.get(i - 1).findElement(By.xpath(".//following-sibling::*[@class='a-price-fraction']")).getText();
            String currentPrice = priceElements.get(i).getText() + "." +
                    priceElements.get(i).findElement(By.xpath(".//following-sibling::*[@class='a-price-fraction']")).getText();
            assertTrue("last: " + previousPrice + " current: " + currentPrice,
                    Double.parseDouble(currentPrice) >= Double.parseDouble(previousPrice));
        }
    }

    /**
     * SK_38
     * Tamar
     * HTML refer to SK_1, SK_4
     */
    @Test
    public void SK_38_Tamar() throws InterruptedException {
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

        driver.findElement(By.className("a-dropdown-container")).click();
        driver.findElement(By.xpath("//a[text()='Avg. Customer Review']")).click();
        Thread.sleep(2000);
        List<WebElement> reviewsElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label$='out of 5 stars']")));
        for (int i = 1; i < 20; i++) { // checking the first ones only
            String previousReview = reviewsElements.get(i - 1).getAttribute("aria-label");
            String currentReview = reviewsElements.get(i).getAttribute("aria-label");
            assertTrue("last: " + previousReview + " current: " + currentReview,
                    Double.parseDouble(previousReview.substring(0, previousReview.indexOf(" "))) >=
                            Double.parseDouble(currentReview.substring(0, currentReview.indexOf(" "))));
        }
    }

    /**
     * SK_39
     * Tamar
     * HTML refer to SK_1, SK_4
     */
    @Test
    public void SK_39_Tamar() throws InterruptedException {
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

        driver.findElement(By.cssSelector("[aria-label='Climate Pledge Friendly'] a")).click();
        Thread.sleep(2000);
        List<WebElement> searchResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//*[contains(@data-cel-widget, 'MAIN-SEARCH_RESULTS')]")));
        for (int i = 0; i < 20; i++) { // checking the first ones only
            assertEquals("Climate Pledge Friendly", searchResults.get(i).findElement(By.cssSelector(".s-cpf-badge")).getText());
        }
        element = driver.findElement(By.cssSelector("[data-action='s-cpf-popover']"));
        actions.moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("a-popover-4")).getCssValue("display"));
        assertNotEquals("", driver.findElement(By.cssSelector("#a-popover-4 .a-color-base")).getText());
        assertTrue(driver.findElement(By.cssSelector("#a-popover-4 .a-text-bold")).getText().contains("PRODUCT CERTIFICATION"));
    }

    /**
     * SK_40
     * Tamar
     * HTML refer to SK_1, SK_4
     */
    @Test
    public void SK_40_Tamar() {
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

        By locator = By.xpath("//*[text()='Smartwatches']/..");
        driver.findElement(locator).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class, 'a-text-bold') and text()='Smartwatches']")));

        List<WebElement> searchResults = driver.findElements(By.xpath
                ("//*[contains(@data-cel-widget, 'MAIN-SEARCH_RESULTS')]/descendant::a/span[contains(@class, 'a-text-normal')]"));
        for (WebElement result : searchResults) {
            String itemName = result.getText().toLowerCase(Locale.ROOT).replaceAll(" ", "");
            assertTrue(itemName.contains("smartwatch") || itemName.contains("applewatch"));
        }
    }

    /**
     * SK_41
     * Tamar
     * HTML refer to SK_1, SK_4
     */
    @Test
    public void SK_41_Tamar() {
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

        By locator = By.xpath("//*[@aria-label='4 Stars & Up']/..");
        driver.findElement(locator).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@aria-label='4 Stars & Up']//span[contains(@class, 'a-text-bold') and text()='& Up']")));

        List<WebElement> reviewsElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label$='out of 5 stars']")));
        for (WebElement review : reviewsElements) {
            String currentReview = review.getAttribute("aria-label");
            assertTrue(Double.parseDouble(currentReview.substring(0, currentReview.indexOf(" "))) >= 4.0);
        }
    }

    /**
     * SK_42
     * Tamar
     * HTML refer to SK_1, SK_4
     */
    @Test
    public void SK_42_Tamar() {
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

        By locator = By.xpath("//*[text()='Apple']/..");
        driver.findElement(locator).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class, 'a-text-bold') and text()='Apple']")));

        List<WebElement> searchResults = driver.findElements(By.xpath
                ("//*[contains(@data-cel-widget, 'MAIN-SEARCH_RESULTS')]/descendant::a/span[contains(@class, 'a-text-normal')]"));
        for (WebElement result : searchResults) {
            assertTrue(result.getText().contains("Apple"));
        }
    }

    /**
     * SK_43
     * Tamar
     * HTML refer to SK_1, SK_4
     */
    @Test
    public void SK_43_Tamar() {
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

        driver.findElement(By.id("low-price")).sendKeys("50");
        driver.findElement(By.id("high-price")).sendKeys("100");
        driver.findElement(By.xpath("//*[contains(text(), 'Go')]/preceding-sibling::input[@type='submit']")).click();

        List<WebElement> priceElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("a-price-whole")));
        for (int i = 1; i < 10; i++) { // checking the first ones only.
            double dOGPrice = 0;
            if (priceElements.get(i).findElements(By.xpath(".//ancestor::*[@class='a-price']/following-sibling::*")).size() > 0) {
                String originalPrice = priceElements.get(i).findElement(By.xpath(".//ancestor::*[@class='a-price']/following-sibling::*/*[@aria-hidden='true']"))
                        .getText();
                if (!originalPrice.isEmpty()) {
                    originalPrice = originalPrice.substring(1);
                    dOGPrice = Double.parseDouble(originalPrice);
                }
            }
            String currentPrice = priceElements.get(i).getText() + "." +
                    priceElements.get(i).findElement(By.xpath(".//following-sibling::*[@class='a-price-fraction']")).getText();
            double price = Double.parseDouble(currentPrice);
            assertTrue(i + " price: " + currentPrice + ", og price: " + dOGPrice,
                    price >= 50.0 && price <= 100.0 || dOGPrice >= 50.0 && dOGPrice <= 100.0);
        }
    }

    /**
     * SK_44
     * Tamar
     * HTML refer to SK_1, SK_4
     */
    @Test
    public void SK_44_Tamar() {
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

        driver.findElement(By.xpath("//*[@aria-label='4 Stars & Up']/..")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@aria-label='4 Stars & Up']//span[contains(@class, 'a-text-bold') and text()='& Up']")));
        driver.findElement(By.xpath("//*[text()='Apple']/..")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class, 'a-text-bold') and text()='Apple']")));

        List<WebElement> reviewsElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label$='out of 5 stars']")));
        for (WebElement review : reviewsElements) {
            String currentReview = review.getAttribute("aria-label");
            assertTrue(Double.parseDouble(currentReview.substring(0, currentReview.indexOf(" "))) >= 4.0);
        }
        List<WebElement> searchResults = driver.findElements(By.xpath
                ("//*[contains(@data-cel-widget, 'MAIN-SEARCH_RESULTS')]/descendant::a/span[contains(@class, 'a-text-normal')]"));
        for (WebElement result : searchResults) {
            assertTrue(result.getText().contains("Apple"));
        }
    }

    /**
     * SK_45
     * Tamar
     * HTML refer to SK_4, SK_45
     */
    @Test
    public void SK_45_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        element = driver.findElement(By.id("twotabsearchtextbox"));
        element.sendKeys("watch");
        wait.until(ExpectedConditions.attributeContains(By.id("nav-flyout-searchAjax"), "style", "display: block"));
        List<WebElement> suggestionsElements = driver.findElements(By.cssSelector("#suggestions > .s-suggestion"));
        for (WebElement result : suggestionsElements) {
            assertTrue(result.getText().contains("watch"));
        }
        String selectedSuggestion = suggestionsElements.get(3).getText();
        suggestionsElements.get(3).click();
        String searchWord = driver.findElement(By.cssSelector(".a-color-state.a-text-bold")).getText();
        assertEquals(selectedSuggestion, searchWord.replaceAll("\"", ""));
    }

    /**
     * SK_46
     * Tamar
     * HTML refer to SK_4, SK_45
     */
    @Test
    public void SK_46_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        element = driver.findElement(By.id("twotabsearchtextbox"));
        element.sendKeys("watch");
        wait.until(ExpectedConditions.attributeContains(By.id("nav-flyout-searchAjax"), "style", "display: block"));
        List<WebElement> suggestionsElements = driver.findElements(By.cssSelector("#suggestions > .s-suggestion"));
        for (WebElement result : suggestionsElements) {
            assertTrue(result.getText().contains("watch"));
        }
        assertEquals("by price", driver.findElement(By.cssSelector(".discover-tr span")).getText());
        List<WebElement> priceSuggestionsElements = driver.findElements(By.cssSelector(".discover-tr-carousel-container  a"));
        Set<WebElement> set = new HashSet<>(priceSuggestionsElements);
        assertEquals(set.size(), priceSuggestionsElements.size());

        String priceSelectedSuggestion = priceSuggestionsElements.get(2).getText();
        double minLimit = Double.parseDouble(priceSelectedSuggestion.substring(1, priceSelectedSuggestion.indexOf(' ')));
        double maxLimit = Double.parseDouble(priceSelectedSuggestion.substring(priceSelectedSuggestion.lastIndexOf('$') + 1));
        priceSuggestionsElements.get(2).click();
        assertEquals(priceSelectedSuggestion, driver.findElement(By.cssSelector("#priceRefinements a > .a-text-bold")).getText());

        List<WebElement> priceElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("a-price-whole")));
        for (int i = 1; i < 10; i++) { // checking the first ones only.
            double dOGPrice = 0;
            if (priceElements.get(i).findElements(By.xpath(".//ancestor::*[@class='a-price']/following-sibling::*")).size() > 0) {
                String originalPrice = priceElements.get(i).findElement(By.xpath(".//ancestor::*[@class='a-price']/following-sibling::*/*[@aria-hidden='true']"))
                        .getText();
                if (!originalPrice.isEmpty()) {
                    originalPrice = originalPrice.substring(1);
                    dOGPrice = Double.parseDouble(originalPrice);
                }
            }
            String currentPrice = priceElements.get(i).getText() + "." +
                    priceElements.get(i).findElement(By.xpath(".//following-sibling::*[@class='a-price-fraction']")).getText();
            double price = Double.parseDouble(currentPrice);
            assertTrue(i + " price: " + currentPrice + ", og price: " + dOGPrice,
                    price >= minLimit && price <= maxLimit || dOGPrice >= minLimit && dOGPrice <= maxLimit);
        }
    }

    /**
     * SK_47
     * Tamar
     * HTML refer to SK_1, SK_4, SK_15, SK_16
     */
    @Test
    public void SK_47_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        String[] itemsNames = new String[2];
        for (int i = 0; i < 2; i++) {
            element = driver.findElement(By.id("twotabsearchtextbox"));
            element.sendKeys("watch");
            element.submit();
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-cel-widget='search_result_" + (i + 2) + "'] a.a-text-normal")));
            itemsNames[i] = element.getText();
            element.click();
            assertEquals(itemsNames[i], driver.findElement(By.id("productTitle")).getText());
            driver.findElement(By.id("add-to-cart-button")).click();
        }
        driver.findElement(By.id("nav-cart")).click();

        for (int i = 0; i < 2; i++) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-item-count='" + (i + 1) + "'] .sc-product-link .a-truncate-cut")));
            assertTrue(itemsNames[1 - i].contains(element.getText()) || element.getText().contains(itemsNames[1 - i]));
        }
    }

    /**
     * SK_48
     * Tamar
     * HTML refer to SK_1, SK_48
     */
    @Test
    public void SK_48_Tamar() {
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

        driver.findElement(By.xpath("//*[text()='AmazonSmile Charity Lists']/..")).click();
        assertTrue(driver.findElement(By.cssSelector("[alt='AmazonSmile logo']")).isDisplayed());
        assertEquals("Charity Lists", driver.findElement(By.tagName("h1")).getText());
        assertTrue(driver.findElement(By.className("donate-amount")).isDisplayed());
        List<WebElement> buttons = driver.findElements(By.xpath("//a/*[contains(text(),'Get started')]"));
        assertEquals(2, buttons.size());
        buttons.get(0).click();
        assertEquals("You shop. Amazon gives.", driver.findElement(By.tagName("h1")).getText());
        assertEquals("Email or mobile phone number", driver.findElement(By.className("a-form-label")).getText());
    }

    /**
     * SK_49
     * Tamar
     * HTML refer to SK_1, SK_49
     */
    @Test
    public void SK_49_Tamar() {
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

        driver.findElement(By.xpath("//*[text()='Music Library']/..")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[alt='Amazon Music']")));
        assertTrue(driver.findElement(By.cssSelector("[title='Sign in button']")).isDisplayed());
        assertTrue(driver.findElement(By.id("navbarSearchInput")).isDisplayed());
        assertTrue(driver.findElements(By.tagName("music-vertical-item")).size() > 3);
        driver.findElement(By.cssSelector("[icon-name='play']")).click();
        assertTrue(driver.findElement(By.id("dialog")).isDisplayed());
        assertEquals("Sign In for Free Streaming Music", driver.findElement(By.tagName("h1")).getText());
        driver.findElement(By.cssSelector("[icon-name='cancel']")).click();
        assertEquals(0, driver.findElements(By.id("dialog")).size());
    }

    /**
     * SK_50
     * Tamar
     * HTML refer to SK_1, SK_4, SK_15, SK_50
     */
    @Test
    public void SK_50_Tamar() {
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

        element = driver.findElement(By.id("nav-link-accountList"));
        actions.moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-accountList")).getCssValue("display"));
        driver.findElement(By.xpath("//*[text()='Browsing History']/..")).click();
        assertEquals("Browsing history", driver.findElement(By.tagName("h1")).getText());
        assertTrue(driver.findElement(By.cssSelector("[title='" + itemName + "']")).isDisplayed());
    }

    /**
     * SK_51
     * Tamar
     * HTML refer to SK_1, SK_4, SK_15, SK_51
     */
    @Test
    public void SK_51_Tamar() {
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

        element = driver.findElement(By.id("nav-link-accountList"));
        actions.moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-accountList")).getCssValue("display"));
        driver.findElement(By.xpath("//*[text()='Browsing History']/..")).click();
        assertEquals("Browsing history", driver.findElement(By.tagName("h1")).getText());
        element = driver.findElement(By.cssSelector("[title='" + itemName + "']"));
        element.findElement(By.xpath(".//ancestor::*[@data-type='ViewedItems']/descendant::*[contains(@class, 'ybh-remove')]//input")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(
                element.findElement(By.xpath(".//ancestor::*[@data-type='ViewedItems']/*[contains(@class, 'removed')]")),
                "class", "hidden")));
    }

    /**
     * SK_52
     * Tamar
     * HTML refer to SK_1, SK_4, SK_15, SK_50
     */
    @Test
    public void SK_52_Tamar() {
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

        element = driver.findElement(By.id("nav-link-accountList"));
        actions.moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-accountList")).getCssValue("display"));
        driver.findElement(By.xpath("//*[text()='Browsing History']/..")).click();
        assertEquals("Browsing history", driver.findElement(By.tagName("h1")).getText());
        driver.findElement(By.xpath("//*[contains(text(), 'Manage history')]")).click();
        assertNotEquals("none", driver.findElement(By.cssSelector("[aria-expanded]")).getCssValue("display"));
        driver.findElement(By.cssSelector("[aria-labelledby='btn_clear-announce']")).click();
        assertNotEquals("none", driver.findElement(By.id("a-popover-3")).getCssValue("display"));
        element = driver.findElement(By.cssSelector("[data-action='delete-all'] > *"));
        actions.moveByOffset(0, 0).moveToElement(element).click(element).perform();
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(By.id("a-popover-3"), "style", "visibility: visible")));
        assertNotEquals("none", driver.findElement(By.id("no_items_message")).getCssValue("display"));
    }

    /**
     * SK_53
     * Tamar
     * HTML refer to SK_1, SK_4, SK_15, SK_16
     */
    @Test
    public void SK_53_Tamar() {
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

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-cel-widget='search_result_2'] a.a-text-normal")));
        String itemName = element.getText();
        element.click();
        assertEquals(itemName, driver.findElement(By.id("productTitle")).getText());
        driver.findElement(By.id("add-to-cart-button")).click();
        driver.findElement(By.id("nav-cart")).click();

        assertTrue(driver.findElement(By.id("cart-recs-carousel")).isDisplayed());
        String title = driver.findElement(By.tagName("h2")).getText();
        assertTrue("Customers who bought items in your cart also bought".equals(title) ||
                (title.contains("Customers who shopped for") && title.contains("also shopped for:")));
        assertTrue(driver.findElements(By.cssSelector("#cart-recs-carousel .a-carousel-card")).size() > 1);
    }

    /**
     * SK_54
     * Tamar
     * HTML refer to SK_1, SK_12
     */
    @Test
    public void SK_54_Tamar() {
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

        driver.findElement(By.id("nav-flyout-ya-signin")).click();
        assertEquals("Sign-In", driver.findElement(By.tagName("h1")).getText());
        driver.findElement(By.className("a-link-nav-icon")).click();
        assertTrue(driver.findElement(By.id("gw-desktop-herotator")).isDisplayed());
    }

    /**
     * SK_55
     * Tamar
     * HTML refer to SK_1, SK_2
     */
    @Test
    public void SK_55_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        driver.findElement(By.xpath("//a[contains(text(),'Deals')]")).click();
        assertEquals("Deals and Promotions", driver.findElement(By.tagName("h1")).getText());

        driver.findElement(By.id("nav-logo-sprites")).click();
        assertTrue(driver.findElement(By.id("gw-desktop-herotator")).isDisplayed());
    }

    /**
     * SK_56
     * Tamar
     * HTML refer to SK_1, SK_35, SK_56
     */
    @Test
    public void SK_56_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[(text()='Registry')]"))).click();

        assertEquals("Find a registry or gift list", driver.findElement(By.cssSelector(".gr-find-stripe__header")).getText());
        assertTrue(driver.findElement(By.cssSelector("[placeholder='Search by Registrant name']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[data-action='a-dropdown-select']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//button[@aria-label='Search']")).isDisplayed());

        driver.findElement(By.cssSelector("[placeholder='Search by Registrant name']")).sendKeys("Ali");
        driver.findElement(By.cssSelector("[data-a-class='gr-find-stripe__type']")).click();
        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("[data-action='a-dropdown-select']"), "aria-pressed", "true"));
        driver.findElement(By.xpath("//option[contains(text(), 'Birthday Gift List')]")).click();
        driver.findElement(By.xpath("//button[@aria-label='Search']")).click();

        wait.until(ExpectedConditions.textToBe(By.id("gr-search-result-title-id"), "Search results for \"Ali\""));
        List<WebElement> nameElements = driver.findElements(By.cssSelector("#search-result-container .gr-search-registry-name"));
        for (WebElement name : nameElements) {
            assertTrue(name.getText().toLowerCase(Locale.ROOT).contains("ali"));
        }
    }

    /**
     * SK_57
     * Tamar
     * HTML refer to SK_1, SK_35, SK_56
     */
    @Test
    public void SK_57_Tamar() throws InterruptedException {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[(text()='Registry')]"))).click();

        assertEquals("Find a registry or gift list", driver.findElement(By.cssSelector(".gr-find-stripe__header")).getText());
        assertTrue(driver.findElement(By.cssSelector("[placeholder='Search by Registrant name']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[data-action='a-dropdown-select']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//button[@aria-label='Search']")).isDisplayed());

        driver.findElement(By.cssSelector("[placeholder='Search by Registrant name']")).sendKeys("Ali");
        driver.findElement(By.cssSelector("[data-a-class='gr-find-stripe__type']")).click();
        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("[data-action='a-dropdown-select']"), "aria-pressed", "true"));
        driver.findElement(By.xpath("//option[contains(text(), 'Birthday Gift List')]")).click();
        driver.findElement(By.xpath("//button[@aria-label='Search']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".gr-search-criteria-location-drop-down > *"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='California']"))).click();
        driver.findElement(By.cssSelector("[data-action='grSearchButton'] [type='submit']")).click();

        Thread.sleep(1000);
        List<WebElement> locationElements = driver.findElements(By.cssSelector("#search-result-container .gr-search-registry-event-location"));
        for (WebElement location : locationElements) {
            assertEquals("CA", location.getText());
        }
    }

    /**
     * SK_58
     * Tamar
     * HTML refer to SK_1, SK_35, SK_56
     */
    @Test
    public void SK_58_Tamar() throws InterruptedException {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[(text()='Registry')]"))).click();

        assertEquals("Find a registry or gift list", driver.findElement(By.cssSelector(".gr-find-stripe__header")).getText());
        assertTrue(driver.findElement(By.cssSelector("[placeholder='Search by Registrant name']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[data-action='a-dropdown-select']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//button[@aria-label='Search']")).isDisplayed());

        driver.findElement(By.cssSelector("[placeholder='Search by Registrant name']")).sendKeys("Ali");
        driver.findElement(By.cssSelector("[data-a-class='gr-find-stripe__type']")).click();
        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("[data-action='a-dropdown-select']"), "aria-pressed", "true"));
        driver.findElement(By.xpath("//option[contains(text(), 'Birthday Gift List')]")).click();
        driver.findElement(By.xpath("//button[@aria-label='Search']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".gr-search-month-drop-down-wrapper > *"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='January']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".gr-search-year-drop-down-wrapper > *"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='2020']"))).click();
        driver.findElement(By.cssSelector("[data-action='grSearchButton'] [type='submit']")).click();

        Thread.sleep(1000);
        List<WebElement> nameElements = driver.findElements(By.cssSelector("#search-result-container .gr-search-registry-name"));
        for (WebElement name : nameElements) {
            assertTrue(name.getText().toLowerCase(Locale.ROOT).contains("ali"));
        }

        List<WebElement> dateElements = driver.findElements(By.cssSelector("#search-result-container .gr-search-registry-date"));
        for (WebElement date : dateElements) {
            String dateText = date.getText();
            String year = dateText.substring(dateText.indexOf(",") + 1);
            assertTrue(Integer.parseInt(year) >= 2020);
        }
    }

    /**
     * SK_59
     * Tamar
     * HTML refer to SK_1, SK_35, SK_56. SK_59
     */
    @Test
    public void SK_59_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[(text()='Registry')]"))).click();

        assertEquals("Find a registry or gift list", driver.findElement(By.cssSelector(".gr-find-stripe__header")).getText());
        assertTrue(driver.findElement(By.cssSelector("[placeholder='Search by Registrant name']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[data-action='a-dropdown-select']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//button[@aria-label='Search']")).isDisplayed());

        driver.findElement(By.cssSelector("[placeholder='Search by Registrant name']")).sendKeys("Ali");
        driver.findElement(By.cssSelector("[data-a-class='gr-find-stripe__type']")).click();
        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("[data-action='a-dropdown-select']"), "aria-pressed", "true"));
        driver.findElement(By.xpath("//option[contains(text(), 'Birthday Gift List')]")).click();
        driver.findElement(By.xpath("//button[@aria-label='Search']")).click();

        wait.until(ExpectedConditions.textToBe(By.id("gr-search-result-title-id"), "Search results for \"Ali\""));
        List<WebElement> nameElements = driver.findElements(By.cssSelector("#search-result-container .gr-search-registry-name"));
        for (WebElement name : nameElements) {
            assertTrue(name.getText().toLowerCase(Locale.ROOT).contains("ali"));
        }

        element = driver.findElements(By.cssSelector("#search-result-container .gr-search-registry-title > a")).get(2);
        String listName = element.getText();
        element.click();
        assertEquals(listName, driver.findElement(By.className("gr-guest-summary-registry-name")).getText());
        assertTrue(driver.findElements(By.className("gr-product-tile")).size() > 0);
    }

    /**
     * SK_60
     * Tamar
     * HTML refer to SK_1, SK_12, SK_35, SK_60
     */
    @Test
    public void SK_60_Tamar() {
        driver.get("https://www.amazon.com/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("icp-nav-flyout")));
        actions.moveByOffset(0, 0).moveToElement(element).perform();
        assertNotEquals("none", driver.findElement(By.id("nav-flyout-icp")).getCssValue("display"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English - EN']/.."))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[(text()='Registry')]"))).click();
        driver.findElement(By.className("gr-registry-types__card")).click();
        assertEquals("Amazon Wedding", driver.findElement(By.xpath("//img/parent::a")).getAttribute("aria-label"));
        driver.findElement(By.xpath("//a[contains(text(), 'Create your registry')]")).click();
        assertEquals("Sign-In", driver.findElement(By.tagName("h1")).getText());
    }
}
