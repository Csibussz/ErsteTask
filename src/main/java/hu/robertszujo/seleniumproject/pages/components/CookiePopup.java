package hu.robertszujo.seleniumproject.pages.components;

import com.aventstack.extentreports.ExtentTest;
import hu.robertszujo.seleniumproject.pages.BasePageObject;
import hu.robertszujo.seleniumproject.utils.ElementActions;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CookiePopup extends BasePageObject {

    public CookiePopup(WebDriver driver, ExtentTest reporter) {
        super(driver, reporter);
    }

    // *** Elements ***

    @FindBy(css = "div[id='popin_tc_privacy']")
    private WebElement cookiePopup;

    @FindBy(css = "button[id='popin_tc_privacy_button']")
    private WebElement acceptButton;

    // *** Element methods ***

    public boolean isCookiePopupDisplayedAfterWaiting() {
        try {
            waitForCookiePopupToBeDisplayed();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void waitForCookiePopupToBeDisplayed() {
        reporter.info("Waiting for cookie popup to be displayed");
        ElementActions.waitForElementToBeDisplayed(cookiePopup, driver);
    }

    public boolean hasCookiePopupDisappearedAfterWaiting() {
        try {
            waitForCookiePopupToDisappear();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void waitForCookiePopupToDisappear() {
        reporter.info("Waiting for cookie popup to disappear");
        ElementActions.waitForElementToDisappear(cookiePopup, driver);
    }

    public void clickOnCookieAcceptButton() {
        reporter.info("Clicking on cookie accept button");
        acceptButton.click();
    }
}
