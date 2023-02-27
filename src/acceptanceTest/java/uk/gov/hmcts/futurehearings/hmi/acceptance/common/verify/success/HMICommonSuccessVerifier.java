package uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Component("HMICommonSuccessVerifier")
public class HMICommonSuccessVerifier implements HMISuccessVerifier {
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {
        log.debug("Response" + response.getBody().asString());
        assertEquals(expectedHttpStatus.value(),response.statusCode());
    }
}
