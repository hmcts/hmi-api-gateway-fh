package uk.gov.hmcts.futurehearings.hmi.functional.videohearing;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.videohearing.steps.VideoHearingSteps;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHmiHeader;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings("java:S2699")
public class CloneVideoHearingTest extends FunctionalTest {

    private static final String VALID_HEARING_ID_IN_VH_TEST = "bab5ccb9-1cc5-4a24-9d26-b9c0aafaf43f";
    private static final String SNL = "SNL";

    @Value("${cloneVideoHearingsRootContext}")
    protected String cloneVideoHearingsRootContext;

    @Steps
    VideoHearingSteps videoHearingSteps;

    @Before
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Disabled("This Test is skipped as we are getting service unavailable - 503")
    @Test
    public void testCloneVideoHearingWithValidHearingIdAndNoPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, VALID_HEARING_ID_IN_VH_TEST);
        videoHearingSteps.shouldRequestCloneVideoHearing(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                HttpMethod.POST,
                HttpStatus.BAD_REQUEST,
                "");
    }

    @Test
    public void testCloneVideoHearingWithInvalidHearingIdAndEmptyPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, "123");
        videoHearingSteps.shouldRequestCloneVideoHearing(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                HttpMethod.POST,
                HttpStatus.BAD_REQUEST,
                "{}");
    }

    @Test
    public void testCloneVideoHearingWithValidHearingIdAndEmptyPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, VALID_HEARING_ID_IN_VH_TEST);
        videoHearingSteps.shouldRequestCloneVideoHearing(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                HttpMethod.POST,
                HttpStatus.NO_CONTENT,
                "{}");
    }

    @Test
    public void testCloneVideoHearingWithValidHearingIdAndPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, VALID_HEARING_ID_IN_VH_TEST);
        videoHearingSteps.shouldRequestCloneVideoHearing(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                HttpMethod.POST,
                HttpStatus.NO_CONTENT,
                "{\"status\": 0}");
    }
}
