package uk.gov.hmcts.futurehearings.hmi.smoke.resources;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.smoke.common.rest.RestClient;
import uk.gov.hmcts.futurehearings.hmi.smoke.common.test.SmokeTest;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
@DisplayName("Smoke Test for the HMI Resources Context")
@SuppressWarnings("java:S2187")
class ResourcesApiSmokeTest extends SmokeTest {

    @Value("${resourcesApiRootContext}")
    private String resourcesApiRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        setRootContext(resourcesApiRootContext);
    }

    @Test
    @DisplayName("Smoke Test to test the resources endpoint")
    void testResourcesHmiApiGet() {
        assertEquals(HttpStatus.OK.value(),
                RestClient.makeGetRequest(getHeadersAsMap(), getAuthorizationToken(), getRootContext()).getStatusCode());
    }
}
