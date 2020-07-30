package uk.gov.hmcts.futurehearings.hmi.cdc.consumer;

import static uk.gov.hmcts.futurehearings.hmi.cdc.consumer.common.TestingUtils.readFileContents;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(PactConsumerTestExt.class)
@ExtendWith(SpringExtension.class)
public class HearingAPIConsumerTest {

    private static final String PROVIDER_REQUEST_HEARING_API = "/hearings";

    @Pact(provider = "SandL_HEARING_API", consumer = "HMI_HEARING_API")
    public RequestResponsePact createRequestHearingAPIPact(
            PactDslWithProvider builder) throws IOException {

        String requestHearingPayload =
                readFileContents("payloads/request-hearing.json");

        Map<String, String> headersAsMap = new HashMap<>();
        headersAsMap.put("Host", "hmi-apim-svc-test.azure-api.net");
        headersAsMap.put("Ocp-Apim-Subscription-Key", "587c56f59a604054b1ce73dac0ea5b28");
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Company-Name", "HMCTS");
        headersAsMap.put("Content-Type", "application/json");

        return builder
                .given("Request Hearing API")
                .uponReceiving("Provider confirms request received")
                .path(PROVIDER_REQUEST_HEARING_API)
                .method(HttpMethod.POST.toString())
                .body(requestHearingPayload)
                .willRespondWith()
                .headers(headersAsMap)
                .status(HttpStatus.CREATED.value())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createRequestHearingAPIPact")
    public void shouldExecutePostHearingAndReturn200(MockServer mockServer) throws IOException {
        String requestHearingPayload =
                readFileContents("payloads/request-hearing.json");

        Map<String, String> headersAsMap = new HashMap<>();
        headersAsMap.put("Host", "hmi-apim-svc-test.azure-api.net");
        headersAsMap.put("Ocp-Apim-Subscription-Key", "587c56f59a604054b1ce73dac0ea5b28");
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Company-Name", "HMCTS");
        headersAsMap.put("Content-Type", "application/json");

        SerenityRest
                .given()
                .headers(headersAsMap)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestHearingPayload)
                .when()
                .post(mockServer.getUrl() + PROVIDER_REQUEST_HEARING_API)
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }
}
