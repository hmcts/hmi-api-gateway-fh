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
@SuppressWarnings("java:S2699")
public class CloneVideoHearingTest extends FunctionalTest {

    private static final String VALID_HEARING_ID_IN_VH_TEST = "bab5ccb9-1cc5-4a24-9d26-b9c0aafaf43f";
    private static final String SNL = "SNL";

    @Value("${cloneVideoHearingsRootContext}")
    protected String cloneVideoHearingsRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    public void testCloneVideoHearingWithValidHearingIdAndNoPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, VALID_HEARING_ID_IN_VH_TEST);
        callRestEndpointWithPayload(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "", HttpMethod.POST, HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCloneVideoHearingWithInvalidHearingIdAndEmptyPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, "123");
        callRestEndpointWithPayload(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.POST, HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCloneVideoHearingWithValidHearingIdAndEmptyPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, VALID_HEARING_ID_IN_VH_TEST);
        callRestEndpointWithPayload(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.POST,
                HttpStatus.NO_CONTENT);
    }

    @Test
    public void testCloneVideoHearingWithValidHearingIdAndPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, VALID_HEARING_ID_IN_VH_TEST);
        callRestEndpointWithPayload(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "{\"status\": 0}", HttpMethod.POST,
                HttpStatus.NO_CONTENT);
    }
}
