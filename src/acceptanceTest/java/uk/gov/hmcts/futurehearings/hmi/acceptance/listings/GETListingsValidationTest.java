package uk.gov.hmcts.futurehearings.hmi.acceptance.listings;

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
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.listings.verify.GETListingsValidationVerifier;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper.buildQueryParams;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("java:S2187")
class GETListingsValidationTest extends ListingsValidationTest {

    @Value("${listingsRootContext}")
    private String listingsRootContext;

    private static final String LISTINGS_SUCCESS_MSG = "The request was received successfully.";
    private static final String INVALID_QUERY_PARAMETER_MSG = "Invalid query parameter/s in the request URL.";

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeUrl(listingsRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSuccessStatus(HttpStatus.OK);
        this.setHmiSuccessVerifier(new GETListingsValidationVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }

    @ParameterizedTest(name = "Date of listing with and without values - Param : {0} --> {1}")
    @CsvSource(value = {"date_of_listing, 2018-01-29 21:36:01Z", "date_of_listing,' '", "date_of_listing,NIL"},
            nullValues = "NIL")
    void test_date_of_listing_queryparam_with_value(final String dateOfListingKey, final String dateOfListingValue)
            throws Exception {
        this.setUrlParams(buildQueryParams(dateOfListingKey, dateOfListingValue));
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeUrl(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                LISTINGS_SUCCESS_MSG,null);
    }

    @ParameterizedTest(name = "Hearing Type with and without values - Param : {0} --> {1}")
    @CsvSource(value = {"hearing_type, VH", "hearing_type,' '", "hearing_type,NIL"}, nullValues = "NIL")
    void test_hearing_type_queryparam_with_value(final String hearingTypeKey, final String hearingTypeValue)
            throws Exception {
        this.setUrlParams(buildQueryParams(hearingTypeKey, hearingTypeValue));
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeUrl(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                LISTINGS_SUCCESS_MSG,null);
    }

    @ParameterizedTest(name = "Multiple params - (date_of_listing & hearing_type) - Param : {0} --> {1}")
    @CsvSource(value = {"date_of_listing,2018-01-29 20:36:01Z,hearing_type,VH",
                "date_of_listing,'',hearing_type,''",
                "date_of_listing,' ',hearing_type,' '",
                "date_of_listing,NIL,hearing_type,NIL",
                "date_of_listing,2018-01-29 20:36:01Z,hearing_type,",
                "date_of_listing,,hearing_type,2018-01-29 20:36:01Z"}, nullValues = "NIL")
    void test_multiple_query_params(final String paramKey1,
                                              final String paramVal1,
                                              final String paramKey2,
                                              final String paramVal2) throws Exception {
        this.setUrlParams(QueryParamsHelper.buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2));
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeUrl(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                "The request was received successfully.",null);
    }

}
