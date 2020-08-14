package uk.gov.hmcts.futurehearings.hmi.acceptance.common.test;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Setter
@Getter
public abstract class CommonTest {

    @Autowired
    CommonDelegate common;

    private String apiSubscriptionKey;
    private String relativeURL;


    @Test
    @DisplayName("A Successfull Post Scenario")
    public void test_sucessfull_post() throws Exception {
        common.test_successfull_post(apiSubscriptionKey,
                relativeURL);
    }
}
