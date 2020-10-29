package uk.gov.hmcts.futurehearings.hmi.acceptance.schedules.helper.verify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMISuccessVerifier;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
@Slf4j
public class GETSchedulesValidationVerifier implements HMISuccessVerifier {

    @Override
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {
        log.debug(response.getBody().asString());
        assertEquals(expectedHttpStatus.value(), response.getStatusCode());
        /*Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals(expectedHttpStatus.value(),responseMap.get(("statusCode")));
        assertEquals(expectedMessage,responseMap.get(("message")));*/
    }
}
