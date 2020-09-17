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
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResponseVerifier.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.*;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class RetrieveHearingsUnitTests {

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
    static ExtentTest objTestFromUtils, objStep;


    @BeforeAll
    public static void initialiseReport() {

        setupReport();
        objTestFromUtils =  startReport("Retrieve Hearings Validations");

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

        paramsAsMap.put("hearingIdCaseHQ", "CASE1234");
        paramsAsMap.put("hearingType", "THEFT");
        paramsAsMap.put("hearingDate", "2018-02-29T20:36:01Z");

        objStep = objTestFromUtils.createNode(info.getDisplayName());
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    public void testRetrieveHearingsRequestForInvalidResource() {

        final Response response = whenRetrieveHearingsRequestIsInvokedForInvalidResource();
        thenValidateHearingResponseForInvalidResource(response, objStep);
    }


    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    public void testRetrieveHearingsRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateHearingResponseForMissingAcceptHeader(response, objStep);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    public void testRetrieveHearingsRequestWithInvalidAcceptHeader() {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateHearingResponseForMissingAcceptHeader(response, objStep);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing ContentType header")
    public void testRetrieveHearingsRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateHearingResponseForMissingContentTypeHeader(response, objStep);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid ContentType header")
    public void testRetrieveHearingsRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateHearingResponseForMissingContentTypeHeader(response, objStep);
    }


    @Test
    @Order(8)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
    public void testRetrieveHearingsRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOcpSubKey();
        thenResponseForMissingHeaderOcpSubscriptionIsReturned(response, objStep);
    }

    @Test
    @Order(9)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    public void testRetrieveHearingsRequestWithInvalidOcpSubKey(){
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOcpSubKey();
        thenResponseForInvalidOcpSubscriptionIsReturned(response, objStep);
    }


    @Test
    @Order(10)
    @DisplayName("Test for missing Source-System header")
    public void testRetrieveHearingsRequestWithMissingSourceSystemHeader() {
        headersAsMap.remove("Source-System");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateHearingResponseForMissingHeader(response, "Source-System", objStep);
    }

    @Test
    @Order(11)
    @DisplayName("Test for invalid Source-System header")
    public void testRetrieveHearingsRequestWithInvalidSourceSystemHeader() {
        headersAsMap.remove("Source-System");
        headersAsMap.put("Source-System", "A");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateHearingResponseForMissingHeader(response, "Source-System", objStep);
    }

    @Test
    @Order(12)
    @DisplayName("Test for missing Destination-System header")
    public void testRetrieveHearingsRequestWithMissingDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateHearingResponseForMissingHeader(response, "Destination-System", objStep);
    }

    @Test
    @Order(13)
    @DisplayName("Test for invalid Destination-System header")
    public void testRetrieveHearingsRequestWithInvalidDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");
        headersAsMap.put("Destination-System", "A");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateHearingResponseForMissingHeader(response, "Destination-System", objStep);
    }

    @Test
    @Order(14)
    @DisplayName("Test for missing Request-Type header")
    public void testRetrieveHearingsRequestWithMissingRequestTypeHeader() {
        headersAsMap.remove("Request-Type");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateHearingResponseForMissingHeader(response, "Request-Type", objStep);
    }

    @Test
    @Order(15)
    @DisplayName("Test for invalid Request-Type header")
    public void testRetrieveHearingsRequestWithInvalidRequestTypeHeader() {
        headersAsMap.remove("Request-Type");
        headersAsMap.put("Request-Type", "A");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateHearingResponseForMissingHeader(response, "Request-Type", objStep);
    }

    @Test
    @Order(16)
    @DisplayName("Test for missing Request-Created-At header")
    public void testRetrieveHearingsRequestWithMissingRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateHearingResponseForMissingHeader(response, "Request-Created-At", objStep);
    }

    //@Test
    @Order(17)
    @DisplayName("Test for invalid Request-Created-At header")
    public void testRetrieveHearingsRequestWithInvalidRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");
        headersAsMap.put("Request-Created-At", "2018-01-29A20:36:01Z");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateHearingResponseForMissingHeader(response, "Request-Created-At", objStep);
    }

    @Test
    @Order(18)
    @DisplayName("Test for missing Request-Processed-At header")
    public void testRetrieveHearingsRequestWithMissingRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateHearingResponseForMissingHeader(response, "Request-Processed-At", objStep);
    }

    //@Test
    @Order(19)
    @DisplayName("Test for invalid Request-Processed-At header")
    public void testRetrieveHearingsRequestWithInvalidRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");
        headersAsMap.put("Request-Processed-At", "2018-02-29A20:36:01Z");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateHearingResponseForMissingHeader(response, "Request-Processed-At", objStep);
    }

    //@Test
    @Order(20)
    @DisplayName("Test for Additional Parameter")
    public void testRetrieveHearingScheduleRequestWithAdditionalParam() {
        paramsAsMap.put("Additional-Param","Value");

        final Response response = whenRetrieveHearingScheduleIsInvokedWithAdditionalParam();
        thenValidateHearingsResponseForAdditionalParam(response, objStep);
    }

    @Test
    @Order(21)
    @DisplayName("Test for HearingID Param ")
    public void testRetrieveHearingsRequestWithHearingIDParam() {
        paramsAsMap.remove("hearingDate");
        paramsAsMap.remove("hearingType");

        final Response response = whenRetrieveHearingsIsInvokedWithCorrectHeadersAndParams();
        thenValidateHearingsResponse(response, objStep);
    }

    @Test
    @Order(22)
    @DisplayName("Test for HearingDate Param ")
    public void testRetrieveHearingsRequestWithHearingDateParam() {
        paramsAsMap.remove("hearingIdCaseHQ");
        paramsAsMap.remove("hearingType");

        final Response response = whenRetrieveHearingsIsInvokedWithCorrectHeadersAndParams();
        thenValidateHearingsResponse(response, objStep);
    }

    @Test
    @Order(23)
    @DisplayName("Test for HearingType Param ")
    public void testRetrieveHearingsRequestWithHearingTypeParam() {
        paramsAsMap.remove("hearingIdCaseHQ");
        paramsAsMap.remove("hearingDate");

        final Response response = whenRetrieveHearingsIsInvokedWithCorrectHeadersAndParams();
        thenValidateHearingsResponse(response, objStep);
    }

    @Test
    @Order(24)
    @DisplayName("Test for Correct Headers and All Params")
    public void testRetrieveHearingsRequestWithCorrectHeadersAndParams() {

        final Response response = whenRetrieveHearingsIsInvokedWithCorrectHeadersAndParams();
        thenValidateHearingsResponse(response, objStep);
    }

    @Test
    @Order(25)
    @DisplayName("Test for Correct Headers with No Params")
    public void testRetrieveHearingsRequestWithCorrectHeadersAndNoParams() {

        final Response response = whenRetrieveHearingsIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateHearingsResponse(response, objStep);
    }

    private Response whenRetrieveHearingScheduleIsInvokedWithAdditionalParam() {
        return hearingResponseForCorrectHeadersAndParams(hearingApiRootContext, headersAsMap, paramsAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsRequestIsInvokedForInvalidResource() {
        return hearingResponseForInvalidResource(hearingApiRootContext+"get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsIsInvokedWithCorrectHeadersAndParams() {
        return hearingResponseForCorrectHeadersAndParams(hearingApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsIsInvokedWithCorrectHeadersAndNoParams() {
        return hearingResponseForCorrectHeadersAndNoParams(hearingApiRootContext, headersAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsRequestIsInvokedWithMissingOcpSubKey() {
        return hearingResponseForMissingOcpSubKey(hearingApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader() {
        return hearingResponseForAMissingHeader(hearingApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response hearingResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response hearingResponseForCorrectHeadersAndParams(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }


    private Response hearingResponseForCorrectHeadersAndNoParams(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response hearingResponseForMissingOcpSubKey(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response hearingResponseForAMissingHeader(final String api, final Map<String, Object> headersAsMap,final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();

    }
}
