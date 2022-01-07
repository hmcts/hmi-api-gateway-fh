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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForPublicationRelatedUpdate;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("PUT /publication/list/{artefact_id} - Update PublicationList")
public class PUT_PublicationList_UnitTest {

    static final String CORRECT_UPDATE_PUBLICATION_LIST_RESOURCE_PAYLOAD = "requests/update-resources-publication-list-payload.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${modifyPublicationApiRootContext}")
    private String modifyPublicationApiRootContext;

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

    private String publicationCtx;

    @BeforeAll
    void setToken(){
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenURL, scope);
        publicationCtx = String.format(modifyPublicationApiRootContext, "list", "aid123");
    }

    @BeforeEach
    void initialiseValues() {
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");
        headersAsMap.put("Request-Type", "THEFT");

    }

    @Test
    @Order(1)
    @DisplayName("Test UpdatePublicationList for Invalid Resource")
    void testUpdatePublicationListForInvalidResource() throws IOException {
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_LIST_RESOURCE_PAYLOAD);
        final Response response = whenUpdatePublicationListIsInvokedForInvalidResource(input);
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test UpdatePublicationList for missing ContentType header")
    void testUpdatePublicationListWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_LIST_RESOURCE_PAYLOAD);
        final Response response = whenUpdatePublicationListIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test UpdatePublicationList for invalid ContentType header")
    void testUpdatePublicationListWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_LIST_RESOURCE_PAYLOAD);
        final Response response = whenUpdatePublicationListIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test UpdatePublicationList for missing Accept header")
    void testUpdatePublicationListWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_LIST_RESOURCE_PAYLOAD);
        final Response response = whenUpdatePublicationListIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test UpdatePublicationList for invalid Accept header")
    void testUpdatePublicationListWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_LIST_RESOURCE_PAYLOAD);
        final Response response = whenUpdatePublicationListIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Order(6)
    @ParameterizedTest(name = "Test UpdatePublicationList for missing {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At"})
    void testUpdatePublicationListWithMissingHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_LIST_RESOURCE_PAYLOAD);
        final Response response = whenUpdatePublicationListIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(7)
    @ParameterizedTest(name = "Test UpdatePublicationList for invalid {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At"})
    void testUpdatePublicationListWithInvalidHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_LIST_RESOURCE_PAYLOAD);
        final Response response = whenUpdatePublicationListIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(8)
    @DisplayName("Test UpdatePublicationList for correct Headers")
    void testUpdatePublicationListRequestWithCorrectHeaders() throws IOException {
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_LIST_RESOURCE_PAYLOAD);
        final Response response = whenUpdatePublicationListIsInvokedWithCorrectHeaders(input);
        thenValidateResponseForPublicationRelatedUpdate(response);
    }

    @Test
    @Order(9)
    @DisplayName("Test UpdatePublicationList for missing Access Token")
    void testUpdatePublicationListRequestWithMissingAccessToken() throws IOException {
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_LIST_RESOURCE_PAYLOAD);
        final Response response = whenUpdatePublicationListIsInvokedWithMissingAccessToken(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(10)
    @DisplayName("Test UpdatePublicationList for invalid Access Token")
    void testUpdatePublicationListRequestWithInvalidAccessToken() throws IOException {
        accessToken = "TestUtilities.getToken(grantType, invalidClientID, invalidClientSecret, invalidTokenURL, invalidScope)";
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_LIST_RESOURCE_PAYLOAD);
        final Response response = whenUpdatePublicationListIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }

    private Response whenUpdatePublicationListIsInvokedWithMissingOrInvalidHeader(final String input) {
        return updatePublicationListResponseForAMissingOrInvalidHeader(publicationCtx, headersAsMap, targetInstance, input);
    }

    private Response whenUpdatePublicationListIsInvokedWithMissingAccessToken(final String input) {
        return updatePublicationListResponseForMissingAccessToken(publicationCtx, headersAsMap, targetInstance, input);
    }

    private Response whenUpdatePublicationListIsInvokedForInvalidResource(final String input) {
        return updatePublicationListResponseForInvalidResource(publicationCtx +"/put", headersAsMap, targetInstance, input);
    }

    private Response whenUpdatePublicationListIsInvokedWithCorrectHeaders(final String input) {
        return updatePublicationListResponseForCorrectHeadersAndParams(publicationCtx, headersAsMap, targetInstance, input);
    }

    private Response updatePublicationListResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .auth()
                .oauth2(accessToken)
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response updatePublicationListResponseForCorrectHeadersAndParams(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .auth()
                .oauth2(accessToken)
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response updatePublicationListResponseForAMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return given()
                .auth()
                .oauth2(accessToken)
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response updatePublicationListResponseForMissingAccessToken(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return  given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }
    
}
