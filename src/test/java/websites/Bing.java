package websites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());

        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("h1 > *"), "aria-label", "Bing"));
        assertTrue(driver.findElement(By.id("images")).isDisplayed());
        assertTrue(driver.findElement(By.id("video")).isDisplayed());
        assertTrue(driver.findElement(By.id("shopping")).isDisplayed());
        element = driver.findElement(By.cssSelector(".vs img"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
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
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("h1 > *"), "aria-label", "Bing"));
        assertTrue(driver.findElement(By.id("images")).isDisplayed());
        assertTrue(driver.findElement(By.id("video")).isDisplayed());
        assertTrue(driver.findElement(By.id("shopping")).isDisplayed());
        for (int i = 0; i < 2; i++) {
            driver.findElement(locator).click();
            wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Customize your homepage']")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Show menu bar']")));
            locator = By.xpath("//*[text()='Show menu bar']//following-sibling::*");
            assertTrue(driver.findElement(locator).getAttribute("class").contains("toggle_ctrl"));
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
            assertEquals("problem in" + i, i != 0, driver.findElement(By.xpath("//*[text()='Show menu bar']//following-sibling::*//*"))
                    .getAttribute("class").contains("toggle_on"));
            locator = By.cssSelector("[aria-label='Settings and quick links']");
            driver.findElement(locator).click();
            wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));
            assertEquals(i, driver.findElements(By.id("images")).size());
            assertTrue(driver.findElements(By.id("video")).size() >= i);
            assertTrue(driver.findElements(By.id("shopping")).size() >= i);
        }
    }

    /**
     * BING03
     * Tamar
     * HTML refers to BING01
     */
    @Test
    public void BING03_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("h1 > *"), "aria-label", "Bing"));
        assertTrue(driver.findElement(By.id("images")).isDisplayed());
        assertTrue(driver.findElement(By.id("video")).isDisplayed());
        assertTrue(driver.findElement(By.id("shopping")).isDisplayed());
        for (int i = 0; i < 2; i++) {
            driver.findElement(locator).click();
            wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Customize your homepage']")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Show news and interests']")));
            locator = By.xpath("//*[text()='Show news and interests']//following-sibling::*");
            assertTrue(driver.findElement(locator).getAttribute("class").contains("toggle_ctrl"));
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
            assertEquals("problem in" + i, i != 0, driver.findElement(By.xpath("//*[text()='Show news and interests']//following-sibling::*//*"))
                    .getAttribute("class").contains("toggle_on"));
            locator = By.cssSelector("[aria-label='Settings and quick links']");
            driver.findElement(locator).click();
            wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));
            assertEquals(i != 0, ((JavascriptExecutor) driver).executeScript(
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
                    , driver.findElement(By.cssSelector(".vs img"))));
        }
    }

    /**
     * BING04
     * Tamar
     * HTML refers to BING01
     */
    @Test
    public void BING04_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("h1 > *"), "aria-label", "Bing"));
        assertTrue(driver.findElement(By.id("images")).isDisplayed());
        assertTrue(driver.findElement(By.id("video")).isDisplayed());
        assertTrue(driver.findElement(By.id("shopping")).isDisplayed());
        for (int i = 0; i < 2; i++) {
            driver.findElement(locator).click();
            wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Customize your homepage']")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Show homepage image']")));
            locator = By.xpath("//*[text()='Show homepage image']//following-sibling::*");
            assertTrue(driver.findElement(locator).getAttribute("class").contains("toggle_ctrl"));
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
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
     * BING05
     * Tamar
     * HTML refers to BING01, BING05
     */
    @Test
    public void BING05_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        List<WebElement> navElements = driver.findElements(By.cssSelector("[aria-label='Main menu'] a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("ALL", "NEWS", "IMAGES", "VIDEOS", "MAPS", "SHOPPING"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        String url = driver.getCurrentUrl();
        driver.navigate().back();
        assertTrue(driver.getCurrentUrl().contains("https://www.bing.com/") && !driver.getCurrentUrl().equals(url));
    }

    /**
     * BING06
     * Tamar
     * HTML refers to BING01
     */
    @Test
    public void BING06_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(1)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//*[contains(text(), 'English')]//preceding-sibling::a"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".musCard .icon_text")));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
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
                , driver.findElement(By.className("musCardCont"))));
        assertTrue(driver.findElement(By.cssSelector("[aria-label='Share to Facebook']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[aria-label='Share to Twitter']")).isDisplayed());
        element = driver.findElement(By.cssSelector(".share .downloadLink"));
        assertTrue(element.isDisplayed());
        actions.moveToElement(element).perform();
        assertEquals("Download this image. Use of this image is restricted to wallpaper only.", element.getAttribute("title"));
    }

    /**
     * BING07
     * Tamar
     * HTML refers to BING01, BING09
     */
    @Test
    public void BING07_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".musCard .icon_text")));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
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
                , driver.findElement(By.className("musCardCont"))));
        assertTrue(driver.findElement(By.cssSelector("[aria-label='Share to Facebook']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[aria-label='Share to Twitter']")).isDisplayed());
        element = driver.findElement(By.cssSelector(".musCardCont .title"));
        assertTrue(element.isDisplayed());
        element.click();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        List<WebElement> navElements = driver.findElements(By.cssSelector("[aria-label='Main menu'] > ul > [data-menuurl] > a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("ALL", "NEWS", "IMAGES", "VIDEOS", "MAPS", "SHOPPING"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertTrue(actualTitles.containsAll(expectedTitles));
    }

    /**
     * BING08
     * Tamar
     * HTML refers to BING01
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

    /**
     * BING09
     * Tamar
     * HTML refers to BING01, BING05, BING09
     */
    @Test
    public void BING09_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        List<WebElement> navElements = driver.findElements(By.cssSelector("[aria-label='Main menu'] a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("ALL", "NEWS", "IMAGES", "VIDEOS", "MAPS", "SHOPPING"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        driver.findElement(By.className("b_searchboxSubmit")).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//h2[text()='Trending on Bing']"), 0));
    }

    /**
     * BING10
     * Tamar
     * HTML refers to BING01, BING05, BING09
     */
    @Test
    public void BING10_Tamar() throws InterruptedException {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        List<WebElement> navElements = driver.findElements(By.cssSelector("[aria-label='Main menu'] a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("ALL", "NEWS", "IMAGES", "VIDEOS", "MAPS", "SHOPPING"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);

        driver.findElement(By.className("b_searchboxSubmit")).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("sb_count")));
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[aria-label='Filtered by Any time']")));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        actions.moveToElement(element).click().perform();
        wait.until(ExpectedConditions.attributeContains(By.cssSelector("[aria-label='Filtered by Any time']"), "class", "b_selected"));

        expectedTitles.clear();
        actualTitles.clear();
        Thread.sleep(3000);
        List<WebElement> optionElements = wait.until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("#ftrD_Any_time > a"), 5));
        expectedTitles = new ArrayList<>(Arrays.asList("All", "Past 24 hours", "Past week", "Past month", "Past year"));
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        wait.until(ExpectedConditions.textToBe(By.cssSelector("#CustomRangeFilter span"), "Custom range"));
        String originalText = driver.findElement(By.className("sb_count")).getText();
        String originalCount = originalText.split(" ")[0];
        optionElements.get(2).click();

        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.className("sb_count"), originalText)));
        String newCount = driver.findElement(By.className("sb_count")).getText().split(" ")[0];
        assertTrue(Integer.parseInt(originalCount.replace(",", "")) >
                Integer.parseInt(newCount.replace(",", "")));
    }

    /**
     * BING11
     * Tamar
     * HTML refers to BING01, BING05, BING12
     */
    @Test
    public void BING11_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        List<WebElement> navElements = driver.findElements(By.cssSelector("[aria-label='Main menu'] a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("ALL", "NEWS", "IMAGES", "VIDEOS", "MAPS", "SHOPPING"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(1).click();

        expectedTitles.clear();
        actualTitles.clear();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".image.right")));
        List<WebElement> optionElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("a.ntext")));
        expectedTitles = new ArrayList<>(Arrays.asList("Top stories", "Sports", "U.S.", "Local", "World", "Science",
                "Technology", "Entertainment", "Politics", "Business"));
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * BING12
     * Tamar
     * HTML refers to BING01, BING05, BING12
     */
    @Test
    public void BING12_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        List<WebElement> navElements = driver.findElements(By.cssSelector("[aria-label='Main menu'] > ul > [data-menuurl] > a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("ALL", "NEWS", "IMAGES", "VIDEOS", "MAPS", "SHOPPING"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(1).click();

        expectedTitles.clear();
        actualTitles.clear();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".image.right")));
        List<WebElement> optionElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("a.ntext")));
        expectedTitles = new ArrayList<>(Arrays.asList("Top stories", "Sports", "U.S.", "Local", "World", "Science",
                "Technology", "Entertainment", "Politics", "Business"));
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);

        optionElements.get(4).click();
        wait.until(ExpectedConditions.attributeToBe(By.cssSelector(".b_searchbox"), "value", "World"));
        assertTrue(driver.getTitle().contains("World"));
        driver.findElement(By.xpath("//a[text()='Africa']")).click();
        wait.until(ExpectedConditions.attributeToBe(By.cssSelector(".b_searchbox"), "value", "Africa"));
        assertTrue(driver.getTitle().contains("Africa"));
    }

    /**
     * BING13
     * Tamar
     * HTML refers to BING01, BING05, BING13
     */
    @Test
    public void BING13_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        List<WebElement> navElements = driver.findElements(By.cssSelector("[aria-label='Main menu'] > ul > [data-menuurl] > a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("ALL", "NEWS", "IMAGES", "VIDEOS", "MAPS", "SHOPPING"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(2).click();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("imgpt"))).get(0).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("OverlayIFrame")));
        driver.switchTo().frame(element);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".mainImage.current")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Visual Search']")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Share']")).isDisplayed());
        driver.findElement(By.cssSelector(".mainImage .imgContainer img")).click();
        while (true) {
            if (driver.getWindowHandles().size() != 1) break;
        }
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertTrue(driver.findElement(By.tagName("img")).isDisplayed());
    }

    /**
     * BING14
     * Tamar
     * HTML refers to BING01, BING05, BING13, BING14
     */
    @Test
    public void BING14_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        List<WebElement> navElements = driver.findElements(By.cssSelector("[aria-label='Main menu'] > ul > [data-menuurl] > a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("ALL", "NEWS", "IMAGES", "VIDEOS", "MAPS", "SHOPPING"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(2).click();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("imgpt"))).get(0).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("OverlayIFrame")));
        driver.switchTo().frame(element);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".mainImage.current")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Visual Search']")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Share']"))).click();
        wait.until(ExpectedConditions.attributeContains(By.cssSelector("#shdlg[role='dialog']"), "style", "display: block;"));
        assertTrue(driver.findElement(By.cssSelector("[title='Share on Facebook']")).isDisplayed());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("shdlg-close"))).click();
        wait.until(ExpectedConditions.attributeContains(By.cssSelector("#shdlg[role='dialog']"), "style", "display: none;"));
    }

    /**
     * BING15
     * Tamar
     * HTML refers to BING01, BING05, BING13
     */
    @Test
    public void BING15_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        List<WebElement> navElements = driver.findElements(By.cssSelector("[aria-label='Main menu'] > ul > [data-menuurl] > a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("ALL", "NEWS", "IMAGES", "VIDEOS", "MAPS", "SHOPPING"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(2).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='fltIdtCon']//*[contains(text(), 'SafeSearch')]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='fltIdtCon']//*[contains(text(), 'Page titles')]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='fltIdtCon']//*[contains(text(), 'Filter')]")));
        element = driver.findElement(By.id("ftr_ss_d"));
        element.findElement(By.xpath(".//preceding-sibling::*")).click();
        assertTrue(element.getText().contains("Strict"));
        element = driver.findElement(By.id("ftr_ulo_d"));
        element.findElement(By.xpath(".//preceding-sibling::*")).click();
        assertTrue(element.getText().contains("Show"));

        locator = By.cssSelector("[title='Show or hide filters']");
        driver.findElement(locator).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        expectedTitles.clear();
        actualTitles.clear();
        List<WebElement> navFilterElements = driver.findElements(
                By.cssSelector("[aria-label='Search results filters'] [role='button'] > span"));
        expectedTitles = new ArrayList<>(Arrays.asList("Image size", "Color", "Type", "Layout", "People", "Date", "License"));
        for (WebElement element : navFilterElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        List<String> expectedDropdownOptions = new ArrayList<>(Arrays.asList("Small", "Color only", "Clipart", "Square", "Just faces", "Public domain"));
        navFilterElements.remove(5);
        for (int i = 0; i < navFilterElements.size(); i++) {
            element = navFilterElements.get(i);
            element.click();
            wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(element, By.xpath(".//ancestor::li//div//*[contains(text(), '"
                    + expectedDropdownOptions.get(i) + "')]")));
        }
        driver.findElement(locator).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));
    }

    /**
     * BING16
     * Tamar
     * HTML refers to BING01, BING05, BING16
     */
    @Test
    public void BING16_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        List<WebElement> navElements = driver.findElements(By.cssSelector("[aria-label='Main menu'] > ul > [data-menuurl] > a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("ALL", "NEWS", "IMAGES", "VIDEOS", "MAPS", "SHOPPING"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(3).click();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[id*='mc_vtvc_video']"))).get(0).click();
        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("iframe[title='Video player']"), "allow", "autoplay"));
    }

    /**
     * BING17
     * Tamar
     * HTML refers to BING01, BING05, BING17
     */
    @Test
    public void BING17_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        List<WebElement> navElements = driver.findElements(By.cssSelector("[aria-label='Main menu'] > ul > [data-menuurl] > a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("ALL", "NEWS", "IMAGES", "VIDEOS", "MAPS", "SHOPPING"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(4).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("inner-container")));
        expectedTitles.clear();
        actualTitles.clear();
        List<WebElement> optionElements = wait.until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector(".actions  li:not([style='display: none;']) > a .actionLabel"), 5));
        expectedTitles = new ArrayList<>(Arrays.asList("Directions", "Traffic", "Local", "My Places", "More"));
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        optionElements.get(4).findElement(By.xpath(".//ancestor::a")).click();

        wait.until(ExpectedConditions.attributeToBe(By.className("hidePopupButton"), "aria-expanded", "true"));
        expectedTitles.clear();
        actualTitles.clear();
        List<WebElement> dropdownElements = driver.findElements(By.cssSelector(".taskBarPopout li.bm_dropdownEntry"));
        expectedTitles = new ArrayList<>(Arrays.asList("Share", "Print", "Feedback", "Full Screen", "Embed a map", "My Contributions"));
        for (WebElement element : dropdownElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * BING18
     * Tamar
     * HTML refers to BING01, BING05, BING17, BING18
     */
    @Test
    public void BING18_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        List<WebElement> navElements = driver.findElements(By.cssSelector("[aria-label='Main menu'] > ul > [data-menuurl] > a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("ALL", "NEWS", "IMAGES", "VIDEOS", "MAPS", "SHOPPING"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(4).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("b_entityTP")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Nearby']")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Directions']"))).click();

        wait.until(ExpectedConditions.attributeToBe(By.cssSelector(".directionsPopOut a"), "aria-expanded", "true"));
        element = driver.findElement(By.cssSelector(".directionsPopOut .bm_dropdownEntry"));
        assertEquals("Directions to here", element.getText());
        assertEquals("Directions from here", driver.findElement(By.cssSelector(".directionsPopOut .bm_dropdownEntry:nth-child(2)")).getText());
        element.click();
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".cardTitleText h2"), "Directions"));
        wait.until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(By.cssSelector("[title='From']")), "style"));
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(By.cssSelector("[title='To']")), "style")));

        driver.navigate().back();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Nearby']")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Directions']"))).click();
        wait.until(ExpectedConditions.attributeToBe(By.cssSelector(".directionsPopOut a"), "aria-expanded", "true"));
        driver.findElement(By.cssSelector(".directionsPopOut .bm_dropdownEntry:nth-child(2)")).click();
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".cardTitleText h2"), "Directions"));
        wait.until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(By.cssSelector("[title='To']")), "style"));
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(By.cssSelector("[title='From']")), "style")));
    }

    /**
     * BING19
     * Tamar
     * HTML refers to BING01, BING05, BING19
     */
    @Test
    public void BING19_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        List<WebElement> navElements = driver.findElements(By.cssSelector("[aria-label='Main menu'] > ul > [data-menuurl] > a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("ALL", "NEWS", "IMAGES", "VIDEOS", "MAPS", "SHOPPING"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(4).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("b_entityTP")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Directions']")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Nearby']"))).click();

        element = driver.findElement(By.id("maps_sb"));
        assertEquals(element, driver.switchTo().activeElement());
        expectedTitles.clear();
        actualTitles.clear();
        List<WebElement> suggestionElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("suggestLink")));
        expectedTitles = new ArrayList<>(Arrays.asList("Parking", "Hotels", "Restaurants", "Coffee", "Gas stations"));
        for (WebElement element : suggestionElements) {
            actualTitles.add(element.getText());
        }
        assertTrue(actualTitles.containsAll(expectedTitles));

        suggestionElements.get(2).click();
        wait.until(ExpectedConditions.textToBe(By.className("cardTitleText"), "restaurants"));
    }

    /**
     * BING20
     * Tamar
     * HTML refers to BING01, BING05, BING17
     * not finished
     */
    @Test
    public void BING20_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".id_avatar")));
        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        List<WebElement> navElements = driver.findElements(By.cssSelector("[aria-label='Main menu'] > ul > [data-menuurl] > a"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("ALL", "NEWS", "IMAGES", "VIDEOS", "MAPS", "SHOPPING"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(4).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("inner-container")));
        expectedTitles.clear();
        actualTitles.clear();
        List<WebElement> optionElements = wait.until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector(".actions  li:not([style='display: none;']) > a .actionLabel"), 5));
        expectedTitles = new ArrayList<>(Arrays.asList("Directions", "Traffic", "Local", "My Places", "More"));
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        optionElements.get(1).findElement(By.xpath(".//ancestor::a")).click();

        wait.until(ExpectedConditions.textToBe(By.className("cardTitleText"), "Traffic"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#cameraToggleInput[checked]")));
//        assertTrue(driver.findElement(By.cssSelector("#cameraToggleInput[checked]")).isDisplayed());
    }

    /**
     * BING21
     * Tamar
     * HTML refers to BING01, BING09, BING21
     */
    @Test
    public void BING21_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img")));
        element = driver.findElement(By.cssSelector("[type='search']"));
        element.sendKeys("Jacket");
        element.submit();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        assertEquals("page", driver.findElement(By.xpath("//*[text()='All']")).getAttribute("aria-current"));
        driver.findElement(By.xpath("//*[text()='Shopping']")).click();
        wait.until(ExpectedConditions.textToBe(By.className("br-breadcrumbs"), "Bing Shopping > jacket"));
        assertTrue(driver.findElement(By.xpath("//h2[text()='Brand']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//h2[text()='Price']")).isDisplayed());
    }

    /**
     * BING22
     * Tamar
     * HTML refers to BING01, BING09, BING21, BING22
     */
    @Test
    public void BING22_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img")));
        element = driver.findElement(By.cssSelector("[type='search']"));
        element.sendKeys("Jacket");
        element.submit();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        assertEquals("page", driver.findElement(By.xpath("//*[text()='All']")).getAttribute("aria-current"));
        driver.findElement(By.xpath("//*[text()='Shopping']")).click();

        List<WebElement> navElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("np-tabttltxt")));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("SHOPPING HOME", "DEPARTMENTS", "STORES",
                "EDITOR'S PICKS", "DEALS", "TRENDING PRODUCTS", "PRICE DROPS", "MY COLLECTIONS"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        Actions actions = new Actions(driver);
        actions.moveToElement(navElements.get(1)).perform();

        expectedTitles.clear();
        actualTitles.clear();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.cssSelector("#Departments_Key .b_hList strong"), "")));
        List<WebElement> categoryElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#Departments_Key .b_hList strong")));
        expectedTitles = new ArrayList<>(Arrays.asList(
                "Home Furnishings", "Clothing & Shoes", "Toys", "Lawn & Garden", "Tools & Hardware"));
        for (WebElement element : categoryElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        driver.findElement(By.xpath("//*[text()='Games']")).click();

        wait.until(ExpectedConditions.textToBe(By.className("hpcb-ttl"), "Games"));
        assertTrue(driver.findElements(By.cssSelector("[alt='Product Image']")).size() > 1);
        assertTrue(driver.findElement(By.id("sb_form_q")).getAttribute("value").isEmpty());
    }

    /**
     * BING23
     * Tamar
     * HTML refers to BING01, BING09, BING21, BING23
     */
    @Test
    public void BING23_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img")));
        element = driver.findElement(By.cssSelector("[type='search']"));
        element.sendKeys("Jacket");
        element.submit();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        assertEquals("page", driver.findElement(By.xpath("//*[text()='All']")).getAttribute("aria-current"));
        driver.findElement(By.xpath("//*[text()='Shopping']")).click();

        List<WebElement> navElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("np-tabttltxt")));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("SHOPPING HOME", "DEPARTMENTS", "STORES",
                "EDITOR'S PICKS", "DEALS", "TRENDING PRODUCTS", "PRICE DROPS", "MY COLLECTIONS"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(2).click();

        List<WebElement> letterElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".sf-filter_N")));
        for (int i = 0; i < letterElements.size(); i++) {
            char letter = letterElements.get(i).getText().charAt(0);
            assertEquals(i + 65, (int) letter); // ASCII
        }
        letterElements.get(0).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h2"), "POPULAR STORES IN 'A'"));
        List<WebElement> shopsElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("ps-cardttl")));
        for (WebElement shop : shopsElements) {
            assertTrue(shop.getText().startsWith("A"));
        }
        String shopName = shopsElements.get(0).getText();
        shopsElements.get(0).click();
        while (true) {
            if (driver.getWindowHandles().size() != 1) break;
        }
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.textToBe(By.className("csf-ttl"), shopName));
    }

    /**
     * BING24
     * Tamar
     * HTML refers to BING01, BING09, BING21, BING24
     */
    @Test
    public void BING24_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img")));
        element = driver.findElement(By.cssSelector("[type='search']"));
        element.sendKeys("Jacket");
        element.submit();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        assertEquals("page", driver.findElement(By.xpath("//*[text()='All']")).getAttribute("aria-current"));
        driver.findElement(By.xpath("//*[text()='Shopping']")).click();

        List<WebElement> navElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("np-tabttltxt")));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("SHOPPING HOME", "DEPARTMENTS", "STORES",
                "EDITOR'S PICKS", "DEALS", "TRENDING PRODUCTS", "PRICE DROPS", "MY COLLECTIONS"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        Actions actions = new Actions(driver);
        actions.moveToElement(navElements.get(3)).perform();

        wait.until(ExpectedConditions.textToBe(By.cssSelector("#editors_picks_key .np-catttl"), "Curated Collections"));
        driver.findElement(By.xpath("//*[text()='Beauty']")).click();
        wait.until(ExpectedConditions.textToBe(By.className("br-edtrlttl"), "Beauty & Spa"));
    }


    /**
     * BING25
     * Tamar
     * HTML refers to BING01, BING09, BING21, BING23, BING25
     */
    @Test
    public void BING25_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img")));
        element = driver.findElement(By.cssSelector("[type='search']"));
        element.sendKeys("Jacket");
        element.submit();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        assertEquals("page", driver.findElement(By.xpath("//*[text()='All']")).getAttribute("aria-current"));
        driver.findElement(By.xpath("//*[text()='Shopping']")).click();

        List<WebElement> navElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("np-tabttltxt")));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("SHOPPING HOME", "DEPARTMENTS", "STORES",
                "EDITOR'S PICKS", "DEALS", "TRENDING PRODUCTS", "PRICE DROPS", "MY COLLECTIONS"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(4).click();

        wait.until(ExpectedConditions.textToBe(By.className("tsl-ttl"), "DEALS FROM TOP BRANDS"));
        element = driver.findElement(By.className("tsl-cardttl"));
        String name = element.getText();
        element.click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("csf-ttl"), name));
    }

    /**
     * BING26
     * Tamar
     * HTML refers to BING01, BING09, BING21, BING26
     * not finished
     */
    @Test
    public void BING26_Tamar() {
        driver.get("https://www.bing.com");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//*[text()='Country/Region']/../following-sibling::*"), "")));
        assertTrue(driver.findElement(By.xpath("//*[text()='Country/Region']/../following-sibling::*")).getText().contains("United States - English"));
        assertEquals("English", driver.findElement(By.xpath("//*[text()='Language']/../following-sibling::*")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "false"));


        wait.until(ExpectedConditions.urlContains("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vs img")));
        element = driver.findElement(By.cssSelector("[type='search']"));
        element.sendKeys("Jacket");
        element.submit();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[aria-label='Search Results']")));
        assertEquals("page", driver.findElement(By.xpath("//*[text()='All']")).getAttribute("aria-current"));
        driver.findElement(By.xpath("//*[text()='Shopping']")).click();

        List<WebElement> navElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("np-tabttltxt")));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("SHOPPING HOME", "DEPARTMENTS", "STORES",
                "EDITOR'S PICKS", "DEALS", "TRENDING PRODUCTS", "PRICE DROPS", "MY COLLECTIONS"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : navElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        navElements.get(5).click();

        wait.until(ExpectedConditions.textToBe(By.className("hpcb-ttl"), "Trending Products"));
        List<String> subsections = new ArrayList<>(Arrays.asList("Across Web", "TRENDING CATEGORIES", "Trending Products For You"));
        Actions actions = new Actions(driver);
        for (int i = 0; i < subsections.size(); i++) {
            element = driver.findElement(By.xpath("//h2[text()='" + subsections.get(i) + "']"));
            if (i != 0)
                actions.moveToElement(element).perform();
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
        }
    }


    /**
     * BING27
     * Mika
     * HTML refer to BING27
     */
    @Test
    public void BING27_Mika() {
        String textToShop = "Jacket";
        String[] sectionsToCheck = new String[]{"Shopping_Home_key", "Departments_Key", "Stores_key",
                "editors_picks_key", "dealstab_key", "trending_products_key", "price_drops_key", "my_collections_key"};
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
     * HTML refer to BING27 - home_page_Israel_Hebrew.html, home_page_United-States-English_English.html,
     * settings_page.html, BING28
     */
    @Test
    public void BING28_Mika() throws InterruptedException {
        String[] optionsToCheck = new String[]{"images", "video", "shopping"};

        driver.get("https://www.bing.com");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        wait.until(ExpectedConditions.urlToBe("https://www.bing.com/"));

        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        driver.findElement(By.xpath("//*[text()='Language' or text()='שפה']/../..")).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//a[text()='אנגלית']")));
        action.moveToElement(element).click().perform();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[text()='Country/Region']/../.."))).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(text(), 'United States - English')]")));
        action.moveToElement(element).click().perform();
        for (String option : optionsToCheck)
            wait.until(ExpectedConditions.presenceOfElementLocated
                    (By.xpath("//*[contains(@class, 'scope') and @id='" + option + "']")));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()='Show news and interests']/following-sibling::*[contains(@class, 'toggle_ctrl')]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[text()='Country/Region']/../.."))).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(text(), 'India')]")));
        action.moveToElement(element).click().perform();
        for (String option : optionsToCheck) {
            if (!option.equals("shopping"))
                wait.until(ExpectedConditions.presenceOfElementLocated
                        (By.xpath("//*[contains(@class, 'scope') and @id='" + option + "']")));
            else
                assertEquals(0, driver.findElements
                        (By.xpath("//*[contains(@class, 'scope') and @id='" + option + "']")).size());
        }
        assertTrue(driver.findElement(By.xpath("//*[text()='Languages:']")).isDisplayed());


        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[text()='Country/Region']/../.."))).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(text(), 'Israel')]")));
        action.moveToElement(element).click().perform();
        for (String option : optionsToCheck) {
            if (!option.equals("shopping"))
                wait.until(ExpectedConditions.presenceOfElementLocated
                        (By.xpath("//*[contains(@class, 'scope') and @id='" + option + "']")));
            else
                assertEquals(0, driver.findElements
                        (By.xpath("//*[contains(@class, 'scope') and @id='" + option + "']")).size());
        }
        driver.findElement(By.xpath("//*[contains(@class, 'idp_ham')]")).click();
        Thread.sleep(3000);
        assertEquals(0, driver.findElements
                        (By.xpath("//*[text()='Show news and interests']/following-sibling::*[contains(@class, 'toggle_ctrl')]"))
                .size());
    }

    /**
     * BING29
     * Mika
     * HTML refer to BING27 - home_page_Israel_Hebrew.html, settings_page.html, BING28 - home_page_Israel_English.html
     */
    @Test
    public void BING29_Mika() {
        String countryToChoose = "China";

        driver.get("https://www.bing.com");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        wait.until(ExpectedConditions.urlToBe("https://www.bing.com/"));

        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        driver.findElement(By.xpath("//*[text()='Language' or text()='שפה']/../..")).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//a[text()='אנגלית']")));
        action.moveToElement(element).click().perform();

        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        driver.findElement(By.xpath("//*[text()='Language']/../..")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Search']"))).click();
        element = driver.findElement(By.id("geoname"));
        element.sendKeys(countryToChoose);
        element.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        wait.until(ExpectedConditions.textToBe(By.xpath("//*[text()='Location']/../following-sibling::*"), countryToChoose));
    }

    /**
     * BING30
     * Mika
     * HTML refer to BING27 - home_page_Israel_Hebrew.html, settings_page.html, BING28 - home_page_Israel_English.html
     */
    @Test
    public void BING30_Mika() throws InterruptedException {
        String textToSearch = "Jacket";

        driver.get("https://www.bing.com");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        wait.until(ExpectedConditions.urlToBe("https://www.bing.com/"));

        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        driver.findElement(By.xpath("//*[text()='Language' or text()='שפה']/../..")).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//a[text()='אנגלית']")));
        action.moveToElement(element).click().perform();

        for (int i = 0; i < 2; i++) {
            wait.until(ExpectedConditions.elementToBeClickable
                    (By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='More']/../.."))).click();

            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='settings_suggestions']//*[@type='checkbox']")));
            if (i == 0)
                assertTrue(element.isSelected());
            else // if (i == 1)
                assertFalse(element.isSelected());
            action.moveToElement(element).click().perform();

            element = driver.findElement(By.xpath("//*[@value='Save']"));
            assertTrue(element.isDisplayed());
            assertTrue(driver.findElement(By.id("cancel_changes_button")).isDisplayed());
            element.click();

            wait.until(ExpectedConditions.urlToBe("https://www.bing.com/"));
            element = driver.findElement(By.xpath("//*[@aria-label='Enter your search term']"));
            action.click(element).sendKeys(textToSearch).perform();
            Thread.sleep(1000);
            if (i == 0)
                assertEquals(0, driver.findElements(By.xpath("//*[@aria-label='Suggestions']")).size());
            else // if (i == 1)
                assertTrue(driver.findElement(By.xpath("//*[@aria-label='Suggestions']")).isDisplayed());
        }
    }

    /**
     * BING31
     * Mika
     * HTML refer to BING27 - home_page_Israel_Hebrew.html, settings_page.html, all_search_results_page.html,
     * BING28 - home_page_Israel_English.html, BING31
     */
    @Test
    public void BING31_Mika() throws InterruptedException {
        String textToSearch = "Jacket";
        String newTab;

        driver.get("https://www.bing.com");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        wait.until(ExpectedConditions.urlToBe("https://www.bing.com/"));

        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Language' or text()='שפה']/../.."))).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//a[text()='אנגלית']")));
        action.moveToElement(element).click().perform();

        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='More']/../.."))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Settings']")));
        driver.findElement(By.id("newwnd")).click();
        element = driver.findElement(By.xpath("//*[@value='Save']"));
        assertTrue(element.isDisplayed());
        assertTrue(driver.findElement(By.id("cancel_changes_button")).isDisplayed());
        element.click();

        wait.until(ExpectedConditions.urlToBe("https://www.bing.com/"));
        element = driver.findElement(By.xpath("//*[@aria-label='Enter your search term']"));
        action.click(element).sendKeys(textToSearch).perform();
        driver.findElement(By.id("search_icon")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='All']")));
        element = driver.findElement(By.xpath("//a[text()='" + textToSearch + " - Wikipedia']"));
        action.moveToElement(element).click().perform();
        Thread.sleep(1000);

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);
        Thread.sleep(1000);
        driver.close();
        newTab = (String) driver.getWindowHandles().toArray()[0];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='All']")));

        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='More']/../.."))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Settings']")));
        driver.findElement(By.id("newwnd")).click();
        element = driver.findElement(By.xpath("//*[@value='Save']"));
        assertTrue(element.isDisplayed());
        assertTrue(driver.findElement(By.id("cancel_changes_button")).isDisplayed());
        element.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='All']")));
        driver.findElement(By.xpath("//a[text()='" + textToSearch + " - Wikipedia']")).click();

        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//*[text()='All']"), 0));
        assertEquals(1, driver.getWindowHandles().size());
    }

    /**
     * BING32 not finished
     * Mika
     * HTML refer to
     */
    @Test
    public void BING32_Mika() {
        String textToSearch = "Jacket";

        driver.get("https://www.bing.com");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        wait.until(ExpectedConditions.urlToBe("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@aria-label='Bing']")));

        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Language' or text()='שפה']/../.."))).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//a[text()='אנגלית']")));
        action.moveToElement(element).click().perform();

        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='More']/../.."))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Settings']")));
        element = driver.findElement(By.xpath("//*[text()='Open links from news results in a new tab or window']" +
                "/preceding-sibling::*[@type='checkbox']"));
        assertTrue(element.isSelected());
        action.moveToElement(element).click().perform();
        element = driver.findElement(By.xpath("//*[@value='Save']"));
        assertTrue(element.isDisplayed());
        assertTrue(wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cancel_changes_button"))).isDisplayed());
        element.click();

        wait.until(ExpectedConditions.urlToBe("https://www.bing.com/"));
        element = driver.findElement(By.xpath("//*[@aria-label='Enter your search term']"));
        action.click(element).sendKeys(textToSearch).perform();
        driver.findElement(By.id("search_icon")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='News']"))).click();
        element = driver.findElement(By.xpath("//*[contains(@class, 'news-card')]"));
        action.moveToElement(element).click().perform();

        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//*[text()='News']"), 0));
        assertEquals(1, driver.getWindowHandles().size());

        driver.navigate().back();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='News']")));

        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[contains(@class, 'idp_ham')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='More']/../.."))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Settings']")));
        element = driver.findElement(By.xpath("//*[text()='Open links from news results in a new tab or window']" +
                "/preceding-sibling::*[@type='checkbox']"));
        action.moveToElement(element).click().perform();
        element = driver.findElement(By.xpath("//*[@value='Save']"));
        assertTrue(element.isDisplayed());
        assertTrue(driver.findElement(By.id("cancel_changes_button")).isDisplayed());
        element.click();

        element = driver.findElement(By.xpath("//*[contains(@class, 'news-card')]"));
        action.moveToElement(element).click().perform();

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
    }

    /**
     * BING33 not finished
     * Mika
     * HTML refer to
     */
    @Test
    public void BING33_Mika() throws InterruptedException {
        String textToSearch = "House";
        String resultsNumString;

        driver.get("https://www.bing.com");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        wait.until(ExpectedConditions.urlToBe("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@aria-label='Bing']")));
        Thread.sleep(1000);

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class, 'idp_ham')]")));
        action.click(element).pause(500).click(element).perform();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Language' or text()='שפה']/../.."))).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//a[text()='אנגלית']")));
        action.moveToElement(element).click().perform();

        element = driver.findElement(By.xpath("//*[@aria-label='Enter your search term']"));
        action.click(element).sendKeys(textToSearch).perform();
        driver.findElement(By.id("search_icon")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("sb_count")));
        resultsNumString = element.getText();

        element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'idp_ham')]")));
        element.click();
        action.click(element).pause(500).click(element).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='SafeSearch']"))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Settings']")));
        assertTrue(driver.findElement(By.id("adlt_set_moderate")).isSelected());
        driver.findElement(By.id("adlt_set_off")).click();

        element = driver.findElement(By.xpath("//*[@value='Save']"));
        assertTrue(element.isDisplayed());
        assertTrue(driver.findElement(By.id("cancel_changes_button")).isDisplayed());
        element.click();

        assertTrue(driver.findElement(By.xpath("//*[text()='Turning off SafeSearch requires age verification']"))
                .isDisplayed());
        driver.findElement(By.xpath("//*[text()='Agree']")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("sb_count")));
        assertNotEquals(resultsNumString, element.getText());

        element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'idp_ham')]")));
        element.click();
        action.click(element).pause(500).click(element).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='SafeSearch']"))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Settings']")));
        driver.findElement(By.id("adlt_set_moderate")).click();
        driver.findElement(By.xpath("//*[@value='Save']")).click();
    }

    /**
     * BING34 not finished
     * Mika
     * HTML refer to
     */
    @Test
    public void BING34_Mika() throws InterruptedException {
        String textToSearch = "House";
        String menuCategory = "Search history";

        driver.get("https://www.bing.com");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions action = new Actions(driver);

        wait.until(ExpectedConditions.urlToBe("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@aria-label='Bing']")));
        Thread.sleep(1000);

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class, 'idp_ham')]")));
        action.click(element).pause(500).click(element).perform();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Language' or text()='שפה']/../.."))).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//a[text()='אנגלית']")));
        action.moveToElement(element).click().perform();

        element = driver.findElement(By.xpath("//*[@aria-label='Enter your search term']"));
        action.click(element).sendKeys(textToSearch).perform();
        driver.findElement(By.id("search_icon")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("sb_count")));

        element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'idp_ham')]")));
        element.click();
        action.click(element).pause(500).click(element).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='" + menuCategory + "']"))).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='" + menuCategory + "']")));
        assertTrue(element.findElement(By.xpath("..")).getAttribute("class").contains("selected"));
        assertTrue(driver.findElement(By.xpath("//*[text()='Insights']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[contains(@class, 'donut-chart')]")).isDisplayed());

        element = driver.findElement(By.xpath("(//*[contains(@class, 'query-list__requery-link')])[1]"));
        action.moveToElement(element).perform();
        assertTrue((Boolean) js.executeScript
                ("var elem = arguments[0],                 " +
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
        assertEquals(textToSearch, element.getText());
    }

    /**
     * BING35 not finished
     * Mika
     * HTML refer to
     */
    @Test
    public void BING35_Mika() throws InterruptedException {
        String textToSearch = "House";
        String menuCategory = "Search history";
        int searchedContentsNum;

        driver.get("https://www.bing.com");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions action = new Actions(driver);

        wait.until(ExpectedConditions.urlToBe("https://www.bing.com/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@aria-label='Bing']")));
        Thread.sleep(1000);

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class, 'idp_ham')]")));
        action.click(element).pause(500).click(element).perform();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("hbsettings"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Language' or text()='שפה']/../.."))).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//a[text()='אנגלית']")));
        action.moveToElement(element).click().perform();

        element = driver.findElement(By.xpath("//*[@aria-label='Enter your search term']"));
        action.click(element).sendKeys(textToSearch).perform();
        driver.findElement(By.id("search_icon")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("sb_count")));

        element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'idp_ham')]")));
        element.click();
        action.click(element).pause(500).click(element).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='" + menuCategory + "']"))).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='" + menuCategory + "']")));
        assertTrue(element.findElement(By.xpath("..")).getAttribute("class").contains("selected"));
        assertTrue(driver.findElement(By.xpath("//*[text()='Insights']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[contains(@class, 'donut-chart')]")).isDisplayed());

        element = driver.findElement(By.xpath("(//*[contains(@class, 'query-list__requery-link')])[1]"));
        action.moveToElement(element).perform();
        assertTrue((Boolean) js.executeScript
                ("var elem = arguments[0],                 " +
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
        assertEquals(textToSearch, element.getText());

        searchedContentsNum = driver.findElements(By.xpath("//*[@class='query-list__query-row']")).size();
        driver.findElement(By.xpath("(//*[@class='query-list__query-row'])[1]//*[@role='checkbox']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Clear']"))).click();

        assertNotEquals(searchedContentsNum,
                driver.findElements(By.xpath("//*[@class='query-list__query-row']")).size());
    }
}
