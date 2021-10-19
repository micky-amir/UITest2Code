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
}
