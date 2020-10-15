package uk.gov.hmcts.futurehearings.hmi.contract.consumer.resources;

import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.TestingUtils.readFileContents;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.validation.factory.PayloadValidationFactory.validateHMIPayload;

import uk.gov.hmcts.futurehearings.hmi.Application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import io.restassured.RestAssured;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.springframework.http.MediaType;


@ActiveProfiles("contract")
@SpringBootTest(classes = {Application.class})
@ExtendWith(PactConsumerTestExt.class)
@ExtendWith(SpringExtension.class)
public class ResourcesAPIConsumerTests {

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    private static final String PROVIDER_REQUEST_HEARING_API = "/hmi/resources/user/1234";
    private static final String PROVIDER_REQUEST_SnL_USER_RESOURCE_API_PATH = "/hmcts/resources/user";

    private Map<String, String> headersAsMap = new HashMap<>();

    public static final String RESOURCES_USER_REQUEST_SCHEMA_JSON = "/userMessage.json";
    public static final String RESOURCES_USER_REQUEST_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/resources/resources-TEMPLATE-user-payload.json";
    public static final String RESOURCES_USER_REQUEST_EMPTY_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/resources/resources-TEMPLATE-user-empty-payload.json";

    @BeforeEach
    public void initialiseValues() {
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        //Only Commenting out in this step as this is not required as of the moment for the McGirr Deployment
        //headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", "S&L");
        headersAsMap.put("Request-Created-At", "2002-10-02T15:00:00Z");
        headersAsMap.put("Request-Processed-At", "2002-10-02 15:00:00Z");
        headersAsMap.put("Request-Type", "ASSAULT");
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createCompletePayloadForRequestResourceByUserAPIPact(
            PactDslWithProvider builder) throws IOException {

        return buildPactForUserResource(builder,
                "Provider confirms request received for a complete payload for an Resource By User",
                RESOURCES_USER_REQUEST_PAYLOAD_JSON_PATH);
    }

    @Test
    @PactTestFor(pactMethod = "createCompletePayloadForRequestResourceByUserAPIPact")
    void shouldCompletePayloadForRequestResourceByUserAPIAndReturn200(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(RESOURCES_USER_REQUEST_PAYLOAD_JSON_PATH))),
                RESOURCES_USER_REQUEST_SCHEMA_JSON);
        invokeUserResourceRequest(mockServer, RESOURCES_USER_REQUEST_PAYLOAD_JSON_PATH);
        Assertions.assertTrue(true);
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createEmptyPayloadWithDetailsForRequestResourceByUserAPIPact(
            PactDslWithProvider builder) throws IOException {

        return buildPactForUserResource(builder,
                "Provider confirms request received for a complete payload for an Resource By User",
                RESOURCES_USER_REQUEST_EMPTY_PAYLOAD_JSON_PATH);
    }

    @Test
    @PactTestFor(pactMethod = "createEmptyPayloadWithDetailsForRequestResourceByUserAPIPact")
    void shouldEmptyPayloadWithDetailsForRequestResourceByUserAPIAndReturn200(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(RESOURCES_USER_REQUEST_EMPTY_PAYLOAD_JSON_PATH))),
                RESOURCES_USER_REQUEST_SCHEMA_JSON);
        invokeUserResourceRequest(mockServer, RESOURCES_USER_REQUEST_EMPTY_PAYLOAD_JSON_PATH);
        Assertions.assertTrue(true);
    }

    private RequestResponsePact buildPactForUserResource(final PactDslWithProvider builder,
                                                           final String pactDescription,
                                                           final String requestHearingMandatoryPayloadJsonPath) throws IOException {
        return builder
                .given("Request User Resource API")
                .uponReceiving(pactDescription)
                .path(PROVIDER_REQUEST_SnL_USER_RESOURCE_API_PATH)
                .method(HttpMethod.PUT.toString())
                .headers(headersAsMap)
                .body(readFileContents(requestHearingMandatoryPayloadJsonPath), ContentType.APPLICATION_JSON)
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .toPact();
    }

    private void invokeUserResourceRequest(final MockServer mockServer,
                                      final String requestHearingPayload) throws IOException {
        RestAssured
                .given()
                .headers(headersAsMap)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(readFileContents(requestHearingPayload))
                .when()
                .put(mockServer.getUrl() + PROVIDER_REQUEST_SnL_USER_RESOURCE_API_PATH)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}

