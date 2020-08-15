package uk.gov.hmcts.futurehearings.hmi.acceptance.common.test;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonPostDelegate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Setter
@Getter
public class CommonTest {

    private String apiSubscriptionKey;
    private String relativeURL;

}
