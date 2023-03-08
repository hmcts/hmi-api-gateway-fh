package uk.gov.hmcts.futurehearings.hmi.functional.reservations.steps;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;

@SuppressWarnings({"PMD.LinguisticNaming", "PMD.LawOfDemeter"})
public class ReservationSteps {

    @Step("User makes a request to Get List of Reservations for relevant search parameters on the Hearings")
    public Response shouldFetchListOfReservations(final String apiUrl,
                                            final Map<String, Object> headersAsMap,
                                            final String authorizationToken,
                                            final Map<String, String> queryParameters) {

        Response response = callRestEndpointWithQueryParams(apiUrl,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        return response;
    }
}
