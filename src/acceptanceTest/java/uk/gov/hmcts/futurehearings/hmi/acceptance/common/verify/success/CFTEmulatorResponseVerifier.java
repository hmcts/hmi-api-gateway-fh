package uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component("CFTEmulatorResponseVerifier")
public class CFTEmulatorResponseVerifier implements HMISuccessVerifier {
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {
        log.debug("Response" + response.getBody().asString());
        Map<String, ?> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals(expectedHttpStatus.value(),response.statusCode());
        assertNotNull(responseMap.get(("description")));
    }
}
