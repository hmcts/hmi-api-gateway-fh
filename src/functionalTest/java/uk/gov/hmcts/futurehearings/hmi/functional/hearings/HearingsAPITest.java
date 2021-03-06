package uk.gov.hmcts.futurehearings.hmi.functional.hearings;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.hearings.steps.HearingsSteps;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
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
    public void testRequestHearingWithEmptyPayload() {
        hearingsSteps.shouldRequestHearingWithInvalidPayload(hearingsApiRootContext,
                headersAsMap,
                authorizationToken, HttpMethod.POST,
                "{}");
    }

    @Test
    public void testAmendHearingWithEmptyPayload() {
        int randomId = new Random().nextInt(99999999);

        hearingsSteps.shouldRequestHearingWithInvalidPayload(String.format(hearings_idRootContext,randomId),
                headersAsMap,
                authorizationToken, HttpMethod.PUT,
                "{}");
    }

    @Test
    public void testDeleteHearingWithEmptyPayload() {
        int randomId = new Random().nextInt(99999999);

        hearingsSteps.shouldRequestHearingWithInvalidPayload(String.format(hearings_idRootContext,randomId),
                headersAsMap,
                authorizationToken,HttpMethod.DELETE,
                "{}");
    }
}
