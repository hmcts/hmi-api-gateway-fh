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
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateInvalidResponseForGetPeopleByParams;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForGetPeopleById;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForGetPeopleByIdWithInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("GET /people - Retrieve People")
@SuppressWarnings({"PMD.TooManyMethods"})
class GetPeopleUnitTests {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${elinksApiRootContext}")
    private String listingsApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

    private final Map<String, Object> headersAsMap = new ConcurrentHashMap<>();
    private final Map<String, Object> queryParams = new ConcurrentHashMap<>();

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

    @BeforeAll
    void setToken() {
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenUrl, scope);
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
    @DisplayName("Test for Valid Path Param")
    void testRetrievePeopleForValidPathParams() {
        final Response response = whenGetPeopleRequestIsInvokedForWithPathParam();
        thenValidateResponseForGetPeopleById(response);
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Header for Get People with ID")
    void testRetrievePeopleForInvalidHeader() {
        headersAsMap.remove("Request-Created-At");
        headersAsMap.put("Request-Created-At", "invalid date");
        final Response response = whenGetPeopleRequestIsInvokedForWithPathParam();
        thenValidateResponseForGetPeopleByIdWithInvalidHeader(response);
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid AccessToken for Get People with ID")
    void testRetrievePeopleForInvalidAccessToken() {
        final Response response = whenGetPeopleRequestIsInvokedForWithInvalidAcessToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for Invalid Query Params")
    void testGetPeopleForInvalidQueryParams() {
        queryParams.clear();
        queryParams.put("per_page", "100");
        queryParams.put("page", "2");
        final Response response = whenGetPeopleRequestIsInvokedForWithQueryParam();
        thenValidateInvalidResponseForGetPeopleByParams(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for Invalid Query Params")
    void testGetPeopleForInvalidNoOfQueryParams() {
        queryParams.clear();
        queryParams.put("updated_since", "PID012");
        queryParams.put("per_page", "100");
        queryParams.put("page", "2");
        queryParams.put("OneMore", "OneMore");
        final Response response = whenGetPeopleRequestIsInvokedForWithQueryParam();
        thenValidateInvalidResponseForGetPeopleByParams(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for Invalid AccessToken for Get People with ID")
    void testRetrievePeopleWithParamsForInvalidAccessToken() {
        final Response response = whenGetPeopleRequestIsInvokedForWithQueryParamAnInvalidToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
    }

    private Response whenGetPeopleRequestIsInvokedForWithQueryParam() {
        return retrievePeopleForValidQueryParams(listingsApiRootContext, headersAsMap,
                queryParams, targetInstance);
    }

    private Response whenGetPeopleRequestIsInvokedForWithQueryParamAnInvalidToken() {
        return retrievePeopleForValidQueryParamsWithInvalidToken(listingsApiRootContext, headersAsMap,
                queryParams, targetInstance);
    }

    private Response whenGetPeopleRequestIsInvokedForWithPathParam() {
        return retrievePeopleForValidId(listingsApiRootContext + "/PID012", headersAsMap, targetInstance);
    }

    private Response whenGetPeopleRequestIsInvokedForWithInvalidAcessToken() {
        return retrievePeopleForValidIdWithInvalidAccessToken(listingsApiRootContext + "/PID012",
                headersAsMap, targetInstance);
    }

    private Response retrievePeopleForValidIdWithInvalidAccessToken(final String api,
        final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2("accessToken")
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrievePeopleForValidId(final String api,
        final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrievePeopleForValidQueryParams(final String api,
        final Map<String, Object> headersAsMap, final Map<String, Object> queryParams, final String basePath) {

        return given()
                .auth()
                .oauth2(accessToken)
                .headers(headersAsMap)
                .queryParams(queryParams)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrievePeopleForValidQueryParamsWithInvalidToken(final String api,
        final Map<String, Object> headersAsMap, final Map<String, Object> queryParams, final String basePath) {

        return given()
                .auth()
                .oauth2("accessToken")
                .headers(headersAsMap)
                .queryParams(queryParams)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }
}
