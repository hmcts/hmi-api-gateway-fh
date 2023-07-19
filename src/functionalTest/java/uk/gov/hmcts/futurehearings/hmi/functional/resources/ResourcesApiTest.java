package uk.gov.hmcts.futurehearings.hmi.functional.resources;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointDelete;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings({"PMD.LawOfDemeter"})
class ResourcesApiTest extends FunctionalTest {

    @Value("${resourcesLinkedHearingGroupRootContext}")
    private String resourcesLinkedHearingGroupRootContext;

    @Value("${resourcesLinkedHearingGroup_idRootContext}")
    private String resourcesLinkedHearingGroupIdRootContext;

    private final Random rand;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    public ResourcesApiTest() throws NoSuchAlgorithmException {
        super();
        rand = SecureRandom.getInstanceStrong();
    }

    @Test
    void testRequestLinkedHearingGroup() {
        Response response = callRestEndpointWithPayload(resourcesLinkedHearingGroupRootContext,
                headersAsMap,
                authorizationToken,
                "{}",
                HttpMethod.POST,
                HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode(),
                "Status code do not match");
    }

    @Test
    void testAmendLinkedHearingGroup() {
        int randomId = rand.nextInt(99999999);
        resourcesLinkedHearingGroupIdRootContext = String.format(resourcesLinkedHearingGroupIdRootContext, randomId);
        Response response = callRestEndpointWithPayload(resourcesLinkedHearingGroupIdRootContext,
                headersAsMap,
                authorizationToken,
                "{}",
                HttpMethod.PUT,
                HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode(),
                "Status code do not match");
    }

    @Test
    void testDeleteLinkedHearingGroupInvalid() {
        int randomId = rand.nextInt(99999999);
        resourcesLinkedHearingGroupIdRootContext = String.format(resourcesLinkedHearingGroupIdRootContext, randomId);
        Response response = callRestEndpointDelete(resourcesLinkedHearingGroupIdRootContext,
                headersAsMap,
                authorizationToken,
                HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode(),
                "Status code do not match");
    }
}
