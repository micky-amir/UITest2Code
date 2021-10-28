package websites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Locale;

import static org.junit.Assert.*;

public class FoxNews2
{
    ChromeDriver driver;
    WebElement element;

    @Before
    public void initDriver()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown()
    {
        // Quit the driver
        driver.quit();
    }

    /**
     * FXN25
     * Mika
     * HTML refer to FXN25
     */
    @Test
    public void FXN25_Mika()
    {
        String newTab;
        String topic = "Business";
        String category = "Economy";
        String pageToChoose = "Small Business";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("menu-" + topic.toLowerCase(Locale.ROOT))).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("search"))).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxbusiness.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@aria-label='header nav item " + category + "']//*[text()='" + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[contains(text(), '" + pageToChoose + "')]")));
    }

    /**
     * FXN26
     * Mika
     * HTML refer to FXN25
     */
    @Test
    public void FXN26_Mika()
    {
        String newTab;
        String topic = "Business";
        String category = "Markets";
        String pageToChoose = "Retail";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("menu-" + topic.toLowerCase(Locale.ROOT))).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("search"))).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxbusiness.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@aria-label='header nav item " + category + "']//*[text()='" + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[contains(text(), '" + pageToChoose + "')]")));
    }

    /**
     * FXN27
     * Mika
     * HTML refer to FXN25
     */
    @Test
    public void FXN27_Mika()
    {
        String newTab;
        String topic = "Business";
        String category = "Lifestyle";
        String pageToChoose = "Travel";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("menu-" + topic.toLowerCase(Locale.ROOT))).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("search"))).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxbusiness.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@aria-label='header nav item " + category + "']//*[text()='" + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[contains(text(), '" + pageToChoose + "')]")));
    }

    /**
     * FXN28
     * Mika
     * HTML refer to FXN25
     */
    @Test
    public void FXN28_Mika()
    {
        String newTab;
        String topic = "Business";
        String category = "Real Estate";
        String pageToChoose = "Commercial";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("menu-" + topic.toLowerCase(Locale.ROOT))).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("search"))).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxbusiness.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@aria-label='header nav item " + category + "']//*[text()='" + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[contains(text(), '" + pageToChoose + "')]")));
    }

    /**
     * FXN29
     * Mika
     * HTML refer to FXN25
     */
    @Test
    public void FXN29_Mika()
    {
        String newTab;
        String topic = "Business";
        String category = "Tech";
        String pageToChoose = "Space";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("menu-" + topic.toLowerCase(Locale.ROOT))).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("search"))).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxbusiness.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@aria-label='header nav item " + category + "']//*[text()='" + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[contains(text(), '" + pageToChoose + "')]")));
    }

    /**
     * FXN30
     * Mika
     * HTML refer to FXN25
     */
    @Test
    public void FXN30_Mika()
    {
        String newTab;
        String topic = "Business";
        String category = "Shows";
        String pageToChoose = "Mornings with Maria";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("menu-" + topic.toLowerCase(Locale.ROOT))).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("search"))).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxbusiness.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@aria-label='header nav item " + category + "']//*[text()='" + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//h1[contains(text(), '" + pageToChoose + "')]")));
    }

    /**
     * FXN31
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31
     */
    @Test
    public void FXN31_Mika()
    {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[] { "Our Brands", "Life At FOX", "Internships", "Job Search", "My Account" };

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());
    }

    /**
     * FXN32
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31, FXN32
     */
    @Test
    public void FXN32_Mika()
    {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[] { "Our Brands", "Life At FOX", "Internships", "Job Search", "My Account" };
        String itemToChoose = "Our Brands";
        String optionInItem = "Fox Corporation";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());

        driver.findElement(By.xpath("//a[@title='" + itemToChoose + "']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='" + itemToChoose + "']")));

        try
        {
            driver.findElement(By.xpath("//*[@alt='close']")).click();
        }
        catch (Exception ignored)
        {
            ;
        }

        element = driver.findElement(By.xpath("//*[contains(@class, 'sectionNav') and text()='" + optionInItem + "']"));
        action.moveToElement(element).perform();
        element.click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='View Jobs']")));
        action.moveToElement(element).perform();
        element.click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));
        assertTrue(Integer.parseInt(driver.findElement(By.id("jobsCount")).getText()) >= 1);
    }

    /**
     * FXN33
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31
     */
    @Test
    public void FXN33_Mika() throws InterruptedException
    {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[] { "Our Brands", "Life At FOX", "Internships", "Job Search", "My Account" };
        String itemToChoose = "Job Search";
        String textToType = "Sales";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[@aria-label='search foxnews.com']")));
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());

        driver.findElement(By.xpath("//a[@title='" + itemToChoose + "']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='" + itemToChoose + "']")));

        try
        {
            driver.findElement(By.xpath("//*[@alt='close']")).click();
        }
        catch (Exception ignored)
        {
            ;
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.id("keywordSearch"))).sendKeys(textToType);
        driver.findElement(By.xpath("//*[@id='filterButton' and @value='Go']")).click();

        Thread.sleep(2000);
        assertTrue(Integer.parseInt(driver.findElement(By.id("jobsCount")).getText()) >= 1);
    }

    /**
     * FXN34
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31
     */
    @Test
    public void FXN34_Mika() throws InterruptedException
    {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[] { "Our Brands", "Life At FOX", "Internships", "Job Search", "My Account" };
        String itemToChoose = "Job Search";
        String checkboxToSelect = "Administrative";

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());

        driver.findElement(By.xpath("//a[@title='" + itemToChoose + "']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='" + itemToChoose + "']")));

        try
        {
            driver.findElement(By.xpath("//*[@alt='close']")).click();
        }
        catch (Exception ignored)
        {
            ;
        }

        element = driver.findElement(By.id(checkboxToSelect + "_checkbox"));
        action.moveToElement(element).click().perform();
        wait.until(ExpectedConditions.elementToBeSelected(element));

        Thread.sleep(2000);
        assertTrue(Integer.parseInt(driver.findElement(By.id("jobsCount")).getText()) >= 1);
    }

    /**
     * FXN35 not finished
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31
     */
    @Test
    public void FXN35_Mika() throws InterruptedException
    {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[] { "Our Brands", "Life At FOX", "Internships", "Job Search", "My Account" };
        String itemToChoose = "Job Search";
        String[] checkboxesToSelect = new String[] { "Fox Sports", "India" };

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());

        driver.findElement(By.xpath("//a[@title='" + itemToChoose + "']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='" + itemToChoose + "']")));

        try
        {
            driver.findElement(By.xpath("//*[@alt='close']")).click();
        }
        catch (Exception ignored)
        {
            ;
        }

        for (String checkbox : checkboxesToSelect)
        {
            element = driver.findElement(By.id(checkbox + "_checkbox"));
            action.moveToElement(element).click().perform();
            Thread.sleep(2000);
        }

        wait.until(ExpectedConditions.textToBe(By.id("jobsCount"), "0"));
        element = driver.findElement(By.linkText("New Job Search"));
        assertTrue(element.isDisplayed());

        action.moveToElement(element).click().perform();

        for (String checkbox : checkboxesToSelect)
        {
            element = driver.findElement(By.id(checkbox + "_checkbox"));
            wait.until(ExpectedConditions.elementSelectionStateToBe(element, false));
        }
    }

    /**
     * FXN36 not finished
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31
     */
    @Test
    public void FXN36_Mika()
    {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[] { "Our Brands", "Life At FOX", "Internships", "Job Search", "My Account" };
        String itemToChoose = "Internships";
        String newTab;

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());

        driver.findElement(By.xpath("//a[@title='" + itemToChoose + "']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='" + itemToChoose + "']")));

        try
        {
            driver.findElement(By.xpath("//*[@alt='close']")).click();
        }
        catch (Exception ignored)
        {
            ;
        }

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Search for Internships']")));
        action.moveToElement(element).click().perform();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));
        assertTrue(Integer.parseInt(driver.findElement(By.id("jobsCount")).getText()) >= 1);
        assertTrue(driver.findElement(By.id("Internships_checkbox")).isSelected());
    }

    /**
     * FXN37 not finished
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31
     */
    @Test
    public void FXN37_Mika()
    {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[] { "Our Brands", "Life At FOX", "Internships", "Job Search", "My Account" };
        String itemToChoose = "My Account";
        String emailAddress = "qa.tries.123@gmail.com";
        String password = "Qa@123456789";
        String newTab;

        driver.close();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        try
        {
            driver.findElement(By.xpath("//*[@aria-label='close']")).click();
        }
        catch (Exception ignored)
        {
            ;
        }

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")))
                .click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());

        driver.findElement(By.xpath("//a[@title='" + itemToChoose + "']")).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()='FOX Careers TERMS OF USE AGREEMENTS']")));

        try
        {
            driver.findElement(By.xpath("//*[@alt='close']")).click();
        }
        catch (Exception ignored)
        {
            ;
        }

        assertTrue(driver.findElement(By.xpath("//label[text()='I have read and understood the ']")).isDisplayed());
        assertTrue(driver.findElement(By.linkText("Privacy Policy")).isDisplayed());

        driver.findElement(By.name("PrivacyPolicyCheckbox")).click();
        driver.findElement(By.xpath("//*[@value='Submit']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-automation-id='email']")));
        assertTrue(driver.findElement(By.xpath("//*[@data-automation-id='password']")).isDisplayed());

        driver.findElement(By.xpath("//*[@data-automation-id='createAccountLink']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Create Account']")));

        driver.findElement(By.xpath("//*[@data-automation-id='email']")).sendKeys(emailAddress);
        driver.findElement(By.xpath("//*[@data-automation-id='password']")).sendKeys(password);
        driver.findElement(By.xpath("//*[@data-automation-id='verifyPassword']")).sendKeys(password);

        driver.findElement(By.xpath("//*[@aria-label='Create Account']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2/*[@title='Candidate Home']")));
        driver.findElement(By.xpath("//*[text()='" + emailAddress + "']")).click();

        element = driver.findElement(By.xpath("//*[@aria-label='Account Settings']"));
        assertTrue(element.isDisplayed());

        element = driver.findElement(By.xpath("//*[@aria-label='Sign Out']"));
        assertTrue(element.isDisplayed());
        element.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Sign In']")));
    }

    /**
     * FXN38 not finished
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31
     */
    @Test
    public void FXN38_Mika()
    {
        String category = "About";
        String pageToChoose = "Careers";
        String[] navigableItems = new String[] { "Our Brands", "Life At FOX", "Internships", "Job Search", "My Account" };
        String itemToChoose = "My Account";
        String emailAddress = "qa.tries.123@gmail.com";
        String password = "Qa@123456789";
        String newTab;
        String jobsNumString;
        int jobsNumInt;

        driver.close();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 15);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        try
        {
            driver.findElement(By.xpath("//*[@aria-label='close']")).click();
        }
        catch (Exception ignored)
        {
            ;
        }

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        wait.until(ExpectedConditions.elementToBeClickable
                        (By.xpath("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']")))
                .click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());

        driver.findElement(By.xpath("//a[@title='" + itemToChoose + "']")).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()='FOX Careers TERMS OF USE AGREEMENTS']")));

        try
        {
            driver.findElement(By.xpath("//*[@alt='close']")).click();
        }
        catch (Exception ignored)
        {
            ;
        }

        assertTrue(driver.findElement(By.xpath("//label[text()='I have read and understood the ']")).isDisplayed());
        assertTrue(driver.findElement(By.linkText("Privacy Policy")).isDisplayed());

        driver.findElement(By.name("PrivacyPolicyCheckbox")).click();
        driver.findElement(By.xpath("//*[@value='Submit']")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-automation-id='email']")));
        element.sendKeys(emailAddress);
        element = driver.findElement(By.xpath("//*[@data-automation-id='password']"));
        element.sendKeys(password);
        driver.findElement(By.xpath("//*[@aria-label='Sign In']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()='" + emailAddress + "']")));

        driver.findElement(By.xpath("//button[text()='Search for Jobs']")).click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[@aria-label='Search Results']//*[contains(text(), 'Results')]")));
        jobsNumString = element.getText();
        jobsNumInt = Integer.parseInt(jobsNumString.substring(0, jobsNumString.indexOf(' ')));
        assertTrue(jobsNumInt >= 1);
    }

    /**
     * FXN39 not finished
     * Mika
     * HTML refer to FXN25 - home_page.html, FXN31
     */
    @Test
    public void FXN39_Mika()
    {
        String category = "About";
        String pageToChoose = "Media Relations";
        String[] navigableItems = new String[] { "Our Brands", "Life At FOX", "Internships", "Job Search", "My Account" };

        driver.get("https://www.foxnews.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Hot Topics']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@class='subnav-title' and text()='Markets']")).isDisplayed());

        driver.findElement(By.className("js-focus-search")).click();

        assertTrue(driver.findElement(By.xpath("//*[@aria-label='search foxnews.com']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[@aria-label='submit search']")).isDisplayed());

        element = driver.findElement(By.xpath
                ("//*[@class='expandable-nav']//*[@aria-label='" + category + " - " + pageToChoose + "']"));
        action.moveToElement(element).click().perform();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[contains(@class, 'h2') and text()='Job Search']")));

        for (String item : navigableItems)
            assertTrue(driver.findElement(By.xpath("//a[@title='" + item + "']")).isDisplayed());
    }
}