package uk.gov.hmcts.futurehearings.hmi.functional.schedules;

import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;

import uk.gov.hmcts.futurehearings.hmi.Application;

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
import uk.gov.hmcts.futurehearings.hmi.functional.schedules.steps.SchedulesSteps;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHMIHeader;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"In order to test that the Schedules API is functioning properly",
        "As a tester",
        "I want to be able to execute the tests for Schedules API methods works in a lifecycle mode of execution"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class SchedulesAPITest extends FunctionalTest {

    @Value("${schedulesApiRootContext}")
    protected String schedulesApiRootContext;

    @Steps
    SchedulesSteps schedulesSteps;

    @Test
    public void testCreateScheduleWithEmptyPayload() {
        headersAsMap = createStandardHMIHeader("SNL");
        schedulesSteps.shouldPostSessionsWithPayload(schedulesApiRootContext,
                headersAsMap, authorizationToken, HttpMethod.POST, "{}");
    }
}
