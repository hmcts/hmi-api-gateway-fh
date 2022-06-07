package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import org.springframework.beans.factory.annotation.Value;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForRequestVideoHearingWithInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForRequestOrDelete;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HmiHttpClient;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("POST /hearings/{hearingId}/clone - Request clone")
@SuppressWarnings("java:S2699")
class POST_clone_UnitTests {

    private final Map<String, Object> headersAsMap = new HashMap<>();
    private final Map<String, String> paramsAsMap = new HashMap<>();

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${cloneApiRootContext}")
    private String cloneApiRootContext;

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
    @DisplayName("Test for Valid Headers")
    void testRetrieveCloneForValidHeaders() {
        final Response response = requestClone();
        thenValidateResponseForRequestVideoHearing(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for Invalid Headers")
    void testRetrieveCloneForInvalidHeaders() {
        headersAsMap.put("Source-System", "");
        final Response response = requestClone();
        thenValidateResponseForRequestVideoHearingWithInvalidHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for Invalid Token")
    void testRetrieveCloneForMissingToken() {
        final Response response = requestCloneNoAuth();
        thenValidateResponseForRequestVideoHearingWithInvalidToken(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for Invalid Date")
    void testRetrieveCloneForInvalidDate() {
        headersAsMap.put("Request-Created-At", "InvalidDate");
        final Response response = requestClone();
        thenValidateResponseForRequestVideoHearingWithInvalidHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for Invalid MediaType")
    void testRetrieveCloneForInvalidMediaType() {
        headersAsMap.put("Accept", "InvalidMedia");
        final Response response = requestClone();
        thenValidateResponseForRequestVideoHearingWithInvalidMedia(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for Invalid Content Type")
    void testRetrieveCloneForInvalidContentType() {
        headersAsMap.put("Content-Type", "InvalidMedia");
        final Response response = requestCloneNoPayload();
        thenValidateResponseForRequestVideoHearingWithInvalidHeader(response);
    }

    private Response requestCloneNoPayload() {
        return sendPostRequestForVideoHearing(cloneApiRootContext + "/clone", headersAsMap, targetInstance);
    }

    private Response requestCloneNoAuth() {
        return httpClient.httpPostNoAuth(cloneApiRootContext + "/clone", headersAsMap, paramsAsMap, "");
    }

    private Response requestClone() {
        return httpClient.httpPost(cloneApiRootContext + "/clone", headersAsMap, paramsAsMap, "");
    }

    private Response sendPostRequestForVideoHearing(final String api, final Map<String, Object> headersAsMap, final String basePath) {
        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

}