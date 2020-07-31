package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import io.restassured.response.Response;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

public class ResponseVerifier {

    public static void verifySessionResponse (Response response) {

        //Option 1 - Use JsonPath - (Native Matcher to RestAssured and Serenity Rest)
        //System.out.println(response.getBody().asString());
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        //assertEquals("Morning",response.getBody().jsonPath().getString("$.Session"));
        //assertEquals("Type",response.getBody().jsonPath().getString("$.Civil"));
        assertEquals("HMCTS",responseMap.get(("Name")));
        assertEquals("London",responseMap.get(("Place")));

        //Option 2 - Use a Json Equality based library like JsonAssert
        try {
            JSONAssert.assertEquals(
                    "{\n" +
                            "    \"Name\": \"HMCTS\",\n" +
                            "    \"Place\": \"London\"\n" +
                            "}",
                    response.getBody().asString(), JSONCompareMode.STRICT);
        } catch (JSONException jsonException) {
            throw new AssertionError("Payloads have not matched");
        }


        //Option 3 - Use a better Third Party Specialised Matcher (Hamcrest)

    }

    public static void verifyHearingResponseForMissingCaseTitle(Response response) {

        //Option 1 - Use JsonPath - (Native Matcher to RestAssured and Serenity Rest)
        /*System.out.println(response.getBody().asString());
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("400",responseMap.get(("Error")));
        assertEquals("Malformed request. Missing/Invalid property: 'Case Title'",responseMap.get(("Reason")));*/

        //Option 2 - Use a Json Equality based library like JsonAssert
        comparePayloads("responses/case-title-missing-response.json", response);
    }

    public static void comparePayloads(final String expectedPayloadPath, final Response response) {
        try {
            String output = readFileContents(expectedPayloadPath);
            JSONAssert.assertEquals(output,
                    response.getBody().asString(), JSONCompareMode.STRICT);
        } catch (JSONException jsonException) {
            throw new AssertionError("Payloads have not matched");
        } catch (IOException ioException ) {
            throw new AssertionError("Response file cannot be read..");
        }
    }

}
