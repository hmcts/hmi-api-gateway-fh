package uk.gov.hmcts.futurehearings.hmi.acceptance.schedules.verify;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HmiSuccessVerifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class GetSchedulesValidationVerifier implements HmiSuccessVerifier {

    @Override
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {
        log.debug(response.getBody().asString());
        assertTrue(response.getBody().jsonPath().getList("$").isEmpty());
    }
}
