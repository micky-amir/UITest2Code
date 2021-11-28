package websites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static org.junit.Assert.*;

public class Thesaurus {

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
     * HTML Refers to SK_1
     */
    @Test
    public void SK_1_Tamar() {
        driver.get("https://www.thesaurus.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Sign up for an account']"))).click();
        assertTrue(driver.findElement(By.cssSelector("[aria-label='sign up']")).isDisplayed());
        List<String> inputNames = new ArrayList<>(Arrays.asList("firstName", "lastName", "email", "password"));
        for (String name : inputNames) {
            assertTrue(driver.findElement(By.cssSelector("[name='" + name + "']")).isDisplayed());
        }
    }

    /**
     * SK_2
     * Tamar
     * HTML Refers to SK_1
     */
    @Test
    public void SK_2_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//*[@data-testid='wotd']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        assertTrue(element.findElement(By.tagName("a")).isDisplayed());
        String date = element.findElement(By.cssSelector(".colored-card div:nth-child(3) div:nth-child(2)")).getText();
        LocalDate currentDate = LocalDate.now();
        String expectedDate = currentDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH)).toUpperCase();
        assertEquals(expectedDate, date);
    }

    /**
     * SK_3
     * Tamar
     * HTML Refers to SK_1
     */
    @Test
    public void SK_3_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//h2[text()='BROWSE THESAURUS.COM']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        List<WebElement> elementsList = element.findElements(By.xpath(".//ancestor::section/descendant::ul[@data-linkid]/li/a"));
        for (int i = 0; i < elementsList.size(); i++) {
            String expected = (char) (i + 64) + String.valueOf((char) (i + 96));
            if (i == 0)
                expected = "#";
            assertEquals(expected, elementsList.get(i).getText());
        }
    }

    /**
     * SK_4
     * Tamar
     * HTML Refers to SK_1, SK_4
     */
    @Test
    public void SK_4_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//h2[text()='BROWSE THESAURUS.COM']/ancestor::section"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element.findElement(By.xpath(".//descendant::a[text()='#']")).click();
        assertTrue(driver.findElement(By.tagName("h1")).getText().contains("NUMERALS & DIACRITICS"));
        driver.findElement(By.xpath("//a[text()='0']")).click();
        assertEquals("0", driver.findElement(By.tagName("h1")).getText());
        assertEquals("0", driver.findElement(By.cssSelector("[type='search']")).getAttribute("value"));
    }

    /**
     * SK_5
     * Tamar
     * HTML Refers to SK_1
     */
    @Test
    public void SK_5_Tamar() {
        driver.get("https://www.thesaurus.com/");
        assertEquals("DICTIONARY.COM", driver.findElement(By.id("dictionary-nav-tab")).getText());
        assertEquals("THESAURUS.COM", driver.findElement(By.id("thesaurus-nav-tab")).getText());
        List<WebElement> navTitleElements = driver.findElements(
                By.cssSelector("[aria-label='Site Navigation'] ul > li[data-current-active] > button"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("MEANINGS", "GAMES", "LEARN", "WRITING", "WORD OF THE DAY"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navTitleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * SK_6
     * Tamar
     * HTML Refers to SK_1
     */
    @Test
    public void SK_6_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='MEANINGS']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//../descendant::ul/../..")).getCssValue("display"));
        List<WebElement> navTitleElements = element.findElements(By.xpath(".//../descendant::ul/li/a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList(
                "Emoji", "Slang", "Acronyms", "Pop Culture", "Memes", "Gender And Sexuality", "Mixed-Up Meanings"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navTitleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * SK_7
     * Tamar
     * HTML Refers to SK_1
     */
    @Test
    public void SK_7_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='GAMES']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//../descendant::ul/../..")).getCssValue("display"));
        List<WebElement> navTitleElements = element.findElements(By.xpath(".//../descendant::ul/li/a"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("Word Puzzle", "Quizzes", "Crossword Solver", "Scrabble Word Finder",
                        "Words With Friends Cheat", "Daily Crossword Puzzle"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navTitleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * SK_8
     * Tamar
     * HTML Refers to SK_1
     */
    @Test
    public void SK_8_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='LEARN']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//../descendant::ul/../..")).getCssValue("display"));
        List<WebElement> navTitleElements = element.findElements(By.xpath(".//../descendant::ul/li/a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Word Lists", "Online Tutors", "New Words",
                "Trending Words", "All About English", "Science And Technology", "Literature And Arts"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navTitleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * SK_9
     * Tamar
     * HTML Refers to SK_1
     */
    @Test
    public void SK_9_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='WRITING']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//../descendant::ul/../..")).getCssValue("display"));
        List<WebElement> navTitleElements = element.findElements(By.xpath(".//../descendant::ul/li/a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList(
                "Grammar Coach", "Writing Prompts", "Grammar 101", "Writing Tips", "Ways To Say It Better"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navTitleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * SK_10
     * Tamar
     * HTML Refers to SK_1
     */
    @Test
    public void SK_10_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='WORD OF THE DAY']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//../descendant::ul/../..")).getCssValue("display"));
        List<WebElement> navTitleElements = element.findElements(By.xpath(".//../descendant::ul/li/a"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("Word Of The Day", "Synonym Of The Day", "Word Of The Year"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navTitleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * SK_11
     * Tamar
     * HTML Refers to SK_1
     */
    @Test
    public void SK_11_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.id("active-synonyms"));
        element.click();
        assertNotEquals("max-height: 0px;",
                element.findElement(By.xpath("//*[@id='definitions-filter']/..")).getAttribute("style"));
        assertTrue(element.findElement(By.xpath("//*[text()='DEFINITIONS']")).isDisplayed());
    }

    /**
     * SK_12
     * Tamar
     * HTML Refers to SK_12
     */
    @Test
    public void SK_12_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//h2[text()='MOST SEARCHED WORDS']/ancestor::section/following-sibling::*"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        WebElement inputElement = element.findElement(By.tagName("input"));
        assertEquals("Enter your email", inputElement.getAttribute("aria-label"));
        inputElement.sendKeys("testmanqa1@gmail.com");
        actions.moveToElement(element.findElement(By.xpath("./following-sibling::*"))).perform();
        actions.moveToElement(element.findElement(By.tagName("button"))).click().perform();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        assertEquals("You've been subscribed. New words are on the way!",
                wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(element,
                        By.xpath("//*[contains(text(), 'subscribed')]"))).getText());
    }

    /**
     * SK_13
     * Tamar
     * HTML Refers to SK_1
     */
    @Test
    public void SK_13_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//h2[text()='MOST SEARCHED WORDS']/ancestor::section/following-sibling::*[2]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
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
                , driver.findElement(By.xpath("//h2[text()='GRAMMAR & WRITING TIPS']"))));
        List<WebElement> linkElements = driver.findElements(By.cssSelector("[data-testid='thumbnail-card'] > a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("ways-to-say", "plagiarism", "gerund"));
        for (int i = 0; i < linkElements.size(); i++) {
            assertTrue(linkElements.get(i).getAttribute("href").contains(expectedTitles.get(i)));
        }
    }

    /**
     * SK_14
     * Tamar
     * HTML Refers to SK_1, SK_14
     */
    @Test
    public void SK_14_Tamar() {
        driver.get("https://www.thesaurus.com/");
        driver.findElement(By.id("searchbar_input")).sendKeys("acrobat");
        driver.findElement(By.id("search-submit")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        assertEquals("SYNONYMS FOR acrobat",
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("[data-testid='word-grid-section-heading']"))).getText());
        assertTrue(driver.findElement(By.cssSelector("#meanings [data-testid='word-grid-container'] li")).isDisplayed());
    }

    /**
     * SK_16
     * Tamar
     * HTML Refers to SK_1, SK_14
     */
    @Test
    public void SK_16_Tamar() {
        driver.get("https://www.thesaurus.com/");
        driver.findElement(By.id("searchbar_input")).sendKeys("acrobat");
        driver.findElement(By.id("search-submit")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        assertEquals("SYNONYMS FOR acrobat",
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("[data-testid='word-grid-section-heading']"))).getText());
        assertTrue(driver.findElement(By.cssSelector("#meanings [data-testid='word-grid-container'] li")).isDisplayed());
        driver.findElement(By.cssSelector("[aria-label='save word']")).click();
        assertTrue(driver.findElement(By.cssSelector("[aria-label='sign up']")).isDisplayed());
    }

    /**
     * SK_17
     * Tamar
     * HTML Refers to SK_1, SK_17
     */
    @Test
    public void SK_17_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.id("active-synonyms"));
        element.click();
        element.findElement(By.xpath("//*[text()='DEFINITIONS']")).click();
        assertTrue(driver.findElement(By.id("active-definitions")).isDisplayed());
        driver.findElement(By.id("searchbar_input")).sendKeys("acrobat");
        driver.findElement(By.id("search-submit")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.attributeContains(By.id("dictionary-nav-tab"), "class", "header-tab-active"));
        assertEquals("a skilled performer of gymnastic feats, as walking on a tightrope or swinging on a trapeze.",
                driver.findElement(By.cssSelector("[value='1']")).getText());
    }

    /**
     * SK_18
     * Tamar
     * HTML Refers to SK_1, SK_14, SK_17
     */
    @Test
    public void SK_18_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.id("active-synonyms"));
        element.click();
        element.findElement(By.xpath("//*[text()='DEFINITIONS']")).click();
        assertTrue(driver.findElement(By.id("active-definitions")).isDisplayed());
        driver.findElement(By.id("searchbar_input")).sendKeys("acrobat");
        driver.findElement(By.id("search-submit")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.attributeContains(By.id("dictionary-nav-tab"), "class", "header-tab-active"));
        assertEquals("a skilled performer of gymnastic feats, as walking on a tightrope or swinging on a trapeze.",
                driver.findElement(By.cssSelector("[value='1']")).getText());

        driver.findElement(By.xpath("//*[contains(text(), 'See')]")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.attributeContains(By.id("thesaurus-nav-tab"), "class", "header-tab-active"));
        List<WebElement> synonymsElements = driver.findElements(By.cssSelector("#meanings [data-testid='word-grid-container'] a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("clown", "dancer", "gymnast", "performer", "tumbler"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : synonymsElements) {
            if (element.getCssValue("background-color").equals("rgba(244, 71, 37, 1)"))
                actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * SK_19
     * Tamar
     * HTML Refers to SK_1, SK_19
     */
    @Test
    public void SK_19_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.tagName("footer"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        element.findElement(By.id("facebook")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.attributeToBe(By.tagName("html"), "id", "facebook"));
        assertEquals("Thesaurus.com", driver.findElement(By.tagName("h1")).getText());
        assertEquals("@thesauruscom", driver.findElement(By.cssSelector("a[data-hover]")).getText());
    }

    /**
     * SK_20
     * Tamar
     * HTML Refers to SK_1, SK_20
     */
    @Test
    public void SK_20_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.tagName("footer"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        element.findElement(By.id("twitter")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Twitter, Inc.')]")));
        assertEquals("Thesaurus.com\n@thesauruscom",
                driver.findElement(By.cssSelector("[data-testid='UserName']")).getText());
    }

    /**
     * SK_21
     * Tamar
     * HTML Refers to SK_1, SK_21
     */
    @Test
    public void SK_21_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.tagName("footer"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        element.findElement(By.id("instagram")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@alt='Instagram']")));
        assertEquals("Thesaurus.com", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_22
     * Tamar
     * HTML Refers to SK_1, SK_22
     */
    @Test
    public void SK_22_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.tagName("footer"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        element.findElement(By.id("pinterest")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.textToBe(By.tagName("h2"), "Pinterest"));
        assertEquals("Thesaurus.com", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_23
     * Tamar
     * HTML Refers to SK_23
     */
    @Test
    public void SK_23_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.tagName("footer"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        WebElement inputElement = element.findElement(By.tagName("input"));
        assertEquals("Enter your email", inputElement.getAttribute("aria-label"));
        inputElement.sendKeys("testmanqa1@gmail.com");
        assertEquals("Valid email address", driver.findElement(By.cssSelector("[opacity='1']")).getText());
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        actions.moveToElement(element.findElement(By.tagName("button"))).click().perform();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        assertEquals("You've been subscribed. New words are on the way!",
                wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(element,
                        By.xpath("//*[contains(text(), 'subscribed')]"))).getText());
    }

    /**
     * SK_24
     * Tamar
     * HTML Refers to SK_23
     */
    @Test
    public void SK_24_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.tagName("footer"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        WebElement inputElement = element.findElement(By.tagName("input"));
        assertEquals("Enter your email", inputElement.getAttribute("aria-label"));
        inputElement.sendKeys("abcd123");
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        actions.moveToElement(element.findElement(By.tagName("button"))).click().perform();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        assertEquals("Uh oh! We spotted an email error. Please re-enter!",
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[opacity='1']"))).getText());
    }

    /**
     * SK_25
     * Tamar
     * HTML Refers to SK_1, SK_25
     */
    @Test
    public void SK_25_Tamar() {
        WebDriverManager.edgedriver().setup();
        EdgeDriver driver2 = new EdgeDriver();
        driver2.manage().window().maximize();
        driver2.get("https://www.thesaurus.com/");
        WebDriverWait wait = new WebDriverWait(driver2, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Sign up for an account']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='sign up']")));
        assertEquals("https://www.dictionary.com/sign-up?redirect=https://www.thesaurus.com/", driver2.getCurrentUrl());
        driver2.findElement(By.cssSelector("[name='firstName']")).sendKeys("test");
        driver2.findElement(By.cssSelector("[name='lastName']")).sendKeys("1");
        driver2.findElement(By.cssSelector("[name='email']")).sendKeys("tamar.gur@outlook.co.il");
        driver2.findElement(By.cssSelector("[name='password']")).sendKeys("Test123!");
        driver2.findElement(By.cssSelector("[aria-label='sign up']")).click();
        wait.until(ExpectedConditions.attributeContains(By.id("thesaurus-nav-tab"), "class", "header-tab-active"));
        driver2.findElement(By.xpath("//*[@data-account-icon-signedin]/..")).click();
        assertNotEquals("none", driver2.findElement(By.cssSelector("[data-access-menu]")).getCssValue("display"));
        assertEquals("tamar.gur@outlook.co.il", driver2.findElement(By.cssSelector("[data-email-display]")).getText());
        driver2.quit();
    }

    /**
     * SK_26
     * Tamar
     * HTML Refers to SK_1, SK_26
     */
    @Test
    public void SK_26_Tamar() {
        driver.get("https://www.thesaurus.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Sign up for an account']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Log In']"))).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h3"), "Welcome Back"));
        driver.findElement(By.cssSelector("[name='email']")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.cssSelector("[name='password']")).sendKeys("Test123!");
        driver.findElement(By.xpath("//button[text()='Log In']")).click();
        wait.until(ExpectedConditions.attributeContains(By.id("thesaurus-nav-tab"), "class", "header-tab-active"));

        driver.findElement(By.id("searchbar_input")).sendKeys("people");
        driver.findElement(By.id("search-submit")).click();
        assertEquals("SYNONYMS FOR people",
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("[data-testid='word-grid-section-heading']"))).getText());
        assertTrue(driver.findElement(By.cssSelector("#meanings [data-testid='word-grid-container'] li")).isDisplayed());

        element = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("[aria-label='save word']"))));
        element.click();
        wait.until(ExpectedConditions.attributeToBe(element, "aria-label", "delete word"));
        assertEquals("rgba(255, 182, 0, 1)", element.findElement(By.xpath("./*")).getCssValue("color"));
        driver.findElement(By.xpath("//*[@data-account-icon-signedin]/..")).click();
        driver.findElement(By.xpath("//*[@data-access-menu]//a[text()='Word Lists']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Favorites']"))).click();
        assertTrue(driver.findElement(By.xpath("//*[@data-testid='word-wrapper']/*[text()='people']")).isDisplayed());
    }

    /**
     * SK_27
     * Tamar
     * HTML Refers to SK_1, SK_26, SK_27
     */
    @Test
    public void SK_27_Tamar() {
        driver.get("https://www.thesaurus.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Sign up for an account']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Log In']"))).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h3"), "Welcome Back"));
        driver.findElement(By.cssSelector("[name='email']")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.cssSelector("[name='password']")).sendKeys("Test123!");
        driver.findElement(By.xpath("//button[text()='Log In']")).click();
        wait.until(ExpectedConditions.attributeContains(By.id("thesaurus-nav-tab"), "class", "header-tab-active"));

        driver.findElement(By.id("searchbar_input")).sendKeys("acrobat");
        driver.findElement(By.id("search-submit")).click();
        assertEquals("SYNONYMS FOR acrobat",
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("[data-testid='word-grid-section-heading']"))).getText());
        assertTrue(driver.findElement(By.cssSelector("#meanings [data-testid='word-grid-container'] li")).isDisplayed());

        element = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("[aria-label='save word']"))));
        element.click();
        wait.until(ExpectedConditions.attributeToBe(element, "aria-label", "delete word"));
        assertEquals("rgba(255, 182, 0, 1)", element.findElement(By.xpath("./*")).getCssValue("color"));
        driver.findElement(By.xpath("//*[@data-account-icon-signedin]/..")).click();
        driver.findElement(By.xpath("//*[@data-access-menu]//a[text()='Word Lists']")).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "My Lists"));
        driver.findElement(By.xpath("//button[text()='New List']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[text()='Create a new list']")));
        driver.findElement(By.cssSelector("[name='listName']")).sendKeys("test");
        driver.findElement(By.xpath("//*[text()='Select a Category']")).click();
        driver.findElement(By.xpath("//*[text()='General']")).click();
        driver.findElement(By.cssSelector("[name='listDescription']")).sendKeys("...");
        Actions actions = new Actions(driver);
        element = driver.findElements(By.cssSelector("input[type='checkbox']")).get(1);
        actions.moveToElement(element).click(element).perform();
        driver.findElement(By.cssSelector("[data-testid='CreateListCTA']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='test']")));
    }

    /**
     * SK_28
     * Tamar
     * HTML Refers to SK_1, SK_25, SK_28
     */
    @Test
    public void SK_28_Tamar() {
        driver.get("https://www.thesaurus.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Sign up for an account']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Log In']"))).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h3"), "Welcome Back"));
        driver.findElement(By.cssSelector("[name='email']")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.cssSelector("[name='password']")).sendKeys("Test123!");
        driver.findElement(By.xpath("//button[text()='Log In']")).click();
        wait.until(ExpectedConditions.attributeContains(By.id("thesaurus-nav-tab"), "class", "header-tab-active"));

        driver.findElement(By.xpath("//*[@data-account-icon-signedin]/..")).click();
        driver.findElement(By.xpath("//*[@data-access-menu]//a[text()='Word Lists']")).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "My Lists"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='kebab menu']"))).click();
        List<WebElement> menuButtonElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector(".floating-nav-enter-done > button")));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("Launch Flashcards", "Take a Multiple Choice Quiz", "Take a Spelling Quiz",
                        "Edit List Details", "Copy Link", "Delete List"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : menuButtonElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        menuButtonElements.get(3).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[text()='Edit list details']")));
        driver.findElement(By.xpath("//*[@for='listCategory']/../*[@tabindex]")).click();
        driver.findElement(By.xpath("//*[text()='Fun']")).click();
        driver.findElement(By.xpath("//button[text()='Save Changes']")).click();
    }

    /**
     * SK_29
     * Tamar
     * HTML Refers to SK_1, SK_25, SK_28
     */
    @Test
    public void SK_29_Tamar() {
        driver.get("https://www.thesaurus.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Sign up for an account']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Log In']"))).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h3"), "Welcome Back"));
        driver.findElement(By.cssSelector("[name='email']")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.cssSelector("[name='password']")).sendKeys("Test123!");
        driver.findElement(By.xpath("//button[text()='Log In']")).click();
        wait.until(ExpectedConditions.attributeContains(By.id("thesaurus-nav-tab"), "class", "header-tab-active"));

        driver.findElement(By.xpath("//*[@data-account-icon-signedin]/..")).click();
        driver.findElement(By.xpath("//*[@data-access-menu]//a[text()='Word Lists']")).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "My Lists"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='kebab menu']"))).click();
        List<WebElement> menuButtonElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector(".floating-nav-enter-done > button")));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("Launch Flashcards", "Take a Multiple Choice Quiz", "Take a Spelling Quiz",
                        "Edit List Details", "Copy Link", "Delete List"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : menuButtonElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        menuButtonElements.get(4).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Link Copied!']")));
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Object copiedData = clipboard.getData(DataFlavor.stringFlavor);
            driver.get(copiedData.toString());
            assertEquals("https://www.dictionary.com/learn/word-lists/general/JyYBLjenBKE", driver.getCurrentUrl());
        } catch (IOException | UnsupportedFlavorException e) {
            e.printStackTrace();
        }
    }

    /**
     * SK_30
     * Tamar
     * HTML Refers to SK_1, SK_25, SK_28, SK_30
     */
    @Test
    public void SK_30_Tamar() throws InterruptedException {
        driver.get("https://www.thesaurus.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Sign up for an account']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Log In']"))).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h3"), "Welcome Back"));
        driver.findElement(By.cssSelector("[name='email']")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.cssSelector("[name='password']")).sendKeys("Test123!");
        driver.findElement(By.xpath("//button[text()='Log In']")).click();
        wait.until(ExpectedConditions.attributeContains(By.id("thesaurus-nav-tab"), "class", "header-tab-active"));

        driver.findElement(By.xpath("//*[@data-account-icon-signedin]/..")).click();
        driver.findElement(By.xpath("//*[@data-access-menu]//a[text()='Word Lists']")).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "My Lists"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='kebab menu']"))).click();
        List<WebElement> menuButtonElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector(".floating-nav-enter-done > button")));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("Launch Flashcards", "Take a Multiple Choice Quiz", "Take a Spelling Quiz",
                        "Edit List Details", "Copy Link", "Delete List"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : menuButtonElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        menuButtonElements.get(5).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[text()='Delete List']")));
        assertTrue(driver.findElement(By.xpath("//button[@type='submit' and text()='Keep List']")).isDisplayed());
        element = driver.findElement(By.xpath("//button[@type='submit' and text()='Delete List']"));
        assertTrue(element.isDisplayed());
        element.click();
        Thread.sleep(2000);
        assertEquals(0, driver.findElements(By.xpath("//*[text()='test']")).size());
    }

    /**
     * SK_31
     * Tamar
     * HTML Refers to SK_1, SK_25, SK_26, SK_31
     */
    @Test
    public void SK_31_Tamar() {
        driver.get("https://www.thesaurus.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Sign up for an account']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Log In']"))).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h3"), "Welcome Back"));
        driver.findElement(By.cssSelector("[name='email']")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.cssSelector("[name='password']")).sendKeys("Test123!");
        driver.findElement(By.xpath("//button[text()='Log In']")).click();
        wait.until(ExpectedConditions.attributeContains(By.id("thesaurus-nav-tab"), "class", "header-tab-active"));

        driver.findElement(By.xpath("//*[@data-account-icon-signedin]/..")).click();
        assertNotEquals("none", driver.findElement(By.cssSelector("[data-access-menu]")).getCssValue("display"));
        List<WebElement> optionElements = driver.findElements(By.cssSelector("[data-access-menu] ul a"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("Word Lists", "Account Settings", "Subscriptions", "Help Center"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        assertEquals("Sign Out", driver.findElement(By.cssSelector("[data-access-menu] ul button")).getText());

        optionElements.get(1).click();
        driver.findElement(By.xpath("//*[@name='firstName']//following-sibling::button")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[text()='Update Your Name']")));
        element = driver.findElement(By.cssSelector("[name='newFirstName']"));
        assertTrue(element.isDisplayed());
        element.sendKeys("Alwana");
        element = driver.findElement(By.cssSelector("[name='newLastName']"));
        assertTrue(element.isDisplayed());
        element.sendKeys("1");
        driver.findElement(By.xpath("//*[text()='Update your name']")).click();
        assertEquals("Alwana", driver.findElement(By.cssSelector("[name='firstName']")).getAttribute("value"));
    }

    /**
     * SK_32
     * Tamar
     * HTML Refers to SK_1, SK_32
     */
    @Test
    public void SK_32_Tamar() {
        driver.get("https://www.thesaurus.com/");
        assertTrue(driver.findElement(By.id("active-synonyms")).isDisplayed());
        driver.findElement(By.id("search-submit")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        assertEquals("SYNONYMS FOR search",
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("[data-testid='word-grid-section-heading']"))).getText());
    }

    /**
     * SK_33
     * Tamar
     * HTML Refers to SK_1, SK_33
     */
    @Test
    public void SK_33_Tamar() {
        driver.get("https://www.thesaurus.com/");
        assertTrue(driver.findElement(By.id("active-synonyms")).isDisplayed());
        driver.findElement(By.id("searchbar_input")).sendKeys("golf");
        driver.findElement(By.id("search-submit")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        assertEquals("SYNONYMS FOR golf",
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("[data-testid='word-grid-section-heading']"))).getText());
        assertEquals("Thesaurus / golf", driver.findElement(By.cssSelector("[data-testid='breadcrumbs']")).getText());
    }

    /**
     * SK_34
     * Tamar
     * HTML Refers to SK_1, SK_25, SK_26, SK_34
     */
    @Test
    public void SK_34_Tamar() {
        driver.get("https://www.thesaurus.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Sign up for an account']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Log In']"))).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h3"), "Welcome Back"));
        driver.findElement(By.cssSelector("[name='email']")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.cssSelector("[name='password']")).sendKeys("Test123!");
        driver.findElement(By.xpath("//button[text()='Log In']")).click();
        wait.until(ExpectedConditions.attributeContains(By.id("thesaurus-nav-tab"), "class", "header-tab-active"));

        driver.findElement(By.xpath("//*[@data-account-icon-signedin]/..")).click();
        assertNotEquals("none", driver.findElement(By.cssSelector("[data-access-menu]")).getCssValue("display"));
        List<WebElement> optionElements = driver.findElements(By.cssSelector("[data-access-menu] ul a"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("Word Lists", "Account Settings", "Subscriptions", "Help Center"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        assertEquals("Sign Out", driver.findElement(By.cssSelector("[data-access-menu] ul button")).getText());

        optionElements.get(1).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='DELETE ACCOUNT']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[text()='DELETE ACCOUNT']")));
        assertTrue(driver.findElement(By.xpath("//button[text()='KEEP ACCOUNT']")).isDisplayed());
        element = driver.findElement(By.xpath("//button[text()='DELETE ACCOUNT']"));
        assertTrue(element.isDisplayed());
        element.click();
    }

    /**
     * SK_35
     * Tamar
     * HTML Refers to SK_1, SK_35
     */
    @Test
    public void SK_35_Tamar() {
        driver.get("https://www.thesaurus.com/");
        assertTrue(driver.findElement(By.id("active-synonyms")).isDisplayed());
        driver.findElement(By.id("searchbar_input")).sendKeys("gather");
        driver.findElement(By.id("search-submit")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        assertEquals("SYNONYMS FOR gather",
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("[data-testid='word-grid-section-heading']"))).getText());
        assertTrue(driver.findElement(By.tagName("nav")).isDisplayed());
        assertEquals("DICTIONARY.COM", driver.findElement(By.id("dictionary-nav-tab")).getText());
        assertEquals("THESAURUS.COM", driver.findElement(By.id("thesaurus-nav-tab")).getText());
        List<WebElement> navTitleElements = driver.findElements(
                By.cssSelector("[aria-label='Site Navigation'] ul > li[data-current-active] > button"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("MEANINGS", "GAMES", "LEARN", "WRITING", "WORD OF THE DAY"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navTitleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        driver.findElement(By.id("thesaurus-nav-tab")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-testid='synonym-of-the-day']")).isDisplayed());
    }

    /**
     * SK_36
     * Tamar
     * HTML Refers to SK_1, SK_36
     */
    @Test
    public void SK_36_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//*[text()='WORD OF THE DAY']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList
                ("Word Of The Day", "Synonym Of The Day", "Word Of The Year"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(1).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.className("otd-item-wrapper__label"), "SYNONYM OF THE DAY"));
        element = driver.findElement(By.cssSelector(".otd-item-headword__date > *"));
        LocalDate currentDate = LocalDate.now();
        String current = currentDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy", Locale.ENGLISH)).toUpperCase();
        assertEquals(current, element.getText());
    }

    /**
     * SK_37
     * Tamar
     * HTML Refers to SK_1, SK_14
     */
    @Test
    public void SK_37_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.id("searchbar_input"));
        element.sendKeys("happy");
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "happy"));
        assertTrue(driver.findElement(By.id("meanings")).isDisplayed());
    }

    /**
     * SK_38
     * Tamar
     * HTML Refers to SK_1, SK_14
     */
    @Test
    public void SK_38_Tamar() {
        driver.get("https://www.thesaurus.com/");
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath
                ("//h2[text()='MOST SEARCHED WORDS']/ancestor::section/following-sibling::*"))).click().perform();
        element = driver.findElement(By.xpath("//h2[text()='MOST SEARCHED WORDS']/../following-sibling::*//a"));
        String mostSearchedWordText = element.getText();
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), mostSearchedWordText));
        assertTrue(driver.findElement(By.id("meanings")).isDisplayed());
    }

    /**
     * SK_39
     * Tamar
     * HTML Refers to SK_1, SK_36
     */
    @Test
    public void SK_39_Tamar() {
        driver.get("https://www.thesaurus.com/");
        List<WebElement> answersElements = driver.findElements(By.xpath("//h2[text()='SYNONYM OF THE DAY']/following-sibling::*//a/../following-sibling::*[2]//a"));
        List<String> answers = new ArrayList<>();
        for (WebElement answer : answersElements) {
            answers.add(answer.getText());
        }
        answersElements.get(0).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.className("otd-item-wrapper__label"), "SYNONYM OF THE DAY"));
        assertTrue(answers.contains(driver.findElement(By.cssSelector(".otd-item-headword__word h2")).getText()));
    }

    /**
     * SK_40
     * Tamar
     * HTML Refers to SK_1, SK_17
     */
    @Test
    public void SK_40_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.id("active-synonyms"));
        element.click();

        element.findElement(By.xpath("//*[text()='DEFINITIONS']")).click();
        assertTrue(driver.findElement(By.id("active-definitions")).isDisplayed());
        element = driver.findElement(By.id("searchbar_input"));
        element.sendKeys("happy");
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "happy"));
        assertTrue(driver.findElement(By.id("top-definitions-section")).isDisplayed());
    }

    /**
     * SK_41
     * Tamar
     * HTML Refers to SK_1, SK_41
     */
    @Test
    public void SK_41_Tamar() {
        driver.get("https://www.thesaurus.com/");
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//*[@data-testid='wotd']//following-sibling::section"))).perform();
        element = driver.findElement(By.cssSelector("[data-testid='wotd'] a"));
        String word = element.getText();
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.className("otd-item-wrapper__label"), "WORD OF THE DAY"));
        wait.until(ExpectedConditions.textToBe(By.className("otd-item-headword__word"), word));
    }

    /**
     * SK_42
     * Tamar
     * HTML Refers to SK_1, SK_42
     */
    @Test
    public void SK_42_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='GAMES']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Word Puzzle", "Quizzes", "Crossword Solver",
                "Scrabble Word Finder", "Words With Friends Cheat", "Daily Crossword Puzzle"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(3).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Scrabble Word Finder"));
        assertEquals("Words That Start With:", driver.findElement(By.className("links__title")).getText());

        List<WebElement> letters = driver.findElements(By.cssSelector("[title^='Words that start with']"));
        for (int i = 0; i < letters.size(); i++) {
            assertEquals((char) (i + 65) + "" + (char) (i + 97), letters.get(i).getText());
        }
    }

    /**
     * SK_43
     * Tamar
     * HTML Refers to SK_1, SK_17, SK_42, SK_43
     */
    @Test
    public void SK_43_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='GAMES']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Word Puzzle", "Quizzes", "Crossword Solver",
                "Scrabble Word Finder", "Words With Friends Cheat", "Daily Crossword Puzzle"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(3).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Scrabble Word Finder"));
        assertEquals("Words That Start With:", driver.findElement(By.className("links__title")).getText());

        driver.findElement(By.cssSelector("[title='Words that start with d']")).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "WORDS THAT START WITH “D”"));

        List<WebElement> sections = driver.findElements(By.cssSelector(".word-finder-widgets .section__body"));
        for (WebElement sectionElement : sections) {
            List<WebElement> words = sectionElement.findElements(By.cssSelector(".list--words li > a"));
            for (WebElement wordElement : words) {
                String word = wordElement.getText();
                if (!word.isEmpty())
                    assertTrue(word.startsWith("d"));
            }
        }

        element = sections.get(0).findElement(By.cssSelector(".list--words li > a"));
        String firstWord = element.getText();
        element.click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), firstWord));
        assertTrue(driver.findElement(By.id("top-definitions-section")).isDisplayed());
    }

    /**
     * SK_44
     * Tamar
     * HTML Refers to SK_1, SK_42
     */
    @Test
    public void SK_44_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='GAMES']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Word Puzzle", "Quizzes", "Crossword Solver",
                "Scrabble Word Finder", "Words With Friends Cheat", "Daily Crossword Puzzle"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(3).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Scrabble Word Finder"));
        assertTrue(driver.findElement(By.xpath("//h4[contains(text(), 'Word length:')]")).isDisplayed());

        List<WebElement> buttons = driver.findElements(By.cssSelector("[title$='-letter']"));
        assertEquals(14, buttons.size());
        for (int i = 0; i < buttons.size(); i++) {
            assertEquals(i + 2, Integer.parseInt(buttons.get(i).getText().split("-")[0]));
        }
    }

    /**
     * SK_45
     * Tamar
     * HTML Refers to SK_1, SK_42, SK_45
     */
    @Test
    public void SK_45_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='GAMES']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Word Puzzle", "Quizzes", "Crossword Solver",
                "Scrabble Word Finder", "Words With Friends Cheat", "Daily Crossword Puzzle"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(3).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Scrabble Word Finder"));
        assertTrue(driver.findElement(By.xpath("//h4[contains(text(), 'Word length:')]")).isDisplayed());

        driver.findElement(By.cssSelector("[title='4-letter']")).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "WORDS WITH LENGTH “4”"));
        List<WebElement> sectionTitlesElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector(".word-finder-widgets .section__title")));
        for (int i = 0; i < sectionTitlesElements.size(); i++) {
            String text = sectionTitlesElements.get(i).getText();
            assertEquals((char) (i + 65), text.charAt(text.length() - 1));
            List<WebElement> words = sectionTitlesElements.get(i).findElements(
                    By.xpath(".//ancestor::*[@class='section__group-inner']//li/a"));
            String startsWithLetter = String.valueOf((char) (i + 97));
            for (WebElement wordElement : words) {
                String word = wordElement.getText();
                if (!word.isEmpty()) {
                    assertTrue("problem in word " + word + " letter " + startsWithLetter, word.startsWith(startsWithLetter));
                    assertEquals(4, word.length());
                }
            }
        }
    }

    /**
     * SK_46
     * Tamar
     * HTML Refers to SK_1, SK_42, SK_46
     */
    @Test
    public void SK_46_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='GAMES']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Word Puzzle", "Quizzes", "Crossword Solver",
                "Scrabble Word Finder", "Words With Friends Cheat", "Daily Crossword Puzzle"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(3).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Scrabble Word Finder"));
        assertTrue(driver.findElement(By.className("scrabble-form")).isDisplayed());
        driver.findElement(By.cssSelector(".form__controls input")).sendKeys("abl");
        driver.findElement(By.cssSelector("[value='find']")).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".word-finder-widgets .section__head h3"), "results for ABL"));
        List<WebElement> wordElements = driver.findElements(By.cssSelector(".list--words a"));
        for (WebElement element : wordElements) {
            String word = element.getText();
            assertTrue(word.matches("^(?!.*?(.).*?\\1)[abl?]*$"));
        }
    }

    /**
     * SK_47
     * Tamar
     * HTML Refers to SK_1, SK_42, SK_46
     */
    @Test
    public void SK_47_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='GAMES']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Word Puzzle", "Quizzes", "Crossword Solver",
                "Scrabble Word Finder", "Words With Friends Cheat", "Daily Crossword Puzzle"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(3).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Scrabble Word Finder"));
        assertTrue(driver.findElement(By.className("scrabble-form")).isDisplayed());
        driver.findElement(By.cssSelector(".form__controls input")).sendKeys("blagin");

        driver.findElement(By.cssSelector("a.advanced-toggle")).click();
        assertNotEquals("none", driver.findElement(By.cssSelector(".form__row--advanced")).getCssValue("display"));
        expectedTitles.clear();
        actualTitles.clear();
        List<WebElement> inputElements = driver.findElements(By.cssSelector(
                ".scrabble-form:first-of-type .form__controls--advanced input"));
        expectedTitles = new ArrayList<>(Arrays.asList("Starts with", "Includes", "Ends with", "Apply"));
        for (WebElement element : inputElements) {
            String placeHolder = element.getAttribute("placeholder");
            if (placeHolder.isEmpty())
                actualTitles.add(element.getAttribute("value"));
            else
                actualTitles.add(placeHolder);
        }
        assertEquals(expectedTitles, actualTitles);
        inputElements.get(0).sendKeys("b");
        inputElements.get(1).sendKeys("i");
        inputElements.get(2).sendKeys("g");
        inputElements.get(3).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".word-finder-widgets .section__head h3"), "results for BLAGIN"));
        List<WebElement> wordElements = driver.findElements(By.cssSelector(".list--words a"));
        for (WebElement element : wordElements) {
            String word = element.getText();
            assertTrue(word.matches("^(?!.*?(.).*?\\1)[blagin?]*$"));
            assertTrue(word.matches("^[b].*[i].*[g]$"));
        }
    }

    /**
     * SK_48
     * Tamar
     * HTML Refers to SK_1, SK_48
     */
    @Test
    public void SK_48_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='LEARN']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Word Lists", "Online Tutors", "New Words",
                "Trending Words", "All About English", "Science And Technology", "Literature And Arts"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(0).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Word Lists & Flashcards"));
        assertTrue(driver.findElement(By.xpath("//h3[text()='Featured Lists']")).isDisplayed());
        List<WebElement> featuredLists = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("[data-testid='word-list-hub-card-featured']")));
        assertEquals(3, featuredLists.size());
        String listName = featuredLists.get(0).findElement(By.tagName("div")).getText();
        featuredLists.get(0).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), listName));
        assertTrue(driver.findElement(By.cssSelector("[data-testid='word-wrapper']")).isDisplayed());
    }

    /**
     * SK_49
     * Tamar
     * HTML Refers to SK_1, SK_48
     */
    @Test
    public void SK_49_Tamar() throws InterruptedException {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='LEARN']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Word Lists", "Online Tutors", "New Words",
                "Trending Words", "All About English", "Science And Technology", "Literature And Arts"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(0).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Word Lists & Flashcards"));
        assertTrue(driver.findElement(By.xpath("//h3[text()='All Lists']")).isDisplayed());
        driver.findElement(By.tagName("select")).click();
        driver.findElement(By.xpath("//*[text()='A - Z']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='word-list-hub-show-more-button']"))).click();
        Thread.sleep(2000);
        List<WebElement> listElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("[data-testid='word-list-hub-card']")));
        for (int i = 0; i < listElements.size() - 1; i++) {
            String currentText = listElements.get(i).findElement(By.tagName("div")).getText().replaceAll(" ", "");
            String nextText = listElements.get(i + 1).findElement(By.tagName("div")).getText().replaceAll(" ", "");
            assertTrue(currentText + ", " + nextText, currentText.compareTo(nextText) < 0);
        }
    }

    /**
     * SK_50
     * Tamar
     * HTML Refers to SK_1, SK_48
     */
    @Test
    public void SK_50_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='LEARN']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Word Lists", "Online Tutors", "New Words",
                "Trending Words", "All About English", "Science And Technology", "Literature And Arts"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(0).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Word Lists & Flashcards"));
        element = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[text()='Would you like to contribute to the community?']")));
        element = element.findElement(By.xpath(".//following-sibling::*/button"));
        assertEquals("Create A List", element.getText());
        element.click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h3"), "Build Your Own Word Lists!"));
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[aria-label='sign up']")));
        assertEquals("rgba(0, 36, 139, 1)", element.getCssValue("background-color"));
    }

    /**
     * SK_51
     * Tamar
     * HTML Refers to SK_1, SK_51
     */
    @Test
    public void SK_51_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='GAMES']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Word Puzzle", "Quizzes", "Crossword Solver",
                "Scrabble Word Finder", "Words With Friends Cheat", "Daily Crossword Puzzle"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(1).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Quizzes"));
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='quiz-hub-card-featured']")));
        String quizName = element.getText().toUpperCase(Locale.ROOT);
        if (!quizName.matches("^[a-zA-Z0-9]"))
            quizName = quizName.substring(2).trim();
        element.click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), quizName));
        By statusLocator = By.xpath("//*[contains(text(), 'Question')]");
        String status = driver.findElement(statusLocator).getText();
        int numberOfQuestions = Integer.parseInt(status.substring(status.lastIndexOf(' ') + 1));
        for (int i = 0; i < numberOfQuestions; i++) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-quiz-answer]"))).click();
            if (i == numberOfQuestions - 1)
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text() = 'SEE RESULTS']"))).click();
            else {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-next-question-button]"))).click();
                wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(statusLocator, status)));
                status = driver.findElement(statusLocator).getText();
            }
        }
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), ' / " + numberOfQuestions + "')]")));
        assertTrue(element.getText(), element.getText().trim().matches("^\\d.*"));
    }

    /**
     * SK_52
     * Tamar
     * HTML Refers to SK_1, SK_48, SK_51
     */
    @Test
    public void SK_52_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='GAMES']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Word Puzzle", "Quizzes", "Crossword Solver",
                "Scrabble Word Finder", "Words With Friends Cheat", "Daily Crossword Puzzle"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(1).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Quizzes"));
        element = driver.findElement(By.cssSelector("[data-testid='Word Lists-test']"));
        assertTrue(element.isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[data-testid='Quizzes-test']")).isDisplayed());
        element.click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Word Lists & Flashcards"));
    }

    /**
     * SK_53
     * Tamar
     * HTML Refers to SK_1, SK_53
     */
    @Test
    public void SK_53_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='MEANINGS']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList(
                "Emoji", "Slang", "Acronyms", "Pop Culture", "Memes", "Gender And Sexuality", "Mixed-Up Meanings"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(0).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "EMOJI DICTIONARY"));
        assertEquals("FEATURED TERMS", driver.findElement(By.tagName("h2")).getText());
        element = driver.findElement(By.cssSelector(".slick-track .word-item h2 a"));
        String title = element.getText();
        element.click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("article-word__title")));
        assertEquals(title, element.getText().toUpperCase(Locale.ROOT));
    }

    /**
     * SK_54
     * Tamar
     * HTML Refers to SK_1, SK_53
     */
    @Test
    public void SK_54_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='MEANINGS']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList(
                "Emoji", "Slang", "Acronyms", "Pop Culture", "Memes", "Gender And Sexuality", "Mixed-Up Meanings"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(0).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "EMOJI DICTIONARY"));
        assertEquals("MORE FROM EMOJI", driver.findElement(By.cssSelector("#just-added h2")).getText());
        List<WebElement> links = driver.findElements(By.cssSelector("#just-added .list a"));
        assertEquals(60, links.size());
        String title = links.get(0).getText();
        links.get(0).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("article-word__title")));
        assertEquals(title, element.getText());
    }

    /**
     * SK_55
     * Tamar
     * HTML Refers to SK_1, SK_55
     */
    @Test
    public void SK_55_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='WRITING']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList(
                "Grammar Coach", "Writing Prompts", "Grammar 101", "Writing Tips", "Ways To Say It Better"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(4).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Ways To Say"));
        List<WebElement> cardsCategories = driver.findElements(By.className("section__post-categories"));
        for (WebElement category : cardsCategories) {
            assertEquals("Ways To Say", category.getText());
        }
        element = cardsCategories.get(0).findElement(By.xpath(".//preceding-sibling::*"));
        String title = element.getText();
        element.click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("article__title"), title.substring(0, title.length() - 3)));
        assertTrue(driver.findElement(By.cssSelector(".slides > [aria-label='Previous']")).isDisplayed());
        element = driver.findElement(By.cssSelector(".slides > [aria-label='Next']"));
        assertTrue(element.isDisplayed());
        By locator = By.cssSelector(".slick-current h2");
        String current = driver.findElement(locator).getText();
        element.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, current)));
    }

    /**
     * SK_56
     * Tamar
     * HTML Refers to SK_1, SK_26
     */
    @Test
    public void SK_56_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.id("searchbar_input"));
        element.sendKeys("people");
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "people"));
        assertTrue(driver.findElement(By.id("meanings")).isDisplayed());
        element = driver.findElement(By.xpath("//*[contains(text(), 'See also synonyms for:')]//a"));
        String expectedTitle = element.getText();
        element.click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), expectedTitle));
        assertTrue(driver.findElement(By.id("meanings")).isDisplayed());
    }

    /**
     * SK_57
     * Tamar
     * HTML Refers to SK_1, SK_57
     */
    @Test
    public void SK_57_Tamar() {
        driver.get("https://www.thesaurus.com/");
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.tagName("footer"))).perform();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.cssSelector("#about-click a")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("section-about-intro")));

        element = driver.findElement(By.className("section-about-chart"));
        actions.moveToElement(element).perform();
        assertEquals(5, element.findElements(By.cssSelector(".lines > li")).size());
    }

    /**
     * SK_58
     * Tamar
     * HTML Refers to SK_1, SK_58
     */
    @Test
    public void SK_58_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.id("searchbar_input"));
        element.sendKeys("people");
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "people"));
        assertTrue(driver.findElement(By.id("meanings")).isDisplayed());

        element = driver.findElement(By.id("example-creator"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        assertEquals("TRY USING people", element.findElement(By.tagName("h2")).getText());
        element = element.findElement(By.tagName("input"));
        assertTrue(element.isDisplayed());
        element.sendKeys("The people are here");
        driver.findElement(By.xpath("//button[text()='TRY NOW']")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-example-sentences]")));
        assertEquals("Showing examples for the selected word:", element.findElement(By.tagName("span")).getText());
        List<WebElement> sentences = driver.findElements(By.cssSelector("[data-test-swapped-sentence]"));
        for (WebElement sentence : sentences) {
            assertTrue(sentence.getText().matches("The .+ are here"));
        }
        assertTrue(driver.findElement(By.xpath("//a[text()='GIVE FEEDBACK']")).isDisplayed());
    }

    /**
     * SK_59
     * Tamar
     * HTML Refers to SK_1, SK_59
     */
    @Test
    public void SK_59_Tamar() {
        driver.get("https://www.thesaurus.com/");
        Actions actions = new Actions(driver);
        element = driver.findElement(By.xpath("//*[text()='DOWNLOAD OUR APPS']"));
        actions.moveToElement(driver.findElement(By.tagName("footer"))).perform();
        WebDriverWait wait = new WebDriverWait(driver, 10);
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
        assertEquals("Our apps also have more than 1.5 million definitions and synonyms plus access to our " +
                        "trusted reference articles. Look up the words anywhere anytime-we work offline too!",
                element.findElement(By.xpath(".//../following-sibling::p")).getText());

        driver.findElement(By.id("android-appstore")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Google Play Logo']")));
        assertEquals("Dictionary.com English Word Meanings & Definitions", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_60
     * Tamar
     * HTML Refers to SK_1, SK_60
     */
    @Test
    public void SK_60_Tamar() {
        driver.get("https://www.thesaurus.com/");
        Actions actions = new Actions(driver);
        element = driver.findElement(By.xpath("//*[text()='DOWNLOAD OUR APPS']"));
        actions.moveToElement(driver.findElement(By.tagName("footer"))).perform();
        WebDriverWait wait = new WebDriverWait(driver, 10);
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
        assertEquals("Our apps also have more than 1.5 million definitions and synonyms plus access to our " +
                        "trusted reference articles. Look up the words anywhere anytime-we work offline too!",
                element.findElement(By.xpath(".//../following-sibling::p")).getText());

        driver.findElement(By.id("apple-appstore")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.textToBe(By.cssSelector("[data-test-we-localnav-store-title]"), "App Store"));
        assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Dictionary.com: English Words"));
    }

    /**
     * SK_61
     * Tamar
     * HTML Refers to SK_1, SK_61
     */
    @Test
    public void SK_61_Tamar() {
        driver.get("https://www.thesaurus.com/");
        driver.findElement(By.id("dictionary-nav-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".header-tab-active"), "DICTIONARY.COM"));
        assertTrue(driver.findElement(By.cssSelector("[data-testid='wotd']")).isDisplayed());

        driver.findElement(By.id("thesaurus-nav-tab")).click();
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".header-tab-active"), "THESAURUS.COM"));
        assertTrue(driver.findElement(By.cssSelector("[data-testid='synonym-of-the-day']")).isDisplayed());
    }

    /**
     * SK_62
     * Tamar
     * HTML Refers to SK_1, SK_14
     */
    @Test
    public void SK_62_Tamar() {
        driver.get("https://www.thesaurus.com/");
        Actions actions = new Actions(driver);
        element = driver.findElement(By.xpath("//*[text()='SYNONYMS FOR OVERUSED WORDS']/ancestor::section"));
        actions.moveToElement(element).perform();
        WebDriverWait wait = new WebDriverWait(driver, 10);
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
        assertEquals("We've found the most overused words and the synonyms you should be using instead.",
                element.findElement(By.cssSelector(".heading-description")).getText());

        List<WebElement> wordBlocks = driver.findElements(By.cssSelector("[data-testid='synonyms-block-word']"));
        assertEquals(3, wordBlocks.size());
        List<String> wordsInFirstBlock = new ArrayList<>();
        for (int i = 0; i < wordBlocks.size(); i++) {
            List<WebElement> words = wordBlocks.get(i).findElements(By.tagName("a"));
            assertEquals(4, words.size());
            if (i == 0) {
                for (WebElement wordElement : words) {
                    wordsInFirstBlock.add(wordElement.getText());
                }
            }
        }
        wordBlocks.get(0).findElement(By.tagName("a")).click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), wordsInFirstBlock.get(0)));
        element = driver.findElement(By.id("meanings"));
        assertTrue(element.isDisplayed());
        for (int i = 1; i < wordsInFirstBlock.size(); i++) {
            assertTrue(element.findElement(By.xpath(".//*[text()='" + wordsInFirstBlock.get(i) + "']")).isDisplayed());
        }
    }

    /**
     * SK_63
     * Tamar
     * HTML Refers to SK_1, SK_63
     */
    @Test
    public void SK_63_Tamar() {
        driver.get("https://www.thesaurus.com/");
        Actions actions = new Actions(driver);
        element = driver.findElement(By.cssSelector("[data-browse-az]"));
        actions.moveToElement(element).perform();
        WebDriverWait wait = new WebDriverWait(driver, 10);
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
                , element.findElement(By.xpath("//*[text()='BROWSE THESAURUS.COM']"))));
        List<WebElement> buttons = driver.findElements(By.cssSelector("[data-browse-az] div:nth-of-type(2) a"));

        for (int i = 1; i < buttons.size(); i++) {
            assertEquals((char) (i + 64) + "" + (char) (i + 96), buttons.get(i).getText());
        }
        buttons.get(3).click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "BROWSE THESAURUS: LETTER \"C\""));
        List<WebElement> words = driver.findElements(By.cssSelector("[data-testid='list-az-results'] a"));
        for (WebElement word : words) {
            assertTrue(word.getText().toLowerCase(Locale.ROOT).startsWith("c"));
        }
    }

    /**
     * SK_64
     * Tamar
     * HTML Refers to SK_1, SK_64
     */
    @Test
    public void SK_64_Tamar() {
        driver.get("https://www.thesaurus.com/");
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.tagName("footer"))).perform();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.cssSelector("#careers-click a")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("section-careers-intro")));

        element = driver.findElement(By.id("twitter-widget-0"));
        actions.moveToElement(element).perform();
        driver.switchTo().frame(element);
        assertEquals("Tweets by @Dictionarycom", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_65
     * Tamar
     * HTML Refers to SK_1, SK_65
     */
    @Test
    public void SK_65_Tamar() {
        driver.get("https://www.thesaurus.com/");
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.tagName("footer"))).perform();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.cssSelector("#contactUs-click a")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".main-contact")));
        assertEquals("How can we help you?", driver.findElement(By.tagName("h1")).getText());

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("contact-search-bar__input")));
        element.sendKeys("date");
        element.submit();

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "results for \"date\""));
    }

    /**
     * SK_66
     * Tamar
     * HTML Refers to SK_1, SK_65, SK_66
     */
    @Test
    public void SK_66_Tamar() {
        driver.get("https://www.thesaurus.com/");
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.tagName("footer"))).perform();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.cssSelector("#contactUs-click a")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".main-contact")));
        assertEquals("Quick Links:", driver.findElement(By.xpath("//strong[text()]")).getText());

        List<WebElement> titleElements = driver.findElements(By.className("contact-quick-link"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("iPhone Apps", "Android Apps", "Accounts",
                "Word of the Day", "Grammar Coach Subscription", "Dictionary Academy Tutors"));
        List<String> actualTitles = new ArrayList<>();
        for (int i = 0; i < titleElements.size() - 1; i++) {
            actualTitles.add(titleElements.get(i).getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(0).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "iPhone Mobile Apps"));
        assertTrue(driver.findElement(By.className("article-list-link")).isDisplayed());
    }

    /**
     * SK_67
     * Tamar
     * HTML Refers to SK_1, SK_65, SK_67
     */
    @Test
    public void SK_67_Tamar() {
        driver.get("https://www.thesaurus.com/");
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.tagName("footer"))).perform();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.cssSelector("#contactUs-click a")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".main-contact")));
        assertEquals("Want to suggest a word or have a comment about one of our definitions or synonyms?",
                driver.findElement(By.xpath("(//strong[text()])[2]")).getText());

        driver.findElement(By.xpath("//a[text()='Fill out this form']")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.className("freebirdFormviewerViewNavigationPasswordWarning"), "Google Forms"));
        assertEquals("Dictionary.com & Thesaurus.com Lexicography Feedback",
                driver.findElement(By.cssSelector(".freebirdFormviewerViewHeaderTitle")).getText());
    }

    /**
     * SK_68
     * Tamar
     * HTML Refers to SK_1, SK_65
     */
    @Test
    public void SK_68_Tamar() {
        driver.get("https://www.thesaurus.com/");
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.tagName("footer"))).perform();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.cssSelector("#contactUs-click a")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".main-contact")));
        assertEquals("Can’t find the answer in our Help Center?",
                driver.findElement(By.xpath("(//strong[text()])[3]")).getText());

        element = driver.findElement(By.cssSelector("form[id*='gform']"));
        actions.moveToElement(element).perform();
        Hashtable<String, String> elementsTextAndKind = new Hashtable<String, String>() {{
            put("Department", "select");
            put("Subject", "select");
            put("Name", "input");
            put("Email Address", "input");
            put("Message", "textarea");
        }};
        elementsTextAndKind.forEach((name, kind) -> assertTrue(element.findElement(By.xpath
                ("//*[text()='" + name + "']/following-sibling::*/" + kind)).isDisplayed()));
        assertTrue(element.findElement(By.xpath("//*[text()='CAPTCHA']")).isDisplayed());
        assertTrue(element.findElement(By.cssSelector("[type='submit']")).isDisplayed());
    }

    /**
     * SK_69
     * Tamar
     * HTML Refers to SK_1, SK_69
     */
    @Test
    public void SK_69_Tamar() throws InterruptedException {
        driver.get("https://www.thesaurus.com/");
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.cssSelector("#termAndPrivacy-click a")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "Terms of Service & Privacy Policy"));

        List<WebElement> titleElements = driver.findElements(By.cssSelector(".article__entry > ul a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Privacy & Cookie Policy", "Children’s Privacy",
                "Contact Information", "Terms of Service", "Thesaurus Gift Card Terms and Conditions"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement title : titleElements) {
            actualTitles.add(title.getText());
        }
        assertEquals(expectedTitles, actualTitles);

        titleElements.get(1).click();
        Thread.sleep(2000);
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
                , driver.findElement(By.xpath("//b[text()='Children’s Privacy.']"))));
    }

    /**
     * SK_70
     * Tamar
     * HTML Refers to SK_1, SK_17, SK_61
     */
    @Test
    public void SK_70_Tamar() {
        driver.get("https://www.thesaurus.com/");
        driver.findElement(By.id("dictionary-nav-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".header-tab-active"), "DICTIONARY.COM"));
        assertTrue(driver.findElement(By.cssSelector("[data-testid='wotd']")).isDisplayed());

        element = driver.findElement(By.cssSelector(".trending-words-word-block"));
        String word = element.findElement(By.tagName("span")).getText();
        element.click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), word));
        assertTrue(driver.findElement(By.id("top-definitions-section")).isDisplayed());
    }

    /**
     * SK_71
     * Tamar
     * HTML Refers to SK_1, SK_71
     */
    @Test
    public void SK_71_Tamar() {
        driver.get("https://www.thesaurus.com/");
        driver.findElement(By.cssSelector("[data-grammar-coach-promo] button")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Grammar Coach™"));
        assertEquals("Your Key to Brilliant Writing", driver.findElement(By.tagName("h2")).getText());
        assertEquals("Our AI-backed writing tool helps writers of all kinds produce high-quality, " +
                        "error-free work. From grammar corrections to Thesaurus.com-powered synonym suggestions, " +
                        "Grammar Coach™ ensures every essay, email, and application letter is perfectly written.",
                driver.findElement(By.cssSelector("[data-hero-paragraph]")).getText());
        element = driver.findElement(By.cssSelector("[data-signed-out-grammar-coach]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element.findElement(By.cssSelector("[role='textbox']")).sendKeys("He went home.");
        wait.until(ExpectedConditions.textToBe(By.cssSelector("[data-check-grammar-message]"), "No grammar mistakes!"));
        assertEquals("0", element.findElement(By.cssSelector("[data-notification-count]")).getText());
    }

    /**
     * SK_72
     * Tamar
     * HTML Refers to SK_1, SK_72
     */
    @Test
    public void SK_72_Tamar() {
        driver.get("https://www.thesaurus.com/");
        driver.findElement(By.cssSelector("[data-grammar-coach-promo] button")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Grammar Coach™"));
        assertEquals("Your Key to Brilliant Writing", driver.findElement(By.tagName("h2")).getText());
        assertEquals("Our AI-backed writing tool helps writers of all kinds produce high-quality, " +
                        "error-free work. From grammar corrections to Thesaurus.com-powered synonym suggestions, " +
                        "Grammar Coach™ ensures every essay, email, and application letter is perfectly written.",
                driver.findElement(By.cssSelector("[data-hero-paragraph]")).getText());
        element = driver.findElement(By.cssSelector("[data-signed-out-grammar-coach]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        By countLocator = By.cssSelector("[data-notification-count]");
        By textLocator = By.cssSelector("[data-gramm]");
        By buttonLocator = By.cssSelector("[data-edit-button]");

        element.findElement(By.cssSelector("[role='textbox']")).sendKeys("he go home");
        wait.until(ExpectedConditions.textToBe(
                By.cssSelector("[data-check-grammar-message] span:not([data-grammar-corrections-count])"),
                "Please check the highlighted grammar fixes."));
        assertEquals("2", element.findElement(countLocator).getText());
        element.findElement(buttonLocator).click();
        wait.until(ExpectedConditions.textToBe(textLocator, "He go home"));
        element.findElement(buttonLocator).click();
        wait.until(ExpectedConditions.textToBe(textLocator, "He went home"));
        wait.until(ExpectedConditions.textToBe(By.cssSelector("[data-check-grammar-message]"), "No grammar mistakes!"));
        assertEquals("0", element.findElement(countLocator).getText());
    }

    /**
     * SK_73
     * Tamar
     * HTML Refers to SK_1, SK_71
     */
    @Test
    public void SK_73_Tamar() {
        driver.get("https://www.thesaurus.com/");
        driver.findElement(By.cssSelector("[data-grammar-coach-promo] button")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Grammar Coach™"));
        assertEquals("Your Key to Brilliant Writing", driver.findElement(By.tagName("h2")).getText());
        assertEquals("Our AI-backed writing tool helps writers of all kinds produce high-quality, " +
                        "error-free work. From grammar corrections to Thesaurus.com-powered synonym suggestions, " +
                        "Grammar Coach™ ensures every essay, email, and application letter is perfectly written.",
                driver.findElement(By.cssSelector("[data-hero-paragraph]")).getText());
        element = driver.findElement(By.cssSelector("[data-faq-section]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element.findElements(By.cssSelector("button[data-faq-dropdown]")).get(1).click();
        assertNotEquals("none", element.findElements(By.cssSelector("[data-faq-answer]")).get(1).getCssValue("display"));
    }


    /**
     * SK_74
     * Tamar
     * HTML Refers to SK_1, SK_71, SK_74
     */
    @Test
    public void SK_74_Tamar() {
        driver.get("https://www.thesaurus.com/");
        driver.findElement(By.cssSelector("[data-grammar-coach-promo] button")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Grammar Coach™"));
        assertEquals("Your Key to Brilliant Writing", driver.findElement(By.tagName("h2")).getText());
        assertEquals("Our AI-backed writing tool helps writers of all kinds produce high-quality, " +
                        "error-free work. From grammar corrections to Thesaurus.com-powered synonym suggestions, " +
                        "Grammar Coach™ ensures every essay, email, and application letter is perfectly written.",
                driver.findElement(By.cssSelector("[data-hero-paragraph]")).getText());
        driver.findElement(By.cssSelector("[data-upgrade-button]")).click();

        wait.until(ExpectedConditions.textToBe(By.cssSelector(".section-grammar-coach-content-block h1"),
                "Grammar Coach™ Premium. Elevate Your Writing."));
        List<WebElement> options = driver.findElements(By.cssSelector(".section__pricing-item  h2"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Monthly", "Yearly", "Quarterly"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : options) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        driver.findElement(By.cssSelector(".section__pricing-item-button")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("form")));
        assertTrue(driver.findElement(By.cssSelector("[aria-label='sign up']")).isDisplayed());
    }

    /**
     * SK_75
     * Tamar
     * HTML Refers to SK_1, SK_75
     */
    @Test
    public void SK_75_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='WRITING']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList(
                "Grammar Coach", "Writing Prompts", "Grammar 101", "Writing Tips", "Ways To Say It Better"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(1).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.cssSelector("section h2"),
                "How To Use Writing Prompts To Kickstart And Improve Writing"));
        assertTrue(driver.findElement(By.xpath("//a[contains(text(), 'PROMPT')]")).isDisplayed());
        driver.findElement(By.xpath("//a[text()='LOOK FOR A WRITING PROMPT FOR YOUR KID HERE']")).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Writing Prompts For Young Kids"));
    }

    /**
     * SK_76
     * Tamar
     * HTML Refers to SK_1, SK_75
     */
    @Test
    public void SK_76_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='WRITING']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList(
                "Grammar Coach", "Writing Prompts", "Grammar 101", "Writing Tips", "Ways To Say It Better"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(1).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.cssSelector("section h2"),
                "How To Use Writing Prompts To Kickstart And Improve Writing"));
        expectedTitles.clear();
        actualTitles.clear();
        List<WebElement> categoryElements = driver.findElements(By.xpath("//*[text()='Categories']/following-sibling::*/a"));
        expectedTitles = new ArrayList<>(Arrays.asList(
                "LOVE", "KIDS", "HOLIDAY", "FUNNY", "JOURNAL", "MYSTERY", "NOVELS", "DIALOGUES"));
        for (WebElement element : categoryElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        categoryElements.get(1).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Writing Prompts For Young Kids"));
    }

    /**
     * SK_77
     * Tamar
     * HTML Refers to SK_1, SK_77
     */
    @Test
    public void SK_77_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='LEARN']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Word Lists", "Online Tutors", "New Words",
                "Trending Words", "All About English", "Science And Technology", "Literature And Arts"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(4).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.attributeToBe(By.cssSelector(".header__logo img"), "alt", "Dictionary.com"));
        assertTrue(driver.findElement(By.className("articles")).isDisplayed());
        element = driver.findElement(By.cssSelector(".articles .article__title a"));
        String name = element.getText();
        element.click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), name));
        assertTrue(driver.findElement(By.tagName("article")).isDisplayed());
    }

    /**
     * SK_78
     * Tamar
     * HTML Refers to SK_1, SK_78
     */
    @Test
    public void SK_78_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//*[text()='WORD OF THE DAY']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList
                ("Word Of The Day", "Synonym Of The Day", "Word Of The Year"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(2).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "The Dictionary.com Word Of The Year For 2020 Is …"));
        assertEquals("pandemic:", driver.findElement(By.cssSelector("h3 strong")).getText().split(" ")[0]);
    }

    /**
     * SK_79
     * Tamar
     * HTML Refers to SK_1, SK_79
     */
    @Test
    public void SK_79_Tamar() {
        driver.get("https://www.thesaurus.com/");
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
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
                , driver.findElement(By.xpath("//h4[text()='CHECK OUT OUR OTHER PRODUCTS']"))));

        driver.findElement(By.id("alexaskill")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Amazon']")));
        assertEquals("Dictionary.com Word of the Day", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_80
     * Tamar
     * HTML Refers to SK_1, SK_80
     */
    @Test
    public void SK_80_Tamar() {
        driver.get("https://www.thesaurus.com/");
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
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
                , driver.findElement(By.xpath("//h4[text()='CHECK OUT OUR OTHER PRODUCTS']"))));

        driver.findElement(By.id("lexico-es")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[alt='Lexico logo']")));
        assertEquals("SPANISH", driver.findElement(By.className("sbSelector")).getText());
    }

    /**
     * SK_81
     * Tamar
     * HTML Refers to SK_1, SK_80
     */
    @Test
    public void SK_81_Tamar() {
        driver.get("https://www.thesaurus.com/");
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
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
                , driver.findElement(By.xpath("//h4[text()='CHECK OUT OUR OTHER PRODUCTS']"))));

        driver.findElement(By.id("lexico-en")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[alt='Lexico logo']")));
        assertEquals("UK DICTIONARY", driver.findElement(By.className("sbSelector")).getText());
    }

    /**
     * SK_82
     * Tamar
     * HTML Refers to SK_1, SK_61
     */
    @Test
    public void SK_82_Tamar() {
        driver.get("https://www.thesaurus.com/");
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
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
                , driver.findElement(By.xpath("//h4[text()='CHECK OUT OUR OTHER PRODUCTS']"))));

        driver.findElement(By.id("dcom")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".header-tab-active"), "DICTIONARY.COM"));
        assertTrue(driver.findElement(By.cssSelector(".trending-words-word-block")).isDisplayed());
    }

    /**
     * SK_83
     * Tamar
     * HTML Refers to SK_1, SK_17, SK_83
     */
    @Test
    public void SK_83_Tamar() {
        driver.get("https://www.thesaurus.com/");
        driver.findElement(By.id("dictionary-nav-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".header-tab-active"), "DICTIONARY.COM"));
        assertTrue(driver.findElement(By.cssSelector(".trending-words-word-block")).isDisplayed());

        element = driver.findElement(By.xpath("//*[text()='ONLINE TUTORING HELP! ']/ancestor::section"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        List<WebElement> stepElements = element.findElements(By.tagName("h3"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("English Tutors", "Math Tutors", "Test Prep"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : stepElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        stepElements.get(0).click();

        wait.until(ExpectedConditions.titleIs("Appointy"));
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".info p"),
                "Welcome to Dictionary Academy Tutors, where our teacher-trained certified tutors are " +
                        "waiting to engage your learner! Click the subject and grade level of your learner. Next, you’ll " +
                        "select the time that works for you and choose from our list of expert tutors. It's that easy!"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".page-services")));
    }

    /**
     * SK_84
     * Tamar
     * HTML Refers to SK_1, SK_17, SK_63, SK_84
     */
    @Test
    public void SK_84_Tamar() {
        driver.get("https://www.thesaurus.com/");
        driver.findElement(By.id("dictionary-nav-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".header-tab-active"), "DICTIONARY.COM"));
        assertTrue(driver.findElement(By.cssSelector(".trending-words-word-block")).isDisplayed());

        Actions actions = new Actions(driver);
        element = driver.findElement(By.cssSelector("[data-browse-az]"));
        actions.moveToElement(element).perform();
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
                , element.findElement(By.xpath("//*[text()='BROWSE DICTIONARY.COM']"))));
        List<WebElement> buttons = driver.findElements(By.cssSelector("[data-browse-az] div:nth-of-type(2) a"));
        assertEquals("#", buttons.get(0).getText());
        for (int i = 1; i < buttons.size(); i++) {
            assertEquals((char) (i + 64) + "" + (char) (i + 96), buttons.get(i).getText());
        }
        buttons.get(3).click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "BROWSE DICTIONARY: LETTER \"C\""));
        List<WebElement> words = driver.findElements(By.cssSelector("[data-testid='list-az-results'] li > a:first-child"));
        for (WebElement word : words) {
            assertTrue(word.getText().toLowerCase(Locale.ROOT).startsWith("c"));
        }
    }

    /**
     * SK_85
     * Tamar
     * HTML Refers to SK_1, SK_17
     */
    @Test
    public void SK_85_Tamar() throws InterruptedException {
        driver.get("https://www.thesaurus.com/");
        driver.findElement(By.id("dictionary-nav-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".header-tab-active"), "DICTIONARY.COM"));
        assertTrue(driver.findElement(By.cssSelector(".trending-words")).isDisplayed());
        By locator = By.cssSelector("[alt*='ticker']");
        driver.findElement(locator).click();
        assertEquals("play ticker", driver.findElement(locator).getAttribute("alt"));

        List<WebElement> originalBlocks = driver.findElements(By.cssSelector(".trending-words-word-block"));
        List<Integer> originalPositions = new ArrayList<>();
        for (WebElement element : originalBlocks) {
            originalPositions.add(element.getLocation().x);
        }
        Thread.sleep(3000);
        List<WebElement> newBlocks = driver.findElements(By.cssSelector(".trending-words-word-block"));
        List<Integer> newPositions = new ArrayList<>();
        for (WebElement element : newBlocks) {
            newPositions.add(element.getLocation().x);
        }
        assertEquals(originalPositions, newPositions);
    }

    /**
     * SK_86
     * Tamar
     * HTML Refers to SK_1, SK_86
     */
    @Test
    public void SK_86_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='MEANINGS']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList(
                "Emoji", "Slang", "Acronyms", "Pop Culture", "Memes", "Gender And Sexuality", "Mixed-Up Meanings"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(2).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "ACRONYMS DICTIONARY"));
        assertEquals("FEATURED TERMS", driver.findElement(By.tagName("h2")).getText());
        element = driver.findElement(By.cssSelector(".slick-track .word-item h2 a"));
        String title = element.getText();
        element.click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), title));
    }

    /**
     * SK_87
     * Tamar
     * HTML Refers to SK_1, SK_86
     */
    @Test
    public void SK_87_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='MEANINGS']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList(
                "Emoji", "Slang", "Acronyms", "Pop Culture", "Memes", "Gender And Sexuality", "Mixed-Up Meanings"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(2).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "ACRONYMS DICTIONARY"));
        assertEquals("Home / Acronyms", driver.findElement(By.className("breadcrumbs")).getText());
        element = driver.findElement(By.cssSelector(".slick-track .word-item h2 a"));
        String title = element.getText();
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("article-word")));
        assertTrue(driver.findElement(By.className("breadcrumbs")).getText().endsWith(title));
    }

    /**
     * SK_88
     * Tamar
     * HTML Refers to SK_1, SK_86
     */
    @Test
    public void SK_88_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='MEANINGS']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList(
                "Emoji", "Slang", "Acronyms", "Pop Culture", "Memes", "Gender And Sexuality", "Mixed-Up Meanings"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(2).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "ACRONYMS DICTIONARY"));
        assertEquals("MORE FROM ACRONYMS", driver.findElement(By.cssSelector("#just-added h2")).getText());
        assertEquals(60, driver.findElements(By.cssSelector("#just-added .list a")).size());
        assertEquals("1", driver.findElement(By.cssSelector("#just-added ol")).getAttribute("start"));

        driver.findElement(By.className("paging-next")).click();
        assertEquals(60, driver.findElements(By.cssSelector("#just-added .list a")).size());
        assertEquals("61", driver.findElement(By.cssSelector("#just-added ol")).getAttribute("start"));
    }

    /**
     * SK_89
     * Tamar
     * HTML Refers to SK_1, SK_86
     */
    @Test
    public void SK_89_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='MEANINGS']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList(
                "Emoji", "Slang", "Acronyms", "Pop Culture", "Memes", "Gender And Sexuality", "Mixed-Up Meanings"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(2).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "ACRONYMS DICTIONARY"));
        assertEquals("MORE FROM ACRONYMS", driver.findElement(By.cssSelector("#just-added h2")).getText());
        List<WebElement> links = driver.findElements(By.cssSelector("#just-added .list a"));
        assertEquals(60, links.size());
        String title = links.get(0).getText();
        links.get(0).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), title));
    }

    /**
     * SK_90
     * Tamar
     * HTML Refers to SK_1, SK_53, SK_86
     */
    @Test
    public void SK_90_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//button[text()='MEANINGS']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList(
                "Emoji", "Slang", "Acronyms", "Pop Culture", "Memes", "Gender And Sexuality", "Mixed-Up Meanings"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(2).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "ACRONYMS DICTIONARY"));

        expectedTitles.clear();
        actualTitles.clear();
        List<WebElement> categories = driver.findElements(By.cssSelector(".section-atw-categories a"));
        expectedTitles = new ArrayList<>(Arrays.asList("ACRONYMS", "EMOJI", "FAMOUS PEOPLE", "FASHION",
                "FICTIONAL CHARACTERS", "GENDER & SEXUALITY", "HISTORICAL & CURRENT EVENTS", "MEMES", "POLITICS",
                "POP CULTURE", "RELIGION", "SLANG", "TECH & SCIENCE", "TRANSLATIONS"));
        for (WebElement element : categories) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        categories.get(1).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "EMOJI DICTIONARY"));
    }

    /**
     * SK_91
     * Tamar
     * HTML Refers to SK_1, SK_14
     */
    @Test
    public void SK_91_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.id("searchbar_input"));
        element.sendKeys("happy");
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "happy"));
        assertTrue(driver.findElement(By.id("meanings")).isDisplayed());

        driver.findElement(By.xpath("//*[text()='Compare Synonyms']/..")).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Compare similar synonym words']")));
        assertTrue(element.findElement(By.xpath("//h3[text()='Select up to 3 synonyms to compare']")).isDisplayed());
        List<String> chosenSynonyms = new ArrayList<>(Arrays.asList("lively", "blessed", "exultant"));
        for (String synonym : chosenSynonyms) {
            wait.until(ExpectedConditions.elementToBeClickable(element.findElement(By.xpath(".//button[text()='" + synonym + "']")))).click();
        }
        element.findElement(By.xpath(".//a[text()='Compare Synonyms']")).click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Compare Synonyms of happy"));
        List<WebElement> cards = driver.findElements(By.xpath("//h2/../.."));
        for (int i = 0; i < cards.size(); i++) {
            String synonym = chosenSynonyms.get(i);
            assertEquals(synonym, cards.get(i).findElement(By.tagName("h2")).getText());
            List<WebElement> subtitles = cards.get(i).findElements(By.tagName("h3"));
            List<String> expectedTitles = new ArrayList<>(
                    Arrays.asList("Definitions(s)", "Shared synonyms between " + synonym + " and happy",
                            "Shared antonyms between " + synonym + " and happy"));
            List<String> actualTitles = new ArrayList<>();
            for (WebElement element : subtitles) {
                actualTitles.add(element.getText());
            }
            assertEquals(expectedTitles, actualTitles);
        }
    }

    /**
     * SK_92
     * Tamar
     * HTML Refers to SK_1, SK_14, SK_41
     */
    @Test
    public void SK_92_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.id("searchbar_input"));
        element.sendKeys("happy");
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "happy"));
        assertTrue(driver.findElement(By.id("meanings")).isDisplayed());

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        element = driver.findElement(By.cssSelector("[data-testid='wotd']"));
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
        String name = element.findElement(By.cssSelector(".wotd-word")).getText();
        element.click();
        wait.until(ExpectedConditions.textToBe(By.className("otd-item-wrapper__label"), "WORD OF THE DAY"));
        wait.until(ExpectedConditions.textToBe(By.className("otd-item-headword__word"), name));
    }

    /**
     * SK_93
     * Tamar
     * HTML Refers to SK_1, SK_41, SK_93
     */
    @Test
    public void SK_93_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//*[text()='WORD OF THE DAY']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList
                ("Word Of The Day", "Synonym Of The Day", "Word Of The Year"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(0).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.className("otd-item-wrapper__label"), "WORD OF THE DAY"));


        element = driver.findElement(By.cssSelector("[title='Search for:']"));
        element.sendKeys("micro");
        element.submit();
        wait.until(ExpectedConditions.textToBe(By.tagName("h2"), "Search Results for: micro"));
        List<WebElement> articles = driver.findElements(By.className("article__content"));
        for (int i = 0; i < 6; i++) {
            assertTrue(articles.get(i).getText().toLowerCase(Locale.ROOT).contains("micro"));
        }
    }

    /**
     * SK_94
     * Tamar
     * HTML Refers to SK_1, SK_17, SK_41
     */
    @Test
    public void SK_94_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//*[text()='WORD OF THE DAY']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList
                ("Word Of The Day", "Synonym Of The Day", "Word Of The Year"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(0).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.className("otd-item-wrapper__label"), "WORD OF THE DAY"));

        String word = driver.findElement(By.className("otd-item-headword__word")).getText();
        driver.findElement(By.xpath("//a[contains(text(), 'Look it up')]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("top-definitions-section")));
        assertEquals(word, driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_95
     * Tamar
     * HTML Refers to SK_1, SK_41
     */
    @Test
    public void SK_95_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//*[text()='WORD OF THE DAY']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList
                ("Word Of The Day", "Synonym Of The Day", "Word Of The Year"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(0).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.className("otd-item-wrapper__label"), "WORD OF THE DAY"));

        driver.findElement(By.cssSelector("[data-cy='otd-podcast-play']")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.className("current-time"), "00:00")));
    }

    /**
     * SK_96
     * Tamar
     * HTML Refers to SK_1, SK_14, SK_36
     */
    @Test
    public void SK_96_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//*[text()='WORD OF THE DAY']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList
                ("Word Of The Day", "Synonym Of The Day", "Word Of The Year"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(1).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.className("otd-item-wrapper__label"), "SYNONYM OF THE DAY"));

        String word = driver.findElement(By.tagName("h2")).getText();
        word = word.substring(word.lastIndexOf(' ') + 1);
        driver.findElement(By.xpath("//a[contains(text(), 'See all synonyms for')]")).click();

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), word));
        assertTrue(driver.findElement(By.id("meanings")).isDisplayed());
    }

    /**
     * SK_97
     * Tamar
     * HTML Refers to SK_1, SK_36
     */
    @Test
    public void SK_97_Tamar() throws InterruptedException {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.xpath("//*[text()='WORD OF THE DAY']"));
        element.click();
        assertNotEquals("none", element.findElement(By.xpath(".//following-sibling::*[2]")).getCssValue("display"));
        List<WebElement> titleElements = element.findElements(By.xpath(".//following-sibling::*//ul//a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList
                ("Word Of The Day", "Synonym Of The Day", "Word Of The Year"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        titleElements.get(1).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.className("otd-item-wrapper__label"), "SYNONYM OF THE DAY"));

        element = driver.findElement(By.cssSelector(".otd-widget__nav--sotd a"));
        String dateNumber = element.findElement(By.tagName("span")).getText().split(" ")[1];
        element.click();
        Thread.sleep(1000);
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
                , driver.findElement(By.xpath("//*[@class='otd-item-headword__date']/*[contains(text(), '" + dateNumber + "')]"))));
    }

    /**
     * SK_98
     * Tamar
     * HTML Refers to SK_1, SK_104
     */
    @Test
    public void SK_98_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.id("searchbar_input"));
        element.sendKeys("100");
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "0 results for 100"));
    }

    /**
     * SK_99
     * Tamar
     * HTML Refers to SK_1, SK_14, SK_63
     */
    @Test
    public void SK_99_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.id("searchbar_input"));
        element.sendKeys("happy");
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "happy"));
        assertTrue(driver.findElement(By.id("meanings")).isDisplayed());

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.tagName("footer"))).perform();
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
                , driver.findElement(By.xpath("//*[contains(text(), 'Browse the')]"))));
        List<WebElement> letters = driver.findElements(By.cssSelector("[id^='azFooter']"));
        assertEquals("#", letters.get(0).getText());
        for (int i = 1; i < letters.size(); i++) {
            assertEquals(String.valueOf((char) (i + 64)), letters.get(i).getText());
        }

        letters.get(3).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "BROWSE THESAURUS: LETTER \"C\""));
    }

    /**
     * SK_100
     * Tamar
     * HTML Refers to SK_1, SK_14, SK_100
     */
    @Test
    public void SK_100_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.id("searchbar_input"));
        element.sendKeys("happy");
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "happy"));
        assertTrue(driver.findElement(By.id("meanings")).isDisplayed());

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.tagName("footer"))).perform();
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
                , driver.findElement(By.xpath("//*[contains(text(), 'Browse by')]"))));
        List<WebElement> categories = driver.findElements(By.cssSelector("[id^='atwFooter']"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Slang", "Emoji", "Acronyms", "Pop Culture", "More"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : categories) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        categories.get(1).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("sitemap")));
        assertEquals("EMOJI", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_104
     * Tamar
     * HTML Refers to SK_1, SK_14, SK_104
     */
    @Test
    public void SK_104_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.id("searchbar_input"));
        element.sendKeys("heppy");
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "0 results for heppy"));
        element = driver.findElement(By.xpath("//a[text()='happy']"));
        assertTrue(element.isDisplayed());
        element.click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "happy"));
        assertTrue(driver.findElement(By.id("meanings")).isDisplayed());
    }

    /**
     * SK_105
     * Tamar
     * HTML Refers to SK_1, SK_14
     */
    @Test
    public void SK_105_Tamar() {
        driver.get("https://www.thesaurus.com/");
        element = driver.findElement(By.id("searchbar_input"));
        element.sendKeys("10");
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "10"));
        assertTrue(driver.findElement(By.id("meanings")).isDisplayed());
    }

    /**
     * SK_1
     * Abhijit
     * Html refer to SK_1
     */
    @Test
    public void SK_1_Abhijit() throws InterruptedException {
        driver.get("https://www.thesaurus.com/");
        Thread.sleep(10000);
        driver.findElement(By.xpath("//span[@aria-label='Sign up for an account']")).click();
        Thread.sleep(10000);
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(05,700)");
        Thread.sleep(10000);

    }

    /**
     * SK_2
     * Abhijit
     * Html refer to SK_1
     */

    @Test
    public void SK_2_Abhijit() throws InterruptedException {
        driver.get("https://www.thesaurus.com/");
        Thread.sleep(10000);
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(05,1400)");
        Thread.sleep(10000);

    }

    /**
     * SK_3
     * Abhijit
     * Html refer to SK_1
     */

    @Test
    public void SK_3_Abhijit() throws InterruptedException {
        driver.get("https://www.thesaurus.com/");
        Thread.sleep(10000);
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(05,2300)");
        Thread.sleep(10000);

    }

}
