package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;
import org.springframework.beans.factory.annotation.Value;

import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForCreate;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("POST /resources/linked-hearing-group - Create LinkedHearingGroup Resource")
public class POST_Resources_LinkedHearingGrp_UnitTests {

    private static final String CORRECT_CREATE_LINKED_HEARING_GROUP_RESOURCE_PAYLOAD = "requests/create-resources-linked-hearing-group-payload.json";

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${resourcesApiRootContext}")
    private String resourcesApiRootContext;

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

    @BeforeAll
    void setToken(){
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenURL, scope);
        this.httpClient = new HmiHttpClient(accessToken, targetInstance);
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
    @DisplayName("Test for Invalid Resource for linked-hearing-group")
    void testCreateLinkedHearingGroupResourceForInvalidResource() throws IOException {
        final String input = givenAPayload(CORRECT_CREATE_LINKED_HEARING_GROUP_RESOURCE_PAYLOAD);
        final Response response = httpClient.httpPost(resourcesApiRootContext+"/linkedHearingGrouppost", headersAsMap, paramsAsMap, input);
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header for linked-hearing-group")
    void testCreateLinkedHearingGroupResourceWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(CORRECT_CREATE_LINKED_HEARING_GROUP_RESOURCE_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }
    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header for linked-hearing-group")
    void testCreateLinkedHearingGroupResourceWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final String input = givenAPayload(CORRECT_CREATE_LINKED_HEARING_GROUP_RESOURCE_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header for linked-hearing-group")
    void testCreateLinkedHearingGroupResourceWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(CORRECT_CREATE_LINKED_HEARING_GROUP_RESOURCE_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header for linked-hearing-group")
    void testCreateLinkedHearingGroupResourceWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final String input = givenAPayload(CORRECT_CREATE_LINKED_HEARING_GROUP_RESOURCE_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Order(6)
    @ParameterizedTest(name = "Test for missing {0} header for linked-hearing-group")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At"})
    void testCreateLinkedHearingGroupResourceWithMissingHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        final String input = givenAPayload(CORRECT_CREATE_LINKED_HEARING_GROUP_RESOURCE_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(7)
    @ParameterizedTest(name = "Test for invalid {0} header for linked-hearing-group")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At"})
    void testCreateLinkedHearingGroupResourceWithInvalidHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");
        final String input = givenAPayload(CORRECT_CREATE_LINKED_HEARING_GROUP_RESOURCE_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(8)
    @DisplayName("Test for Correct Headers for linked-hearing-group")
    void testCreateLinkedHearingGroupResourceWithCorrectHeaders() throws IOException {
        final String input = givenAPayload(CORRECT_CREATE_LINKED_HEARING_GROUP_RESOURCE_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForCreate(response);
    }

    @Test
    @Order(9)
    @DisplayName("Test for missing Access Token for linked-hearing-group")
    void testCreateLinkedHearingGroupResourceWithMissingAccessToken() throws IOException {
        final String input = givenAPayload(CORRECT_CREATE_LINKED_HEARING_GROUP_RESOURCE_PAYLOAD);
        final Response response = createResourceNoAuth(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(10)
    @DisplayName("Test for invalid Access Token for linked-hearing-group")
    void testCreateLinkedHearingGroupResourceWithInvalidAccessToken() throws IOException {
        accessToken = TestUtilities.getToken(grantType, invalidClientID, invalidClientSecret, invalidTokenURL, invalidScope);
        httpClient.setAccessToken(accessToken);
        final String input = givenAPayload(CORRECT_CREATE_LINKED_HEARING_GROUP_RESOURCE_PAYLOAD);
        final Response response = createResource(input);
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private Response createResourceNoAuth(final String input) {
        return httpClient.httpPostNoAuth(resourcesApiRootContext+"/linked-hearing-group", headersAsMap, paramsAsMap, input);
    }

    private Response createResource(final String input) {
        return httpClient.httpPost(resourcesApiRootContext+"/linked-hearing-group", headersAsMap, paramsAsMap, input);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }
}
