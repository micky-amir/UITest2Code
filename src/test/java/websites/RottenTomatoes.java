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

import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * RT_11
     * Tamar
     * HTML refers to RT_1, RT_11
     */
    @Test
    public void RT_11_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//h2[text()='New & Upcoming Movies']/following-sibling::a"));
        assertEquals("VIEW ALL", element.getText());
        element.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "COMING SOON"));
        By locator = By.cssSelector("btn.view-icon:nth-of-type(2)");
        driver.findElement(locator).click();
        wait.until(ExpectedConditions.attributeContains(locator, "class", "active"));
        assertTrue(driver.findElement(By.cssSelector(".mb-movies")).getAttribute("class").contains("list-view"));
    }

    /**
     * RT_12
     * Tamar
     * HTML refers to RT_1, RT_10
     */
    @Test
    public void RT_12_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//h2[text()='New & Upcoming Movies']/following-sibling::a"));
        assertEquals("VIEW ALL", element.getText());
        element.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "COMING SOON"));

        Actions actions = new Actions(driver);
        element = driver.findElement(By.id("tomatometerFilter"));
        actions.moveToElement(element).perform();
        assertNotEquals("none", element.findElement(By.className("dropdown-menu")).getCssValue("display"));
        assertTrue(element.findElement(By.cssSelector(".slider")).isDisplayed());
        element = element.findElement(By.cssSelector(".certified-fresh-checkbox input"));
        assertTrue(element.isDisplayed());
        element.click();

        wait.until(ExpectedConditions.textToBe(By.cssSelector(".tomatometerScore"), "70% — 100%"));
        List<WebElement> scores = driver.findElements(By.cssSelector(".movie_info .tMeterScore"));
        for (WebElement score : scores) {
            int scoreNumber = Integer.parseInt(score.getText().replace("%", ""));
            assertTrue(scoreNumber >= 70 && scoreNumber <= 100);
        }
    }

    /**
     * RT_13
     * Tamar
     * HTML refers to RT_1, RT_10
     */
    @Test
    public void RT_13_Tamar() throws InterruptedException {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//h2[text()='New & Upcoming Movies']/following-sibling::a"));
        assertEquals("VIEW ALL", element.getText());
        element.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "COMING SOON"));

        Actions actions = new Actions(driver);
        element = driver.findElement(By.id("tomatometerFilter"));
        actions.moveToElement(element).perform();
        assertNotEquals("none", element.findElement(By.className("dropdown-menu")).getCssValue("display"));
        assertTrue(element.findElement(By.cssSelector(".slider")).isDisplayed());
        assertTrue(element.findElement(By.cssSelector(".certified-fresh-checkbox")).isDisplayed());

        element = element.findElement(By.className("noUi-origin"));
        actions.dragAndDrop(element, driver.findElement(By.cssSelector(".slider"))).perform();

        wait.until(ExpectedConditions.textToBe(By.cssSelector(".tomatometerScore"), "50% — 100%"));
        Thread.sleep(500);
        List<WebElement> scores = driver.findElements(By.cssSelector(".movie_info .tMeterScore"));
        for (WebElement score : scores) {
            int scoreNumber = Integer.parseInt(score.getText().replace("%", ""));
            assertTrue(scoreNumber >= 50 && scoreNumber <= 100);
        }
    }

    /**
     * RT_14
     * Tamar
     * HTML refers to RT_1, RT_2
     */
    @Test
    public void RT_14_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//h2[text()='Popular Streaming Movies']//ancestor::section"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.cssSelector("[slot='list-items'] li"));
        String title = element.findElement(By.cssSelector(".dynamic-text-list__item-title")).getText();
        String score = element.findElement(By.cssSelector("[slot='tomatometer-value']")).getText();
        element.findElement(By.tagName("a")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-qa='movie-main-column']")));
        assertEquals(title.toUpperCase(Locale.ROOT), driver.findElement(By.tagName("h1")).getText());
        assertEquals(score.replace("%", ""), driver.findElement(By.tagName("score-board")).getAttribute("tomatometerscore"));
    }

    /**
     * RT_15
     * Tamar
     * HTML refers to RT_1
     */
    @Test
    public void RT_15_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//h2[text()='Popular Streaming Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element.findElement(By.xpath("./.."))).perform();
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
                , element));

        List<WebElement> movies = element.findElements(By.cssSelector("[slot='list-items'] li"));
        for (WebElement movie : movies) {
            assertTrue(movie.findElement(By.cssSelector(".dynamic-text-list__item-title")).isDisplayed());
            assertTrue(movie.findElement(By.cssSelector("[slot='tomatometer-value']")).isDisplayed());
            assertTrue(movie.findElement(By.cssSelector("[slot='tomatometer-icon']")).isDisplayed());
        }
    }

    /**
     * RT_16
     * Tamar
     * HTML refers to RT_1, RT_16
     */
    @Test
    public void RT_16_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//h2[text()='Best Series on Netflix']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element.findElement(By.xpath("./.."))).perform();
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
                , element));

        element.findElement(By.xpath("./following-sibling::a[@data-track='showmore']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.tagName("h1"), "BEST NETFLIX SERIES TO WATCH RIGHT NOW"));
        String expectedNumberOfSeries = driver.findElement(By.tagName("h1")).getText().split(" ")[1];
        List<WebElement> seriesRatingNumber = driver.findElements(By.className("countdown-index"));
        assertEquals(Integer.parseInt(expectedNumberOfSeries), seriesRatingNumber.size());
        for (int i = 0; i < seriesRatingNumber.size(); i++) {
            assertEquals(String.valueOf(seriesRatingNumber.size() - i),
                    seriesRatingNumber.get(i).getText().replace("#", ""));
        }
    }

    /**
     * RT_17
     * Tamar
     * HTML refers to RT_1, RT_17
     */
    @Test
    public void RT_17_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//h2[text()='Best Series on Netflix']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element.findElement(By.xpath("./.."))).perform();
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
                , element));

        element = element.findElement(By.xpath(".//ancestor::section[@id='dynamic-poster-list']//tile-dynamic"));
        String title = element.findElement(By.className("p--small")).getText();
        String score = element.findElement(By.tagName("score-pairs")).getAttribute("criticsscore");
        element.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.cssSelector("[data-qa='score-panel-series-title']"), title.toUpperCase(Locale.ROOT)));
        assertEquals(score, driver.findElement(By.cssSelector("[data-qa='tomatometer']")).getText().replace("%", ""));
        assertEquals("Netflix", driver.findElement(By.cssSelector("[data-qa='series-details-network']")).getText());
    }

    /**
     * RT_18
     * Tamar
     * HTML refers to RT_1, RT_18
     */
    @Test
    public void RT_18_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.cssSelector("[data-qa='header:link-whats-tmeter']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".about-main")));
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
                , driver.findElement(By.id("whatisthetomatometer"))));
    }

    /**
     * RT_19
     * Tamar
     * HTML refers to RT_1, RT_19
     */
    @Test
    public void RT_19_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.cssSelector("[data-qa='header:link-critics-home']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Who are the Tomatometer-approved critics?"));
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("critics-home-spotlight")));
        assertEquals(4, element.findElements(By.className("critics-home-spotlight__li")).size());
        element = element.findElement(By.cssSelector("[data-qa='critic-social-link']"));
        String handle = element.getText();
        element.click();

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Twitter, Inc.')]")));
        assertEquals(handle, driver.findElement(By.cssSelector("[data-testid='UserName'] [dir='ltr']")).getText());
    }

    /**
     * RT_20
     * Tamar
     * HTML refers to RT_1, RT_20
     */
    @Test
    public void RT_20_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//h2[text()='Top Headlines']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element.findElement(By.xpath("./.."))).perform();
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
                , element));
        assertEquals(8, driver.findElements(By.className("top-headlines-item")).size());
        element.findElement(By.xpath("./following-sibling::a[text()='View All']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "TOP HEADLINES"));
        assertTrue(driver.findElement(By.className("publication-row")).isDisplayed());
    }

    /**
     * RT_21
     * Tamar
     * HTML refers to RT_1
     */
    @Test
    public void RT_21_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        List<WebElement> titleElements = element.findElements(By.tagName("h3"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList(
                "MOVIES IN THEATERS", "ON DVD & STREAMING", "MORE", "CERTIFIED FRESH PICKS"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * RT_22
     * Tamar
     * HTML refers to RT_1, RT_2
     */
    @Test
    public void RT_22_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        List<WebElement> movieElements = element.findElements(By.xpath("//h3[text()='Certified Fresh Picks']/following-sibling::*//a"));
        assertEquals(3, movieElements.size());
        element = movieElements.get(0);
        String title = element.findElement(By.cssSelector(".masthead__certified-fresh-pick-text.clamp")).getText();
        String score = element.findElement(By.tagName("score-icon-critic")).getAttribute("percentage");
        element.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), title.toUpperCase(Locale.ROOT)));
        assertEquals(score, driver.findElement(By.tagName("score-board")).getAttribute("tomatometerscore"));
    }

    /**
     * RT_23
     * Tamar
     * HTML refers to RT_1, RT_23
     */
    @Test
    public void RT_23_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        driver.findElement(By.xpath("//a[text()='Top Movies']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TOP MOVIES"));
        List<WebElement> titles = driver.findElements(By.cssSelector("h2.panel-heading"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("BEST MOVIES OF 2021", "TOP 100 MOVIES BY GENRE",
                "BEST MOVIES OF ALL TIME", "BEST MOVIES OF 2020", "MOVIE AWARD WINNERS", "BEST MOVIES BY YEAR",
                "BEST MOVIES OF 2019", "TODAY'S TOP RATED MOVIES", "MOVIE GUIDES"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titles) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * RT_24
     * Tamar
     * HTML refers to RT_1, RT_23, RT_24
     */
    @Test
    public void RT_24_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        driver.findElement(By.xpath("//a[text()='Top Movies']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TOP MOVIES"));
        element = driver.findElement(By.xpath("//*[text()='Best Movies of 2021']"));
        assertTrue(element.isDisplayed());
        element.findElement(By.xpath(".//ancestor::section//*[text()='View All']/..")).click();

        wait.until(ExpectedConditions.textToBe(By.className("panel-heading"), "TOP 100 MOVIES OF 2021"));

        List<WebElement> movieTitles = driver.findElements(By.cssSelector("tbody .articleLink"));
        assertEquals(100, movieTitles.size());
        for (WebElement element : movieTitles) {
            assertTrue(element.getText().endsWith("(2021)"));
        }
    }

    /**
     * RT_25
     * Tamar
     * HTML refers to RT_1, RT_23, RT_24
     */
    @Test
    public void RT_25_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        driver.findElement(By.xpath("//a[text()='Top Movies']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TOP MOVIES"));
        element = driver.findElement(By.xpath("//*[text()='Best Movies of 2021']"));
        assertTrue(element.isDisplayed());
        element.findElement(By.xpath(".//ancestor::section//*[text()='View All']/..")).click();

        wait.until(ExpectedConditions.textToBe(By.className("panel-heading"), "TOP 100 MOVIES OF 2021"));
        List<WebElement> movieTitles = driver.findElements(By.cssSelector("tbody .articleLink"));
        assertEquals(100, movieTitles.size());
        for (WebElement element : movieTitles) {
            assertTrue(element.getText().endsWith("(2021)"));
        }

        driver.findElement(By.cssSelector("#top_movies_main .dropdown-toggle")).click();
        element = driver.findElement(By.cssSelector("ul.dropdown-menu"));
        assertNotEquals("none", element.getCssValue("display"));
        List<WebElement> yearOptions = element.findElements(By.tagName("a"));
        for (int i = 0; i < yearOptions.size(); i++) {
            assertEquals(String.valueOf(2021 - i), yearOptions.get(i).getText());
        }

        yearOptions.get(3).click();

        wait.until(ExpectedConditions.textToBe(By.className("panel-heading"), "TOP 100 MOVIES OF 2018"));
        movieTitles.clear();
        movieTitles = driver.findElements(By.cssSelector("tbody .articleLink"));
        assertEquals(100, movieTitles.size());
        for (WebElement element : movieTitles) {
            assertTrue(element.getText().endsWith("(2018)"));
        }
    }

    /**
     * RT_26
     * Tamar
     * HTML refers to RT_1, RT_23, RT_24, RT_26
     */
    @Test
    public void RT_26_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        driver.findElement(By.xpath("//a[text()='Top Movies']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TOP MOVIES"));
        element = driver.findElement(By.xpath("//*[text()='Best Movies of 2021']"));
        assertTrue(element.isDisplayed());
        element.findElement(By.xpath(".//ancestor::section//*[text()='View All']/..")).click();

        wait.until(ExpectedConditions.textToBe(By.className("panel-heading"), "TOP 100 MOVIES OF 2021"));

        actions.moveToElement(driver.findElement(By.cssSelector("[rel='tooltip'].glyphicon"))).perform();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".tooltip")));
        assertEquals("Each critic from our discrete list gets one vote, weighted equally. " +
                "A movie must have 40 or more rated reviews to be considered. " +
                "The 'Adjusted Score' comes from a weighted formula (Bayesian) that we use that accounts " +
                "for variation in the number of reviews per movie.", element.getText());
        actions.moveToElement(driver.findElement(By.tagName("section"))).perform();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(".tooltip"), 0));
    }

    /**
     * RT_27
     * Tamar
     * HTML refers to RT_1, RT_27
     */
    @Test
    public void RT_27_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        driver.findElement(By.xpath("//a[text()='Browse All']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "BROWSE ALL"));
        element = driver.findElement(By.id("sort-dropdown"));
        actions.moveToElement(element).perform();
        assertNotEquals("none", element.findElement(By.cssSelector(".options")).getCssValue("display"));
        By locator = By.cssSelector("[data-sort-option='Tomatometer']");
        element.findElement(locator).click();

        wait.until(ExpectedConditions.attributeContains(locator, "class", "selected"));
        List<WebElement> scores = driver.findElements(By.cssSelector(".movie_info .tMeterScore"));
        for (int i = 0; i < scores.size() - 1; i++) {
            int currentScoreNumber = Integer.parseInt(scores.get(i).getText().replace("%", ""));
            int nextScoreNumber = Integer.parseInt(scores.get(i + 1).getText().replace("%", ""));
            assertTrue(currentScoreNumber >= nextScoreNumber);
        }
    }

    /**
     * RT_28
     * Tamar
     * HTML refers to RT_1, RT_27
     */
    @Test
    public void RT_28_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        driver.findElement(By.xpath("//a[text()='Browse All']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "BROWSE ALL"));
        String stringExpectedNumber;
        int expectedNumber;
        for (int i = 0; i < 2; i++) {
            int iExpected = (i + 1) * 32;
            if (i == 1) {
                driver.findElement(By.xpath("//button[text()='Show More']")).click();
                wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("count-link"), String.valueOf(iExpected)));
            }
            stringExpectedNumber = driver.findElement(By.id("count-link")).getText().split(" ")[1];
            expectedNumber = Integer.parseInt(stringExpectedNumber);
            assertEquals(iExpected, expectedNumber);
            assertEquals(expectedNumber, driver.findElements(By.className("poster_container")).size());
        }
    }

    /**
     * RT_29
     * Tamar
     * HTML refers to RT_1, RT_27
     */
    @Test
    public void RT_29_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        driver.findElement(By.xpath("//a[text()='Browse All']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "BROWSE ALL"));
        By countLocator = By.id("count-link");
        String originalText = driver.findElement(countLocator).getText();
        String originalNumberOfResultsText = originalText.substring(originalText.lastIndexOf(' ') + 1);

        element = driver.findElement(By.id("genre-dropdown"));
        actions.moveToElement(element).perform();
        assertNotEquals("none", element.findElement(By.cssSelector(".options")).getCssValue("display"));
        actions.moveToElement(element.findElement(By.xpath("//*[@id='genre-action']/following-sibling::*[@class='only']"))).click().perform();

        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(countLocator, originalText)));
        String newText = driver.findElement(countLocator).getText();
        String newNumberOfResultsText = newText.substring(newText.lastIndexOf(' ') + 1);
        assertTrue(Integer.parseInt(newNumberOfResultsText) < Integer.parseInt(originalNumberOfResultsText));
    }


    /**
     * RT_30
     * Tamar
     * HTML refers to RT_1, RT_27, RT_30
     */
    @Test
    public void RT_30_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        driver.findElement(By.xpath("//a[text()='Browse All']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Movies & DVDs')]")));
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        driver.findElement(By.xpath("//h2[text()='On Dvd & Streaming']/..")).click();
        List<WebElement> titles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("panel-heading")));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("NEW ON DVD", "UPCOMING DVDS", "CERTIFIED FRESH PICK",
                "WHAT'S HOT ON RT", "TOP RENTALS", "CRITICS' BUZZ", "GALLERIES", "NEWS & FEATURES"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titles) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * RT_31
     * Tamar
     * HTML refers to RT_1, RT_27, RT_30, RT_31
     */
    @Test
    public void RT_31_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        driver.findElement(By.xpath("//a[text()='Browse All']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Movies & DVDs')]")));
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        driver.findElement(By.xpath("//h2[text()='On Dvd & Streaming']/..")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Galleries']")));
        driver.findElement(By.cssSelector("#picture_galleries .media:nth-child(2) a")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("gallery")));
        assertTrue(element.findElement(By.tagName("h2")).getText().endsWith("PHOTOS"));
    }

    /**
     * RT_32
     * Tamar
     * HTML refers to RT_1, RT_27, RT_30
     */
    @Test
    public void RT_32_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        driver.findElement(By.xpath("//a[text()='Browse All']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Movies & DVDs')]")));
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        driver.findElement(By.xpath("//h2[text()='On Dvd & Streaming']/..")).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("newondvd")));
        assertTrue(element.findElement(By.id("movies_carousel")).isDisplayed());

        element = element.findElement(By.cssSelector(".slick-active > a"));
        String title = element.getAttribute("title");
        actions.moveToElement(element).perform();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".poster_caption:not([style='display: none;'])")));
        assertEquals(title, element.findElement(By.tagName("a")).getText());
    }

    /**
     * RT_33
     * Tamar
     * HTML refers to RT_1, RT_27, RT_30
     */
    @Test
    public void RT_33_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        driver.findElement(By.xpath("//a[text()='Browse All']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Movies & DVDs')]")));
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        driver.findElement(By.xpath("//h2[text()='On Dvd & Streaming']/..")).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("newondvd")));
        assertTrue(element.findElement(By.id("movies_carousel")).isDisplayed());

        List<WebElement> currentActives = element.findElements(By.cssSelector(".slick-active > a"));
        element.findElement(By.cssSelector("button.slick-next")).click();

        List<WebElement> newActives = element.findElements(By.cssSelector(".slick-active > a"));
        assertEquals(currentActives.size(), newActives.size());
        for (int i = 0; i < newActives.size(); i++) {
            assertNotEquals(currentActives.get(i).getAttribute("title"), newActives.get(i).getAttribute("title"));
        }
    }

    /**
     * RT_34
     * Tamar
     * HTML refers to RT_1, RT_27, RT_30, RT_34
     */
    @Test
    public void RT_34_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        driver.findElement(By.xpath("//a[text()='Browse All']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Movies & DVDs')]")));
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        driver.findElement(By.xpath("//h2[text()='On Dvd & Streaming']/..")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='More News & Interview ']")));
        int halfOfHeight = element.getSize().getHeight() / 2;
        int offset = halfOfHeight - 3;
        actions.moveToElement(element).moveByOffset(0, offset).click().perform();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "NEWS & INTERVIEWS"));
    }

    /**
     * RT_35
     * Tamar
     * HTML refers to RT_1, RT_27, RT_30, RT_34
     */
    @Test
    public void RT_35_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        driver.findElement(By.xpath("//a[text()='Browse All']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Movies & DVDs')]")));
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        driver.findElement(By.xpath("//h2[text()='On Dvd & Streaming']/..")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='More News & Interview ']")));
        int halfOfHeight = element.getSize().getHeight() / 2;
        int offset = halfOfHeight - 3;
        actions.moveToElement(element).moveByOffset(0, offset).click().perform();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "NEWS & INTERVIEWS"));
        List<WebElement> currentTitles = driver.findElements(By.cssSelector(".article_body .title"));
        List<String> currentTitlesText = new ArrayList<>();
        for (WebElement title : currentTitles) {
            currentTitlesText.add(title.getText());
        }
        driver.findElement(By.xpath("//a[text()='Next']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Previous']")));
        List<WebElement> newTitles = driver.findElements(By.cssSelector(".article_body .title"));
        assertEquals(currentTitlesText.size(), newTitles.size());
        for (int i = 0; i < currentTitlesText.size(); i++) {
            assertNotEquals(currentTitlesText.get(i), newTitles.get(i).getText());
        }
    }

    /**
     * RT_36
     * Tamar
     * HTML refers to RT_1, RT_27, RT_30, RT_34
     */
    @Test
    public void RT_36_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='Movies']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        driver.findElement(By.xpath("//a[text()='Browse All']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Movies & DVDs')]")));
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        driver.findElement(By.xpath("//h2[text()='On Dvd & Streaming']/..")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='More News & Interview ']")));
        int halfOfHeight = element.getSize().getHeight() / 2;
        int offset = halfOfHeight - 3;
        actions.moveToElement(element).moveByOffset(0, offset).click().perform();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "NEWS & INTERVIEWS"));
        List<WebElement> currentTitles = driver.findElements(By.cssSelector(".article_body .title"));
        List<String> currentTitlesText = new ArrayList<>();
        for (WebElement title : currentTitles) {
            currentTitlesText.add(title.getText());
        }

        element = driver.findElement(By.tagName("select"));
        element.click();
        String numberOfPages = element.findElement(By.xpath("./..")).getText();
        numberOfPages = numberOfPages.substring(numberOfPages.indexOf("of") + 3);
        int intNumberOfPages = Integer.parseInt(numberOfPages);
        List<WebElement> optionsElements = element.findElements(By.tagName("option"));
        assertEquals(intNumberOfPages, optionsElements.size());
        for (int i = 0; i < intNumberOfPages; i++) {
            assertEquals(String.valueOf(i + 1), optionsElements.get(i).getText());
        }
        optionsElements.get(1).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Previous']")));
        List<WebElement> newTitles = driver.findElements(By.cssSelector(".article_body .title"));
        assertEquals(currentTitlesText.size(), newTitles.size());
        for (int i = 0; i < currentTitlesText.size(); i++) {
            assertNotEquals(currentTitlesText.get(i), newTitles.get(i).getText());
        }
        assertEquals("2", driver.findElement(By.cssSelector("option[selected]")).getText());
    }

    /**
     * RT_37
     * Tamar
     * HTML refers to RT_1
     */
    @Test
    public void RT_37_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='TV Shows']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        List<WebElement> titleElements = element.findElements(By.tagName("h3"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList(
                "NEW TV TONIGHT", "MOST POPULAR TV ON RT", "MORE", "EPISODIC REVIEWS", "CERTIFIED FRESH PICK"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * RT_38
     * Tamar
     * HTML refers to RT_1, RT_38
     */
    @Test
    public void RT_38_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='TV Shows']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        element = element.findElement(By.xpath("//h3[text()='New TV Tonight']"));
        assertTrue(element.isDisplayed());
        List<WebElement> articleElements = element.findElements(By.xpath("./../table//td[1]/a"));
        int chosenIndex = 0;
        String title = "", score = "";
        for (int i = 0; i < articleElements.size(); i++) {
            element = articleElements.get(i).findElement(By.cssSelector("score-icon-critic[percentage]"));
            assertTrue(element.isDisplayed());
            String percentageAttribute = element.getAttribute("percentage");
            if (!percentageAttribute.isEmpty() && title.isEmpty()) {
                title = element.findElement(By.xpath("./ancestor::tr//a[@data-qa='list-item-link']")).getText();
                score = percentageAttribute;
                chosenIndex = i;
            }
        }
        articleElements.get(chosenIndex).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("season-body")));
        assertTrue(driver.findElement(By.tagName("h1")).getText().startsWith(title.toUpperCase(Locale.ROOT)));
        assertEquals(score, driver.findElement(By.cssSelector("[data-qa='tomatometer']")).getText().replace("%", ""));
    }

    /**
     * RT_39
     * Tamar
     * HTML refers to RT_1, RT_39
     */
    @Test
    public void RT_39_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='TV Shows']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        element = element.findElement(By.xpath("//h3[text()='New TV Tonight']"));
        assertTrue(element.isDisplayed());
        List<WebElement> articleElements = element.findElements(By.xpath("./../table//td[1]/a"));
        int chosenIndex = 0;
        String title = "";
        for (int i = 0; i < articleElements.size(); i++) {
            element = articleElements.get(i).findElement(By.cssSelector("score-icon-critic[percentage]"));
            assertTrue(element.isDisplayed());
            String percentageAttribute = element.getAttribute("percentage");
            if (percentageAttribute.isEmpty() && title.isEmpty()) {
                title = element.findElement(By.xpath("./ancestor::tr//a[@data-qa='list-item-link']")).getText();
                chosenIndex = i;
            }
        }
        articleElements.get(chosenIndex).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("season-body")));
        assertTrue(driver.findElement(By.tagName("h1")).getText().startsWith(title.toUpperCase(Locale.ROOT)));
        assertEquals("Not enough ratings to\ncalculate a score.",
                driver.findElement(By.cssSelector("[data-qa='tomatometer-container'] p")).getText());
    }


    /**
     * RT_40
     * Tamar
     * HTML refers to RT_1, RT_40
     */
    @Test
    public void RT_40_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='TV Shows']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        element = element.findElement(By.xpath("//h3[text()='New TV Tonight']"));
        assertTrue(element.isDisplayed());
        element.findElement(By.xpath("./following-sibling::a[text()='View All']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "NEW TV TONIGHT"));
        assertEquals("New TV Tonight", driver.findElement(By.cssSelector("#navigation-column .active")).getText());

        List<WebElement> scores = driver.findElements(By.cssSelector(".movie_info .tMeterScore"));
        for (int i = 0; i < scores.size() - 1; i++) {
            int currentScoreNumber = Integer.parseInt(scores.get(i).getText().replace("%", ""));
            int nextScoreNumber = Integer.parseInt(scores.get(i + 1).getText().replace("%", ""));
            assertTrue(currentScoreNumber >= nextScoreNumber);
        }
    }

    /**
     * RT_41
     * Tamar
     * HTML refers to RT_1, RT_40
     */
    @Test
    public void RT_41_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='TV Shows']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        element = element.findElement(By.xpath("//h3[text()='New TV Tonight']"));
        assertTrue(element.isDisplayed());
        element.findElement(By.xpath("./following-sibling::a[text()='View All']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "NEW TV TONIGHT"));
        assertEquals("New TV Tonight", driver.findElement(By.cssSelector("#navigation-column .active")).getText());
        for (int i = 0; i < 3; i++) {
            if (i != 0) {
                By locator = By.cssSelector(".view-icon:nth-of-type(" + (3 - i) + ")");
                driver.findElement(locator).click();
                assertTrue(driver.findElement(locator).getAttribute("class").contains("active"));
                assertEquals(i == 1, driver.findElement(By.cssSelector(".mb-movies")).getAttribute("class").contains("list-view"));
            }
            List<WebElement> scores = driver.findElements(By.cssSelector(".movie_info .tMeterScore"));
            for (int j = 0; j < scores.size() - 1; j++) {
                int currentScoreNumber = Integer.parseInt(scores.get(j).getText().replace("%", ""));
                int nextScoreNumber = Integer.parseInt(scores.get(j + 1).getText().replace("%", ""));
                assertTrue(currentScoreNumber >= nextScoreNumber);
            }
        }
    }

    /**
     * RT_42
     * Tamar
     * HTML refers to RT_1, RT_42
     */
    @Test
    public void RT_42_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='TV Shows']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        element.findElement(By.xpath("//a[text()='Top TV Shows']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TOP TV SHOWS"));
        By locator = By.cssSelector(".carousel-inner .item.active");
        String currentActiveText = driver.findElement(locator).getText();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, currentActiveText)));
    }

    /**
     * RT_43
     * Tamar
     * HTML refers to RT_1, RT_42
     */
    @Test
    public void RT_43_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='TV Shows']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        element.findElement(By.xpath("//a[text()='Top TV Shows']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TOP TV SHOWS"));
        element = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[text()='Popular Shows Available on Streaming']/..")));
        List<WebElement> mediaElements = element.findElements(By.className("media"));
        int countDisplayed = 0;
        for (WebElement media : mediaElements) {
            if (media.isDisplayed())
                countDisplayed++;
        }
        assertEquals(7, countDisplayed);
        element.findElement(By.xpath(".//a[contains(text(), 'View All')]")).click();
        countDisplayed = 0;
        for (WebElement media : mediaElements) {
            if (media.isDisplayed())
                countDisplayed++;
        }
        assertEquals(16, countDisplayed);
    }

    /**
     * RT_44
     * Tamar
     * HTML refers to RT_1, RT_42, RT_44
     */
    @Test
    public void RT_44_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='TV Shows']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        element.findElement(By.xpath("//a[text()='Top TV Shows']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TOP TV SHOWS"));
        driver.findElement(By.xpath("//h3[@class='panel-heading' and text()='Premiere Dates']")).click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TV PREMIERE DATES 2021"));
        List<WebElement> datesTitles = driver.findElements(
                By.xpath("//h2/strong[text()='December']/../following-sibling::p//*[contains(text(), 'Dec.')]"));
        for (int i = 0; i < datesTitles.size(); i++) {
            assertTrue(datesTitles.get(i).getText().endsWith("Dec. " + (i + 1)));
        }
    }

    /**
     * RT_45
     * Tamar
     * HTML refers to RT_1, RT_42, RT_44
     */
    @Test
    public void RT_45_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='TV Shows']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        element.findElement(By.xpath("//a[text()='Top TV Shows']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TOP TV SHOWS"));
        driver.findElement(By.xpath("//h3[@class='panel-heading' and text()='Premiere Dates']")).click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TV PREMIERE DATES 2021"));
        driver.findElement(By.xpath("//a[text()='December']")).click();
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
                , driver.findElement(By.xpath("//h2/strong[text()='December']"))));
    }

    /**
     * RT_46
     * Tamar
     * HTML refers to RT_1, RT_42, RT_44
     */
    @Test
    public void RT_46_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='TV Shows']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        element.findElement(By.xpath("//a[text()='Top TV Shows']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TOP TV SHOWS"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[@class='panel-heading' and text()='Premiere Dates']"))).click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TV PREMIERE DATES 2021"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Archive')]/following-sibling::a[text()='January']"))).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TV PREMIERE DATES 2021 ARCHIVE"));
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
                , driver.findElement(By.xpath("//h2/strong[text()='January']"))));
    }

    /**
     * RT_47
     * Tamar
     * HTML refers to RT_1, RT_42, RT_44, RT_47
     */
    @Test
    public void RT_47_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='TV Shows']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        element.findElement(By.xpath("//a[text()='Top TV Shows']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TOP TV SHOWS"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[@class='panel-heading' and text()='Premiere Dates']"))).click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TV PREMIERE DATES 2021"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Renewed & Cancelled']"))).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "RENEWED AND CANCELLED TV SHOWS 2021"));

        List<WebElement> links = driver.findElements(By.cssSelector(".alpha-menu > a"));
        assertEquals("#", links.get(0).getText());
        for (int i = 1; i < links.size(); i++) {
            assertEquals(String.valueOf((char) (i + 64)), links.get(i).getText());
        }
        links.get(7).click();

        element = driver.findElement(By.xpath("//*[@name='g']/following-sibling::*"));
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
                , element));
        assertEquals("youtube-container", element.findElement(By.xpath("./../following-sibling::*")).getAttribute("class"));
        List<WebElement> shows = element.findElements(By.xpath("./../following-sibling::p[1]/span"));
        for (WebElement showIndication : shows) {
            String indication = showIndication.getText();
            assertTrue(indication.equals("Renewed") || indication.equals("Cancelled"));
        }

        element.findElement(By.xpath("./../following-sibling::a[text()='Back to Top']")).click();
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
                , driver.findElement(By.className("alpha-menu"))));
    }

    /**
     * RT_48
     * Tamar
     * HTML refers to RT_1, RT_38
     */
    @Test
    public void RT_48_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='TV Shows']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        element = element.findElement(By.xpath("//h3[contains(text(), 'Episodic Reviews')]"));
        assertTrue(element.isDisplayed());
        element = element.findElement(By.xpath("./following-sibling::*//a"));
        String title = element.getText();
        element.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("season-body")));
        assertTrue(driver.findElement(By.tagName("h1")).getText().startsWith(title.toUpperCase(Locale.ROOT)));

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
                , driver.findElement(By.xpath("//*[@id='desktopEpisodeList']/h2[text()='Episodes']"))));
    }

    /**
     * RT_49
     * Tamar
     * HTML refers to RT_1, RT_49
     */
    @Test
    public void RT_49_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.id("podcast-link")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"),
                "\"ROTTEN TOMATOES IS WRONG\" (A PODCAST FROM ROTTEN TOMATOES)"));
        assertTrue(driver.findElement(By.className("youtube-container")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[src*='spotify']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[src*='apple']")).isDisplayed());
    }

    /**
     * RT_50
     * Tamar
     * HTML refers to RT_1, RT_49, RT_50
     */
    @Test
    public void RT_50_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.id("podcast-link")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"),
                "\"ROTTEN TOMATOES IS WRONG\" (A PODCAST FROM ROTTEN TOMATOES)"));
        element = driver.findElement(By.cssSelector(".youtube-container iframe"));
        driver.switchTo().frame(element);
        driver.findElement(By.cssSelector(".ytp-large-play-button")).click();
        By locator = By.className("ytp-time-current");
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, "")));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, "0:00")));
    }

    /**
     * RT_51
     * Tamar
     * HTML refers to RT_1, RT_49
     */
    @Test
    public void RT_51_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.id("podcast-link")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"),
                "\"ROTTEN TOMATOES IS WRONG\" (A PODCAST FROM ROTTEN TOMATOES)"));
        element = driver.findElement(By.cssSelector("iframe[src*='spotify']"));
        driver.switchTo().frame(element);
        driver.findElement(By.id("play-button")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBe(
                By.id("progress-bar-slider"), "style", "left: 0%;")));
    }

    /**
     * RT_52
     * Tamar
     * HTML refers to RT_1, RT_49, RT_52
     */
    @Test
    public void RT_52_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.id("podcast-link")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"),
                "\"ROTTEN TOMATOES IS WRONG\" (A PODCAST FROM ROTTEN TOMATOES)"));

        driver.findElement(By.linkText("On an Apple device? Follow Rotten Tomatoes on Apple News")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "This channel is only available in Apple News."));
    }

    /**
     * RT_53
     * Tamar
     * HTML refers to RT_1, RT_49
     */
    @Test
    public void RT_53_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.id("podcast-link")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"),
                "\"ROTTEN TOMATOES IS WRONG\" (A PODCAST FROM ROTTEN TOMATOES)"));

        List<WebElement> episodes = driver.findElements(By.cssSelector(".articleContentBody ul li"));
        for (WebElement episodeLine : episodes) {
            assertTrue(episodeLine.getText().contains("“Rotten Tomatoes Is Wrong”"));
        }
        element = episodes.get(3).findElement(By.tagName("a"));
        String linkText = element.getText().replaceAll("[“”]", "\"").replaceAll("…", "...");
        element.click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), linkText.toUpperCase(Locale.ROOT)));
    }


    /**
     * RT_54
     * Tamar
     * HTML refers to RT_1, RT_49, RT_54
     */
    @Test
    public void RT_54_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.id("podcast-link")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"),
                "\"ROTTEN TOMATOES IS WRONG\" (A PODCAST FROM ROTTEN TOMATOES)"));
        element = driver.findElement(By.xpath("//h2[text()='Tag Cloud']"));
        assertTrue(element.isDisplayed());

        List<WebElement> words = element.findElements(By.xpath("./..//a"));
        String expectedStyleSize = element.findElement(By.xpath("./..//a[contains(@aria-label, '(1 item)')]")).getAttribute("style");
        for (WebElement wordLink : words) {
            if (wordLink.getAttribute("aria-label").endsWith("(1 item)"))
                assertEquals(expectedStyleSize, wordLink.getAttribute("style"));
        }

        String chosenWord = words.get(1).getText();
        String chosenWordNumberOfItems = words.get(1).getAttribute("aria-label");
        chosenWordNumberOfItems = chosenWordNumberOfItems.substring(
                chosenWordNumberOfItems.indexOf("(") + 1, chosenWordNumberOfItems.indexOf("item") - 1);
        words.get(1).click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "TAG > " + chosenWord.toUpperCase(Locale.ROOT)));
        assertEquals(Integer.parseInt(chosenWordNumberOfItems), driver.findElements(By.cssSelector(".article_body .newsItem")).size());
    }

    /**
     * RT_55
     * Tamar
     * HTML refers to RT_1
     */
    @Test
    public void RT_55_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='News']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));

        List<WebElement> titleElements = element.findElements(By.tagName("h3"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("COLUMNS", "BEST AND WORST", "GUIDES", "RT NEWS"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * RT_56
     * Tamar
     * HTML refers to RT_1, RT_34
     */
    @Test
    public void RT_56_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='News']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        element = element.findElement(By.xpath("//h3[text()='Columns']"));
        assertTrue(element.isDisplayed());

        List<WebElement> links = element.findElements(By.xpath("./following-sibling::*//a"));
        assertEquals(18, links.size());
        String linkText = links.get(0).getText();
        links.get(0).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), linkText.toUpperCase(Locale.ROOT)));
        assertTrue(driver.findElement(By.cssSelector(".article_body .newsItem")).isDisplayed());
    }

    /**
     * RT_57
     * Tamar
     * HTML refers to RT_1, RT_57
     */
    @Test
    public void RT_57_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='News']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        element = element.findElement(By.xpath("//h3[text()='Best and Worst']"));
        assertTrue(element.isDisplayed());

        List<WebElement> links = element.findElements(By.xpath("./../following-sibling::*"));
        assertEquals(2, links.size());
        String linkText = "";
        for (WebElement link : links) {
            linkText = link.getText();
            assertTrue(linkText.toLowerCase(Locale.ROOT).endsWith("ranked worst to best by tomatometer"));
        }

        links.get(1).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains(linkText.substring(0, linkText.indexOf("Ranked") - 1)));

        List<WebElement> seriesRatingNumber = driver.findElements(By.className("countdown-index"));
        for (int i = 0; i < seriesRatingNumber.size(); i++) {
            assertEquals(String.valueOf(seriesRatingNumber.size() - i),
                    seriesRatingNumber.get(i).getText().replace("#", ""));
        }
    }

    /**
     * RT_58
     * Tamar
     * HTML refers to RT_1, RT_58
     */
    @Test
    public void RT_58_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='News']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        element = element.findElement(By.xpath("//h3[text()='Guides']/../following-sibling::a"));
        assertTrue(element.isDisplayed());
        assertEquals("Fall TV", element.getText());
        element.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains("Fall TV"));
        List<WebElement> navigationElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector(".hub-subnav-bar a")));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Home", "Premiere Dates", "News & Guides", "Renewed/Cancelled"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navigationElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        assertTrue(driver.findElement(By.cssSelector("[data-ride='carousel']")).isDisplayed());
        assertTrue(driver.findElement(By.id("homepage-spotlight1")).isDisplayed());
        assertTrue(driver.findElement(By.id("homepage-spotlight2")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//h2[text()='More Fall TV']")).isDisplayed());
        driver.switchTo().frame(driver.findElement(By.id("twitter-widget-0")));
        assertEquals("Tweets by @RottenTomatoes", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * RT_59
     * Tamar
     * HTML refers to RT_1
     */
    @Test
    public void RT_59_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='News']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        element = element.findElement(By.xpath("//h3[text()='RT News']"));
        assertTrue(element.isDisplayed());
        element = element.findElement(By.xpath("./../following-sibling::a"));
        String title = element.getText().replace('’', '\'');
        element.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("article_main_body")));
        assertEquals(title.toUpperCase(Locale.ROOT), driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * RT_60
     * Tamar
     * HTML refers to RT_1, RT_34
     */
    @Test
    public void RT_60_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='News']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        element = element.findElement(By.xpath("//h3[text()='RT News']"));
        assertTrue(element.isDisplayed());
        element.findElement(By.xpath("./following-sibling::a[text()='View All']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "NEWS & INTERVIEWS"));
        element = driver.findElement(By.xpath
                ("//h2[text()='Featured on RT']/following-sibling::*/a[contains(@class, 'articleLink')]"));
        String title = element.findElement(By.xpath(".//*[contains(@class, 'title')]")).getText().replace('’', '\'');
        element.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("article_main_body")));
        assertEquals(title.toUpperCase(Locale.ROOT), driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * RT_61
     * Tamar
     * HTML refers to RT_1, RT_34
     */
    @Test
    public void RT_61_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        element = driver.findElement(By.xpath("//a[text()='News']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = element.findElement(By.xpath(".//following-sibling::*[@role='menu']"));
        assertNotEquals("none", element.getCssValue("display"));
        element = element.findElement(By.xpath("//h3[text()='RT News']"));
        assertTrue(element.isDisplayed());
        element.findElement(By.xpath("./following-sibling::a[text()='View All']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "NEWS & INTERVIEWS"));
        element = driver.findElement(By.xpath("(//h2[text()='Top Headlines']/following-sibling::*//a)[2]"));
        String title = element.getText();
        element.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("article_main_body")));
        assertEquals(title.toUpperCase(Locale.ROOT), driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * RT_62
     * Tamar
     * HTML refers to RT_1, RT_62
     */
    @Test
    public void RT_62_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.xpath("//a[text()='Showtimes']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains("Movie Showtimes"));

        driver.findElement(By.id("location-picker__change-button")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(
                By.id("location-picker__form"), "class", "hide")));
        driver.findElement(By.id("location-picker__search-field")).sendKeys("New York");
        driver.findElement(By.className("location-picker__submit-button")).click();
        wait.until(ExpectedConditions.textToBe(By.className("location-picker__text"), "TICKETS & SHOWTIMES NEAR NEW YORK"));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".movie")));
    }

    /**
     * RT_63
     * Tamar
     * HTML refers to RT_1, RT_62
     */
    @Test
    public void RT_63_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.xpath("//a[text()='Showtimes']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains("Movie Showtimes"));

        driver.findElement(By.id("location-picker__change-button")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(
                By.id("location-picker__form"), "class", "hide")));
        driver.findElement(By.id("location-picker__search-field")).sendKeys("10001");
        driver.findElement(By.className("location-picker__submit-button")).click();
        wait.until(ExpectedConditions.textToBe(By.className("location-picker__text"), "TICKETS & SHOWTIMES NEAR 10001"));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".movie")));
    }

    /**
     * RT_64
     * Tamar
     * HTML refers to RT_1, RT_62, RT_64
     */
    @Test
    public void RT_64_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.xpath("//a[text()='Showtimes']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains("Movie Showtimes"));

        element = driver.findElement(By.cssSelector("#moviesFilters__container li:nth-of-type(2) .showtimes-filters__checkbox"));
        String expectedTitle = element.findElement(By.xpath("./..")).getText();
        element.click();

        element = wait.until(ExpectedConditions.numberOfElementsToBe(By.className("movie_title"), 1)).get(0);
        String movieTitle = element.getText();
        assertTrue(expectedTitle.toUpperCase(Locale.ROOT).startsWith(movieTitle));
        String tomatometerScore = driver.findElement(By.className("tmeterpanel")).getText().replace("%", "");
        String audienceScore = driver.findElement(By.className("audiencepanel")).getText().replace("%", "");
        element.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-qa='movie-main-column']")));
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), movieTitle));
        element = driver.findElement(By.tagName("score-board"));
        assertEquals(tomatometerScore, element.getAttribute("tomatometerscore"));
        assertEquals(audienceScore, element.getAttribute("audiencescore"));
    }

    /**
     * RT_65
     * Tamar
     * HTML refers to RT_1, RT_62, RT_64
     */
    @Test
    public void RT_65_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.xpath("//a[text()='Showtimes']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains("Movie Showtimes"));

        driver.findElement(By.id("moviesFilters__dropdown-button")).click();
        assertNotEquals("none", driver.findElement(By.id("moviesFilters__dropdown")).getCssValue("display"));
        element = driver.findElement(By.className("showtimes-filters__additional-item"));
        String expectedTitle = element.getText();
        element.click();

        element = wait.until(ExpectedConditions.numberOfElementsToBe(By.className("movie_title"), 1)).get(0);
        String movieTitle = element.getText();
        assertTrue(expectedTitle.toUpperCase(Locale.ROOT).startsWith(movieTitle));
        String tomatometerScore = driver.findElement(By.className("tmeterpanel")).getText().replace("%", "");
        String audienceScore = driver.findElement(By.className("audiencepanel")).getText().replace("%", "");
        element.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-qa='movie-main-column']")));
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), movieTitle));
        element = driver.findElement(By.tagName("score-board"));
        assertEquals(tomatometerScore, element.getAttribute("tomatometerscore"));
        assertEquals(audienceScore, element.getAttribute("audiencescore"));
    }

    /**
     * RT_66
     * Tamar
     * HTML refers to RT_1, RT_62
     */
    @Test
    public void RT_66_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.xpath("//a[text()='Showtimes']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains("Movie Showtimes"));

        driver.findElement(By.id("location-picker__change-button")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(
                By.id("location-picker__form"), "class", "hide")));
        element = driver.findElement(By.id("location-picker__search-field"));
        element.sendKeys("New York");
        element.submit();
        wait.until(ExpectedConditions.textToBe(By.className("location-picker__text"), "TICKETS & SHOWTIMES NEAR NEW YORK"));

        element = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#theatersList__nearby .showtimes-filters__checkbox")));
        String expectedTitle = element.findElement(By.xpath("./..")).getText();
        element.click();

        element = wait.until(ExpectedConditions.numberOfElementsToBe(By.className("theater-header__title"), 1)).get(0);
        assertEquals(expectedTitle.toUpperCase(Locale.ROOT), element.getText());
    }

    /**
     * RT_67
     * Tamar
     * HTML refers to RT_1, RT_62
     */
    @Test
    public void RT_67_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.xpath("//a[text()='Showtimes']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains("Movie Showtimes"));

        driver.findElement(By.id("location-picker__change-button")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(
                By.id("location-picker__form"), "class", "hide")));
        element = driver.findElement(By.id("location-picker__search-field"));
        element.sendKeys("New York");
        element.submit();
        wait.until(ExpectedConditions.textToBe(By.className("location-picker__text"), "TICKETS & SHOWTIMES NEAR NEW YORK"));

        int originalNumberOfResults = driver.findElements(By.cssSelector(".movie")).size();
        driver.findElement(By.id("theatersList__loadMoreButton")).click();

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".movie"), originalNumberOfResults));
    }

    /**
     * RT_68
     * Tamar
     * HTML refers to RT_1, RT_62
     */
    @Test
    public void RT_68_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.xpath("//a[text()='Showtimes']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains("Movie Showtimes"));

        driver.findElement(By.id("location-picker__change-button")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(
                By.id("location-picker__form"), "class", "hide")));
        element = driver.findElement(By.id("location-picker__search-field"));
        element.sendKeys("New York");
        element.submit();
        wait.until(ExpectedConditions.textToBe(By.className("location-picker__text"), "TICKETS & SHOWTIMES NEAR NEW YORK"));

        By locator = By.cssSelector(".showtimes-calendar__item.active .day-of-month");
        String currentActiveDate = driver.findElement(locator).getText();
        List<WebElement> originalMoviesElements = driver.findElements(By.cssSelector(".movie__title"));
        List<String> originalTitles = new ArrayList<>();
        for (WebElement title : originalMoviesElements) {
            originalTitles.add(title.getText());
        }

        driver.findElement(By.cssSelector(".showtimes-calendar__item:not(.active)")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("showtimesList--loading")));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, currentActiveDate)));

        List<WebElement> newMoviesElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector(".movie__title")));
        List<String> newTitles = new ArrayList<>();
        for (WebElement title : newMoviesElements) {
            newTitles.add(title.getText());
        }
        assertNotEquals(originalTitles, newTitles);
    }

    /**
     * RT_69
     * Tamar
     * HTML refers to RT_1, RT_62
     */
    @Test
    public void RT_69_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.xpath("//a[text()='Showtimes']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains("Movie Showtimes"));

        By locator = By.cssSelector(".showtimes-calendar .showtimes-calendar__item.slick-active .day-of-month");
        List<WebElement> originalDates = driver.findElements(locator);
        List<String> originalDatesText = new ArrayList<>();
        for (WebElement date : originalDates) {
            originalDatesText.add(date.getText());
        }

        driver.findElement(By.cssSelector("[data-qa='carousel-right-btn']")).click();

        List<WebElement> newDates = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        List<String> newDatesText = new ArrayList<>();
        for (WebElement title : newDates) {
            newDatesText.add(title.getText());
        }

        for (int i = 0; i < newDatesText.size() - 1; i++) {
            assertEquals(originalDatesText.get(i + 1), newDatesText.get(i));
        }
        String[] originalFinalDateArr = originalDatesText.get(6).split(" ");
        String originalFinalDateMonth = originalFinalDateArr[0];
        int originalFinalDateDay = Integer.parseInt(originalFinalDateArr[1]);
        assertEquals(originalFinalDateMonth + " " + (originalFinalDateDay + 1), newDatesText.get(6));
    }

    /**
     * RT_70
     * Tamar
     * HTML refers to RT_1, RT_62, RT_70
     */
    @Test
    public void RT_70_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.xpath("//a[text()='Showtimes']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains("Movie Showtimes"));

        driver.findElement(By.id("location-picker__change-button")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(
                By.id("location-picker__form"), "class", "hide")));
        element = driver.findElement(By.id("location-picker__search-field"));
        element.sendKeys("New York");
        element.submit();
        wait.until(ExpectedConditions.textToBe(By.className("location-picker__text"), "TICKETS & SHOWTIMES NEAR NEW YORK"));

        String currentActiveDate = driver.findElement(By.cssSelector(
                ".showtimes-calendar__item.active .day-of-month")).getText();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".movie")));
        String title = element.findElement(By.className("movie__title")).getText();
        element = element.findElement(By.cssSelector(".btn-showtime--active"));
        String time = element.getText().toUpperCase(Locale.ROOT);
        element.click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Buy Tickets"));
        String actualTitle = driver.findElement(By.cssSelector("[data-test='movie-title']")).getText();
        assertEquals(title, actualTitle.substring(0, actualTitle.indexOf("(") - 1));
        String dayDateTimeLine = driver.findElement(By.cssSelector("[data-test='show-date-time']")).getText();
        String dateAndTime = dayDateTimeLine.substring(dayDateTimeLine.indexOf(",") + 2).toUpperCase(Locale.ROOT);
        dateAndTime = dateAndTime.substring(0, dateAndTime.length() - 3) + dateAndTime.substring(dateAndTime.length() - 2);
        assertEquals(currentActiveDate + " AT " + time, dateAndTime);
    }

    /**
     * RT_71
     * Tamar
     * HTML refers to RT_1, RT_71
     */
    @Test
    public void RT_71_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.id("header-facebook-social-link")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.attributeToBe(By.tagName("html"), "id", "facebook"));
        assertEquals("Rotten Tomatoes", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * RT_72
     * Tamar
     * HTML refers to RT_1, RT_72
     */
    @Test
    public void RT_72_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.id("header-snapchat-social-link")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.titleIs("Snapchat"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='rottentomatoes']")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[alt='Snapcode']")));
    }

    /**
     * RT_73
     * Tamar
     * HTML refers to RT_1, RT_73
     */
    @Test
    public void RT_73_Tamar() {
        driver.get("https://www.rottentomatoes.com/");
        driver.findElement(By.id("header-youtube-social-link")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("yt-icon")));
        assertEquals("Rotten Tomatoes", driver.findElement(By.cssSelector(".style-scope.ytd-channel-name")).getText());
    }
}
