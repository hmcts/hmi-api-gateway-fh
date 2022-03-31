package uk.gov.hmcts.futurehearings.hmi.functional.hearings.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;

import java.util.Map;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Slf4j
public class HearingsSteps {
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

    @Step("User retrieves Reservations")
    public void shouldGETReservations(final String apiURL,
                                                       final Map<String, Object> headersAsMap,
                                                       final String authorizationToken,
                                      final Map<String, String> queryParameters) {

        Response response = callRestEndpointWithQueryParams(apiURL,
                headersAsMap,
                authorizationToken,
                queryParameters,
                HttpStatus.OK);
        assertEquals(HttpStatus.OK.value(),response.getStatusCode());
    }

    public void GETReservationsInvalidParams(final String apiURL,
                                      final Map<String, Object> headersAsMap,
                                      final String authorizationToken,
                                      final Map<String, String> queryParameters) {

        Response response = callRestEndpointWithQueryParams(apiURL,
                headersAsMap,
                authorizationToken,
                queryParameters,
                HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatusCode());
    }

}
