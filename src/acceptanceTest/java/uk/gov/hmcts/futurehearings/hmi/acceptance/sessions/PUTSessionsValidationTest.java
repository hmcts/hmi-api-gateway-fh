package uk.gov.hmcts.futurehearings.hmi.acceptance.sessions;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithEmulatorValues;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.CaseHQCommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMICommonSuccessVerifier;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses(PUTSessionsValidationTest.class)
@IncludeTags("Put")
class PUTSessionsValidationTest extends SessionsValidationTest {

    @Qualifier("CommonDelegate")
    @Autowired(required = true)
    public CommonDelegate commonDelegate;

    @Value("${sessions_idRootContext}")
    private String sessions_idRootContext;

    private HttpMethod httpMethod;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        sessions_idRootContext = String.format(sessions_idRootContext,"12345");
        this.setRelativeURL(sessions_idRootContext);
        this.setHttpMethod(HttpMethod.PUT);
        this.setInputPayloadFileName("put-sessions-request-valid.json");
        this.setHttpSucessStatus(HttpStatus.OK);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("sessions","session"));
        this.setHmiSuccessVerifier(new HMICommonSuccessVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }

    //This test is for a Standard Header but a Payload for Non JSON Type is to be tested.
    //Confirmed by Product Owner that this should be a Success Scenario.
    @Test
    @DisplayName("Successfully validated response with an xml payload")
    void test_successful_response_for_test_xml_body() throws Exception {

        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), "sample-xml-payload.xml",
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                this.getHttpSucessStatus(), "common",
                getHmiSuccessVerifier(),"The request was received successfully.",null);
    }

    @ParameterizedTest(name = "Testing against the Emulator for Error Responses that come from the Case HQ System")
    @CsvSource(value = {"EMULATOR,400,1000,Invalid LOV Value","EMULATOR,400,1003,mandatory value missing","EMULATOR,400,1004,schema validation failure"}, nullValues = "NIL")
    void test_successful_response_from_the_emulator_stub(final String destinationSystem,
                                                         final String returnHttpCode,
                                                         final String returnErrorCode,
                                                         final String returnDescription) throws Exception {

        final HttpStatus httpStatus =
                returnHttpCode.equalsIgnoreCase("400") ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_ACCEPTABLE;
        commonDelegate.test_expected_response_for_supplied_header(
                getAuthorizationToken(),
                getRelativeURL(), "put-sessions-request-valid.json",
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
