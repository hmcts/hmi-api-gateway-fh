package uk.gov.hmcts.futurehearings.hmi.functional.videohearing;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHMIHeader;

import org.springframework.http.HttpStatus;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.videohearing.steps.VideoHearingSteps;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings("java:S2699")
public class CloneVideoHearingTest extends FunctionalTest {

    private static final String ValidHearingIdInVhTest1 = "bab5ccb9-1cc5-4a24-9d26-b9c0aafaf43f";

    @Value("${cloneVideoHearingsRootContext}")
    protected String cloneVideoHearingsRootContext;

    @Steps
    VideoHearingSteps videoHearingSteps;

    @Before
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    public void testCloneVideoHearingWithValidHearingIdAndNoPayload() {
        headersAsMap = createStandardHMIHeader("SNL", "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, ValidHearingIdInVhTest1);
        videoHearingSteps.shouldRequestCloneVideoHearing(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                HttpMethod.POST,
                HttpStatus.BAD_REQUEST,
                "");
    }

    @Test
    public void testCloneVideoHearingWithInvalidHearingIdAndEmptyPayload() {
        headersAsMap = createStandardHMIHeader("SNL", "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext,"123");
        videoHearingSteps.shouldRequestCloneVideoHearing(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                HttpMethod.POST,
                HttpStatus.BAD_REQUEST,
                "{}");
    }

    @Test
    public void testCloneVideoHearingWithValidHearingIdAndEmptyPayload() {
        headersAsMap = createStandardHMIHeader("SNL", "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, ValidHearingIdInVhTest1);
        videoHearingSteps.shouldRequestCloneVideoHearing(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                HttpMethod.POST,
                HttpStatus.NO_CONTENT,
                "{}");
    }

    @Test
    public void testCloneVideoHearingWithValidHearingIdAndPayload() {
        headersAsMap = createStandardHMIHeader("SNL", "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, ValidHearingIdInVhTest1);
        videoHearingSteps.shouldRequestCloneVideoHearing(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                HttpMethod.POST,
                HttpStatus.NO_CONTENT,
                "{\"status\": 0}");
    }
}
