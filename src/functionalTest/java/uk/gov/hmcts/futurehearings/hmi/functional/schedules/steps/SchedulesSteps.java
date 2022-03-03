package uk.gov.hmcts.futurehearings.hmi.functional.schedules.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.*;

import java.util.Map;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class SchedulesSteps {

    @Step("Request made to Sessions API with invalid payload")
    public void shouldPostSessionsWithPayload(final String apiURL,
                                              final Map<String, Object> headersAsMap,
                                              final String authorizationToken,
                                              final HttpMethod httpMethod,
                                              final String body) {
                callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body,
                httpMethod,
                HttpStatus.BAD_REQUEST);
    }
}
