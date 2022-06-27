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
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings("java:S2699")
public class GetVideoHearingsByGroupIdTest extends FunctionalTest {

    private static final String ValidGroupIdInVhTest1 = "f138520a-2a20-4b08-9777-a53fbb651e33";

    @Value("${videoHearings_GroupIdRootContext}")
    protected String videoHearings_GroupIdRootContext;

    @Steps
    VideoHearingSteps videoHearingSteps;

    @Before
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    public void testGetVideoHearingsWithInvalidGroupIdAndNoPayload() {
        headersAsMap = createStandardHMIHeader("SNL", "VH");
        videoHearings_GroupIdRootContext = String.format(videoHearings_GroupIdRootContext, "123");
        videoHearingSteps.performGetVideoHearingsByGroupIdWithSpecifiedStatus(videoHearings_GroupIdRootContext,
                headersAsMap,
                authorizationToken,
                HttpStatus.BAD_REQUEST,
                "");
    }

    @Test
    public void testGetVideoHearingsWithInvalidGroupIdAndEmptyPayload() {
        headersAsMap = createStandardHMIHeader("SNL", "VH");
        videoHearings_GroupIdRootContext = String.format(videoHearings_GroupIdRootContext,"123");
        videoHearingSteps.performGetVideoHearingsByGroupIdWithSpecifiedStatus(videoHearings_GroupIdRootContext,
                headersAsMap,
                authorizationToken,
                HttpStatus.BAD_REQUEST,
                "{}");
    }

    @Test
    public void testGetVideoHearingsWithInvalidGroupIdAndPayload() {
        headersAsMap = createStandardHMIHeader("SNL", "VH");
        videoHearings_GroupIdRootContext = String.format(videoHearings_GroupIdRootContext, "123");
        videoHearingSteps.performGetVideoHearingsByGroupIdWithSpecifiedStatus(videoHearings_GroupIdRootContext,
                headersAsMap,
                authorizationToken,
                HttpStatus.BAD_REQUEST,
                "{\"status\": \"Booked\"}");
    }

    @Test
    public void testGetsVideoHearingWithValidGroupIdAndNoPayload() {
        headersAsMap = createStandardHMIHeader("SNL", "VH");
        videoHearings_GroupIdRootContext = String.format(videoHearings_GroupIdRootContext, ValidGroupIdInVhTest1);
        videoHearingSteps.performGetVideoHearingsByGroupIdWithSpecifiedStatus(videoHearings_GroupIdRootContext,
                headersAsMap,
                authorizationToken,
                HttpStatus.OK,
                "");
    }

    @Test
    public void testGetsVideoHearingWithValidGroupIdAndEmptyPayload() {
        headersAsMap = createStandardHMIHeader("SNL", "VH");
        videoHearings_GroupIdRootContext = String.format(videoHearings_GroupIdRootContext, ValidGroupIdInVhTest1);
        videoHearingSteps.performGetVideoHearingsByGroupIdWithSpecifiedStatus(videoHearings_GroupIdRootContext,
                headersAsMap,
                authorizationToken,
                HttpStatus.OK,
                "{}");
    }

    @Test
    public void testGetVideoHearingsWithValidGroupIdAndPayload() {
        headersAsMap = createStandardHMIHeader("SNL", "VH");
        videoHearings_GroupIdRootContext = String.format(videoHearings_GroupIdRootContext, ValidGroupIdInVhTest1);
        videoHearingSteps.performGetVideoHearingsByGroupIdWithSpecifiedStatus(videoHearings_GroupIdRootContext,
                headersAsMap,
                authorizationToken,
                HttpStatus.OK,
                "{\"status\": \"Booked\"}");
    }
}
