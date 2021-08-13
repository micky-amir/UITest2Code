package websites;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Wikipedia {

    ChromeDriver driver;

    @BeforeTest
    public void initDriver() {
        driver = new ChromeDriver();
    }

    //        WebDriverWait wait = new WebDriverWait(driver, 10);
//        WebElement el = wait.until(presenceOfElementLocated(By.xpath("//span[text()='Languages']")));
//        driver.findElement(By.xpath("//span[text()='Languages']"));


    /**
     * SK_1
     * Amir
     */
    @Test
    public void SK_1() throws InterruptedException {
        String path = "https://www.wikipedia.org/";
        driver.get(path);
        String logoText = driver.findElement(By.className("central-textlogo-wrapper")).getText();
        Assert.assertTrue(logoText.contains("Wikipedia"));
        Assert.assertTrue(logoText.contains("The Free Encyclopedia"));
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
        List<String> languages = driver.findElementsByClassName("central-featured-lang").stream().map(language ->
                        ((RemoteWebElement) language).findElementsByXPath(".//*")
                                .stream()
                                .filter(b -> b.getTagName()
                                        .equals("strong")).findFirst().get().getText()
                )
                .collect(Collectors.toList());
        Assert.assertEquals(languagesList, languages);

        WebElement searchInput = driver.findElement(By.id("search-input"));
        Assert.assertEquals(searchInput.findElement(By.id("searchLanguage")).getTagName(), "select");
        List<String> searchLanguage = ((RemoteWebElement) searchInput.findElement(By.id("searchLanguage")))
                .findElementsByXPath(".//*")
                .stream().map(WebElement::getText).collect(Collectors.toList());
        Assert.assertTrue(searchLanguage.contains("Polski"));
        driver.findElement(By.xpath("//span[text()='Read Wikipedia in your language ']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Thread.sleep(3000);
        WebElement el = wait.until(presenceOfElementLocated(By.className("lang-list-content")));
        List<String> headlines = ((RemoteWebElement) el)
                .findElementsByClassName("bookshelf").stream().map(headline ->
                        headline.findElement(By.tagName("bdi")).getText()
                ).collect(Collectors.toList());
        Assert.assertTrue(headlines.stream().allMatch(numberOfArticles -> numberOfArticles.contains("00")));

        driver.findElement(By.xpath("//span[text()='Apple App Store']"));
        driver.findElement(By.xpath("//span[text()='Google Play Store']"));


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
                ((RemoteWebElement) project).findElementsByXPath(".//*").stream().map(WebElement::getText)
                        .collect(Collectors.joining(" - "))).map(projectDescription -> "- " + projectDescription).collect(Collectors.toList());
        Assert.assertTrue(otherProjectDescriptions.containsAll(projectNamesList));
    }

    /**
     * SK_2
     * Amir
     */
    @Test
    public void SK_2() throws InterruptedException {
        String path = "https://www.wikipedia.org/";
        driver.get(path);
        List<WebElement> languages = driver.findElements(By.className("central-featured-lang"));
        Optional<WebElement> english = languages.stream().filter(language -> language.getAttribute("lang").equals("en")).findFirst();
        assert english.isPresent();
        Assert.assertEquals(english.get().getAttribute("title"), "English — Wikipedia — The Free Encyclopedia");
        english.get().findElement(By.tagName("a")).click();
        Thread.sleep(3000);
        Assert.assertEquals(driver.getCurrentUrl(), "https://en.wikipedia.org/wiki/Main_Page");
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
            Assert.assertEquals(lang.getAttribute("title"), langEntry.getValue().get(0));
            driver.findElement(By.xpath("//a[text()='{" + langEntry.getValue().get(1) + "']"));
        }
    }

}
