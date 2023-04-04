package uk.gov.hmcts.futurehearings.hmi.functional.publication;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class PublicationTest extends PihFunctionalTest {

    @Value("${pihPublicationRootContext}")
    private String publicationAndInformationRootContext;

    private static final LocalDateTime CURRENT_DATETIME = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    @BeforeEach
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }


    private void setPnIMandatoryHeaders(Map<String, Object> headersAsMap) {
        PublicationHeaders.setPnIMandatoryHeaders(headersAsMap,
                "LIST",
                "CARE_STANDARDS_LIST",
                "5555",
                CURRENT_DATETIME.toString(),
                "ENGLISH");
    }

    private void setPnIAdditionalHeaders(Map<String, Object> headersAsMap) {
        PublicationHeaders.setPnIAdditionalHeaders(headersAsMap,
                "PUBLIC",
                CURRENT_DATETIME.toString(),
                CURRENT_DATETIME.plusDays(1).toString()
        );
    }

    @Test
    public void testCreatePublicationWithAllValidHeadersAndPayload() throws IOException {
        String fileText;
        try (InputStream mockFile = Files.newInputStream(Paths.get("src/functionalTest/"
                + "resources/uk/gov/hmcts/futurehearings/hmi/functional/"
                + "Publications.input/POST-Publication-request.json"))) {
            fileText = new String(mockFile.readAllBytes(), StandardCharsets.UTF_8);
        }
        setPnIMandatoryHeaders(headersAsMap);
        setPnIAdditionalHeaders(headersAsMap);

        callRestEndpointWithPayload(pihPublicationRootContext,
                headersAsMap,
                authorizationToken,
                fileText,
                HttpMethod.POST,
                HttpStatus.CREATED);
    }

    @Test
    public void tesCreatePublicationWithInvalidHeader() {
        setPnIMandatoryHeaders(headersAsMap);
        PublicationHeaders.setAHeader(headersAsMap, "x-type", "invalid x-type");

        callRestEndpointWithPayload(pihPublicationRootContext,
                headersAsMap,
                authorizationToken,
                "{}",
                HttpMethod.POST,
                HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCreatePublicationUnauthorized() {
        setPnIMandatoryHeaders(headersAsMap);
        callRestEndpointWithPayload(pihPublicationRootContext,
                headersAsMap,
                "Invalid token 123456",
                "{}",
                HttpMethod.POST,
                HttpStatus.UNAUTHORIZED);
    }
}
