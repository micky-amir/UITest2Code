package websites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
     */
    @Test
    public void FXN05_Tamar() {
        driver.get("https://www.foxnews.com/");
        assertTrue(driver.findElement(By.cssSelector("[aria-label='hot topics']")).isDisplayed());
        assertTrue(driver.findElement(By.className("market-data")).isDisplayed());
        driver.findElement(By.cssSelector(".js-exclusive-clips a")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".video-player iframe[aria-label='MVPD Picker']")));
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
        driver.findElement(By.className("cancel")).click();
        element.click();
        wait.until(ExpectedConditions.textToBe(locator, originalCount));
    }
}
