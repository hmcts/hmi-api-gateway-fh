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
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseforRetrieve;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForInvalidSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForAdditionalParam;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("GET /hearings - Retrieve Hearings")
@SuppressWarnings("java:S2699")
class GET_hearings_UnitTests {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetHost}")
    private String targetHost;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${hearingApiRootContext}")
    private String hearingApiRootContext;

    private final Map<String, Object> headersAsMap = new HashMap<>();
    private final Map<String, String> paramsAsMap = new HashMap<>();

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

        paramsAsMap.put("hearingIdCaseHQ", "CASE1234");
        paramsAsMap.put("hearingType", "THEFT");
        paramsAsMap.put("hearingDate", "2018-02-29T20:36:01Z");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testRetrieveHearingsRequestForInvalidResource() {

        final Response response = whenRetrieveHearingsRequestIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testRetrieveHearingsRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testRetrieveHearingsRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testRetrieveHearingsRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testRetrieveHearingsRequestWithInvalidAcceptHeader() {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
    void testRetrieveHearingsRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    void testRetrieveHearingsRequestWithInvalidOcpSubKey(){
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }


    @Test
    @Order(8)
    @DisplayName("Test for missing Source-System header")
    void testRetrieveHearingsRequestWithMissingSourceSystemHeader() {
        headersAsMap.remove("Source-System");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }

    @Test
    @Order(9)
    @DisplayName("Test for invalid Source-System header")
    void testRetrieveHearingsRequestWithInvalidSourceSystemHeader() {
        headersAsMap.remove("Source-System");
        headersAsMap.put("Source-System", "A");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }

    @Test
    @Order(10)
    @DisplayName("Test for missing Destination-System header")
    void testRetrieveHearingsRequestWithMissingDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }

    @Test
    @Order(11)
    @DisplayName("Test for invalid Destination-System header")
    void testRetrieveHearingsRequestWithInvalidDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");
        headersAsMap.put("Destination-System", "A");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }

    @Test
    @Order(12)
    @DisplayName("Test for missing Request-Created-At header")
    void testRetrieveHearingsRequestWithMissingRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }

    @Test
    @Order(13)
    @DisplayName("Test for invalid Request-Created-At header")
    void testRetrieveHearingsRequestWithInvalidRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");
        headersAsMap.put("Request-Created-At", "2018-01-29A20:36:01Z");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }

    @Test
    @Order(14)
    @DisplayName("Test for missing Request-Processed-At header")
    void testRetrieveHearingsRequestWithMissingRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }

    @Test
    @Order(15)
    @DisplayName("Test for invalid Request-Processed-At header")
    void testRetrieveHearingsRequestWithInvalidRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");
        headersAsMap.put("Request-Processed-At", "2018-02-29A20:36:01Z");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }

    @Test
    @Order(16)
    @DisplayName("Test for missing Request-Type header")
    void testRetrieveHearingsRequestWithMissingRequestTypeHeader() {
        headersAsMap.remove("Request-Type");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }

    @Test
    @Order(17)
    @DisplayName("Test for invalid Request-Type header")
    void testRetrieveHearingsRequestWithInvalidRequestTypeHeader() {
        headersAsMap.remove("Request-Type");
        headersAsMap.put("Request-Type", "A");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }

    @Test
    @Order(18)
    @DisplayName("Test for Invalid Parameter")
    void testRetrieveHearingScheduleRequestWithAdditionalParam() {
        paramsAsMap.put("Invalid-Param","Value");

        final Response response = whenRetrieveHearingScheduleIsInvokedWithAdditionalParam();
        thenValidateResponseForAdditionalParam(response);
    }

    @Test
    @Order(19)
    @DisplayName("Test for HearingID Parameter")
    void testRetrieveHearingsRequestWithHearingIDParam() {
        paramsAsMap.remove("hearingDate");
        paramsAsMap.remove("hearingType");

        final Response response = whenRetrieveHearingsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseforRetrieve(response);
    }

    @Test
    @Order(20)
    @DisplayName("Test for HearingDate Parameter")
    void testRetrieveHearingsRequestWithHearingDateParam() {
        paramsAsMap.remove("hearingIdCaseHQ");
        paramsAsMap.remove("hearingType");

        final Response response = whenRetrieveHearingsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseforRetrieve(response);
    }

    @Test
    @Order(21)
    @DisplayName("Test for HearingType Parameter")
    void testRetrieveHearingsRequestWithHearingTypeParam() {
        paramsAsMap.remove("hearingIdCaseHQ");
        paramsAsMap.remove("hearingDate");

        final Response response = whenRetrieveHearingsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseforRetrieve(response);
    }

    @Test
    @Order(22)
    @DisplayName("Test for Correct Headers with No Parameters")
    void testRetrieveHearingsRequestWithCorrectHeadersAndNoParams() {

        final Response response = whenRetrieveHearingsIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseforRetrieve(response);
    }

    @Test
    @Order(23)
    @DisplayName("Test for Correct Headers and Parameters")
    void testRetrieveHearingsRequestWithCorrectHeadersAndParams() {

        final Response response = whenRetrieveHearingsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseforRetrieve(response);
    }



    private Response whenRetrieveHearingScheduleIsInvokedWithAdditionalParam() {
        return retrieveHearingsResponseForCorrectHeadersAndParams(hearingApiRootContext, headersAsMap, paramsAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsRequestIsInvokedForInvalidResource() {
        return retrieveHearingsResponseForInvalidResource(hearingApiRootContext+"get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsIsInvokedWithCorrectHeadersAndParams() {
        return retrieveHearingsResponseForCorrectHeadersAndParams(hearingApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveHearingsResponseForCorrectHeadersAndNoParams(hearingApiRootContext, headersAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsRequestIsInvokedWithMissingOcpSubKey() {
        return retrieveHearingsResponseForMissingOrInvalidOcpSubKey(hearingApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader() {
        return retrieveHearingsResponseForMissingOrInvalidHeader(hearingApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response retrieveHearingsResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveHearingsResponseForCorrectHeadersAndParams(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }


    private Response retrieveHearingsResponseForCorrectHeadersAndNoParams(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveHearingsResponseForMissingOrInvalidOcpSubKey(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveHearingsResponseForMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap,final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();

    }
}
