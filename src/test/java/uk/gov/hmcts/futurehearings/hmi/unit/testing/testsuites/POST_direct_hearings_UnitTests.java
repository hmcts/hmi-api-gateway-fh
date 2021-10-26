package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HmiHttpClient;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@Slf4j
@SpringBootTest(classes = { Application.class })
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("POST /direct-hearings - Request Hearings")
@SuppressWarnings("java:S2699")
class POST_direct_hearings_UnitTests {

    private static final String PAYLOAD_WITH_ALL_FIELDS = "requests/create-hearing-request-payload.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${hearingApiRootContext}")
    private String hearingApiRootContext;

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
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testDirectHearingsForInvalidResource() throws IOException {
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = httpClient.httpPut(hearingApiRootContext + "/sessions/s123/post", headersAsMap, paramsAsMap, input);
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testDirectHearingsWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = requestDirectHearing(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testDirectHearingsWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = requestDirectHearing(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testDirectHearingsWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = requestDirectHearing(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testDirectHearingsWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = requestDirectHearing(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Order(6)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = { "Source-System", "Destination-System", "Request-Created-At" })
    void testDirectHearingsWithMissingHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = requestDirectHearing(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(7)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = { "Source-System", "Destination-System", "Request-Created-At" })
    void testDirectHearingsWithInvalidHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = requestDirectHearing(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(8)
    @DisplayName("Test for Correct Headers")
    void testDirectHearingsWithCorrectHeaders() throws IOException {
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = requestDirectHearing(input);
        thenValidateResponseForCreate(response);
    }

    @Test
    @Order(9)
    @DisplayName("Test for missing Access Token")
    void testDirectHearingsWithMissingAccessToken() throws IOException {

        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = requestDirectHearingNoAuth(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(10)
    @DisplayName("Test for invalid Access Token")
    void testDirectHearingsWithInvalidAccessToken() throws IOException {
        accessToken = TestUtilities.getToken(grantType, invalidClientID, invalidClientSecret, invalidTokenURL,
                invalidScope);
        httpClient.setAccessToken(accessToken);
        final String input = givenAPayload(PAYLOAD_WITH_ALL_FIELDS);
        final Response response = requestDirectHearing(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private Response requestDirectHearing(final String input) {
        return httpClient.httpPut(hearingApiRootContext + "/sessions/s123", headersAsMap, paramsAsMap, input);
    }

    private Response requestDirectHearingNoAuth(final String input) {
        return httpClient.httpPutNoAuth(hearingApiRootContext + "/sessions/s123", headersAsMap, paramsAsMap, input);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }
}