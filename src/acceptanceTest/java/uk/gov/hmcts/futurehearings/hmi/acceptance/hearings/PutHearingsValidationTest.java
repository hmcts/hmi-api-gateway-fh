package uk.gov.hmcts.futurehearings.hmi.acceptance.hearings;

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
@SelectClasses(PutHearingsValidationTest.class)
@IncludeTags("Put")
@SuppressWarnings({"PMD.TestClassWithoutTestCases"})
class PutHearingsValidationTest extends HearingValidationTest {

    @Value("${hearings_idRootContext}")
    private String hearingsIdRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        hearingsIdRootContext = String.format(hearingsIdRootContext, "12345");
        this.setRelativeUrl(hearingsIdRootContext);
        this.setHttpMethod(HttpMethod.PUT);
        this.setInputPayloadFileName("hearing-request-standard.json");
        this.setHttpSuccessStatus(HttpStatus.ACCEPTED);
        setCheckUnsupportedDestinations(true);
        String[] supportedDestinations = {"CFT", "SNL", "CRIME"};
        this.extractUnsupportedDestinations(supportedDestinations);
    }
}
