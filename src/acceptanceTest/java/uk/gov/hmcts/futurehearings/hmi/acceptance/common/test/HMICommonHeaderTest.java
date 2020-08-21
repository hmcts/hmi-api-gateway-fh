package uk.gov.hmcts.futurehearings.hmi.acceptance.common.test;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.createPayloadHeaderNullFields;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.createPayloadHeaderRemoveFields;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithAllValuesEmpty;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithAllValuesNull;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithEmptyRequestCreatedAt;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithLongRequestCreatedAt;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithNullRequestCreatedAt;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithSingleCharRequestCreatedAt;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithSourceSystemValueAsCFT;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createHeaderWithSpacedRequestCreatedAt;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.HearingHeaderHelper.createStandardPayloadHeader;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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
    private HttpMethod httpMethod;
    private String inputPayloadFileName;

    @Autowired(required = false)
    public CommonDelegate commonDelegate;

    @Test
    @DisplayName("Message successfully validated")
    public void test_successful_response() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getHttpMethod(),
                HttpStatus.OK, null);
    }

    @Test
    @DisplayName("Message with no Source System defined in the Header")
    public void test_source_system_removed() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createPayloadHeaderRemoveFields(getApiSubscriptionKey(),
                Arrays.asList("Source-System")), getHttpMethod(),
                HttpStatus.BAD_REQUEST, "Missing/Invalid Header Source-System");
    }

    @Disabled("This Test May have to be done manually as Rest Assured Does not accept a 'Null Content-Type Header' in the Request Header")
    @Test
    @DisplayName("Message with all Header Values Populated as Nulls")
    public void test_supplied_all_headers_null() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithAllValuesNull(getApiSubscriptionKey()),
                getHttpMethod(),
                HttpStatus.UNAUTHORIZED, null);
    }

    @Disabled("This Test May have to be done manually as Rest Assured Does not accept a Empty Content Type in the Request Header")
    @Test
    @DisplayName("Message with all Header Values Populated as Nulls")
    public void test_supplied_all_headers_empty() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithAllValuesEmpty(getApiSubscriptionKey()),
                getHttpMethod(),
                HttpStatus.UNAUTHORIZED, null);
    }

    @Test
    @DisplayName("Message with a proper Header but an Improper URL to replicate a NOT FOUND")
    public void test_invalidURL() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL().replace("hearings","hearing"),
                //Performed a near to the Real URL Transformation
                getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getHttpMethod(),
                HttpStatus.NOT_FOUND, "Resource not found");
    }

    @Test
    @DisplayName("Message with a Source System defined with value 'CFT'")
    public void test_supplied_source_system() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSourceSystemValueAsCFT(getApiSubscriptionKey()),
                getHttpMethod(),
                HttpStatus.OK, null);
    }

    @Test
    @DisplayName("Message with a Request Created At as Null")
    public void test_supplied_request_created_at_as_null() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithNullRequestCreatedAt(getApiSubscriptionKey()),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, "Resource not found");
    }

    @Test
    @DisplayName("Message with a Request Created At as Empty")
    public void test_supplied_request_created_at_as_empty() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithEmptyRequestCreatedAt(getApiSubscriptionKey()),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, "Missing or invalid header 'Request-Created-At'");
    }

    @Test
    @DisplayName("Message with a Request Created At as a Space")
    public void test_supplied_request_created_at_as_spaced() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSpacedRequestCreatedAt(getApiSubscriptionKey()),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, "Resource not found");
    }

    @Disabled("TODO - Had to Disable this test as the Headers were brought back to Source due to a Pipeline build Overwrite or so")
    @Test
    @DisplayName("Message with a Request Created At as a Single Character")
    public void test_supplied_request_created_at_as_single_character() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithSingleCharRequestCreatedAt(getApiSubscriptionKey()),
                getHttpMethod(),
                HttpStatus.OK, "Resource not found");
    }

    @Disabled("TODO - Had to Disable this test as the Headers were brought back to Source due to a Pipeline build Overwrite or so")
    @Test
    @DisplayName("Message with a Request Created At as a Long String")
    public void test_supplied_request_created_at_as_long_string() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithLongRequestCreatedAt(getApiSubscriptionKey()),
                getHttpMethod(),
                HttpStatus.OK, "Resource not found");
    }

    @Test
    @DisplayName("Message with a Source System defined in the Header as Null")
    public void test_source_system_nulled() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createPayloadHeaderNullFields(getApiSubscriptionKey(),
                        Arrays.asList("Source-System")),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, "Missing/Invalid Header Source-System");
    }

    @Test
    @DisplayName("Message with a Source System defined in the Header as Empty")
    public void test_source_system_empty() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createPayloadHeaderNullFields(getApiSubscriptionKey(), Arrays.asList("Source-System")),
                getHttpMethod(), HttpStatus.BAD_REQUEST, "Missing/Invalid Header Source-System");
    }
}
