package uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
@Component("CFTEmulatorErrorVerifier")
public class CftEmulatorErrorVerifier implements HmiErrorVerifier {
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {
        log.debug(response.getBody().asString());
        Map<String, ?> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals(expectedHttpStatus.value(), response.statusCode());
        assertNotNull(responseMap.get("error"));
    }
}