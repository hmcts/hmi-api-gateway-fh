package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForInvalidSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForRequestOrDelete;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("POST /sessions - Request Sessions")
@SuppressWarnings("java:S2699")
class POST_sessions_UnitTests {

    private static final String CORRECT_CREATE_SESSIONS_PAYLOAD = "requests/correct-create-sessions-payload.json";
    private static final String INCORRECT_CREATE_SESSIONS_PAYLOAD = "requests/incorrect-create-sessions-payload.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${sessionsApiRootContext}")
    private String sessionsApiRootContext;

    private final Map<String, Object> headersAsMap = new HashMap<>();

    @BeforeEach
    void initialiseValues() {
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", "S&L");
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
        headersAsMap.put("Request-Processed-At", "2018-02-29 20:36:01Z");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testRequestSessionsForInvalidResource() throws IOException {
        final String input = givenAPayload(CORRECT_CREATE_SESSIONS_PAYLOAD);
        final Response response = whenRequestSessionsIsInvokedForInvalidResource(input);
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testRequestSessionsWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(CORRECT_CREATE_SESSIONS_PAYLOAD);
        final Response response = whenRequestSessionsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }
    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testRequestSessionsWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final String input = givenAPayload(CORRECT_CREATE_SESSIONS_PAYLOAD);
        final Response response = whenRequestSessionsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testRequestSessionsWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(CORRECT_CREATE_SESSIONS_PAYLOAD);
        final Response response = whenRequestSessionsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testRequestSessionsWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final String input = givenAPayload(CORRECT_CREATE_SESSIONS_PAYLOAD);
        final Response response = whenRequestSessionsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
    void testRequestSessionsWithMissingOcpSubKey() throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final String input = givenAPayload(CORRECT_CREATE_SESSIONS_PAYLOAD);
        final Response response = whenRequestSessionsIsInvokedWithMissingOrInvalidOcpSubKey(input);
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    void testRequestSessionsWithInvalidOcpSubKey()throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final String input = givenAPayload(CORRECT_CREATE_SESSIONS_PAYLOAD);
        final Response response = whenRequestSessionsIsInvokedWithMissingOrInvalidOcpSubKey(input);
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }

    @Order(8)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At","Request-Type"})
    void testRequestSessionsWithMissingHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        final String input = givenAPayload(CORRECT_CREATE_SESSIONS_PAYLOAD);
        final Response response = whenRequestSessionsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(9)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At","Request-Type"})
    void testRequestSessionsWithInvalidHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");
        final String input = givenAPayload(CORRECT_CREATE_SESSIONS_PAYLOAD);
        final Response response = whenRequestSessionsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(18)
    @DisplayName("Test for Correct Headers with Invalid Payload")
    void testRequestSessionsWithCorrectHeadersAndInvalidPayload() throws IOException {
        final String input = givenAPayload(INCORRECT_CREATE_SESSIONS_PAYLOAD);
        final Response response = whenRequestSessionsIsInvokedWithCorrectHeaders(input);
        thenValidateResponseForRequestOrDelete(response);
    }

    @Test
    @Order(19)
    @DisplayName("Test for Correct Headers")
    void testRequestSessionsWithCorrectHeaders() throws IOException {
        final String input = givenAPayload(CORRECT_CREATE_SESSIONS_PAYLOAD);
        final Response response = whenRequestSessionsIsInvokedWithCorrectHeaders(input);
        thenValidateResponseForRequestOrDelete(response);
    }

    private Response whenRequestSessionsIsInvokedForInvalidResource(final String input) {
        return requestSessionsResponseForInvalidResource(sessionsApiRootContext+"post", headersAsMap, targetInstance, input);
    }

    private Response whenRequestSessionsIsInvokedWithCorrectHeaders(final String input) {
        return requestSessionsResponseForCorrectHeaders(sessionsApiRootContext, headersAsMap, targetInstance, input);
    }

    private Response whenRequestSessionsIsInvokedWithMissingOrInvalidOcpSubKey(final String input) {
        return requestSessionsResponseForMissingOcpSubKey(sessionsApiRootContext, headersAsMap, targetInstance, input);
    }

    private Response whenRequestSessionsIsInvokedWithMissingOrInvalidHeader(final String input) {
        return requestSessionsResponseForMissingOrInvalidHeader(sessionsApiRootContext, headersAsMap, targetInstance, input);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }

    private Response requestSessionsResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

    private Response requestSessionsResponseForCorrectHeaders(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

    private Response requestSessionsResponseForMissingOcpSubKey(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

    private Response requestSessionsResponseForMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap,final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();

    }
}