package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import org.springframework.beans.factory.annotation.Value;

import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForCreate;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

import lombok.extern.slf4j.Slf4j;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HmiHttpClient;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;

@Slf4j
@SpringBootTest(classes = { Application.class })
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("POST /direct-listings - Request Direct Listing")
@SuppressWarnings("java:S2699")
class POST_direct_listings_UnitTests {

    private static final String PAYLOAD_WITH_ALL_FIELDS = "requests/create-listing-request-payload.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${listingsApiRootContext}")
    private String listingsApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

    private final Map<String, Object> headersAsMap = new HashMap<>();
    private final Map<String, String> paramsAsMap = new HashMap<>();

    @Value("${tokenURL}")
    private String tokenURL;

    @Value("${clientID}")
    private String clientID;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${scope}")
    private String scope;

    @Value("${grantType}")
    private String grantType;

    private static String accessToken;

    @Value("${invalidTokenURL}")
    private String invalidTokenURL;

    @Value("${invalidScope}")
    private String invalidScope;

    @Value("${invalidClientID}")
    private String invalidClientID;

    @Value("${invalidClientSecret}")
    private String invalidClientSecret;
    
	private HmiHttpClient httpClient;

    @BeforeAll
    void setToken() {
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenURL, scope);
		this.httpClient = new HmiHttpClient(accessToken, targetInstance);
    }

    @BeforeEach
    void initialiseValues() {
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testDirectHearingsForInvalidResource() throws IOException {
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = httpClient.httpPost(listingsApiRootContext + "/s123/post", headersAsMap, paramsAsMap, input);
        ListingsResponseVerifier.thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testDirectHearingsWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = requestDirectHearing(input);
        ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for missing or invalid Accept header")
    void testDirectHearingsWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = requestDirectHearing(input);
        ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Order(4)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = { "Source-System", "Destination-System", "Request-Created-At" })
    void testDirectHearingsWithMissingHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = requestDirectHearing(input);
        ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(5)
    @DisplayName("Test for Correct Headers")
    void testRequestHearingsWithCorrectHeaders() throws IOException {
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = requestDirectHearing(input);
        ListingsResponseVerifier.thenValidateResponseForDirectListing(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing Access Token")
    void testDirectHearingsWithMissingAccessToken() throws IOException {

        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = requestDirectHearingNoAuth(input);
        ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid Access Token")
    void testDirectHearingsWithInvalidAccessToken() throws IOException {
        accessToken = TestUtilities.getToken(grantType, invalidClientID, invalidClientSecret, invalidTokenURL,
                invalidScope);
        httpClient.setAccessToken(accessToken);
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = requestDirectHearing(input);
        ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private Response requestDirectHearing(final String input) {
        return httpClient.httpPost(listingsApiRootContext, headersAsMap, paramsAsMap, input);
    }

    private Response requestDirectHearingNoAuth(final String input) {
        return httpClient.httpPostNoAuth(listingsApiRootContext, headersAsMap, paramsAsMap, input);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }
}