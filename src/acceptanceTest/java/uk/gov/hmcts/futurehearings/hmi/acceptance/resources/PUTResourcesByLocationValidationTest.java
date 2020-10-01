package uk.gov.hmcts.futurehearings.hmi.acceptance.resources;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper.buildQueryParams;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMICommonSuccessVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.resources.verify.GETResourcesValidationVerifier;

import java.io.IOException;

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

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("java:S2187")
public class PUTResourcesByLocationValidationTest extends ResourceValidationTest {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${resourcesByLocationRootContext}")
    private String resourcesByLocationRootContext;

    @BeforeAll
    public void initialiseValues() {
        super.initialiseValues();
        this.setRelativeURL(resourcesByLocationRootContext);
        this.setHttpMethod(HttpMethod.PUT);
        this.setInputPayloadFileName("put-resource-by-location-request-valid.json");
        this.setHttpSucessStatus(HttpStatus.CREATED);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("location","locatio"));
        this.setHmiSuccessVerifier(new HMICommonSuccessVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }

    @ParameterizedTest(name = "Hearing Date with and without value - Param : {0} --> {1}")
    @CsvSource(value = {"sessionIdCaseHQ, date", "sessionIdCaseHQ,''", "sessionIdCaseHQ,NIL", "sessionIdCaseHQ, 123456"},nullValues = "NIL")
    void test_hearing_date_queryparam_with_value(final String hearingDateKey,
                                                 final String hearingDateValue) throws IOException {
        this.setUrlParams(buildQueryParams(hearingDateKey, hearingDateValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.CREATED,  getInputFileDirectory(),
                getHmiSuccessVerifier(),
                "The request was received successfully.");
    }

}
