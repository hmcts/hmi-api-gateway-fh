package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.RetrieveResourcesResponseVerifier.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.*;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RetrieveResourcesUnitTests {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetHost}")
    private String targetHost;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${resourcesApiRootContext}")
    private String resourcesApiRootContext;



    private final Map<String, Object> headersAsMap = new HashMap<>();
    static ExtentTest objTestFromUtils, objStep;


    @BeforeAll
    public static void initialiseReport() {

        setupReport();
        objTestFromUtils = startReport("Retrieve Resources Validations");

    }

    @AfterAll
    public static void finaliseReport() {

        endReport();
        objTestFromUtils=null;
        objStep=null;

    }


    @BeforeEach
    public void initialiseValues(TestInfo info) {
        headersAsMap.put("Host", targetHost);
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", "S&L");
        headersAsMap.put("Request-Type", "THEFT");
        headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
        headersAsMap.put("Request-Processed-At", "2018-02-29 20:36:01Z");

         objStep = objTestFromUtils.createNode(info.getDisplayName());
    }


    @Test
    @Order(1)
    @DisplayName("Test for Invalid Resource")
    public void testRetrieveResourcesRequestForInvalidResource() {
        final Response response = whenRetrieveResourcesIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response, objStep);
    }

    @Test
    @Order(2)
    @DisplayName("Test for No Parameters")
    public void testRetrieveResourcesRequestWithCorrectRequestAndNoParams() {
        final Response response = whenRetrieveResourcesIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForACorrectRequest(response, objStep);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
    public void testRetrieveResourcesRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForMissingOcpSubscriptionHeader(response, objStep);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    public void testRetrieveResourcesRequestWithInvalidOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForInvalidOcpSubscription(response, objStep);
    }


    @Test
    @Order(6)
    @DisplayName("Test for missing Source-System header")
    public void testRetrieveResourcesRequestWithMissingSourceSystemHeader() {
        headersAsMap.remove("Source-System");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Source-System", objStep);
    }

    @Test
    @Order(7)
    @DisplayName("Test for missing Destination-System header")
    public void testRetrieveResourcesRequestWithMissingDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Destination-System", objStep);
    }

    @Test
    @Order(8)
    @DisplayName("Test for missing Request-Type header")
    public void testRetrieveResourcesRequestWithMissingRequestTypeHeader() {
        headersAsMap.remove("Request-Type");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Request-Type", objStep);
    }

    @Test
    @Order(9)
    @DisplayName("Test for missing Request-Created-At header")
    public void testRetrieveResourcesRequestWithMissingRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Request-Created-At", objStep);
    }

    @Test
    @Order(10)
    @DisplayName("Test for missing Request-Processed-At header")
    public void testRetrieveResourcesRequestWithMissingRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Request-Processed-At", objStep);
    }

    @Test
    @Order(11)
    @DisplayName("Test for missing Accept header")
    public void testRetrieveResourcesRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingHeader();
        thenValidateResponseForMissingAcceptHeader(response, objStep);
    }

    @Test
    @Order(12)
    @DisplayName("Test for missing ContentType header")
    public void testRetrieveResourcesRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        final Response response = whenRetrieveResourcesIsInvokedWithMissingHeader();
        thenValidateResponseForMissingContentTypeHeader(response, objStep);
    }


    private Response whenRetrieveResourcesIsInvokedForInvalidResource() {
        return retrieveResourcesForInvalidResource(resourcesApiRootContext+"get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveResourcesIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveResourcesWithCorrectRequestAndNoParams(resourcesApiRootContext, headersAsMap, targetInstance);
    }

    private Response whenRetrieveResourcesIsInvokedWithMissingOcpSubKey() {
        return retrieveResourcesWithAMissingOcpSubKey(resourcesApiRootContext, headersAsMap, targetInstance);
    }

    private Response whenRetrieveResourcesIsInvokedWithMissingHeader() {
        return retrieveResourcesWithAMissingHeader(resourcesApiRootContext, headersAsMap, targetInstance);
    }

//INDIVIDUAL RESOURCE TESTS - START

    @Test
    @Order(13)
    @DisplayName("Test for Invalid Resource - Individual Resource")
    public void testRetrieveIndividualResourceRequestForInvalidResource() {
        final Response response = whenRetrieveIndividualResourceIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response, objStep);
    }

    @Test
    @Order(14)
    @DisplayName("Test for No Parameters - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithCorrectRequestAndNoParams() {
        final Response response = whenRetrieveIndividualResourceIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForACorrectRequest(response, objStep);
    }

    @Test
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForMissingOcpSubscriptionHeader(response, objStep);
    }

    @Test
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithInvalidOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForInvalidOcpSubscription(response, objStep);
    }


    @Test
    @DisplayName("Test for missing Source-System header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingSourceSystemHeader() {
        headersAsMap.remove("Source-System");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Source-System", objStep);
    }

    @Test
    @DisplayName("Test for missing Destination-System header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Destination-System", objStep);
    }

    @Test
    @DisplayName("Test for missing Request-Type header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingRequestTypeHeader() {
        headersAsMap.remove("Request-Type");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Request-Type", objStep);
    }

    @Test
    @DisplayName("Test for missing Request-Created-At header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Request-Created-At", objStep);
    }

    @Test
    @DisplayName("Test for missing Request-Processed-At header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Request-Processed-At", objStep);
    }

    @Test
    @DisplayName("Test for missing Accept header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingHeader();
        thenValidateResponseForMissingAcceptHeader(response, objStep);
    }

    @Test
    @DisplayName("Test for missing ContentType header - Individual Resource")
    public void testRetrieveIndividualResourceRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        final Response response = whenRetrieveIndividualResourceIsInvokedWithMissingHeader();
        thenValidateResponseForMissingContentTypeHeader(response, objStep);
    }


    private Response whenRetrieveIndividualResourceIsInvokedForInvalidResource() {
        return retrieveResourcesForInvalidResource(resourcesApiRootContext+"get"+"/CASE123432", headersAsMap, targetInstance);
    }

    private Response whenRetrieveIndividualResourceIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveResourcesWithCorrectRequestAndNoParams(resourcesApiRootContext+ "/CASE123432", headersAsMap, targetInstance);
    }

    private Response whenRetrieveIndividualResourceIsInvokedWithMissingOcpSubKey() {
        return retrieveResourcesWithAMissingOcpSubKey(resourcesApiRootContext+ "/CASE123432", headersAsMap, targetInstance);
    }

    private Response whenRetrieveIndividualResourceIsInvokedWithMissingHeader() {
        return retrieveResourcesWithAMissingHeader(resourcesApiRootContext+ "/CASE123432", headersAsMap, targetInstance);
    }


//INDIVIDUAL RESOURCE TESTS - END

    private Response retrieveResourcesForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveResourcesWithCorrectRequestAndNoParams(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }



    private Response retrieveResourcesWithAMissingOcpSubKey(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveResourcesWithAMissingHeader(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();

    }


}
