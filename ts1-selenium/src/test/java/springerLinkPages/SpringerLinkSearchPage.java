package springerLinkPages;

import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SpringerLinkSearchPage {

    WebDriver driver;

    public SpringerLinkSearchPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(xpath = "//div[@id='content-type-facet']//child::a//span[@class='facet-title']")
    private List<WebElement> contetTypes;

    @FindBy(xpath = "//ol[@id='results-list']//child::li//a[@class='title']")
    private List<WebElement> searchResult;

    @FindBy(xpath = "//ol[@id='results-list']//child::li//child::span[@class='year']")
    private List<WebElement> yearsOfSearchResults;

    @FindBy(xpath = "//button[@class='cc-button cc-button--contrast cc-banner__button cc-banner__button-accept']")
    private WebElement acceptCookiesButton;

    @FindBy(xpath = "//div[@id='content-type-facet']//child::span[text() = 'Remove']")
    private WebElement removeButtonForContentType;

    @FindBy(xpath = "//div[@id='google_image_div']")
    private WebElement imgAd;


    public void openSearchPage(String url){
        driver.get(url);
    }


    public boolean isSearchPage(){
        if(driver.getTitle().equals("Search Results - Springer")){
            return true;
        }else{
            return false;
        }
    }

    public void clickOnContentType(String text) {
        for (WebElement element : contetTypes) {
            if (element.getText().equals(text)) {
                element.click();
                break;
            }
        }
    }


    public void clickOnSearchResult(int index){
        searchResult.get(index).click();
    }

    public String getNameOfSearchResult(int placeInList){
        String text = searchResult.get(placeInList - 1).getText();
        return text;
    }

    public List<WebElement> getSearchResult(){
        return this.searchResult;
    }


    public List<WebElement> getYearsOfSearchResult(){
        return this.yearsOfSearchResults;
    }

    public void clickAcceptCookiesButton(){
        acceptCookiesButton.click();
    }

    public WebElement searchArticleByName(String name) {
        for(int j = 0; j < searchResult.size(); j++){
            if(searchResult.get(j).getText().equals(name)){
                return searchResult.get(j);
            }
        }
        return null;
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

    public boolean checkRemoveContentTypeButtonDisplayed(){
        return removeButtonForContentType.isDisplayed();
    }

    public void closeWebDriver(){driver.quit();}


}
