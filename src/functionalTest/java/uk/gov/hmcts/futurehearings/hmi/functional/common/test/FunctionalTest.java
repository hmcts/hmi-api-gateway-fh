package uk.gov.hmcts.futurehearings.hmi.functional.common.test;

import io.restassured.RestAssured;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.config.SSLConfig.sslConfig;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHmiHeader;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.security.OAuthTokenGenerator.generateOAuthToken;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("PMD")
public class FunctionalTest {

    @Value("${targetInstance}")
    protected String targetInstance;

    @Value("${hearingApiRootContext}")
    protected String hearingApiRootContext;

    @Value("${token_apiURL}")
    protected String tokenApiUrl;

    @Value("${token_apiTenantId}")
    protected String tokenApiTenantId;

    @Value("${grantType}")
    protected String grantType;

    @Value("${clientID}")
    protected String clientID;

    @Value("${clientSecret}")
    protected String clientSecret;

    @Value("${scope}")
    protected String scope;

    protected Map<String, Object> headersAsMap = new ConcurrentHashMap<>();

    protected String authorizationToken;

    public void initialiseValues() throws Exception {
        RestAssured.config = //NOSONAR
                RestAssured.config()
                        .sslConfig(sslConfig().relaxedHTTPSValidation())
                        .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        RestAssured.baseURI = targetInstance; //NOSONAR

        this.authorizationToken = generateOAuthToken(tokenApiUrl,
                tokenApiTenantId,
                grantType, clientID,
                clientSecret,
                scope,
                HttpStatus.OK);
        this.setAuthorizationToken(authorizationToken);
        headersAsMap = createStandardHmiHeader("SNL");
    }
}
