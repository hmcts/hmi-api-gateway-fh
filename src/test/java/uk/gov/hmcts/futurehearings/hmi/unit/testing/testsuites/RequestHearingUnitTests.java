package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResponseVerifier.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.*;

import com.aventstack.extentreports.ExtentTest;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
//@Disabled("Disabled as test is validating against the payload")
public class RequestHearingUnitTests {

    private static final String PAYLOAD_WITH_ALL_FIELDS = "requests/correct-hearing-request-payload.json";
    private static final String CASE_ID_MISSING_REQ_PATH = "requests/case-id-missing-request.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetHost}")
    private String targetHost;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${hearingApiRootContext}")
    private String hearingApiRootContext;

    private final Map<String, Object> headersAsMap = new HashMap<>();
    static ExtentTest objTestFromUtils, objStep;


    @BeforeAll
    public static void initialiseReport() {

        setupReport();
        objTestFromUtils =  startReport("RequestHearing Validations");

    }

    @AfterAll
    public static void finaliseReport() {

        endReport();
        objTestFromUtils=null;
        objStep=null;

    }

    @BeforeEach
    public void initialiseValues(TestInfo info) {

        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", "S&L");
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
        headersAsMap.put("Request-Processed-At", "2018-02-29 20:36:01Z");

        objStep = objTestFromUtils.createNode(info.getDisplayName());
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    public void testHearingRequestForInvalidResource() throws IOException {
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = whenHearingRequestIsInvokedForInvalidResource(input);
        thenValidateHearingResponseForInvalidResource(response, objStep);
    }

    @Test
    @Order(2)
    @DisplayName("Test for Correct Headers")
    public void testHearingRequestWithCorrectHeaders() throws IOException {
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = whenHearingRequestIsInvokedWithCorrectHeaders(input);
        thenASuccessfulResponseForHearingRequestIsReturned(response, objStep);
    }

    @Test
    @Order(3)
    @DisplayName("Test for Correct Headers with Invalid Payload")
    public void testHearingRequestWithCorrectHeadersAndInvalidPayload() throws IOException {
        final String input = givenAPayload(CASE_ID_MISSING_REQ_PATH);
        final Response response = whenHearingRequestIsInvokedWithCorrectHeaders(input);
        thenASuccessfulResponseForHearingRequestIsReturned(response, objStep);
    }

    @Test
    @DisplayName("Test for missing Accept header")
    public void testHearingRequestWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = whenHearingRequestIsInvokedWithMissingHeader(input);
        thenValidateHearingResponseForMissingAcceptHeader(response, objStep);
    }

    @Test
    @DisplayName("Test for missing ContentType header")
    public void testHearingRequestWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = whenHearingRequestIsInvokedWithMissingHeader(input);
        thenValidateHearingResponseForMissingContentTypeHeader(response, objStep);
    }


    @Test
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
    public void testHearingRequestWithMissingOcpSubKey() throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = whenHearingRequestIsInvokedWithMissingOcpSubKey(input);
        thenResponseForMissingHeaderOcpSubscriptionIsReturned(response, objStep);
    }

    @Test
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    public void testHearingRequestWithInvalidOcpSubKey()throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = whenHearingRequestIsInvokedWithMissingOcpSubKey(input);
        thenResponseForInvalidOcpSubscriptionIsReturned(response, objStep);
    }


    @Test
    @DisplayName("Test for missing Source-System header")
    public void testHearingRequestWithMissingSourceSystemHeader() throws IOException {
        headersAsMap.remove("Source-System");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = whenHearingRequestIsInvokedWithMissingHeader(input);
        thenValidateHearingResponseForMissingHeader(response, "Source-System", objStep);
    }

    @Test
    @DisplayName("Test for missing Destination-System header")
    public void testHearingRequestWithMissingDestinationSystemHeader() throws IOException {
        headersAsMap.remove("Destination-System");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = whenHearingRequestIsInvokedWithMissingHeader(input);
        thenValidateHearingResponseForMissingHeader(response, "Destination-System", objStep);
    }

    @Test
    @DisplayName("Test for missing Request-Type header")
    public void testHearingRequestWithMissingRequestTypeHeader() throws IOException {
        headersAsMap.remove("Request-Type");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = whenHearingRequestIsInvokedWithMissingHeader(input);
        thenValidateHearingResponseForMissingHeader(response, "Request-Type", objStep);
    }

    @Test
    @DisplayName("Test for missing Request-Created-At header")
    public void testHearingRequestWithMissingRequestCreatedAtHeader() throws IOException {
        headersAsMap.remove("Request-Created-At");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = whenHearingRequestIsInvokedWithMissingHeader(input);
        thenValidateHearingResponseForMissingHeader(response, "Request-Created-At", objStep);
    }

    @Test
    @DisplayName("Test for missing Request-Processed-At header")
    public void testHearingRequestWithMissingRequestProcessedAtHeader() throws IOException {
        headersAsMap.remove("Request-Processed-At");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = whenHearingRequestIsInvokedWithMissingHeader(input);
        thenValidateHearingResponseForMissingHeader(response, "Request-Processed-At", objStep);
    }


    private Response whenHearingRequestIsInvokedForInvalidResource(final String input) {
        return hearingResponseForInvalidResource(hearingApiRootContext+"get", headersAsMap, targetInstance, input);
    }

    private Response whenHearingRequestIsInvokedWithCorrectHeaders(final String input) {
        return hearingResponseForCorrectHeaders(hearingApiRootContext, headersAsMap, targetInstance, input);
    }

    private Response whenHearingRequestIsInvokedWithMissingOcpSubKey(final String input) {
        return hearingResponseForMissingOcpSubKey(hearingApiRootContext, headersAsMap, targetInstance, input);
    }

    private Response whenHearingRequestIsInvokedWithMissingHeader(final String input) {
        return hearingResponseForAMissingHeader(hearingApiRootContext, headersAsMap, targetInstance, input);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }

    private Response hearingResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

    private Response hearingResponseForCorrectHeaders(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

    private Response hearingResponseForMissingOcpSubKey(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

    private Response hearingResponseForAMissingHeader(final String api, final Map<String, Object> headersAsMap,final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();

    }
}