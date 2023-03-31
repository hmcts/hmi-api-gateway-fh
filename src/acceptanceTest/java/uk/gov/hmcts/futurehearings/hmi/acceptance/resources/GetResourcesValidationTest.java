package uk.gov.hmcts.futurehearings.hmi.acceptance.resources;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HmiCommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.resources.verify.GetResourcesValidationVerifier;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings({"PMD.TestClassWithoutTestCases", "java:S2187"})
public class GetResourcesValidationTest extends ResourceValidationTest {

    @Value("${resourcesRootContext}")
    private String resourcesRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeUrl(resourcesRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSuccessStatus(HttpStatus.OK);
        hmiSuccessVerifier = new GetResourcesValidationVerifier();
        hmiErrorVerifier = new HmiCommonErrorVerifier();
    }
}
