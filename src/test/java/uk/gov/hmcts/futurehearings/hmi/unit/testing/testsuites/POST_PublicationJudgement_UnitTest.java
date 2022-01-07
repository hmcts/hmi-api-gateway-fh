package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForPublicationRelatedCreation;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForPublicationRelatedCreation;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

import org.springframework.beans.factory.annotation.Value;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("POST /publication/judgement - Create PublicationJudgement")
public class POST_PublicationJudgement_UnitTest {

    private static final String CORRECT_PUBLICATION_JUDGEMENT_PAYLOAD = "requests/create-publication-judgement-payload.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${genericPublicationApiRootContext}")
    private String genericPublicationApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

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

    private final Map<String, Object> headersAsMap = new HashMap<>();
    private final Map<String, String> paramsAsMap = new HashMap<>();

    private HmiHttpClient httpClient;
    private String publicationCtx;

    @BeforeAll
    void setToken(){
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenURL, scope);
        this.httpClient = new HmiHttpClient(accessToken, targetInstance);
        publicationCtx = String.format(genericPublicationApiRootContext, "judgement");
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
    void testCreatePublicationJudgementForInvalidResource() throws IOException {
        final String input = givenAPayload(CORRECT_PUBLICATION_JUDGEMENT_PAYLOAD);
        final Response response = httpClient.httpPost(genericPublicationApiRootContext +"/linkedHearingGrouppost", headersAsMap, paramsAsMap, input);
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header for publication")
    void testCreatePublicationJudgementWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(CORRECT_PUBLICATION_JUDGEMENT_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }
    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header for publication")
    void testCreatePublicationJudgementWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final String input = givenAPayload(CORRECT_PUBLICATION_JUDGEMENT_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header for publication")
    void testCreatePublicationJudgementWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(CORRECT_PUBLICATION_JUDGEMENT_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header for publication")
    void testCreatePublicationJudgementWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final String input = givenAPayload(CORRECT_PUBLICATION_JUDGEMENT_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Order(6)
    @ParameterizedTest(name = "Test for missing {0} header for publication")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At"})
    void testCreatePublicationJudgementWithMissingHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        final String input = givenAPayload(CORRECT_PUBLICATION_JUDGEMENT_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(7)
    @ParameterizedTest(name = "Test for invalid {0} header for publication")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At"})
    void testCreatePublicationJudgementWithInvalidHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");
        final String input = givenAPayload(CORRECT_PUBLICATION_JUDGEMENT_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(8)
    @DisplayName("Test for Correct Headers for publication")
    void testCreatePublicationJudgementWithCorrectHeaders() throws IOException {
        final String input = givenAPayload(CORRECT_PUBLICATION_JUDGEMENT_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForPublicationRelatedCreation(response);
    }

    @Test
    @Order(9)
    @DisplayName("Test for missing Access Token for publication")
    void testCreatePublicationJudgementWithMissingAccessToken() throws IOException {
        final String input = givenAPayload(CORRECT_PUBLICATION_JUDGEMENT_PAYLOAD);
        final Response response = createResourceNoAuth(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(10)
    @DisplayName("Test for invalid Access Token for publication")
    void testCreatePublicationJudgementWithInvalidAccessToken() throws IOException {
        accessToken = "TestUtilities.getToken(grantType, invalidClientID, invalidClientSecret, invalidTokenURL, invalidScope)";
        httpClient.setAccessToken(accessToken);
        final String input = givenAPayload(CORRECT_PUBLICATION_JUDGEMENT_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private Response createResourceNoAuth(final String input) {
        return httpClient.httpPostNoAuth(publicationCtx, headersAsMap, paramsAsMap, input);
    }

    private Response createResource(final String input) {
        return httpClient.httpPost(publicationCtx, headersAsMap, paramsAsMap, input);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }

}
