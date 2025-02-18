package hu.robertszujo.seleniumproject;

import com.aventstack.extentreports.ExtentTest;
import hu.robertszujo.seleniumproject.constants.TestConstants;
import hu.robertszujo.seleniumproject.constants.TestContextConstants;
import hu.robertszujo.seleniumproject.pages.LoanCalculatorPage;
import hu.robertszujo.seleniumproject.pages.LoanCalculatorPage.ChangeDirection;
import hu.robertszujo.seleniumproject.pages.components.CookiePopup;
import hu.robertszujo.seleniumproject.utils.ErrorTypeExpextedText;
import hu.robertszujo.seleniumproject.utils.ErrorTypeID;
import org.assertj.core.api.Assertions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class LoanCalculatorTests extends BaseTestClass {

    private ExtentTest reporter;

    // Page objects
    private LoanCalculatorPage loanCalculatorPage;
    private CookiePopup cookiePopup;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestContext context, ITestResult result) {
        reporter = SuiteWideStorage.testReport.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        context.setAttribute(TestContextConstants.REPORTER, reporter);
        initializePageObjects();
    }

    public void initializePageObjects() {
        loanCalculatorPage = new LoanCalculatorPage(driver, reporter);
        cookiePopup = new CookiePopup(driver, reporter);
    }

    // *** Tests ***

    @Test(description = "Cookie popup should be displayed after page load")
    public void loadCalculatorPage_cookiePopupShouldBeDisplayed() {
        // Given + When
        driver.get(TestConstants.CALCULATOR_PAGE_URL);

        // Then
        Assertions.assertThat(cookiePopup.isCookiePopupDisplayedAfterWaiting())
                .as("Cookie popup should have displayed after page load")
                .isTrue();
        reporter.pass("Cookie popup was displayed successfully");
    }

    @Test(description = "Cookie popup should disappear after accepting cookies")
    public void acceptCookies_CookiePopupShouldDisappear() {
        //Given
        driver.get(TestConstants.CALCULATOR_PAGE_URL);
        cookiePopup.waitForCookiePopupToBeDisplayed();

        //When
        cookiePopup.clickOnCookieAcceptButton();

        //Then
        Assertions.assertThat(cookiePopup.hasCookiePopupDisappearedAfterWaiting())
                .as("Cookie popup should have disappeared")
                .isTrue();
        reporter.pass("Cookie popup has disappeared successfully");
    }

    @Test(description = "Calculator form should be displayed after page load & accepting cookies")
    public void loadPageAndAcceptCookies_CalculatorFormShouldBeDisplayed() {
        //Given + When
        loadPageAndAcceptCookies();

        //Then
        loanCalculatorPage.isCalculatorFormDisplayedAfterWaiting();
        reporter.pass("Loan calculator page was displayed successfully");
    }

    /*
        Error case
            Checking if error texts are visible
     */

    @Test(  testName = "Error case: Age under 18",
            description = "Error text should be displayed if the age is under 18")
    public void testIfErrorIsVisible_UnderagePeople(){
        //Given + When
        loadPageAndAcceptCookies();

        //Then
        loanCalculatorPage.isCalculatorFormDisplayedAfterWaiting();
        reporter.pass("Loan calculator page was displayed successfully");

        // When fill up the age field with 17
        loanCalculatorPage.fillUpAgeField(17);

        // Then check the age error text is visible
        Assertions.assertThat(loanCalculatorPage.isErrorTextToBeDisplayed(ErrorTypeID.ELETKOR))
                .as("Age error text should be visible")
                .isTrue();
        reporter.pass("Age error text is visible");

        // And check the age error text
        String expectedErrorText = ErrorTypeExpextedText.UNDERAGE_ERROR.getExpectedErrorText();
        Assertions.assertThat(loanCalculatorPage.getErrorText(ErrorTypeID.ELETKOR).equals(expectedErrorText))
                .as("Age error text should be: \"" + expectedErrorText + "\"")
                .isTrue();
        reporter.pass("Age error text is as expected");
    }

    @Test(  testName = "Error case: Age over 65",
            description = "Error text should be displayed if the age is over 65")
    public void testIfErrorIsVisible_OveragePeople(){
        //Given + When
        loadPageAndAcceptCookies();

        //Then
        loanCalculatorPage.isCalculatorFormDisplayedAfterWaiting();
        reporter.pass("Loan calculator page was displayed successfully");

        // When fill up the age field with 66
        loanCalculatorPage.fillUpAgeField(66);

        // Then check the age error text is visible
        Assertions.assertThat(loanCalculatorPage.isErrorTextToBeDisplayed(ErrorTypeID.ELETKOR))
                .as("Age error text should be visible")
                .isTrue();
        reporter.pass("Age error text is visible");

        // And check the age error text
        String expectedErrorText = ErrorTypeExpextedText.OVERAGE_ERROR.getExpectedErrorText();
        Assertions.assertThat(loanCalculatorPage.getErrorText(ErrorTypeID.ELETKOR).equals(expectedErrorText))
                .as("Age error text should be: \"" + expectedErrorText + "\"")
                .isTrue();
        reporter.pass("Age error text is as expected");
    }

    @Test(  testName = "Error case: Value of real estate is under 5.000.000 HUF",
            description = "Error text should be displayed if the value of the real estate is under 5.000.000 HUF")
    public void testIfErrorIsVisible_TooLowValueOfRealEstate(){
        //Given + When
        loadPageAndAcceptCookies();

        //Then
        loanCalculatorPage.isCalculatorFormDisplayedAfterWaiting();
        reporter.pass("Loan calculator page was displayed successfully");

        // When fill up the value of the real estate field with 4.999.999
        loanCalculatorPage.fillUpValueOfRealEstateField(4999999);

        // Then check the low real estate value error text is visible
        Assertions.assertThat(loanCalculatorPage.isErrorTextToBeDisplayed(ErrorTypeID.INGATLAN_ERTEKE))
                .as("Low real estate value error text should be visible")
                .isTrue();
        reporter.pass("Low real estate value error text is visible");

        // And check the low real estate value error text
        String expectedErrorText = ErrorTypeExpextedText.LOW_VALUE_REALESTATE_ERROR.getExpectedErrorText();
        Assertions.assertThat(loanCalculatorPage.getErrorText(ErrorTypeID.INGATLAN_ERTEKE).equals(expectedErrorText))
                .as("Low real estate value error text should be: \"" + expectedErrorText + "\"")
                .isTrue();
        reporter.pass("Low real estate value error text is as expected");
    }

    @Test(  testName = "Error case: Income under 175k - one person family",
            description = "Error text should be displayed if the income is under 175k in case of a one person family")
    public void testIfErrorIsVisible_OnePersonIncomeIsNotEnough(){
        //Given + When
        loadPageAndAcceptCookies();

        //Then
        loanCalculatorPage.isCalculatorFormDisplayedAfterWaiting();
        reporter.pass("Loan calculator page was displayed successfully");

        // When fill up the income field with 174.999
        loanCalculatorPage.fillUpIncomeField(174999);

        // Then check the low income error text is visible
        Assertions.assertThat(loanCalculatorPage.isErrorTextToBeDisplayed(ErrorTypeID.JOVEDELEM))
                .as("Low income error text should be visible")
                .isTrue();
        reporter.pass("Low income error text is visible");

        // And check the low income error text
        String expectedErrorText = ErrorTypeExpextedText.LOW_INCOME_ONE_PERSON.getExpectedErrorText();
        Assertions.assertThat(loanCalculatorPage.getErrorText(ErrorTypeID.JOVEDELEM).equals(expectedErrorText))
                .as("Low income error text should be: \"" + expectedErrorText + "\"")
                .isTrue();
        reporter.pass("Low income error text is as expected");
    }

    @Test(  testName = "Error case: Income under 260k - More person family - First click more option",
            description = "Error text should be displayed if the income is under 260k in case of a more person family")
    public void testIfErrorIsVisible_MorePersonIncomeIsNotEnough(){
        //Given + When
        loadPageAndAcceptCookies();

        //Then
        loanCalculatorPage.isCalculatorFormDisplayedAfterWaiting();
        reporter.pass("Loan calculator page was displayed successfully");

        // When click on more person option
        loanCalculatorPage.clickOnMorePersonOption();

        // When fill up the income field with 259.999
        loanCalculatorPage.fillUpIncomeField(259999);

        // Then check the low income error text is visible
        Assertions.assertThat(loanCalculatorPage.isErrorTextToBeDisplayed(ErrorTypeID.JOVEDELEM))
                .as("Low income error text should be visible")
                .isTrue();
        reporter.pass("Low income error text is visible");

        // And check the low income error text
        String expectedErrorText = ErrorTypeExpextedText.LOW_INCOME_MORE_PERSON.getExpectedErrorText();
        Assertions.assertThat(loanCalculatorPage.getErrorText(ErrorTypeID.JOVEDELEM).equals(expectedErrorText))
                .as("Low income error text should be: \"" + expectedErrorText + "\"")
                .isTrue();
        reporter.pass("Low income error text is as expected");
    }

    @Test(  testName = "Error case: Income under 260k - More person family - Lastly click on more option",
            description = "Error text should be displayed if the income is under 260k in case of a more person family even if the option is clicked later")
    public void testIfErrorIsVisible_MorePersonIncomeIsNotEnough_LastlyClickOnMoreOption(){
        //Given + When
        loadPageAndAcceptCookies();

        //Then
        loanCalculatorPage.isCalculatorFormDisplayedAfterWaiting();
        reporter.pass("Loan calculator page was displayed successfully");

        // When fill up the income field with 259.999
        loanCalculatorPage.fillUpIncomeField(259999);

        // Then check the low income error text is invisible
        Assertions.assertThat(loanCalculatorPage.isErrorTextToBeDisplayed(ErrorTypeID.JOVEDELEM))
                .as("Low income error text should be visible")
                .isFalse();
        reporter.pass("Low income error text is visible");

        // When click on more person option
        loanCalculatorPage.clickOnMorePersonOption();

        // Then check the low income error text is visible
        Assertions.assertThat(loanCalculatorPage.isErrorTextToBeDisplayed(ErrorTypeID.JOVEDELEM))
                .as("Low income error text should be visible")
                .isTrue();
        reporter.pass("Low income error text is visible");

        // And check the low income error text
        String expectedErrorText = ErrorTypeExpextedText.LOW_INCOME_MORE_PERSON.getExpectedErrorText();
        Assertions.assertThat(loanCalculatorPage.getErrorText(ErrorTypeID.JOVEDELEM).equals(expectedErrorText))
                .as("Low income error text should be: \"" + expectedErrorText + "\"")
                .isTrue();
        reporter.pass("Low income error text is as expected");
    }

    @Test(  testName = "Error case: Sum loan repayment too much - under 500.000",
            description = "Error text should be displayed if the sum of loan repayment amount is at least 50% of the income - under 500.000")
    public void testIfErrorIsVisible_SumLoanRepaymentAmountIsHalfOfIncome(){
        //Given + When
        loadPageAndAcceptCookies();

        //Then
        loanCalculatorPage.isCalculatorFormDisplayedAfterWaiting();
        reporter.pass("Loan calculator page was displayed successfully");

        // When fill up the income field with 175.000
        loanCalculatorPage.fillUpIncomeField(175000);

        // And fill up the sum of loan repayment field with 87.500
        loanCalculatorPage.fillUpLoanRepaymentField(87500);

        // Then check the too much loan repayment error text is visible
        Assertions.assertThat(loanCalculatorPage.isErrorTextToBeDisplayed(ErrorTypeID.MEGLEVO_TORLESZTO))
                .as("Too much loan repayment error text should be visible")
                .isTrue();
        reporter.pass("Too much loan repayment error text is visible");

        // And check the too much loan repayment error text
        String expectedErrorText = ErrorTypeExpextedText.LOAN_REPAYMENT_TOO_MUCH.getExpectedErrorText();
        Assertions.assertThat(loanCalculatorPage.getErrorText(ErrorTypeID.MEGLEVO_TORLESZTO).equals(expectedErrorText))
                .as("Too much loan repayment error text should be: \"" + expectedErrorText + "\"")
                .isTrue();
        reporter.pass("Too much loan repayment error text is as expected");
    }

    @Test(  testName = "Error case: Sum loan repayment too much - over 500.000",
            description = "Error text should be displayed if the sum of loan repayment amount is at least 60% of the income - over 500.000")
    public void testIfErrorIsVisible_SumLoanAmountIs60PercentOfIncome(){
        //Given + When
        loadPageAndAcceptCookies();

        //Then
        loanCalculatorPage.isCalculatorFormDisplayedAfterWaiting();
        reporter.pass("Loan calculator page was displayed successfully");

        // When fill up the income field with 500.000
        loanCalculatorPage.fillUpIncomeField(500000);

        // And fill up the sum of loan repayment field with 250.000
        loanCalculatorPage.fillUpLoanRepaymentField(250000);

        // Then check the too much loan repayment error text is NOT visible
        Assertions.assertThat(loanCalculatorPage.isErrorTextToBeDisplayed(ErrorTypeID.MEGLEVO_TORLESZTO))
                .as("Too much loan repayment error text should NOT be visible")
                .isFalse();
        reporter.pass("Too much loan repayment error text is invisible");

        // When clears up the sum of loan repayment field
        loanCalculatorPage.clearsUpLoanRepaymentField();

        // And fill up the sum of loan repayment field with 300.000
        loanCalculatorPage.fillUpLoanRepaymentField(300000);

        // Then check the too much loan repayment error text is visible
        Assertions.assertThat(loanCalculatorPage.isErrorTextToBeDisplayed(ErrorTypeID.MEGLEVO_TORLESZTO))
                .as("Too much loan repayment error text should be visible")
                .isTrue();
        reporter.pass("Too much loan repayment error text is visible");

        // And check the too much loan repayment error text
        String expectedErrorText = ErrorTypeExpextedText.LOAN_REPAYMENT_TOO_MUCH.getExpectedErrorText();
        Assertions.assertThat(loanCalculatorPage.getErrorText(ErrorTypeID.MEGLEVO_TORLESZTO).equals(expectedErrorText))
                .as("Too much loan repayment error text should be: \"" + expectedErrorText + "\"")
                .isTrue();
        reporter.pass("Too much loan repayment error text is as expected");
    }

    /*
        Happy path without additional checks
     */

    @Test(  testName = "Happy path - Successful loan calculation",
            description = "Happy path - Successful loan calculation")
    public void testHappyPath(){
        //Given + When
        loadPageAndAcceptCookies();

        //Then
        loanCalculatorPage.isCalculatorFormDisplayedAfterWaiting();
        reporter.pass("Loan calculator page was displayed successfully");

        // When fill up the value of the real estate field with 10.000.000
        loanCalculatorPage.fillUpValueOfRealEstateField(10000000);

        // And fill up the age field with 29
        loanCalculatorPage.fillUpAgeField(29);

        // And fill up the income field with 260.000
        loanCalculatorPage.fillUpIncomeField(260000);

        // And fill up the sum of loan repayment field with 0
        loanCalculatorPage.fillUpLoanRepaymentField(0);

        // And fill up the overdraft field with 0
        loanCalculatorPage.fillUpOverdraftField(0);

        // And click on the calculate button
        loanCalculatorPage.clickCalculateButton();

        // Then check the calculation result and interested button is displayed
        Assertions.assertThat(loanCalculatorPage.isCalculatedResultAndInterestedButtonDisplayedAfterWaiting())
                .as("The calculation result and interested button should be displayed")
                .isTrue();
        reporter.pass("The calculation result and interested button is displayed");

        // And check the calculated max loan is higher than 0
        Assertions.assertThat(loanCalculatorPage.isLoanAmountHigherThanZero())
                .as("The calculated max loan should be higher than 0")
                .isTrue();
        reporter.pass("The calculated max loan is higher than 0");
    }

    /*
        Testing connections
            Lowering or rising loan amount and THM amounts
     */

    @Test(  testName = "Testing connections: Raised loan amount if raised real estate value",
            description = "Loan amount should be rising if the real estate amount is raised")
    public void testingConnections_RaisedLoanAmountIfRaisedRealEstateValue(){
        //Given + When
        loadPageAndAcceptCookies();

        //Then
        loanCalculatorPage.isCalculatorFormDisplayedAfterWaiting();
        reporter.pass("Loan calculator page was displayed successfully");

        // When fill up the value of the real estate field with 5.000.000
        loanCalculatorPage.fillUpValueOfRealEstateField(5000000);

        // And fill up the age field with 29
        loanCalculatorPage.fillUpAgeField(29);

        // And fill up the income field with 260.000
        loanCalculatorPage.fillUpIncomeField(260000);

        // And fill up the sum of loan repayment field with 0
        loanCalculatorPage.fillUpLoanRepaymentField(0);

        // And fill up the overdraft field with 0
        loanCalculatorPage.fillUpOverdraftField(0);

        // And click on the calculate button
        loanCalculatorPage.clickCalculateButton();

        // Then check the calculation result and interested button is displayed
        Assertions.assertThat(loanCalculatorPage.isCalculatedResultAndInterestedButtonDisplayedAfterWaiting())
                .as("The calculation result and interested button should be displayed")
                .isTrue();
        reporter.pass("The calculation result and interested button is displayed");

        // And check the calculated max loan is higher than 0
        Assertions.assertThat(loanCalculatorPage.isLoanAmountHigherThanZero())
                .as("The calculated max loan should be higher than 0")
                .isTrue();
        reporter.pass("The calculated max loan is higher than 0");

        // When clean up real estate field
        loanCalculatorPage.clearsUpValueOfRealEstateField();

        // And fill up the value of the real estate field with 50.000.000
        loanCalculatorPage.fillUpValueOfRealEstateField(50000000);

        // And click on the calculate button
        loanCalculatorPage.clickCalculateButton();

        // Then check the calculation result and interested button is displayed
        Assertions.assertThat(loanCalculatorPage.isCalculatedResultAndInterestedButtonDisplayedAfterWaiting())
                .as("The calculation result and interested button should be displayed")
                .isTrue();
        reporter.pass("The calculation result and interested button is displayed");

        // And check the calculated loan amount is higher than before
        Assertions.assertThat(loanCalculatorPage.isLoanAmountDifferentThanLastTime(ChangeDirection.HIGHER))
                .as("The newly calculated loan amount should be higher than the calculation before")
                .isTrue();
        reporter.pass("The newly calculated loan amount is higher than the calculation before");
    }

    @Test(  testName = "Testing connections: Raised loan amount if raised income value",
            description = "Loan amount should be rising if the income amount is raised")
    public void testingConnections_RaisedLoanAmountIfRaisedIncomeValue(){
        //Given + When
        loadPageAndAcceptCookies();

        //Then
        loanCalculatorPage.isCalculatorFormDisplayedAfterWaiting();
        reporter.pass("Loan calculator page was displayed successfully");

        // When fill up the value of the real estate field with 60.000.000
        loanCalculatorPage.fillUpValueOfRealEstateField(60000000);

        // And fill up the age field with 29
        loanCalculatorPage.fillUpAgeField(29);

        // And fill up the income field with 260.000
        loanCalculatorPage.fillUpIncomeField(260000);

        // And fill up the sum of loan repayment field with 0
        loanCalculatorPage.fillUpLoanRepaymentField(0);

        // And fill up the overdraft field with 0
        loanCalculatorPage.fillUpOverdraftField(0);

        // And click on the calculate button
        loanCalculatorPage.clickCalculateButton();

        // Then check the calculation result and interested button is displayed
        Assertions.assertThat(loanCalculatorPage.isCalculatedResultAndInterestedButtonDisplayedAfterWaiting())
                .as("The calculation result and interested button should be displayed")
                .isTrue();
        reporter.pass("The calculation result and interested button is displayed");

        // And check the calculated max loan is higher than 0
        Assertions.assertThat(loanCalculatorPage.isLoanAmountHigherThanZero())
                .as("The calculated max loan should be higher than 0")
                .isTrue();
        reporter.pass("The calculated max loan is higher than 0");

        // When clean up income field
        loanCalculatorPage.clearsUpIncomeField();

        // And fill up the income field with 1.500.000
        loanCalculatorPage.fillUpIncomeField(1500000);

        // And click on the calculate button
        loanCalculatorPage.clickCalculateButton();

        // Then check the calculation result and interested button is displayed
        Assertions.assertThat(loanCalculatorPage.isCalculatedResultAndInterestedButtonDisplayedAfterWaiting())
                .as("The calculation result and interested button should be displayed")
                .isTrue();
        reporter.pass("The calculation result and interested button is displayed");

        // And check the calculated loan amount is higher than before
        Assertions.assertThat(loanCalculatorPage.isLoanAmountDifferentThanLastTime(ChangeDirection.HIGHER))
                .as("The newly calculated loan amount should be higher than the calculation before")
                .isTrue();
        reporter.pass("The newly calculated loan amount is higher than the calculation before");
    }

    @Test(  testName = "Testing connections: Lowered loan amount if raised loan repayment value",
            description = "Loan amount should be lowered if the loan repayment amount is raised")
    public void testingConnections_LoweredLoanAmountIfRaisedLoanRepaymentValue(){
        //Given + When
        loadPageAndAcceptCookies();

        //Then
        loanCalculatorPage.isCalculatorFormDisplayedAfterWaiting();
        reporter.pass("Loan calculator page was displayed successfully");

        // When fill up the value of the real estate field with 20.000.000
        loanCalculatorPage.fillUpValueOfRealEstateField(20000000);

        // And fill up the age field with 29
        loanCalculatorPage.fillUpAgeField(29);

        // And fill up the income field with 350.000
        loanCalculatorPage.fillUpIncomeField(350000);

        // And fill up the sum of loan repayment field with 0
        loanCalculatorPage.fillUpLoanRepaymentField(0);

        // And fill up the overdraft field with 0
        loanCalculatorPage.fillUpOverdraftField(0);

        // And click on the calculate button
        loanCalculatorPage.clickCalculateButton();

        // Then check the calculation result and interested button is displayed
        Assertions.assertThat(loanCalculatorPage.isCalculatedResultAndInterestedButtonDisplayedAfterWaiting())
                .as("The calculation result and interested button should be displayed")
                .isTrue();
        reporter.pass("The calculation result and interested button is displayed");

        // And check the calculated max loan is higher than 0
        Assertions.assertThat(loanCalculatorPage.isLoanAmountHigherThanZero())
                .as("The calculated max loan should be higher than 0")
                .isTrue();
        reporter.pass("The calculated max loan is higher than 0");

        // When clean up loan repayment field
        loanCalculatorPage.clearsUpLoanRepaymentField();

        // And fill up the loan repayment field with 100.000
        loanCalculatorPage.fillUpLoanRepaymentField(100000);

        // And click on the calculate button
        loanCalculatorPage.clickCalculateButton();

        // Then check the calculation result and interested button is displayed
        Assertions.assertThat(loanCalculatorPage.isCalculatedResultAndInterestedButtonDisplayedAfterWaiting())
                .as("The calculation result and interested button should be displayed")
                .isTrue();
        reporter.pass("The calculation result and interested button is displayed");

        // And check the calculated loan amount is lower than before
        Assertions.assertThat(loanCalculatorPage.isLoanAmountDifferentThanLastTime(ChangeDirection.LOWER))
                .as("The newly calculated loan amount should be lower than the calculation before")
                .isTrue();
        reporter.pass("The newly calculated loan amount is lower than the calculation before");
    }

    @Test(  testName = "Testing connections: Lowered THM if insurance checked",
            description = "THM should be lowered if the insurance checkbox is checked")
    public void testingConnections_LoweredThmIfInsuranceChecked(){
        //Given + When
        loadPageAndAcceptCookies();

        //Then
        loanCalculatorPage.isCalculatorFormDisplayedAfterWaiting();
        reporter.pass("Loan calculator page was displayed successfully");

        // When fill up the value of the real estate field with 20.000.000
        loanCalculatorPage.fillUpValueOfRealEstateField(20000000);

        // And fill up the age field with 29
        loanCalculatorPage.fillUpAgeField(29);

        // And fill up the income field with 350.000
        loanCalculatorPage.fillUpIncomeField(350000);

        // And fill up the sum of loan repayment field with 0
        loanCalculatorPage.fillUpLoanRepaymentField(0);

        // And fill up the overdraft field with 0
        loanCalculatorPage.fillUpOverdraftField(0);

        // And click on the calculate button
        loanCalculatorPage.clickCalculateButton();

        // Then check the calculation result and interested button is displayed
        Assertions.assertThat(loanCalculatorPage.isCalculatedResultAndInterestedButtonDisplayedAfterWaiting())
                .as("The calculation result and interested button should be displayed")
                .isTrue();
        reporter.pass("The calculation result and interested button is displayed");

        // And check the calculated THM is higher than 0
        Assertions.assertThat(loanCalculatorPage.isThmHigherThanZero())
                .as("The calculated THM should be higher than 0")
                .isTrue();
        reporter.pass("The calculated THM is higher than 0");

        // When click insurance checkbox
        loanCalculatorPage.clickInsurance();

        // Then check the calculated THM is lower than before
        Assertions.assertThat(loanCalculatorPage.isThmDifferentThanLastTime(ChangeDirection.LOWER))
                .as("The newly calculated THM should be higher than the calculation before")
                .isTrue();
        reporter.pass("The newly calculated THM is higher than the calculation before");
    }
    
    // *** Helper methods ***
    
    private void loadPageAndAcceptCookies() {
        driver.get(TestConstants.CALCULATOR_PAGE_URL);
        cookiePopup.waitForCookiePopupToBeDisplayed();
        cookiePopup.clickOnCookieAcceptButton();
        cookiePopup.waitForCookiePopupToDisappear();
    }
}
