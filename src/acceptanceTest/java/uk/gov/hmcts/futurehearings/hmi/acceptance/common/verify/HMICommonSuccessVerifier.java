package uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component("HMICommonSuccessVerifier")
public class HMICommonSuccessVerifier implements HMISuccessVerifier {

    @Override
    public void verify(final HttpStatus expectedHttpStatus, final String expectedMessage, final Response response) {
        log.info("FINALLY INSIDE THE SUCCESS VERFIER");
    }
}
