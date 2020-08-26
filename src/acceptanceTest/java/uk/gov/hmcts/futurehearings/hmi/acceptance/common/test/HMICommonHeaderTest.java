package uk.gov.hmcts.futurehearings.hmi.acceptance.common.test;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithAcceptTypeAtSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithAllValuesEmpty;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithAllValuesNull;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithContentTypeAtSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithCorruptedHeaderKey;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithDestinationSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithRemovedHeaderKey;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithRequestCreatedAtSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithRequestProcessedAtSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithRequestTypeAtSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithSourceSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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
    private String apiName;
    private String inputPayloadFileName;
    private Map<String, String> urlParams;

    private static final String NULL_CHECK = null ;
    private static final String EMPTY_VALUE_CHECK = "";
    private static final String ONE_SPACE_VALUE_CHECK = " ";
    private static final String RANDOM_VALUE_CHECK = "RANDOMVALUE";
    private static final String REQUEST_TYPE_ASSAULT = "ASSAULT";
    private static final String REQUEST_TYPE_THEFT = "THEFT";


    @Autowired(required = false)
    public CommonDelegate commonDelegate;

    @Test
    @DisplayName("Message successfully validated")
    public void test_successful_response() throws Exception {
        log.info("Message successfully validated");
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                getHttpSucessStatus(), getApiName(),null);
    }

    @Test
    @DisplayName("Message with a proper Header but an Improper URL to replicate a NOT FOUND")
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
    @DisplayName("Message with a proper Header but an Improper URL to replicate a NOT FOUND")
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
    @DisplayName("Message with Subscription Key Truncated in the Header")
    public void test_subscription_key_truncated() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                Arrays.asList("Ocp-Apim-Subscription-Key")), getUrlParams(), getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Missing/Invalid Header Source-System");
    }

    @Test
    @DisplayName("Message with Subscription Key Invalid(Null,Empty,Spaced or Wrong Value) Header")
    public void test_subscription_key_invalid_values() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(null),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(""),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader("  "),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Missing/Invalid Header Source-System");

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

    @Test
    @Order(6)
    @DisplayName("Message with Source System Header Invalid(Null,Empty,Spaced or Wrong Values(S&L,SNL)) Header")
    public void test_source_system_invalid_values() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(getApiSubscriptionKey(),null),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(getApiSubscriptionKey(),""),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(getApiSubscriptionKey()," "),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(getApiSubscriptionKey(),"S&L"),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(getApiSubscriptionKey(),"SNL"),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
    }

    @Test
    @DisplayName("Message with Destination System Header Invalid(Null,Empty,Spaced or Wrong Values(CFT,SNL)) Header")
    public void test_destination_system_invalid_values() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey(),null),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey(),""),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey()," "),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey(),"CFT"),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey(),"SNL"),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
    }

    @Test
    @DisplayName("Message with Request Created At System Header Invalid (Null,Empty,Spaced or Wrong Values Header)")
    public void test_request_created_at_invalid_values() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), null),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), ""),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), " "),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), "value"),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), "2002-02-31T10:00:30-05:00Z"),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), "2002-02-31T1000:30-05:00"),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), "2002-02-31T10:00-30-05:00"),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), "2002-02-31 10:00-30-05:00"),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), "2002-10-02T15:00:00*05Z"),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), "2002-10-02 15:00?0005Z"),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), "2002-10-02T15:00:00"),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
    }

    @Test
    @DisplayName("Message with mandatory Keys truncated from the Header")
    public void test_header_keys_truncated() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Content-Type")),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Accept")),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.NOT_ACCEPTABLE,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Source-System")),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Destination-System")),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Request-Created-At")),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Request-Processed-At")),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Request-Type")),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");
    }

    @Test
    @DisplayName("Message with mandatory keys removed from the Header")
    public void test_with_keys_removed_from_header() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Content-Type")),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Accept")),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.NOT_ACCEPTABLE,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Source-System")),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Destination-System")),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Request-Created-At")),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Request-Processed-At")),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Request-Type")),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");
    }

    @Test
    @DisplayName("Message with 'Request Processed At' System Header Invalid Values (Null,Empty,Spaced or Incorrect date format values)")
    public void test_request_processed_at_with_invalid_values() throws Exception {
        final List<String> requestProcessedTestValues = new ArrayList<String>();
        requestProcessedTestValues.add(NULL_CHECK);
        requestProcessedTestValues.add(EMPTY_VALUE_CHECK);
        requestProcessedTestValues.add(ONE_SPACE_VALUE_CHECK);
        requestProcessedTestValues.add(RANDOM_VALUE_CHECK);
        requestProcessedTestValues.add("2002-02-31T10:00:30-05:00Z");
        requestProcessedTestValues.add("2002-02-31T1000:30-05:00");
        requestProcessedTestValues.add("2002-02-31T10:00-30-05:00");
        requestProcessedTestValues.add("2002-10-02T15:00:00*05Z");
        requestProcessedTestValues.add("2002-10-02 15:00?0005Z");

        for (String testValue : requestProcessedTestValues) {
            System.out.println(testValue);
            commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                    getRelativeURL(), getInputPayloadFileName(),
                    createHeaderWithRequestProcessedAtSystemValue(getApiSubscriptionKey(), testValue),
                    getUrlParams(),
                    getHttpMethod(),
                    HttpStatus.BAD_REQUEST,
                    getApiName(),
                    null);
        }
    }

    @Test
    @DisplayName("Message with 'Request Type' System Header Invalid Values(Null,Empty,Spaced or Incorrect value other than Assault or Theft)")
    public void test_request_type_at_with_invalid_values() throws Exception {
        final List<String> requestTypeTestValues = new ArrayList<String>();
        requestTypeTestValues.add(NULL_CHECK);
        requestTypeTestValues.add(EMPTY_VALUE_CHECK);
        requestTypeTestValues.add(ONE_SPACE_VALUE_CHECK);
        requestTypeTestValues.add(RANDOM_VALUE_CHECK);

        for (String testValue : requestTypeTestValues) {
            commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                    getRelativeURL(), getInputPayloadFileName(),
                    createHeaderWithRequestTypeAtSystemValue(getApiSubscriptionKey(), testValue),
                    getUrlParams(),
                    getHttpMethod(),
                    HttpStatus.BAD_REQUEST,
                    getApiName(),
                    null);
        }
    }

    @Test
    @Disabled
    @DisplayName("Message with 'Content Type' System Header Invalid Values(Null,Empty,Spaced or Incorrect format)")
    public void test_content_type_at_with_invalid_values() throws Exception {
        final List<String> contentTypeTestValues = new ArrayList<String>();
        //contentTypeTestValues.add(NULL_CHECK);
        //contentTypeTestValues.add(EMPTY_VALUE_CHECK);
        //contentTypeTestValues.add(ONE_SPACE_VALUE_CHECK);
        //contentTypeTestValues.add(RANDOM_VALUE_CHECK);
        contentTypeTestValues.add("application/pdf");

        for (String testValue : contentTypeTestValues) {
            commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                    getRelativeURL(), getInputPayloadFileName(),
                    createHeaderWithContentTypeAtSystemValue(getApiSubscriptionKey(), testValue),
                    getUrlParams(),
                    getHttpMethod(),
                    HttpStatus.BAD_REQUEST,
                    getApiName(),
                    null);
        }
    }

    @Test
    @DisplayName("Message with 'Accept' System Header Invalid Values(Null,Empty,Spaced or Incorrect format)")
    public void test_accept_at_with_invalid_values() throws Exception {
        final List<String> acceptTestValues = new ArrayList<String>();
        acceptTestValues.add(NULL_CHECK);
        acceptTestValues.add(EMPTY_VALUE_CHECK);
        acceptTestValues.add(ONE_SPACE_VALUE_CHECK);
        acceptTestValues.add(RANDOM_VALUE_CHECK);
        acceptTestValues.add("application/pdf");

        for (String testValue : acceptTestValues) {
            commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                    getRelativeURL(), getInputPayloadFileName(),
                    createHeaderWithAcceptTypeAtSystemValue(getApiSubscriptionKey(), testValue),
                    getUrlParams(),
                    getHttpMethod(),
                    HttpStatus.NOT_ACCEPTABLE,
                    getApiName(),
                    null);
        }
    }

    @Test
    @DisplayName("Message with 'Request Type' System Header valid values(Assault or Theft)")
    public void test_request_type_at_with_valid_values() throws Exception {
        final List<String> requestTypeValidValues = new ArrayList<String>();
        requestTypeValidValues.add(REQUEST_TYPE_ASSAULT);
        requestTypeValidValues.add(REQUEST_TYPE_THEFT);

        for (String testValue : requestTypeValidValues) {
            commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                    getRelativeURL(), getInputPayloadFileName(),
                    createHeaderWithRequestTypeAtSystemValue(getApiSubscriptionKey(), testValue),
                    getUrlParams(),
                    getHttpMethod(),
                    getHttpSucessStatus(),
                    getApiName(),
                    null);
        }
    }

    @Test
    @DisplayName("Message with 'Request Processed At' System Header valid values")
    public void test_request_processed_at_with_valid_values() throws Exception {
        final List<String> requestProcessedValidValues = new ArrayList<String>();
        requestProcessedValidValues.add("2002-10-02T10:00:00-05:00");
        requestProcessedValidValues.add("2002-10-02T15:00:00Z");
        requestProcessedValidValues.add("2002-10-02T15:00:00.05Z");
        requestProcessedValidValues.add("2019-10-12 07:20:50.52Z");

        for (String testValue : requestProcessedValidValues) {
            commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                    getRelativeURL(), getInputPayloadFileName(),
                    createHeaderWithRequestProcessedAtSystemValue(getApiSubscriptionKey(), testValue),
                    getUrlParams(),
                    getHttpMethod(),
                    getHttpSucessStatus(),
                    getApiName(),
                    null);
        }
    }

    @Test
    @DisplayName("Message with 'Request Created At' System Header valid values")
    public void test_request_created_at_with_valid_values() throws Exception {
        final List<String> requestCreatedValidValues = new ArrayList<String>();
        requestCreatedValidValues.add("2002-10-02T10:00:00-05:00");
        requestCreatedValidValues.add("2002-10-02T15:00:00Z");
        requestCreatedValidValues.add("2002-10-02T15:00:00.05Z");
        requestCreatedValidValues.add("2019-10-12 07:20:50.52Z");

        for (String testValue : requestCreatedValidValues) {
            commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                    getRelativeURL(), getInputPayloadFileName(),
                    createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), testValue),
                    getUrlParams(),
                    getHttpMethod(),
                    getHttpSucessStatus(),
                    getApiName(),
                    null);
        }
    }
}
