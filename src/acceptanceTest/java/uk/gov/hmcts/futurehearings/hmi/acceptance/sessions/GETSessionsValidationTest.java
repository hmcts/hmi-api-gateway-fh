package uk.gov.hmcts.futurehearings.hmi.acceptance.sessions;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithEmulatorValues;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper.buildQueryParams;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.CaseHQCommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.sessions.verify.GETSessionsValidationVerifier;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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
@SuppressWarnings("java:S2187")
class GETSessionsValidationTest extends SessionsValidationTest {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${sessionsRootContext}")
    private String sessionsRootContext;

    private static final String SESSIONS_SUCCESS_MSG = "The request was received successfully.";
    private static final String REQUEST_TYPE_MANDATORY_ERROR_MSG = "You need to provide mandatory parameter: 'requestSessionType'";

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeURL(sessionsRootContext);
        this.setUrlParams(buildQueryParams("requestSessionType", "1234"));
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSucessStatus(HttpStatus.OK);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("sessions", "session"));
        this.setHmiSuccessVerifier(new GETSessionsValidationVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }

    @Test
    @DisplayName("Testing the Endpoint with an Invalid Query Parameter")
    void test_invalid_query_param_with_value() throws Exception {
        this.setUrlParams(buildQueryParams("extra_param_key", " "));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                REQUEST_TYPE_MANDATORY_ERROR_MSG, null);
    }

    @ParameterizedTest(name = "Testing the valid mandatory value of the query parameter : {0} --> {1}")
    @CsvSource(value = {"requestSessionType,ADHOC", "requestSessionType,1234", "requestSessionType,''", "requestSessionType,' '", "requestSessionType,NIL"}, nullValues = "NIL")
    void test_valid_query_param_with_value(final String sessionStartDateHQKey, final String sessionStartDateValue) throws Exception {
        this.setUrlParams(buildQueryParams(sessionStartDateHQKey, sessionStartDateValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SESSIONS_SUCCESS_MSG, null);
    }

    @ParameterizedTest(name = "Testing the invalid optional only value of the query parameter - requestStartDate : {0} --> {1}")
    @CsvSource(value = {"requestStartDate,AD12H", "requestStartDate,''", "requestStartDate,' '", "requestStartDate,NIL"}, nullValues = "NIL")
    void test_request_start_date_query_param_with_value(final String requestStartDate, final String requestStartDateValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestStartDate, requestStartDateValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                REQUEST_TYPE_MANDATORY_ERROR_MSG, null);
    }

    @ParameterizedTest(name = "Testing the invalid optional only value of the query parameter - requestEndDate : {0} --> {1}")
    @CsvSource(value = {"requestEndDate,AD12H", "requestEndDate,''", "requestEndDate,' '", "requestEndDate,NIL"}, nullValues = "NIL")
    void test_request_end_date_query_param_with_value(final String requestEndDate, final String requestEndDateValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestEndDate, requestEndDateValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                REQUEST_TYPE_MANDATORY_ERROR_MSG, null);
    }

    @ParameterizedTest(name = "Testing the invalid optional only value of the query parameter - requestJudgeType : {0} --> {1}")
    @CsvSource(value = {"requestJudgeType,AD12H", "requestJudgeType,''", "requestJudgeType,' '", "requestJudgeType,NIL"}, nullValues = "NIL")
    void test_request_judge_type_query_param_with_value(final String requestJudgeType, final String requestJudgeTypeValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestJudgeType, requestJudgeTypeValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                REQUEST_TYPE_MANDATORY_ERROR_MSG, null);
    }

    @ParameterizedTest(name = "Testing the invalid optional only value of the query parameter - requestLocationId : {0} --> {1}")
    @CsvSource(value = {"requestLocationId, R012", "requestLocationId,''", "requestLocationId,' '", "requestLocationId,NIL"}, nullValues = "NIL")
    void test_request_location_id_without_mandatory_query_params(final String roomNameKey, final String roomNameValue) throws Exception {
        this.setUrlParams(buildQueryParams(roomNameKey, roomNameValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                REQUEST_TYPE_MANDATORY_ERROR_MSG, null);
    }

    @ParameterizedTest(name = "Testing the invalid optional only value of the query parameter - requestDuration : {0} --> {1}")
    @CsvSource(value = {"requestDuration, case01", "requestDuration,''", "requestDuration,' '", "requestDuration,NIL"}, nullValues = "NIL")
    void test_request_duration_without_mandatory_query_params(final String requestDurationKey, final String requestDurationValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestDurationKey, requestDurationValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                REQUEST_TYPE_MANDATORY_ERROR_MSG, null);
    }

    @ParameterizedTest(name = "Multiple params - RoomName with mandatory requestSessionType, requestJudge and requestStartDate - Param : {0} --> {1}")
    @CsvSource({"requestSessionType,ADHOC,requestJudgeType,1234,requestStartDate,R012",
            "requestSessionType,,requestJudgeType,,requestStartDate,",
            "requestSessionType,ADHOC,requestJudgeType,TESTjudge,requestStartDate,2001-01-54",
            "requestSessionType,ADHOC,requestJudgeType,Judging321,requestStartDate,01/MON/2011"})
    void test_request_start_date_with_multiple_query_params(final String paramKey1,
                                                            final String paramVal1,
                                                            final String paramKey2,
                                                            final String paramVal2,
                                                            final String paramKey3,
                                                            final String paramVal3) throws Exception {
        this.setUrlParams(QueryParamsHelper.buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2, paramKey3, paramVal3));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SESSIONS_SUCCESS_MSG, null);
    }

    @ParameterizedTest(name = "Multiple params - RoomName with mandatory requestSessionType, requestLocationId and requestEndDate - Param : {0} --> {1}")
    @CsvSource({"requestSessionType,ADHOC,requestLocationId,1234,requestEndDate,R012",
            "requestSessionType,,requestLocationId,,requestEndDate,",
            "requestSessionType,ADHOC,requestLocationId,TESTjudge,requestEndDate,2001-01-54",
            "requestSessionType,ADHOC,requestLocationId,Judging321,requestEndDate,01/JAN/2011"})
    void test_request_end_date_with_multiple_query_params(final String paramKey1,
                                                          final String paramVal1,
                                                          final String paramKey2,
                                                          final String paramVal2,
                                                          final String paramKey3,
                                                          final String paramVal3) throws Exception {
        this.setUrlParams(QueryParamsHelper.buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2, paramKey3, paramVal3));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SESSIONS_SUCCESS_MSG, null);
    }

    @ParameterizedTest(name = "Multiple params - RoomName with mandatory SessionStartDate and SessionEndDate - Param : {0} --> {1}")
    @CsvSource({"requestSessionType,ADHOC,requestJudgeType,1234,requestLocationId,R012", "requestSessionType,,requestJudgeType,,requestLocationId,"})
    void test_request_location_id_with_multiple_query_params(final String paramKey1,
                                                             final String paramVal1,
                                                             final String paramKey2,
                                                             final String paramVal2,
                                                             final String paramKey3,
                                                             final String paramVal3) throws Exception {
        this.setUrlParams(QueryParamsHelper.buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2, paramKey3, paramVal3));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SESSIONS_SUCCESS_MSG, null);
    }

    @ParameterizedTest(name = "Multiple params - CourtCase with mandatory SessionStartDate and SessionEndDate - Param : {0} --> {1}")
    @CsvSource({"requestSessionType,ADHOC,requestLocationId,301,requestDuration,360", "requestSessionType,1234,requestLocationId,280,requestDuration,-1"})
    void test_request_duration_with_multiple_query_params(final String paramKey1,
                                                          final String paramVal1,
                                                          final String paramKey2,
                                                          final String paramVal2,
                                                          final String paramKey3,
                                                          final String paramVal3) throws Exception {
        this.setUrlParams(QueryParamsHelper.buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2, paramKey3, paramVal3));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SESSIONS_SUCCESS_MSG, null);
    }

    @ParameterizedTest(name = "Test with All Query Parameters - Param : {0} --> {1}")
    @CsvSource({"requestSessionType,ADHOC,requestStartDate,10/37/2001,requestEndDate,01/JAN/2011,requestJudgeType,1234,requestLocationId,301,requestDuration,360"})
    void test_all_query_params_with_value(final String paramKey1,
                                          final String paramVal1,
                                          final String paramKey2,
                                          final String paramVal2,
                                          final String paramKey3,
                                          final String paramVal3,
                                          final String paramKey4,
                                          final String paramVal4,
                                          final String paramKey5,
                                          final String paramVal5,
                                          final String paramKey6,
                                          final String paramVal6) throws Exception {
        this.setUrlParams(QueryParamsHelper.buildQueryParams(paramKey1, paramVal1,
                paramKey2, paramVal2,
                paramKey3, paramVal3,
                paramKey4, paramVal4,
                paramKey5, paramVal5,
                paramKey6, paramVal6));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SESSIONS_SUCCESS_MSG, null);
    }

    @Disabled("Disabled as parameters checks are disabled in dev")
    @ParameterizedTest(name = "Test with All Query Parameters with extra params - Param : {0} --> {1}")
    @CsvSource({"requestSessionType,ADHOC,requestJudgeType,1234,requestLocation,301,requestDuration,360,requestStartDate,2011-10-37,requestEndDate,01/JAN/2011,extra_params,extra", "requestSessionType,,requestJudgeType,,requestLocation,,requestDuration,,requestStartDate,,requestEndDate,,extra_params,"})
    void test_all_query_params_with_extra_params(final String paramKey1,
                                                 final String paramVal1,
                                                 final String paramKey2,
                                                 final String paramVal2,
                                                 final String paramKey3,
                                                 final String paramVal3,
                                                 final String paramKey4,
                                                 final String paramVal4,
                                                 final String paramKey5,
                                                 final String paramVal5,
                                                 final String paramKey6,
                                                 final String paramVal6,
                                                 final String paramKey7,
                                                 final String paramVal7) throws Exception {
        this.setUrlParams(QueryParamsHelper.buildQueryParams(paramKey1, paramVal1,
                paramKey2, paramVal2,
                paramKey3, paramVal3,
                paramKey4, paramVal4,
                paramKey5, paramVal5,
                paramKey6, paramVal6,
                paramKey7, paramVal7));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                REQUEST_TYPE_MANDATORY_ERROR_MSG, null);
    }

    @ParameterizedTest(name = "Testing against the Emulator for Error Responses that come from the Case HQ System")
    @CsvSource(value = {"EMULATOR,400,1000,mandatory value missing", "EMULATOR,400,1003,Bad LOV value"}, nullValues = "NIL")
    void test_successful_response_from_the_emulator_stub(final String destinationSystem,
                                                         final String returnHttpCode,
                                                         final String returnErrorCode,
                                                         final String returnDescription) throws Exception {

        this.setUrlParams(buildQueryParams("requestSessionType", "12345"));
        final HttpStatus httpStatus =
                returnHttpCode.equalsIgnoreCase("400") ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_ACCEPTABLE;
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithEmulatorValues(getApiSubscriptionKey(),
                        destinationSystem,
                        returnHttpCode,
                        returnErrorCode,
                        returnDescription),
                null,
                getUrlParams(),
                getHttpMethod(),
                httpStatus,
                getInputFileDirectory(),
                new CaseHQCommonErrorVerifier(),
                returnDescription,
                null);
    }
}
