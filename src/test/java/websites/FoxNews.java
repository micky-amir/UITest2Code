package websites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

public class FoxNews {
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
     * FXN01
     * Tamar
     */
    @Test
    public void FXN01_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='Politics']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Politics"));
        driver.findElement(By.className("logo")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("market-data")));
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
    }

    /**
     * FXN02
     * Tamar
     * HTML refers to FXN01, FXN02
     */
    @Test
    public void FXN02_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        element = driver.findElement(By.cssSelector("[aria-label='hot topics'] a"));
        String text = element.getText();
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs(text));
        driver.navigate().back();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("market-data")));
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
    }

    /**
     * FXN03
     * Tamar
     * HTML refers to FXN01, FXN03
     */
    @Test
    public void FXN03_Tamar() {
        driver.get("https://www.foxnews.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = driver.findElement(By.id("main-nav"));
        assertTrue(element.isDisplayed());
        String script = "return window.getComputedStyle(document.querySelector('#main-nav'),':selection').getPropertyValue('background-color')";
        JavascriptExecutor js = driver;
        String content = (String) js.executeScript(script);
        assertEquals("rgba(0, 51, 102, 0.99)", content);

        List<String> titles = Arrays.asList("U.S.", "Politics", "Media", "Opinion", "Business", "Entertainment",
                "Sports", "Lifestyle", "TV", "Fox Nation", "Listen");
        for (String title : titles) {
            driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='" + title + "']")).click();
            switch (title) {
                case "Business":
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//nav[@id='main-nav']//*[text()='Economy']")));
                    driver.navigate().back();
                    break;
                case "Sports":
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.className("fox-sports-logo")));
                    break;
                case "TV":
                    wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Fox News Shows"));
                    break;
                case "Fox Nation":
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Try Fox Nation Today']")));
                    break;
                case "Listen":
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='td-top-menu']//*[text()='Podcasts']")));
                    break;
                default:
                    wait.until(ExpectedConditions.textToBe(By.tagName("h1"), title));
                    break;
            }
        }
        driver.navigate().back();
        driver.findElement(By.className("menu-more")).click();
        assertNotEquals("none", driver.findElement(By.className("expandable-nav")).getCssValue("display"));
        assertTrue(driver.findElement(By.className("search")).isDisplayed());
    }

    /**
     * FXN04
     * Tamar
     * HTML refers to FXN01, FXN04
     */
    @Test
    public void FXN04_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        driver.findElement(By.xpath("//*[text()='Watch TV']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".video-player iframe[aria-label='MVPD Picker']")));
        driver.switchTo().frame(element);
        assertEquals("Choose your provider", wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h2"))).getText());
        driver.findElement(By.cssSelector("[data-id='ATT']")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[title='att.net']")));
        driver.switchTo().window(tabs.get(0));
        driver.findElement(By.id("show-clips")).click();
        driver.switchTo().frame(element);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-type='tve']")));
    }

    /**
     * FXN05
     * Tamar
     * HTML refers to FXN01, FXN05
     */
    @Test
    public void FXN05_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        driver.findElement(By.cssSelector(".js-exclusive-clips h2 a")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".video-player iframe[aria-label='MVPD Picker']")));
        element = driver.findElement(By.id("show-clips"));
        assertTrue(element.isDisplayed());
        assertEquals("active", element.getAttribute("class"));
    }

    /**
     * FXN06
     * Tamar
     * HTML refers to FXN01, FXN06
     */
    @Test
    public void FXN06_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        driver.findElement(By.className("js-focus-search")).click();
        assertNotEquals("none", driver.findElement(By.className("expandable-nav")).getCssValue("display"));
        element = driver.findElement(By.cssSelector("[aria-label='search foxnews.com']"));
        assertTrue(element.isDisplayed());
        element.sendKeys("Covid");
        element = driver.findElement(By.cssSelector("[aria-label='submit search']"));
        assertTrue(element.isDisplayed());
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("search-results-content")));
        assertTrue(driver.findElements(By.className("article")).size() > 9);
    }

    /**
     * FXN07
     * Tamar
     * HTML refers to FXN01, FXN06
     */
    @Test
    public void FXN07_Tamar() throws InterruptedException {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        driver.findElement(By.className("js-focus-search")).click();
        assertNotEquals("none", driver.findElement(By.className("expandable-nav")).getCssValue("display"));
        element = driver.findElement(By.cssSelector("[aria-label='search foxnews.com']"));
        assertTrue(element.isDisplayed());
        element.sendKeys("Covid");
        element = driver.findElement(By.cssSelector("[aria-label='submit search']"));
        assertTrue(element.isDisplayed());
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("search-results-content")));
        assertEquals(10, driver.findElements(By.className("article")).size());
        driver.findElement(By.cssSelector(".load-more > a")).click();
        Thread.sleep(1000);
        assertEquals(20, wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("article"))).size());
        assertTrue(driver.findElement(By.cssSelector(".load-more")).isDisplayed());
    }

    /**
     * FXN08
     * Tamar
     * HTML refers to FXN01, FXN06
     */
    @Test
    public void FXN08_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        driver.findElement(By.className("js-focus-search")).click();
        assertNotEquals("none", driver.findElement(By.className("expandable-nav")).getCssValue("display"));
        element = driver.findElement(By.cssSelector("[aria-label='search foxnews.com']"));
        assertTrue(element.isDisplayed());
        element.sendKeys("Covid");
        element = driver.findElement(By.cssSelector("[aria-label='submit search']"));
        assertTrue(element.isDisplayed());
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("search-results-content")));
        By locator = By.cssSelector(".num-found > span > span");
        element = driver.findElement(locator);
        assertTrue(element.isDisplayed());
        String originalCount = element.getText();

        driver.findElement(By.xpath("//*[text()='By Content']/../button")).click();
        assertNotEquals("none", driver.findElement(By.cssSelector(".filter.content ul")).getCssValue("display"));
        List<WebElement> optionElements = driver.findElements(By.xpath("//*[@type='checkbox']/.."));
        List<String> expectedTitles = Arrays.asList("Article", "Video", "Slideshow");
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        optionElements.get(0).click();
        element = driver.findElement(By.xpath("//a[text()='Search']"));
        element.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, originalCount)));
        String newCount = driver.findElement(By.className("num-found")).getText();

        List<WebElement> buttons = driver.findElements(By.cssSelector(".date button"));
        for (WebElement button : buttons) {
            button.click();
            String requiredID = "01";
            if (button.getText().equals("YYYY")) {
                if (button.findElement(By.xpath(".//ancestor::*[contains(@class, 'date')]")).getAttribute("class").contains("min"))
                    requiredID = "2020";
                else
                    requiredID = "2021";
            }
            button.findElement(By.xpath(".//following-sibling::*//*[@id='" + requiredID + "']")).click();
        }
        element.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, newCount)));
    }

    /**
     * FXN09
     * Tamar
     * HTML refers to FXN01, FXN06, FXN09
     */
    @Test
    public void FXN09_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        driver.findElement(By.className("js-focus-search")).click();
        assertNotEquals("none", driver.findElement(By.className("expandable-nav")).getCssValue("display"));
        element = driver.findElement(By.cssSelector("[aria-label='search foxnews.com']"));
        assertTrue(element.isDisplayed());
        element.sendKeys("Covid");
        element = driver.findElement(By.cssSelector("[aria-label='submit search']"));
        assertTrue(element.isDisplayed());
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("search-results-content")));
        assertTrue(driver.findElement(By.className("num-found")).isDisplayed());
        element = driver.findElement(By.cssSelector("h2 > a"));
        String title = element.getText();
        element.click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertEquals(title, driver.getTitle());
        assertTrue(driver.getCurrentUrl().startsWith("https://www.foxnews.com/"));
    }

    /**
     * FXN10
     * Tamar
     * HTML refers to FXN01, FXN10
     */
    @Test
    public void FXN10_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        driver.findElement(By.cssSelector(".more-markets > a")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertEquals("Markets", driver.findElement(By.tagName("h1")).getText());
        driver.switchTo().frame(driver.findElement(By.id("market-index-charts")));
        List<WebElement> sectionElements = driver.findElements(By.className("fdsg-headtext"));
        List<String> expectedTitles = Arrays.asList("DOW JONES AVERAGES", "NASDAQ COMPOSITE INDEX", "S&P 500");
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : sectionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * FXN11
     * Tamar
     * HTML refers to FXN01, FXN03, FX10, FXN11
     */
    @Test
    public void FXN11_Tamar() {
        driver.get("https://www.foxnews.com/");
        element = driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='Business']"));
        assertTrue(element.isDisplayed());
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertEquals("Fox Business", driver.findElement(By.tagName("h1")).getText());
        List<WebElement> navElements = driver.findElements(By.cssSelector("#main-nav a"));
        List<String> expectedTitles = Arrays.asList("Personal Finance", "Economy", "Markets", "Watchlist",
                "Lifestyle", "Real Estate", "Tech", "TV", "Podcasts", "More");
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        for (String title : actualTitles) {
            driver.findElement(By.xpath("//nav[@id='main-nav']//a[contains(text(), '" + title + "')]")).click();
            switch (title) {
                case "Personal Finance":
                    assertTrue(driver.findElement(By.tagName("h1")).getText().contains(title));
                    driver.navigate().back();
                    wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Fox Business"));
                    break;
                case "Watchlist":
                    wait.until(ExpectedConditions.textToBe(By.className("page-title"), title));
                    break;
                case "Tech":
                    wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Technology"));
                    break;
                case "TV":
                    wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "On Air"));
                    break;
                case "Podcasts":
                    wait.until(ExpectedConditions.textToBe(By.tagName("strong"), "FOX News Talk"));
                    driver.navigate().back();
                    wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "On Air"));
                    break;
                case "More":
                    assertNotEquals("none", driver.findElement(By.className("expandable-nav")).getCssValue("display"));
                    assertTrue(driver.findElement(By.className("search")).isDisplayed());
                    break;
                default:
                    wait.until(ExpectedConditions.textToBe(By.tagName("h1"), title));
                    break;
            }
        }
    }

    /**
     * FXN12
     * Tamar
     * HTML refers to FXN01, FXN03, FXN11
     */
    @Test
    public void FXN12_Tamar() {
        driver.get("https://www.foxnews.com/");
        element = driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='Business']"));
        assertTrue(element.isDisplayed());
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertEquals("Fox Business", driver.findElement(By.tagName("h1")).getText());
        List<WebElement> navElements = driver.findElements(By.cssSelector("#main-nav a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Personal Finance", "Economy", "Markets", "Watchlist",
                "Lifestyle", "Real Estate", "Tech", "TV", "Podcasts", "More"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(0).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(), 'Personal Finance')]")));
        navElements.clear();
        expectedTitles.clear();
        actualTitles.clear();

        navElements = driver.findElements(By.cssSelector(".navbar__link"));
        expectedTitles = Arrays.asList("Mortgages", "Student Loans", "Personal Loans", "Credit Cards", "Insurance", "High Yield Savings");
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        Actions actions = new Actions(driver);
        for (int i = 0; i < navElements.size(); i++) {
            actions.moveToElement(navElements.get(i)).perform();
            assertNotEquals("hidden", navElements.get(i).getCssValue("visibility"));
            List<WebElement> columns = navElements.get(i).findElements(By.xpath(".//following-sibling::*//*[@class='category__title']"));
            List<String> expectedCategories = new ArrayList<>();
            List<String> actualCategories = new ArrayList<>();
            switch (i) {
                case 0:
                    expectedCategories = Arrays.asList("Best refinance rates", "Cash out refinancing", "Guide to refinancing", "How refinancing works");
                    break;
                case 1:
                    expectedCategories = Arrays.asList("Applying for FAFSA", "Best student loans", "How to refinance", "Reduce student loan debt");
                    break;
                case 2:
                    expectedCategories = Arrays.asList("Best personal loans", "Debt consolidation loans", "Pay off credit card debt", "Take out personal loan");
                    break;
                case 3:
                    expectedCategories = Arrays.asList("Avoid credit card mistakes", "How to build credit", "Types of credit cards");
                    break;
                case 4:
                    expectedCategories = Arrays.asList("Auto insurance", "Homeowners insurance", "Life insurance");
                    break;
                case 5:
                    expectedCategories = Arrays.asList("Boost your savings", "Guide to high-yield savings account", "How to open high-yield savings account");
                    break;
            }
            for (WebElement element : columns) {
                actualCategories.add(element.getText());
            }
            assertEquals(expectedCategories, actualCategories);
        }
    }

    /**
     * FXN13
     * Tamar
     * HTML refers to FXN01, FXN03, FXN11, FXN13
     */
    @Test
    public void FXN13_Tamar() {
        driver.get("https://www.foxnews.com/");
        element = driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='Business']"));
        assertTrue(element.isDisplayed());
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertEquals("Fox Business", driver.findElement(By.tagName("h1")).getText());
        List<WebElement> navElements = driver.findElements(By.cssSelector("#main-nav a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Personal Finance", "Economy", "Markets", "Watchlist",
                "Lifestyle", "Real Estate", "Tech", "TV", "Podcasts", "More"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(3).click();
        wait.until(ExpectedConditions.textToBe(By.className("page-title"), "Watchlist"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Create a New List']"))).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Sign In"));
        driver.findElement(By.xpath("//*[text()='Create Account']")).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Create Account"));
        driver.findElement(By.cssSelector("[name='email']")).sendKeys("qa.tries.123@gmail.com");
        driver.findElement(By.id("password")).sendKeys("test123!");
        driver.findElement(By.cssSelector("[name='firstName']")).sendKeys("first");
        driver.findElement(By.cssSelector("[name='lastName']")).sendKeys("last");
        driver.findElement(By.cssSelector("[name='displayName']")).sendKeys("testUser");
        driver.findElement(By.cssSelector("[name='month']")).click();
        driver.findElement(By.xpath("//option[text()='Jan']")).click();
        driver.findElement(By.cssSelector("[name='day']")).click();
        driver.findElement(By.xpath("//option[text()='01']")).click();
        driver.findElement(By.cssSelector("[name='year']")).click();
        driver.findElement(By.xpath("//option[text()='2000']")).click();
        driver.findElement(By.cssSelector("[name='gender']")).click();
        driver.findElement(By.xpath("//option[text()='Female']")).click();
        driver.findElement(By.className("foxid-input-checkbox")).click();
        driver.findElement(By.xpath("//button[text()='Create Account']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Verify Account']")));
        driver.findElement(By.xpath("//button[text()='Log Out']")).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Fox Business"));
    }

    /**
     * FXN14
     * Tamar
     * HTML refers to FXN01, FXN03, FXN11, FXN13, FXN14
     */
    @Test
    public void FXN14_Tamar() {
        driver.get("https://www.foxnews.com/");
        element = driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='Business']"));
        assertTrue(element.isDisplayed());
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertEquals("Fox Business", driver.findElement(By.tagName("h1")).getText());
        List<WebElement> navElements = driver.findElements(By.cssSelector("#main-nav a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Personal Finance", "Economy", "Markets", "Watchlist",
                "Lifestyle", "Real Estate", "Tech", "TV", "Podcasts", "More"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(3).click();

        wait.until(ExpectedConditions.textToBe(By.className("page-title"), "Watchlist"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Create a New List']"))).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Sign In"));
        driver.findElement(By.cssSelector("[name='email']")).sendKeys("qa.tries.123@gmail.com");
        driver.findElement(By.cssSelector("[name='password']")).sendKeys("test123!");
        driver.findElement(By.cssSelector(".login")).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Fox Business"));
        driver.findElement(By.cssSelector("[data-omtr-intcmp='nav_watchlist']")).click();

        wait.until(ExpectedConditions.textToBe(By.className("page-title"), "Watchlist"));
        assertTrue(driver.findElement(By.className("watchlist-search-bar")).isDisplayed());
        assertTrue(driver.findElement(By.className("edit-button")).isDisplayed());
        String[] searchWords = {"ST", "A"};
        for (int i = 0; i < 2; i++) {
            driver.findElement(By.cssSelector(".watchlist-search-bar > input")).sendKeys(searchWords[i]);
            wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(By.className("dropdown")), "style")));
            element = driver.findElement(By.className("result"));
            String name = element.findElement(By.className("company")).getText();
            element.click();
            wait.until(ExpectedConditions.textToBe(By.cssSelector(".table-row:last-child .stock-summary-cell .company-name"), name));
        }
    }

    /**
     * FXN15
     * Tamar
     * HTML refers to FXN01, FXN03, FXN11, FXN13, FXN14, FXN15
     */
    @Test
    public void FXN15_Tamar() {
        driver.get("https://www.foxnews.com/");
        element = driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='Business']"));
        assertTrue(element.isDisplayed());
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertEquals("Fox Business", driver.findElement(By.tagName("h1")).getText());
        List<WebElement> navElements = driver.findElements(By.cssSelector("#main-nav a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Personal Finance", "Economy", "Markets", "Watchlist",
                "Lifestyle", "Real Estate", "Tech", "TV", "Podcasts", "More"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(3).click();

        wait.until(ExpectedConditions.textToBe(By.className("page-title"), "Watchlist"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Create a New List']"))).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Sign In"));
        driver.findElement(By.cssSelector("[name='email']")).sendKeys("qa.tries.123@gmail.com");
        driver.findElement(By.cssSelector("[name='password']")).sendKeys("test123!");
        driver.findElement(By.cssSelector(".login")).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Fox Business"));
        driver.findElement(By.cssSelector("[data-omtr-intcmp='nav_watchlist']")).click();

        wait.until(ExpectedConditions.textToBe(By.className("page-title"), "Watchlist"));
        assertTrue(driver.findElement(By.className("watchlist-search-bar")).isDisplayed());
        assertTrue(driver.findElement(By.className("edit-button")).isDisplayed());
        driver.findElement(By.cssSelector(".watchlist-search-bar > input")).sendKeys("ST");
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(By.className("dropdown")), "style")));
        element = driver.findElement(By.className("result"));
        String name = element.findElement(By.className("company")).getText();
        element.click();
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".table-row:last-child .stock-summary-cell .company-name"), name));

        driver.findElement(By.className("edit-button")).click();
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".watchlist-header > button"), "Save"));
        driver.findElement(By.className("edit-done-button")).click();
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".watchlist-header > button"), "Edit"));
    }

    /**
     * FXN16
     * Tamar
     * HTML refers to FXN01, FXN03, FXN11, FXN13, FXN14, FXN15
     */
    @Test
    public void FXN16_Tamar() {
        driver.get("https://www.foxnews.com/");
        element = driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='Business']"));
        assertTrue(element.isDisplayed());
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertEquals("Fox Business", driver.findElement(By.tagName("h1")).getText());
        List<WebElement> navElements = driver.findElements(By.cssSelector("#main-nav a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Personal Finance", "Economy", "Markets", "Watchlist",
                "Lifestyle", "Real Estate", "Tech", "TV", "Podcasts", "More"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(3).click();

        wait.until(ExpectedConditions.textToBe(By.className("page-title"), "Watchlist"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Create a New List']"))).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Sign In"));
        driver.findElement(By.cssSelector("[name='email']")).sendKeys("qa.tries.123@gmail.com");
        driver.findElement(By.cssSelector("[name='password']")).sendKeys("test123!");
        driver.findElement(By.cssSelector(".login")).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Fox Business"));
        driver.findElement(By.cssSelector("[data-omtr-intcmp='nav_watchlist']")).click();

        wait.until(ExpectedConditions.textToBe(By.className("page-title"), "Watchlist"));
        assertTrue(driver.findElement(By.className("watchlist-search-bar")).isDisplayed());
        assertTrue(driver.findElement(By.className("edit-button")).isDisplayed());
        for (int i = 0; i < 2; i++) {
            driver.findElement(By.cssSelector(".watchlist-search-bar > input")).sendKeys("ST");
            wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(By.className("dropdown")), "style")));
            element = driver.findElement(By.className("result"));
            String name = element.findElement(By.className("company")).getText();
            element.click();
            wait.until(ExpectedConditions.textToBe(By.cssSelector(".table-row:last-child .stock-summary-cell .company-name"), name));
            driver.findElement(By.className("edit-button")).click();

            if (i == 0) {
                wait.until(ExpectedConditions.textToBe(By.cssSelector(".watchlist-header > button"), "Save"));
                driver.findElement(By.cssSelector(".table-row:last-child .watchlist-edit-row [alt='delete']")).click();
                assertEquals(0, driver.findElements(By.xpath("//*[@class='table-row']//*[@class='company-name' and text()='" + name + "']")).size());
                driver.findElement(By.className("edit-done-button")).click();
                wait.until(ExpectedConditions.textToBe(By.cssSelector(".watchlist-header > button"), "Edit"));
                assertEquals(0, driver.findElements(By.xpath("//*[@class='table-row']//*[@class='company-name' and text()='" + name + "']")).size());
            } else {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.className("delete-button"))).click();
                assertEquals("Are you sure you want to delete the whole list?", driver.switchTo().alert().getText());
                driver.switchTo().alert().accept();
                wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(".table-row .company-name"), 0));
            }
        }
    }

    /**
     * FXN17
     * Tamar
     * HTML refers to FXN01, FXN03, FXN17
     */
    @Test
    public void FXN17_Tamar() {
        driver.get("https://www.foxnews.com/");
        element = driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='Business']"));
        assertTrue(element.isDisplayed());
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertEquals("Fox Business", driver.findElement(By.tagName("h1")).getText());
        List<WebElement> navElements = driver.findElements(By.cssSelector("#main-nav a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Personal Finance", "Economy", "Markets", "Watchlist",
                "Lifestyle", "Real Estate", "Tech", "TV", "Podcasts", "More"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(7).click();
        List<WebElement> buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy((By.cssSelector(".heading a"))));
        expectedTitles.clear();
        actualTitles.clear();
        expectedTitles = new ArrayList<>(Arrays.asList("Watch Live", "Full Schedule", "Personalities", "Channel Finder"));
        for (WebElement element : buttons) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * FXN18
     * Tamar
     * HTML refers to FXN01, FXN03, FXN17, FXN18
     */
    @Test
    public void FXN18_Tamar() {
        driver.get("https://www.foxnews.com/");
        element = driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='Business']"));
        assertTrue(element.isDisplayed());
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertEquals("Fox Business", driver.findElement(By.tagName("h1")).getText());
        List<WebElement> navElements = driver.findElements(By.cssSelector("#main-nav a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Personal Finance", "Economy", "Markets", "Watchlist",
                "Lifestyle", "Real Estate", "Tech", "TV", "Podcasts", "More"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(7).click();
        List<WebElement> buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy((By.cssSelector(".heading a"))));
        expectedTitles.clear();
        actualTitles.clear();
        expectedTitles = new ArrayList<>(Arrays.asList("Watch Live", "Full Schedule", "Personalities", "Channel Finder"));
        for (WebElement element : buttons) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        buttons.get(0).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("video-player")));
        List<WebElement> subNavElements = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy((By.cssSelector(".show nav.secondary a"))));
        expectedTitles.clear();
        actualTitles.clear();
        expectedTitles = new ArrayList<>(Arrays.asList("Featured", "Full Episodes", "Primetime", "Daytime", "All Shows"));
        for (WebElement element : subNavElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * FXN19
     * Tamar
     * HTML refers to FXN01, FXN03, FXN17, FXN19
     */
    @Test
    public void FXN19_Tamar() {
        driver.get("https://www.foxnews.com/");
        element = driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='Business']"));
        assertTrue(element.isDisplayed());
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertEquals("Fox Business", driver.findElement(By.tagName("h1")).getText());
        List<WebElement> navElements = driver.findElements(By.cssSelector("#main-nav a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Personal Finance", "Economy", "Markets", "Watchlist",
                "Lifestyle", "Real Estate", "Tech", "TV", "Podcasts", "More"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(7).click();
        expectedTitles.clear();
        actualTitles.clear();
        List<WebElement> buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy((By.cssSelector(".heading a"))));
        expectedTitles = new ArrayList<>(Arrays.asList("Watch Live", "Full Schedule", "Personalities", "Channel Finder"));
        for (WebElement element : buttons) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        buttons.get(1).click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Full Schedule"));
        element = driver.findElement(By.xpath("//*[@class='weekday']//*[contains(text(), 'Yesterday')]"));
        assertTrue(element.isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='weekday']//*[contains(text(), 'Today')]")).isDisplayed());
        element.click();
        assertTrue(driver.findElement(By.cssSelector(".item-day.active")).getText().contains("Yesterday"));
    }

    /**
     * FXN20
     * Tamar
     * HTML refers to FXN01, FXN03, FXN17, FXN20
     */
    @Test
    public void FXN20_Tamar() {
        driver.get("https://www.foxnews.com/");
        element = driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='Business']"));
        assertTrue(element.isDisplayed());
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertEquals("Fox Business", driver.findElement(By.tagName("h1")).getText());
        List<WebElement> navElements = driver.findElements(By.cssSelector("#main-nav a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Personal Finance", "Economy", "Markets", "Watchlist",
                "Lifestyle", "Real Estate", "Tech", "TV", "Podcasts", "More"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(7).click();
        expectedTitles.clear();
        actualTitles.clear();
        List<WebElement> buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy((By.cssSelector(".heading a"))));
        expectedTitles = new ArrayList<>(Arrays.asList("Watch Live", "Full Schedule", "Personalities", "Channel Finder"));
        for (WebElement element : buttons) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        buttons.get(2).click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Anchors & Reporters"));
    }

    /**
     * FXN21
     * Tamar
     * HTML refers to FXN01, FXN03, FXN17, FXN21
     */
    @Test
    public void FXN21_Tamar() {
        driver.get("https://www.foxnews.com/");
        element = driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='Business']"));
        assertTrue(element.isDisplayed());
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertEquals("Fox Business", driver.findElement(By.tagName("h1")).getText());
        List<WebElement> navElements = driver.findElements(By.cssSelector("#main-nav a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Personal Finance", "Economy", "Markets", "Watchlist",
                "Lifestyle", "Real Estate", "Tech", "TV", "Podcasts", "More"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(7).click();
        expectedTitles.clear();
        actualTitles.clear();
        List<WebElement> buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy((By.cssSelector(".heading a"))));
        expectedTitles = new ArrayList<>(Arrays.asList("Watch Live", "Full Schedule", "Personalities", "Channel Finder"));
        for (WebElement element : buttons) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        buttons.get(3).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("channel-finder")));
        driver.switchTo().frame(element);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Fox Business Network"));
        driver.findElement(By.id("cfZip")).sendKeys("12010");
        driver.findElement(By.id("bttnGO")).click();
        assertEquals("Choose your Provider", driver.findElement(By.id("providerLabel")).getText());
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("#cfProviderSelect > *"), 1));
    }

    /**
     * FXN22
     * Tamar
     * HTML refers to FXN01, FXN03
     */
    @Test
    public void FXN22_Tamar() {
        driver.get("https://www.foxnews.com/");
        element = driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='Business']"));
        assertTrue(element.isDisplayed());
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertEquals("Fox Business", driver.findElement(By.tagName("h1")).getText());
        List<WebElement> navElements = driver.findElements(By.cssSelector("#main-nav a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Personal Finance", "Economy", "Markets", "Watchlist",
                "Lifestyle", "Real Estate", "Tech", "TV", "Podcasts", "More"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(8).click();
        expectedTitles.clear();
        actualTitles.clear();
        List<WebElement> menuItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                (By.cssSelector(".menu-top-nav-container #menu-top-nav-1 > li.menu-item > a"))));
        expectedTitles = new ArrayList<>(Arrays.asList("How to Listen", "FOX News Talk Shows", "Podcasts", "Station Finder"));
        for (int i = 1; i < menuItems.size(); i++) {
            actualTitles.add(menuItems.get(i).getText().replace("\n»", ""));
        }
        assertEquals(expectedTitles, actualTitles);

        Actions actions = new Actions(driver);
        actions.moveToElement(menuItems.get(3)).perform();
        wait.until(ExpectedConditions.attributeContains(menuItems.get(3).findElement(
                By.xpath(".//following-sibling::ul[@class='sub-menu']")), "style", "display: block;"));
        expectedTitles.clear();
        actualTitles.clear();
        List<WebElement> submenuItems = menuItems.get(3).findElements((By.xpath(
                ".//following-sibling::ul[@class='sub-menu']//li[contains(@class, 'menu-item-type-custom')]/a")));
        expectedTitles = new ArrayList<>(Arrays.asList(
                "Premium Podcast Account Access", "FOX News Talk Radio Premium Podcasts",
                "FOX News Channel Premium Podcasts", "FOX Business Network Premium Podcasts", "Free Podcasts"));
        for (WebElement element : submenuItems) {
            if (!element.getText().isEmpty())
                actualTitles.add(element.getText().replace("\n»", ""));
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * FXN23
     * Tamar
     * HTML refers to FXN01, FXN03, FXN23
     */
    @Test
    public void FXN23_Tamar() {
        driver.get("https://www.foxnews.com/");
        element = driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='Business']"));
        assertTrue(element.isDisplayed());
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertEquals("Fox Business", driver.findElement(By.tagName("h1")).getText());
        List<WebElement> navElements = driver.findElements(By.cssSelector("#main-nav a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Personal Finance", "Economy", "Markets", "Watchlist",
                "Lifestyle", "Real Estate", "Tech", "TV", "Podcasts", "More"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(8).click();
        expectedTitles.clear();
        actualTitles.clear();
        List<WebElement> menuItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                (By.cssSelector(".menu-top-nav-container #menu-top-nav-1 > li.menu-item > a"))));
        expectedTitles = new ArrayList<>(Arrays.asList("How to Listen", "FOX News Talk Shows", "Podcasts", "Station Finder"));
        for (int i = 1; i < menuItems.size(); i++) {
            actualTitles.add(menuItems.get(i).getText().replace("\n»", ""));
        }
        assertEquals(expectedTitles, actualTitles);
        menuItems.get(4).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".td-page-text-content iframe")));
        driver.switchTo().frame(element);
        assertTrue(driver.findElement(By.cssSelector("select[name='State']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("select[name='Show']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("input[value='Search']")).isDisplayed());
        driver.findElement(By.cssSelector("select[name='State']")).click();
        driver.findElement(By.cssSelector("option[value='AZ']")).click();
        driver.findElement(By.cssSelector("select[name='Show']")).click();
        driver.findElement(By.cssSelector("option[value='5']")).click();
        driver.findElement(By.cssSelector("input[value='Search']")).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("tbody > tr")));
        assertEquals("5 MINUTE NEWS", driver.findElement(By.cssSelector("h1 span:nth-child(1)")).getText());
        assertEquals("AZ", driver.findElement(By.cssSelector("h1 span:nth-child(2)")).getText());
    }

    /**
     * FXN24
     * Tamar
     * HTML refers to FXN01, FXN03, FXN24
     */
    @Test
    public void FXN24_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        driver.findElement(By.xpath("//nav[@id='main-nav']//a[text()='Business']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.findElement(By.className("js-focus-search")).click();
        assertNotEquals("none", driver.findElement(By.className("expandable-nav")).getCssValue("display"));
        assertTrue(driver.findElement(By.cssSelector("[aria-label='search foxbusiness.com']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[aria-label='submit search']")).isDisplayed());
        driver.findElement(By.cssSelector("[data-omtr-intcmp='more_personalfinance_subsection_retirement']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(), 'Retirement')]")));
    }

    /**
     * FXN44
     * Tamar
     * HTML refers to FXN01, FXN44
     */
    @Test
    public void FXN44_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.findElement(By.className("js-focus-search")).click();
        assertNotEquals("none", driver.findElement(By.className("expandable-nav")).getCssValue("display"));
        assertTrue(driver.findElement(By.cssSelector("[aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[aria-label='submit search']")).isDisplayed());
        driver.findElement(By.cssSelector("[aria-label='Other - Fox News Shop']")).click();
        wait.until(ExpectedConditions.titleContains("Shop"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".field.search")));
        assertTrue(driver.findElement(By.xpath("//*[text()='My Cart']")).isDisplayed());
        driver.findElement(By.xpath("//*[text()='Patriotic Collection']/..")).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".product-item")));
        driver.findElement(By.cssSelector("[title='Add to Cart']")).click();
        wait.until(ExpectedConditions.textToBe(By.className("counter-number"), "1"));
        driver.findElement(By.cssSelector(".showcart")).click();
        assertEquals("1 Item in Cart", driver.findElement(By.className("items-total")).getText());
    }

    /**
     * FXN45
     * Tamar
     * HTML refers to FXN01, FXN44, FXN45
     */
    @Test
    public void FXN45_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        WebDriverWait wait = new WebDriverWait(driver, 15);

        driver.findElement(By.className("js-focus-search")).click();
        assertNotEquals("none", driver.findElement(By.className("expandable-nav")).getCssValue("display"));
        assertTrue(driver.findElement(By.cssSelector("[aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[aria-label='submit search']")).isDisplayed());
        driver.findElement(By.cssSelector("[aria-label='Other - Fox News Shop']")).click();
        wait.until(ExpectedConditions.titleContains("Shop"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".field.search")));
        assertTrue(driver.findElement(By.xpath("//*[text()='My Cart']")).isDisplayed());
        driver.findElement(By.xpath("//*[text()='Patriotic Collection']/..")).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".product-item")));
        driver.findElement(By.cssSelector("[title='Add to Cart']")).click();
        wait.until(ExpectedConditions.textToBe(By.className("counter-number"), "1"));
        driver.findElement(By.cssSelector(".showcart")).click();

        assertNotEquals("none",
                driver.findElement(By.cssSelector(".minicart-wrapper .ui-dialog")).getCssValue("display"));
        driver.findElement(By.xpath("//*[text()='View and Edit Cart']/..")).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Shopping Cart"));
        assertEquals(1, driver.findElements(By.cssSelector(".cart.item")).size());
        element = driver.findElement(By.cssSelector("[title='Qty']"));
        assertEquals("1", element.getAttribute("value"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='cart-summary']//*[text()='Order Total']")));
        By locator = By.cssSelector("[data-th='Order Total']");
        String originalTotal = driver.findElement(locator).getText();
        element.clear();
        element.sendKeys("3");
        driver.findElement(By.xpath("//*[text()='Update Shopping Cart']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, originalTotal)));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[title='Remove item']"))).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("cart-empty"),
                "You have no items in your shopping cart."));
        assertTrue(driver.findElement(By.cssSelector(".counter.qty")).getAttribute("class").contains("empty"));
    }

    /**
     * FXN46
     * Tamar
     * HTML refers to FXN01
     */
    @Test
    public void FXN46_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        JavascriptExecutor js = driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
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
                , driver.findElement(By.className("footer-lower"))));
        driver.findElement(By.cssSelector("footer a[aria-label*='Terms of Use']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Fox News Network, LLC Terms of Use Agreement"));
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
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
                , driver.findElement(By.xpath("//*[text()=" +
                        "'I HAVE READ THIS AGREEMENT AND AGREE TO ALL OF THE PROVISIONS CONTAINED ABOVE.']"))));
    }

    /**
     * FXN47
     * Tamar
     * HTML refers to FXN01, FXN47
     */
    @Test
    public void FXN47_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        JavascriptExecutor js = driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
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
                , driver.findElement(By.className("footer-lower"))));
        driver.findElement(By.cssSelector("footer a[aria-label*='Privacy Policy']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Privacy Policy"));
        assertEquals("Table of Contents:", driver.findElement(By.tagName("h2")).getText());
        List<WebElement> navElements = driver.findElements(By.cssSelector("ol > li strong"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Scope and Application",
                "Collection of Information", "Use and Disclosure", "Security", "User Access and Control",
                "Other Important Information", "Contact Us", "California Consumer Privacy Act Notice"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * FXN48
     * Tamar
     * HTML refers to FXN01
     */
    @Test
    public void FXN48_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        JavascriptExecutor js = driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
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
                , driver.findElement(By.className("footer-lower"))));
        driver.findElement(By.cssSelector("footer a[aria-label='Facebook']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("https://www.facebook.com/FoxNews"));
    }

    /**
     * FXN49
     * Tamar
     * HTML refers to FXN01
     */
    @Test
    public void FXN49_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        JavascriptExecutor js = driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
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
                , driver.findElement(By.className("footer-lower"))));
        driver.findElement(By.cssSelector("footer a[aria-label='Instagram']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("https://www.instagram.com/foxnews/"));
    }

    /**
     * FXN50
     * Tamar
     * HTML refers to FXN01
     */
    @Test
    public void FXN50_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        JavascriptExecutor js = driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
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
                , driver.findElement(By.className("footer-lower"))));
        driver.findElement(By.cssSelector("footer a[aria-label='Twitter']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("https://twitter.com/foxnews"));
    }

    /**
     * FXN51
     * Tamar
     * HTML refers to FXN01
     */
    @Test
    public void FXN51_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        JavascriptExecutor js = driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
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
                , driver.findElement(By.className("footer-lower"))));
        driver.findElement(By.cssSelector("footer a[aria-label='Youtube']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("https://www.youtube.com/c/FoxNews"));
    }

    /**
     * FXN25
     * Mika
     * HTML refer to FXN25
     */
    @Test
    public void FXN25_Mika() {
        String newTab;
        String topic = "Business";
        String category = "Economy";
        String pageToChoose = "Small Business";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("menu-" + topic.toLowerCase(Locale.ROOT))).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("search"))).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxbusiness.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@aria-label='header nav item " + category + "']//*[text()='" + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[contains(text(), '" + pageToChoose + "')]")));
    }

    /**
     * FXN26
     * Mika
     * HTML refer to FXN25
     */
    @Test
    public void FXN26_Mika() {
        String newTab;
        String topic = "Business";
        String category = "Markets";
        String pageToChoose = "Retail";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("menu-" + topic.toLowerCase(Locale.ROOT))).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("search"))).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxbusiness.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@aria-label='header nav item " + category + "']//*[text()='" + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[contains(text(), '" + pageToChoose + "')]")));
    }

    /**
     * FXN27
     * Mika
     * HTML refer to FXN25
     */
    @Test
    public void FXN27_Mika() {
        String newTab;
        String topic = "Business";
        String category = "Lifestyle";
        String pageToChoose = "Travel";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("menu-" + topic.toLowerCase(Locale.ROOT))).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("search"))).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxbusiness.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@aria-label='header nav item " + category + "']//*[text()='" + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[contains(text(), '" + pageToChoose + "')]")));
    }

    /**
     * FXN28
     * Mika
     * HTML refer to FXN25
     */
    @Test
    public void FXN28_Mika() {
        String newTab;
        String topic = "Business";
        String category = "Real Estate";
        String pageToChoose = "Commercial";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("menu-" + topic.toLowerCase(Locale.ROOT))).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("search"))).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxbusiness.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@aria-label='header nav item " + category + "']//*[text()='" + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[contains(text(), '" + pageToChoose + "')]")));
    }

    /**
     * FXN29
     * Mika
     * HTML refer to FXN25
     */
    @Test
    public void FXN29_Mika() {
        String newTab;
        String topic = "Business";
        String category = "Tech";
        String pageToChoose = "Space";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("menu-" + topic.toLowerCase(Locale.ROOT))).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("search"))).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxbusiness.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@aria-label='header nav item " + category + "']//*[text()='" + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[contains(text(), '" + pageToChoose + "')]")));
    }

    /**
     * FXN30
     * Mika
     * HTML refer to FXN25
     */
    @Test
    public void FXN30_Mika() {
        String newTab;
        String topic = "Business";
        String category = "Shows";
        String pageToChoose = "Mornings with Maria";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("menu-" + topic.toLowerCase(Locale.ROOT))).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("search"))).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxbusiness.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@aria-label='header nav item " + category + "']//*[text()='" + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[contains(text(), '" + pageToChoose + "')]")));
    }

    /**
     * FXN31
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31
     */
    @Test
    public void FXN31_Mika() {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[]{"Our Brands", "Life At FOX", "Internships", "Job Search", "My Account"};

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());
    }

    /**
     * FXN32
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31
     */
    @Test
    public void FXN32_Mika() {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[]{"Our Brands", "Life At FOX", "Internships", "Job Search", "My Account"};
        String itemToChoose = "Our Brands";
        String optionInItem = "Fox Corporation";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());

        driver.findElement(By.xpath("//a[@title='" + itemToChoose + "']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='" + itemToChoose + "']")));

        try {
            driver.findElement(By.xpath("//*[@alt='close']")).click();
        } catch (Exception ignored) {

        }

        element = driver.findElement(By.xpath("//*[contains(@class, 'sectionNav') and text()='" + optionInItem + "']"));
        action.moveToElement(element).perform();
        element.click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='View Jobs']")));
        action.moveToElement(element).perform();
        element.click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));
        assertTrue(Integer.parseInt(driver.findElement(By.id("jobsCount")).getText()) >= 1);
    }

    /**
     * FXN33
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31
     */
    @Test
    public void FXN33_Mika() throws InterruptedException {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[]{"Our Brands", "Life At FOX", "Internships", "Job Search", "My Account"};
        String itemToChoose = "Job Search";
        String textToType = "Sales";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[@aria-label='search foxnews.com']")));
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());

        driver.findElement(By.xpath("//a[@title='" + itemToChoose + "']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='" + itemToChoose + "']")));

        try {
            driver.findElement(By.xpath("//*[@alt='close']")).click();
        } catch (Exception ignored) {
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.id("keywordSearch"))).sendKeys(textToType);
        driver.findElement(By.xpath("//*[@id='filterButton' and @value='Go']")).click();

        Thread.sleep(2000);
        assertTrue(Integer.parseInt(driver.findElement(By.id("jobsCount")).getText()) >= 1);
    }

    /**
     * FXN34
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31
     */
    @Test
    public void FXN34_Mika() throws InterruptedException {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[]{"Our Brands", "Life At FOX", "Internships", "Job Search", "My Account"};
        String itemToChoose = "Job Search";
        String checkboxToSelect = "Administrative";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());

        driver.findElement(By.xpath("//a[@title='" + itemToChoose + "']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='" + itemToChoose + "']")));

        try {
            driver.findElement(By.xpath("//*[@alt='close']")).click();
        } catch (Exception ignored) {
        }

        element = driver.findElement(By.id(checkboxToSelect + "_checkbox"));
        action.moveToElement(element).click().perform();
        wait.until(ExpectedConditions.elementToBeSelected(element));

        Thread.sleep(2000);
        assertTrue(Integer.parseInt(driver.findElement(By.id("jobsCount")).getText()) >= 1);
    }

    /**
     * FXN35
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31
     */
    @Test
    public void FXN35_Mika() throws InterruptedException {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[]{"Our Brands", "Life At FOX", "Internships", "Job Search", "My Account"};
        String itemToChoose = "Job Search";
        String[] checkboxesToSelect = new String[]{"Fox Sports", "India"};

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());

        driver.findElement(By.xpath("//a[@title='" + itemToChoose + "']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='" + itemToChoose + "']")));

        try {
            driver.findElement(By.xpath("//*[@alt='close']")).click();
        } catch (Exception ignored) {

        }

        for (String checkbox : checkboxesToSelect) {
            element = driver.findElement(By.id(checkbox + "_checkbox"));
            action.moveToElement(element).click().perform();
            Thread.sleep(2000);
        }

        wait.until(ExpectedConditions.textToBe(By.id("jobsCount"), "0"));
        element = driver.findElement(By.linkText("New Job Search"));
        assertTrue(element.isDisplayed());

        action.moveToElement(element).click().perform();

        for (String checkbox : checkboxesToSelect) {
            element = driver.findElement(By.id(checkbox + "_checkbox"));
            wait.until(ExpectedConditions.elementSelectionStateToBe(element, false));
        }
    }

    /**
     * FXN36
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31, FXN36
     */
    @Test
    public void FXN36_Mika() {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[]{"Our Brands", "Life At FOX", "Internships", "Job Search", "My Account"};
        String itemToChoose = "Internships";
        String newTab;

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());

        driver.findElement(By.xpath("//a[@title='" + itemToChoose + "']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='" + itemToChoose + "']")));

        try {
            driver.findElement(By.xpath("//*[@alt='close']")).click();
        } catch (Exception ignored) {

        }

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Search for Internships']")));
        action.moveToElement(element).click().perform();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));
        assertTrue(Integer.parseInt(driver.findElement(By.id("jobsCount")).getText()) >= 1);
        assertTrue(driver.findElement(By.id("Internships_checkbox")).isSelected());
    }

    /**
     * FXN37
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31, FXN37
     */
    @Test
    public void FXN37_Mika() {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[]{"Our Brands", "Life At FOX", "Internships", "Job Search", "My Account"};
        String itemToChoose = "My Account";
        String emailAddress = "qa.tries.123@gmail.com";
        String password = "Qa@123456789";
        String newTab;

        driver.close();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        try {
            driver.findElement(By.xpath("//*[@aria-label='close']")).click();
        } catch (Exception ignored) {

        }

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        wait.until(ExpectedConditions.elementToBeClickable
                        (By.xpath("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")))
                .click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());

        driver.findElement(By.xpath("//a[@title='" + itemToChoose + "']")).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()='FOX Careers TERMS OF USE AGREEMENTS']")));

        try {
            driver.findElement(By.xpath("//*[@alt='close']")).click();
        } catch (Exception ignored) {

        }

        assertTrue(driver.findElement(By.xpath("//label[text()='I have read and understood the ']")).isDisplayed());
        assertTrue(driver.findElement(By.linkText("Privacy Policy")).isDisplayed());

        driver.findElement(By.name("PrivacyPolicyCheckbox")).click();
        driver.findElement(By.xpath("//*[@value='Submit']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-automation-id='email']")));
        assertTrue(driver.findElement(By.xpath("//*[@data-automation-id='password']")).isDisplayed());

        driver.findElement(By.xpath("//*[@data-automation-id='createAccountLink']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Create Account']")));

        driver.findElement(By.xpath("//*[@data-automation-id='email']")).sendKeys(emailAddress);
        driver.findElement(By.xpath("//*[@data-automation-id='password']")).sendKeys(password);
        driver.findElement(By.xpath("//*[@data-automation-id='verifyPassword']")).sendKeys(password);

        driver.findElement(By.xpath("//*[@aria-label='Create Account']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2/*[@title='Candidate Home']")));
        driver.findElement(By.xpath("//*[text()='" + emailAddress + "']")).click();

        element = driver.findElement(By.xpath("//*[@aria-label='Account Settings']"));
        assertTrue(element.isDisplayed());

        element = driver.findElement(By.xpath("//*[@aria-label='Sign Out']"));
        assertTrue(element.isDisplayed());
        element.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Sign In']")));
    }

    /**
     * FXN38
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31, FXN37 - careers_my_account_page.html,
     * careers_sign_in_page.html, careers_candidate_home_page.html, FXN38
     */
    @Test
    public void FXN38_Mika() {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[]{"Our Brands", "Life At FOX", "Internships", "Job Search", "My Account"};
        String itemToChoose = "My Account";
        String emailAddress = "qa.tries.123@gmail.com";
        String password = "Qa@123456789";
        String newTab;
        String jobsNumString;
        int jobsNumInt;

        driver.close();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 15);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        try {
            driver.findElement(By.xpath("//*[@aria-label='close']")).click();
        } catch (Exception ignored) {

        }

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        wait.until(ExpectedConditions.elementToBeClickable
                        (By.xpath("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")))
                .click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());

        driver.findElement(By.xpath("//a[@title='" + itemToChoose + "']")).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()='FOX Careers TERMS OF USE AGREEMENTS']")));

        try {
            driver.findElement(By.xpath("//*[@alt='close']")).click();
        } catch (Exception ignored) {

        }

        assertTrue(driver.findElement(By.xpath("//label[text()='I have read and understood the ']")).isDisplayed());
        assertTrue(driver.findElement(By.linkText("Privacy Policy")).isDisplayed());

        driver.findElement(By.name("PrivacyPolicyCheckbox")).click();
        driver.findElement(By.xpath("//*[@value='Submit']")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-automation-id='email']")));
        element.sendKeys(emailAddress);
        element = driver.findElement(By.xpath("//*[@data-automation-id='password']"));
        element.sendKeys(password);
        driver.findElement(By.xpath("//*[@aria-label='Sign In']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()='" + emailAddress + "']")));

        driver.findElement(By.xpath("//button[text()='Search for Jobs']")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[@aria-label='Search Results']//*[contains(text(), 'Results')]")));
        jobsNumString = element.getText();
        jobsNumInt = Integer.parseInt(jobsNumString.substring(0, jobsNumString.indexOf(' ')));
        assertTrue(jobsNumInt >= 1);
    }

    /**
     * FXN39
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31 - search_menu.html, FXN39
     */
    @Test
    public void FXN39_Mika() {
        String category = "About";
        String pageToChoose = "Media Relations";
        String[] buttons = new String[]{"Home", "Press Releases", "Media Contacts", "FAQ", "Fox Business", "Help Center"};
        String buttonToPress = "Help Center";
        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        element = driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']"));
        action.moveToElement(element).click().perform();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[text()='Media Contacts']")));

        for (String button : buttons)
            assertTrue(driver.findElement(By.linkText(button)).isDisplayed());

        driver.findElement(By.linkText(buttonToPress)).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Search']")));
        assertTrue(driver.findElement(By.linkText("Submit a request")).isDisplayed());
    }

    /**
     * FXN40
     * Mika
     * HTML refer to FXN25 - home_page.html
     */
    @Test
    public void FXN40_Mika() {
        String[] articleCategories = new String[]{"U.S.", "World", "Politics", "Entertainment", "Business",
                "Lifestyle", "Science", "Tech", "Health", "TV", "About", "Other"};
        List<String> haveNoLink = Arrays.asList("About", "Other");
        String blueRGB = "rgba(0, 51, 102, 1)";

        driver.get("https://www.foxnews.com/");

        JavascriptExecutor js = (JavascriptExecutor) driver;

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        element = driver.findElement(By.className("site-footer"));
        assertTrue((Boolean) js.executeScript
                ("var elem = arguments[0],                 " +
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
        assertEquals(blueRGB, element.getCssValue("background-color"));

        for (String category : articleCategories) {
            if (haveNoLink.contains(category))
                assertTrue(driver.findElement(By.xpath("//footer//h6[text()='" + category + "']")).isDisplayed());
            else
                assertTrue(driver.findElement(By.xpath("//footer//a[text()='" + category + "']")).isDisplayed());
        }
    }

    /**
     * FXN41
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN41
     */
    @Test
    public void FXN41_Mika() {
        String[] articleCategories = new String[]{"U.S.", "World", "Politics", "Entertainment", "Business",
                "Lifestyle", "Science", "Tech", "Health", "TV", "About", "Other"};
        List<String> haveNoLink = Arrays.asList("About", "Other");
        int loopNum = 3;
        String[] categoriesToChoose = new String[]{"Health", "Science", "World"};
        String[] articlesToChoose = new String[]{"Coronavirus", "Wild Nature", "Disasters"};
        String[] titlesToCheck = new String[]{"Coronavirus", "Wild Nature".toUpperCase(Locale.ROOT), "Disasters".toUpperCase(Locale.ROOT)};
        String blueRGB = "rgba(0, 51, 102, 1)";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        for (int i = 0; i < loopNum; i++) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

            element = driver.findElement(By.className("site-footer"));
            assertTrue((Boolean) js.executeScript
                    ("var elem = arguments[0],                 " +
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
            assertEquals(blueRGB, element.getCssValue("background-color"));

            for (String category : articleCategories) {
                if (haveNoLink.contains(category))
                    assertTrue(driver.findElement(By.xpath("//footer//h6[contains(text(), '" + category + "')]")).isDisplayed());
                else
                    assertTrue(driver.findElement(By.xpath("//footer//a[text()='" + category + "']")).isDisplayed());
            }

            driver.findElement(By.xpath
                    ("//footer//*[@aria-label='" + categoriesToChoose[i] + " - " + articlesToChoose[i] + "']")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='" + titlesToCheck[i] + "']")));
        }
    }

    /**
     * FXN42
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN42
     */
    @Test
    public void FXN42_Mika() throws InterruptedException {
        String[] articleCategories = new String[]{"U.S.", "World", "Politics", "Entertainment", "Business",
                "Lifestyle", "Science", "Tech", "Health", "TV", "About", "Other"};
        List<String> haveNoLink = Arrays.asList("About", "Other");
        String categoryToChoose = "Other";
        String articleToChoose = "Newsletters";
        String titleToCheck = "Newsletter Subscriptions";
        String blueRGB = "rgba(0, 51, 102, 1)";
        String emailAddress = "qa.tries.123@gmail.com";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        element = driver.findElement(By.className("site-footer"));
        assertTrue((Boolean) js.executeScript
                ("var elem = arguments[0],                 " +
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
        assertEquals(blueRGB, element.getCssValue("background-color"));

        for (String category : articleCategories) {
            if (haveNoLink.contains(category))
                assertTrue(driver.findElement(By.xpath("//footer//h6[contains(text(), '" + category + "')]")).isDisplayed());
            else
                assertTrue(driver.findElement(By.xpath("//footer//a[text()='" + category + "']")).isDisplayed());
        }

        driver.findElement(By.xpath
                ("//footer//*[@aria-label='" + categoryToChoose + " - " + articleToChoose + "']")).click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='" + titleToCheck + "']")));

        element = driver.findElement(By.xpath("//li[1]//*[@class='button subscribe']//a"));
        action.moveToElement(element).click().perform();

        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//li[1]//*[@name='email']")));
        assertTrue(element.isDisplayed());
        element.sendKeys(emailAddress);
        driver.findElement(By.xpath("//li[1]//*[@class='button enter']//a")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//li[1]//*[contains(text(), 'successfully')]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//li[1]//*[text()='Subscribed']")));
    }

    /**
     * FXN43
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31 - search_menu.html, FXN41 - an_article_page.html, FXN43
     */
    @Test
    public void FXN43_Mika() {
        String category = "U.S.";
        String pageToChoose = "Crime";
        String blueRGB = "rgba(0, 51, 102, 1)";
        String newTab;

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("menu-more")).click();
        assertTrue(driver.findElement(By.xpath("//header//*[@class='section-nav']")).isDisplayed());
        driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[text()='" + pageToChoose.toUpperCase(Locale.ROOT) + "']")));
        driver.navigate().back();

        driver.findElement(By.className("js-focus-search")).click();
        assertTrue(driver.findElement(By.xpath("//header//*[@class='section-nav']")).isDisplayed());
        driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).sendKeys(pageToChoose);
        driver.findElement(By.xpath("//*[@aria-label='submit search']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='num-found']")));
        driver.findElements(By.xpath("//a[text()='category']")).get(0).click();
        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[text()='" + pageToChoose.toUpperCase(Locale.ROOT) + "']")));
        newTab = (String) driver.getWindowHandles().toArray()[0];
        driver.switchTo().window(newTab);
        driver.navigate().back();

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        element = driver.findElement(By.className("site-footer"));
        assertTrue((Boolean) js.executeScript
                ("var elem = arguments[0],                 " +
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
        assertEquals(blueRGB, element.getCssValue("background-color"));
        driver.findElement(By.xpath
                ("//footer//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[text()='" + pageToChoose.toUpperCase(Locale.ROOT) + "']")));
    }

    /**
     * FXN44
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31 - search_menu.html, FXN44
     */
    @Test
    public void FXN44_Mika() throws InterruptedException {
        String category = "Other";
        String pageToChoose = "Fox News Shop";
        int myCartCount;

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='field search']")));
        assertTrue(driver.findElement(By.xpath("//a//*[text()='My Cart']")).isDisplayed());

        driver.findElement(By.xpath("//*[text()='Patriotic Collection']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='toolbar-number'][2]")));
        assertTrue(Integer.parseInt(driver.findElements(By.xpath("//*[@class='toolbar-number'][2]")).get(0).getText()) > 1);

        if (driver.findElement(By.className("counter-number")).getText().equals(""))
            myCartCount = 0;
        else
            myCartCount = Integer.parseInt(driver.findElement(By.className("counter-number")).getText());
        element = driver.findElement(By.xpath("//*[@title='Add to Cart']"));
        action.moveToElement(element).click().perform();
        Thread.sleep(2000);
        assertEquals(myCartCount + 1, Integer.parseInt(driver.findElement(By.className("counter-number")).getText()));
        myCartCount++;

        driver.findElement(By.xpath("//a//*[text()='My Cart']")).click();
        if (myCartCount == 1)
            assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'Item in Cart')]")).isDisplayed());
        else
            assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'Items in Cart')]")).isDisplayed());
    }

    /**
     * FXN45
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31 - search_menu.html, FXN44, FXN45
     */
    @Test
    public void FXN45_Mika() throws InterruptedException {
        String category = "Other";
        String pageToChoose = "Fox News Shop";
        String totalPrice;
        int myCartCount;

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='field search']")));
        assertTrue(driver.findElement(By.xpath("//a//*[text()='My Cart']")).isDisplayed());

        driver.findElement(By.xpath("//*[text()='Patriotic Collection']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='toolbar-number'][2]")));
        assertTrue(Integer.parseInt(driver.findElements(By.xpath("//*[@class='toolbar-number'][2]")).get(0).getText()) > 1);

        if (driver.findElement(By.className("counter-number")).getText().equals(""))
            myCartCount = 0;
        else
            myCartCount = Integer.parseInt(driver.findElement(By.className("counter-number")).getText());
        element = driver.findElement(By.xpath("//*[@title='Add to Cart']"));
        action.moveToElement(element).click().perform();
        Thread.sleep(2000);
        assertEquals(myCartCount + 1, Integer.parseInt(driver.findElement(By.className("counter-number")).getText()));

        driver.findElement(By.xpath("//a//*[text()='My Cart']")).click();
        element = driver.findElement(By.xpath("//*[text()='View and Edit Cart']"));
        element.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1//*[text()='Shopping Cart']")));
        assertEquals(1, driver.findElements(By.xpath("//*[@class='cart item']")).size());
        assertEquals(1, Integer.parseInt(driver.findElement(By.xpath("//*[@class='control qty']//input")).getAttribute("value")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Order Total']")));
        Thread.sleep(1000);

        totalPrice = driver.findElement(By.xpath("//*[@data-th='Order Total']//*[@class='price']")).getText();
        element = driver.findElement(By.xpath("//*[@title='Increase the quantity']"));
        element.click();
        element.click();
        driver.findElement(By.xpath("//*[text()='Update Shopping Cart']")).click();
        Thread.sleep(3000);
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[@data-th='Order Total']//*[@class='price']")));
        assertNotEquals(totalPrice, element.getText());

        driver.findElement(By.xpath("//*[contains(@class, 'action-delete')]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[@class='cart-empty']//*[text()='You have no items in your shopping cart.']")));
        assertEquals("", driver.findElement(By.className("counter-number")).getText());
    }

    /**
     * FXN46
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN46
     */
    @Test
    public void FXN46_Mika() {
        String linkToClick = "New Terms of Use";
        String titleToCheck = "Fox News Network, LLC Terms of Use Agreement";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        element = driver.findElement(By.className("site-footer"));
        assertTrue((Boolean) js.executeScript
                ("var elem = arguments[0],                 " +
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


        driver.findElement(By.xpath("//footer//a[text()='" + linkToClick + "']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='" + titleToCheck + "']")));

        element = driver.findElement(By.xpath
                ("//*[text()='I HAVE READ THIS AGREEMENT AND AGREE TO ALL OF THE PROVISIONS CONTAINED ABOVE.']"));
        action.moveToElement(element).perform();
        assertTrue((Boolean) js.executeScript
                ("var elem = arguments[0],                 " +
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
    }

    /**
     * FXN47
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN47
     */
    @Test
    public void FXN47_Mika() {
        String linkToClick = "New Privacy Policy";
        String titleToCheck = "Privacy Policy";
        String[] contents = new String[]{"Scope and Application", "Collection of Information", "Use and Disclosure",
                "Security", "User Access and Control", "Other Important Information", "Contact Us",
                "California Consumer Privacy Act Notice"};

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        element = driver.findElement(By.className("site-footer"));
        assertTrue((Boolean) js.executeScript
                ("var elem = arguments[0],                 " +
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


        driver.findElement(By.xpath("//footer//a[text()='" + linkToClick + "']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='" + titleToCheck + "']")));

        for (String content : contents)
            assertTrue(driver.findElement(By.xpath("//strong[text()='" + content + "']")).isDisplayed());
    }

    /**
     * FXN48
     * Mika
     * HTML refer to FXN25 - home_page.html
     */
    @Test
    public void FXN48_Mika() {
        String socialNetwork = "Facebook";
        String urlToCheck = "https://www.facebook.com/FoxNews";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        element = driver.findElement(By.className("site-footer"));
        assertTrue((Boolean) js.executeScript
                ("var elem = arguments[0],                 " +
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


        driver.findElement(By.xpath("//footer//*[@aria-label='" + socialNetwork + "']")).click();
        wait.until(ExpectedConditions.urlToBe(urlToCheck));
    }

    /**
     * FXN49
     * Mika
     * HTML refer to FXN25 - home_page.html
     */
    @Test
    public void FXN49_Mika() {
        String socialNetwork = "Instagram";
        String urlToCheck = "https://www.instagram.com/foxnews/";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        element = driver.findElement(By.className("site-footer"));
        assertTrue((Boolean) js.executeScript
                ("var elem = arguments[0],                 " +
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


        driver.findElement(By.xpath("//footer//*[@aria-label='" + socialNetwork + "']")).click();
        wait.until(ExpectedConditions.urlToBe(urlToCheck));
    }

    /**
     * FXN50
     * Mika
     * HTML refer to FXN25 - home_page.html
     */
    @Test
    public void FXN50_Mika() {
        String socialNetwork = "Twitter";
        String urlToCheck = "https://twitter.com/foxnews";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        element = driver.findElement(By.className("site-footer"));
        assertTrue((Boolean) js.executeScript
                ("var elem = arguments[0],                 " +
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


        driver.findElement(By.xpath("//footer//*[@aria-label='" + socialNetwork + "']")).click();
        wait.until(ExpectedConditions.urlToBe(urlToCheck));
    }
}
