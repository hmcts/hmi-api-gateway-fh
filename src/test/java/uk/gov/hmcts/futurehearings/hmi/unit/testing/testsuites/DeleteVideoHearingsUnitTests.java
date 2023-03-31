package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForDeleteVideoHearing;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("DELETE /video-hearings - Delete Video Hearings")
@SuppressWarnings({"java:S2699", "PMD.TooManyMethods"})
class DeleteVideoHearingsUnitTests {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${resourcesApiRootContext}")
    private String resourcesApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

    @Value("${tokenURL}")
    private String tokenUrl;

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
    private String invalidTokenUrl;

    @Value("${invalidScope}")
    private String invalidScope;

    @Value("${invalidClientID}")
    private String invalidClientID;

    @Value("${invalidClientSecret}")
    private String invalidClientSecret;

    private final Map<String, Object> headersAsMap = new ConcurrentHashMap<>();

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ACCEPT = "Accept";

    @BeforeAll
    void setToken() {
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenUrl, scope);
    }

    @BeforeEach
    void initialiseValues() {
        headersAsMap.put(CONTENT_TYPE, "application/json");
        headersAsMap.put(ACCEPT, "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");
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
        headersAsMap.remove(CONTENT_TYPE);
        final Response response = whenDeleteHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testDeleteVideoHearingsRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);
        headersAsMap.put(CONTENT_TYPE, "application/xml");
        final Response response = whenDeleteHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testDeleteVideoHearingsRequestWithMissingAcceptHeader() {
        headersAsMap.remove(ACCEPT);
        final Response response = whenDeleteHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testDeleteVideoHearingsRequestWithInvalidAcceptHeader() {
        headersAsMap.remove(ACCEPT);
        headersAsMap.put(ACCEPT, "application/jsonxml");
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
        accessToken = TestUtilities.getToken(grantType, invalidClientID,
                invalidClientSecret, invalidTokenUrl, invalidScope);
        final Response response = whenDeleteHearingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }


    private Response whenDeleteVideoHearingIsInvoked() {
        return sendDeleteRequestForVideoHearing(resourcesApiRootContext + "/video-hearing/VH1234",
                headersAsMap, targetInstance);
    }

    private Response whenDeleteHearingsRequestIsInvokedWithMissingOrInvalidHeader() {
        return deleteHearingsResponseForMissingOrInvalidHeader(resourcesApiRootContext + "/video-hearing/VH1234",
                headersAsMap, targetInstance);
    }

    private Response whenDeleteHearingsRequestIsInvokedWithMissingAccessToken() {
        return deleteHearingsResponseForMissingAccessToken(resourcesApiRootContext + "/video-hearing/VH1234",
                headersAsMap, targetInstance);
    }


    private Response sendDeleteRequestForVideoHearing(final String api,
        final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();
    }

    private Response deleteHearingsResponseForMissingOrInvalidHeader(final String api,
        final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response deleteHearingsResponseForMissingAccessToken(final String api,
        final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }
}
