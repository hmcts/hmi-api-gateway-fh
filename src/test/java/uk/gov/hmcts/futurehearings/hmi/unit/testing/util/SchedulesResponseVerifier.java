package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import io.restassured.response.Response;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter.getObjStep;


public class SchedulesResponseVerifier {

    public static void thenValidateResponseForInvalidResource(Response response) {
        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            assertEquals(404, response.getStatusCode(), "Status Code Validation:");
            getObjStep().pass("Got the expected status code: 404");
            assertEquals("Resource not found", responseMap.get("message"),
                    "Status Code Message Validation:");
            getObjStep().pass("Got the expected message: " + responseMap.get("message"));
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
            assertEquals(200, response.getStatusCode(), "Response Code Validation:");
            getObjStep().pass("Got the expected response code: 200");

        } catch (AssertionError e) {
            getObjStep().fail("Exception in " + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail("Exception: " + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForCreate(Response response) {
        try {
            assertEquals(201, response.getStatusCode(), "Response Code Validation:");
            getObjStep().pass("Got the expected response code: 201");
        } catch (AssertionError e) {
            getObjStep().fail("Exception in " + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail("Exception: " + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForMissingOrInvalidAccessToken(Response response) {
        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            assertEquals(401, response.getStatusCode(), "Status Code Validation:");
            getObjStep().pass("Got the expected status code: 401");
            assertEquals("Access denied due to invalid OAuth information",
                    responseMap.get("message"), "Status Code Message Validation:");
            getObjStep().pass("Got the expected message: " + responseMap.get("message"));
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
            assertEquals(400, response.getStatusCode(), "Status Code Validation:");
            getObjStep().pass("Got the expected status code: 400");
            assertEquals("Missing/Invalid Header " + missingField, responseMap.get("message"),
                    "Status Code Message Validation:");
            getObjStep().pass("Got the expected message: " + responseMap.get("message"));
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
            assertEquals(406, response.getStatusCode(), "Status Code Validation:");
            getObjStep().pass("Got the expected status code: 406");
            assertEquals("Missing/Invalid Media Type", responseMap.get("message"),
                    "Status Code Message Validation:");
            getObjStep().pass("Got the expected message: " + responseMap.get("message"));
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
            assertEquals(400, response.getStatusCode(), "Status Code Validation:");
            getObjStep().pass("Got the expected status code: 400");
            assertEquals("Missing/Invalid Media Type", responseMap.get("message"),
                    "Status Code Message Validation:");
            getObjStep().pass("Got the expected message: " + responseMap.get("message"));
        } catch (AssertionError e) {
            getObjStep().fail("Exception in " + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail("Exception: " + e.getClass());
            throw e;
        }
    }

    private SchedulesResponseVerifier() {
    }
}
