package uk.gov.hmcts.futurehearings.hmi.functional.schedules;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHmiHeader;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class SchedulesApiTest extends FunctionalTest {

    @Value("${schedulesApiRootContext}")
    protected String schedulesApiRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    public void testCreateScheduleWithEmptyPayload() {
        headersAsMap = createStandardHmiHeader("SNL");
        callRestEndpointWithPayload(schedulesApiRootContext,
                headersAsMap,
                authorizationToken,
                "{}",
                HttpMethod.POST,
                HttpStatus.BAD_REQUEST);
    }
}
