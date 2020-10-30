package uk.gov.hmcts.futurehearings.hmi.acceptance.sessions;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper.buildQueryParams;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.sessions.verify.GETSessionsValidationVerifier;

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
@SuppressWarnings("java:S2187")
class GETSessionsValidationTest extends SessionsValidationTest {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${sessionsRootContext}")
    private String sessionsRootContext;

    private static final String START_END_DATE_MANDATORY_ERROR_MSG= "You need to provide both of the following parameters: 'sessionStartDate', 'sessionEndDate'";
    private static final String INVALID_QUERY_PARAMETER_MSG = "Invalid query parameter/s in the request URL.";
    private static final String SESSIONS_SUCCESS_MSG= "The request was received successfully.";

    @BeforeAll
    public void initialiseValues() throws Exception{
        super.initialiseValues();
        this.setRelativeURL(sessionsRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSucessStatus(HttpStatus.OK);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("sessions","session"));
        this.setHmiSuccessVerifier(new GETSessionsValidationVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }

    @Test
    @DisplayName("Testing the Endpoint with an Invalid Query Parameter")
    void test_invalid_query_param_with_value() throws IOException {
        this.setUrlParams(buildQueryParams("extra_param_key", " "));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,  getInputFileDirectory(),
                getHmiErrorVerifier(),
                INVALID_QUERY_PARAMETER_MSG);
    }

    @ParameterizedTest(name = "Session StartDate without mandatory Session EndDate - Param : {0} --> {1}")
    @CsvSource(value = {"sessionStartDate, 2018-01-29 20:36:01Z","sessionStartDate,''", "sessionStartDate,' '", "sessionStartDate,NIL"}, nullValues= "NIL")
    void test_session_startDate_queryparam_with_value(final String sessionStartDateHQKey, final String sessionStartDateValue) throws IOException {
        this.setUrlParams(buildQueryParams(sessionStartDateHQKey, sessionStartDateValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                START_END_DATE_MANDATORY_ERROR_MSG);
    }

    @ParameterizedTest(name = "Session EndDate without mandatory Session StartDate - Param : {0} --> {1}")
    @CsvSource(value = {"sessionEndDate, 2018-01-29 20:36:01Z", "sessionEndDate,''", "sessionEndDate,' '",  "sessionEndDate,NIL"}, nullValues= "NIL")
    void test_session_endDate_queryparam_with_value(final String sessionEndDateKey, final String sessionEndDateValue) throws IOException {
        this.setUrlParams(buildQueryParams(sessionEndDateKey, sessionEndDateValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                START_END_DATE_MANDATORY_ERROR_MSG);
    }

    @ParameterizedTest(name = "Session Room Name without mandatory Session StartDate & Session EndDate - Param : {0} --> {1}")
    @CsvSource(value = {"room-Name, R012", "room-Name,''", "room-Name,' '", "room-Name,NIL"}, nullValues = "NIL")
    void test_roomName_without_mandatory_queryparams(final String roomNameKey, final String roomNameValue) throws IOException {
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
                START_END_DATE_MANDATORY_ERROR_MSG);
    }

    @ParameterizedTest(name = "Session Case Court without mandatory Session StartDate & Session EndDate - Param : {0} --> {1}")
    @CsvSource(value = {"caseCourt, case01", "caseCourt,''", "caseCourt,' '", "caseCourt,NIL"}, nullValues = "NIL")
    void test_caseCourt_without_mandatory_queryparams(final String roomNameKey, final String roomNameValue) throws IOException {
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
                START_END_DATE_MANDATORY_ERROR_MSG);
    }

    @ParameterizedTest(name = "Multiple params - RoomName with mandatory SessionStartDate and SessionEndDate - Param : {0} --> {1}")
    @CsvSource({"sessionStartDate,2018-01-29 20:36:01Z,sessionEndDate,2018-01-29 20:36:01Z,room-Name,R121", "sessionStartDate,,sessionEndDate,,room-Name,"})
    void test_roomName_with_multiple_queryparams(final String paramKey1,
                                              final String paramVal1,
                                              final String paramKey2,
                                              final String paramVal2,
                                              final String paramKey3,
                                              final String paramVal3) throws IOException {
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
                SESSIONS_SUCCESS_MSG);
    }

    @ParameterizedTest(name = "Multiple params - CourtCase with mandatory SessionStartDate and SessionEndDate - Param : {0} --> {1}")
    @CsvSource({"sessionStartDate,2018-01-29 20:36:01Z,sessionEndDate,2018-01-29 20:36:01Z,caseCourt,R121", "sessionStartDate,,sessionEndDate,,caseCourt,"})
    void test_courtCase_with_multiple_queryparams(final String paramKey1,
                                              final String paramVal1,
                                              final String paramKey2,
                                              final String paramVal2,
                                              final String paramKey3,
                                              final String paramVal3) throws IOException {
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
                SESSIONS_SUCCESS_MSG);
    }

    @ParameterizedTest(name = "Test with All Query Parameters - Param : {0} --> {1}")
    @CsvSource({"sessionStartDate,2018-01-29 20:36:01Z,sessionEndDate,2018-01-29 20:36:01Z,room-Name,R121,caseCourt,case123", "sessionStartDate,,sessionEndDate,,room-Name,,caseCourt,"})
    void test_all_queryparams_with_value(final String paramKey1,
                                              final String paramVal1,
                                              final String paramKey2,
                                              final String paramVal2,
                                              final String paramKey3,
                                              final String paramVal3,
                                              final String paramKey4,
                                              final String paramVal4) throws IOException {
        this.setUrlParams(QueryParamsHelper.buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2, paramKey3, paramVal3, paramKey4, paramVal4));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SESSIONS_SUCCESS_MSG);
    }

    @ParameterizedTest(name = "Test with All Query Parameters with extram params - Param : {0} --> {1}")
    @CsvSource({"sessionStartDate,2018-01-29 20:36:01Z,sessionEndDate,2018-01-29 20:36:01Z,room-Name,R121,caseCourt,case123,extra_params,extra", "sessionStartDate,,sessionEndDate,,room-Name,,caseCourt,,extra_params,,"})
    void test_all_queryparams_with_extra_params(final String paramKey1,
                                         final String paramVal1,
                                         final String paramKey2,
                                         final String paramVal2,
                                         final String paramKey3,
                                         final String paramVal3,
                                         final String paramKey4,
                                         final String paramVal4,
                                         final String paramKey5,
                                         final String paramVal5
                                         ) throws IOException {
        this.setUrlParams(QueryParamsHelper.buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2, paramKey3, paramVal3, paramKey4, paramVal4, paramKey5, paramVal5));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                INVALID_QUERY_PARAMETER_MSG);
    }

}
