package uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component("testDelegate")
public abstract class CommonTestDelegate implements CommonDelegate {

    public void test_successful_response_in_a_post_test(final String targetSubscriptionKey,
                                                   final String targetURL,
                                                   final String inputFile) throws IOException {
        throw new RuntimeException("Not Implemented Yet");
    }
}
