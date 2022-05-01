package springerLinkPages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SpringerLinkArticlePage {

    private WebDriver driver;

    public SpringerLinkArticlePage(WebDriver driver){
        this.driver = driver;
    }

    @FindBy(xpath = "//h1[@class='c-article-title']")
    WebElement articleName;

    @FindBy(xpath = "//li[@class='c-bibliographic-information__list-item c-bibliographic-information__list-item--doi']//child::span[@class='c-bibliographic-information__value']")
    WebElement DOIURL;

    @FindBy(xpath = "//a[@href='#article-info' and text() = 'Published: ']//child::time")
    WebElement dateOfPublish;

    @FindBy(xpath = "//button[@class='cc-button cc-button--contrast cc-banner__button cc-banner__button-accept']")
    private WebElement acceptCookiesButton;

    @FindBy(xpath = "//button[@class='cc-button cc-button--contrast cc-banner__button cc-banner__button-accept']")
    private WebElement removeButtonForContentType;



    String acticleNameString;
    String DOIURLString;
    String dateOfPublishString;

    public void openArticlePage(String url){
        driver.get(url);
    }

    public String  readArticleName(){
        acticleNameString = articleName.getText();
        System.out.println(acticleNameString);
        return acticleNameString;
    }

    public String readDOIURL(){
        DOIURLString = DOIURL.getText();
        System.out.println(DOIURLString);
        return DOIURLString;

    }

    public String readDateOfPublish(){
        dateOfPublishString = dateOfPublish.getText();
        System.out.println(dateOfPublishString);
        return dateOfPublishString;
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

    public void clickAcceptCookiesButton(){
        acceptCookiesButton.click();
    }

    public void closeWebDriver(){driver.quit();}


}
