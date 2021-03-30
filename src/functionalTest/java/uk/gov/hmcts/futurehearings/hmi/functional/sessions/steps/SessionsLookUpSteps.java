package uk.gov.hmcts.futurehearings.hmi.functional.sessions.steps;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpStatus;

@Slf4j
public class SessionsLookUpSteps {

    @Step("User makes a request to Get the Sessions with the right Query parameter conditions")
    public void checkSessionsForAllTheRelevantQueryParameters(final String apiURL,
                                                              final Map<String, Object> headersAsMap,
                                                              final String authorizationToken,
                                                              final Map<String, String> queryParameters) {

         callRestEndpointWithQueryParams(apiURL,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
    }
}
