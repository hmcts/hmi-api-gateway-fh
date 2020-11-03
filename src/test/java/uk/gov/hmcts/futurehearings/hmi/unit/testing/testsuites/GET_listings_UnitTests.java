package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForAdditionalParam;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForInvalidSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForMissingSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ListingsResponseVerifier.thenValidateResponseForRetrieve;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("GET /listings - Retrieve Listings")
@SuppressWarnings("java:S2699")
class GET_listings_UnitTests {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${listingsApiRootContext}")
    private String listingsApiRootContext;

    @Value("${destinationSystem}")
    private String destinationSystem;

    private final Map<String, Object> headersAsMap = new HashMap<>();
    private final Map<String, String> paramsAsMap = new HashMap<>();

    @BeforeEach
    void initialiseValues() {

        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
        headersAsMap.put("Request-Processed-At", "2018-02-29 20:36:01Z");

        paramsAsMap.put("date_of_listing", "2018-01-29 21:36:01Z");
        paramsAsMap.put("hearing_type", "VH");

    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    void testRetrieveListingsRequestForInvalidResource() {

        final Response response = whenRetrieveListingsRequestIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testRetrieveListingsRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");

        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testRetrieveListingsRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");

        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testRetrieveListingsRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");

        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testRetrieveListingsRequestWithInvalidAcceptHeader() {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");

        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
    void testRetrieveListingsRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");

        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidOcpSubKey();
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    void testRetrieveListingsRequestWithInvalidOcpSubKey(){
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");

        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidOcpSubKey();
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }

    @Order(8)
    @ParameterizedTest(name = "Test for missing {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At","Request-Type"})
    void testRetrieveListingsRequestWithMissingHeader(String iteration) {
        headersAsMap.remove(iteration);

        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(9)
    @ParameterizedTest(name = "Test for invalid {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At","Request-Type"})
    void testRetrieveListingsRequestWithInvalidHeader(String iteration) {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");

        final Response response = whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(10)
    @DisplayName("Test for Invalid Parameter")
    void testRetrieveListingsRequestWithAdditionalParam() {
        paramsAsMap.put("Invalid-Param","Value");

        final Response response = whenRetrieveListingsIsInvokedWithAdditionalParam();
        thenValidateResponseForAdditionalParam(response);
    }

    @Test
    @Order(11)
    @DisplayName("Test for Correct Headers with No Parameters")
    void testRetrieveListingsRequestWithCorrectHeadersAndNoParams() {

        final Response response = whenRetrieveListingsIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForRetrieve(response);
    }

    @Test
    @Order(12)
    @DisplayName("Test for Correct Headers and Parameters")
    void testRetrieveListingsRequestWithCorrectHeadersAndParams() {

        final Response response = whenRetrieveListingsIsInvokedWithCorrectHeadersAndParams();
        thenValidateResponseForRetrieve(response);
    }

    private Response whenRetrieveListingsIsInvokedWithAdditionalParam() {
        return retrieveListingsResponseForCorrectHeadersAndParams(listingsApiRootContext, headersAsMap, paramsAsMap, targetInstance);
    }

    private Response whenRetrieveListingsRequestIsInvokedForInvalidResource() {
        return retrieveListingsResponseForInvalidResource(listingsApiRootContext+"get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveListingsIsInvokedWithCorrectHeadersAndParams() {
        return retrieveListingsResponseForCorrectHeadersAndParams(listingsApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response whenRetrieveListingsIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveListingsResponseForCorrectHeadersAndNoParams(listingsApiRootContext, headersAsMap, targetInstance);
    }

    private Response whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidOcpSubKey() {
        return retrieveListingsResponseForMissingOrInvalidOcpSubKey(listingsApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response whenRetrieveListingsRequestIsInvokedWithMissingOrInvalidHeader() {
        return retrieveListingsResponseForMissingOrInvalidHeader(listingsApiRootContext, headersAsMap,  paramsAsMap, targetInstance);
    }

    //Listings by ID

    @Test
    @Order(13)
    @DisplayName("Test for Invalid Resource - By ID")
    void testRetrieveListingsByIDRequestForInvalidResource() {

        final Response response = whenRetrieveListingsByIDRequestIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(14)
    @DisplayName("Test for missing ContentType header - By ID")
    void testRetrieveListingsByIDRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");

        final Response response = whenRetrieveListingsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(15)
    @DisplayName("Test for invalid ContentType header - By ID")
    void testRetrieveListingsByIDRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");

        final Response response = whenRetrieveListingsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(16)
    @DisplayName("Test for missing Accept header - By ID")
    void testRetrieveListingsByIDRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");

        final Response response = whenRetrieveListingsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(17)
    @DisplayName("Test for invalid Accept header - By ID")
    void testRetrieveListingsByIDRequestWithInvalidAcceptHeader() {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");

        final Response response = whenRetrieveListingsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(18)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header - By ID")
    void testRetrieveListingsByIDRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");

        final Response response = whenRetrieveListingsByIDRequestIsInvokedWithMissingOrInvalidOcpSubKey();
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(19)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header - By ID")
    void testRetrieveListingsByIDRequestWithInvalidOcpSubKey(){
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");

        final Response response = whenRetrieveListingsByIDRequestIsInvokedWithMissingOrInvalidOcpSubKey();
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }

    @Order(20)
    @ParameterizedTest(name = "Test for missing {0} header - By ID")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At","Request-Type"})
    void testRetrieveListingsByIDRequestWithMissingHeader(String iteration) {
        headersAsMap.remove(iteration);

        final Response response = whenRetrieveListingsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Order(21)
    @ParameterizedTest(name = "Test for invalid {0} header - By ID")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At","Request-Type"})
    void testRetrieveListingsByIDRequestWithInvalidHeader(String iteration) {
        headersAsMap.remove(iteration);
        headersAsMap.put(iteration, "A");

        final Response response = whenRetrieveListingsByIDRequestIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

    @Test
    @Order(22)
    @DisplayName("Test for Correct Headers with No Parameters")
    void testRetrieveListingsByIDRequestWithCorrectHeadersAndNoParams() {

        final Response response = whenRetrieveListingsByIDIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForRetrieve(response);
    }

    private Response whenRetrieveListingsByIDRequestIsInvokedForInvalidResource() {
        return retrieveListingsResponseForInvalidResource(listingsApiRootContext+"/list_id"+"/get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveListingsByIDIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveListingsResponseForCorrectHeadersAndNoParams(listingsApiRootContext+"/list_id", headersAsMap, targetInstance);
    }

    private Response whenRetrieveListingsByIDRequestIsInvokedWithMissingOrInvalidOcpSubKey() {
        return retrieveListingsResponseForMissingOrInvalidOcpSubKey(listingsApiRootContext+"/list_id", headersAsMap,  paramsAsMap, targetInstance);
    }

    private Response whenRetrieveListingsByIDRequestIsInvokedWithMissingOrInvalidHeader() {
        return retrieveListingsResponseForMissingOrInvalidHeader(listingsApiRootContext+"/list_id", headersAsMap,  paramsAsMap, targetInstance);
    }




    private Response retrieveListingsResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveListingsResponseForCorrectHeadersAndParams(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }


    private Response retrieveListingsResponseForCorrectHeadersAndNoParams(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveListingsResponseForMissingOrInvalidOcpSubKey(final String api, final Map<String, Object> headersAsMap, final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveListingsResponseForMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap,final Map<String, String> paramsAsMap, final String basePath) {

        return given()
                .queryParams(paramsAsMap)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();

    }
}
