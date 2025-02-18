package hu.robertszujo.seleniumproject.pages;

import com.aventstack.extentreports.ExtentTest;
import hu.robertszujo.seleniumproject.constants.TestConstants;
import hu.robertszujo.seleniumproject.utils.ElementActions;
import hu.robertszujo.seleniumproject.utils.ErrorTypeID;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class LoanCalculatorPage extends BasePageObject {

    public LoanCalculatorPage(WebDriver driver, ExtentTest reporter) {
        super(driver, reporter);
    }

    // *** Elements ***

    @FindBy(css = "div[class='content_hitelmaximum']")
    private WebElement calculatorForm;

    @FindBy(id = "meletkor")
    private WebElement ageInputField;

    @FindBy(id = "ingatlan_erteke")
    private WebElement valueOfRealEstateField;

    @FindBy(id = "mjovedelem")
    private WebElement incomeField;

    @FindBy(xpath = "//label[@for=\"egyedul\"]")
    private WebElement onePersonRadioButton;

    @FindBy(xpath = "//label[@for=\"tobben\"]")
    private WebElement morePersonRadioButton;

    @FindBy(id = "meglevo_torleszto")
    private WebElement loanRepaymentField;

    @FindBy(id = "folyoszamla")
    private WebElement overdraftField;

    @FindBy(xpath = "//input[@value=\"Mennyi lakáshitelt kaphatok?\"]")
    private WebElement calculateButton;

    @FindBy(xpath = "//input[@value=\"Érdekel az ajánlat\"]")
    private WebElement interestedButton;

    @FindBy(xpath = "//input[@value=\"Visszahívást kérek\"]")
    private WebElement callBackButton;

    @FindBy(className = "calc-result")
    private WebElement calcResult;

    @FindBy(id = "box_1_max_desktop")
    private WebElement loanAmount;

    @FindBy(id = "box_1_thm")
    private WebElement thmAmount;

    @FindBy(xpath = "//label[@for=\"kedvezmeny_biztositasm\"]")
    private WebElement insuranceCheckbox;


    // *** Element methods ***

    /*
        Waiting methods
     */

    public void waitForCalculatorFormToBeDisplayed() {
        reporter.info("Waiting for calculator form to be displayed");
        ElementActions.waitForElementToBeDisplayed(calculatorForm, driver);
    }

    private void waitForCalculationResultAndInterestedButtonToBeDisplayed(){
        reporter.info("Waiting for calculation result to be displayed");
        ElementActions.waitForElementToBeDisplayed(calcResult, driver);
        ElementActions.waitForElementToBeDisplayed(interestedButton, driver);
    }

    /*
        Is Displayed methods
     */

    public boolean isCalculatorFormDisplayedAfterWaiting() {
        try {
            waitForCalculatorFormToBeDisplayed();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isCalculatedResultAndInterestedButtonDisplayedAfterWaiting() {
        try {
            waitForCalculationResultAndInterestedButtonToBeDisplayed();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /*
        Fill up TextFields
     */

    public void fillUpAgeField(int age){
        fillUpTextField(ageInputField, "age", String.valueOf(age));
    }

    public void fillUpValueOfRealEstateField(int value){
        fillUpTextField(valueOfRealEstateField, "value of real estate", String.valueOf(value));
    }

    public void fillUpIncomeField(int income){
        fillUpTextField(incomeField, "income", String.valueOf(income));
    }

    public void fillUpLoanRepaymentField(int loan){
        fillUpTextField(loanRepaymentField, "loan repayment", String.valueOf(loan));
    }

    public void fillUpOverdraftField(int overdraft){
        fillUpTextField(overdraftField, "overdraft", String.valueOf(overdraft));
    }

    /*
        Clears TextFields
     */

    public void clearsUpValueOfRealEstateField() {
        clearTextField(valueOfRealEstateField, "value of real estate");
    }

    public void clearsUpIncomeField() {
        clearTextField(incomeField, "income");
    }

    public void clearsUpLoanRepaymentField(){
        clearTextField(loanRepaymentField, "loan repayment");
    }

    /*
        Click methods
     */

    public void clickOnMorePersonOption(){
        clickButton(morePersonRadioButton, "more person");
    }

    public void clickCalculateButton(){
        clickButton(calculateButton, "calculate");
        // Because of automatic scrolling we should wait a bit
        ElementActions.waitFor(1000);
    }

    public void clickInsurance(){
        clickButton(insuranceCheckbox, "insurance");
        // Because of automatic scrolling we should wait a bit
        ElementActions.waitFor(1000);
    }

    /*
        Error related methods
     */


    public boolean isErrorTextToBeDisplayed(ErrorTypeID errorType){
        reporter.info("Checking if error message is visible");
        try {
            String id = errorType.getId();
            return driver.findElement(By.id(id)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getErrorText(ErrorTypeID errorType){
        reporter.info("Checking the text of the error message");
        try {
            String id = errorType.getId();
            ElementActions.waitForElementToBeDisplayed(By.id(id), driver);
            return driver.findElement(By.id(id)).getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    /*
        Calculated THM related methods
     */

    public boolean isThmHigherThanZero(){
        double thm = getCalculatedThm();
        return thm > 0;
    }

    public boolean isThmDifferentThanLastTime(ChangeDirection direction){
        double thm = getCalculatedThm();
        switch (direction){
            case LOWER -> {
                reporter.info("Old value: " + TestConstants.getCalculatedThm() + " - New value: " + thm);
                return thm < TestConstants.getCalculatedThm();
            }
            case HIGHER -> {
                reporter.info("Old value: " + TestConstants.getCalculatedThm() + " - New value: " + thm);
                return thm > TestConstants.getCalculatedThm();
            }
            default -> throw new IllegalArgumentException("There's no such direction as: " + direction);
        }
    }

    /**
     * Getting loan amount from the page, and save it in ElementConstants.java for future use.
     * @return the displayed amount of loan as an integer
     */
    private double getCalculatedThm(){
        double thm = Double.parseDouble(thmAmount.getText().replace(",", ".").replaceAll("/[^0-9.]/g", ""));
        if(TestConstants.getCalculatedThm() == 0) TestConstants.setCalculatedThm(thm);
        reporter.info("Calculated THM is: " + thm);
        return thm;
    }

    /*
        Calculated loan amount related methods
     */

    public boolean isLoanAmountHigherThanZero(){
        int amountOfLoan = getCalculatedLoanAmount();
        return amountOfLoan > 0;
    }

    public boolean isLoanAmountDifferentThanLastTime(ChangeDirection direction){
        int amountOfLoan = getCalculatedLoanAmount();
        switch (direction){
            case LOWER -> {
                reporter.info("Old value: " + TestConstants.getCalculatedMaxLoan() + " - New value: " + amountOfLoan);
                return amountOfLoan < TestConstants.getCalculatedMaxLoan();
            }
            case HIGHER -> {
                reporter.info("Old value: " + TestConstants.getCalculatedMaxLoan() + " - New value: " + amountOfLoan);
                return amountOfLoan > TestConstants.getCalculatedMaxLoan();
            }
            default -> throw new IllegalArgumentException("There's no such direction as: " + direction);
        }
    }

    /**
     * Getting loan amount from the page, and save it in ElementConstants.java for future use.
     * @return the displayed amount of loan as an integer
     */
    private int getCalculatedLoanAmount(){
        int amountOfLoan = Integer.parseInt(loanAmount.getText().replaceAll("\\D", ""));
        if(TestConstants.getCalculatedMaxLoan() == 0) TestConstants.setCalculatedMaxLoan(amountOfLoan);
        reporter.info("Calculated loan amount is: " + amountOfLoan);
        return amountOfLoan;
    }

    public enum ChangeDirection{
        HIGHER,
        LOWER
    }
}
