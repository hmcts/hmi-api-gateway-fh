package uk.gov.hmcts.futurehearings.hmi.contract.consumer.hearings;

import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.PACTFactory.buildPactForSnL;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.RestDelegate.invokeSnLAPI;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.TestingUtils.readFileContents;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.validation.factory.PayloadValidationFactory.validateHMIPayload;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.ContractTest;

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
class RequestHearingAPIConsumerTest extends ContractTest {



    public static final String POST_HEARING_REQUEST_MESSAGE_SCHEMA_FILE = "/hearingRequestMessage.json";
    private static final String PROVIDER_REQUEST_SnL_HEARING_API_PATH = "/casehqapi/rest/hmcts/resources/hearings";

    public static final String REQUEST_HEARING_COMPLETE_ENTITIES_IND_ORG_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/hearings/request-hearing-complete-entities-ind-org-payload.json";
    public static final String REQUEST_HEARING_COMPLETE_STANDARD_NO_ENTITIES_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/hearings/request-hearing-standard-no-entities-org-payload.json";

    public static final String REQUEST_HEARING_STANDARD_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/hearings/request-hearing-standard-payload.json";
    public static final String REQUEST_HEARING_MANDATORY_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/hearings/request-hearing-mandatory-payload.json";

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createCompletePayloadWithIndOrgEntitiesForRequestHearingAPIPactPOST(
            PactDslWithProvider builder) throws IOException {

        return buildPactForSnL(headersAsMap,builder,
                "Provider confirms request received for a complete payload with 2 Entities(Ind and Org) populated - POST",
                REQUEST_HEARING_COMPLETE_ENTITIES_IND_ORG_PAYLOAD_JSON_PATH,
                PROVIDER_REQUEST_SnL_HEARING_API_PATH,
                HttpMethod.POST,
                HttpStatus.OK,
                "Request Hearing API");
    }

    @Test
    @PactTestFor(pactMethod = "createCompletePayloadWithIndOrgEntitiesForRequestHearingAPIPactPOST")
    void shouldCompletePayloadWithIndOrgEntitiesForRequestHearingAPIPOST(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(REQUEST_HEARING_COMPLETE_ENTITIES_IND_ORG_PAYLOAD_JSON_PATH))),
                POST_HEARING_REQUEST_MESSAGE_SCHEMA_FILE);
        invokeSnLAPI(headersAsMap,
                authorizationToken,
                REQUEST_HEARING_COMPLETE_ENTITIES_IND_ORG_PAYLOAD_JSON_PATH,
                HttpMethod.POST,mockServer,
                PROVIDER_REQUEST_SnL_HEARING_API_PATH,
                HttpStatus.OK);
        Assertions.assertTrue(true);
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createCompletePayloadWithNoEntitiesForRequestHearingAPIPactPOST(
            PactDslWithProvider builder) throws IOException {

        return buildPactForSnL(headersAsMap,builder,
                "Provider confirms request received for a complete payload with no Entities populated - POST",
                REQUEST_HEARING_COMPLETE_STANDARD_NO_ENTITIES_PAYLOAD_JSON_PATH,
                PROVIDER_REQUEST_SnL_HEARING_API_PATH,
                HttpMethod.POST,
                HttpStatus.OK,
                "Request Hearing API");
    }

    @Test
    @PactTestFor(pactMethod = "createCompletePayloadWithNoEntitiesForRequestHearingAPIPactPOST")
    void shouldCompletePayloadWithNoEntitiesForRequestHearingAPIPOST(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(REQUEST_HEARING_COMPLETE_STANDARD_NO_ENTITIES_PAYLOAD_JSON_PATH))),
                POST_HEARING_REQUEST_MESSAGE_SCHEMA_FILE);
        invokeSnLAPI(headersAsMap,
                authorizationToken,
                REQUEST_HEARING_COMPLETE_STANDARD_NO_ENTITIES_PAYLOAD_JSON_PATH,
                HttpMethod.POST,mockServer,
                PROVIDER_REQUEST_SnL_HEARING_API_PATH,
                HttpStatus.OK);
        Assertions.assertTrue(true);
    }


    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createStandardPayloadForRequestHearingAPIPactPOST(
            PactDslWithProvider builder) throws IOException {

        return buildPactForSnL(headersAsMap,builder,
                "Provider confirms request received for a standard (only outer elements) payload - POST",
                REQUEST_HEARING_STANDARD_PAYLOAD_JSON_PATH,
                PROVIDER_REQUEST_SnL_HEARING_API_PATH,
                HttpMethod.POST,
                HttpStatus.OK,
                "Request Hearing API");
    }

    @Test
    @PactTestFor(pactMethod = "createStandardPayloadForRequestHearingAPIPactPOST")
    void shouldStandardPayloadForRequestHearingAPIPOST(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(REQUEST_HEARING_STANDARD_PAYLOAD_JSON_PATH))),
                POST_HEARING_REQUEST_MESSAGE_SCHEMA_FILE);
        invokeSnLAPI(headersAsMap,
                authorizationToken,
                REQUEST_HEARING_STANDARD_PAYLOAD_JSON_PATH,
                HttpMethod.POST,mockServer,
                PROVIDER_REQUEST_SnL_HEARING_API_PATH,
                HttpStatus.OK);
        Assertions.assertTrue(true);
    }


    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createMandatoryPayloadForRequestHearingAPIPactPOST(
            PactDslWithProvider builder) throws IOException {

        return buildPactForSnL(headersAsMap,builder,
                "Provider confirms request received for the most basic mandatory payload - POST",
                REQUEST_HEARING_MANDATORY_PAYLOAD_JSON_PATH,
                PROVIDER_REQUEST_SnL_HEARING_API_PATH,
                HttpMethod.POST,
                HttpStatus.OK,
                "Request Hearing API");
    }

    @Test
    @PactTestFor(pactMethod = "createMandatoryPayloadForRequestHearingAPIPactPOST")
    void shouldMandatoryPayloadForRequestHearingAPIPOST(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(REQUEST_HEARING_MANDATORY_PAYLOAD_JSON_PATH))),
                POST_HEARING_REQUEST_MESSAGE_SCHEMA_FILE);
        invokeSnLAPI(headersAsMap,
                authorizationToken,
                REQUEST_HEARING_MANDATORY_PAYLOAD_JSON_PATH,
                HttpMethod.POST,mockServer,
                PROVIDER_REQUEST_SnL_HEARING_API_PATH,
                HttpStatus.OK);
        Assertions.assertTrue(true);
    }
}
