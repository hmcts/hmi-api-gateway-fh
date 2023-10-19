package uk.gov.hmcts.futurehearings.hmi.functional.videohearing;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("functional")
@SuppressWarnings({"java:S2699", "PMD.LawOfDemeter"})
class CloneVideoHearingTest extends FunctionalTest {

    @Value("${targetInstance}")
    protected String targetInstance;

    private String validHearingId = "";

    private static final String SNL = "SNL";

    @Value("${videohearingsRootContext}")
    protected String videoHearingsRootContext;

    @Value("${cloneVideoHearingsRootContext}")
    protected String cloneVideoHearingsRootContext;

    static final String CREATE_VH_LISTINGS_PAYLOAD = "requests/create-vh-listings-payload.json";

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    @Order(1)
    void testCloneVideoHearingWithValidHearingIdAndNoPayload() throws IOException {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        Response response = callRestEndpointWithPayload(videoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                readFileContents(CREATE_VH_LISTINGS_PAYLOAD),
                HttpMethod.POST, HttpStatus.CREATED);

        validHearingId = getHearingId(response);
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, validHearingId);
        callRestEndpointWithPayload(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "", HttpMethod.POST, HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(2)
    void testCloneVideoHearingWithInvalidHearingIdAndEmptyPayload() {
        headersAsMap = createStandardHmiHeader(SNL, "VH");
        cloneVideoHearingsRootContext = String.format(cloneVideoHearingsRootContext, "123");
        callRestEndpointWithPayload(cloneVideoHearingsRootContext,
                headersAsMap,
                authorizationToken,
                "{}", HttpMethod.POST, HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(3)
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
    @Order(4)
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
