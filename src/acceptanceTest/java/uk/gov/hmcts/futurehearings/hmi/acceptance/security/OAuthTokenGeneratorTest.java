package uk.gov.hmcts.futurehearings.hmi.acceptance.security;

import static io.restassured.RestAssured.expect;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.security.OAuthTokenGenerator.callTokenGeneratorEndpoint;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.security.OAuthTokenGenerator.generateOAuthToken;

import uk.gov.hmcts.futurehearings.hmi.Application;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Test to Check the JWT Token Generator for OAuth, Note : In case of tests breaking - The client secret may change once a year")
@SuppressWarnings({"java:S2187","java:S5786","java:S2699"})
class OAuthTokenGeneratorTest {

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

    @BeforeAll
    public void initialiseValues() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = RestAssured.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
    }

    @Test
    @DisplayName("Successfully validated response with an xml payload")
    void test_get_token_successfully() throws Exception {
        generateOAuthToken(token_apiURL,
                token_apiTenantId,
                grantType,clientID,
                clientSecret,
                scope,
                HttpStatus.OK);
    }

    @ParameterizedTest(name = "TenantId negative scenarios - Param : {0}")
    @NullSource
    @ValueSource(strings = {"trial_value", "9912f05e-21f6-4a6a-9ca1-db101306db45"})
    void test_get_token_with_negative_tenant_scenarios(final String tenantId) throws Exception {
        final HttpStatus httpStatus = tenantId != null && tenantId.trim().equals("") ?
                HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        generateOAuthToken(token_apiURL,
                tenantId,
                grantType,clientID,
                clientSecret,
                scope,
                httpStatus);
    }

    @ParameterizedTest(name = "Invalid grant Type - Param : {0}")
    @NullAndEmptySource
    @ValueSource(strings = {"", "authorization_code", "9912f05e-21f6-4a6a-9ca1-db101306db45"})
    void test_get_token_with_negative_grant_type(final String grantType) throws Exception {

        generateOAuthToken(token_apiURL,
                token_apiTenantId,
                grantType,clientID,
                clientSecret,
                scope,
                HttpStatus.BAD_REQUEST);
    }

    @ParameterizedTest(name = "Invalid client id - Param : {0}")
    @NullAndEmptySource
    @ValueSource(strings = {"", "test_id", "9912f05e-21f6-4a6a-9ca1-db101306db45"})
    void test_get_token_with_negative_client_id(final String clientID) throws Exception {

        generateOAuthToken(token_apiURL,
                token_apiTenantId,
                grantType,clientID,
                clientSecret,
                scope,
                HttpStatus.BAD_REQUEST);
    }

    @ParameterizedTest(name = "Invalid client secret - Param : {0}")
    @NullAndEmptySource
    @ValueSource(strings = {"", "test_id", "9912f05e-21f6-4a6a-9ca1-db101306db45"})
    void test_get_token_with_negative_client_secret(final String clientSecret) throws Exception {
        generateOAuthToken(token_apiURL,
                token_apiTenantId,
                grantType,clientID,
                clientSecret,
                scope,
                HttpStatus.UNAUTHORIZED);
    }

    @ParameterizedTest(name = "Invalid client secret - Param : {0}")
    @NullAndEmptySource
    @ValueSource(strings = {"", "test_id", "api://be6f8454-a584-41f7-bd74-ea6c4032c3a4/.default"})
    void test_get_token_with_negative_scope(final String scope) throws Exception {
        generateOAuthToken(token_apiURL,
                token_apiTenantId,
                grantType,clientID,
                clientSecret,
                scope,
                HttpStatus.BAD_REQUEST);
    }
}
