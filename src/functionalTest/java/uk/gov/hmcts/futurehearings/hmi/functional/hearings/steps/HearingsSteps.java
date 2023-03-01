package uk.gov.hmcts.futurehearings.hmi.functional.hearings.steps;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

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
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

    }
}
