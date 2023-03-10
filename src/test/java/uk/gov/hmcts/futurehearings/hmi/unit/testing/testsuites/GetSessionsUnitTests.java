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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForNoMandatoryParams;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.SessionsResponseVerifier.thenValidateResponseForRetrieve;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("GET /sessions - Retrieve Sessions")
@SuppressWarnings({"java:S2699", "PMD.TooManyMethods", "PMD.TooManyFields"})
class GetSessionsUnitTests {
    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${sessionsApiRootContext}")
    private String sessionsApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

    private final Map<String, Object> headersAsMap = new ConcurrentHashMap<>();
    private final Map<String, String> paramsAsMap = new ConcurrentHashMap<>();
    private static final String BODY_PAYLOAD = "";

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
    private static final String SOURCE_SYSTEM = "Source-System";
    private static final String DESTINATION_SYSTEM = "Destination-System";
    private static final String REQUEST_CREATED_AT = "Request-Created-At";

    @BeforeAll
    void setToken() {
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenUrl, scope);
        this.httpClient = new HmiHttpClient(accessToken, targetInstance);
    }

    @BeforeEach
    void initialiseValues() {

        headersAsMap.put(CONTENT_TYPE, "application/json");
        headersAsMap.put(ACCEPT, "application/json");
        headersAsMap.put(SOURCE_SYSTEM, "CFT");
        headersAsMap.put(DESTINATION_SYSTEM, destinationSystem);
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put(REQUEST_CREATED_AT, "2018-01-29T20:36:01Z");

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
        headersAsMap.remove(CONTENT_TYPE);

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testRetrieveSessionsRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);
        headersAsMap.put(CONTENT_TYPE, "application/xml");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testRetrieveSessionsRequestWithMissingAcceptHeader() {
        headersAsMap.remove(ACCEPT);

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testRetrieveSessionsRequestWithInvalidAcceptHeader() {
        headersAsMap.remove(ACCEPT);
        headersAsMap.put(ACCEPT, "application/jsonxml");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Order(6)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {SOURCE_SYSTEM, DESTINATION_SYSTEM, REQUEST_CREATED_AT})
    void testRetrieveSessionsRequestWithMissingHeader(String iteration) {
        headersAsMap.remove(iteration);

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(7)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {SOURCE_SYSTEM, DESTINATION_SYSTEM, REQUEST_CREATED_AT})
    void testRetrieveSessionsRequestWithInvalidHeader(String iteration) {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");

        final Response response = whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }


    @Order(9)
    @ParameterizedTest(name = "Test for mandatory parameter - {0}")
    @ValueSource(strings = {"requestSessionType"})
    void testRetrieveSessionsRequestWithDateParams(String iteration) {
        paramsAsMap.clear();
        paramsAsMap.put(iteration, "ADHOC");
        final Response response = whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseForNoMandatoryParams(response);
    }

    @Test
    @Order(10)
    @DisplayName("Test for missing Access Token")
    void testRetrieveSessionsRequestWithMissingAccessToken() {
        final Response response = whenRetrieveSessionsIsInvokedWithMissingAccessToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(11)
    @DisplayName("Test for invalid Access Token")
    void testRetrieveSessionsRequestWithInvalidAccessToken() {
        accessToken = TestUtilities.getToken(grantType, invalidClientID, invalidClientSecret,
                invalidTokenUrl, invalidScope);
        httpClient.setAccessToken(accessToken);
        final Response response = httpClient.httpGet(sessionsApiRootContext, headersAsMap, paramsAsMap, BODY_PAYLOAD);
        thenValidateResponseForMissingOrInvalidAccessToken(response);

        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenUrl, scope);
        httpClient.setAccessToken(accessToken);
    }

    private Response whenRetrieveSessionsRequestIsInvokedForInvalidResource() {
        return httpClient.httpGet(sessionsApiRootContext + "get", headersAsMap, paramsAsMap, BODY_PAYLOAD);
    }

    private Response whenRetrieveSessionsIsInvokedWithCorrectHeadersAndParams() {
        paramsAsMap.clear();
        paramsAsMap.put("requestSessionType", "any");
        return httpClient.httpGet(sessionsApiRootContext, headersAsMap, paramsAsMap, BODY_PAYLOAD);
    }

    private Response whenRetrieveSessionsIsInvokedWithMissingAccessToken() {
        return httpClient.httpGetNoAuth(sessionsApiRootContext, headersAsMap, paramsAsMap, BODY_PAYLOAD);
    }

    private Response whenRetrieveSessionsRequestIsInvokedWithMissingOrInvalidHeader() {
        return httpClient.httpGet(sessionsApiRootContext, headersAsMap, paramsAsMap, BODY_PAYLOAD);
    }

    @Test
    @Order(19)
    @DisplayName("Test for Invalid Resource - By ID")
    void testRetrieveSessionsByIdRequestForInvalidResource() {

        final Response response = httpClient.httpGet(sessionsApiRootContext + "/CASE1234/get",
                headersAsMap, paramsAsMap, BODY_PAYLOAD);
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(20)
    @DisplayName("Test for missing ContentType header - By ID")
    void testRetrieveSessionsByIdRequestWithMissingContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);

        final Response response = retrieveSessionById();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(21)
    @DisplayName("Test for invalid ContentType header - By ID")
    void testRetrieveSessionsByIdRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);
        headersAsMap.put(CONTENT_TYPE, "application/xml");

        final Response response = retrieveSessionById();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(22)
    @DisplayName("Test for missing Accept header - By ID")
    void testRetrieveSessionsByIdRequestWithMissingAcceptHeader() {
        headersAsMap.remove(ACCEPT);

        final Response response = retrieveSessionById();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(23)
    @DisplayName("Test for invalid Accept header - By ID")
    void testRetrieveSessionsByIdRequestWithInvalidAcceptHeader() {
        headersAsMap.remove(ACCEPT);
        headersAsMap.put(ACCEPT, "application/jsonxml");

        final Response response = retrieveSessionById();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(24)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header - By ID")
    void testRetrieveSessionsByIdRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");

        final Response response = retrieveSessionById();
        thenValidateResponseForRetrieve(response);
    }

    @Order(26)
    @ParameterizedTest(name = "Test for missing {0} header - By ID")
    @ValueSource(strings = {SOURCE_SYSTEM, DESTINATION_SYSTEM, REQUEST_CREATED_AT})
    void testRetrieveSessionsByIdRequestWithMissingHeader(String iteration) {
        headersAsMap.remove(iteration);

        final Response response = retrieveSessionById();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(27)
    @ParameterizedTest(name = "Test for invalid {0} header - By ID")
    @ValueSource(strings = {SOURCE_SYSTEM, DESTINATION_SYSTEM, REQUEST_CREATED_AT})
    void testRetrieveSessionsByIdRequestWithInvalidHeader(String iteration) {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");

        final Response response = retrieveSessionById();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(28)
    @DisplayName("Test for Correct Headers with No Parameters - By ID")
    void testRetrieveSessionsByIdRequestWithCorrectHeadersAndNoParams() {

        final Response response = retrieveSessionById();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(29)
    @DisplayName("Test for missing Access Token")
    void testRetrieveSessionsByIdRequestWithMissingAccessToken() {
        final Response response = retrieveSessionByIdNoAuth();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(30)
    @DisplayName("Test for invalid Access Token")
    void testRetrieveSessionsByIdRequestWithInvalidAccessToken() {
        accessToken = TestUtilities.getToken(grantType, invalidClientID, invalidClientSecret,
                invalidTokenUrl, invalidScope);
        httpClient.setAccessToken(accessToken);
        final Response response = retrieveSessionById();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private Response retrieveSessionById() {
        return httpClient.httpGet(sessionsApiRootContext + "/CASE1234",
                headersAsMap, paramsAsMap, BODY_PAYLOAD);
    }

    private Response retrieveSessionByIdNoAuth() {
        return httpClient.httpGetNoAuth(sessionsApiRootContext + "/CASE1234",
                headersAsMap, paramsAsMap, BODY_PAYLOAD);
    }
}
