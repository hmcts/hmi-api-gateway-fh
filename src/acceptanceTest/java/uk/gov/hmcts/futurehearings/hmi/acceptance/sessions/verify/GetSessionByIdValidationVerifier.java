package uk.gov.hmcts.futurehearings.hmi.acceptance.sessions.verify;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HmiSuccessVerifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SuppressWarnings({"PMD.TestClassWithoutTestCases"})
public class GetSessionByIdValidationVerifier implements HmiSuccessVerifier {

    @Override
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {
        log.debug(response.getBody().asString());
        assertTrue(response.getBody().jsonPath().getMap("$").size() > 1,
                "Response body is empty");
    }
}
