package websites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
}
