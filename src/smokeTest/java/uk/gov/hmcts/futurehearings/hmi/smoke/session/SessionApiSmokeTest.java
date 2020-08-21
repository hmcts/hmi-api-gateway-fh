package uk.gov.hmcts.futurehearings.hmi.smoke.session;

import static io.restassured.RestAssured.given;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.smoke.SmokeTest;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
@Disabled("For as the endpoint for Sessions API is not available")
public class SessionApiSmokeTest extends SmokeTest {

    @Value("${sessionApiRootContext}")
    private String sessionApiRootContext;

    @Test
    @Disabled
    @DisplayName("Smoke Test to Test the Endpoint for the Get All Sessions Root Context")
    public void testSuccessfulAllSessionsApiGet() {

        Response response = given()
        .headers(headersAsMap)
        .basePath(sessionApiRootContext)
        .when().get();

        if (response.getStatusCode() != 200) {
            log.info(" The value of the Response Status " + response.getStatusCode());
            log.info(" The value of the Response body " + response.getBody().prettyPrint());
        }
    }
}