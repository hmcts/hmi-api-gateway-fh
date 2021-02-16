package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForDeleteVideoHearing;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("DELETE /video-hearings - Delete Video Hearings")
@SuppressWarnings("java:S2699")
public class DELETE_VideoHearings_UnitTests {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${resourcesApiRootContext}")
    private String resourcesApiRootContext;

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

    @BeforeAll
    void setToken(){
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenURL, scope);
    }

    @BeforeEach
    void initialiseValues() {
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
        headersAsMap.put("Request-Processed-At", "2018-02-29 20:36:01Z");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Valid Headers")
    void testDeleteVideoHearingsForValidHeaders() {
        final Response response = whenDeleteVideoHearingIsInvoked();
        thenValidateResponseForDeleteVideoHearing(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testDeleteVideoHearingsRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        final Response response = whenDeleteHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testDeleteVideoHearingsRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final Response response = whenDeleteHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testDeleteVideoHearingsRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");
        final Response response = whenDeleteHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testDeleteVideoHearingsRequestWithInvalidAcceptHeader() {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final Response response = whenDeleteHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing Access Token")
    void testDeleteVideoHearingsRequestWithMissingAccessToken() {
        final Response response = whenDeleteHearingsRequestIsInvokedWithMissingAccessToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid Access Token")
    void testDeleteVideoHearingsRequestWithInvalidAccessToken() {
        accessToken = TestUtilities.getToken(grantType, invalidClientID, invalidClientSecret, invalidTokenURL, invalidScope);
        final Response response = whenDeleteHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }


    private Response whenDeleteVideoHearingIsInvoked() {
        return sendDeleteRequestForVideoHearing(resourcesApiRootContext + "/video-hearing/VH1234", headersAsMap, targetInstance);
    }

    private Response whenDeleteHearingsRequestIsInvokedWithMissingOrInvalidHeader() {
        return deleteHearingsResponseForMissingOrInvalidHeader(resourcesApiRootContext + "/video-hearing/VH1234", headersAsMap, targetInstance);
    }

    private Response whenDeleteHearingsRequestIsInvokedWithMissingAccessToken() {
        return deleteHearingsResponseForMissingAccessToken(resourcesApiRootContext + "/video-hearing/VH1234", headersAsMap, targetInstance);
    }


    private Response sendDeleteRequestForVideoHearing(final String api, final Map<String, Object> headersAsMap, final String basePath) {
        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }

    private Response deleteHearingsResponseForMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap, final String basePath) {
        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response deleteHearingsResponseForMissingAccessToken(final String api, final Map<String, Object> headersAsMap, final String basePath) {
        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

}
