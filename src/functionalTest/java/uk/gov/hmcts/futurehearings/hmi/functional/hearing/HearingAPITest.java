package uk.gov.hmcts.futurehearings.hmi.functional.hearing;

import static io.restassured.config.EncoderConfig.encoderConfig;
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
public class HearingAPITest {

    @Steps
    HearingSteps hearingSteps;

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${hearingApiRootContext}")
    private String hearingApiRootContext;

    Map<String, Object> headersAsMap = new HashMap<>();

    @Before
    public void initialiseValues() {

        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", "S&L");
        headersAsMap.put("Request-Created-At", "2002-10-02T15:00:00Z");
        headersAsMap.put("Request-Processed-At", "2002-10-02 15:00:00Z");
        headersAsMap.put("Request-Type", "ASSAULT");

        RestAssured.config =
        SerenityRest.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
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