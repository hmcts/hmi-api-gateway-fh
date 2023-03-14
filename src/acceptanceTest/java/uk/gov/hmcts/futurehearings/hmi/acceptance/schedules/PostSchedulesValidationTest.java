package uk.gov.hmcts.futurehearings.hmi.acceptance.schedules;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostSchedulesValidationTest extends SchedulesValidationTest {

    @Value("${schedulesApiRootContext}")
    private String schedulesApiRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeUrl(schedulesApiRootContext);
        this.setHttpMethod(HttpMethod.POST);
        this.setInputPayloadFileName("POST-schedules-payload.json");
        this.setHttpSuccessStatus(HttpStatus.CREATED);
        setCheckUnsupportedDestinations(true);
        String[] supportedDestinations = {"SNL"};
        this.extractUnsupportedDestinations(supportedDestinations);
    }
}
