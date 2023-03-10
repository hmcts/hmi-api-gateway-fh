package uk.gov.hmcts.futurehearings.hmi.functional.videohearing;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
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
public class GetVideoHearingsByGroupIdTest extends FunctionalTest {

    private static final String VALID_GROUP_ID = "f138520a-2a20-4b08-9777-a53fbb651e33";
    private static final String SNL = "SNL";

    @Value("${videoHearings_GroupIdRootContext}")
    protected String videoHearingsGroupIdRootContext;

    @Steps
    VideoHearingSteps videoHearingSteps;

    @Before
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    public void testGetVideoHearingsWithInvalidGroupIdAndNoPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        videoHearingsGroupIdRootContext = String.format(videoHearingsGroupIdRootContext, "123");
        videoHearingSteps.performGetVideoHearingsByGroupIdWithSpecifiedStatus(videoHearingsGroupIdRootContext,
                headersAsMap,
                authorizationToken,
                HttpStatus.BAD_REQUEST,
                "");
    }

    @Test
    public void testGetVideoHearingsWithInvalidGroupIdAndEmptyPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        videoHearingsGroupIdRootContext = String.format(videoHearingsGroupIdRootContext, "123");
        videoHearingSteps.performGetVideoHearingsByGroupIdWithSpecifiedStatus(videoHearingsGroupIdRootContext,
                headersAsMap,
                authorizationToken,
                HttpStatus.BAD_REQUEST,
                "{}");
    }

    @Test
    public void testGetVideoHearingsWithInvalidGroupIdAndPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        videoHearingsGroupIdRootContext = String.format(videoHearingsGroupIdRootContext, "123");
        videoHearingSteps.performGetVideoHearingsByGroupIdWithSpecifiedStatus(videoHearingsGroupIdRootContext,
                headersAsMap,
                authorizationToken,
                HttpStatus.BAD_REQUEST,
                "{\"status\": \"Booked\"}");
    }

    @Test
    public void testGetsVideoHearingWithValidGroupIdAndNoPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        videoHearingsGroupIdRootContext = String.format(videoHearingsGroupIdRootContext, VALID_GROUP_ID);
        videoHearingSteps.performGetVideoHearingsByGroupIdWithSpecifiedStatus(videoHearingsGroupIdRootContext,
                headersAsMap,
                authorizationToken,
                HttpStatus.OK,
                "");
    }

    @Test
    public void testGetsVideoHearingWithValidGroupIdAndEmptyPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        videoHearingsGroupIdRootContext = String.format(videoHearingsGroupIdRootContext, VALID_GROUP_ID);
        videoHearingSteps.performGetVideoHearingsByGroupIdWithSpecifiedStatus(videoHearingsGroupIdRootContext,
                headersAsMap,
                authorizationToken,
                HttpStatus.OK,
                "{}");
    }

    @Test
    public void testGetVideoHearingsWithValidGroupIdAndPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        videoHearingsGroupIdRootContext = String.format(videoHearingsGroupIdRootContext, VALID_GROUP_ID);
        videoHearingSteps.performGetVideoHearingsByGroupIdWithSpecifiedStatus(videoHearingsGroupIdRootContext,
                headersAsMap,
                authorizationToken,
                HttpStatus.OK,
                "{\"status\": \"Booked\"}");
    }
}
