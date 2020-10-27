package uk.gov.hmcts.futurehearings.hmi.acceptance.resources;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.TestingUtils.readFileContents;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createCompletePayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.mock.CommonStubFactory.resetMocks;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.mock.CommonStubFactory.uploadCommonMocks;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMICommonSuccessVerifier;

import java.io.IOException;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
public class ResourceInteractionTest {

    private static final String COMMON_MOCK_PATH = "uk/gov/hmcts/futurehearings/hmi/acceptance/common/mock";

    private WireMock wireMock;

    @Value("${mockServerHost}")
    private String mockServerHost;

    @Value("${targetInstance}")
    private String targetInstance;

    @Qualifier("CommonDelegate")
    @Autowired(required = true)
    public CommonDelegate commonDelegate;

    @Value("${resourcesByUser_idRootContext}")
    private String resourcesByUser_idRootContext;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @BeforeAll
    public void initialiseValues() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    @DisplayName("Successfully validated response with all the header values")
    void test_successful_response_with_a_complete_header() throws Exception {

        RestAssured.baseURI = mockServerHost;
        log.debug("The value of the base URI" + RestAssured.baseURI);
        resetMocks("/__admin/mappings/reset");
        uploadCommonMocks("/__admin/mappings/import",
                readFileContents(COMMON_MOCK_PATH+"/common-mock-responses.json"));

        RestAssured.baseURI = targetInstance;
        resourcesByUser_idRootContext = String.format(resourcesByUser_idRootContext,"12345");
        commonDelegate.test_expected_response_for_supplied_header(targetSubscriptionKey,
                resourcesByUser_idRootContext, "put-user-as-resource-request-valid.json",
                createCompletePayloadHeader(targetSubscriptionKey),
                null,
                null,
                HttpMethod.PUT,
                HttpStatus.OK,
                "resources",
                new HMICommonSuccessVerifier(), "The request was received successfully.");

        //wireMock.verify(1, getRequestedFor(urlEqualTo("/casehqapi/rest/hmcts/resources/users")));
    }
}
