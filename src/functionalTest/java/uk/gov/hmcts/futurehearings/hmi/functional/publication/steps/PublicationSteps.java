package uk.gov.hmcts.futurehearings.hmi.functional.publication.steps;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

import java.util.Map;

import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class PublicationSteps {

    @Step("Successful creation of data using the Publication API")
    public void createPublicationWithValidHeadersAndPayload(final String apiUrl,
                                                         final Map<String, Object> headersAsMap,
                                                         final String authorizationToken,
                                                         final String body) {
        callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body,
                HttpMethod.POST,
                HttpStatus.CREATED);
    }

    @Step("Invalid headers, Bad request creating data using the Publication API")
    public void createPublicationWithInvalidPayload(final String apiUrl,
                                                    final Map<String, Object> headersAsMap,
                                                    final String authorizationToken,
                                                    final String body) {
        callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body,
                HttpMethod.POST,
                HttpStatus.BAD_REQUEST);
    }

    @Step("Unauthorised, Bad request creating data using the Publication API")
    public void createPublicationUnauthorized(final String apiUrl,
                                              final Map<String, Object> headersAsMap,
                                              final String authorizationToken,
                                              final String body) {
        callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body,
                HttpMethod.POST,
                HttpStatus.UNAUTHORIZED);
    }
}
