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

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("java:S2187")
public class PutResourcesLinkedHearingGroupTest extends ResourceValidationTest {

    @Value("${resourcesLinkedHearingGroup_idRootContext}")
    private String resourcesLinkedHearingGroupIdRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        resourcesLinkedHearingGroupIdRootContext = String.format(resourcesLinkedHearingGroupIdRootContext, "TEST");
        this.setRelativeUrl(resourcesLinkedHearingGroupIdRootContext);
        this.setHttpMethod(HttpMethod.PUT);
        this.setInputPayloadFileName("put-resource-linked-hearing-group.json");
        this.setHttpSuccessStatus(HttpStatus.ACCEPTED);
        setCheckUnsupportedDestinations(true);
        String[] supportedDestinations = {"SNL"};
        this.extractUnsupportedDestinations(supportedDestinations);
    }
}
