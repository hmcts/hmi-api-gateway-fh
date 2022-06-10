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
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.*;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.sessionId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForRetrieve;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForRequestVideoHearingWithInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Retrieve Hearings groupid Endpoints")
@SuppressWarnings("java:S2699")
class GET_hearings_groupid_UnitTests {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${snlApiVersion}")
    private String snlApiVersion;

    @Value("${hearinggroupidApiRootContext}")
    private String hearinggroupidApiRootContext;

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
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");
        headersAsMap.put("Accept", "application/json");

    }

    @Test
    @Order(8)
    @DisplayName("Test for Correct Headers and No Parameters")
    void testRetrieveGroupidRequestWithCorrectRequestAndNoParams() {
        final Response response = whenRetrieveGroupidIsInvokedWithCorrectHeadersAndNoParams();
        SchedulesResponseVerifier.thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for Correct Headers and Version Number")
    void testRetrieveGroupidWithCorrectHeaders() {
        final Response response = whenRetrieveGroupidWithCorrectHeaders();
        thenValidateResponseForRetrieve(response);
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");

        assertEquals(responseMap.get("project.version"),sessionId);
    }

    private Response whenRetrieveGroupidWithCorrectHeaders() {
        return retrieveGroupidForInvalidResource(hearinggroupidApiRootContext , headersAsMap, targetInstance);
    }

    private Response whenRetrieveGroupidIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveGroupidResponseForCorrectRequestAndNoParams(hearinggroupidApiRootContext, headersAsMap, targetInstance);
    }

    private Response retrieveGroupidResponseForCorrectRequestAndNoParams(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }
    private Response retrieveGroupidForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

}
