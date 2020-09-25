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
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForAdditionalParam;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForRetrieve;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForInvalidSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ResourcesResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("GET /resources - Retrieve Resources")
public class GET_resources_UnitTests {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetHost}")
    private String targetHost;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${resourcesApiRootContext}")
    private String resourcesApiRootContext;

    private final Map<String, Object> headersAsMap = new HashMap<>();

    @BeforeEach
    public void initialiseValues() {
        headersAsMap.put("Host", targetHost);
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", "S&L");
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
        headersAsMap.put("Request-Processed-At", "2018-02-29 20:36:01Z");
    }

    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    public void testRetrieveResourcesRequestForInvalidResource() {
        final Response response = whenRetrieveResourcesIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    public void testRetrieveResourcesRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    public void testRetrieveResourcesRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    public void testRetrieveResourcesRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    public void testRetrieveResourcesRequestWithInvalidAcceptHeader() {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
    public void testRetrieveResourcesRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    public void testRetrieveResourcesRequestWithInvalidOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }

    @Test
    @Order(8)
    @DisplayName("Test for missing Source-System header")
    public void testRetrieveResourcesRequestWithMissingSourceSystemHeader() {
        headersAsMap.remove("Source-System");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }

    @Test
    @Order(9)
    @DisplayName("Test for invalid Source-System header")
    public void testRetrieveResourcesRequestWithInvalidSourceSystemHeader() {
        headersAsMap.remove("Source-System");
        headersAsMap.put("Source-System", "A");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }

    @Test
    @Order(10)
    @DisplayName("Test for missing Destination-System header")
    public void testRetrieveResourcesRequestWithMissingDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }

    @Test
    @Order(11)
    @DisplayName("Test for invalid Destination-System header")
    public void testRetrieveResourcesRequestWithInvalidDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");
        headersAsMap.put("Destination-System", "A");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }

    @Test
    @Order(12)
    @DisplayName("Test for missing Request-Created-At header")
    public void testRetrieveResourcesRequestWithMissingRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }

    @Test
    @Order(13)
    @DisplayName("Test for invalid Request-Created-At header")
    public void testRetrieveResourcesRequestWithInvalidRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");
        headersAsMap.put("Request-Created-At", "2018-01-29A20:36:01Z");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }


    @Test
    @Order(14)
    @DisplayName("Test for missing Request-Processed-At header")
    public void testRetrieveResourcesRequestWithMissingRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }

    @Test
    @Order(15)
    @DisplayName("Test for invalid Request-Processed-At header")
    public void testRetrieveResourcesRequestWithInvalidRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");
        headersAsMap.put("Request-Processed-At", "2018-02-29A20:36:01Z");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }

    @Test
    @Order(16)
    @DisplayName("Test for missing Request-Type header")
    public void testRetrieveResourcesRequestWithMissingRequestTypeHeader() {
        headersAsMap.remove("Request-Type");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }

    @Test
    @Order(17)
    @DisplayName("Test for invalid Request-Type header")
    public void testRetrieveResourcesRequestWithInvalidRequestTypeHeader() {
        headersAsMap.remove("Request-Type");
        headersAsMap.put("Request-Type", "A");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }

    @Test
    @Order(18)
    @DisplayName("Test for Correct Headers and No Parameters")
    public void testRetrieveResourcesRequestWithCorrectRequestAndNoParams() {
        final Response response = whenRetrieveResourcesIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForRetrieve(response);
    }

    private Response whenRetrieveResourcesIsInvokedForInvalidResource() {
        return retrieveResourcesResponseForInvalidResource(resourcesApiRootContext+"get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveResourcesIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveResourcesResponseForCorrectRequestAndNoParams(resourcesApiRootContext, headersAsMap, targetInstance);
    }

    private Response whenRetrieveResourcesIsInvokedWithMissingOcpSubKey() {
        return retrieveResourcesResponseForAMissingOcpSubKey(resourcesApiRootContext, headersAsMap, targetInstance);
    }

    private Response whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader() {
        return retrieveResourcesResponseForMissingOrInvalidHeader(resourcesApiRootContext, headersAsMap, targetInstance);
    }

//INDIVIDUAL RESOURCE TESTS - START

    @Test
    @Order(19)
    @DisplayName("Test for Invalid Resource - Individual Resource")
    public void testRetrieveIndividualResourceRequestForInvalidResource() {
        final Response response = whenRetrieveIndividualResourceIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response);
    }

    @Test
    @Order(20)
    @DisplayName("Test for missing ContentType header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }
    @Test
    @Order(21)
    @DisplayName("Test for invalid ContentType header - Individual Resource")
    public void testRetrieveIndividualResourcesRequestWithInvalidContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(22)
    @DisplayName("Test for missing Accept header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(23)
    @DisplayName("Test for invalid Accept header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithInvalidAcceptHeader() {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(24)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(25)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithInvalidOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }

    @Test
    @Order(26)
    @DisplayName("Test for missing Source-System header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingSourceSystemHeader() {
        headersAsMap.remove("Source-System");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }

    @Test
    @Order(27)
    @DisplayName("Test for invalid Source-System header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithInvalidSourceSystemHeader() {
        headersAsMap.remove("Source-System");
        headersAsMap.put("Source-System", "A");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Source-System");
    }

    @Test
    @Order(28)
    @DisplayName("Test for missing Destination-System header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }

    @Test
    @Order(29)
    @DisplayName("Test for invalid Destination-System header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithInvalidDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");
        headersAsMap.put("Destination-System", "A");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Destination-System");
    }

    @Test
    @Order(30)
    @DisplayName("Test for missing Request-Created-At header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }

    @Test
    @Order(31)
    @DisplayName("Test for invalid Request-Created-At header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithInvalidRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");
        headersAsMap.put("Request-Created-At", "2018-01-29A20:36:01Z");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Created-At");
    }

    @Test
    @Order(32)
    @DisplayName("Test for missing Request-Processed-At header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }

    @Test
    @Order(33)
    @DisplayName("Test for invalid Request-Processed-At header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithInvalidRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");
        headersAsMap.put("Request-Processed-At", "2018-02-29A20:36:01Z");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Processed-At");
    }

    @Test
    @Order(34)
    @DisplayName("Test for missing Request-Type header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingRequestTypeHeader() {
        headersAsMap.remove("Request-Type");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }

    @Test
    @Order(35)
    @DisplayName("Test for invalid Request-Type header - Individual Resource")
    public void testRetrieveIndividualResourcesRequestWithInvalidRequestTypeHeader() {
        headersAsMap.remove("Request-Type");
        headersAsMap.put("Request-Type", "A");

        final Response response = whenRetrieveResourcesIsInvokedWithMissingOrInvalidHeader();
        thenValidateResponseForMissingOrInvalidHeader(response, "Request-Type");
    }

    @Test
    @Order(36)
    @DisplayName("Test for No Parameters - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithCorrectRequestAndNoParams() {
        final Response response = whenRetrieveIndividualResourceIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForRetrieve(response);
    }

    private Response whenRetrieveIndividualResourceIsInvokedForInvalidResource() {
        return retrieveResourcesResponseForInvalidResource(resourcesApiRootContext+"get"+"/CASE123432", headersAsMap, targetInstance);
    }

    private Response whenRetrieveIndividualResourceIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveResourcesResponseForCorrectRequestAndNoParams(resourcesApiRootContext+ "/CASE123432", headersAsMap, targetInstance);
    }

    private Response whenRetrieveIndividualResourceIsInvokedWithMissingOcpSubKey() {
        return retrieveResourcesResponseForAMissingOcpSubKey(resourcesApiRootContext+ "/CASE123432", headersAsMap, targetInstance);
    }

    private Response whenRetrieveIndividualResourceIsInvokedWithMissingOrInvalidHeader() {
        return retrieveResourcesResponseForMissingOrInvalidHeader(resourcesApiRootContext+ "/CASE123432", headersAsMap, targetInstance);
    }


//INDIVIDUAL RESOURCE TESTS - END

    private Response retrieveResourcesResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveResourcesResponseForCorrectRequestAndNoParams(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }



    private Response retrieveResourcesResponseForAMissingOcpSubKey(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveResourcesResponseForMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();

    }


}
