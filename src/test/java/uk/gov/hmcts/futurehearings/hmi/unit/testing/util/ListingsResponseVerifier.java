package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import io.restassured.response.Response;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter.getObjStep;

@SuppressWarnings({"PMD.GodClass", "PMD.TooManyMethods", "PMD.CyclomaticComplexity"})
public final class ListingsResponseVerifier {

    private static final String STATUS_CODE_VALIDATION = "Status Code Validation:";
    private static final String STATUS_CODE_MESSAGE_VALIDATION = "Status Code Message Validation:";

    private static final String MESSAGE = "message";
    private static final String GOT_EXPECTED_MESSAGE = "Got the expected message: ";
    private static final String EXCEPTION_IN = "Exception in ";
    private static final String EXCEPTION = "Exception: ";
    private static final String EXPECTED_STATUS_CODE_200 = "Got the expected status code: 200";
    private static final String EXPECTED_STATUS_CODE_400 = "Got the expected status code: 400";

    public static void thenValidateResponseForInvalidResource(Response response) {
        try {
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            assertEquals(404, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass("Got the expected status code: 404");
            assertEquals("Resource not found", responseMap.get(MESSAGE), STATUS_CODE_MESSAGE_VALIDATION);
            getObjStep().pass(GOT_EXPECTED_MESSAGE + responseMap.get(MESSAGE));
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForGetPeopleById(Response response) {
        try {
            assertEquals(200, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass(EXPECTED_STATUS_CODE_200);
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForRequestVideoHearing(Response response) {
        try {
            assertEquals(201, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass("Got the expected status code: 201");
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForDirectListing(final Response response) {
        try {
            assertEquals(204, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass("Got the expected status code: 204");
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForRetrieveVideoHearing(final Response response) {
        try {
            assertEquals(200, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass(EXPECTED_STATUS_CODE_200);
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForUpdateVideoHearing(final Response response) {
        try {
            assertEquals(200, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass(EXPECTED_STATUS_CODE_200);
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForDeleteVideoHearing(final Response response) {
        try {
            assertEquals(204, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass("Got the expected status code: 204");
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForRetrieveVideoHearingWithInvalidQueryParams(Response response) {
        try {
            assertEquals(400, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass(EXPECTED_STATUS_CODE_400);
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForRetrieveVideoHearingWithPathParam(Response response) {
        try {
            assertEquals(200, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass(EXPECTED_STATUS_CODE_200);
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForRequestVideoHearingWithInvalidHeader(Response response) {
        try {
            assertEquals(400, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass(EXPECTED_STATUS_CODE_400);
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForRequestVideoHearingWithInvalidMedia(Response response) {
        try {
            assertEquals(406, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass("Got the expected status code: 406");
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForRequestVideoHearingWithInvalidToken(final Response response) {
        try {
            assertEquals(401, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass("Got the expected status code: 401");
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForGetPeopleByIdWithInvalidHeader(Response response) {
        try {
            assertEquals(400, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass(EXPECTED_STATUS_CODE_400);
        } catch (AssertionError e) {
            getObjStep().fail(EXCEPTION_IN + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail(EXCEPTION + e.getClass());
            throw e;
        }
    }

    public static void thenValidateInvalidResponseForGetPeopleByParams(Response response) {
        try {
            assertEquals(400, response.getStatusCode(), STATUS_CODE_VALIDATION);
            getObjStep().pass(EXPECTED_STATUS_CODE_400);
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
            assertEquals(200, response.getStatusCode(), "Response Code Validation:");
            getObjStep().pass("Got the expected response code: 200");
            assertEquals("The request was received successfully.", responseMap.get("description"),
                    "Response Code Description Validation:");
            getObjStep().pass("Got the expected description: " + responseMap.get("description"));

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
            assertEquals(204, response.getStatusCode(), "Response Code Validation:");
            getObjStep().pass("Got the expected response code: 204");
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
            assertEquals(200, response.getStatusCode(), "Response Code Validation:");
            getObjStep().pass("Got the expected response code: 200");
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
            getObjStep().pass(EXPECTED_STATUS_CODE_400);
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
            getObjStep().pass(EXPECTED_STATUS_CODE_400);
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

    private ListingsResponseVerifier() {
    }
}
