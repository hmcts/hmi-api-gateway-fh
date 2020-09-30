package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForRequestOrDelete;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForInvalidSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(TestReporter.class)
@DisplayName("DELETE /hearings - Delete Hearings ")
class DELETE_hearings_UnitTests {

    private static final String CORRECT_DELETE_REQUEST_PAYLOAD = "requests/correct-delete-request-payload.json";
    private static final String INCORRECT_DELETE_REQUEST_PAYLOAD = "requests/incorrect-delete-request-payload.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetHost}")
    private String targetHost;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${hearingApiRootContext}")
    private String hearingApiRootContext;

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
    void testDeleteHearingRequestForInvalidResource() throws IOException {
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedForInvalidResource(input);
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testDeleteHearingRequestWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testDeleteHearingRequestWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testDeleteHearingRequestWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testDeleteHearingRequestWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
    void testDeleteHearingRequestWithMissingOcpSubKey() throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOcpSubKey(input);
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    void testDeleteHearingRequestWithInvalidOcpSubKey()throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOcpSubKey(input);
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }


    @Test
    @Order(8)
    @DisplayName("Test for missing Source-System header")
    void testDeleteHearingRequestWithMissingSourceSystemHeader() throws IOException {
        headersAsMap.remove("Source-System");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }

    @Test
    @Order(9)
    @DisplayName("Test for invalid Source-System header")
    void testDeleteHearingRequestWithInvalidSourceSystemHeader() throws IOException {
        headersAsMap.remove("Source-System");
        headersAsMap.put("Source-System", "A");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }

    @Test
    @Order(10)
    @DisplayName("Test for missing Destination-System header")
    void testDeleteHearingRequestWithMissingDestinationSystemHeader() throws IOException {
        headersAsMap.remove("Destination-System");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }

    @Test
    @Order(11)
    @DisplayName("Test for invalid Destination-System header")
    void testDeleteHearingRequestWithInvalidDestinationSystemHeader() throws IOException {
        headersAsMap.remove("Destination-System");
        headersAsMap.put("Destination-System", "A");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }

    @Test
    @Order(12)
    @DisplayName("Test for missing Request-Created-At header")
    void testDeleteHearingRequestWithMissingRequestCreatedAtHeader() throws IOException {
        headersAsMap.remove("Request-Created-At");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }

    @Test
    @Order(13)
    @DisplayName("Test for invalid Request-Created-At header")
    void testDeleteHearingRequestWithInvalidRequestCreatedAtHeader() throws IOException {
        headersAsMap.remove("Request-Created-At");
        headersAsMap.put("Request-Created-At", "2018-01-29A20:36:01Z");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }

    @Test
    @Order(14)
    @DisplayName("Test for missing Request-Processed-At header")
    void testDeleteHearingRequestWithMissingRequestProcessedAtHeader() throws IOException {
        headersAsMap.remove("Request-Processed-At");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }

    @Test
    @Order(15)
    @DisplayName("Test for invalid Request-Processed-At header")
    void testDeleteHearingRequestWithInvalidRequestProcessedAtHeader() throws IOException {
        headersAsMap.remove("Request-Processed-At");
        headersAsMap.put("Request-Processed-At", "2018-02-29A20:36:01Z");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }

    @Test
    @Order(16)
    @DisplayName("Test for missing Request-Type header")
    void testDeleteHearingRequestWithMissingRequestTypeHeader() throws IOException {
        headersAsMap.remove("Request-Type");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }

    @Test
    @Order(17)
    @DisplayName("Test for invalid Request-Type header")
    void testDeleteHearingRequestWithInvalidRequestTypeHeader() throws IOException {
        headersAsMap.remove("Request-Type");
        headersAsMap.put("Request-Type", "A");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }

    @Test
    @Order(18)
    @DisplayName("Test for Correct Headers with Invalid Payload")
    void testDeleteHearingRequestWithCorrectHeadersAndInvalidPayload() throws IOException {
        final String input = givenAPayload(INCORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithCorrectHeaders(input);
        thenValidateResponseForRequestOrDelete(response);
    }

    @Test
    @Order(19)
    @DisplayName("Test for Correct Headers and Payload")
    void testDeleteHearingRequestWithCorrectHeaders() throws IOException {
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithCorrectHeaders(input);
        thenValidateResponseForRequestOrDelete(response);
    }

    private Response whenDeleteHearingRequestIsInvokedForInvalidResource(final String input) {
        return deleteHearingsResponseForInvalidResource(hearingApiRootContext+"delete", headersAsMap, targetInstance, input);
    }

    private Response whenDeleteHearingRequestIsInvokedWithCorrectHeaders(final String input) {
        return deleteHearingsResponseForCorrectHeaders(hearingApiRootContext, headersAsMap, targetInstance, input);
    }

    private Response whenDeleteHearingRequestIsInvokedWithMissingOcpSubKey(final String input) {
        return deleteHearingsResponseForMissingOrInvalidOcpSubKey(hearingApiRootContext, headersAsMap, targetInstance, input);
    }

    private Response whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(final String input) {
        return deleteHearingsResponseForMissingOrInvalidHeader(hearingApiRootContext, headersAsMap, targetInstance, input);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }

    private Response deleteHearingsResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }

    private Response deleteHearingsResponseForCorrectHeaders(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }

    private Response deleteHearingsResponseForMissingOrInvalidOcpSubKey(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }

    private Response deleteHearingsResponseForMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap,final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();

    }
}
