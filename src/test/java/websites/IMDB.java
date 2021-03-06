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
import java.util.*;
import java.util.List;

import static org.junit.Assert.*;


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
     * HTML refer to SK_2
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

    /**
     * SK_4
     * Tamar
     */
    @Test
    public void SK_4_Tamar() throws InterruptedException {
        driver.get("https://www.imdb.com/");
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".fan-picks .ipc-icon.ipc-icon--chevron-right-inline.ipc-icon--inline.ipc-pager-icon"))).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@aria-label='View title page for Cruella']")));
        element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@aria-label='View title page for Cruella']")));
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).click(element).perform();

        element = driver.findElement(By.cssSelector("[data-testid='hero-rating-bar__aggregate-rating'] > *"));
        assertEquals("IMDb RATING", element.getText());
        element = driver.findElement(By.cssSelector("[data-testid='hero-rating-bar__user-rating'] > *"));
        assertEquals("YOUR RATING", element.getText());
        element = driver.findElement(By.cssSelector("[data-testid='hero-rating-bar__popularity'] > *"));
        assertEquals("POPULARITY", element.getText());

        assertEquals("a", element.findElement(By.xpath("//*[text()='User reviews']")).getTagName());
        assertEquals("a", element.findElement(By.xpath("//*[text()='Critic reviews']/../..")).getTagName());
        assertEquals("a", element.findElement(By.xpath("//*[text()='Metascore']/../..")).getTagName());
    }

    /**
     * SK_5
     * Tamar
     * HTML refer to SK_4
     */
    @Test
    public void SK_5_Tamar() {
        driver.get("https://www.imdb.com/");
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".fan-picks .ipc-poster-card--baseAlt")));
        element.findElement(By.xpath(".//*[contains(@aria-label, 'Watch trailer')]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@aria-label='Pause']")));
    }

    /**
     * SK_6
     * Tamar
     * HTML refer to SK_4, SK_6
     */
    @Test
    public void SK_6_Tamar() {
        driver.get("https://www.imdb.com/");
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@aria-label='View title page for Loki']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@title='Share on social media']"))).click();
        List<WebElement> menuItems = driver.findElements(By.cssSelector("[data-testid='share-container'] [role='menuitem']"));
        List<String> expectedItemsText = new LinkedList<>(Arrays.asList("Facebook", "Twitter", "Email Link", "Copy Link", "Share IMDb rating"));
        List<String> actualItemsText = new LinkedList<>();
        for (WebElement item : menuItems) {
            actualItemsText.add(item.getText());
        }
        assertEquals(expectedItemsText, actualItemsText);
    }

    /**
     * SK_7
     * Tamar
     * HTML refer to SK_4, SK_7
     */
    @Test
    public void SK_7_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='All']")).click();
        driver.findElement(By.xpath("//*[@role='menuitem'][@aria-label='Celebs']")).click();

        element = driver.findElement(By.cssSelector(".imdb-header-search__input"));
        element.sendKeys("Virgin River");
        element.submit();

        assertEquals("Names", driver.findElement(By.className("findSectionHeader")).getText());
        List<WebElement> resultsElements = driver.findElements(By.className("result_text"));
        for (WebElement element : resultsElements) {
            String elementText = element.getText().toLowerCase(Locale.ROOT);
            assertTrue(elementText.contains("virgin") || elementText.contains("river"));
        }
    }

    /**
     * SK_8
     * Tamar
     * HTML refer to SK_2, SK_8
     */
    @Test
    public void SK_8_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        element = driver.findElement(By.className("from-your-watchlist"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//descendant::button[@aria-label='Rate Cruella']"))).click();
        element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label='Rate 6']")));
        actions.moveByOffset(0, 0).click(element).perform();
        element.findElement(By.xpath("//*[text()='Rate']")).click();
        element = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@class='from-your-watchlist']/descendant::a[@aria-label='View title page for Cruella']/preceding-sibling::*/descendant::*[@data-testid='rate-button']/*"))));
        assertEquals("6", element.getText());
    }

    /**
     * SK_9
     * Tamar
     * HTML refer to SK_4
     */
    @Test
    public void SK_9_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='All']")).click();
        List<WebElement> itemsElements = driver.findElements(By.xpath("//*[@id='navbar-search-category-select-contents']/*/*[@role='menuitem']"));
        List<String> expectedItemsText = new LinkedList<>(Arrays.asList("All", "Titles", "TV Episodes", "Celebs", "Companies", "Keywords", "Advanced Search"));
        List<String> actualItemsText = new LinkedList<>();
        for (WebElement element : itemsElements) {
            actualItemsText.add(element.getText());
        }
        assertEquals(expectedItemsText, actualItemsText);
    }

    /**
     * SK_10
     * Tamar
     */
    @Test
    public void SK_10_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.id("imdbHeader-navDrawerOpen--desktop")).click();
        element = driver.findElement(By.xpath("//*[text()='Top Rated Movies']"));
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).click(element).perform();
        assertTrue(driver.findElement(By.xpath("//table/descendant::th[text()='IMDb Rating']")).isDisplayed()); // different locator
        assertTrue(driver.findElement(By.xpath("//table/descendant::th[text()='Your Rating']")).isDisplayed());
    }

    /**
     * SK_11
     * Tamar
     * HTML refer to SK_10
     */
    @Test
    public void SK_11_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.id("imdbHeader-navDrawerOpen--desktop")).click();
        element = driver.findElement(By.xpath("//*[text()='Top Rated Movies']"));
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).click(element).perform();

        element = driver.findElement(By.id("lister-sort-by-options"));
        element.click();
        element.findElement(By.xpath(".//*[@value='rk:ascending']")).click();

        List<WebElement> itemsElements = driver.findElements(By.cssSelector(".lister-list .titleColumn"));
        for (int i = 1; i < itemsElements.size(); i++) {
            String currentText = itemsElements.get(i).getText();
            currentText = currentText.substring(0, currentText.indexOf(" ") - 1);
            String previousText = itemsElements.get(i - 1).getText();
            previousText = previousText.substring(0, previousText.indexOf(" ") - 1);
            assertTrue("problem in i: " + i, Integer.parseInt(currentText) > (Integer.parseInt((previousText))));
        }
    }

    /**
     * SK_12
     * HTML refer to SK_10
     */
    @Test
    public void SK_12_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.id("imdbHeader-navDrawerOpen--desktop")).click();
        element = driver.findElement(By.xpath("//*[text()='Top Rated Movies']"));
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).click(element).perform();

        element = driver.findElement(By.id("lister-sort-by-options"));
        element.click();
        element.findElement(By.xpath(".//*[@value='ir:descending']")).click();

        List<WebElement> itemsElements = driver.findElements(By.cssSelector(".lister-list .ratingColumn.imdbRating"));
        for (int i = 1; i < itemsElements.size(); i++) {
            assertTrue((Double.parseDouble(itemsElements.get(i).getText()) <= (Double.parseDouble(itemsElements.get(i - 1).getText()))));
        }
    }

    /**
     * SK_13
     * Tamar
     * HTML refer to SK_10
     */
    @Test
    public void SK_13_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.id("imdbHeader-navDrawerOpen--desktop")).click();
        element = driver.findElement(By.xpath("//*[text()='Top Rated Movies']"));
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).click(element).perform();

        element = driver.findElement(By.id("lister-sort-by-options"));
        element.click();
        element.findElement(By.xpath(".//*[@value='us:descending']")).click();

        List<WebElement> itemsElements = driver.findElements(By.cssSelector(".lister-list .secondaryInfo"));
        for (int i = 1; i < itemsElements.size(); i++) {
            String currentText = itemsElements.get(i).getText();
            String previousText = itemsElements.get(i).getText();
            assertTrue((Integer.parseInt(currentText.substring(1, currentText.length() - 2)) <= (Integer.parseInt(previousText.substring(1, previousText.length() - 2)))));
        }
    }

    /**
     * SK_14
     * Tamar
     * HTML refer to SK_10
     */
    @Test
    public void SK_14_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.id("imdbHeader-navDrawerOpen--desktop")).click();
        element = driver.findElement(By.xpath("//*[text()='Top Rated Movies']"));
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).click(element).perform();

        element = driver.findElement(By.id("lister-sort-by-options"));
        element.click();
        element.findElement(By.xpath(".//*[@value='nv:descending']")).click();

        List<WebElement> itemsElements = driver.findElements(By.cssSelector(".lister-list .ratingColumn.imdbRating > strong"));
        for (int i = 1; i < itemsElements.size(); i++) {
            String currentText = itemsElements.get(i).getAttribute("title");
            currentText = currentText.substring(currentText.indexOf("on ") + 3, currentText.indexOf(" user"));
            String previousText = itemsElements.get(i).getAttribute("title");
            previousText = previousText.substring(previousText.indexOf("on ") + 3, previousText.indexOf(" user"));
            assertTrue((Integer.parseInt(currentText.replaceAll(",", "")) <= (Integer.parseInt(previousText.replaceAll(",", "")))));
        }
    }

    /**
     * SK_15
     * Tamar
     * HTML refer to SK_2, SK_10
     */
    @Test
    public void SK_15_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        driver.findElement(By.id("imdbHeader-navDrawerOpen--desktop")).click();
        element = driver.findElement(By.xpath("//*[text()='Top Rated Movies']"));
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).click(element).perform();

        element = driver.findElement(By.xpath("//a[text()='The Godfather']/../following-sibling::*[@class='ratingColumn']/*"));
        element.click();
        element.findElement(By.xpath("//a[text()='The Godfather']/../following-sibling::*[@class='ratingColumn']/descendant::li[4]")).click();

        element = driver.findElement(By.id("lister-sort-by-options"));
        element.click();
        element.findElement(By.xpath(".//*[@value='ur:descending']")).click();

        List<WebElement> itemsElements = driver.findElements(By.cssSelector(".lister-list .ratingColumn .rating"));
        for (int i = 1; i < itemsElements.size(); i++) {
            String currentText = itemsElements.get(i).getText();
            if (currentText.equals("")) {
                currentText = "0";
            }
            String previousText = itemsElements.get(i).getText();
            if (previousText.equals("")) {
                previousText = "0";
            }
            assertTrue((Integer.parseInt(currentText) <= (Integer.parseInt(previousText))));
        }
    }

    /**
     * SK_16
     * Tamar
     * HTML refer to SK_10
     */
    @Test
    public void SK_16_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.id("imdbHeader-navDrawerOpen--desktop")).click();
        element = driver.findElement(By.xpath("//*[text()='Top Rated Movies']"));
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).click(element).perform();

        driver.findElement(By.cssSelector(".dropdown.share-widget")).click();
        element = driver.findElement(By.xpath("//*[@class='share-widget-copy-icon']/.."));
        actions.moveToElement(element).perform();
        assertEquals("Click to copy", element.getAttribute("title"));
        element.click();
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Object copiedData = clipboard.getData(DataFlavor.stringFlavor);
            assertEquals("https://www.imdb.com/chart/top/", copiedData.toString());
        } catch (IOException | UnsupportedFlavorException e) {
            e.printStackTrace();
        }
    }

    /**
     * SK_17
     * Tamar
     * HTML refer to SK_10
     */
    @Test
    public void SK_17_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.id("imdbHeader-navDrawerOpen--desktop")).click();
        element = driver.findElement(By.xpath("//*[text()='Top Rated Movies']"));
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).click(element).perform();
        List<WebElement> elements = driver.findElements(By.cssSelector(".lister-list > tr"));
        assertEquals(250, elements.size());
        for (WebElement element : elements) {
            assertTrue(element.isDisplayed());
        }
    }

    /**
     * SK_18
     * Tamar
     * HTML refer to SK_4, SK_18
     */
    @Test
    public void SK_18_Tamar() {
        driver.get("https://www.imdb.com/");
        element = driver.findElement(By.cssSelector(".imdb-header-search__input"));
        element.sendKeys("Virgin River");
        element.submit();

        assertEquals("Titles", driver.findElement(By.className("findSectionHeader")).getText());
        List<WebElement> resultsElements = driver.findElements(By.className("result_text"));
        for (WebElement element : resultsElements) {
            String elementText = element.getText().toLowerCase(Locale.ROOT);
            assertTrue(elementText.contains("virgin") || elementText.contains("river"));
        }
    }

    /**
     * SK_19
     * Tamar
     * HTML refer to SK_4, SK_19
     */
    @Test
    public void SK_19_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='All']")).click();
        driver.findElement(By.xpath("//*[@role='menuitem']//*[text()='Advanced Search']/..")).click();

        assertEquals("Advanced Search", driver.findElement(By.cssSelector("#header > h1")).getText());
        List<WebElement> optionsElements = driver.findElements(By.className("article"));
        assertEquals(4, optionsElements.size());
        List<String> expectedText = new LinkedList<>(Arrays.asList("Advanced Title Search", "Advanced Name Search", "Search Collaborations", "Search Within a Topic"));
        for (int i = 0; i < optionsElements.size() - 1; i++) {
            assertEquals(expectedText.get(i), optionsElements.get(i).findElement(By.xpath(".//a")).getText());
        }
        assertEquals(expectedText.get(3), optionsElements.get(3).findElement(By.tagName("h4")).getText());
    }

    /**
     * SK_20
     * Tamar
     * HTML refer to SK_4
     */
    @Test
    public void SK_20_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.id("imdbHeader-navDrawerOpen--desktop")).click();
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[title='Close Navigation Drawer']")));
        driver.findElement(By.cssSelector("[data-testid='panel-header'] > a")).click();
        assertTrue(driver.findElement(By.cssSelector("[aria-label='Home']")).isDisplayed());
        assertFalse(driver.findElement(By.cssSelector("[title='Close Navigation Drawer']")).isDisplayed());
    }

    /**
     * SK_21
     * Tamar
     * HTML refer to SK_4
     */
    @Test
    public void SK_21_Tamar() throws InterruptedException {
        driver.get("https://www.imdb.com/");
        for (int i = 0; i < 13; i++) {
            element = driver.findElement(By.cssSelector(".swiper-slide.swiper-slide-active img"));
            assertNotEquals("", element.getAttribute("alt"));
            assertNotEquals("", element.getAttribute("src"));
            element.findElement(By.xpath("//*[@aria-label='Next slide']")).click();
            Thread.sleep(200);
        }
        for (int i = 0; i < 13; i++) {
            element = driver.findElement(By.cssSelector(".swiper-slide.swiper-slide-active img"));
            assertNotEquals("", element.getAttribute("alt"));
            assertNotEquals("", element.getAttribute("src"));
            element.findElement(By.xpath("//*[@aria-label='Previous slide']")).click();
            Thread.sleep(200);
        }
    }

    /**
     * SK_22
     * Tamar
     * HTML refer to SK_2
     */
    @Test
    public void SK_22_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        driver.findElement(By.xpath("//*[@title='Toggle Acount Menu']/..")).click();
        element = driver.findElement(By.id("navUserMenu-contents"));
        assertEquals("visible", element.getCssValue("visibility"));
        List<WebElement> menuItems = element.findElements(By.tagName("a"));
        List<String> expectedItemsText = new LinkedList<>(Arrays.asList("Your activity", "Your watchlist", "Your ratings", "Your lists", "Account settings", "Sign out"));
        WebDriverWait wait = new WebDriverWait(driver, 10);
        for (int i = 1; i < menuItems.size(); i++) { // i=0 is the username
            wait.until(ExpectedConditions.elementToBeClickable(menuItems.get(i)));
            assertEquals(expectedItemsText.get(i - 1), menuItems.get(i).getText());
        }
    }

    /**
     * SK_23
     * Tamar
     * HTML refer to SK_4
     */
    @Test
    public void SK_23_Tamar() {
        driver.get("https://www.imdb.com/");
        element = driver.findElement(By.cssSelector(".swiper-slide.swiper-slide-active"));
        assertTrue(element.isDisplayed());
        assertFalse(element.findElement(By.cssSelector("[class^='SlideCaption__WithPeekCaptionHeadingText']")).getText().isEmpty());
        element = element.findElement(By.cssSelector("[class^='SlideCaption__WithPeekRuntimeText']"));
        String time = element.getText();
        int timeStringLength = time.length();
        assertTrue(timeStringLength > 3 && timeStringLength < 6); // 4 or 5
        assertEquals(timeStringLength - 3, time.indexOf(":")); // 4 or 5
    }

    /**
     * SK_24
     * Tamar
     * HTML refer to SK_4, SK_24
     */
    @Test
    public void SK_24_Tamar() throws InterruptedException {
        driver.get("https://www.imdb.com/");
        element = driver.findElement(By.xpath("//*[text()='Featured today']/../.."));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = element.findElement(By.cssSelector(".ipc-lockup-overlay__content"));
        assertTrue(element.isDisplayed());
        assertEquals("List", element.getText());
        element.click();
        Thread.sleep(200);
        assertTrue(driver.findElement(By.cssSelector("[data-testid='media-viewer']")).isDisplayed());
        element = driver.findElement(By.cssSelector("[aria-label='gallery']"));
        assertTrue(element.isDisplayed());
        element.click();
        assertTrue(driver.findElement(By.className("media_index_thumb_list")).isDisplayed());
    }

    /**
     * SK_25
     * Tamar
     * HTML refer to SK_4, SK_25
     */
    @Test
    public void SK_25_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.id("imdbHeader-navDrawerOpen--desktop")).click();
        assertTrue(driver.findElements(By.cssSelector("[data-testid='nav-link-category']")).size() > 1);
        element = driver.findElement(By.xpath("//*[text()='Release Calendar']"));
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).click(element).perform();
        assertEquals("Upcoming Releases for United States", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_26
     * Tamar
     * HTML refer to SK_4, SK_26
     */
    @Test
    public void SK_26_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.id("imdbHeader-navDrawerOpen--desktop")).click();
        assertTrue(driver.findElements(By.cssSelector("[data-testid='nav-link-category']")).size() > 1);
        element = driver.findElement(By.xpath("//*[text()='DVD & Blu-ray Releases']"));
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).click(element).perform();
        assertEquals("New and Upcoming VOD, DVD, and Blu-ray Releases", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_27
     * Tamar
     * HTML refer to SK_2, SK_27
     */
    @Test
    public void SK_27_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        driver.findElement(By.xpath("//*[@title='Toggle Acount Menu']/..")).click();
        assertEquals("visible", driver.findElement(By.cssSelector("[data-menu-id='navUserMenu']")).getCssValue("visibility"));
        driver.findElement(By.xpath("//span[text()='Your activity']/..")).click();
        assertTrue(driver.findElement(By.id("avatar-frame")).isDisplayed());
        assertEquals("Profile Checklist", driver.findElement(By.cssSelector("#profile-checklist > h3")).getText());
    }

    /**
     * SK_28
     * Tamar
     * HTML refer to SK_4, SK_28
     */
    @Test
    public void SK_28_Tamar() {
        driver.get("https://www.imdb.com/");
        element = driver.findElement(By.cssSelector("[title='Facebook']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click(element).perform();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.attributeToBe(By.tagName("html"), "id", "facebook"));
        assertEquals("IMDb", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_29
     * Tamar
     * HTML refer to SK_4, SK_29
     */
    @Test
    public void SK_29_Tamar() {
        driver.get("https://www.imdb.com/");
        element = driver.findElement(By.cssSelector("[title='Twitter']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click(element).perform();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Twitter, Inc.')]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='/IMDb/header_photo']")));
    }

    /**
     * SK_30
     * Tamar
     * HTML refer to SK_4, SK_30
     */
    @Test
    public void SK_30_Tamar() {
        driver.get("https://www.imdb.com/");
        element = driver.findElement(By.cssSelector("[title='Twitch']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click(element).perform();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@aria-label='Twitch Home']")));
        assertEquals("IMDb", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_31
     * Tamar
     * HTML refer to SK_4, SK_31
     */
    @Test
    public void SK_31_Tamar() {
        driver.get("https://www.imdb.com/");
        element = driver.findElement(By.cssSelector("[title='Instagram']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click(element).perform();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@alt='Instagram']")));
        assertEquals("imdb", driver.findElement(By.tagName("h2")).getText());
    }

    /**
     * SK_32
     * Tamar
     * HTML refer to SK_4, SK_32
     */
    @Test
    public void SK_32_Tamar() {
        driver.get("https://www.imdb.com/");
        element = driver.findElement(By.cssSelector("[title='YouTube']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click(element).perform();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("yt-icon")));
        assertEquals("IMDb", driver.findElement(By.cssSelector(".style-scope.ytd-channel-name")).getText());
    }

    /**
     * SK_33
     * Tamar
     * HTML refer to SK_2, SK_33
     */
    @Test
    public void SK_33_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        driver.findElement(By.cssSelector("[aria-label='Go To IMDbPro'] > div")).click();
        assertEquals("Log in With Amazon", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_34
     * Tamar
     * HTML refer to SK_2, SK_34
     */
    @Test
    public void SK_34_Tamar() throws InterruptedException {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Watch trailer for The Suicide Squad']")));
        String elementLabel = element.getAttribute("aria-label");
        String movieName = elementLabel.substring(elementLabel.indexOf("for") + 4);
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).click(element).perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@aria-label='Pause']")));
        String title = driver.findElement(By.tagName("h5")).getText();
        assertTrue(movieName.contains(title) || title.contains(movieName));
        driver.findElement(By.cssSelector(".ipc-icon-link--onBase")).click();
        String pageTitle = driver.findElement(By.xpath("//*[@data-testid='hero-title-block__title']")).getText();
        assertTrue(pageTitle.contains(title) || title.contains(pageTitle));
        driver.findElement(By.xpath("//*[contains(text(), 'User reviews')]")).click();
        driver.findElement(By.cssSelector(".user-reviews")).click();

        Thread.sleep(3000);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("cboxIframe")));
        driver.switchTo().frame(element);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("icicle-page")));
        driver.findElement(By.cssSelector(".klondike-userreview-summary")).sendKeys("good");
        driver.findElement(By.tagName("textarea")).sendKeys("Saw it yesterday. I enjoyed it. It was good. I Liked it.");
        driver.findElement(By.xpath("//*[contains(@class,'ice-radio-button')]/*[text()='No']")).click();
        driver.findElement(By.className("a-button-input")).click();
    }

    /**
     * SK_35
     * Tamar
     * HTML refer to SK_2, SK_35
     */
    @Test
    public void SK_35_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        driver.findElement(By.xpath("//*[text()='All']")).click();
        driver.findElement(By.xpath("//*[@role='menuitem'][@aria-label='Titles']")).click();

        element = driver.findElement(By.cssSelector(".imdb-header-search__input"));
        element.sendKeys("Virgin River");
        element.submit();

        assertEquals("Titles", driver.findElement(By.className("findSectionHeader")).getText());
        List<WebElement> resultsElements = driver.findElements(By.className("result_text"));
        for (WebElement element : resultsElements) {
            String elementText = element.getText().toLowerCase(Locale.ROOT);
            assertTrue(elementText.contains("virgin") || elementText.contains("river"));
        }
    }

    /**
     * SK_36
     * Tamar
     * HTML refer to SK_2, SK_36
     */
    @Test
    public void SK_36_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        actions.moveToElement(driver.findElement(By.className("top-picks"))).perform();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".top-picks [data-testid='title']")));
        String movieName = element.getText();
        element = driver.findElement(By.cssSelector(".top-picks [title='info']"));
        actions.moveToElement(element).click(element).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ipc-promptable-base__panel")));
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.className("ipc-title-prompt__title")), movieName));
    }

    /**
     * SK_37
     * Tamar
     * HTML refer to SK_4, SK_37
     */
    @Test
    public void SK_37_Tamar() throws InterruptedException {
        driver.get("https://www.imdb.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-testid='title'][text()='Forrest Gump']/..")));
        WebElement rightButton = element.findElement(By.xpath(".//../../../following-sibling::*[contains(@class, 'ipc-pager--right')]"));
        for (int i = 0; i < 4; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(rightButton)).click();
            Thread.sleep(200);
        }
        Thread.sleep(1000);
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Cast & crew']")));
        driver.findElement(By.xpath("//a[text()='Cast & crew']")).click();
        assertEquals("Forrest Gump", driver.findElement(By.cssSelector("h3 > a")).getText());
        assertEquals("Full Cast & Crew", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_38
     * Tamar
     * HTML refer to SK_2, SK_38
     */
    @Test
    public void SK_38_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Watch trailer for The Suicide Squad']")));
        String elementLabel = element.getAttribute("aria-label");
        String movieName = elementLabel.substring(elementLabel.indexOf("for") + 4);
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).click(element).perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@aria-label='Pause']")));
        String title = driver.findElement(By.tagName("h5")).getText();
        assertTrue(movieName.contains(title) || title.contains(movieName));
        driver.findElement(By.cssSelector(".ipc-icon-link--onBase")).click();
        String pageTitle = driver.findElement(By.xpath("//*[@data-testid='hero-title-block__title']")).getText();
        assertTrue(pageTitle.contains(title) || title.contains(pageTitle));
        driver.findElement(By.cssSelector("[data-testid='tm-box-addtolist-button']")).click();
        assertTrue(driver.findElement(By.cssSelector(".attlp-watchlist")).isDisplayed());
        By createListLocator = By.cssSelector(".attlp-creat-list");
        assertTrue(driver.findElement(createListLocator).isDisplayed());
        driver.findElement(createListLocator).click();
        assertEquals("Create a new list", driver.findElement(By.tagName("h1")).getText());
        driver.findElement(By.id("list-create-name")).sendKeys("test list");
        driver.findElement(By.cssSelector(".list-create-button")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("lister-edit-form")));
    }

    /**
     * SK_39
     * Tamar
     * HTML refer to SK_2, SK_39
     */
    @Test
    public void SK_39_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        driver.findElement(By.xpath("//*[@title='Toggle Acount Menu']/..")).click();
        driver.findElement(By.xpath("//*[@id='navUserMenu-contents']//a/*[text()='Account settings']/..")).click();
        assertEquals("Account Settings", driver.findElement(By.tagName("h1")).getText());
        List<WebElement> titlesElements = driver.findElements(By.tagName("h2"));
        List<String> expectedTitles = new LinkedList<>(Arrays.asList("Personal Information", "Preferences", "Other"));
        List<String> actualTitles = new LinkedList<>();
        for (WebElement element : titlesElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * SK_40
     * Tamar
     * HTML refer to SK_2, SK_40
     */
    @Test
    public void SK_40_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".imdb-header__watchlist-button-count")));
        driver.findElement(By.cssSelector(".imdb-header__watchlist-button > a")).click();
        assertEquals("Your Watchlist", driver.findElement(By.tagName("h1")).getText());
        String details = driver.findElement(By.className("lister-details")).getText();
        assertNotEquals("0", details.substring(0, details.indexOf(" ")));
        driver.findElement(By.cssSelector("[title='Edit']")).click();
        driver.findElement(By.id("list-settings")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("privacy-toggle-switch")));
        if (!driver.findElement(By.cssSelector(".privacy-overview.text-muted")).getText().equals("private")) {
            driver.findElement(By.className("label")).click();
        }
        driver.findElement(By.cssSelector("#list-settings-content [value='SAVE']")).click();
        driver.findElement(By.xpath("//button[text()='Done']")).click();
        assertEquals("PRIVATE", driver.findElement(By.cssSelector(".lister-privacy")).getText());
    }

    /**
     * SK_41
     * Tamar
     * HTML refer to SK_2, SK_40
     */
    @Test
    public void SK_41_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".imdb-header__watchlist-button-count")));
        driver.findElement(By.cssSelector(".imdb-header__watchlist-button > a")).click();
        assertEquals("Your Watchlist", driver.findElement(By.tagName("h1")).getText());
        assertFalse(driver.findElement(By.cssSelector("#social-sharing-widget button")).isEnabled());
    }

    /**
     * SK_42
     * Tamar
     * HTML refer to SK_2, SK_27
     */
    @Test
    public void SK_42_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        driver.findElement(By.xpath("//*[@title='Toggle Acount Menu']/..")).click();
        driver.findElement(By.xpath("//*[@id='navUserMenu-contents']//a/*[text()='Your activity']/..")).click();
        assertTrue(driver.findElement(By.cssSelector(".user-profile.own-profile")).isDisplayed());
        driver.findElement(By.cssSelector("a[aria-label='Home']")).click();
        assertTrue(driver.findElement(By.className("swiper-wrapper")).isDisplayed());
    }

    /**
     * SK_43
     * Tamar
     * HTML refer to SK_4
     */
    @Test
    public void SK_43_Tamar() throws InterruptedException {
        WebDriverManager.edgedriver().setup();
        EdgeDriver driver2 = new EdgeDriver();
        driver2.manage().window().maximize();
        driver2.get("https://www.imdb.com/");

        String currentName = driver2.findElement(By.cssSelector(".swiper-slide-active [class*=SlideCaption__WithPeekCaptionHeadingText]")).getText();
        driver2.findElement(By.xpath("//*[@aria-label='Next slide']")).click();
        Thread.sleep(500);
        String nextName = driver2.findElement(By.cssSelector(".swiper-slide-active [class*=SlideCaption__WithPeekCaptionHeadingText]")).getText();
        assertNotEquals(currentName, nextName);
        driver2.quit();
    }

    /**
     * SK_44
     * Tamar
     * HTML refer to SK_2
     */
    @Test
    public void SK_44_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        driver.findElement(By.xpath("//*[@title='Toggle Acount Menu']/..")).click();
        driver.findElement(By.xpath("//*[@id='navUserMenu-contents']//a/*[text()='Sign out']/..")).click();
        assertEquals("Sign In", driver.findElement(By.cssSelector(".imdb-header__signin-text")).getText());
    }

    /**
     * SK_45
     * Tamar
     * HTML refer to SK_4
     */
    @Test
    public void SK_45_Tamar() throws InterruptedException {
        driver.get("https://www.imdb.com/");
        for (int i = 0; i < 13; i++) {
            String nextUpName = driver.findElement(By.xpath("//*[@class='editorial-slots']/descendant::span[contains(@class, 'AutorotatePeekstyle__EditorialSlotTitle')][3]")).getText();
            driver.findElement(By.xpath("//*[@aria-label='Next slide']")).click();
            Thread.sleep(500);
            element = driver.findElement(By.cssSelector(".swiper-slide-active [class*=SlideCaption__WithPeekCaptionHeadingText]"));
            String currentName = element.getText();
            assertEquals(currentName, nextUpName);
        }
    }

    /**
     * SK_46
     * Tamar
     * HTML refer to SK_2, SK_40
     */
    @Test
    public void SK_46_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".imdb-header__watchlist-button-count")));
        driver.findElement(By.cssSelector(".imdb-header__watchlist-button > a")).click();
        assertEquals("Your Watchlist", driver.findElement(By.tagName("h1")).getText());
        driver.findElement(By.id("lister-sort-by-options")).click();
        driver.findElement(By.xpath("//option[text()='IMDb Rating']")).click();
        driver.findElement(By.id("lister-sort-by-options")).click();

        List<WebElement> metascoresElements = driver.findElements(By.className("ratings-imdb-rating"));
        for (int i = 1; i < metascoresElements.size(); i++) {
            String previousScore = metascoresElements.get(i - 1).getText();
            String currentScore = metascoresElements.get(i).getText();
            assertTrue(Double.parseDouble(previousScore) >= Double.parseDouble(currentScore));
        }
    }

    /**
     * SK_47
     * Tamar
     * HTML refer to SK_2
     */
    @Test
    public void SK_47_Tamar() {
        driver.get("https://www.imdb.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Cruella']/../preceding-sibling::*/*[contains(@class, 'ipc-watchlist-ribbon')]"))).click();
        assertEquals("Sign in", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_48
     * Tamar
     * HTML refer to SK_2
     */
    @Test
    public void SK_48_Tamar() {
        driver.get("https://www.imdb.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Cruella']/../preceding-sibling::*[contains(@class, 'ipc-poster-card__rating-star-group')]/button"))).click();
        assertEquals("Sign in", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * SK_49
     * Tamar
     * HTML refer to SK_1
     */
    @Test
    public void SK_49_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        By createAccountLocator = By.xpath("//a[text()='Create a New Account']");
        assertTrue(driver.findElement(createAccountLocator).isDisplayed());
        driver.findElement(createAccountLocator).click();
        driver.findElement(By.id("ap_customer_name")).sendKeys("username");
        driver.findElement(By.id("ap_email")).sendKeys("weenakhalid@gmail.com");
        driver.findElement(By.id("ap_password")).sendKeys("password");
        driver.findElement(By.id("ap_password_check")).sendKeys("password");
        driver.findElement(By.id("continue")).click();
        WebElement alertElement = driver.findElement(By.id("auth-warning-message-box"));
        assertEquals("Important Message!\n" +
                        "You indicated you're a new customer, but an account already exists with the email address weenakhalid@gmail.com.",
                alertElement.getText());
    }

    /**
     * SK_50
     * Tamar
     * HTML refer to SK_2
     */
    @Test
    public void SK_50_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[@title='Toggle Acount Menu']/..")).click();
        driver.findElement(By.xpath("//*[@id='navUserMenu-contents']//a/*[text()='Sign out']/..")).click();
        assertEquals("Sign In", driver.findElement(By.cssSelector(".imdb-header__signin-text")).getText());

        driver.switchTo().window(tabs.get(0));
        driver.navigate().refresh();
        assertEquals("Sign In", driver.findElement(By.cssSelector(".imdb-header__signin-text")).getText());
    }

    /**
     * SK_51
     * Tamar
     * HTML refer to SK_2, SK_40
     */
    @Test
    public void SK_51_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".imdb-header__watchlist-button-count")));
        driver.findElement(By.cssSelector(".imdb-header__watchlist-button > a")).click();
        assertEquals("Your Watchlist", driver.findElement(By.tagName("h1")).getText());
        assertTrue(driver.findElements(By.className("lister-item")).size() > 0);
        driver.findElement(By.cssSelector("a[aria-label='Home']")).click();
        assertTrue(driver.findElement(By.className("swiper-wrapper")).isDisplayed());
    }

    /**
     * SK_52
     * Tamar
     * HTML refer to SK_2, SK_52
     */
    @Test
    public void SK_52_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        driver.findElement(By.xpath("//*[@title='Toggle Acount Menu']/..")).click();
        driver.findElement(By.xpath("//*[@id='navUserMenu-contents']//a/*[text()='Your ratings']/..")).click();
        assertEquals("Your Ratings", driver.findElement(By.tagName("h1")).getText());
        driver.findElement(By.cssSelector("a[aria-label='Home']")).click();
        assertTrue(driver.findElement(By.className("swiper-wrapper")).isDisplayed());
    }

    /**
     * SK_53
     * Tamar
     * HTML refer to SK_2, SK_52
     */
    @Test
    public void SK_53_Tamar() {
        driver.get("https://www.imdb.com/");
        driver.findElement(By.xpath("//*[text()='Sign In']")).click();
        assertTrue(driver.findElements(By.className("list-group")).size() > 1);
        driver.findElement(By.xpath("//*[text()='Sign in with IMDb']")).click();
        driver.findElement(By.id("ap_email")).sendKeys("tamar.gur@outlook.co.il");
        driver.findElement(By.id("ap_password")).sendKeys("imdbtest");
        driver.findElement(By.id("signInSubmit")).click();

        driver.findElement(By.xpath("//*[@title='Toggle Acount Menu']/..")).click();
        driver.findElement(By.xpath("//*[@id='navUserMenu-contents']//a/*[text()='Your ratings']/..")).click();
        assertEquals("Your Ratings", driver.findElement(By.tagName("h1")).getText());
        driver.findElement(By.className("share_url_link")).click();
        assertEquals("inline-block", driver.findElement(By.cssSelector("[name='share-link']")).getCssValue("display"));
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).perform();
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Object copiedData = clipboard.getData(DataFlavor.stringFlavor);
            assertTrue(copiedData.toString().startsWith("https://www.imdb.com/user/") && copiedData.toString().endsWith("/ratings"));
        } catch (IOException | UnsupportedFlavorException e) {
            e.printStackTrace();
        }
    }

    /**
     * SK_1
     * Amir
     */
    @Test
    public void SK_1_Amir() {
        String path = "https://www.imdb.com/";
        WebDriverWait wait = new WebDriverWait(driver, 1000);

        driver.get(path);
        driver.findElement(By.className("imdb-header__signin-text")).click();
        WebElement signInDiv = driver.findElement(By.id("signin-options"));
        List<WebElement> signInOptions = signInDiv.findElements(By.className("list-group-item"));
        Assert.assertTrue(signInOptions.size() > 1);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Create a New Account']"))).click();

        String userName = "some name";
        driver.findElement(By.id("ap_customer_name")).sendKeys(userName);
        driver.findElement(By.id("ap_email")).sendKeys("some@email.com");
        driver.findElement(By.id("ap_password")).sendKeys("Password1");
        driver.findElement(By.id("ap_password_check")).sendKeys("Password1");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        String userNameFromPage = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("navbar__user-name"))).getText();
        Assert.assertEquals(userNameFromPage, userName);
    }

    /**
     * SK_2
     * Amir
     */
    @Test
    public void SK_2_Amir() {
        String path = "https://www.imdb.com/";
        WebDriverWait wait = new WebDriverWait(driver, 1000);

        driver.get(path);
        driver.findElement(By.className("imdb-header__signin-text")).click();
        WebElement signInDiv = driver.findElement(By.id("signin-options"));
        List<WebElement> signInOptions = signInDiv.findElements(By.className("list-group-item"));
        Assert.assertTrue(signInOptions.size() > 1);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Sign in with IMDb']"))).click();
        String userName = "some name";
        driver.findElement(By.id("ap_email")).sendKeys("some@email.com");
        driver.findElement(By.id("ap_password")).sendKeys("Password1");
        driver.findElement(By.id("signInSubmit")).click();
        String userNameFromPage = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("navbar__user-name"))).getText();
        Assert.assertEquals(userNameFromPage, userName);
    }

    /**
     * SK_3
     * Amir
     */
    @Test
    public void SK_3_Amir() {
        String path = "https://www.imdb.com/";
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        driver.get(path);
        driver.findElement(By.className("imdb-header__signin-text")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Sign in with IMDb']"))).click();
        driver.findElement(By.id("ap_email")).sendKeys("amirbenami21@gmail.com");
        driver.findElement(By.id("ap_password")).sendKeys("nba12345");
        driver.findElement(By.id("signInSubmit")).click();
        WebElement featureTodaySection = driver.findElement(By.xpath("//*[text()='Featured today']/../.."));
        driver.switchTo().frame(featureTodaySection);
        featureTodaySection.findElement(By.xpath("//span[text()='Photos']"));
        featureTodaySection.findElement(By.className("ipc-media__img"));
        driver.switchTo().defaultContent();
        WebElement whatToSee = driver.findElement(By.xpath("//*[text()='What to watch']/../../.."));
        Assert.assertTrue(whatToSee.findElements(By.className("ipc-media__img")).size() > 1);

        driver.switchTo().frame(whatToSee.findElement(By.xpath("//span[text()='Cruella']/../..")));
        WebElement watchlistButton = driver.findElement(By.xpath("//*[text()='Watchlist']/.."));
        driver.switchTo().frame(watchlistButton);
        Assert.assertTrue(driver.findElement(By.className("ipc-icon--add")).isDisplayed());
        watchlistButton.click();
        Assert.assertTrue(driver.findElement(By.className("ipc-icon--done")).isDisplayed());
        driver.switchTo().defaultContent();
    }


}
