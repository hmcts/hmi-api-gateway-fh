package uk.gov.hmcts.futurehearings.snl.acceptance.security;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.futurehearings.snl.acceptance.common.security.OAuthTokenGenerator.generateOAuthToken;

import uk.gov.hmcts.futurehearings.hmi.Application;

import java.util.Objects;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("snlacceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Test to Check the JWT Token Generator for OAuth, Note : In case of tests breaking - The client secret may change once a year")
@SuppressWarnings({"java:S2187", "java:S5786", "java:S2699"})
class OAuthTokenGeneratorTest {

    @Value("${token_apiURL}")
    private String token_apiURL;

    @Value("${token_username}")
    private String token_username;

    @Value("${token_password}")
    private String token_password;

    @Value("${token_password}")
    private String token_password;
    expiredAccessToken

    @BeforeAll
    public void initialiseValues() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = RestAssured.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
    }

    @Test
    @DisplayName("Successfully validated response with the right response String")
    void test_get_token_successfully() throws Exception {
        String token = generateOAuthToken(token_apiURL,
                token_username,
                token_password,
                HttpStatus.OK);
        assertTrue(Objects.nonNull(token));
        assertTrue(token.length() > 1);
    }


    @ParameterizedTest(name = "Invalid UserName - Param : {0}")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "api-sit-casehq@mcmshmcts.onmicrosoft.com"})
    void test_get_token_with_negative_username(final String username) throws Exception {
        generateOAuthToken(token_apiURL,
                username,
                token_password,
                HttpStatus.BAD_REQUEST);
    }

    @ParameterizedTest(name = "Invalid Password - Param : {0}")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "JTuREp6m4W9KyjbzFpnUfanh"})
    void test_get_token_with_negative_password(final String password) throws Exception {
        generateOAuthToken(token_apiURL,
                token_username,
                password,
                HttpStatus.BAD_REQUEST);
    }


    @ParameterizedTest(name = "Invalid Password - Param : {0}")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "https://hmcts-uat.casehq.net/hmi_oauth1/resources/hmi/token"})
    void test_get_token_with_negative_token_url(final String tokenURL) throws Exception {
        generateOAuthToken(token_apiURL,
                token_username,
                tokenURL,
                HttpStatus.BAD_REQUEST);
    }

    /*@Test
    @Order(3)
    @DisplayName("Test for Invalid OAuth Token")
    void testSecureRequestForInvalidOAuthToken() {

        Response response = given()
                .auth()
                .oauth2("accessToken")
                .header("Ocp-Apim-Subscription-Key", targetSubscriptionKey)
                .baseUri(targetInstance)
                .basePath(secureApiRootContext)
                .when()
                .get();

        validateInvalidOauthResponse(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for expired OAuth Token")
    void testSecureRequestForExpiredOAuthToken() {

        Response response = given()
                .auth()
                .oauth2(expiredAccessToken)
                .header("Ocp-Apim-Subscription-Key", targetSubscriptionKey)
                .baseUri(targetInstance)
                .basePath(secureApiRootContext)
                .when()
                .get();

        validateInvalidOauthResponse(response);
    }
*/

}
