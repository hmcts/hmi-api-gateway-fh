package uk.gov.hmcts.futurehearings.hmi.functional.videohearing.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.*;

import java.util.Map;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class VideoHearingSteps {

    @Step("User makes a request to Video Hearing API with invalid payload")
    public void shouldRequestVideoHearingWithInvalidPayload(final String apiURL,
                                         final Map<String, Object> headersAsMap,
                                         final String authorizationToken,
                                         final HttpMethod httpMethod,
                                         final String body) {
        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body, httpMethod, HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatusCode());
    }

    @Step("User makes a request to Video Hearing API with invalid payload")
    public void shouldAmendVideoHearingWithInvalidPayload(final String apiURL,
                                                            final Map<String, Object> headersAsMap,
                                                            final String authorizationToken,
                                                            final HttpMethod httpMethod,
                                                            final String body) {
        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body, httpMethod, HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatusCode());
    }

    @Step("User makes a request to Delete Video Hearing API")
    public void shouldDeleteVideoHearing(final String apiURL,
                                       final Map<String, Object> headersAsMap,
                                       final String authorizationToken,
                                       final String body) {
        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body, HttpMethod.DELETE,HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatusCode());
    }

    @Step("User makes a request to Get List of hearings for relevant search parameters on the Video Hearing API")
    public void performVideoHearingGetByUsername(final String apiURL,
                                       final Map<String, Object> headersAsMap,
                                       final String authorizationToken,
                                       final Map<String, String> queryParameters) {
        System.out.println("URL" + apiURL);
        callRestEndpointWithQueryParams(apiURL,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
    }
    
    @Step("User makes a request to Get Video hearing by ID")
    public Response performVideoHearingGetByHearingId(final String apiURL,
                                                       final Map<String, Object> headersAsMap,
                                                       final String authorizationToken) {
        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                null, HttpMethod.GET,HttpStatus.OK);
        return response;
    }
}
