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
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForDeletePublication;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("DELETE /publication/outcome/{artefact_id} - Delete Publication Outcome")
public class DELETE_PublicationOutcome_UnitTest {
    
    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${modifyPublicationApiRootContext}")
    private String modifyPublicationApiRootContext;

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

    @Value("${invalidTokenURL}")
    private String invalidTokenURL;

    @Value("${invalidScope}")
    private String invalidScope;

    @Value("${invalidClientID}")
    private String invalidClientID;

    @Value("${invalidClientSecret}")
    private String invalidClientSecret;

    private static String accessToken;

    private HmiHttpClient httpClient;
    private String publicationCtx;

    private final Map<String, Object> headersAsMap = new HashMap<>();
    private final Map<String, String> paramsAsMap = new HashMap<>();

    @BeforeAll
    void setToken(){
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenURL, scope);
        this.httpClient = new HmiHttpClient(accessToken, targetInstance);
        publicationCtx = String.format(modifyPublicationApiRootContext, "outcome", "aid123");
    }

    @BeforeEach
    void initialiseValues() {
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", "MOCK");
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");
    }

    @Test
    @Order(1)
    @DisplayName("Test PublicationOutcome for Invalid Resource")
    void testDeletePublicationOutcomeForInvalidResource() throws IOException {
        final Response response = whenDeletePublicationOutcomeIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test PublicationOutcome for missing ContentType header")
    void testDeletePublicationOutcomeWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final Response response = whenDeletePublicationOutcomeIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test PublicationOutcome for invalid ContentType header")
    void testDeletePublicationOutcomeWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final Response response = whenDeletePublicationOutcomeIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test PublicationOutcome for missing Accept header")
    void testDeletePublicationOutcomeWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final Response response = whenDeletePublicationOutcomeIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test PublicationOutcome for invalid Accept header")
    void testDeletePublicationOutcomeWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final Response response = whenDeletePublicationOutcomeIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Order(6)
    @ParameterizedTest(name = "Test PublicationOutcome for missing {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At"})
    void testDeletePublicationOutcomeWithMissingHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        final Response response = whenDeletePublicationOutcomeIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(7)
    @ParameterizedTest(name = "Test PublicationOutcome for invalid {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At"})
    void testDeletePublicationOutcomeWithInvalidHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");
        final Response response = whenDeletePublicationOutcomeIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(8)
    @DisplayName("Test PublicationOutcome for correct Headers")
    void testDeletePublicationOutcomeWithCorrectHeaders() throws IOException {
        final Response response = whenDeletePublicationOutcomeIsInvokedWithCorrectHeaders();
        thenValidateResponseForDeletePublication(response);
    }

    @Test
    @Order(9)
    @DisplayName("Test PublicationOutcome for missing Access Token")
    void testDeletePublicationOutcomeWithMissingAccessToken() throws IOException {
        final Response response = whenDeletePublicationOutcomeIsInvokedWithMissingAccessToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(10)
    @DisplayName("Test PublicationOutcome for invalid Access Token")
    void testDeletePublicationOutcomeWithInvalidAccessToken() throws IOException {
        accessToken = "TestUtilities.getToken(grantType,invalidClientID,invalidClientSecret,invalidTokenURL,invalidScope)";
        final Response response = whenDeletePublicationOutcomeIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }

    private Response whenDeletePublicationOutcomeIsInvokedWithMissingOrInvalidHeader() {
        return deletePublicationResponseForAMissingOrInvalidHeader(publicationCtx, headersAsMap, targetInstance);
    }

    private Response whenDeletePublicationOutcomeIsInvokedWithMissingAccessToken() {
        return deletePublicationResponseForMissingAccessToken(publicationCtx, headersAsMap, targetInstance);
    }

    private Response whenDeletePublicationOutcomeIsInvokedForInvalidResource() {
        return deletePublicationResponseForInvalidResource(publicationCtx+"/put", headersAsMap, targetInstance);
    }

    private Response whenDeletePublicationOutcomeIsInvokedWithCorrectHeaders() {
        return deletePublicationResponseForCorrectHeadersAndParams(publicationCtx, headersAsMap, targetInstance);
    }

    private Response deletePublicationResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }

    private Response deletePublicationResponseForCorrectHeadersAndParams(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }

    private Response deletePublicationResponseForAMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap, final String basePath) {
        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }

    private Response deletePublicationResponseForMissingAccessToken(final String api, final Map<String, Object> headersAsMap, final String basePath) {
        return  given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }
    
}
