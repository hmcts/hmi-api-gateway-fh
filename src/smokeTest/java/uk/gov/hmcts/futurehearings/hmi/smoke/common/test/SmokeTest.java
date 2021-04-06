package uk.gov.hmcts.futurehearings.hmi.smoke.common.test;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static uk.gov.hmcts.futurehearings.hmi.smoke.common.header.factory.HeaderFactory.createStandardHMIHeader;
import static uk.gov.hmcts.futurehearings.hmi.smoke.common.security.OAuthTokenGenerator.generateOAuthToken;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import uk.gov.hmcts.futurehearings.hmi.Application;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("java:S5786")
public abstract class SmokeTest {

    @Value("${targetInstance}")
    protected String targetInstance;

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

    protected Map<String, String> headersAsMap = new HashMap<String,String>();

    protected String authorizationToken;

    protected String rootContext;

    protected Map<String, String> params;

    protected String destinationSystem = "MOCK";

    @BeforeAll
    public void beforeAll(TestInfo info) {
        log.debug("Test execution Class Initiated: " + info.getTestClass().get().getName());
    }

    @BeforeAll
    public void initialiseValues() throws Exception {
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
        this.setAuthorizationToken(authorizationToken);

        headersAsMap = createStandardHMIHeader(destinationSystem);
    }
}
