package uk.gov.hmcts.futurehearings.hmi.acceptance.resources.verify;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HmiSuccessVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class GetResourceByIdValidationVerifier implements HmiSuccessVerifier {

    @Override
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {
        log.debug(response.getBody().asString());
        assertEquals(22, response.getBody().jsonPath().getMap("$").size(),
                "Size of Json is not 22");
    }
}
