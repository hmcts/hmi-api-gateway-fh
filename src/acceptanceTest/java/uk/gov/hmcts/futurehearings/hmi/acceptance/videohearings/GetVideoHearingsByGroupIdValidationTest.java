package uk.gov.hmcts.futurehearings.hmi.acceptance.videohearings;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HmiCommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HmiCommonSuccessVerifier;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses(GetVideoHearingsByGroupIdValidationTest.class)
@IncludeTags("Get")
public class GetVideoHearingsByGroupIdValidationTest extends VideoHearingValidationTest {

    @Value("${videoHearings_GroupIdRootContext}")
    private String videoHearingsGroupIdRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        videoHearingsGroupIdRootContext = String.format(videoHearingsGroupIdRootContext, "InvalidGroupId123");
        this.setRelativeUrl(videoHearingsGroupIdRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setSourceSystem("SNL");
        this.setHttpSuccessStatus(HttpStatus.OK);
        this.setHmiSuccessVerifier(new HmiCommonSuccessVerifier());
        this.setHmiErrorVerifier(new HmiCommonErrorVerifier());
    }
}
