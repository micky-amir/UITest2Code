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

public class TheFreeDictionary {
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
    public void SK_1_Tamar() {
        driver.get("https://www.thefreedictionary.com/");
        driver.findElement(By.xpath("//a[text()='Tools']")).click();
        assertTrue(driver.findElement(By.id("popupBox")).getAttribute("class").contains("open-close-active"));
        assertTrue(driver.findElements(By.cssSelector(".popup-holder > *")).size() > 1);
        element = driver.findElement(By.cssSelector("#popupBox .language-block .select-holder"));
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).click(element).perform();
        assertEquals("English", element.findElement(By.cssSelector("[selected]")).getText());
        element.findElement(By.xpath(".//option[text()='Italiano']")).click();
        assertEquals("it", driver.findElement(By.tagName("body")).getAttribute("lang"));
    }

    /**
     * SK_2
     * Tamar
     * HTML refers to SK_1
     */
    @Test
    public void SK_2_Tamar() {
        driver.get("https://www.thefreedictionary.com/");
        driver.findElement(By.xpath("//a[text()='Tools']")).click();
        assertTrue(driver.findElement(By.id("popupBox")).getAttribute("class").contains("open-close-active"));
        assertTrue(driver.findElements(By.cssSelector(".popup-holder > *")).size() > 1);
        List<WebElement> fontsElements = driver.findElements(By.cssSelector("#popupBox .text-size a"));
        assertEquals(4, fontsElements.size());
        for (int i = 1; i < fontsElements.size(); i++) {
            String onClickCurrent = fontsElements.get(i).getAttribute("onclick");
            onClickCurrent = onClickCurrent.substring(onClickCurrent.indexOf('(') + 1, onClickCurrent.length() - 1);
            String onClickPrevious = fontsElements.get(i - 1).getAttribute("onclick");
            onClickPrevious = onClickPrevious.substring(onClickPrevious.indexOf('(') + 1, onClickPrevious.length() - 1);
            assertTrue(Integer.parseInt(onClickCurrent) > Integer.parseInt(onClickPrevious));
        }
        fontsElements.get(0).click();
        assertEquals("font-size: 11px;", driver.findElement(By.tagName("html")).getAttribute("style"));
    }
}
