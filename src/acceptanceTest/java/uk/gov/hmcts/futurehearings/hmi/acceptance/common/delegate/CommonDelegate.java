package uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate;

import io.restassured.http.Headers;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.dto.DelegateDto;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.HmiVerifier;

import java.util.Map;

public interface CommonDelegate {


    void test_expected_response_for_supplied_header(final String authorizationToken,
                                                           final String targetUrl,
                                                           final String inputFile,
                                                           final Map<String, String> standardHeaderMap,
                                                           final Headers headers,
                                                           final Map<String, String> params,
                                                           final HttpMethod httpMethod,
                                                           final HttpStatus status,
                                                           final String inputFileDirectory,
                                                           final HmiVerifier hmiVerifier,
                                                           final String expectedMessage,
                                                           final DelegateDto delegateDT0) throws Exception;
}
