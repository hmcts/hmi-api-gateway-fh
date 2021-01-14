package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForRequestVideoHearingWithInvalidHeader;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("GET /resources/video-hearing - Request Video Hearing")
public class GET_VideoHearings_UnitTests {

    private final Map<String, Object> headersAsMap = new HashMap<>();
    private final Map<String, Object> queryParams = new HashMap<>();

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${resourcesApiRootContext}")
    private String resourcesApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

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
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
        headersAsMap.put("Request-Processed-At", "2018-02-29 20:36:01Z");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Valid Headers")
    void testRetrievePeopleForValidHeaders() {
        final Response response = whenRetrieveVideoHearingIsInvoked();
        thenValidateResponseForRetrieveVideoHearing(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for Valid Headers with query param")
    void testRetrievePeopleForValidHeadersWithQueryParam() {
        queryParams.put("username", "name");
        final Response response = whenRetrieveVideoHearingIsInvokedWithQueryParam();
        thenValidateResponseForRetrieveVideoHearing(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for Valid Headers with invalid query param")
    void testRetrievePeopleForValidHeadersWithInvalidQueryParam() {
        queryParams.clear();
        queryParams.put("any", "name");
        final Response response = whenRetrieveVideoHearingIsInvokedWithQueryParam();
        thenValidateResponseForRetrieveVideoHearingWithInvalidQueryParams(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for Valid Headers with Path Param")
    void testRetrievePeopleForValidHeadersWithPathParam() {
        final Response response = whenRetrieveVideoHearingIsInvokedWithPathParam();
        thenValidateResponseForRetrieveVideoHearingWithPathParam(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for Invalid Headers")
    void testRetrievePeopleForInvalidHeaders() {
        headersAsMap.put("Source-System", "");
        final Response response = whenRetrieveVideoHearingIsInvoked();
        thenValidateResponseForRequestVideoHearingWithInvalidHeader(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for Invalid Token")
    void testRetrievePeopleForMissingToken() {
        final Response response = whenRequestVideoHearingIsInvokedForWithInvalidToken();
        thenValidateResponseForRequestVideoHearingWithInvalidToken(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for Invalid Date")
    void testRetrievePeopleForInvalidDate() {
        final Response response = whenRequestVideoHearingIsInvokedForWithInvalidDate();
        thenValidateResponseForRequestVideoHearingWithInvalidHeader(response);
    }

    @Test
    @Order(8)
    @DisplayName("Test for Invalid MediaType")
    void testRetrievePeopleForInvalidMediaType() {
        final Response response = whenRequestVideoHearingIsInvokedForWithInvalidMedia();
        thenValidateResponseForRequestVideoHearingWithInvalidMedia(response);
    }

    @Test
    @Order(9)
    @DisplayName("Test for Invalid Content Type")
    void testRetrievePeopleForInvalidContentType() {
        final Response response = whenRequestVideoHearingIsInvokedForWithInvalidContentType();
        thenValidateResponseForRequestVideoHearingWithInvalidHeader(response);
    }

    private Response whenRequestVideoHearingIsInvokedForWithInvalidContentType() {
        headersAsMap.put("Content-Type", "InvalidMedia");
        return sendGetRequestForVideoHearing(resourcesApiRootContext + "/video-hearing", headersAsMap, targetInstance);
    }

    private Response whenRequestVideoHearingIsInvokedForWithInvalidMedia() {
        headersAsMap.put("Accept", "InvalidMedia");
        return sendGetRequestForVideoHearing(resourcesApiRootContext + "/video-hearing", headersAsMap, targetInstance);
    }

    private Response whenRequestVideoHearingIsInvokedForWithInvalidDate() {
        headersAsMap.put("Request-Created-At", "InvalidDate");
        return sendGetRequestForVideoHearing(resourcesApiRootContext + "/video-hearing", headersAsMap, targetInstance);
    }

    private Response whenRequestVideoHearingIsInvokedForWithInvalidToken() {
        return sendPostRequestForVideoHearingWithInvalidToken(resourcesApiRootContext + "/video-hearing", headersAsMap, targetInstance);
    }

    private Response whenRetrieveVideoHearingIsInvoked() {
        return sendGetRequestForVideoHearing(resourcesApiRootContext + "/video-hearing", headersAsMap, targetInstance);
    }

    private Response whenRetrieveVideoHearingIsInvokedWithQueryParam() {
        return sendGetRequestForVideoHearing(resourcesApiRootContext + "/video-hearing", headersAsMap, queryParams, targetInstance);
    }

    private Response whenRetrieveVideoHearingIsInvokedWithPathParam() {
        return sendGetRequestForVideoHearing(resourcesApiRootContext + "/video-hearing/hearingId", headersAsMap, targetInstance);
    }

    private Response sendGetRequestForVideoHearing(final String api, final Map<String, Object> headersAsMap, final String basePath) {
        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response sendGetRequestForVideoHearing(final String api, final Map<String, Object> headersAsMap, final Map<String, Object> queryParams, final String basePath) {
        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .queryParams(queryParams)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response sendPostRequestForVideoHearingWithInvalidToken(final String api, final Map<String, Object> headersAsMap, final String basePath) {
        return given()
                .auth()
                .oauth2("accessToken")
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

}
