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
}
