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

public class TestReporter implements TestWatcher, BeforeAllCallback, AfterAllCallback, BeforeEachCallback {

    static ExtentReports objExtent;
    static ExtentTest objTest, objStep;
    static int counter=0;

    String timeStamp = new SimpleDateFormat("yyyyMMdd HH.mm.ss").format(new Date());
    private final String REPORT_FOLDER_PATH = "./build/reports/unittests/"+"Run_" + timeStamp + ".html";

    @Override
    public void beforeAll(ExtensionContext context) {
        if (counter==0){

            objExtent = new ExtentReports();
            objExtent.setAnalysisStrategy(AnalysisStrategy.CLASS);
            ExtentSparkReporter spark = new ExtentSparkReporter(REPORT_FOLDER_PATH);
            objExtent.attachReporter(spark);
            counter++;
        }
        objTest = objExtent.createTest(context.getDisplayName());
    }

    public static ExtentTest getObjStep() {
        return objStep;
    }

    @Override
    public void beforeEach(ExtensionContext Context) {

        objStep = objTest.createNode(Context.getDisplayName());

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
        objStep=null;
        objTest=null;
    }

}
