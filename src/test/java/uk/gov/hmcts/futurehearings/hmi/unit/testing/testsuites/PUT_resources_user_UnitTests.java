package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForInvalidSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForUpdate;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("PUT /resources/user - Update User Resource")
public class PUT_resources_user_UnitTests {

    public static final String CORRECT_UPDATE_USER_RESOURCE_PAYLOAD = "requests/correct-update-user-resource-payload.json";
    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${resourcesApiRootContext}")
    private String resourcesApiRootContext;

    private final Map<String, Object> headersAsMap = new HashMap<>();

    @BeforeEach
    public void initialiseValues() {

        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", "S&L");
        headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
        headersAsMap.put("Request-Processed-At", "2018-02-29 20:36:01Z");
        headersAsMap.put("Request-Type", "THEFT");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    public void testUpdateUserResourceForInvalidResource() throws IOException {
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedForInvalidResource(input);
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    public void testUpdateUserResourceWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }
    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    public void testUpdateUserResourceWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    public void testUpdateUserResourceWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    public void testUpdateUserResourceWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing OcpSubKey")
    public void testUpdateUserResourceRequestWithMissingOcpSubKey() throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateHearingIsInvokedWithMissingOrInvalidOcSubKey(input);
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    public void testUpdateUserResourceRequestWithInvalidOcpSubKey()throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateHearingIsInvokedWithMissingOrInvalidOcSubKey(input);
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }

    @Test
    @Order(8)
    @DisplayName("Test for missing Source-System")
    public void testUpdateUserResourceRequestWithMissingSrcHeader() throws IOException {
        headersAsMap.remove("Source-System");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }


    @Test
    @Order(9)
    @DisplayName("Test for invalid Source-System header")
    public void testUpdateUserResourceRequestWithInvalidSourceSystemHeader() throws IOException {
        headersAsMap.remove("Source-System");
        headersAsMap.put("Source-System", "A");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }

    @Test
    @Order(10)
    @DisplayName("Test for missing Destination-System")
    public void testUpdateUserResourceRequestWithMissingHeaderDestination() throws IOException {
        headersAsMap.remove("Destination-System");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }

    @Test
    @Order(11)
    @DisplayName("Test for invalid Destination-System header")
    public void testUpdateUserResourceRequestWithInvalidDestinationSystemHeader() throws IOException {
        headersAsMap.remove("Destination-System");
        headersAsMap.put("Destination-System", "A");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }


    @Test
    @Order(12)
    @DisplayName("Test for missing Request-Created-At")
    public void testUpdateUserResourceRequestWithMissingHeaderDateTime() throws IOException {
        headersAsMap.remove("Request-Created-At");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }

    @Test
    @Order(13)
    @DisplayName("Test for missing Request-Processed-At header")
    public void testUpdateUserResourceRequestWithMissingRequestCreatedAtHeader() throws IOException {
        headersAsMap.remove("Request-Processed-At");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }

    @Test
    @Order(14)
    @DisplayName("Test for invalid Request-Created-At header")
    public void testUpdateUserResourceRequestWithInvalidRequestCreatedAtHeader() throws IOException {
        headersAsMap.remove("Request-Created-At");
        headersAsMap.put("Request-Created-At", "2018-01-29A20:36:01Z");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }

    @Test
    @Order(15)
    @DisplayName("Test for invalid Request-Processed-At header")
    public void testUpdateUserResourceRequestWithInvalidRequestProcessedAtHeader() throws IOException {
        headersAsMap.remove("Request-Processed-At");
        headersAsMap.put("Request-Processed-At", "2018-02-29A20:36:01Z");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }


    @Test
    @Order(16)
    @DisplayName("Test for missing Request-Type")
    public void testUpdateUserResourceRequestWithMissingRequestTypeHeader() throws IOException {
        headersAsMap.remove("Request-Type");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }

    @Test
    @Order(17)
    @DisplayName("Test for invalid Request-Type header")
    public void testUpdateUserResourceRequestWithInvalidRequestTypeHeader() throws IOException {
        headersAsMap.remove("Request-Type");
        headersAsMap.put("Request-Type", "A");
        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(input);
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }

    @Test
    @Order(18)
    @DisplayName("Test for correct Request")
    public void testUpdateUserResourceRequestWithCorrectRequest() throws IOException {

        final String input = givenAPayload(CORRECT_UPDATE_USER_RESOURCE_PAYLOAD);
        final Response response = whenUpdateHearingIsInvokedWithCorrectRequest(input);
        thenValidateResponseForUpdate(response);
    }

    private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
    }

    private Response whenUpdateHearingIsInvokedWithCorrectRequest(final String input) {
        return updateUserResourceResponseForCorrectRequest(resourcesApiRootContext + "/user", headersAsMap, targetInstance, input);
    }

    private Response whenUpdateUserResourceIsInvokedWithMissingOrInvalidHeader(final String input) {
        return updateUserResourceResponseForAMissingOrInvalidHeader(resourcesApiRootContext + "/user", headersAsMap, targetInstance, input);
    }

    private Response whenUpdateHearingIsInvokedWithMissingOrInvalidOcSubKey(final String input) {
        return updateUserResourceResponseForAMissingOrInvalidOcpSubKey(resourcesApiRootContext + "/user", headersAsMap, targetInstance, input);
    }

    private Response whenUpdateUserResourceIsInvokedForInvalidResource(final String input) {
        return updateUserResourceResponseForInvalidResource(resourcesApiRootContext+"Put", headersAsMap, targetInstance, input);
    }

    private Response updateUserResourceResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

    private Response updateUserResourceResponseForCorrectRequest(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return   given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response updateUserResourceResponseForAMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }

    private Response updateUserResourceResponseForAMissingOrInvalidOcpSubKey(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return  given()
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }
}
