package uk.gov.hmcts.futurehearings.hmi.acceptance.sessions;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper.buildQueryParams;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.sessions.verify.GETSessionsValidationVerifier;

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

    @Value("${sessionsRootContext}")
    private String sessionsRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeURL(sessionsRootContext);
        this.setUrlParams(buildQueryParams("requestSessionType", "1234"));
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSuccessStatus(HttpStatus.OK);
        this.setHmiSuccessVerifier(new GETSessionsValidationVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }

    private void test_response_for_supplied_parameter() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(),
                getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK,
                getInputFileDirectory(),
                getHmiSuccessVerifier(),
                null,
                null);
    }

    @Test
    @DisplayName("Testing the Endpoint with an Invalid Query Parameter")
    void test_invalid_query_param_with_value() throws Exception {
        this.setUrlParams(buildQueryParams("non-existing-param", "Verify no validation from HMI "));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Testing valid and invalid values of the query parameter - requestSessionType : {0} --> {1}")
    @CsvSource(value = {"requestSessionType,DS", "requestSessionType,ADHOC", "requestSessionType,''", "requestSessionType,' '", "requestSessionType,NIL"}, nullValues = "NIL")
    void test_request_session_type_query_param(final String requestSessionType, final String requestSessionValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestSessionType, requestSessionValue));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Testing valid and invalid values of the query parameter - requestStartDate : {0} --> {1}")
    @CsvSource(value = {"requestStartDate,2018-01-29 20:36:01Z", "requestStartDate,AD12H", "requestStartDate,''", "requestStartDate,' '", "requestStartDate,NIL"}, nullValues = "NIL")
    void test_request_start_date_query_param(final String requestStartDate, final String requestStartDateValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestStartDate, requestStartDateValue));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Testing valid and invalid values of the query parameter - requestEndDate : {0} --> {1}")
    @CsvSource(value = {"requestEndDate,2018-01-29 20:36:01Z", "requestEndDate,AD12H", "requestEndDate,''", "requestEndDate,' '", "requestEndDate,NIL"}, nullValues = "NIL")
    void test_request_end_date_query_param(final String requestEndDate, final String requestEndDateValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestEndDate, requestEndDateValue));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Testing valid and invalid values of the query parameter - requestLocationId : {0} --> {1}")
    @CsvSource(value = {"requestLocationId, 300", "requestLocationId, R012", "requestLocationId,''", "requestLocationId,' '", "requestLocationId,NIL"}, nullValues = "NIL")
    void test_request_location_id_query_param(final String roomNameKey, final String roomNameValue) throws Exception {
        this.setUrlParams(buildQueryParams(roomNameKey, roomNameValue));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Testing valid and invalid values of the query parameter - requestDuration : {0} --> {1}")
    @CsvSource(value = {"requestDuration, 360", "requestDuration, case01", "requestDuration,''", "requestDuration,' '", "requestDuration,NIL"}, nullValues = "NIL")
    void test_request_duration_query_param(final String requestDurationKey, final String requestDurationValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestDurationKey, requestDurationValue));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Multiple params - requestSessionType, requestStartDate - Param : {0} --> {1}")
    @CsvSource({"requestSessionType,ADHOC,requestStartDate,R012",
            "requestSessionType,,requestStartDate,",
            "requestSessionType,ADHOC,requestStartDate,2001-01-54",
            "requestSessionType,ADHOC,requestStartDate,01/MON/2011"})
    void test_request_start_date_with_multiple_query_params(final String paramKey1,
                                                            final String paramVal1,
                                                            final String paramKey2,
                                                            final String paramVal2) throws Exception {
        this.setUrlParams(buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Multiple params - requestSessionType, requestLocationId and requestEndDate - Param : {0} --> {1}")
    @CsvSource({"requestSessionType,ADHOC,requestLocationId,1234,requestEndDate,R012",
            "requestSessionType,,requestLocationId,,requestEndDate,",
            "requestSessionType,ADHOC,requestLocationId,nil,requestEndDate,2001-01-54",
            "requestSessionType,ADHOC,requestLocationId,Anything321,requestEndDate,01/JAN/2011"})
    void test_request_end_date_with_multiple_query_params(final String paramKey1,
                                                          final String paramVal1,
                                                          final String paramKey2,
                                                          final String paramVal2,
                                                          final String paramKey3,
                                                          final String paramVal3) throws Exception {
        this.setUrlParams(buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2, paramKey3, paramVal3));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Multiple params - requestSessionType and requestLocationId - Param : {0} --> {1}")
    @CsvSource({"requestSessionType,ADHOC,requestLocationId,R012", "requestSessionType,,requestLocationId,"})
    void test_request_location_id_with_multiple_query_params(final String paramKey1,
                                                             final String paramVal1,
                                                             final String paramKey2,
                                                             final String paramVal2) throws Exception {
        this.setUrlParams(buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Multiple params - requestSessionType, requestLocationId and requestDuration - Param : {0} --> {1}")
    @CsvSource({"requestSessionType,ADHOC,requestLocationId,301,requestDuration,360", "requestSessionType,1234,requestLocationId,280,requestDuration,-1"})
    void test_request_duration_with_multiple_query_params(final String paramKey1,
                                                          final String paramVal1,
                                                          final String paramKey2,
                                                          final String paramVal2,
                                                          final String paramKey3,
                                                          final String paramVal3) throws Exception {
        this.setUrlParams(buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2, paramKey3, paramVal3));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Test with All 5 Query Parameters - Param : {0} --> {1}")
    @CsvSource({"requestSessionType,ADHOC,requestStartDate,10/37/2001,requestEndDate,01/JAN/2011,requestLocationId,301,requestDuration,360"})
    void test_all_five_query_params(final String paramKey1,
                                          final String paramVal1,
                                          final String paramKey2,
                                          final String paramVal2,
                                          final String paramKey3,
                                          final String paramVal3,
                                          final String paramKey4,
                                          final String paramVal4,
                                          final String paramKey5,
                                          final String paramVal5) throws Exception {
        this.setUrlParams(buildQueryParams( paramKey1, paramVal1,
                                            paramKey2, paramVal2,
                                            paramKey3, paramVal3,
                                            paramKey4, paramVal4,
                                            paramKey5, paramVal5));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Test with All Query Parameters with an extra param - Param : {0} --> {1}")
    @CsvSource({"requestSessionType,ADHOC,requestLocation,301,requestDuration,360,requestStartDate,2011-10-37,requestEndDate,01/JAN/2011,extra_param,extra", "requestSessionType,,requestLocation,,requestDuration,,requestStartDate,,requestEndDate,,extra_param,"})
    void test_all_five_query_params_with_extra_param(final String paramKey1,
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
        this.setUrlParams(buildQueryParams( paramKey1, paramVal1,
                                            paramKey2, paramVal2,
                                            paramKey3, paramVal3,
                                            paramKey4, paramVal4,
                                            paramKey5, paramVal5,
                                            paramKey6, paramVal6));
        test_response_for_supplied_parameter();
    }
}
