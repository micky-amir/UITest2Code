package websites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.text.html.HTML;
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
