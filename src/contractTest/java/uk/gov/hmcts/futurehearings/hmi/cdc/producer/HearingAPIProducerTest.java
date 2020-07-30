package uk.gov.hmcts.futurehearings.hmi.cdc.producer;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@PactBroker(host = "localhost", port = "80")
@Provider("SandL_HEARING_API")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class HearingAPIProducerTest {

    private String providerUrl = "http://hmi-apim-svc-test.azure-api.net/hmi/hearings";
    private Map<String, Object> headersAsMap = new HashMap<>();

    @BeforeEach
    void setupTestTarget(PactVerificationContext context) throws Exception {

         context.setTarget(HttpTestTarget.fromUrl(new URL(
                providerUrl)));

    }

    @BeforeClass
    void enablePublishingPact() {
        System.setProperty("pact.verifier.publishResults", "true");
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("Request Hearing API")
    public void employeeExist() {

    }

}
