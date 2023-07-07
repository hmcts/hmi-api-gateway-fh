package uk.gov.hmcts.futurehearings.hmi.smoke.people;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.smoke.common.rest.RestClient;
import uk.gov.hmcts.futurehearings.hmi.smoke.common.test.SmokeTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
@DisplayName("Smoke Test for the HMI People Context")
@SuppressWarnings({"java:S2187", "PMD.LawOfDemeter"})
class PeopleApiSmokeTest extends SmokeTest {

    @Value("${peopleHealthcheck}")
    private String peopleHealthcheck;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValuesDefault();
        setRootContext(peopleHealthcheck);
    }

    @Test
    @Disabled
    @DisplayName("Smoke Test to test the people endpoint")
    void testPeopleHmiApiGet() {
        Response response = RestClient.makeGetRequest(getRootContext());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode(), "Response codes do not match");
    }

    public static Response makeGetRequest(final String rootContext) {
        return given()
                .baseUri(rootContext)
                .when()
                .get();
    }
}
