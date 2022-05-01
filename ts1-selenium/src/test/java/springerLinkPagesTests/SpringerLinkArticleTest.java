package springerLinkPagesTests;

import com.beust.ah.A;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import springerLinkPages.*;

import java.time.Duration;

public class SpringerLinkArticleTest {
    WebDriver driver;
    SpringerLinkArticlePage articlePage;

    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        articlePage = PageFactory.initElements(driver, SpringerLinkArticlePage.class);
        driver.manage().window().maximize();
    }

    @Test
    public void articlePageTest_comparisonOfArticleParameters_areTheSame(){

        String expectedUrl = "https://link.springer.com/article/10.21136/AM.2021.0246-20";
        String expectedName = "A new optimized iterative method for solving M-matrix linear systems";
        String expectedYear = "22 November 2021";
        String expectedDoi = "https://doi.org/10.21136/AM.2021.0246-20";

        //ArticlePage
        articlePage.openArticlePage("https://link.springer.com/article/10.21136/AM.2021.0246-20");
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
        articlePage.clickAcceptCookiesButton();
        Assertions.assertEquals(expectedName, articlePage.readArticleName());
        Assertions.assertEquals(expectedYear, articlePage.readDateOfPublish());
        Assertions.assertEquals(expectedDoi, articlePage.readDOIURL());
        driver.close();
    }
}
