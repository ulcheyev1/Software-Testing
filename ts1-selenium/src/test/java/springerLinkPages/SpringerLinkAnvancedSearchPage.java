package springerLinkPages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SpringerLinkAnvancedSearchPage {
    private WebDriver driver;

    @FindBy(xpath = "//input[@id='all-words']")
    private WebElement advancedSearchFormWithAllWordsField;

    @FindBy(xpath = "//input[@id='least-words']")
    private WebElement advancedSearchFormWithAtLeatOneWordField;

    @FindBy(xpath = "//select[@id='date-facet-mode']")
    private WebElement advancedSearchFormSelect;

    @FindBy(xpath = "//input[@id='facet-start-year']")
    private WebElement advancedSearchFormStartYearInput;

    @FindBy(xpath = "//input[@id='facet-end-year']")
    private WebElement advancedSearchFormEndYearInput;

    @FindBy(xpath = "//button[@id='submit-advanced-search']")
    private WebElement advancedSearchFormSearchButton;

    @FindBy(xpath = "//button[@class='cc-button cc-button--contrast cc-banner__button cc-banner__button-accept']")
    private WebElement acceptCookiesButton;

    public SpringerLinkAnvancedSearchPage(WebDriver driver) {
        this.driver = driver;
    }


    public void openAdvancedSearchPage(){
        driver.get("https://link.springer.com/advanced-search");
    }


    public boolean isAdvancedSearchPage(){
        if(driver.getTitle().equals("Advanced Search - Springer")){
            return true;
        }else{
            return false;
        }
    }

    public void fillAdvancedSearchFormWithAllWordsField(String text){
        advancedSearchFormWithAllWordsField.sendKeys(text);
    }

    public void fillAdvancedSearchFormWithAtLeatOneWordField(String text) {
        advancedSearchFormWithAtLeatOneWordField.sendKeys(text);
    }

    public void selectAnOptionInAdvancedSearchFormSelect(String text){
        Select select = new Select(advancedSearchFormSelect);
        select.selectByValue(text);
    }



    public void fillAdvancedSearchFormStartYearInput(String text){
        advancedSearchFormStartYearInput.sendKeys(text);
    }

    public void fillAdvancedSearchFormEndYearInput(String text){
        advancedSearchFormEndYearInput.sendKeys(text);
    }

    public void clickAdvancedSearchFormSearchButton(){advancedSearchFormSearchButton.click();}

    public void clickAcceptCookiesButton(){
        acceptCookiesButton.click();
    }

    public void closeWebDriver(){
        driver.quit();
    }

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

}
