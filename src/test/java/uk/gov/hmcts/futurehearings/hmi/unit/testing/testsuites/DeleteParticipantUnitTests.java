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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ParticipantResponseVerifier.thenValidateResponseForAddParticipantWithInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ParticipantResponseVerifier.thenValidateResponseForAddParticipantWithInvalidToken;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ParticipantResponseVerifier.thenValidateResponseForUpdateParticipant;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("DELETE /{hearingId}/participants/{participantId} - Delete Participant")
public class DeleteParticipantUnitTests {

    private final Map<String, Object> headersAsMap = new HashMap<>();
    private final Map<String, String> paramsAsMap = new HashMap<>();

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

    @Value("${participants_idRootContext}")
    private String participantsIdRootContext;

    private static String accessToken;
    private HmiHttpClient httpClient;

    private String participantIdCtx;
    private String hearingId = String.valueOf(new Random().nextInt(99999999));
    private String participantId = String.valueOf(new Random().nextInt(99999999));

    @BeforeAll
    void setToken() {
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenUrl, scope);
        this.httpClient = new HmiHttpClient(accessToken, targetInstance);
        participantIdCtx = String.format(participantsIdRootContext, hearingId, participantId);
    }

    @BeforeEach
    void initialiseValues() {
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");
    }

    @Test
    @Order(1)
    @DisplayName("Test Delete Participant for Valid Headers")
    void testDeleteParticipantForValidHeaders() {
        final Response response = invokeAddParticipant();
        thenValidateResponseForUpdateParticipant(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test Delete Participant for Invalid Headers")
    void testDeleteParticipantForInvalidHeaders() {
        headersAsMap.put("Source-System", "");
        final Response response = invokeAddParticipant();
        thenValidateResponseForAddParticipantWithInvalidHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test Delete Participant for Invalid Token")
    void testDeleteParticipantForMissingToken() {
        final Response response = invokeAddParticipantNoAuth();
        thenValidateResponseForAddParticipantWithInvalidToken(response);
    }

    private Response invokeAddParticipant() {
        return httpClient.httpDelete(participantIdCtx, headersAsMap, paramsAsMap, "");
    }

    private Response invokeAddParticipantNoAuth() {
        return httpClient.httpDeleteNoAuth(participantIdCtx, headersAsMap, paramsAsMap, "");
    }

}
