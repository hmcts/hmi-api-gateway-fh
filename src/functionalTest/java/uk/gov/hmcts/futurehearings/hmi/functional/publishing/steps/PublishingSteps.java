package uk.gov.hmcts.futurehearings.hmi.functional.publishing.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;

import java.util.Map;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class PublishingSteps {

    @Step("Creation of data using the Publishing API")
    public void shouldCreatePublishingWithInvalidPayload(final String apiUrl,
                                       final Map<String, Object> headersAsMap,
                                       final String authorizationToken,
                                       final String body) {
        Response response = callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body,
                HttpMethod.POST,
                HttpStatus.CREATED);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Step("Amend data using the Publishing API")
    public void shouldAmendPublishingWithInvalidPayload(final String apiUrl,
                                                         final Map<String, Object> headersAsMap,
                                                         final String authorizationToken,
                                                         final String body) {
        Response response = callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body,
                HttpMethod.PUT,
                HttpStatus.OK);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Step("Get data using the Publishing API")
    public void shouldGetPublishing(final String apiUrl,
                                    final Map<String, Object> headersAsMap,
                                    final String authorizationToken,
                                    final Map<String, String> queryParameters) {
        callRestEndpointWithQueryParams(apiUrl,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
    }

    @Step("Delete data using the Publishing API")
    public void shouldDeletePublishing(final String apiUrl,
                                       final Map<String, Object> headersAsMap,
                                       final String authorizationToken,
                                       final String body) {
        Response response = callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body,
                HttpMethod.DELETE,
                HttpStatus.NO_CONTENT);
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());
    }
}
