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
@SelectClasses(PutVideoHearingsValidationTest.class)
@IncludeTags("Post")
@SuppressWarnings({"PMD.TestClassWithoutTestCases"})
class PutVideoHearingsValidationTest extends VideoHearingValidationTest {

    @Value("${videohearings_idRootContext}")
    private String videohearingsIdRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeUrl(videohearingsIdRootContext);
        this.setHttpMethod(HttpMethod.PUT);
        this.setInputPayloadFileName("put-video-hearing-request.json");
        this.setHttpSuccessStatus(HttpStatus.OK);
        setCheckUnsupportedDestinations(true);
        String[] supportedDestinations = {"VH"};
        this.extractUnsupportedDestinations(supportedDestinations);
    }
}
