package uk.gov.hmcts.futurehearings.hmi.unit.testing;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.expect;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.UpdateHearingResponseVerifier.*;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class UpdateHearingUnitTest {

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

    @BeforeEach
    public void initialiseValues() {
        headersAsMap.put("Host", targetHost);
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Source-System", "SnL");
        headersAsMap.put("Destination-System", "CFT");
        headersAsMap.put("Request-Created-At", "datetimestring");
        headersAsMap.put("Request-Type", "THEFT");
    }

    //@Test
    public void testUpdateHearingRequestWithCorrectRequest() throws IOException {
        final String updateHearingRequest = givenAnUpdateHearingRequest(CORRECT_UPDATE_HEARING_REQUEST_JSON);
        final Response response = whenUpdateHearingIsInvokedWithCorrectRequest(updateHearingRequest);
        thenASuccessfulResponseForUpdateIsReturned(response);
    }

    @Test
    public void testUpdateHearingRequestWithMissingOcpSubKey() throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final String updateHearingRequest = givenAnUpdateHearingRequest(CORRECT_UPDATE_HEARING_REQUEST_JSON);
        final Response response = whenUpdateHearingIsInvokedWithMissingOcSubKey(updateHearingRequest);
        thenResponseForMissingHeaderOcpSubscriptionIsReturned(response);
    }

    @Test
    public void testUpdateHearingRequestWithMissingSrcHeader() throws IOException {
        headersAsMap.remove("Source-System");
        final String updateHearingRequest = givenAnUpdateHearingRequest(CORRECT_UPDATE_HEARING_REQUEST_JSON);
        final Response response = whenUpdateHearingIsInvokedWithMissingHeader(updateHearingRequest);
        thenResponseForMissingHeaderSourceIsReturned(response);
    }

    @Test
    public void testUpdateHearingRequestWithMissingHeaderDestination() throws IOException {
        headersAsMap.remove("Destination-System");
        final String updateHearingRequest = givenAnUpdateHearingRequest(CORRECT_UPDATE_HEARING_REQUEST_JSON);
        final Response response = whenUpdateHearingIsInvokedWithMissingHeader(updateHearingRequest);
        thenResponseForMissingHeaderDestinationIsReturned(response);
    }

    @Test
    public void testUpdateHearingRequestWithMissingHeaderDateTime() throws IOException {
        headersAsMap.remove("Request-Created-At");
        final String updateHearingRequest = givenAnUpdateHearingRequest(CORRECT_UPDATE_HEARING_REQUEST_JSON);
        final Response response = whenUpdateHearingIsInvokedWithMissingHeader(updateHearingRequest);
        thenResponseForMissingHeaderDateTimeIsReturned(response);
    }

    @Test
    public void testUpdateHearingRequestWithMissingRequestTypeHeader() throws IOException {
        headersAsMap.remove("Request-Type");
        final String updateHearingRequest = givenAnUpdateHearingRequest(CORRECT_UPDATE_HEARING_REQUEST_JSON);
        final Response response = whenUpdateHearingIsInvokedWithMissingHeader(updateHearingRequest);
        thenResponseForMissingHeaderRequestTypeIsReturned(response);
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
        return expect().that().statusCode(201)
                .given().contentType("application/json")
                .accept(ContentType.JSON).body(payloadBody)
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
