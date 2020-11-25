package uk.gov.hmcts.futurehearings.hmi.acceptance.listings;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithEmulatorValues;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper.buildQueryParams;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.CaseHQCommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.listings.verify.GETListingsValidationVerifier;

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
class GETListingsValidationTest extends ListingsValidationTest {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${listingsRootContext}")
    private String listingsRootContext;

    private static final String LISTINGS_SUCCESS_MSG= "The request was received successfully.";
    private static final String INVALID_QUERY_PARAMETER_MSG= "Invalid query parameter/s in the request URL.";

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeURL(listingsRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSucessStatus(HttpStatus.OK);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("listings","listing"));
        this.setHmiSuccessVerifier(new GETListingsValidationVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }


    @Test
    @DisplayName("Testing the Endpoint with an Invalid Query Parameter")
    void test_date_of_listing_with_invalid_queryparam() throws IOException {
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
                INVALID_QUERY_PARAMETER_MSG,null);
    }

    @ParameterizedTest(name = "Date of listing with and without values - Param : {0} --> {1}")
    @CsvSource(value = {"date_of_listing, 2018-01-29 21:36:01Z", "date_of_listing,' '", "date_of_listing,NIL"}, nullValues= "NIL")
    void test_date_of_listing_queryparam_with_value(final String dateOfListingKey, final String dateOfListingValue) throws IOException {
        this.setUrlParams(buildQueryParams(dateOfListingKey, dateOfListingValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                LISTINGS_SUCCESS_MSG,null);
    }

    @ParameterizedTest(name = "Hearing Type with and without values - Param : {0} --> {1}")
    @CsvSource(value = {"hearing_type, VH", "hearing_type,' '", "hearing_type,NIL"}, nullValues= "NIL")
    void test_hearing_type_queryparam_with_value(final String hearingTypeKey, final String hearingTypeValue) throws IOException {
        this.setUrlParams(buildQueryParams(hearingTypeKey, hearingTypeValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
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
                                              final String paramVal2) throws IOException {
        this.setUrlParams(QueryParamsHelper.buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                getHmiSuccessVerifier(),
                "The request was received successfully.",null);
    }

    @Test
    void test_multiple_query_params_with_an_error_parameter() throws IOException {
        this.setUrlParams(QueryParamsHelper.buildQueryParams("date_of_listing",
                "2018-01-29 20:36:01Z",
                "hearing_type",
                "VH","test_extra_param",""));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),
                INVALID_QUERY_PARAMETER_MSG,null);
    }

    @ParameterizedTest(name = "Testing against the Emulator for Error Responses that come from the Case HQ System")
    @CsvSource(value = {"EMULATOR,400,1002,reference to a resource that doesn't exist","EMULATOR,400,1003,mandatory value missing"}, nullValues = "NIL")
    void test_successful_response_from_the_emulator_stub(final String destinationSystem,
                                                         final String returnHttpCode,
                                                         final String returnErrorCode,
                                                         final String returnDescription) throws Exception {

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
