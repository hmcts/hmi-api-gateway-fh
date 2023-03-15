package uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error;

import io.restassured.response.Response;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Component("CaseHQCommonErrorVerifier")
@NoArgsConstructor
@SuppressWarnings({"PMD.LawOfDemeter"})
public class CaseHqCommonErrorVerifier implements HmiErrorVerifier {

    @Override
    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {

        log.debug(response.getBody().asString());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        assertEquals(2, response.getBody().jsonPath().getMap("$").size());
        Map<String, ?> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals(expectedMessage, responseMap.get("errorDesc"));
    }
}
