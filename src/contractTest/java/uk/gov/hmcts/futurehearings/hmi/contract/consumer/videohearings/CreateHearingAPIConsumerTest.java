package uk.gov.hmcts.futurehearings.hmi.contract.consumer.videohearings;

import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.PACTFactory.buildPact;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.PACTFactory.buildPactWithPayload;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.RestDelegate.invokeAPIWithPayloadBody;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.TestingUtils.getRFC3339FormattedDateForwardDays;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.TestingUtils.readFileContents;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.header.factory.HeaderFactory.createStandardSnLHeader;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.test.ContractTest;

import java.io.IOException;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
class CreateHearingAPIConsumerTest extends ContractTest {

    public static final String CREATE_HEARING_MANDATORY_PAYLOAD_JSON_PATH
            = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/videohearings/create-video-hearing-mandatory-payload.json";
    public static final String CREATE_HEARING_STANDARD_PAYLOAD_JSON_PATH
            = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/videohearings/create-video-hearing-standard-payload.json";
    public static final String CREATE_HEARING_COMPLETE_PAYLOAD_JSON_PATH
            = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/videohearings/create-video-hearing-complete-payload.json";
    private static final String PROVIDER_CREATE_A_HEARING_API_PATH = "/hearings";

    private String inputPayload = null;

    @BeforeAll
    public void initialiseValues() throws Exception {
        this.setDestinationSystem("VH");
    }

    @Pact(provider = "VideoHearings_API", consumer = "HMI_API")
    public RequestResponsePact createMandatoryPayloadToCreateAHearingAPIPactPOST(
            PactDslWithProvider builder) throws IOException {
        this.inputPayload = String.format(readFileContents(CREATE_HEARING_MANDATORY_PAYLOAD_JSON_PATH),
                getRFC3339FormattedDateForwardDays(10));
        return buildPactWithPayload(headersAsMap, builder,
                "Provider confirms create booking/hearing request received for a mandatory payload POST",
                this.inputPayload,
                PROVIDER_CREATE_A_HEARING_API_PATH,
                HttpMethod.POST,
                HttpStatus.CREATED,
                "Video Hearing API");
    }

    @Test
    @PactTestFor(pactMethod = "createMandatoryPayloadToCreateAHearingAPIPactPOST")
    void shouldCreateMandatoryPayloadToCreateAHearingAPIPactPOST(MockServer mockServer)
            throws Exception {

        invokeAPIWithPayloadBody(headersAsMap,
                getAuthorizationToken(),//TODO - Checkup on the Auth Details for Video Hearings.
                this.inputPayload,
                HttpMethod.POST, mockServer,
                PROVIDER_CREATE_A_HEARING_API_PATH,
                HttpStatus.CREATED);
        this.inputPayload = null;
        Assertions.assertTrue(true);
    }

    @Pact(provider = "VideoHearings_API", consumer = "HMI_API")
    public RequestResponsePact createStandardPayloadToCreateAHearingAPIPactPOST(
            PactDslWithProvider builder) throws IOException {

        this.inputPayload = String.format(readFileContents(CREATE_HEARING_STANDARD_PAYLOAD_JSON_PATH),
                getRFC3339FormattedDateForwardDays(10));
        return buildPactWithPayload(headersAsMap, builder,
                "Provider confirms create booking/hearing request received for a standard payload POST",
                this.inputPayload,
                PROVIDER_CREATE_A_HEARING_API_PATH,
                HttpMethod.POST,
                HttpStatus.CREATED,
                "Video Hearing API");
    }

    @Test
    @PactTestFor(pactMethod = "createStandardPayloadToCreateAHearingAPIPactPOST")
    void shouldCreateStandardPayloadToCreateAHearingAPIPactPOST(MockServer mockServer)
            throws Exception {
        invokeAPIWithPayloadBody(headersAsMap,
                getAuthorizationToken(),//TODO - Checkup on the Auth Details for Video Hearings.
                this.inputPayload,
                HttpMethod.POST, mockServer,
                PROVIDER_CREATE_A_HEARING_API_PATH,
                HttpStatus.CREATED);
        Assertions.assertTrue(true);
        this.inputPayload = null;
    }

    @Pact(provider = "VideoHearings_API", consumer = "HMI_API")
    public RequestResponsePact createCompletePayloadToCreateAHearingAPIPactPOST(
            PactDslWithProvider builder) throws IOException {
        this.inputPayload = String.format(readFileContents(CREATE_HEARING_COMPLETE_PAYLOAD_JSON_PATH),
                getRFC3339FormattedDateForwardDays(10));
        return buildPactWithPayload(headersAsMap, builder,
                "Provider confirms create booking/hearing request received for a standard payload POST",
                CREATE_HEARING_COMPLETE_PAYLOAD_JSON_PATH,//TODO - The payload has to be made dynamic for a Date in the future.
                this.inputPayload,
                HttpMethod.POST,
                HttpStatus.CREATED,
                "Video Hearing API");
    }

    @Test
    @PactTestFor(pactMethod = "createStandardPayloadToCreateAHearingAPIPactPOST")
    void shouldCreateCompletePayloadToCreateAHearingAPIPactPOST(MockServer mockServer)
            throws Exception {
        invokeAPIWithPayloadBody(headersAsMap,
                getAuthorizationToken(),//TODO - Checkup on the Auth Details for Video Hearings.
                this.inputPayload,
                HttpMethod.POST, mockServer,
                PROVIDER_CREATE_A_HEARING_API_PATH,
                HttpStatus.CREATED);
        this.inputPayload = null;
        Assertions.assertTrue(true);
    }
}
