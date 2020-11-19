package uk.gov.hmcts.futurehearings.snl.acceptance.common.verify.error;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component("HMICommonErrorVerifier")
public class HMICommonErrorVerifier implements HMIErrorVerifier {
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {
        log.debug(response.getBody().asString());
        assertEquals(2, response.getBody().jsonPath().getMap("$").size());
        Map<String, ?> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals(expectedHttpStatus.value(), responseMap.get("statusCode"));
        assertEquals(expectedMessage, responseMap.get(("message")));
    }
}
