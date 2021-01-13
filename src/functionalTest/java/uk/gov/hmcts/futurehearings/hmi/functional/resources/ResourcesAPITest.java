package uk.gov.hmcts.futurehearings.hmi.functional.resources;


import static uk.gov.hmcts.futurehearings.hmi.functional.common.TestingUtils.readFileContents;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.resources.steps.ResourcesSteps;

import java.io.IOException;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"In order to test that the Resources API is functioning properly",
        "As a tester",
        "I want to be able to execute the tests for Resources API methods works in a lifecycle mode of execution"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class ResourcesAPITest extends FunctionalTest {

    public static final String RESOURCES_INPUT_PATH = "uk/gov/hmcts/futurehearings/hmi/functional/resources/input";

    @Value("${resourcesByUserRootContext}")
    protected String resourcesByUserRootContext;

    @Value("${resourcesByUser_idRootContext}")
    protected String resourcesByUser_idRootContext;

    @Value("${resourcesByLocationRootContext}")
    protected String resourcesByLocationRootContext;

    @Value("${resourcesByLocation_idRootContext}")
    protected String resourcesByLocation_idRootContext;

    @Steps
    ResourcesSteps resourceSteps;

    @Test
    public void testRequestAndAmendAResourceByUser() throws IOException {

        log.debug("In the testRequestAndAmendAResourceByUser() method");
        int randomId = new Random().nextInt(99999999);
        String inputBodyForCreateResources =
                String.format(readFileContents(RESOURCES_INPUT_PATH + "/POST-resources-user-payload.json"), randomId);
        resourceSteps.shouldCreateAnUser(resourcesByUserRootContext,
                headersAsMap,
                authorizationToken,
                inputBodyForCreateResources);

        resourcesByUser_idRootContext = String.format(resourcesByUser_idRootContext, randomId);
        String inputBodyForAmendResources =
                String.format(readFileContents(RESOURCES_INPUT_PATH + "/PUT-resources-user-payload.json"), randomId);
        resourceSteps.shouldUpdateAnUser(resourcesByUser_idRootContext,
                headersAsMap,
                authorizationToken,
                inputBodyForAmendResources);
    }

    @Test
    public void testRequestAndAmendAResourceByLocation() throws IOException {

        log.debug("In the testRequestAndAmendAResourceByUser() method");
        int randomId = new Random().nextInt(99999999);
        String inputBodyForCreateResourcesByLocation =
                String.format(readFileContents(RESOURCES_INPUT_PATH + "/POST-resources-location-payload.json"), randomId,randomId);
        resourceSteps.shouldCreateALocation(resourcesByLocationRootContext,
                headersAsMap,
                authorizationToken,
                inputBodyForCreateResourcesByLocation);

        resourcesByLocation_idRootContext = String.format(resourcesByLocation_idRootContext,randomId);
        String inputBodyForAmendResourcesByLocation =
                String.format(readFileContents(RESOURCES_INPUT_PATH + "/PUT-resources-location-payload.json"), randomId,randomId);
        resourceSteps.shouldUpdateALocation(resourcesByLocation_idRootContext,
                headersAsMap,
                authorizationToken,
                inputBodyForAmendResourcesByLocation);
    }
}
