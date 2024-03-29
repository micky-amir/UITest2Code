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

import java.util.*;

import static org.junit.Assert.*;

public class CNN {
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
     * CNN01
     * Tamar
     */
    @Test
    public void CNN01_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("[data-test='searchButton']")).click();
        element = driver.findElement(By.id("header-search-bar"));
        assertEquals(element, driver.switchTo().activeElement());
        element.sendKeys("India");
        driver.findElement(By.cssSelector("button[aria-label='Search']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[text()='Everything']/preceding-sibling::*[@checked]")));
        assertEquals("India", driver.findElement(By.cssSelector(".cnn-search__results-count strong")).getText());
        List<WebElement> resultElements = driver.findElements(By.className("cnn-search__result-contents"));
        for (WebElement result : resultElements) {
            assertTrue(result.getText().toLowerCase(Locale.ROOT).contains("india"));
        }
        driver.findElement(By.id("left_sport")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.className("cnn-search__results-count"),
                driver.findElement(By.className("cnn-search__results-count")).getText())));
        List<WebElement> linkElements = driver.findElements(By.cssSelector(".cnn-search__result-headline a"));
        for (WebElement link : linkElements) {
            String allResultText = link.findElement(By.xpath("./../..")).getText().toLowerCase(Locale.ROOT);
            assertTrue(link.getAttribute("href").contains("/sport/") || allResultText.contains("sport"));
            assertTrue(allResultText.contains("india"));
        }
    }

    /**
     * CNN02
     * Tamar
     * HTML refer to CNN01, CNN02
     */
    @Test
    public void CNN02_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("a[name='videos']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".live-videos")));
        driver.findElement(By.xpath("//a[text()='Live TV']")).click();
        assertEquals("This product isn't currently available in your area.", driver.findElement(By.tagName("h1")).getText());
        By locator = By.xpath("//a[contains(text(), 'Back to the headlines')]");
        assertTrue(driver.findElement(locator).isDisplayed());
        driver.findElement(locator).click();
        assertTrue(driver.getTitle().contains("World News"));
    }

    /**
     * CNN03
     * Tamar
     * HTML refer to CNN01
     */
    @Test
    public void CNN03_Tamar() {
        driver.get("https://edition.cnn.com/");
        String baseUrl = driver.getCurrentUrl();
        assertTrue(driver.getTitle().contains("World News"));

        Map<String, List<String>> languagesInfo = new HashMap<String, List<String>>() {{
            put("U.S.", new ArrayList<>(Arrays.asList("https://us.cnn.com/?hpt=header_edition-picker",
                    "//*[@data-analytics='header_top-nav' and text()='World']")));
            put("Arabic", new ArrayList<>(Arrays.asList("https://arabic.cnn.com/?hpt=header_edition-picker",
                    "//*[@id='navbar']//*[text()='منوعات']")));
            put("Español", new ArrayList<>(Arrays.asList("https://cnnespanol.cnn.com/?hpt=header_edition-picker",
                    "//*[@id='nav-header']//*[text()='Mundo']")));
        }};
        WebDriverWait wait = new WebDriverWait(driver, 10);
        for (String i : languagesInfo.keySet()) {
            driver.findElement(By.id("edition-picker-toggle-desktop")).click();
            if (i.equals("U.S.")) {
                element = driver.findElement(
                        By.xpath("//*[contains(@class, 'indexes__Dropdown')]"));
                assertNotEquals("none", element.getCssValue("display"));
                List<WebElement> optionElements = element.findElements(By.tagName("a"));
                List<String> expectedTitles = new ArrayList<>(
                        Arrays.asList("U.S.", "International", "Arabic", "Español"));
                List<String> actualTitles = new ArrayList<>();
                for (WebElement element : optionElements) {
                    actualTitles.add(element.getText());
                }
                assertEquals(expectedTitles, actualTitles);
            }
            driver.findElement(By.xpath("//*[contains(@class, 'indexes__Dropdown')]//a[text()='" + i + "']")).click();
            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(baseUrl)));
            assertEquals(languagesInfo.get(i).get(0), driver.getCurrentUrl());
            assertTrue(driver.findElement(By.xpath(languagesInfo.get(i).get(1))).isDisplayed());
            driver.navigate().back();
        }
    }

    /**
     * CNN04
     * Tamar
     * HTML refer to CNN01, CNN04
     */
    @Test
    public void CNN04_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("a[name='world']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "World"));
        element = driver.findElement(By.cssSelector("[data-zone-label='Featured sections']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = driver.findElement(By.cssSelector("[data-analytics='Travel_list-hierarchical-xs_article_']"));
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
        element.findElement(By.tagName("img")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("Header__logoTravel")));
        List<WebElement> optionElements = driver.findElements(By.cssSelector(".Header__section"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("DESTINATIONS", "FOOD & DRINK", "NEWS", "STAY", "VIDEO"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * CNN05
     * Tamar
     * HTML refer to CNN01, CNN04, CNN05
     */
    @Test
    public void CNN05_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("a[name='travel']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("Header__logoTravel")));
        List<WebElement> optionElements = driver.findElements(By.cssSelector(".Header__section"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("DESTINATIONS", "FOOD & DRINK", "NEWS", "STAY", "VIDEO"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        optionElements.get(0).click();
        wait.until(ExpectedConditions.textToBe(By.className("Breadcrumb__label"), "DESTINATIONS"));
        List<WebElement> countriesElements = driver.findElements(By.className("Destinations__link"));
        for (int i = 1; i < countriesElements.size(); i++) {
            assertTrue(countriesElements.get(i - 1).getText().compareTo(countriesElements.get(i).getText()) < 0);
        }
        String countryName = countriesElements.get(0).getText();
        countriesElements.get(0).click();
        assertEquals(countryName, driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * CNN06
     * Tamar
     * HTML refer to CNN01, CNN04, CNN05, CNN06
     */
    @Test
    public void CNN06_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("a[name='travel']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("Header__logoTravel")));
        List<WebElement> optionElements = driver.findElements(By.cssSelector(".Header__section"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("DESTINATIONS", "FOOD & DRINK", "NEWS", "STAY", "VIDEO"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        optionElements.get(0).click();
        wait.until(ExpectedConditions.textToBe(By.className("Breadcrumb__label"), "DESTINATIONS"));
        List<WebElement> countriesElements = driver.findElements(By.className("Destinations__link"));
        for (int i = 1; i < countriesElements.size(); i++) {
            assertTrue(countriesElements.get(i - 1).getText().compareTo(countriesElements.get(i).getText()) < 0);
        }
        driver.findElement(By.className("Header__search")).click();
        element = driver.findElement(By.cssSelector("[type='search']"));
        element.sendKeys("Greece");

        List<WebElement> articleElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                (By.className("CardBasic__title"))));
        for (WebElement element : articleElements) {
            assertTrue(element.getText().contains("Greece") ||
                    element.getAttribute("href").contains("greece"));
        }
        driver.navigate().back();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.textToBe(By.className("Breadcrumb__label"), "DESTINATIONS"));
        countriesElements.clear();
        countriesElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy((
                By.className("Destinations__link"))));
        for (int i = 1; i < countriesElements.size(); i++) {
            assertTrue(countriesElements.get(i - 1).getText().compareTo(countriesElements.get(i).getText()) < 0);
        }
    }

    /**
     * CNN07
     * Tamar
     * HTML refer to CNN01, CNN04, CNN07
     */
    @Test
    public void CNN07_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("a[name='travel']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("Header__logoTravel")));
        List<WebElement> optionElements = driver.findElements(By.cssSelector(".Header__section"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("DESTINATIONS", "FOOD & DRINK", "NEWS", "STAY", "VIDEO"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        optionElements.get(4).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("VideoHub__ctaWrapper")));
        assertEquals("Watch now", element.getText());
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".Article__zone")));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(
                By.cssSelector(".pui_control-bar_playback-time > span"), "0:00")));
        element = driver.findElement(By.className("sound-full-icon"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).moveToElement(driver.findElement(By.cssSelector("[aria-label='Volume bar']"))).click().perform();
        assertNotEquals("left: 100%;", driver.findElement(By.cssSelector("[aria-label='Volume bar']")).getAttribute("style"));
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".pui_control-bar_fullscreen-toggle")));
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("windowCollapseIconTitle")));
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("windowExpandIconTitle")));
    }

    /**
     * CNN08
     * Tamar
     * HTML refer to CNN01, CNN04, CNN07
     */
    @Test
    public void CNN08_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("a[name='travel']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("Header__logoTravel")));
        List<WebElement> optionElements = driver.findElements(By.cssSelector(".Header__section"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("DESTINATIONS", "FOOD & DRINK", "NEWS", "STAY", "VIDEO"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        optionElements.get(4).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("VideoHub__ctaWrapper")));
        assertEquals("Watch now", element.getText());
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".Article__zone")));
        By locator = By.cssSelector(".pui_control-bar_playback-time > span");
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.className("VideoPlayer__videoPlayer"))).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, "")));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, "0:00")));
        element = driver.findElement(locator);
        String videoName = driver.findElement(By.cssSelector(".pui_metadata_title")).getText();
        String videoLength = driver.findElement(By.cssSelector(".pui_control-bar_playback-time > :nth-child(3)")).getText();
        while (!element.getText().equals(videoLength)) {
            element = driver.findElement(locator);
        }
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.cssSelector(".pui_metadata_title"), videoName)));
    }

    /**
     * CNN09
     * Tamar
     * HTML refer to CNN01, CNN04, CNN07
     */
    @Test
    public void CNN09_Tamar() throws InterruptedException {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("a[name='travel']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("Header__logoTravel")));
        List<WebElement> optionElements = driver.findElements(By.cssSelector(".Header__section"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("DESTINATIONS", "FOOD & DRINK", "NEWS", "STAY", "VIDEO"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        optionElements.get(4).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("VideoHub__ctaWrapper")));
        assertEquals("Watch now", element.getText());
        Actions actions = new Actions(driver);
        element = driver.findElement(By.xpath("//*[text()='Latest Videos on ']"));
        actions.moveToElement(element).perform();
        List<WebElement> videoRowsElements = driver.findElements(By.className("VideosFilters__row"));
        assertEquals(5, videoRowsElements.size());
        for (WebElement row : videoRowsElements) {
            assertEquals(4, row.findElements(By.className("LayoutGrid__card")).size());
        }
        for (int i = 0; i < 4; i++) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".VideosFilters__showMore")));
            actions.moveToElement(element).click(element).perform();
            Thread.sleep(1000);
            assertEquals(20 * (i + 2), driver.findElements(By.className("LayoutGrid__card")).size());
        }
        assertEquals(0, driver.findElements(By.cssSelector(".VideosFilters__showMore")).size());
    }

    /**
     * CNN10
     * Tamar
     * HTML refer to CNN01, CNN04, CNN10
     */
    @Test
    public void CNN10_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("a[name='world']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "World"));
        element = driver.findElement(By.cssSelector("[data-zone-label='Featured sections']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = driver.findElement(By.cssSelector("[data-analytics='Climate in crisis_list-hierarchical-small-horizontal_article_']"));
        element.findElement(By.tagName("img")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("health-logo-icon")));
        List<WebElement> optionElements = driver.findElements(By.cssSelector("nav a[data-analytics='header_top-nav']"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("Life, But Better", "Fitness", "Food", "Sleep", "Mindfulness", "Relationships"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);

        for (int i = 1; i < expectedTitles.size(); i++) {
            String xpathLocator = "//nav//a[text()='" + expectedTitles.get(i) + "']";
            driver.findElement(By.xpath(xpathLocator)).click();
            wait.until(ExpectedConditions.attributeToBe(By.xpath(xpathLocator + "/.."), "data-selected", "selected"));
            assertTrue(driver.findElement(By.tagName("article")).isDisplayed());
        }
    }

    /**
     * CNN11
     * Tamar
     * HTML refer to CNN01, CNN04, CNN10, CNN11
     */
    @Test
    public void CNN11_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("a[name='world']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "World"));
        element = driver.findElement(By.cssSelector("[data-zone-label='Featured sections']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = driver.findElement(By.cssSelector("[data-analytics='Climate in crisis_list-hierarchical-small-horizontal_article_']"));
        element.findElement(By.tagName("img")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("health-logo-icon")));
        List<WebElement> optionElements = driver.findElements(By.cssSelector("nav a[data-analytics='header_top-nav']"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("Life, But Better", "Fitness", "Food", "Sleep", "Mindfulness", "Relationships"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        optionElements.get(0).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("strong"), "Life, But Better"));
        assertTrue(driver.findElement(By.xpath("//*[text()=\"I'd like a personalized recommendation\"]")).isDisplayed());
        element = driver.findElement(By.xpath("//*[text()=\"I'll pick one myself\"]"));
        assertTrue(element.isDisplayed());
        element.click();

        List<WebElement> titleElements = driver.findElements(By.xpath("//*[@id='close']/../..//a"));
        expectedTitles.remove(0);
        expectedTitles.set(0, "Food");
        expectedTitles.set(1, "Fitness");
        actualTitles.clear();
        for (WebElement element : titleElements) {
            assertEquals("visible", element.getCssValue("visibility"));
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        assertTrue(driver.findElement(By.xpath("//*[text()='Actually, maybe I do need help deciding']")).isDisplayed());
        titleElements.get(2).click();

        wait.until(ExpectedConditions.attributeToBe(By.xpath("//nav//a[text()='Sleep']/.."), "data-selected", "selected"));
        assertTrue(driver.findElement(By.tagName("article")).isDisplayed());
    }

    /**
     * CNN12
     * Tamar
     * HTML refer to CNN01, CNN04, CNN10, CNN11
     */
    @Test
    public void CNN12_Tamar() throws InterruptedException {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("a[name='world']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "World"));
        element = driver.findElement(By.cssSelector("[data-zone-label='Featured sections']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = driver.findElement(By.cssSelector("[data-analytics='Climate in crisis_list-hierarchical-small-horizontal_article_']"));
        element.findElement(By.tagName("img")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("health-logo-icon")));
        List<WebElement> optionElements = driver.findElements(By.cssSelector("nav a[data-analytics='header_top-nav']"));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("Life, But Better", "Fitness", "Food", "Sleep", "Mindfulness", "Relationships"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : optionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        optionElements.get(0).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("strong"), "Life, But Better"));
        element = driver.findElement(By.xpath("//*[text()=\"I'd like a personalized recommendation\"]"));
        assertTrue(element.isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[text()=\"I'll pick one myself\"]")).isDisplayed());
        element.click();

        assertTrue(driver.findElement(By.xpath("//*[text()='How are you doing today?']")).isDisplayed());
        assertTrue(driver.findElements(By.cssSelector(".uppercase p")).size() > 1);
        driver.findElement(By.xpath("//*[text()='I have energy']")).click();
        driver.findElement(By.xpath("//*[text()='Next']")).click();
        assertTrue(driver.findElement(By.xpath("//*[text()='What areas are you interested to learn more about?']")).isDisplayed());
        List<String> clickOnStrings = new ArrayList<>(Arrays.asList(
                "Actualizing goals", "Nutrition", "Rest", "Wellness products", "Meal planning"));
        for (String text : clickOnStrings) {
            driver.findElement(By.xpath("//*[text()='" + text + "']")).click();
        }
        assertTrue(driver.findElement(By.xpath("//*[text()='" + clickOnStrings.get(0) + "']/../..")).getAttribute("class").contains("disabled"));
        driver.findElement(By.xpath("//*[text()=\"That’s it!\"]")).click();
        assertTrue(driver.findElement(By.xpath("//*[text()='Could you rank these?']")).isDisplayed());
        assertTrue(driver.findElements(By.className("smooth-dnd-draggable-wrapper")).size() > 1);

        element = driver.findElement(By.cssSelector(".smooth-dnd-draggable-wrapper:nth-child(5)"));
        actions.clickAndHold(element).perform();
        Thread.sleep(500);
        actions.release(driver.findElement(By.className("smooth-dnd-draggable-wrapper"))).perform();
        driver.findElement(By.xpath("//*[text()='Next']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'recommend')]")));
        element = driver.findElement(By.xpath("//*[contains(text(), 'No thanks! Take me to')]"));
        String category = element.findElement(By.xpath("./strong")).getText();
        element.click();
        wait.until(ExpectedConditions.attributeToBe(By.xpath("//nav//a[text()='" + category + "']/.."), "data-selected", "selected"));
        assertTrue(driver.findElement(By.tagName("article")).isDisplayed());
    }

    /**
     * CNN13
     * Tamar
     * HTML refer to CNN01, CNN04, CNN13
     */
    @Test
    public void CNN13_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("a[name='world']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "World"));
        element = driver.findElement(By.cssSelector("[data-zone-label='Featured sections']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = driver.findElement(By.cssSelector("[data-analytics='Style_list-hierarchical-xs_article_']"));
        element.findElement(By.tagName("img")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("Logo__style")));
        driver.findElement(By.className("Header__burger")).click();
        List<WebElement> menuElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("Menu__link")));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("Arts", "Design", "Fashion", "Architecture", "Luxury", "Beauty", "Video"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : menuElements) {
            actualTitles.add(element.getText());
            actions.moveToElement(element).perform();
            wait.until(ExpectedConditions.attributeContains(By.className("Menu__background"),
                    "class", "Menu__" + element.getText().toLowerCase(Locale.ROOT)));
        }
        assertEquals(expectedTitles, actualTitles);

        menuElements.get(0).click();
        assertEquals("Arts", driver.findElement(By.tagName("h1")).getText());
    }

    /**
     * CNN14
     * Tamar
     * HTML refer to CNN01, CNN04, CNN13, CNN14
     */
    @Test
    public void CNN14_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.getTitle().contains("World News"));
        driver.findElement(By.cssSelector("a[name='world']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "World"));
        element = driver.findElement(By.cssSelector("[data-zone-label='Featured sections']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element = driver.findElement(By.cssSelector("[data-analytics='Style_list-hierarchical-xs_article_']"));
        element.findElement(By.tagName("img")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("Logo__style")));
        driver.findElement(By.className("Header__burger")).click();
        List<WebElement> menuElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("Menu__link")));
        List<String> expectedTitles = new ArrayList<>(
                Arrays.asList("Arts", "Design", "Fashion", "Architecture", "Luxury", "Beauty", "Video"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : menuElements) {
            actualTitles.add(element.getText());
            actions.moveToElement(element).perform();
            wait.until(ExpectedConditions.attributeContains(By.className("Menu__background"),
                    "class", "Menu__" + element.getText().toLowerCase(Locale.ROOT)));
        }
        assertEquals(expectedTitles, actualTitles);

        menuElements.get(6).click();
        assertEquals("Video", driver.findElement(By.className("VideoHub__title")).getText());
        element = driver.findElement(By.xpath("//*[text()='Latest Videos on Style']/ancestor::*[@class='Zone__wrapper']"));
        assertEquals(20, driver.findElements(By.cssSelector(".VideosFilters__card")).size());
    }

    /**
     * CNN15
     * Tamar
     * HTML refer to CNN01
     */
    @Test
    public void CNN15_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.findElement(By.cssSelector("a[data-analytics='header_top-nav'][name='world']")).isDisplayed());
        driver.findElement(By.cssSelector("[data-test='searchButton']")).click();
        assertNotEquals("none", driver.findElement(By.cssSelector("[class*='indexes__NavGrid']")).getCssValue("display"));
        driver.findElement(By.id("menuButton")).click();
        assertEquals("none", driver.findElement(By.cssSelector("[class*='indexes__NavGrid']")).getCssValue("display"));
        driver.findElement(By.id("menuButton")).click();
        assertNotEquals("none", driver.findElement(By.cssSelector("[class*='indexes__NavGrid']")).getCssValue("display"));
    }

    /**
     * CNN16
     * Tamar
     * HTML refer to CNN01, CNN16
     */
    @Test
    public void CNN16_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.findElement(By.cssSelector("a[data-analytics='header_top-nav'][name='world']")).isDisplayed());
        WebDriverWait wait = new WebDriverWait(driver, 10);
        for (int i = 0; i < 2; i++) {
            driver.findElement(By.id("menuButton")).click();
            assertNotEquals("none", driver.findElement(By.cssSelector("[class*='indexes__NavGrid']")).getCssValue("display"));
            String section = "tech", category = "gadget",
                    cssSelectorText = "[data-analytics='header_top-nav'][aria-label='Business']", title = "Gadget";
            if (i == 1) {
                section = "business";
                category = "markets";
                cssSelectorText = "[alt='CNN Business']";
                title = "Market";
            }
            driver.findElement(By.cssSelector("#nav [data-section='" + section + "'] [name='" + category + "']")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelectorText)));
            assertTrue(driver.getTitle().contains(title));
            if (i == 0) {
                driver.navigate().back();
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".pg-intl_homepage")));
            }
        }
    }

    /**
     * CNN17
     * Tamar
     * HTML refer to CNN01, CNN16
     */
    @Test
    public void CNN17_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.findElement(By.cssSelector("a[data-analytics='header_top-nav'][name='world']")).isDisplayed());
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("menuButton")).click();
        assertNotEquals("none", driver.findElement(By.cssSelector("[class*='indexes__NavGrid']")).getCssValue("display"));
        List<WebElement> titleElements = driver.findElements(By.cssSelector("ul[mode='dark'] > li > a[data-analytics='header_expanded-nav']"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("World", "US Politics", "Business", "Health",
                "Entertainment", "Tech", "Style", "Travel", "Sports", "Videos", "Features", "Weather", "More"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);

        driver.findElement(By.cssSelector("#nav [data-section='business'] [name='markets']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[alt='CNN Business']")));
        assertTrue(driver.getTitle().contains("Market"));
        driver.findElement(By.className("menu-icon")).click();
        wait.until(ExpectedConditions.attributeContains(By.className("menu-icon"), "class", "open"));
        titleElements.clear();
        expectedTitles.clear();
        actualTitles.clear();
        titleElements = driver.findElements(By.cssSelector(".subnav h3 a"));
        expectedTitles.addAll(Arrays.asList("Markets", "Tech", "Media", "Success", "Center Piece",
                "Perspectives", "Video", "International", "More"));
        for (WebElement element : titleElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
    }

    /**
     * CNN18
     * Tamar
     * HTML refer to CNN01, CNN16
     */
    @Test
    public void CNN18_Tamar() throws InterruptedException {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.findElement(By.cssSelector("a[data-analytics='header_top-nav'][name='world']")).isDisplayed());
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("menuButton")).click();
        assertNotEquals("none", driver.findElement(By.cssSelector("[class*='indexes__NavGrid']")).getCssValue("display"));

        driver.findElement(By.cssSelector("#nav [data-section='business'] [name='markets']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[alt='CNN Business']")));
        assertTrue(driver.getTitle().contains("Market"));
        By locator = By.xpath("//*[@class='world-market']/ancestor::*[@class='module']");
        for (int i = 0; i < 10; i++) {
            String initialText = driver.findElement(locator).getText();
            Thread.sleep(2000);
            assertNotEquals("problem in the" + i + "time", initialText, driver.findElement(locator).getText());
        }
    }

    /**
     * CNN19
     * Tamar
     * HTML refer to CNN01, CNN19
     */
    @Test
    public void CNN19_Tamar() throws InterruptedException {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.findElement(By.cssSelector("a[data-analytics='header_top-nav'][name='world']")).isDisplayed());
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("menuButton")).click();
        assertNotEquals("none", driver.findElement(By.cssSelector("[class*='indexes__NavGrid']")).getCssValue("display"));

        driver.findElement(By.cssSelector("#nav [data-section='business'] [name='success']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[data-analytics='header_top-nav'][aria-label='Business']")));
        assertEquals("SUCCESS", driver.findElement(By.tagName("h1")).getText());
        element = driver.findElement(By.cssSelector(".media__image"));
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
        driver.findElement(By.cssSelector("[data-ad-section='const-video-leaf'] a")).click();

        By locator = By.cssSelector(".pui_control-bar_playback-time > span");
        actions.moveToElement(wait.until(ExpectedConditions.presenceOfElementLocated((By.tagName("video"))))).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        actions.moveToElement(driver.findElement(locator)).perform();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, "")));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, "0:00")));

        driver.navigate().back();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[data-analytics='header_top-nav'][aria-label='Business']")));

        element = wait.until(ExpectedConditions.presenceOfElementLocated((By.cssSelector(".ad.ad--desktop iframe"))));
        actions.moveToElement(element).perform();
        Thread.sleep(1000);
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
        element.click();
        assertEquals(2, driver.getWindowHandles().size());
    }

    /**
     * CNN20
     * Tamar
     * HTML refer to CNN01, CNN19, CNN20
     */
    @Test
    public void CNN20_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.findElement(By.cssSelector("a[data-analytics='header_top-nav'][name='world']")).isDisplayed());
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("menuButton")).click();
        assertNotEquals("none", driver.findElement(By.cssSelector("[class*='indexes__NavGrid']")).getCssValue("display"));

        driver.findElement(By.cssSelector("#nav [data-section='business'] [name='success']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[data-analytics='header_top-nav'][aria-label='Business']")));
        assertEquals("SUCCESS", driver.findElement(By.tagName("h1")).getText());
        driver.findElement(By.id("menuButton")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Dow']"))).click();
        wait.until(ExpectedConditions.titleContains("Dow Jones Industrial Average"));
        List<WebElement> sectionElements = driver.findElements(By.cssSelector("#cnnBody h3"));
        List<String> expectedTitles = new ArrayList<>(Arrays.asList("Quote Details", "Gainers", "Losers"));
        List<String> actualTitles = new ArrayList<>();
        for (WebElement element : sectionElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);
        Actions actions = new Actions(driver);
        actions.moveToElement(sectionElements.get(0)).perform();
        List<WebElement> categoriesElements = driver.findElements(By.cssSelector("#wsod_quoteRight tr > td:nth-child(1)"));
        expectedTitles.clear();
        actualTitles.clear();
        expectedTitles.addAll(Arrays.asList("Today's volume", "Average daily volume (3 months)", "Average P/E", "1 year change"));
        for (WebElement element : categoriesElements) {
            actualTitles.add(element.getText());
        }
        assertEquals(expectedTitles, actualTitles);

        for (int i = 0; i < 2; i++) {
            actions.moveToElement(sectionElements.get(i + 1)).perform();
            categoriesElements.clear();
            expectedTitles.clear();
            actualTitles.clear();
            categoriesElements = driver.findElements(By.cssSelector("table:nth-child(2) th"));
            expectedTitles.addAll(Arrays.asList("Company", "Price", "Change", "% Change"));
            for (WebElement element : categoriesElements) {
                actualTitles.add(element.getText());
            }
            assertEquals(expectedTitles, actualTitles);
            List<WebElement> dataElements = sectionElements.get(1).findElements(By.cssSelector("table:nth-child(" + i + 2 + ") .posData"));
            char expectedSymbol = (char) (45 - ((i - 1) * 2));
            for (WebElement element : dataElements) {
                assertTrue(element.getText().startsWith(String.valueOf(expectedSymbol)));
            }
        }
    }

    /**
     * CNN21
     * Tamar
     * HTML refer to CNN01, CNN19, CNN21
     */
    @Test
    public void CNN21_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.findElement(By.cssSelector("a[data-analytics='header_top-nav'][name='world']")).isDisplayed());
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("menuButton")).click();
        assertNotEquals("none", driver.findElement(By.cssSelector("[class*='indexes__NavGrid']")).getCssValue("display"));

        driver.findElement(By.cssSelector("#nav [data-section='business'] [name='success']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[data-analytics='header_top-nav'][aria-label='Business']")));
        assertEquals("SUCCESS", driver.findElement(By.tagName("h1")).getText());
        driver.findElement(By.id("menuButton")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Before the Bell']"))).click();
        wait.until(ExpectedConditions.titleContains("Before the Bell"));
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".cnn-audio-player-wrapper > iframe")));
        driver.switchTo().frame(element);
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.id("elapsed"), "")));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.id("elapsed"), "0:00")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("control-play-pause"))).click();
        assertFalse(driver.findElement(By.id("control-play-pause")).getAttribute("class").contains(" pause"));
        String currentTime = driver.findElement(By.id("elapsed")).getText();
        driver.findElement(By.id("control-play-pause")).click();
        assertEquals(currentTime, driver.findElement(By.id("elapsed")).getText());
        assertTrue(driver.findElement(By.id("control-play-pause")).getAttribute("class").contains(" pause"));
    }

    /**
     * CNN22
     * Tamar
     * HTML refer to CNN01, CNN19, CNN22
     */
    @Test
    public void CNN22_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.findElement(By.cssSelector("a[data-analytics='header_top-nav'][name='world']")).isDisplayed());
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("menuButton")).click();
        assertNotEquals("none", driver.findElement(By.cssSelector("[class*='indexes__NavGrid']")).getCssValue("display"));

        driver.findElement(By.cssSelector("#nav [data-section='business'] [name='success']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[data-analytics='header_top-nav'][aria-label='Business']")));
        assertEquals("SUCCESS", driver.findElement(By.tagName("h1")).getText());
        driver.findElement(By.id("menuButton")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Fear & Greed']"))).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Fear & Greed Index"));
        assertTrue(driver.findElement(By.id("needleChart")).isDisplayed());
        List<WebElement> titleElements = driver.findElements(By.cssSelector(".label"));
        List<String> expectedNames = new ArrayList<>(
                Arrays.asList("Junk Bond Demand", "Market Volatility", "Safe Haven Demand", "Stock Price Breadth",
                        "Put and Call Options", "Market Momentum", "Stock Price Strength"));
        List<String> actualNames = new ArrayList<>();
        List<String> options = new ArrayList<>(Arrays.asList("Extreme Fear", "Fear", "Neutral", "Greed", "Extreme Greed"));
        for (WebElement element : titleElements) {
            actualNames.add(element.findElement(By.className("wsod_fLeft")).getText());
            assertTrue(options.contains(element.findElement(By.className("wsod_fRight")).getText()));
        }
        assertTrue(actualNames.containsAll(expectedNames));
        assertTrue(driver.findElement(By.id("feargreedOverTime")).isDisplayed());
    }

    /**
     * CNN23
     * Tamar
     * HTML refer to CNN01, CNN19, CNN23
     */
    @Test
    public void CNN23_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.findElement(By.cssSelector("a[data-analytics='header_top-nav'][name='world']")).isDisplayed());
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("menuButton")).click();
        assertNotEquals("none", driver.findElement(By.cssSelector("[class*='indexes__NavGrid']")).getCssValue("display"));

        driver.findElement(By.cssSelector("#nav [data-section='business'] [name='success']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[data-analytics='header_top-nav'][aria-label='Business']")));
        assertEquals("SUCCESS", driver.findElement(By.tagName("h1")).getText());
        driver.findElement(By.id("menuButton")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()=\"Tracking America's Recovery\"]"))).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Tracking America's recovery"));
        assertTrue(driver.findElement(By.xpath("//*[text()='JUMP TO']")).isDisplayed());
        List<String> titles = Arrays.asList("Your Job", "Your Home", "Your Investments", "Your Money", "Your Spending", "Your Leisure", "Your Travel");
        for (String title : titles) {
            By locator = By.id("sticky-link-" + title.toLowerCase(Locale.ROOT).replace(' ', '-'));
            if (title.equals("Your Home"))
                locator = By.id("sticky-link-" + title.toLowerCase(Locale.ROOT).replace(' ', '-') + "-");
            if (title.equals("Your Job")) {
                Actions actions = new Actions(driver);
                actions.moveToElement(driver.findElement(By.id("anchor-link-your-job"))).click().perform();
            } else
                driver.findElement(locator).click();
            wait.until(ExpectedConditions.attributeContains(locator, "class", "active"));

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
                    , driver.findElement(By.xpath("//h1[contains(text(),'" + title + "')]"))));
        }
    }

    /**
     * CNN24
     * Tamar
     * HTML refer to CNN01, CNN24
     */
    @Test
    public void CNN24_Tamar() {
        driver.get("https://edition.cnn.com/");
        assertTrue(driver.findElement(By.cssSelector("a[data-analytics='header_top-nav'][name='world']")).isDisplayed());
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("menuButton")).click();
        assertNotEquals("none", driver.findElement(By.cssSelector("[class*='indexes__NavGrid']")).getCssValue("display"));

        driver.findElement(By.cssSelector("[name='entertainment']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("entertainment-logo-icon")));
        List<String> subsections = Arrays.asList("Stars", "Screen", "Binge", "Culture", "Media");
        for (String text : subsections) {
            driver.findElement(By.cssSelector
                    ("[data-analytics='header_top-nav'][name='" + text.toLowerCase(Locale.ROOT) + "']")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
                    ("[selected][data-analytics='header_top-nav'][name='" + text.toLowerCase(Locale.ROOT) + "']")));
        }
    }

    /**
     * CNN25
     * Mika
     * HTML refer to CNN25
     */
    @Test
    public void CNN25_Mika() {
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
    public void CNN26_Mika() {
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
    public void CNN27_Mika() {
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
    public void CNN28_Mika() {
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
    public void CNN29_Mika() {
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
    public void CNN30_Mika() {
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
    public void CNN31_Mika() {
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
    public void CNN32_Mika() {
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
    public void CNN33_Mika() {
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
    public void CNN34_Mika() {
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
    public void CNN35_Mika() {
        String[] countries = new String[]
                {"Africa", "Americas", "Asia", "Australia", "China", "Europe", "India", "Middle East", "United Kingdom"};
        String countryXPath;

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        for (String country : countries) {
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
    public void CNN36_Mika() {
        String[] countries = new String[]
                {"Africa", "Americas", "Asia", "Australia", "China", "Europe", "India", "Middle East", "United Kingdom"};
        String countryXPath;

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.findElement(By.xpath("//*[@name='world' and @data-analytics='header_top-nav']")).click();

        for (String country : countries) {
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
    public void CNN37_Mika() {
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
    public void CNN38_Mika() throws InterruptedException {
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

        for (WebElement article : articles) {
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
    public void CNN39_Mika() {
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
    public void CNN40_Mika() {
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
    public void CNN41_Mika() {
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
    public void CNN42_Mika() {
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
     * CNN43
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN35 - hamburger_menu.html, CNN43
     */
    @Test
    public void CNN43_Mika() throws InterruptedException {
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

        js.executeScript("window.scrollTo(0, -document.body.scrollHeight)");

        for (WebElement period : dayPeriods) {
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
     * CNN44
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN35 - hamburger_menu.html, CNN44
     */
    @Test
    public void CNN44_Mika() {
        String location = "United States";

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);

        driver.findElement(By.className("menu-icon")).click();
        element = driver.findElement
                (By.xpath("//*[@name='weather' and @data-analytics='header_expanded-nav']"));
        action.moveToElement(element).click().perform();

        element = wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//input[@placeholder='Enter Location']")));
        element.click();
        element.sendKeys(location);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='tt-dropdown-menu']/*[@class='tt-dataset-user-location']/*/*"))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[@data-temp='fahrenheit']"))).click();
        assertTrue(driver.findElement(By.xpath("//*[@data-temptype='fahrenheit']")).isDisplayed());
    }

    /**
     * CNN45
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN35 - hamburger_menu.html, CNN45
     */
    @Test
    public void CNN45_Mika() {
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
    public void CNN46_Mika() {
        String[] socialNetworks = new String[]{"facebook", "instagram", "twitter"};
        String currentNetwork = "facebook";
        String expectedUrl = "https://www.facebook.com/cnninternational";
        String newTab;

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        for (String socialNetwork : socialNetworks) {
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
    public void CNN47_Mika() {
        String[] socialNetworks = new String[]{"facebook", "instagram", "twitter"};
        String currentNetwork = "twitter";
        String expectedUrl = "https://twitter.com/cnni";
        String newTab;

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        for (String socialNetwork : socialNetworks) {
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
    public void CNN48_Mika() {
        String[] socialNetworks = new String[]{"facebook", "instagram", "twitter"};
        String currentNetwork = "instagram";
        String expectedUrl = "https://www.instagram.com/cnn/";
        String newTab;

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        for (String socialNetwork : socialNetworks) {
            assertTrue(driver.findElement(By.xpath("//*[@id='footer-wrap']//*[@data-icon='" + socialNetwork + "']"))
                    .isDisplayed());
        }

        driver.findElement(By.xpath("//*[@id='footer-wrap']//*[@data-icon='" + currentNetwork + "']")).click();

        newTab = (String) driver.getWindowHandles().toArray()[1];
        driver.switchTo().window(newTab);
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
    }

    /**
     * CNN49
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN28 - CNN_Newsletters_page.html, CNN49
     */
    @Test
    public void CNN49_Mika() {
        String[] optionsList = new String[]{"Terms of Use", "Privacy Policy", "Accessibility & CC", "AdChoices",
                "About Us", "Modern Slavery Act Statement", "Advertise with us", "CNN Store", "Newsletters",
                "Transcripts", "License Footage", "CNN Newsource", "Sitemap"};
        String[] xpathToCheck = new String[]
                {"//h1[text()='CNN Terms of Use']", "//h1[contains(text(), 'Privacy Policy')]",
                        "//h1[contains(text(), 'Accessibility')]", "//h1[contains(text(), 'Advertising Choices')]",
                        "//h1[text()='ABOUT CNN DIGITAL']", "//h1[contains(text(), 'Modern Slavery Act Statement')]",
                        "//h2[contains(text(), 'Welcome to CNNIC')]", "//h1[contains(text(), 'CNN Store')]",
                        "//h1[text()='CNN Newsletters']", "//img[@alt='TRANSCRIPTS']",
                        "//h4[contains(text(), 'FOOTAGE')]", "//h1[text()='WE’RE AT THE HEART OF IT']",
                        "//h1[contains(text(), 'CNN Site Map')]"};
        List<String> goBack = Arrays.asList("Advertise with us", "CNN Store", "Newsletters",
                "Transcripts", "License Footage", "CNN Newsource");
        String hasPopUp = "AdChoices";

        driver.get("https://edition.cnn.com/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        for (String option : optionsList)
            assertTrue(driver.findElement(By.linkText(option)).isDisplayed());

        for (int i = 0; i < optionsList.length; i++) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(optionsList[i]))).click();

            if (optionsList[i].equals(hasPopUp)) {
                wait.until(ExpectedConditions.elementToBeClickable
                        (By.xpath("//*[contains(@id, 'closeBtn')]"))).click();
            } else
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathToCheck[i])));

            if (goBack.contains(optionsList[i])) {
                driver.navigate().back();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(optionsList[i + 1])));
            }

            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        }
    }

    /**
     * CNN50
     * Mika
     * HTML refer to CNN25 - home_page.html, CNN49 - About_Us_page, CNN50
     */
    @Test
    public void CNN50_Mika() {
        String[] optionsList = new String[]{"Terms of Use", "Privacy Policy", "Accessibility & CC", "AdChoices",
                "About Us", "Modern Slavery Act Statement", "Advertise with us", "CNN Store", "Newsletters",
                "Transcripts", "License Footage", "CNN Newsource", "Sitemap"};
        String newTab;
        String textToType = "try";
        String emailAddress = "qa.tries.123@gmail.com";
        String[] feedbackFields = new String[]{"Name", "Email", "Thoughts", "Comments"};

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

        for (String field : feedbackFields) {
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
