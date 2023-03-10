package uk.gov.hmcts.futurehearings.hmi.acceptance.hearings;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HmiCommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.hearings.verify.GetHearingsByQueryValidationVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.hearings.verify.GetHearingsValidationVerifier;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper.buildQueryParams;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses(GetHearingsValidationTest.class)
@IncludeTags("GET")
class GetHearingsValidationTest extends HearingValidationTest {

    private static final String REQUEST_RECEIVED_SUCCESSFULLY_MSG = "The request was received successfully.";
    private static final String INVALID_QUERY_PARAMETER_MSG = "Invalid query parameter/s in the request URL.";

    @Qualifier("CommonDelegate")
    @Autowired(required = true)
    private CommonDelegate commonDelegate;

    @Value("${hearingsApiRootContext}")
    private String hearingsApiRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeUrl(hearingsApiRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setInputPayloadFileName("hearing-request-standard.json");
        this.setHttpSuccessStatus(HttpStatus.OK);
        this.setHmiSuccessVerifier(new GetHearingsValidationVerifier());
        this.setHmiErrorVerifier(new HmiCommonErrorVerifier());
    }

    @Test
    @DisplayName("Testing the Endpoint with an Invalid Query Parameter")
    void test_invalid_query_param_with_value() throws Exception {
        this.setUrlParams(buildQueryParams("extra_param_key", " "));
        commonDelegate.testExpectedResponseForSuppliedHeader(
                getAuthorizationToken(),
                getRelativeUrl(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                INVALID_QUERY_PARAMETER_MSG, null);
    }

    //@ParameterizedTest(name = "Hearing Date with and without value - Param : {0} --> {1}")
    @CsvSource(value = {"hearingDate, date", "hearingDate,''", "hearingDate,' '", "hearingDate,NIL",
            "hearingDate, 2002-10-02T10:00:00-05:00"}, nullValues = "NIL")
    void test_hearing_date_query_param_with_value(final String hearingDateKey,
                                                        final String hearingDateValue) throws Exception {
        this.setUrlParams(buildQueryParams(hearingDateKey, hearingDateValue));
        commonDelegate.testExpectedResponseForSuppliedHeader(
                getAuthorizationToken(),
                getRelativeUrl(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                REQUEST_RECEIVED_SUCCESSFULLY_MSG, null);
    }

    //@ParameterizedTest(name = "Hearing Id CaseHQ with and without value - Param : {0} --> {1}")
    @CsvSource(value = {"hearingIdCaseHQ, 234"})
    void test_invalid_hearing_id_casehq_query_param_with_value(final String hearingIdCaseHqKey,
                                                               final String hearingIdCaseHqValue) throws Exception {
        this.setUrlParams(buildQueryParams(hearingIdCaseHqKey, hearingIdCaseHqValue));
        commonDelegate.testExpectedResponseForSuppliedHeader(
                getAuthorizationToken(),
                getRelativeUrl(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                new GetHearingsByQueryValidationVerifier(),
                INVALID_QUERY_PARAMETER_MSG, null);
    }

    //@ParameterizedTest(name = "Hearing Type with and without value - Param : {0} --> {1}")
    @CsvSource(value = {"hearingType, Theft", "hearingType,''", "hearingType,' '",
            "hearingType,NIL"}, nullValues = "NIL")
    void test_hearing_type_queryparam_with_value(final String hearingTypeKey,
                                                 final String hearingIdCaseHqValue) throws Exception {
        this.setUrlParams(buildQueryParams(hearingTypeKey, hearingIdCaseHqValue));
        commonDelegate.testExpectedResponseForSuppliedHeader(
                getAuthorizationToken(),
                getRelativeUrl(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                REQUEST_RECEIVED_SUCCESSFULLY_MSG, null);
    }

    //@ParameterizedTest(name = "Multiple params (Hearing_Date & Hearing Type)
    // with and without value - Param : {0} --> {1}")
    @CsvSource(value = {"hearingDate,2002-10-02T10:00:00-05:00,hearingType,Theft",
            "hearingDate,'',hearingType,''", "hearingDate,' ',hearingType,' '"})
    void test_multiple_query_params_with_value(final String hearingDateKey,
                                              final String hearingDateValue,
                                              final String hearingTypeKey,
                                              final String hearingTypeValue) throws Exception {
        this.setUrlParams(QueryParamsHelper.buildQueryParams(hearingDateKey,
                hearingDateValue, hearingTypeKey, hearingTypeValue));
        commonDelegate.testExpectedResponseForSuppliedHeader(
                getAuthorizationToken(),
                getRelativeUrl(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                hmiSuccessVerifier,
                REQUEST_RECEIVED_SUCCESSFULLY_MSG, null);
    }

    //@ParameterizedTest(name = "All Query params (Hearing_Date & Hearing Type)
    // with and without value - Param : {0} --> {1}")
    @CsvSource(value = {"hearingDate,2002-10-02T10:00:00-05:00,hearingType,Theft",
            "hearingDate,'',hearingType,''", "hearingDate,' ',hearingType,' '"})
    void test_all_query_params_with_value(final String hearingDateKey,
                                           final String hearingDateValue,
                                           final String hearingTypeKey,
                                           final String hearingTypeValue) throws Exception {
        this.setUrlParams(QueryParamsHelper.buildQueryParams(hearingDateKey,
                hearingDateValue, hearingTypeKey, hearingTypeValue));
        commonDelegate.testExpectedResponseForSuppliedHeader(
                getAuthorizationToken(),
                getRelativeUrl(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                REQUEST_RECEIVED_SUCCESSFULLY_MSG, null);
    }

    //@ParameterizedTest(name = "All Query params with extra parameter (Hearing_Date & Hearing Type,
    // Extra Params) with and without value - Param : {0} --> {1}")
    @CsvSource(value = {"hearingDate,2002-10-02T10:00:00-05:00,hearingType,Theft,extra_param,extravalue",
            "hearingDate,'',hearingType,'',extra_param,''", "hearingDate,' ',hearingType,' ',extra_param, ' '"})
    void test_all_query_params_with_extra_params(final String hearingDateKey,
                                           final String hearingDateValue,
                                           final String hearingTypeKey,
                                           final String hearingTypeValue,
                                           final String extraParamKey,
                                           final String extraParamValue) throws Exception {
        this.setUrlParams(QueryParamsHelper.buildQueryParams(hearingDateKey,
                hearingDateValue, hearingTypeKey, hearingTypeValue, extraParamKey, extraParamValue));
        commonDelegate.testExpectedResponseForSuppliedHeader(
                getAuthorizationToken(),
                getRelativeUrl(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                INVALID_QUERY_PARAMETER_MSG, null);
    }
}
