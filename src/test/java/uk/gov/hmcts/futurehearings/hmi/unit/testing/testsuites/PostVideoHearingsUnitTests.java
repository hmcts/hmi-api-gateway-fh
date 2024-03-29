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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HmiHttpClient;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForRequestVideoHearing;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForRequestVideoHearingWithInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForRequestVideoHearingWithInvalidMedia;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForRequestVideoHearingWithInvalidToken;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("POST /resources/video-hearing - Request Video Hearing")
@SuppressWarnings({"PMD.TooManyMethods"})
class PostVideoHearingsUnitTests {

    private final Map<String, Object> headersAsMap = new ConcurrentHashMap<>();
    private final Map<String, String> paramsAsMap = new ConcurrentHashMap<>();

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${resourcesApiRootContext}")
    private String resourcesApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

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

    private HmiHttpClient httpClient;

    @BeforeAll
    void setToken() {
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenUrl, scope);
        this.httpClient = new HmiHttpClient(accessToken, targetInstance);
    }

    @BeforeEach
    void initialiseValues() {
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Valid Headers")
    void testRetrievePeopleForValidHeaders() {
        final Response response = requestVideoHearing();
        thenValidateResponseForRequestVideoHearing(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for Invalid Headers")
    void testRetrievePeopleForInvalidHeaders() {
        headersAsMap.put("Source-System", "");
        final Response response = requestVideoHearing();
        thenValidateResponseForRequestVideoHearingWithInvalidHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for Invalid Token")
    void testRetrievePeopleForMissingToken() {
        final Response response = requestVideoHearingNoAuth();
        thenValidateResponseForRequestVideoHearingWithInvalidToken(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for Invalid Date")
    void testRetrievePeopleForInvalidDate() {
        headersAsMap.put("Request-Created-At", "InvalidDate");
        final Response response = requestVideoHearing();
        thenValidateResponseForRequestVideoHearingWithInvalidHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for Invalid MediaType")
    void testRetrievePeopleForInvalidMediaType() {
        headersAsMap.put("Accept", "InvalidMedia");
        final Response response = requestVideoHearing();
        thenValidateResponseForRequestVideoHearingWithInvalidMedia(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for Invalid Content Type")
    void testRetrievePeopleForInvalidContentType() {
        headersAsMap.put("Content-Type", "InvalidMedia");
        final Response response = requestVideoHearingNoPayload();
        thenValidateResponseForRequestVideoHearingWithInvalidHeader(response);
    }

    private Response requestVideoHearingNoPayload() {
        return sendPostRequestForVideoHearing(resourcesApiRootContext + "/video-hearing", headersAsMap, targetInstance);
    }

    private Response requestVideoHearingNoAuth() {
        return httpClient.httpPostNoAuth(resourcesApiRootContext + "/video-hearing", headersAsMap, paramsAsMap, "");
    }

    private Response requestVideoHearing() {
        return httpClient.httpPost(resourcesApiRootContext + "/video-hearing", headersAsMap, paramsAsMap, "");
    }

    private Response sendPostRequestForVideoHearing(final String api,
        final Map<String, Object> headersAsMap, final String basePath) {
        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }
}
