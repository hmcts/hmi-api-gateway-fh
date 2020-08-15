package uk.gov.hmcts.futurehearings.hmi.acceptance.hearing;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonPostDelegate.test_expected_response_for_supplied_header_in_a_post;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonPostDelegate.test_source_system_empty_in_a_post;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonPostDelegate.test_source_system_nulled_in_a_post;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonPostDelegate.test_source_system_removed_in_a_post;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonPostDelegate.test_successful_response_in_a_post;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardBuinessHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardSytemHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMap;

import uk.gov.hmcts.futurehearings.hmi.Application;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses(uk.gov.hmcts.futurehearings.hmi.acceptance.hearing.RequestHearingValidationTest.class)
@IncludeTags("Post")
public class RequestHearingValidationTest extends HearingValidationTest {

    @Test
    @DisplayName("A Request Hearing message sucessfully validated")
    public void test_successful_response_post() throws Exception {
        test_successful_response_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(),"mock-demo-request.json");
    }


    @Test
    @DisplayName("A Request Hearing message with no Source System defined in the Header")
    public void test_source_system_removed() throws Exception {
        test_source_system_removed_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "mock-demo-request.json");
    }

    @Test
    @DisplayName("A Request Hearing message with a Source System defined in the Header as Null")
    public void test_source_system_nulled() throws Exception {
        test_source_system_nulled_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "mock-demo-request.json");
    }

    @Test
    @DisplayName("A Request Hearing message with a Source System defined in the Header as Empty")
    public void test_source_system_empty() throws Exception {
        test_source_system_empty_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "mock-demo-request.json");
    }

    @Test
    @DisplayName("A Request Hearing message with a Source System is CFT defined as a Single Char String")
    public void test_supplied_source_system() throws Exception {
       /* Map<String,String> standardHeaderMap = convertToMap(buildStandardSytemHeaderPart(MediaType.APPLICATION_JSON_VALUE,
                                        null,
                                    null,
                                    null,
                                    getApiSubscriptionKey(),
                                        null),
        buildStandardBuinessHeaderPart("Qwerty",
                                        "sampledate",
                                "CFT",
                "S&L",
                "SomeString"));*/
        Map<String,String> standardHeaderMap = convertToMap(buildStandardSytemHeaderPart(MediaType.APPLICATION_JSON_VALUE,
                null,
                null,
                null,
                getApiSubscriptionKey(),
                null),
                buildStandardBuinessHeaderPart("Qwerty",
                        "sampledate",
                        "SnL",
                        "CFT",
                        "THEFT"));
        test_expected_response_for_supplied_header_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "mock-demo-request.json",standardHeaderMap,HttpStatus.OK);
    }
    @Test
    @DisplayName("A Request Hearing message with a Source System defined in the Header as Empty")
    public void test_invalidURL() throws Exception {
        test_source_system_empty_in_a_post(getApiSubscriptionKey(),
                getRelativeURL(), "mock-demo-request.json");
    }
}
