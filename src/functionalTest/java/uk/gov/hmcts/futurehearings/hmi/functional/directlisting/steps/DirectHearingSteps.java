package uk.gov.hmcts.futurehearings.hmi.functional.directlisting.steps;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

@Slf4j
@SuppressWarnings("PMD.LawOfDemeter")
public class DirectHearingSteps {
    @Step("User makes a request to List a Hearing Request Directly (POST in the Hearing API)")
    public void performDirectHearingListingForGivenSessionId(final String apiUrl,
                                                      final Map<String, Object> headersAsMap,
                                                      final String authorizationToken,
                                                      final String body) {
        Response response = callRestEndpointWithPayload(apiUrl,
                headersAsMap,
                authorizationToken,
                body, HttpMethod.PUT,
                HttpStatus.BAD_REQUEST);
        log.info(response.getBody().asString());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }
}
