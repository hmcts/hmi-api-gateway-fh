package uk.gov.hmcts.futurehearings.hmi.acceptance.people.verify;

import static org.junit.jupiter.api.Assertions.assertTrue;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMIErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMISuccessVerifier;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

@Slf4j
public class GETPeopleValidationErrorVerifier implements HMIErrorVerifier {

    @Override
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) throws Exception {
        log.debug(response.getBody().asString());
        /*assertTrue(response.getBody().jsonPath().getList("$").size()>0);
        String sessionId = null;
        JSONObject JSONResponseBody = new JSONObject(response.asString());
        JSONObject jsonObject = JSONResponseBody.getJSONArray("$").getJSONObject(0);
        log.debug("The value of the id "+jsonObject.getString(""));*/
    }
}
