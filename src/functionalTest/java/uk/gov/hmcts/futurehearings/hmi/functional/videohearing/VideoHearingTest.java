package uk.gov.hmcts.futurehearings.hmi.functional.videohearing;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.videohearing.steps.VideoHearingSteps;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHMIHeader;

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

    @Value("${participantsRootContext}")
    protected String participantsRootContext;

    @Value("${participants_idRootContext}")
    protected String participants_idRootContext;

    @Steps
    VideoHearingSteps videoHearingSteps;

    @Before
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    public void testCreateVideoHearingWithEmptyPayload() {
        headersAsMap = createStandardHMIHeader("VH");
        videoHearingSteps.shouldRequestVideoHearingWithInvalidPayload(videohearingsRootContext,
                headersAsMap,
                authorizationToken, HttpMethod.POST,
                "{}");
    }

    @Test
    public void testAmendVideoHearingWithEmptyPayload() {
        headersAsMap = createStandardHMIHeader("VH");
        videohearings_idRootContext = String.format(videohearings_idRootContext, new Random().nextInt(99999999));
        videoHearingSteps.shouldAmendVideoHearingWithInvalidPayload(videohearings_idRootContext,
                headersAsMap,
                authorizationToken, HttpMethod.PUT,
                "{}");
    }

    @Test
    public void testDeleteVideoHearing() {
        headersAsMap = createStandardHMIHeader("VH");
        videohearings_idRootContext = String.format(videohearings_idRootContext, new Random().nextInt(99999999));
        videoHearingSteps.shouldDeleteVideoHearing(videohearings_idRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }

    @Test
    public void testGetVideoHearing() {
        headersAsMap = createStandardHMIHeader("VH");
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("username", String.valueOf(new Random().nextInt(99999999)));

        videoHearingSteps.performVideoHearingGetByUsername(videohearingsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testGetVideoHearingById() {
        headersAsMap = createStandardHMIHeader("VH");
        String hearingId = "4564616C-0F7E-4D70-B0D7-A1BBF0874D5D";
        videohearings_idRootContext = String.format(videohearings_idRootContext, hearingId);
        videoHearingSteps.performVideoHearingGetByHearingId(videohearings_idRootContext,
                headersAsMap,
                authorizationToken);
    }

    @Test
    public void testPostParticipant() {
        headersAsMap = createStandardHMIHeader("VH");
        String hearingId = String.valueOf(new Random().nextInt(99999999));
        participantsRootContext = String.format(participantsRootContext, hearingId);
        videoHearingSteps.performPostParticipant(participantsRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }

    @Test
    public void testPutParticipant() {
        headersAsMap = createStandardHMIHeader("VH");
        String hearingId = String.valueOf(new Random().nextInt(99999999));
        String participantId = String.valueOf(new Random().nextInt(99999999));
        participants_idRootContext = String.format(participants_idRootContext, hearingId, participantId);
        videoHearingSteps.performPutParticipant(participants_idRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }

    @Test
    public void testDeleteParticipant() {
        headersAsMap = createStandardHMIHeader("VH");
        String hearingId = String.valueOf(new Random().nextInt(99999999));
        String participantId = String.valueOf(new Random().nextInt(99999999));
        participants_idRootContext = String.format(participants_idRootContext, hearingId, participantId);
        videoHearingSteps.performDeleteParticipant(participants_idRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }
}
