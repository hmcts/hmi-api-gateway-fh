package uk.gov.hmcts.futurehearings.hmi.functional.videohearing;

import io.restassured.response.Response;
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

import java.io.IOException;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.TestingUtils.getHearingId;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.TestingUtils.readFileContents;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHmiHeader;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings({"java:S2699", "PMD.LawOfDemeter"})
class AddVideoHearingJohTest extends FunctionalTest {

    @Value("${videohearingsRootContext}")
    protected String videoHearingsRootContext;

    private static final String VALID_HEARING_ID_IN_VH_TEST = "933cf0bb-418c-4664-892b-00b56f05fae9";

    static final String CREATE_VH_LISTINGS_PAYLOAD = "requests/create-vh-listings-payload.json";

    static final String CREATE_VH_LISTINGS_JOH_PAYLOAD = "requests/create-vh-joh-listings-payload.json";
    private static final String SNL = "SNL";

    @Value("${johVideoHearingRootContext}")
    protected String johVideoHearingRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    void testAddVideoHearingJohWithValidHearingIdAndNoPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        johVideoHearingRootContext = String.format(johVideoHearingRootContext, VALID_HEARING_ID_IN_VH_TEST);
        callRestEndpointWithPayload(johVideoHearingRootContext,
                headersAsMap,
                authorizationToken,
                "", HttpMethod.POST, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testAddVideoHearingJohWithInvalidHearingIdAndEmptyPayload() throws IOException {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        johVideoHearingRootContext = String.format(johVideoHearingRootContext, "123");
        callRestEndpointWithPayload(johVideoHearingRootContext,
                headersAsMap,
                authorizationToken,
                readFileContents(CREATE_VH_LISTINGS_JOH_PAYLOAD),
                HttpMethod.POST, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testAddVideoHearingJohWithValidHearingIdAndEmptyPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        johVideoHearingRootContext = String.format(johVideoHearingRootContext, VALID_HEARING_ID_IN_VH_TEST);
        callRestEndpointWithPayload(johVideoHearingRootContext,
                headersAsMap,
                authorizationToken,
                "{}",
                HttpMethod.POST,
                HttpStatus.BAD_REQUEST);
    }

    @Test
    void testAddVideoHearingJohWithValidHearingIdAndPayload() throws IOException {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        Response response = callRestEndpointWithPayload(videoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                readFileContents(CREATE_VH_LISTINGS_PAYLOAD),
                HttpMethod.POST, HttpStatus.CREATED);

        String hearingId = getHearingId(response);

        johVideoHearingRootContext = String.format(johVideoHearingRootContext, hearingId);
        callRestEndpointWithPayload(johVideoHearingRootContext,
                headersAsMap,
                authorizationToken,
                readFileContents(CREATE_VH_LISTINGS_JOH_PAYLOAD),
                HttpMethod.POST,
                HttpStatus.NOT_FOUND);
    }
}
