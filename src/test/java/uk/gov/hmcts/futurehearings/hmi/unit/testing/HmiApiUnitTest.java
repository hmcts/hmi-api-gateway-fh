package uk.gov.hmcts.futurehearings.hmi.unit.testing;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.steps.HearingApiCallSteps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResponseVerifier.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class HmiApiUnitTest {

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

    @Steps
    HearingApiCallSteps hearingApiCallSteps;

    private final Map<String, Object> headersAsMap = new HashMap<>();

    @Before
    public void initialiseValues() {
        headersAsMap.put("Host", targetHost);
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Source", "SnL");
        headersAsMap.put("Destination", "CFT");
        headersAsMap.put("DateTime", "datetimestring");
        headersAsMap.put("RequestType", "TypeOfCase");
    }

    @Test
    public void testRequestValidationWhenCaseTitleMissing() throws IOException{
        final String input = givenARequesWithMissingField(CASE_TITLE_MISSING_REQ_PATH);
        whenRequestHearingIsInvoked(input);
        thenResponseHasErrorForMissingCaseTitle(lastResponse());
    }

    @Test
    public void testRequestValidationWhenCaseIdMissing() throws IOException{
        final String input = givenARequesWithMissingField(CASE_ID_MISSING_REQ_PATH);
        whenRequestHearingIsInvoked(input);
        thenResponseHasErrorForMissingCaseId(lastResponse());
    }

    @Test
    public void testRequestValidationWhenTransactionIDHMCTSMissing() throws IOException{
        final String input = givenARequesWithMissingField(TRANSACTIONIDHMCTS_MISSING_REQ_PATH);
        whenRequestHearingIsInvoked(input);
        thenResponseHasErrorForMissingTransactionIDHMCTS(lastResponse());
    }

    @Test
    public void testRequestValidationWhenVenueMissing() throws IOException{
        final String input = givenARequesWithMissingField(VENUE_MISSING_REQ_PATH);
        whenRequestHearingIsInvoked(input);
        thenResponseHasErrorForMissingVenue(lastResponse());
    }

    @Test
    public void testRequestValidationWhenJurisdictionMissing() throws IOException{
        final String input = givenARequesWithMissingField(JURISDICTION_MISSING_REQ_PATH);
        whenRequestHearingIsInvoked(input);
        thenResponseHasErrorForMissingJurisdiction(lastResponse());
    }

    @Test
    public void testRequestValidationWhenServiceMissing() throws IOException{
        final String input = givenARequesWithMissingField(SERVICE_MISSING_REQ_PATH);
        whenRequestHearingIsInvoked(input);
        thenResponseHasErrorForMissingService(lastResponse());
    }

    @Test
    public void testRequestValidationWhenCaseTypeMissing() throws IOException{
        final String input = givenARequesWithMissingField(CASE_TYPE_MISSING_REQ_PATH);
        whenRequestHearingIsInvoked(input);
        thenResponseHasErrorForMissingCaseType(lastResponse());
    }

    @Test
    public void testRequestValidationWhenHearingTypeMissingTest() throws IOException{
        final String input = givenARequesWithMissingField(HEARING_TYPE_MISSING_REQ_PATH);
        whenRequestHearingIsInvoked(input);
        thenResponseHasErrorForMissingHearingType(lastResponse());
    }

    @Test
    public void testRequestValidationWhenHearingChannelMissing() throws IOException{
        final String input = givenARequesWithMissingField(HEARING_CHANNEl_MISSING_REQ_PATH);
        whenRequestHearingIsInvoked(input);
        thenResponseHasErrorForMissingHearingChannel(lastResponse());
    }

    @Test
    public void testRequestValidationWhenPrivateHearingMissing() throws IOException{
        final String input = givenARequesWithMissingField(PRIVATE_HEARING_MISSING_REQ_PATH);
        whenRequestHearingIsInvoked(input);
        thenResponseHasErrorForMissingPrivateHearing(lastResponse());
    }

    @Test
    public void testRequestValidationWhenAllocatedListingTeamMissing() throws IOException{
        final String input = givenARequesWithMissingField(ALLOCATED_LISTING_TEAM_MISSING_REQ_PATH);
        whenRequestHearingIsInvoked(input);
        thenResponseHasErrorForMissingAllocatedListingTeam(lastResponse());
    }

    private String givenARequesWithMissingField(final String path) throws IOException {
        return readFileContents(path);
    }

    private void whenRequestHearingIsInvoked(final String input) {
        hearingApiCallSteps.requestHearingWithMissingField(hearingApiRootContext, headersAsMap, targetInstance, input);
    }

}