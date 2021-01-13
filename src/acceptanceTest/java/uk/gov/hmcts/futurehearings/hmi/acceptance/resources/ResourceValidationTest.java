package uk.gov.hmcts.futurehearings.hmi.acceptance.resources;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithEmulatorValues;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.security.OAuthTokenGenerator.generateOAuthToken;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.test.HMICommonHeaderTest;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.CaseHQCommonErrorVerifier;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
public abstract class ResourceValidationTest extends HMICommonHeaderTest {

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
        this.setInputFileDirectory("resources");
        String authorizationToken = generateOAuthToken (token_apiURL,
                token_apiTenantId,
                grantType, clientID,
                clientSecret,
                scope,
                HttpStatus.OK);
        this.setAuthorizationToken(authorizationToken);
    }

    @ParameterizedTest(name = "Testing against the Emulator for Error Responses that come from the Case HQ System")
    @CsvSource(value = {"EMULATOR,400,1000,Invalid LOV Value","EMULATOR,400,1002,reference to a resource that doesn't exist","EMULATOR,400,1003,mandatory value missing","EMULATOR,400,1004,schema validation failure"}, nullValues = "NIL")
    void test_successful_response_from_the_emulator_stub(final String destinationSystem,
                                                         final String returnHttpCode,
                                                         final String returnErrorCode,
                                                         final String returnDescription) throws Exception {

        final HttpStatus httpStatus =
                returnHttpCode.equalsIgnoreCase("400") ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_ACCEPTABLE;
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), "put-user-as-resource-request-valid.json",
                createHeaderWithEmulatorValues(
                        destinationSystem,
                        returnHttpCode,
                        returnErrorCode,
                        returnDescription),
                null,
                getUrlParams(),
                getHttpMethod(),
                httpStatus,
                getInputFileDirectory(),
                new CaseHQCommonErrorVerifier(),
                returnDescription,
                null);
    }
}
