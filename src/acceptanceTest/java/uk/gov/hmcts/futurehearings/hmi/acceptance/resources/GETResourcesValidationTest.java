package uk.gov.hmcts.futurehearings.hmi.acceptance.resources;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.resources.verify.GETResourceByIDValidationVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.resources.verify.GETResourcesValidationVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.schedules.helper.verify.GETSchedulesValidationVerifier;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GETResourcesValidationTest extends ResourceValidationTest {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${resourcesRootContext}")
    private String resourcesRootContext;

    @BeforeAll
    public void initialiseValues() {
        super.initialiseValues();
        this.setRelativeURL(resourcesRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSucessStatus(HttpStatus.OK);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("resources","resource"));
        this.setHmiSuccessVerifier(new GETResourcesValidationVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }
}
