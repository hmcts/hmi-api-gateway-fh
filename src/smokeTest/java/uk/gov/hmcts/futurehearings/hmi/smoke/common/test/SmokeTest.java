package uk.gov.hmcts.futurehearings.hmi.smoke.common.test;

import io.restassured.RestAssured;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static uk.gov.hmcts.futurehearings.hmi.smoke.common.header.factory.HeaderFactory.createStandardHmiHeader;
import static uk.gov.hmcts.futurehearings.hmi.smoke.common.security.OAuthTokenGenerator.generateOAuthToken;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings({"java:S5786", "PMD"})
public class SmokeTest {

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

    private Map<String, String> headersAsMap = new ConcurrentHashMap<>();

    private String authorizationToken;

    private String rootContext;

    private String destinationSystem = "MOCK";

    @BeforeAll
    public void beforeAll(TestInfo info) {
        info.getTestClass().ifPresent(value ->
            log.debug("Test execution Class Initiated: " + value.getName())
        );
    }

    @BeforeAll
    public void initialiseValues() throws Exception {
        RestAssured.baseURI = targetInstance; //NOSONAR
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = RestAssured.config() //NOSONAR
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));

        this.authorizationToken = generateOAuthToken(tokenApiUrl,
                tokenApiTenantId,
                grantType, clientID,
                clientSecret,
                scope,
                HttpStatus.OK);
        this.setAuthorizationToken(authorizationToken);

        headersAsMap = createStandardHmiHeader(destinationSystem);
    }

    @BeforeAll
    public void initialiseValuesDefault() {
        RestAssured.baseURI = targetInstance; //NOSONAR
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = RestAssured.config() //NOSONAR
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
    }
}
