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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HealthResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("GET /reservations - Retrieve Reservations")
class GetReservationsUnitTests {
    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${reservationsApiRootContext}")
    private String reservationsApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

    private final Map<String, Object> headersAsMap = new ConcurrentHashMap<>();
    private final Map<String, String> paramsAsMap = new ConcurrentHashMap<>();

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
    @DisplayName("Test for missing ContentType header")
    void testRetrieveListingsRequestWithMissingContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);
        final Response response = whenGetReservationsIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for invalid ContentType header")
    void testRetrieveListingsRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove(CONTENT_TYPE);
        headersAsMap.put(CONTENT_TYPE, "application/xml");

        final Response response = whenGetReservationsIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for missing Accept header")
    void testRetrieveListingsRequestWithMissingAcceptHeader() {
        headersAsMap.remove(ACCEPT);
        final Response response = whenGetReservationsIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for invalid Accept header")
    void testRetrieveListingsRequestWithInvalidAcceptHeader() {
        headersAsMap.remove(ACCEPT);
        headersAsMap.put(ACCEPT, "application/jsonxml");
        final Response response = whenGetReservationsIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Order(5)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {"Source-System", "Destination-System", "Request-Created-At"})
    void testRetrieveListingsRequestWithMissingHeader(final String iteration) {
        headersAsMap.remove(iteration);
        final Response response = whenGetReservationsIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(6)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {"Source-System", "Destination-System", "Request-Created-At"})
    void testRetrieveListingsRequestWithInvalidHeader(final String iteration) {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");
        final Response response = whenGetReservationsIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    private Response whenGetReservationsIsInvokedWithMissingOrInvalidHeader() {
        return retrieveReservationsResponseForMissingOrInvalidHeader(
                reservationsApiRootContext, headersAsMap, paramsAsMap, targetInstance);
    }

    private Response retrieveReservationsResponseForMissingOrInvalidHeader(final String api,
        final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }
}
