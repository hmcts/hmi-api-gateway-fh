package uk.gov.hmcts.futurehearings.hmi.unit.testing;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.functional.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.steps.HearingApiCallSteps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.expect;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("local")
public class HmiApiUnitTest {

    private static final String CASE_TITLE_MISSING_REQ_PATH = "requests/case-title-missing-request.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetHost}")
    private String targetHost;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${hmiApiRootContext}")
    private String hmiApiRootContext;

    @Value("${hearingApiRootContext}")
    private String hearingApiRootContext;

    @Steps
    HearingApiCallSteps hearingApiCallSteps;

    private Map<String, Object> headersAsMap = new HashMap<>();

    @Before
    public void initialiseValues() {
        headersAsMap.put("Host", targetHost);
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Company-Name", "HMCTS");
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Source", "SnL");
        headersAsMap.put("Destination", "CFT");
        headersAsMap.put("DateTime", "datetimestring");
        headersAsMap.put("RequestType", "TypeOfCase");
    }

    @Test
    public void testSuccessfulHmiApiGet() {

        expect().that().statusCode(200)
                .given().contentType("application/json")
                .headers(headersAsMap)
                .baseUri(targetInstance)
                .basePath(hmiApiRootContext)
                .when().get();
    }

    @Test
    public void testRequestValidationWhenCaseTitleMissing() throws IOException{
        final String input = readFileContents(CASE_TITLE_MISSING_REQ_PATH);
        hearingApiCallSteps.requestHearingWithMissingCaseTitle(hearingApiRootContext, headersAsMap, targetInstance, input);
    }

}