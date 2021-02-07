package uk.gov.hmcts.futurehearings.hmi.contract.consumer.resources;

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
class ResourcesByUserAPIConsumerTests extends ContractTest {

    private static final String PROVIDER_REQUEST_SnL_USER_RESOURCE_API_PATH = "/casehqapi/rest/hmcts/resources/user";

    public static final String RESOURCES_USER_REQUEST_SCHEMA_JSON = "/userMessage.json";
    public static final String RESOURCES_USER_REQUEST_COMPLETE_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/resources/request-user-complete-payload.json";
    public static final String RESOURCES_USER_REQUEST_OPTIONAL_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/resources/request-user-optional-payload.json";

    @BeforeAll
    public void initialiseValues() throws Exception {
        this.setDestinationSystem("SNL");
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createCompletePOSTPayloadForRequestUserAPIPact(
            PactDslWithProvider builder) throws IOException {

        return buildPact(headersAsMap, builder,
                "Provider confirms request received for a complete payload for an Resource By User - POST",
                RESOURCES_USER_REQUEST_COMPLETE_PAYLOAD_JSON_PATH,
                PROVIDER_REQUEST_SnL_USER_RESOURCE_API_PATH,
                HttpMethod.POST,
                HttpStatus.OK,
                "Resource User API"
                );
    }

    @Test
    @PactTestFor(pactMethod = "createCompletePOSTPayloadForRequestUserAPIPact")
    void shouldPOSTCompletePayloadForRequestResourceByUserAPIAndReturn200(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(RESOURCES_USER_REQUEST_COMPLETE_PAYLOAD_JSON_PATH))),
                RESOURCES_USER_REQUEST_SCHEMA_JSON);

        invokeAPI(headersAsMap,
                getAuthorizationToken(),
                RESOURCES_USER_REQUEST_COMPLETE_PAYLOAD_JSON_PATH,
                HttpMethod.POST,
                mockServer,
                PROVIDER_REQUEST_SnL_USER_RESOURCE_API_PATH,
                HttpStatus.OK);

        Assertions.assertTrue(true);
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createOptionalPOSTPayloadForRequestUserAPIPact(
            PactDslWithProvider builder) throws IOException {

        return buildPact(headersAsMap,
                builder,
                "Provider confirms request received for an optional payload for an Resource By User - POST",
                RESOURCES_USER_REQUEST_OPTIONAL_PAYLOAD_JSON_PATH,
                PROVIDER_REQUEST_SnL_USER_RESOURCE_API_PATH,
                HttpMethod.POST,
                HttpStatus.OK,
                "Resource User API"
        );
    }

    @Test
    @PactTestFor(pactMethod = "createOptionalPOSTPayloadForRequestUserAPIPact")
    void shouldPOSTOptionalPayloadWithDetailsForRequestUserAPIAndReturn200(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(RESOURCES_USER_REQUEST_OPTIONAL_PAYLOAD_JSON_PATH))),
                RESOURCES_USER_REQUEST_SCHEMA_JSON);

        invokeAPI(headersAsMap,
                getAuthorizationToken(),
                RESOURCES_USER_REQUEST_OPTIONAL_PAYLOAD_JSON_PATH,
                HttpMethod.POST,
                mockServer,
                PROVIDER_REQUEST_SnL_USER_RESOURCE_API_PATH,
                HttpStatus.OK);

        Assertions.assertTrue(true);
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createCompletePUTPayloadForRequestUserAPIPact(
            PactDslWithProvider builder) throws IOException {

        return buildPact(headersAsMap, builder,
                "Provider confirms request received for a complete payload for an Resource By User - PUT",
                RESOURCES_USER_REQUEST_COMPLETE_PAYLOAD_JSON_PATH,
                PROVIDER_REQUEST_SnL_USER_RESOURCE_API_PATH,
                HttpMethod.PUT,
                HttpStatus.NO_CONTENT,
                "Resource User API"
        );
    }

    @Test
    @PactTestFor(pactMethod = "createCompletePUTPayloadForRequestUserAPIPact")
    void shouldPUTCompletePayloadForRequestUserAPIAndReturn204(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(RESOURCES_USER_REQUEST_COMPLETE_PAYLOAD_JSON_PATH))),
                RESOURCES_USER_REQUEST_SCHEMA_JSON);

        invokeAPI(headersAsMap,
                getAuthorizationToken(),
                RESOURCES_USER_REQUEST_COMPLETE_PAYLOAD_JSON_PATH,
                HttpMethod.PUT,
                mockServer,
                PROVIDER_REQUEST_SnL_USER_RESOURCE_API_PATH,
                HttpStatus.NO_CONTENT);

        Assertions.assertTrue(true);
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createOptionalPUTPayloadForRequestUserAPIPact(
            PactDslWithProvider builder) throws IOException {

        return buildPact(headersAsMap,
                builder,
                "Provider confirms request received for an optional payload for an Resource By User - PUT",
                RESOURCES_USER_REQUEST_OPTIONAL_PAYLOAD_JSON_PATH,
                PROVIDER_REQUEST_SnL_USER_RESOURCE_API_PATH,
                HttpMethod.PUT,
                HttpStatus.NO_CONTENT,
                "Resource User API"
        );
    }

    @Test
    @PactTestFor(pactMethod = "createOptionalPUTPayloadForRequestUserAPIPact")
    void shouldPUTOptionalPayloadWithDetailsForRequestUserAPIAndReturn204(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(RESOURCES_USER_REQUEST_OPTIONAL_PAYLOAD_JSON_PATH))),
                RESOURCES_USER_REQUEST_SCHEMA_JSON);

        invokeAPI(headersAsMap,
                getAuthorizationToken(),
                RESOURCES_USER_REQUEST_OPTIONAL_PAYLOAD_JSON_PATH,
                HttpMethod.PUT,
                mockServer,
                PROVIDER_REQUEST_SnL_USER_RESOURCE_API_PATH,
                HttpStatus.NO_CONTENT);

        Assertions.assertTrue(true);
    }
}

