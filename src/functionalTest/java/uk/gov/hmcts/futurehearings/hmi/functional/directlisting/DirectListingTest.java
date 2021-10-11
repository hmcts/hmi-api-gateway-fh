package uk.gov.hmcts.futurehearings.hmi.functional.directlisting;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.directlisting.steps.DirectListingSteps;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"In order to test that the Direct Listing Functionality is working properly",
        "As a tester",
        "I want to be able to execute the tests for Direct Listing"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings("java:S2699")
public class DirectListingTest extends FunctionalTest {

    @Value("${listingsRootContext}")
    protected String listingsRootContext;

    @Steps
    DirectListingSteps directListingSteps;

    @Before
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    public void testDirectListing() {
        directListingSteps.performDirectListingRequest(listingsRootContext, headersAsMap,
                authorizationToken, "{}");
    }
}
