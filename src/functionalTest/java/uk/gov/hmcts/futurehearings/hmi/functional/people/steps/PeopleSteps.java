package uk.gov.hmcts.futurehearings.hmi.functional.People.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.futurehearings.hmi.functional.People.PeopleLookUpResponseProcess.getPeopleId;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;

import java.util.Map;
import java.util.Objects;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpStatus;

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
    public void performGetByPeopleId(final String apiURL,
                                       final Map<String, Object> headersAsMap,
                                       final String authorizationToken,
                                       final Map<String, String> queryParameters) throws Exception {

        Response response = callRestEndpointWithQueryParams(apiURL,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
        assertEquals(HttpStatus.OK.value(),response.getStatusCode());
    }
}
