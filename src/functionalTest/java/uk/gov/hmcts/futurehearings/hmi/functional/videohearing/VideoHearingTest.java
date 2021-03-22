package uk.gov.hmcts.futurehearings.hmi.functional.videohearing;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.TestingUtils.readFileContents;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHMIHeader;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.videohearing.steps.VideoHearingSteps;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"In order to test that the Video Hearing Functionality is working properly",
        "As a tester",
        "I want to be able to execute the tests for People API"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings("java:S2699")
public class VideoHearingTest extends FunctionalTest {

    @Value("${videohearingsRootContext}")
    protected String videohearingsRootContext;

    @Value("${videohearings_idRootContext}")
    protected String videohearings_idRootContext;

    @Steps
    VideoHearingSteps videoHearingSteps;

    public static final String VIDEO_HEARING_INPUT_PATH = "uk/gov/hmcts/futurehearings/hmi/functional/videohearing/input";

    @Before
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    public void testCreateVideoHearingWithEmptyPayload() {
        headersAsMap = createStandardHMIHeader("EMULATOR");
        videoHearingSteps.shouldRequestVideoHearingWithInvalidPayload(videohearingsRootContext,
                headersAsMap,
                authorizationToken, HttpMethod.POST,
                "{}");
    }

    @Test
    public void testAmendVideoHearingWithEmptyPayload() throws Exception {
        final String username = String.format("abc" + new Random().nextInt(999999));
        final String payloadForVideoHearing =
                String.format(readFileContents(VIDEO_HEARING_INPUT_PATH + "/POST-video-hearing-request.json"), username);

        //Make Post call for video hearing with username
        headersAsMap = createStandardHMIHeader("EMULATOR");
        videoHearingSteps.shouldCreateVideoHearing(videohearingsRootContext,
                headersAsMap,
                authorizationToken,
                payloadForVideoHearing);

        //Make Get call for video hearing with username as query param
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("username", username);

        final String hearingId = videoHearingSteps.performVideoHearingGetByUsername(videohearingsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);

        //Make Put call for amend video hearing with hearingId as path param
        videohearings_idRootContext = String.format(videohearings_idRootContext, hearingId);
        videoHearingSteps.shouldAmendVideoHearingWithInvalidPayload(videohearings_idRootContext,
                headersAsMap,
                authorizationToken, HttpMethod.PUT,
                "{}");
    }

    @Test
    public void testDeleteVideoHearing() throws Exception {

        log.debug("In the testSuccessfulPostVideoHearing() method");
        final String username = String.format("abc" + new Random().nextInt(999999));
        final String payloadForVideoHearing =
                String.format(readFileContents(VIDEO_HEARING_INPUT_PATH + "/POST-video-hearing-request.json"), username);

        //Make Post call for video hearing with username
        headersAsMap = createStandardHMIHeader("EMULATOR");
        videoHearingSteps.shouldCreateVideoHearing(videohearingsRootContext,
                headersAsMap,
                authorizationToken,
                payloadForVideoHearing);

        //Make Get call for video hearing with username as query param
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("username", username);

        final String hearingId = videoHearingSteps.performVideoHearingGetByUsername(videohearingsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);

        //Make Delete call for delete video hearing with hearingId as path param
        headersAsMap = createStandardHMIHeader("EMULATOR");
        videohearings_idRootContext = String.format(videohearings_idRootContext, hearingId);
        videoHearingSteps.shouldDeleteVideoHearing(videohearings_idRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }
}
