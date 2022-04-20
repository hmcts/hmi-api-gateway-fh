package uk.gov.hmcts.futurehearings.hmi.functional.sessions;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.sessions.steps.SessionsLookUpSteps;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"In order to test that the Session Get Functionality is working properly",
        "As a tester",
        "I want to be able to execute the tests for the Sessions API"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class SessionsLookUpTest extends FunctionalTest {

    @Value("${sessionsRootContext}")
    protected String sessionsRootContext;

    @Steps
    SessionsLookUpSteps sessionsLookUpSteps;

    @Before
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionType() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "CJ");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndDuration() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "DS");
        queryParameters.put("requestDuration", "360");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndLocation() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "DS");
        queryParameters.put("requestLocationId", "300");
        queryParameters.put("requestLocationType", "Court");
        queryParameters.put("requestLocationReferenceType", "CASEHQ");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndStartDateAndEndDate() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "CJ");
        queryParameters.put("requestStartDate", "2022-02-25T09:00:00Z");
        queryParameters.put("requestEndDate", "2022-03-01T09:00:00Z");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndPanelType() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "CJ");
        queryParameters.put("requestPanelType", "Adult");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndJurisdiction() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "CJ");
        queryParameters.put("requestJurisdiction", "CIV");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndGroupBooking() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "CJ");
        queryParameters.put("requestGroupBooking", "false");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndAvailableDuration() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "CJ");
        queryParameters.put("requestAvailableDuration", "200");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndAvailableSlotCount() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "CJ");
        queryParameters.put("requestAvailableSlotCount", "2");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndReturnAllSessions() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "CJ");
        queryParameters.put("requestReturnAllSessions", "false");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndIncludeDummyRooms() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "CJ");
        queryParameters.put("requestIncludeDummyRooms", "false");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testInvalidGetSessionForSessionRequestTypeAndAvailableSlotCountMinValueMinusOne() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "CJ");
        queryParameters.put("requestAvailableSlotCount", "0");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters,
                HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testSuccessfulGetSessionForSessionRequestTypeAndAvailableSlotCountMinValue() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "CJ");
        queryParameters.put("requestAvailableSlotCount", "1");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForSessionRequestTypeAndAvailableSlotCountMinValuePlusOne() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "CJ");
        queryParameters.put("requestAvailableSlotCount", "2");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForSessionRequestTypeAndHearingType() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "CJ");
        queryParameters.put("requestHearingType", "LISTING");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForSessionRequestTypeAndRoomAttributes() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestSessionType", "CJ");
        queryParameters.put("requestRoomAttributes ", "???");
        headersAsMap.put("Destination-System", "SNL");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }
}
