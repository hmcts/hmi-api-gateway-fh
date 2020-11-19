package uk.gov.hmcts.futurehearings.snl.acceptance.common.delegate;


import uk.gov.hmcts.futurehearings.snl.acceptance.common.verify.HMIVerifier;

import java.io.IOException;
import java.util.Map;

import io.restassured.http.Headers;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public interface CommonDelegate {


    public void test_expected_response_for_supplied_header(final String targetSubscriptionKey,
                                                           final String authorizationToken,
                                                           final String targetURL,
                                                           final String inputFile,
                                                           final Map<String, String> standardHeaderMap,
                                                           final Headers headers,
                                                           final Map<String, String> params,
                                                           final HttpMethod httpMethod,
                                                           final HttpStatus status,
                                                           final String inputFileDirectory,
                                                           final HMIVerifier hmiVerifier,
                                                           final String expectedMessage) throws IOException;
}