package uk.gov.hmcts.futurehearings.hmi.acceptance.common.test;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithAllValuesEmpty;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithAllValuesNull;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithCorruptedHeaderKey;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithDestinationSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithRemovedHeaderKey;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithRequestCreatedAtSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithSourceSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createStandardPayloadHeader;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;

import java.util.Arrays;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
    private Map<String, String> params;

    @Autowired(required = false)
    public CommonDelegate commonDelegate;

    @Test
    @Order(1)
    @DisplayName("Message successfully validated")
    public void test_successful_response() throws Exception {
        log.info("Message successfully validated");
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getParams(),
                getHttpMethod(),
                getHttpSucessStatus(), getApiName(),null);
    }

    @Test
    @Order(2)
    @DisplayName("Message with a proper Header but an Improper URL to replicate a NOT FOUND")
    public void test_invalid_URL() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURLForNotFound(),
                //Performed a near to the Real URL Transformation
                getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getParams(),
                getHttpMethod(),
                HttpStatus.NOT_FOUND, getApiName(),"Resource not found");
    }

    @Test
    @Order(3)
    @DisplayName("Message with a proper Header but an Improper URL to replicate a NOT FOUND")
    public void test_no_headers_populated() throws Exception {
        //2 Sets of Headers Tested - Nulls and Empty
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(),
                getInputPayloadFileName(),
                createHeaderWithAllValuesEmpty(),
                //The Content Type Has to be Populated for Rest Assured to function properly
                //So this Test was manually executed in Postman Manually as well with the same Order Number
                getParams(),
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
                getParams(),
                getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Resource not found");
    }

    @Test
    @Order(4)
    @DisplayName("Message with Subscription Key Truncated in the Header")
    public void test_subscription_key_truncated() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(), getParams(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                Arrays.asList("Ocp-Apim-Subscription-Key")), getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Missing/Invalid Header Source-System");
    }

    @Test
    @Order(5)
    @DisplayName("Message with Subscription Key Invalid(Null,Empty,Spaced or Wrong Value) Header")
    public void test_subscription_key_invalid_values() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(null),
                getParams(),
                getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(""),
                getParams(),
                getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader("  "),
                getParams(),
                getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(
                getApiSubscriptionKey().substring(0,getApiSubscriptionKey().length()-1),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader("  "),
                getParams(),
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
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(getApiSubscriptionKey(),""),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(getApiSubscriptionKey()," "),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(getApiSubscriptionKey(),"S&L"),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(getApiSubscriptionKey(),"SNL"),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
    }

    @Test
    @Order(7)
    @DisplayName("Message with Destination System Header Invalid(Null,Empty,Spaced or Wrong Values(CFT,SNL)) Header")
    public void test_destination_system_invalid_values() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey(),null),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey(),""),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey()," "),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey(),"CFT"),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey(),"SNL"),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
    }

    @Test
    @Order(8)
    @DisplayName("Message with Request Created At System Header Invalid(Null,Empty,Spaced or Wrong Values Header")
    public void test_request_created_at_invalid_values() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), null),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), ""),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), " "),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), "value"),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRequestCreatedAtSystemValue(getApiSubscriptionKey(), "2002-02-31T10:00:30-05:00Z"),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
    }

    @Test
    @Order(9)
    @DisplayName("Message with mandatory Keys Truncated from the Header")
    public void test_all_other_keys_truncated() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Content-Type")),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Accept")),
                getParams(),
                getHttpMethod(),
                HttpStatus.NOT_ACCEPTABLE,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Source-System")),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Destination-System")),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Request-Created-At")),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Request-Processed-At")),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithCorruptedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Request-Type")),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");
    }

    @Test
    @Order(10)
    @DisplayName("Message with mandatory Keys Removed from the Header")
    public void test_with_keys_removed_from_header() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Content-Type")),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Accept")),
                getParams(),
                getHttpMethod(),
                HttpStatus.NOT_ACCEPTABLE,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Source-System")),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Destination-System")),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Request-Created-At")),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Request-Processed-At")),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithRemovedHeaderKey(getApiSubscriptionKey(),
                        Arrays.asList("Request-Type")),
                getParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");
    }
}
