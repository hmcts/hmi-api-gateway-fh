package uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Component("HMICommonErrorVerifier")
public class HmiCommonErrorVerifier implements HmiErrorVerifier {

    @Override
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {
        log.debug(response.getBody().asString());
        assertEquals(2, response.getBody().jsonPath().getMap("$").size(),
                "Body size is not 2");
        Map<String, ?> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals(expectedHttpStatus.value(), responseMap.get("statusCode"),
                "Status code do not match");
        assertEquals(expectedMessage, responseMap.get("message"),
                "Message do not match");
    }
}
