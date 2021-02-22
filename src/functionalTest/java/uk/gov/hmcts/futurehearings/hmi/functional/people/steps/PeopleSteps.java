package uk.gov.hmcts.futurehearings.hmi.functional.people.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;
import static uk.gov.hmcts.futurehearings.hmi.functional.people.process.PeopleLookUpResponseProcess.getPeopleId;

import java.util.Map;
import java.util.Objects;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class PeopleSteps {

    private String actor;

    @Step("User makes a request to Get List of People for relevant search parameters on the People API")
    public Response shouldFetchListOfPeople(final String apiURL,
                                       final Map<String, Object> headersAsMap,
                                       final String authorizationToken,
                                       final Map<String, String> queryParameters) throws Exception {

        Response response = callRestEndpointWithQueryParams(apiURL,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
        assertEquals(HttpStatus.OK.value(),response.getStatusCode());
        return response;
    }

    @Step("Verify People List and Fetch people id from the first record")
    public String assertAndFetchPeopleId(Response response) throws Exception {
        System.out.println(response.body().asString());
        JSONArray jsonArray = new JSONArray(response.body().asString());
        System.out.println(jsonArray.length());

        String peopleId = getPeopleId(response);
        assertTrue(Objects.nonNull(peopleId) && !peopleId.trim().equals(""));
        return peopleId;
    }

    @Step("User makes a request to Get List of People for relevant search parameters on the People API")
    public void shouldGetByPeopleId(final String apiURL, final String peopleId,
                                       final Map<String, Object> headersAsMap,
                                       final String authorizationToken,
                                       final Map<String, String> queryParameters) throws Exception {

        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                null, HttpMethod.GET,HttpStatus.OK);
        assertEquals(HttpStatus.OK.value(),response.getStatusCode());

        JSONObject peopleObj = new JSONObject(response.body().asString());
        assertEquals(peopleId ,peopleObj.getString("id"));
        assertTrue(!StringUtils.isEmpty(peopleObj.getString("known_as")));
        assertTrue(!StringUtils.isEmpty(peopleObj.getString("surname")));
        assertTrue(!StringUtils.isEmpty(peopleObj.getString("fullname")));
        assertTrue(!StringUtils.isEmpty(peopleObj.getString("email")));
    }

    @Step("User makes a request to Update People (PUT in the People API)")
    public void performPeopleUpdate(final String apiURL,
                                                      final Map<String, Object> headersAsMap,
                                                      final String authorizationToken,
                                                      final String body) {
        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body, HttpMethod.PUT,HttpStatus.NO_CONTENT);
        assertEquals(HttpStatus.NO_CONTENT.value(),response.getStatusCode());
    }
}
