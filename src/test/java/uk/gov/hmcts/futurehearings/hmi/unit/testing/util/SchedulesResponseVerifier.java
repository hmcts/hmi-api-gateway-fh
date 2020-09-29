package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter.getObjStep;


public class SchedulesResponseVerifier {

    private static final String MISSING_SUB_KEY_ERROR = "Access denied due to missing subscription key. Make sure to include subscription key when making requests to an API.";
    private static final String INVALID_SUB_KEY_ERROR = "Access denied due to invalid subscription key. Make sure to provide a valid key for an active subscription.";


    public void thenValidateResponseForInvalidResource(Response response) {
        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals("Status Code Validation:", 404, response.getStatusCode());
            getObjStep().pass("Got the expected status code: 404");
            assertEquals("Status Code Message Validation:", "Resource not found", responseMap.get(("message")));
            getObjStep().pass("Got the expected message: " + responseMap.get(("message")));
        } catch (AssertionError e) {
            getObjStep().fail("Exception in " + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail("Exception: " + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForRetrieve(Response response) {
        try {

            assertEquals("Response Code Validation:", 200, response.getStatusCode());
            getObjStep().pass("Got the expected response code: 200");

        } catch (AssertionError e) {
            getObjStep().fail("Exception in " + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail("Exception: " + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForMissingSubscriptionKeyHeader(Response response) {

        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals("Status Code Validation:", 401, response.getStatusCode());
            getObjStep().pass("Got the expected status code: 401");
            assertEquals("Status Code Message Validation:", MISSING_SUB_KEY_ERROR, responseMap.get(("message")));
            getObjStep().pass("Got the expected message: " + responseMap.get(("message")));
        } catch (AssertionError e) {
            getObjStep().fail("Exception in " + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail("Exception: " + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForInvalidSubscriptionKeyHeader(Response response) {

        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals("Status Code Validation:", 401, response.getStatusCode());
            getObjStep().pass("Got the expected status code: 401");
            assertEquals("Status Code Message Validation:", INVALID_SUB_KEY_ERROR, responseMap.get(("message")));
            getObjStep().pass("Got the expected message: " + responseMap.get(("message")));
        } catch (AssertionError e) {
            getObjStep().fail("Exception in " + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail("Exception: " + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForMissingOrInvalidHeader(Response response, String missingField) {

        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals("Status Code Validation:", 400, response.getStatusCode());
            getObjStep().pass("Got the expected status code: 400");
            assertEquals("Status Code Message Validation:", "Missing/Invalid Header " + missingField, responseMap.get(("message")));
            getObjStep().pass("Got the expected message: " + responseMap.get(("message")));
        } catch (AssertionError e) {
            getObjStep().fail("Exception in " + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail("Exception: " + e.getClass());
            throw e;
        }

    }

    public static void thenValidateResponseForMissingOrInvalidAcceptHeader(Response response) {

        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals("Status Code Validation:", 406, response.getStatusCode());
            getObjStep().pass("Got the expected status code: 406");
            assertEquals("Status Code Message Validation:", "Missing/Invalid Media Type", responseMap.get(("message")));
            getObjStep().pass("Got the expected message: " + responseMap.get(("message")));
        } catch (AssertionError e) {
            getObjStep().fail("Exception in " + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail("Exception: " + e.getClass());
            throw e;
        }
    }


    public static void thenValidateResponseForMissingOrInvalidContentTypeHeader(Response response) {

        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals("Status Code Validation:", 400, response.getStatusCode());
            getObjStep().pass("Got the expected status code: 400");
            assertEquals("Status Code Message Validation:", "Missing/Invalid Media Type", responseMap.get(("message")));
            getObjStep().pass("Got the expected message: " + responseMap.get(("message")));
        } catch (AssertionError e) {
            getObjStep().fail("Exception in " + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail("Exception: " + e.getClass());
            throw e;
        }
    }
}
