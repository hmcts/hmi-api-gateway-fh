package uk.gov.hmcts.futurehearings.hmi.functional.directlisting;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.TestingUtils.readFileContents;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings("java:S2699")
public class DirectHearingTest extends FunctionalTest {

    @Value("${sessionsRootContext}")
    protected String sessionsRootContext;

    @Value("${directhearings_idRootContext}")
    protected String directHearingsIdRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    public void testDirectHearing() throws IOException {

        String inputBodyForDirectListing = readFileContents("uk/gov/hmcts/futurehearings/"
                + "hmi/functional/direct-listing/input/PUT-Hearing-Direct-Listing-Payload.json");
        Response response = callRestEndpointWithPayload(directHearingsIdRootContext,
                headersAsMap,
                authorizationToken,
                inputBodyForDirectListing, HttpMethod.PUT,
                HttpStatus.BAD_REQUEST);
        log.info(response.getBody().asString());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }
}
