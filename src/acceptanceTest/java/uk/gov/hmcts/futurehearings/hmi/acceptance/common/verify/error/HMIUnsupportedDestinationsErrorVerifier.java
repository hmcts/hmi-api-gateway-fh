package uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.HMIVerifier;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@Slf4j
@Component("HMIVerifier")
public class HMIUnsupportedDestinationsErrorVerifier implements HMIVerifier{
    public void verify(HttpStatus httpStatus, String expectedMessage,
                       Response response) {
        log.debug(response.getBody().asString());
        Map<String, ?> responseMap = response.getBody().jsonPath().getMap("$");
        String actualMessage = (String) responseMap.get(("message"));
        assertEquals(expectedMessage, actualMessage);
    }
}
