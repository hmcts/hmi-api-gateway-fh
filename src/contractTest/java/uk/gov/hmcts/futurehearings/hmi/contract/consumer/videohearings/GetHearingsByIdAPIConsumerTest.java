package uk.gov.hmcts.futurehearings.hmi.contract.consumer.videohearings;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.PACTFactory.buildResponsePact;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.RestDelegate.invokeAPI;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.test.ContractTest;

import java.io.IOException;
import java.net.URISyntaxException;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("contract")
@SpringBootTest(classes = {Application.class})
@ExtendWith(PactConsumerTestExt.class)
@ExtendWith(SpringExtension.class)
public class GetHearingsByIdAPIConsumerTest extends ContractTest {

    private static final String PROVIDER_VIDEO_HEARINGS_API = "/hearings/12345";
    public static final String GET_HEARINGS_COMPLETE_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/response/videohearings/get-hearings-by-id-response.json";

    @Pact(provider = "VideoHearings_API", consumer = "HMI_API")
    public RequestResponsePact createCompleteGetHearingsResponsePact(
            PactDslWithProvider builder) throws IOException {

        return buildResponsePact(headersAsMap, builder,
                "Provider confirms complete response received for the GET Sessions",
                GET_HEARINGS_COMPLETE_PAYLOAD_JSON_PATH,
                PROVIDER_VIDEO_HEARINGS_API,
                null,
                HttpMethod.GET,
                HttpStatus.OK,
                "Sessions API"
        );
    }

    @Test
    @PactTestFor(pactMethod = "createCompleteGetHearingsResponsePact")
    void shouldGetCompleteHearingsResponse(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        Response response = invokeAPI(headersAsMap,
                getAuthorizationToken(),
                GET_HEARINGS_COMPLETE_PAYLOAD_JSON_PATH,
                HttpMethod.GET,
                mockServer,
                PROVIDER_VIDEO_HEARINGS_API,
                HttpStatus.OK);

        verifyMandatoryResponse(response);
    }

    private void verifyMandatoryResponse(Response response) {
        assertTrue(response.getBody().asString().contains("id"));
        assertTrue(response.getBody().asString().contains("scheduled_date_time"));
        assertTrue(response.getBody().asString().contains("hearing_venue_name"));
        assertTrue(response.getBody().asString().contains("hearing_type_name"));
        assertTrue(response.getBody().asString().contains("hearing_room_name"));
    }
}
