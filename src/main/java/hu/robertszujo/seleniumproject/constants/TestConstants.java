package hu.robertszujo.seleniumproject.constants;

public class TestConstants {

    public static final String CALCULATOR_PAGE_URL = "https://erstelakashitel.hu/#lakashitel-maximum-kalkulator";

    private static int CALCULATED_MAX_LOAN = 0;
    public static int getCalculatedMaxLoan() {
        return CALCULATED_MAX_LOAN;
    }
    public static void setCalculatedMaxLoan(int calculatedMaxLoan) {
        CALCULATED_MAX_LOAN = calculatedMaxLoan;
    }

    private static double CALCULATED_THM = 0;
    public static double getCalculatedThm() {
        return CALCULATED_THM;
    }
    public static void setCalculatedThm(double calculatedThm) {
        CALCULATED_THM = calculatedThm;
    }
}
