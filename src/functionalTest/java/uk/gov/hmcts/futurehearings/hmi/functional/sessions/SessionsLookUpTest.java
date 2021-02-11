package uk.gov.hmcts.futurehearings.hmi.functional.sessions;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.sessions.steps.SessionsLookUpSteps;

import java.util.HashMap;
import java.util.Map;

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
    public void testSuccessfulGetSessionForSessionRequestType() throws Exception {

        log.debug("In the testSuccessfulGetSessionForSessionRequestType () method");
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("requestSessionType", "ADHOC");

        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);

    }

    @Test
    public void testSuccessfulGetSessionForSessionRequestTypeAndRequestDuration() throws Exception {

        log.debug("In the testSuccessfulGetSessionForSessionRequestType() method");
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("requestSessionType", "ADHOC");
        queryParameters.put("requestDuration", "360");

        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);

    }

    @Test
    public void testSuccessfulGetSessionForSessionRequestTypeAndRequestLocation() throws Exception {

        log.debug("In the testSuccessfulGetSessionForSessionRequestType() method");
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("requestSessionType", "ADHOC");
        queryParameters.put("requestLocationId", "301");

        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    @Ignore("Ignoring this test as it is Failing Due to Defect MCGIRRSD-2359")
    public void testSuccessfulGetSessionForSessionRequestTypeAndRequestJudgeType() throws Exception {

        log.debug("In the testSuccessfulGetSessionForSessionRequestType() method");
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("requestSessionType", "ADHOC");
        queryParameters.put("requestJudgeType", "PUBLAW");

        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testSuccessfulGetSessionForSessionRequestTypeAndRequestStartDateAndRequestEndDate() throws Exception {

        log.debug("In the testSuccessfulGetSessionForSessionRequestType() method");
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("requestSessionType", "ADHOC");
        queryParameters.put("requestStartDate", "2020-12-01T10:00:00Z");
        queryParameters.put("requestEndDate", "2020-12-09T10:00:00Z");

        sessionsLookUpSteps.checkSessionsForAllTheRelevantQueryParameters(sessionsRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }
}