package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import io.restassured.response.Response;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter.getObjStep;

public final class SessionsResponseVerifier {

    private static final String STATUS_CODE_VALIDATION = "Status Code Validation:";
    private static final String STATUS_CODE_MESSAGE_VALIDATION = "Status Code Message Validation:";

    private static final String MESSAGE = "message";
    private static final String GOT_EXPECTED_MESSAGE = "Got the expected message: ";
    private static final String EXCEPTION_IN = "Exception in ";
    private static final String EXCEPTION = "Exception: ";
    private static final String RESPONSE_CODE_VALIDATION = "Response Code Validation:";
    private static final String EXPECTED_STATUS_CODE_200 = "Got the expected response code: 200";
    private static final String DESCRIPTION = "description";

    public static void thenValidateResponseForInvalidResource(Response response) {
        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            assertEquals(404, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass("Got the expected status code: 404");
            assertEquals("Resource not found", responseMap.get(MESSAGE),
                    STATUS_CODE_MESSAGE_VALIDATION);
            getObjStep().pass(GOT_EXPECTED_MESSAGE + responseMap.get(MESSAGE));
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForRequestOrDelete(Response response) {
        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            assertEquals(200, response.getStatusCode(), RESPONSE_CODE_VALIDATION);
            getObjStep().pass(EXPECTED_STATUS_CODE_200);
            assertEquals("The request was received successfully.", responseMap.get(DESCRIPTION),
                    "Response Code Description Validation:");
            getObjStep().pass("Got the expected description: " + responseMap.get(DESCRIPTION));

        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForUpdate(Response response) {
        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            assertEquals(200, response.getStatusCode(), RESPONSE_CODE_VALIDATION);
            getObjStep().pass(EXPECTED_STATUS_CODE_200);
            assertEquals("The request was received successfully.", responseMap.get(DESCRIPTION),
                    "Response Code Description Validation:");
            getObjStep().pass("Got the expected description: " + responseMap.get(DESCRIPTION));
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForRetrieve(Response response) {
        try {
            assertEquals(200, response.getStatusCode(), RESPONSE_CODE_VALIDATION);
            getObjStep().pass(EXPECTED_STATUS_CODE_200);
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForNoMandatoryParams(Response response) {
        try {
            assertEquals(200, response.getStatusCode(), RESPONSE_CODE_VALIDATION);
            getObjStep().pass(EXPECTED_STATUS_CODE_200);
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForMissingOrInvalidAccessToken(Response response) {
        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            assertEquals(401, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass("Got the expected status code: 401");
            assertEquals("Access denied due to invalid OAuth information", responseMap.get(MESSAGE),
                    STATUS_CODE_MESSAGE_VALIDATION);
            getObjStep().pass(GOT_EXPECTED_MESSAGE + responseMap.get(MESSAGE));
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForMissingOrInvalidHeader(Response response, String missingField) {
        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            assertEquals(400, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass("Got the expected status code: 400");
            assertEquals("Missing/Invalid Header " + missingField, responseMap.get(MESSAGE),
                    STATUS_CODE_MESSAGE_VALIDATION);
            getObjStep().pass(GOT_EXPECTED_MESSAGE + responseMap.get(MESSAGE));
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForMissingOrInvalidAcceptHeader(Response response) {
        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            assertEquals(406, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass("Got the expected status code: 406");
            assertEquals("Missing/Invalid Media Type", responseMap.get(MESSAGE),
                    STATUS_CODE_MESSAGE_VALIDATION);
            getObjStep().pass(GOT_EXPECTED_MESSAGE + responseMap.get(MESSAGE));
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForMissingOrInvalidContentTypeHeader(Response response) {
        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            assertEquals(400, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass("Got the expected status code: 400");
            assertEquals("Missing/Invalid Media Type", responseMap.get(MESSAGE),
                    STATUS_CODE_MESSAGE_VALIDATION);
            getObjStep().pass(GOT_EXPECTED_MESSAGE + responseMap.get(MESSAGE));
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    private SessionsResponseVerifier() {
    }
}
