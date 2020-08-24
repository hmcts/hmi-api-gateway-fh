package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.http.ContentType;
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

import static io.restassured.RestAssured.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.endReport;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.UpdateHearingResponseVerifier.*;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
//@Disabled("Disabled until Reporting is done")
public class UpdateHearingUnitTests {

    public static final String CORRECT_UPDATE_HEARING_REQUEST_JSON = "requests/correct-update-hearing-request.json";
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
        objTestFromUtils = startReport("UpdateHearing Validations");

    }

    @AfterAll
    public static void finaliseReport() {

        endReport();
        objTestFromUtils=null;
        objStep=null;

    }


    @BeforeEach
    public void initialiseValues(TestInfo info) {
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
        headersAsMap.put("User-Agent", "PostmanRuntime/7.26.3");


        objStep = objTestFromUtils.createNode(info.getDisplayName());
    }



@Test
    public void testUpdateHearingRequestWithCorrectRequest() throws IOException {

        final String updateHearingRequest = givenAnUpdateHearingRequest(CORRECT_UPDATE_HEARING_REQUEST_JSON);
        final Response response = whenUpdateHearingIsInvokedWithCorrectRequest(updateHearingRequest);
        thenASuccessfulResponseForUpdateIsReturned(response, objStep);

    }

    @Test
    @DisplayName("Test for missing OcpSubKey")
    public void testUpdateHearingRequestWithMissingOcpSubKey() throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final String updateHearingRequest = givenAnUpdateHearingRequest(CORRECT_UPDATE_HEARING_REQUEST_JSON);
        final Response response = whenUpdateHearingIsInvokedWithMissingOcSubKey(updateHearingRequest);
        thenResponseForMissingHeaderOcpSubscriptionIsReturned(response, objStep);
    }

    @Test
    @DisplayName("Test for missing Source-System")
    public void testUpdateHearingRequestWithMissingSrcHeader() throws IOException {
        headersAsMap.remove("Source-System");
        final String updateHearingRequest = givenAnUpdateHearingRequest(CORRECT_UPDATE_HEARING_REQUEST_JSON);
        final Response response = whenUpdateHearingIsInvokedWithMissingHeader(updateHearingRequest);
        thenValidateUpdateHearingResponse(response, "Source-System", objStep);
    }

    @Test
    @DisplayName("Test for missing Destination-System")
    public void testUpdateHearingRequestWithMissingHeaderDestination() throws IOException {
        headersAsMap.remove("Destination-System");
        final String updateHearingRequest = givenAnUpdateHearingRequest(CORRECT_UPDATE_HEARING_REQUEST_JSON);
        final Response response = whenUpdateHearingIsInvokedWithMissingHeader(updateHearingRequest);
        thenValidateUpdateHearingResponse(response, "Destination-System", objStep);
    }

    @Test
    @DisplayName("Test for missing Request-Created-At")
    public void testUpdateHearingRequestWithMissingHeaderDateTime() throws IOException {
        headersAsMap.remove("Request-Created-At");
        final String updateHearingRequest = givenAnUpdateHearingRequest(CORRECT_UPDATE_HEARING_REQUEST_JSON);
        final Response response = whenUpdateHearingIsInvokedWithMissingHeader(updateHearingRequest);
        thenValidateUpdateHearingResponse(response, "Request-Created-At", objStep);
    }

    @Test
    @DisplayName("Test for missing Request-Type")
    public void testUpdateHearingRequestWithMissingRequestTypeHeader() throws IOException {
        headersAsMap.remove("Request-Type");
        final String updateHearingRequest = givenAnUpdateHearingRequest(CORRECT_UPDATE_HEARING_REQUEST_JSON);
        final Response response = whenUpdateHearingIsInvokedWithMissingHeader(updateHearingRequest);
        thenValidateUpdateHearingResponse(response, "Request-Type", objStep);
    }


    private String givenAnUpdateHearingRequest(final String path) throws IOException {
        return readFileContents(path);
    }

    private Response whenUpdateHearingIsInvokedWithCorrectRequest(final String input) {
        return requestHearingWithCorrectRequest(hearingApiRootContext + "/CASE123432", headersAsMap, targetInstance, input);
    }

    private Response whenUpdateHearingIsInvokedWithMissingHeader(final String input) {
        return requestHearingWithAMissingHeader(hearingApiRootContext + "/CASE123432", headersAsMap, targetInstance, input);
    }

    private Response whenUpdateHearingIsInvokedWithMissingOcSubKey(final String input) {
        return requestHearingWithAMissingOcpSubKey(hearingApiRootContext + "/CASE123432", headersAsMap, targetInstance, input);
    }

    private Response requestHearingWithCorrectRequest(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return   given()
                .accept(ContentType.JSON)
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response requestHearingWithAMissingHeader(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return expect().that().statusCode(400)
                .given().contentType("application/json")
                .accept(ContentType.JSON).body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response requestHearingWithAMissingOcpSubKey(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return expect().that().statusCode(401)
                .given().contentType("application/json").accept(ContentType.JSON).body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }


}
