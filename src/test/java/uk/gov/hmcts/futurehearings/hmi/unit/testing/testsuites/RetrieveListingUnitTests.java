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
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.RetrieveListingResponseVerifier.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.*;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RetrieveListingUnitTests {

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
        objTestFromUtils = startReport("RetrieveList Validations");

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
    public void testRetrieveListingRequestForInvalidResource() {
        final Response response = whenRetrieveListingIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response, objStep);
    }

    @Test
    @Order(2)
    @DisplayName("Test for No Parameters")
    public void testRetrieveListingRequestWithCorrectRequestAndNoParams() {
        final Response response = whenRetrieveListingIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForACorrectRequest(response, objStep);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
    public void testRetrieveListingRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final Response response = whenRetrieveListingIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForMissingOcpSubscriptionHeader(response, objStep);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    public void testRetrieveListingRequestWithInvalidOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final Response response = whenRetrieveListingIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForInvalidOcpSubscription(response, objStep);
    }


    @Test
    @Order(6)
    @DisplayName("Test for missing Source-System header")
    public void testRetrieveListingRequestWithMissingSourceSystemHeader() {
        headersAsMap.remove("Source-System");
        final Response response = whenRetrieveListingIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Source-System", objStep);
    }

    @Test
    @Order(7)
    @DisplayName("Test for missing Destination-System header")
    public void testRetrieveListingRequestWithMissingDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");
        final Response response = whenRetrieveListingIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Destination-System", objStep);
    }

    @Test
    @Order(8)
    @DisplayName("Test for missing Request-Type header")
    public void testRetrieveListingRequestWithMissingRequestTypeHeader() {
        headersAsMap.remove("Request-Type");
        final Response response = whenRetrieveListingIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Request-Type", objStep);
    }

    @Test
    @Order(9)
    @DisplayName("Test for missing Request-Created-At header")
    public void testRetrieveListingRequestWithMissingRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");
        final Response response = whenRetrieveListingIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Request-Created-At", objStep);
    }

    @Test
    @Order(10)
    @DisplayName("Test for missing Request-Processed-At header")
    public void testRetrieveListingRequestWithMissingRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");
        final Response response = whenRetrieveListingIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Request-Processed-At", objStep);
    }

    @Test
    @Order(11)
    @DisplayName("Test for missing Accept header")
    public void testRetrieveListingRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");
        final Response response = whenRetrieveListingIsInvokedWithMissingHeader();
        thenValidateResponseForMissingAcceptHeader(response, objStep);
    }

    @Test
    @Order(12)
    @DisplayName("Test for missing ContentType header")
    public void testRetrieveListingRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        final Response response = whenRetrieveListingIsInvokedWithMissingHeader();
        thenValidateResponseForMissingContentTypeHeader(response, objStep);
    }


    private Response whenRetrieveListingIsInvokedForInvalidResource() {
        return retrieveListingForInvalidResource(resourcesApiRootContext+"get", headersAsMap, targetInstance);
    }

    private Response whenRetrieveListingIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveListingWithCorrectRequestAndNoParams(resourcesApiRootContext, headersAsMap, targetInstance);
    }

    private Response whenRetrieveListingIsInvokedWithMissingOcpSubKey() {
        return retrieveListingWithAMissingOcpSubKey(resourcesApiRootContext, headersAsMap, targetInstance);
    }

    private Response whenRetrieveListingIsInvokedWithMissingHeader() {
        return retrieveListingWithAMissingHeader(resourcesApiRootContext, headersAsMap, targetInstance);
    }

//INDIVIDUAL RESOURCE TESTS - START

    @Test
    @Order(13)
    @DisplayName("Test for Invalid Resource - Individual Listing")
    public void testRetrieveIndividualListingRequestForInvalidResource() {
        final Response response = whenRetrieveIndividualListingIsInvokedForInvalidResource();
        thenValidateResponseForInvalidResource(response, objStep);
    }

    @Test
    @Order(14)
    @DisplayName("Test for No Parameters - Individual Listing")
    public void testRetrieveIndividualListingRequestWithCorrectRequestAndNoParams() {
        final Response response = whenRetrieveIndividualListingIsInvokedWithCorrectHeadersAndNoParams();
        thenValidateResponseForACorrectRequest(response, objStep);
    }

    @Test
    @DisplayName("Test for missing Ocp-Apim-Subscription-Key header - Individual Listing")
    public void testRetrieveIndividualListingRequestWithMissingOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final Response response = whenRetrieveIndividualListingIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForMissingOcpSubscriptionHeader(response, objStep);
    }

    @Test
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header - Individual Listing")
    public void testRetrieveIndividualListingRequestWithInvalidOcpSubKey() {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final Response response = whenRetrieveIndividualListingIsInvokedWithMissingOcpSubKey();
        thenValidateResponseForInvalidOcpSubscription(response, objStep);
    }


    @Test
    @DisplayName("Test for missing Source-System header - Individual Listing")
    public void testRetrieveIndividualListingRequestWithMissingSourceSystemHeader() {
        headersAsMap.remove("Source-System");
        final Response response = whenRetrieveIndividualListingIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Source-System", objStep);
    }

    @Test
    @DisplayName("Test for missing Destination-System header - Individual Listing")
    public void testRetrieveIndividualListingRequestWithMissingDestinationSystemHeader() {
        headersAsMap.remove("Destination-System");
        final Response response = whenRetrieveIndividualListingIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Destination-System", objStep);
    }

    @Test
    @DisplayName("Test for missing Request-Type header - Individual Listing")
    public void testRetrieveIndividualListingRequestWithMissingRequestTypeHeader() {
        headersAsMap.remove("Request-Type");
        final Response response = whenRetrieveIndividualListingIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Request-Type", objStep);
    }

    @Test
    @DisplayName("Test for missing Request-Created-At header - Individual Listing")
    public void testRetrieveIndividualListingRequestWithMissingRequestCreatedAtHeader() {
        headersAsMap.remove("Request-Created-At");
        final Response response = whenRetrieveIndividualListingIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Request-Created-At", objStep);
    }

    @Test
    @DisplayName("Test for missing Request-Processed-At header - Individual Listing")
    public void testRetrieveIndividualListingRequestWithMissingRequestProcessedAtHeader() {
        headersAsMap.remove("Request-Processed-At");
        final Response response = whenRetrieveIndividualListingIsInvokedWithMissingHeader();
        thenValidateResponseForMissingHeader(response, "Request-Processed-At", objStep);
    }

    @Test
    @DisplayName("Test for missing Accept header - Individual Listing")
    public void testRetrieveIndividualListingRequestWithMissingAcceptHeader() {
        headersAsMap.remove("Accept");
        final Response response = whenRetrieveIndividualListingIsInvokedWithMissingHeader();
        thenValidateResponseForMissingAcceptHeader(response, objStep);
    }

    @Test
    @DisplayName("Test for missing ContentType header - Individual Listing")
    public void testRetrieveIndividualListingRequestWithMissingContentTypeHeader() {
        headersAsMap.remove("Content-Type");
        final Response response = whenRetrieveIndividualListingIsInvokedWithMissingHeader();
        thenValidateResponseForMissingContentTypeHeader(response, objStep);
    }


    private Response whenRetrieveIndividualListingIsInvokedForInvalidResource() {
        return retrieveListingForInvalidResource(resourcesApiRootContext+"get"+"/CASE123432", headersAsMap, targetInstance);
    }

    private Response whenRetrieveIndividualListingIsInvokedWithCorrectHeadersAndNoParams() {
        return retrieveListingWithCorrectRequestAndNoParams(resourcesApiRootContext+ "/CASE123432", headersAsMap, targetInstance);
    }

    private Response whenRetrieveIndividualListingIsInvokedWithMissingOcpSubKey() {
        return retrieveListingWithAMissingOcpSubKey(resourcesApiRootContext+ "/CASE123432", headersAsMap, targetInstance);
    }

    private Response whenRetrieveIndividualListingIsInvokedWithMissingHeader() {
        return retrieveListingWithAMissingHeader(resourcesApiRootContext+ "/CASE123432", headersAsMap, targetInstance);
    }


//INDIVIDUAL RESOURCE TESTS - END

    private Response retrieveListingForInvalidResource(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveListingWithCorrectRequestAndNoParams(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }



    private Response retrieveListingWithAMissingOcpSubKey(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }

    private Response retrieveListingWithAMissingHeader(final String api, final Map<String, Object> headersAsMap, final String basePath) {

        return given()
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();

    }


}
