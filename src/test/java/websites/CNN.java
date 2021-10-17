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

public class CNN {
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
     * CNN01
     * Tamar
     */
    @Test
    public void CNN01_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("[data-test='searchButton']")).click();
        element = driver.findElement(By.id("header-search-bar"));
        assertEquals(element, driver.switchTo().activeElement());
        element.sendKeys("India");
        driver.findElement(By.cssSelector("button[aria-label='Search']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[text()='Everything']/preceding-sibling::*[@checked]")));
        assertEquals("India", driver.findElement(By.cssSelector(".cnn-search__results-count strong")).getText());
        List<WebElement> resultElements = driver.findElements(By.className("cnn-search__result-contents"));
        for (WebElement result : resultElements) {
            assertTrue(result.getText().toLowerCase(Locale.ROOT).contains("india"));
        }
        driver.findElement(By.id("left_sport")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.className("cnn-search__results-count"),
                driver.findElement(By.className("cnn-search__results-count")).getText())));
        List<WebElement> linkElements = driver.findElements(By.cssSelector(".cnn-search__result-headline a"));
        for (WebElement link : linkElements) {
            String allResultText = link.findElement(By.xpath("./../..")).getText().toLowerCase(Locale.ROOT);
            assertTrue(link.getAttribute("href").contains("/sport/") || allResultText.contains("sport"));
            assertTrue(allResultText.contains("india"));
        }
    }

    /**
     * CNN02
     * Tamar
     * HTML refer to CNN01, CNN02
     */
    @Test
    public void CNN02_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("a[name='videos']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".live-videos")));
        driver.findElement(By.xpath("//a[text()='Live TV']")).click();
        assertEquals("This product isn't currently available in your area.", driver.findElement(By.tagName("h1")).getText());
        By locator = By.xpath("//a[contains(text(), 'Back to the headlines')]");
        assertTrue(driver.findElement(locator).isDisplayed());
        driver.findElement(locator).click();
        assertTrue(driver.getTitle().contains("World News"));
    }

    /**
     * CNN03
     * Tamar
     * HTML refer to CNN01
     */
    @Test
    public void CNN03_Tamar() {
        driver.get("https://edition.cnn.com/");
        String baseUrl = driver.getCurrentUrl();
        assertTrue(driver.getTitle().contains("World News"));

        Map<String, List<String>> languagesInfo = new HashMap<String, List<String>>() {{
            put("U.S.", new ArrayList<>(Arrays.asList("https://us.cnn.com/?hpt=header_edition-picker",
                    "//*[@data-analytics='header_top-nav' and text()='World']")));
            put("Arabic", new ArrayList<>(Arrays.asList("https://arabic.cnn.com/?hpt=header_edition-picker",
                    "//*[@id='navbar']//*[text()='منوعات']")));
            put("Español", new ArrayList<>(Arrays.asList("https://cnnespanol.cnn.com/?hpt=header_edition-picker",
                    "//*[@id='nav-header']//*[text()='Mundo']")));
        }};
        WebDriverWait wait = new WebDriverWait(driver, 10);
        for (String i : languagesInfo.keySet()) {
            driver.findElement(By.id("edition-picker-toggle-desktop")).click();
            if (i.equals("U.S.")) {
                element = driver.findElement(
                        By.xpath("//*[contains(@class, 'indexes__Dropdown')]"));
                assertNotEquals("none", element.getCssValue("display"));
                List<WebElement> optionElements = element.findElements(By.tagName("a"));
                List<String> expectedTitles = new ArrayList<>(
                        Arrays.asList("U.S.", "International", "Arabic", "Español"));
                List<String> actualTitles = new ArrayList<>();
                for (WebElement element : optionElements) {
                    actualTitles.add(element.getText());
                }
                assertEquals(expectedTitles, actualTitles);
            }
            driver.findElement(By.xpath("//*[contains(@class, 'indexes__Dropdown')]//a[text()='" + i + "']")).click();
            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(baseUrl)));
            assertEquals(languagesInfo.get(i).get(0), driver.getCurrentUrl());
            assertTrue(driver.findElement(By.xpath(languagesInfo.get(i).get(1))).isDisplayed());
            driver.navigate().back();
        }
    }

    /**
     * CNN04
     * Tamar
     * HTML refer to CNN01, CNN04
     */
    @Test
    public void CNN04_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("a[name='world']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "World"));
        element = driver.findElement(By.cssSelector("[data-zone-label='Featured sections']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = driver.findElement(By.cssSelector("[data-analytics='Travel_list-hierarchical-xs_article_']"));
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
        element.findElement(By.tagName("img")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("Header__logoTravel")));
        List<WebElement> optionElements = driver.findElements(By.cssSelector(".Header__section"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("DESTINATIONS", "FOOD & DRINK", "NEWS", "STAY", "VIDEO"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * CNN05
     * Tamar
     * HTML refer to CNN01, CNN04, CNN05
     */
    @Test
    public void CNN05_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("a[name='travel']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("Header__logoTravel")));
        List<WebElement> optionElements = driver.findElements(By.cssSelector(".Header__section"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("DESTINATIONS", "FOOD & DRINK", "NEWS", "STAY", "VIDEO"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        optionElements.get(0).click();
        wait.until(ExpectedConditions.textToBe(By.className("Breadcrumb__label"), "DESTINATIONS"));
        List<WebElement> countriesElements = driver.findElements(By.className("Destinations__link"));
        for (int i = 1; i < countriesElements.size(); i++) {
            assertTrue(countriesElements.get(i-1).getText().compareTo(countriesElements.get(i).getText()) < 0);
        }
        String countryName = countriesElements.get(0).getText();
        countriesElements.get(0).click();
        assertEquals(countryName, driver.findElement(By.tagName("h1")).getText());
    }
}
