package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInfo;
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
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForRequestOrDelete;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForInvalidSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;

import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.setupReport;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.endReport;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.startReport;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DELETE_resources_UnitTests {


    private static final String CORRECT_DELETE_REQUEST_PAYLOAD = "requests/correct-delete-hearings-payload.json";
    private static final String INCORRECT_DELETE_REQUEST_PAYLOAD = "requests/incorrect-delete-hearings-payload.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetHost}")
    private String targetHost;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${resourcesApiRootContext}")
    private String resourcesApiRootContext;

    private final Map<String, Object> headersAsMap = new HashMap<>();
    static ExtentTest objTestFromUtils, objStep;


    @BeforeAll
    public static void initialiseReport() {

        setupReport();
        objTestFromUtils =  startReport("Delete Resources Validations");

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
    public void testDeleteResourcesRequestForInvalidResource() throws IOException {
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedForInvalidResource(input);
        thenValidateResponseForInvalidResource(response, objStep);
    }

    @Test
    @Order(2)
    @DisplayName("Test for Correct Headers")
    public void testDeleteResourcesRequestWithCorrectHeaders() throws IOException {
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithCorrectHeaders(input);
        thenValidateResponseForRequestOrDelete(response, objStep);
    }

    @Test
    @Order(3)
    @DisplayName("Test for Correct Headers with Invalid Payload")
    public void testDeleteResourcesRequestWithCorrectHeadersAndInvalidPayload() throws IOException {
        final String input = givenAPayload(INCORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithCorrectHeaders(input);
        thenValidateResponseForRequestOrDelete(response, objStep);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    public void testDeleteResourcesRequestWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response, objStep);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    public void testDeleteResourcesRequestWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response, objStep);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing ContentType header")
    public void testDeleteResourcesRequestWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response, objStep);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid ContentType header")
    public void testDeleteResourcesRequestWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response, objStep);
    }


    @Test
    @Order(8)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
    public void testDeleteResourcesRequestWithMissingOcpSubKey() throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOcpSubKey(input);
        thenValidateResponseForMissingSubscriptionKeyHeader(response, objStep);
    }

    @Test
    @Order(9)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    public void testDeleteResourcesRequestWithInvalidOcpSubKey()throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOcpSubKey(input);
        thenValidateResponseForInvalidSubscriptionKeyHeader(response, objStep);
    }


    @Test
    @Order(10)
    @DisplayName("Test for missing Source-System header")
    public void testDeleteResourcesRequestWithMissingSourceSystemHeader() throws IOException {
        headersAsMap.remove("Source-System");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System", objStep);
    }

    @Test
    @Order(11)
    @DisplayName("Test for invalid Source-System header")
    public void testDeleteResourcesRequestWithInvalidSourceSystemHeader() throws IOException {
        headersAsMap.remove("Source-System");
        headersAsMap.put("Source-System", "A");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System", objStep);
    }

    @Test
    @Order(12)
    @DisplayName("Test for missing Destination-System header")
    public void testDeleteResourcesRequestWithMissingDestinationSystemHeader() throws IOException {
        headersAsMap.remove("Destination-System");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System", objStep);
    }

    @Test
    @Order(13)
    @DisplayName("Test for invalid Destination-System header")
    public void testDeleteResourcesRequestWithInvalidDestinationSystemHeader() throws IOException {
        headersAsMap.remove("Destination-System");
        headersAsMap.put("Destination-System", "A");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System", objStep);
    }

    @Test
    @Order(14)
    @DisplayName("Test for missing Request-Type header")
    public void testDeleteResourcesRequestWithMissingRequestTypeHeader() throws IOException {
        headersAsMap.remove("Request-Type");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type", objStep);
    }

    @Test
    @Order(15)
    @DisplayName("Test for invalid Request-Type header")
    public void testDeleteResourcesRequestWithInvalidRequestTypeHeader() throws IOException {
        headersAsMap.remove("Request-Type");
        headersAsMap.put("Request-Type", "A");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type", objStep);
    }

    @Test
    @Order(16)
    @DisplayName("Test for missing Request-Created-At header")
    public void testDeleteResourcesRequestWithMissingRequestCreatedAtHeader() throws IOException {
        headersAsMap.remove("Request-Created-At");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At", objStep);
    }

    @Test
    @Order(17)
    @DisplayName("Test for invalid Request-Created-At header")
    public void testDeleteResourcesRequestWithInvalidRequestCreatedAtHeader() throws IOException {
        headersAsMap.remove("Request-Created-At");
        headersAsMap.put("Request-Created-At", "2018-01-29A20:36:01Z");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At", objStep);
    }

    @Test
    @Order(18)
    @DisplayName("Test for missing Request-Processed-At header")
    public void testDeleteResourcesRequestWithMissingRequestProcessedAtHeader() throws IOException {
        headersAsMap.remove("Request-Processed-At");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At", objStep);
    }

    @Test
    @Order(19)
    @DisplayName("Test for invalid Request-Processed-At header")
    public void testDeleteResourcesRequestWithInvalidRequestProcessedAtHeader() throws IOException {
        headersAsMap.remove("Request-Processed-At");
        headersAsMap.put("Request-Processed-At", "2018-02-29A20:36:01Z");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = whenDeleteResourcesRequestIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At", objStep);
    }


    private Response whenDeleteResourcesRequestIsInvokedForInvalidResource(final String input) {
        return hearingResponseForInvalidResource(resourcesApiRootContext+"get", headersAsMap, targetInstance, input);
    }

    private Response whenDeleteResourcesRequestIsInvokedWithCorrectHeaders(final String input) {
        return hearingResponseForCorrectHeaders(resourcesApiRootContext+ "/resource123", headersAsMap, targetInstance, input);
    }

    private Response whenDeleteResourcesRequestIsInvokedWithMissingOcpSubKey(final String input) {
        return hearingResponseForMissingOcpSubKey(resourcesApiRootContext+ "/resource123", headersAsMap, targetInstance, input);
    }

    private Response whenDeleteResourcesRequestIsInvokedWithMissingOrInvalidHeader(final String input) {
        return hearingResponseForAMissingHeader(resourcesApiRootContext+ "/resource123", headersAsMap, targetInstance, input);
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
