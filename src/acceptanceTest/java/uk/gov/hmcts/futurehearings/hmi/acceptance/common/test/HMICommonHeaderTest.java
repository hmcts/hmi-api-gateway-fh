package uk.gov.hmcts.futurehearings.hmi.acceptance.common.test;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createCompletePayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMIErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMISuccessVerifier;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Slf4j
@Setter
@Getter
public abstract class HMICommonHeaderTest {


    private String apiSubscriptionKey;
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

    @Autowired(required = false)
    public HMISuccessVerifier hmiSuccessVerifier;

    @Autowired(required = false)
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
        log.debug("After execution : "+info.getTestMethod().get().getName());
    }

    @AfterAll
    public void afterAll(TestInfo info) {
        log.debug("Test execution Class Completed: " + info.getTestClass().get().getName());
    }

    @Test
    @DisplayName("Successfully validated response with all the header values")
    public void test_successful_response_with_a_complete_header() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createCompletePayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                getHttpSucessStatus(),
                getInputFileDirectory(),
                "common",
                "standard-success-response.json",
                getHmiSuccessVerifier(),"The request was received successfully.");
    }

    @Test
    @DisplayName("Successfully validated response with mandatory header values")
    public void test_successful_response_with_a_mandatory_header() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                getHttpSucessStatus(), getInputFileDirectory(),
                "common",
                "standard-success-response.json",getHmiSuccessVerifier(),"The request was received successfully.");
    }

    @Test
    @DisplayName("Successfully validated response with an empty payload")
    public void test_successful_response_for_empty_json_body() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), "empty-json-payload.json",
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                getHttpSucessStatus(), "common",
                "common",
                "standard-success-response.json",getHmiSuccessVerifier(),"The request was received successfully.");
    }


    @Test
    @DisplayName("Successfully validated response with a valid payload but a charset appended to the Content-Type")
    public void test_successful_response_for_content_type_with_charset_appended() throws Exception {
        RestAssured.config = RestAssured.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(true));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                getHttpSucessStatus(), getInputFileDirectory(),
                "common",
                "standard-success-response.json",getHmiSuccessVerifier(),"The request was received successfully.");
        RestAssured.config = RestAssured.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
    }
    /*
    @Test
    @DisplayName("Incorrect Url with correct Header")
    public void test_invalid_URL() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURLForNotFound(),
                //Performed a near to the Real URL Transformation
                getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.NOT_FOUND, getApiName(),"Resource not found");
    }

    @Test
    @DisplayName("Headers with all empty and null values")
    public void test_no_headers_populated() throws Exception {
        //2 Sets of Headers Tested - Nulls and Empty
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(),
                getInputPayloadFileName(),
                createHeaderWithAllValuesEmpty(),
                //The Content Type Has to be Populated for Rest Assured to function properly
                //So this Test was manually executed in Postman Manually as well with the same Order Number
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Resource not found");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(),
                getInputPayloadFileName(),
                createHeaderWithAllValuesNull(),
                //The Content Type Has to be Populated for Rest Assured to function properly
                //So this Test was manually executed in Postman Manually as well with the same Order Number
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Resource not found");
    }

    @Test
    @DisplayName("Subscription Key Truncated in the Header")
    public void test_subscriptionkey_key_truncated() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                Arrays.asList("Ocp-Apim-Subscription-Key")), getUrlParams(), getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Missing/Invalid Header Source-System");
    }

    @Test
    @DisplayName("Subscription Key Value Truncated in the Header")
    public void test_subscriptionkey_value_truncated() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getApiSubscriptionKey().substring(0,getApiSubscriptionKey().length()-1),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader("  "),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Missing/Invalid Header Source-System");
    }

    @ParameterizedTest(name = "Subscription Key with invalid values  - Param : {0} --> {1}")
    @CsvSource({ "Null_Value, null","Empty_Space,\" \"", "Tab, \"\\t\"", "Newline, \"\\n\"","Wrong_Value,c602c8ed3b8147be910449b563dce008"})
    public void test_subscription_key_invalid_values(String subKey, String subKeyVal) throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(subKeyVal),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Missing/Invalid Header Source-System");
    }

    @ParameterizedTest(name = "Source System Header invalid values - Param : {0} --> {1}")
    @CsvSource({ "Null_Value, null","Empty_Space,\" \"", "Invalid_Value, SNL", "Invalid_Source_System, S&L"})
    public void test_source_system_invalid_values(String sourceSystemKey, String sourceSystemVal) throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(getApiSubscriptionKey(),sourceSystemVal),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
    }

    @ParameterizedTest(name = "Destination System Header with invalid values - Param : {0} --> {1}")
    @CsvSource({ "Null_Value, null", "Empty_Space,\" \"", "Invalid_Value, SNL", "Invalid_Destination_System, CFT"})
    public void test_destination_system_invalid_values(String destinationSystemKey, String destinationSystemVal) throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey(),destinationSystemVal),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
    }

    @ParameterizedTest(name = "Request Created At System Header invalid values - Param : {0} --> {1}")
    @CsvSource({ "Null_Value, null", "Empty_Space,\" \"", "Invalid_Value, value",
            "Invalid_Date_Format, 2002-02-31T10:00:30-05:00Z",
            "Invalid_Date_Format, 2002-02-31T1000:30-05:00",
            "Invalid_Date_Format, 2002-02-31T10:00-30-05:00",
            "Invalid_Date_Format, 2002-10-02T15:00:00*05Z",
            "Invalid_Date_Format, 2002-10-02 15:00?0005Z",
            "Invalid_Date_Format, 2002-10-02T15:00:00",
    })
    public void test_request_created_at_invalid_values(String requestCreatedAtKey, String requestCreatedAtVal) throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), requestCreatedAtVal),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
    }

    @ParameterizedTest(name = "Mandatory Keys truncated from the Header - Key : {0}")
    @ValueSource(strings = {"Content-Type", "Accept", "Source-System",
            "Destination-System", "Request-Created-At",
            "Request-Processed-At", "Request-Type"})
    public void test_header_keys_truncated(String keyToBeTruncated) throws Exception {
        final HttpStatus httpStatus = keyToBeTruncated.equalsIgnoreCase("Accept")?HttpStatus.NOT_ACCEPTABLE:HttpStatus.BAD_REQUEST;

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList(keyToBeTruncated)),
                getUrlParams(),
                getHttpMethod(),
                httpStatus,
                getApiName(),
                "Missing/Invalid Header Source-System");
    }

    @ParameterizedTest(name = "Mandatory keys removed from the Header - Key : {0}")
    @ValueSource(strings = {"Content-Type", "Accept", "Source-System",
            "Destination-System", "Request-Created-At",
            "Request-Processed-At", "Request-Type"})
    public void test_with_keys_removed_from_header(String keyToBeRemoved) throws Exception {
        final HttpStatus httpStatus = keyToBeRemoved.equalsIgnoreCase("Accept")?HttpStatus.NOT_ACCEPTABLE:HttpStatus.BAD_REQUEST;

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList(keyToBeRemoved)),
                getUrlParams(),
                getHttpMethod(),
                httpStatus,
                getApiName(),
                "Missing/Invalid Header Source-System");
    }

    @ParameterizedTest(name = "Request Processed At System Header With Invalid Values - Param : {0} --> {1}")
    @CsvSource({ "Null_Value, null","Empty_Space,\" \"", "Invalid_Value, value",
            "Invalid_Date_Format, 2002-02-31T10:00:30-05:00Z",
            "Invalid_Date_Format, 2002-02-31T1000:30-05:00",
            "Invalid_Date_Format, 2002-02-31T10:00-30-05:00",
            "Invalid_Date_Format, 2002-10-02T15:00:00*05Z",
            "Invalid_Date_Format, 2002-10-02 15:00?0005Z",
            "Invalid_Date_Format, 2002-10-02T15:00:00",
    })
    public void test_request_processed_at_with_invalid_values(String requestProcessedAtKey, String requestProcessedAtVal) throws Exception {
            commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                    getRelativeURL(), getInputPayloadFileName(),
                    createHeaderWithRequestProcessedAtSystemValue(getApiSubscriptionKey(), requestProcessedAtVal),
                    getUrlParams(),
                    getHttpMethod(),
                    HttpStatus.BAD_REQUEST,
                    getApiName(),
                    null);
    }

    @ParameterizedTest(name = "Request Type System Header with invalid values - Param : {0} --> {1}")
    @CsvSource({"Null_Value, null", "Invalid_Value, Robbery"})
    public void test_request_type_at_with_invalid_values(String requestTypeKey, String requestTypeVal) throws Exception {
            commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                    getRelativeURL(), getInputPayloadFileName(),
                    createHeaderWithRequestTypeAtSystemValue(getApiSubscriptionKey(), requestTypeVal),
                    getUrlParams(),
                    getHttpMethod(),
                    HttpStatus.BAD_REQUEST,
                    getApiName(),
                    null);
    }

    @ParameterizedTest(name = "Accept System Header with invalid format - Param : {0} --> {1}")
    @CsvSource({ "Invalid_Value, Random", "Invalid_Format, application/pdf", "Invalid_Format, application/text"})
    public void test_accept_at_with_invalid_values(String acceptTypeKey, String acceptTypeVal) throws Exception {
            commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                    getRelativeURL(), getInputPayloadFileName(),
                    createHeaderWithAcceptTypeAtSystemValue(getApiSubscriptionKey(), acceptTypeVal),
                    getUrlParams(),
                    getHttpMethod(),
                    HttpStatus.NOT_ACCEPTABLE,
                    getApiName(),
                    null);
    }

    @ParameterizedTest(name = "Request Type System Header with valid values - Value : {0}")
    @ValueSource(strings = {"Assault", "Theft"})
    public void test_request_type_at_with_valid_values(String requestType) throws Exception {
            commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                    getRelativeURL(), getInputPayloadFileName(),
                    createHeaderWithRequestTypeAtSystemValue(getApiSubscriptionKey(), requestType),
                    getUrlParams(),
                    getHttpMethod(),
                    getHttpSucessStatus(),
                    getApiName(),
                    null);
    }

    @ParameterizedTest(name = "Request Processed At System Header With Valid Date Format - Param : {0} --> {1}")
    @CsvSource({"Valid_Date_Format,2002-10-02T10:00:00-05:00",
            "Valid_Date_Format,2002-10-02T15:00:00Z",
            "Valid_Date_Format,2002-10-02T15:00:00.05Z",
            "Valid_Date_Format,2019-10-12 07:20:50.52Z"
    })
    public void test_request_processed_at_with_valid_values(String requestProcessedAtKey, String requestProcessedAtVal) throws Exception {
            commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                    getRelativeURL(), getInputPayloadFileName(),
                    createHeaderWithRequestProcessedAtSystemValue(getApiSubscriptionKey(), requestProcessedAtVal),
                    getUrlParams(),
                    getHttpMethod(),
                    getHttpSucessStatus(),
                    getApiName(),
                    null);
    }

    @ParameterizedTest(name = "Request Created At System Header With Valid Date Format - Param : {0} --> {1}")
    @CsvSource({"Valid_Date_Format, 2012-03-19T07:22:00Z", "Valid_Date_Format, 2002-10-02T15:00:00Z",
            "Valid_Date_Format, 2002-10-02T15:00:00.05Z",
            "Valid_Date_Format, 2019-10-12 07:20:50.52Z"})
    public void test_request_created_at_with_valid_values(String requestCreatedAtKey, String requestCreatedAtVal) throws Exception {
      commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                    getRelativeURL(), getInputPayloadFileName(),
                    createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), requestCreatedAtVal),
                    getUrlParams(),
                    getHttpMethod(),
                    getHttpSucessStatus(),
                    getApiName(),
                    null);

    }

    @ParameterizedTest(name = "Deprecated System headers with valid values - Param : {0} --> {1}")
    @CsvSource({"X-Accept, application/json",
            "X-Source-System, CFT",
            "X-Destination-System, S&L",
            "X-Request-Created-At, 2012-03-19T07:22:00Z",
            "X-Request-Processed-At, 2012-03-19T07:22:00Z",
            "X-Request-Type, Assault"
    })
    public void test_deprecated_header_values(String deprecatedHeaderKey, String deprecatedHeaderVal) throws Exception {
        final HttpStatus httpStatus = deprecatedHeaderKey.equalsIgnoreCase("X-Accept")?HttpStatus.NOT_ACCEPTABLE:HttpStatus.BAD_REQUEST;

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDeprecatedHeaderValue(getApiSubscriptionKey(), deprecatedHeaderKey, deprecatedHeaderVal),
                getUrlParams(),
                getHttpMethod(),
                httpStatus,
                getApiName(),
                null);

    }*/
}
