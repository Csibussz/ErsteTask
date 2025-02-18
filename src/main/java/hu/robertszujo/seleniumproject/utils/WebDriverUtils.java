package hu.robertszujo.seleniumproject.utils;

import org.apache.hc.client5.http.utils.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class WebDriverUtils {

    public static String captureScreenshotAsBase64(WebDriver driver) throws IOException {
        // Take a screenshot of the entire visible part of page/screen
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage startImg = ImageIO.read(screenshot);
        BufferedImage finalImg = new BufferedImage(startImg.getWidth(), startImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        finalImg.createGraphics().drawImage(startImg, 0, 0, Color.WHITE, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(finalImg, "jpeg", baos);
        return Base64.encodeBase64String(baos.toByteArray());
    }

}
