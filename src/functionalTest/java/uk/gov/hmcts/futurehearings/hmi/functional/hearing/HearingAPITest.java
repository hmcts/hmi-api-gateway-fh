package uk.gov.hmcts.futurehearings.hmi.functional.hearing;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.TestingUtils.readFileContents;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.hearing.steps.HearingSteps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text={"In order to test that the Hearing Service is working properly",
        "As a tester",
        "I want to be able to execute the tests for various endpoints"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings("java:S2699")
public class HearingAPITest extends FunctionalTest {

    @Steps
    HearingSteps hearingSteps;

    @Before
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }


    @Test
    public void testSuccessfulPostToHearing() throws IOException {

        log.info("Post hearing request to target Instance" + targetInstance);
        String input =
                readFileContents("uk/gov/hmcts/futurehearings/hmi/functional/hearing/input/mock-demo-request.json");
        hearingSteps.requestHearing(hearingApiRootContext,
                                    headersAsMap,
                                    getAuthorizationToken(),
                                    input);
    }

    @Pending
    @Test
    public void testPendingPostToHearing() throws IOException {

    }
}