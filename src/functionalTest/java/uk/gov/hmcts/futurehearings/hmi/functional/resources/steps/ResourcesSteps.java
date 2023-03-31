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
@SuppressWarnings({"PMD.LawOfDemeter"})
public class ResourcesSteps {

    @Step("User performs the creation of a linked hearing group with an invalid payload")
    public void shouldCreateLinkedHearingGroup(final String apiUrl,
                                               final Map<String, Object> headersAsMap,
                                               final String authorizationToken,
                                               final HttpMethod httpMethod,
                                               final String body) {

        Response response = callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body,
                httpMethod,
                HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Step("User performs the deletion of a linked hearing group")
    public void shouldDeleteLinkedHearingGroupInvalid(final String apiUrl,
                                                      final Map<String, Object> headersAsMap,
                                                      final String authorizationToken,
                                                      final HttpMethod httpMethod) {
        Response response = callRestEndpointDelete(apiUrl,
                headersAsMap,
                authorizationToken,
                HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }
}
