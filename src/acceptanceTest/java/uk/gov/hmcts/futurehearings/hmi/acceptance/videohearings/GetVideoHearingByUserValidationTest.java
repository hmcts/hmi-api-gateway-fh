package uk.gov.hmcts.futurehearings.hmi.acceptance.videohearings;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper.buildQueryParams;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("java:S2187")
class GetVideoHearingByUserValidationTest extends VideoHearingValidationTest {

    @Value("${videohearingsRootContext}")
    private String videohearingsRootContext;

    private static final String SUCCESS_MSG = "The request was received successfully.";
    private static final String ERROR_MSG = "Invalid query params.";

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeUrl(videohearingsRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSuccessStatus(HttpStatus.OK);
        setCheckUnsupportedDestinations(true);
        String[] supportedDestinations = {"VH"};
        this.extractUnsupportedDestinations(supportedDestinations);
    }

    @ParameterizedTest(name = "Test username with invalid values  - Param : {0} --> {1}")
    @CsvSource(value = {"username,'abc124'", "username,''", "username,' '", "username,NIL"}, nullValues = "NIL")
    void testUsernameQueryParamValidValue(final String usernameKey, final String usernameValue) throws Exception {
        this.setUrlParams(buildQueryParams(usernameKey, usernameValue));
        commonDelegate.testExpectedResponseForSuppliedHeader(
                getAuthorizationToken(),
                getRelativeUrl(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                hmiSuccessVerifier,
                SUCCESS_MSG, null);
    }

    @ParameterizedTest(name = "Test All query params with extra param - Param : {0} --> {1}")
    @CsvSource({"username,abc123,extra_param,extra", "username,,extra_param,"})
    void testAllQueryParamWithExtraParam(final String paramKey1, final String paramVal1,
                                               final String paramKey2, final String paramVal2)
            throws Exception {
        this.setUrlParams(buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2));
        commonDelegate.testExpectedResponseForSuppliedHeader(
                getAuthorizationToken(),
                getRelativeUrl(), getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                hmiSuccessVerifier,
                ERROR_MSG, null);
    }
}
