package uk.gov.hmcts.futurehearings.hmi.functional.sessions;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.sessions.steps.SessionsLookUpSteps;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"In order to test that the Session Get Functionality is working properly",
        "As a tester",
        "I want to be able to execute the tests for the Sessions API"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class SessionsLookUpTest extends FunctionalTest {

    @Value("${sessionsRootContext}")
    protected String sessionsRootContext;

    @Steps
    SessionsLookUpSteps sessionsLookUpSteps;

    @Before
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    @Ignore("Ignored as failing due to defect with API, will remove ignore when fixed")
    public void testSuccessfulGetSessionForSessionRequestType() {
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("requestSessionType", "DS");
        headersAsMap.put("Destination-System", "MOCK");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    @Ignore("Ignored as failing due to defect with API, will remove ignore when fixed")
    public void testSuccessfulGetSessionForSessionRequestTypeAndRequestDuration() {
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("requestSessionType", "DS");
        queryParameters.put("requestDuration", "360");
        headersAsMap.put("Destination-System", "MOCK");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForSessionRequestTypeAndRequestLocation() {
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("requestSessionType", "DS");
        queryParameters.put("requestLocationId", "301");
        headersAsMap.put("Destination-System", "MOCK");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForSessionRequestTypeAndRequestJudgeType() {
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("requestSessionType", "DS");
        queryParameters.put("requestJudgeType", "PUBLAW");
        headersAsMap.put("Destination-System", "MOCK");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForSessionRequestTypeAndRequestStartDateAndRequestEndDate() {
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("requestSessionType", "DS");
        queryParameters.put("requestStartDate", "2020-12-01T10:00:00Z");
        queryParameters.put("requestEndDate", "2020-12-09T10:00:00Z");
        headersAsMap.put("Destination-System", "MOCK");
        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }
}
