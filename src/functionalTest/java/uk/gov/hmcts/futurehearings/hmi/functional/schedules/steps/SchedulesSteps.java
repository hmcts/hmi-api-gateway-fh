package uk.gov.hmcts.futurehearings.hmi.functional.schedules.steps;

import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

public class SchedulesSteps {

    @Step("Request made to Sessions API with invalid payload")
    public void shouldPostSessionsWithPayload(final String apiUrl,
                                              final Map<String, Object> headersAsMap,
                                              final String authorizationToken,
                                              final HttpMethod httpMethod,
                                              final String body) {
        callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body,
                httpMethod,
                HttpStatus.BAD_REQUEST)
        ;
    }
}
