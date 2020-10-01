package uk.gov.hmcts.futurehearings.hmi.acceptance.resources;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper.buildQueryParams;

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
@SelectClasses(PUTUserAsResourcesValidationTest.class)
@IncludeTags("Put")
class PUTUserAsResourcesValidationTest extends ResourceValidationTest {

    @Qualifier("CommonDelegate")
    @Autowired(required = true)
    public CommonDelegate commonDelegate;

    @Value("${userAsResourceRootContext}")
    private String userAsResourceRootContext;

    private HttpMethod httpMethod;

    @BeforeAll
    public void initialiseValues() {
        super.initialiseValues();
        this.setRelativeURL(userAsResourceRootContext);
        this.setUrlParams(buildQueryParams("sessionIdCaseHQ", "12345"));
        this.setHttpMethod(HttpMethod.PUT);
        this.setInputPayloadFileName("put-user-as-resource-request-valid.json");
        this.setHttpSucessStatus(HttpStatus.CREATED);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("resources/user","resource/user"));
        this.setHmiSuccessVerifier(new HMICommonSuccessVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }

    @Test
    @DisplayName("Invalid Query Parameter supplied")
    void test_invalid_user_query_param() throws Exception {
        this.setUrlParams(buildQueryParams("sessionId", "12345"));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                userAsResourceRootContext, getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getInputFileDirectory(),
                getHmiErrorVerifier(),"Invalid query parameter/s in the request URL.");
    }

    //This test is for a Standard Header but a Payload for Non JSON Type is to be tested.
    //Confirmed by Product Owner that this should be a Success Scenario.
    @Test
    @DisplayName("Successfully validated response with an xml payload")
    void test_successful_response_for_test_xml_body() throws Exception {
        this.setUrlParams(buildQueryParams("sessionIdCaseHQ", "12345"));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), "sample-xml-payload.xml",
                createStandardPayloadHeader(getApiSubscriptionKey()),
                null,
                getUrlParams(),
                getHttpMethod(),
                this.getHttpSucessStatus(), "common",
                getHmiSuccessVerifier(),"The request was received successfully.");
    }
}
