package uk.gov.hmcts.futurehearings.hmi.functional.videohearing.steps;

import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;

public class VideoHearingSteps {

    @Step("User makes a request to Video Hearing API with invalid payload")
    public void shouldRequestVideoHearingWithInvalidPayload(final String apiUrl,
                                         final Map<String, Object> headersAsMap,
                                         final String authorizationToken,
                                         final HttpMethod httpMethod,
                                         final String body) {
        callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body, httpMethod, HttpStatus.BAD_REQUEST);
    }

    @Step("User makes a request to Clone Video Hearing API")
    public void shouldRequestCloneVideoHearing(final String apiUrl,
                                               final Map<String, Object> headersAsMap,
                                               final String authorizationToken,
                                               final HttpMethod httpMethod,
                                               final HttpStatus httpStatus,
                                               final String body) {
        callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body, httpMethod, httpStatus);
    }

    @Step("User makes a request to Video Hearing API with invalid payload")
    public void shouldAmendVideoHearingWithInvalidPayload(final String apiUrl,
                                                            final Map<String, Object> headersAsMap,
                                                            final String authorizationToken,
                                                            final HttpMethod httpMethod,
                                                            final String body) {
        callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body, httpMethod, HttpStatus.BAD_REQUEST);
    }

    @Step("User makes a request to Delete Video Hearing API")
    public void shouldDeleteVideoHearing(final String apiUrl,
                                       final Map<String, Object> headersAsMap,
                                       final String authorizationToken,
                                       final String body) {
        callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body, HttpMethod.DELETE, HttpStatus.BAD_REQUEST);
    }

    @Step("User makes a request to Get List of hearings for relevant search parameters on the Video Hearing API")
    public void performVideoHearingGetByUsername(final String apiUrl,
                                       final Map<String, Object> headersAsMap,
                                       final String authorizationToken,
                                       final Map<String, String> queryParameters) {
        callRestEndpointWithQueryParams(apiUrl,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
    }

    @Step("User makes a request to Get Video Hearings by Group ID")
    public void performGetVideoHearingsByGroupIdWithSpecifiedStatus(final String apiUrl,
                                                                    final Map<String, Object> headersAsMap,
                                                                    final String authorizationToken,
                                                                    final HttpStatus httpStatus,
                                                                    final String body) {
        callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body, HttpMethod.GET, httpStatus);
    }

    @Step("User makes a request to Get Video hearing by ID")
    public void performVideoHearingGetByHearingId(final String apiUrl,
                                                       final Map<String, Object> headersAsMap,
                                                       final String authorizationToken) {
        callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                null, HttpMethod.GET, HttpStatus.OK);
    }

    @Step("User makes a request to add participant")
    public void performPostParticipant(final String apiUrl,
                                           final Map<String, Object> headersAsMap,
                                           final String authorizationToken,
                                           final String body) {

        callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body, HttpMethod.POST, HttpStatus.BAD_REQUEST);
    }

    @Step("User makes a request to edit a participant")
    public void performPutParticipant(final String apiUrl,
                                          final Map<String, Object> headersAsMap,
                                          final String authorizationToken,
                                          final String body) {

        callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body, HttpMethod.PUT, HttpStatus.BAD_REQUEST);
    }

    @Step("User makes a request to delete a participant")
    public void performDeleteParticipant(final String apiUrl,
                                             final Map<String, Object> headersAsMap,
                                             final String authorizationToken,
                                             final String body) {

        callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body, HttpMethod.DELETE, HttpStatus.BAD_REQUEST);
    }
}
