package uk.gov.hmcts.futurehearings.hmi.smoke.people;

import io.restassured.response.Response;

import org.junit.jupiter.api.Disabled;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.smoke.common.rest.RestClient;
import uk.gov.hmcts.futurehearings.hmi.smoke.common.test.SmokeTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
@DisplayName("Smoke Test for the HMI People Context")
@SuppressWarnings("java:S2187")
class PeopleApiSmokeTest extends SmokeTest {

    private String peopleHealthcheck = "https://hmi-apim.test.platform.hmcts.net/hmi/elinks-health";

    @BeforeAll
    public void initialiseValues() throws Exception {
        this.setDestinationSystem("ELINKS");
        super.initialiseValues();
        setRootContext(peopleHealthcheck);
    }

    @Test
    @DisplayName("Smoke Test to test the people endpoint")
    void testPeopleHmiApiGet() {
        Response response = makeGetRequest(getRootContext());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    public static Response makeGetRequest(final String rootContext) {
        Response response = given()
                .baseUri(rootContext)
                .when()
                .get();
        return response;
    }
}