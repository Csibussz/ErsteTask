package hu.robertszujo.seleniumproject.utils;


public enum ErrorTypeExpextedText {
    UNDERAGE_ERROR("Hitelt kizárólag 18. életévüket betöltött személyek igényelhetnek."),
    OVERAGE_ERROR("Sajnos jelenleg nem tudunk kalkulálni, kérjük add meg adataid és visszahívunk."),
    LOW_VALUE_REALESTATE_ERROR("Minimum 5 millió forint érékű ingatlan szükséges"),
    LOW_INCOME_ONE_PERSON("Minimum 175 000 ft jövedelem szükséges"),
    LOW_INCOME_MORE_PERSON("Minimum 260 000 ft jövedelem szükséges"),
    LOAN_REPAYMENT_TOO_MUCH("A megadott jövedelemhez képest túl nagy a törlesztőrészlet.")
    ;

    final String expectedErrorText;

    ErrorTypeExpextedText(String expectedErrorText) {
        this.expectedErrorText = expectedErrorText;
    }

    public String getExpectedErrorText() {
        return expectedErrorText;
    }
}
