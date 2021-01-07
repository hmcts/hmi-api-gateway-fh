package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import org.springframework.beans.factory.annotation.Value;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForInvalidSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForUpdate;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("PUT /resources/user - Update User Resource")
@SuppressWarnings("java:S2699")
class PUT_resources_user_UnitTests {

    static final String CORRECT_UPDATE_USER_RESOURCE_PAYLOAD = "requests/update-resources-user-payload.json";
    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${resourcesApiRootContext}")
    private String resourcesApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

    private final Map<String, Object> headersAsMap = new HashMap<>();

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

    @BeforeAll
    void setToken(){
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenURL, scope);
    }

    @BeforeEach
    void initialiseValues() {

        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
        headersAsMap.put("Request-Type", "THEFT");

    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testUpdateUserResourceForInvalidResource() throws IOException {
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedForInvalidResource(input);
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testUpdateUserResourceWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }
    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testUpdateUserResourceWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testUpdateUserResourceWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testUpdateUserResourceWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing OcpSubKey")
    void testUpdateUserResourceRequestWithMissingOcpSubKey() throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForUpdate(response);
    }

    @Order(8)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At"})
    void testUpdateUserResourceWithMissingHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(9)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At"})
    void testUpdateUserResourceWithInvalidHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(10)
    @DisplayName("Test for correct Headers")
    void testUpdateUserResourceRequestWithCorrectHeaders() throws IOException {

        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithCorrectHeaders(input);
        thenValidateResponseForUpdate(response);
    }



    @Test
    @Order(11)
    @DisplayName("Test for missing Access Token")
    void testUpdateUserResourceRequestWithMissingAccessToken() throws IOException {

        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingAccessToken(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(12)
    @DisplayName("Test for invalid Access Token")
    void testUpdateUserResourceRequestWithInvalidAccessToken() throws IOException {
        accessToken = TestUtilities.getToken(grantType, invalidClientID, invalidClientSecret, invalidTokenURL, invalidScope);

        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }

    private Response whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(final String input) {
        return updateUserResourceResponseForAMissingOrInvalidHeader(resourcesApiRootContext + "/user/UserID", headersAsMap, targetInstance, input);
    }

    private Response whenUpdateUserResourceIsInvokedWithMissingAccessToken(final String input) {
        return updateUserResourceResponseFForMissingAccessToken(resourcesApiRootContext + "/user/UserID", headersAsMap, targetInstance, input);
    }

    private Response whenUpdateUserResourceIsInvokedForInvalidResource(final String input) {
        return updateUserResourceResponseForInvalidResource(resourcesApiRootContext+"/UserID/put", headersAsMap, targetInstance, input);
    }

    private Response whenUpdateUserResourceIsInvokedWithCorrectHeaders(final String input) {
        return updateUserResourceResponseForCorrectHeadersAndParams(resourcesApiRootContext+ "/user/UserID", headersAsMap, targetInstance, input);
    }

    private Response updateUserResourceResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .auth()
                .oauth2(accessToken)
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response updateUserResourceResponseForCorrectHeadersAndParams(final String api, final Map<String, Object> headersAsMap, final String basePath,  final String payloadBody) {

        return given()
                .auth()
                .oauth2(accessToken)
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response updateUserResourceResponseForAMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return given()
                .auth()
                .oauth2(accessToken)
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response updateUserResourceResponseFForMissingAccessToken(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return  given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }
}
