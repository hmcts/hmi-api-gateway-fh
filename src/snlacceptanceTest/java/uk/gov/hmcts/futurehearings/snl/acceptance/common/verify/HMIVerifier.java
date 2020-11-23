package uk.gov.hmcts.futurehearings.snl.acceptance.common.verify;

import uk.gov.hmcts.futurehearings.snl.acceptance.common.dto.SNLDto;
import uk.gov.hmcts.futurehearings.snl.acceptance.common.verify.dto.SNLVerificationDTO;

import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

public interface HMIVerifier {

    void verify(SNLDto snlDto, Response response);
}
