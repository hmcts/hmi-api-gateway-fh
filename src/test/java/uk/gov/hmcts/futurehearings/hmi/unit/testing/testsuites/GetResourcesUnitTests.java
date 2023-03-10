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
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForRetrieve;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("GET /resources - Retrieve Resources")
@SuppressWarnings({"java:S2699", "PMD.TooManyMethods"})
class GetResourcesUnitTests {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${resourcesApiRootContext}")
    private String resourcesApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

    private final Map<String, Object> headersAsMap = new ConcurrentHashMap<>();

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
    private static final String CASE = "/CASE123432";

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
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testRetrieveResourcesRequestForInvalidResource() {
        final Response response = whenRetrieveResourcesIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testRetrieveResourcesRequestWithMissingContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);
        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testRetrieveResourcesRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);
        headersAsMap.put(CONTENT_TYPE, "application/xml");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testRetrieveResourcesRequestWithMissingAcceptHeader() {
        headersAsMap.remove(ACCEPT);
        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testRetrieveResourcesRequestWithInvalidAcceptHeader() {
        headersAsMap.remove(ACCEPT);
        headersAsMap.put(ACCEPT, "application/jsonxml");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Order(6)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {SOURCE_SYSTEM, DESTINATION_SYSTEM, REQUEST_CREATED_AT})
    void testRetrieveResourcesRequestWithMissingHeader(String iteration) {
        headersAsMap.remove(iteration);

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(7)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {SOURCE_SYSTEM, DESTINATION_SYSTEM, REQUEST_CREATED_AT})
    void testRetrieveResourcesRequestWithInvalidHeader(String iteration) {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(8)
    @DisplayName("Test for Correct Headers and No Parameters")
    void testRetrieveResourcesRequestWithCorrectRequestAndNoParams() {
        final Response response = whenRetrieveResourcesIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(9)
    @DisplayName("Test for missing Access Token")
    void testRetrieveResourcesRequestWithMissingAccessToken() {

        final Response response = whenRetrieveResourcesIsInvokedWithMissingAccessToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(10)
    @DisplayName("Test for invalid Access Token")
    void testRetrieveResourcesRequestWithInvalidAccessToken() {
        accessToken = TestUtilities.getToken(grantType,
                invalidClientID, invalidClientSecret, invalidTokenUrl, invalidScope);

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAccessToken(response);

        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenUrl, scope);
    }


    private Response whenRetrieveResourcesIsInvokedForInvalidResource() {
        return retrieveResourcesResponseForInvalidResource(resourcesApiRootContext + "get",
                headersAsMap, targetInstance);
    }

    private Response whenRetrieveResourcesIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveResourcesResponseForCorrectRequestAndNoParams(resourcesApiRootContext,
                headersAsMap, targetInstance);
    }

    private Response whenRetrieveResourcesIsInvokedWithMissingAccessToken() {
        return retrieveResourcesResponseForMissingAccessToken(resourcesApiRootContext,
                headersAsMap, targetInstance);
    }

    private Response whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader() {
        return retrieveResourcesResponseForMissingOrInvalidHeader(resourcesApiRootContext,
                headersAsMap, targetInstance);
    }

    @Test
    @Order(11)
    @DisplayName("Test for Invalid Resource - Individual Resource")
    void testRetrieveIndividualResourceRequestForInvalidResource() {
        final Response response = whenRetrieveIndividualResourceIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(12)
    @DisplayName("Test for missing ContentType header - Individual Resource")
    void testRetrieveIndividualResourceRequestWithMissingContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(13)
    @DisplayName("Test for invalid ContentType header - Individual Resource")
    void testRetrieveIndividualResourcesRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);
        headersAsMap.put(CONTENT_TYPE, "application/xml");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(14)
    @DisplayName("Test for missing Accept header - Individual Resource")
    void testRetrieveIndividualResourceRequestWithMissingAcceptHeader() {
        headersAsMap.remove(ACCEPT);
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(15)
    @DisplayName("Test for invalid Accept header - Individual Resource")
    void testRetrieveIndividualResourceRequestWithInvalidAcceptHeader() {
        headersAsMap.remove(ACCEPT);
        headersAsMap.put(ACCEPT, "application/jsonxml");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(16)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header - Individual Resource")
    void testRetrieveIndividualResourceRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForRetrieve(response);
    }

    @Order(18)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {SOURCE_SYSTEM, DESTINATION_SYSTEM, REQUEST_CREATED_AT})
    void testRetrieveIndividualResourcesRequestWithMissingHeader(String iteration) {
        headersAsMap.remove(iteration);

        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(19)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {SOURCE_SYSTEM, DESTINATION_SYSTEM, REQUEST_CREATED_AT})
    void testRetrieveIndividualResourcesRequestWithInvalidHeader(String iteration) {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");

        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(20)
    @DisplayName("Test for No Parameters - Individual Resource")
    void testRetrieveIndividualResourceRequestWithCorrectRequestAndNoParams() {
        final Response response = whenRetrieveIndividualResourceIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(23)
    @DisplayName("Test for missing Access Token")
    void testRetrieveIndividualResourcesRequestWithMissingAccessToken() {

        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingAccessToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(24)
    @DisplayName("Test for invalid Access Token")
    void testRetrieveIndividualResourcesRequestWithInvalidAccessToken() {
        accessToken = TestUtilities.getToken(grantType, invalidClientID, invalidClientSecret,
                invalidTokenUrl, invalidScope);

        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private Response whenRetrieveIndividualResourceIsInvokedForInvalidResource() {
        return retrieveResourcesResponseForInvalidResource(resourcesApiRootContext + "get" + CASE,
                headersAsMap, targetInstance);
    }

    private Response whenRetrieveIndividualResourceIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveResourcesResponseForCorrectRequestAndNoParams(resourcesApiRootContext + CASE,
                headersAsMap, targetInstance);
    }

    private Response whenRetrieveIndividualResourceIsInvokedWithMissingAccessToken() {
        return retrieveResourcesResponseForMissingAccessToken(resourcesApiRootContext + CASE,
                headersAsMap, targetInstance);
    }

    private Response whenRetrieveIndividualResourceIsInvokedWithMissingOrInvalidHeader() {
        return retrieveResourcesResponseForMissingOrInvalidHeader(resourcesApiRootContext + CASE,
                headersAsMap, targetInstance);
    }

    private Response retrieveResourcesResponseForInvalidResource(final String api,
        final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveResourcesResponseForCorrectRequestAndNoParams(final String api,
        final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveResourcesResponseForMissingAccessToken(final String api,
        final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveResourcesResponseForMissingOrInvalidHeader(final String api,
        final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();

    }
}
