package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SchedulesResponseVerifier.thenValidateResponseForInvalidSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SchedulesResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SchedulesResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SchedulesResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SchedulesResponseVerifier.thenValidateResponseForMissingSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SchedulesResponseVerifier.thenValidateResponseForRetrieve;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SchedulesResponseVerifier.thenValidateResponseForInvalidResource;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import uk.gov.hmcts.futurehearings.hmi.Application;

import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("GET /schedules - Retrieve Hearing Schedules")
@SuppressWarnings("java:S2699")
class GET_schedules_UnitTests {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${schedulesApiRootContext}")
    private String schedulesApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

    private final Map<String, Object> headersAsMap = new HashMap<>();
    private final Map<String, String> paramsAsMap = new HashMap<>();

    @BeforeEach
    void initialiseValues() {

        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
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

    @Order(8)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At","Request-Type"})
    void testRetrieveHearingSchedulesRequestWithMissingHeader(String iteration) {
        headersAsMap.remove(iteration);

        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(9)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At","Request-Type"})
    void testRetrieveHearingSchedulesRequestWithInvalidHeader(String iteration) {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");

        final Response response = whenRetrieveHearingSchedulesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
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
