package websites;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Wikipedia {

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
     * Amir
     */
    @Test
    public void SK_1_Amir() {
        String path = "https://www.wikipedia.org/";
        driver.get(path);
        String logoText = driver.findElement(By.className("central-textlogo-wrapper")).getText();
        assertTrue(logoText.contains("Wikipedia"));
        assertTrue(logoText.contains("The Free Encyclopedia"));
    }

    /**
     * SK_2
     * Amir
     */
    @Test
    public void SK_2_Amir() {
        String path = "https://www.wikipedia.org/";
        driver.get(path);

        String[] languagesArray = {"English",
                "日本語",
                "Español",
                "Deutsch",
                "Русский",
                "Français",
                "中文",
                "Italiano",
                "Português",
                "Polski"};
        Set<String> languagesList = ImmutableSet.copyOf(languagesArray);
        Set<String> languages = driver.findElementsByClassName("central-featured-lang").stream().map(language ->
                        ((RemoteWebElement) language).findElementsByXPath(".//*")
                                .stream()
                                .filter(b -> b.getTagName()
                                        .equals("strong")).findFirst().get().getText()
                )
                .collect(Collectors.toSet());
        assertEquals(languagesList, languages);
    }

    /**
     * SK_3
     * Amir
     */
    @Test
    public void SK_3_Amir() {
        String path = "https://www.wikipedia.org/";
        driver.get(path);

        WebElement searchInput = driver.findElement(By.id("search-input"));
        assertEquals(searchInput.findElement(By.id("searchLanguage")).getTagName(), "select");
        List<String> searchLanguage = ((RemoteWebElement) searchInput.findElement(By.id("searchLanguage")))
                .findElementsByXPath(".//*")
                .stream().map(WebElement::getText).collect(Collectors.toList());
        assertTrue(searchLanguage.contains("Polski"));
    }

    /**
     * SK_4
     * Amir
     */
    @Test
    public void SK_4_Amir() throws InterruptedException {
        String path = "https://www.wikipedia.org/";
        driver.get(path);

        driver.findElement(By.xpath("//span[text()='Read Wikipedia in your language ']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Thread.sleep(3000);
        WebElement el = wait.until(presenceOfElementLocated(By.className("lang-list-content")));
        List<String> headlines = ((RemoteWebElement) el)
                .findElementsByClassName("bookshelf").stream().map(headline ->
                        headline.findElement(By.tagName("bdi")).getText()
                ).collect(Collectors.toList());
        assertTrue(headlines.stream().allMatch(numberOfArticles -> numberOfArticles.contains("00")));
    }

    /**
     * SK_6
     * Amir
     */
    @Test
    public void SK_6_Amir() {
        String path = "https://www.wikipedia.org/";
        driver.get(path);

        driver.findElement(By.xpath("//span[text()='Apple App Store']"));
        driver.findElement(By.xpath("//span[text()='Google Play Store']"));
    }
    /**
     * SK_7
     * Amir
     */
    @Test
    public void SK_7_Amir() {

        String[] projectNames = {
                "- Commons - Freely usable photos & more",
                "- Wikibooks - Free textbooks",
                "- Wikiversity - Free course materials",
                "- Wikisource - Free library",
                "- Wikivoyage - Free travel guide",
                "- Wikinews - Free news source",
                "- Wikiquote - Free quote compendium",
                "- Wikispecies - Free species directory",
                "- Wiktionary - Free dictionary",
                "- Wikidata - Free knowledge base",
                "- MediaWiki - Free & open wiki application",
                "- Meta-Wiki - Community coordination & documentation"};
        Set<String> projectNamesList = ImmutableSet.copyOf(projectNames);

        WebElement otherProjects = driver.findElementByClassName("other-projects");
        List<String> otherProjectDescriptions = otherProjects.findElements(By.className("other-project-text")).stream().map(project ->
                ((RemoteWebElement) project).findElementsByXPath(".//*")
                        .stream()
                        .map(WebElement::getText)
                        .collect(Collectors.joining(" - ")))
                .map(projectDescription -> "- " + projectDescription)
                .collect(Collectors.toList());
        assertTrue(otherProjectDescriptions.containsAll(projectNamesList));
    }

    /**
     * SK_9
     * Amir
     */
    @Test
    public void SK_9_Amir() throws InterruptedException {
        String path = "https://www.wikipedia.org/";
        driver.get(path);
        List<WebElement> languages = driver.findElements(By.className("central-featured-lang"));
        Optional<WebElement> english = languages.stream().filter(language -> language.getAttribute("lang").equals("en")).findFirst();
        assert english.isPresent();
        assertEquals(english.get().getAttribute("title"), "English — Wikipedia — The Free Encyclopedia");
        english.get().findElement(By.tagName("a")).click();
        Thread.sleep(3000);
        assertEquals(driver.getCurrentUrl(), "https://en.wikipedia.org/wiki/Main_Page");
        driver.findElement(By.xpath("//a[text()='Main page']"));

        Map<String, List<String>> languagesWithExpectedResults = ImmutableMap.<String, List<String>>builder()
                .put("es", ImmutableList.of("Español — Wikipedia — La enciclopedia libre", "Portada"))
                .put("ru", ImmutableList.of("Russkiy — Википедия — Свободная энциклопедия", "Заглавная"))
                .put("zh", ImmutableList.of("Zhōngwén — 維基百科 — 自由的百科全書", "首页"))
                .put("pt", ImmutableList.of("Português — Wikipédia — A enciclopédia livre", "Página principal"))
                .put("ja", ImmutableList.of(null, " メインページ"))
                .put("de", ImmutableList.of("Deutsch — Wikipedia — Die freie Enzyklopädie", "Hauptseite"))
                .build();

        WebElement lang;
        for (Map.Entry<String, List<String>> langEntry : languagesWithExpectedResults.entrySet()) {
            driver.get(path);
            lang = languages.stream().filter(language -> language.getAttribute("lang").equals(langEntry.getKey())).findFirst().get();
            assertEquals(lang.getAttribute("title"), langEntry.getValue().get(0));
            driver.findElement(By.xpath("//a[text()='{" + langEntry.getValue().get(1) + "']"));
        }
    }

    /**
     * SK_1
     * Tamar
     */
    @Test
    public void SK_1_Tamar() {
        driver.get("https://www.wikipedia.org/");
        element = driver.findElement(By.className("central-textlogo__image"));
        assertTrue(element.isDisplayed());
        assertEquals("Wikipedia", element.getText());
        assertEquals("center", element.getCssValue("text-align"));
        // locator uses the fact that subtitle is under the title
        element = driver.findElement(By.xpath("//following-sibling::strong"));
        assertTrue(element.isDisplayed());
        assertEquals("The Free Encyclopedia", element.getText());
        assertEquals("center", element.getCssValue("text-align"));
    }

    /**
     * SK_2
     * Tamar
     * HTML refer to SK_1
     */
    @Test
    public void SK_2_Tamar() {
        driver.get("https://www.wikipedia.org/");
        assertTrue(driver.findElement(By.className("central-featured-logo")).isDisplayed());
        element = driver.findElement(By.className("central-featured-logo"));
        // check the presence of 10 languages
        List<WebElement> languagesElements = driver.findElements(By.cssSelector(".central-featured > *"));
        assertEquals(10, driver.findElements(By.cssSelector(".central-featured > *")).size());
        List<String> languagesNames = new LinkedList<>(Arrays.asList("日本語", "Deutsch", "Français", "Italiano", "Polski", "English", "Español", "Русский", "中文", "Português"));
        int countRight = 0, countLeft = 0;
        for (WebElement langElement : languagesElements) {
            if (langElement.getLocation().getX() > element.getLocation().getX())
                countRight++;
            else
                countLeft++;
            String currentLanguage = langElement.findElement(By.xpath(".//strong")).getText();
            languagesNames.remove(currentLanguage);
            assertTrue(langElement.findElement(By.xpath(".//small")).isDisplayed()); // number of articles id displayed
        }
        assertEquals(5, countRight);
        assertEquals(5, countLeft);
        assertEquals(0, languagesNames.size());
    }

    /**
     * SK_3
     * Tamar
     * HTML refer to SK_1
     */
    @Test
    public void SK_3_Tamar() {
        driver.get("https://www.wikipedia.org/");
        assertTrue(driver.findElement(By.id("searchInput")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[for='searchLanguage']")).isDisplayed());
        By buttonLocator = By.cssSelector("[data-jsl10n='search-input-button']");
        assertTrue(driver.findElement(buttonLocator).isDisplayed());
        assertEquals("rgba(51, 102, 204, 1)",
                driver.findElement(buttonLocator).findElement(By.xpath("..")).getCssValue("background-color"));
        driver.findElement(By.id("searchLanguage")).click();
        assertTrue(driver.findElement(By.xpath("//option[text()='Polski']")).isDisplayed());
    }

    /**
     * SK_4
     * Tamar
     * HTML refer to SK_1
     */
    @Test
    public void SK_4_Tamar() {
        WebDriverWait wait = new WebDriverWait(driver, 2000);
        driver.get("https://www.wikipedia.org/");
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Read Wikipedia in your language']")));
        assertTrue(driver.findElement(By.className("svg-arrow-down-blue")).isDisplayed()); // arrow
        element.click();

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("lang-list-content")));
        List<WebElement> headers = driver.findElements(By.cssSelector(".lang-list-content > h2"));
        assertEquals(5, headers.size()); //5 categories
    }

    /**
     * SK_5
     * Tamar
     * HTML refer to SK_1
     */
    @Test
    public void SK_5_Tamar() {
        driver.get("https://www.wikipedia.org/");
        By locator = By.className("footer-sidebar-content");
        assertTrue(driver.findElement(locator).isDisplayed());
        String expectedDescriptionText = "Wikipedia is hosted by the Wikimedia Foundation, a non-profit organization that also hosts a range of other projects.\n" +
                "You can support our work with a donation.";
        assertEquals(expectedDescriptionText, driver.findElement(locator).getText());
    }

    /**
     * SK_6
     * Tamar
     * HTML refer to SK_1
     */
    @Test
    public void SK_6_Tamar() {
        driver.get("https://www.wikipedia.org/");
        WebDriverWait wait = new WebDriverWait(driver, 2000);
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-jsl10n='portal.app-links.description']")));
        assertTrue(element.isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[data-jsl10n='portal.app-links.google-store']")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[data-jsl10n='portal.app-links.apple-store']")).isDisplayed());
    }

    /**
     * SK_7
     * Tamar
     * HTML refer to SK_1
     */
    @Test
    public void SK_7_Tamar() {
        driver.get("https://www.wikipedia.org/");
        WebDriverWait wait = new WebDriverWait(driver, 2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("other-project")));
        List<WebElement> projects = driver.findElements(By.className("other-project"));
        assertEquals(12, projects.size());
        List<String> expectedProjectsDescription = new LinkedList<>(Arrays.asList(
                "Commons - Freely usable photos & more",
                "Wikibooks - Free textbooks",
                "Wikiversity - Free course materials",
                "Wikisource - Free library",
                "Wikivoyage - Free travel guide",
                "Wikinews - Free news source",
                "Wikiquote - Free quote compendium",
                "Wikispecies - Free species directory",
                "Wiktionary - Free dictionary",
                "Wikidata - Free knowledge base",
                "MediaWiki - Free & open wiki application",
                "Meta-Wiki - Community coordination & documentation"));
        List<String> actualProjectsDescription = new LinkedList<>();
        for (WebElement p : projects) {
            String text = p.getText().replace("\n", " - ");
            actualProjectsDescription.add(text);
        }
        assertEquals(expectedProjectsDescription, actualProjectsDescription);
    }

    /**
     * SK_8
     * Tamar
     * HTML refer to SK_1
     */
    @Test
    public void SK_8_Tamar() {
        driver.get("https://www.wikipedia.org/");
        element = driver.findElement(By.cssSelector("[data-jsl10n='license']"));
        assertTrue(element.findElement(By.xpath("//./a")).isDisplayed());
        assertEquals("This page is available under the Creative Commons Attribution-ShareAlike License", element.getText());
        element = driver.findElement(By.cssSelector("[data-jsl10n='terms']"));
        assertTrue(element.findElement(By.xpath("//./a")).isDisplayed());
        assertEquals("Terms of Use", element.getText());
        element = driver.findElement(By.cssSelector("[data-jsl10n='privacy-policy']"));
        assertTrue(element.findElement(By.xpath("//./a")).isDisplayed());
        assertEquals("Privacy Policy", element.getText());
    }

    /**
     * SK_9
     * Tamar
     * HTML refer to SK_1
     */
    @Test
    public void SK_9_Tamar() {
        driver.get("https://www.wikipedia.org/");
        assertTrue(driver.findElement(By.className("central-textlogo__image")).isDisplayed());
        assertTrue(driver.findElement(By.className("central-featured-logo")).isDisplayed());
        assertTrue(driver.findElement(By.className("footer")).isDisplayed());
    }

    /**
     * SK_10
     * Tamar
     * HTML refer to SK_1, SK_10
     */
    @Test
    public void SK_10_Tamar() {
        Map<String, List<String>> languagesInfo = new HashMap<String, List<String>>() {{
            put("English", new ArrayList<>(Arrays.asList("English — Wikipedia — The Free Encyclopedia", "Main page")));
            put("Español", new ArrayList<>(Arrays.asList("Español — Wikipedia — La enciclopedia libre", "Portada")));
            put("Русский", new ArrayList<>(Arrays.asList("Russkiy — Википедия — Свободная энциклопедия", "Заглавная страница")));
            put("中文", new ArrayList<>(Arrays.asList("Zhōngwén — 維基百科 — 自由的百科全書", "首页")));
            put("Português", new ArrayList<>(Arrays.asList("Português — Wikipédia — A enciclopédia livre", "Página principal")));
            put("日本語", new ArrayList<>(Arrays.asList("Nihongo — ウィキペディア — フリー百科事典", "メインページ")));
            put("Deutsch", new ArrayList<>(Arrays.asList("Deutsch — Wikipedia — Die freie Enzyklopädie", "Hauptseite")));
        }};
        languagesInfo.forEach((lang, expectedStrings) -> {
            driver.get("https://www.wikipedia.org/");
            element = driver.findElement(By.xpath("//strong[text()='" + lang + "']/.."));
            Actions actions = new Actions(driver);
            actions.moveByOffset(0, 0).moveToElement(element).perform();
            assertEquals(expectedStrings.get(0), element.getAttribute("title"));
            element.click();
            assertEquals(expectedStrings.get(1), driver.findElement(By.id("n-mainpage-description")).getText());
        });
    }

    /**
     * SK_11
     * Tamar
     * HTML refer to SK_1, SK_11
     */
    @Test
    public void SK_11_Tamar() {
        Map<String, String> languagesInfo = new HashMap<String, String>() {{
            put("Polski", "Strona główna");
            put("Dansk", "Forside");
            put("Scots", "Main page");
            put("Akan", "Main page");
            put("Romani", "Sherutni patrin");
        }};

        By buttonLocator = By.xpath("//*[text()='Read Wikipedia in your language']");
        Actions actions = new Actions(driver);

        languagesInfo.forEach((language, expectedMainPageText) -> {
            driver.get("https://www.wikipedia.org/");
            assertTrue(driver.findElement(By.className("central-textlogo__image")).isDisplayed());
            assertTrue(driver.findElement(By.className("central-featured-logo")).isDisplayed());
            assertTrue(driver.findElement(By.className("footer")).isDisplayed());

            assertTrue(driver.findElement(buttonLocator).isDisplayed());
            assertTrue(driver.findElement(buttonLocator).isEnabled());
            actions.moveByOffset(0, 0).moveToElement(driver.findElement(buttonLocator)).click().perform();
            WebDriverWait wait1 = new WebDriverWait(driver, 2000);
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("js-lang-lists")));
            By locator = By.xpath("//a[text()='" + language + "']");
            wait1.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element = wait1.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();

            By mainPageLocator = By.id("n-mainpage-description");
            if (language.equals("Dansk"))
                mainPageLocator = By.id("n-mainpage");
            element = wait1.until(ExpectedConditions.presenceOfElementLocated(mainPageLocator));
            assertEquals(expectedMainPageText, element.getText());
        });
    }

    /**
     * SK_12
     * Tamar
     */
    @Test
    public void SK_12_Tamar() {
        driver.get("https://en.wikipedia.org/wiki/Main_Page");
        assertTrue(driver.findElement(By.id("mw-head")).isDisplayed());
        assertTrue(driver.findElement(By.id("mw-panel")).isDisplayed());
        assertTrue(driver.findElement(By.id("bodyContent")).isDisplayed());
    }

    /**
     * SK_13
     * Tamar
     * HTML refer to SK_12
     */
    @Test
    public void SK_13_Tamar() {
        driver.get("https://en.wikipedia.org/wiki/Main_Page");
        assertTrue(driver.findElement(By.className("mw-wiki-logo")).isDisplayed()); // logo
        List<WebElement> categoriesElements = driver.findElements(By.xpath("//*[@id='mw-panel']/nav/h3"));
        List<String> expectedCategoriesNames = new LinkedList<>(Arrays.asList("Contribute", "Tools", "Print/export", "In other projects", "Languages"));
        List<String> actualCategoriesNames = new LinkedList<>();
        for (WebElement element : categoriesElements) {
            String categoryName = element.getText();
            if (!categoryName.equals(""))
                actualCategoriesNames.add(categoryName);
        }
        assertEquals(expectedCategoriesNames, actualCategoriesNames);
    }

    /**
     * SK_14
     * Tamar
     * HTML refer to SK_12
     */
    @Test
    public void SK_14_Tamar() {
        driver.get("https://en.wikipedia.org/wiki/Main_Page");
        assertTrue(driver.findElement(By.id("pt-anonuserpage")).isDisplayed()); // info if the user is logged in or not
        List<WebElement> linkElements = driver.findElements(By.xpath("//*[@id='p-personal']//*[@class='vector-menu-content-list']/li/a"));
        List<String> expectedLinkNames = new LinkedList<>(Arrays.asList("Talk", "Contributions", "Create account", "Log in"));
        List<String> actualLinkNames = new LinkedList<>();
        for (WebElement element : linkElements) {
            actualLinkNames.add(element.getText());
        }
        assertEquals(expectedLinkNames, actualLinkNames);

        List<WebElement> tabsElements = driver.findElements(By.xpath("//*[@id='p-namespaces']//*[@class='vector-menu-content-list']/li/a"));
        tabsElements.addAll(driver.findElements(By.xpath("//*[@id='p-views']//*[@class='vector-menu-content-list']/li/a")));
        List<String> expectedTabsNames = new LinkedList<>(Arrays.asList("Main Page", "Talk", "Read", "View source", "View history"));
        List<String> actualTabsNames = new LinkedList<>();
        for (WebElement element : tabsElements) {
            actualTabsNames.add(element.getText());
        }
        assertEquals(expectedTabsNames, actualTabsNames);
        assertTrue(driver.findElement(By.id("searchInput")).isDisplayed()); // search bar
    }

    /**
     * SK_15
     * Tamar
     * HTML refer to SK_12
     */
    @Test
    public void SK_15_Tamar() {
        driver.get("https://en.wikipedia.org/wiki/Main_Page");
        assertTrue(driver.findElement(By.id("mp-welcomecount")).isDisplayed()); // welcome area
        assertTrue(driver.findElement(By.id("mp-free")).isDisplayed()); // description
        By portalsLocator = By.id("mp-portals");
        assertTrue(driver.findElement(portalsLocator).isDisplayed()); // portals
        element = driver.findElement(portalsLocator);
        List<WebElement> linkElements = element.findElements(By.xpath(".//li/a"));
        List<String> expectedLinkNames = new LinkedList<>(Arrays.asList(
                "The arts", "Biography", "Geography", "History", "Mathematics", "Science", "Society", "Technology", "All portals"));
        List<String> actualLinkNames = new LinkedList<>();
        for (WebElement element : linkElements) {
            actualLinkNames.add(element.getText());
        }
        assertEquals(expectedLinkNames, actualLinkNames);
    }

    /**
     * SK_16
     * Tamar
     * HTML refer to SK_12
     */
    @Test
    public void SK_16_Tamar() {
        driver.get("https://en.wikipedia.org/wiki/Main_Page");
        List<WebElement> headerElements = driver.findElements(By.className("mw-headline"));
        List<String> expectedHeaders = new LinkedList<>(Arrays.asList(
                "From today's featured article", "In the news", "Did you know ...", "On this day", "From today's featured list",
                "Today's featured picture", "Other areas of Wikipedia", "Wikipedia's sister projects", "Wikipedia languages"));
        List<String> actualHeaders = new LinkedList<>();
        for (WebElement element : headerElements) {
            actualHeaders.add(element.getText());
        }
        assertTrue(actualHeaders.containsAll(expectedHeaders));
    }

}
