package websites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

public class CNN2
{
    private static WebDriver driver;
    private static WebElement element;

    @Before
    public void setUp()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    /**
     * CNN25
     * Mika
     * HTML refer to CNN25
     */
    @Test
    public void CNN25_Mika()
    {
        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.findElements(By.className("user-icon")).get(0).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.cssSelector("*[data-zjs-component_id='signup_link']"))).click();

        assertTrue(driver.findElement(By.xpath("//button[contains(text(),\"Create account\")]"))
                .isDisplayed());
    }

    /**
     * CNN26
     * Mika
     * HTML refer to CNN25 and CNN26
     */
    @Test
    public void CNN26_Mika()
    {
        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.findElements(By.className("user-icon")).get(0).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.cssSelector("*[data-zjs-component_id='signup_link']"))).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.cssSelector("*[aria-label='Email Address']")));
        element.sendKeys("abc@xyz");
        element.sendKeys("\t");
        assertTrue(driver.findElement(By.xpath("//*[text()=\"Please enter a valid email address\"]"))
                .isDisplayed());
        element.clear();
        element.sendKeys("yourname@domain.com");
        //There's a need to delete this user or change the email before running the test again.
        element.sendKeys("\t");

        element = driver.findElement(By.cssSelector("*[aria-label='Password']"));
        element.sendKeys("admintst");
        assertTrue(driver.findElement(By.xpath("//*[text()=\"Please enter a valid password\"]"))
                .isDisplayed());
        assertTrue(driver.findElement(By.className("formfield-text__validation-list"))
                .isDisplayed()); //I didn't manage to find a way to get to that element using the words in the description.
        element.clear();
        element.sendKeys("user@1234567");

        driver.findElement(By.xpath("//button[contains(text(),\"Create account\")]")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("account-icon-button"))).click();
        assertTrue(driver.findElement(By.name("userSettings")).isDisplayed());
        assertTrue(driver.findElement(By.name("userLogout")).isDisplayed());
    }

    /**
     * CNN27
     * Mika
     * HTML refer to CNN25 - home_page.html, log_in_page.html
     */
    @Test
    public void CNN27_Mika()
    {
        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.findElements(By.className("user-icon")).get(0).click();

        driver.findElement(By.cssSelector("*[aria-label='Email address']"))
                .sendKeys("qa.tries.123@gmail.com");
        driver.findElement(By.cssSelector("*[aria-label='Password']"))
                .sendKeys("qa@123456789");
        driver.findElement(By.xpath("//button[text()=\"Log in\"]")).click();

        wait.until(ExpectedConditions.urlToBe("https://edition.cnn.com/"));
    }

    /**
     * CNN28
     * Mika
     * HTML refer to CNN25 - home_page.html, log_in_page.html, CNN26, CNN28
     */
    @Test
    public void CNN28_Mika()
    {
        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        driver.findElements(By.className("user-icon")).get(0).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("*[aria-label='Email address']")))
                .sendKeys("qa.tries.123@gmail.com");
        driver.findElement(By.cssSelector("*[aria-label='Password']"))
                .sendKeys("qa@123456789");
        driver.findElement(By.xpath("//button[text()=\"Log in\"]")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("account-icon-button"))).click();
        driver.findElement(By.name("userSettings")).click();

        action.moveByOffset(0, 30).perform();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Browse all newsletters")));
        action.moveToElement(element).click().perform();

        action.moveByOffset(0, 20).perform();
        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//button[@title=\"Subscribe to Breaking News\"]")));
        action.moveToElement(element).click().perform();
        wait.until(ExpectedConditions.attributeToBe(element, "data-zjs-btn-status", "subscribed"));
        action.moveToElement(element).click().perform();
        wait.until(ExpectedConditions.attributeToBe(element, "data-zjs-btn-status", "not_subscribed"));
    }

    /**
     * CNN29
     * Mika
     * HTML refer to CNN25 - home_page.html, log_in_page.html, CNN26, CNN28 - settings_page.html
     */
    @Test
    public void CNN29_Mika()
    {
        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.findElements(By.className("user-icon")).get(0).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("*[aria-label='Email address']")))
                .sendKeys("qa.tries.123@gmail.com");
        driver.findElement(By.cssSelector("*[aria-label='Password']"))
                .sendKeys("qa@123456789");
        driver.findElement(By.xpath("//button[text()=\"Log in\"]")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("account-icon-button"))).click();
        driver.findElement(By.name("userSettings")).click();

        wait.until(ExpectedConditions.elementToBeClickable
                (By.cssSelector("*[data-zjs-traits-component_type='displayname_info']"))).click();
        //I didn't manage to find a way to get to that element using the words in the description,
        //cause there's another element which is almost the same.
        element = driver.findElement(By.xpath("//*[@aria-label=\"Display name\"]"));
        assertTrue(element.isEnabled());
        element.clear();
        element.sendKeys("name");
        driver.findElement(By.xpath("//button[text()=\"Save\"]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[@data-zjs-traits-component_type='displayname_info' and text()='Edit']")));
    }

    /**
     * CNN30
     * Mika
     * HTML refer to CNN25 - home_page.html, log_in_page.html, CNN26, CNN28 - settings_page.html, CNN30
     */
    @Test
    public void CNN30_Mika()
    {
        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.findElements(By.className("user-icon")).get(0).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("*[aria-label='Email address']")))
                .sendKeys("qa.tries.123@gmail.com");
        driver.findElement(By.cssSelector("*[aria-label='Password']"))
                .sendKeys("qa@123456789");
        driver.findElement(By.xpath("//button[text()=\"Log in\"]")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("account-icon-button"))).click();
        driver.findElement(By.name("userSettings")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("settings-header")));
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        element = driver.findElement(By.xpath("//*[starts-with(@class, 'deleteAccountButton')]"));
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        driver.findElement(By.id("delete-account--nevermind-btn")).click();
    }

    /**
     * CNN31
     * Mika
     * HTML refer to CNN25 - home_page.html, log_in_page.html, CNN26, CNN28 - settings_page.html, CNN30
     */
    @Test
    public void CNN31_Mika()
    {
        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.findElements(By.className("user-icon")).get(0).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("*[aria-label='Email address']")))
                .sendKeys("qa.tries.123@gmail.com");
        driver.findElement(By.cssSelector("*[aria-label='Password']"))
                .sendKeys("qa@123456789");
        driver.findElement(By.xpath("//button[text()=\"Log in\"]")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("account-icon-button"))).click();
        driver.findElement(By.name("userSettings")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("settings-header")));
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        element = driver.findElement(By.xpath("//*[starts-with(@class, 'deleteAccountButton')]"));
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        driver.findElement(By.id("delete-account--delete-btn")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()=\"Your CNN account has been deleted\"]")));
        wait.until(ExpectedConditions.urlToBe("https://edition.cnn.com/"));
    }

    /**
     * CNN32
     * Mika
     * HTML refer to CNN25 - home_page.html, log_in_page.html, CNN26, CNN28 - settings_page.html, CNN30
     */
    @Test
    public void CNN32_Mika()
    {
        String emailAddress = "qa.tries.123@gmail.com";
        String password = "qa@123456789";

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.findElements(By.className("user-icon")).get(0).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("*[aria-label='Email address']")))
                .sendKeys(emailAddress);
        driver.findElement(By.cssSelector("*[aria-label='Password']"))
                .sendKeys(password);
        driver.findElement(By.xpath("//button[text()=\"Log in\"]")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("account-icon-button"))).click();
        driver.findElement(By.name("userSettings")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("settings-header")));
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        element = driver.findElement(By.xpath("//*[starts-with(@class, 'deleteAccountButton')]"));
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        driver.findElement(By.id("delete-account--delete-btn")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()=\"Your CNN account has been deleted\"]")));
        wait.until(ExpectedConditions.urlToBe("https://edition.cnn.com/"));

        driver.findElements(By.className("user-icon")).get(0).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("*[aria-label='Email address']")))
                .sendKeys(emailAddress);
        driver.findElement(By.cssSelector("*[aria-label='Password']"))
                .sendKeys(password);
        driver.findElement(By.xpath("//button[text()=\"Log in\"]")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()=\"You have entered an invalid username or password\"]")));
    }

    /**
     * CNN33
     * Mika
     * HTML refer to CNN25, CNN26, CNN28 - settings_page.html, CNN30
     */
    @Test
    public void CNN33_Mika()
    {
        String emailAddress = "qa.tries.123@gmail.com";
        String password = "qa@123456789";

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.findElements(By.className("user-icon")).get(0).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("*[aria-label='Email address']")))
                .sendKeys(emailAddress);
        driver.findElement(By.cssSelector("*[aria-label='Password']"))
                .sendKeys(password);
        driver.findElement(By.xpath("//button[text()=\"Log in\"]")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("account-icon-button"))).click();
        driver.findElement(By.name("userSettings")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("settings-header")));
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        element = driver.findElement(By.xpath("//*[starts-with(@class, 'deleteAccountButton')]"));
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        driver.findElement(By.id("delete-account--delete-btn")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()=\"Your CNN account has been deleted\"]")));
        wait.until(ExpectedConditions.urlToBe("https://edition.cnn.com/"));

        driver.findElements(By.className("user-icon")).get(0).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.cssSelector("*[data-zjs-component_id='signup_link']"))).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.cssSelector("*[aria-label='Email Address']")));
        element.sendKeys(emailAddress);
        element.sendKeys("\t");
        element = driver.findElement(By.cssSelector("*[aria-label='Password']"));
        element.sendKeys(password);
        driver.findElement(By.xpath("//button[contains(text(),\"Create account\")]")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("account-icon-button"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("userSettings")));
        assertTrue(driver.findElement(By.name("userLogout")).isDisplayed());
    }

    /**
     * CNN34
     * Mika
     * HTML refer to CNN25 - home_page.html, log_in_page.html, CNN26
     */
    @Test
    public void CNN34_Mika()
    {
        String emailAddress = "qa.tries.123@gmail.com";
        String password = "qa@123456789";

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.findElements(By.className("user-icon")).get(0).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("*[aria-label='Email address']")))
                .sendKeys(emailAddress);
        driver.findElement(By.cssSelector("*[aria-label='Password']"))
                .sendKeys(password);
        driver.findElement(By.xpath("//button[text()=\"Log in\"]")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("account-icon-button"))).click();
        driver.findElement(By.name("userLogout")).click();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy
                (By.className("user-icon"))).get(0).click();

        wait.until(ExpectedConditions.urlToBe("https://edition.cnn.com/account/log-in"));
    }

    /**
     * CNN35
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN35
     */
    @Test
    public void CNN35_Mika()
    {
        String[] countries = new String[]
                {"Africa", "Americas", "Asia", "Australia", "China", "Europe", "India", "Middle East", "United Kingdom"};
        String countryXPath;

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        for (String country : countries)
        {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("menu-icon"))).click();
            countryXPath = "//*[text()='" + country +
                    "' and @data-analytics='header_expanded-nav' and @type='expanded']";
            element = driver.findElements(By.xpath(countryXPath)).get(0);
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
            countryXPath = "//h1[text()='" + country + "']";
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(countryXPath)));
        }
    }

    /**
     * CNN36
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN35, CNN36
     */
    @Test
    public void CNN36_Mika()
    {
        String[] countries = new String[]
                {"Africa", "Americas", "Asia", "Australia", "China", "Europe", "India", "Middle East", "United Kingdom"};
        String countryXPath;

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.findElement(By.xpath("//*[@name='world' and @data-analytics='header_top-nav']")).click();

        for (String country : countries)
        {
            countryXPath = "//a[text()='" + country + "' and @data-analytics='header_top-nav']";
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(countryXPath))).click();
            countryXPath = "//h1[text()='" + country + "']";
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(countryXPath)));
        }
    }

    /**
     * CNN37
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN35 - hamburger_menu.html, CNN36
     */
    @Test
    public void CNN37_Mika()
    {
        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[@name='world' and @data-analytics='header_top-nav']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='World']")));

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[@aria-label='World' and @data-analytics='footer']/.."))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='World']")));

        driver.findElement(By.className("menu-icon")).click();
        driver.findElement(By.xpath("//*[@name='world' and @data-analytics='header_expanded-nav']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='World']")));
    }

    /**
     * CNN38
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN38
     */
    @Test
    public void CNN38_Mika() throws InterruptedException
    {
        String textToSearch = "Flood";
        String resultsNumText = "";
        List<WebElement> articles;
        WebElement headline, body;

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 15);

        driver.findElement(By.xpath("//*[@class='search-icon' and @tabindex='0']")).click();
        element = driver.findElement(By.id("header-search-bar"));
        element.sendKeys(textToSearch);
        element.sendKeys(Keys.ENTER);

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("newest")));
        resultsNumText = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.className("cnn-search__results-count"))).getText();
        element.click();

        element = driver.findElement(By.id("relevance"));
        assertTrue(element.isDisplayed());
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[@class='facet_item clicked' and @id='relevance']")));

        Thread.sleep(1000);
        assertEquals(resultsNumText, driver.findElement(By.className("cnn-search__results-count")).getText());

        articles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy
                (By.className("cnn-search__result-contents")));

        for (WebElement article : articles)
        {
            headline = article.findElement(By.className("cnn-search__result-headline"));
            body = article.findElement(By.className("cnn-search__result-body"));
            assertTrue(headline.findElement(By.tagName("a")).getText().toLowerCase(Locale.ROOT).contains(textToSearch.toLowerCase(Locale.ROOT))
            || body.getText().toLowerCase(Locale.ROOT).contains(textToSearch.toLowerCase(Locale.ROOT)));
        }
    }

    /**
     * CNN39
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN38 - search_menu.html, Flood_search_results_by_date.html
     */
    @Test
    public void CNN39_Mika()
    {
        String textToSearch = "Flood";

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 15);

        driver.findElement(By.xpath("//*[@class='search-icon' and @tabindex='0']")).click();
        element = driver.findElement(By.id("header-search-bar"));
        element.sendKeys(textToSearch);
        element.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[@class='facet_item clicked' and @id='newest']")));
    }

    /**
     * CNN40
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN38 - search_menu.html, CNN40
     */
    @Test
    public void CNN40_Mika()
    {
        String textToSearch = "xyzaaaa";

        driver.get("https://edition.cnn.com/");

        driver.findElement(By.xpath("//*[@class='search-icon' and @tabindex='0']")).click();
        element = driver.findElement(By.id("header-search-bar"));
        element.sendKeys(textToSearch);
        element.sendKeys(Keys.ENTER);

        assertTrue(driver.findElement
                (By.xpath("//h3[text()=' did not match any documents.']")).isDisplayed());
    }

    /**
     * CNN41
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN35 - hamburger_menu.html, CNN41
     */
    @Test
    public void CNN41_Mika()
    {
        String sportToSelect = "Football";

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        driver.findElement(By.className("menu-icon")).click();
        element = driver.findElement
                (By.xpath("//*[@name='" + sportToSelect.toLowerCase() + "' and @data-analytics='header_expanded-nav']"));
        action.moveToElement(element).click().perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='" + sportToSelect + "']")));
    }

    /**
     * CNN42
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN35 - hamburger_menu.html, CNN41, CNN42
     */
    @Test
    public void CNN42_Mika()
    {
        String sportToSelect = "Football";
        String articleTitle = "";

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        driver.findElement(By.className("menu-icon")).click();
        element = driver.findElement
                (By.xpath("//*[@name='" + sportToSelect.toLowerCase() + "' and @data-analytics='header_expanded-nav']"));
        action.moveToElement(element).click().perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='" + sportToSelect + "']")));

        element = driver.findElement(By.xpath
                ("//li[1]//*[@class='media']/following-sibling::*[@class='cd__content']/h3[@class='cd__headline']"));
        //There's more than 1 element with this xpath, but we want the first one anyway.
        articleTitle = element.findElement(By.xpath("//*[contains(@class, 'cd__headline-text')]")).getText();
        action.moveToElement(element).click().perform();

        assertEquals(articleTitle,
                wait.until(ExpectedConditions.presenceOfElementLocated(By.className("pg-headline"))).getText());

        driver.navigate().back();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='" + sportToSelect + "']")));
    }

    /**
     * CNN43 not finished
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN35 - hamburger_menu.html, CNN43
     */
    @Test
    public void CNN43_Mika() throws InterruptedException
    {
        String periodName = "";
        String region = "Asia";
        String day = "Monday, October 25, 2021";
        int dayNum = 1;

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.findElement(By.className("menu-icon")).click();
        element = driver.findElement
                (By.xpath("//*[@name='tv-schedule' and @data-analytics='header_expanded-nav']"));
        action.moveToElement(element).click().perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='TV Schedule - CNN']")));

        element = driver.findElement(By.xpath("//*[contains(@class, 'region__container')]"));
        element.findElement(By.xpath("//*[contains(@class, 'select')]")).click();
        element.findElement(By.linkText(region)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath
                ("//h1[text()='Intl - TV Schedule " + region + "']")));
        element = driver.findElements(By.xpath("//h2[@class='zn-header__text']")).get(0);
        assertEquals("Morning", element.findElement(By.tagName("a")).getText());

        element = driver.findElement(By.xpath("//*[contains(@class, 'day__container')]"));
        driver.findElement(By.xpath("//*[contains(@class, 'day__container')]//*[contains(@class, 'select')]")).click();
        wait.until(ExpectedConditions.elementToBeClickable
                (element.findElement(By.xpath("//li[text()='" + day + "']")))).click();
        assertEquals(day, element.findElement(By.xpath("//*[contains(@class, 'initial-day__label')]")).getText());

        List<WebElement> dayPeriods = driver.findElements
                (By.xpath("//ul[contains(@class, 'day-period')]/li[contains(@class, 'day-period')]"));

        for (WebElement period : dayPeriods)
        {
            periodName = period.getText();
            action.moveToElement(period).click().perform();
            Thread.sleep(2000);
            element = driver.findElement(By.xpath("//*[contains(@class, 'tv_schedule_day_" + dayNum
                    + "')]//h2[@class='zn-header__text']/a[text()='" + periodName + "']"));
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
        }
    }

    /**
     * CNN44 not finished
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN35 - hamburger_menu.html, CNN41, CNN42
     */
    @Test
    public void CNN44_Mika()
    {
        String location = "United States";

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        driver.findElement(By.className("menu-icon")).click();
        element = driver.findElement
                (By.xpath("//*[@name='weather' and @data-analytics='header_expanded-nav']"));
        action.moveToElement(element).click().perform();

        element = wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//input[@placeholder='Enter Location']")));
        element.click();
        element.sendKeys(location);
        driver.findElement(By.xpath("//*[@class='tt-dropdown-menu']/*[@class='tt-dataset-user-location']")).click();
        // driver.findElement(By.xpath("//*[@value='Get Forecast']"));

        driver.findElement(By.xpath("//*[@data-temp='fahrenheit']")).click();
        assertTrue(driver.findElement(By.xpath("//*[@data-temptype='fahrenheit']")).isDisplayed());
    }

    /**
     * CNN45
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN35 - hamburger_menu.html, CNN45
     */
    @Test
    public void CNN45_Mika()
    {
        String location = "United States";

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        driver.findElement(By.className("menu-icon")).click();
        element = driver.findElement
                (By.xpath("//*[@name='wildfire-tracker' and @data-analytics='header_expanded-nav']"));
        action.moveToElement(element).click().perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(), 'wildfires')]")));

        element = driver.findElement(By.xpath("//h3[text()='Air quality']"));
        action.moveToElement(element).perform();
        element = driver.findElement(By.xpath("//input[@placeholder='Search your location']"));
        element.click();
        element.sendKeys(location);
        element.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'air quality index')]")));
    }

    /**
     * CNN46
     * Mika
     * HTML refer to CNN25 - home_page.html
     */
    @Test
    public void CNN46_Mika()
    {
        String[] socialNetworks = new String[] {"facebook", "instagram", "twitter"};
        String currentNetwork = "facebook";
        String expectedUrl = "https://www.facebook.com/cnninternational";
        String newTab;

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        for (String socialNetwork : socialNetworks)
        {
            assertTrue(driver.findElement(By.xpath("//*[@id='footer-wrap']//*[@data-icon='" + socialNetwork + "']"))
                    .isDisplayed());
        }

        driver.findElement(By.xpath("//*[@id='footer-wrap']//*[@data-icon='" + currentNetwork + "']")).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
    }

    /**
     * CNN47
     * Mika
     * HTML refer to CNN25 - home_page.html
     */
    @Test
    public void CNN47_Mika()
    {
        String[] socialNetworks = new String[] {"facebook", "instagram", "twitter"};
        String currentNetwork = "twitter";
        String expectedUrl = "https://twitter.com/cnni";
        String newTab;

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        for (String socialNetwork : socialNetworks)
        {
            assertTrue(driver.findElement(By.xpath("//*[@id='footer-wrap']//*[@data-icon='" + socialNetwork + "']"))
                    .isDisplayed());
        }

        driver.findElement(By.xpath("//*[@id='footer-wrap']//*[@data-icon='" + currentNetwork + "']")).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
    }

    /**
     * CNN48
     * Mika
     * HTML refer to CNN25 - home_page.html
     */
    @Test
    public void CNN48_Mika()
    {
        String[] socialNetworks = new String[] {"facebook", "instagram", "twitter"};
        String currentNetwork = "instagram";
        String expectedUrl = "https://www.instagram.com/cnn/";
        String newTab;

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        for (String socialNetwork : socialNetworks)
        {
            assertTrue(driver.findElement(By.xpath("//*[@id='footer-wrap']//*[@data-icon='" + socialNetwork + "']"))
                    .isDisplayed());
        }

        driver.findElement(By.xpath("//*[@id='footer-wrap']//*[@data-icon='" + currentNetwork + "']")).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
    }

    /**
     * CNN49 not finished - add assertions
     * Mika
     * HTML refer to CNN25 - home_page.html
     */
    @Test
    public void CNN49_Mika()
    {
        String[] optionsList = new String[] { "Terms of Use", "Privacy Policy", "Accessibility & CC", "AdChoices",
                "About Us", "Modern Slavery Act Statement", "Advertise with us", "CNN Store", "Newsletters",
                "Transcripts", "License Footage", "CNN Newsource", "Sitemap" };
        String[] xpathToCheck = new String[]
                { "//h1[text()='CNN Terms of Use']", "//h1[contains(text(), 'Privacy Policy')]",
                "//h1[contains(text(), 'Accessibility')]", "//h1[contains(text(), 'Advertising Choices')]",
                "//h1[text()='ABOUT CNN DIGITAL']", "//h1[contains(text(), 'Modern Slavery Act Statement')]",
                "//h2[contains(text(), 'Welcome to CNNIC')]", "//h1[contains(text(), 'CNN Store')]",
                "//h1[text()='CNN Newsletters']", "//img[@alt='TRANSCRIPTS']",
                "//h4[contains(text(), 'FOOTAGE')]", "//h1[text()='WE’RE AT THE HEART OF IT']",
                "//h1[contains(text(), 'CNN Site Map')]" };
        List<String> goBack = Arrays.asList("Advertise with us", "CNN Store", "Newsletters",
                "Transcripts", "License Footage", "CNN Newsource");
        String hasPopUp = "AdChoices";
        int popUpFrameNum = 7;

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        for (String option : optionsList)
            assertTrue(driver.findElement(By.linkText(option)).isDisplayed());

        for (int i = 0; i < optionsList.length; i++)
        {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(optionsList[i]))).click();

            if (optionsList[i].equals(hasPopUp))
            {
                driver.switchTo().frame(popUpFrameNum);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathToCheck[i])));
                wait.until(ExpectedConditions.elementToBeClickable
                        (By.xpath("//*[contains(@id, 'closeBtn')]"))).click();
                driver.switchTo().defaultContent();
            }
            else
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathToCheck[i])));

            if (goBack.contains(optionsList[i]))
            {
                driver.navigate().back();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(optionsList[i + 1])));
            }

            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        }
    }

    /**
     * CNN50 not finished - add html pages
     * Mika
     * HTML refer to CNN25 - home_page.html
     */
    @Test
    public void CNN50_Mika()
    {
        String[] optionsList = new String[] { "Terms of Use", "Privacy Policy", "Accessibility & CC", "AdChoices",
                "About Us", "Modern Slavery Act Statement", "Advertise with us", "CNN Store", "Newsletters",
                "Transcripts", "License Footage", "CNN Newsource", "Sitemap" };
        String newTab;
        String textToType = "try";
        String emailAddress = "qa.tries.123@gmail.com";
        String[] feedbackFields = new String[] {"Name", "Email", "Thoughts", "Comments"};

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        for (String option : optionsList)
            assertTrue(driver.findElement(By.linkText(option)).isDisplayed());

        driver.findElement(By.linkText("About Us")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='ABOUT CNN DIGITAL']")));

        element = driver.findElement(By.xpath("//*[text()='SUBMIT FEEDBACK:']"));
        action.moveToElement(element).perform();

        driver.findElement(By.linkText("Have something to say? Click Here")).click();
        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='CNN Feedback']")));

        for (String field : feedbackFields)
        {
            if (field.equals("Email"))
                driver.findElement(By.xpath("//*[@name='feedback" + field + "']")).sendKeys(emailAddress);
            else
                driver.findElement(By.xpath("//*[@name='feedback" + field + "']")).sendKeys(textToType);
        }

        driver.findElement(By.id("js-feedback-send")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//*[text()='Thank you for your feedback.  It has been submitted successfully.']")));
    }
}
