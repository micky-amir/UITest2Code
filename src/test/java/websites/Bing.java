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
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("h1 > *"), "aria-label", "Bing"));
        assertTrue(driver.findElement(By.id("images")).isDisplayed());
        assertTrue(driver.findElement(By.id("video")).isDisplayed());
        assertTrue(driver.findElement(By.id("shopping")).isDisplayed());
        for (int i = 0; i < 2; i++) {
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
            assertTrue(driver.findElements(By.id("shopping")).size() >= i);
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
        By locator = By.cssSelector("a.idp_ham");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.attributeToBe(locator, "aria-expanded", "true"));
        driver.findElement(By.id("hbsettings")).click();
        wait.until(ExpectedConditions.attributeToBe(By.id("hbsettree"), "aria-hidden", "false"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hbsettree > a[role='menuitem']:nth-child(2)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//a[contains(text(), 'United States - English') or contains(text(), 'ארצות הברית - אנגלית')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='English']"))).click();

        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("h1 > *"), "aria-label", "Bing"));
        assertTrue(driver.findElement(By.id("images")).isDisplayed());
        assertTrue(driver.findElement(By.id("video")).isDisplayed());
        assertTrue(driver.findElement(By.id("shopping")).isDisplayed());
        for (int i = 0; i < 2; i++) {
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
    public void BING10_Tamar() {
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
        String originalCount = element.getText().split(" ")[0];
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[aria-label='Filtered by Any time']")));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(By.xpath
                ("//a[@aria-label='Filtered by Any time']/following-sibling::div"), "class", "b_hide")));

        expectedTitles.clear();
        actualTitles.clear();
        List<WebElement> optionElements = wait.until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("#ftrD_Any_time > a"), 5));
        expectedTitles = new ArrayList<>(Arrays.asList("All", "Past 24 hours", "Past week", "Past month", "Past year"));
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        assertEquals("Custom range", driver.findElement(By.cssSelector("#CustomRangeFilter span")).getText());
        optionElements.get(2).click();
        String newCount = driver.findElement(By.className("sb_count")).getText().split(" ")[0];
        assertTrue(Integer.parseInt(originalCount.replace(",", "")) >
                Integer.parseInt(newCount.replace(",", "")));
    }

}
