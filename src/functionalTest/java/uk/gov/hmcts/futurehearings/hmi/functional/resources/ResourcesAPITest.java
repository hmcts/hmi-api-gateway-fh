package uk.gov.hmcts.futurehearings.hmi.functional.resources;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.TestingUtils.readFileContents;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.hearings.steps.HearingsSteps;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"In order to test that the Direct Listing Functionality is working properly",
        "As a tester",
        "I want to be able to execute the tests for Direct Listing a Hearing Request into a known Session"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class ResourcesAPITest extends FunctionalTest {

    public static final String HEARINGS_INPUT_PATH = "uk/gov/hmcts/futurehearings/hmi/functional/hearings/input";

    @Value("${hearings_rootContext}")
    protected String hearings_rootContext;

    @Value("${hearingsByID_rootContext}")
    protected String hearingsByID_rootContext;

    @Steps
    HearingsSteps hearingsSteps;

    @Test
    @Pending
    public void testRequestAndAmendAResourceByUser() throws IOException {

        log.debug("In the testRequestAndAmendAResourceByUser() method");
        String inputBodyForRequestHearing =
                String.format(readFileContents(HEARINGS_INPUT_PATH + "/POST-hearing-payload.json"), "615");
        hearingsSteps.shouldRequestAHearing(hearings_rootContext,
                headersAsMap,
                authorizationToken,
                inputBodyForRequestHearing);

        String inputBodyForAmendHearing =
                String.format(readFileContents(HEARINGS_INPUT_PATH + "/PUT-hearing-payload.json"), "615", "615");
        hearingsSteps.shouldAmendAHearing(hearings_rootContext,
                headersAsMap,
                authorizationToken,
                inputBodyForAmendHearing);
    }
}
