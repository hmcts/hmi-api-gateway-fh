package uk.gov.hmcts.futurehearings.hmi.functional.common.test;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.security.OAuthTokenGenerator.generateOAuthToken;

import uk.gov.hmcts.futurehearings.hmi.Application;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Before;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class FunctionalTest {

    @Value("${targetInstance}")
    protected String targetInstance;

    @Value("${targetSubscriptionKey}")
    protected String targetSubscriptionKey;

    @Value("${hearingApiRootContext}")
    protected String hearingApiRootContext;

    @Value("${token_apiURL}")
    protected String token_apiURL;

    @Value("${token_apiTenantId}")
    protected String token_apiTenantId;

    @Value("${grantType}")
    protected String grantType;

    @Value("${clientID}")
    protected String clientID;

    @Value("${clientSecret}")
    protected String clientSecret;

    @Value("${scope}")
    protected String scope;

    protected Map<String, Object> headersAsMap = new HashMap<>();

    protected String authorizationToken;

    @Before
    public void initialiseValues() throws Exception {

        RestAssured.config =
                SerenityRest.config()
                        .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        RestAssured.baseURI = targetInstance;
        SerenityRest.useRelaxedHTTPSValidation();

        this.authorizationToken = generateOAuthToken (token_apiURL,
                token_apiTenantId,
                grantType, clientID,
                clientSecret,
                scope,
                HttpStatus.OK);
        this.setAuthorizationToken(authorizationToken);

        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", "S&L");
        headersAsMap.put("Request-Created-At", "2002-10-02T15:00:00Z");
        headersAsMap.put("Request-Processed-At", "2002-10-02 15:00:00Z");
        headersAsMap.put("Request-Type", "ASSAULT");



    }
}
