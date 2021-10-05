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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

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
        assertTrue(driver.findElement(By.cssSelector(".logo > a")).getText().contains("The Free Dictionary"));
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
        assertTrue(driver.findElement(By.cssSelector(".logo > a")).getText().contains("The Free Dictionary"));
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

    /**
     * SK_3
     * Tamar
     * HTML refers to SK_3
     */
    @Test
    public void SK_3_Tamar() {
        driver.get("https://www.thefreedictionary.com/");
        assertTrue(driver.findElement(By.cssSelector(".logo > a")).getText().contains("The Free Dictionary"));
        driver.findElement(By.id("f1_tfd")).click();
        List<WebElement> radioElements = driver.findElements(By.cssSelector("#lstTFD li"));
        List<String> expectedTitles = new LinkedList<>(Arrays.asList("Word / Article", "Starts with", "Ends with", "Text"));
        List<String> actualTitles = new LinkedList<>();
        for (WebElement element : radioElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        driver.findElement(By.id("f1_google")).click();
        assertEquals("visibility: hidden;", driver.findElement(By.id("f1_TFDBy")).getAttribute("style"));
    }

    /**
     * SK_4
     * Tamar
     * HTML refers to SK_4
     */
    @Test
    public void SK_4_Tamar() {
        driver.get("https://www.thefreedictionary.com/");
        assertTrue(driver.findElement(By.cssSelector(".logo > a")).getText().contains("The Free Dictionary"));
        driver.findElement(By.id("f1_tfd")).click();
        driver.findElement(By.id("f1_tfd_word")).click();
        driver.findElement(By.cssSelector("[type='search']")).sendKeys("customize");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(By.className("sayt"), "style", "display: none")));
        Actions actions = new Actions(driver);
        element = driver.findElement(By.cssSelector("[type='search']"));
        actions.moveByOffset(0, 0).moveToElement(element).click(element).perform();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".sayt > *")));
        wait.until(ExpectedConditions.textToBe(By.id("SAYTk0"), "customize"));
        assertEquals("Customize Look and Feel", driver.findElement(By.id("SAYTk1")).getText());
    }

    /**
     * SK_5
     * Tamar
     * HTML refers to SK_4, SK_5
     */
    @Test
    public void SK_5_Tamar() {
        driver.get("https://www.thefreedictionary.com/");
        assertTrue(driver.findElement(By.cssSelector(".logo > a")).getText().contains("The Free Dictionary"));
        driver.findElement(By.id("f1_tfd")).click();
        driver.findElement(By.id("f1_tfd_word")).click();
        driver.findElement(By.cssSelector("[type='search']")).sendKeys("customize");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(By.className("sayt"), "style", "display: none")));
        Actions actions = new Actions(driver);
        element = driver.findElement(By.cssSelector("[type='search']"));
        actions.moveByOffset(0, 0).moveToElement(element).click(element).perform();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".sayt > *")));
        wait.until(ExpectedConditions.textToBe(By.id("SAYTk0"), "customize"));
        assertEquals("Customize Look and Feel", driver.findElement(By.id("SAYTk1")).getText());
        driver.findElement(By.cssSelector("[type='submit']")).click();
        assertEquals("customize", driver.findElement(By.tagName("h1")).getText());
        assertEquals("To make or alter to individual or personal specifications: customize a van.",
                driver.findElement(By.className("ds-single")).getText());
    }

    /**
     * SK_6
     * Tamar
     * HTML refers to SK_4, SK_5
     */
    @Test
    public void SK_6_Tamar() {
        driver.get("https://www.thefreedictionary.com/");
        assertTrue(driver.findElement(By.cssSelector(".logo > a")).getText().contains("The Free Dictionary"));
        driver.findElement(By.id("f1_tfd")).click();
        driver.findElement(By.id("f1_tfd_word")).click();
        driver.findElement(By.cssSelector("[type='search']")).sendKeys("customize");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(By.className("sayt"), "style", "display: none")));
        Actions actions = new Actions(driver);
        element = driver.findElement(By.cssSelector("[type='search']"));
        actions.moveByOffset(0, 0).moveToElement(element).click(element).perform();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".sayt > *")));
        wait.until(ExpectedConditions.textToBe(By.id("SAYTk0"), "customize"));
        assertEquals("Customize Look and Feel", driver.findElement(By.id("SAYTk1")).getText());
        driver.findElement(By.cssSelector("[type='submit']")).click();
        assertEquals("customize", driver.findElement(By.tagName("h1")).getText());
        assertEquals("To make or alter to individual or personal specifications: customize a van.",
                driver.findElement(By.className("ds-single")).getText());

        driver.findElement(By.cssSelector("[title='Thesaurus']")).click();
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
                , driver.findElement(By.id("Thesaurus"))));
    }

    /**
     * SK_7
     * Tamar
     * HTML refers to SK_4, SK_5
     */
    @Test
    public void SK_7_Tamar() {
        driver.get("https://www.thefreedictionary.com/");
        assertTrue(driver.findElement(By.cssSelector(".logo > a")).getText().contains("The Free Dictionary"));
        driver.findElement(By.id("f1_tfd")).click();
        driver.findElement(By.id("f1_tfd_word")).click();
        driver.findElement(By.cssSelector("[type='search']")).sendKeys("customize");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(By.className("sayt"), "style", "display: none")));
        Actions actions = new Actions(driver);
        element = driver.findElement(By.cssSelector("[type='search']"));
        actions.moveByOffset(0, 0).moveToElement(element).click(element).perform();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".sayt > *")));
        wait.until(ExpectedConditions.textToBe(By.id("SAYTk0"), "customize"));
        assertEquals("Customize Look and Feel", driver.findElement(By.id("SAYTk1")).getText());
        driver.findElement(By.cssSelector("[type='submit']")).click();
        assertEquals("customize", driver.findElement(By.tagName("h1")).getText());
        assertEquals("To make or alter to individual or personal specifications: customize a van.",
                driver.findElement(By.className("ds-single")).getText());

        driver.findElement(By.cssSelector("[title='Translations']")).click();
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
                , driver.findElement(By.id("Translations"))));
    }

    /**
     * SK_8
     * Tamar
     * HTML refers to SK_8
     */
    @Test
    public void SK_8_Tamar() {
        driver.get("https://www.thefreedictionary.com/");
        assertTrue(driver.findElement(By.cssSelector(".logo > a")).getText().contains("The Free Dictionary"));
        driver.findElement(By.id("f1_tfd")).click();
        By keyboardLocator = By.xpath("//a[text()='Keyboard']");
        assertTrue(driver.findElement(keyboardLocator).isDisplayed());
        driver.findElement(keyboardLocator).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(By.id("keyboard"), "style", "display: none")));
        driver.findElement(By.xpath("//td[text()='P']/ancestor::*[@onclick]")).click();
        driver.findElement(By.xpath("//td[text()='L']/ancestor::*[@onclick]")).click();
        driver.findElement(By.xpath("//td[text()='U']/ancestor::*[@onclick]")).click();
        driver.findElement(By.xpath("//td[text()='G']/ancestor::*[@onclick]")).click();
        driver.findElement(By.xpath("//*[@onclick and text()='Enter']")).click();
        driver.findElement(By.cssSelector("[type='submit']")).click();
        assertEquals("plug", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_9
     * Tamar
     * HTML refers to SK_3, SK_9
     */
    @Test
    public void SK_9_Tamar() {
        driver.get("https://www.thefreedictionary.com/");
        assertTrue(driver.findElement(By.cssSelector(".logo > a")).getText().contains("The Free Dictionary"));
        driver.findElement(By.id("f1_google")).click();
        driver.findElement(By.cssSelector("[type='search']")).sendKeys("Clogged");
        driver.findElement(By.cssSelector("[type='submit']")).click();
        assertTrue(driver.findElement(By.cssSelector("[name='googleSearchFrame']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[name='googleSearchFrame']")).getAttribute("src").contains("Clogged"));

    }
}
