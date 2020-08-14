package uk.gov.hmcts.futurehearings.hmi.acceptance.schedule;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.test.CommonTest;
import uk.gov.hmcts.futurehearings.hmi.acceptance.schedule.delegate.Schedule;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScheduleValidationTest extends CommonTest {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${hearingApiRootContext}")
    private String hearingAPIRootContext;

    @Autowired
    private Schedule scheduleDelegate;

    @BeforeAll
    public void initialiseValues() {
        RestAssured.baseURI = targetInstance;
        RestAssured.useRelaxedHTTPSValidation();
        this.setApiSubscriptionKey(targetSubscriptionKey);
        this.setRelativeURL(hearingAPIRootContext);
    }

    @BeforeEach
    public void beforeEach(TestInfo info) {
        log.debug("Before execute : " + info.getTestMethod().get().getName());
        log.debug("The value of the targetInstance "+targetInstance);
    }

    @AfterEach
    public void afterEach(TestInfo info) {
        log.debug("After execute : "+info.getTestMethod().get().getName());
    }

   /* @Test
    public void test_sucessfull_post() throws Exception {
        scheduleDelegate.test_successfull_post(targetSubscriptionKey,
                hearingAPIRootContext);
    }*/

}
