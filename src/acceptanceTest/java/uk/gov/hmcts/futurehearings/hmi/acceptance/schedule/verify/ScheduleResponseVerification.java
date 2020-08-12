package uk.gov.hmcts.futurehearings.hmi.acceptance.schedule.verify;

import static org.junit.Assert.assertEquals;

import uk.gov.hmcts.futurehearings.hmi.integration.common.TestingUtils;

import java.util.Map;

import io.restassured.response.Response;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class ScheduleResponseVerification {

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

    public static void verifyScheduleResponse (Response response) {

        //Option 1 - Use JsonPath - (Native Matcher to RestAssured and Serenity Rest)
        System.out.println(response.getBody().asString());
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("HMCTS",responseMap.get(("Name")));
        assertEquals("London",responseMap.get(("Place")));

        //Option 2 - Use a Json Equality based library like JsonAssert
        TestingUtils.comparePayloads("uk/gov/hmcts/futurehearings/hmi/acceptance/schedule/output/mock-demo-response.json",
                            response);

        //Option 3 - Use a better Third Party Specialised Matcher (Hamcrest)

    }

}
