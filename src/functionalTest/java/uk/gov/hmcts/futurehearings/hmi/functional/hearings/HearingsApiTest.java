package uk.gov.hmcts.futurehearings.hmi.functional.hearings;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings({"PMD.LawOfDemeter"})
class HearingsApiTest extends FunctionalTest {

    @Value("${hearingsApiRootContext}")
    protected String hearingsApiRootContext;

    @Value("${hearings_idRootContext}")
    protected String hearingsIdRootContext;

    private final Random rand;

    @BeforeEach
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    public HearingsApiTest() throws NoSuchAlgorithmException {
        super();
        rand = SecureRandom.getInstanceStrong();
    }

    @Test
    void testRequestHearingWithEmptyPayload() {
        Response response = callRestEndpointWithPayload(hearingsApiRootContext,
                headersAsMap,
                authorizationToken,
                "{}",
                HttpMethod.POST,
                HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode(),
                "Status code do not match");
    }

    @Test
    void testAmendHearingWithEmptyPayload() {
        int randomId = rand.nextInt(99999999);
        Response response = callRestEndpointWithPayload(String.format(hearingsIdRootContext, randomId),
                headersAsMap,
                authorizationToken,
                "{}",
                HttpMethod.PUT,
                HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode(),
                "Status code do not match");
    }

    @Test
    void testDeleteHearingWithEmptyPayload() {
        int randomId = rand.nextInt(99999999);
        Response response = callRestEndpointWithPayload(String.format(hearingsIdRootContext, randomId),
                headersAsMap,
                authorizationToken,
                "{}",
                HttpMethod.DELETE,
                HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode(),
                "Status code do not match");
    }
}