package uk.gov.hmcts.futurehearings.hmi.functional.hearings.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;
import static uk.gov.hmcts.futurehearings.hmi.functional.directlisting.process.DirectListingResponseProcess.getSessionId;

import java.util.Map;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Slf4j
public class HearingsSteps {

    private String hearingUser;

    @Step("User creates a Hearing request")
    public void shouldRequestAHearing(final String apiURL,
                                               final Map<String, Object> headersAsMap,
                                               final String authorizationToken,
                                               final String body) {

        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body,
                HttpMethod.POST,
                HttpStatus.ACCEPTED);
        log.debug("The value of the Hearing Response Body"+response.getBody().prettyPrint());
        assertEquals(HttpStatus.ACCEPTED.value(),response.getStatusCode());

    }

    @Step("User amends a Hearing request")
    public void shouldAmendAHearing(final String apiURL,
                                      final Map<String, Object> headersAsMap,
                                      final String authorizationToken,
                                      final String body) {

        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body,
                HttpMethod.PUT,
                HttpStatus.ACCEPTED);
        assertEquals(HttpStatus.ACCEPTED.value(),response.getStatusCode());

    }
}
