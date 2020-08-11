package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import io.restassured.response.Response;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ResponseVerifier {

    public static void thenResponseHasErrorForMissingCaseTitle(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Case Title'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingCaseId(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Case Id'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingTransactionIDHMCTS(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'TransactionIDHMCTS'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingVenue(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Venue'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingJurisdiction(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Jurisdiction'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingService(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Service'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingCaseType (Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Case Type'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingHearingType(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Hearing Type'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingPriority(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Priority'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingStatusOfTheListingRequest(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Status of the Listing Request'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingTicketTypeRequired(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Ticket Type required'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingWaitingTimeKPI(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Waiting time KPI'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingReasonForSTLChange(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Reason for STL change'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingEstimatedDurationOfHearing(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Estimated Duration of the Hearing'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingHearingChannel(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Hearing channel'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingPrivateHearing(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Private Hearing'",responseMap.get(("Reason")));
    }

    public static void thenResponseHasErrorForMissingAllocatedListingTeam(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Allocated Listing Team'",responseMap.get(("Reason")));
    }

}
