package uk.gov.hmcts.futurehearings.hmi.functional.hearing;

import uk.gov.hmcts.futurehearings.hmi.functional.hearing.steps.HearingSteps;
import uk.gov.hmcts.futurehearings.hmi.functional.Application;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.TestingUtils.readFileContents;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Steps;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import lombok.extern.slf4j.Slf4j;

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

    @Value("${targetHost}")
    private String targetHost;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    private static final String EMPLOYEE_API = "employees";
    private static final String SESSION_API = "future-hearings-api/session/1?time=morning";
    private static final String HEARINGS_API = "hmi-apim-api/hearings";
    Map<String, Object> headersAsMap = new HashMap<>();

    @Before
    public void initialiseValues() {
        headersAsMap.put("Host", targetHost);
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Company-Name", "HMCTS");

        RestAssured.baseURI = targetInstance;
        SerenityRest.useRelaxedHTTPSValidation();
    }


    @Test
    @Ignore
    public void testSuccessfullGet() throws IOException {
        String input =
                readFileContents("uk/gov/hmcts/futurehearings/hmi/functional/hearing/input/poc-input.json");
        System.out.println("The value of the Input File : "+ input);
        System.out.println("The target instance of the Functional Test : " + targetInstance);
        hearingSteps.invokeEmployee(targetInstance,
                SESSION_API,
                headersAsMap);
    }

    @Test
    public void testSuccessfullPostToHearing() throws IOException {

        String input =
                readFileContents("uk/gov/hmcts/futurehearings/hmi/functional/hearing/input/mock-demo-request.json");
        hearingSteps.requestHearing(HEARINGS_API,
                                    headersAsMap,
                                    input);
    }

    @Pending
    @Test
    public void testPendingPostToHearing() throws IOException {

    }
}