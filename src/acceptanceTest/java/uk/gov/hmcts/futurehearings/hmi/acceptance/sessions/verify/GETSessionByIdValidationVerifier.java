package uk.gov.hmcts.futurehearings.hmi.acceptance.sessions.verify;

import static org.junit.jupiter.api.Assertions.assertTrue;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMISuccessVerifier;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class GETSessionByIdValidationVerifier implements HMISuccessVerifier {

    @Override
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {
        log.debug(response.getBody().asString());
        assertTrue(response.getBody().jsonPath().getMap("$").size() > 1);
    }
}
