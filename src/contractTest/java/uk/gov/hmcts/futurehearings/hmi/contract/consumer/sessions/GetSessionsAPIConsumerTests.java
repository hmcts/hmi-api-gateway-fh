package uk.gov.hmcts.futurehearings.hmi.contract.consumer.sessions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.PACTFactory.buildResponsePactFromSnL;
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
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ActiveProfiles("contract")
@SpringBootTest(classes = {Application.class})
@ExtendWith(PactConsumerTestExt.class)
@ExtendWith(SpringExtension.class)
public class GetSessionsAPIConsumerTests {

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    private static final String PROVIDER_SnL_GET_SESSION_API_PATH = "/casehqapi/rest/hmcts/resources/sessions";

    private Map<String, String> headersAsMap = new HashMap<>();

    public static final String GET_SESSION_RESPONSE_SCHEMA_JSON = "/getSessionsResponseMessage.json";
    public static final String GET_SESSION_COMPLETE_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/response/sessions/get-sessions-complete-response.json";
    public static final String GET_SESSION_MANDATORY_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/response/sessions/get-sessions-mandatory-response.json";

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
    public RequestResponsePact createCompleteGetSessionsResponsePact(
            PactDslWithProvider builder) throws IOException {

        return buildResponsePactFromSnL(headersAsMap, builder,
                "Provider confirms complete response received for Get Sessions",
                GET_SESSION_COMPLETE_PAYLOAD_JSON_PATH,
                PROVIDER_SnL_GET_SESSION_API_PATH,
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

        Response response = invokeSnLAPI(headersAsMap,
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

        return buildResponsePactFromSnL(headersAsMap, builder,
                "Provider confirms complete response received for Get Sessions",
                GET_SESSION_MANDATORY_PAYLOAD_JSON_PATH,
                PROVIDER_SnL_GET_SESSION_API_PATH,
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

        Response response = invokeSnLAPI(headersAsMap,
                GET_SESSION_MANDATORY_PAYLOAD_JSON_PATH,
                HttpMethod.GET,
                mockServer,
                PROVIDER_SnL_GET_SESSION_API_PATH,
                HttpStatus.OK);

        verifyMandatoryResponse(response);
    }

    private void verifyMandatoryResponse(Response response) {
        assertNotNull(response.getBody().asString().contains("sessionIdCaseHQ"));
        assertNotNull(response.getBody().asString().contains("sessionType"));
        assertNotNull(response.getBody().asString().contains("sessionStartTime"));
        assertNotNull(response.getBody().asString().contains("sessionDuration"));
        assertNotNull(response.getBody().asString().contains("sessionVenueId"));
    }
}



