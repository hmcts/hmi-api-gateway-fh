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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.PubHubResponseVerifier.thenValidateResponseForPost;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("PUT /update-published-info - Update published info")
public class PUT_Published_Info_UnitTests {

    private static final String REQUESTS_PUT_PUBLISHED_INFO_PAYLOAD_JSON = "requests/update-published-info-payload.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${publishingRootContext}")
    private String hmiRootContext;

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
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");
    }

    @Test
    @Order(1)
    @DisplayName("Test for valid success status for Update Published info")
    void testUpdatePublishedInfoForHappyPath() throws IOException {
        final String input = givenAPayload(REQUESTS_PUT_PUBLISHED_INFO_PAYLOAD_JSON);
        final Response response = whenUpdatePublishedInfoIsInvoked(input);
        thenValidateResponseForPost(response);
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Header for Update Published info")
    void testUpdatePublishedInfoForInvalidHeader() throws IOException {
        headersAsMap.remove("Request-Created-At");
        headersAsMap.put("Request-Created-At", "invalid date");
        final String input = givenAPayload(REQUESTS_PUT_PUBLISHED_INFO_PAYLOAD_JSON);
        final Response response = whenUpdatePublishedInfoIsInvoked(input);
        thenValidateResponseForGetPeopleByIdWithInvalidHeader(response);
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid AccessToken for Update Published info")
    void testUpdatePublishedInfoForInvalidAccessToken() throws IOException {
        final String input = givenAPayload(REQUESTS_PUT_PUBLISHED_INFO_PAYLOAD_JSON);
        final Response response = whenUpdatePublishedInfoIsInvokedWithInvalidAcessToken(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private Response whenUpdatePublishedInfoIsInvoked(final String input) {
        return updatePublishedInfo(hmiRootContext + "/update-published-info", headersAsMap, targetInstance, input);
    }

    private Response whenUpdatePublishedInfoIsInvokedWithInvalidAcessToken(final String input) {
        return updatePublishedInfoWithInvalidAccessToken(hmiRootContext + "/update-published-info", headersAsMap, targetInstance, input);
    }

    private Response updatePublishedInfoWithInvalidAccessToken(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payload) {
        return given()
                .auth()
                .oauth2("accessToken")
                .body(payload)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response updatePublishedInfo(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payload) {
        return given()
                .auth()
                .oauth2(accessToken)
                .body(payload)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }

}
