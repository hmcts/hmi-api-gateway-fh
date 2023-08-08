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
import org.springframework.util.ResourceUtils;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHmiHeader;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings({"java:S2699", "PMD.LawOfDemeter"})
class CancelVideoHearingTest extends FunctionalTest  {

    @Value("${videohearingsRootContext}")
    protected String videoHearingsRootContext;

    private static final String VALID_HEARING_ID_IN_VH_TEST = "933cf0bb-418c-4664-892b-00b56f05fae9";

    static final String CREATE_VH_LISTINGS_PAYLOAD = "requests/create-vh-listings-payload.json";
    private static final String SNL = "SNL";

    @Value("${cancelVideoHearingsRootContext}")
    protected String cancelVideoHearingsRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    void testCancelVideoHearingWithValidHearingIdAndNoPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cancelVideoHearingsRootContext = String.format(cancelVideoHearingsRootContext, VALID_HEARING_ID_IN_VH_TEST);
        callRestEndpointWithPayload(cancelVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "", HttpMethod.PATCH, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCancelVideoHearingWithInvalidHearingIdAndEmptyPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cancelVideoHearingsRootContext = String.format(cancelVideoHearingsRootContext, "123");
        callRestEndpointWithPayload(cancelVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "{\"updated_by\": \"string\"},{\"cancel_reason\": \"string\"}",
                HttpMethod.PATCH, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCancelVideoHearingWithValidHearingIdAndEmptyPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cancelVideoHearingsRootContext = String.format(cancelVideoHearingsRootContext, VALID_HEARING_ID_IN_VH_TEST);
        callRestEndpointWithPayload(cancelVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "{}",
                HttpMethod.PATCH,
                HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCancelVideoHearingWithValidHearingIdAndPayload() throws IOException {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        Response response = callRestEndpointWithPayload(videoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                readFileContents(CREATE_VH_LISTINGS_PAYLOAD),
                HttpMethod.POST, HttpStatus.CREATED);

        String hearingId = getHearingId(response);

        cancelVideoHearingsRootContext = String.format(cancelVideoHearingsRootContext, hearingId);
        callRestEndpointWithPayload(cancelVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "{\"updated_by\": \"string\",\"cancel_reason\": \"string\"}",
                HttpMethod.PATCH,
                HttpStatus.NO_CONTENT);
    }

    private String readFileContents(final String path) throws IOException {
        final File file = ResourceUtils.getFile("classpath:" + path);
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }

    private String getHearingId(Response response) {
        String newlyCreatedHearing = response.getHeader("Location");
        return newlyCreatedHearing.substring(newlyCreatedHearing.lastIndexOf('/') + 1);
    }
}
