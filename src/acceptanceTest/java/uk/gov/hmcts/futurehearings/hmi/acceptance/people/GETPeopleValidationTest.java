package uk.gov.hmcts.futurehearings.hmi.acceptance.people;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper.buildQueryParams;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMICommonSuccessVerifier;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
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
class GETPeopleValidationTest extends PeopleValidationTest {

    @Value("${peopleRootContext}")
    private String peopleRootContext;

    private static final String UPDATED_SINCE_INVALID_DATE_FORMAT = "Please supply ‘updated_since’ date in iso8601 form" ;
    private static final String SUCCESS_MSG = "The request was received successfully.";
    private static final String ERROR_MSG = "Invalid query params.";

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeURL(peopleRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSucessStatus(HttpStatus.OK);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("people","peopl"));
        this.setHmiSuccessVerifier(new HMICommonSuccessVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
        this.setUrlParams(Map.of("updated_since","2020-10-01"));
    }

    @ParameterizedTest(name = "Test updated_since param with valid ISO 8601 date format (YYYY-mm-dd)  - Param : {0} --> {1}")
    @CsvSource(value = {"updated_since, '2020-10-01'","updated_since,'2020-12-03T15:05:57Z'", "updated_since,'2020-12-03T15:05:57+00:00'",
            "updated_since,'20201203T150557Z'"})
    void test_updated_since_query_param_valid_value(final String updatedSinceKey, final String updatedSinceValue) throws Exception {
        this.setUrlParams(buildQueryParams(updatedSinceKey, updatedSinceValue));
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SUCCESS_MSG,null);
    }

    @ParameterizedTest(name = "Test per_page param without mandatory updated_since param - Param : {0} --> {1}")
    @CsvSource(value = {"per_page, 50","per_page,' '", "per_page,NIL"}, nullValues= "NIL")
    void test_per_page_without_mandatory_updated_since_query_param(final String perPageKey, final String perPageValue) throws Exception {
        this.setUrlParams(buildQueryParams(perPageKey, perPageValue));
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                UPDATED_SINCE_INVALID_DATE_FORMAT,null);
    }

    @ParameterizedTest(name = "Test page param without mandatory updated_since param - Param : {0} --> {1}")
    @CsvSource(value = {"page, 2","page,' '", "page,NIL"}, nullValues= "NIL")
    void test_page_without_mandatory_updated_since_queryparam(final String pageKey, final String pageValue) throws Exception {
        this.setUrlParams(buildQueryParams(pageKey, pageValue));
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                UPDATED_SINCE_INVALID_DATE_FORMAT,null);
    }

    @ParameterizedTest(name = "Test All query params with valid values - Param : {0} --> {1}")
    @CsvSource(value = {"updated_since,2018-04-10,page,1,per_page,50", "updated_since,2018-04-10,page,2,per_page,100"})
    void test_all_query_param_with_valid_values(final String paramKey1, final String paramVal1,
                                         final String paramKey2, final String paramVal2,
                                         final String paramKey3, final String paramVal3) throws Exception {
        this.setUrlParams(buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2, paramKey3, paramVal3));
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                SUCCESS_MSG,null);
    }

    @ParameterizedTest(name = "Test All query params with extra param - Param : {0} --> {1}")
    @CsvSource(value = {"updatedSince,2018-04-10,page,1,per_page,50,extra_param,extra", "updatedSince,2018-04-10,page,2,per_page,100,extra_param,"})
    void test_all_query_param_with_extra_param(final String paramKey1, final String paramVal1,
                                               final String paramKey2, final String paramVal2,
                                               final String paramKey3, final String paramVal3,
                                                final String paramKey4, final String paramVal4) throws Exception {
        this.setUrlParams(buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2, paramKey3, paramVal3, paramKey4, paramVal4));
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                ERROR_MSG,null);
    }
}
