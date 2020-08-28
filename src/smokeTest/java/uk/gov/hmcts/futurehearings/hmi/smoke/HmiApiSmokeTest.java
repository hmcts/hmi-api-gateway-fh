package uk.gov.hmcts.futurehearings.hmi.smoke;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import uk.gov.hmcts.futurehearings.hmi.Application;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
public class HmiApiSmokeTest extends SmokeTest {

    @Value("${hmiApiRootContext}")
    private String hmiApiRootContext;

    @Test
    @DisplayName("Smoke Test to Test the Endpoint for the HMI Root Context")
    public void testSuccessfulHmiApiGet() {

        Response response = given()
                .headers(headersAsMap)
                .basePath(hmiApiRootContext)
                .when().get();

        if (response.getStatusCode() != 200) {
            log.debug(" The value of the Response Status " + response.getStatusCode());
            log.debug(" The value of the Response body " + response.getBody().prettyPrint());
        }
        assertEquals(HttpStatus.OK.value(),response.getStatusCode());
    }
}