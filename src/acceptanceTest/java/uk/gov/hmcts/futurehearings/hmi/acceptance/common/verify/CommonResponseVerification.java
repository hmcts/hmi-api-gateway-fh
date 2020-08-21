package uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.TestingUtils;

import java.util.Map;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonResponseVerification {

    public static void verifyResponse (Response response, String expectedMessage) {

       if(response.getStatusCode() == 200) {
           verifySuccessResponse(response);
       } else {
           verifyFailedResponse(response, expectedMessage);
       }
    }

    public static void verifySuccessResponse(Response response) {
        //Option 1 - Use JsonPath - (Native Matcher to RestAssured and Serenity Rest)
        log.debug(response.getBody().asString());
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

    private static void verifyFailedResponse(Response response, String expectedMessage) {
        response.then().body("message", equalTo(expectedMessage));
    }

}
