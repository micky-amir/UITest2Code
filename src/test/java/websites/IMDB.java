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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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
     * Tamar
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
     * not done
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
        WebDriverWait wait = new WebDriverWait(driver, 10000);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@class='popover']/ol/li[4]")));
        //actions.moveByOffset(0, 0).click(element).perform();
        //element.findElement(By.xpath(".//*[text()='4']")).click();
        element.findElement(By.xpath("//*[@id=\"main\"]/div/span/div/div/div[3]/table/tbody/tr[2]/td[4]/div/div[1]/div/ol/li[4]")).click();

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
}
