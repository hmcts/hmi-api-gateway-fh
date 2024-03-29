package uk.gov.hmcts.futurehearings.hmi.acceptance.videohearings;

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
import uk.gov.hmcts.futurehearings.hmi.Application;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses(PostCloneVideoHearingsValidationTest.class)
@IncludeTags("Post")
@SuppressWarnings({"PMD.TestClassWithoutTestCases"})
public class PostCloneVideoHearingsValidationTest extends VideoHearingValidationTest {

    @Value("${cloneVideoHearingsRootContext}")
    private String cloneVideoHearingsRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, "InvalidHearingId123");
        this.setRelativeUrl(cloneVideoHearingsRootContext);
        this.setHttpMethod(HttpMethod.POST);
        this.setSourceSystem("SNL");
        this.setInputPayloadFileName("video-hearing-request-standard.json");
        this.setHttpSuccessStatus(HttpStatus.OK);
        setCheckUnsupportedDestinations(true);
        String[] supportedDestinations = {"VH"};
        this.extractUnsupportedDestinations(supportedDestinations);
    }
}
