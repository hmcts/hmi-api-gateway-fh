package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForDelete;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(TestReporter.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("DELETE /hearings - Delete Hearings")
@SuppressWarnings({"java:S2699", "PMD.TooManyMethods", "PMD.TooManyFields"})
class DeleteHearingsUnitTests {

    private static final String CORRECT_DELETE_REQUEST_PAYLOAD = "requests/delete-request-payload.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${hearingApiRootContext}")
    private String hearingApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

    @Value("${tokenURL}")
    private String tokenUrl;

    @Value("${clientID}")
    private String clientID;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${scope}")
    private String scope;

    @Value("${grantType}")
    private String grantType;

    private static String accessToken;

    private final Map<String, Object> headersAsMap = new ConcurrentHashMap<>();
    private final Map<String, String> paramsAsMap = new ConcurrentHashMap<>();

    @Value("${invalidTokenURL}")
    private String invalidTokenUrl;

    @Value("${invalidScope}")
    private String invalidScope;

    @Value("${invalidClientID}")
    private String invalidClientID;

    @Value("${invalidClientSecret}")
    private String invalidClientSecret;

    private HmiHttpClient httpClient;

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ACCEPT = "Accept";

    @BeforeAll
    void setToken() {
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenUrl, scope);
        this.httpClient = new HmiHttpClient(accessToken, targetInstance);
    }

    @BeforeEach
    void initialiseValues() {
        headersAsMap.put(CONTENT_TYPE, "application/json");
        headersAsMap.put(ACCEPT, "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");

    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testDeleteHearingRequestForInvalidResource() throws Exception {
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = httpClient.httpDelete(hearingApiRootContext
                + "delete", headersAsMap, paramsAsMap, input);
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testDeleteHearingRequestWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove(CONTENT_TYPE);
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteHearingAuth(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testDeleteHearingRequestWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove(CONTENT_TYPE);
        headersAsMap.put(CONTENT_TYPE, "application/xml");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteHearingAuth(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testDeleteHearingRequestWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove(ACCEPT);
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteHearingAuth(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testDeleteHearingRequestWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove(ACCEPT);
        headersAsMap.put(ACCEPT, "application/jsonxml");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteHearingAuth(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Order(6)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {"Source-System", "Destination-System", "Request-Created-At"})
    void testDeleteHearingRequestWithMissingHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteHearingAuth(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(7)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {"Source-System", "Destination-System", "Request-Created-At"})
    void testDeleteHearingRequestWithInvalidHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteHearingAuth(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(8)
    @DisplayName("Test for Correct Headers and Payload")
    void testDeleteHearingRequestWithCorrectHeaders() throws IOException {
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteHearingAuth(input);
        thenValidateResponseForDelete(response);
    }

    @Test
    @Order(9)
    @DisplayName("Test for missing Access Token")
    void testDeleteHearingRequestWithMissingAccessToken() throws IOException {

        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deteHearingNoAuth(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(10)
    @DisplayName("Test for invalid Access Token")
    void testDeleteHearingRequestWithInvalidAccessToken() throws IOException {
        accessToken = TestUtilities.getToken(grantType,
                invalidClientID, invalidClientSecret, invalidTokenUrl, invalidScope);
        httpClient.setAccessToken(accessToken);
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteHearingAuth(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private Response deleteHearingAuth(final String input) {
        return httpClient.httpDelete(hearingApiRootContext  + "/h123", headersAsMap, paramsAsMap, input);
    }

    private Response deteHearingNoAuth(final String input) {
        return httpClient.httpDeleteNoAuth(hearingApiRootContext  + "/h123", headersAsMap, paramsAsMap, input);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }

}
