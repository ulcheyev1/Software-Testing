package springerLinkProcessTest;
import com.beust.ah.A;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import springerLinkPages.*;

import java.io.IOException;
import java.time.Duration;


public class ProcessTest {

    SpringerLinkHomePage homePage;
    SpringerLinkLogInPage logInPage;
    SpringerLinkAnvancedSearchPage advancedSearchPage;
    SpringerLinkSearchPage searchPage;
    SpringerLinkArticlePage articlePage;
    DataWriter writer;
    WebDriver driver;
    WebDriverWait wait;

    String articleName;
    String arcticlePublishDate;
    String articleDOI;

    public ProcessTest(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        homePage = PageFactory.initElements(driver, SpringerLinkHomePage.class);
        logInPage = PageFactory.initElements(driver, SpringerLinkLogInPage.class);
        advancedSearchPage = PageFactory.initElements(driver, SpringerLinkAnvancedSearchPage.class);
        searchPage = PageFactory.initElements(driver, SpringerLinkSearchPage.class);
        articlePage = PageFactory.initElements(driver, SpringerLinkArticlePage.class);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void processTestN1_searchAndSaveArticleData_writeToFile(){
        String contentType = "Article";
        String allWords = "Page Object Model";
        String alLeastWords = "Sellenium Testing";
        String year = "2022";

        //Home page
        homePage.openHomePage();
        Assertions.assertTrue(homePage.isHomePage());
        if(homePage.checkPopUpDisplayed()){homePage.closePopUp();}
        if(homePage.checkPopUpDisplayed()){homePage.closePopUp();}
        homePage.clickAcceptCookiesButton();

        //Go to advanced search
        homePage.clickButtonWithGearForAdvancedSearch();
        homePage.clickAdvancedSearchLink();

        //AdvancedSearch
        Assertions.assertTrue(advancedSearchPage.isAdvancedSearchPage());
        if(advancedSearchPage.checkPopUpDisplayed()){advancedSearchPage.closePopUp();}
        advancedSearchPage.fillAdvancedSearchFormWithAllWordsField(allWords);
        advancedSearchPage.fillAdvancedSearchFormWithAtLeatOneWordField(alLeastWords);
        advancedSearchPage.selectAnOptionInAdvancedSearchFormSelect("in");
        advancedSearchPage.fillAdvancedSearchFormStartYearInput(year);
        advancedSearchPage.clickAdvancedSearchFormSearchButton();

        //Search result page
        Assertions.assertTrue(searchPage.isSearchPage());
        if(searchPage.checkPopUpDisplayed()){searchPage.closePopUp();}
        searchPage.clickOnContentType(contentType);

        //Scan data and write
        for(int i = 0; i < 4; i++){
            searchPage.clickOnSearchResult(i);
            articleName = articlePage.readArticleName();
            arcticlePublishDate = articlePage.readDateOfPublish();
            articleDOI = articlePage.readDOIURL();
            writeFoundData(articleName, arcticlePublishDate, articleDOI);
            driver.navigate().back();
        }
        driver.close();
    }

    @ParameterizedTest(name = "Article title = {0} Article publish date = {1} Article DOI = {2}")
    @CsvFileSource(resources = "/dataForSearch.csv")
    public void processTestN2_autorizationAndSearchArticlesByDataFromFile_articleParamsAreEqualToWithParamsFromFile(String title, String date, String doi) {
        //Home page
        homePage.openHomePage();
        Assertions.assertTrue(homePage.isHomePage());
        if (homePage.checkPopUpDisplayed()) {homePage.closePopUp();}
        homePage.clickAcceptCookiesButton();
        homePage.clickSignUpLogInButton();

        //LogIn page
        Assertions.assertTrue(logInPage.isLogInPage());
        if (logInPage.checkPopUpDisplayed()) {logInPage.closePopUp();}
        logInPage.fillLogInFormEmailField("ts1@gmail.com");
        logInPage.fillLogInFormPasswordField("ts12022");
        logInPage.clickLogInFormLogInButton();

        //Verification of successful authorization by go to the home page
        Assertions.assertTrue(homePage.isHomePage());
        if (homePage.checkPopUpDisplayed()) {homePage.closePopUp();}
        Assertions.assertTrue(logInPage.checkAuthorizedUserProfileButtonDisplayed());

        //Search articles
        homePage.fillSearchField(title);
        homePage.clickSearchButton();

        //Search result page
        Assertions.assertTrue(searchPage.isSearchPage());

        //Search article by name on search result page and compare param
        WebElement element = searchPage.searchArticleByName(title);
        Assertions.assertEquals(title, element.getText());

        //The names match, move to this element and control the dates and DOI
        element.click();
        Assertions.assertEquals(date, articlePage.readDateOfPublish());
        Assertions.assertEquals(doi, articlePage.readDOIURL());
        driver.close();
    }


    public void writeFoundData(String title, String date, String doi) {
        try {
            writer = new DataWriter("src/test/resources/dataForSearch.csv");
            writer.writeData(title, date, doi);
        }catch (IOException e){
        }
    }
}
