package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import io.restassured.response.Response;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UpdateHearingResponseVerifier {

    private static final String MISSING_SUB_KEY_ERROR = "Access denied due to missing subscription key. Make sure to include subscription key when making requests to an API.";

    public static void thenASuccessfulResponseForUpdateIsReturned(Response response) {
        assertEquals(2, response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals(201, responseMap.get(("status")));
        assertEquals("Hearings updated successfully", responseMap.get(("description")));
    }

    public static void thenResponseForMissingHeaderOcpSubscriptionIsReturned(Response response) {
        assertEquals(2, response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals(401, responseMap.get(("statusCode")));
        assertEquals(MISSING_SUB_KEY_ERROR, responseMap.get(("message")));
    }

    public static void thenResponseForMissingHeaderSourceIsReturned(Response response) {
        assertEquals(2, response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals(401, responseMap.get(("statusCode")));
        assertEquals("Missing/Invalid Header Source-System", responseMap.get(("message")));
    }

    public static void thenResponseForMissingHeaderDestinationIsReturned(Response response) {
        assertEquals(2, response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals(401, responseMap.get(("statusCode")));
        assertEquals("Missing/Invalid Header Destination-System", responseMap.get(("message")));
    }

    public static void thenResponseForMissingHeaderDateTimeIsReturned(Response response) {
        assertEquals(2, response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals(401, responseMap.get(("statusCode")));
        assertEquals("Missing/Invalid Header Request-Created-At", responseMap.get(("message")));
    }

    public static void thenResponseForMissingHeaderRequestTypeIsReturned(Response response) {
        assertEquals(2, response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals(401, responseMap.get(("statusCode")));
        assertEquals("Missing/Invalid Header Request-Type", responseMap.get(("message")));
    }

}
