package uk.gov.hmcts.futurehearings.hmi.acceptance.common.test;

import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMIErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMIUnsupportedDestinationsErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMICommonSuccessVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMISuccessVerifier;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.*;

@Slf4j
@Setter
@Getter
@SuppressWarnings("java:S5786")
public abstract class HMICommonHeaderTest {
    private String authorizationToken;
    private String relativeURL;
    private HttpMethod httpMethod;
    private HttpStatus httpSuccessStatus;
    private String inputFileDirectory;
    private String outputFileDirectory;
    private String inputPayloadFileName;
    private Map<String, String> urlParams;
    private String sourceSystem;
    private String destinationSystem;
    private final String[] AllAvailableDestinations = {"VH", "SNL", "CFT", "CRIME","ELINKS", "RM", "PIH", "HMI-DTU"};

    protected String[] unsupportedDestinations;
    private boolean CheckUnsupportedDestinations;

    @Autowired(required = false)
    public CommonDelegate commonDelegate;

    public HMISuccessVerifier hmiSuccessVerifier;

    public HMIErrorVerifier hmiErrorVerifier;

    public HMIUnsupportedDestinationsErrorVerifier hmiUnsupportedDestinationsErrorVerifier;

    @BeforeAll
    public void beforeAll(TestInfo info) {
        log.debug("Test execution Class Initiated: " + info.getTestClass().get().getName());
        sourceSystem = "CFT";
        CheckUnsupportedDestinations = false;
        setHmiSuccessVerifier(new HMICommonSuccessVerifier());
        setHmiErrorVerifier(new HMICommonErrorVerifier());
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

    protected void extractUnsupportedDestinations(String[] supportedDestinations) {
        var updatedArrayItems = RemoveItemsFromArray(AllAvailableDestinations.clone(), supportedDestinations);
        setUnsupportedDestinations(updatedArrayItems);
    }

    //For individual API end points, Destination-System Header valid values are different and
    //unsupported destinations may be all or a subset of the eight of these {"VH", "SNL", "CFT", "CRIME", "ELINKS", "RM", "PIH", "HMI-DTU"}
    //This is to test specified unsupported destinations for the specified API end point URL
    @ParameterizedTest (name = "Testing unsupported destinations  - unsupportedDestinationSystem : {0}")
    @MethodSource ("getUnsupportedDestinationsMethodSource")
    void test_destination_system_unsupported_value(String unsupportedDestinationSystem) throws Exception {
        if (CheckUnsupportedDestinations) {
            setHmiUnsupportedDestinationsErrorVerifier(new HMIUnsupportedDestinationsErrorVerifier());
            commonDelegate.test_expected_response_for_supplied_header(
                    getAuthorizationToken(),
                    getRelativeURL(),
                    getInputPayloadFileName(),
                    createHeaderWithSourceAndDestinationSystemValues(sourceSystem, unsupportedDestinationSystem),
                    null,
                    getUrlParams(),
                    getHttpMethod(),
                    HttpStatus.BAD_REQUEST,
                    getInputFileDirectory(),
                    getHmiUnsupportedDestinationsErrorVerifier(),
                    unsupportedDestinationSystem + " destination doesn't support this functionality",
                    null);
        }
    }

    private String[] getUnsupportedDestinationsMethodSource() {
        return getUnsupportedDestinations();
    }

    @Test
    @DisplayName("Successfully validated response with all the header values")
    void test_successful_response_with_a_complete_header() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createCompletePayloadHeader(sourceSystem),
                null,
                getUrlParams(),
                getHttpMethod(),
                getHttpSuccessStatus(),
                getInputFileDirectory(),
                getHmiSuccessVerifier(), "The request was received successfully.",null);
    }
    
    @Test
    @DisplayName("Headers with all empty values")
    void test_no_headers_populated() throws Exception {
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
    }

    //Source-System Header Valid value are SNL, RM, MOCK, EMULATOR,CRIME and CFT - This can only be verified manually
    // and tested for dependant EMULATOR or End Systems being available
    @Test
    @DisplayName("Source System Header invalid value")
    void test_source_system_invalid_value() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue("R&M"),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getInputFileDirectory(),
                getHmiErrorVerifier(),
                "Missing/Invalid Header Source-System",null);
    }

    //Destination-System Header Valid value are SNL, RM, MOCK, EMULATOR,CRIME,CFT and ELINKS - This can only be verified manually and tested for
    //dependant Azure Mock,EMULATOR or End Systems being available
    @Test
    @DisplayName("Destination System Header invalid value")
    void test_destination_system_invalid_value() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceAndDestinationSystemValues(sourceSystem, "R&M"),
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
            "Invalid_Date_Format, 2019-10-12 07:20:50.52Z",
            "Invalid_Date_Format, 2019-10-12 07:20:Z",
            "Invalid_Date_Format, 2019-10-12 07:20:00",
            "Invalid_Date_Format, 2019-02-00T07:20:00Z",
            "Invalid_Date_Format, 2019-12-32T07:20:00Z",
            "Invalid_Date_Format, 2019-02-10T24:00:00Z",
            "Invalid_Date_Format, 2019-02-10T23:60:00Z",
            "Invalid_Date_Format, 2019-02-10T23:50:60Z",
            "Invalid_Date_Format, 2019-02-10T23:59:61Z",
            "Invalid_Date_Format, 20-02-10T07:20:00Z",
            "Invalid_Date_Format, 2019-10-12T07:20:00+01:00Z",
            "Invalid_Date_Format, 2019-10-12T07:20:00+01:61Z",
            "Invalid_Date_Format, 2019-10-12T07:20:00-02:00Z",
            "Invalid_Date_Format, 2019-10-12T07:20:00.00+01:00Z",
            "Invalid_Date_Format, 2019-10-12T07:20:00.00-01:00Z",
            "Invalid_Date_Format, 2019-10-12t07:20:00.00z",
    })
    void test_request_created_at_invalid_values(String requestCreatedAtKey, String requestCreatedAtVal) throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(requestCreatedAtVal, sourceSystem),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getInputFileDirectory(),
                getHmiErrorVerifier(),
                "Missing/Invalid Header Request-Created-At",null);
    }

    @ParameterizedTest(name = "Request Created At System Header valid values - Param : {0} --> {1}")
    @CsvSource({
            "Valid_Date_Format, 2019-10-12T07:20:00",
            "Valid_Date_Format, 2019-10-12T07:20:11.1111",
            "Valid_Date_Format, 2019-10-12T07:20:00+00:00",
            "Valid_Date_Format, 2019-10-12T07:20:00+01:00",
            "Valid_Date_Format, 2019-10-12T07:20:00+01:00",
            "Valid_Date_Format, 2019-10-12T07:20:00-02:00",
            "Valid_Date_Format, 2019-10-12T07:20:00.00",
            "Valid_Date_Format, 2019-10-12T07:20:00.00+01:00",
            "Valid_Date_Format, 2019-10-12T07:20:00.00-01:00",
            "Valid_Date_Format, 2019-10-12T07:20:00Z",
            "Valid_Date_Format, 2019-10-12T15:20:00Z",
            "Valid_Date_Format, 2019-10-12T07:20:00.00Z",
    })
    void test_request_created_at_with_valid_values(String requestCreatedAtKey, String requestCreatedAtVal) throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(requestCreatedAtVal, sourceSystem),
                null,
                getUrlParams(),
                getHttpMethod(),
                getHttpSuccessStatus(),
                getInputFileDirectory(),
                getHmiSuccessVerifier(),
                "The request was received successfully.",null);
    }

    @Test
    @DisplayName("Accept System Header invalid value")
    void test_accept_at_with_invalid_value() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithAcceptTypeAtSystemValue("application/pdf", sourceSystem),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.NOT_ACCEPTABLE,
                getInputFileDirectory(),
                getHmiErrorVerifier(),
                "Missing/Invalid Media Type",null);
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
        Map<String,String> duplicateHeaderField  = new HashMap<>();
        duplicateHeaderField.put(duplicateHeaderKey,duplicateHeaderValue);
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                null,
                createStandardPayloadHeaderWithDuplicateValues(duplicateHeaderField, sourceSystem),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getInputFileDirectory(),
                getHmiErrorVerifier(),
                expectedErrorMessage,null);
    }
}
