package uk.gov.hmcts.futurehearings.hmi.acceptance.resources;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HmiCommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HmiCommonSuccessVerifier;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses(DeleteResourcesValidationTest.class)
@IncludeTags("Delete")
@SuppressWarnings({"PMD.TestClassWithoutTestCases"})
class DeleteResourcesValidationTest extends ResourceValidationTest {

    @Value("${resources_idRootContext}")
    private String resourcesIdRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        resourcesIdRootContext = String.format(resourcesIdRootContext, "12345");
        this.setRelativeUrl(resourcesIdRootContext);
        this.setHttpMethod(HttpMethod.DELETE);
        this.setInputPayloadFileName("delete-resource-request-valid.json");
        this.setHttpSuccessStatus(HttpStatus.OK);
        hmiSuccessVerifier = new HmiCommonSuccessVerifier();
        hmiErrorVerifier = new HmiCommonErrorVerifier();
    }
}
