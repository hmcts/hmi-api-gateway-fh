package uk.gov.hmcts.futurehearings.hmi.smoke.hearing;

import static io.restassured.RestAssured.expect;

import uk.gov.hmcts.futurehearings.hmi.functional.Application;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
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

    private Map<String, Object> headersAsMap = new HashMap<>();
    private static final String HEARING_API_ROOT_CONTEXT = "hmi-apim-api/hearings";

    @BeforeEach
    public void initialiseValues() {
        headersAsMap.put("Host", targetHost);
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Company-Name", "HMCTS");
        headersAsMap.put("Content-Type", "application/json");
    }

    @Test
    public void testGet() {
        System.out.println("The target instance of the Smoke Test : " + targetInstance);

         expect().that().statusCode(200)
                .given().contentType("application/json")
                .headers(headersAsMap)
                .baseUri(targetInstance)
                .basePath(HEARING_API_ROOT_CONTEXT)
                .when().get();
    }
}