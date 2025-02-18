package hu.robertszujo.seleniumproject.webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverSetup {

    public void setupChromeDriver() {
        WebDriverManager wdm = WebDriverManager.chromedriver();
        wdm.setup();
    }
}
