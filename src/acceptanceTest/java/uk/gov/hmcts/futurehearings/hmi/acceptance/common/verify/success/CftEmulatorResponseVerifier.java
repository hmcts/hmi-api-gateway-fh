package uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@Component("CFTEmulatorResponseVerifier")
public class CftEmulatorResponseVerifier implements HmiSuccessVerifier {

    @Override
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {
        log.debug("Response" + response.getBody().asString());
        Map<String, ?> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals(expectedHttpStatus.value(), response.statusCode(),
                "Status code do not match");
        assertNotNull(responseMap.get("description"),
                "Description cannot be null");
    }
}
