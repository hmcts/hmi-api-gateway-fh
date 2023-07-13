package uk.gov.hmcts.futurehearings.hmi.functional.videohearing;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHmiHeader;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings({"java:S2699", "PMD.LawOfDemeter"})
class CloneVideoHearingTest extends FunctionalTest {

    @Value("${targetInstance}")
    protected String TARGET_INSTANCE;

    private String validHearingId = "";

    private static final String SNL = "SNL";

    @Value("${cloneVideoHearingsRootContext}")
    protected String cloneVideoHearingsRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {

        if (TARGET_INSTANCE.contains("staging")) {
            validHearingId = "f761c4ee-3eb8-45f2-b5fe-011bbf800f29";
        } else {
            validHearingId = "9ba41f11-f288-4c3a-b1b2-de0dc0dd59c3";
        }

        super.initialiseValues();
    }

    @Test
    void testCloneVideoHearingWithValidHearingIdAndNoPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, validHearingId);
        callRestEndpointWithPayload(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "", HttpMethod.POST, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCloneVideoHearingWithInvalidHearingIdAndEmptyPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, "123");
        callRestEndpointWithPayload(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.POST, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCloneVideoHearingWithValidHearingIdAndEmptyPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, validHearingId);
        callRestEndpointWithPayload(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.POST,
                HttpStatus.NO_CONTENT);
    }

    @Test
    void testCloneVideoHearingWithValidHearingIdAndPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, validHearingId);
        callRestEndpointWithPayload(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "{\"status\": 0}", HttpMethod.POST,
                HttpStatus.NO_CONTENT);
    }
}
