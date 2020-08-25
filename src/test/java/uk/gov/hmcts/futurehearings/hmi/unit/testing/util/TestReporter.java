package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import com.aventstack.extentreports.ExtentTest;
import org.junit.jupiter.api.extension.ExtensionContext;

import static uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites.RetrieveHearingScheduleUnitTests.reportStats;
import org.junit.jupiter.api.extension.TestWatcher;
import java.util.Optional;


public class TestReporter implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {

    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {

    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {

        ExtentTest objTest  =  reportStats();
        objTest.createNode(context.getDisplayName()).skip(reason.get());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {

    }

}
