package uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public interface CommonDelegate {


    public void test_expected_response_for_supplied_header(final String targetSubscriptionKey,
                                                           final String targetURL,
                                                           final String inputFile,
                                                           final Map<String, String> standardHeaderMap,
                                                           final HttpMethod httpMethod,
                                                           final HttpStatus status,
                                                           final String apiName,
                                                           final String expectedMessage) throws IOException;
}