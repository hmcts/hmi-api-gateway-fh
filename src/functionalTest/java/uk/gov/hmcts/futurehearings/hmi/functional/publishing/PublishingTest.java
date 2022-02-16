package uk.gov.hmcts.futurehearings.hmi.functional.publishing;

import org.junit.Ignore;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import org.junit.Before;
import org.junit.Test;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.functional.publishing.steps.PublishingSteps;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHMIHeader;

@Ignore("disabled failed tests due to removal of the endpoints under the test")
@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"Testing the Publishing API is working correctly"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class PublishingTest extends FunctionalTest {

    @Value("${publishingCreateRootContext}")
    private String publishingCreateRootContext;

    @Value("${publishingAmendRootContext}")
    private String publishingAmendRootContext;

    @Value("${publishingGetRootContext}")
    private String publishingGetRootContext;

    @Value("${publishingDeleteRootContext}")
    private String publishingDeleteRootContext;

    @Steps
    PublishingSteps publishingSteps;

    @Before
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    public void testCreatePublishedEmptyPayload() {
        headersAsMap = createStandardHMIHeader("MOCK");
        publishingSteps.shouldCreatePublishingWithInvalidPayload(publishingCreateRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }

    @Test
    public void testAmendPublishedEmptyPayload() {
        headersAsMap = createStandardHMIHeader("MOCK");
        publishingSteps.shouldAmendPublishingWithInvalidPayload(publishingAmendRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }

    @Test
    public void testGetPublished() {
        headersAsMap = createStandardHMIHeader("MOCK");
        Map<String, String> queryParameters = new HashMap<String, String>();

        publishingSteps.shouldGetPublishing(publishingGetRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testDeletePublished() {
        headersAsMap = createStandardHMIHeader("MOCK");
        publishingSteps.shouldDeletePublishing(publishingDeleteRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }
}
