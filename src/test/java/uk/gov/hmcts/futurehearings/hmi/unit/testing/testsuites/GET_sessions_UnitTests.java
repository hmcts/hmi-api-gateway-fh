package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseforRetrieve;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForInvalidSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForAdditionalParam;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("GET /sessions - Retrieve Sessions")
public class GET_sessions_UnitTests {
    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetHost}")
    private String targetHost;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${sessionsApiRootContext}")
    private String sessionsApiRootContext;

    private final Map<String, Object> headersAsMap = new HashMap<>();
    private final Map<String, String> paramsAsMap = new HashMap<>();

    @BeforeEach
    public void initialiseValues() {

        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", "S&L");
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
        headersAsMap.put("Request-Processed-At", "2018-02-29 20:36:01Z");

        paramsAsMap.put("sessionIdCaseHQ", "CASE1234");
        paramsAsMap.put("sessionStartDate", "2018-02-29T20:36:01Z");
        paramsAsMap.put("sessionEndDate", "2018-02-29T21:36:01Z");
        paramsAsMap.put("caseCourt", "oxford");
        paramsAsMap.put("room-Name", "Room 7");

    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    public void testRetrieveSessionsRequestForInvalidResource() {

        final Response response = whenRetrieveSessionsRequestIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    public void testRetrieveSessionsRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    public void testRetrieveSessionsRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    public void testRetrieveSessionsRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    public void testRetrieveSessionsRequestWithInvalidAcceptHeader() {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
    public void testRetrieveSessionsRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    public void testRetrieveSessionsRequestWithInvalidOcpSubKey(){
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }


    @Test
    @Order(8)
    @DisplayName("Test for missing Source-System header")
    public void testRetrieveSessionsRequestWithMissingSourceSystemHeader() {
        headersAsMap.remove("Source-System");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }

    @Test
    @Order(9)
    @DisplayName("Test for invalid Source-System header")
    public void testRetrieveSessionsRequestWithInvalidSourceSystemHeader() {
        headersAsMap.remove("Source-System");
        headersAsMap.put("Source-System", "A");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }

    @Test
    @Order(10)
    @DisplayName("Test for missing Destination-System header")
    public void testRetrieveSessionsRequestWithMissingDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }

    @Test
    @Order(11)
    @DisplayName("Test for invalid Destination-System header")
    public void testRetrieveSessionsRequestWithInvalidDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");
        headersAsMap.put("Destination-System", "A");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }

    @Test
    @Order(12)
    @DisplayName("Test for missing Request-Created-At header")
    public void testRetrieveSessionsRequestWithMissingRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }

    @Test
    @Order(13)
    @DisplayName("Test for invalid Request-Created-At header")
    public void testRetrieveSessionsRequestWithInvalidRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");
        headersAsMap.put("Request-Created-At", "2018-01-29A20:36:01Z");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }

    @Test
    @Order(14)
    @DisplayName("Test for missing Request-Processed-At header")
    public void testRetrieveSessionsRequestWithMissingRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }

    @Test
    @Order(15)
    @DisplayName("Test for invalid Request-Processed-At header")
    public void testRetrieveSessionsRequestWithInvalidRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");
        headersAsMap.put("Request-Processed-At", "2018-02-29A20:36:01Z");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }

    @Test
    @Order(16)
    @DisplayName("Test for missing Request-Type header")
    public void testRetrieveSessionsRequestWithMissingRequestTypeHeader() {
        headersAsMap.remove("Request-Type");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }

    @Test
    @Order(17)
    @DisplayName("Test for invalid Request-Type header")
    public void testRetrieveSessionsRequestWithInvalidRequestTypeHeader() {
        headersAsMap.remove("Request-Type");
        headersAsMap.put("Request-Type", "A");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }

    @Test
    @Order(18)
    @DisplayName("Test for Invalid Parameter")
    public void testRetrieveSessionsRequestWithAdditionalParam() {
        paramsAsMap.put("Invalid-Param","Value");

        final Response response = whenRetrieveSessionsIsInvokedWithAdditionalParam();
        thenValidateResponseForAdditionalParam(response);
    }

    @Test
    @Order(19)
    @DisplayName("Test for HearingID Parameter")
    public void testRetrieveSessionsRequestWithHearingIDParam() {
        paramsAsMap.remove("hearingDate");
        paramsAsMap.remove("hearingType");

        final Response response = whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseforRetrieve(response);
    }

    @Test
    @Order(20)
    @DisplayName("Test for HearingDate Parameter")
    public void testRetrieveSessionsRequestWithHearingDateParam() {
        paramsAsMap.remove("hearingIdCaseHQ");
        paramsAsMap.remove("hearingType");

        final Response response = whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseforRetrieve(response);
    }

    @Test
    @Order(21)
    @DisplayName("Test for HearingType Parameter")
    public void testRetrieveSessionsRequestWithHearingTypeParam() {
        paramsAsMap.remove("hearingIdCaseHQ");
        paramsAsMap.remove("hearingDate");

        final Response response = whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseforRetrieve(response);
    }

    @Test
    @Order(22)
    @DisplayName("Test for Correct Headers with No Parameters")
    public void testRetrieveSessionsRequestWithCorrectHeadersAndNoParams() {

        final Response response = whenRetrieveSessionsIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseforRetrieve(response);
    }

    @Test
    @Order(23)
    @DisplayName("Test for Correct Headers and Parameters")
    public void testRetrieveSessionsRequestWithCorrectHeadersAndParams() {

        final Response response = whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseforRetrieve(response);
    }



    private Response whenRetrieveSessionsIsInvokedWithAdditionalParam() {
        return retrieveSessionsResponseForCorrectHeadersAndParams(sessionsApiRootContext, headersAsMap, paramsAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsRequestIsInvokedForInvalidResource() {
        return retrieveSessionsResponseForInvalidResource(sessionsApiRootContext+"get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams() {
        return retrieveSessionsResponseForCorrectHeadersAndParams(sessionsApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveSessionsResponseForCorrectHeadersAndNoParams(sessionsApiRootContext, headersAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsRequestIsInvokedWithMissingOcpSubKey() {
        return retrieveSessionsResponseForMissingOrInvalidOcpSubKey(sessionsApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader() {
        return retrieveSessionsResponseForMissingOrInvalidHeader(sessionsApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response retrieveSessionsResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveSessionsResponseForCorrectHeadersAndParams(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }


    private Response retrieveSessionsResponseForCorrectHeadersAndNoParams(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveSessionsResponseForMissingOrInvalidOcpSubKey(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveSessionsResponseForMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap,final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();

    }
}
