package uk.gov.hmcts.futurehearings.hmi.smoke.sessions;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.smoke.common.test.SmokeTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
@DisplayName("Smoke Test for the HMI Sessions Context")
@SuppressWarnings("java:S2187")
 class SessionsApiSmokeTest extends SmokeTest {

    @Value("${sessionsApiRootContext}")
    private String sessionsApiRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        setRootContext(sessionsApiRootContext);
    }
}