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
@SelectClasses(PutDirectHearingsByIdValidationTest.class)
@IncludeTags("Put")
@SuppressWarnings({"PMD.TestClassWithoutTestCases","java:S2187"})
class PutDirectHearingsByIdValidationTest extends HearingValidationTest {

    @Value("${directhearings_idRootContext}")
    private String directhearingsIdRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        directhearingsIdRootContext = String.format(directhearingsIdRootContext, "12345");
        this.setRelativeUrl(directhearingsIdRootContext);
        this.setHttpMethod(HttpMethod.PUT);
        this.setInputPayloadFileName("direct-hearing-request-valid.json");
        this.setHttpSuccessStatus(HttpStatus.ACCEPTED);
        setCheckUnsupportedDestinations(true);
        String[] supportedDestinations = {"SNL"};
        this.extractUnsupportedDestinations(supportedDestinations);
    }
}
