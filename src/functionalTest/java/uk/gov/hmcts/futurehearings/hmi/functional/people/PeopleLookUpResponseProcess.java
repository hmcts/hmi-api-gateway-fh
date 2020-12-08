package uk.gov.hmcts.futurehearings.hmi.functional.people;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Slf4j
public class PeopleLookUpResponseProcess {

    public static String getPeopleId(Response response) throws JSONException {
        log.debug(response.getBody().prettyPrint());
        //JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        //JSONObject jsonObject = JSONResponseBody.getJSONArray("People").getJSONObject(0);

        JSONArray jsonArray = new JSONArray(response.body().asString());
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        return jsonObject.getString("id");
    }
}
