package uk.gov.hmcts.futurehearings.hmi.functional.hearings;

import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.hearings.steps.HearingsSteps;

import java.util.Random;

@Slf4j
@Narrative(text = {"In order to test that the Hearing Functionality is working properly",
        "As a tester",
        "I want to be able to execute the tests for Hearings API methods works in a lifecycle mode of execution"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class HearingsApiTest extends FunctionalTest {

    @Value("${hearingsApiRootContext}")
    protected String hearingsApiRootContext;

    @Value("${hearings_idRootContext}")
    protected String hearingsIdRootContext;

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

        hearingsSteps.shouldRequestHearingWithInvalidPayload(String.format(hearingsIdRootContext, randomId),
                headersAsMap,
                authorizationToken, HttpMethod.PUT,
                "{}");
    }

    @Test
    public void testDeleteHearingWithEmptyPayload() {
        int randomId = new Random().nextInt(99999999);

        hearingsSteps.shouldRequestHearingWithInvalidPayload(String.format(hearingsIdRootContext, randomId),
                headersAsMap,
                authorizationToken, HttpMethod.DELETE,
                "{}");
    }
}
