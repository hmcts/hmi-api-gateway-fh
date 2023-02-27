package uk.gov.hmcts.futurehearings.hmi.functional.resources;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.resources.steps.ResourcesSteps;

import java.util.Random;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"In order to test that the Resources API is functioning properly",
        "As a tester",
        "I want to be able to execute the tests for Resources API methods works in a lifecycle mode of execution"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class ResourcesAPITest extends FunctionalTest {

    @Value("${resourcesLinkedHearingGroupRootContext}")
    private String resourcesLinkedHearingGroupRootContext;

    @Value("${resourcesLinkedHearingGroup_idRootContext}")
    private String resourcesLinkedHearingGroup_idRootContext;

    @Steps
    ResourcesSteps resourceSteps;

    @Test
    public void testRequestLinkedHearingGroup() {
        resourceSteps.shouldCreateLinkedHearingGroup(resourcesLinkedHearingGroupRootContext,
                headersAsMap, authorizationToken, HttpMethod.POST,
                "{}");
    }

    @Test
    public void testAmendLinkedHearingGroup() {
        int randomId = new Random().nextInt(99999999);
        resourcesLinkedHearingGroup_idRootContext = String.format(resourcesLinkedHearingGroup_idRootContext, randomId);
        resourceSteps.shouldAmendLinkedHearingGroupWithEmptyPayload(resourcesLinkedHearingGroup_idRootContext,
                headersAsMap,
                authorizationToken, HttpMethod.PUT,
                "{}" );
    }

    @Test
    public void testDeleteLinkedHearingGroupInvalid() {
        int randomId = new Random().nextInt(99999999);
        resourcesLinkedHearingGroup_idRootContext = String.format(resourcesLinkedHearingGroup_idRootContext, randomId);
        resourceSteps.shouldDeleteLinkedHearingGroupInvalid(resourcesLinkedHearingGroup_idRootContext,
                headersAsMap,
                authorizationToken,
                HttpMethod.DELETE);
    }
}
