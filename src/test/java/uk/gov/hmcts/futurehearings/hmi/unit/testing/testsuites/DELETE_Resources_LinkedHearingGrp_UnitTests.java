package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForLinkedHearingGroup;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
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
@DisplayName("PUT /resources/location - Update Location Resource")
public class DELETE_Resources_LinkedHearingGrp_UnitTests {
    static final String CORRECT_DELETE_LOCATION_RESOURCE_PAYLOAD = "requests/delete-resources-location-payload.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${genericResourcesApiRootContext}")
    private String genericResourcesApiRootContext;

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

    private String linkedHearingGroupCtx;

    @BeforeAll
    void setToken(){
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenURL, scope);
        linkedHearingGroupCtx = String.format(genericResourcesApiRootContext, "linked-hearing-group/groupClientReference");
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
    @DisplayName("Test for Invalid Resource")
    void testDeleteLinkedHearingGroupResourceForInvalidResource() throws IOException {
        final Response response = whenDeleteLinkedHearingGroupResourceIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testDeleteLinkedHearingGroupResourceWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final Response response = whenDeleteLinkedHearingGroupResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testDeleteLinkedHearingGroupResourceWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final Response response = whenDeleteLinkedHearingGroupResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testDeleteLinkedHearingGroupResourceWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final Response response = whenDeleteLinkedHearingGroupResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testDeleteLinkedHearingGroupResourceWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final Response response = whenDeleteLinkedHearingGroupResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Order(6)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At"})
    void testDeleteLinkedHearingGroupResourceWithMissingHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        final Response response = whenDeleteLinkedHearingGroupResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(7)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At"})
    void testDeleteLinkedHearingGroupResourceWithInvalidHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");
        final Response response = whenDeleteLinkedHearingGroupResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(8)
    @DisplayName("Test for correct Headers")
    void testDeleteLinkedHearingGroupResourceRequestWithCorrectHeaders() throws IOException {
        final Response response = whenDeleteLinkedHearingGroupResourceIsInvokedWithCorrectHeaders();
        thenValidateResponseForLinkedHearingGroup(response);
    }

    @Test
    @Order(9)
    @DisplayName("Test for missing Access Token")
    void testDeleteLinkedHearingGroupResourceRequestWithMissingAccessToken() throws IOException {
        final Response response = whenDeleteLinkedHearingGroupResourceIsInvokedWithMissingAccessToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(10)
    @DisplayName("Test for invalid Access Token")
    void testDeleteLinkedHearingGroupResourceRequestWithInvalidAccessToken() throws IOException {
        accessToken = TestUtilities.getToken(grantType, invalidClientID, invalidClientSecret, invalidTokenURL, invalidScope);
        final Response response = whenDeleteLinkedHearingGroupResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }

    private Response whenDeleteLinkedHearingGroupResourceIsInvokedWithMissingOrInvalidHeader() {
        return deleteLinkedHearingGroupResourceResponseForAMissingOrInvalidHeader(linkedHearingGroupCtx, headersAsMap, targetInstance);
    }

    private Response whenDeleteLinkedHearingGroupResourceIsInvokedWithMissingAccessToken() {
        return deleteLinkedHearingGroupResourceResponseForMissingAccessToken(linkedHearingGroupCtx, headersAsMap, targetInstance);
    }

    private Response whenDeleteLinkedHearingGroupResourceIsInvokedForInvalidResource() {
        return deleteLinkedHearingGroupResourceResponseForInvalidResource(linkedHearingGroupCtx+"/put", headersAsMap, targetInstance);
    }

    private Response whenDeleteLinkedHearingGroupResourceIsInvokedWithCorrectHeaders() {
        return deleteLinkedHearingGroupResourceResponseForCorrectHeadersAndParams(linkedHearingGroupCtx, headersAsMap, targetInstance);
    }

    private Response deleteLinkedHearingGroupResourceResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response deleteLinkedHearingGroupResourceResponseForCorrectHeadersAndParams(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }

    private Response deleteLinkedHearingGroupResourceResponseForAMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap, final String basePath) {
        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }

    private Response deleteLinkedHearingGroupResourceResponseForMissingAccessToken(final String api, final Map<String, Object> headersAsMap, final String basePath) {
        return  given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }
}
