package uk.gov.hmcts.futurehearings.snl.acceptance.common.verify.success;

import static org.junit.jupiter.api.Assertions.assertEquals;

import uk.gov.hmcts.futurehearings.snl.acceptance.common.dto.SNLDto;
import uk.gov.hmcts.futurehearings.snl.acceptance.common.verify.dto.SNLVerificationDTO;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component("HMICommonSuccessVerifier")
public class HMICommonSuccessVerifier implements HMISuccessVerifier {
    public void verify(SNLDto snlDTO,
                       Response response) {
        log.debug("Response" + response.getBody().asString());
        SNLVerificationDTO snlVerificationDTO = null;
        if (snlDTO instanceof SNLVerificationDTO){
            snlVerificationDTO = (SNLVerificationDTO) snlDTO;
        }
        assertEquals(snlVerificationDTO.httpStatus().value(),response.statusCode());
    }
}
