package uk.gov.hmcts.futurehearings.hmi.smoke.session;

import static io.restassured.RestAssured.expect;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.smoke.SmokeTest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
@Disabled("For the purpose of adding the acceptance Test initial framework")
public class SessionApiSmokeTest extends SmokeTest {

    @Value("${sessionApiRootContext}")
    private String sessionApiRootContext;

    @Test
    @Disabled
    @DisplayName("Smoke Test to Test the Endpoint for the Get All Sessions Root Context")
    public void testSuccessfulAllSessionsApiGet() {
         expect().that().statusCode(200)
                .given().contentType("application/json")
                .headers(headersAsMap)
                .baseUri(targetInstance)
                .basePath(sessionApiRootContext)
                .when().get();
    }
}