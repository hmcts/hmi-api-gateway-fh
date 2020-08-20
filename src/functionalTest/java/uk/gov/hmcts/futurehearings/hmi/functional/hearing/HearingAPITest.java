package uk.gov.hmcts.futurehearings.hmi.functional.hearing;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.TestingUtils.readFileContents;

import uk.gov.hmcts.futurehearings.hmi.Application;
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
import org.junit.jupiter.api.Disabled;
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
@Disabled("For the purpose of adding the acceptance Test initial framework")
public class HearingAPITest {

    @Steps
    HearingSteps hearingSteps;

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetHost}")
    private String targetHost;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${hearingApiRootContext}")
    private String hearingApiRootContext;

    Map<String, Object> headersAsMap = new HashMap<>();

    @Before
    public void initialiseValues() {
        headersAsMap.put("Host", targetHost);
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Company-Name", "HMCTS");
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Source-System", "SnL");
        headersAsMap.put("Destination-System", "CFT");
        headersAsMap.put("Request-Created-At", "datetimestring");
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Accept", "application/json");

        RestAssured.baseURI = targetInstance;
        SerenityRest.useRelaxedHTTPSValidation();
    }


    @Test
    public void testSuccessfullPostToHearing() throws IOException {

        log.info("Post hearing request to target Instance" + targetInstance);

        String input =
                readFileContents("uk/gov/hmcts/futurehearings/hmi/functional/hearing/input/mock-demo-request.json");
        hearingSteps.requestHearing(hearingApiRootContext,
                                    headersAsMap,
                                    input);
    }

    @Pending
    @Test
    public void testPendingPostToHearing() throws IOException {

    }
}