package uk.gov.hmcts.futurehearings.hmi.acceptance.videohearings;

import uk.gov.hmcts.futurehearings.hmi.Application;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Random;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses(POSTVideoHearingsValidationTest.class)
@IncludeTags("Post")
public class POSTParticipantValidationTest extends VideoHearingValidationTest {

    @Value("${participantsRootContext}")
    private String participantsRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        String hearingId = String.valueOf(new Random().nextInt(99999999));
        participantsRootContext = String.format(participantsRootContext, hearingId);
        this.setRelativeURL(participantsRootContext);
        this.setHttpMethod(HttpMethod.POST);
        this.setInputPayloadFileName("post-participants-request.json");
        this.setHttpSuccessStatus(HttpStatus.CREATED);
        setCheckUnsupportedDestinations(true);
        String[] supportedDestinations = {"VH"};
        this.extractUnsupportedDestinations(supportedDestinations);
    }
}
