package uk.gov.hmcts.futurehearings.hmi.contract.consumer.resources;

import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.PACTFactory.buildPactForSnL;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.RestDelegate.invokeSnLAPI;
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
class ResourcesByLocationAPIConsumerTests {

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    public static final String POST_RESOURCE_LISTING_LOCATION_MESSAGE_SCHEMA_FILE = "/locationMessage.json";
    private static final String PROVIDER_REQUEST_SnL_RESOURCES_LOCATION_API_PATH = "/casehqapi/rest/hmcts/resources/location";

    private Map<String, String> headersAsMap = new HashMap<>();
    public static final String REQUEST_LISTING_COMPLETE_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/resources/request-location-complete-payload.json";
    public static final String REQUEST_LISTING_OPTIONAL_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/resources/request-location-optional-payload.json";

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
        headersAsMap.put("Request-Type", "THEFT");
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createCompletePayloadRequestResourceLocationAPIPactPOST(
            PactDslWithProvider builder) throws IOException {

        return buildPactForSnL(headersAsMap, builder,
                "Provider confirms request received for a complete payload For Request Resource Location - POST",
                REQUEST_LISTING_COMPLETE_PAYLOAD_JSON_PATH,
                PROVIDER_REQUEST_SnL_RESOURCES_LOCATION_API_PATH,
                HttpMethod.POST,
                HttpStatus.OK,
                "Resource Location API");
    }

    @Test
    @PactTestFor(pactMethod = "createCompletePayloadRequestResourceLocationAPIPactPOST")
    void shouldCompletePayloadForRequestResourceLocationAPIPOST(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(REQUEST_LISTING_COMPLETE_PAYLOAD_JSON_PATH))),
                POST_RESOURCE_LISTING_LOCATION_MESSAGE_SCHEMA_FILE);
        invokeSnLAPI(headersAsMap,
                REQUEST_LISTING_COMPLETE_PAYLOAD_JSON_PATH,
                HttpMethod.POST, mockServer,
                PROVIDER_REQUEST_SnL_RESOURCES_LOCATION_API_PATH,
                HttpStatus.OK);
        Assertions.assertTrue(true);
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createOptionalPayloadRequestResourceLocationAPIPactPOST(
            PactDslWithProvider builder) throws IOException {

        return buildPactForSnL(headersAsMap, builder,
                "Provider confirms request received for a optional payload For Request Resource Location - POST",
                REQUEST_LISTING_OPTIONAL_PAYLOAD_JSON_PATH,
                PROVIDER_REQUEST_SnL_RESOURCES_LOCATION_API_PATH,
                HttpMethod.POST,
                HttpStatus.OK,
                "Resource Location API");
    }

    @Test
    @PactTestFor(pactMethod = "createOptionalPayloadRequestResourceLocationAPIPactPOST")
    void shouldOptionalPayloadForRequestResourceLocationAPIPOST(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(REQUEST_LISTING_OPTIONAL_PAYLOAD_JSON_PATH))),
                POST_RESOURCE_LISTING_LOCATION_MESSAGE_SCHEMA_FILE);
        invokeSnLAPI(headersAsMap,
                REQUEST_LISTING_OPTIONAL_PAYLOAD_JSON_PATH,
                HttpMethod.POST, mockServer,
                PROVIDER_REQUEST_SnL_RESOURCES_LOCATION_API_PATH,
                HttpStatus.OK);
        Assertions.assertTrue(true);
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createCompletePayloadRequestResourceLocationAPIPactPUT(
            PactDslWithProvider builder) throws IOException {

        return buildPactForSnL(headersAsMap, builder,
                "Provider confirms request received for a complete payload For Request Resource Location - PUT",
                REQUEST_LISTING_COMPLETE_PAYLOAD_JSON_PATH,
                PROVIDER_REQUEST_SnL_RESOURCES_LOCATION_API_PATH,
                HttpMethod.PUT,
                HttpStatus.OK,
                "Resource Location API");
    }

    @Test
    @PactTestFor(pactMethod = "createCompletePayloadRequestResourceLocationAPIPactPUT")
    void shouldCompletePayloadForRequestResourceLocationAPIPUT(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(REQUEST_LISTING_COMPLETE_PAYLOAD_JSON_PATH))),
                POST_RESOURCE_LISTING_LOCATION_MESSAGE_SCHEMA_FILE);
        invokeSnLAPI(headersAsMap,
                REQUEST_LISTING_COMPLETE_PAYLOAD_JSON_PATH,
                HttpMethod.PUT, mockServer,
                PROVIDER_REQUEST_SnL_RESOURCES_LOCATION_API_PATH,
                HttpStatus.OK);
        Assertions.assertTrue(true);
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createOptionalPayloadRequestResourceLocationAPIPactPUT(
            PactDslWithProvider builder) throws IOException {

        return buildPactForSnL(headersAsMap, builder,
                "Provider confirms request received for a optional payload For Request Resource Location - PUT",
                REQUEST_LISTING_OPTIONAL_PAYLOAD_JSON_PATH,
                PROVIDER_REQUEST_SnL_RESOURCES_LOCATION_API_PATH,
                HttpMethod.PUT,
                HttpStatus.OK,
                "Resource Location API");
    }

    @Test
    @PactTestFor(pactMethod = "createOptionalPayloadRequestResourceLocationAPIPactPUT")
    void shouldOptionalPayloadForRequestResourceLocationAPIPUT(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(REQUEST_LISTING_OPTIONAL_PAYLOAD_JSON_PATH))),
                POST_RESOURCE_LISTING_LOCATION_MESSAGE_SCHEMA_FILE);
        invokeSnLAPI(headersAsMap,
                REQUEST_LISTING_OPTIONAL_PAYLOAD_JSON_PATH,
                HttpMethod.PUT, mockServer,
                PROVIDER_REQUEST_SnL_RESOURCES_LOCATION_API_PATH,
                HttpStatus.OK);
        Assertions.assertTrue(true);
    }

}
