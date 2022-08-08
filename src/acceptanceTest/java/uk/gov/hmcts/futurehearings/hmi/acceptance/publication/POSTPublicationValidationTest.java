package uk.gov.hmcts.futurehearings.hmi.acceptance.publication;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class POSTPublicationValidationTest extends PublicationValidationTest {
    @Qualifier("CommonDelegate")

    @Value("${pihPublicationRootContext}")
    private String pihPublicationRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeURL(pihPublicationRootContext);
        this.setHttpMethod(HttpMethod.POST);
        this.setInputPayloadFileName("post-test-publication.json");
        this.setHttpSuccessStatus(HttpStatus.CREATED);
        setCheckUnsupportedDestinations(true);
        String[] supportedDestinations = {"PIH"};
        this.extractUnsupportedDestinations(supportedDestinations);
    }
}
