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
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForRetrieve;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("GET /listings - Retrieve Listings")
@SuppressWarnings({"java:S2699", "PMD.TooManyMethods"})
class GetListingsUnitTests {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${listingsApiRootContext}")
    private String listingsApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

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

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ACCEPT = "Accept";
    private static final String SOURCE_SYSTEM = "Source-System";
    private static final String DESTINATION_SYSTEM = "Destination-System";
    private static final String REQUEST_CREATED_AT = "Request-Created-At";
    private static final String LIST_ID = "/list_id";


    @BeforeAll
    void setToken() {
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenUrl, scope);
    }

    @BeforeEach
    void initialiseValues() {
        headersAsMap.put(CONTENT_TYPE, "application/json");
        headersAsMap.put(ACCEPT, "application/json");
        headersAsMap.put(SOURCE_SYSTEM, "CFT");
        headersAsMap.put(DESTINATION_SYSTEM, destinationSystem);
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put(REQUEST_CREATED_AT, "2018-01-29T20:36:01Z");

        paramsAsMap.put("date_of_listing", "2018-01-29 21:36:01Z");
        paramsAsMap.put("hearing_type", "VH");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testRetrieveListingsRequestForInvalidResource() {
        final Response response = whenRetrieveListingsRequestIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testRetrieveListingsRequestWithMissingContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);

        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testRetrieveListingsRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);
        headersAsMap.put(CONTENT_TYPE, "application/xml");

        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testRetrieveListingsRequestWithMissingAcceptHeader() {
        headersAsMap.remove(ACCEPT);

        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testRetrieveListingsRequestWithInvalidAcceptHeader() {
        headersAsMap.remove(ACCEPT);
        headersAsMap.put(ACCEPT, "application/jsonxml");

        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Order(6)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {SOURCE_SYSTEM, DESTINATION_SYSTEM, REQUEST_CREATED_AT})
    void testRetrieveListingsRequestWithMissingHeader(String iteration) {
        headersAsMap.remove(iteration);

        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(7)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {SOURCE_SYSTEM, DESTINATION_SYSTEM, REQUEST_CREATED_AT})
    void testRetrieveListingsRequestWithInvalidHeader(String iteration) {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");

        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(8)
    @DisplayName("Test for Correct Headers with No Parameters")
    void testRetrieveListingsRequestWithCorrectHeadersAndNoParams() {
        final Response response = whenRetrieveListingsIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(9)
    @DisplayName("Test for Correct Headers and Parameters")
    void testRetrieveListingsRequestWithCorrectHeadersAndParams() {
        final Response response = whenRetrieveListingsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(10)
    @DisplayName("Test for missing Access Token")
    void testRetrieveListingsRequestWithMissingAccessToken() {
        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingAccessToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(11)
    @DisplayName("Test for invalid Access Token")
    void testRetrieveListingsRequestWithInvalidAccessToken() {
        accessToken = TestUtilities.getToken(grantType, invalidClientID,
                invalidClientSecret, invalidTokenUrl, invalidScope);

        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenUrl, scope);
    }

    private Response whenRetrieveListingsRequestIsInvokedForInvalidResource() {
        return retrieveListingsResponseForInvalidResource(listingsApiRootContext + "get",
                headersAsMap, targetInstance);
    }

    private Response whenRetrieveListingsIsInvokedWithCorrectHeadersAndParams() {
        return retrieveListingsResponseForCorrectHeadersAndParams(listingsApiRootContext,
                headersAsMap, paramsAsMap, targetInstance);
    }

    private Response whenRetrieveListingsIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveListingsResponseForCorrectHeadersAndNoParams(listingsApiRootContext,
                headersAsMap, targetInstance);
    }

    private Response whenRetrieveListingsRequestIsInvokedWithMissingAccessToken() {
        return retrieveListingsResponseForMissingAccessToken(listingsApiRootContext,
                headersAsMap, paramsAsMap, targetInstance);
    }

    private Response whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidHeader() {
        return retrieveListingsResponseForMissingOrInvalidHeader(listingsApiRootContext,
                headersAsMap, paramsAsMap, targetInstance);
    }

    @Test
    @Order(12)
    @DisplayName("Test for Invalid Resource - By ID")
    void testRetrieveListingsByIdRequestForInvalidResource() {

        final Response response = whenRetrieveListingsByIdRequestIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(13)
    @DisplayName("Test for missing ContentType header - By ID")
    void testRetrieveListingsByIdRequestWithMissingContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);

        final Response response = whenRetrieveListingsByIdRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(14)
    @DisplayName("Test for invalid ContentType header - By ID")
    void testRetrieveListingsByIdRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);
        headersAsMap.put(CONTENT_TYPE, "application/xml");

        final Response response = whenRetrieveListingsByIdRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(15)
    @DisplayName("Test for missing Accept header - By ID")
    void testRetrieveListingsByIdRequestWithMissingAcceptHeader() {
        headersAsMap.remove(ACCEPT);

        final Response response = whenRetrieveListingsByIdRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(16)
    @DisplayName("Test for invalid Accept header - By ID")
    void testRetrieveListingsByIdRequestWithInvalidAcceptHeader() {
        headersAsMap.remove(ACCEPT);
        headersAsMap.put(ACCEPT, "application/jsonxml");

        final Response response = whenRetrieveListingsByIdRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(17)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header - By ID")
    void testRetrieveListingsByIdRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");

        final Response response = whenRetrieveListingsByIdRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(18)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header - By ID")
    void testRetrieveListingsByIdRequestWithInvalidOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key", "invalidocpsubkey");

        final Response response = whenRetrieveListingsByIdRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForRetrieve(response);
    }

    @Order(19)
    @ParameterizedTest(name = "Test for missing {0} header - By ID")
    @ValueSource(strings = {SOURCE_SYSTEM, DESTINATION_SYSTEM, REQUEST_CREATED_AT})
    void testRetrieveListingsByIdRequestWithMissingHeader(String iteration) {
        headersAsMap.remove(iteration);

        final Response response = whenRetrieveListingsByIdRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(20)
    @ParameterizedTest(name = "Test for invalid {0} header - By ID")
    @ValueSource(strings = {SOURCE_SYSTEM, DESTINATION_SYSTEM, REQUEST_CREATED_AT})
    void testRetrieveListingsByIdRequestWithInvalidHeader(String iteration) {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");

        final Response response = whenRetrieveListingsByIdRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(21)
    @DisplayName("Test for Correct Headers with No Parameters - By ID")
    void testRetrieveListingsByIdRequestWithCorrectHeadersAndNoParams() {
        final Response response = whenRetrieveListingsByIdIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(22)
    @DisplayName("Test for missing Access Token - By ID")
    void testRetrieveListingsByIdRequestWithMissingAccessToken() {
        final Response response = whenRetrieveListingsByIdRequestIsInvokedWithMissingAccessToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(23)
    @DisplayName("Test for invalid Access Token - By ID")
    void testRetrieveListingsByIdRequestWithInvalidAccessToken() {
        accessToken = TestUtilities.getToken(grantType, invalidClientID,
                invalidClientSecret, invalidTokenUrl, invalidScope);

        final Response response = whenRetrieveListingsByIdRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private Response whenRetrieveListingsByIdRequestIsInvokedForInvalidResource() {
        return retrieveListingsResponseForInvalidResource(listingsApiRootContext
                + LIST_ID + "/get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveListingsByIdIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveListingsResponseForCorrectHeadersAndNoParams(listingsApiRootContext
                + LIST_ID, headersAsMap, targetInstance);
    }

    private Response whenRetrieveListingsByIdRequestIsInvokedWithMissingAccessToken() {
        return retrieveListingsResponseForMissingAccessToken(listingsApiRootContext
                + LIST_ID, headersAsMap, paramsAsMap, targetInstance);
    }

    private Response whenRetrieveListingsByIdRequestIsInvokedWithMissingOrInvalidHeader() {
        return retrieveListingsResponseForMissingOrInvalidHeader(listingsApiRootContext
                + LIST_ID, headersAsMap, paramsAsMap, targetInstance);
    }

    private Response retrieveListingsResponseForInvalidResource(final String api,
        final Map<String, Object> headersAsMap, final String basePath) {
        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveListingsResponseForCorrectHeadersAndParams(final String api,
        final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {
        return given()
                .auth()
                .oauth2(accessToken)
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }


    private Response retrieveListingsResponseForCorrectHeadersAndNoParams(final String api,
        final Map<String, Object> headersAsMap, final String basePath) {
        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveListingsResponseForMissingAccessToken(final String api,
        final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {
        return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveListingsResponseForMissingOrInvalidHeader(final String api,
        final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {
        return given()
                .auth()
                .oauth2(accessToken)
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }
}
