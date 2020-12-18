package uk.gov.hmcts.futurehearings.hmi.contract.consumer.videohearings;

import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.PACTFactory.buildPactForSnL;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.RestDelegate.invokeSnLAPI;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.TestingUtils.readFileContents;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.validation.factory.PayloadValidationFactory.validateHMIPayload;

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
import org.json.JSONObject;
import org.json.JSONTokener;
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

    public static final String CREATE_HEARING_MANDATORY_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/videohearings//amend-hearing-complete-entities-ind-org-payload.json";
    private static final String PROVIDER_CREATE_A_HEARING_API_PATH = "/hearings";

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createMandatoryPayloadToCreateAHearingAPIPactPOST (
            PactDslWithProvider builder) throws IOException {

        return buildPactForSnL(headersAsMap,builder,
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

        invokeSnLAPI(headersAsMap,
                getAuthorizationToken(),//TODO - Checkup on the Auth Details for Video Hearings.
                CREATE_HEARING_MANDATORY_PAYLOAD_JSON_PATH,
                HttpMethod.POST,mockServer,
                PROVIDER_CREATE_A_HEARING_API_PATH,
                HttpStatus.CREATED);
        Assertions.assertTrue(true);
    }
}
