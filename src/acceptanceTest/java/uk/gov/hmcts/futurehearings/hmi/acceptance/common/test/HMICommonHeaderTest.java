package uk.gov.hmcts.futurehearings.hmi.acceptance.common.test;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.createPayloadHeaderNullFields;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithAllValuesEmpty;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithAllValuesNull;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithCorruptedHeaderKey;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithDestinationSystemValue;
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
    @DisplayName("Message with Source System Header Invalid(Null,Empty,Spaced or Wrong Values(S&L,SNL)) Header")
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
    @Order(7)
    @DisplayName("Message with Destination System Header Invalid(Null,Empty,Spaced or Wrong Values(S&L,SNL)) Header")
    public void test_destination_system_invalid_values() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey(),null),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey(),""),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey()," "),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey(),"CFT"),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithDestinationSystemValue(getApiSubscriptionKey(),"SNL"),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                getApiName(),
                null);
    }
}
