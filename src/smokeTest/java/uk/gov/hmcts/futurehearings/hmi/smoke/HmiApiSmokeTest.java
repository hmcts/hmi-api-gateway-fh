package uk.gov.hmcts.futurehearings.hmi.smoke;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.smoke.common.rest.RestClient;
import uk.gov.hmcts.futurehearings.hmi.smoke.common.test.SmokeTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
@DisplayName("Smoke Test for the Root HMI Context")
@SuppressWarnings({"java:S2187", "PMD.LawOfDemeter"})
class HmiApiSmokeTest extends SmokeTest {

    @Value("${hmiApiRootContext}")
    private String hmiApiRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        setRootContext(hmiApiRootContext);
    }

    @Test
    @DisplayName("Smoke Test to test the root endpoint")
    void testRootHmiApiGet() {
        Response response = RestClient.makeGetRequest(getHeadersAsMap(),
                getAuthorizationToken(), getRootContext());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode(), "Status code do not match");
    }
}
