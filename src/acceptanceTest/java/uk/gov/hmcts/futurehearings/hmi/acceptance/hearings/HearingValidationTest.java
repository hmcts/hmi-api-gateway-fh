package uk.gov.hmcts.futurehearings.hmi.acceptance.hearings;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.test.HmiCommonHeaderTest;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.security.OAuthTokenGenerator.generateOAuthToken;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
abstract class HearingValidationTest extends HmiCommonHeaderTest {

    @Value("${targetInstance}")
    private String targetInstance;

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

    @BeforeAll
    public void initialiseValues() throws Exception {
        RestAssured.baseURI = targetInstance;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = RestAssured.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        this.setInputFileDirectory("hearings");
        String authorizationToken = generateOAuthToken(tokenApiUrl,
                tokenApiTenantId,
                grantType, clientID,
                clientSecret,
                scope,
                HttpStatus.OK);
        this.setAuthorizationToken(authorizationToken);
    }
}
