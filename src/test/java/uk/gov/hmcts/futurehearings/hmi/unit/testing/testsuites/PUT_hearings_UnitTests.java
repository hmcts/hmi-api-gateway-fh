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
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForInvalidSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForUpdate;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("PUT /hearings - Update Hearings")
@SuppressWarnings("java:S2699")
class PUT_hearings_UnitTests {

    static final String CORRECT_UPDATE_HEARINGS_PAYLOAD = "requests/correct-update-hearings-payload.json";
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
        headersAsMap.put("Host", targetHost);
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", "S&L");
        headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
        headersAsMap.put("Request-Processed-At", "2018-02-29 20:36:01Z");
        headersAsMap.put("Request-Type", "THEFT");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testUpdateHearingsForInvalidResource() throws IOException {
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingsIsInvokedForInvalidResource(input);
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testUpdateHearingsWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }
    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testUpdateHearingsWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testUpdateHearingsWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testUpdateHearingsWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing OcpSubKey")
    void testUpdateHearingsRequestWithMissingOcpSubKey() throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingIsInvokedWithMissingOrInvalidOcSubKey(input);
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    void testUpdateHearingsRequestWithInvalidOcpSubKey()throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingIsInvokedWithMissingOrInvalidOcSubKey(input);
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }

    @Test
    @Order(8)
    @DisplayName("Test for missing Source-System")
    void testUpdateHearingsRequestWithMissingSrcHeader() throws IOException {
        headersAsMap.remove("Source-System");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }


    @Test
    @Order(9)
    @DisplayName("Test for invalid Source-System header")
    void testUpdateHearingsRequestWithInvalidSourceSystemHeader() throws IOException {
        headersAsMap.remove("Source-System");
        headersAsMap.put("Source-System", "A");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }

    @Test
    @Order(10)
    @DisplayName("Test for missing Destination-System")
    void testUpdateHearingsRequestWithMissingHeaderDestination() throws IOException {
        headersAsMap.remove("Destination-System");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }

    @Test
    @Order(11)
    @DisplayName("Test for invalid Destination-System header")
    void testUpdateHearingsRequestWithInvalidDestinationSystemHeader() throws IOException {
        headersAsMap.remove("Destination-System");
        headersAsMap.put("Destination-System", "A");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }


    @Test
    @Order(12)
    @DisplayName("Test for missing Request-Created-At")
    void testUpdateHearingsRequestWithMissingHeaderDateTime() throws IOException {
        headersAsMap.remove("Request-Created-At");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }

    @Test
    @Order(13)
    @DisplayName("Test for missing Request-Processed-At header")
    void testUpdateHearingsRequestWithMissingRequestCreatedAtHeader() throws IOException {
        headersAsMap.remove("Request-Processed-At");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }

    @Test
    @Order(14)
    @DisplayName("Test for invalid Request-Created-At header")
    void testUpdateHearingsRequestWithInvalidRequestCreatedAtHeader() throws IOException {
        headersAsMap.remove("Request-Created-At");
        headersAsMap.put("Request-Created-At", "2018-01-29A20:36:01Z");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }

    @Test
    @Order(15)
    @DisplayName("Test for invalid Request-Processed-At header")
    void testUpdateHearingsRequestWithInvalidRequestProcessedAtHeader() throws IOException {
        headersAsMap.remove("Request-Processed-At");
        headersAsMap.put("Request-Processed-At", "2018-02-29A20:36:01Z");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }


    @Test
    @Order(16)
    @DisplayName("Test for missing Request-Type")
    void testUpdateHearingsRequestWithMissingRequestTypeHeader() throws IOException {
        headersAsMap.remove("Request-Type");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }

    @Test
    @Order(17)
    @DisplayName("Test for invalid Request-Type header")
    void testUpdateHearingsRequestWithInvalidRequestTypeHeader() throws IOException {
        headersAsMap.remove("Request-Type");
        headersAsMap.put("Request-Type", "A");
        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingsIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }

    @Test
    @Order(18)
    @DisplayName("Test for correct Request")
    void testUpdateHearingsRequestWithCorrectRequest() throws IOException {

        final String input = givenAPayload(CORRECT_UPDATE_HEARINGS_PAYLOAD);
        final Response response = whenUpdateHearingIsInvokedWithCorrectRequest(input);
        thenValidateResponseForUpdate(response);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }

    private Response whenUpdateHearingIsInvokedWithCorrectRequest(final String input) {
        return updateHearingsResponseForCorrectRequest(hearingApiRootContext + "/CASE123432", headersAsMap, targetInstance, input);
    }

    private Response whenUpdateHearingsIsInvokedWithMissingOrInvalidHeader(final String input) {
        return updateHearingsResponseForAMissingOrInvalidHeader(hearingApiRootContext + "/CASE123432", headersAsMap, targetInstance, input);
    }

    private Response whenUpdateHearingIsInvokedWithMissingOrInvalidOcSubKey(final String input) {
        return updateHearingsResponseForAMissingOrInvalidOcpSubKey(hearingApiRootContext + "/CASE123432", headersAsMap, targetInstance, input);
    }

    private Response whenUpdateHearingsIsInvokedForInvalidResource(final String input) {
        return updateHearingsResponseForInvalidResource(hearingApiRootContext+"Put", headersAsMap, targetInstance, input);
    }

    private Response updateHearingsResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

    private Response updateHearingsResponseForCorrectRequest(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return   given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response updateHearingsResponseForAMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response updateHearingsResponseForAMissingOrInvalidOcpSubKey(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return  given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }


}
