package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@SuppressWarnings("PMD")
public class TestReporter implements TestWatcher, BeforeAllCallback, AfterAllCallback, BeforeEachCallback {

    static ExtentReports objExtent;
    static ExtentTest objTest;
    static ExtentTest objStep;
    static int counter;

    String timeStamp = new SimpleDateFormat("yyyyMMdd HH.mm.ss").format(new Date());
    private final String reportFolderPath = "./build/reports/unittests/" + "Run_" + timeStamp + ".html";

    @Override
    public void beforeAll(ExtensionContext context) {
        if (counter == 0) {
            objExtent = new ExtentReports();
            objExtent.setAnalysisStrategy(AnalysisStrategy.CLASS);
            ExtentSparkReporter spark = new ExtentSparkReporter(reportFolderPath);
            objExtent.attachReporter(spark);
            counter++;
        }
        objTest = objExtent.createTest(context.getDisplayName());
    }

    public static ExtentTest getObjStep() {
        return objStep;
    }

    @Override
    public void beforeEach(ExtensionContext context) {

        objStep = objTest.createNode(context.getDisplayName());

    }

    @Override
    public void testSuccessful(ExtensionContext context) {

    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {

    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {

        objTest.createNode(context.getDisplayName()).skip(reason.get());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        objStep.fail(cause);
    }

    @Override
    public void afterAll(ExtensionContext context) {
        objExtent.flush();
        objStep = null;
        objTest = null;
    }

}
