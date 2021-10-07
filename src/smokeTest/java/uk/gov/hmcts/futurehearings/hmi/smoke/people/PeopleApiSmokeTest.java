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

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Disabled("Failing due to issue, to be re-enabled in future ticket once fix implemented")
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
@DisplayName("Smoke Test for the HMI People Context")
@SuppressWarnings("java:S2187")
class PeopleApiSmokeTest extends SmokeTest {

    @Value("${peopleHealthcheck}")
    private String peopleHealthcheck;

    @BeforeAll
    public void initialiseValues() throws Exception {
        this.setDestinationSystem("ELINKS");
        super.initialiseValues();
        setRootContext(peopleHealthcheck);
    }

    @Test
    @DisplayName("Smoke Test to test the people endpoint")
    void testPeopleHmiApiGet() {
        Response response = RestClient.makeGetRequest(getHeadersAsMap(),
                getAuthorizationToken(),
                getRootContext());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }
}
