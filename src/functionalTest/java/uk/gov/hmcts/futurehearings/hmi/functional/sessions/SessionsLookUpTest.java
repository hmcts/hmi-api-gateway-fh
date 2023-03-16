package uk.gov.hmcts.futurehearings.hmi.functional.sessions;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.sessions.steps.SessionsLookUpSteps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"In order to test that the Session Get Functionality is working properly",
        "As a tester",
        "I want to be able to execute the tests for the Sessions API"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings({"PMD.TooManyMethods"})
public class SessionsLookUpTest extends FunctionalTest {

    private static final String REQUEST_SESSION_TYPE = "requestSessionType";
    private static final String DESTINATION_SYSTEM = "Destination-System";
    private static final String SNL = "SNL";
    private static final String AVAILABLE_SLOT_COUNT = "requestAvailableSlotCount";

    @Value("${sessionsRootContext}")
    protected String sessionsRootContext;

    @Value("${sessionsRootContext}")
    protected String sessionsIdRootContext;
    @Steps
    SessionsLookUpSteps sessionsLookUpSteps;

    @Before
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionType() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndDuration() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "DS");
        queryParameters.put("requestDuration", "360");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndLocation() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "DS");
        queryParameters.put("requestLocationId", "300");
        queryParameters.put("requestLocationType", "Court");
        queryParameters.put("requestLocationReferenceType", "CASEHQ");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndStartDateAndEndDate() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put("requestStartDate", "2022-02-25T09:00:00Z");
        queryParameters.put("requestEndDate", "2022-03-01T09:00:00Z");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndPanelType() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put("requestPanelType", "Adult");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndJurisdiction() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");

        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndGroupBooking() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put("requestGroupBooking", "false");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndAvailableDuration() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put("requestAvailableDuration", "200");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndAvailableSlotCount() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put(AVAILABLE_SLOT_COUNT, "2");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndReturnAllSessions() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put("requestReturnAllSessions", "false");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestSessionTypeAndIncludeDummyRooms() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put("requestIncludeDummyRooms", "false");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testInvalidGetSessionForSessionRequestTypeAndAvailableSlotCountMinValueMinusOne() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put(AVAILABLE_SLOT_COUNT, "0");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters,
                HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testSuccessfulGetSessionForSessionRequestTypeAndAvailableSlotCountMinValue() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put(AVAILABLE_SLOT_COUNT, "1");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForSessionRequestTypeAndAvailableSlotCountMinValuePlusOne() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put(AVAILABLE_SLOT_COUNT, "2");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestServiceCode() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put("requestServiceCode", "1");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestHearingType() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put("requestHearingType", "AC");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForRequestRoomAttributes() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put("requestRoomAttributes", "8");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }
}
