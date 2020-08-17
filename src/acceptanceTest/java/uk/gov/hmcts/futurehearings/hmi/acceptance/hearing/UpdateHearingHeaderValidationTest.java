package uk.gov.hmcts.futurehearings.hmi.acceptance.hearing;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses(UpdateHearingHeaderValidationTest.class)
@IncludeTags("Put")
public class UpdateHearingHeaderValidationTest extends HearingValidationTest {

    @Qualifier("PostDelegate")
    @Autowired(required = true)
    private CommonDelegate commonDelegate;

    @Value("${updateHearingRootContext}")
    private String updateHearingRootContext;

    @BeforeAll
    public void initialiseValues() {
        super.initialiseValues();
        updateHearingRootContext = String.format(updateHearingRootContext,"12345");
        this.setRelativeURL(updateHearingRootContext);
    }
}
