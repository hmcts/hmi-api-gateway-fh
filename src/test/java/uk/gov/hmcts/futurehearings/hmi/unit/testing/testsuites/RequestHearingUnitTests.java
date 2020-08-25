package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResponseVerifier.checkResponseForError;

import com.aventstack.extentreports.ExtentTest;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
//@Disabled("Disabled until test design is done")
public class RequestHearingUnitTests {

    private static final String CASE_TITLE_MISSING_REQ_PATH = "requests/case-title-missing-request.json";
    private static final String CASE_ID_MISSING_REQ_PATH = "requests/case-id-missing-request.json";
    private static final String TRANSACTIONIDHMCTS_MISSING_REQ_PATH = "requests/transactionidhmcts-missing-request.json";
    private static final String VENUE_MISSING_REQ_PATH = "requests/venue-missing-request.json";
    private static final String JURISDICTION_MISSING_REQ_PATH = "requests/jurisdiction-missing-request.json";
    private static final String SERVICE_MISSING_REQ_PATH = "requests/service-missing-request.json";
    private static final String CASE_TYPE_MISSING_REQ_PATH = "requests/case-type-missing-request.json";
    private static final String HEARING_TYPE_MISSING_REQ_PATH = "requests/hearing-type-missing-request.json";
    private static final String HEARING_CHANNEl_MISSING_REQ_PATH = "requests/hearing-channel-missing-request.json";
    private static final String PRIVATE_HEARING_MISSING_REQ_PATH = "requests/private-hearing-missing-request.json";
    private static final String ALLOCATED_LISTING_TEAM_MISSING_REQ_PATH = "requests/allocated-listing-team-missing-request.json";

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
        objTestFromUtils =  startReport("RequestHearing Validations");

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
        headersAsMap.put("Source", "SnL");
        headersAsMap.put("Destination", "CFT");
        headersAsMap.put("DateTime", "datetimestring");
        headersAsMap.put("RequestType", "TypeOfCase");
        headersAsMap.put("Accept", "application/json");

        objStep = objTestFromUtils.createNode(info.getDisplayName());

    }

    @Test
    @DisplayName("Test for missing Case Title")
    public void testRequestValidationWhenCaseTitleMissing() throws IOException{
        final String input = givenARequestWithMissingField(CASE_TITLE_MISSING_REQ_PATH);
        Response response = whenRequestHearingIsInvoked(input);
        checkResponseForError(response, "Case Title", objStep);
    }

    @Test
    @DisplayName("Test for missing Case ID")
    public void testRequestValidationWhenCaseIdMissing() throws IOException{
        final String input = givenARequestWithMissingField(CASE_ID_MISSING_REQ_PATH);
        Response response = whenRequestHearingIsInvoked(input);
        checkResponseForError(response, "Case Id", objStep);
    }

    @Test
    @DisplayName("Test for missing TransactionIDHMCTS")
    public void testRequestValidationWhenTransactionIDHMCTSMissing() throws IOException{
        final String input = givenARequestWithMissingField(TRANSACTIONIDHMCTS_MISSING_REQ_PATH);
        Response response = whenRequestHearingIsInvoked(input);
        checkResponseForError(response, "TransactionIDHMCTS", objStep);
    }

    @Test
    @DisplayName("Test for missing Venue")
    public void testRequestValidationWhenVenueMissing() throws IOException{
        final String input = givenARequestWithMissingField(VENUE_MISSING_REQ_PATH);
        Response response = whenRequestHearingIsInvoked(input);
        checkResponseForError(response, "Venue", objStep);
    }

    @Test
    @DisplayName("Test for missing Jurisdiction")
    public void testRequestValidationWhenJurisdictionMissing() throws IOException{
        final String input = givenARequestWithMissingField(JURISDICTION_MISSING_REQ_PATH);
        Response response = whenRequestHearingIsInvoked(input);
        checkResponseForError(response, "Jurisdiction", objStep);
    }

    @Test
    @DisplayName("Test for missing Service")
    public void testRequestValidationWhenServiceMissing() throws IOException{
        final String input = givenARequestWithMissingField(SERVICE_MISSING_REQ_PATH);
        Response response = whenRequestHearingIsInvoked(input);
        checkResponseForError(response, "Service", objStep);
    }

    @Test
    @DisplayName("Test for missing CaseType")
    public void testRequestValidationWhenCaseTypeMissing() throws IOException{
        final String input = givenARequestWithMissingField(CASE_TYPE_MISSING_REQ_PATH);
        Response response = whenRequestHearingIsInvoked(input);
        checkResponseForError(response, "Case Type", objStep);
    }

    @Test
    @DisplayName("Test for missing HearingType")
    public void testRequestValidationWhenHearingTypeMissingTest() throws IOException{
        final String input = givenARequestWithMissingField(HEARING_TYPE_MISSING_REQ_PATH);
        Response response = whenRequestHearingIsInvoked(input);
        checkResponseForError(response, "Hearing Type", objStep);
    }

    @Test
    @DisplayName("Test for missing HearingChannel")
    public void testRequestValidationWhenHearingChannelMissing() throws IOException{
        final String input = givenARequestWithMissingField(HEARING_CHANNEl_MISSING_REQ_PATH);
        Response response = whenRequestHearingIsInvoked(input);
        checkResponseForError(response, "Hearing channel", objStep);
    }

    @Test
    @DisplayName("Test for missing PrivateHearing")
    public void testRequestValidationWhenPrivateHearingMissing() throws IOException{
        final String input = givenARequestWithMissingField(PRIVATE_HEARING_MISSING_REQ_PATH);
        Response response = whenRequestHearingIsInvoked(input);
        checkResponseForError(response, "Private Hearing", objStep);
    }

    @Test
    @DisplayName("Test for missing AllocatedListingTeam")
    public void testRequestValidationWhenAllocatedListingTeamMissing() throws IOException{
        final String input = givenARequestWithMissingField(ALLOCATED_LISTING_TEAM_MISSING_REQ_PATH);
        Response response = whenRequestHearingIsInvoked(input);
        checkResponseForError(response, "Allocated Listing Team", objStep);
    }

    private String givenARequestWithMissingField(final String path) throws IOException {
        return readFileContents(path);
    }

    private Response whenRequestHearingIsInvoked(final String input) {
        return requestHearingWithMissingField(hearingApiRootContext, headersAsMap, targetInstance, input);
    }

    private Response requestHearingWithMissingField(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return  given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

}