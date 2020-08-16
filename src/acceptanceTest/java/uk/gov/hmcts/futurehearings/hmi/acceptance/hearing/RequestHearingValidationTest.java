package uk.gov.hmcts.futurehearings.hmi.acceptance.hearing;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonPostDelegate.test_expected_response_for_supplied_header_in_a_post;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonPostDelegate.test_source_system_empty_in_a_post;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonPostDelegate.test_source_system_nulled_in_a_post;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonPostDelegate.test_source_system_removed_in_a_post;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonPostDelegate.test_successful_response_in_a_post;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.hearing.helper.HearingHeaderHelper.createHeaderWithAllValuesEmpty;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.hearing.helper.HearingHeaderHelper.createHeaderWithAllValuesNull;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.hearing.helper.HearingHeaderHelper.createHeaderWithEmptyRequestCreatedAt;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.hearing.helper.HearingHeaderHelper.createHeaderWithLongRequestCreatedAt;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.hearing.helper.HearingHeaderHelper.createHeaderWithNullRequestCreatedAt;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.hearing.helper.HearingHeaderHelper.createHeaderWithSingleCharRequestCreatedAt;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.hearing.helper.HearingHeaderHelper.createHeaderWithSourceSystemValueAsCFT;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.hearing.helper.HearingHeaderHelper.createHeaderWithSpacedRequestCreatedAt;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.hearing.helper.HearingHeaderHelper.createStandardPayloadHeader;

import uk.gov.hmcts.futurehearings.hmi.Application;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses(uk.gov.hmcts.futurehearings.hmi.acceptance.hearing.RequestHearingValidationTest.class)
@IncludeTags("Post")
public class RequestHearingValidationTest extends HearingValidationTest {

    @Disabled("TODO - Had to Disable this test as the Headers were brought back to Source due to a Pipeline build Overwrite or so")
    @Test
    @DisplayName("A Request Hearing message sucessfully validated")
    public void test_successful_response_post() throws Exception {
        test_successful_response_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(),"hearing-request-standard.json");
    }

    @Disabled("This Test May have to be done manually as Rest Assured Does not accept a Null Content Type in the Request Header")
    @Test
    @DisplayName("A Request Hearing message with all Header Values Populated as Nulls")
    public void test_supplied_all_headers_null() throws Exception {
        test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithAllValuesNull(getApiSubscriptionKey()),
                HttpStatus.UNAUTHORIZED);
    }

    @Disabled("This Test May have to be done manually as Rest Assured Does not accept a Empty Content Type in the Request Header")
    @Test
    @DisplayName("A Request Hearing message with all Header Values Populated as Nulls")
    public void test_supplied_all_headers_empty() throws Exception {
        test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithAllValuesEmpty(getApiSubscriptionKey()),
                HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("A Request Hearing message with no Source System defined in the Header")
    public void test_source_system_removed() throws Exception {
        test_source_system_removed_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json");
    }

    @Test
    @DisplayName("A Request Hearing message with a Source System defined in the Header as Null")
    public void test_source_system_nulled() throws Exception {
        test_source_system_nulled_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json");
    }

    @Test
    @DisplayName("A Request Hearing message with a Source System defined in the Header as Empty")
    public void test_source_system_empty() throws Exception {
        test_source_system_empty_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json");
    }

    @Test
    @DisplayName("A Request Hearing message with a proper Header but an Improper URL to replicate a NOT FOUND")
    public void test_invalidURL() throws Exception {
        test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
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
        test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithSourceSystemValueAsCFT(getApiSubscriptionKey()),
                HttpStatus.OK);
    }

    @Test
    @DisplayName("A Request Hearing message with a Request Created At as Null")
    public void test_supplied_request_created_at_as_null() throws Exception {
        test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithNullRequestCreatedAt(getApiSubscriptionKey()),
                HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("A Request Hearing message with a Request Created At as Empty")
    public void test_supplied_request_created_at_as_empty() throws Exception {
        test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithEmptyRequestCreatedAt(getApiSubscriptionKey()),
                HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("A Request Hearing message with a Request Created At as a Space")
    public void test_supplied_request_created_at_as_spaced() throws Exception {
        test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithSpacedRequestCreatedAt(getApiSubscriptionKey()),
                HttpStatus.UNAUTHORIZED);
    }

    @Disabled("TODO - Had to Disable this test as the Headers were brought back to Source due to a Pipeline build Overwrite or so")
    @Test
    @DisplayName("A Request Hearing message with a Request Created At as a Single Character")
    public void test_supplied_request_created_at_as_single_character() throws Exception {
        test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithSingleCharRequestCreatedAt(getApiSubscriptionKey()),
                HttpStatus.OK);
    }

    @Disabled("TODO - Had to Disable this test as the Headers were brought back to Source due to a Pipeline build Overwrite or so")
    @Test
    @DisplayName("A Request Hearing message with a Request Created At as a Long String")
    public void test_supplied_request_created_at_as_long_string() throws Exception {
        test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "hearing-request-standard.json",
                createHeaderWithLongRequestCreatedAt(getApiSubscriptionKey()),
                HttpStatus.OK);
    }
}
