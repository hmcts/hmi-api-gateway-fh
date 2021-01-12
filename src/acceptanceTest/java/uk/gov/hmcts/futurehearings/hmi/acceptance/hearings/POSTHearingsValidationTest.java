package uk.gov.hmcts.futurehearings.hmi.acceptance.hearings;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMICommonSuccessVerifier;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
@SelectClasses(POSTHearingsValidationTest.class)
@IncludeTags("Post")
class POSTHearingsValidationTest extends HearingValidationTest {

    @Qualifier("CommonDelegate")
    @Autowired(required = true)
    private CommonDelegate commonDelegate;

    @Value("${hearingsApiRootContext}")
    private String hearingsApiRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeURL(hearingsApiRootContext);
        this.setHttpMethod(HttpMethod.POST);
        this.setInputPayloadFileName("hearing-request-standard.json");
        this.setHttpSucessStatus(HttpStatus.ACCEPTED);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("hearings","hearing"));
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
                this.getHttpSucessStatus(),
                "common",
                getHmiSuccessVerifier(),"The request was received successfully.",null);
    }
}
