package uk.gov.hmcts.futurehearings.hmi.functional.directlisting;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.TestingUtils.readFileContents;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHMIHeader;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.directlisting.steps.DirectListingSteps;

import java.util.HashMap;
import java.util.Map;

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

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"In order to test that the Direct Listing Functionality is working properly",
        "As a tester",
        "I want to be able to execute the tests for Direct Listing a Hearing Request into a known Session"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings("java:S2699")
public class DirectListingTest extends FunctionalTest {

    @Value("${sessionsRootContext}")
    protected String sessionsRootContext;

    @Value("${listings_idRootContext}")
    protected String listings_idRootContext;

    @Value("${directhearings_idRootContext}")
    protected String directhearings_idRootContext;

    @Steps
    DirectListingSteps directListingSteps;

    @Before
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }


    @Test
    public void testSuccessfulDirectListing() throws Exception {

        log.debug("In the testSuccessfulPostToHearing () method");

        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("requestSessionType", "ADHOC");

        String sessionIdCaseHQ = directListingSteps.getSessionIdForDirectListing(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);

        listings_idRootContext = String.format(listings_idRootContext, sessionIdCaseHQ);
        headersAsMap = createStandardHMIHeader("MOCK");
       String inputBodyForDirectListing =
                String.format(readFileContents("uk/gov/hmcts/futurehearings/hmi/functional/direct-listing/input/POST-Hearing-Direct-Listing-Payload.json"), sessionIdCaseHQ);
        directListingSteps.performDirectHearingListingForGivenSessionId(directhearings_idRootContext,
                headersAsMap,
                authorizationToken,
                inputBodyForDirectListing);
    }
}
