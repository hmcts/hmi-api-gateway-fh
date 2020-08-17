package uk.gov.hmcts.futurehearings.hmi.acceptance.hearing;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.test.HMICommonHeaderTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class})
public class TestHearingValidationTest extends HMICommonHeaderTest {

    @Qualifier("testDelegate")
    @Autowired(required = true)
    private CommonDelegate commonDelegate;


}
