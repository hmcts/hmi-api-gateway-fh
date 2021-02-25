package uk.gov.hmcts.futurehearings.hmi.functional.hearings.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

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

    @Step("User deletes a Hearing request")
    public void shouldDeleteAHearing(final String apiURL,
                                    final Map<String, Object> headersAsMap,
                                    final String authorizationToken,
                                    final String body) {

        System.out.println(apiURL);
        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body,
                HttpMethod.DELETE,
                HttpStatus.BAD_REQUEST);
        System.out.println("RES" + response.getBody().asString());
        assertEquals(HttpStatus.ACCEPTED.value(),response.getStatusCode());

    }

    @Step("User amends a Hearing request with invalid payload")
    public void shouldRequestHearingWithInvalidPayload(final String apiURL,
                                    final Map<String, Object> headersAsMap,
                                    final String authorizationToken, final HttpMethod httpMethod,
                                    final String body) {

        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body,
                httpMethod,
                HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatusCode());

    }
}
