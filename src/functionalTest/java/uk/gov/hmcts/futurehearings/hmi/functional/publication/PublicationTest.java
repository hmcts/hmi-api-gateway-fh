package uk.gov.hmcts.futurehearings.hmi.functional.publication;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.publication.steps.PublicationSteps;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"Testing the Publication API is working correctly"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class PublicationTest extends PihFunctionalTest {

    @Value("${pihPublicationRootContext}")
    private String pihPublicationRootContext;

    @Steps
    PublicationHeaders publicationHeaders;

    @Steps
    PublicationSteps publicationSteps;

    private static final LocalDateTime CURRENT_DATETIME = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    @BeforeEach
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }


    private void setPnIMandatoryHeaders(Map<String, Object> headersAsMap) {
        publicationHeaders.setPnIMandatoryHeaders(headersAsMap,
                "LIST",
                "CARE_STANDARDS_LIST",
                "5555",
                CURRENT_DATETIME.toString(),
                "ENGLISH");
    }

    private void setPnIAdditionalHeaders(Map<String, Object> headersAsMap) {
        publicationHeaders.setPnIAdditionalHeaders(headersAsMap,
                "PUBLIC",
                CURRENT_DATETIME.toString(),
                CURRENT_DATETIME.plusDays(1).toString()
        );
    }

    /**
     * Successful create publication test to P&I.
     */
    @Test
    public void testCreatePublicationWithAllValidHeadersAndPayload() throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(Files.newInputStream(Paths.get("src/functionalTest/"
                + "resources/uk/gov/hmcts/futurehearings/hmi/functional/"
                + "Publications.input/POST-Publication-request.json")),
                writer,
                Charset.defaultCharset()
        );
        setPnIMandatoryHeaders(headersAsMap);
        setPnIAdditionalHeaders(headersAsMap);

        publicationSteps.createPublicationWithValidHeadersAndPayload(
                pihPublicationRootContext,
                headersAsMap,
                authorizationToken,
                writer.toString()
        );
    }

    /**
     * Invalid header test to P&I.
     */
    @Test
    public void tesCreatePublicationWithInvalidHeader() {
        setPnIMandatoryHeaders(headersAsMap);
        publicationHeaders.setAHeader(headersAsMap, "x-type", "invalid x-type");

        publicationSteps.createPublicationWithInvalidPayload(pihPublicationRootContext,
                headersAsMap,
                authorizationToken,
                "{}"
        );
    }

    /**
     * Unauthorised test to P&I.
     */
    @Test
    public void testCreatePublicationUnauthorized() {
        setPnIMandatoryHeaders(headersAsMap);
        publicationSteps.createPublicationUnauthorized(pihPublicationRootContext,
                headersAsMap,
                "Invalid token 123456",
                "{}"
        );
    }
}
