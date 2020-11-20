package uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import javax.naming.OperationNotSupportedException;

import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component("CaseHQCommonErrorVerifier")
@NoArgsConstructor
public class CaseHQCommonErrorVerifier implements HMIErrorVerifier {

    private String returnHttpCode;
    private String returnErrorCode;
    private String returnDescription;

    public CaseHQCommonErrorVerifier(String returnHttpCode,
                                     String returnErrorCode,
                                     String returnDescription) {
        this.returnHttpCode = returnHttpCode;
        this.returnErrorCode = returnErrorCode;
        this.returnDescription = returnDescription;
    }

    public void verify(HttpStatus expectedHttpStatus,
                       String expectedMessage,
                       Response response) {

        log.debug(response.getBody().asString());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        assertEquals(2, response.getBody().jsonPath().getMap("$").size());
        Map<String, ?> responseMap = response.getBody().jsonPath().getMap("$");
        //assertEquals(expectedHttpStatus.value(), responseMap.get("statusCode"));
        assertEquals(expectedMessage, responseMap.get(("errorDesc")));
    }
}
