package uk.gov.hmcts.futurehearings.hmi.acceptance.listings.verify;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMISuccessVerifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class GETListingsValidationVerifier implements HMISuccessVerifier {

    @Override
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {
        log.debug(response.getBody().asString());
        assertTrue(response.getBody().jsonPath().getList("$").size() > 0);
    }
}
