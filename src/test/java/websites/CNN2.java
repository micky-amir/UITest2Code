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
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(countryXPath))).click();
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
     * CNN42 not finished
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN35 - hamburger_menu.html, CNN41
     */
    @Test
    public void CNN42_Mika()
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

        driver.findElement(By.xpath
                ("//li[1]//*[@class='media']/following-sibling::*[@class='cd__content']/h3[@class='cd__headline']"))
                .click();

        driver.navigate().back();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='" + sportToSelect + "']")));
    }
}
