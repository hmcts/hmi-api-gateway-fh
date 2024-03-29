package uk.gov.hmcts.futurehearings.hmi.acceptance.security;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.security.OAuthTokenGenerator.generateOAuthToken;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Test to Check the JWT Token Generator for OAuth, Note :"
        + " In case of tests breaking - The client secret may change once a year")
@SuppressWarnings({"java:S2187", "java:S5786", "java:S2699"})
class OAuthTokenGeneratorTest {

    @Value("${token_apiURL}")
    private String tokenApiUrl;

    @Value("${token_apiTenantId}")
    private String tokenApiTenantId;

    @Value("${grantType}")
    private String grantType;

    @Value("${clientID}")
    private String clientID;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${scope}")
    private String scope;

    private static final String TRIAL_VALUE = "9912f05e-21f6-4a6a-9ca1-db101306db45";

    @BeforeAll
    public void initialiseValues() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = RestAssured.config() //NOSONAR
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
    }

    @Test
    @DisplayName("Successfully validated response with an xml payload")
    void testGetTokenSuccessfully() throws Exception {
        generateOAuthToken(tokenApiUrl,
                tokenApiTenantId,
                grantType, clientID,
                clientSecret,
                scope,
                HttpStatus.OK);
    }

    @ParameterizedTest(name = "TenantId negative scenarios - Param : {0}")
    @NullSource
    @ValueSource(strings = {"trial_value", })
    void testGetTokenWithNegativeTenantScenarios(final String tenantId) throws Exception {
        final HttpStatus httpStatus = tenantId != null && "".equals(tenantId.trim())
                ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        generateOAuthToken(tokenApiUrl,
                tenantId,
                grantType, clientID,
                clientSecret,
                scope,
                httpStatus);
    }

    @ParameterizedTest(name = "Invalid grant Type - Param : {0}")
    @NullAndEmptySource
    @ValueSource(strings = {"", "authorization_code", TRIAL_VALUE})
    void testGetTokenWithNegativeGrantType(final String grantType) throws Exception {

        generateOAuthToken(tokenApiUrl,
                tokenApiTenantId,
                grantType, clientID,
                clientSecret,
                scope,
                HttpStatus.BAD_REQUEST);
    }

    @ParameterizedTest(name = "Invalid client id - Param : {0}")
    @NullAndEmptySource
    @ValueSource(strings = {"", "test_id", TRIAL_VALUE})
    void testGetTokenWithNegativeClientId(final String clientID) throws Exception {

        generateOAuthToken(tokenApiUrl,
                tokenApiTenantId,
                grantType, clientID,
                clientSecret,
                scope,
                HttpStatus.BAD_REQUEST);
    }

    @ParameterizedTest(name = "Invalid client secret - Param : {0}")
    @NullAndEmptySource
    @ValueSource(strings = {"", "test_id", TRIAL_VALUE})
    void testGetTokenWithNegativeClientSecret(final String clientSecret) throws Exception {
        generateOAuthToken(tokenApiUrl,
                tokenApiTenantId,
                grantType, clientID,
                clientSecret,
                scope,
                HttpStatus.UNAUTHORIZED);
    }

    @ParameterizedTest(name = "Invalid client secret - Param : {0}")
    @NullAndEmptySource
    @ValueSource(strings = {"", "test_id", "api://be6f8454-a584-41f7-bd74-ea6c4032c3a4/.default"})
    void testGetTokenWithNegativeScope(final String scope) throws Exception {
        generateOAuthToken(tokenApiUrl,
                tokenApiTenantId,
                grantType, clientID,
                clientSecret,
                scope,
                HttpStatus.BAD_REQUEST);
    }
}
