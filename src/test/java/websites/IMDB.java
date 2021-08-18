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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class IMDB {

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
     */
    @Test
    public void SK_1_Tamar() throws InterruptedException {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        By createAccountLocator = By.xpath("//a[text()='Create a New Account']");
        assertTrue(driver.findElement(createAccountLocator).isDisplayed());
        driver.findElement(createAccountLocator).click();
        driver.findElement(By.id("ap_customer_name")).sendKeys("Tamar");
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("ap_password_check")).sendKeys("imdbtest");
        driver.findElement(By.id("continue")).click();
        Thread.sleep(20000); // manually solve captcha
        driver.findElement(By.className("a-button-input")).click();
        Thread.sleep(20000); // manually enter otp
        driver.findElement(By.className("a-button-input")).click();
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Toggle Acount Menu'] .ipc-button__text")));
        assertEquals("Tamar", element.getText());
    }

    /**
     * SK_2
     * Tamar
     */
    @Test
    public void SK_2_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Toggle Acount Menu'] .ipc-button__text")));
        assertEquals("Tamar", element.getText());
    }

    /**
     * SK_3
     * Tamar
     */
    @Test
    public void SK_3_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        WebDriverWait wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//*[@id='featured-today']/../*)[3]")));
        element = driver.findElement(By.xpath("(//*[@id='featured-today']/../*)[3]")); // section under featured today
        assertTrue(element.findElement(By.xpath("./descendant::img")).isDisplayed()); // image
        assertTrue(element.findElement(By.xpath(".//a[href=contains(text(), 'movies')]")).isDisplayed());
        assertTrue(element.findElement(By.xpath(".//a[href=contains(text(), 'movies')]")).isDisplayed());
        List<WebElement> items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(@class, 'WhatToWatch__WhatToWatchDiv-e8z2cy-1')]/../descendant::div[contains(@class, 'ipc-poster-card--baseAlt')]")));
        assertTrue(items.size() > 1);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".fan-picks .ipc-icon.ipc-icon--chevron-right-inline.ipc-icon--inline.ipc-pager-icon"))).click();
        By wishListLocator = By.xpath("//*[text()='Cruella']/../following-sibling::*/button");
        wait.until(ExpectedConditions.presenceOfElementLocated(wishListLocator));
        assertTrue(driver.findElement(wishListLocator).isDisplayed());
        wait.until(ExpectedConditions.elementToBeClickable(wishListLocator)).click();
        assertEquals("remove from watchlist", driver.findElement(By.xpath("//img[@alt='Cruella']/../preceding-sibling::*")).getAttribute("aria-label"));
    }
}
