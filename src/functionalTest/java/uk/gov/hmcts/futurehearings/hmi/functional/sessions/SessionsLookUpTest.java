package uk.gov.hmcts.futurehearings.hmi.functional.sessions;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings({"PMD.TooManyMethods"})
class SessionsLookUpTest extends FunctionalTest {

    private static final String REQUEST_SESSION_TYPE = "requestSessionType";
    private static final String DESTINATION_SYSTEM = "Destination-System";
    private static final String SNL = "SNL";
    private static final String AVAILABLE_SLOT_COUNT = "requestAvailableSlotCount";
    private static final String FALSE = "false";
    private Integer availableSlotCount = 1;

    @Value("${sessionsRootContext}")
    protected String sessionsRootContext;

    @Value("${sessionsRootContext}")
    protected String sessionsIdRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    void testSuccessfulGetSessionForRequestSessionType() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);

        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
    }

    @Test
    void testSuccessfulGetSessionForRequestSessionTypeAndDuration() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "DS");
        queryParameters.put("requestDuration", "360");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters,  HttpStatus.OK);
    }

    @Test
    void testSuccessfulGetSessionForRequestSessionTypeAndLocation() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "DS");
        queryParameters.put("requestLocationId", "300");
        queryParameters.put("requestLocationType", "Court");
        queryParameters.put("requestLocationReferenceType", "CASEHQ");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters,  HttpStatus.OK);
    }

    @Test
    void testSuccessfulGetSessionForRequestSessionTypeAndStartDateAndEndDate() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put("requestStartDate", "2022-02-25T09:00:00Z");
        queryParameters.put("requestEndDate", "2022-03-01T09:00:00Z");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters,  HttpStatus.OK);
    }

    @Test
    void testSuccessfulGetSessionForRequestSessionTypeAndPanelType() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put("requestPanelType", "Adult");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters,  HttpStatus.OK);
    }

    @Test
    void testSuccessfulGetSessionForRequestSessionTypeAndJurisdiction() { //NOSONAR
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters,  HttpStatus.OK);
    }

    @Test
    void testSuccessfulGetSessionForRequestSessionTypeAndGroupBooking() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put("requestGroupBooking", FALSE);
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters,  HttpStatus.OK);
    }

    @Test
    void testSuccessfulGetSessionForRequestSessionTypeAndAvailableDuration() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put("requestAvailableDuration", "200");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters,  HttpStatus.OK);
    }

    @Test
    void testSuccessfulGetSessionForRequestSessionTypeAndAvailableSlotCount() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put(AVAILABLE_SLOT_COUNT, "2");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters,  HttpStatus.OK);
    }

    @Test
    void testSuccessfulGetSessionForRequestSessionTypeAndReturnAllSessions() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put("requestReturnAllSessions", FALSE);
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters,  HttpStatus.OK);
    }

    @Test
    void testSuccessfulGetSessionForRequestSessionTypeAndIncludeDummyRooms() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put("requestIncludeDummyRooms", FALSE);
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters,  HttpStatus.OK);
    }

    @Test
    void testInvalidGetSessionForSessionRequestTypeAndAvailableSlotCountMinValueMinusOne() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put(AVAILABLE_SLOT_COUNT, "0");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testSuccessfulGetSessionForSessionRequestTypeAndAvailableSlotCountMinValue() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put(AVAILABLE_SLOT_COUNT, availableSlotCount.toString());
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
    }

    @Test
    void testSuccessfulGetSessionForSessionRequestTypeAndAvailableSlotCountMinValuePlusOne() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        availableSlotCount = availableSlotCount + 1;
        queryParameters.put(REQUEST_SESSION_TYPE, "CJ");
        queryParameters.put(AVAILABLE_SLOT_COUNT, availableSlotCount.toString());
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
    }

    @Test
    void testSuccessfulGetSessionForRequestServiceCode() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put("requestServiceCode", "1");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
    }

    @Test
    void testSuccessfulGetSessionForRequestHearingType() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put("requestHearingType", "AC");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
    }

    @Test
    void testSuccessfulGetSessionForRequestRoomAttributes() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put("requestRoomAttributes", "8");
        headersAsMap.put(DESTINATION_SYSTEM, SNL);
        callRestEndpointWithQueryParams(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
    }
}
