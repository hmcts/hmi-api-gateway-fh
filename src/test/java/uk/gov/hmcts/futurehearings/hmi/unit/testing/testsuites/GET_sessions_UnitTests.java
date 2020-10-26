package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForNoMandatoryParams;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForRetrieve;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForInvalidSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForAdditionalParam;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
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
        headersAsMap.put("Destination-System", "S&L");
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
        headersAsMap.put("Request-Processed-At", "2018-02-29 20:36:01Z");

        paramsAsMap.put("sessionStartDate", "2018-01-29 20:36:01Z");
        paramsAsMap.put("sessionEndDate", "2018-01-29 21:36:01Z");
        paramsAsMap.put("caseCourt", "oxford");
        paramsAsMap.put("room-Name", "RM012");

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

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidOcpSubKey();
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    void testRetrieveSessionsRequestWithInvalidOcpSubKey(){
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidOcpSubKey();
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }

    @Order(8)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At","Request-Type"})
    void testRetrieveSessionsRequestWithMissingHeader(String iteration) {
        headersAsMap.remove(iteration);

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(9)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At","Request-Type"})
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
        paramsAsMap.put("Invalid-Param","Value");

        final Response response = whenRetrieveSessionsIsInvokedWithAdditionalParam();
        thenValidateResponseForAdditionalParam(response);
    }


    @Order(11)
    @ParameterizedTest(name = "Test for mandatory parameter - {0}")
    @ValueSource(strings = {"sessionStartDate","sessionEndDate"})
    void testRetrieveSessionsRequestWithDateParams(String iteration) {
        paramsAsMap.clear();
        paramsAsMap.put(iteration,"2018-01-29 20:36:01Z");

        final Response response = whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseForNoMandatoryParams(response);
    }

    @Test
    @Order(12)
    @DisplayName("Test with one non-mandatory and one mandatory parameters")
    void testRetrieveSessionsRequestWithOneNonMandatoryParams() {
        paramsAsMap.remove("sessionStartDate");
        paramsAsMap.remove("room-Name");

        final Response response = whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseForNoMandatoryParams(response);
    }

    @Test
    @Order(13)
    @DisplayName("Test with two non-mandatory and one mandatory parameters")
    void testRetrieveSessionsRequestWithTwoNonMandatoryParams() {
        paramsAsMap.remove("sessionEndDate");

        final Response response = whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseForNoMandatoryParams(response);
    }


    @Test
    @Order(14)
    @DisplayName("Test with no mandatory parameters")
    void testRetrieveSessionsRequestWithNoMandatoryParams() {
        paramsAsMap.clear();
        paramsAsMap.put("caseCourt","oxford");
        paramsAsMap.put("room-Name", "RM012");

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



    private Response whenRetrieveSessionsIsInvokedWithAdditionalParam() {
        return retrieveSessionsResponseForCorrectHeadersAndParams(sessionsApiRootContext, headersAsMap, paramsAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsRequestIsInvokedForInvalidResource() {
        return retrieveSessionsResponseForInvalidResource(sessionsApiRootContext+"get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams() {
        return retrieveSessionsResponseForCorrectHeadersAndParams(sessionsApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveSessionsResponseForCorrectHeadersAndNoParams(sessionsApiRootContext, headersAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidOcpSubKey() {
        return retrieveSessionsResponseForMissingOrInvalidOcpSubKey(sessionsApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader() {
        return retrieveSessionsResponseForMissingOrInvalidHeader(sessionsApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }


    //Sessions By ID


    @Test
    @Order(17)
    @DisplayName("Test for Invalid Resource - By ID")
    void testRetrieveSessionsByIDRequestForInvalidResource() {

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(18)
    @DisplayName("Test for missing ContentType header - By ID")
    void testRetrieveSessionsByIDRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(19)
    @DisplayName("Test for invalid ContentType header - By ID")
    void testRetrieveSessionsByIDRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(20)
    @DisplayName("Test for missing Accept header - By ID")
    void testRetrieveSessionsByIDRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(21)
    @DisplayName("Test for invalid Accept header - By ID")
    void testRetrieveSessionsByIDRequestWithInvalidAcceptHeader() {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(22)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header - By ID")
    void testRetrieveSessionsByIDRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidOcpSubKey();
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(23)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header - By ID")
    void testRetrieveSessionsByIDRequestWithInvalidOcpSubKey(){
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidOcpSubKey();
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }

    @Order(24)
    @ParameterizedTest(name = "Test for missing {0} header - By ID")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At","Request-Type"})
    void testRetrieveSessionsByIDRequestWithMissingHeader(String iteration) {
        headersAsMap.remove(iteration);

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(25)
    @ParameterizedTest(name = "Test for invalid {0} header - By ID")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At","Request-Type"})
    void testRetrieveSessionsByIDRequestWithInvalidHeader(String iteration) {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");

        final Response response = whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(26)
    @DisplayName("Test for Correct Headers with No Parameters - By ID")
    void testRetrieveSessionsByIDRequestWithCorrectHeadersAndNoParams() {

        final Response response = whenRetrieveSessionsByIDIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForRetrieve(response);
    }

    private Response whenRetrieveSessionsByIDRequestIsInvokedForInvalidResource() {
        return retrieveSessionsResponseForInvalidResource(sessionsApiRootContext+"/CASE1234/get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsByIDIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveSessionsResponseForCorrectHeadersAndNoParams(sessionsApiRootContext+"/CASE1234", headersAsMap, targetInstance);
    }

    private Response whenRetrieveSessionsByIDRequestIsInvokedWithMissingOrInvalidOcpSubKey() {
        return retrieveSessionsResponseForMissingOrInvalidOcpSubKey(sessionsApiRootContext+"/CASE1234", headersAsMap,  paramsAsMap, targetInstance);
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

    private Response retrieveSessionsResponseForMissingOrInvalidOcpSubKey(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
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
