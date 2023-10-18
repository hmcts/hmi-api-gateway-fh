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

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses(PostVideoHearingsValidationTest.class)
@IncludeTags("Delete")
@SuppressWarnings({"PMD.TestClassWithoutTestCases"})
public class DeleteJudicialOfficeValidationTest extends VideoHearingValidationTest {

    @Value("${joh_idVideoHearingRootContext}")
    private String deleteJohRootContext;

    private final Random rand;

    DeleteJudicialOfficeValidationTest() throws NoSuchAlgorithmException {
        super();
        rand = SecureRandom.getInstanceStrong();
    }

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        String hearingId = String.valueOf(rand.nextInt(99999999));
        String johId = String.valueOf(rand.nextInt(99999999));
        deleteJohRootContext = String.format(deleteJohRootContext, hearingId, johId);
        this.setRelativeUrl(deleteJohRootContext);
        this.setHttpMethod(HttpMethod.DELETE);
        this.setInputPayloadFileName("delete-joh-request.json");
        this.setHttpSuccessStatus(HttpStatus.NO_CONTENT);
        setCheckUnsupportedDestinations(true);
        String[] supportedDestinations = {"VH"};
        this.extractUnsupportedDestinations(supportedDestinations);
    }
}
