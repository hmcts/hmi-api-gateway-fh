package uk.gov.hmcts.futurehearings.hmi.smoke.sessions;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import uk.gov.hmcts.futurehearings.hmi.Application;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.smoke.common.header.factory.HeaderFactory.createStandardHMIHeader;
import static uk.gov.hmcts.futurehearings.hmi.smoke.common.security.OAuthTokenGenerator.generateOAuthToken;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
@DisplayName("Smoke Test for the HMI Sessions Context")
@SuppressWarnings("java:S2187")
class SessionsApiSmokeTest {

    @Value("${sessionsApiRootContext}")
    private String sessionsApiRootContext;

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${token_apiURL}")
    private String token_apiURL;

    @Value("${token_apiTenantId}")
    private String token_apiTenantId;

    @Value("${grantType}")
    private String grantType;

    @Value("${clientID}")
    private String clientID;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${scope}")
    private String scope;

    private Map<String, String> headersAsMap = new HashMap<String,String>();

    private String authorizationToken;

    private String rootContext;

    @BeforeAll
    static void beforeAll(TestInfo info) {
        log.debug("Test execution Class Initiated: " + info.getTestClass().get().getName());
    }

    @BeforeEach
    public void beforeEach(TestInfo info) {
        log.debug("Before execution : " + info.getTestMethod().get().getName());
    }

    @AfterEach
    public void afterEach(TestInfo info) {
        log.debug("After execution : " + info.getTestMethod().get().getName());
    }

    @AfterAll
    static void afterAll(TestInfo info) {
        log.debug("Test execution Class Completed: " + info.getTestClass().get().getName());
    }

    @Test
    @DisplayName("Smoke Test to Test the Endpoint for the HMI Root Context")
    void testSessionsHmiApiGet() throws Exception{
        RestAssured.baseURI = targetInstance;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = RestAssured.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));

        this.authorizationToken = generateOAuthToken(token_apiURL,
                token_apiTenantId,
                grantType, clientID,
                clientSecret,
                scope,
                HttpStatus.OK);

        headersAsMap = createStandardHMIHeader("SNL");
        rootContext = sessionsApiRootContext;

        Response response = null;
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("requestSessionType", "ADHOC");

            log.debug("Query params :" + queryParams);
            response = given()
                    .queryParams(queryParams)
                    .headers(headersAsMap)
                    .auth().oauth2(authorizationToken)
                    .basePath(rootContext)
                    .when().get();

        if (response.getStatusCode() != 400) {
            log.debug(" The value of the Response Status " + response.getStatusCode());
            log.debug(" The value of the Response body " + response.getBody().prettyPrint());
        }
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }
}