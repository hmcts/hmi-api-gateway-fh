package uk.gov.hmcts.futurehearings.hmi.acceptance.common.test;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithAllValuesEmpty;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithAllValuesNull;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithCorruptedHeaderKey;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithDestinationSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithRemovedHeaderKey;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithRequestCreatedAtSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithSourceSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;

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
    private Map<String, String> urlParams;

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
                getUrlParams(),
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
                getUrlParams(),
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
    @Order(4)
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
    @Order(5)
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
    @Order(7)
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
    @Order(8)
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
    @Order(9)
    @DisplayName("Message with mandatory Keys Truncated from the Header")
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
    @Order(10)
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
}
