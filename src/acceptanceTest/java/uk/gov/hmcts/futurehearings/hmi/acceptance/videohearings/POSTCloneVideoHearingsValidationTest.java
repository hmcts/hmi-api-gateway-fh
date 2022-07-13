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

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses(POSTCloneVideoHearingsValidationTest.class)
@IncludeTags("Post")
public class POSTCloneVideoHearingsValidationTest extends VideoHearingValidationTest {

    @Value("${cloneVideoHearingsRootContext}")
    private String cloneVideoHearingsRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, "InvalidHearingId123");
        this.setRelativeURL(cloneVideoHearingsRootContext);
        this.setHttpMethod(HttpMethod.POST);
        this.setSourceSystem("SNL");
        this.setInputPayloadFileName("video-hearing-request-standard.json");
        this.setHttpSuccessStatus(HttpStatus.OK);
        setCheckUnsupportedDestinations(true);
        String[] supportedDestinations = {"VH"};
        this.extractUnsupportedDestinations(supportedDestinations);
    }
}
