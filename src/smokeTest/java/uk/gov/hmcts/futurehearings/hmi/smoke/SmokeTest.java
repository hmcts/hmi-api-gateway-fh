package uk.gov.hmcts.futurehearings.hmi.smoke;

import static io.restassured.config.EncoderConfig.encoderConfig;

import uk.gov.hmcts.futurehearings.hmi.Application;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class SmokeTest {

    @Value("${targetInstance}")
    protected String targetInstance;

    @Value("${targetSubscriptionKey}")
    protected String targetSubscriptionKey;

    protected Map<String, Object> headersAsMap = new HashMap<>();

    @BeforeAll
    public void initialiseValues() {

        RestAssured.baseURI = targetInstance;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = RestAssured.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));

        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", "S&L");
        headersAsMap.put("Request-Created-At", "2002-10-02T15:00:00Z");
        headersAsMap.put("Request-Processed-At", "2002-10-02T15:00:00Z");
        headersAsMap.put("Request-Type", "ASSAULT");
    }
}
