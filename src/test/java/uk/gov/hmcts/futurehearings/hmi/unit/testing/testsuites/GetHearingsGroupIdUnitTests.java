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
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForAdditionalParam;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForRetrieve;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("GET /hearings - Retrieve Hearings")
@SuppressWarnings({"java:S2699", "PMD.TooManyMethods"})
class GetHearingsGroupIdUnitTests {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${hearingApiRootContext}")
    private String hearingApiRootContext;

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

    @BeforeAll
    void setToken() {
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenUrl, scope);
    }

    @BeforeEach
    void initialiseValues() {

        headersAsMap.put(CONTENT_TYPE, "application/json");
        headersAsMap.put(ACCEPT, "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");

        paramsAsMap.put("hearingType", "THEFT");
        paramsAsMap.put("hearingDate", "2018-02-29T20:36:01Z");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testRetrieveHearingsRequestForInvalidResource() {

        final Response response = whenRetrieveHearingsRequestIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testRetrieveHearingsRequestWithMissingContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testRetrieveHearingsRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);
        headersAsMap.put(CONTENT_TYPE, "application/xml");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testRetrieveHearingsRequestWithMissingAcceptHeader() {
        headersAsMap.remove(ACCEPT);

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testRetrieveHearingsRequestWithInvalidAcceptHeader() {
        headersAsMap.remove(ACCEPT);
        headersAsMap.put(ACCEPT, "application/jsonxml");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Order(6)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {"Source-System", "Destination-System", "Request-Created-At"})
    void testRetrieveHearingsRequestWithMissingHeader(String iteration) {
        headersAsMap.remove(iteration);

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(7)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {"Source-System", "Destination-System", "Request-Created-At"})
    void testRetrieveHearingsRequestWithInvalidHeader(String iteration) {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(8)
    @DisplayName("Test for Invalid Parameter")
    void testRetrieveHearingsRequestWithAdditionalParam() {
        paramsAsMap.put("Invalid-Param", "Value");

        final Response response = whenRetrieveHearingsIsInvokedWithAdditionalParam();
        thenValidateResponseForAdditionalParam(response);
        paramsAsMap.remove("Invalid-Param");
    }

    @Order(9)
    @ParameterizedTest(name = "Test for {0} Parameter")
    @ValueSource(strings = {"hearingDate", "hearingType"})
    void testRetrieveHearingsRequestWithParam(String iteration) {
        paramsAsMap.clear();
        paramsAsMap.put(iteration, "Value");

        final Response response = whenRetrieveHearingsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(10)
    @DisplayName("Test for Correct Headers with No Parameters")
    void testRetrieveHearingsRequestWithCorrectHeadersAndNoParams() {

        final Response response = whenRetrieveHearingsIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(11)
    @DisplayName("Test for Correct Headers and Parameters")
    void testRetrieveHearingsRequestWithCorrectHeadersAndParams() {

        final Response response = whenRetrieveHearingsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(12)
    @DisplayName("Test for missing Access Token")
    void testRetrieveHearingRequestWithMissingAccessToken() {

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingAccessToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(15)
    @DisplayName("Test only with path param")
    void testRetrieveHearingRequestWithPathParam() {
        final Response response = whenRetrieveHearingsIsInvokedWithCorrectHeadersAndPathParams();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(16)
    @DisplayName("Test for invalid Access Token")
    void testRetrieveHearingRequestWithInvalidAccessToken() {
        accessToken = TestUtilities.getToken(grantType, invalidClientID,
                invalidClientSecret, invalidTokenUrl, invalidScope);

        final Response response = whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private Response whenRetrieveHearingsIsInvokedWithAdditionalParam() {
        return retrieveHearingsResponseForCorrectHeadersAndParams(hearingApiRootContext,
                headersAsMap, paramsAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsRequestIsInvokedForInvalidResource() {
        return retrieveHearingsResponseForInvalidResource(hearingApiRootContext + "get",
                headersAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsIsInvokedWithCorrectHeadersAndParams() {
        return retrieveHearingsResponseForCorrectHeadersAndParams(hearingApiRootContext, headersAsMap,
                paramsAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsIsInvokedWithCorrectHeadersAndPathParams() {
        return retrieveHearingsResponseForCorrectHeadersAndPathParam(hearingApiRootContext + "/CASE1234",
                headersAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveHearingsResponseForCorrectHeadersAndNoParams(hearingApiRootContext,
                headersAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsRequestIsInvokedWithMissingAccessToken() {
        return retrieveHearingsResponseForMissingAccessToken(hearingApiRootContext, headersAsMap,
                paramsAsMap, targetInstance);
    }

    private Response whenRetrieveHearingsRequestIsInvokedWithMissingOrInvalidHeader() {
        return retrieveHearingsResponseForMissingOrInvalidHeader(hearingApiRootContext, headersAsMap,
                paramsAsMap, targetInstance);
    }

    private Response retrieveHearingsResponseForInvalidResource(final String api,
        final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveHearingsResponseForCorrectHeadersAndParams(final String api,
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

    private Response retrieveHearingsResponseForCorrectHeadersAndPathParam(final String api,
        final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }


    private Response retrieveHearingsResponseForCorrectHeadersAndNoParams(final String api,
        final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveHearingsResponseForMissingAccessToken(final String api,
        final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveHearingsResponseForMissingOrInvalidHeader(final String api,
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
