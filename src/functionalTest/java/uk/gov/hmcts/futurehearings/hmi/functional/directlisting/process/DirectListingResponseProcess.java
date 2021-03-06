package uk.gov.hmcts.futurehearings.hmi.functional.directlisting.process;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

@Slf4j
public class DirectListingResponseProcess {

    public static String getSessionId(Response response) throws JSONException {
        log.debug(response.getBody().prettyPrint());
        JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        JSONObject jsonObject = JSONResponseBody.getJSONObject("sessionsResponse").getJSONArray("sessions").getJSONObject(1);
        return jsonObject.getString("sessionIdCaseHQ");
    }
}
