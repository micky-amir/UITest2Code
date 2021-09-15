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

public class Thesaurus {
	
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
     * Abhijit
     * Html refer to SK_1
     */
    @Test
    public void SK_1_Abhijit () throws InterruptedException
    {
    	driver.get("https://www.thesaurus.com/");
    	Thread.sleep(10000);
    	driver.findElement(By.xpath("//span[@aria-label='Sign up for an account']")).click();
    	Thread.sleep(10000);
    	((JavascriptExecutor) driver).executeScript("window.scrollBy(05,700)");
    	Thread.sleep(10000);
    	
    }

    /**
     * SK_2
     * Abhijit
     * Html refer to SK_1
     */
   
    @Test
    public void SK_2_Abhijit () throws InterruptedException
    {
    	driver.get("https://www.thesaurus.com/");
    	Thread.sleep(10000);
    	((JavascriptExecutor) driver).executeScript("window.scrollBy(05,1400)");
    	Thread.sleep(10000);
    	
    }
    
    /**
     * SK_3
     * Abhijit
     * Html refer to SK_1
     */
    
    @Test
    public void SK_3_Abhijit () throws InterruptedException
    {
    	driver.get("https://www.thesaurus.com/");
    	Thread.sleep(10000);
    	((JavascriptExecutor) driver).executeScript("window.scrollBy(05,2300)");
    	Thread.sleep(10000);
    	
    }

}
