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

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHmiHeader;

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
    protected String videoHearingsRootContext;

    @Value("${videohearings_idRootContext}")
    protected String videoHearingsIdRootContext;

    @Value("${participantsRootContext}")
    protected String participantsRootContext;

    @Value("${participants_idRootContext}")
    protected String participantsIdRootContext;

    @Steps
    VideoHearingSteps videoHearingSteps;

    private final Random rand;

    public VideoHearingTest() throws NoSuchAlgorithmException {
        super();
        rand = SecureRandom.getInstanceStrong();
    }

    @Before
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    public void testCreateVideoHearingWithEmptyPayload() {
        headersAsMap = createStandardHmiHeader("VH");
        videoHearingSteps.shouldRequestVideoHearingWithInvalidPayload(videoHearingsRootContext,
                headersAsMap,
                authorizationToken, HttpMethod.POST,
                "{}");
    }

    @Test
    public void testAmendVideoHearingWithEmptyPayload() {
        headersAsMap = createStandardHmiHeader("VH");
        videoHearingsIdRootContext = String.format(videoHearingsIdRootContext, rand.nextInt(99999999));
        videoHearingSteps.shouldAmendVideoHearingWithInvalidPayload(videoHearingsIdRootContext,
                headersAsMap,
                authorizationToken, HttpMethod.PUT,
                "{}");
    }

    @Test
    public void testDeleteVideoHearing() {
        headersAsMap = createStandardHmiHeader("VH");
        videoHearingsIdRootContext = String.format(videoHearingsIdRootContext, rand.nextInt(99999999));
        videoHearingSteps.shouldDeleteVideoHearing(videoHearingsIdRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }

    @Test
    public void testGetVideoHearing() {
        headersAsMap = createStandardHmiHeader("VH");
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put("username", String.valueOf(rand.nextInt(99999999)));

        videoHearingSteps.performVideoHearingGetByUsername(videoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testGetVideoHearingById() {
        headersAsMap = createStandardHmiHeader("VH");
        String hearingId = "4564616C-0F7E-4D70-B0D7-A1BBF0874D5D";
        videoHearingsIdRootContext = String.format(videoHearingsIdRootContext, hearingId);
        videoHearingSteps.performVideoHearingGetByHearingId(videoHearingsIdRootContext,
                headersAsMap,
                authorizationToken);
    }

    @Test
    public void testPostParticipant() {
        headersAsMap = createStandardHmiHeader("VH");
        String hearingId = String.valueOf(rand.nextInt(99999999));
        participantsRootContext = String.format(participantsRootContext, hearingId);
        videoHearingSteps.performPostParticipant(participantsRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }

    @Test
    public void testPutParticipant() {
        headersAsMap = createStandardHmiHeader("VH");
        String hearingId = String.valueOf(rand.nextInt(99999999));
        String participantId = String.valueOf(rand.nextInt(99999999));
        participantsIdRootContext = String.format(participantsIdRootContext, hearingId, participantId);
        videoHearingSteps.performPutParticipant(participantsIdRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }

    @Test
    public void testDeleteParticipant() {
        headersAsMap = createStandardHmiHeader("VH");
        String hearingId = String.valueOf(rand.nextInt(99999999));
        String participantId = String.valueOf(rand.nextInt(99999999));
        participantsIdRootContext = String.format(participantsIdRootContext, hearingId, participantId);
        videoHearingSteps.performDeleteParticipant(participantsIdRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }
}
