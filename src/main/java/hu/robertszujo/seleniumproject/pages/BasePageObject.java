package hu.robertszujo.seleniumproject.pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageObject {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ExtentTest reporter;

    public BasePageObject(WebDriver driver, ExtentTest reporter) {
        this.driver = driver;
        this.reporter = reporter;
        PageFactory.initElements(driver, this);
    }

    public void clickButton(WebElement element, String buttonName){
        reporter.info("Clicking on " + buttonName + " button");
        Actions a = new Actions(driver);
        a.moveToElement(element).perform();
        element.click();
    }

    public void clickOutside(WebElement element){
        Actions builder = new Actions(driver);
        builder.moveToElement(element, -150, 0).click().build().perform();
    }

    public void fillUpTextField(WebElement element, String fieldName, String value){
        reporter.info("Fill up the value of " + fieldName + " field with " + value);
        element.click();
        element.sendKeys(String.valueOf(value));
        clickOutside(element);
    }

    public void clearTextField(WebElement element, String fieldName){
        reporter.info("Cleaning up the value of " + fieldName + " field");
        element.clear();
        clickOutside(element);
    }

    public String getTextFieldValue(WebElement field){
        return field.getText();
    }
}
