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

import java.util.List;
import java.util.Locale;

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
        actions.moveToElement(element).perform();
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
        actions.moveToElement(element).perform();
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
        actions.moveToElement(element).perform();
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
        actions.moveToElement(element).perform();
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
        actions.moveToElement(element).perform();
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
        actions.moveToElement(element).perform();
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
        actions.moveToElement(element).perform();
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
}
