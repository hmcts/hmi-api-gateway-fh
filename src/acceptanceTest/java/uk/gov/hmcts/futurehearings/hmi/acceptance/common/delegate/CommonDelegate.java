package uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public interface CommonDelegate {

    public void test_successful_response_in_a_post_test(final String targetSubscriptionKey,
                                                        final String targetURL,
                                                        final String inputFile) throws IOException;

    public void test_expected_response_for_supplied_header(final String targetSubscriptionKey,
                                                           final String targetURL,
                                                           final String inputFile,
                                                           final Map<String,String> standardHeaderMap,
                                                           final HttpMethod httpMethod,
                                                           final HttpStatus status) throws IOException;

    public void test_successful_response_in_a_post(final String targetSubscriptionKey,
                                                   final String targetURL,
                                                   final String inputFile) throws IOException;


    public void test_source_system_removed_in_a_post(final String targetSubscriptionKey,
                                                                  final String targetURL,
                                                                  final String inputFile) throws IOException;

    public void test_source_system_nulled_in_a_post(final String targetSubscriptionKey,
                                                                 final String targetURL,
                                                                 final String inputFile) throws IOException;

    public void test_source_system_empty_in_a_post(final String targetSubscriptionKey,
                                                                final String targetURL,
                                                                final String inputFile) throws IOException;

}