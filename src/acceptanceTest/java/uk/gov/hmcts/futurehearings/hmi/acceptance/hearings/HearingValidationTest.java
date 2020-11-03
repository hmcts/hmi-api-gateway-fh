package uk.gov.hmcts.futurehearings.hmi.acceptance.hearings;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.TestingUtils.readFileContents;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.mock.CommonStubFactory.resetMocks;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.mock.CommonStubFactory.uploadCommonMocks;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.security.OAuthTokenGenerator.generateOAuthToken;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.test.HMICommonHeaderTest;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
public abstract class HearingValidationTest extends HMICommonHeaderTest {

    private static final String COMMON_MOCK_PATH = "uk/gov/hmcts/futurehearings/hmi/acceptance/common/mock";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${mockServerHost}")
    private String mockServerHost;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

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
        this.setApiSubscriptionKey(targetSubscriptionKey);
        RestAssured.config = RestAssured.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        this.setInputFileDirectory("hearings");
        String authorizationToken = generateOAuthToken (token_apiURL,
                token_apiTenantId,
                grantType, clientID,
                clientSecret,
                scope,
                HttpStatus.OK);
        this.setAuthorizationToken(authorizationToken);

       /* RestAssured.baseURI = mockServerHost;
        log.debug("The value of the base URI" + RestAssured.baseURI);

        resetMocks("/__admin/mappings/reset");
        uploadCommonMocks("/__admin/mappings/import",
                readFileContents(COMMON_MOCK_PATH+"/common-mock-responses.json"));
        RestAssured.baseURI = targetInstance;*/
    }
}
