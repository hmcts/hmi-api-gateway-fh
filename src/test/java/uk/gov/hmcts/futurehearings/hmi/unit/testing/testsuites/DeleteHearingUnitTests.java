package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
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
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResponseVerifier.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResponseVerifier.thenValidateHearingResponseForMissingHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeleteHearingUnitTests {


    private static final String CORRECT_DELETE_HEARINGS_PAYLOAD = "requests/correct-delete-hearings-payload.json";
    private static final String INCORRECT_DELETE_HEARINGS_PAYLOAD = "requests/incorrect-delete-hearings-payload.json";

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
        objTestFromUtils =  startReport("Delete Hearing Validations");

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
    public void testDeleteHearingRequestForInvalidResource() throws IOException {
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedForInvalidResource(input);
        thenValidateHearingResponseForInvalidResource(response, objStep);
    }

    @Test
    @Order(2)
    @DisplayName("Test for Correct Headers")
    public void testDeleteHearingRequestWithCorrectHeaders() throws IOException {
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithCorrectHeaders(input);
        thenASuccessfulResponseForHearingRequestIsReturned(response, objStep);
    }

    @Test
    @Order(3)
    @DisplayName("Test for Correct Headers with Invalid Payload")
    public void testDeleteHearingRequestWithCorrectHeadersAndInvalidPayload() throws IOException {
        final String input = givenAPayload(INCORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithCorrectHeaders(input);
        thenASuccessfulResponseForHearingRequestIsReturned(response, objStep);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    public void testDeleteHearingRequestWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateHearingResponseForMissingAcceptHeader(response, objStep);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    public void testDeleteHearingRequestWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateHearingResponseForMissingAcceptHeader(response, objStep);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing ContentType header")
    public void testDeleteHearingRequestWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateHearingResponseForMissingContentTypeHeader(response, objStep);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid ContentType header")
    public void testDeleteHearingRequestWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateHearingResponseForMissingContentTypeHeader(response, objStep);
    }


    @Test
    @Order(8)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
    public void testDeleteHearingRequestWithMissingOcpSubKey() throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOcpSubKey(input);
        thenResponseForMissingHeaderOcpSubscriptionIsReturned(response, objStep);
    }

    @Test
    @Order(9)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    public void testDeleteHearingRequestWithInvalidOcpSubKey()throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOcpSubKey(input);
        thenResponseForInvalidOcpSubscriptionIsReturned(response, objStep);
    }


    @Test
    @Order(10)
    @DisplayName("Test for missing Source-System header")
    public void testDeleteHearingRequestWithMissingSourceSystemHeader() throws IOException {
        headersAsMap.remove("Source-System");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateHearingResponseForMissingHeader(response, "Source-System", objStep);
    }

    @Test
    @Order(11)
    @DisplayName("Test for invalid Source-System header")
    public void testDeleteHearingRequestWithInvalidSourceSystemHeader() throws IOException {
        headersAsMap.remove("Source-System");
        headersAsMap.put("Source-System", "A");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateHearingResponseForMissingHeader(response, "Source-System", objStep);
    }

    @Test
    @Order(12)
    @DisplayName("Test for missing Destination-System header")
    public void testDeleteHearingRequestWithMissingDestinationSystemHeader() throws IOException {
        headersAsMap.remove("Destination-System");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateHearingResponseForMissingHeader(response, "Destination-System", objStep);
    }

    @Test
    @Order(13)
    @DisplayName("Test for invalid Destination-System header")
    public void testDeleteHearingRequestWithInvalidDestinationSystemHeader() throws IOException {
        headersAsMap.remove("Destination-System");
        headersAsMap.put("Destination-System", "A");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateHearingResponseForMissingHeader(response, "Destination-System", objStep);
    }

    @Test
    @Order(14)
    @DisplayName("Test for missing Request-Type header")
    public void testDeleteHearingRequestWithMissingRequestTypeHeader() throws IOException {
        headersAsMap.remove("Request-Type");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateHearingResponseForMissingHeader(response, "Request-Type", objStep);
    }

    @Test
    @Order(15)
    @DisplayName("Test for invalid Request-Type header")
    public void testDeleteHearingRequestWithInvalidRequestTypeHeader() throws IOException {
        headersAsMap.remove("Request-Type");
        headersAsMap.put("Request-Type", "A");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateHearingResponseForMissingHeader(response, "Request-Type", objStep);
    }

    @Test
    @Order(16)
    @DisplayName("Test for missing Request-Created-At header")
    public void testDeleteHearingRequestWithMissingRequestCreatedAtHeader() throws IOException {
        headersAsMap.remove("Request-Created-At");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateHearingResponseForMissingHeader(response, "Request-Created-At", objStep);
    }

    @Test
    @Order(17)
    @DisplayName("Test for invalid Request-Created-At header")
    public void testDeleteHearingRequestWithInvalidRequestCreatedAtHeader() throws IOException {
        headersAsMap.remove("Request-Created-At");
        headersAsMap.put("Request-Created-At", "2018-01-29A20:36:01Z");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateHearingResponseForInvalidHeader(response, "Request-Created-At", objStep);
    }

    @Test
    @Order(18)
    @DisplayName("Test for missing Request-Processed-At header")
    public void testDeleteHearingRequestWithMissingRequestProcessedAtHeader() throws IOException {
        headersAsMap.remove("Request-Processed-At");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateHearingResponseForMissingHeader(response, "Request-Processed-At", objStep);
    }

    @Test
    @Order(19)
    @DisplayName("Test for invalid Request-Processed-At header")
    public void testDeleteHearingRequestWithInvalidRequestProcessedAtHeader() throws IOException {
        headersAsMap.remove("Request-Processed-At");
        headersAsMap.put("Request-Processed-At", "2018-02-29A20:36:01Z");
        final String input = givenAPayload(CORRECT_DELETE_HEARINGS_PAYLOAD);
        final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateHearingResponseForInvalidHeader(response, "Request-Processed-At", objStep);
    }


    private Response whenDeleteHearingRequestIsInvokedForInvalidResource(final String input) {
        return hearingResponseForInvalidResource(hearingApiRootContext+"get", headersAsMap, targetInstance, input);
    }

    private Response whenDeleteHearingRequestIsInvokedWithCorrectHeaders(final String input) {
        return hearingResponseForCorrectHeaders(hearingApiRootContext, headersAsMap, targetInstance, input);
    }

    private Response whenDeleteHearingRequestIsInvokedWithMissingOcpSubKey(final String input) {
        return hearingResponseForMissingOcpSubKey(hearingApiRootContext, headersAsMap, targetInstance, input);
    }

    private Response whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(final String input) {
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
                .when().delete().then().extract().response();
    }

    private Response hearingResponseForCorrectHeaders(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }

    private Response hearingResponseForMissingOcpSubKey(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }

    private Response hearingResponseForAMissingHeader(final String api, final Map<String, Object> headersAsMap,final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();

    }
}
