package uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success;

import static org.junit.Assert.assertEquals;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.TestingUtils;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMISuccessVerifier;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component("HMICommonSuccessVerifier")
public class HMICommonSuccessVerifier implements HMISuccessVerifier {

    @Override
    public void verify(final String expectedPayloadPath,
                       final Response response) {
        TestingUtils.comparePayloads(expectedPayloadPath,
                response);
    }
}
