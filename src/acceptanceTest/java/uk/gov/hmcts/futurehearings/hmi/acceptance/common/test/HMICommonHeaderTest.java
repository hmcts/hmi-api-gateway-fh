package uk.gov.hmcts.futurehearings.hmi.acceptance.common.test;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.createPayloadHeaderNullFields;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithAllValuesEmpty;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithAllValuesNull;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithCorruptedHeaderKey;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithEmptyRequestCreatedAt;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithLongRequestCreatedAt;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithNullRequestCreatedAt;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithSingleCharRequestCreatedAt;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithSourceSystemValue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithSpacedRequestCreatedAt;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createStandardPayloadHeader;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;

import java.util.Arrays;

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
                createStandardPayloadHeader(null), getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(""), getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader("  "), getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Missing/Invalid Header Source-System");

        commonDelegate.test_expected_response_for_supplied_header(
                getApiSubscriptionKey().substring(0,getApiSubscriptionKey().length()-1),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader("  "), getHttpMethod(),
                HttpStatus.UNAUTHORIZED,
                getApiName(),
                "Missing/Invalid Header Source-System");
    }
    @Test
    @Order(6)
    @DisplayName("Message with Subscription Key Invalid(Null,Empty,Spaced or Wrong Values(S&L,SNL)) Header")
    public void test_source_system_invalid_values() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(getApiSubscriptionKey(),null),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(getApiSubscriptionKey(),""),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(getApiSubscriptionKey()," "),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(getApiSubscriptionKey(),"S&L"),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValue(getApiSubscriptionKey(),"SNL"),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
    }

    @Test
    @DisplayName("Message with a Request Created At as Null")
    public void test_supplied_request_created_at_as_null() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithNullRequestCreatedAt(getApiSubscriptionKey()),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Resource not found");
    }

    @Test
    @DisplayName("Message with a Request Created At as Empty")
    public void test_supplied_request_created_at_as_empty() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithEmptyRequestCreatedAt(getApiSubscriptionKey()),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing or invalid header 'Request-Created-At'");
    }

    @Test
    @DisplayName("Message with a Request Created At as a Space")
    public void test_supplied_request_created_at_as_spaced() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSpacedRequestCreatedAt(getApiSubscriptionKey()),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Resource not found");
    }

    @Disabled("TODO - Had to Disable this test as the Headers were brought back to Source due to a Pipeline build Overwrite or so")
    @Test
    @DisplayName("Message with a Request Created At as a Single Character")
    public void test_supplied_request_created_at_as_single_character() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSingleCharRequestCreatedAt(getApiSubscriptionKey()),
                getHttpMethod(),
                HttpStatus.OK,
                getApiName(),
                "Resource not found");
    }

    @Disabled("TODO - Had to Disable this test as the Headers were brought back to Source due to a Pipeline build Overwrite or so")
    @Test
    @DisplayName("Message with a Request Created At as a Long String")
    public void test_supplied_request_created_at_as_long_string() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithLongRequestCreatedAt(getApiSubscriptionKey()),
                getHttpMethod(),
                HttpStatus.OK,
                getApiName(),
                "Resource not found");
    }

    @Test
    @DisplayName("Message with a Source System defined in the Header as Null")
    public void test_source_system_nulled() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createPayloadHeaderNullFields(getApiSubscriptionKey(),
                        Arrays.asList("Source-System")),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");
    }

    @Test
    @DisplayName("Message with a Source System defined in the Header as Empty")
    public void test_source_system_empty() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createPayloadHeaderNullFields(getApiSubscriptionKey(), Arrays.asList("Source-System")),
                getHttpMethod(), HttpStatus.BAD_REQUEST,
                getApiName(),
                "Missing/Invalid Header Source-System");
    }
}
