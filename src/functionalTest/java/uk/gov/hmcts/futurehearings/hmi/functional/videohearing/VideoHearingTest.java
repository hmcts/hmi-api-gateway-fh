package uk.gov.hmcts.futurehearings.hmi.functional.videohearing;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHmiHeader;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;

@Slf4j
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

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    public void testCreateVideoHearingWithEmptyPayload() {
        headersAsMap = createStandardHmiHeader("VH");
        callRestEndpointWithPayload(videoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.POST, HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testAmendVideoHearingWithEmptyPayload() {
        headersAsMap = createStandardHmiHeader("VH");
        videoHearingsIdRootContext = String.format(videoHearingsIdRootContext, new Random().nextInt(99999999));
        callRestEndpointWithPayload(videoHearingsIdRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.PUT, HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testDeleteVideoHearing() {
        headersAsMap = createStandardHmiHeader("VH");
        videoHearingsIdRootContext = String.format(videoHearingsIdRootContext, new Random().nextInt(99999999));
        callRestEndpointWithPayload(videoHearingsIdRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.DELETE, HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetVideoHearing() {
        headersAsMap = createStandardHmiHeader("VH");
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put("username", String.valueOf(new Random().nextInt(99999999)));
        callRestEndpointWithQueryParams(videoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
    }

    @Test
    public void testGetVideoHearingById() {
        headersAsMap = createStandardHmiHeader("VH");
        String hearingId = "4564616C-0F7E-4D70-B0D7-A1BBF0874D5D";
        videoHearingsIdRootContext = String.format(videoHearingsIdRootContext, hearingId);
        callRestEndpointWithPayload(videoHearingsIdRootContext,
                headersAsMap,
                authorizationToken,
                null, HttpMethod.GET, HttpStatus.OK);
    }

    @Test
    public void testPostParticipant() {
        headersAsMap = createStandardHmiHeader("VH");
        String hearingId = String.valueOf(new Random().nextInt(99999999));
        participantsRootContext = String.format(participantsRootContext, hearingId);
        callRestEndpointWithPayload(participantsRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.POST, HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPutParticipant() {
        headersAsMap = createStandardHmiHeader("VH");
        String hearingId = String.valueOf(new Random().nextInt(99999999));
        String participantId = String.valueOf(new Random().nextInt(99999999));
        participantsIdRootContext = String.format(participantsIdRootContext, hearingId, participantId);
        callRestEndpointWithPayload(participantsIdRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.PUT, HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testDeleteParticipant() {
        headersAsMap = createStandardHmiHeader("VH");
        String hearingId = String.valueOf(new Random().nextInt(99999999));
        String participantId = String.valueOf(new Random().nextInt(99999999));
        participantsIdRootContext = String.format(participantsIdRootContext, hearingId, participantId);
        callRestEndpointWithPayload(participantsIdRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.DELETE, HttpStatus.BAD_REQUEST);
    }
}
