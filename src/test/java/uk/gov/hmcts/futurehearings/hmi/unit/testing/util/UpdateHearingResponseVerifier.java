package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import io.restassured.response.Response;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UpdateHearingResponseVerifier {

    public static void thenASuccessfulResponseForUpdateIsReturned(Response response) {
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("201",responseMap.get(("status")));
        assertEquals("Successfully updated hearing",responseMap.get(("description")));
    }
}
