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

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses(DeleteResourcesValidationTest.class)
@IncludeTags("Delete")
public class DeleteResourcesLinkedHearingGroupTest extends ResourceValidationTest {

    @Value("${resourcesLinkedHearingGroup_idRootContext}")
    private String resourcesLinkedHearingGroupIdRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        resourcesLinkedHearingGroupIdRootContext = String.format(resourcesLinkedHearingGroupIdRootContext, "123456");
        this.setRelativeUrl(resourcesLinkedHearingGroupIdRootContext);
        this.setHttpMethod(HttpMethod.DELETE);
        this.setInputFileDirectory(null);
        this.setHttpSuccessStatus(HttpStatus.ACCEPTED);
        setCheckUnsupportedDestinations(true);
        String[] supportedDestinations = {"SNL"};
        this.extractUnsupportedDestinations(supportedDestinations);
    }
}
