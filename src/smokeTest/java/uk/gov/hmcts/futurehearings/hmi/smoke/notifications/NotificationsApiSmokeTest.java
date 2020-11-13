package uk.gov.hmcts.futurehearings.hmi.smoke.notifications;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.smoke.common.test.SmokeTest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
@DisplayName("Smoke Test for the HMI Notification Context")
@Disabled("For as the endpoint for Notification API is not available")
@SuppressWarnings("java:S2187")
class NotificationsApiSmokeTest extends SmokeTest {

    @Value("${notificationApiRootContext}")
    private String notificationApiRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        setRootContext(notificationApiRootContext);
    }
}