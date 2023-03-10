package uk.gov.hmcts.futurehearings.hmi.functional.directlisting.steps;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

@SuppressWarnings("PMD.LawOfDemeter")
public class DirectListingSteps {

    @Step("User makes a request to Direct Listing")
    public void performDirectListingRequest(final String apiUrl,
                                            final Map<String, Object> headersAsMap,
                                            final String authorizationToken,
                                            final String body) {
        Response response = callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body, HttpMethod.POST, HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }
}
