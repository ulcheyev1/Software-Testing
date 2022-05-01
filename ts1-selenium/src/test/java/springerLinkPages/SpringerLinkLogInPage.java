package springerLinkPages;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class SpringerLinkLogInPage {

    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private WebDriver driver;

    public SpringerLinkLogInPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(xpath = "//input[@name='IDToken1']")
    private WebElement logInFormEmailField;

    @FindBy(xpath = "//input[@name='IDToken2']")
    private WebElement logInFormPasswordField;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-monster' and text() = 'Log in']")
    private WebElement logInFormLogInButton;

    @FindBy(xpath = "//input[@id='first-name']")
    private WebElement signUpFormFirstNameField;

    @FindBy(xpath = "//input[@id='last-name']")
    private WebElement signUpFormLastNameField;

    @FindBy(xpath = "//input[@id='email-address']")
    private WebElement signUpFormEmailField;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement signUpFormPasswordField;

    @FindBy(xpath = "//input[@id='password-confirm']")
    private WebElement signUpFormConfirmPasswordField;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-monster' and text() = 'Create account']")
    private WebElement signUpFormCreateAccountButton;

    @FindBy(xpath = "//a[@id='continue-button']")
    private WebElement signUpContinueButton;

    @FindBy(xpath = "//label[@id='error-email-address']")
    private WebElement signUpFormEmailErrorLabel;

    @FindBy(xpath = "//button[@class='cc-button cc-button--contrast cc-banner__button cc-banner__button-accept']")
    private WebElement acceptCookiesButton;

    @FindBy(xpath = "//button[@id='user']")
    private WebElement authorizedUserProfileButton;

    @FindBy(xpath = "//div[@class='error-message']")
    private WebElement logInFormErrorLabel;


    public void openLogInPage(){
        driver.get("https://link.springer.com/signup-login?previousUrl=https%3A%2F%2Flink.springer.com%2F");
    }

    public boolean isLogInPage(){
        if(driver.getTitle().equals("Create Account - Springer")){
            return true;
        }else {
            return false;
        }

    }

    public void fillLogInFormEmailField(String email){
        logInFormEmailField.sendKeys(email);
    }

    public void fillLogInFormPasswordField(String password){
        logInFormPasswordField.sendKeys(password);
    }

    public void clickLogInFormLogInButton(){
        logInFormLogInButton.click();
    }


    public void fillSignUpFormFirstNameField(){
        firstname = RandomStringUtils.randomAlphabetic(8);
        signUpFormFirstNameField.sendKeys(firstname);
    }

    public boolean checkPopUpDisplayed() {
        try {
            return driver.findElement(By.xpath("//div[@class='QSIWebResponsive-creative-container-fade']")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    public void closePopUp(){
        driver.findElement(By.xpath("//div[@class='QSIWebResponsive-creative-container-fade']//child::div[1]//child::button[text() = 'No Thanks']")).click();
    }

    public void fillSignUpFormFirstNameField(String firstname){
        this.firstname = firstname;
        signUpFormFirstNameField.sendKeys(firstname);
    }

    public void fillSignUpFormLastNameField(){
        lastname = RandomStringUtils.randomAlphabetic(8);
        signUpFormLastNameField.sendKeys(lastname);
    }

    public void fillSignUpFormLastNameField(String lastname){
        this.lastname = lastname;
        signUpFormLastNameField.sendKeys(lastname);
    }

    public void fillSignUpFormEmailField(){
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        email = "username" + randomNumber + "@gmail.com";
        signUpFormEmailField.sendKeys(email);
    }

    public void fillSignUpFormEmailField(String email){
        this.email = email;
        signUpFormEmailField.sendKeys(email);
    }

    public void fillSignUpFormPasswordField(){
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        password = RandomStringUtils.randomAlphabetic(8) + randomNumber;
        signUpFormPasswordField.sendKeys(password);
    }

    public void fillSignUpFormPasswordField(String password) {
        signUpFormPasswordField.sendKeys(password);
    }

    public void fillSignUpFormConfirmPasswordField(){
        signUpFormConfirmPasswordField.sendKeys(password);
    }

    public void fillSignUpFormConfirmPasswordField(String password){
        this.password = password;
        signUpFormConfirmPasswordField.sendKeys(password);
    }

    public void clickSignUpFormCreateAccountButton(){
        signUpFormCreateAccountButton.click();
    }

    public void clickSignUpContinueButton(){
        signUpContinueButton.click();
    }

    public boolean checkSignUpFormEmailErrorLabelDispayed(){
        return signUpFormEmailErrorLabel.isDisplayed();
    }

    public boolean checkAuthorizedUserProfileButtonDisplayed(){return authorizedUserProfileButton.isDisplayed();}

    public boolean checkLogInFormErrorLabelDisplayed(){return logInFormErrorLabel.isDisplayed();}

    public void clickAcceptCookiesButton(){
        acceptCookiesButton.click();
    }

    public void closeWebDriver(){
        driver.close();
    }


}
