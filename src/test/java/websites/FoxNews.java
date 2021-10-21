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
}
