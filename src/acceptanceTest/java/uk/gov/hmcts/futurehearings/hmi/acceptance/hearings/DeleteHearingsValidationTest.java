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
@SelectClasses(DeleteHearingsValidationTest.class)
@IncludeTags("Delete")
class DeleteHearingsValidationTest extends HearingValidationTest {

    @Value("${hearings_idRootContext}")
    private String hearingsIdRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeUrl(String.format(hearingsIdRootContext, "12345"));
        this.setHttpMethod(HttpMethod.DELETE);
        this.setInputPayloadFileName("delete-hearing-request-valid.json");
        this.setHttpSuccessStatus(HttpStatus.OK);
        setCheckUnsupportedDestinations(true);
        String[] supportedDestinations = {"SNL"};
        this.extractUnsupportedDestinations(supportedDestinations);
    }
}
