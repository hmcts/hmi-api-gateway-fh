package uk.gov.hmcts.futurehearings.hmi.contract.consumer.listings;

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
class ListingRequestAPIConsumerTests extends ContractTest {

    private static final String PROVIDER_REQUEST_SnL_LISTING_RESOURCE_API_PATH = "/casehqapi/rest/hmcts/resources/listings/1234";

    public static final String RESOURCES_LISTING_REQUEST_SCHEMA_JSON = "/listingRequestMessage.json";
    public static final String RESOURCES_LISTING_REQUEST_COMPLETE_PAYLOAD_WITH_ENTITIES_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/listings/request-listing-complete-payload-with-entities.json";
    public static final String RESOURCES_LISTING_REQUEST_MANDATORY_PAYLOAD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/listings/request-listing-mandatory-payload.json";
    public static final String RESOURCES_LISTING_REQUEST_STANDARD_PAYLOAD_WITH_NO_ENTITIES_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/listings/request-listing-standard-no-entities-payload.json";
    public static final String RESOURCES_LISTING_REQUEST_STANDARD_JSON_PATH = "uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload/listings/request-listing-standard-payload.json";

    @BeforeAll
    public void initialiseValues() throws Exception {
        this.setDestinationSystem("SNL");
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createCompletePUTPayloadWithEntitiesForRequestListingPact(
            PactDslWithProvider builder) throws IOException {

        return buildPact(headersAsMap, builder,
                "Provider confirms request received for a complete payload for an Request Listing - PUT",
                RESOURCES_LISTING_REQUEST_COMPLETE_PAYLOAD_WITH_ENTITIES_JSON_PATH,
                PROVIDER_REQUEST_SnL_LISTING_RESOURCE_API_PATH,
                HttpMethod.PUT,
                HttpStatus.ACCEPTED,
                "Request Listing API"
                );
    }

    @Test
    @PactTestFor(pactMethod = "createCompletePUTPayloadWithEntitiesForRequestListingPact")
    void shouldPUTCompletePayloadWithEntForRequestListingAPI(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(RESOURCES_LISTING_REQUEST_COMPLETE_PAYLOAD_WITH_ENTITIES_JSON_PATH))),
                RESOURCES_LISTING_REQUEST_SCHEMA_JSON);

        invokeAPI(headersAsMap,
                getAuthorizationToken(),
                RESOURCES_LISTING_REQUEST_COMPLETE_PAYLOAD_WITH_ENTITIES_JSON_PATH,
                HttpMethod.PUT,
                mockServer,
                PROVIDER_REQUEST_SnL_LISTING_RESOURCE_API_PATH,
                HttpStatus.ACCEPTED);

        Assertions.assertTrue(true);
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createMandatoryPUTPayloadForRequestListingPact(
            PactDslWithProvider builder) throws IOException {

        return buildPact(headersAsMap,
                builder,
                "Provider confirms request received for an mandatory payload for an Request Listing - PUT",
                RESOURCES_LISTING_REQUEST_MANDATORY_PAYLOAD_JSON_PATH,
                PROVIDER_REQUEST_SnL_LISTING_RESOURCE_API_PATH,
                HttpMethod.PUT,
                HttpStatus.ACCEPTED,
                "Resource User API"
        );
    }

    @Test
    @PactTestFor(pactMethod = "createMandatoryPUTPayloadForRequestListingPact")
    void shouldPUTMandatoryPayloadForRequestListingAPI(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(RESOURCES_LISTING_REQUEST_MANDATORY_PAYLOAD_JSON_PATH))),
                RESOURCES_LISTING_REQUEST_SCHEMA_JSON);

        invokeAPI(headersAsMap,
                getAuthorizationToken(),
                RESOURCES_LISTING_REQUEST_MANDATORY_PAYLOAD_JSON_PATH,
                HttpMethod.PUT,
                mockServer,
                PROVIDER_REQUEST_SnL_LISTING_RESOURCE_API_PATH,
                HttpStatus.ACCEPTED);

        Assertions.assertTrue(true);
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createStandardWithNoEntitiesPUTPayloadForRequestListingPact(
            PactDslWithProvider builder) throws IOException {

        return buildPact(headersAsMap,
                builder,
                "Provider confirms request received for an standard with no entities payload for an Request Listing - PUT",
                RESOURCES_LISTING_REQUEST_STANDARD_PAYLOAD_WITH_NO_ENTITIES_JSON_PATH,
                PROVIDER_REQUEST_SnL_LISTING_RESOURCE_API_PATH,
                HttpMethod.PUT,
                HttpStatus.ACCEPTED,
                "Resource User API"
        );
    }

    @Test
    @PactTestFor(pactMethod = "createStandardWithNoEntitiesPUTPayloadForRequestListingPact")
    void shouldPUTStandardWithNoEntitiesPayloadForRequestListingAPI(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(RESOURCES_LISTING_REQUEST_STANDARD_PAYLOAD_WITH_NO_ENTITIES_JSON_PATH))),
                RESOURCES_LISTING_REQUEST_SCHEMA_JSON);

        invokeAPI(headersAsMap,
                getAuthorizationToken(),
                RESOURCES_LISTING_REQUEST_STANDARD_PAYLOAD_WITH_NO_ENTITIES_JSON_PATH,
                HttpMethod.PUT,
                mockServer,
                PROVIDER_REQUEST_SnL_LISTING_RESOURCE_API_PATH,
                HttpStatus.ACCEPTED);

        Assertions.assertTrue(true);
    }

    @Pact(provider = "SandL_API", consumer = "HMI_API")
    public RequestResponsePact createStandardPUTPayloadForRequestListingPact(
            PactDslWithProvider builder) throws IOException {

        return buildPact(headersAsMap,
                builder,
                "Provider confirms request received for an standard payload for an Request Listing - PUT",
                RESOURCES_LISTING_REQUEST_STANDARD_JSON_PATH,
                PROVIDER_REQUEST_SnL_LISTING_RESOURCE_API_PATH,
                HttpMethod.PUT,
                HttpStatus.ACCEPTED,
                "Resource User API"
        );
    }

    @Test
    @PactTestFor(pactMethod = "createStandardPUTPayloadForRequestListingPact")
    void shouldStandardPUTPayloadForRequestListingAPI(MockServer mockServer)
            throws IOException, URISyntaxException, JSONException {

        validateHMIPayload(new JSONObject(new JSONTokener(readFileContents(RESOURCES_LISTING_REQUEST_STANDARD_JSON_PATH))),
                RESOURCES_LISTING_REQUEST_SCHEMA_JSON);

        invokeAPI(headersAsMap,
                getAuthorizationToken(),
                RESOURCES_LISTING_REQUEST_STANDARD_JSON_PATH,
                HttpMethod.PUT,
                mockServer,
                PROVIDER_REQUEST_SnL_LISTING_RESOURCE_API_PATH,
                HttpStatus.ACCEPTED);

        Assertions.assertTrue(true);
    }
}

