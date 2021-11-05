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
import java.util.Locale;

import static org.junit.Assert.*;

public class Bing2
{
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
     * BING27
     * Mika
     * HTML refer to
     */
    @Test
    public void BING27_Mika()
    {
        String textToShop = "Jacket";
        String[] sectionsToCheck = new String[] { "Shopping_Home_key", "Departments_Key", "Stores_key",
                "editors_picks_key", "dealstab_key", "trending_products_key", "price_drops_key", "my_collections_key" };
        String sectionToChoose = "price_drops_key";
        String titleToCheck = "Recent Price Drops";

        driver.get("https://www.bing.com");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[text()='Country/Region' or text()='ארץ/אזור']/../.."))).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()='United States - English' or text()='ארצות הברית - אנגלית']")));
        action.moveToElement(element).click().perform();
        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        driver.findElement(By.xpath("//*[text()='Language' or text()='שפה']/../..")).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//a[text()='אנגלית']")));
        action.moveToElement(element).click().perform();

        wait.until(ExpectedConditions.urlToBe("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("tile_grp")));

        driver.findElement(By.xpath("//*[@aria-label='Enter your search term']")).sendKeys(textToShop);
        driver.findElement(By.id("search_icon")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=' b_active']")));
        assertEquals("ALL", element.getText());
        driver.findElement(By.linkText("SHOPPING")).click();

        for (String sectionId : sectionsToCheck)
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id(sectionId)));
        driver.findElement(By.id(sectionToChoose)).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()='" + titleToCheck + "']")));
    }

    /**
     * BING28
     * Mika
     * HTML refer to
     */
    @Test
    public void BING28_Mika()
    {
        String textToShop = "Jacket";
        String[] sectionsToCheck = new String[] { "Shopping_Home_key", "Departments_Key", "Stores_key",
                "editors_picks_key", "dealstab_key", "trending_products_key", "price_drops_key", "my_collections_key" };
        String sectionToChoose = "price_drops_key";
        String titleToCheck = "Recent Price Drops";

        driver.get("https://www.bing.com");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[text()='Country/Region' or text()='ארץ/אזור']/../.."))).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()='United States - English' or text()='ארצות הברית - אנגלית']")));
        action.moveToElement(element).click().perform();
        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        driver.findElement(By.xpath("//*[text()='Language' or text()='שפה']/../..")).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//a[text()='אנגלית']")));
        action.moveToElement(element).click().perform();

        wait.until(ExpectedConditions.urlToBe("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("tile_grp")));

        driver.findElement(By.xpath("//*[@aria-label='Enter your search term']")).sendKeys(textToShop);
        driver.findElement(By.id("search_icon")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=' b_active']")));
        assertEquals("ALL", element.getText());
        driver.findElement(By.linkText("SHOPPING")).click();

        for (String sectionId : sectionsToCheck)
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id(sectionId)));
        driver.findElement(By.id(sectionToChoose)).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()='" + titleToCheck + "']")));
    }
}