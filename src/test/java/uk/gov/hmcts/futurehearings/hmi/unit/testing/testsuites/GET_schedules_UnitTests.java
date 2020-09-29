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

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SchedulesResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SchedulesResponseVerifier.thenValidateResponseForRetrieve;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SchedulesResponseVerifier.thenValidateResponseForMissingSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SchedulesResponseVerifier.thenValidateResponseForInvalidSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SchedulesResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SchedulesResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SchedulesResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("GET /schedules - Retrieve Hearing Schedules")
class GET_schedules_UnitTests {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetHost}")
    private String targetHost;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${schedulesApiRootContext}")
    private String schedulesApiRootContext;

    private final Map<String, Object> headersAsMap = new HashMap<>();
    private final Map<String, String> paramsAsMap = new HashMap<>();

    @BeforeEach
    void initialiseValues() {
        headersAsMap.put("Host", targetHost);
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", "S&L");
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
        headersAsMap.put("Request-Processed-At", "2018-02-29 20:36:01Z");
        headersAsMap.put("Accept", "application/json");

        paramsAsMap.put("hearing_date", "2018-02-29T20:36:01Z");
        paramsAsMap.put("hearing_venue_id", "venueid");
        paramsAsMap.put("hearing_room_id", "roomid");
        paramsAsMap.put("hearing_session_id_casehq", "sessionid");
        paramsAsMap.put("hearing_case_id_hmcts", "caseid");
        paramsAsMap.put("hearing_id_casehq", "hearingid");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testRetrieveHearingSchedulesRequestForInvalidResource() {
        final Response response = whenRetrieveHearingScheduleIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testRetrieveHearingSchedulesRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testRetrieveHearingSchedulesRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");

        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testRetrieveHearingSchedulesRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");
        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testDeleteHearingRequestWithInvalidAcceptHeader() {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/xml");

        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
    void testRetrieveHearingSchedulesRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final Response response = whenRetrieveHearingScheduleIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    void testRetrieveHearingSchedulesRequestWithInvalidOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final Response response = whenRetrieveHearingScheduleIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }

    @Test
    @Order(8)
    @DisplayName("Test for missing Source-System header")
    void testRetrieveHearingSchedulesRequestWithMissingSourceSystemHeader() {
        headersAsMap.remove("Source-System");
        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }

    @Test
    @Order(9)
    @DisplayName("Test for invalid Source-System header")
    void testRetrieveHearingSchedulesRequestWithInvalidSourceSystemHeader() {
        headersAsMap.remove("Source-System");
        headersAsMap.put("Source-System", "A");

        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }

    @Test
    @Order(10)
    @DisplayName("Test for missing Destination-System header")
    void testRetrieveHearingSchedulesRequestWithMissingDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");
        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }

    @Test
    @Order(11)
    @DisplayName("Test for invalid Destination-System header")
    void testRetrieveHearingSchedulesRequestWithInvalidDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");
        headersAsMap.put("Destination-System", "A");

        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }

    @Test
    @Order(12)
    @DisplayName("Test for missing Request-Created-At header")
    void testRetrieveHearingSchedulesRequestWithMissingRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");
        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }

    @Test
    @Order(13)
    @DisplayName("Test for invalid Request-Processed-At header")
    void testRetrieveHearingSchedulesRequestWithInvalidRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");
        headersAsMap.put("Request-Processed-At", "2018-02-29A20:36:01Z");

        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }


    @Test
    @Order(14)
    @DisplayName("Test for missing Request-Processed-At header")
    void testRetrieveHearingSchedulesRequestWithMissingRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");
        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }


    @Test
    @Order(15)
    @DisplayName("Test for invalid Request-Created-At header")
    void testRetrieveHearingSchedulesRequestWithInvalidRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");
        headersAsMap.put("Request-Created-At", "2018-01-29A20:36:01Z");

        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }

    @Test
    @Order(16)
    @DisplayName("Test for missing Request-Type header")
    void testRetrieveHearingSchedulesRequestWithMissingRequestTypeHeader() {
        headersAsMap.remove("Request-Type");
        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }


    @Test
    @Order(17)
    @DisplayName("Test for invalid Request-Type header")
    void testRetrieveHearingSchedulesRequestWithInvalidRequestTypeHeader() {
        headersAsMap.remove("Request-Type");
        headersAsMap.put("Request-Type", "A");

        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }

    @Test
    @Order(18)
    @DisplayName("Test for Correct Headers and No Parameters")
    void testRetrieveHearingSchedulesRequestWithCorrectRequestAndNoParams() {
        final Response response = whenRetrieveHearingScheduleIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(19)
    @DisplayName("Test for Correct Headers and Parameters")
    void testRetrieveHearingSchedulesRequestWithCorrectRequestAndAllParams() {
        final Response response = whenRetrieveHearingScheduleIsInvokedWithCorrectHeadersAndAllParams();
        thenValidateResponseForRetrieve(response);
    }

    private Response whenRetrieveHearingScheduleIsInvokedForInvalidResource() {
        return retrieveHearingSchedulesResponseForInvalidResource(schedulesApiRootContext +"get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveHearingScheduleIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveHearingSchedulesResponseForCorrectRequestAndNoParams(schedulesApiRootContext, headersAsMap, targetInstance);
    }

    private Response whenRetrieveHearingScheduleIsInvokedWithCorrectHeadersAndAllParams() {
        return retrieveHearingSchedulesResponseForCorrectRequestAndAllParams(schedulesApiRootContext, headersAsMap, paramsAsMap, targetInstance);
    }

    private Response whenRetrieveHearingScheduleIsInvokedWithMissingOcpSubKey() {
        return retrieveHearingSchedulesResponseForMissingOrInvalidOcpSubKey(schedulesApiRootContext, headersAsMap, paramsAsMap, targetInstance);
    }

    private Response whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader() {
        return retrieveHearingSchedulesResponseForMissingOrInvalidHeader(schedulesApiRootContext, headersAsMap, paramsAsMap, targetInstance);
    }


    private Response retrieveHearingSchedulesResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath) {

         return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveHearingSchedulesResponseForCorrectRequestAndNoParams(final String api, final Map<String, Object> headersAsMap, final String basePath) {

          return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveHearingSchedulesResponseForCorrectRequestAndAllParams(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {

         return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveHearingSchedulesResponseForMissingOrInvalidOcpSubKey(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap,final String basePath) {

         return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveHearingSchedulesResponseForMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap,final String basePath) {

             return given()
                    .queryParams(paramsAsMap)
                    .headers(headersAsMap)
                    .baseUri(basePath)
                    .basePath(api)
                    .when().get().then().extract().response();

    }

}
