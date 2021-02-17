package uk.gov.hmcts.futurehearings.hmi.functional.videohearing.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;
import static uk.gov.hmcts.futurehearings.hmi.functional.videohearing.process.VideoHearingLookUpResponseProcess.getHearingId;

import java.util.Map;
import java.util.Objects;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class VideoHearingSteps {

    private String actor;

    @Step("User makes a request to Post Video Hearing API")
    public void shouldCreateVideoHearing(final String apiURL,
                                        final Map<String, Object> headersAsMap,
                                        final String authorizationToken,
                                        final String body) {
        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body, HttpMethod.POST,HttpStatus.CREATED);
        assertEquals(HttpStatus.CREATED.value(),response.getStatusCode());
    }

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

    @Step("User makes a request to Put Video Hearing API")
    public void shouldAmendVideoHearing(final String apiURL,
                                        final Map<String, Object> headersAsMap,
                                        final String authorizationToken,
                                        final String body) {
        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body, HttpMethod.PUT,HttpStatus.OK);
        assertEquals(HttpStatus.OK.value(),response.getStatusCode());
    }

    @Step("User makes a request to Delete Video Hearing API")
    public void shouldDeleteVideoHearing(final String apiURL,
                                       final Map<String, Object> headersAsMap,
                                       final String authorizationToken,
                                       final String body) {
        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body, HttpMethod.DELETE,HttpStatus.NO_CONTENT);
        assertEquals(HttpStatus.NO_CONTENT.value(),response.getStatusCode());
    }

    @Step("User makes a request to Get List of hearings for relevant search parameters on the Video Hearing API")
    public String performVideoHearingGetByUsername(final String apiURL,
                                       final Map<String, Object> headersAsMap,
                                       final String authorizationToken,
                                       final Map<String, String> queryParameters) throws Exception {

        System.out.println("URL" + apiURL);
        String hearingId = getHearingId(callRestEndpointWithQueryParams(apiURL,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK));

        assertTrue(Objects.nonNull(hearingId) && !hearingId.trim().equals(""));
        return hearingId;
    }

    @Step("User makes a request to Get Video hearing by ID")
    public Response shouldFetchVideoHearingByHearingId(final String apiURL,
                                                   final Map<String, Object> headersAsMap,
                                                   final String authorizationToken,
                                                   final Map<String, String> queryParameters
                                                   ) throws Exception {

        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                null, HttpMethod.GET,HttpStatus.OK);
        return response;
    }

    @Step("Verify video hearing created")
    public void assertVideoHearingCreated(Response response, final String hearingId) throws Exception {
        assertEquals(HttpStatus.OK.value(),response.getStatusCode());

        JSONObject peopleObj = new JSONObject(response.body().asString());
        assertEquals(hearingId ,peopleObj.getString("id"));
        assertTrue(!StringUtils.isEmpty(peopleObj.getString("hearing_venue_name")));
        assertTrue(!StringUtils.isEmpty(peopleObj.getString("scheduled_date_time")));
    }

    @Step("Verify video hearing updated")
    public void assertVideoHearingUpdated(Response response,
                                             final String hearingId,
                                             final String updatedHearingRoomName) throws Exception {
        assertEquals(HttpStatus.OK.value(),response.getStatusCode());

        JSONObject peopleObj = new JSONObject(response.body().asString());
        assertEquals(hearingId ,peopleObj.getString("id"));
        assertTrue(!StringUtils.isEmpty(peopleObj.getString("hearing_room_name")));
        assertEquals(updatedHearingRoomName ,peopleObj.getString("hearing_room_name"));
    }

    @Step("Verify video hearing deleted")
    public void assertVideoHearingDeleted(final String apiURL,
                                                       final Map<String, Object> headersAsMap,
                                                       final String authorizationToken,
                                                       final Map<String, String> queryParameters,
                                                       final String hearingId
    ) throws Exception {

        Response response = callRestEndpointWithQueryParams(apiURL,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
        JSONArray jsonArray = new JSONArray(response.body().asString());
        for(int i=0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            assertTrue(!jsonObject.getString("id").equalsIgnoreCase(hearingId));
        }
    }
}
