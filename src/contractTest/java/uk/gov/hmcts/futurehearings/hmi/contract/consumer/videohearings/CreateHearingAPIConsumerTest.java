package uk.gov.hmcts.futurehearings.hmi.contract.consumer.videohearings;

import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.PACTFactory.buildPact;
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
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
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
class CreateHearingAPIConsumerTest extends ContractTest {

    public static final String CREATE_HEARING_MANDATORY_PAYLOAD_JSON_PATH
            = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/videohearings/create-video-hearing-mandatory-payload.json";
    public static final String CREATE_HEARING_STANDARD_PAYLOAD_JSON_PATH
            = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/videohearings/create-video-hearing-standard-payload.json";
    private static final String PROVIDER_CREATE_A_HEARING_API_PATH = "/hearings";

    @Pact(provider = "VideoHearings_API", consumer = "HMI_API")
    public RequestResponsePact createMandatoryPayloadToCreateAHearingAPIPactPOST (
            PactDslWithProvider builder) throws IOException {

        return buildPact(headersAsMap,builder,
                "Provider confirms create booking/hearing request received for a mandatory payload POST",
                CREATE_HEARING_MANDATORY_PAYLOAD_JSON_PATH,//TODO - The payload has to be made dynamic for a Date in the future.
                PROVIDER_CREATE_A_HEARING_API_PATH,
                HttpMethod.POST,
                HttpStatus.CREATED,
                "Video Hearing API");
    }

    @Test
    @PactTestFor(pactMethod = "createMandatoryPayloadToCreateAHearingAPIPactPOST")
    void shouldCreateMandatoryPayloadToCreateAHearingAPIPactPOST(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        invokeAPI(headersAsMap,
                getAuthorizationToken(),//TODO - Checkup on the Auth Details for Video Hearings.
                CREATE_HEARING_MANDATORY_PAYLOAD_JSON_PATH,
                HttpMethod.POST,mockServer,
                PROVIDER_CREATE_A_HEARING_API_PATH,
                HttpStatus.CREATED);
        Assertions.assertTrue(true);
    }

    @Pact(provider = "VideoHearings_API", consumer = "HMI_API")
    public RequestResponsePact createStandardPayloadToCreateAHearingAPIPactPOST (
            PactDslWithProvider builder) throws IOException {
        return buildPact(headersAsMap,builder,
                "Provider confirms create booking/hearing request received for a standard payload POST",
                CREATE_HEARING_STANDARD_PAYLOAD_JSON_PATH,//TODO - The payload has to be made dynamic for a Date in the future.
                PROVIDER_CREATE_A_HEARING_API_PATH,
                HttpMethod.POST,
                HttpStatus.CREATED,
                "Video Hearing API");
    }

    @Test
    @PactTestFor(pactMethod = "createStandardPayloadToCreateAHearingAPIPactPOST")
    void shouldCreateStandardPayloadToCreateAHearingAPIPactPOST(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {
        invokeAPI(headersAsMap,
                getAuthorizationToken(),//TODO - Checkup on the Auth Details for Video Hearings.
                CREATE_HEARING_STANDARD_PAYLOAD_JSON_PATH,
                HttpMethod.POST,mockServer,
                PROVIDER_CREATE_A_HEARING_API_PATH,
                HttpStatus.CREATED);
        Assertions.assertTrue(true);
    }
}
