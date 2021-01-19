package uk.gov.hmcts.futurehearings.hmi.acceptance.common.test;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createCompletePayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithAcceptTypeAtSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithAllValuesEmpty;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithAllValuesNull;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithCorruptedHeaderKey;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithDeprecatedHeaderValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithDestinationSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithRemovedHeaderKey;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithRequestCreatedAtSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithSourceSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeaderWithDuplicateValues;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMIErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMISuccessVerifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Slf4j
@Setter
@Getter
@SuppressWarnings("java:S5786")
public abstract class HMICommonHeaderTest {


    private String authorizationToken;
    private String relativeURL;
    private String relativeURLForNotFound;
    private HttpMethod httpMethod;
    private HttpStatus httpSucessStatus;
    private String inputFileDirectory;
    private String outputFileDirectory;
    private String inputPayloadFileName;
    private Map<String, String> urlParams;

    @Autowired(required = false)
    public CommonDelegate commonDelegate;

    public HMISuccessVerifier hmiSuccessVerifier;

    public HMIErrorVerifier hmiErrorVerifier;

    @BeforeAll
    public void beforeAll(TestInfo info) {
        log.debug("Test execution Class Initiated: " + info.getTestClass().get().getName());
    }

    @BeforeEach
    public void beforeEach(TestInfo info) {
        log.debug("Before execution : " + info.getTestMethod().get().getName());
    }

    @AfterEach
    public void afterEach(TestInfo info) {
        log.debug("After execution : " + info.getTestMethod().get().getName());
    }

    @AfterAll
    public void afterAll(TestInfo info) {
        log.debug("Test execution Class Completed: " + info.getTestClass().get().getName());
    }

    @Test
    @DisplayName("Successfully validated response with all the header values")
    void test_successful_response_with_a_complete_header() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createCompletePayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                getHttpSucessStatus(),
                getInputFileDirectory(),
                getHmiSuccessVerifier(), "The request was received successfully.",null);
    }

    @Test
    @DisplayName("Successfully validated response with mandatory header values")
    void test_successful_response_with_a_mandatory_header() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                getHttpSucessStatus(), getInputFileDirectory(),
                getHmiSuccessVerifier(),
                "The request was received successfully.",null);
    }

    @Test
    @DisplayName("Successfully validated response with an empty payload")
    void test_successful_response_for_empty_json_body() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), "empty-json-payload.json",
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                getHttpSucessStatus(), "common",
                getHmiSuccessVerifier(),
                "The request was received successfully.",null);
    }


    @Test
    @DisplayName("Successfully validated response with a valid payload but a charset appended to the Content-Type")
    void test_successful_response_for_content_type_with_charset_appended() throws Exception {
        RestAssured.config = RestAssured.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(true));
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                getHttpSucessStatus(),
                getInputFileDirectory(),
                getHmiSuccessVerifier(), "The request was received successfully.",null);
        RestAssured.config = RestAssured.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
    }


    @Test
    @DisplayName("API call with Standard Header but slight Error URL")
    void test_invalid_URL() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURLForNotFound(),
                //Performed a near to the Real URL Transformation
                getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.NOT_FOUND, getInputFileDirectory(),
                getHmiErrorVerifier(), "Resource not found",null);
    }

    @Test
    @DisplayName("API call with Standard Header but unimplemented METHOD")
    void test_invalid_REST_method() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(),
                getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                HttpMethod.OPTIONS,
                HttpStatus.NOT_FOUND, getInputFileDirectory(),
                getHmiErrorVerifier(), "Resource not found",null);
    }


    @Test
    @DisplayName("Headers with all empty and null values")
    void test_no_headers_populated() throws Exception {
        //2 Sets of Headers Tested - Nulls and Empty
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(),
                getInputPayloadFileName(),
                createHeaderWithAllValuesEmpty(),
                //The Content Type Has to be Populated for Rest Assured to function properly
                //So this Test was manually executed in Postman Manually as well with the same Order Number
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getInputFileDirectory(),
                getHmiErrorVerifier(),
                "Missing/Invalid Header Source-System",null);

        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(),
                getInputPayloadFileName(),
                createHeaderWithAllValuesNull(),
                //The Content Type Has to be Populated for Rest Assured to function properly
                //So this Test was manually executed in Postman Manually as well with the same Order Number
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getInputFileDirectory(),
                getHmiErrorVerifier(),
                "Missing/Invalid Header Source-System",null);
    }


    @ParameterizedTest(name = "Source System Header invalid values - Param : {0} --> {1}")
    @CsvSource(value = {"Null_Value, NIL", "Empty_Space,''", "Invalid_Value, snl", "Invalid_Value, R&M", "Invalid_Value, rm", "Invalid_Source_System, DIV-FR"}, nullValues = "NIL")
    //TODO - Destination System of - "Invalid_Value, S&L", removed due to the Beta 5 on UAT and Beta 6 on SIT release issue.....
    //Source-System Header Valid value are SNL, RM, MOCK, EMULATOR,CRIME and CFT - This can only be verified manually
    // and tested for dependant EMULATOR or End Systems being available
    void test_source_system_invalid_values(String sourceSystemKey, String sourceSystemVal) throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(sourceSystemVal),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getInputFileDirectory(),
                getHmiErrorVerifier(),
                "Missing/Invalid Header Source-System",null);
    }


    @ParameterizedTest(name = "Destination System Header with invalid values - Param : {0} --> {1}")
    @CsvSource(value = {"Null_Value, NIL", "Empty_Space,''", "Invalid_Value, snl", "Invalid_Value, R&M", "Invalid_Value, rm", "Invalid_Destination_System, CRIMES"}, nullValues = "NIL")
    //TODO - Destination System of - "Invalid_Value, S&L", removed due to the Beta 5 on UAT and Beta 6 on SIT release issue.....
    //Destination-System Header Valid value are SNL, RM, MOCK, EMULATOR,CRIME,CFT and ELINKS - This can only be verified manually and tested for
    //dependant Azure Mock,EMULATOR or End Systems being available
    void test_destination_system_invalid_values(String destinationSystemKey, String destinationSystemVal) throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(destinationSystemVal),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getInputFileDirectory(),
                getHmiErrorVerifier(),
                "Missing/Invalid Header Destination-System",null);
    }


    @ParameterizedTest(name = "Request Created At System Header invalid values - Param : {0} --> {1}")
    @CsvSource({"Null_Value, null", "Empty_Space,\" \"", "Invalid_Value, value",
            "Invalid_Date_Format, 2002-02-31T10:00:30-05:00Z",
            "Invalid_Date_Format, 2002-02-31T1000:30-05:00",
            "Invalid_Date_Format, 2002-02-31T10:00-30-05:00",
            "Invalid_Date_Format, 2002-10-02T15:00:00*05Z",
            "Invalid_Date_Format, 2002-10-02 15:00?0005Z",
            "Invalid_Date_Format, 2002-10-02T15:00:00",
            "InValid_Date_Format, 2002-10-02T15:00:00.05Z",
            "InValid_Date_Format, 2019-10-12 07:20:50.52Z",
    })
    void test_request_created_at_invalid_values(String requestCreatedAtKey, String requestCreatedAtVal) throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue( requestCreatedAtVal),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getInputFileDirectory(),
                getHmiErrorVerifier(),
                "Missing/Invalid Header Request-Created-At",null);
    }


    @ParameterizedTest(name = "Mandatory Keys truncated from the Header - Key : {0}")
    @ValueSource(strings = {"Content-Type", "Accept", "Source-System",
            "Destination-System", "Request-Created-At"
            })
    void test_header_keys_truncated(String keyToBeTruncated) throws Exception {

        final HttpStatus httpStatus =
                keyToBeTruncated.equalsIgnoreCase("Accept") ? HttpStatus.NOT_ACCEPTABLE : HttpStatus.BAD_REQUEST;
        final String expectedErrorMessage =
                keyToBeTruncated.equalsIgnoreCase("Accept") ||
                        keyToBeTruncated.equalsIgnoreCase("Content-Type") ?
                        "Missing/Invalid Media Type" : "Missing/Invalid Header " + keyToBeTruncated;

        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(
                        Arrays.asList(keyToBeTruncated)),
                null,
                getUrlParams(),
                getHttpMethod(),
                httpStatus,
                getInputFileDirectory(),
                getHmiErrorVerifier(),
                expectedErrorMessage,null);
    }


    @ParameterizedTest(name = "Mandatory keys removed from the Header - Key : {0}")
    @ValueSource(strings = {"Content-Type", "Accept", "Source-System",
            "Destination-System", "Request-Created-At"
           })
    void test_with_keys_removed_from_header(String keyToBeRemoved) throws Exception {
        final HttpStatus httpStatus = keyToBeRemoved.equalsIgnoreCase("Accept") ? HttpStatus.NOT_ACCEPTABLE : HttpStatus.BAD_REQUEST;
        final String expectedErrorMessage =
                keyToBeRemoved.equalsIgnoreCase("Accept") ||
                        keyToBeRemoved.equalsIgnoreCase("Content-Type") ?
                        "Missing/Invalid Media Type" : "Missing/Invalid Header " + keyToBeRemoved;

        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(
                        Arrays.asList(keyToBeRemoved)),
                null,
                getUrlParams(),
                getHttpMethod(),
                httpStatus,
                getInputFileDirectory(),
                getHmiErrorVerifier(),
                expectedErrorMessage,null);
    }


    @ParameterizedTest(name = "Accept System Header with invalid format - Param : {0} --> {1}")
    @CsvSource({"Invalid_Value, Random", "Invalid_Format, application/pdf", "Invalid_Format, application/text"})
    void test_accept_at_with_invalid_values(String acceptTypeKey, String acceptTypeVal) throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithAcceptTypeAtSystemValue(acceptTypeVal),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.NOT_ACCEPTABLE,
                getInputFileDirectory(),
                getHmiErrorVerifier(),
                "Missing/Invalid Media Type",null);
    }



    @ParameterizedTest(name = "Request Created At System Header With Valid Date Format - Param : {0} --> {1}")
    @CsvSource({"Valid_Date_Format, 2012-03-19T07:22:00Z", "Valid_Date_Format, 2002-10-02T15:00:00Z",
            "Valid_Date_Format, 2020-10-13T20:20:39+01:00"
    })
    void test_request_created_at_with_valid_values(String requestCreatedAtKey, String requestCreatedAtVal) throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(requestCreatedAtVal),
                null,
                getUrlParams(),
                getHttpMethod(),
                getHttpSucessStatus(),
                getInputFileDirectory(),
                getHmiSuccessVerifier(),
                "The request was received successfully.",null);

    }


    @ParameterizedTest(name = "Deprecated System headers with valid values - Param : {0} --> {1}")
    @CsvSource({"X-Accept, application/json",
            "X-Source-System, CFT",
            "X-Destination-System, SNL",
            "X-Request-Created-At, 2012-03-19T07:22:00Z"
    })
    void test_deprecated_header_values(String deprecatedHeaderKey, String deprecatedHeaderVal) throws Exception {

        final HttpStatus httpStatus = deprecatedHeaderKey.equalsIgnoreCase("X-Accept") ? HttpStatus.NOT_ACCEPTABLE : HttpStatus.BAD_REQUEST;
        final String expectedErrorMessage =
                deprecatedHeaderKey.equalsIgnoreCase("X-Accept") ||
                        deprecatedHeaderKey.equalsIgnoreCase("X-Content-Type") ?
                        "Missing/Invalid Media Type" : "Missing/Invalid Header " + deprecatedHeaderKey.replace("X-", "");
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDeprecatedHeaderValue(deprecatedHeaderKey, deprecatedHeaderVal),
                null,
                getUrlParams(),
                getHttpMethod(),
                httpStatus,
                getInputFileDirectory(),
                getHmiErrorVerifier(),
                expectedErrorMessage,null);

    }

    @ParameterizedTest(name = "Duplicate System headers with valid values - Param : {0} --> {1}")
    @CsvSource(value = {
            //System Headers of Accept and Content-Type could not be duplicated as Rest Assured seems to remove the Duplication of valid same values.
            //This should be tested manually using Postman.
            "Source-System,NIL","Source-System,''","Source-System,CFT",
            "Destination-System,NIL","Destination-System,''","Destination-System,SNL",
            "Request-Created-At,NIL","Request-Created-At,''","Request-Created-At,2002-10-02T15:00:00Z"
    }, nullValues = "NIL")
    void test_duplicate_headers(String duplicateHeaderKey, String duplicateHeaderValue) throws Exception {

        final String expectedErrorMessage =
                        "Missing/Invalid Header " + duplicateHeaderKey;
        Map<String,String> duplicateHeaderField  = new HashMap<String,String>();
        duplicateHeaderField.put(duplicateHeaderKey,duplicateHeaderValue);
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                null,
                createStandardPayloadHeaderWithDuplicateValues(
                        duplicateHeaderField),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getInputFileDirectory(),
                getHmiErrorVerifier(),
                expectedErrorMessage,null);

    }
}
