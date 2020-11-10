package uk.gov.hmcts.futurehearings.hmi.functional.resources.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

import java.util.Map;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class ResourcesSteps {

    private String resourcesUser;

    @Step("User performs the creation of a user using the Resources API")
    public void shouldCreateAnUser(final String apiURL,
                                   final Map<String, Object> headersAsMap,
                                   final String authorizationToken,
                                   final String body) {

        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body,
                HttpMethod.POST,
                HttpStatus.CREATED);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

    }

    @Step("User performs the amend of a user using the Resources API")
    public void shouldUpdateAnUser(final String apiURL,
                                   final Map<String, Object> headersAsMap,
                                   final String authorizationToken,
                                   final String body) {

        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body,
                HttpMethod.PUT,
                HttpStatus.NO_CONTENT);
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());

    }

    @Step("User performs the creation of a location using the Resources API")
    public void shouldCreateALocation(final String apiURL,
                                   final Map<String, Object> headersAsMap,
                                   final String authorizationToken,
                                   final String body) {

        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body,
                HttpMethod.POST,
                HttpStatus.CREATED);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

    }

    @Step("User performs the amend of a location using the Resources API")
    public void shouldUpdateALocation(final String apiURL,
                                   final Map<String, Object> headersAsMap,
                                   final String authorizationToken,
                                   final String body) {

        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body,
                HttpMethod.PUT,
                HttpStatus.NO_CONTENT);
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());

    }


}
