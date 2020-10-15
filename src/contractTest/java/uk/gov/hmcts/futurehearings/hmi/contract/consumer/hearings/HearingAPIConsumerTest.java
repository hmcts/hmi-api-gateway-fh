package uk.gov.hmcts.futurehearings.hmi.contract.consumer.hearings;

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
import org.apache.http.entity.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("contract")
@SpringBootTest(classes = {Application.class})
@ExtendWith(PactConsumerTestExt.class)
@ExtendWith(SpringExtension.class)
class HearingAPIConsumerTest {



    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    public static final String POST_HEARING_REQUEST_MESSAGE_SCHEMA_FILE = "/hearingRequestMessage.json";
    private static final String PROVIDER_REQUEST_SnL_HEARING_API_PATH = "/hmcts/hearings";

    private Map<String, String> headersAsMap = new HashMap<>();
    public static final String REQUEST_HEARING_COMPLETE_ENTITIES_IND_ORG_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/hearings/request-hearing-complete-entities-ind-org-payload.json";
    public static final String REQUEST_HEARING_COMPLETE_STANDARD_NO_ENTITIES_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/hearings/request-hearing-standard-no-entities-org-payload.json";

    public static final String REQUEST_HEARING_STANDARD_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/hearings/request-hearing-standard-payload.json";
    public static final String REQUEST_HEARING_MANDATORY_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/hearings/request-hearing-mandatory-payload.json";

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
    public RequestResponsePact createCompletePayloadWithIndOrgEntitiesForRequestHearingAPIPact(
            PactDslWithProvider builder) throws IOException {

        return buildPactForRequestHearing(builder,
                "Provider confirms request received for a complete payload with 2 Entities(Ind and Org) populated",
                REQUEST_HEARING_COMPLETE_ENTITIES_IND_ORG_PAYLOAD_JSON_PATH);
    }

    @Test
    @PactTestFor(pactMethod = "createCompletePayloadWithIndOrgEntitiesForRequestHearingAPIPact")
    void shouldCompletePayloadWithIndOrgEntitiesForRequestHearingAPIAndReturn200(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(REQUEST_HEARING_COMPLETE_ENTITIES_IND_ORG_PAYLOAD_JSON_PATH))),
                POST_HEARING_REQUEST_MESSAGE_SCHEMA_FILE);
        invokeHearingRequest(mockServer, REQUEST_HEARING_COMPLETE_ENTITIES_IND_ORG_PAYLOAD_JSON_PATH);
        Assertions.assertTrue(true);
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createCompletePayloadWithNoEntitiesForRequestHearingAPIPact(
            PactDslWithProvider builder) throws IOException {

        return buildPactForRequestHearing(builder,
                "Provider confirms request received for a complete payload with no Entities populated",
                REQUEST_HEARING_COMPLETE_STANDARD_NO_ENTITIES_PAYLOAD_JSON_PATH);
    }

    @Test
    @PactTestFor(pactMethod = "createCompletePayloadWithNoEntitiesForRequestHearingAPIPact")
    void shouldCompletePayloadWithNoEntitiesForRequestHearingAPIAndReturn200(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(REQUEST_HEARING_COMPLETE_STANDARD_NO_ENTITIES_PAYLOAD_JSON_PATH))),
                POST_HEARING_REQUEST_MESSAGE_SCHEMA_FILE);
        invokeHearingRequest(mockServer, REQUEST_HEARING_COMPLETE_STANDARD_NO_ENTITIES_PAYLOAD_JSON_PATH);
        Assertions.assertTrue(true);
    }


    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createStandardPayloadForRequestHearingAPIPact(
            PactDslWithProvider builder) throws IOException {
        return buildPactForRequestHearing(builder,
                "Provider confirms request received for a standard (only outer elements) payload",
                REQUEST_HEARING_STANDARD_PAYLOAD_JSON_PATH);
    }

    @Test
    @PactTestFor(pactMethod = "createStandardPayloadForRequestHearingAPIPact")
    void shouldStandardPayloadForRequestHearingAPIAndReturn200(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(REQUEST_HEARING_STANDARD_PAYLOAD_JSON_PATH))),
                POST_HEARING_REQUEST_MESSAGE_SCHEMA_FILE);
        invokeHearingRequest(mockServer, REQUEST_HEARING_STANDARD_PAYLOAD_JSON_PATH);
        Assertions.assertTrue(true);
    }


    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createMandatoryPayloadForRequestHearingAPIPact(
            PactDslWithProvider builder) throws IOException {

        return buildPactForRequestHearing(builder,
                "Provider confirms request received for the most basic mandatory payload",
                REQUEST_HEARING_MANDATORY_PAYLOAD_JSON_PATH);
    }

    @Test
    @PactTestFor(pactMethod = "createMandatoryPayloadForRequestHearingAPIPact")
    void shouldMandatoryPayloadForRequestHearingAPIAndReturn200(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(REQUEST_HEARING_MANDATORY_PAYLOAD_JSON_PATH))),
                POST_HEARING_REQUEST_MESSAGE_SCHEMA_FILE);
        invokeHearingRequest(mockServer, REQUEST_HEARING_MANDATORY_PAYLOAD_JSON_PATH);
        Assertions.assertTrue(true);
    }

    private RequestResponsePact buildPactForRequestHearing(final PactDslWithProvider builder,
                                                           final String pactDescription,
                                                           final String requestHearingMandatoryPayloadJsonPath) throws IOException {
        return builder
                .given("Request Hearing API")
                .uponReceiving(pactDescription)
                .path(PROVIDER_REQUEST_SnL_HEARING_API_PATH)
                .method(HttpMethod.POST.toString())
                .headers(headersAsMap)
                .body(readFileContents(requestHearingMandatoryPayloadJsonPath), ContentType.APPLICATION_JSON)
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .toPact();
    }

    private void invokeHearingRequest(final MockServer mockServer,
                                      final String requestHearingPayload) throws IOException {
        RestAssured
                .given()
                .headers(headersAsMap)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(readFileContents(requestHearingPayload))
                .when()
                .post(mockServer.getUrl() + PROVIDER_REQUEST_SnL_HEARING_API_PATH)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
