package uk.gov.hmcts.futurehearings.hmi.acceptance.resources;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithEmulatorValues;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.CaseHQCommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.resources.verify.GETResourceByIDValidationVerifier;

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
class GETResourceByIDValidationTest extends ResourceValidationTest {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${resources_idRootContext}")
    private String resources_idRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        resources_idRootContext = String.format(resources_idRootContext,"12345");
        this.setRelativeURL(resources_idRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSucessStatus(HttpStatus.OK);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("resources","resource"));
        this.setHmiSuccessVerifier(new GETResourceByIDValidationVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }

    @ParameterizedTest(name = "Testing against the Emulator for Error Responses that come from the Case HQ System")
    @CsvSource(value = {"EMULATOR,400,1002,reference to a resource that doesn't exist"}, nullValues = "NIL")
    void test_successful_response_from_the_emulator_stub(final String destinationSystem,
                                                         final String returnHttpCode,
                                                         final String returnErrorCode,
                                                         final String returnDescription) throws Exception {

        final HttpStatus httpStatus =
                returnHttpCode.equalsIgnoreCase("400") ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_ACCEPTABLE;
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getAuthorizationToken(),
                getRelativeURL(), getInputPayloadFileName(),
                createHeaderWithEmulatorValues(getApiSubscriptionKey(),
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
