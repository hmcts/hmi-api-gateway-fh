package uk.gov.hmcts.futurehearings.hmi.acceptance.people;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper.buildQueryParams;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMICommonSuccessVerifier;

import java.io.IOException;

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

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${peopleRootContext}")
    private String peopleRootContext;

    private static final String UPDATED_SINCE_INVALID_DATE_FORMAT = "Please supply ‘updated_since’ date in iso8601 form" ;
    private static final String SUCCESS_MSG = "The request was received successfully.";

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeURL(peopleRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSucessStatus(HttpStatus.OK);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("people","peopl"));
        this.setHmiSuccessVerifier(new HMICommonSuccessVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }

    @ParameterizedTest(name = "Update Since without valid ISO 8601 date format (YYYY-mm-dd)  - Param : {0} --> {1}")
    @CsvSource(value = {"updatedSince, 01-31-2018","updatedSince,'31-01-2018'", "updatedSince,'2018-01-29 20:36:01Z'", "updatedSince,NIL"}, nullValues= "NIL")
    void test_updated_since_queryparam_with_value(final String updatedSinceKey, final String updatedSinceValue) throws IOException {
        this.setUrlParams(buildQueryParams(updatedSinceKey, updatedSinceValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                UPDATED_SINCE_INVALID_DATE_FORMAT,null);
    }

    @ParameterizedTest(name = "Per Page without mandatory update since field - Param : {0} --> {1}")
    @CsvSource(value = {"per_page, 50","per_page,' '", "per_page,NIL"}, nullValues= "NIL")
    void test_per_page_without_mandatory_updated_since_queryparam(final String perPageKey, final String perPageValue) throws IOException {
        this.setUrlParams(buildQueryParams(perPageKey, perPageValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                UPDATED_SINCE_INVALID_DATE_FORMAT,null);
    }

    @ParameterizedTest(name = "Page without mandatory update since field - Param : {0} --> {1}")
    @CsvSource(value = {"page, 2","page,' '", "page,NIL"}, nullValues= "NIL")
    void test_page_without_mandatory_updated_since_queryparam(final String pageKey, final String pageValue) throws IOException {
        this.setUrlParams(buildQueryParams(pageKey, pageValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                UPDATED_SINCE_INVALID_DATE_FORMAT,null);
    }

    @ParameterizedTest(name = "All query params with values - Param : {0} --> {1}")
    @CsvSource(value = {"updatedSince,2018-04-10,page,2,per_page,50","updatedSince,,page,,per_page,"})
    void test_all_queryparam_with_values(final String paramKey1, final String paramVal1,
                                         final String paramKey2, final String paramVal2,
                                         final String paramKey3, final String paramVal3) throws IOException {
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
                SUCCESS_MSG,null);
    }
}
