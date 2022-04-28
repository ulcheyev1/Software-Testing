import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebTestClicks {

    WebDriver driver;
    WebDriverWait wait;

    public WebTestClicks(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        clickLogInButton();
        fillLogInFormData();
        clickMyCourse();
        clickCviceni();
        reattempAndSubmitClick();
        fillTestDataAndSubmit();
        logout();
        driver.close();
    }

    public void clickLogInButton(){
        driver.findElement(By.xpath("//a[text() = 'Log in']")).click();
        driver.findElement(By.id("sso-form")).click();
    }


    public void fillLogInFormData(){
        driver.findElement(By.id("username")).sendKeys("ulcheyev");
        driver.findElement(By.id("password")).sendKeys("Aragoneprod123");
        driver.findElement(By.name("_eventId_proceed")).click();
    }


    public void clickMyCourse(){
        WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text() = 'My courses']")));
        element1.click();
        driver.findElement(By.xpath("//h5[text() = 'Software Testing']")).click();
    }

    public void clickCviceni(){
        driver.findElement(By.xpath("//span[@class = 'sectionname' and text() = 'Cvičení']")).click();
        WebElement element2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = 'CV 11: Test pro Robota']")));
        element2.click();
    }

    public void reattempAndSubmitClick(){
        driver.findElement(By.xpath("//button[starts-with(@id,'single_button')]")).click();
        driver.findElement(By.id("id_submitbutton")).click();
    }

    public void fillTestDataAndSubmit(){
        driver.findElement(By.xpath("//div[@class = 'answer']//child::textarea")).sendKeys("Ulchenkov Yevgeniy");
        driver.findElement(By.xpath("//div[@class = 'ablock form-inline']//child::input")).sendKeys("86400");
        Select select1 = new Select(driver.findElement(By.xpath("//select[@class = 'select custom-select custom-select place1']")));
        select1.selectByVisibleText("Oberon");
        Select select2 = new Select(driver.findElement(By.xpath(" //p[contains(text(),'Mezi státy evropské unie patří')]//child::select")));
        select2.selectByVisibleText("Rumunsko");

        driver.findElement(By.name("next")).click();
        WebElement element3 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='mod_quiz-next-nav' and contains(text(), 'Finish review')]")));
        element3.click();
    }

    public void logout(){
        driver.findElement(By.xpath("//div[@class='dropdown rounded']//child::a")).click();
        WebElement element4 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class = 'dropdown-menu dropdown-menu-right menu align-tr-br border show']//child::span[contains(text(), 'Log out')]")));
        element4.click();
        driver.findElement(By.xpath("//button[@class = 'btn btn-primary' and starts-with(@id, 'single_button')]")).click();
    }




}
