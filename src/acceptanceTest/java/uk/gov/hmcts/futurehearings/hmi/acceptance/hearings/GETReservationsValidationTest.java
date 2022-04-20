package uk.gov.hmcts.futurehearings.hmi.acceptance.hearings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMICommonSuccessVerifier;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.IncludeTags;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper.buildQueryParams;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@IncludeTags("GET")
public class GETReservationsValidationTest extends HearingValidationTest {

    @Value("${hearings_ReservationsApiRootContext}")
    private String hearings_ReservationsApiRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeURL(hearings_ReservationsApiRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSuccessStatus(HttpStatus.OK);
        this.setHmiSuccessVerifier(new HMICommonSuccessVerifier());
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
    @DisplayName("Testing the Endpoint for GET Reservation with an Invalid Query Parameter")
    void test_invalid_query_param_with_value() throws Exception {
        this.setUrlParams(buildQueryParams("non-existing-param", "HMI should not have validation"));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Testing valid and invalid values of the query parameter - requestStartDate : {0} --> {1}")
    @CsvSource(value = {"requestStartDate,2018-01-29 20:36:01Z", "requestStartDate,AD12H", "requestStartDate,''", "requestStartDate,' '", "requestStartDate,NIL"}, nullValues = "NIL")
    void test_request_start_date_query_param(final String requestStartDateKey, final String requestStartDateValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestStartDateKey, requestStartDateValue));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Testing valid and invalid values of the query parameter - requestEndDate : {0} --> {1}")
    @CsvSource(value = {"requestEndDate, 2018-01-29 20:36:01Z", "requestEndDate, AD12H", "requestEndDate, ''", "requestEndDate, ' '", "requestEndDate, NIL"}, nullValues = "NIL")
    void test_request_end_date_query_param(final String requestEndDateKey, final String requestEndDateValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestEndDateKey, requestEndDateValue));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Testing valid and invalid values of the query parameter - requestDuration : {0} --> {1}")
    @CsvSource(value = {"requestDuration, 360", "requestDuration, case01", "requestDuration, ''", "requestDuration, ' '", "requestDuration, NIL"}, nullValues = "NIL")
    void test_request_duration_query_param(final String requestDurationKey, final String requestDurationValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestDurationKey, requestDurationValue));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Testing valid and invalid values of the query parameters - requestLocationType, requestLocationId and requestLocationReferenceType - Param : {0} --> {1}")
    @CsvSource({"requestLocationType, Court, requestLocationId, 300, requestLocationReferenceType, EXTERNAL", "requestLocationType, ADHOC, requestLocationId, Unknown, requestLocationReferenceType, Undefined"})
    void test_request_location_type_with_multiple_query_params (final String paramKey1,
                                                                final String paramVal1,
                                                                final String paramKey2,
                                                                final String paramVal2,
                                                                final String paramKey3,
                                                                final String paramVal3) throws Exception {
        this.setUrlParams(buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2, paramKey3, paramVal3));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Testing valid and invalid values of the query parameter - requestComments : {0} --> {1}")
    @CsvSource(value = {"requestComments, civil case", "requestComments, ADHOC", "requestComments, ''", "requestComments, ' '", "requestComments, NIL"}, nullValues = "NIL")
    void test_request_Comments_query_param(final String requesCommentsKey, final String requestCommentsValue) throws Exception {
        this.setUrlParams(buildQueryParams(requesCommentsKey, requestCommentsValue));
        test_response_for_supplied_parameter();
    }

    @ParameterizedTest(name = "Testing valid and invalid values of the query parameter - requestExternalId : {0} --> {1}")
    @CsvSource(value = {"requestExternalId, TEST_EXTERNAL_ID", "requestExternalId, ADHOC", "requestExternalId, ''", "requestExternalId, ' '", "requestExternalId, NIL"}, nullValues = "NIL")
    void test_request_external_id_query_param(final String requestExternalIdKey, final String requestExternalIdValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestExternalIdKey, requestExternalIdValue));
        test_response_for_supplied_parameter();
    }
}
