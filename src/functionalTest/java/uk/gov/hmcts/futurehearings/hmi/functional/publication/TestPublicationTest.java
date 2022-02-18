package uk.gov.hmcts.futurehearings.hmi.functional.publication;

import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.BeforeEach;
import uk.gov.hmcts.futurehearings.hmi.Application;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.functional.publication.steps.PublicationSteps;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"Testing the Publication API is working correctly"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class TestPublicationTest extends PIHFunctionalTest {

    @Value("${pihPublicationRootContext}")
    private String pihPublicationRootContext;

    @Steps
    PublicationHeaders publicationHeaders;

    @Steps
    PublicationSteps publicationSteps;

    @BeforeEach
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    private void setMandatoryHeaders(Map<String, Object> headersAsMap) {
        publicationHeaders.setPnIMandatoryHeaders(headersAsMap,
                "GENERAL_PUBLICATION",
                "SJP_PUBLIC_LIST",
                "1234",
                "2022-02-11T09:39:41.362Z",
                "ENGLISH");
    }

    private void setAdditionalHeaders(Map<String, Object> headersAsMap) {
        publicationHeaders.setPnIAdditionalHeaders(headersAsMap,
                "daily-ward",
                "PUBLIC",
                "2022-02-09T10:00:00.368Z",
                "2022-02-14T10:00:00"
                );
    }

    @Disabled("Test fails as the header x-source-artefact-id is incorrectly required as mandatory at P&I end. P&I needs fix this")
   // @Test
    public void testCreatePublicationWithValidMandatoryHeadersAndPayload() {

        setMandatoryHeaders(headersAsMap);

        publicationSteps.createPublicationWithValidHeadersAndPayload(
                pihPublicationRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }

    @Test
    public void testCreatePublicationWithAllValidHeadersAndPayload() {

        setMandatoryHeaders(headersAsMap);
        setAdditionalHeaders(headersAsMap);

        publicationSteps.createPublicationWithValidHeadersAndPayload(
                pihPublicationRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }

    @Test
    public void testCreatePublicationWithDestinationHeaderOnly() {
        Map<String, Object> headersAsMap = new HashMap<>();
        headersAsMap.put("Destination-System", "PIH");
        publicationSteps.createPublicationWithInvalidPayload(pihPublicationRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }

    @Test
    public void tesCreatePublicationWithInvalidHeader() {
        setMandatoryHeaders(headersAsMap);
        publicationHeaders.setAHeader(headersAsMap,"x-type","invalid x-type");

        publicationSteps.createPublicationWithInvalidPayload(pihPublicationRootContext,
                headersAsMap,
                authorizationToken,
                "{}");
    }

    @Test
    public void testCreatePublicationUnauthorized() {
        setMandatoryHeaders(headersAsMap);
        publicationSteps.createPublicationUnauthorized(pihPublicationRootContext,
                headersAsMap,
                "Invalid token 123456",
                "{}");
    }
}
