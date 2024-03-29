package uk.gov.hmcts.futurehearings.hmi.functional.videohearing;

import io.restassured.response.Response;
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

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.TestingUtils.getHearingId;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.TestingUtils.readFileContents;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHmiHeader;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings({"java:S2699", "PMD.LawOfDemeter"})
class VideoHearingTest extends FunctionalTest {

    @Value("${videohearingsRootContext}")
    protected String videoHearingsRootContext;

    @Value("${videohearings_idRootContext}")
    protected String videoHearingsIdRootContext;

    @Value("${participantsRootContext}")
    protected String participantsRootContext;

    @Value("${participants_idRootContext}")
    protected String participantsIdRootContext;

    @Value("${targetInstance}")
    protected String targetInstance;

    static final String CREATE_VH_LISTINGS_PAYLOAD = "requests/create-vh-listings-payload.json";

    private final Random rand;

    public VideoHearingTest() throws NoSuchAlgorithmException {
        super();
        rand = SecureRandom.getInstanceStrong();
    }

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    void testCreateVideoHearingWithEmptyPayload() {
        headersAsMap = createStandardHmiHeader("VH");
        callRestEndpointWithPayload(videoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.POST, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testAmendVideoHearingWithEmptyPayload() {
        headersAsMap = createStandardHmiHeader("VH");
        videoHearingsIdRootContext = String.format(videoHearingsIdRootContext, rand.nextInt(99999999));
        callRestEndpointWithPayload(videoHearingsIdRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.PUT, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testDeleteVideoHearing() {
        headersAsMap = createStandardHmiHeader("VH");
        videoHearingsIdRootContext = String.format(videoHearingsIdRootContext, rand.nextInt(99999999));
        callRestEndpointWithPayload(videoHearingsIdRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.DELETE, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testGetVideoHearing() {
        headersAsMap = createStandardHmiHeader("VH");
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put("username", String.valueOf(rand.nextInt(99999999)));
        callRestEndpointWithQueryParams(videoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
    }

    @Test
    void testGetVideoHearingById() throws IOException {
        headersAsMap = createStandardHmiHeader("SNL", "VH");
        Response response = callRestEndpointWithPayload(videoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                readFileContents(CREATE_VH_LISTINGS_PAYLOAD),
                HttpMethod.POST, HttpStatus.CREATED);

        String validHearingId = getHearingId(response);
        videoHearingsIdRootContext = String.format(videoHearingsIdRootContext, validHearingId);
        callRestEndpointWithPayload(videoHearingsIdRootContext,
                headersAsMap,
                authorizationToken,
                null, HttpMethod.GET, HttpStatus.OK);
    }

    @Test
     void testPostParticipant() {
        headersAsMap = createStandardHmiHeader("VH");
        String hearingId = String.valueOf(rand.nextInt(99999999));
        participantsRootContext = String.format(participantsRootContext, hearingId);
        callRestEndpointWithPayload(participantsRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.POST, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPutParticipant() {
        headersAsMap = createStandardHmiHeader("VH");
        String hearingId = String.valueOf(rand.nextInt(99999999));
        String participantId = String.valueOf(rand.nextInt(99999999));
        participantsIdRootContext = String.format(participantsIdRootContext, hearingId, participantId);
        callRestEndpointWithPayload(participantsIdRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.PUT, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testDeleteParticipant() {
        headersAsMap = createStandardHmiHeader("VH");
        String hearingId = String.valueOf(rand.nextInt(99999999));
        String participantId = String.valueOf(rand.nextInt(99999999));
        participantsIdRootContext = String.format(participantsIdRootContext, hearingId, participantId);
        callRestEndpointWithPayload(participantsIdRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.DELETE, HttpStatus.BAD_REQUEST);
    }
}
