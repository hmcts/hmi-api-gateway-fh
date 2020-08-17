package uk.gov.hmcts.futurehearings.hmi.acceptance.common.test;

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

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Slf4j
@Setter
@Getter
public abstract class HMICommonHeaderTest {

    private String apiSubscriptionKey;
    private String relativeURL;

    @Autowired(required = false)
    public CommonDelegate commonDelegate;

    @Test
    public void test () throws Exception {
        commonDelegate.test_successful_response_in_a_post_test(null,null,null);
    }

    @Disabled("TODO - Had to Disable this test as the Headers were brought back to Source due to a Pipeline build Overwrite or so")
    @Test
    @DisplayName("A Request Hearing message sucessfully validated")
    public void test_successful_response_post() throws Exception {
        commonDelegate.test_successful_response_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(),"hearing-request-standard.json");
    }

    @Test
    @DisplayName("A Request Hearing message with no Source System defined in the Header")
    public void test_source_system_removed() throws Exception {
        commonDelegate.test_source_system_removed_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json");
    }

    @Disabled("This Test May have to be done manually as Rest Assured Does not accept a Null Content Type in the Request Header")
    @Test
    @DisplayName("A Request Hearing message with all Header Values Populated as Nulls")
    public void test_supplied_all_headers_null() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithAllValuesNull(getApiSubscriptionKey()),
                HttpStatus.UNAUTHORIZED);
    }

    @Disabled("This Test May have to be done manually as Rest Assured Does not accept a Empty Content Type in the Request Header")
    @Test
    @DisplayName("A Request Hearing message with all Header Values Populated as Nulls")
    public void test_supplied_all_headers_empty() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithAllValuesEmpty(getApiSubscriptionKey()),
                HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("A Request Hearing message with a proper Header but an Improper URL to replicate a NOT FOUND")
    public void test_invalidURL() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL().replace("hearings","hearing"),
                //Performed a near to the Real URL Transformation
                "hearing-request-standard.json",
                createStandardPayloadHeader(getApiSubscriptionKey()),
                HttpStatus.NOT_FOUND);
    }

    @Disabled("Test is not working as the API seems to be not accepting CFT as a Source System")
    @Test
    @DisplayName("A Request Hearing message with a Source System defined with value 'CFT'")
    public void test_supplied_source_system() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithSourceSystemValueAsCFT(getApiSubscriptionKey()),
                HttpStatus.OK);
    }

    @Test
    @DisplayName("A Request Hearing message with a Request Created At as Null")
    public void test_supplied_request_created_at_as_null() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithNullRequestCreatedAt(getApiSubscriptionKey()),
                HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("A Request Hearing message with a Request Created At as Empty")
    public void test_supplied_request_created_at_as_empty() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithEmptyRequestCreatedAt(getApiSubscriptionKey()),
                HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("A Request Hearing message with a Request Created At as a Space")
    public void test_supplied_request_created_at_as_spaced() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithSpacedRequestCreatedAt(getApiSubscriptionKey()),
                HttpStatus.UNAUTHORIZED);
    }

    @Disabled("TODO - Had to Disable this test as the Headers were brought back to Source due to a Pipeline build Overwrite or so")
    @Test
    @DisplayName("A Request Hearing message with a Request Created At as a Single Character")
    public void test_supplied_request_created_at_as_single_character() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithSingleCharRequestCreatedAt(getApiSubscriptionKey()),
                HttpStatus.OK);
    }

    @Disabled("TODO - Had to Disable this test as the Headers were brought back to Source due to a Pipeline build Overwrite or so")
    @Test
    @DisplayName("A Request Hearing message with a Request Created At as a Long String")
    public void test_supplied_request_created_at_as_long_string() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithLongRequestCreatedAt(getApiSubscriptionKey()),
                HttpStatus.OK);
    }

    @Test
    @DisplayName("A Request Hearing message with a Source System defined in the Header as Null")
    public void test_source_system_nulled() throws Exception {
        commonDelegate.test_source_system_nulled_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json");
    }

    @Test
    @DisplayName("A Request Hearing message with a Source System defined in the Header as Empty")
    public void test_source_system_empty() throws Exception {
        commonDelegate.test_source_system_empty_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json");
    }
}
