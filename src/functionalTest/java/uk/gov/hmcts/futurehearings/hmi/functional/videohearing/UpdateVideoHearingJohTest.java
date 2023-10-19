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
class UpdateVideoHearingJohTest extends FunctionalTest {

    @Value("${videohearingsRootContext}")
    protected String videoHearingsRootContext;

    private static final String VALID_HEARING_ID_IN_VH_TEST = "933cf0bb-418c-4664-892b-00b56f05fae9";

    static final String CREATE_VH_LISTINGS_PAYLOAD = "requests/create-vh-listings-payload.json";

    static final String UPDATE_VH_LISTINGS_JOH_PAYLOAD = "requests/update-vh-joh-listings-payload.json";

    static final String VH_JOH_ID = "123";
    private static final String SNL = "SNL";

    @Value("${joh_idVideoHearingRootContext}")
    protected String johUpdateVideoHearingRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    void testUpdateVideoHearingJohWithValidHearingIdAndNoPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        johUpdateVideoHearingRootContext =
                String.format(johUpdateVideoHearingRootContext, VALID_HEARING_ID_IN_VH_TEST, VH_JOH_ID);
        callRestEndpointWithPayload(johUpdateVideoHearingRootContext,
                headersAsMap,
                authorizationToken,
                "", HttpMethod.PATCH, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUpdateVideoHearingJohWithInvalidHearingIdAndEmptyPayload() throws IOException {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        johUpdateVideoHearingRootContext =
                String.format(johUpdateVideoHearingRootContext, VALID_HEARING_ID_IN_VH_TEST, VH_JOH_ID);
        callRestEndpointWithPayload(johUpdateVideoHearingRootContext,
                headersAsMap,
                authorizationToken,
                readFileContents(UPDATE_VH_LISTINGS_JOH_PAYLOAD),
                HttpMethod.PATCH, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUpdateVideoHearingJohWithValidHearingIdAndEmptyPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        johUpdateVideoHearingRootContext =
                String.format(johUpdateVideoHearingRootContext, VALID_HEARING_ID_IN_VH_TEST, VH_JOH_ID);
        callRestEndpointWithPayload(johUpdateVideoHearingRootContext,
                headersAsMap,
                authorizationToken,
                "{}",
                HttpMethod.PATCH,
                HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUpdateAndDeleteVideoHearingJohWithValidHearingIdAndPayload() throws IOException {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        Response response = callRestEndpointWithPayload(videoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                readFileContents(CREATE_VH_LISTINGS_PAYLOAD),
                HttpMethod.POST, HttpStatus.CREATED);

        String hearingId = getHearingId(response);

        johUpdateVideoHearingRootContext = String.format(johUpdateVideoHearingRootContext, hearingId, VH_JOH_ID);
        callRestEndpointWithPayload(johUpdateVideoHearingRootContext,
                headersAsMap,
                authorizationToken,
                readFileContents(UPDATE_VH_LISTINGS_JOH_PAYLOAD),
                HttpMethod.PATCH,
                HttpStatus.NOT_FOUND);

        callRestEndpointWithPayload(johUpdateVideoHearingRootContext,
                headersAsMap,
                authorizationToken,
                readFileContents(UPDATE_VH_LISTINGS_JOH_PAYLOAD),
                HttpMethod.DELETE,
                HttpStatus.NOT_FOUND);
    }
}
