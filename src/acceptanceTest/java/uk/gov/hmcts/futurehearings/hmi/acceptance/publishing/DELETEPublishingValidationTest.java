package uk.gov.hmcts.futurehearings.hmi.acceptance.publishing;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMICommonSuccessVerifier;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("java:S2187")
public class DELETEPublishingValidationTest extends PublishingValidationTest {
    @Qualifier("CommonDelegate")
    @Autowired(required = true)
    private CommonDelegate commonDelegate;

    @Value("${publishingDeleteRootContext}")
    private String publishingDeleteRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        publishingDeleteRootContext = String.format(publishingDeleteRootContext, "12345");
        this.setRelativeURL(publishingDeleteRootContext);
        this.setHttpMethod(HttpMethod.DELETE);
        this.setInputPayloadFileName("delete-test-publishing.json");
        this.setHttpSucessStatus(HttpStatus.NO_CONTENT);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace(publishingDeleteRootContext, publishingDeleteRootContext + "s"));
        this.setHmiSuccessVerifier(new HMICommonSuccessVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }
}
