package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HmiHttpClient;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.JudicialOfficeHoldersVerifier.thenValidateResponseForAddJoh;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.JudicialOfficeHoldersVerifier.thenValidateResponseForAddJohWithInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.JudicialOfficeHoldersVerifier.thenValidateResponseForAddJohWithInvalidToken;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("POST /hearings/{hearingId}/joh - Add Judicial Office Holders")
class PostAddVideoHearingsJohUnitTests {

    private final Map<String, Object> headersAsMap = new ConcurrentHashMap<>();
    private final Map<String, String> paramsAsMap = new ConcurrentHashMap<>();

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${destinationSystem}")
    private String destinationSystem;

    @Value("${tokenURL}")
    private String tokenUrl;

    @Value("${clientID}")
    private String clientID;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${scope}")
    private String scope;

    @Value("${grantType}")
    private String grantType;

    @Value("${johVideoHearingRootContext}")
    private String johVideoHearingRootContext;

    private static String accessToken;
    private HmiHttpClient httpClient;
    private String participantCtx;
    private final String hearingId = String.valueOf(new Random().nextInt(99999999));

    @BeforeAll
    void setToken() {
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenUrl, scope);
        this.httpClient = new HmiHttpClient(accessToken, targetInstance);
    }

    @BeforeEach
    void initialiseValues() {
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");
        participantCtx = String.format(johVideoHearingRootContext, hearingId);
    }

    @Test
    @Order(1)
    @DisplayName("Test for Valid Headers")
    void testAddVideoHearingJohForValidHeaders() {
        final Response response = invokeAddParticipant();
        thenValidateResponseForAddJoh(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for Invalid Headers")
    void testAddVideoHearingJohForInvalidHeaders() {
        headersAsMap.put("Source-System", "");
        final Response response = invokeAddParticipant();
        thenValidateResponseForAddJohWithInvalidHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for Invalid Token")
    void testAddVideoHearingJohForMissingToken() {
        final Response response = invokeAddParticipantNoAuth();
        thenValidateResponseForAddJohWithInvalidToken(response);
    }

    private Response invokeAddParticipant() {
        return httpClient.httpPost(participantCtx, headersAsMap, paramsAsMap, "");
    }

    private Response invokeAddParticipantNoAuth() {
        return httpClient.httpPostNoAuth(participantCtx, headersAsMap, paramsAsMap, "");
    }
}
