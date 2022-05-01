package springerLinkPagesTests;

import com.beust.ah.A;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import springerLinkPages.SpringerLinkAnvancedSearchPage;
import springerLinkPages.SpringerLinkHomePage;
import springerLinkPages.SpringerLinkLogInPage;
import springerLinkPages.SpringerLinkSearchPage;

import java.time.Duration;

public class SpringerLinkAdvancedSearchTest {

    WebDriver driver;
    SpringerLinkHomePage homePage;
    SpringerLinkAnvancedSearchPage advancedSearch;
    SpringerLinkSearchPage searchPage;

    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        homePage = PageFactory.initElements(driver, SpringerLinkHomePage.class);
        advancedSearch = PageFactory.initElements(driver, SpringerLinkAnvancedSearchPage.class);
        searchPage = PageFactory.initElements(driver, SpringerLinkSearchPage.class);
    }

    @Test
    public void advancedSearchTest_fillAdvancedFormAndSearch_thereAreSpecifiedWordsInTheSearchResults(){
        String withAllWords = "Selenium";
        String year = "2015";

        //HomePage
        homePage.openHomePage();
        Assertions.assertTrue(homePage.isHomePage());
        if(homePage.checkPopUpDisplayed()){homePage.closePopUp();}
        homePage.clickAcceptCookiesButton();
        homePage.clickButtonWithGearForAdvancedSearch();
        homePage.clickAdvancedSearchLink();

        //AdvanceSearch
        Assertions.assertTrue(advancedSearch.isAdvancedSearchPage());
        if(advancedSearch.checkPopUpDisplayed()){advancedSearch.closePopUp();}
        advancedSearch.fillAdvancedSearchFormWithAllWordsField(withAllWords);
        advancedSearch.selectAnOptionInAdvancedSearchFormSelect("in");
        advancedSearch.fillAdvancedSearchFormStartYearInput(year);
        advancedSearch.clickAdvancedSearchFormSearchButton();

        //SearchPage
        Assertions.assertTrue(searchPage.isSearchPage());
        if(searchPage.checkPopUpDisplayed()){searchPage.closePopUp();}

        //Comparison query results
        //Words
        for(int i = 20; i < searchPage.getSearchResult().size(); i++){
            Assertions.assertTrue(searchPage.getSearchResult().get(i).getText().contains(withAllWords));
        }
        //Years
        for(int i = 20; i < searchPage.getSearchResult().size(); i++){
            Assertions.assertTrue(searchPage.getYearsOfSearchResult().get(i).getText().contains(year));
        }
        driver.close();

    }
}
