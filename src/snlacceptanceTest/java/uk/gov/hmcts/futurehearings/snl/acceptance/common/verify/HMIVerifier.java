package uk.gov.hmcts.futurehearings.snl.acceptance.common.verify;

import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

public interface HMIVerifier {

    void verify(HttpStatus expectedHttpStatus, String expectedMessage, Response response);
}
