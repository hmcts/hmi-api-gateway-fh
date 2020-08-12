package uk.gov.hmcts.futurehearings.hmi.acceptance.schedule;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.delegate.Schedule;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
public class ScheduleAcceptanceTest {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Autowired
    private Schedule scheduleDelegate;

    @BeforeEach
    public void beforeEach(TestInfo info) {
        System.out.println("Before execute : "+info.getTestMethod().get().getName());
        System.out.println("The value of the targetInstance "+targetInstance);
    }

    @AfterEach
    public void afterEach(TestInfo info) {
        System.out.println("After execute : "+info.getTestMethod().get().getName());
    }

    @Test
    public void test_sucessfull_post() throws Exception {
        scheduleDelegate.test_successfull_post(targetSubscriptionKey,
                   targetInstance);
    }
}
