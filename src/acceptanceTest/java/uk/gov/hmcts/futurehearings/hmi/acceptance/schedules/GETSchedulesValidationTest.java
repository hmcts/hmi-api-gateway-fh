package uk.gov.hmcts.futurehearings.hmi.acceptance.schedules;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithEmulatorValues;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper.buildQueryParams;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.CaseHQCommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.schedules.verify.GETSchedulesValidationVerifier;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GETSchedulesValidationTest extends SchedulesValidationTest {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${schedulesApiRootContext}")
    private String schedulesApiRootContext;
    @Value("${token_apiURL}")
    private String token_apiURL;

    @Value("${token_apiTenantId}")
    private String token_apiTenantId;

    @Value("${grantType}")
    private String grantType;

    @Value("${clientID}")
    private String clientID;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${scope}")
    private String scope;

    private static final String INVALID_QUERY_PARAMETER_MSG = "Invalid query parameter/s in the request URL.";
    private static final String SCHEDULES_SUCCESS_MSG = "The request was received successfully.";

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeURL(schedulesApiRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSucessStatus(HttpStatus.OK);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("schedules", "schedule"));
        this.setHmiSuccessVerifier(new GETSchedulesValidationVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }


    @Test
    @DisplayName("Testing the Endpoint with an Invalid Query Parameter")
    void test_date_of_listing_with_invalid_query_param() throws Exception {
        this.setUrlParams(buildQueryParams("test_param", ""));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                INVALID_QUERY_PARAMETER_MSG, null);
    }

    @ParameterizedTest(name = "Hearing Date with and without value - Param : {0} --> {1}")
    @CsvSource(value = {"hearing_date, date", "hearing_date,''", "hearing_date,' '", "hearing_date,NIL", "hearing_date, 2002-10-02T10:00:00-05:00"}, nullValues = "NIL")
    void test_hearing_date_queryparam_with_value(final String hearingDateKey,
                                                 final String hearingDateValue) throws Exception {
        this.setUrlParams(buildQueryParams(hearingDateKey, hearingDateValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SCHEDULES_SUCCESS_MSG, null);
    }


    @ParameterizedTest(name = "Hearing Venue Id with and without value - Param : {0} --> {1}")
    @CsvSource(value = {"hearing_venue_id, 234", "hearing_venue_id,''", "hearing_venue_id,' '", "hearing_venue_id,NIL"}, nullValues = "NIL")
    void test_hearing_venue_id_queryparam_with_value(final String hearingVenueIdKey, final String hearingVenueIdValue) throws Exception {
        this.setUrlParams(buildQueryParams(hearingVenueIdKey, hearingVenueIdValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SCHEDULES_SUCCESS_MSG, null);
    }

    @ParameterizedTest(name = "Hearing Room Id with and without value - Param : {0} --> {1}")
    @CsvSource(value = {"hearing_room_id, 234", "hearing_room_id,''", "hearing_room_id,' '", "hearing_room_id,NIL"}, nullValues = "NIL")
    void test_hearing_room_id_queryparam_with_value(final String hearingRoomIdKey, final String hearingRoomIdValue) throws Exception {
        this.setUrlParams(buildQueryParams(hearingRoomIdKey, hearingRoomIdValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SCHEDULES_SUCCESS_MSG, null);
    }

    @ParameterizedTest(name = "Hearing Session Id CaseHQ with and without value - Param : {0} --> {1}")
    @CsvSource(value = {"hearing_session_id_casehq, 234", "hearing_session_id_casehq,''", "hearing_session_id_casehq,' '", "hearing_session_id_casehq,NIL"}, nullValues = "NIL")
    void test_hearing_session_id_casehq_queryparam_with_value(final String hearingSessionIdCaseHQKey, final String hearingSessionIdCaseHQValue) throws Exception {
        this.setUrlParams(buildQueryParams(hearingSessionIdCaseHQKey, hearingSessionIdCaseHQValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SCHEDULES_SUCCESS_MSG, null);
    }

    @ParameterizedTest(name = "Hearing Case Id HMCTS with and without value - Param : {0} --> {1}")
    @CsvSource(value = {"hearing_case_id_hmcts, 234", "hearing_case_id_hmcts,''", "hearing_case_id_hmcts,' '", "hearing_case_id_hmcts,NIL"}, nullValues = "NIL")
    void test_hearing_case_id_hmcts_queryparam_with_value(final String hearingCaseIdHmctsKey, final String hearingCaseIdHmctsValue) throws Exception {
        this.setUrlParams(buildQueryParams(hearingCaseIdHmctsKey, hearingCaseIdHmctsValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SCHEDULES_SUCCESS_MSG, null);
    }

    @ParameterizedTest(name = "Hearing Id CaseHQ with and without value - Param : {0} --> {1}")
    @CsvSource(value = {"hearing_id_casehq, 234", "hearing_id_casehq,''", "hearing_id_casehq,' '", "hearing_id_casehq,NIL"}, nullValues = "NIL")
    void test_hearing_id_casehq_queryparam_with_value(final String hearingIdCaseHQKey, final String hearingIdCaseHQValue) throws Exception {
        this.setUrlParams(buildQueryParams(hearingIdCaseHQKey, hearingIdCaseHQValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SCHEDULES_SUCCESS_MSG, null);
    }

    @ParameterizedTest(name = "Multiple params (Hearing_Venue_Id & Hearing_Room_Id) with and without value - Param : {0} --> {1}")
    @CsvSource(value = {"hearing_venue_id,234,hearing_room_id,123", "hearing_venue_id,NIL,hearing_room_id,NIL"}, nullValues = "NIL")
    void test_multiple_queryparam_with_value(final String hearingVenueIdKey,
                                             final String hearingVenueIdValue,
                                             final String hearingRoomIdKey,
                                             final String hearingRoomIdValue) throws Exception {
        this.setUrlParams(buildQueryParams(hearingVenueIdKey, hearingVenueIdValue, hearingRoomIdKey, hearingRoomIdValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SCHEDULES_SUCCESS_MSG, null);
    }

    @ParameterizedTest(name = "Multiple params (Hearing_Session_Id_CaseHQ, Hearing_Case_Id_Hmcts & Hearing_Id_Casehq) with and without value - Param : {0} --> {1}")
    @CsvSource(value = {"hearing_session_id_casehq,234,hearing_case_id_hmcts,123,hearing_id_casehq,case1", "hearing_session_id_casehq,NIL,hearing_case_id_hmcts,NIL,hearing_id_casehq,NIL"}, nullValues = "NIL")
    void test_multiple_queryparams_with_value(final String paramKey1,
                                              final String paramVal1,
                                              final String paramKey2,
                                              final String paramVal2,
                                              final String paramKey3,
                                              final String paramVal3) throws Exception {
        this.setUrlParams(buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2, paramKey3, paramVal3));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SCHEDULES_SUCCESS_MSG, null);
    }

    @Test
    void test_all_params_with_value() throws Exception {
        this.setUrlParams(buildQueryParams("hearing_date", "09/09/1964",
                "hearing_venue_id", "1",
                "hearing_room_id", null,
                "hearing_session_id_casehq", "",
                "hearing_case_id_hmcts", " ",
                "hearing_id_casehq", ""));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SCHEDULES_SUCCESS_MSG, null);
    }

    @Test
    void test_all_params_with_an_invalid_query_param() throws Exception {
        this.setUrlParams(buildQueryParams("hearing_date", "09/09/1964",
                "hearing_venue_id", "1",
                "hearing_room_id", null,
                "hearing_session_id_casehq", "",
                "hearing_case_id_hmcts", " ",
                "hearing_id_casehq", "",
                "extra_param", ""));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                INVALID_QUERY_PARAMETER_MSG, null);
    }
}
