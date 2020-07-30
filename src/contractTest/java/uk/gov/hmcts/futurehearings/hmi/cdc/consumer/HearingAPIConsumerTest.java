package uk.gov.hmcts.futurehearings.hmi.cdc.consumer;

import static uk.gov.hmcts.futurehearings.hmi.cdc.consumer.common.TestingUtils.readFileContents;

import uk.gov.hmcts.futurehearings.hmi.functional.Application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import net.serenitybdd.rest.SerenityRest;
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

    @Value("${targetHost}")
    private String targetHost;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    private static final String PROVIDER_REQUEST_HEARING_API = "/hmi/hearings";

    private Map<String, String> headersAsMap = new HashMap<>();

    @BeforeEach
    public void initialiseValues() {
        headersAsMap.put("Host", targetHost);
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Company-Name", "HMCTS");
        headersAsMap.put("Content-Type", "application/json");
    }

   @Pact(provider = "SandL_HEARING_API", consumer = "HMI_HEARING_API")
    public RequestResponsePact createRequestHearingAPIPact(
            PactDslWithProvider builder) throws IOException {

        String requestHearingPayload =
                readFileContents("payloads/request-hearing.json");

        return builder
                .given("Request Hearing API")
                .uponReceiving("Provider confirms request received")
                .path(PROVIDER_REQUEST_HEARING_API)
                .method(HttpMethod.POST.toString())
                .headers(headersAsMap)
                .body(requestHearingPayload)
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createRequestHearingAPIPact")
    public void shouldExecutePostHearingAndReturn200(MockServer mockServer) throws IOException {
        String requestHearingPayload =
                readFileContents("payloads/request-hearing.json");

        SerenityRest
                .given()
                .headers(headersAsMap)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestHearingPayload)
                .when()
                .post(mockServer.getUrl() + PROVIDER_REQUEST_HEARING_API)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
