package uk.gov.hmcts.futurehearings.hmi.smoke;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.expect;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
public class HmiApiSmokeTest extends SmokeTest {

    @Value("${hmiApiRootContext}")
    private String hmiApiRootContext;

    private Map<String, Object> headersAsMap = new HashMap<>();

    @BeforeEach
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
    }

    @Test
    @DisplayName("Smoke Test to Test the Endpoint for the HMI Root Context")
    public void testSuccessfulHmiApiGet() {

        log.info("Get hearing request to target Instance " +targetInstance);

        expect().that().statusCode(200)
                .given().contentType("application/json")
                .headers(headersAsMap)
                .baseUri(targetInstance)
                .basePath(hmiApiRootContext)
                .when().get();
    }
}