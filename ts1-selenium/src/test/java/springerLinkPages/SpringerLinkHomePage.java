package springerLinkPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SpringerLinkHomePage {
    private WebDriver driver;

    public SpringerLinkHomePage(WebDriver driver){
        this.driver = driver;
    }

    @FindBy(xpath = "//a[@class='register-link flyout-caption']")
    private WebElement signUpLogInButton;

    @FindBy(xpath = "//button[@class='cc-button cc-button--contrast cc-banner__button cc-banner__button-accept']")
    private WebElement acceptCookiesButton;

    @FindBy(xpath = "//button[@class='pillow-btn open-search-options']")
    private WebElement buttonWithGearForAdvancedSearch;

    @FindBy(xpath = "//a[@id='advanced-search-link']")
    private WebElement advancedSearchLink;

    @FindBy(xpath = "//input[@id='query']")
    private WebElement searchField;

    @FindBy(xpath = "//input[@id='search']")
    private WebElement searchButton;



    public void openHomePage(){
        driver.get("https://link.springer.com/");
        driver.manage().window().maximize();
    }

    public boolean isHomePage(){
        if(driver.getTitle().equals("Home - Springer")){
            return true;
        }else{
            return false;
        }
    }

    public void clickAcceptCookiesButton(){
        acceptCookiesButton.click();
    }

    public void clickSignUpLogInButton(){
        signUpLogInButton.click();
    }

    public void clickButtonWithGearForAdvancedSearch(){
        buttonWithGearForAdvancedSearch.click();
    }

    public void clickAdvancedSearchLink(){
        advancedSearchLink.click();
    }

    public void fillSearchField(String text){searchField.sendKeys(text);}

    public void clickSearchButton(){searchButton.click();}

    public boolean checkPopUpDisplayed(){
        try {
            return driver.findElement(By.xpath("//div[@class='QSIWebResponsive-creative-container-fade']")).isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }

    }
    public void closePopUp(){
        driver.findElement(By.xpath("//div[@class='QSIWebResponsive-creative-container-fade']//child::div[1]//child::button[text() = 'No Thanks']")).click();
    }

    public void closeWebDriver(){driver.close();}

}
