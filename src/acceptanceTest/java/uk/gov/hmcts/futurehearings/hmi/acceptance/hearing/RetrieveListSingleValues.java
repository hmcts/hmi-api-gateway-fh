package uk.gov.hmcts.futurehearings.hmi.acceptance.hearing;

import uk.gov.hmcts.futurehearings.hmi.Application;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RetrieveListSingleValues extends HearingValidationTest {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${retrieveIndividualHearingRootContext}")
    private String retrieveIndividualHearingAPIRootContext;

    @BeforeAll
    public void initialiseValues() {
        super.initialiseValues();
        retrieveIndividualHearingAPIRootContext = String.format(retrieveIndividualHearingAPIRootContext,"12345");
        this.setRelativeURL(retrieveIndividualHearingAPIRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSucessStatus(HttpStatus.OK);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("hearings","hearing"));
    }
}
