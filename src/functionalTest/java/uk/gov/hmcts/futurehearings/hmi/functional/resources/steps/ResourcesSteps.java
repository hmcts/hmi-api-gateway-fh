package uk.gov.hmcts.futurehearings.hmi.functional.resources.steps;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointDelete;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

@Slf4j
public class ResourcesSteps {
//    Needs Confirming - Not supported - Mocked response for this test (potentially will be needed - ASK SNL.
//    @Step("User performs the creation of a location using the Resources API")
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

    @Step("User performs the creation of a location using invalid payload")
    public void shouldCreateOrAmendLocationWithInvalidPayload(final String apiURL,
                                      final Map<String, Object> headersAsMap,
                                      final String authorizationToken,
                                      final HttpMethod httpMethod,
                                      final String body) {

        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body,
                httpMethod,
                HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

    }

    @Step("User performs the creation of a user with invalid payload")
    public void shouldCreateOrAmendUserWithInvalidPayload(final String apiURL,
                                                          final Map<String, Object> headersAsMap,
                                                          final String authorizationToken,
                                                          final HttpMethod httpMethod,
                                                          final String body) {

        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body,
                httpMethod,
                HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

    }

    @Step("User performs the creation of a linked hearing group with an invalid payload")
    public void shouldCreateLinkedHearingGroup(final String apiURL,
                                               final Map<String, Object> headersAsMap,
                                               final String authorizationToken,
                                               final HttpMethod httpMethod,
                                               final String body) {

        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body,
                httpMethod,
                HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Step("User performs amend of a linked hearing group with an empty payload")
    public void shouldAmendLinkedHearingGroupWithEmptyPayload(final String apiURL,
                                              final Map<String, Object> headersAsMap,
                                              final String authorizationToken,
                                              final HttpMethod httpMethod,
                                              final String body) {

        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body,
                httpMethod,
                HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Step("User performs the deletion of a linked hearing group")
    public void shouldDeleteLinkedHearingGroupInvalid(final String apiURL,
                                                      final Map<String, Object> headersAsMap,
                                                      final String authorizationToken,
                                                      final HttpMethod httpMethod) {
        Response response = callRestEndpointDelete(apiURL,
                headersAsMap,
                authorizationToken,
                HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }
}
