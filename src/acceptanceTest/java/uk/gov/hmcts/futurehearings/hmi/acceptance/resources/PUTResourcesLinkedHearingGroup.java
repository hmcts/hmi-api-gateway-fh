package uk.gov.hmcts.futurehearings.hmi.acceptance.resources;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import uk.gov.hmcts.futurehearings.hmi.Application;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMICommonSuccessVerifier;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("java:S2187")
public class PUTResourcesLinkedHearingGroup extends ResourceValidationTest {

    @Value("${resourcesLinkedHearingGroup_idRootContext}")
    private String resourcesLinkedHearingGroup_idRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        resourcesLinkedHearingGroup_idRootContext = String.format(resourcesLinkedHearingGroup_idRootContext, "TEST");
        this.setRelativeURL(resourcesLinkedHearingGroup_idRootContext);
        this.setHttpMethod(HttpMethod.PUT);
        this.setInputPayloadFileName("put-resource-by-location-request-valid.json");
        this.setHttpSucessStatus(HttpStatus.ACCEPTED);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("resources","resource"));
        this.setHmiSuccessVerifier(new HMICommonSuccessVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }
}
