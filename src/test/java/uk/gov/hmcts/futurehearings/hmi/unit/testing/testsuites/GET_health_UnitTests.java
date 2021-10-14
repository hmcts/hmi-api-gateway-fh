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
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.*;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Retrieve Health Endpoints")
@SuppressWarnings("java:S2699")
class GET_health_UnitTests {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${snlApiVersion}")
    private String snlApiVersion;

    @Value("${hmiApiRootContext}")
    private String hmiApiRootContext;

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

        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");
        headersAsMap.put("Accept", "application/json");

    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testRetrieveHearingSchedulesRequestForInvalidResource() {
        final Response response = whenRetrieveHearingScheduleIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for Correct Headers and No Parameters")
    void testRetrieveHearingSchedulesRequestWithCorrectRequestAndNoParams() {
        final Response response = whenRetrieveHearingScheduleIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForRetrieve(response);
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");

        assertEquals(responseMap.get("project.version"), snlApiVersion);
    }

    private Response whenRetrieveHearingScheduleIsInvokedForInvalidResource() {
        return retrieveHearingSchedulesResponseForInvalidResource(hmiApiRootContext +"get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveHearingScheduleIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveHearingSchedulesResponseForCorrectRequestAndNoParams(hmiApiRootContext + "snl-health", headersAsMap, targetInstance);
    }

    private Response retrieveHearingSchedulesResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath) {

         return given()
                 .auth()
                 .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveHearingSchedulesResponseForCorrectRequestAndNoParams(final String api, final Map<String, Object> headersAsMap, final String basePath) {

          return given()
                  .auth()
                  .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

}
