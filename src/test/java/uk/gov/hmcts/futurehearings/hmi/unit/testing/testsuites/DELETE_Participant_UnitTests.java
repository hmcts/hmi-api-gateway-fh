package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;

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

import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ParticipantResponseVerifier.*;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("DELETE /{hearingId}/participants/{participantId} - Delete Participant")
public class DELETE_Participant_UnitTests {

    private final Map<String, Object> headersAsMap = new HashMap<>();
    private final Map<String, String> paramsAsMap = new HashMap<>();

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${hmiApiRootContext}")
    private String hmiApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

    @Value("${tokenURL}")
    private String tokenURL;

    @Value("${clientID}")
    private String clientID;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${scope}")
    private String scope;

    @Value("${grantType}")
    private String grantType;

    private static String accessToken;

    @Value("${invalidTokenURL}")
    private String invalidTokenURL;

    @Value("${invalidScope}")
    private String invalidScope;

    @Value("${invalidClientID}")
    private String invalidClientID;

    @Value("${invalidClientSecret}")
    private String invalidClientSecret;

    private HmiHttpClient httpClient;

    @BeforeAll
    void setToken(){
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenURL, scope);
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
        return httpClient.httpDelete("/hmi/HID123456/participants/PID123456", headersAsMap, paramsAsMap, "");
    }

    private Response invokeAddParticipantNoAuth() {
        return httpClient.httpDeleteNoAuth("/hmi/HID123456/participants/PID123456", headersAsMap, paramsAsMap, "");
    }

}
