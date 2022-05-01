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
import springerLinkPages.DataWriter;
import springerLinkPages.SpringerLinkHomePage;
import springerLinkPages.SpringerLinkLogInPage;

import java.time.Duration;

public class SpringerLinkSignUpTest {

    WebDriver driver;
    SpringerLinkLogInPage logInPage_SignUp;
    SpringerLinkHomePage homePage;

    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        logInPage_SignUp = PageFactory.initElements(driver, SpringerLinkLogInPage.class);
        homePage = PageFactory.initElements(driver, SpringerLinkHomePage.class);
    }

    @Test
    public void signUpTest_signUpWithRandomData_everythingOk(){
        //Home page
        homePage.openHomePage();
        Assertions.assertTrue(homePage.isHomePage());
        if(homePage.checkPopUpDisplayed()){homePage.closePopUp();}
        homePage.clickAcceptCookiesButton();
        homePage.clickSignUpLogInButton();

        //LogIn page-SignUp
        Assertions.assertTrue(logInPage_SignUp.isLogInPage());
        if(logInPage_SignUp.checkPopUpDisplayed()){logInPage_SignUp.closePopUp();}
        logInPage_SignUp.fillSignUpFormFirstNameField();
        logInPage_SignUp.fillSignUpFormLastNameField();
        logInPage_SignUp.fillSignUpFormEmailField();
        logInPage_SignUp.fillSignUpFormPasswordField();
        logInPage_SignUp.fillSignUpFormConfirmPasswordField();
        logInPage_SignUp.clickSignUpFormCreateAccountButton();
        logInPage_SignUp.clickSignUpContinueButton();

        //Check
        Assertions.assertTrue(homePage.isHomePage());
        driver.close();
    }

//    @Test
    /*** INSERT DATA FOR REGISTRATION ***/
    public void signUpTest_signUpWithData_everythingOk(){
        /****//******************************//****/
        /****/  String firstname = "";        /****/
        /****/  String lastname = "";         /****/
        /****/  String email = "";            /****/
        /****/  String password = "";         /****/
        /****/  String confirmPassword = "";  /****/
        /****//******************************//****/
        //Home page
        homePage.openHomePage();
        Assertions.assertTrue(homePage.isHomePage());
        if(homePage.checkPopUpDisplayed()){homePage.closePopUp();}
        homePage.clickAcceptCookiesButton();
        homePage.clickSignUpLogInButton();

        //LogIn page-SignUp
        Assertions.assertTrue(logInPage_SignUp.isLogInPage());
        if(logInPage_SignUp.checkPopUpDisplayed()){logInPage_SignUp.closePopUp();}
        logInPage_SignUp.fillSignUpFormFirstNameField(firstname);
        logInPage_SignUp.fillSignUpFormLastNameField(lastname);
        logInPage_SignUp.fillSignUpFormEmailField(email);
        logInPage_SignUp.fillSignUpFormPasswordField(password);
        logInPage_SignUp.fillSignUpFormConfirmPasswordField(confirmPassword);
        logInPage_SignUp.clickSignUpFormCreateAccountButton();
        logInPage_SignUp.clickSignUpContinueButton();

        //Check
        Assertions.assertTrue(homePage.isHomePage());
        driver.close();
    }

    @Test
    public void signUpTest_signUpWithExistingEmail_errorLabelCheck(){
        /***INSERT DATA FOR REGISTRATION WHICH EXISTS***/
        String firstname = "ts1";
        String lastname = "ts1";
        String email = "ts1@gmail.com";
        String password = "ts12022";
        String confirmPassword = "ts12022";

        //Home page
        homePage.openHomePage();
        Assertions.assertTrue(homePage.isHomePage());
        if(homePage.checkPopUpDisplayed()){homePage.closePopUp();}
        homePage.clickAcceptCookiesButton();
        homePage.clickSignUpLogInButton();

        //LogIn page-SignUp
        Assertions.assertTrue(logInPage_SignUp.isLogInPage());
        if(logInPage_SignUp.checkPopUpDisplayed()){logInPage_SignUp.closePopUp();}
        logInPage_SignUp.fillSignUpFormFirstNameField(firstname);
        logInPage_SignUp.fillSignUpFormLastNameField(lastname);
        logInPage_SignUp.fillSignUpFormEmailField(email);
        logInPage_SignUp.fillSignUpFormPasswordField(password);
        logInPage_SignUp.fillSignUpFormConfirmPasswordField(confirmPassword);
        logInPage_SignUp.clickSignUpFormCreateAccountButton();

        //Check
        Assertions.assertTrue(logInPage_SignUp.checkSignUpFormEmailErrorLabelDispayed());
        Assertions.assertTrue(logInPage_SignUp.isLogInPage());
        Assertions.assertFalse(homePage.isHomePage());
        driver.close();
    }




}
