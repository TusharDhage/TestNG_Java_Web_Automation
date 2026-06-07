package reports;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {

            File dir = new File(System.getProperty("user.dir") + "/reports");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String path = dir.getAbsolutePath() + "/extent.html";

            ExtentSparkReporter reporter = new ExtentSparkReporter(path);
            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
        return extent;
    }
}