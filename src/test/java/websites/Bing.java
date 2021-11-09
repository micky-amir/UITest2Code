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

}
