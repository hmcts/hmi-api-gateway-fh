package uk.gov.hmcts.futurehearings.hmi.functional.videohearing;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.TestingUtils.readFileContents;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHMIHeader;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.videohearing.steps.VideoHearingSteps;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
    public void testCreateVideoHearing() throws Exception {

        log.debug("In the testSuccessfulPostVideoHearing() method");
        final String username = String.format("abc" + new Random().nextInt(999999));
        final String payloadForVideoHearing =
                String.format(readFileContents(VIDEO_HEARING_INPUT_PATH + "/POST-video-hearing-request.json"), username);

        //Make Post call for video hearing with username
        headersAsMap = createStandardHMIHeader("MOCK");
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

        //Make Get call for video hearing with hearingId as path param
        videohearings_idRootContext = String.format(videohearings_idRootContext, hearingId);
        videoHearingSteps.shouldFetchVideoHearingByHearingId(videohearings_idRootContext, hearingId,
                headersAsMap,
                authorizationToken,
                null);
    }

    @Disabled("Disabling this test as functional test is pointing to MOCK which supports any format and return success")
    public void testCreateVideoHearingWithEmptyPayload() throws Exception {

        log.debug("In the testCreateVideoHearingWithEmptyPayload() method");
        //Make Post call for video hearing with username
        headersAsMap = createStandardHMIHeader("MOCK");
        videoHearingSteps.shouldRequestVideoHearingWithInvalidPayload(videohearingsRootContext,
                headersAsMap,
                authorizationToken, HttpMethod.POST,
                "{}");
    }

    @Disabled("Disabling this test as functional test is pointing to MOCK which supports any format and return success")
    public void testCreateVideoHearingWithXmlPayload() throws Exception {

        log.debug("In the testCreateVideoHearingWithXmlPayload() method");
        //Make Post call for video hearing with username
        headersAsMap = createStandardHMIHeader("MOCK");
        videoHearingSteps.shouldRequestVideoHearingWithInvalidPayload(videohearingsRootContext,
                headersAsMap,
                authorizationToken, HttpMethod.POST,
                "<xml><test></xml>");
    }

    @Test
    public void testAmendVideoHearing() throws Exception {

        log.debug("In the testSuccessfulPostVideoHearing() method");
        final String username = String.format("abc" + new Random().nextInt(999999));
        final String payloadForVideoHearing =
                String.format(readFileContents(VIDEO_HEARING_INPUT_PATH + "/POST-video-hearing-request.json"), username);

        //Make Post call for video hearing with username
        headersAsMap = createStandardHMIHeader("MOCK");
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
        final String payloadForPutVideoHearing =
                String.format(readFileContents(VIDEO_HEARING_INPUT_PATH + "/PUT-video-hearing-request.json"), username);
        videohearings_idRootContext = String.format(videohearings_idRootContext, hearingId);
        videoHearingSteps.shouldAmendVideoHearing(videohearings_idRootContext,
                headersAsMap,
                authorizationToken,
                payloadForPutVideoHearing);

        //Make Get call for video hearing with hearingId as path param
        videohearings_idRootContext = String.format(videohearings_idRootContext, hearingId);
        videoHearingSteps.shouldFetchVideoHearingByHearingId(videohearings_idRootContext, hearingId,
                headersAsMap,
                authorizationToken,
                null);
    }

    @Disabled("Disabling this test as functional test is pointing to MOCK which supports any format and return success")
    public void testAmendVideoHearingWithEmptyPayload() throws Exception {

        log.debug("In the testSuccessfulPostVideoHearing() method");
        final String username = String.format("abc" + new Random().nextInt(999999));
        final String payloadForVideoHearing =
                String.format(readFileContents(VIDEO_HEARING_INPUT_PATH + "/POST-video-hearing-request.json"), username);

        //Make Post call for video hearing with username
        headersAsMap = createStandardHMIHeader("MOCK");
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
        videoHearingSteps.shouldRequestVideoHearingWithInvalidPayload(videohearings_idRootContext,
                headersAsMap,
                authorizationToken, HttpMethod.PUT,
                "{}");
    }

    @Disabled("Disabling this test as functional test is pointing to MOCK which supports any format and return success")
    public void testAmendVideoHearingWithXmlPayload() throws Exception {

        log.debug("In the testSuccessfulPostVideoHearing() method");
        final String username = String.format("abc" + new Random().nextInt(999999));
        final String payloadForVideoHearing =
                String.format(readFileContents(VIDEO_HEARING_INPUT_PATH + "/POST-video-hearing-request.json"), username);

        //Make Post call for video hearing with username
        headersAsMap = createStandardHMIHeader("MOCK");
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
        videoHearingSteps.shouldRequestVideoHearingWithInvalidPayload(videohearings_idRootContext,
                headersAsMap,
                authorizationToken, HttpMethod.PUT,
                "<xml><test></xml>");
    }

    @Test
    public void testDeleteVideoHearing() throws Exception {

        log.debug("In the testSuccessfulPostVideoHearing() method");
        final String username = String.format("abc" + new Random().nextInt(999999));
        final String payloadForVideoHearing =
                String.format(readFileContents(VIDEO_HEARING_INPUT_PATH + "/POST-video-hearing-request.json"), username);

        //Make Post call for video hearing with username
        headersAsMap = createStandardHMIHeader("MOCK");
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
        headersAsMap = createStandardHMIHeader("MOCK");
        videohearings_idRootContext = String.format(videohearings_idRootContext, hearingId);
        videoHearingSteps.shouldDeleteVideoHearing(videohearings_idRootContext,
                headersAsMap,
                authorizationToken,
                "{}");

        //Verify - Cannot verify as Mock return values for Get
        //TODO: Needs to add verify test once mapped to video hearing
        
    }

}
