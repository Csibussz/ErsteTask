package hu.robertszujo.seleniumproject.webdriver;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

public class ChromeDriverOptions {

    public ChromeOptions getChromeDriverOptions() {
        ChromeOptions options = new ChromeOptions();

        //Required to allow access from Selenium
        options.addArguments("--remote-allow-origins=*", "--headless", "--start-maximized");


        //Force Hungarian language as preferred for visited pages
        Map<String, Object> userSettings = Map.of("intl.accept_languages", "hu-HU,hu");
        options.setExperimentalOption("prefs", userSettings);

        return options;
    }
}
