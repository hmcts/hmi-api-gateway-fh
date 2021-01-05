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
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.*;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("GET /sessions - Retrieve Sessions")
@SuppressWarnings("java:S2699")
class GET_sessions_UnitTests {
    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${sessionsApiRootContext}")
    private String sessionsApiRootContext;

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
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
        headersAsMap.put("Request-Processed-At", "2018-02-29 20:36:01Z");

        paramsAsMap.put("requestSessionType", "ADHOC");
        paramsAsMap.put("requestStartDate", "2018-01-29 20:36:01Z");
        paramsAsMap.put("requestEndDate", "2018-01-29 21:36:01Z");
        paramsAsMap.put("requestJudgeType", "AC");
        paramsAsMap.put("requestLocationId", "354");
        paramsAsMap.put("requestDuration", "360");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testRetrieveSessionsRequestForInvalidResource() {
        final Response response = whenRetrieveSessionsRequestIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testRetrieveSessionsRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testRetrieveSessionsRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testRetrieveSessionsRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testRetrieveSessionsRequestWithInvalidAcceptHeader() {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
    void testRetrieveSessionsRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    void testRetrieveSessionsRequestWithInvalidOcpSubKey(){
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }

    @Order(8)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At"})
    void testRetrieveSessionsRequestWithMissingHeader(String iteration) {
        headersAsMap.remove(iteration);

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(9)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At"})
    void testRetrieveSessionsRequestWithInvalidHeader(String iteration) {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(10)
    @DisplayName("Test for Invalid Parameter")
    void testRetrieveSessionsRequestWithAdditionalParam() {
        paramsAsMap.put("requestSessionType", "ADHOC");
        paramsAsMap.put("Invalid-Param","Value");
        final Response response = whenRetrieveSessionsIsInvokedWithAdditionalParam();
        thenValidateResponseFoInvalidParam("Invalid-Param", response);
    }


    @Order(11)
    @ParameterizedTest(name = "Test for mandatory parameter - {0}")
    @ValueSource(strings = {"requestSessionType"})
    void testRetrieveSessionsRequestWithDateParams(String iteration) {
        paramsAsMap.clear();
        paramsAsMap.put(iteration,"ADHOC");
        final Response response = whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseForNoMandatoryParams(response);
    }

    @Test
    @Order(12)
    @DisplayName("Test with one non-mandatory and one mandatory parameters")
    void testRetrieveSessionsRequestWithOneNonMandatoryParams() {
        paramsAsMap.clear();
        paramsAsMap.put("requestSessionType", "ADHOC");
        paramsAsMap.put("requestStartDate", "2020-12-13T20:00:00Z");
        final Response response = whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseForNoMandatoryParams(response);
    }

    @Test
    @Order(13)
    @DisplayName("Test with two non-mandatory and one mandatory parameters")
    void testRetrieveSessionsRequestWithTwoNonMandatoryParams() {
        paramsAsMap.remove("requestEndDate");
        final Response response = whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseForNoMandatoryParams(response);
    }


    @Test
    @Order(14)
    @DisplayName("Test with no mandatory parameters")
    void testRetrieveSessionsRequestWithNoMandatoryParams() {
        paramsAsMap.clear();
            final Response response = whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseForNoMandatoryParams(response);
    }

    @Test
    @Order(15)
    @DisplayName("Test for Correct Headers with No Parameters")
    void testRetrieveSessionsRequestWithCorrectHeadersAndNoParams() {
        final Response response = whenRetrieveSessionsIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(16)
    @DisplayName("Test for Correct Headers and Parameters")
    void testRetrieveSessionsRequestWithCorrectHeadersAndParams() {
        final Response response = whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(17)
    @DisplayName("Test for missing Access Token")
    void testRetrieveSessionsRequestWithMissingAccessToken() {
        final Response response = whenRetrieveSessionsIsInvokedWithMissingAccessToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(18)
    @DisplayName("Test for invalid Access Token")
    void testRetrieveSessionsRequestWithInvalidAccessToken()  {
        accessToken = TestUtilities.getToken(grantType, invalidClientID, invalidClientSecret, invalidTokenURL, invalidScope);

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAccessToken(response);

        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenURL, scope);
    }



    private Response whenRetrieveSessionsIsInvokedWithAdditionalParam() {
        return retrieveSessionsResponseForCorrectHeadersAndParams(sessionsApiRootContext, headersAsMap, paramsAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsRequestIsInvokedForInvalidResource() {
        return retrieveSessionsResponseForInvalidResource(sessionsApiRootContext+"get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams() {
        paramsAsMap.clear();
        paramsAsMap.put("requestSessionType", "any");
        return retrieveSessionsResponseForCorrectHeadersAndParams(sessionsApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsIsInvokedWithCorrectHeadersAndNoParams() {
        paramsAsMap.clear();
        paramsAsMap.put("requestSessionType", "any");
        return retrieveSessionsResponseForCorrectHeadersAndParams(sessionsApiRootContext, headersAsMap, paramsAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsIsInvokedWithMissingAccessToken() {
        return retrieveSessionsResponseForMissingAccessToken(sessionsApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader() {
        return retrieveSessionsResponseForMissingOrInvalidHeader(sessionsApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }


    //Sessions By ID


    @Test
    @Order(19)
    @DisplayName("Test for Invalid Resource - By ID")
    void testRetrieveSessionsByIDRequestForInvalidResource() {

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(20)
    @DisplayName("Test for missing ContentType header - By ID")
    void testRetrieveSessionsByIDRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(21)
    @DisplayName("Test for invalid ContentType header - By ID")
    void testRetrieveSessionsByIDRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(22)
    @DisplayName("Test for missing Accept header - By ID")
    void testRetrieveSessionsByIDRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(23)
    @DisplayName("Test for invalid Accept header - By ID")
    void testRetrieveSessionsByIDRequestWithInvalidAcceptHeader() {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(24)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header - By ID")
    void testRetrieveSessionsByIDRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(25)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header - By ID")
    void testRetrieveSessionsByIDRequestWithInvalidOcpSubKey(){
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }

    @Order(26)
    @ParameterizedTest(name = "Test for missing {0} header - By ID")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At"})
    void testRetrieveSessionsByIDRequestWithMissingHeader(String iteration) {
        headersAsMap.remove(iteration);

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(27)
    @ParameterizedTest(name = "Test for invalid {0} header - By ID")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At"})
    void testRetrieveSessionsByIDRequestWithInvalidHeader(String iteration) {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(28)
    @DisplayName("Test for Correct Headers with No Parameters - By ID")
    void testRetrieveSessionsByIDRequestWithCorrectHeadersAndNoParams() {

        final Response response = whenRetrieveSessionsByIDIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(29)
    @DisplayName("Test for missing Access Token")
    void testRetrieveSessionsByIDRequestWithMissingAccessToken() {
        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingAccessToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(30)
    @DisplayName("Test for invalid Access Token")
    void testRetrieveSessionsByIDRequestWithInvalidAccessToken()  {
        accessToken = TestUtilities.getToken(grantType, invalidClientID, invalidClientSecret, invalidTokenURL, invalidScope);

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private Response whenRetrieveSessionsByIDRequestIsInvokedForInvalidResource() {
        return retrieveSessionsResponseForInvalidResource(sessionsApiRootContext+"/CASE1234/get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsByIDIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveSessionsResponseForCorrectHeadersAndNoParams(sessionsApiRootContext+"/CASE1234", headersAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsByIDRequestIsInvokedWithMissingAccessToken() {
        return retrieveSessionsResponseForMissingAccessToken(sessionsApiRootContext+"/CASE1234", headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader() {
        return retrieveSessionsResponseForMissingOrInvalidHeader(sessionsApiRootContext+"/CASE1234", headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response retrieveSessionsResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveSessionsResponseForCorrectHeadersAndParams(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }


    private Response retrieveSessionsResponseForCorrectHeadersAndNoParams(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveSessionsResponseForMissingAccessToken(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveSessionsResponseForMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap,final Map<String, String> paramsAsMap, final String basePath) {

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
