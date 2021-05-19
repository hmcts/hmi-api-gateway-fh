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
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.PubHubResponseVerifier.thenValidateResponseForDelete;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("DELETE /delete-published-info - Delete published info")
public class DELETE_Published_Info_UnitTests {

    private static final String REQUESTS_DELETE_PUBLISHED_INFO_PAYLOAD_JSON = "requests/delete-published-info-payload.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${hmiApiRootContext}")
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
    @DisplayName("Test for valid success status for Delete Published info")
    void testDeletePublishedInfoForHappyPath() throws IOException {
        final String input = givenAPayload(REQUESTS_DELETE_PUBLISHED_INFO_PAYLOAD_JSON);
        final Response response = whenDeletePublishedInfoIsInvoked(input);
        thenValidateResponseForDelete(response);
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Header for Delete Published info")
    void testDeletePublishedInfoForInvalidHeader() throws IOException {
        headersAsMap.remove("Request-Created-At");
        headersAsMap.put("Request-Created-At", "invalid date");
        final String input = givenAPayload(REQUESTS_DELETE_PUBLISHED_INFO_PAYLOAD_JSON);
        final Response response = whenDeletePublishedInfoIsInvoked(input);
        thenValidateResponseForGetPeopleByIdWithInvalidHeader(response);
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid AccessToken for Delete Published info")
    void testDeletePublishedInfoForInvalidAccessToken() throws IOException {
        final String input = givenAPayload(REQUESTS_DELETE_PUBLISHED_INFO_PAYLOAD_JSON);
        final Response response = whenDeletePublishedInfoIsInvokedWithInvalidAcessToken(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private Response whenDeletePublishedInfoIsInvoked(final String payload) {
        return deletePublishedInfo(hmiRootContext + "delete-published-info", headersAsMap, targetInstance, payload);
    }

    private Response whenDeletePublishedInfoIsInvokedWithInvalidAcessToken(final String payload) {
        return deletePublishedInfoWithInvalidAccessToken(hmiRootContext + "delete-published-info", headersAsMap, targetInstance, payload);
    }

    private Response deletePublishedInfoWithInvalidAccessToken(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payload) {
        return given()
                .auth()
                .oauth2("accessToken")
                .body(payload)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }

    private Response deletePublishedInfo(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payload) {
        return given()
                .auth()
                .oauth2(accessToken)
                .body(payload)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }

}
