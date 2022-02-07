package uk.gov.hmcts.futurehearings.hmi.acceptance.publication;

import io.restassured.RestAssured;
        import lombok.extern.slf4j.Slf4j;
        import org.junit.jupiter.api.BeforeAll;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.http.HttpStatus;
        import org.springframework.test.context.ActiveProfiles;
        import uk.gov.hmcts.futurehearings.hmi.Application;
        import uk.gov.hmcts.futurehearings.hmi.acceptance.common.test.HMICommonHeaderTest;

        import static io.restassured.config.EncoderConfig.encoderConfig;
        import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.security.OAuthTokenGenerator.generateOAuthToken;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
public abstract class PublicationValidationTest extends HMICommonHeaderTest {
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

    @BeforeAll
    public void initialiseValues() throws Exception {
        RestAssured.baseURI = targetInstance;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = RestAssured.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        this.setInputFileDirectory("publication");
        String authorizationToken = generateOAuthToken (token_apiURL,
                token_apiTenantId,
                grantType,
                clientID,
                clientSecret,
                scope,
                HttpStatus.OK);
        this.setAuthorizationToken(authorizationToken);
    }
}
