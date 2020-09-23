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

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.endReport;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SchedulesResponseVerifier.*;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GET_schedules_UnitTests {

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

    static ExtentTest objTestFromUtils, objStep;


    @BeforeAll
    public static void initialiseReport() {

        setupReport();
        objTestFromUtils = startReport("RetrieveHearingSchedule Validations");

    }

    @AfterAll
    public static void finaliseReport() {

        endReport();
        objTestFromUtils=null;
        objStep=null;

    }

    public static ExtentTest reportStats() {

        return objTestFromUtils;
    }


    @BeforeEach
    public void initialiseValues(TestInfo info) {
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

        objStep = objTestFromUtils.createNode(info.getDisplayName());
    }


    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    public void testRetrieveHearingScheduleRequestForInvalidResource() {
        final Response response = whenRetrieveHearingScheduleIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response, objStep);
    }

    @Test
    @Order(11)
    @DisplayName("Test for No Parameters")
    public void testRetrieveHearingScheduleRequestWithCorrectRequestAndNoParams() {
        final Response response = whenRetrieveHearingScheduleIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseforRetrieve(response, objStep);
    }


    @Test
    @Order(12)
    @DisplayName("Test for All Parameters")
    public void testRetrieveHearingScheduleRequestWithCorrectRequestAndAllParams() {
        final Response response = whenRetrieveHearingScheduleIsInvokedWithCorrectHeadersAndAllParams();
        thenValidateResponseforRetrieve(response, objStep);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
    public void testRetrieveHearingScheduleRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final Response response = whenRetrieveHearingScheduleIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForMissingSubscriptionKeyHeader(response, objStep);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    public void testRetrieveHearingScheduleRequestWithInvalidOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final Response response = whenRetrieveHearingScheduleIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForInvalidSubscriptionKeyHeader(response, objStep);
    }


    @Test
    @DisplayName("Test for missing Source-System header")
    public void testRetrieveHearingScheduleRequestWithMissingSourceSystemHeader() {
        headersAsMap.remove("Source-System");
        final Response response = whenRetrieveHearingScheduleIsInvokedWithMissingHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System", objStep);
    }

    @Test
    @DisplayName("Test for missing Destination-System header")
    public void testRetrieveHearingScheduleRequestWithMissingDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");
        final Response response = whenRetrieveHearingScheduleIsInvokedWithMissingHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System", objStep);
    }

    @Test
    @DisplayName("Test for missing Request-Type header")
    public void testRetrieveHearingScheduleRequestWithMissingRequestTypeHeader() {
        headersAsMap.remove("Request-Type");
        final Response response = whenRetrieveHearingScheduleIsInvokedWithMissingHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type", objStep);
    }

    @Test
    @DisplayName("Test for missing Request-Created-At header")
    public void testRetrieveHearingScheduleRequestWithMissingRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");
        final Response response = whenRetrieveHearingScheduleIsInvokedWithMissingHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At", objStep);
    }

    @Test
    @DisplayName("Test for missing Request-Processed-At header")
    public void testRetrieveHearingScheduleRequestWithMissingRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");
        final Response response = whenRetrieveHearingScheduleIsInvokedWithMissingHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At", objStep);
    }

    @Test
    @DisplayName("Test for missing Accept header")
    public void testRetrieveHearingScheduleRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");
        final Response response = whenRetrieveHearingScheduleIsInvokedWithMissingHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response, objStep);
    }

    @Test
    @DisplayName("Test for missing ContentType header")
    public void testRetrieveHearingScheduleRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        final Response response = whenRetrieveHearingScheduleIsInvokedWithMissingHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response, objStep);
    }

    private Response whenRetrieveHearingScheduleIsInvokedForInvalidResource() {
        return retrieveHearingScheduleForInvalidResource(schedulesApiRootContext +"get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveHearingScheduleIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveHearingScheduleWithCorrectRequestAndNoParams(schedulesApiRootContext, headersAsMap, targetInstance);
    }

    private Response whenRetrieveHearingScheduleIsInvokedWithCorrectHeadersAndAllParams() {
        return retrieveHearingScheduleWithCorrectRequestAndAllParams(schedulesApiRootContext, headersAsMap, paramsAsMap, targetInstance);
    }

    private Response whenRetrieveHearingScheduleIsInvokedWithMissingOcpSubKey() {
        return retrieveHearingScheduleWithAMissingOcpSubKey(schedulesApiRootContext, headersAsMap, paramsAsMap, targetInstance);
    }

    private Response whenRetrieveHearingScheduleIsInvokedWithMissingHeader() {
        return retrieveHearingScheduleWithAMissingHeader(schedulesApiRootContext, headersAsMap, paramsAsMap, targetInstance);
    }


    private Response retrieveHearingScheduleForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveHearingScheduleWithCorrectRequestAndNoParams(final String api, final Map<String, Object> headersAsMap, final String basePath) {

          return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveHearingScheduleWithCorrectRequestAndAllParams(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {

         return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveHearingScheduleWithAMissingOcpSubKey(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap,final String basePath) {

         return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveHearingScheduleWithAMissingHeader(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap,final String basePath) {

             return given()
                    .queryParams(paramsAsMap)
                    .headers(headersAsMap)
                    .baseUri(basePath)
                    .basePath(api)
                    .when().get().then().extract().response();

    }

}
