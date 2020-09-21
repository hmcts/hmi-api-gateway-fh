package uk.gov.hmcts.futurehearings.hmi.acceptance.hearings.verify;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMISuccessVerifier;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class GETHearingsByQueryValidationVerifier implements HMISuccessVerifier {

    @Override
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {
        log.debug(response.getBody().asString());
        assertNotNull(response.getBody().jsonPath().getMap("$").size() > 1);
    }
}
