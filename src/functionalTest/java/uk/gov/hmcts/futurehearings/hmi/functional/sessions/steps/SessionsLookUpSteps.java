package uk.gov.hmcts.futurehearings.hmi.functional.sessions.steps;

import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;

@Slf4j
public class SessionsLookUpSteps {

    @Step("User makes a request to Get the Sessions with the right Query parameter conditions")
    public void checkSessionsForAllTheRelevantQueryParameters(final String apiURL,
                                                              final Map<String, Object> headersAsMap,
                                                              final String authorizationToken,
                                                              final Map<String, String> queryParameters) {
        checkSessionsForAllTheRelevantQueryParameters(apiURL, headersAsMap, authorizationToken, queryParameters, HttpStatus.OK);
    }

    @Step("User makes a request to Get the Sessions with valid and invalid Query parameter values")
    public void checkSessionsForAllTheRelevantQueryParameters(final String apiURL,
                                                              final Map<String, Object> headersAsMap,
                                                              final String authorizationToken,
                                                              final Map<String, String> queryParameters,
                                                              HttpStatus expectedStatusCode) {

         callRestEndpointWithQueryParams(apiURL,
                headersAsMap,
                authorizationToken,
                queryParameters, expectedStatusCode);
    }
}
