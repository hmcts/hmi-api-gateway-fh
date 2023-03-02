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

import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForRequestOrDelete;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("DELETE /listings - Delete Listings")
@SuppressWarnings({"java:S2699", "PMD.TooManyMethods"})
class DeleteListingsUnitTests {

    private static final String CORRECT_DELETE_REQUEST_PAYLOAD = "requests/delete-request-payload.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${listingsApiRootContext}")
    private String listingsApiRootContext;

    private final Map<String, Object> headersAsMap = new ConcurrentHashMap<>();
    private final Map<String, String> paramsAsMap = new ConcurrentHashMap<>();

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
        headersAsMap.put("Destination-System", "MOCK");
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testDeleteListingsRequestForInvalidResource() throws IOException {
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = httpClient.httpDelete(listingsApiRootContext + "delete",
                headersAsMap, paramsAsMap, input);
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testDeleteListingsRequestWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove(CONTENT_TYPE);
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteListingById(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testDeleteListingsRequestWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove(CONTENT_TYPE);
        headersAsMap.put(CONTENT_TYPE, "application/xml");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteListingById(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testDeleteListingsRequestWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove(ACCEPT);
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteListingById(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testDeleteListingsRequestWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove(ACCEPT);
        headersAsMap.put(ACCEPT, "application/xml");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteListingById(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Order(9)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {"Source-System", "Destination-System", "Request-Created-At"})
    void testDeleteListingsRequestWithMissingHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteListingById(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(10)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {"Source-System", "Destination-System", "Request-Created-At"})
    void testDeleteListingsRequestWithInvalidHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteListingById(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(11)
    @DisplayName("Test for Correct Headers and Payload")
    void testDeleteListingsRequestWithCorrectHeaders() throws IOException {
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteListingById(input);
        thenValidateResponseForRequestOrDelete(response);
    }

    @Test
    @Order(12)
    @DisplayName("Test for missing Access Token")
    void testDeleteListingsRequestWithMissingAccessToken() throws IOException {

        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteListingByIdNoAuth(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(13)
    @DisplayName("Test for invalid Access Token")
    void testDeleteListingsRequestWithInvalidAccessToken() throws IOException {
        accessToken = TestUtilities.getToken(grantType, invalidClientID,
                invalidClientSecret, invalidTokenUrl, invalidScope);
        httpClient.setAccessToken(accessToken);
        final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
        final Response response = deleteListingById(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private Response deleteListingById(final String input) {
        return httpClient.httpDelete(listingsApiRootContext + "/list_id", headersAsMap, paramsAsMap, input);
    }

    private Response deleteListingByIdNoAuth(final String input) {
        return httpClient.httpDeleteNoAuth(listingsApiRootContext + "/list_id", headersAsMap, paramsAsMap, input);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }
}
