package uk.gov.hmcts.futurehearings.hmi.functional.hearings;

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
@Narrative(text = {"In order to test that the Hearing Functionality is working properly",
        "As a tester",
        "I want to be able to execute the tests for Hearings API methods works in a lifecycle mode of execution"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class HearingsAPITest extends FunctionalTest {

    public static final String HEARINGS_INPUT_PATH = "uk/gov/hmcts/futurehearings/hmi/functional/hearings/input";

    @Value("${hearingsApiRootContext}")
    protected String hearingsApiRootContext;

    @Value("${hearings_idRootContext}")
    protected String hearings_idRootContext;

    @Steps
    HearingsSteps hearingsSteps;

    @Test
    public void testRequestAndAmendHearing() throws IOException {

        log.debug("In the testRequestAndAmendHearing () method");
        String inputBodyForRequestHearing =
                String.format(readFileContents(HEARINGS_INPUT_PATH + "/POST-hearing-payload.json"), "615");
        hearingsSteps.shouldRequestAHearing(hearingsApiRootContext,
                headersAsMap,
                authorizationToken,
                inputBodyForRequestHearing);

        hearings_idRootContext = String.format(hearings_idRootContext,"615");
        String inputBodyForAmendHearing =
                String.format(readFileContents(HEARINGS_INPUT_PATH + "/PUT-hearing-payload.json"), "615", "615");
        hearingsSteps.shouldAmendAHearing(hearings_idRootContext,
                headersAsMap,
                authorizationToken,
                inputBodyForAmendHearing);
    }
}
