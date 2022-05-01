package springerLinkPagesTests;

import com.beust.ah.A;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import springerLinkPages.SpringerLinkHomePage;
import springerLinkPages.SpringerLinkLogInPage;

import java.time.Duration;

public class SpringerLinkLogInTest {

    WebDriver driver;
    SpringerLinkLogInPage logInPage_LogIn;
    SpringerLinkHomePage homePage;

    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        logInPage_LogIn = PageFactory.initElements(driver, SpringerLinkLogInPage.class);
        homePage = PageFactory.initElements(driver, SpringerLinkHomePage.class);
    }

    @Test
    public void logInTest_logInWithCorrectData_everythingOk(){
        //Home page
        homePage.openHomePage();
        Assertions.assertTrue(homePage.isHomePage());
        if(homePage.checkPopUpDisplayed()){homePage.closePopUp();}
        homePage.clickAcceptCookiesButton();
        homePage.clickSignUpLogInButton();

        //LogIn page
        Assertions.assertTrue(logInPage_LogIn.isLogInPage());
        if(logInPage_LogIn.checkPopUpDisplayed()){logInPage_LogIn.closePopUp();}
        logInPage_LogIn.fillLogInFormEmailField("ts1@gmail.com");
        logInPage_LogIn.fillLogInFormPasswordField("ts12022");
        logInPage_LogIn.clickLogInFormLogInButton();

        //Check
        Assertions.assertTrue(homePage.isHomePage());
        if(homePage.checkPopUpDisplayed()){homePage.closePopUp();}
        Assertions.assertTrue(logInPage_LogIn.checkAuthorizedUserProfileButtonDisplayed());
        driver.close();
    }

    @Test
    public void logInTest_logInWithIncorrectData_errorLabelCheck() {
        //Home page
        homePage.openHomePage();
        if(homePage.checkPopUpDisplayed()){homePage.closePopUp();}
        Assertions.assertTrue(homePage.isHomePage());
        homePage.clickAcceptCookiesButton();
        homePage.clickSignUpLogInButton();

        //LogIn page
        Assertions.assertTrue(logInPage_LogIn.isLogInPage());
        if(logInPage_LogIn.checkPopUpDisplayed()){logInPage_LogIn.closePopUp();}
        logInPage_LogIn.fillLogInFormEmailField("ts1@gmail.com");
        logInPage_LogIn.fillLogInFormPasswordField("ts1add3wqr122022");
        logInPage_LogIn.clickLogInFormLogInButton();

        //Check
        Assertions.assertTrue(logInPage_LogIn.isLogInPage());
        if(logInPage_LogIn.checkPopUpDisplayed()){logInPage_LogIn.closePopUp();}
        Assertions.assertTrue(logInPage_LogIn.checkLogInFormErrorLabelDisplayed());
        Assertions.assertFalse(homePage.isHomePage());
        driver.close();
    }

}
