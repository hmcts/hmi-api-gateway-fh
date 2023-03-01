package uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate;

import io.restassured.http.Headers;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.dto.DelegateDTO;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.HMIVerifier;

import java.util.Map;

public interface CommonDelegate {


    public void test_expected_response_for_supplied_header(final String authorizationToken,
                                                           final String targetURL,
                                                           final String inputFile,
                                                           final Map<String, String> standardHeaderMap,
                                                           final Headers headers,
                                                           final Map<String, String> params,
                                                           final HttpMethod httpMethod,
                                                           final HttpStatus status,
                                                           final String inputFileDirectory,
                                                           final HMIVerifier hmiVerifier,
                                                           final String expectedMessage,
                                                           final DelegateDTO delegateDT0) throws Exception;
}
