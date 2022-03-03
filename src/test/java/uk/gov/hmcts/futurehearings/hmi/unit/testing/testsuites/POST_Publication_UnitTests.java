package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.PubHubResponseVerifier.thenValidateResponseForPost;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;

import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HmiHttpClient;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("POST /pih/publication - Create/Update Publication")
public class POST_Publication_UnitTests {
    private static final String CORRECT_UPDATE_PUBLICATION_PAYLOAD = "requests/create-publication-payload.json";
    private static final String CORRECT_UPDATE_PUBLICATION_PDF_PAYLOAD = "requests/publication.pdf";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${genericPublicationApiRootContext}")
    private String genericPublicationApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

    private final Map<String, Object> headersAsMap = new HashMap<>();
    private final Map<String, String> paramsAsMap = new HashMap<>();

    @Value("${tokenURL}")
    private String tokenURL;

    @Value("${clientID}")
    private String clientID;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${scope}")
    private String scope;

    @Value("${grantType}")
    private String grantType;

    private static String accessToken;

    @Value("${invalidTokenURL}")
    private String invalidTokenURL;

    @Value("${invalidScope}")
    private String invalidScope;

    @Value("${invalidClientID}")
    private String invalidClientID;

    @Value("${invalidClientSecret}")
    private String invalidClientSecret;

    private HmiHttpClient httpClient;
    private String linkedHearingGroupCtx;

    @BeforeAll
    void setToken(){
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenURL, scope);
        this.httpClient = new HmiHttpClient(accessToken, targetInstance);
        linkedHearingGroupCtx = String.format(genericPublicationApiRootContext, "publication");
    }

    @BeforeEach
    void initialiseValues() {
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource for publication")
    void testUpdatePublicationForInvalidResource() throws IOException {
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_PAYLOAD);
        final Response response = httpClient.httpPost(genericPublicationApiRootContext +"/linkedHearingGrouppost", headersAsMap, paramsAsMap, input);
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header for publication")
    void testUpdatePublicationWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }
    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header for publication")
    void testUpdatePublicationWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header for publication")
    void testUpdatePublicationWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header for publication")
    void testUpdatePublicationWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Order(6)
    @ParameterizedTest(name = "Test for missing {0} header for publication")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At"})
    void testUpdatePublicationWithMissingHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(7)
    @ParameterizedTest(name = "Test for invalid {0} header for publication")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At"})
    void testUpdatePublicationWithInvalidHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(8)
    @DisplayName("Test for Correct Headers for publication")
    void testUpdatePublicationWithCorrectHeaders() throws IOException {
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForPost(response);
    }

    @Test
    @Order(9)
    @DisplayName("Test for Correct Content-Type Headers for publication")
    void testUpdatePublicationWithCorrectContentTypeHeaders() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "multipart/form-data");
        final File input = givenAPDFPayload(CORRECT_UPDATE_PUBLICATION_PDF_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForPost(response);
    }

    @Test
    @Order(10)
    @DisplayName("Test for missing Access Token for publication")
    void testUpdatePublicationWithMissingAccessToken() throws IOException {
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_PAYLOAD);
        final Response response = createResourceNoAuth(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(11)
    @DisplayName("Test for invalid Access Token for publication")
    void testUpdatePublicationWithInvalidAccessToken() throws IOException {
        httpClient.setAccessToken("accessToken");
        final String input = givenAPayload(CORRECT_UPDATE_PUBLICATION_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private Response createResourceNoAuth(final String input) {
        return httpClient.httpPostNoAuth(linkedHearingGroupCtx, headersAsMap, paramsAsMap, input);
    }

    private Response createResource(final String input) {
        return httpClient.httpPost(linkedHearingGroupCtx, headersAsMap, paramsAsMap, input);
    }

    private Response createResource(final File input) {
        return httpClient.httpPostMultiPart(linkedHearingGroupCtx, headersAsMap, paramsAsMap, input);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }

    private File givenAPDFPayload(String path) throws IOException{
        return ResourceUtils.getFile("classpath:" + path);
    }
}

