package uk.gov.hmcts.futurehearings.hmi.functional.people.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.*;

import java.util.Map;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class PeopleSteps {

    @Step("User makes a request to Get List of People for relevant search parameters on the People API")
    public Response shouldFetchListOfPeople(final String apiURL,
                                       final Map<String, Object> headersAsMap,
                                       final String authorizationToken,
                                       final Map<String, String> queryParameters) {

        Response response = callRestEndpointWithQueryParams(apiURL,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
        assertEquals(HttpStatus.OK.value(),response.getStatusCode());
        return response;
    }

    @Step("User makes a request to Get List of People for relevant search parameters on the People API")
    public void shouldGetByPeopleId(final String apiURL, final Map<String, Object> headersAsMap,
                                       final String authorizationToken) {

        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                null, HttpMethod.GET,HttpStatus.NOT_FOUND);
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatusCode());
    }

}
