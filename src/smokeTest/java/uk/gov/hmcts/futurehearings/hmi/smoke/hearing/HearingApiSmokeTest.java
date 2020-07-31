package uk.gov.hmcts.futurehearings.hmi.smoke.hearing;

import static io.restassured.RestAssured.expect;

import uk.gov.hmcts.futurehearings.hmi.Application;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
public class HearingApiSmokeTest {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetHost}")
    private String targetHost;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${hearingApiRootContext}")
    private String hearingApiRootContext;

    private Map<String, Object> headersAsMap = new HashMap<>();

    @BeforeEach
    public void initialiseValues() {
        headersAsMap.put("Host", targetHost);
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Company-Name", "HMCTS");
        headersAsMap.put("Content-Type", "application/json");
    }

    @Test
    @Disabled
    @DisplayName("Smoke Test to Test the Endpoint for the Hearing Root Context")
    public void testSuccessfulHearingApiGet() {
         expect().that().statusCode(200)
                .given().contentType("application/json")
                .headers(headersAsMap)
                .baseUri(targetInstance)
                .basePath(hearingApiRootContext)
                .when().get();
    }
}