package uk.gov.hmcts.futurehearings.hmi.contract.consumer.sessions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.PACTFactory.buildResponsePact;
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
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
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
class GetSessionsAPIConsumerTests extends ContractTest {

    private static final String PROVIDER_SnL_GET_SESSION_API_PATH = "/casehqapi/rest/hmcts/resources/sessions";

    public static final String GET_SESSION_RESPONSE_SCHEMA_JSON = "/getSessionsResponseMessage.json";
    public static final String GET_SESSION_COMPLETE_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/response/sessions/get-sessions-complete-response.json";
    public static final String GET_SESSION_MANDATORY_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/response/sessions/get-sessions-mandatory-response.json";

    @BeforeAll
    public void initialiseValues() throws Exception {
        this.setDestinationSystem("SNL");
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createCompleteGetSessionsResponsePact(
            PactDslWithProvider builder) throws IOException {

        return buildResponsePact(headersAsMap, builder,
                "Provider confirms complete response received for the GET Sessions",
                GET_SESSION_COMPLETE_PAYLOAD_JSON_PATH,
                PROVIDER_SnL_GET_SESSION_API_PATH,
                null,
                HttpMethod.GET,
                HttpStatus.OK,
                "Sessions API"
                );
    }

    @Test
    @PactTestFor(pactMethod = "createCompleteGetSessionsResponsePact")
    void shouldGetCompleteSessionResponse(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(GET_SESSION_COMPLETE_PAYLOAD_JSON_PATH))),
                GET_SESSION_RESPONSE_SCHEMA_JSON);

        Response response = invokeAPI(headersAsMap,
                getAuthorizationToken(),
                GET_SESSION_COMPLETE_PAYLOAD_JSON_PATH,
                HttpMethod.GET,
                mockServer,
                PROVIDER_SnL_GET_SESSION_API_PATH,
                HttpStatus.OK);

        verifyMandatoryResponse(response);
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createMandatoryGetSessionsResponsePact(
            PactDslWithProvider builder) throws IOException {

        return buildResponsePact(headersAsMap, builder,
                "Provider confirms only mandatory response received for GET Sessions",
                GET_SESSION_MANDATORY_PAYLOAD_JSON_PATH,
                PROVIDER_SnL_GET_SESSION_API_PATH,
                null,
                HttpMethod.GET,
                HttpStatus.OK,
                "Sessions API"
        );
    }

    @Test
    @PactTestFor(pactMethod = "createMandatoryGetSessionsResponsePact")
    void shouldGetMandatorySessionResponse(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(GET_SESSION_MANDATORY_PAYLOAD_JSON_PATH))),
                GET_SESSION_RESPONSE_SCHEMA_JSON);

        Response response = invokeAPI(headersAsMap,
                getAuthorizationToken(),
                GET_SESSION_MANDATORY_PAYLOAD_JSON_PATH,
                HttpMethod.GET,
                mockServer,
                PROVIDER_SnL_GET_SESSION_API_PATH,
                HttpStatus.OK);

        verifyMandatoryResponse(response);
    }

    private void verifyMandatoryResponse(Response response) {
        assertTrue(response.getBody().asString().contains("sessionIdCaseHQ"));
        assertTrue(response.getBody().asString().contains("sessionType"));
        assertTrue(response.getBody().asString().contains("sessionStartTime"));
        assertTrue(response.getBody().asString().contains("sessionDuration"));
        assertTrue(response.getBody().asString().contains("sessionVenueId"));
    }
}



