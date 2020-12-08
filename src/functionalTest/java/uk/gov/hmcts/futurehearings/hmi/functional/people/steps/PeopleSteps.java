package uk.gov.hmcts.futurehearings.hmi.functional.people.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;
import static uk.gov.hmcts.futurehearings.hmi.functional.people.PeopleLookUpResponseProcess.getPeopleId;

import java.util.Map;
import java.util.Objects;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class PeopleSteps {

    private String actor;

    @Step("User makes a request to Get List of People for relevant search parameters on the People API")
    public String getPeopleIdForELinks(final String apiURL,
                                               final Map<String, Object> headersAsMap,
                                               final String authorizationToken,
                                               final Map<String, String> queryParameters) throws Exception {

        String peopleId = getPeopleId(callRestEndpointWithQueryParams(apiURL,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK));
        assertTrue(Objects.nonNull(peopleId) && !peopleId.trim().equals(""));
        return peopleId;
    }

    @Step("User makes a request to Get List of People for relevant search parameters on the People API")
    public void performGetByPeopleId(final String apiURL, final String peopleId,
                                       final Map<String, Object> headersAsMap,
                                       final String authorizationToken,
                                       final Map<String, String> queryParameters) throws Exception {

        Response response = callRestEndpointWithQueryParams(apiURL,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
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
