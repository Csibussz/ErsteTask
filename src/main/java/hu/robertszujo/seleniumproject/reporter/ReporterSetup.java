package hu.robertszujo.seleniumproject.reporter;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReporterSetup {

    private final String reportFolderPath = System.getProperty("user.dir") + File.separator + "target" + File.separator + "reports";
    private final String reportFileName = "Report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".html";

    public ExtentSparkReporter createReporter() {
        //Create report folder first if it does not exist
        File reportFolder = new File(reportFolderPath);
        if (!reportFolder.exists()) reportFolder.mkdir();

        ExtentSparkReporter reporter = new ExtentSparkReporter(new File(reportFolderPath + File.separator + reportFileName));
        reporter.config().thumbnailForBase64(true);
        return reporter;
    }
}
