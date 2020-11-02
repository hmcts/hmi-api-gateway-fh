package uk.gov.hmcts.futurehearings.hmi.smoke.schedules;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.smoke.common.test.SmokeTest;

import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
 class SchedulesApiSmokeTest extends SmokeTest {

    @Value("${schedulesApiRootContext}")
    private String schedulesApiRootContext;

    @Test
    @DisplayName("Smoke Test to Test the Endpoint for the Get All Schedules Root Context")
     void testSuccessfulAllSchedulesApiGet() {

        Response response = given()
        .headers(headersAsMap)
        .basePath(schedulesApiRootContext)
        .when().get();

        if (response.getStatusCode() != 200) {
            log.debug(" The value of the Response Status " + response.getStatusCode());
            log.debug(" The value of the Response body " + response.getBody().prettyPrint());
        }
        assertEquals(HttpStatus.OK.value(),response.getStatusCode());
    }
}