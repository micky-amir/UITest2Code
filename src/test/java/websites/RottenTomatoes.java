package websites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

public class RottenTomatoes {

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
        driver.quit();
    }

    /**
     * RT_1
     * Tamar
     */
    @Test
    public void RT_1_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = driver.findElement(By.className("trending-bar__link"));
        String title = element.getText();
        element.click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), title.toUpperCase(Locale.ROOT)));
        assertTrue(driver.findElement(By.cssSelector(".trendingBar")).isDisplayed());
        List<WebElement> links = driver.findElements(By.cssSelector(".trendingEl .trendingLink"));
        assertEquals(4, links.size());

        element = links.get(1);
        title = element.getText();
        element.click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), title.toUpperCase(Locale.ROOT)));
    }

    /**
     * RT_2
     * Tamar
     * HTML refers to RT_1, RT_2
     */
    @Test
    public void RT_2_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        assertTrue(driver.findElement(By.xpath("//h2[text()='New & Upcoming Movies']")).isDisplayed());
        element = driver.findElement(By.cssSelector("#dynamic-poster-list [data-qa='tile']"));
        assertTrue(element.isDisplayed());
        String title = element.findElement(By.className("p--small")).getText();
        String score = element.findElement(By.tagName("score-pairs")).getAttribute("criticsscore");
        element.findElement(By.cssSelector("[slot='caption']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), title.toUpperCase(Locale.ROOT)));
        assertEquals(score, driver.findElement(By.tagName("score-board")).getAttribute("tomatometerscore"));
    }

    /**
     * RT_3
     * Tamar
     * HTML refers to RT_2, RT_3
     */
    @Test
    public void RT_3_Tamar() throws InterruptedException {
        driver.get("https://www.rottentomatoes.com/");
        assertTrue(driver.findElement(By.xpath("//h2[text()='New & Upcoming Movies']")).isDisplayed());
        element = driver.findElement(By.cssSelector("#dynamic-poster-list [data-qa='tile']"));
        assertTrue(element.isDisplayed());
        element = element.findElement(By.cssSelector("[slot='imageAction']"));
        String title = element.getAttribute("data-title");
        element.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".player")));
        driver.switchTo().frame(element);

        Thread.sleep(2000);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("tpBottomFloatRegion")));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        By locator = By.cssSelector(".tpTimeInfo span");
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, "--:--")));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, "0:00")));

        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//a[contains(text(), 'See movie details')]")).click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), title.toUpperCase(Locale.ROOT)));
    }

    /**
     * RT_4
     * Tamar
     * HTML refers to RT_1, RT_4
     */
    @Test
    public void RT_4_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.className("search-text")).sendKeys("Encanto");
        driver.findElement(By.className("search-submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Search Results for : \"Encanto\""));
    }

    /**
     * RT_5
     * Tamar
     * HTML refers to RT_2, RT_5
     */
    @Test
    public void RT_5_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.className("search-text")).sendKeys("Encanto");
        element = driver.findElement(By.cssSelector("[data-qa='search-results-overlay']"));
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.attributeToBe(element, "searchterm", "Encanto"));
        assertNotEquals("none", element.getCssValue("display"));
        element = driver.findElement(By.cssSelector("[data-qa='search-results-link']"));
        assertTrue(element.getAttribute("href").contains("encanto_2021"));
        element.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-qa='movie-main-column']")));
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "ENCANTO"));
    }

    /**
     * RT_6
     * Tamar
     * HTML refers to RT_1
     */
    @Test
    public void RT_6_Tamar() throws InterruptedException {
        driver.get("https://www.rottentomatoes.com/");
        By activeTitleLocator = By.cssSelector("#headliners .headlineItem.active h2");
        element = driver.findElement(By.cssSelector("button[data-slide='next']"));

        String text1 = driver.findElement(activeTitleLocator).getText();
        Thread.sleep(1000);
        element.click();
        Thread.sleep(1000);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.cssSelector("#headliners .headlineItem.active h2"), text1)));
        String text2 = driver.findElement(activeTitleLocator).getText();

        element.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(activeTitleLocator, text2)));
        String text3 = driver.findElement(activeTitleLocator).getText();
        assertNotEquals(text1, text3);

        driver.findElement(By.cssSelector("button[data-slide='prev']"));
        wait.until(ExpectedConditions.textToBe(activeTitleLocator, text3));
    }

    /**
     * RT_7
     * Tamar
     * HTML refers to RT_1
     */
    @Test
    public void RT_7_Tamar() throws InterruptedException {
        driver.get("https://www.rottentomatoes.com/");
        By activeTitleLocator = By.cssSelector("#headliners .headlineItem.active h2");

        String text1 = driver.findElement(activeTitleLocator).getText();

        Thread.sleep(6000);
        String text2 = driver.findElement(activeTitleLocator).getText();
        assertNotEquals(text1, text2);

        Thread.sleep(6000);
        String text3 = driver.findElement(activeTitleLocator).getText();
        assertNotEquals(text2, text3);
        assertNotEquals(text1, text3);
    }

    /**
     * RT_8
     * Tamar
     * HTML refers to RT_1
     */
    @Test
    public void RT_8_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#headliners .headlineItem.active h2")));
        String url = element.findElement(By.xpath(".//ancestor::a")).getAttribute("href");
        element.click();

        wait.until(ExpectedConditions.urlToBe(url));
    }

    /**
     * RT_9
     * Tamar
     * HTML refers to RT_1
     */
    @Test
    public void RT_9_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        List<WebElement> spotlights = driver.findElements(By.cssSelector(".homepage-spotlight"));
        assertEquals(2, spotlights.size());

        element = spotlights.get(1);
        String title = element.findElement(By.tagName("h2")).getText();
        element.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), title));
        assertTrue(driver.findElement(By.className("articleContentBody")).isDisplayed());
    }

    /**
     * RT_10
     * Tamar
     * HTML refers to RT_1, RT_10
     */
    @Test
    public void RT_10_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//h2[text()='New & Upcoming Movies']/following-sibling::a"));
        assertEquals("VIEW ALL", element.getText());
        element.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "COMING SOON"));
        String stringExpectedNumber = driver.findElement(By.id("count-link")).getText().split(" ")[1];
        int expectedNumber = Integer.parseInt(stringExpectedNumber);
        assertEquals(expectedNumber, driver.findElements(By.className("poster_container")).size());
        List<WebElement> dates = driver.findElements(By.className("release-date"));
        assertEquals(expectedNumber, dates.size());
        for (WebElement date : dates) {
            assertTrue(date.getText().startsWith("Available"));
        }
    }
}
