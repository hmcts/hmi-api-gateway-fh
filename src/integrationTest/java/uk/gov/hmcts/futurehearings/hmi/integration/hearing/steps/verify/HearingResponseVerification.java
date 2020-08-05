package uk.gov.hmcts.futurehearings.hmi.integration.hearing.steps.verify;

import static org.junit.Assert.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.integration.common.TestingUtils.comparePayloads;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import io.restassured.response.Response;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
@Slf4j
public class HearingResponseVerification {

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

    public static void verifyHearingResponse (Response response) {

        //Option 1 - Use JsonPath - (Native Matcher to RestAssured and Serenity Rest)
        log.info("Response : " + response.getBody().asString());
        assertEquals(2,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("HMCTS",responseMap.get(("Name")));
        assertEquals("London",responseMap.get(("Place")));

        //Option 2 - Use a Json Equality based library like JsonAssert
        comparePayloads("uk/gov/hmcts/futurehearings/hmi/integration/hearing/output/mock-demo-response.json",
                            response);

        //Option 3 - Use a better Third Party Specialised Matcher (Hamcrest)

    }

}
