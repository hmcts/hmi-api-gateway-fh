package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForInvalidSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForUpdate;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer;
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
@DisplayName("PUT /sessions - Update Sessions")
@SuppressWarnings("java:S2699")
class PUT_sessions_UnitTests {

    static final String CORRECT_UPDATE_SESSIONS_PAYLOAD = "requests/update-sessions-payload.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${sessionsApiRootContext}")
    private String sessionsApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

    private final Map<String, Object> headersAsMap = new HashMap<>();

    @BeforeEach
    void initialiseValues() {

        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
        headersAsMap.put("Request-Processed-At", "2018-02-29 20:36:01Z");
        headersAsMap.put("Request-Type", "THEFT");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testUpdateSessionsForInvalidResource() throws IOException {
        final String input = givenAPayload(CORRECT_UPDATE_SESSIONS_PAYLOAD);
        final Response response = whenUpdateSessionsIsInvokedForInvalidResource(input);
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testUpdateSessionsWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(CORRECT_UPDATE_SESSIONS_PAYLOAD);
        final Response response = whenUpdateSessionsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }
    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testUpdateSessionsWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final String input = givenAPayload(CORRECT_UPDATE_SESSIONS_PAYLOAD);
        final Response response = whenUpdateSessionsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testUpdateSessionsWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(CORRECT_UPDATE_SESSIONS_PAYLOAD);
        final Response response = whenUpdateSessionsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testUpdateSessionsWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final String input = givenAPayload(CORRECT_UPDATE_SESSIONS_PAYLOAD);
        final Response response = whenUpdateSessionsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing OcpSubKey")
    void testUpdateSessionsRequestWithMissingOcpSubKey() throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final String input = givenAPayload(CORRECT_UPDATE_SESSIONS_PAYLOAD);
        final Response response = whenUpdateHearingIsInvokedWithMissingOrInvalidOcSubKey(input);
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    void testUpdateSessionsRequestWithInvalidOcpSubKey()throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final String input = givenAPayload(CORRECT_UPDATE_SESSIONS_PAYLOAD);
        final Response response = whenUpdateHearingIsInvokedWithMissingOrInvalidOcSubKey(input);
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }

    @Order(8)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At","Request-Type"})
    void testUpdateSessionsWithMissingHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        final String input = givenAPayload(CORRECT_UPDATE_SESSIONS_PAYLOAD);
        final Response response = whenUpdateSessionsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(9)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At","Request-Type"})
    void testUpdateSessionsWithInvalidHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");
        final String input = givenAPayload(CORRECT_UPDATE_SESSIONS_PAYLOAD);
        final Response response = whenUpdateSessionsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(10)
    @DisplayName("Test for correct Request")
    void testUpdateSessionsRequestWithCorrectRequest() throws IOException {

        final String input = givenAPayload(CORRECT_UPDATE_SESSIONS_PAYLOAD);
        final Response response = whenUpdateHearingIsInvokedWithCorrectRequest(input);
        thenValidateResponseForUpdate(response);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }

    private Response whenUpdateHearingIsInvokedWithCorrectRequest(final String input) {
        return updateSessionsResponseForCorrectRequest(sessionsApiRootContext + "/CASE1234", headersAsMap, targetInstance, input);
    }

    private Response whenUpdateSessionsIsInvokedWithMissingOrInvalidHeader(final String input) {
        return updateSessionsResponseForAMissingOrInvalidHeader(sessionsApiRootContext + "/CASE1234", headersAsMap, targetInstance, input);
    }

    private Response whenUpdateHearingIsInvokedWithMissingOrInvalidOcSubKey(final String input) {
        return updateSessionsResponseForAMissingOrInvalidOcpSubKey(sessionsApiRootContext + "/CASE1234", headersAsMap, targetInstance, input);
    }

    private Response whenUpdateSessionsIsInvokedForInvalidResource(final String input) {
        return updateSessionsResponseForInvalidResource(sessionsApiRootContext+"/CASE1234/"+"put", headersAsMap, targetInstance, input);
    }

    private Response updateSessionsResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

    private Response updateSessionsResponseForCorrectRequest(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return   given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response updateSessionsResponseForAMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response updateSessionsResponseForAMissingOrInvalidOcpSubKey(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return  given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }
}
