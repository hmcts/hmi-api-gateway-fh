package uk.gov.hmcts.futurehearings.hmi.acceptance.resources.verify;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMISuccessVerifier;

import java.util.Map;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
public class GETResourceByIDValidationVerifier implements HMISuccessVerifier {

    @Override
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {
        log.debug(response.getBody().asString());
        assertTrue(response.getBody().jsonPath().getMap("$").size() > 0);
        /*Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals(expectedHttpStatus.value(),responseMap.get(("statusCode")));
        assertEquals(expectedMessage,responseMap.get(("message")));*/
    }
}
