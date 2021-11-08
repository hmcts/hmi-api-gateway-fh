package uk.gov.hmcts.futurehearings.hmi.functional.directlisting.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

import java.util.Map;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class DirectHearingSteps {
    @Step("User makes a request to List a Hearing Request Directly (POST in the Hearing API)")
    public void performDirectHearingListingForGivenSessionId(final String apiURL,
                                                      final Map<String, Object> headersAsMap,
                                                      final String authorizationToken,
                                                      final String body) {
        Response response = callRestEndpointWithPayload(apiURL,
                headersAsMap,
                authorizationToken,
                body, HttpMethod.PUT,
                HttpStatus.BAD_REQUEST);
        System.out.println(response.getBody().asString());
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatusCode());
    }
}
