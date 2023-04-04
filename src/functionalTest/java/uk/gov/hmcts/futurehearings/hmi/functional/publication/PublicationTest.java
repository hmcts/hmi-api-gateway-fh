package uk.gov.hmcts.futurehearings.hmi.functional.publication;

import com.google.common.io.CharStreams;
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
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class PublicationTest extends PihFunctionalTest {

    @Value("${pihPublicationRootContext}")
    private String pihPublicationRootContext;

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
        String data = "";
        try (InputStream mockFile = this.getClass().getClassLoader()
                .getResourceAsStream("uk/gov/hmcts/futurehearings/hmi/functional/" +
                        "Publications.input/POST-Publication-request.json")) {
            try (Reader reader = new InputStreamReader(mockFile)) {
                data = CharStreams.toString(reader);
            }
        }
        setPnIMandatoryHeaders(headersAsMap);
        setPnIAdditionalHeaders(headersAsMap);

        callRestEndpointWithPayload(pihPublicationRootContext,
                headersAsMap,
                authorizationToken,
                data,
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
