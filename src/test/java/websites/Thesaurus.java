package websites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
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
