package uk.gov.hmcts.futurehearings.hmi.contract.consumer.hearings;

import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.PACTFactory.buildPact;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.RestDelegate.invokeAPI;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AmendHearingAPIConsumerTest extends ContractTest {

    public static final String PUT_AMEND_HEARING_REQUEST_MESSAGE_SCHEMA_FILE = "/hearingRequestMessage.json";
    private static final String PROVIDER_AMEND_SnL_HEARING_API_PATH = "/casehqapi/rest/hmcts/resources/hearings/1234";

    public static final String AMEND_HEARING_COMPLETE_ENTITIES_IND_ORG_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/hearings/amend/amend-hearing-complete-entities-ind-org-payload.json";
    public static final String AMEND_HEARING_COMPLETE_STANDARD_NO_ENTITIES_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/hearings/amend/amend-hearing-standard-no-entities-org-payload.json";

    public static final String AMEND_HEARING_STANDARD_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/hearings/amend/amend-hearing-standard-payload.json";
    public static final String AMEND_HEARING_MANDATORY_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/hearings/amend/amend-hearing-mandatory-payload.json";

    @BeforeAll
    public void initialiseValues() throws Exception {
        this.setDestinationSystem("SNL");
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createCompletePayloadWithIndOrgEntitiesForAmendHearingAPIPactPUT(
            PactDslWithProvider builder) throws IOException {

        return buildPact(headersAsMap,builder,
                "Provider confirms amend hearing request received for a complete payload with 2 Entities(Ind and Org) populated - PUT",
                AMEND_HEARING_COMPLETE_ENTITIES_IND_ORG_PAYLOAD_JSON_PATH,
                PROVIDER_AMEND_SnL_HEARING_API_PATH,
                HttpMethod.PUT,
                HttpStatus.OK,
                "Amend Hearing API");
    }

    @Test
    @PactTestFor(pactMethod = "createCompletePayloadWithIndOrgEntitiesForAmendHearingAPIPactPUT")
    void shouldCompletePayloadWithIndOrgEntitiesForAmendHearingAPIPOST(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(AMEND_HEARING_COMPLETE_ENTITIES_IND_ORG_PAYLOAD_JSON_PATH))),
                PUT_AMEND_HEARING_REQUEST_MESSAGE_SCHEMA_FILE);
        invokeAPI(headersAsMap,
                getAuthorizationToken(),
                AMEND_HEARING_COMPLETE_ENTITIES_IND_ORG_PAYLOAD_JSON_PATH,
                HttpMethod.PUT,mockServer,
                PROVIDER_AMEND_SnL_HEARING_API_PATH,
                HttpStatus.OK);
        Assertions.assertTrue(true);
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createCompletePayloadWithNoEntitiesForAmendHearingAPIPactPUT(
            PactDslWithProvider builder) throws IOException {

        return buildPact(headersAsMap,builder,
                "Provider confirms amend hearing request received for a complete payload with no Entities populated - PUT",
                AMEND_HEARING_COMPLETE_STANDARD_NO_ENTITIES_PAYLOAD_JSON_PATH,
                PROVIDER_AMEND_SnL_HEARING_API_PATH,
                HttpMethod.PUT,
                HttpStatus.OK,
                "Amend Hearing API");
    }

    @Test
    @PactTestFor(pactMethod = "createCompletePayloadWithNoEntitiesForAmendHearingAPIPactPUT")
    void shouldCompletePayloadWithNoEntitiesForAmendHearingAPIPUT(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(AMEND_HEARING_COMPLETE_STANDARD_NO_ENTITIES_PAYLOAD_JSON_PATH))),
                PUT_AMEND_HEARING_REQUEST_MESSAGE_SCHEMA_FILE);
        invokeAPI(headersAsMap,
                getAuthorizationToken(),
                AMEND_HEARING_COMPLETE_STANDARD_NO_ENTITIES_PAYLOAD_JSON_PATH,
                HttpMethod.PUT,mockServer,
                PROVIDER_AMEND_SnL_HEARING_API_PATH,
                HttpStatus.OK);
        Assertions.assertTrue(true);
    }


    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createStandardPayloadForAmendHearingAPIPactPUT(
            PactDslWithProvider builder) throws IOException {

        return buildPact(headersAsMap,builder,
                "Provider confirms amend hearing request received for a standard (only outer elements) payload - PUT",
                AMEND_HEARING_STANDARD_PAYLOAD_JSON_PATH,
                PROVIDER_AMEND_SnL_HEARING_API_PATH,
                HttpMethod.PUT,
                HttpStatus.OK,
                "Amend Hearing API");
    }

    @Test
    @PactTestFor(pactMethod = "createStandardPayloadForAmendHearingAPIPactPUT")
    void shouldStandardPayloadForAmendHearingAPIPUT(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(AMEND_HEARING_STANDARD_PAYLOAD_JSON_PATH))),
                PUT_AMEND_HEARING_REQUEST_MESSAGE_SCHEMA_FILE);
        invokeAPI(headersAsMap,
                getAuthorizationToken(),
                AMEND_HEARING_STANDARD_PAYLOAD_JSON_PATH,
                HttpMethod.PUT,mockServer,
                PROVIDER_AMEND_SnL_HEARING_API_PATH,
                HttpStatus.OK);
        Assertions.assertTrue(true);
    }


    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createMandatoryPayloadForAmendHearingAPIPactPUT(
            PactDslWithProvider builder) throws IOException {

        return buildPact(headersAsMap,builder,
                "Provider confirms amend hearing request received for the most basic mandatory payload - PUT",
                AMEND_HEARING_MANDATORY_PAYLOAD_JSON_PATH,
                PROVIDER_AMEND_SnL_HEARING_API_PATH,
                HttpMethod.PUT,
                HttpStatus.OK,
                "Amend Hearing API");
    }

    @Test
    @PactTestFor(pactMethod = "createMandatoryPayloadForAmendHearingAPIPactPUT")
    void shouldMandatoryPayloadForAmendHearingAPIPUT(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(AMEND_HEARING_MANDATORY_PAYLOAD_JSON_PATH))),
                PUT_AMEND_HEARING_REQUEST_MESSAGE_SCHEMA_FILE);
        invokeAPI(headersAsMap,
                getAuthorizationToken(),
                AMEND_HEARING_MANDATORY_PAYLOAD_JSON_PATH,
                HttpMethod.PUT,mockServer,
                PROVIDER_AMEND_SnL_HEARING_API_PATH,
                HttpStatus.OK);
        Assertions.assertTrue(true);
    }
}
