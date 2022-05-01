package springerLinkPagesTests;
import com.beust.ah.A;
import dev.failsafe.internal.util.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import springerLinkPages.SpringerLinkHomePage;
import springerLinkPages.SpringerLinkLogInPage;
import springerLinkPages.SpringerLinkSearchPage;

import java.time.Duration;

public class SpringerLinkSearchTest {

    WebDriver driver;
    SpringerLinkSearchPage searchPage;
    SpringerLinkHomePage homePage;

    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        searchPage = PageFactory.initElements(driver, SpringerLinkSearchPage.class);
        homePage = PageFactory.initElements(driver, SpringerLinkHomePage.class);
    }

    @Test
    public void searchTest_contentTypeSet_contentTypeIsSet(){

        String expectedUrl = "https://link.springer.com/search?query=Selenium&facet-content-type=%22Article%22";

        //HomePage
        homePage.openHomePage();
        Assertions.assertTrue(homePage.isHomePage());
        if(homePage.checkPopUpDisplayed()){homePage.closePopUp();}
        homePage.clickAcceptCookiesButton();
        homePage.fillSearchField("Selenium");
        homePage.clickSearchButton();

        //SearchPage
        Assertions.assertTrue(searchPage.isSearchPage());
        if(searchPage.checkPopUpDisplayed()){searchPage.closePopUp();}
        searchPage.clickOnContentType("Article");
        Assertions.assertTrue(searchPage.checkRemoveContentTypeButtonDisplayed());
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
        driver.close();
    }

    @Test
    public void searchTest_correctFillSearchField_thereAreSpecifiedWordsInTheSearchResults(){
        String textForSearch = "Selenium";
        String expectedUrl = "https://link.springer.com/search?query="+textForSearch;

        //HomePage
        homePage.openHomePage();
        Assertions.assertTrue(homePage.isHomePage());
        if(homePage.checkPopUpDisplayed()){homePage.closePopUp();}
        homePage.clickAcceptCookiesButton();
        homePage.fillSearchField(textForSearch);
        homePage.clickSearchButton();

        //SearchPage
        Assertions.assertTrue(searchPage.isSearchPage());
        if(searchPage.checkPopUpDisplayed()){searchPage.closePopUp();}
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());

        //Comparison query results
        for(int i = 20; i < searchPage.getSearchResult().size(); i++){
            Assertions.assertTrue(searchPage.getSearchResult().get(i).getText().contains(textForSearch));
        }

        driver.close();
    }

}
