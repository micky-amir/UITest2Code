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

import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

public class Bing2 {
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