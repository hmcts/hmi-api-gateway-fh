package uk.gov.hmcts.futurehearings.hmi.smoke.updateuser;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.smoke.common.test.SmokeTest;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
@Disabled("For as the endpoint for User API is not available")
class UserApiSmokeTest extends SmokeTest {

    @Value("${userApiRootContext}")
    public String userApiRootContext;

    @Test
    @DisplayName("Smoke Test to Test the Endpoint for the Get Update Users Root Context")
    void testSuccessfulAllSessionsApiGet() {
        Response response = given()
                .headers(headersAsMap)
                .basePath(userApiRootContext)
                .when().get();

        if (response.getStatusCode() != 200) {
            log.info(" The value of the Response Status " + response.getStatusCode());
            log.info(" The value of the Response body " + response.getBody().prettyPrint());
        }
        assertEquals(HttpStatus.OK.value(),response.getStatusCode());
    }
}