package uk.gov.hmcts.futurehearings.hmi.acceptance.resources;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMICommonSuccessVerifier;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
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
@SelectClasses(PUTResourcesByUserValidationTest.class)
@IncludeTags("Put")
@SuppressWarnings("java:S2187")
class PUTResourcesByUserValidationTest extends ResourceValidationTest {

    @Qualifier("CommonDelegate")
    @Autowired(required = true)
    public CommonDelegate commonDelegate;

    @Value("${resourcesByUser_idRootContext}")
    private String resourcesByUser_idRootContext;

    private HttpMethod httpMethod;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        resourcesByUser_idRootContext = String.format(resourcesByUser_idRootContext,"12345");
        this.setRelativeURL(resourcesByUser_idRootContext);
        this.setHttpMethod(HttpMethod.PUT);
        this.setInputPayloadFileName("put-user-as-resource-request-valid.json");
        this.setHttpSucessStatus(HttpStatus.NO_CONTENT);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("resources/user","resource/user"));
        this.setHmiSuccessVerifier(new HMICommonSuccessVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }
}
