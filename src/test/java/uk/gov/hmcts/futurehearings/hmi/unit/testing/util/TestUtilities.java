package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtilities {

    static String timeStamp = new SimpleDateFormat("yyyyMMdd HH.mm.ss").format(new Date());
    private static final String REPORT_FOLDER_PATH = "./build/reports/unittests/"+"Run_" + timeStamp + ".html";
    static ExtentReports objExtent;
    static int counter=0;

    public static String readFileContents (final String path) throws IOException {
        final File file = ResourceUtils.getFile("classpath:" + path);
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }

    public static void setupReport() {
        if (counter==0){

            objExtent = new ExtentReports();
            objExtent.setAnalysisStrategy(AnalysisStrategy.CLASS);

            ExtentSparkReporter spark = new ExtentSparkReporter(REPORT_FOLDER_PATH);
            objExtent.attachReporter(spark);
            counter++;

        }
    }

    public static ExtentTest startReport(String suiteName) {

        return objExtent.createTest(suiteName);
    }

    public static void endReport() {

        objExtent.flush();
    }
}
