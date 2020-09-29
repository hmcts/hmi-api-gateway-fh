package uk.gov.hmcts.futurehearings.hmi.contract.consumer.hearings;

import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.TestingUtils.readFileContents;

import uk.gov.hmcts.futurehearings.hmi.Application;

import java.io.IOException;
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
import org.json.JSONObject;
import org.json.JSONTokener;
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
public class HearingAPIConsumerTest {


    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    private static final String PROVIDER_REQUEST_HEARING_API = "/hmi/hearings";
    private static final String PROVIDER_REQUEST_SnL_HEARING_API_PATH = "/hmcts/hearings";

    private Map<String, String> headersAsMap = new HashMap<>();
    public static final String REQUEST_HEARING_COMPLETE_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/request-hearing-complete-payload.json";
    public static final String REQUEST_HEARING_STANDARD_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/request-hearing-standard-payload.json";
    public static final String REQUEST_HEARING_MANDATORY_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/request-hearing-mandatory-payload.json";
    public static final String HEARING_PAYLOAD_SCHEMA_PATH = "uk/gov/hmcts/futurehearings/hmi/thirdparty/schema/S&L/postListingRequestMessage.json";

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
    public RequestResponsePact createCompletePayloadForRequestHearingAPIPact(
            PactDslWithProvider builder) throws IOException {

        return builder
                .given("Request Hearing API")
                .uponReceiving("Provider confirms request received for a Complete Payload")
                .path(PROVIDER_REQUEST_SnL_HEARING_API_PATH)
                .method(HttpMethod.POST.toString())
                .headers(headersAsMap)
                .body(readFileContents(REQUEST_HEARING_STANDARD_PAYLOAD_JSON_PATH),ContentType.APPLICATION_JSON)
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createCompletePayloadForRequestHearingAPIPact")
    public void shouldCompletePayloadForRequestHearingAPIAndReturn200(MockServer mockServer) throws IOException {


        RestAssured
                .given()
                .headers(headersAsMap)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(readFileContents(REQUEST_HEARING_COMPLETE_PAYLOAD_JSON_PATH))
                .when()
                .post(mockServer.getUrl() + PROVIDER_REQUEST_SnL_HEARING_API_PATH)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createStandardPayloadForRequestHearingAPIPact(
            PactDslWithProvider builder) throws IOException {

        return builder
                .given("Request Hearing API")
                .uponReceiving("Provider confirms request received for a Standards (Only Outer Elements) Payload")
                .path(PROVIDER_REQUEST_SnL_HEARING_API_PATH)
                .method(HttpMethod.POST.toString())
                .headers(headersAsMap)
                .body(readFileContents(REQUEST_HEARING_STANDARD_PAYLOAD_JSON_PATH),ContentType.APPLICATION_JSON)
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createStandardPayloadForRequestHearingAPIPact")
    public void shouldStandardPayloadForRequestHearingAPIAndReturn200(MockServer mockServer) throws IOException {

        RestAssured
                .given()
                .headers(headersAsMap)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(readFileContents(REQUEST_HEARING_STANDARD_PAYLOAD_JSON_PATH))
                .when()
                .post(mockServer.getUrl() + PROVIDER_REQUEST_SnL_HEARING_API_PATH)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createMandatoryPayloadForRequestHearingAPIPact(
            PactDslWithProvider builder) throws IOException {

        return builder
                .given("Request Hearing API")
                .uponReceiving("Provider confirms request received for a Complete Payload")
                .path(PROVIDER_REQUEST_SnL_HEARING_API_PATH)
                .method(HttpMethod.POST.toString())
                .headers(headersAsMap)
                .body(readFileContents(REQUEST_HEARING_MANDATORY_PAYLOAD_JSON_PATH), ContentType.APPLICATION_JSON)
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createMandatoryPayloadForRequestHearingAPIPact")
    public void shouldMandatoryPayloadForRequestHearingAPIAndReturn200(MockServer mockServer) throws IOException {

        RestAssured
                .given()
                .headers(headersAsMap)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(readFileContents(REQUEST_HEARING_MANDATORY_PAYLOAD_JSON_PATH))
                .when()
                .post(mockServer.getUrl() + PROVIDER_REQUEST_SnL_HEARING_API_PATH)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
