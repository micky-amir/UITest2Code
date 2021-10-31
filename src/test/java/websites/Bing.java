package websites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

public class Bing {
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
     * BING01
     * Tamar
     */
    @Test
    public void BING01_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("h1 > *"), "aria-label", "Bing"));
        assertTrue(driver.findElement(By.id("images")).isDisplayed());
        assertTrue(driver.findElement(By.id("video")).isDisplayed());
    }

    /**
     * BING02
     * Tamar
     * HTML refers to BING01
     */
    @Test
    public void BING02_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("h1 > *"), "aria-label", "Bing"));
        assertTrue(driver.findElement(By.id("images")).isDisplayed());
        assertTrue(driver.findElement(By.id("video")).isDisplayed());
        for (int i = 0; i < 2; i++) {
            By locator = By.cssSelector("[aria-label='Settings and quick links']");
            driver.findElement(locator).click();
            wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
            assertTrue(driver.findElement(By.xpath("//*[text()='Customize your homepage']")).isDisplayed());
            assertTrue(driver.findElement(By.xpath("//*[text()='Show menu bar']")).isDisplayed());
            locator = By.xpath("//*[text()='Show menu bar']//following-sibling::*");
            assertTrue(driver.findElement(locator).getAttribute("class").contains("toggle_ctrl"));
            driver.findElement(locator).click();
            assertEquals("problem in" + i, i != 0, driver.findElement(By.xpath("//*[text()='Show menu bar']//following-sibling::*//*"))
                    .getAttribute("class").contains("toggle_on"));
            locator = By.cssSelector("[aria-label='Settings and quick links']");
            driver.findElement(locator).click();
            wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));
            assertEquals(i, driver.findElements(By.id("images")).size());
            assertTrue(driver.findElements(By.id("video")).size() >= i);
        }
    }

    /**
     * BING04
     * Tamar
     * HTML refers to BING04
     */
    @Test
    public void BING04_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("h1 > *"), "aria-label", "Bing"));
        assertTrue(driver.findElement(By.id("images")).isDisplayed());
        assertTrue(driver.findElement(By.id("video")).isDisplayed());
        for (int i = 0; i < 2; i++) {
            By locator = By.cssSelector("[aria-label='Settings and quick links']");
            driver.findElement(locator).click();
            wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
            assertTrue(driver.findElement(By.xpath("//*[text()='Customize your homepage']")).isDisplayed());
            assertTrue(driver.findElement(By.xpath("//*[text()='Show homepage image']")).isDisplayed());
            locator = By.xpath("//*[text()='Show homepage image']//following-sibling::*");
            assertTrue(driver.findElement(locator).getAttribute("class").contains("toggle_ctrl"));
            driver.findElement(locator).click();
            assertEquals("problem in" + i, i != 0, driver.findElement(By.xpath("//*[text()='Show homepage image']//following-sibling::*//*"))
                    .getAttribute("class").contains("toggle_on"));
            locator = By.cssSelector("[aria-label='Settings and quick links']");
            driver.findElement(locator).click();
            wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));
            assertEquals(i == 0, driver.findElement(By.cssSelector(".hp_body"))
                    .getAttribute("class").contains("no_image"));
        }
    }

    /**
     * BING08
     * Tamar
     * HTML refers to BING01, BING08
     */
    @Test
    public void BING08_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        element = driver.findElement(By.id("hbsettings"));
        assertTrue(element.isDisplayed());
        element.click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        element = driver.findElement(By.cssSelector("#hbsettree > a[role='menuitem']"));
        assertTrue(element.isDisplayed());
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@for='setlang']")));
        driver.findElement(By.xpath("//*[contains(text(), 'Français')]//preceding-sibling::a")).click();
        wait.until(ExpectedConditions.textToBe(By.id("id_s"), "Connexion"));
        driver.navigate().back();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@for='setlang']")));
        driver.findElement(By.xpath("//*[contains(text(), 'English')]//preceding-sibling::a")).click();
        wait.until(ExpectedConditions.textToBe(By.id("id_s"), "Sign in"));
    }
}
